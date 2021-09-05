/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.25 : Database - guli
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`guli` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `guli`;

/*Table structure for table `acl_permission` */

DROP TABLE IF EXISTS `acl_permission`;

CREATE TABLE `acl_permission` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '编号',
  `pid` char(19) NOT NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限';

/*Data for the table `acl_permission` */

insert  into `acl_permission`(`id`,`pid`,`name`,`type`,`permission_value`,`path`,`component`,`icon`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','0','全部数据',0,NULL,'','',NULL,NULL,0,'2019-11-15 17:13:06','2019-11-15 17:13:06'),('1195268474480156673','1','Auth Management',1,NULL,'/acl','Layout',NULL,NULL,0,'2019-11-15 17:13:06','2019-11-18 13:54:25'),('1195268616021139457','1195268474480156673','User Management',1,NULL,'user/list','/acl/user/list',NULL,NULL,0,'2019-11-15 17:13:40','2019-11-18 13:53:12'),('1195268788138598401','1195268474480156673','Role Management',1,NULL,'role/list','/acl/role/list',NULL,NULL,0,'2019-11-15 17:14:21','2019-11-15 17:14:21'),('1195268893830864898','1195268474480156673','Manu Management',1,NULL,'menu/list','/acl/menu/list',NULL,NULL,0,'2019-11-15 17:14:46','2019-11-15 17:14:46'),('1195269143060602882','1195268616021139457','查看',2,'user.list','','',NULL,NULL,0,'2019-11-15 17:15:45','2019-11-17 21:57:16'),('1195269295926206466','1195268616021139457','添加',2,'user.add','user/add','/acl/user/form',NULL,NULL,0,'2019-11-15 17:16:22','2019-11-15 17:16:22'),('1195269473479483394','1195268616021139457','修改',2,'user.update','user/update/:id','/acl/user/form',NULL,NULL,0,'2019-11-15 17:17:04','2019-11-15 17:17:04'),('1195269547269873666','1195268616021139457','删除',2,'user.remove','','',NULL,NULL,0,'2019-11-15 17:17:22','2019-11-15 17:17:22'),('1195269821262782465','1195268788138598401','修改',2,'role.update','role/update/:id','/acl/role/form',NULL,NULL,0,'2019-11-15 17:18:27','2019-11-15 17:19:53'),('1195269903542444034','1195268788138598401','查看',2,'role.list','','',NULL,NULL,0,'2019-11-15 17:18:47','2019-11-15 17:18:47'),('1195270037005197313','1195268788138598401','添加',2,'role.add','role/add','/acl/role/form',NULL,NULL,0,'2019-11-15 17:19:19','2019-11-18 11:05:42'),('1195270442602782721','1195268788138598401','删除',2,'role.remove','','',NULL,NULL,0,'2019-11-15 17:20:55','2019-11-15 17:20:55'),('1195270621548568578','1195268788138598401','角色权限',2,'role.acl','role/distribution/:id','/acl/role/roleForm',NULL,NULL,0,'2019-11-15 17:21:38','2019-11-15 17:21:38'),('1195270744097742849','1195268893830864898','查看',2,'permission.list','','',NULL,NULL,0,'2019-11-15 17:22:07','2019-11-15 17:22:07'),('1195270810560684034','1195268893830864898','添加',2,'permission.add','','',NULL,NULL,0,'2019-11-15 17:22:23','2019-11-15 17:22:23'),('1195270862100291586','1195268893830864898','修改',2,'permission.update','','',NULL,NULL,0,'2019-11-15 17:22:35','2019-11-15 17:22:35'),('1195270887933009922','1195268893830864898','删除',2,'permission.remove','','',NULL,NULL,0,'2019-11-15 17:22:41','2019-11-15 17:22:41'),('1195349439240048642','1','Artist Management',1,NULL,'/teacher','Layout',NULL,NULL,0,'2019-11-15 22:34:49','2019-11-15 22:34:49'),('1195349699995734017','1195349439240048642','List Artist',1,NULL,'table','/edu/teacher/list',NULL,NULL,0,'2019-11-15 22:35:52','2019-11-15 22:35:52'),('1195349810561781761','1195349439240048642','Insert Artist',1,NULL,'save','/edu/teacher/save',NULL,NULL,0,'2019-11-15 22:36:18','2019-11-15 22:36:18'),('1195349876252971010','1195349810561781761','添加',2,'teacher.add','','',NULL,NULL,0,'2019-11-15 22:36:34','2019-11-15 22:36:34'),('1195349979797753857','1195349699995734017','查看',2,'teacher.list','','',NULL,NULL,0,'2019-11-15 22:36:58','2019-11-15 22:36:58'),('1195350117270261762','1195349699995734017','修改',2,'teacher.update','edit/:id','/edu/teacher/save',NULL,NULL,0,'2019-11-15 22:37:31','2019-11-15 22:37:31'),('1195350188359520258','1195349699995734017','删除',2,'teacher.remove','','',NULL,NULL,0,'2019-11-15 22:37:48','2019-11-15 22:37:48'),('1195350299365969922','1','Movie Category',1,NULL,'/subject','Layout',NULL,NULL,0,'2019-11-15 22:38:15','2019-11-15 22:38:15'),('1195350397751758850','1195350299365969922','Category List',1,NULL,'list','/edu/subject/list',NULL,NULL,0,'2019-11-15 22:38:38','2019-11-15 22:38:38'),('1195350500512206850','1195350299365969922','Import Category',1,NULL,'save','/edu/subject/save',NULL,NULL,0,'2019-11-15 22:39:03','2019-11-15 22:39:03'),('1195350612172967938','1195350397751758850','查看',2,'subject.list','','',NULL,NULL,0,'2019-11-15 22:39:29','2019-11-15 22:39:29'),('1195350687590748161','1195350500512206850','导入',2,'subject.import','','',NULL,NULL,0,'2019-11-15 22:39:47','2019-11-15 22:39:47'),('1195350831744782337','1','Movie Management',1,NULL,'/course','Layout',NULL,NULL,0,'2019-11-15 22:40:21','2019-11-15 22:40:21'),('1195350919074385921','1195350831744782337','Movie List',1,NULL,'list','/edu/course/list',NULL,NULL,0,'2019-11-15 22:40:42','2019-11-15 22:40:42'),('1195351020463296513','1195350831744782337','Movie Publishment',1,NULL,'info','/edu/course/info',NULL,NULL,0,'2019-11-15 22:41:06','2019-11-15 22:41:06'),('1195351159672246274','1195350919074385921','完成发布',2,'course.publish','publish/:id','/edu/course/publish',NULL,NULL,0,'2019-11-15 22:41:40','2019-11-15 22:44:01'),('1195351326706208770','1195350919074385921','编辑课程',2,'course.update','info/:id','/edu/course/info',NULL,NULL,0,'2019-11-15 22:42:19','2019-11-15 22:42:19'),('1195351566221938690','1195350919074385921','编辑课程大纲',2,'chapter.update','chapter/:id','/edu/course/chapter',NULL,NULL,0,'2019-11-15 22:43:17','2019-11-15 22:43:17'),('1195351862889254913','1','Statistic',1,NULL,'/sta','Layout',NULL,NULL,0,'2019-11-15 22:44:27','2019-11-15 22:44:27'),('1195351968841568257','1195351862889254913','Generate Statistics',1,NULL,'create','/sta/create',NULL,NULL,0,'2019-11-15 22:44:53','2019-11-15 22:44:53'),('1195352054917074946','1195351862889254913','Statistical Graph',1,NULL,'show','/sta/show',NULL,NULL,0,'2019-11-15 22:45:13','2019-11-15 22:45:13'),('1195352127734386690','1195352054917074946','查看',2,'daily.list','','',NULL,NULL,0,'2019-11-15 22:45:30','2019-11-15 22:45:30'),('1195352215768633346','1195351968841568257','生成',2,'daily.add','','',NULL,NULL,0,'2019-11-15 22:45:51','2019-11-15 22:45:51'),('1195352547621965825','1','CMS管理',1,NULL,'/cms','Layout',NULL,NULL,0,'2019-11-15 22:47:11','2019-11-18 10:51:46'),('1195352856645701633','1195353513549205505','查看',2,'banner.list','',NULL,NULL,NULL,0,'2019-11-15 22:48:24','2019-11-15 22:48:24'),('1195352909401657346','1195353513549205505','添加',2,'banner.add','banner/add','/cms/banner/form',NULL,NULL,0,'2019-11-15 22:48:37','2019-11-18 10:52:10'),('1195353051395624961','1195353513549205505','修改',2,'banner.update','banner/update/:id','/cms/banner/form',NULL,NULL,0,'2019-11-15 22:49:11','2019-11-18 10:52:05'),('1195353513549205505','1195352547621965825','Bander List',1,NULL,'banner/list','/cms/banner/list',NULL,NULL,0,'2019-11-15 22:51:01','2019-11-18 10:51:29'),('1195353672110673921','1195353513549205505','删除',2,'banner.remove','','',NULL,NULL,0,'2019-11-15 22:51:39','2019-11-15 22:51:39'),('1195354076890370050','1','订单管理',1,NULL,'/order','Layout',NULL,NULL,0,'2019-11-15 22:53:15','2019-11-15 22:53:15'),('1195354153482555393','1195354076890370050','Order',1,NULL,'list','/order/list',NULL,NULL,0,'2019-11-15 22:53:33','2019-11-15 22:53:58'),('1195354315093282817','1195354153482555393','查看',2,'order.list','','',NULL,NULL,0,'2019-11-15 22:54:12','2019-11-15 22:54:12'),('1196301740985311234','1195268616021139457','分配角色',2,'user.assgin','user/role/:id','/acl/user/roleForm',NULL,NULL,0,'2019-11-18 13:38:56','2019-11-18 13:38:56');

/*Table structure for table `acl_role` */

DROP TABLE IF EXISTS `acl_role`;

CREATE TABLE `acl_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `acl_role` */

insert  into `acl_role`(`id`,`role_name`,`role_code`,`remark`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','普通管理员',NULL,NULL,0,'2019-11-11 13:09:32','2019-11-18 10:27:18'),('1193757683205607426','课程管理员',NULL,NULL,0,'2019-11-11 13:09:45','2019-11-18 10:25:44'),('1196300996034977794','test',NULL,NULL,0,'2019-11-18 13:35:58','2019-11-18 13:35:58'),('1373629470695575553','test1010',NULL,NULL,0,'2021-03-21 21:36:01','2021-03-21 21:36:01');

/*Table structure for table `acl_role_permission` */

DROP TABLE IF EXISTS `acl_role_permission`;

CREATE TABLE `acl_role_permission` (
  `id` char(19) NOT NULL DEFAULT '',
  `role_id` char(19) NOT NULL DEFAULT '',
  `permission_id` char(19) NOT NULL DEFAULT '',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色权限';

/*Data for the table `acl_role_permission` */

insert  into `acl_role_permission`(`id`,`role_id`,`permission_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1373629504405196802','1373629470695575553','1195268616021139457',0,'2021-03-21 21:36:09','2021-03-21 21:36:09'),('1373629504405196803','1373629470695575553','1195269143060602882',0,'2021-03-21 21:36:09','2021-03-21 21:36:09'),('1373629504405196804','1373629470695575553','1195269295926206466',0,'2021-03-21 21:36:09','2021-03-21 21:36:09'),('1373629504405196805','1373629470695575553','1195269473479483394',0,'2021-03-21 21:36:09','2021-03-21 21:36:09'),('1373629504413585409','1373629470695575553','1195269547269873666',0,'2021-03-21 21:36:09','2021-03-21 21:36:09'),('1373629504413585410','1373629470695575553','1196301740985311234',0,'2021-03-21 21:36:09','2021-03-21 21:36:09'),('1373645429871325186','1','1195268616021139457',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429871325187','1','1195269143060602882',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429879713794','1','1195269295926206466',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429879713795','1','1195269473479483394',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429879713796','1','1195269547269873666',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429879713797','1','1196301740985311234',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429879713798','1','1',0,'2021-03-21 22:39:26','2021-03-21 22:39:26'),('1373645429879713799','1','1195268474480156673',0,'2021-03-21 22:39:26','2021-03-21 22:39:26');

/*Table structure for table `acl_user` */

DROP TABLE IF EXISTS `acl_user`;

CREATE TABLE `acl_user` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '微信openid',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `salt` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `token` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `acl_user` */

insert  into `acl_user`(`id`,`username`,`password`,`nick_name`,`salt`,`token`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','admin','96e79218965eb72c92a549dd5a330112','admin','',NULL,0,'2019-11-01 10:39:47','2019-11-01 10:39:47'),('1373674259382116354','123','4297f44b13955235245b2497399d7a93','123',NULL,NULL,0,'2021-03-22 00:33:59','2021-03-22 00:43:45');

/*Table structure for table `acl_user_role` */

DROP TABLE IF EXISTS `acl_user_role`;

CREATE TABLE `acl_user_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
  `role_id` char(19) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` char(19) NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `acl_user_role` */

insert  into `acl_user_role`(`id`,`role_id`,`user_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1373629792490967042','1373629470695575553','2',0,'2021-03-21 21:37:17','2021-03-21 21:37:17'),('1373630319488487426','1','1373629744243888129',0,'2021-03-21 21:39:23','2021-03-21 21:39:23'),('1373633643222749186','1','1373633619524931586',0,'2021-03-21 21:52:36','2021-03-21 21:52:36'),('1373637886084231170','1','1373637838713761793',0,'2021-03-21 22:09:27','2021-03-21 22:09:27'),('1373637886084231171','1193757683205607426','1373637838713761793',0,'2021-03-21 22:09:27','2021-03-21 22:09:27'),('1373645664584577026','1','1373644544684437506',0,'2021-03-21 22:40:22','2021-03-21 22:40:22'),('1373646010098757634','1','1373645981543936001',0,'2021-03-21 22:41:44','2021-03-21 22:41:44');

/*Table structure for table `crm_banner` */

DROP TABLE IF EXISTS `crm_banner`;

CREATE TABLE `crm_banner` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `link_url` varchar(500) DEFAULT '' COMMENT '链接地址',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页banner表';

/*Data for the table `crm_banner` */

insert  into `crm_banner`(`id`,`title`,`image_url`,`link_url`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1194556896025845762','test1','https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2912964659,843222402&fm=26&gp=0.jpg','/course',1,0,'2019-11-13 18:05:32','2019-11-18 10:28:22'),('1194607458461216769','test2','https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/e18a4f2ae2f2f98a585727eee98fd7c1.jpeg','/teacher',2,0,'2019-11-13 21:26:27','2019-11-14 09:12:15');

/*Table structure for table `edu_chapter` */

DROP TABLE IF EXISTS `edu_chapter`;

CREATE TABLE `edu_chapter` (
  `id` char(19) NOT NULL COMMENT '章节ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `title` varchar(50) NOT NULL COMMENT '章节名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_chapter` */

insert  into `edu_chapter`(`id`,`course_id`,`title`,`sort`,`gmt_create`,`gmt_modified`) values ('1370567374835613698','1370567317658861570','Amateur',1,'2021-03-13 10:48:20','2021-06-08 00:19:59'),('1373714027063132161','1373713971081756673','哈利波特全集',1,'2021-03-22 03:12:01','2021-03-22 03:12:01'),('1373755260154265601','1373755210539843586','傲慢与偏见',1,'2021-03-22 05:55:51','2021-03-22 05:55:51'),('1373779757213270018','1373779732416544769','The Shawshank Redemption',1,'2021-03-22 07:33:12','2021-06-09 01:26:53'),('1403165569728909314','1403165518621315073','你好李焕英',1,'2021-06-11 09:41:56','2021-06-11 09:41:56'),('1403167908003057665','1403167818777628674','天気の子',1,'2021-06-11 09:51:14','2021-06-11 09:51:14'),('1403169147415695361','1403169079828680706','Wrath of Man',0,'2021-06-11 09:56:09','2021-06-11 09:56:09');

/*Table structure for table `edu_comment` */

DROP TABLE IF EXISTS `edu_comment`;

CREATE TABLE `edu_comment` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `teacher_id` char(19) NOT NULL DEFAULT '' COMMENT '讲师id',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论';

/*Data for the table `edu_comment` */

insert  into `edu_comment`(`id`,`course_id`,`teacher_id`,`member_id`,`nickname`,`avatar`,`content`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1194499162790211585','1370567317658861570','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','课程很好',0,'2019-11-13 14:16:08','2019-11-13 14:16:08'),('1194898406466420738','1370567317658861570','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','11',0,'2019-11-14 16:42:35','2019-11-14 16:42:35'),('1194898484388200450','1370567317658861570','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','111',0,'2019-11-14 16:42:53','2019-11-14 16:42:53'),('1195251020861317122','1370567317658861570','1189389726308478977','1',NULL,NULL,'2233',0,'2019-11-15 16:03:45','2019-11-15 16:03:45'),('1195251382720700418','1370567317658861570','1189389726308478977','1',NULL,NULL,'4455',0,'2019-11-15 16:05:11','2019-11-15 16:05:11'),('1195252819177570306','1370567317658861570','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','55',0,'2019-11-15 16:10:53','2019-11-15 16:10:53'),('1195252899448160258','1370567317658861570','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','55',0,'2019-11-15 16:11:13','2019-11-15 16:11:13'),('1195252920587452417','1370567317658861570','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','223344',0,'2019-11-15 16:11:18','2019-11-15 16:11:18'),('1195262128095559681','1370567317658861570','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','11',0,'2019-11-15 16:47:53','2019-11-15 16:47:53'),('1196264505170767874','1370567317658861570','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','666666',0,'2019-11-18 11:10:58','2019-11-18 11:10:58'),('1370816540165849090','1370567317658861570','','1369417887341719553','hh','https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png','你好，这里是java高级课程',0,'2021-03-14 03:18:26','2021-03-14 03:18:26');

/*Table structure for table `edu_course` */

DROP TABLE IF EXISTS `edu_course`;

CREATE TABLE `edu_course` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) NOT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) NOT NULL DEFAULT 'null' COMMENT '课程专业父级ID',
  `title` varchar(50) NOT NULL COMMENT '课程标题',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `status` varchar(10) NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `is_deleted` tinyint DEFAULT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_course` */

insert  into `edu_course`(`id`,`teacher_id`,`subject_id`,`subject_parent_id`,`title`,`price`,`lesson_num`,`cover`,`buy_count`,`view_count`,`version`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1370567317658861570','1401934723307061249','1364950721443782658','1364950721427005441','PornHub','0.00',6,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/03/21/70bcb20a33ad4a36bda9c7bf527de02epornhub.jpg',0,0,1,'Normal',NULL,'2021-03-13 10:48:07','2021-06-10 20:28:33'),('1373713971081756673','1359933999229804545','1364950721376673794','1364950721351507969','Harry Potter','0.00',10,'https://specials-images.forbesimg.com/imageserve/5efa33eed4034b0007bf4b5c/960x0.jpg?fit=scale',0,0,1,'Normal',NULL,'2021-03-22 03:11:47','2021-06-07 23:54:56'),('1373755210539843586','1','1364950721376673794','1364950721351507969','Pride and Prejudice','0.00',1,'https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/pap.jpg',0,0,1,'Normal',NULL,'2021-03-22 05:55:39','2021-06-07 23:54:40'),('1373779732416544769','1364394545945575426','1364950721376673794','1364950721351507969','The Hudsucker Proxy','0.00',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/08/2388ff3b8c4b46088b3d2ee82f74fe07The Shawshank Redemption.jpg',0,0,1,'Normal',NULL,'2021-03-22 07:33:06','2021-06-09 01:27:32'),('1403165518621315073','1403164918881980417','1364950721376673794','1364950721351507969','你好，李焕英','0.00',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/11/d993fc37eb28486f9d24c3fbf899762d1.jpg',0,0,1,'Normal',NULL,'2021-06-11 09:41:44','2021-06-11 09:42:06'),('1403167818777628674','1403167433522417665','1364950721443782658','1364950721427005441','天気の子','0.00',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/11/7f651cc15c1a410fa2d1dbc654b45f551.jpeg',0,0,1,'Normal',NULL,'2021-06-11 09:50:52','2021-06-11 09:51:23'),('1403169079828680706','1403168752660385793','1364950721531863041','1364950721515085825','Wrath of Man','0.00',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/11/d02b8f3638234d9fa53516406070d0b11.jpeg',0,0,1,'Normal',NULL,'2021-06-11 09:55:53','2021-06-11 09:57:45');

/*Table structure for table `edu_course_collect` */

DROP TABLE IF EXISTS `edu_course_collect`;

CREATE TABLE `edu_course_collect` (
  `id` char(19) NOT NULL COMMENT '收藏ID',
  `course_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `member_id` char(19) NOT NULL DEFAULT '' COMMENT '课程专业ID',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程收藏';

/*Data for the table `edu_course_collect` */

insert  into `edu_course_collect`(`id`,`course_id`,`member_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1196269345666019330','1192252213659774977','1',1,'2019-11-18 11:30:12','2019-11-18 11:30:12');

/*Table structure for table `edu_course_description` */

DROP TABLE IF EXISTS `edu_course_description`;

CREATE TABLE `edu_course_description` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `description` text COMMENT '课程简介',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程简介';

/*Data for the table `edu_course_description` */

insert  into `edu_course_description`(`id`,`description`,`gmt_create`,`gmt_modified`) values ('1365032985561149442','This is ITI1121','2021-02-26 04:16:39','2021-02-26 04:16:39'),('1370567317658861570','<p>Porn Movies</p>','2021-03-13 10:48:07','2021-06-08 00:18:12'),('1373713971081756673','<p>Harry Potter1~7</p>','2021-03-22 03:11:47','2021-06-07 23:54:52'),('1373755210539843586','<p>Pride and Prejudice</p>','2021-03-22 05:55:39','2021-06-07 23:54:37'),('1373779732416544769','<p><em><strong>The Shawshank Redemption</strong></em>&nbsp;is a 1994 American&nbsp;<a title=\"Drama (film and television)\" href=\"https://en.wikipedia.org/wiki/Drama_(film_and_television)\">drama film</a>&nbsp;written and directed by&nbsp;<a title=\"Frank Darabont\" href=\"https://en.wikipedia.org/wiki/Frank_Darabont\">Frank Darabont</a>, based on the 1982&nbsp;<a title=\"Stephen King\" href=\"https://en.wikipedia.org/wiki/Stephen_King\">Stephen King</a>&nbsp;novella&nbsp;<em><a title=\"Rita Hayworth and Shawshank Redemption\" href=\"https://en.wikipedia.org/wiki/Rita_Hayworth_and_Shawshank_Redemption\">Rita Hayworth and Shawshank Redemption</a></em>. It tells the story of banker Andy Dufresne (<a title=\"Tim Robbins\" href=\"https://en.wikipedia.org/wiki/Tim_Robbins\">Tim Robbins</a>), who is sentenced to life in Shawshank State Penitentiary for the murders of his wife and her lover, despite his claims of innocence. Over the following two decades, he befriends a fellow prisoner, contraband smuggler Ellis \"Red\" Redding (<a title=\"Morgan Freeman\" href=\"https://en.wikipedia.org/wiki/Morgan_Freeman\">Morgan Freeman</a>), and becomes instrumental in a money-laundering operation led by the&nbsp;<a title=\"\" href=\"https://en.wikipedia.org/wiki/Prison_warden\">prison warden</a>&nbsp;Samuel Norton (<a title=\"Bob Gunton\" href=\"https://en.wikipedia.org/wiki/Bob_Gunton\">Bob Gunton</a>).&nbsp;<a title=\"William Sadler (actor)\" href=\"https://en.wikipedia.org/wiki/William_Sadler_(actor)\">William Sadler</a>,&nbsp;<a title=\"Clancy Brown\" href=\"https://en.wikipedia.org/wiki/Clancy_Brown\">Clancy Brown</a>,&nbsp;<a title=\"Gil Bellows\" href=\"https://en.wikipedia.org/wiki/Gil_Bellows\">Gil Bellows</a>, and&nbsp;<a title=\"James Whitmore\" href=\"https://en.wikipedia.org/wiki/James_Whitmore\">James Whitmore</a>&nbsp;appear in supporting roles.</p>','2021-03-22 07:33:06','2021-06-09 01:27:29'),('1403165518621315073','<p>影片根据2016年的同名小品及<a href=\"https://baike.baidu.com/item/%E8%B4%BE%E7%8E%B2/55854\" target=\"_blank\" rel=\"noopener\" data-lemmaid=\"55854\">贾玲</a>亲身经历改编，讲述了刚考上大学的女孩贾晓玲在一天之内经历了人生的大起大落，在情绪失控中，意外穿越回到了八十年代，与20年前与正值青春的母亲李焕英相遇的故事</p>','2021-06-11 09:41:44','2021-06-11 09:41:44'),('1403167818777628674','<p>《天气之子》是由<a href=\"https://baike.baidu.com/item/%E6%96%B0%E6%B5%B7%E8%AF%9A/2722278\" target=\"_blank\" rel=\"noopener\" data-lemmaid=\"2722278\">新海诚</a>执导、Comix Wave Film负责制作的原创动画电影，该片讲述了少年帆高与拥有操控天气超能力的少女阳菜之间的奇幻爱情故事</p>','2021-06-11 09:50:52','2021-06-11 09:50:52'),('1403169079828680706','<p>Mysterious and wild-eyed, a new security guard for a cash truck surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman\'s ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.</p>','2021-06-11 09:55:53','2021-06-11 09:57:41');

/*Table structure for table `edu_subject` */

DROP TABLE IF EXISTS `edu_subject`;

CREATE TABLE `edu_subject` (
  `id` char(19) NOT NULL COMMENT '课程类别ID',
  `title` varchar(10) NOT NULL COMMENT '类别名称',
  `parent_id` char(19) NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程科目';

/*Data for the table `edu_subject` */

insert  into `edu_subject`(`id`,`title`,`parent_id`,`sort`,`gmt_create`,`gmt_modified`) values ('1364950721351507969','Love','0',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721376673794','Love01','1364950721351507969',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721393451010','Love02','1364950721351507969',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721414422530','Love03','1364950721351507969',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721427005441','Magic','0',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721443782658','Magic01','1364950721427005441',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721460559873','Magic02','1364950721427005441',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721481531394','Magic03','1364950721427005441',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721506697217','Magic04','1364950721427005441',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721515085825','Action','0',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721531863041','Action01','1364950721515085825',0,'2021-02-25 22:49:46','2021-02-25 22:49:46'),('1364950721548640257','Action02','1364950721515085825',0,'2021-02-25 22:49:46','2021-02-25 22:49:46');

/*Table structure for table `edu_teacher` */

DROP TABLE IF EXISTS `edu_teacher`;

CREATE TABLE `edu_teacher` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲师';

/*Data for the table `edu_teacher` */

insert  into `edu_teacher`(`id`,`name`,`intro`,`career`,`level`,`avatar`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','张三','近年主持国家自然科学基金（6项）、江苏省重大科技成果转化项目（5项）、江苏省产学研前瞻性联合研究项目（3项）、省工业科技支撑、省高技术、省自然科学基金等省部级及其企业的主要科研项目40多个，多个项目在企业成功转化，产生了较好的经济、社会和环境效益。积极开展产学研科技合作，并与省内16家企业建立了江苏省研究生工作站，其中6家为江苏省优秀研究生工作站','高级',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',0,1,'2019-10-30 14:18:46','2019-11-12 13:36:36'),('1189389726308478977','晴天','高级讲师简介','高级讲师资历',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',1,1,'2019-10-30 11:53:03','2019-10-30 11:53:03'),('1189390295668469762','李刚','高级讲师简介','高级讲师',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',2,1,'2019-10-30 11:55:19','2019-11-12 13:37:52'),('1189426437876985857','kento kaku','He has appeared in a number of feature films, television series and stage productions. He is represented with Amuse, Inc. His wife is actress Nana Eikura','Japanese actor born in Tokyo',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/37f5fd9131894c7497744de81f968575file.png',0,0,'2019-10-30 14:18:56','2021-06-07 23:52:41'),('1189426464967995393','Keira Knightley','She has starred in both independent films and big-budget blockbusters, and is particularly noted for her roles in period dramas.','British actress',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/761330322ace4ca09aba2a58cdd71032file.png',1,0,'2019-10-30 14:19:02','2021-06-07 23:40:49'),('1192249914833055746','Matthew Macfadyen','He is known for his performance as Mr. Darcy in Joe Wright\'s Pride & Prejudice (2005), and Daniel in the Frank Oz comedy Death at a Funeral.','English actor who has appeared in film, television, and theatre',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/19f43b212c3048f4afb5c79dd4ffef21file.png',1,0,'2019-11-07 09:18:25','2021-06-07 23:37:38'),('1192327476087115778','1222-12-12','1111','11',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',0,1,'2019-11-07 14:26:37','2019-11-11 16:26:26'),('1195337453429129218','test','sdfsdf','sdfdf',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',0,1,'2019-11-15 21:47:12','2019-11-15 21:47:27'),('1358530509534228482','Emma Watson','She has gained recognition for her roles in both blockbusters and independent films, as well as her women\'s rights work. ','English actress and activist',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/f1380b595e284a3f8a1eb6effc7fb3fbfile.png',0,0,'2021-02-08 05:38:08','2021-06-07 23:34:02'),('1359726119042002945','Rupert Grint','He rose to fame for his role as Ron Weasley, one of the three main characters in the Harry Potter film series. ','English actor',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/9a92d968f8a9458891940b97e75c307afile.png',1,0,'2021-02-11 12:49:03','2021-06-07 23:24:52'),('1359933999229804545','Daniel Radcliffe','Born and raised in London, Radcliffe made his acting debut at age ten in the BBC One television film David Copperfield (1999), followed by his feature film debut in The Tailor of Panama (2001). ','English actor, best known for playing Harry Potter',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/3e179ac423584ad9af72a3ae958dad15file.png',2,0,'2021-02-12 02:35:06','2021-06-07 23:34:33'),('1364390594588270594','Morgan Freeman','He has appeared in a range of film genres portraying character roles and is particularly known for his distinctive deep voice. Freeman is the recipient of various accolades','American actor, director, and narrator',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/de363ef7c8a242c8ac5cf9d00897b654file.png',3,0,'2021-02-24 09:44:01','2021-06-07 23:19:14'),('1364394545945575426','Tim Robbins','He is known for portraying Andy Dufresne in the film The Shawshank Redemption, and has won an Academy Award and three Golden Globe Awards for his roles in the films Mystic River (2003), Short Cuts (1993) and The Player (1992).','American actor',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/d74eb27acd114223984cc8296e9959d1file.png',1,0,'2021-02-24 09:59:43','2021-06-07 23:44:41'),('1401934723307061249','橋本有菜','2015年12月にイメージビデオでデビュー。2016年3月にエスワン専属でAVデビュー。','日本のAV女優',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/07/5e4b03fc839c46c7a645b71e5b7a0e4dfile.png',5,0,'2021-06-08 00:10:59','2021-06-08 00:11:14'),('1403164918881980417','贾玲','2003年，获得“首届北京相声小品邀请赛”专业组一等奖 [97]  。2006年，获得“第三届中央电视台相声大赛”专业组二等奖。2009年，创办的新笑声客栈开张 [2]  。2010年，登上中央电视台春节联欢晚会表演相声《大话捧逗》，获得“我最喜爱的春晚节目评选”曲艺组三等奖 [3]  。2011年，登上中央电视台春节联欢晚会表演相声《芝麻开门》 [4]  。2012年，参加江苏卫视春节晚会，表演小品《万能表演》','中国内地喜剧女演员、导演、编剧',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/11/d223e8795d264ee2ab29cc830410e627file.png',1,0,'2021-06-11 09:39:21','2021-06-11 09:39:37'),('1403167433522417665','森七菜','Mori was born in Osaka on August 31, 2001[2] and brought up in Ōita. In 2016, she was scouted by the talent agency ARBRE.\n\nIn 2021, she signed a contract with SMA (Sony Music Artists).[3]',' Japanese actress from Oita Prefecture, Japan',2,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/11/f64c7f2b5a364f17ba57dd2610b96d14file.png',1,0,'2021-06-11 09:49:20','2021-06-11 09:49:20'),('1403168752660385793','Jason Statham','Born in Shirebrook, Derbyshire, he began practising kung fu, kickboxing and karate recreationally in his youth while working at local market stalls.','English actor',1,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/06/11/f2f46166917c4de0911436ab23f51f64file.png',1,0,'2021-06-11 09:54:35','2021-06-11 09:54:35');

/*Table structure for table `edu_video` */

DROP TABLE IF EXISTS `edu_video`;

CREATE TABLE `edu_video` (
  `id` char(19) NOT NULL COMMENT '视频ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) NOT NULL COMMENT '章节ID',
  `title` varchar(50) NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) DEFAULT NULL COMMENT '原始文件名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `status` varchar(20) NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
  `size` bigint unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程视频';

/*Data for the table `edu_video` */

insert  into `edu_video`(`id`,`course_id`,`chapter_id`,`title`,`video_source_id`,`video_original_name`,`sort`,`play_count`,`is_free`,`duration`,`status`,`size`,`version`,`gmt_create`,`gmt_modified`) values ('1370567415260315649','1370567317658861570','1370567374835613698','1.MSFH-054','4a066dbb4ece456892566b5641a662be','msfh-054',1,0,1,0,'Empty',0,1,'2021-03-13 10:48:30','2021-06-08 00:20:14'),('1373706835631128577','1370567317658861570','1370567374835613698','2.S-CUTE 771','\r\na86962a521054c7d97a5c18c27c021b8','1.S-CUTE 771_ICHIKA_03 制服美少女の黒タイツ破いて中出しSEX／ICHIKA.mp4',2,0,1,0,'Empty',0,1,'2021-03-22 02:43:26','2021-06-08 00:20:21'),('1373710617630851073','1370567317658861570','1370567374835613698','3.S-CUTE 540','c437cce4768f41c794e50339b2738750','3.S-CUTE 540_MIKAKO_05 笑顔でＨするパイパン美少女に中出し／MIKAKO.mp4',1,0,1,0,'Empty',0,1,'2021-03-22 02:58:28','2021-06-08 00:20:28'),('1373714123125276673','1373713971081756673','1373714027063132161','Harry Potter and the Sorcerer\'s Stone','cbb0bbded42d4112b57006eab3d80774','哈利波特之魔法石',1,0,1,0,'Empty',0,1,'2021-03-22 03:12:23','2021-03-22 03:12:23'),('1373755304282537986','1373755210539843586','1373755260154265601','Pride and Prejudice','3383f6ea99e6483c88f8a6010e2ae9cc','傲慢与偏见',1,0,1,0,'Empty',0,1,'2021-03-22 05:56:02','2021-03-22 05:56:02'),('1373779775513018370','1373779732416544769','1373779757213270018','The Shawshank Redemption','\r\n603d201ef62c4a31b058dd3ae92f0c64',NULL,1,0,1,0,'Empty',0,1,'2021-03-22 07:33:16','2021-03-22 07:33:16'),('1402965882749063169','1370567317658861570','1370567374835613698','白城_SDAB-125','175fe7eed106409eaa15f2559308829a',NULL,1,0,1,0,'Empty',0,1,'2021-06-10 20:28:27','2021-06-10 20:28:27'),('1403165594282364929','1403165518621315073','1403165569728909314','你好李焕英','da94b25ba2eb409f9b65aaa211876f0a',NULL,1,0,1,0,'Empty',0,1,'2021-06-11 09:42:02','2021-06-11 09:42:02'),('1403166831815954434','1373713971081756673','1373714027063132161','Harry Potter and the Chamber of Secrets','78a2cda76db845b1b77d78ce0677f06a',NULL,1,0,1,0,'Empty',0,1,'2021-06-11 09:46:57','2021-06-11 09:46:57'),('1403167932757839874','1403167818777628674','1403167908003057665','天気の子','d5eb6f541be641b8ba843fbbd1e44239',NULL,1,0,1,0,'Empty',0,1,'2021-06-11 09:51:19','2021-06-11 09:51:19'),('1403169172568936450','1403169079828680706','1403169147415695361','Wrath of Man','49bc80f0cbfe4300a0f022fc4b9c287f',NULL,1,0,1,0,'Empty',0,1,'2021-06-11 09:56:15','2021-06-11 09:56:15');

/*Table structure for table `statistics_daily` */

DROP TABLE IF EXISTS `statistics_daily`;

CREATE TABLE `statistics_daily` (
  `id` char(19) NOT NULL COMMENT '主键',
  `date_calculated` varchar(20) NOT NULL COMMENT '统计日期',
  `register_num` int NOT NULL DEFAULT '0' COMMENT '注册人数',
  `login_num` int NOT NULL DEFAULT '0' COMMENT '登录人数',
  `video_view_num` int NOT NULL DEFAULT '0' COMMENT '每日播放视频数',
  `course_num` int NOT NULL DEFAULT '0' COMMENT '每日新增课程数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `statistics_day` (`date_calculated`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='网站统计日数据';

/*Data for the table `statistics_daily` */

insert  into `statistics_daily`(`id`,`date_calculated`,`register_num`,`login_num`,`video_view_num`,`course_num`,`gmt_create`,`gmt_modified`) values ('1372305711288135682','2021-03-09',0,169,194,138,'2021-03-18 05:55:52','2021-03-18 05:55:52'),('1372329011426996226','2021-03-10',2,138,127,135,'2021-03-18 07:28:27','2021-03-18 07:28:27'),('1372541120693776385','2021-03-11',1,119,172,135,'2021-03-18 21:31:18','2021-03-18 21:31:18'),('1372914328684998658','2021-03-18',0,134,151,164,'2021-03-19 22:14:18','2021-03-19 22:14:18'),('1373282050220503042','2021-03-19',0,105,131,122,'2021-03-20 22:35:29','2021-03-20 22:35:29'),('1401041089938395138','2021-06-04',1,175,190,178,'2021-06-05 13:00:00','2021-06-05 13:00:00'),('1401544396058464257','2021-06-05',0,121,179,146,'2021-06-06 22:19:58','2021-06-06 22:19:58'),('1401903909156491265','2021-06-06',0,106,160,180,'2021-06-07 22:08:32','2021-06-07 22:08:32'),('1402277707961667585','2021-06-07',0,141,108,166,'2021-06-08 22:53:53','2021-06-08 22:53:53'),('1403155020525867009','2021-06-10',0,181,144,176,'2021-06-11 09:00:01','2021-06-11 09:00:01');

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` char(19) NOT NULL DEFAULT '',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `course_title` varchar(100) DEFAULT NULL COMMENT '课程名称',
  `course_cover` varchar(255) DEFAULT NULL COMMENT '课程封面',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '讲师名称',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '会员手机',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '订单金额（分）',
  `pay_type` tinyint DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
  `status` tinyint DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_order_no` (`order_no`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单';

/*Data for the table `t_order` */

insert  into `t_order`(`id`,`order_no`,`course_id`,`course_title`,`course_cover`,`teacher_name`,`member_id`,`nickname`,`mobile`,`total_fee`,`pay_type`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1371098773388787714','20210314095955153','1370567317658861570','java高级课程','https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/java.jpg','半佛仙人','1369417887341719553','hh','18600146610','0.01',1,1,0,'2021-03-14 21:59:56','2021-03-14 22:00:20');

/*Table structure for table `t_pay_log` */

DROP TABLE IF EXISTS `t_pay_log`;

CREATE TABLE `t_pay_log` (
  `id` char(19) NOT NULL DEFAULT '',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '支付金额（分）',
  `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易流水号',
  `trade_state` char(20) DEFAULT NULL COMMENT '交易状态',
  `pay_type` tinyint NOT NULL DEFAULT '0' COMMENT '支付类型（1：微信 2：支付宝）',
  `attr` text COMMENT '其他属性',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付日志表';

/*Data for the table `t_pay_log` */

/*Table structure for table `ucenter_member` */

DROP TABLE IF EXISTS `ucenter_member`;

CREATE TABLE `ucenter_member` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint unsigned DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员表';

/*Data for the table `ucenter_member` */

insert  into `ucenter_member`(`id`,`openid`,`mobile`,`password`,`nickname`,`sex`,`age`,`avatar`,`sign`,`is_disabled`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1',NULL,'13700000001','96e79218965eb72c92a549dd5a330112','小三123',1,5,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-01 12:11:33','2019-11-08 11:56:01'),('1080736474267144193',NULL,'13700000011','96e79218965eb72c92a549dd5a330112','用户XJtDfaYeKk',1,19,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-02 12:12:45','2019-01-02 12:12:56'),('1080736474355224577',NULL,'13700000002','96e79218965eb72c92a549dd5a330112','用户wUrNkzAPrc',1,27,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-02 12:13:56','2019-01-02 12:14:07'),('1086387099449442306',NULL,'13520191388','96e79218965eb72c92a549dd5a330112','用户XTMUeHDAoj',2,20,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099520745473',NULL,'13520191389','96e79218965eb72c92a549dd5a330112','用户vSdKeDlimn',1,21,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099608825858',NULL,'13520191381','96e79218965eb72c92a549dd5a330112','用户EoyWUVXQoP',1,18,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099701100545',NULL,'13520191382','96e79218965eb72c92a549dd5a330112','用户LcAYbxLNdN',2,24,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099776598018',NULL,'13520191383','96e79218965eb72c92a549dd5a330112','用户dZdjcgltnk',2,25,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099852095490',NULL,'13520191384','96e79218965eb72c92a549dd5a330112','用户wNHGHlxUwX',2,23,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1106746895272849410','o1R-t5u2TfEVeVjO9CPGdHPNw-to',NULL,NULL,'檀梵\'',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/zZfLXcetf2Rpsibq6HbPUWKgWSJHtha9y1XBeaqluPUs6BYicW1FJaVqj7U3ozHd3iaodGKJOvY2PvqYTuCKwpyfQ/132',NULL,0,0,'2019-03-16 10:39:57','2019-03-16 10:39:57'),('1106822699956654081',NULL,NULL,NULL,NULL,NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-03-16 15:41:10','2019-03-16 15:41:10'),('1106823035660357634','o1R-t5i4gENwHYRb5lVFy98Z0bdk',NULL,NULL,'GaoSir',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJI53RcCuc1no02os6ZrattWGiazlPnicoZQ59zkS7phNdLEWUPDk8fzoxibAnXV1Sbx0trqXEsGhXPw/132',NULL,0,0,'2019-03-16 15:42:30','2019-03-16 15:42:30'),('1106823041599492098',NULL,NULL,NULL,NULL,NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-03-16 15:42:32','2019-03-16 15:42:32'),('1106823115788341250','o1R-t5l_3rnbZbn4jWwFdy6Gk6cg',NULL,NULL,'换个网名哇、',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/jJHyeM0EN2jhB70LntI3k8fEKe7W6CwykrKMgDJM4VZqCpcxibVibX397p0vmbKURGkLS4jxjGB0GpZfxCicgt07w/132',NULL,0,0,'2019-03-16 15:42:49','2019-03-16 15:42:49'),('1106826046730227714','o1R-t5gyxumyBqt0CWcnh0S6Ya1g',NULL,NULL,'我是Helen',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDRfib8wy7A2ltERKh4VygxdjVC1x5OaOb1t9hot4JNt5agwaVLdJLcD9vJCNcxkvQnlvLYIPfrZw/132',NULL,0,0,'2019-03-16 15:54:28','2019-03-16 15:54:28'),('1106828185829490690','o1R-t5nNlou5lRwBVgGNJFm4rbc4',NULL,NULL,' 虎头',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKxCqRzuYWQmpwiaqQEjNxbC7WicebicXQusU306jgmfoOzUcFg1qaDq5BStiblwBjw5dUOblQ2gUicQOQ/132',NULL,0,0,'2019-03-16 16:02:58','2019-03-16 16:02:58'),('1106830599651442689','o1R-t5hZHQB1cbX7HZJsiM727_SA',NULL,NULL,'是吴啊',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ9CsqApybcs7f3Dyib9IxIh0sBqJb7LicbjU4WticJFF0PVwFvHgtbFdBwfmk3H2t3NyqmEmVx17tRA/132',NULL,0,0,'2019-03-16 16:12:34','2019-03-16 16:12:34'),('1106830976199278593','o1R-t5meKOoyEJ3-IhWRCBKFcvzU',NULL,NULL,'我才是Helen',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epMicP9UT6mVjYWdno0OJZkOXiajG0sllJTbGJ9DYiceej2XvbDSGCK8LCF7jv1PuG2uoYlePWic9XO8A/132',NULL,0,0,'2019-03-16 16:14:03','2019-03-16 16:14:03'),('1106831936900415490','o1R-t5jXYSWakGtnUBnKbfVT5Iok',NULL,NULL,'文若姬',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/3HEmJwpSzguqqAyzmBwqT6aicIanswZibEOicQInQJI3ZY1qmu59icJC6N7SahKqWYv24GvX5KH2fibwt0mPWcTJ3fg/132',NULL,0,0,'2019-03-16 16:17:52','2019-03-16 16:17:52'),('1106832491064442882','o1R-t5sud081Qsa2Vb2xSKgGnf_g',NULL,NULL,'Peanut',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-03-16 16:20:04','2019-03-16 16:20:04'),('1106833021442510849','o1R-t5lsGc3I8P5bDpHj7m_AIRvQ',NULL,NULL,'食物链终结者',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/MQ7qUmCprK9am16M1Ia1Cs3RK0qiarRrl9y8gsssBjIZeS2GwKSrnq7ZYhmrzuzDwBxSMMAofrXeLic9IBlW4M3Q/132',NULL,0,0,'2019-03-16 16:22:11','2019-03-16 16:22:11'),('1191600824445046786',NULL,'15210078344','96e79218965eb72c92a549dd5a330112','IT妖姬',1,5,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-11-05 14:19:10','2019-11-08 18:04:43'),('1191616288114163713',NULL,'17866603606','96e79218965eb72c92a549dd5a330112','xiaowu',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-11-05 15:20:37','2019-11-05 15:20:37'),('1195187659054329857',NULL,'15010546384','96e79218965eb72c92a549dd5a330112','qy',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-11-15 11:51:58','2019-11-15 11:51:58'),('1369416146537787393',NULL,'13801214923','96e79218965eb72c92a549dd5a330112','weisong',NULL,NULL,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',NULL,0,0,'2021-03-10 06:33:46','2021-03-10 06:33:46'),('1369417887341719553',NULL,'18600146610','96e79218965eb72c92a549dd5a330112','hh',NULL,NULL,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',NULL,0,0,'2021-03-10 06:40:41','2021-03-10 06:40:41'),('1370025910313476097','o3_SC594Us8hw290TLumN0DCkfQ4','',NULL,'天王大赫',NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoERibIfR5Kib3wHJmWB0NEdgIiaaWCHDzlXpcCfcbqAJ2C4829N9lv7cYrOpLhGMsIicsoCtRIMX3rzQ/132',NULL,0,0,'2021-03-11 22:56:45','2021-03-11 22:56:45'),('1400836406317768706',NULL,'18600146666','a643ce3d500d26e176e9d2a64a6d21ca','admin',NULL,NULL,'https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png',NULL,0,0,'2021-06-04 23:26:40','2021-06-04 23:26:40');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
