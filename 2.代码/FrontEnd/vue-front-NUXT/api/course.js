import request from '@/utils/request'

export default {
  //1.分页课程查询
  getCourseList(page, limit, searchObj) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },

  //2.查询所有分类（一级二级）
  getAllSubject(){
    return request({
      url: `/eduservice/subject/getAllSubject`,
      method: 'get',
    })
  }, 

  //3.课程详情
  getCourseInfo(courseId){
    return request({
      url: `/eduservice/coursefront/getFrontCourseInfo/${courseId}`,
      method: 'get',
    })
  }, 
}