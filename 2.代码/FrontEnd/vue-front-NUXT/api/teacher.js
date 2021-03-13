import request from '@/utils/request'

export default {
  //1.分页讲师查询
  getTeacherList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post',
    })
  },

  //2.讲师详情方法
  getTeacherInfo(teacherId){
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontInfo/${teacherId}`,
      method: 'get',
    })
  }, 
}