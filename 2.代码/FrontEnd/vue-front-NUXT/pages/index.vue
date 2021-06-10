<template>
  
  <div>
    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption" style="height: 500px">
        <div class="swiper-wrapper">

            <!-- 遍历 -->
            <div v-for="banner in bannerList" :key="banner.id" class="swiper-slide" style="background: #040B1B;">
                <a target="_blank" :href="banner.linkUrl">
                    <!-- alt:光标移动：显示信息 -->
                    <img  :src="banner.imageUrl" :alt="banner.title" class="dis c-v-pic">
                </a>
            </div>

        </div>
        <div class="swiper-pagination swiper-pagination-white"></div>
        <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
        <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
    </div>
    <!-- 幻灯片 结束 -->
    
     <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">Hot Movie</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <!-- 每个热门课程 开始 -->
                <li v-for="course in eduList" :key="course.id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img
                        :src="course.cover"
                        class="img-responsive"
                        :alt="course.title"
                      >
                      <div class="cc-mask">
                        <a :href="'/course/'+course.id" title="开始放映" class="comm-btn c-btn-1">Start Movie</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <!-- v-if：判断是否免费，Number():根据数字判断 -->
                      <span class="fr jgTag bg-green" v-if="Number(course.price) === 0">
                        <i class="c-fff fsize12 f-fA">Free</i>
                      </span>
                      <!-- <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">9634人学习</i>
                        |
                        <i class="c-999 f-fA">9634评论</i>
                      </span> -->
                    </section>
                  </div>
                </li>
                <!-- 每个热门课程 结束 -->
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a :href="'/course'" title="全部影片" class="comm-btn c-btn-2">All Movies</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">Super Stars</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="teacher in teacherList" :key="teacher.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a href="/teacher/1" :title="teacher.name">
                        <img :alt="teacher.name" :src="teacher.avatar">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a href="/teacher/1" :title="teacher.name" class="fsize18 c-666">{{teacher.name}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{teacher.career}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p
                        class="c-999 f-fA"
                      >{{teacher.intro}}</p>
                    </div>
                  </section>
                </li>
                
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/teacher" title="全部讲师" class="comm-btn c-btn-2">All Artist</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
  </div>
</template>

<script>

  import banner from '@/api/banner'
  import index from '@/api/index'

  export default {
    data () {
      return {
        swiperOption: {
          //配置分页
          pagination: {
            el: '.swiper-pagination'//分页的dom节点
          },
          //配置导航
          navigation: {
            nextEl: '.swiper-button-next',//下一页dom节点
            prevEl: '.swiper-button-prev'//前一页dom节点
          }
        },

        //banner
        bannerList:[],

        //课程和名师
        eduList:[],
        teacherList:[],


      }
    },

    created() {
      //调用查询banner
      this.getBannerList()
      //查询热门课程和名师
      this.getHotCourseTeacher()
    },

    methods: {
      //查询热门课程和名师
      getHotCourseTeacher(){
        index.getIndexData()
          .then(resposne => {
            this.eduList = resposne.data.data.eduCourseList
            this.teacherList = resposne.data.data.eduTeacherList
          })
      },


      //查询banner数据
      getBannerList(){
        banner.getListBanner()
          .then(response => {
            this.bannerList = response.data.data.list //上一个框架的request已经封装了一个.data,所以不用写2个
          })
      }
      
    },
  }
</script>