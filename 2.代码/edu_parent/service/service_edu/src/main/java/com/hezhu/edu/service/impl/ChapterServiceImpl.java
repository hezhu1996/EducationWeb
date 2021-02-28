package com.hezhu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.edu.entity.Chapter;
import com.hezhu.edu.entity.Video;
import com.hezhu.edu.entity.chapter.ChapterVo;
import com.hezhu.edu.entity.chapter.VideoVo;
import com.hezhu.edu.mapper.ChapterMapper;
import com.hezhu.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhu.edu.service.VideoService;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    //课程大纲列表：根据课程id
    @Override
    public List<ChapterVo> getChapterByCourseId(String courseId) {

        //1.根据课程id，在数据库中查询所有章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程id，查询所有小节
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

        //3.创建list集合，封装最终数据
        List<ChapterVo> finalList = new ArrayList<>();

        //4.遍历查询出来的章节chapterList，并一一封装到ChapterVo里面
        for(int i = 0; i < chapterList.size(); i++){
            //4.1 每个章节
            Chapter eduChapter = chapterList.get(i);
            //4.2 封装到chapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //4.3 把chapterVo放到最终list
            finalList.add(chapterVo);

            //5.遍历小节video
            //5.1 创建list，存储某个章节Chapter下的所有小节video
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < videoList.size(); j++){
                //5.2 每个小节
                Video eduVideo = videoList.get(j);
                //5.3 章节号Chapter和小节号video对应上
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //5.4 封装到videoVo
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //5.5 将查找到的小节封装进videoVoList
                    videoVoList.add(videoVo);
                }
            }
            //6.章节的children属性设为小节的list
            chapterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    //4. 删除章节：根据id
    @Override
    public boolean deleteChapter(String chapterId) {
        // 4.1 查询小节表：根据chapter_id，如果有数据，则不删除
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId); //在chapter_id列中，查询所有等于chapterId的数据

        //数据库操作
        int count = videoService.count(wrapper);

        if (count > 0) {
            //查询到有小节，不删除
            throw new HeZhuException(20001, "can not delete chapter");
        }
        else{
            //删除章节：1删除成功，0删除失败
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }
}





























