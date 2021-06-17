import request from '@/utils/request'

export default {

  //1.根据课程id，查询所有评论
  getPageList(page, limit, courseId) {
    return request({
      url: `/eduservice/comment/getCommentPage/${page}/${limit}`,
      method: 'get',
      params: {courseId}
    })
  },

  //2.添加评论
  addComment(comment) {
    return request({
      url: `/eduservice/comment/auth/addComment`,
      method: 'post',
      data: comment
    })
  }
}