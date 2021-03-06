<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">
        <!-- 添加按钮 -->
        <el-button type="text" @click="openChapterDialog">添加章节</el-button>

        <!-- 遍历章节 -->
        <ul class="chanpterList">
            <li
                v-for="chapter in chapterVideoList"
                :key="chapter.id">
                <p>
                    {{ chapter.title }}

                    <span class="acts">
                        <el-button type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                        <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                        <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                    </span>
                </p>

                <!-- 遍历小节 -->
                <ul class="chanpterList videoList">
                    <li
                        v-for="video in chapter.children"
                        :key="video.id">
                        <p>{{ video.title }}
                            <span class="acts">
                                <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>
        <div>
            <el-button @click="previous">上一步</el-button>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
        </div>
    </el-form>

    <!-- 弹窗：添加和修改"章节"表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改 小节 表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
    <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
        <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
        <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
        <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
        </el-radio-group>
        </el-form-item> 
        <!-- 上传视频 -->
        <el-form-item label="上传视频">
            <el-upload
                :on-success="handleVodUploadSuccess"
                :on-remove="handleVodRemove"
                :before-remove="beforeVodRemove"
                :on-exceed="handleUploadExceed"
                :file-list="fileList"
                :action="BASE_API+'/eduvod/video/uploadAliyunVideo'"
                :limit="1"
                class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
                <div slot="content">最大支持1G，<br>
                    支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                    GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                    MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                    SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                <i class="el-icon-question"/>
            </el-tooltip>
            </el-upload>
        </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
    </div>
    </el-dialog>


  </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'

export default {
//--------------------------- Data ---------------------------------
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      chapterVideoList:[], //所有章节数据
      courseId:'',
      
      //章节
      dialogChapterFormVisible:false, //弹框是否弹出
      chapter:{
        courseId:'',
        title: '',
        sort: 0
      },
      
      //小节
      dialogVideoFormVisible:false, //小节弹框
      video: {// 课时对象
        title: '',
        sort: 0,
        isFree: 0,
        videoSourceId: ''
      },
      
      //阿里云点播
      fileList: [],//上传文件列表
      BASE_API: process.env.BASE_API, // 接口API地址

    }
  },
//--------------------------- Create ---------------------------------
  created() {
    console.log('chapter created')
    //获取路由id值
    if(this.$route.params && this.$route.params.id){
        //给courseId赋值
        this.courseId = this.$route.params.id
        
        //根据课程id查询章节、小节
        this.getChapterVideo()
    }
    console.log('chapter finished')
  },

//--------------------------- Method ---------------------------------
  methods: {
//============================= 阿里云点播 =====================================
    //成功回调
    handleVodUploadSuccess(response, file, fileList){ //后端接口返回的数据被response自动接收
        //上传视频id赋值
        this.video.videoSourceId = response.data.videoId
        //上传视频名称赋值
        this.video.videoOriginalName = file.name

    },

    //视图上传多于一个视频
    handleUploadExceed(files, fileList) {
        this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },

    //点击×：调用beforeVodRemove -> 点击确定，调用handleVodRemove
    beforeVodRemove(file, fileList){
        return this.$confirm(`确定删除 ${file.name}?`)
    },

    //点击确定：调用handleVodRemove
    handleVodRemove(){
        //调用后端接口，在阿里云删除视频
        video.deleteAliyunVod(this.video.videoSourceId)
            .then(response => {
                //提示信息
                this.$message({
                    type: 'success',
                    message: '删除视频成功'
                });
                //清空文件列表
                this.fileList = []

                //清除数据库中的内容
                this.video.videoSourceId = ''
                this.video.videoOriginalName = ''

            })
    },



//============================= 小节操作 =====================================
    //1. 添加/修改 小节
    saveOrUpdateVideo(){
        if(!this.video.id){
            this.addVideo()
        }
        else{
            this.updateVideo()
        }
        
    },

    // 2.添加小节
    addVideo(){
        //* 小节中需要：设置课程id
        this.video.courseId = this.courseId
        //api
        video.addVideo(this.video)
            .then(response => {
                //2.1 关闭弹窗
                this.dialogVideoFormVisible = false

                //2.2 提示
                this.$message({
                    type: 'success',
                    message: '添加小节成功'
                });

                //2.3 刷新页面
                this.getChapterVideo()
            })
    },

    //3.添加小节弹框的方法
    openVideo(chapterId){
        this.dialogVideoFormVisible = true
        //清空表单
        this.video={}

        //小节中需要：设置章节id
        this.video.chapterId = chapterId

        //清空文件列表
        this.fileList = []
    },

    //4.修改小节
    updateVideo(){
        video.updateVideo(this.video)
            .then(response => {
                //3.1 关闭弹窗
                this.dialogVideoFormVisible = false

                //3.2 提示
                this.$message({
                    type: 'success',
                    message: '修改小节成功'
                });

                //3.3 刷新页面
                this.getChapterVideo()
            })
    },

    //5.修改小节：点开对话框 + 数据回显
    openEditVideo(videoId){
        //打开对话框
        this.dialogVideoFormVisible = true

        //调用api：根据videoId查找当前小节信息
        video.getVideo(videoId)
            .then(response => {
                //数据回显
                this.video = response.data.video
            })
    },

    //6.删除小节
    removeVideo(videoId){
        //1.删除提示框
        this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            //2.调用删除方法
            video.deleteVideoId(videoId)
            .then(response =>{ //删除成功
                //2.1提示信息
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                //2.2刷新列表页面
                this.getChapterVideo()
            })
            
        })
        .catch(() =>{ //3.取消删除
            this.$message({
                type: 'info',
                message: '已取消删除'
            });    
        })
    },

    

//============================= 章节操作 =====================================
    //1.添加/修改 章节
    saveOrUpdate(){
        if(!this.chapter.id){
            this.addChapter()
        }
        else{
            this.updateChapter()
        }
        
    },

    //2. 弹框：每次打开清空数据
    openChapterDialog(){
        //2.1 弹框显示
        this.dialogChapterFormVisible = true
        //2.2 表单清空
        this.chapter.title = ''
        this.chapter.sort = 0
    },

    //3.添加章节 
    addChapter(){
        //添加courseId到chapter中
        this.chapter.courseId = this.courseId
        //后端api
        chapter.addChapter(this.chapter)
            .then(response => {
                //3.1 关闭弹窗
                this.dialogChapterFormVisible = false

                //3.2 提示
                this.$message({
                    type: 'success',
                    message: '添加章节成功'
                });

                //3.3 刷新页面
                this.getChapterVideo()
            })
    },
    //4.修改章节
    updateChapter(){
        chapter.updateChapter(this.chapter)
            .then(response => {
                //4.1 关闭弹窗
                this.dialogChapterFormVisible = false

                //4.2 提示
                this.$message({
                    type: 'success',
                    message: '修改章节成功'
                });

                //4.3 刷新页面
                this.getChapterVideo()
            })
    },

    //5.修改章节 - 数据回显
    openEditChapter(chapterId){
        //5.1 弹框：用来修改
        this.dialogChapterFormVisible = true
        //5.2 api：查找章节，返回章节所有属性
        chapter.getChapter(chapterId)
            .then(response => {
                //数据回显：chapter有双向绑定
                this.chapter = response.data.chapter
            })
    },

    //6.删除章节
    removeChapter(chapterId){
        //1.删除提示框
        this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            //2.调用删除方法
            chapter.deleteChapter(chapterId)
            .then(response =>{ //删除成功
                //2.1提示信息
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                //2.2刷新列表页面
                this.getChapterVideo()
            })
            
        })
        .catch(() =>{ //3.取消删除
            this.$message({
                type: 'info',
                message: '已取消删除'
            });    
        })
    },





    //.根据课程id：查询所有章节、小节
    getChapterVideo(){
        chapter.getAllChapterVideo(this.courseId) //传入课程id
            .then(response => {
                //获取后端传回来的数据
                this.chapterVideoList = response.data.allChapterVideo
            })
    },

    //上一页
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/info/' + this.courseId})
    },


    //下一页
    next() {
      console.log('next')
      this.$router.push({ path: '/course/publish/' + this.courseId})
    },
    
  }
}
</script>



<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>