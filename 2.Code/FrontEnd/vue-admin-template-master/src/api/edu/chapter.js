import request from '@/utils/request'

export default {
  //1.根据课程id，获取章节和小节数据
  getAllChapterVideo(courseId) {
    return request({
      url: `/eduservice/chapter/getChapterVideo/${courseId}`,
      method: 'get'
    //   data: courseId 因为是地址传值，所以不需要data
    })
  },

  //2.添加章节
  addChapter(chapter) {
    return request({
      url: `/eduservice/chapter/addChapter`,
      method: 'post',
      data: chapter, //传到后端
    })
  },

  //3.查询章节：根据id
  getChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/getChapterInfo/${chapterId}`,
      method: 'get',
    })
  },

  //4. 更新章节
  updateChapter(chapter) {
    return request({
      url: `/eduservice/chapter/updateChapter`,
      method: 'post',
      data: chapter, //传到后端
    })
  },

  //5. 删除章节：根据id
  deleteChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/deleteChapter/${chapterId}`,
      method: 'delete',
    })
  },



}