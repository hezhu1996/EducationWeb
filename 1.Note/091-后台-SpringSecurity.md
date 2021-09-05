# SpringSecurity

https://blog.csdn.net/qq_42449963/article/details/109780205

## 1. 准备工作

### Nacos

* 网关服务 和 权限管理服务 的桥梁
* ![image-20210620111009098](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620111009098.png)

## 2. common.Security.security 认证授权工具类和处理器

<img src="https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620112109479.png" alt="image-20210620112109479" style="zoom:50%;" />



### 2.1 密码处理工具类

* 对密码做 MD5 加密

* ![image-20210620113132200](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620113132200.png)

* ```java
  /**
   * 密码的处理方法类型
   */
  @Component
  public class DefaultPasswordEncoder implements PasswordEncoder {
  
      public DefaultPasswordEncoder() {
          this(-1);
      }
  
      /**
       * @param strength
       * the log rounds to use, between 4 and 31
       */
      public DefaultPasswordEncoder(int strength) {
  
      }
  
      // MD5 加密
      public String encode(CharSequence rawPassword) {
          return MD5.encrypt(rawPassword.toString());
      }
  
      // 进行密码对比
      // (传入明文密码, 以加密密码)
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
          return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
      }
  }
  ```




### 2.2 使用 jwt 根据 用户名 生成 token

- ![image-20210620114313909](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620114313909.png)

- ```java
  /**
   * token管理
   */
  @Component
  public class TokenManager {
  
      // token 有效时长
      private long tokenExpiration = 24*60*60*1000;
      // 编码秘钥
      private String tokenSignKey = "123456";
  
      // 使用 jwt 根据 用户名 生成 token
      public String createToken(String username) {
          // 设置主体部分 username
          String token = Jwts.builder().setSubject(username)
                  // 设置有效时长
                  .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                  // 设置秘钥
                  .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
          return token;
      }
  
      // 根据 token 字符串得到用户信息
      public String getUserFromToken(String token) {
          String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
          return user;
      }
  
      public void removeToken(String token) {
          //jwttoken无需删除，客户端扔掉即可。
      }
  }
  ```

- 



### 2.3 退出处理器

- 移除 token，从 redis 中删除

- ![image-20210620115653265](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620115653265.png)

- ```java
  /**
   * 登出业务逻辑类
   */
  public class TokenLogoutHandler implements LogoutHandler {
  
      private TokenManager tokenManager;
      private RedisTemplate redisTemplate;
  
  
      // 构造函数：得到 tokenManager 和 redisTemplate
      public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
          this.tokenManager = tokenManager;
          this.redisTemplate = redisTemplate;
      }
  
      @Override
      public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
          // 1.从 header 里面获取 token
          String token = request.getHeader("token");
  
          if (token != null) {
              // 2.移除
              tokenManager.removeToken(token);
              // 3.从 token 中获取用户名
              String userName = tokenManager.getUserFromToken(token);
              // 4.清空 redis 中用户缓存的权限数据
              redisTemplate.delete(userName);
          }
          // 返回信息
          ResponseUtil.out(response, R.ok());
      }
  }
  ```

- 



### 2.4 未授权统一处理类

- 未授权时，执行该方法

- ![image-20210620120058624](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620120058624.png)

- ```java
  /**
   * 未授权的统一处理方式
   */
  public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
  
      // 未授权时，执行该方法
      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response,
                           AuthenticationException authException) throws IOException, ServletException {
  
          ResponseUtil.out(response, R.error());
      }
  }
  
  ```

- 



## 3. common.Security.filter 认证授权工具类和处理器

<img src="https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620112109479.png" alt="image-20210620112109479" style="zoom:50%;" />

### 3.1 TokenLoginFilter 认证过滤器

- `UserDetails` => Spring Security基础接口，包含某个用户的账号，密码，权限，状态（是否锁定）等信息。只有getter方法。
- `Authentication` => 认证对象，认证开始时创建，认证成功后存储于`SecurityContext`
- `principal` => 用户信息对象，是一个`Object`，**通常可转为 `UserDetails`**

**UserDetailsService接口**

- 那肯定是认证的时候。其实认证的操作，框架都已经帮你实现了，它所需要的只是，你给我提供获取信息的方式。所以它就定义一个接口，然后让你去实现，实现好了之后再注入给它。
- 框架提供一个`UserDetailsService`接口用来加载用户信息。如果要自定义实现的话，用户可以实现一个`CustomUserDetailsService`的类，然后把你的应用中的`UserService`和`AuthorityService`注入到这个类中，用户获取用户信息和权限信息，然后在`loadUserByUsername`方法中，构造一个User对象（框架的类）返回即可。

<img src="https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620161102640.png" alt="image-20210620161102640"  />

#### UsernamePasswordAuthenticationFilter 

- **拦截登录请求**并 **获取账号** 和 **密码**
- `authenticate` 最终会调用成员变量 `userDetailsService` 的方法 `loadUserByUsername()` 加载数据层中的用户信息
- https://www.jianshu.com/p/32fa221e03b7

#### 过程

1. `attemptAuthentication` 得到表单认证数据，将数据返回。这里会调用 `userDetailService`，去查数据库，返回用户信息
   - 传入 `username`， `userDetailService` 会根据 username，设置权限
   - ![image-20210620180458468](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620180458468.png)
2. 认证成功：调用 `successfulAuthentication`，传入 `authResult` （认证之后的用户信息）
   1. 得到 **用户信息**
   2. 根据 **用户名** 生成 `token`
   3. 将 `<用户名, 用户权限列表>`，存放入 `redis`
   4. 返回 `token` 信息
3.  `successfulAuthentication` 中传入的 `Authentication auth` 是 `attemptAuthentication` 中返回的权限信息，权限信息<自动调用?> `userDetailService` 查出来

- ![image-20210620160234399](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620160234399.png)![image-20210620160517536](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620160517536.png)

- ```java
  /**
   * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
   */
  public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
  
      // 官方认证(权限)管理
      private AuthenticationManager authenticationManager;
      // token 的操作
      private TokenManager tokenManager;
      // redis 的操作
      private RedisTemplate redisTemplate;
  
      public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
          // 构造注入
          this.authenticationManager = authenticationManager;
          this.tokenManager = tokenManager;
          this.redisTemplate = redisTemplate;
          // post 提交
          this.setPostOnly(false);
          // 登录路径 + 提交方式
          this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
      }
  
      // 1.获取表单提交的用户名和密码
      @Override
      public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
          try {
              // 获取表单提交数据
              User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
              // 返回用户权限
              return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                      user.getUsername(),
                      user.getPassword(),
                      new ArrayList<>()));
          }
          catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
  
      /**
       * 登录成功，调用此方法
       */
      @Override
      protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
                                              throws IOException, ServletException {
  
          System.out.println("===================登录成功======================");
  
          // 1.获取认证成功之后的用户信息
          SecurityUser user = (SecurityUser) auth.getPrincipal();
          // 2.根据用户名，生成token
          String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
          // 3.把 <用户名> 和 <用户权限列表> 放到 redis
          redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());
  
          ResponseUtil.out(res, R.ok().data("token", token));
      }
  
      /**
       * 登录失败，调用此方法
       */
      @Override
      protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
                                              throws IOException, ServletException {
  
          System.out.println("===================登录失败======================");
          ResponseUtil.out(response, R.error());
      }
  }
  ```

- 



### 3.2 TokenAuthenticationFilter 授权过滤器

<img src="https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620161242780.png" alt="image-20210620161242780"  />

- ![image-20210620163824503](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620163824503.png)![image-20210620164114780](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620164114780.png)

- ```java
  /**
   * 访问过滤器
   */
  public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
      private TokenManager tokenManager;
      private RedisTemplate redisTemplate;
  
      // 构造函数注入
      public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager,RedisTemplate redisTemplate) {
          super(authManager);
          this.tokenManager = tokenManager;
          this.redisTemplate = redisTemplate;
      }
  
      @Override
      protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                                                                              throws IOException, ServletException {
          logger.info("================="+req.getRequestURI());
  
          if(req.getRequestURI().indexOf("admin") == -1) {
              chain.doFilter(req, res);
              return;
          }
  
          // 1.获取当前认证成功用户权限信息，默认权限为 null
          UsernamePasswordAuthenticationToken authentication = null;
          try {
              // 根据request，获取用户权限
              authentication = getAuthentication(req);
          } catch (Exception e) {
              ResponseUtil.out(res, R.error());
          }
  
          // 2.如果有权限信息，放到权限上下文中
          if (authentication != null) {
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }
          else {
              ResponseUtil.out(res, R.error());
          }
          // 3.放行操作
          chain.doFilter(req, res);
      }
  
      // 获取用户权限
      private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
          // 1.从 header 中获取 token
          String token = request.getHeader("token");
          // 2.如果存在 token
          if (token != null && !"".equals(token.trim())) {
              // 1.从 token 获取用户名
              String userName = tokenManager.getUserFromToken(token);
              // 2.从 redis 获取权限列表
              List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
              // 3.权限列表信息
              Collection<GrantedAuthority> authorities = new ArrayList<>();
              // 4.将 redis 获取的权限列表，转换为集合 Collection<GrantedAuthority>
              for(String permissionValue : permissionValueList) {
                  if(StringUtils.isEmpty(permissionValue)) continue;
  
                  SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                  authorities.add(authority);
              }
              // 5.如果当前用户不为空，返回权限
              if (!StringUtils.isEmpty(userName)) {
                  return new UsernamePasswordAuthenticationToken(userName, token, authorities);
              }
              return null;
          }
          return null;
      }
  }
  ```

- 



## 3.common.Security.config 配置类

- ![image-20210620170937295](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620170937295.png)![image-20210620171239795](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620171239795.png)![image-20210620171107035](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620171107035.png)

- ```java
  /**
   * Security配置类
   */
  @Configuration // 配置类
  @EnableWebSecurity
  @EnableGlobalMethodSecurity(prePostEnabled = true)
  public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
  
      private UserDetailsService userDetailsService;
      private TokenManager tokenManager;
      private DefaultPasswordEncoder defaultPasswordEncoder;
      private RedisTemplate redisTemplate;
  
      // 构造函数注入
      @Autowired
      public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                    TokenManager tokenManager, RedisTemplate redisTemplate) {
          this.userDetailsService = userDetailsService;
          this.defaultPasswordEncoder = defaultPasswordEncoder;
          this.tokenManager = tokenManager;
          this.redisTemplate = redisTemplate;
      }
  
      /**
       * 配置设置
       */
      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http.exceptionHandling()
                  // 未授权访问
                  .authenticationEntryPoint(new UnauthorizedEntryPoint())
                  // 关闭csrf
                  .and().csrf().disable()
                  .authorizeRequests()
                  .anyRequest().authenticated()
                  // 退出路径
                  .and().logout().logoutUrl("/admin/acl/index/logout")
                  // 执行退出逻辑
                  .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                  // 添加自定义过滤器：认证过滤器
                  .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                  // 添加自定义过滤器：授权过滤器
                  .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
      }
  
      /**
       * 密码处理
       */
      @Override
      public void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
      }
  
      /**
       * 配置哪些请求不拦截
       */
      @Override
      public void configure(WebSecurity web) throws Exception {
          // 不需要认证路径
          web.ignoring().antMatchers(
                  "/api/**",
                  "/swagger-resources/**",
                  "/webjars/**",
                  "/v2/**",
                  "/swagger-ui.html/**");
      }
  }
  ```

## 4. UserDetailsServiceImpl

- 实现重写 `UserDetaisService`

- 返回 `UserDetails` 对象，其中包含 **用户名，密码，权限**

- `SecurityUser` 实现了 `userDetails` 接口，所以这里返回 `SecurityUser` 

- ![image-20210620180458468](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620180458468.png)

- ```java
  /**
   * 自定义userDetailsService - 认证用户详情
   */
  @Service("userDetailsService")
  public class UserDetailsServiceImpl implements UserDetailsService {
  
      @Autowired
      private UserService userService;
  
      @Autowired
      private PermissionService permissionService;
  
      /***
       * 根据用户名获取用户信息
       */
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          // 1.从数据库中取出 <用户信息>
          User user = userService.selectByUsername(username);
  
          // 2.判断用户是否存在
          if (null == user){
              //throw new UsernameNotFoundException("用户名不存在！");
          }
          // 3.数据库查出的 User，对拷入 secuirty.User 中
          com.hezhu.security.entity.User curUser = new com.hezhu.security.entity.User();
          BeanUtils.copyProperties(user,curUser);
          // 4.根据 User 的 Id，查询该用户权限
          List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
          // 5.将权限信息放入 SecurityUser ---> TokenLoginFilter 登陆成功使用，向 redis 中给当前用户写入权限
          SecurityUser securityUser = new SecurityUser(curUser);
          securityUser.setPermissionValueList(authorities);
  
          return securityUser;
      }
  }
  ```

- 编写实现类，返回 `UserDetails` 对象， `User` 对象有 **用户名** 和 **密码** 和 **操作权限** 
  - 这里 `User` 实现了 `UserDetail`，在类中已经为 UserDetail 设置了 用户名，密码 和 权限
  - ![image-20210618141058895](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210618141058895.png)



## 5. SecurityUser 实体类

- ![image-20210620181056088](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210620181056088.png)

- ```java
  /**
   * 安全认证用户详情信息
   */
  @Data
  @Slf4j
  public class SecurityUser implements UserDetails {
  
      //当前登录用户
      private transient User currentUserInfo;
  
      //当前权限
      private List<String> permissionValueList;
  
      public SecurityUser() {
      }
  
      public SecurityUser(User user) {
          if (user != null) {
              this.currentUserInfo = user;
          }
      }
  
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
          Collection<GrantedAuthority> authorities = new ArrayList<>();
          for(String permissionValue : permissionValueList) {
              if(StringUtils.isEmpty(permissionValue)) continue;
              SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
              authorities.add(authority);
          }
  
          return authorities;
      }
  
      @Override
      public String getPassword() {
          return currentUserInfo.getPassword();
      }
  
      @Override
      public String getUsername() {
          return currentUserInfo.getUsername();
      }
  
      @Override
      public boolean isAccountNonExpired() {
          return true;
      }
  
      @Override
      public boolean isAccountNonLocked() {
          return true;
      }
  
      @Override
      public boolean isCredentialsNonExpired() {
          return true;
      }
  
      @Override
      public boolean isEnabled() {
          return true;
      }
  }
  ```

# 源码解析

## 1. 认证过程

### 1.1 UsernamePasswordAuthenticationFilter

![image-20210621165328064](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210621165328064.png)



![img](https://upload-images.jianshu.io/upload_images/15200008-545f402fe2355967.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

































































