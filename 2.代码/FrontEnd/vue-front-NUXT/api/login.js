import request from '@/utils/request'

export default {
  //1.用户登录
  submitLoginUser(userInfo) {
    return request({
      url: `/educenter/member/login`,
      method: 'post',
      data: userInfo
    })
  },
  
  //2.根据token，获取用户信息：前端头像显示
  getLoginInfo() {
    return request({
      url: `/educenter/member/getMemberInfo`,
      method: 'get',
    })
  }
}