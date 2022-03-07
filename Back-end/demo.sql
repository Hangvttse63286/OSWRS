-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 07, 2022 lúc 08:39 PM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `demo`
--
use heroku_3be4d1f3b4fba43;
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

/* DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `category_name` varchar(60) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK41g4n0emuvcm3qyf1f6cn43c0` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci; */

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `category_name`, `is_deleted`) VALUES
(1, 'SKIRT', b'0'),
(2, 'SHIRT', b'0'),
(3, 'JEANS', b'0'),
(4, 'DRESS', b'0'),
(5, 'SWEATER', b'0');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `genders`
--

/* DROP TABLE IF EXISTS `genders`;
CREATE TABLE IF NOT EXISTS `genders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discounts`
--

/* DROP TABLE IF EXISTS `discounts`;
CREATE TABLE IF NOT EXISTS `discounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(11) DEFAULT NULL,
  `percentage` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `discounts`
--

INSERT INTO `discounts` (`id`, `description`, `is_active`, `name`, `percentage`) VALUES
(1, 'Flash sale', b'1', 'Flash sale', 10),
(2, 'Spring sale', b'1', 'Spring sale', 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hibernate_sequence`
--

/* DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

/* DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `payments`
--

INSERT INTO `payments` (`id`, `name`) VALUES
(1, 'COD'),
(2, 'VNPAY');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

/* DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_STAFF'),
(3, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vouchers`
--

/* DROP TABLE IF EXISTS `vouchers`;
CREATE TABLE IF NOT EXISTS `vouchers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_amount` double DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `max_discount` double DEFAULT NULL,
  `min_spend` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK30ftp2biebbvpik8e49wlmady` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Cấu trúc bảng cho bảng `users`
--

/* DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birthday` date DEFAULT NULL,
  `date_joined` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender_id` int(11) NOT NULL,
  `is_activate` tinyint(1) DEFAULT 1,
  `is_superuser` tinyint(1) DEFAULT 0,
  `last_login` date DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `birthday`, `date_joined`, `email`, `first_name`, `gender_id`, `last_login`, `last_name`, `password`, `phone_number`, `username`, `is_active`) VALUES
(1, '2014-01-02', '2022-01-09', 'hang211@gmail.com', 'Thi Thuy Hang', 1, NULL, 'Vo', '$2a$10$OzMfqo9nJbnb6azTf7Ilou/03Km3jz02WvWLCB0VqjiINNGcRJOMO', '0123456789', 'hangvttse63286', 1),
(3, '2022-01-25', '2022-01-25', 'admin@gmail.com', 'admin', 1, '2022-01-26', 'ad', '$2a$10$AroA1G17/XBbQ.bClMLnUeZpavFDvEcIkxe52Tda40GWR.au/N92G', '0123456789', 'admin', 1),
(6, '2022-01-26', '2022-01-26', 'vothithuyhangnk92@gmail.com', 'Hana', 0, '2022-03-01', 'Vo', '$2a$10$KzMISnfkb.tBM036zW7tROYm4IghgmMjoVbEzf4Rw6q/TPl72yD5O', '0973764045', 'adminhana', 1),
(9, '2022-02-11', '2022-02-11', 'susuhk0209@gmail.com', 'su', 0, NULL, 'su', '$2a$10$g0iUsQHPK7rQAGFX930DDe4imuYPJ8j3mw3hciQnKVuwKO3TsWwCC', '0123456789', 'suadmin', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `password_reset_token`
--

/* DROP TABLE IF EXISTS `password_reset_token`;
CREATE TABLE IF NOT EXISTS `password_reset_token` (
  `id` bigint(20) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKg0guo4k8krgpwuagos61oc06j` (`token`),
  KEY `FK83nsrttkwkb6ym0anu051mtxn` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `password_reset_token`
--

INSERT INTO `password_reset_token` (`id`, `token`, `user_id`) VALUES
(2, 'Ejjc6wbjxbFKHmiJPVCuaZdtwxYUQUiZrczm4HpZn4rWv', 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tokens`
--

/* DROP TABLE IF EXISTS `tokens`;
CREATE TABLE IF NOT EXISTS `tokens` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expired_at` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKna3v9f8s7ucnj16tylrs822qj` (`token`),
  KEY `FK2dylsfo39lgjyqml2tbe0b0ss` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `tokens`
--

INSERT INTO `tokens` (`id`, `expired_at`, `token`, `user_id`) VALUES
(1, '2022-01-20 14:04:59', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYW5ndnR0c2U2MzI4NiIsImlhdCI6MTY0MjU3NTg5OSwiZXhwIjoxNjQyNjYyMjk5fQ.Irog92TeNr1R__SaLajZCETOt8Fg3ATaDUIlljUU9zC_DDfGvq7OnbMPh1zo97WHxSBQjmTWfgyDB9TEoHj-3A', 1),
(2, '2022-01-26 12:35:08', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA4ODkwOCwiZXhwIjoxNjQzMTc1MzA4fQ.sNEsLo4IwEL3jZ1Jj-zqi1gkvW9vFHy_n9n1cr4m2pYF18tkcyC4rftbA-po-miQ042j-cLfGjGPPDTELhCtqg', 3),
(3, '2022-01-26 12:36:02', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA4ODk2MiwiZXhwIjoxNjQzMTc1MzYyfQ.gCgNLZrghq668EQDAE2k5XlwDYMctVqbM_U0PwidxJn-QciOFjLNesi7Z2qXLnd3g_jwBr-HVILf7lHOXD2Xvw', 3),
(4, '2022-01-26 13:26:26', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5MTk4NiwiZXhwIjoxNjQzMTc4Mzg2fQ.4mU4djxhqDYKXjv4sRuGLa1f9d8xp30IQ1eCVr1F9ExZ1WJGAoGWSF84Z7EXh6EqNU8_EaOANghVW7fmCw0wsw', 3),
(5, '2022-01-26 13:31:21', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5MjI4MSwiZXhwIjoxNjQzMTc4NjgxfQ.PIVAA8H2cORdaxnYC_Hmm9-kQaJaaVOAsoAiHbvMGsR5DhnxZv7mv6H3wg4K6kppQUHb_8hr1HLoO42abFn5dQ', 3),
(6, '2022-01-26 13:38:29', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5MjcwOSwiZXhwIjoxNjQzMTc5MTA5fQ.A_kw0cYPd28Dh-O347Yc5SIGOLasP_qigHmBp8MCKwWubvTHRYt9A1UeCNCgszSzs7RSu8FPVF66i1V-BHaFfw', 3),
(7, '2022-01-26 13:39:28', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5Mjc2OCwiZXhwIjoxNjQzMTc5MTY4fQ.1xHo-rjNLT9pqguvdmlA1tXYdmdpf4RzDLGptrfbyoH8cWUxFQlB7Rsq1dXY5mpZDV-yZVrTJZB4lRPbi9lEOg', 3),
(8, '2022-01-26 13:42:41', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5Mjk2MSwiZXhwIjoxNjQzMTc5MzYxfQ.2ZiKpxLLjO2mvn-A6jO1lj9-kR_mMwiZlH16BBizlhws92YINHEse1ajUrBVoupNt7NQMXLvXga5kHD4fz4YhQ', 3),
(9, '2022-01-26 13:51:26', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5MzQ4NiwiZXhwIjoxNjQzMTc5ODg2fQ.vwBUbiEDHwvvtC_xDbUIbW2AE9817thaVcVNYXzt1EQkdKi2YzS9YXZxg1HXjtmMfYx3bSyL6I7MJ8PnbaKlRw', 3),
(10, '2022-01-26 13:57:52', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzA5Mzg3MiwiZXhwIjoxNjQzMTgwMjcyfQ.6XPQ7QXNjItHjoZmNK65S_GSzYu7AICRogovrRXHloQpnqlEcsfAp07-1hDUQS3ngUzUmdVreuZkVfqK1dPGrw', 3),
(11, '2022-01-26 14:41:04', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYW5ndnR0c2U2MzI4NiIsImlhdCI6MTY0MzA5NjQ2NCwiZXhwIjoxNjQzMTgyODY0fQ.dAIaekimfAhssJ0Wv47_fNL4NgSPNNqMw412HBiNZaAm3tqzMv-vT-mNJQfQOyCXAziU3d-RP98sG1xcAlliow', 1),
(12, '2022-01-26 20:32:29', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzExNzU0OSwiZXhwIjoxNjQzMjAzOTQ5fQ.nh3UCLdS-wi7Zvin62VkiNAbHUsBuvcAG23yEjofJuHIdMHRdesU3n8bcRsLce5WiVuRNajyAAacftYvcNuMPg', 3),
(13, '2022-01-26 23:54:06', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzEyOTY0NiwiZXhwIjoxNjQzMjE2MDQ2fQ.V9JSWmdulT63_SfBD3Q9EaoS41_acCegpH0SUcR5_RJWuxL0V5d_DaVO_Z54lKkx7NXxXDR6VfdQlD-z7rrjKw', 3),
(14, '2022-01-26 23:55:08', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzEyOTcwOCwiZXhwIjoxNjQzMjE2MTA4fQ.vuSxtpXiU3tILZucikkPJ64m7HO5HSQ5qE8rzZ5t2swo6b9Ju0o9UiLSa782mTGkGIppdPA-c7fP79MhX2eZ0Q', 3),
(15, '2022-01-27 03:21:22', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzE0MjA4MiwiZXhwIjoxNjQzMjI4NDgyfQ.0Eu39VWYQp0DiYF5bA5c9MNK7C-xp6i1lsis6IXPZDpY6teTtoXfi-4MBjN5IIGB0Ukp2RZCcjyaQQ7KFSoKKg', 3),
(16, '2022-01-27 03:23:35', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDMxNDIyMTUsImV4cCI6MTY0MzIyODYxNX0.sMxxdqccr4g-q8F-4zH1-Vzfz_W-WZJ4i-djyBijPTv9u3xnpeSeeuunb57OJjlLqcrdqaJxb2Uv8aQsj5Jucg', 6),
(17, '2022-01-27 21:31:13', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDMyMDc0NzMsImV4cCI6MTY0MzI5Mzg3M30.MHUOVcFf1T1NfsMtMrbgwG6HiFRwVI_3ZJfhbcLkPy9mPRMq620mI6iOtycpb3GrplS2RJatbisRmIosjyV7OA', 6),
(18, '2022-01-28 01:18:34', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDMyMjExMTQsImV4cCI6MTY0MzMwNzUxNH0.RQ4uAdBi6PfGzgiZB1wRiPFDw7s55s6UF2xKwbyMqRePb2MMSpDuLsBGOhfncXDUhfuOEbtnY9pzy_1AUfuE9g', 6),
(19, '2022-01-28 01:25:27', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDMyMjE1MjcsImV4cCI6MTY0MzMwNzkyN30.12Sx3j612RKEHeyKszkEwkvm3spVGz9N56yJOZmtPB7X_59DcEHHq8hD5C5CE45lgC0GfeY8msGYlOVi-MP3Yg', 6),
(20, '2022-02-15 17:28:30', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDQ4MzQ1MTAsImV4cCI6MTY0NDkyMDkxMH0.AZLPmk2urT6MHe7JzkkpTJUD2xAphhIEMSZwoZn1B_OS2dnKPMYdXoKdtDfzPBEanToj_wJgR0H75079YT1iMA', 6),
(21, '2022-02-28 17:56:38', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDU5NTkzOTgsImV4cCI6MTY0NjA0NTc5OH0.RWJJ4gy6hFQdZ05b447zL6k1BrWj0_0pObucS0_4_lrC2-9sPt-EmMX6_uQBa8ousg5voaW_ilU_I7DOuVc-sQ', 6),
(22, '2022-03-02 02:35:32', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmhhbmEiLCJpYXQiOjE2NDYwNzY5MzIsImV4cCI6MTY0NjE2MzMzMn0.EzBWlQ6edb0R1_rALIsfni5UlB7wY5jS7sJtPmKIZpiIXCJfZXpmtrEHXLk1YPwqQ5rv_tw1cRIPGd1G0hYbfg', 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_roles`
--

/* DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 2),
(3, 1),
(6, 1),
(6, 2),
(6, 3),
(9, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `verification`
--

/* DROP TABLE IF EXISTS `verification`;
CREATE TABLE IF NOT EXISTS `verification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKa0iaxio0f0unln4qmdryyfiqg` (`user_id`),
  UNIQUE KEY `UKh1ganastgcw00w0sl7wwib132` (`verification_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `verification`
--

INSERT INTO `verification` (`id`, `enabled`, `verification_code`, `user_id`) VALUES
(1, b'0', 'qZsH5iwD09BvgqCeAbCN7z3RvZTZ0TYyrNZUfWTkiDVud2MP1fLn7SvqvD0tuC63', 3),
(4, b'1', 'Ph839qwhZqYcSK1Wp942UvzfS1bfmd9wntXIgQGLqZsc7CakTFfmx0ZmonKDQCCe', 6),
(7, b'1', 'CzXpt4bTdjwK7PGraTIgQdqon2PNv20TQB2cQQ4WZ4kRSVlOKRSeeIL5JEcuRwyk', 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

/* DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` varchar(255) NOT NULL,
  `description_details` varchar(255) DEFAULT NULL,
  `description_list` varchar(255) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_status_id` varchar(255) DEFAULT NULL,
  `search_word` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`product_id`, `description_details`, `description_list`, `discount_id`, `product_name`, `product_status_id`, `search_word`) VALUES
('TS003', 'T-Shirt for men', 'T-Shirt for men', 1, 'T-Shirt3', 'instock', 'T-Shirt'),
('TS004', 'T-Shirt for women', 'T-Shirt for women', 2, 'T-Shirt4', 'outstock', 'T-Shirt');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `addresses`
--

/* DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `sub_district` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `addresses`
--

INSERT INTO `addresses` (`id`, `city`, `district`, `phone_number`, `postal_code`, `province`, `receiver_name`, `street`, `sub_district`, `user_id`) VALUES
(1, 'Da nang', 'Hai Chau', '0123456789', '550000', 'Da Nang', 'Hang', 'Ly Tu Trong', 'Thuan Phuoc', 6),
(2, 'Da nang', 'Hai Chau', '0123456789', '550000', 'Da Nang', 'Hang', 'Ly Tu Trong', 'Thuan Phuoc', 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_categories`
--

/* DROP TABLE IF EXISTS `product_categories`;
CREATE TABLE IF NOT EXISTS `product_categories` (
  `product_id` varchar(255) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  KEY `FKd112rx0alycddsms029iifrih` (`category_id`),
  KEY `FKlda9rad6s180ha3dl1ncsp8n7` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_image`
--

/* DROP TABLE IF EXISTS `product_image`;
CREATE TABLE IF NOT EXISTS `product_image` (
  `product_image_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_sku`
--

/* DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE IF NOT EXISTS `product_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted` bit(1) NOT NULL,
  `price` float NOT NULL,
  `sale_limit` int(11) NOT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog8pyxyvo8vmwjix4so5kvv28` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `product_sku`
--

INSERT INTO `product_sku` (`id`, `is_deleted`, `price`, `sale_limit`, `size`, `stock`, `product_id`) VALUES
(1, b'0', 150000, 16, 'S', 92, 'TS003'),
(2, b'0', 150000, 20, 'M', 196, 'TS003'),
(3, b'0', 150000, 10, 'L', 70, 'TS003'),
(4, b'0', 350000, 11, 'S', 20, 'TS004'),
(5, b'0', 350000, 13, 'M', 40, 'TS004'),
(6, b'0', 350000, 11, 'L', 22, 'TS004');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

/* DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delivery_fee_total` double DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `payment_total` double DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `payment_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhlglkvf5i60dv6dn397ethgpt` (`address_id`),
  KEY `FK8aol9f99s97mtyhij0tvfj41f` (`payment_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  KEY `FKdimvsocblb17f45ikjr6xn1wj` (`voucher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `delivery_fee_total`, `order_date`, `order_status`, `payment_date`, `payment_status`, `payment_total`, `sub_total`, `address_id`, `payment_id`, `user_id`, `voucher_id`) VALUES
(8, 15000, '2022-03-01', 'PENDING', NULL, 'PENDING', 95000, 95000, 2, 2, 6, NULL),
(9, 15000, '2022-03-01', 'PROCCESSING', '2022-03-01', 'COMPLETED', 95000, 95000, 2, 2, 6, NULL),
(10, 15000, '2022-03-01', 'PROCCESSING', NULL, 'COMPLETED', 95000, 80000, 2, 2, 6, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_items`
--

/* DROP TABLE IF EXISTS `order_items`;
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_sku_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FK176qrjikd9dhtee6kpxfj6tdb` (`product_sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8,COLLATE=utf8_general_ci;
 */
--
-- Đang đổ dữ liệu cho bảng `order_items`
--

INSERT INTO `order_items` (`id`, `price`, `quantity`, `order_id`, `product_sku_id`) VALUES
(10, 20000, 2, 8, 1),
(11, 40000, 1, 8, 2),
(12, 20000, 2, 9, 1),
(13, 40000, 1, 9, 2),
(14, 20000, 2, 10, 1),
(15, 40000, 1, 10, 2);

-- --------------------------------------------------------

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `addresses`
--
/* ALTER TABLE `addresses`
  ADD CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK8aol9f99s97mtyhij0tvfj41f` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`),
  ADD CONSTRAINT `FKdimvsocblb17f45ikjr6xn1wj` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`),
  ADD CONSTRAINT `FKhlglkvf5i60dv6dn397ethgpt` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);

--
-- Các ràng buộc cho bảng `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `FK176qrjikd9dhtee6kpxfj6tdb` FOREIGN KEY (`product_sku_id`) REFERENCES `product_sku` (`id`),
  ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Các ràng buộc cho bảng `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD CONSTRAINT `FK83nsrttkwkb6ym0anu051mtxn` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `product_categories`
--
ALTER TABLE `product_categories`
  ADD CONSTRAINT `FKd112rx0alycddsms029iifrih` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKlda9rad6s180ha3dl1ncsp8n7` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `product_sku`
--
ALTER TABLE `product_sku`
  ADD CONSTRAINT `FKog8pyxyvo8vmwjix4so5kvv28` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `FK2dylsfo39lgjyqml2tbe0b0ss` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `verification`
--
ALTER TABLE `verification`
  ADD CONSTRAINT `FK7ntgdvdvok1jx29t3uooau08j` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`); */
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
