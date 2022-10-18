SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


CREATE DATABASE `dting` CHARACTER SET 'utf8mb4';

use dting;

-- ----------------------------
-- Table structure for cpu_data
-- ----------------------------
DROP TABLE IF EXISTS `cpu_data`;
CREATE TABLE `cpu_data`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                             `unique` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的唯一标识，当消息群发后，标记为一批数据，唯一不为空',
                             `message_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息标签，标记消息的来源',
                             `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                             `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                             `total_use` double(4, 2) NOT NULL COMMENT '总使用率',
  `user_use` double(4, 2) NOT NULL DEFAULT 0.00 COMMENT '用户的使用率',
  `system_use` double(4, 2) NOT NULL DEFAULT 0.00 COMMENT '系统使用率',
  `wait` double(4, 2) NOT NULL DEFAULT 0.00 COMMENT '等待率',
  `error` double(4, 2) NOT NULL DEFAULT 0.00 COMMENT '错误率',
  `idle` double(4, 2) NOT NULL COMMENT '空闲率',
  `cpu_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '每一个核心数的使用情况',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `message_unique`(`unique`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'cpu使用率的统计信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for memory_data
-- ----------------------------
DROP TABLE IF EXISTS `memory_data`;
CREATE TABLE `memory_data`  (
                                `id` int(11) NOT NULL COMMENT '主键 自动递增',
                                `unique` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的唯一标识，当消息群发后，标记为一批数据，唯一不为空',
                                `message_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息标签，标记消息的来源',
                                `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                `collect_time` bigint(32) NOT NULL COMMENT '采集时间',
                                `jvm_total_memory` bigint(64) NOT NULL COMMENT 'jvm总内存大小',
                                `total_memory` bigint(64) NOT NULL COMMENT '物理总内存大小',
                                `jvm_use_memory` bigint(64) NOT NULL COMMENT 'jvm已经使用的大小',
                                `use_memory` bigint(64) NOT NULL COMMENT '操作系统的已使用内存',
                                `total_swap` bigint(64) NOT NULL COMMENT '操作系统交换内存总容量',
                                `use_swap` bigint(64) NOT NULL COMMENT '操作系统已经使用的交换内存总量',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `message_unique`(`unique`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统内存以及JVM内存的统计信息数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for network_data
-- ----------------------------
DROP TABLE IF EXISTS `network_data`;
CREATE TABLE `network_data`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                 `unique` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的唯一标识，当消息群发后，标记为一批数据，唯一不为空',
                                 `message_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息标签，标记消息的来源',
                                 `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                 `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                                 `network_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网卡的数据集合',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `message_unique`(`unique`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网卡读写数据包的统计数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for task_run_log
-- ----------------------------
DROP TABLE IF EXISTS `task_run_log`;
CREATE TABLE `task_run_log`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                 `unique` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的唯一标识，当消息群发后，标记为一批数据，唯一不为空',
                                 `message_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息标签，标记消息的来源',
                                 `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                 `thread_pool_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线程池名称',
                                 `thread_pool_group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拼接规则为 message_ip:thread_pool_name',
                                 `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
                                 `start_time` bigint(64) NOT NULL COMMENT '开始的时间戳',
                                 `end_time` bigint(64) NOT NULL COMMENT '结束的时间戳',
                                 `success` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务执行是否成功',
                                 `rejected` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务是否被拒绝',
                                 `error_msg` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '如果是失败了，记录一个失败信息',
                                 `active_count` int(12) NOT NULL COMMENT '当前活跃的任务数量',
                                 `queue_remaining_capacity` int(12) NOT NULL COMMENT '当前队列的剩余容量',
                                 `queue_size` int(12) NOT NULL COMMENT '当前队列已经使用的容量',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `message_unique`(`unique`) USING BTREE,
                                 INDEX `group_index`(`thread_pool_group_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线程池中，任务运行的日志情况' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for thread_pool_data
-- ----------------------------
DROP TABLE IF EXISTS `thread_pool_data`;
CREATE TABLE `thread_pool_data`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
                                     `unique` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的唯一标识，当消息群发后，标记为一批数据，唯一不为空',
                                     `message_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息标签，标记消息的来源',
                                     `message_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标记消息的ip地址',
                                     `collect_time` bigint(32) NOT NULL COMMENT '采集的时间戳',
                                     `thread_pool_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线程池的名称',
                                     `active_count` int(12) NOT NULL COMMENT '活跃数',
                                     `core_count` int(12) NOT NULL COMMENT '核心数',
                                     `max_count` int(12) NOT NULL COMMENT '最大线程数',
                                     `queue_use_count` int(12) NOT NULL COMMENT '队列已使用的长度',
                                     `queue_total_size` int(32) NOT NULL COMMENT '队列的总长度',
                                     `busy_weight` int(6) NOT NULL COMMENT '线程池忙碌的数值，计算方式为 ((活跃数/核心数) + (活跃数/最大线程数) + (队列堆积数/队列总长度))*1000',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `message_unique`(`unique`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线程池的状态采集信息' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

