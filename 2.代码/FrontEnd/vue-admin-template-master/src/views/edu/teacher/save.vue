<template>
  <div class="app-container">

    讲师添加
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher.js'

export default {
  //1.定义变量和初始值
  data(){
    return{
      teacher:{
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: ''
      },
      saveBtnDisabled: false // 保存按钮是否禁用
    }
  },
  //2.页面渲染之前执行，一般调用methods定义的方法
  created(){
    this.init()
  },

  watch: { //监听
    $route(to, from) {
      this.init()
    }
  },

  //3.创建具体的方法
  methods:{
    init() {
      //2.1判断路径是否有id值
      if (this.$route.params && this.$route.params.id) {
        //2.2从路径中获取id值
        //this.$route.params得到路由中参数
        const id = this.$route.params.id
        //根据id值调用查询讲师方法
        this.getInfo(id)
      }
      else{
        //路径中没有id值，做添加
        this.teacher={} //清空表单
      }
    },


    //3.1 根据讲师id查询的方法
    getInfo(id){
      teacherApi.getTeacherInfo(id)
      .then(response => { //response获取返回值
        this.teacher = response.data.teacher  //这里右面的teacher是后端传回来的结果
      })

    },

    //3.2 修改讲师方法
    updateTeacher(){
      teacherApi.updateTeacher(this.teacher)
      .then(response =>{
        //提示信息
        this.$message({
            type: 'success',
            message: '修改成功!'
        });
        //跳转回列表页面，路由跳转
        this.$router.push({path:'/teacher/table'})
      })
    },

    //3.3 添加or更改讲师
    saveOrUpdate(){
      //判断是修改还是添加,根据teacher是否有id
      if(!this.teacher.id){
         //添加
      this.saveTeacher()
      } else{
        //修改
        this.updateTeacher()
      }
     
    },

    //3.4 添加讲师
    saveTeacher(){
      teacherApi.addTeacher(this.teacher)
      .then(response =>{ //添加成功
        //提示信息
        this.$message({
            type: 'success',
            message: '添加成功!'
        });
        //回到列表,路由跳转（redirect）
        this.$router.push({path:'/teacher/table'})
      })
    },
  }
}
</script>