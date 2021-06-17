<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">All movies</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">Category</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#">All</a>
                </li>
                <!-- 遍历一级分类 -->
                <!-- item从1开始，索引index从0开始 -->
                <!-- class: 选中背景改变 -->
                <li v-for="(item, index) in subjectNestedList" :key="index" :class="{active:oneIndex==index}">
                  <!-- 绑定事件，得到二级分类 -->
                  <a :title="item.title" href="#" @click="searchOne(item.id, index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <!-- 遍历二级分类 -->
                <li v-for="(item, index) in subSubjectList" :key="index" :class="{active:twoIndex==index}">
                  <a :title="item.title" href="#" @click="searchTwo(item.id, index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">Sales
                <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">Newest
                <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">Price&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>

        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>

          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total > 0">
            <ul class="film-list" id="bna">
              <!-- 遍历 -->
              <div class="film-list-item" v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <!--  -->
                  <section class="course-img">
                    <img :src="item.cover" 
                    class="img-responsive img-responsive--temp"
                    :alt="item.title"
                    >
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" title="开始学习" class="comm-btn c-btn-1">Start Movie</a>
                    </div>
                  </section>
                  <!--  -->
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{item.title}}</a>
                  </h3>
                  <!--  -->
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="Number(item.price) == 0">
                      <i class="c-fff fsize12 f-fA">FREE</i>
                    </span>
                    <!-- <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span> -->
                  </section>
                </div>
              </div>
              
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">First</a> <!-- prevent：阻止默认事件 -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current-1)">&lt;</a>
            
            <!-- 页面遍历 -->
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>

            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current+1)">&gt;</a>            
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)">Last</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>

import courseApi from '@/api/course'

export default {
  data() {
    return {
      page:1,
      data:{},
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表
      searchObj: {}, // 查询条件：表单对象
      oneIndex:-1, //一级分类，选中效果
      twoIndex:-1,//二级分类，选中效果
      buyCountSort:"",//选中效果
      gmtCreateSort:"",//选中效果
      priceSort:""//选中效果
    }
  },

  created() {
    //1.查询第一页数据
    this.initCourseFirst()

    //2.查询所有分类(一级,包含二级)
    this.initSubject()
  },

  methods: {
    //1.查询第一页数据
    initCourseFirst(){
      courseApi.getCourseList(1, 8, this.searchObj).then(response => {
        this.data = response.data.data
      })
    }, 

    //2.查询所有分类(一级,包含二级)
    initSubject(){
      courseApi.getAllSubject().then(response => {
        this.subjectNestedList = response.data.data.list
      })
    }, 

    //3.分页切换方法
    gotoPage(page){
      courseApi.getCourseList(page, 8, this.searchObj).then(response => {
        this.data = response.data.data
      })
    },

    //4.点击一级分类，查询对应二级分类
    searchOne(subjectParentId, index){

      //4.1把传递index赋值给oneIndex，为了active样式生效
      this.oneIndex = index

      this.twoIndex = -1
      this.searchObj.subjectId=""
      this.subSubjectList=[]

      //4.2把一级分类"选中"的id，赋值到searchObj
      this.searchObj.subjectParentId = subjectParentId
      //4.3点击一级分类，进行条件查询(gotoPage方法中，就有条件查询，只用传递page)
      this.gotoPage(1)


      //4.4拿着点击一级分类id 和 所有一级分类id比较，如果id相同，获取响应二级分类
      for(let i=0; i < this.subjectNestedList.length;i++){
        //获取每个一级分类
        var oneSubject = this.subjectNestedList[i]
        //比较id是否相同
        if(subjectParentId == oneSubject.id){
          //把一级分类的children放入二级分类
          this.subSubjectList = oneSubject.children;
        }
      }
    },

    //5.点击二级分类，实现查询
    searchTwo(subjectId, index){
      //5.1控制样式：把index赋值
      this.twoIndex = index

      //5.2把二级分类"选中"的id，赋值到searchObj
      this.searchObj.subjectId = subjectId
      //5.3点击二级分类，进行条件查询(gotoPage方法中，就有条件查询，只用传递page)
      this.gotoPage(1)
    },

    //6.根据销量排序
    searchBuyCount(){
      //6.1 设置对应变量值，使样式生效
      this.buyCountSort = "1";
      this.gmtCreateSort = "";
      this.priceSort = "";
      //把值，赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      //调用方法查询
      this.gotoPage(this.page)
    },

    //7.更新时间查询
    searchGmtCreate() {
      this.buyCountSort = "";
      this.gmtCreateSort = "1";
      this.priceSort = "";
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;
      this.gotoPage(this.page)
    },
    //8.价格查询
    searchPrice() {
      this.buyCountSort = "";
      this.gmtCreateSort = "";
      this.priceSort = "1";
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;
      this.gotoPage(this.page)
    },


  },
};
</script>
<style scoped>
  .active {
    background: #67cea7;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>

<style lang="css" scoped>
  .film-list{
    display: flex;
    width:100%;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .film-list-item{
    display: flex;
    width:20%;
    margin-right:1%;
    margin-bottom:20px;
  }
  .img-responsive--temp{
    height:200px;
    width:200px !important;
  }
  .film-list-item:hover{
    transform: scale(1.1);
  -webkit-transform: scale(1.1);
  }
  .comm-btn {
  border: 1px solid;
  border-radius: 16px;
  display: inline-block;
  text-align: center;
  height: 32px;
  line-height: 32px;
  padding: 0 12px;
}
.cc-mask:hover{
  text-decoration: none;
  opacity: 0.92;
  -moz-opacity: 0.92;
  filter: alpha(opacity=92);
}
.cc-mask .c-btn-1{
      left: 50%;
    margin-left: -72px;
    margin-top: -16px;
    top: 50%;
    transition: .3s;
}

</style>