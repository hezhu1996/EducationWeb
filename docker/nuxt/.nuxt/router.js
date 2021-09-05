import Vue from 'vue'
import Router from 'vue-router'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _29e86f3d = () => interopDefault(import('..\\pages\\course\\index.vue' /* webpackChunkName: "pages_course_index" */))
const _2408cacf = () => interopDefault(import('..\\pages\\login.vue' /* webpackChunkName: "pages_login" */))
const _197a27ad = () => interopDefault(import('..\\pages\\register.vue' /* webpackChunkName: "pages_register" */))
const _7c096c3a = () => interopDefault(import('..\\pages\\teacher\\index.vue' /* webpackChunkName: "pages_teacher_index" */))
const _42531ce5 = () => interopDefault(import('..\\pages\\course\\_id.vue' /* webpackChunkName: "pages_course__id" */))
const _1ca5258c = () => interopDefault(import('..\\pages\\orders\\_oid.vue' /* webpackChunkName: "pages_orders__oid" */))
const _e7caf44c = () => interopDefault(import('..\\pages\\pay\\_pid.vue' /* webpackChunkName: "pages_pay__pid" */))
const _2e1f78fd = () => interopDefault(import('..\\pages\\player\\_vid.vue' /* webpackChunkName: "pages_player__vid" */))
const _4d993522 = () => interopDefault(import('..\\pages\\teacher\\_id.vue' /* webpackChunkName: "pages_teacher__id" */))
const _5a6127b8 = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages_index" */))

// TODO: remove in Nuxt 3
const emptyFn = () => {}
const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onComplete = emptyFn, onAbort) {
  return originalPush.call(this, location, onComplete, onAbort)
}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: decodeURI('/'),
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
    path: "/course",
    component: _29e86f3d,
    name: "course"
  }, {
    path: "/login",
    component: _2408cacf,
    name: "login"
  }, {
    path: "/register",
    component: _197a27ad,
    name: "register"
  }, {
    path: "/teacher",
    component: _7c096c3a,
    name: "teacher"
  }, {
    path: "/course/:id",
    component: _42531ce5,
    name: "course-id"
  }, {
    path: "/orders/:oid?",
    component: _1ca5258c,
    name: "orders-oid"
  }, {
    path: "/pay/:pid?",
    component: _e7caf44c,
    name: "pay-pid"
  }, {
    path: "/player/:vid?",
    component: _2e1f78fd,
    name: "player-vid"
  }, {
    path: "/teacher/:id",
    component: _4d993522,
    name: "teacher-id"
  }, {
    path: "/",
    component: _5a6127b8,
    name: "index"
  }],

  fallback: false
}

export function createRouter () {
  return new Router(routerOptions)
}
