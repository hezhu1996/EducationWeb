import request from '@/utils/request'

export default {
  //1.添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },

  //2.查询所有讲师
  getListTeacher(){
    return request({
        url: `/eduservice/teacher/findAll`,
        method: 'get',

      })
  },

  //3.查询课程信息
  getCourseInfoId(courseId) {
    return request({
      url: `/eduservice/course/getCourseInfo/${courseId}`,
      method: 'get'
    })
  },

  //4.修改课程信息
  updateCourseInfo(courseInfoVo) {
    return request({
      url: `/eduservice/course/updateCourseInfo`,
      method: 'post',
      data: courseInfoVo
    })
  },

  //5.根据课程id：获取课程全部属性信息
  getPublishCourseInfo(id) {
    return request({
      url: `/eduservice/course/getPublishCourseInfo/${id}`,
      method: 'get',
    })
  },

  //6.课程最终发布
  publishCourse(id) {
    return request({
      url: `/eduservice/course/publishCourse/${id}`,
      method: 'post',
    })
  },

  //7.查询课程列表
  getCourseList() {
    return request({
      url: `/eduservice/course/getCourseList`,
      method: 'get',
    })
  },

  //7.查询课程列表 + 分页 + 条件
  getCourseListPage(current, limit, courseQuery){
    return request({
        url: `/eduservice/course/pageCourseCondition/${current}/${limit}`,
        method: 'post',
        //courseQuery条件对象，后端使用RequestBody获取数据
        //data表示：对象转换为json传递到后端接口
        data: courseQuery
      })
  },

  //2.删除课程
  deleteCourseId(courseId){
    return request({
        url: `/eduservice/course/deleteCourse/${courseId}`,
        method: 'delete',
      })
},
}