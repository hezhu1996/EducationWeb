import request from '@/utils/request'

export default{
    //1.讲师列表（条件分页查询）
    //current当前页；limit每页记录数；teacherQuery条件对象
    getTeacherListPage(current, limit, teacherQuery){
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            //teacherQuery条件对象，后端使用RequestBody获取数据
            //data表示：对象转换为json传递到后端接口
            data: teacherQuery
          })
    },

    //2.删除讲师
    deleteTeacherId(id){
        return request({
            url: `/eduservice/teacher/${id}`,
            method: 'delete',
          })
    },
    
    //3.添加讲师（requestbody接收参数teacher）
    addTeacher(teacher){
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: teacher, //自动转为json
          })
    },
    //4.根据id查询讲师
    getTeacherInfo(id){
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get',
          })
    },
    //5.修改讲师
    updateTeacher(teacher){
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: teacher
          })
    },
}


