import request from '@/utils/request'

export default {
    //1.生成订单
    createOrders(courseId) {
        return request({
          url: `/eduorder/order/createOrder/${courseId}`,
          method: 'post',
        })
      },

    //2.根据订单id，查询订单信息
    getOrdersInfo(orderId) {
      return request({
        url: `/eduorder/order/getOrderInfo/${orderId}`,
        method: 'get',
      })
    },  

    //3.生成微信支付二维码接口
    createNative(orderNo) {
      return request({
        url: `/eduorder/paylog/createNative/${orderNo}`,
        method: 'get',
      })
    },  

    //4.查询订单状态：根据订单号
    queryPayStatus(orderNo) {
      return request({
        url: `/eduorder/paylog/queryPayStatus/${orderNo}`,
        method: 'get',
      })
    },  
}