SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;


CREATE
DATABASE `dting` CHARACTER SET 'utf8mb4';

use dting;


-- ----------------------------
-- Table structure for dting_env
-- ----------------------------
DROP TABLE IF EXISTS `dting_env`;
CREATE TABLE `dting_env`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `env_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环境名称',
                              `create_date` bigint(15) NOT NULL COMMENT '时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `env_name_on_index`(`env_name`) USING BTREE COMMENT '唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dting_instance
-- ----------------------------
DROP TABLE IF EXISTS `dting_instance`;
CREATE TABLE `dting_instance`  (
                                   `id` int(13) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `instance_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
                                   `server_id` int(13) NOT NULL COMMENT '所属服务',
                                   `timeout` bigint(32) NOT NULL DEFAULT -1 COMMENT '实例下线之后多长时间删除，-1不删除',
                                   `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态 0下线  1在线',
                                   `last_update_time` bigint(32) NOT NULL DEFAULT 0 COMMENT '最后一次更新时间',
                                   `create_date` bigint(32) NOT NULL COMMENT '创建时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `instance_name__server_id`(`instance_name`, `server_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dting_server
-- ----------------------------
DROP TABLE IF EXISTS `dting_server`;
CREATE TABLE `dting_server`  (
                                 `id` int(13) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `server_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务名称',
                                 `env_id` int(13) NOT NULL COMMENT '归属环境',
                                 `create_date` bigint(32) NOT NULL COMMENT '创建时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `server_name_env_id_index`(`server_name`, `env_id`) USING BTREE COMMENT '相同的环境id 不可能存在相同的服务名称'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message_cpu_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `message_cpu_snapshot`;
CREATE TABLE `message_cpu_snapshot`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                         `instance_id` int(13) NOT NULL COMMENT '实例id',
                                         `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                         `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                                         `total_use` double(12, 2) NOT NULL COMMENT '总使用率',
  `user_use` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '用户的使用率',
  `system_use` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '系统使用率',
  `wait` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '等待率',
  `error` double(12, 2) NOT NULL DEFAULT 0.00 COMMENT '错误率',
  `idle` double(12, 2) NOT NULL COMMENT '空闲率',
  `cpu_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '每一个核心数的使用情况',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'cpu使用率的统计信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for message_memory_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `message_memory_snapshot`;
CREATE TABLE `message_memory_snapshot`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                            `instance_id` int(13) NOT NULL COMMENT '实例id',
                                            `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                            `collect_time` bigint(32) NOT NULL COMMENT '采集时间',
                                            `total_memory` bigint(32) NOT NULL COMMENT '物理总内存大小',
                                            `use_memory` bigint(32) NOT NULL COMMENT '操作系统的已使用内存',
                                            `jvm_total_memory` bigint(32) NOT NULL COMMENT 'jvm总内存大小',
                                            `jvm_use_memory` bigint(32) NOT NULL COMMENT 'jvm已经使用的大小',
                                            `total_swap` bigint(32) NOT NULL COMMENT '操作系统交换内存总容量',
                                            `use_swap` bigint(32) NOT NULL COMMENT '操作系统已经使用的交换内存总量',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统内存以及JVM内存的统计信息数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for message_network_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `message_network_snapshot`;
CREATE TABLE `message_network_snapshot`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                             `instance_id` int(13) NOT NULL COMMENT '实例id',
                                             `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                             `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                                             `network_data_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网卡的数据集合',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网卡读写数据包的统计数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for message_task_run_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `message_task_run_snapshot`;
CREATE TABLE `message_task_run_snapshot`  (
                                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                              `instance_id` int(13) NOT NULL COMMENT '实例id',
                                              `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                              `collect_time` bigint(32) NOT NULL COMMENT '采集的时间',
                                              `thread_pool_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线程池名称',
                                              `thread_pool_group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拼接规则为 server_env:server_key:instance_key:thread_pool_name',
                                              `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
                                              `start_time` bigint(64) NOT NULL COMMENT '开始的时间戳',
                                              `end_time` bigint(64) NOT NULL COMMENT '结束的时间戳',
                                              `success` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务执行是否成功  0成功  1失败',
                                              `rejected` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务是否被拒绝     0未拒绝  1 拒绝',
                                              `error_msg` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '如果是失败了，记录一个失败信息',
                                              `active_count` int(12) NOT NULL COMMENT '当前活跃的任务数量',
                                              `queue_remaining_capacity` int(12) NOT NULL COMMENT '当前队列的剩余容量',
                                              `queue_size` int(12) NOT NULL COMMENT '当前队列已经使用的容量',
                                              PRIMARY KEY (`id`) USING BTREE,
                                              INDEX `group_index`(`thread_pool_group_name`) USING BTREE,
                                              INDEX `message_tag_index`(`instance_id`) USING BTREE,
                                              INDEX `message_ip_index`(`message_ip`) USING BTREE,
                                              INDEX `thread_pool_name_index`(`thread_pool_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线程池中，任务运行的日志情况' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for message_thread_pool_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `message_thread_pool_snapshot`;
CREATE TABLE `message_thread_pool_snapshot`  (
                                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                                 `instance_id` int(13) NOT NULL COMMENT '实例id',
                                                 `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                                 `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                                                 `collect_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联主键',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线程池的状态采集信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for network_detailed_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `network_detailed_snapshot`;
CREATE TABLE `network_detailed_snapshot`  (
                                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                              `network_data_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '和一批次统计的数据的关联字段',
                                              `network_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网卡名称',
                                              `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
                                              `subnet_mask` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子网掩码',
                                              `receiving_data_byte` bigint(64) NULL DEFAULT 0 COMMENT '接收的数据包数量',
                                              `send_data_byte` bigint(64) NULL DEFAULT 0 COMMENT '发送的数据包数量',
                                              `receiving_data_packet_count` bigint(64) NULL DEFAULT 0 COMMENT '接收的数据字节数',
                                              `send_data_packet_count` bigint(64) NULL DEFAULT 0 COMMENT '发送的数据包字节数',
                                              `receiving_error_data_packet_count` bigint(64) NULL DEFAULT 0 COMMENT '接收的错误数据包数量',
                                              `send_error_data_packet_count` bigint(64) NULL DEFAULT 0 COMMENT '发送的错误数据包数量',
                                              `receiving_discarded_data_dacket_count` bigint(64) NULL DEFAULT 0 COMMENT '接收的丢弃数据包数量',
                                              `send_discarded_data_packet_count` bigint(64) NULL DEFAULT 0 COMMENT '发送的丢弃数据包数量',
                                              PRIMARY KEY (`id`) USING BTREE,
                                              INDEX `network_data_key`(`network_data_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网卡读写数据包的统计数据,详细的网卡数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for thread_pool_config
-- ----------------------------
DROP TABLE IF EXISTS `thread_pool_config`;
CREATE TABLE `thread_pool_config`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                       `instance_id` int(13) NOT NULL COMMENT '实例id',
                                       `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                       `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                                       `thread_pool_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线程池的名称',
                                       `thread_pool_group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线程池的组名称 拼接规则为 server_env:server_key:instance_key:thread_pool_name',
                                       `core_count` int(12) NOT NULL COMMENT '核心数',
                                       `max_count` int(255) NOT NULL COMMENT '最大线程数',
                                       `queue_total_size` int(12) NOT NULL COMMENT '队列总长度',
                                       `reject_handler_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拒绝策略名称',
                                       `queue_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '队列名称',
                                       `keep_alive_time` bigint(14) NOT NULL COMMENT '等待时常    毫秒',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       INDEX `group_index`(`thread_pool_group_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线程池的配置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for thread_pool_detailed_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `thread_pool_detailed_snapshot`;
CREATE TABLE `thread_pool_detailed_snapshot`  (
                                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                                  `collect_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联值，关联线程池的采集',
                                                  `thread_pool_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线程池的名称',
                                                  `active_count` int(12) NOT NULL COMMENT '活跃数',
                                                  `core_count` int(12) NOT NULL COMMENT '核心数',
                                                  `max_count` int(12) NOT NULL COMMENT '最大线程数',
                                                  `queue_use_count` int(12) NOT NULL COMMENT '队列已使用的长度',
                                                  `queue_total_size` int(32) NOT NULL COMMENT '队列的总长度',
                                                  `busy_weight` double(6, 0) NOT NULL COMMENT '线程池忙碌的数值，计算方式为 ((活跃数/核心数) + (活跃数/最大线程数) + (队列堆积数/队列总长度))*1000',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `collect_key_index`(`collect_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线程池的状态采集信息按照时间定时采集的' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

