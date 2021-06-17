import request from '@/utils/request'

export default {

  getPlayAuth(id) {
    return request({
      url: `/eduvod/video/getPlayAuth/${id}`,
      method: 'get'
    })
  }

}