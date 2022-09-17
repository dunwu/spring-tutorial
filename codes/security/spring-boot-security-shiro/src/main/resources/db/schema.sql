SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS t_dept;
CREATE TABLE t_dept (
    dept_id     BIGINT(20)                                              NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    parent_id   BIGINT(20)                                              NOT NULL COMMENT '上级部门ID',
    dept_name   VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
    order_num   BIGINT(20)                                              NULL DEFAULT NULL COMMENT '排序',
    create_time DATETIME(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    modify_time DATETIME(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (dept_id) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 11
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci COMMENT = '部门表'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO t_dept
VALUES (1, 0, '开发部', 1, '2019-06-14 20:56:41', NULL);
INSERT INTO t_dept
VALUES (2, 1, '开发一部', 1, '2019-06-14 20:58:46', NULL);
INSERT INTO t_dept
VALUES (3, 1, '开发二部', 2, '2019-06-14 20:58:56', NULL);
INSERT INTO t_dept
VALUES (4, 0, '采购部', 2, '2019-06-14 20:59:56', NULL);
INSERT INTO t_dept
VALUES (5, 0, '财务部', 3, '2019-06-14 21:00:08', NULL);
INSERT INTO t_dept
VALUES (6, 0, '销售部', 4, '2019-06-14 21:00:15', NULL);
INSERT INTO t_dept
VALUES (7, 0, '工程部', 5, '2019-06-14 21:00:42', NULL);
INSERT INTO t_dept
VALUES (8, 0, '行政部', 6, '2019-06-14 21:00:49', NULL);
INSERT INTO t_dept
VALUES (9, 0, '人力资源部', 8, '2019-06-14 21:01:14', '2019-06-14 21:01:34');
INSERT INTO t_dept
VALUES (10, 0, '系统部', 7, '2019-06-14 21:01:31', NULL);


-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS t_menu;
CREATE TABLE t_menu (
    menu_id     BIGINT(20)                                             NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
    parent_id   BIGINT(20)                                             NOT NULL COMMENT '上级菜单ID',
    menu_name   VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单/按钮名称',
    url         VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
    perms       TEXT CHARACTER SET utf8 COLLATE utf8_general_ci        NULL COMMENT '权限标识',
    icon        VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
    type        CHAR(2) CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL COMMENT '类型 0菜单 1按钮',
    order_num   BIGINT(20)                                             NULL DEFAULT NULL COMMENT '排序',
    create_time DATETIME(0)                                            NOT NULL COMMENT '创建时间',
    modify_time DATETIME(0)                                            NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (menu_id) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 178
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci COMMENT = '菜单表'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO t_menu
VALUES (1, 0, '系统管理', NULL, NULL, 'layui-icon-setting', '0', 1, '2017-12-27 16:39:07', NULL);
INSERT INTO t_menu
VALUES (2, 0, '系统监控', '', '', 'layui-icon-alert', '0', 2, '2017-12-27 16:45:51', '2019-06-13 11:20:40');
INSERT INTO t_menu
VALUES (3, 1, '用户管理', '/system/user', 'user:view', '', '0', 1, '2017-12-27 16:47:13', '2019-12-04 16:46:50');
INSERT INTO t_menu
VALUES (4, 1, '角色管理', '/system/role', 'role:view', '', '0', 2, '2017-12-27 16:48:09', '2019-06-13 08:57:19');
INSERT INTO t_menu
VALUES (5, 1, '菜单管理', '/system/menu', 'menu:view', '', '0', 3, '2017-12-27 16:48:57', '2019-06-13 08:57:34');
INSERT INTO t_menu
VALUES (6, 1, '部门管理', '/system/dept', 'dept:view', '', '0', 4, '2017-12-27 16:57:33', '2019-06-14 19:56:00');
INSERT INTO t_menu
VALUES (8, 2, '在线用户', '/monitor/online', 'online:view', '', '0', 1, '2017-12-27 16:59:33', '2019-06-13 14:30:31');
INSERT INTO t_menu
VALUES (10, 2, '系统日志', '/monitor/log', 'log:view', '', '0', 2, '2017-12-27 17:00:50', '2019-06-13 14:30:37');
INSERT INTO t_menu
VALUES (11, 3, '新增用户', NULL, 'user:add', NULL, '1', NULL, '2017-12-27 17:02:58', NULL);
INSERT INTO t_menu
VALUES (12, 3, '修改用户', NULL, 'user:update', NULL, '1', NULL, '2017-12-27 17:04:07', NULL);
INSERT INTO t_menu
VALUES (13, 3, '删除用户', NULL, 'user:delete', NULL, '1', NULL, '2017-12-27 17:04:58', NULL);
INSERT INTO t_menu
VALUES (14, 4, '新增角色', NULL, 'role:add', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO t_menu
VALUES (15, 4, '修改角色', NULL, 'role:update', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO t_menu
VALUES (16, 4, '删除角色', NULL, 'role:delete', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO t_menu
VALUES (17, 5, '新增菜单', NULL, 'menu:add', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO t_menu
VALUES (18, 5, '修改菜单', NULL, 'menu:update', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO t_menu
VALUES (19, 5, '删除菜单', NULL, 'menu:delete', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO t_menu
VALUES (20, 6, '新增部门', NULL, 'dept:add', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO t_menu
VALUES (21, 6, '修改部门', NULL, 'dept:update', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO t_menu
VALUES (22, 6, '删除部门', NULL, 'dept:delete', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO t_menu
VALUES (23, 8, '踢出用户', NULL, 'user:kickout', NULL, '1', NULL, '2017-12-27 17:11:13', NULL);
INSERT INTO t_menu
VALUES (24, 10, '删除日志', NULL, 'log:delete', NULL, '1', NULL, '2017-12-27 17:11:45', '2019-06-06 05:56:40');
INSERT INTO t_menu
VALUES (101, 0, '任务调度', NULL, NULL, 'layui-icon-time-circle', '0', 3, '2018-02-24 15:52:57', NULL);
INSERT INTO t_menu
VALUES (102, 101, '定时任务', '/job/job', 'job:view', '', '0', 1, '2018-02-24 15:53:53', '2018-04-25 09:05:12');
INSERT INTO t_menu
VALUES (103, 102, '新增任务', NULL, 'job:add', NULL, '1', NULL, '2018-02-24 15:55:10', NULL);
INSERT INTO t_menu
VALUES (104, 102, '修改任务', NULL, 'job:update', NULL, '1', NULL, '2018-02-24 15:55:53', NULL);
INSERT INTO t_menu
VALUES (105, 102, '删除任务', NULL, 'job:delete', NULL, '1', NULL, '2018-02-24 15:56:18', NULL);
INSERT INTO t_menu
VALUES (106, 102, '暂停任务', NULL, 'job:pause', NULL, '1', NULL, '2018-02-24 15:57:08', NULL);
INSERT INTO t_menu
VALUES (107, 102, '恢复任务', NULL, 'job:resume', NULL, '1', NULL, '2018-02-24 15:58:21', NULL);
INSERT INTO t_menu
VALUES (108, 102, '立即执行任务', NULL, 'job:run', NULL, '1', NULL, '2018-02-24 15:59:45', NULL);
INSERT INTO t_menu
VALUES (109, 101, '调度日志', '/job/log', 'job:log:view', '', '0', 2, '2018-02-24 16:00:45', '2019-06-09 02:48:19');
INSERT INTO t_menu
VALUES (110, 109, '删除日志', NULL, 'job:log:delete', NULL, '1', NULL, '2018-02-24 16:01:21', NULL);
INSERT INTO t_menu
VALUES (115, 0, '其他模块', NULL, NULL, 'layui-icon-gift', '0', 5, '2019-05-27 10:18:07', NULL);
INSERT INTO t_menu
VALUES (116, 115, 'Apex图表', '', '', NULL, '0', 2, '2019-05-27 10:21:35', NULL);
INSERT INTO t_menu
VALUES (117, 116, '线性图表', '/others/apex/line', 'apex:line:view', NULL, '0', 1, '2019-05-27 10:24:49', NULL);
INSERT INTO t_menu
VALUES (118, 115, '高德地图', '/others/map', 'map:view', '', '0', 3, '2019-05-27 17:13:12', '2019-06-12 15:33:00');
INSERT INTO t_menu
VALUES (119, 116, '面积图表', '/others/apex/area', 'apex:area:view', NULL, '0', 2, '2019-05-27 18:49:22', NULL);
INSERT INTO t_menu
VALUES (120, 116, '柱形图表', '/others/apex/column', 'apex:column:view', NULL, '0', 3, '2019-05-27 18:51:33', NULL);
INSERT INTO t_menu
VALUES (121, 116, '雷达图表', '/others/apex/radar', 'apex:radar:view', NULL, '0', 4, '2019-05-27 18:56:05', NULL);
INSERT INTO t_menu
VALUES (122, 116, '条形图表', '/others/apex/bar', 'apex:bar:view', NULL, '0', 5, '2019-05-27 18:57:02', NULL);
INSERT INTO t_menu
VALUES (123, 116, '混合图表', '/others/apex/mix', 'apex:mix:view', '', '0', 6, '2019-05-27 18:58:04',
        '2019-06-06 02:55:23');
INSERT INTO t_menu
VALUES (125, 115, '导入导出', '/others/eximport', 'others:eximport:view', '', '0', 4, '2019-05-27 19:01:57',
        '2019-06-13 01:20:14');
INSERT INTO t_menu
VALUES (126, 132, '系统图标', '/others/febs/icon', 'febs:icons:view', '', '0', 4, '2019-05-27 19:03:18',
        '2019-06-06 03:05:26');
INSERT INTO t_menu
VALUES (127, 2, '请求追踪', '/monitor/httptrace', 'httptrace:view', '', '0', 6, '2019-05-27 19:06:38',
        '2019-06-13 14:36:43');
INSERT INTO t_menu
VALUES (128, 2, '系统信息', NULL, NULL, NULL, '0', 7, '2019-05-27 19:08:23', NULL);
INSERT INTO t_menu
VALUES (129, 128, 'JVM信息', '/monitor/jvm', 'jvm:view', '', '0', 1, '2019-05-27 19:08:50', '2019-06-13 14:36:51');
INSERT INTO t_menu
VALUES (131, 128, '服务器信息', '/monitor/server', 'server:view', '', '0', 3, '2019-05-27 19:10:07', '2019-06-13 14:37:04');
INSERT INTO t_menu
VALUES (132, 115, 'FEBS组件', '', '', NULL, '0', 1, '2019-05-27 19:13:54', NULL);
INSERT INTO t_menu
VALUES (133, 132, '表单组件', '/others/febs/form', 'febs:form:view', NULL, '0', 1, '2019-05-27 19:14:45', NULL);
INSERT INTO t_menu
VALUES (134, 132, 'FEBS工具', '/others/febs/tools', 'febs:tools:view', '', '0', 3, '2019-05-29 10:11:22',
        '2019-06-12 13:21:27');
INSERT INTO t_menu
VALUES (135, 132, '表单组合', '/others/febs/form/group', 'febs:formgroup:view', NULL, '0', 2, '2019-05-29 10:16:19', NULL);
INSERT INTO t_menu
VALUES (136, 2, '登录日志', '/monitor/loginlog', 'loginlog:view', '', '0', 3, '2019-05-29 15:56:15', '2019-06-13 14:35:56');
INSERT INTO t_menu
VALUES (137, 0, '代码生成', '', NULL, 'layui-icon-verticalright', '0', 4, '2019-06-03 15:35:58', NULL);
INSERT INTO t_menu
VALUES (138, 137, '生成配置', '/generator/configure', 'generator:configure:view', NULL, '0', 1, '2019-06-03 15:38:36',
        NULL);
INSERT INTO t_menu
VALUES (139, 137, '代码生成', '/generator/generator', 'generator:view', '', '0', 2, '2019-06-03 15:39:15',
        '2019-06-13 14:31:38');
INSERT INTO t_menu
VALUES (159, 132, '其他组件', '/others/febs/others', 'others:febs:others', '', '0', 5, '2019-06-12 07:51:08',
        '2019-06-12 07:51:40');
INSERT INTO t_menu
VALUES (160, 3, '密码重置', NULL, 'user:password:reset', NULL, '1', NULL, '2019-06-13 08:40:13', NULL);
INSERT INTO t_menu
VALUES (161, 3, '导出Excel', NULL, 'user:export', NULL, '1', NULL, '2019-06-13 08:40:34', NULL);
INSERT INTO t_menu
VALUES (162, 4, '导出Excel', NULL, 'role:export', NULL, '1', NULL, '2019-06-13 14:29:00', '2019-06-13 14:29:11');
INSERT INTO t_menu
VALUES (163, 5, '导出Excel', NULL, 'menu:export', NULL, '1', NULL, '2019-06-13 14:29:32', NULL);
INSERT INTO t_menu
VALUES (164, 6, '导出Excel', NULL, 'dept:export', NULL, '1', NULL, '2019-06-13 14:29:59', NULL);
INSERT INTO t_menu
VALUES (165, 138, '修改配置', NULL, 'generator:configure:update', NULL, '1', NULL, '2019-06-13 14:32:09',
        '2019-06-13 14:32:20');
INSERT INTO t_menu
VALUES (166, 139, '生成代码', NULL, 'generator:generate', NULL, '1', NULL, '2019-06-13 14:32:51', NULL);
INSERT INTO t_menu
VALUES (167, 125, '模板下载', NULL, 'eximport:template', NULL, '1', NULL, '2019-06-13 14:33:37', NULL);
INSERT INTO t_menu
VALUES (168, 125, '导出Excel', NULL, 'eximport:export', NULL, '1', NULL, '2019-06-13 14:33:57', NULL);
INSERT INTO t_menu
VALUES (169, 125, '导入Excel', NULL, 'eximport:import', NULL, '1', NULL, '2019-06-13 14:34:19', NULL);
INSERT INTO t_menu
VALUES (170, 10, '导出Excel', NULL, 'log:export', NULL, '1', NULL, '2019-06-13 14:34:55', NULL);
INSERT INTO t_menu
VALUES (171, 136, '删除日志', NULL, 'loginlog:delete', NULL, '1', NULL, '2019-06-13 14:35:27', '2019-06-13 14:36:08');
INSERT INTO t_menu
VALUES (172, 136, '导出Excel', NULL, 'loginlog:export', NULL, '1', NULL, '2019-06-13 14:36:26', NULL);
INSERT INTO t_menu
VALUES (173, 102, '导出Excel', NULL, 'job:export', NULL, '1', NULL, '2019-06-13 14:37:25', NULL);
INSERT INTO t_menu
VALUES (174, 109, '导出Excel', NULL, 'job:log:export', NULL, '1', NULL, '2019-06-13 14:37:46', '2019-06-13 14:38:02');
INSERT INTO t_menu
VALUES (175, 2, 'Swagger文档', '/monitor/swagger', 'swagger:view', '', '0', 8, '2019-08-18 17:25:36',
        '2019-08-18 17:25:59');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role (
    role_id     BIGINT(20)                                              NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_name   VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    remark      VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
    create_time DATETIME(0)                                             NOT NULL COMMENT '创建时间',
    modify_time DATETIME(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (role_id) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 81
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci COMMENT = '角色表'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO t_role
VALUES (1, '系统管理员', '系统管理员，拥有所有操作权限 ^_^', '2019-06-14 16:23:11', '2019-08-18 17:26:19');
INSERT INTO t_role
VALUES (2, '注册账户', '注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限', '2019-06-14 16:00:15', '2019-08-18 17:36:02');
INSERT INTO t_role
VALUES (77, 'Redis监控员', '负责Redis模块', '2019-06-14 20:49:22', NULL);
INSERT INTO t_role
VALUES (78, '系统监控员', '负责整个系统监控模块', '2019-06-14 20:50:07', NULL);
INSERT INTO t_role
VALUES (79, '跑批人员', '负责任务调度跑批模块', '2019-06-14 20:51:02', NULL);
INSERT INTO t_role
VALUES (80, '开发人员', '拥有代码生成模块的权限', '2019-06-14 20:51:26', NULL);

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS t_role_menu;
CREATE TABLE t_role_menu (
    id      BIGINT(20) PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    role_id BIGINT(20) NOT NULL COMMENT '角色ID',
    menu_id BIGINT(20) NOT NULL COMMENT '菜单/按钮ID'
)
    ENGINE = InnoDB
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci COMMENT = '角色菜单关联表'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (77, 2);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 2);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 8);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 23);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 10);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 24);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 170);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 136);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 171);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 172);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 127);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 128);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 129);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (78, 131);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 101);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 102);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 103);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 104);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 105);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 106);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 107);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 108);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 173);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 109);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 110);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (79, 174);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (80, 137);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (80, 138);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (80, 165);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (80, 139);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (80, 166);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 1);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 3);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 11);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 12);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 13);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 160);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 161);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 4);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 14);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 15);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 16);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 162);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 5);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 17);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 18);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 19);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 163);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 6);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 20);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 21);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 22);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 164);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 2);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 8);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 23);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 10);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 24);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 170);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 136);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 171);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 172);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 127);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 128);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 129);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 131);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 175);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 101);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 102);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 103);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 104);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 105);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 106);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 107);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 108);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 173);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 109);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 110);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 174);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 137);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 138);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 165);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 139);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 166);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 115);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 132);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 133);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 135);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 134);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 126);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 159);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 116);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 117);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 119);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 120);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 121);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 122);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 123);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 118);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 125);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 167);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 168);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (1, 169);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 1);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 3);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 161);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 4);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 14);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 162);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 5);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 17);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 163);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 6);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 20);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 164);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 2);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 8);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 10);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 170);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 136);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 172);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 127);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 128);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 129);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 131);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 175);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 101);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 102);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 173);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 109);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 174);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 137);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 138);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 139);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 115);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 132);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 133);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 135);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 134);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 126);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 159);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 116);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 117);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 119);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 120);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 121);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 122);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 123);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 118);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 125);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 167);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 168);
INSERT INTO t_role_menu(role_id, menu_id)
VALUES (2, 169);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    user_id         BIGINT(20)                                              NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username        VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    password        VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    dept_id         BIGINT(20)                                              NULL DEFAULT NULL COMMENT '部门ID',
    email           VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    mobile          VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '联系电话',
    status          CHAR(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL COMMENT '状态 0锁定 1有效',
    create_time     DATETIME(0)                                             NOT NULL COMMENT '创建时间',
    modify_time     DATETIME(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    last_login_time DATETIME(0)                                             NULL DEFAULT NULL COMMENT '最近访问时间',
    ssex            CHAR(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '性别 0男 1女 2保密',
    is_tab          CHAR(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否开启tab，0关闭 1开启',
    theme           VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '主题',
    avatar          VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
    description     VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (user_id) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 8
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci COMMENT = '用户表'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO t_user
VALUES (1, 'dunwu', 'a240906e56113b94c64a12d515b84198', 1, 'dunwu@qq.com', '17788888888', '1', '2019-06-14 20:39:22',
        '2019-12-04 16:47:11', '2019-12-04 16:48:10', '0', '1', 'black', 'cnrhVkzwxjPwAaCfPbdc.png', '我是帅比作者。');
INSERT INTO t_user
VALUES (2, 'Scott', '1d685729d113cfd03872f154939bee1c', 10, 'scott@gmail.com', '17722222222', '1',
        '2019-06-14 20:55:53', '2019-06-14 21:05:43', '2019-08-18 17:36:18', '0', '1', 'black',
        'gaOngJwsRYRaVAuXXcmB.png', '我是scott。');
INSERT INTO t_user
VALUES (3, 'Reina', '1461afff857c02afbfb768aa3771503d', 4, 'Reina@hotmail.com', '17711111111', '0',
        '2019-06-14 21:07:38', '2019-06-14 21:09:06', '2019-06-14 21:08:26', '1', '1', 'black',
        '5997fedcc7bd4cffbd350b40d1b5b987.jpg', '由于公款私用，已被封禁。');
INSERT INTO t_user
VALUES (4, 'Micaela', '9f2daa2c7bed6870fcbb5b9a51d6300e', 10, 'Micaela@163.com', '17733333333', '1',
        '2019-06-14 21:10:13', '2019-06-14 21:11:26', '2019-06-14 21:10:37', '0', '0', 'white', '20180414165909.jpg',
        '我叫米克拉');
INSERT INTO t_user
VALUES (5, 'Jana', '176679b77b3c3e352bd3b30ddc81083e', 8, 'Jana@126.com', '17744444444', '1', '2019-06-14 21:12:16',
        '2019-06-14 21:12:52', '2019-06-14 21:12:32', '1', '1', 'white', '20180414165821.jpg', '大家好，我叫简娜');
INSERT INTO t_user
VALUES (6, 'Georgie', 'dffc683378cdaa015a0ee9554c532225', 3, 'Georgie@qq.com', '17766666666', '0',
        '2019-06-14 21:15:09', '2019-06-14 21:16:25', '2019-06-14 21:16:11', '2', '0', 'black',
        'BiazfanxmamNRoxxVxka.png', '生产执行rm -rf *，账号封禁T T');
INSERT INTO t_user
VALUES (7, 'Margot', '31379841b9f4bfde22b8b40471e9a6ce', 9, 'Margot@qq.com', '13444444444', '1', '2019-06-14 21:17:53',
        '2019-06-14 21:18:59', '2019-06-14 21:18:07', '1', '1', 'white', '20180414165834.jpg', '大家好我叫玛戈');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS t_user_role;
CREATE TABLE t_user_role (
    id      BIGINT(20) PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT(20) NOT NULL COMMENT '用户ID',
    role_id BIGINT(20) NOT NULL COMMENT '角色ID'
)
    ENGINE = InnoDB
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci COMMENT = '用户角色关联表'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO t_user_role(user_id, role_id)
VALUES (1, 1);
INSERT INTO t_user_role(user_id, role_id)
VALUES (2, 2);
INSERT INTO t_user_role(user_id, role_id)
VALUES (3, 77);
INSERT INTO t_user_role(user_id, role_id)
VALUES (4, 78);
INSERT INTO t_user_role(user_id, role_id)
VALUES (5, 79);
INSERT INTO t_user_role(user_id, role_id)
VALUES (6, 80);
INSERT INTO t_user_role(user_id, role_id)
VALUES (7, 78);
INSERT INTO t_user_role(user_id, role_id)
VALUES (7, 79);
INSERT INTO t_user_role(user_id, role_id)
VALUES (7, 80);

SET FOREIGN_KEY_CHECKS = 1;
