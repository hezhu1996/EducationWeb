package com.hezhu.edu.controller;


import com.hezhu.commonutils.R;
import com.hezhu.edu.entity.Chapter;
import com.hezhu.edu.entity.chapter.ChapterVo;
import com.hezhu.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin

public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    //1. 课程大纲列表：根据课程id
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    //2. 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    //3. 修改章节 = 根据id查询章节 + 修改章节
    //3.1 查询章节：根据id
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        Chapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    //3.2 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //4. 删除章节：根据id
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }
        else{
            return R.error();
        }
    }
}










