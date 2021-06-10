<template>
  <div class="app-container">
    Movie List
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="Movie Name"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="Status">
          <el-option :value="'Normal'" label="Published"/>
          <el-option :value="'Draft'" label="Not Published"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">Search</el-button>
      <el-button type="default" @click="resetData()">Clear</el-button>
    </el-form>


    <!-- 表格 -->
    <el-table
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="Serial Number"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="Movie Name" width="80" />

      <el-table-column label="Status" width="80">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'Published':'Not Published' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="Hours" />

      <el-table-column prop="gmtCreate" label="Insertion Date" width="160"/>

      <el-table-column prop="viewCount" label="Watched Number" width="60" />

      <el-table-column label="Operation" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">Edit Basic</el-button>
          </router-link>

          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">Edit episode</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"  
    />


  </div>
</template>

<script>
//引入调用teacher.js文件
import course from '@/api/edu/course'


export default {
    //核心代码位置
    data(){  //定义变量和初始值
        return{
            list:null, //查询之后接口返回集合
            page:1,  //当前页
            limit:5, //每页记录
            total:0, //总记录数
            courseQuery:{} //条件封装对象
        }
    },
    
    created(){ //页面渲染之前执行，一般调用methods定义的方法
        //调用
        this.getList()
    },

    methods:{ //创建具体的方法，调用teacher.js定义的方法
        //课程列表的方法
        getList(page=1){
            this.page = page
            course.getCourseListPage(this.page, this.limit, this.courseQuery)
            .then(response => {
                //response接口返回的数据
                console.log(response)
                this.list = response.data.records
                this.total = response.data.total
                console.log(this.list)
                console.log(this.total)

            }) //请求成功
            .catch(error => {console.log(error)}) //请求失败
        },
        //清空(不需要请求后端)
        resetData(){ 
            //表单输入项清空
            this.courseQuery = {}

            //查询所有讲师
            this.getList()
        },

        //删除课程方法
        removeDataById(id){
            //1.删除提示框
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //2.调用删除方法
                course.deleteCourseId(id)
                .then(response =>{ //删除成功
                    //2.1提示信息
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //2.2刷新列表页面
                    this.getList(this.page)
                })
                
            })
            .catch(() =>{ //3.取消删除
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });    
            })
        }
    }

}
</script>