## 后端测试

`http://localhost:8001/eduservice/course/getCourseList`

`http://localhost:8222/eduservice/course/getCourseList`



## Bug

- **Error creating bean** with name 'bannerAdminController': Unsatisfied dependency expressed through field 'bannerService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'crmBannerServiceImpl': Unsatisfied dependency expressed through field 'baseMapper'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: 
  - 将resource设为resource路径
  - 检查.iml文件
  - @MapperScan扫描