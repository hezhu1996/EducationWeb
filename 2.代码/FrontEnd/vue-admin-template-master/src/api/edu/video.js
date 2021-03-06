import request from '@/utils/request'

export default {

  //1.添加小节
  addVideo(video) {
    return request({
      url: `/eduservice/video/addVideo`,
      method: 'post',
      data: video, //传到后端
    })
  },

  //2.查询小节：根据id
  getVideo(videoId) {
    return request({
      url: `/eduservice/video/getVideoInfo/${videoId}`,
      method: 'get',
    })
  },

  //3. 更新小节
  updateVideo(video) {
    return request({
      url: `/eduservice/video/updateVideo`,
      method: 'post',
      data: video, //传到后端
    })
  },

  //4. 删除小节：根据id
  deleteVideoId(videoId) {
    return request({
      url: `/eduservice/video/deleteVideo/${videoId}`,
      method: 'delete',
    })
  },

  //5. 删除阿里云视频：根据videoid
  deleteAliyunVod(videoId) {
    return request({
      url: `/eduvod/video/removeAliyunVideo/${videoId}`,
      method: 'delete',
    })
  },

}