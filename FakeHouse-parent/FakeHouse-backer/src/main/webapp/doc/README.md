# OauthConsole

## 项目说明

​		本项目意于简化权限管理系统的编写, 因为每次都要进行权限管理系统的编写,而且编写的逻辑基本上相差不会太多, 所以可以直接使用同一个即可.为了做到这样, 项目的限制还是很大的. 基本上与嵌入项目使用的技术要基本一致.

​		简言之, 本项目可以简化权限管理系统模块代码的编写, 但是使用的限制也比较大.

## 功能

### 功能1

​		可以作为其它系统的权限管理模块进行使用, 可以简便地管理权限系统.

### 功能2

​		代码移植.

​		可以将代码复制到其他项目中, 作为其他项目的一部分.但是项目框架需要使用我们这一套. 

## 使用限制

### 数据库

使用的数据库结构必须一致.

如下:

```mysql
-- DROP DATABASE IF EXISTS oauth_console;

-- 创建数据库
-- CREATE DATABASE IF NOT EXISTS oauth_console;
-- SHOW CREATE DATABASE yiran_games;

USE dattabase_name;

-- 修改字符集为utf-8
-- 默认就是utf-8,不需要进行修改
ALTER DATABASE dattabase_name CHARACTER SET utf8mb4  COLLATE utf8mb4_0900_ai_ci;

-- 修改字符集为utf-8
-- 默认就是utf-8,不需要进行修改
-- ALTER DATABASE yiran_games CHARACTER SET utf8mb4  COLLATE utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT "用户表主键自增",
  `username` VARCHAR(32) NOT NULL UNIQUE COMMENT "用户名",
  `password` VARCHAR(256) DEFAULT "$2a$10$MKkCuwy/GM2QF4H4aP9H6.giV435Bbo6FaaJ4rC.WcnLcYUnXYWlK" 
			  COMMENT "密码采用bcrypt方式进行加密,默认为user-123456",
  `remark` VARCHAR(128) DEFAULT NULL COMMENT "备注信息",
  `station` BOOL DEFAULT FALSE COMMENT "用户的状态, 是否已经激活, 需要管理员进行验证",
  `email` VARCHAR(128) DEFAULT NULL COMMENT "邮件, 用于验证",
  `telephone` VARCHAR(11) DEFAULT NULL COMMENT "手机号码",
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- SHOW CREATE TABLE t_user;



-- 默认管理员用户  用户名: admin, 密码: admin
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$6x7HtYgjJEDlPq.WSIZsdufLAx4R8QIiCXU0/JiDtLcm18x4iEJYG', NULL, NULL, NULL, NULL);

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT "角色表主键",
  `name` VARCHAR(32) DEFAULT NULL COMMENT "角色名称",
  `keyword` VARCHAR(64) DEFAULT NULL COMMENT "角色关键字, 用于权限校验",
  `description` VARCHAR(128) DEFAULT NULL COMMENT "角色描述",
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 插入角色: 系统管理员
-- 要求角色关键字符合格式  ROLE_XXX
-- 虽然系统管理员应该有全部权限, 但是设置新权限时需要进行添加
INSERT INTO `t_role` VALUES (1, '系统管理员', 'ROLE_ADMIN', "拥有所有权限, 可以处理管理功能");


-- user与role的中间表
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 给admin用户添加系统管理员角色
INSERT INTO `t_user_role` VALUES (1, 1);

-- 菜单表
-- 设计成可以进行无限嵌套的菜单, 但是现在应该只能使用二级的菜单
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT "菜单表的主键",
  `name` VARCHAR(128) DEFAULT NULL COMMENT "菜单名称",
  `linkUrl` VARCHAR(128) DEFAULT NULL COMMENT "关联的页面的url",
  `path` VARCHAR(128) DEFAULT NULL COMMENT "菜单级别对应的目录等级",
  `priority` INT(11) DEFAULT NULL COMMENT "菜单顺序, 用于查询时对菜单进行排序",
  `icon` VARCHAR(64) DEFAULT NULL COMMENT "图标的名称",
  `description` VARCHAR(128) DEFAULT NULL COMMENT "描述",
  `parentMenuId` INT(11) DEFAULT NULL COMMENT "父菜单id",
  `level` INT(11) DEFAULT NULL COMMENT "菜单等级",
  PRIMARY KEY (`id`),
  -- 自己关联自己字段的外键
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`parentMenuId`) REFERENCES `t_menu` (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8;

-- 说明: 父菜单不需要添加LinkUrl


-- 测试菜单
INSERT INTO `t_menu` VALUES (1, '会员管理', NULL, '2', 1, 'fa-user-md', NULL, NULL, 1);
INSERT INTO `t_menu` VALUES (2, '会员列表', 'member.html', '/2-1', 1, NULL, NULL, 1, 2);
INSERT INTO `t_menu` VALUES (3, '会员操作记录', NULL, '/2-2', 2, NULL, NULL, 1, 2);
INSERT INTO `t_menu` VALUES (4, '会员统计', NULL, '/2-3', 3, NULL, NULL, 1, 2);--

-- 权限管理放在第6级, 方便前面放置其他的菜单
INSERT INTO `t_menu` VALUES (5, '用户权限管理', NULL, '6', 5, 'fa-users', NULL, NULL, 1);
INSERT INTO `t_menu` VALUES (6, '菜单管理', 'menu.html', '/6-1', 1 NULL, NULL, 5, 2);
INSERT INTO `t_menu` VALUES (7, '权限管理', 'permission.html', '/6-2', 2, NULL, NULL, 5, 2);
INSERT INTO `t_menu` VALUES (8, '角色管理', 'role.html', '/6-3', 3, NULL, NULL, 5, 2);
INSERT INTO `t_menu` VALUES (9, '用户管理', 'user.html', '/6-4', 4, NULL, NULL, 5, 2);


-- role和menu的中间表
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `role_id` INT(11) NOT NULL,
  `menu_id` INT(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FK_Reference_10` (`menu_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 给系统管理员角色添加所有角色
INSERT INTO `t_role_menu` VALUES (1, 1);
INSERT INTO `t_role_menu` VALUES (1, 2);
INSERT INTO `t_role_menu` VALUES (1, 3);
INSERT INTO `t_role_menu` VALUES (1, 4);
INSERT INTO `t_role_menu` VALUES (1, 5);
INSERT INTO `t_role_menu` VALUES (1, 6);
INSERT INTO `t_role_menu` VALUES (1, 7);
INSERT INTO `t_role_menu` VALUES (1, 8);
INSERT INTO `t_role_menu` VALUES (1, 9);


-- 权限表
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT "权限表主键",
  `name` VARCHAR(32) DEFAULT NULL COMMENT "权限名称",
  `keyword` VARCHAR(64) DEFAULT NULL COMMENT "权限关键字, 用于权限认证",
  `description` VARCHAR(128) DEFAULT NULL COMMENT "权限描述",
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 添加权限permisssion数据
INSERT INTO `t_permission` VALUES (1, '添加权限', 'PERMISSION_ADD', NULL);
INSERT INTO `t_permission` VALUES (2, '删除权限', 'PERMISSION_DELETE', NULL);
INSERT INTO `t_permission` VALUES (3, '修改权限', 'PERMISSION_UPDATE', NULL);
INSERT INTO `t_permission` VALUES (4, '查询权限', 'PERMISSION_SELECT', NULL);

INSERT INTO `t_permission` VALUES (5, '添加角色', 'ROLE_ADD', NULL);
INSERT INTO `t_permission` VALUES (6, '删除角色', 'ROLE_DELETE', NULL);
INSERT INTO `t_permission` VALUES (7, '修改角色', 'ROLE_UPDATE', NULL);
INSERT INTO `t_permission` VALUES (8, '查询角色', 'ROLE_SELECT', NULL);

INSERT INTO `t_permission` VALUES (9, '添加用户', 'USER_ADD', NULL);
INSERT INTO `t_permission` VALUES (10, '删除用户', 'USER_DELETE', NULL);
INSERT INTO `t_permission` VALUES (11, '修改用户', 'USER_UPDATE', NULL);
INSERT INTO `t_permission` VALUES (12, '查询用户', 'USER_SELECT', NULL);

INSERT INTO `t_permission` VALUES (13, '添加菜单', 'MENU_ADD', NULL);
INSERT INTO `t_permission` VALUES (14, '删除菜单', 'MENU_DELETE', NULL);
INSERT INTO `t_permission` VALUES (15, '修改菜单', 'MENU_UPDATE', NULL);
INSERT INTO `t_permission` VALUES (16, '查询菜单', 'MENU_SELECT', NULL);


-- role和permission的中间表
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_id` INT(11) NOT NULL,
  `permission_id` INT(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FK_Reference_12` (`permission_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 给系统管理员角色添加所有权限
INSERT INTO `t_role_permission` VALUES (1, 1);
INSERT INTO `t_role_permission` VALUES (1, 2);
INSERT INTO `t_role_permission` VALUES (1, 3);
INSERT INTO `t_role_permission` VALUES (1, 4);
INSERT INTO `t_role_permission` VALUES (1, 5);
INSERT INTO `t_role_permission` VALUES (1, 6);
INSERT INTO `t_role_permission` VALUES (1, 7);
INSERT INTO `t_role_permission` VALUES (1, 8);
INSERT INTO `t_role_permission` VALUES (1, 9);
INSERT INTO `t_role_permission` VALUES (1, 10);
INSERT INTO `t_role_permission` VALUES (1, 11);
INSERT INTO `t_role_permission` VALUES (1, 12);
```

在sql目录中也有对应的文件, 注意执行时要求数据库的字符集为UTF8, 否则编码会异常.

复制文件内容到数据库可视化操作软件中, 修改需要使用的数据库.  没有数据库的需要提前进行创建.

### 使用技术

​		 如果是进行代码的移植, 则需要项目使用的技术与本项目基本一致. Spring, SpringMVC, Spring-Security. 前端技术 ElementUI+VUE2.0

## 使用方法

### 功能1

作为管理项目对其他项目的权限系统进行管理.

克隆代码到本地, 使用idea打开, 之后修改 src\main\resources目录中的jdbc.properties文件中的信息为自己数据库的信息, 之后启动即可.

### 功能2

提供代码,让其他系统进行复制, 由于其他系统可能会使用到权限关联模块, 所以可以作为代码移植到其他项目中.

具体的操作方式:

​	克隆项目到本地.

​	初始化自己的项目, 或者在项目进行后端管理构建的时候就进行代码的移植.

​	除了配置文件之外, 将项目的代码复制到自己的项目中;

​	配置好配置文件; 主要包括Spirng配置文件和html的配置以及Spirng-Security的配置.

​	之后就可以进行测试了.

项目扩展阶段:

此时需要将后端的其他部分都配置到后端管理中, 而本项目的允许这样的扩展的, 只需要

1 将相应的权限信息配置好 创建好权限菜单角色以及用户.

2 编写对应的html页面以及controller和service,dao并配置好权限即可.(在controller的方法前加个权限校验的注解).

3 进行测试

## 常见问题

### keyword

添加权限时需要设置 keyword; 

keyword的书写规范:  大写字母, 使用_进行逻辑的连接

例如:

```
USER_SELECT
USER_DELETE
```

同样添加角色时也需要添加keyword

keyword的书写规范:  大写字母, 使用_进行逻辑的连接并以 ROLE 开头

例如:

```
ROLE_ADMIN
ROLE_GAME_MANAGE
```



### 忘记添加密码

在控制台添加用户时, 如果忘记添加密码, 会添加默认密码为 user-123456

### 修改用户权限后无法立即生效

如果授权对象已经登录, 则需要授权对象退出后, 在进行登录, 授权数据才能够生效

### 删除失败

如果 角色, 用户, 菜单, 权限被关联着,则数据无法被删除.

### 图标的更改

使用的图标库是   fontawesome

可以前往   [Font Awesome 图标 | 菜鸟教程 (runoob.com)](https://www.runoob.com/font-awesome/fontawesome-tutorial.html) 

进行图标的查看  由于本来就添加了 class="fa",

值需要添加另一个 class即可  例如      fa-cog

### 虚拟路径的添加

本项目的运行路径要在虚拟路径中, 需要设置web容器的虚拟路径为 /OauthConsole  才能够正常运行


### README.md文档的下载

没有实现该文档的下载, 但是呢? 可以查看, 是因为浏览器默认能够解析该类型文件, 所以要手动进行下载.

### mysql版本问题

如果使用的数据库的版本是8以上的版本, 则导入的jar包也设置成 8以上的版本
并且 设置  jdbc.driver=com.mysql.cj.jdbc.Driver

## 项目发展

​		由于本人的前端技术学得不成熟, 导致做出来的页面不好看, 所以之后的版本要进行优化.

​		项目中应该扔存在着比较多的bug, 之后要进行处理.

