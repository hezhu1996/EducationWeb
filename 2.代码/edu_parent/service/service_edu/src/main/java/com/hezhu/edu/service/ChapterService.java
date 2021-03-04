package com.hezhu.edu.service;

import com.hezhu.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hezhu.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
public interface ChapterService extends IService<Chapter> {
    //课程大纲列表：根据课程id
    List<ChapterVo> getChapterByCourseId(String courseId);
    //4. 删除章节：根据id
    boolean deleteChapter(String chapterId);

    //根据课程id删除：章节
    void removeChapterByCourseId(String courseId);
}
