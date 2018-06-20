-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2018-06-20 17:02:01
-- 服务器版本： 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `im_cheng`
--

-- --------------------------------------------------------

--
-- 表的结构 `im_message`
--

CREATE TABLE `im_message` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `fromUser` bigint(20) UNSIGNED NOT NULL,
  `fromNick` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `fromAvatar` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `toUser` bigint(20) UNSIGNED NOT NULL,
  `content` text COLLATE utf8mb4_bin NOT NULL,
  `createTime` bigint(10) UNSIGNED NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_bin DEFAULT 'text'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- 转存表中的数据 `im_message`
--

INSERT INTO `im_message` (`id`, `fromUser`, `fromNick`, `fromAvatar`, `toUser`, `content`, `createTime`, `type`) VALUES
(42, 10000, 'Cheng', '1', 10002, '<p style="width: 160px;height: auto;word-wrap:break-word;word-break:break-all;margin-top: 0;text-align: right;">?V</p>', 1528711863588, 'text');

-- --------------------------------------------------------

--
-- 表的结构 `im_relationship`
--

CREATE TABLE `im_relationship` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_a_id` bigint(20) UNSIGNED NOT NULL COMMENT 'a用户id',
  `user_b_id` bigint(20) UNSIGNED NOT NULL COMMENT 'b用户id',
  `request` int(30) UNSIGNED NOT NULL COMMENT '1：a请求添加b为好友 2：b请求添加a为好友',
  `request_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  `result` int(30) UNSIGNED DEFAULT NULL COMMENT '1：已接受 2：等待中 3：已拒绝',
  `result_time` datetime DEFAULT NULL COMMENT '回应时间',
  `relationship` int(30) UNSIGNED NOT NULL COMMENT '1：陌生人 2：普通好友',
  `permission_b` int(8) UNSIGNED NOT NULL DEFAULT '33554431' COMMENT 'a给b的权限25种',
  `permission_a` int(8) UNSIGNED NOT NULL DEFAULT '33554431' COMMENT 'b给a的权限25种',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- 转存表中的数据 `im_relationship`
--

INSERT INTO `im_relationship` (`id`, `user_a_id`, `user_b_id`, `request`, `request_time`, `result`, `result_time`, `relationship`, `permission_b`, `permission_a`, `gmt_modified`, `gmt_create`) VALUES
(4, 10001, 10000, 1, '2018-05-19 20:11:30', NULL, NULL, 1, 33554431, 33554431, '2018-05-19 20:11:30', '2018-05-19 20:11:30'),
(5, 10002, 10000, 1, '2018-05-22 09:25:32', NULL, NULL, 1, 33554431, 33554431, '2018-05-22 09:25:32', '2018-05-22 09:25:32'),
(6, 10001, 10002, 1, '2018-05-22 10:19:33', NULL, NULL, 1, 33554431, 33554431, '2018-05-22 10:19:33', '2018-05-22 10:19:33'),
(7, 10005, 10000, 1, '2018-06-20 09:58:56', NULL, NULL, 1, 33554431, 33554431, '2018-06-20 09:58:56', '2018-06-20 09:58:56');

-- --------------------------------------------------------

--
-- 表的结构 `im_user`
--

CREATE TABLE `im_user` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` bigint(20) UNSIGNED DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `email` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机',
  `avatar` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '1' COMMENT '头像',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省份',
  `town` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '城市',
  `address` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址',
  `is_actived` int(5) UNSIGNED NOT NULL COMMENT '1激活；2未激活',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表';

--
-- 转存表中的数据 `im_user`
--

INSERT INTO `im_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `avatar`, `province`, `town`, `address`, `is_actived`, `gmt_modified`, `gmt_create`) VALUES
(10000, 10000, '3cf84af30821e4474b05835c52c2c234', 'Cheng', '511641788@qq.com', '18342918729', '1', 'Liaoning', 'Huludao', '兴城市龙湾南大街188号', 1, '2018-06-18 17:51:18', '2018-05-19 19:59:42'),
(10001, 10001, '3cf84af30821e4474b05835c52c2c234', 'Nickname', '511641926@qq.com', '18342918729', '1', 'Beijing', 'beijing', '辽工大', 1, '2018-06-18 17:51:35', '2018-05-19 20:01:48'),
(10002, 10002, '3cf84af30821e4474b05835c52c2c234', '10002', '511641722@qq.com', NULL, '1', NULL, NULL, NULL, 1, '2018-05-22 09:27:37', '2018-05-22 09:24:57'),
(10005, 10005, '3cf84af30821e4474b05835c52c2c234', '10005', '511641726@qq.com', '18342918729', '1', '辽宁省', '葫芦岛市', NULL, 1, '2018-06-20 13:39:26', '2018-06-20 09:56:54');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `im_message`
--
ALTER TABLE `im_message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `toUser` (`toUser`);

--
-- Indexes for table `im_relationship`
--
ALTER TABLE `im_relationship`
  ADD PRIMARY KEY (`id`),
  ADD KEY `a_b_id` (`user_a_id`,`user_b_id`);

--
-- Indexes for table `im_user`
--
ALTER TABLE `im_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `im_message`
--
ALTER TABLE `im_message`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- 使用表AUTO_INCREMENT `im_relationship`
--
ALTER TABLE `im_relationship`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- 使用表AUTO_INCREMENT `im_user`
--
ALTER TABLE `im_user`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10006;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
