启动

1. nginx
2. nacos
3. redis（依据需要，存储短信验证码等）

## Bug

- **Error creating bean** with name 'bannerAdminController': Unsatisfied dependency expressed through field 'bannerService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'crmBannerServiceImpl': Unsatisfied dependency expressed through field 'baseMapper'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: 
  - 将resource设为resource路径
  - 检查.iml文件
  - @MapperScan扫描

