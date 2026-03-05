-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 05, 2026 lúc 03:43 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `capyshop_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_don_hang`
--

CREATE TABLE `chi_tiet_don_hang` (
  `maChiTietDonHang` int(11) NOT NULL,
  `maDonHang` int(11) NOT NULL,
  `maSanPham` int(11) NOT NULL,
  `giaSanPham` bigint(20) NOT NULL,
  `soLuong` int(11) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chi_tiet_don_hang`
--

INSERT INTO `chi_tiet_don_hang` (`maChiTietDonHang`, `maDonHang`, `maSanPham`, `giaSanPham`, `soLuong`, `thoiGianTao`) VALUES
(40, 36, 46, 33990000, 1, '2026-03-04 16:53:11'),
(41, 36, 25, 62990000, 1, '2026-03-04 16:53:11'),
(42, 37, 39, 19990000, 1, '2026-03-04 17:14:18'),
(43, 38, 29, 36990000, 1, '2026-03-04 17:14:42'),
(44, 39, 36, 51990000, 1, '2026-03-04 17:15:13'),
(45, 40, 27, 7990000, 1, '2026-03-04 17:16:59'),
(46, 41, 37, 19990000, 1, '2026-03-04 17:17:34'),
(47, 41, 33, 7790000, 1, '2026-03-04 17:17:34'),
(48, 41, 30, 8590000, 1, '2026-03-04 17:17:34'),
(49, 42, 32, 28990000, 1, '2026-03-04 17:17:56'),
(50, 43, 26, 24990000, 1, '2026-03-04 17:18:32'),
(51, 44, 40, 22990000, 1, '2026-03-04 19:33:39'),
(52, 45, 38, 27990000, 1, '2026-03-04 19:33:48'),
(53, 46, 27, 7990000, 1, '2026-03-04 19:33:59'),
(54, 47, 34, 17490000, 1, '2026-03-04 19:34:09');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_don_hang_thuoc_tinh`
--

CREATE TABLE `chi_tiet_don_hang_thuoc_tinh` (
  `maChiTietDonHangThuocTinh` int(11) NOT NULL,
  `maChiTietDonHang` int(11) NOT NULL,
  `maSanPhamThuocTinh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chi_tiet_don_hang_thuoc_tinh`
--

INSERT INTO `chi_tiet_don_hang_thuoc_tinh` (`maChiTietDonHangThuocTinh`, `maChiTietDonHang`, `maSanPhamThuocTinh`) VALUES
(79, 40, 201),
(80, 40, 204),
(81, 40, 206),
(82, 41, 48),
(83, 41, 53),
(84, 41, 57),
(85, 42, 156),
(86, 42, 158),
(87, 42, 161),
(88, 43, 88),
(89, 43, 89),
(90, 43, 90),
(91, 44, 135),
(92, 44, 136),
(93, 44, 138),
(94, 44, 142),
(95, 44, 144),
(96, 45, 71),
(97, 45, 76),
(98, 45, 78),
(99, 46, 145),
(100, 46, 148),
(101, 46, 150),
(102, 47, 115),
(103, 47, 119),
(104, 47, 121),
(105, 48, 93),
(106, 48, 97),
(107, 48, 99),
(108, 49, 107),
(109, 49, 111),
(110, 49, 114),
(111, 50, 67),
(112, 50, 70),
(113, 51, 162),
(114, 51, 166),
(115, 51, 169),
(116, 52, 151),
(117, 52, 152),
(118, 52, 155),
(119, 53, 71),
(120, 53, 76),
(121, 53, 78),
(122, 54, 122),
(123, 54, 125),
(124, 54, 127);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_muc`
--

CREATE TABLE `danh_muc` (
  `maDanhMuc` int(11) NOT NULL,
  `tenDanhMuc` varchar(100) NOT NULL,
  `hinhAnhDanhMuc` varchar(255) DEFAULT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danh_muc`
--

INSERT INTO `danh_muc` (`maDanhMuc`, `tenDanhMuc`, `hinhAnhDanhMuc`, `thoiGianTao`) VALUES
(7, 'Điện thoại', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a8324907a07.jpg', '2026-03-04 13:23:21'),
(8, 'Laptop', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a832f10448c.jpg', '2026-03-04 13:26:09'),
(9, 'Chuột máy tính', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a8335ec71a7.jpg', '2026-03-04 13:27:58'),
(10, 'Bàn phím máy tính', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a833c194c1c.jpg', '2026-03-04 13:29:37'),
(11, 'Màn hình máy tính', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a8342398ca5.jpg', '2026-03-04 13:31:15'),
(12, 'Loa bluetooth', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a8347996acf.jpg', '2026-03-04 13:32:41'),
(13, 'Đồng hồ thông minh', 'http://192.168.0.103/capyshop_api/common/images/danhmuc/img_69a834c16a019.jpg', '2026-03-04 13:33:53');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_muc_thuoc_tinh`
--

CREATE TABLE `danh_muc_thuoc_tinh` (
  `maDanhMuc` int(11) NOT NULL,
  `maThuocTinh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danh_muc_thuoc_tinh`
--

INSERT INTO `danh_muc_thuoc_tinh` (`maDanhMuc`, `maThuocTinh`) VALUES
(7, 6),
(7, 7),
(7, 8),
(8, 6),
(8, 7),
(8, 8),
(8, 9),
(8, 10),
(9, 6),
(9, 11),
(10, 6),
(11, 6),
(11, 12),
(11, 13),
(11, 14),
(12, 6),
(13, 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_muc_thuong_hieu`
--

CREATE TABLE `danh_muc_thuong_hieu` (
  `maDanhMuc` int(11) NOT NULL,
  `maThuongHieu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `danh_muc_thuong_hieu`
--

INSERT INTO `danh_muc_thuong_hieu` (`maDanhMuc`, `maThuongHieu`) VALUES
(7, 7),
(7, 8),
(7, 9),
(7, 10),
(8, 7),
(8, 11),
(8, 12),
(8, 13),
(8, 14),
(9, 15),
(9, 16),
(9, 17),
(10, 15),
(10, 17),
(10, 18),
(10, 19),
(11, 8),
(11, 12),
(11, 14),
(11, 20),
(12, 8),
(12, 21),
(12, 22),
(12, 23),
(13, 7),
(13, 8),
(13, 9),
(13, 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `don_hang`
--

CREATE TABLE `don_hang` (
  `maDonHang` int(11) NOT NULL,
  `maNguoiDung` int(11) NOT NULL,
  `maPhuongThucThanhToan` int(11) DEFAULT NULL,
  `maDonViVanChuyen` int(11) DEFAULT NULL,
  `hoTenNguoiDung` varchar(100) DEFAULT NULL,
  `soDienThoai` varchar(15) DEFAULT NULL,
  `diaChi` text DEFAULT NULL,
  `tongTien` bigint(20) NOT NULL,
  `tongSoLuong` int(11) NOT NULL,
  `trangThai` enum('CHO_XAC_NHAN','CHO_LAY_HANG','DANG_GIAO_HANG','DA_GIAO_HANG','DA_HUY') DEFAULT 'CHO_XAC_NHAN',
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `don_hang`
--

INSERT INTO `don_hang` (`maDonHang`, `maNguoiDung`, `maPhuongThucThanhToan`, `maDonViVanChuyen`, `hoTenNguoiDung`, `soDienThoai`, `diaChi`, `tongTien`, `tongSoLuong`, `trangThai`, `thoiGianTao`) VALUES
(36, 6, 1, 3, 'Nguyễn Thành Long', '0385397328', '6 ngõ 72 an trai, Xã Vân Canh, Huyện Hoài Đức, Thành phố Hà Nội', 97020000, 2, 'DA_GIAO_HANG', '2024-03-04 16:53:11'),
(37, 6, 1, 2, 'Nguyễn Thành Long', '0385397328', '6 ngõ 72 an trai, Xã Vân Canh, Huyện Hoài Đức, Thành phố Hà Nội', 20010000, 1, 'DA_GIAO_HANG', '2024-03-24 17:14:18'),
(38, 6, 1, 2, 'Nguyễn Thành Long', '0385397328', '6 ngõ 72 an trai, Xã Vân Canh, Huyện Hoài Đức, Thành phố Hà Nội', 37010000, 1, 'DA_GIAO_HANG', '2024-05-08 17:14:42'),
(39, 6, 1, 1, 'Nguyễn Thành Long', '0385397328', '6 ngõ 72 an trai, Xã Vân Canh, Huyện Hoài Đức, Thành phố Hà Nội', 52020000, 1, 'DA_GIAO_HANG', '2024-08-10 17:15:13'),
(40, 8, 1, 2, 'Đào Lê Ngoc Mai', '0982057184', '6 yên đồng, Xã Yên Đồng, Huyện Ý Yên, Tỉnh Nam Định', 8010000, 1, 'DA_GIAO_HANG', '2025-10-05 17:16:59'),
(41, 8, 1, 3, 'Đào Lê Ngoc Mai', '0982057184', '6 yên đồng, Xã Yên Đồng, Huyện Ý Yên, Tỉnh Nam Định', 36410000, 3, 'DA_GIAO_HANG', '2024-08-19 17:17:34'),
(42, 8, 1, 2, 'Đào Lê Ngoc Mai', '0982057184', '6 yên đồng, Xã Yên Đồng, Huyện Ý Yên, Tỉnh Nam Định', 29010000, 1, 'DA_GIAO_HANG', '2025-04-08 17:17:56'),
(43, 8, 1, 1, 'Đào Lê Ngoc Mai', '0982057184', '6 yên đồng, Xã Yên Đồng, Huyện Ý Yên, Tỉnh Nam Định', 25020000, 1, 'DA_GIAO_HANG', '2025-07-14 17:18:32'),
(44, 9, 1, 2, 'Nguyễn Thành Đạt', '0388697256', 'thôn hữu lễ 3, Xã Thọ Xương, Huyện Thọ Xuân, Tỉnh Thanh Hóa', 23010000, 1, 'DA_GIAO_HANG', '2026-01-01 19:33:39'),
(45, 9, 1, 1, 'Nguyễn Thành Đạt', '0388697256', 'thôn hữu lễ 3, Xã Thọ Xương, Huyện Thọ Xuân, Tỉnh Thanh Hóa', 28020000, 1, 'DA_GIAO_HANG', '2026-01-22 19:33:48'),
(46, 9, 1, 3, 'Nguyễn Thành Đạt', '0388697256', 'thôn hữu lễ 3, Xã Thọ Xương, Huyện Thọ Xuân, Tỉnh Thanh Hóa', 8030000, 1, 'DA_GIAO_HANG', '2026-02-13 19:33:59'),
(47, 9, 1, 1, 'Nguyễn Thành Đạt', '0388697256', 'thôn hữu lễ 3, Xã Thọ Xương, Huyện Thọ Xuân, Tỉnh Thanh Hóa', 17520000, 1, 'DA_GIAO_HANG', '2026-03-04 19:34:09');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `don_vi_van_chuyen`
--

CREATE TABLE `don_vi_van_chuyen` (
  `maDonViVanChuyen` int(11) NOT NULL,
  `tenDonViVanChuyen` varchar(100) NOT NULL,
  `giaDonViVanChuyen` bigint(20) NOT NULL,
  `trangThai` enum('HOAT_DONG','TAM_TAT') DEFAULT 'HOAT_DONG',
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `don_vi_van_chuyen`
--

INSERT INTO `don_vi_van_chuyen` (`maDonViVanChuyen`, `tenDonViVanChuyen`, `giaDonViVanChuyen`, `trangThai`, `thoiGianTao`) VALUES
(1, 'Giao hàng nhanh', 30000, 'HOAT_DONG', '2026-01-03 02:07:16'),
(2, 'Giao hàng tiết kiệm', 20000, 'HOAT_DONG', '2026-01-03 02:07:16'),
(3, 'Viettel Post', 40000, 'HOAT_DONG', '2026-01-03 02:07:16');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `gio_hang`
--

CREATE TABLE `gio_hang` (
  `maGioHang` int(11) NOT NULL,
  `maNguoiDung` int(11) NOT NULL,
  `maSanPham` int(11) NOT NULL,
  `soLuong` int(11) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `gio_hang_thuoc_tinh`
--

CREATE TABLE `gio_hang_thuoc_tinh` (
  `maGioHangThuocTinh` int(11) NOT NULL,
  `maGioHang` int(11) NOT NULL,
  `maSanPhamThuocTinh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hinh_anh`
--

CREATE TABLE `hinh_anh` (
  `maHinhAnh` int(11) NOT NULL,
  `maSanPham` int(11) NOT NULL,
  `hinhAnh` varchar(255) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hinh_anh`
--

INSERT INTO `hinh_anh` (`maHinhAnh`, `maSanPham`, `hinhAnh`, `thoiGianTao`) VALUES
(54, 25, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a837d425a6f.jpg', '2026-03-04 14:02:50'),
(55, 25, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a837d6f3e62.jpg', '2026-03-04 14:02:50'),
(56, 25, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a837dcc605a.jpg', '2026-03-04 14:02:50'),
(57, 25, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a837e1dfe9a.jpg', '2026-03-04 14:02:50'),
(58, 25, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a837e43cd92.jpg', '2026-03-04 14:02:50'),
(64, 26, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ca762ff0.jpg', '2026-03-04 14:11:21'),
(65, 26, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ca9ceb17.jpg', '2026-03-04 14:11:21'),
(66, 26, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83cabf2aa0.jpg', '2026-03-04 14:11:21'),
(67, 26, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83cb0b0340.jpg', '2026-03-04 14:11:21'),
(68, 26, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83cb68192c.jpg', '2026-03-04 14:11:21'),
(69, 27, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ddf72141.jpg', '2026-03-04 14:16:04'),
(70, 27, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83de2ae32f.jpg', '2026-03-04 14:16:04'),
(71, 27, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83deaf26cf.jpg', '2026-03-04 14:16:04'),
(72, 28, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83efa92e7f.jpg', '2026-03-04 14:20:02'),
(73, 28, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83efdf3e4c.jpg', '2026-03-04 14:20:02'),
(74, 28, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83f004363e.jpg', '2026-03-04 14:20:02'),
(75, 28, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83f02974bb.jpg', '2026-03-04 14:20:02'),
(76, 29, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ffdf2e53.jpg', '2026-03-04 14:24:04'),
(77, 29, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a84000a842f.jpg', '2026-03-04 14:24:04'),
(78, 29, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a84004e5096.jpg', '2026-03-04 14:24:04'),
(79, 29, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8400731708.jpg', '2026-03-04 14:24:04'),
(80, 29, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a840094d85c.jpg', '2026-03-04 14:24:04'),
(81, 30, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8541f780ef.jpg', '2026-03-04 15:55:47'),
(82, 30, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a854225ad39.jpg', '2026-03-04 15:55:47'),
(83, 30, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a854251a933.jpg', '2026-03-04 15:55:47'),
(84, 31, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a856d33925f.jpg', '2026-03-04 16:02:48'),
(85, 31, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a856d588ad3.jpg', '2026-03-04 16:02:48'),
(86, 31, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a856d82482a.jpg', '2026-03-04 16:02:48'),
(87, 32, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8587fada08.jpg', '2026-03-04 16:09:16'),
(88, 32, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85882470d1.jpg', '2026-03-04 16:09:16'),
(89, 32, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8588482b9f.jpg', '2026-03-04 16:09:16'),
(90, 32, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a858868ea55.jpg', '2026-03-04 16:09:16'),
(91, 33, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a859efcaeee.jpg', '2026-03-04 16:15:25'),
(92, 33, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a859f2a8eb2.jpg', '2026-03-04 16:15:25'),
(93, 33, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a859f55187a.jpg', '2026-03-04 16:15:25'),
(94, 33, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a859f817908.jpg', '2026-03-04 16:15:25'),
(95, 34, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85adc67ca0.jpg', '2026-03-04 16:18:47'),
(96, 34, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85adeb08db.jpg', '2026-03-04 16:18:47'),
(97, 34, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85ae27a72f.jpg', '2026-03-04 16:18:47'),
(98, 34, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85ae49282c.jpg', '2026-03-04 16:18:47'),
(99, 35, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85baa82f71.jpg', '2026-03-04 16:22:22'),
(100, 35, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85bad13ad6.jpg', '2026-03-04 16:22:22'),
(101, 35, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85bb19e083.jpg', '2026-03-04 16:22:22'),
(102, 35, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85bb3bad1c.jpg', '2026-03-04 16:22:22'),
(103, 36, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85cb7e550b.jpg', '2026-03-04 16:28:35'),
(104, 36, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85cba1b963.jpg', '2026-03-04 16:28:35'),
(105, 36, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85cbc2901e.jpg', '2026-03-04 16:28:35'),
(106, 37, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85e27c6f6e.jpg', '2026-03-04 16:32:16'),
(107, 37, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85e2ae8f09.jpg', '2026-03-04 16:32:16'),
(108, 37, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85e2ce72da.jpg', '2026-03-04 16:32:16'),
(109, 37, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85e2ec22ec.jpg', '2026-03-04 16:32:16'),
(110, 38, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85ee712726.jpg', '2026-03-04 16:35:05'),
(111, 38, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85eea19c76.jpg', '2026-03-04 16:35:05'),
(112, 38, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85eebe16a7.jpg', '2026-03-04 16:35:05'),
(113, 38, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85eedcc637.jpg', '2026-03-04 16:35:05'),
(114, 39, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85f679b270.jpg', '2026-03-04 16:36:59'),
(115, 39, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85f69a3efb.jpg', '2026-03-04 16:36:59'),
(116, 40, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85fd8056a3.jpg', '2026-03-04 16:39:02'),
(117, 40, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85fda6f96a.jpg', '2026-03-04 16:39:02'),
(118, 40, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85fdc350c0.jpg', '2026-03-04 16:39:02'),
(119, 41, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8607523dcd.jpg', '2026-03-04 16:41:56'),
(120, 41, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8607863f21.jpg', '2026-03-04 16:41:56'),
(121, 41, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8607a96cff.jpg', '2026-03-04 16:41:56'),
(122, 41, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8607d12c25.jpg', '2026-03-04 16:41:56'),
(123, 42, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8611d4a2f8.jpg', '2026-03-04 16:44:18'),
(124, 42, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8611f88e62.jpg', '2026-03-04 16:44:18'),
(125, 42, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a861220462e.jpg', '2026-03-04 16:44:18'),
(126, 42, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a86123c06d9.jpg', '2026-03-04 16:44:18'),
(131, 44, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a861c0b60bd.jpg', '2026-03-04 16:47:09'),
(132, 44, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a861c3769da.jpg', '2026-03-04 16:47:09'),
(133, 44, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a861c5bfbb0.jpg', '2026-03-04 16:47:09'),
(134, 44, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a861ca2705f.jpg', '2026-03-04 16:47:09'),
(135, 45, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8623f4e139.jpg', '2026-03-04 16:50:00'),
(136, 45, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8624256070.jpg', '2026-03-04 16:50:00'),
(137, 45, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8624746037.jpg', '2026-03-04 16:50:00'),
(138, 46, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a862d7af1d0.jpg', '2026-03-04 16:51:37'),
(139, 46, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a862db2877c.jpg', '2026-03-04 16:51:37'),
(140, 46, 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a862dce2e5c.jpg', '2026-03-04 16:51:37');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoi_dung`
--

CREATE TABLE `nguoi_dung` (
  `maNguoiDung` int(11) NOT NULL,
  `hoTenNguoiDung` varchar(100) NOT NULL,
  `hinhAnhNguoiDung` varchar(255) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `soDienThoai` varchar(15) DEFAULT NULL,
  `diaChi` text DEFAULT NULL,
  `matKhau` varchar(255) NOT NULL,
  `vaiTro` enum('NGUOI_MUA','ADMIN') DEFAULT 'NGUOI_MUA',
  `trangThai` enum('HOAT_DONG','KHOA') DEFAULT 'HOAT_DONG',
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp(),
  `uId` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `reset_token` varchar(64) DEFAULT NULL,
  `token_expiry` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoi_dung`
--

INSERT INTO `nguoi_dung` (`maNguoiDung`, `hoTenNguoiDung`, `hinhAnhNguoiDung`, `email`, `soDienThoai`, `diaChi`, `matKhau`, `vaiTro`, `trangThai`, `thoiGianTao`, `uId`, `token`, `reset_token`, `token_expiry`) VALUES
(6, 'Nguyễn Thành Long', 'http://192.168.0.102/capyshop_api/common/images/nguoidung/img_698818f0859e0.jpg', 'nguyen.thanh.long.15052000@gmail.com', '0385397328', '6 ngõ 72 an trai, Xã Vân Canh, Huyện Hoài Đức, Thành phố Hà Nội', '$2y$10$wulvYMCDKntz0W5NnoTyme7PH0RrQpPLNAZqV4K8qNTMVEfsL29cy', 'NGUOI_MUA', 'HOAT_DONG', '2026-02-08 02:36:14', '3GpMR41Lpkdjx0Ia4cwwKZl26jx1', '', NULL, NULL),
(7, 'Admin Developer', 'http://192.168.0.102/capyshop_api/common/images/nguoidung/img_6989011cab302.jpg', 'nguyenthanhlong.developer@gmail.com', '0385397328', '', '$2y$10$Nb4/0q7HJN0yuBwgvqDj4OeAZs9Xxl4vIekJzWKyg5UcSAtb2aFdm', 'ADMIN', 'HOAT_DONG', '2026-02-08 21:32:52', 'DuYqm43DluXcRjeNgsXf8QA0FgD2', 'cgmkQW0nRfqob1T-p2oZkC:APA91bFFIyiPKj-6tLkGIy9EK7dTdzjbnS2AyVLxLRf0zUN5rTh_SXyyExqUp37ThuJXWBb6_ujDNlMFoesUk52J9FoYKaBYMuPEazzZ67YtkM04IRFB5CE', NULL, NULL),
(8, 'Đào Lê Ngoc Mai', 'http://192.168.0.102/capyshop_api/common/images/nguoidung/img_69898bd330f1a.jpeg', 'dao.le.ngoc.mai.18092004@gmail.com', '0982057184', '6 yên đồng, Xã Yên Đồng, Huyện Ý Yên, Tỉnh Nam Định', '$2y$10$edt2pCUPCSLdNkf3Xi7BIOXKnPMuhjqL99CKZJWHalO.aDjZEDRkC', 'NGUOI_MUA', 'HOAT_DONG', '2026-02-09 07:24:24', '5oyabVNNeHQc5U5xIEbQ2k0Nsdz1', '', NULL, NULL),
(9, 'Nguyễn Thành Đạt', 'http://192.168.0.103/capyshop_api/common/images/nguoidung/img_69a888a496d6e.jpg', 'nguyen.thanh.dat.20122002@gmail.com', '0388697256', 'thôn hữu lễ 3, Xã Thọ Xương, Huyện Thọ Xuân, Tỉnh Thanh Hóa', '$2y$10$r8.i08YCTbIb4TzhIs7C3u9JYleLIfhFQoyd1qe3EMYMFssCreOry', 'NGUOI_MUA', 'HOAT_DONG', '2026-03-04 19:28:20', '5JdQaA4Nl3RqwqF6Yz9SjyGYrte2', '', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phuong_thuc_thanh_toan`
--

CREATE TABLE `phuong_thuc_thanh_toan` (
  `maPhuongThucThanhToan` int(11) NOT NULL,
  `tenPhuongThucThanhToan` varchar(100) NOT NULL,
  `trangThai` enum('HOAT_DONG','TAM_TAT') DEFAULT 'HOAT_DONG',
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `phuong_thuc_thanh_toan`
--

INSERT INTO `phuong_thuc_thanh_toan` (`maPhuongThucThanhToan`, `tenPhuongThucThanhToan`, `trangThai`, `thoiGianTao`) VALUES
(1, 'Thanh toán khi nhận hàng', 'HOAT_DONG', '2026-01-03 02:07:02'),
(2, 'Chuyển khoản ngân hàng', 'HOAT_DONG', '2026-01-03 02:07:02');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quang_cao`
--

CREATE TABLE `quang_cao` (
  `maQuangCao` int(11) NOT NULL,
  `hinhAnhQuangCao` varchar(255) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `quang_cao`
--

INSERT INTO `quang_cao` (`maQuangCao`, `hinhAnhQuangCao`, `thoiGianTao`) VALUES
(2, 'https://genk.mediacdn.vn/thumb_w/640/Images/Uploaded/Share/2011/02/28/p0228.jpg', '2026-01-03 02:14:02'),
(5, 'https://kimlongcenter.com/upload/image/TOP%20laptop%20dell.png', '2026-01-03 02:16:07'),
(6, 'https://genk.mediacdn.vn/2019/4/23/photo-1-15560365277871628784452.jpg', '2026-01-03 02:16:07');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `san_pham`
--

CREATE TABLE `san_pham` (
  `maSanPham` int(11) NOT NULL,
  `maThuongHieu` int(11) DEFAULT NULL,
  `maDanhMuc` int(11) DEFAULT NULL,
  `tenSanPham` varchar(200) NOT NULL,
  `hinhAnhSanPham` varchar(255) DEFAULT NULL,
  `moTaSanPham` text DEFAULT NULL,
  `giaSanPham` bigint(20) NOT NULL,
  `soLuongTon` int(11) DEFAULT 0,
  `trangThai` enum('DANG_BAN','HET_HANG','AN') DEFAULT 'DANG_BAN',
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `san_pham`
--

INSERT INTO `san_pham` (`maSanPham`, `maThuongHieu`, `maDanhMuc`, `tenSanPham`, `hinhAnhSanPham`, `moTaSanPham`, `giaSanPham`, `soLuongTon`, `trangThai`, `thoiGianTao`) VALUES
(25, 7, 7, 'Iphone 17 pro', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a837d425a6f.jpg', 'Iphone 17 pro chính là phiên bản điện thoại mới nhất trong dòng IPHONE và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, iphone 17 pro tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 62990000, 49, 'DANG_BAN', '2026-03-04 13:50:49'),
(26, 7, 7, 'Iphone 15', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ca762ff0.jpg', 'Iphone 15 chính là phiên bản điện thoại mới nhất trong dòng IPHONE và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, iphone 15 tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 24990000, 74, 'DANG_BAN', '2026-03-04 14:09:44'),
(27, 8, 7, 'Xiaomi redmi note 14 pro', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ddf72141.jpg', 'Redmi note 14 pro chính là phiên bản điện thoại mới nhất trong dòng Redmi và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, redmi note 14 pro tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 7990000, 38, 'DANG_BAN', '2026-03-04 14:16:04'),
(28, 8, 7, 'Xiaomi 15t pro', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83efa92e7f.jpg', 'Xiaomi 15t pro chính là phiên bản điện thoại mới nhất trong dòng xiaomi và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, xiaomi 15t pro tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 19490000, 100, 'DANG_BAN', '2026-03-04 14:20:02'),
(29, 8, 7, 'Xiaomi 17 ultra', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a83ffdf2e53.jpg', 'Xiaomi 17 ultra chính là phiên bản điện thoại mới nhất trong dòng XIAOMI và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, xiaomi 17 ultra tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 36990000, 54, 'DANG_BAN', '2026-03-04 14:24:04'),
(30, 9, 7, 'Samsung galaxy a56', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8541f780ef.jpg', 'Samsung galaxy a56 chính là phiên bản điện thoại mới nhất trong dòng GALAXY và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, samsung galaxy a56 tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 8590000, 29, 'DANG_BAN', '2026-03-04 15:55:47'),
(31, 9, 7, 'Samsung galaxy a36 5G', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a856d33925f.jpg', 'Samsung galaxy a36 5G chính là phiên bản điện thoại mới nhất trong dòng GALAXY và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, SAMSSUNG GALAXY A36 tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 5199000, 40, 'DANG_BAN', '2026-03-04 16:02:48'),
(32, 9, 7, 'Samsung galaxy s25 ultra', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8587fada08.jpg', 'Samsung galaxy s25 ultra chính là phiên bản điện thoại mới nhất trong dòng GALAXY và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, samsung galaxy s25 ultra tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 28990000, 99, 'DANG_BAN', '2026-03-04 16:09:16'),
(33, 10, 7, 'Oppo reno 10 pro', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a859efcaeee.jpg', 'Oppo reno 10 pro chính là phiên bản điện thoại mới nhất trong dòng RENO và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, oppo reno 10 pro tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 7790000, 34, 'DANG_BAN', '2026-03-04 16:15:25'),
(34, 10, 7, 'Oppo find x8', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85adc67ca0.jpg', 'Oppo find x8 chính là phiên bản điện thoại mới nhất trong dòng FIND và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, oppo find x8 tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 17490000, 49, 'DANG_BAN', '2026-03-04 16:18:47'),
(35, 10, 7, 'Oppo reno 15f', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85baa82f71.jpg', 'Oppo reno 15f chính là phiên bản điện thoại mới nhất trong dòng RENO và rất được mong đợi. Đây là chiếc điện thoại thông minh có hỗ trợ 5G với những thông số gây ấn tượng mạnh. Cụ thể, oppo reno 15f tạo được điểm chú ý đầu tiên từ ngay trong thiết kế của sản phẩm. Những tính năng tuyệt vời được trang bị trên chiếc điện thoại này bao gồm bộ xử lý mạnh mẽ, màn hình lớn sống động và hệ thống 3 camera với độ phân giải rất đáng để mong đợi.\n\nThông số kỹ thuật\n\nKích thước màn hình	\n6.74 inches\n\nCông nghệ màn hình	\nAMOLED\n\nCamera sau	\n\nCamera góc rộng: 50 MP\n\nKính tiềm vọng: 64 MP, PDAF, 5x optical zoom\n\nCamera góc siêu rộng: 8 MP, f/2.2\n\nCamera trước	:	Camera góc rộng: 32 MP, f/2.4\n\nChipset	:	Snapdragon 8 plus Gen 1 8 nhân\n\nCông nghệ NFC	:	Có\n\nDung lượng RAM	: 12 GB\n\nBộ nhớ trong	:	256 GB\n\nPin	:	4700 mAh\n\nThẻ SIM	:	2 SIM (Nano-SIM)\n\nHệ điều hành	:	Android 13\n\nĐộ phân giải màn hình	:	1240 x 2772 pixels\n\nTính năng màn hình	:	1 tỷ màu, tần số quét 120Hz, HDR10+, 1100 nits, Kính cường lực AGC DT-Star2\n\nLoại CPU	:	1x3.0 GHz Cortex-X2 & 3x2.75 GHz Cortex-A710 & 4x2.0 GHz Cortex-A510', 11990000, 40, 'DANG_BAN', '2026-03-04 16:22:22'),
(36, 11, 8, 'Lenovo gaming legion 5 pro', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85cb7e550b.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 51990000, 54, 'DANG_BAN', '2026-03-04 16:28:35'),
(37, 11, 8, 'Lenovo ideapad slim 5', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85e27c6f6e.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 19990000, 29, 'DANG_BAN', '2026-03-04 16:32:16'),
(38, 12, 8, 'Asus gaming v16', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85ee712726.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 27990000, 44, 'DANG_BAN', '2026-03-04 16:35:05'),
(39, 12, 8, 'Asus vivobook 15', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85f679b270.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 19990000, 69, 'DANG_BAN', '2026-03-04 16:36:59'),
(40, 13, 8, 'Hp pavilion 15', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a85fd8056a3.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 22990000, 24, 'DANG_BAN', '2026-03-04 16:39:02'),
(41, 13, 8, 'Hp elitebook 840 g8', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8607523dcd.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 31990000, 60, 'DANG_BAN', '2026-03-04 16:41:56'),
(42, 14, 8, 'Msi gf63', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8611d4a2f8.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 38990000, 45, 'DANG_BAN', '2026-03-04 16:44:18'),
(44, 14, 8, 'Msi modern 15', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a861c0b60bd.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 39990000, 80, 'DANG_BAN', '2026-03-04 16:47:09'),
(45, 7, 8, 'Macbook air m4', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a8623f4e139.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 55990000, 20, 'DANG_BAN', '2026-03-04 16:50:00'),
(46, 7, 8, 'Macbook air m2', 'http://192.168.0.103/capyshop_api/common/images/sanpham/img_69a862d7af1d0.jpg', 'Macbook Air M2 2022 8GB 256GB - Cũ Đẹp với con chip Apple M2 mang tới hiệu năng CPU cao hơn tới 18% và GPU cao hơn 35% so với phiên bản chip M1. Nhờ đó mà mẫu Macbook Air cũ này còn có được khả năng xử lý các tác vụ năng như lập trình, chỉnh sửa hình ảnh, video 4K,...Bộ nhớ 256GB sẽ đáp ứng tốt nhu cầu lưu trữ, sử dụng cơ bản của người dùng.\n\nLoại card đồ họa	:	8 nhân GPU, 16 nhân Neural Engine\n\nDung lượng RAM	:	8GB\n\nỔ cứng	:	256GB\n\nKích thước màn hình	:	13.6 inches\n\nCông nghệ màn hình	:	Liquid Retina Display\n\nPin	:	52,6 Wh\n\nHệ điều hành	:	MacOS\n\nĐộ phân giải màn hình	:	2560 x 1664 pixels\n\nLoại CPU	:	Apple M2\n\nCổng giao tiếp	:	2 x Thunderbolt 3\nJack tai nghe 3.5 mm	:	MagSafe 34', 33990000, 29, 'DANG_BAN', '2026-03-04 16:51:37');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `san_pham_thuoc_tinh`
--

CREATE TABLE `san_pham_thuoc_tinh` (
  `maSanPhamThuocTinh` int(11) NOT NULL,
  `maSanPham` int(11) NOT NULL,
  `maThuocTinh` int(11) NOT NULL,
  `giaTri` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `san_pham_thuoc_tinh`
--

INSERT INTO `san_pham_thuoc_tinh` (`maSanPhamThuocTinh`, `maSanPham`, `maThuocTinh`, `giaTri`) VALUES
(48, 25, 6, 'cam'),
(49, 25, 6, 'trắng'),
(50, 25, 6, 'đen'),
(51, 25, 7, '8GB'),
(52, 25, 7, '12GB'),
(53, 25, 7, '16GB'),
(54, 25, 8, '128GB'),
(55, 25, 8, '256GB'),
(56, 25, 8, '512GB'),
(57, 25, 8, '1TB'),
(58, 25, 8, '2TB'),
(65, 26, 6, 'đen'),
(66, 26, 6, 'trắng'),
(67, 26, 6, 'xanh'),
(68, 26, 8, '256GB'),
(69, 26, 8, '512GB'),
(70, 26, 8, '1TB'),
(71, 27, 6, 'vàng'),
(72, 27, 6, 'tím'),
(73, 27, 6, 'đen'),
(74, 27, 6, 'xanh dương'),
(75, 27, 7, '6GB'),
(76, 27, 7, '8GB'),
(77, 27, 8, '128GB'),
(78, 27, 8, '256GB'),
(79, 28, 6, 'vàng'),
(80, 28, 6, 'xám'),
(81, 28, 7, '8GB'),
(82, 28, 7, '12GB'),
(83, 28, 8, '128GB'),
(84, 28, 8, '256GB'),
(85, 28, 8, '512GB'),
(86, 29, 6, 'xanh lá'),
(87, 29, 6, 'trắng'),
(88, 29, 6, 'đen'),
(89, 29, 7, '16GB'),
(90, 29, 8, '512GB'),
(91, 29, 8, '1TB'),
(92, 30, 6, 'hồng'),
(93, 30, 6, 'xám'),
(94, 30, 6, 'đen'),
(95, 30, 6, 'xanh'),
(96, 30, 7, '4GB'),
(97, 30, 7, '8GB'),
(98, 30, 8, '64GB'),
(99, 30, 8, '128GB'),
(100, 31, 6, 'xanh lá'),
(101, 31, 6, 'tím'),
(102, 31, 6, 'đen'),
(103, 31, 7, '8GB'),
(104, 31, 7, '12GB'),
(105, 31, 8, '128GB'),
(106, 31, 8, '256GB'),
(107, 32, 6, 'trắng'),
(108, 32, 6, 'xanh dương'),
(109, 32, 6, 'đen'),
(110, 32, 6, 'xám'),
(111, 32, 7, '12GB'),
(112, 32, 8, '256GB'),
(113, 32, 8, '512GB'),
(114, 32, 8, '1TB'),
(115, 33, 6, 'xanh ngọc'),
(116, 33, 6, 'xám'),
(117, 33, 6, 'tím'),
(118, 33, 7, '4GB'),
(119, 33, 7, '8GB'),
(120, 33, 8, '126GB'),
(121, 33, 8, '256GB'),
(122, 34, 6, 'đen'),
(123, 34, 6, 'xanh'),
(124, 34, 6, 'hồng'),
(125, 34, 7, '16GB'),
(126, 34, 8, '256GB'),
(127, 34, 8, '512GB'),
(128, 35, 6, 'hồng'),
(129, 35, 6, 'xanh sương'),
(130, 35, 6, 'xanh nhạt'),
(131, 35, 7, '8GB'),
(132, 35, 7, '12GB'),
(133, 35, 8, '256GB'),
(134, 35, 8, '512GB'),
(135, 36, 6, 'xám'),
(136, 36, 7, '16GB'),
(137, 36, 7, '32GB'),
(138, 36, 8, '512GB'),
(139, 36, 8, '1TB'),
(140, 36, 9, 'U9-572HX'),
(141, 36, 9, 'I9-14900HX'),
(142, 36, 9, 'I7-14650HX'),
(143, 36, 10, 'RTX4060'),
(144, 36, 10, 'RTX5080'),
(145, 37, 6, 'bạc'),
(146, 37, 6, 'xám'),
(147, 37, 7, '8GB'),
(148, 37, 7, '16GB'),
(149, 37, 8, '512GB'),
(150, 37, 8, '1TB'),
(151, 38, 6, 'đen'),
(152, 38, 7, '16GB'),
(153, 38, 7, '32GB'),
(154, 38, 8, '512GB'),
(155, 38, 8, '1TB'),
(156, 39, 6, 'xám'),
(157, 39, 6, 'trắng'),
(158, 39, 7, '8GB'),
(159, 39, 7, '16GB'),
(160, 39, 8, '512GB'),
(161, 39, 8, '1TB'),
(162, 40, 6, 'bạc'),
(163, 40, 6, 'xám'),
(164, 40, 6, 'đen'),
(165, 40, 7, '8GB'),
(166, 40, 7, '16GB'),
(167, 40, 7, '32GB'),
(168, 40, 8, '512GB'),
(169, 40, 8, '1TB'),
(170, 41, 6, 'bạc'),
(171, 41, 6, 'trắng'),
(172, 41, 6, 'xám'),
(173, 41, 7, '16GB'),
(174, 41, 7, '32GB'),
(175, 41, 8, '512GB'),
(176, 41, 8, '1TB'),
(177, 41, 8, '2TB'),
(178, 42, 6, 'đen'),
(179, 42, 6, 'xám'),
(180, 42, 7, '16GB'),
(181, 42, 7, '32GB'),
(182, 42, 7, '64GB'),
(183, 42, 8, '512GB'),
(184, 42, 8, '1TB'),
(185, 42, 8, '2TB'),
(194, 44, 6, 'đen'),
(195, 44, 7, '32GB'),
(196, 44, 7, '64GB'),
(197, 44, 8, '1TB'),
(198, 45, 6, 'bạc'),
(199, 45, 7, '16GB'),
(200, 45, 8, '256GB'),
(201, 46, 6, 'xanh'),
(202, 46, 6, 'bạc'),
(203, 46, 7, '8GB'),
(204, 46, 7, '16GB'),
(205, 46, 8, '256GB'),
(206, 46, 8, '512GB');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuoc_tinh`
--

CREATE TABLE `thuoc_tinh` (
  `maThuocTinh` int(11) NOT NULL,
  `tenThuocTinh` varchar(100) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thuoc_tinh`
--

INSERT INTO `thuoc_tinh` (`maThuocTinh`, `tenThuocTinh`, `thoiGianTao`) VALUES
(6, 'màu sắc', '2026-03-04 13:23:21'),
(7, 'ram', '2026-03-04 13:23:21'),
(8, 'bộ nhớ', '2026-03-04 13:23:21'),
(9, 'cpu', '2026-03-04 13:26:09'),
(10, 'card đồ họa', '2026-03-04 13:26:09'),
(11, 'dpi', '2026-03-04 13:27:58'),
(12, 'kích thước màn hình', '2026-03-04 13:31:15'),
(13, 'độ phân giải màn hình', '2026-03-04 13:31:15'),
(14, 'tần số quét', '2026-03-04 13:31:15');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuong_hieu`
--

CREATE TABLE `thuong_hieu` (
  `maThuongHieu` int(11) NOT NULL,
  `tenThuongHieu` varchar(100) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thuong_hieu`
--

INSERT INTO `thuong_hieu` (`maThuongHieu`, `tenThuongHieu`, `thoiGianTao`) VALUES
(7, 'apple', '2026-03-04 13:23:21'),
(8, 'xiaomi', '2026-03-04 13:23:21'),
(9, 'samsung', '2026-03-04 13:23:21'),
(10, 'oppo', '2026-03-04 13:23:21'),
(11, 'lenovo', '2026-03-04 13:26:09'),
(12, 'asus', '2026-03-04 13:26:09'),
(13, 'hp', '2026-03-04 13:26:09'),
(14, 'msi', '2026-03-04 13:26:09'),
(15, 'aula', '2026-03-04 13:27:58'),
(16, 'dareu', '2026-03-04 13:27:58'),
(17, 'logitech', '2026-03-04 13:27:58'),
(18, 'akko', '2026-03-04 13:29:37'),
(19, 'razer', '2026-03-04 13:29:37'),
(20, 'lg', '2026-03-04 13:31:15'),
(21, 'boser', '2026-03-04 13:32:41'),
(22, 'harman kardon', '2026-03-04 13:32:41'),
(23, 'jbl', '2026-03-04 13:32:41');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chi_tiet_don_hang`
--
ALTER TABLE `chi_tiet_don_hang`
  ADD PRIMARY KEY (`maChiTietDonHang`),
  ADD KEY `maDonHang` (`maDonHang`),
  ADD KEY `maSanPham` (`maSanPham`);

--
-- Chỉ mục cho bảng `chi_tiet_don_hang_thuoc_tinh`
--
ALTER TABLE `chi_tiet_don_hang_thuoc_tinh`
  ADD PRIMARY KEY (`maChiTietDonHangThuocTinh`),
  ADD KEY `maChiTietDonHang` (`maChiTietDonHang`),
  ADD KEY `maSanPhamThuocTinh` (`maSanPhamThuocTinh`);

--
-- Chỉ mục cho bảng `danh_muc`
--
ALTER TABLE `danh_muc`
  ADD PRIMARY KEY (`maDanhMuc`);

--
-- Chỉ mục cho bảng `danh_muc_thuoc_tinh`
--
ALTER TABLE `danh_muc_thuoc_tinh`
  ADD PRIMARY KEY (`maDanhMuc`,`maThuocTinh`),
  ADD KEY `maThuocTinh` (`maThuocTinh`);

--
-- Chỉ mục cho bảng `danh_muc_thuong_hieu`
--
ALTER TABLE `danh_muc_thuong_hieu`
  ADD PRIMARY KEY (`maDanhMuc`,`maThuongHieu`),
  ADD KEY `maThuongHieu` (`maThuongHieu`);

--
-- Chỉ mục cho bảng `don_hang`
--
ALTER TABLE `don_hang`
  ADD PRIMARY KEY (`maDonHang`),
  ADD KEY `maNguoiDung` (`maNguoiDung`),
  ADD KEY `maPhuongThucThanhToan` (`maPhuongThucThanhToan`),
  ADD KEY `maDonViVanChuyen` (`maDonViVanChuyen`);

--
-- Chỉ mục cho bảng `don_vi_van_chuyen`
--
ALTER TABLE `don_vi_van_chuyen`
  ADD PRIMARY KEY (`maDonViVanChuyen`);

--
-- Chỉ mục cho bảng `gio_hang`
--
ALTER TABLE `gio_hang`
  ADD PRIMARY KEY (`maGioHang`),
  ADD KEY `gio_hang_ibfk_2` (`maSanPham`),
  ADD KEY `gio_hang_ibfk_1` (`maNguoiDung`);

--
-- Chỉ mục cho bảng `gio_hang_thuoc_tinh`
--
ALTER TABLE `gio_hang_thuoc_tinh`
  ADD PRIMARY KEY (`maGioHangThuocTinh`),
  ADD KEY `maGioHang` (`maGioHang`),
  ADD KEY `maSanPhamThuocTinh` (`maSanPhamThuocTinh`);

--
-- Chỉ mục cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  ADD PRIMARY KEY (`maHinhAnh`),
  ADD KEY `maSanPham` (`maSanPham`);

--
-- Chỉ mục cho bảng `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  ADD PRIMARY KEY (`maNguoiDung`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Chỉ mục cho bảng `phuong_thuc_thanh_toan`
--
ALTER TABLE `phuong_thuc_thanh_toan`
  ADD PRIMARY KEY (`maPhuongThucThanhToan`);

--
-- Chỉ mục cho bảng `quang_cao`
--
ALTER TABLE `quang_cao`
  ADD PRIMARY KEY (`maQuangCao`);

--
-- Chỉ mục cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  ADD PRIMARY KEY (`maSanPham`),
  ADD KEY `san_pham_ibfk_1` (`maDanhMuc`),
  ADD KEY `san_pham_ibfk_2` (`maThuongHieu`);

--
-- Chỉ mục cho bảng `san_pham_thuoc_tinh`
--
ALTER TABLE `san_pham_thuoc_tinh`
  ADD PRIMARY KEY (`maSanPhamThuocTinh`),
  ADD KEY `maThuocTinh` (`maThuocTinh`),
  ADD KEY `san_pham_thuoc_tinh_ibfk_1` (`maSanPham`);

--
-- Chỉ mục cho bảng `thuoc_tinh`
--
ALTER TABLE `thuoc_tinh`
  ADD PRIMARY KEY (`maThuocTinh`);

--
-- Chỉ mục cho bảng `thuong_hieu`
--
ALTER TABLE `thuong_hieu`
  ADD PRIMARY KEY (`maThuongHieu`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chi_tiet_don_hang`
--
ALTER TABLE `chi_tiet_don_hang`
  MODIFY `maChiTietDonHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT cho bảng `chi_tiet_don_hang_thuoc_tinh`
--
ALTER TABLE `chi_tiet_don_hang_thuoc_tinh`
  MODIFY `maChiTietDonHangThuocTinh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- AUTO_INCREMENT cho bảng `danh_muc`
--
ALTER TABLE `danh_muc`
  MODIFY `maDanhMuc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `don_hang`
--
ALTER TABLE `don_hang`
  MODIFY `maDonHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT cho bảng `don_vi_van_chuyen`
--
ALTER TABLE `don_vi_van_chuyen`
  MODIFY `maDonViVanChuyen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `gio_hang`
--
ALTER TABLE `gio_hang`
  MODIFY `maGioHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT cho bảng `gio_hang_thuoc_tinh`
--
ALTER TABLE `gio_hang_thuoc_tinh`
  MODIFY `maGioHangThuocTinh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  MODIFY `maHinhAnh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT cho bảng `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `maNguoiDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `phuong_thuc_thanh_toan`
--
ALTER TABLE `phuong_thuc_thanh_toan`
  MODIFY `maPhuongThucThanhToan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `quang_cao`
--
ALTER TABLE `quang_cao`
  MODIFY `maQuangCao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  MODIFY `maSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT cho bảng `san_pham_thuoc_tinh`
--
ALTER TABLE `san_pham_thuoc_tinh`
  MODIFY `maSanPhamThuocTinh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=207;

--
-- AUTO_INCREMENT cho bảng `thuoc_tinh`
--
ALTER TABLE `thuoc_tinh`
  MODIFY `maThuocTinh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `thuong_hieu`
--
ALTER TABLE `thuong_hieu`
  MODIFY `maThuongHieu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chi_tiet_don_hang`
--
ALTER TABLE `chi_tiet_don_hang`
  ADD CONSTRAINT `chi_tiet_don_hang_ibfk_1` FOREIGN KEY (`maDonHang`) REFERENCES `don_hang` (`maDonHang`),
  ADD CONSTRAINT `chi_tiet_don_hang_ibfk_2` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`);

--
-- Các ràng buộc cho bảng `chi_tiet_don_hang_thuoc_tinh`
--
ALTER TABLE `chi_tiet_don_hang_thuoc_tinh`
  ADD CONSTRAINT `chi_tiet_don_hang_thuoc_tinh_ibfk_1` FOREIGN KEY (`maChiTietDonHang`) REFERENCES `chi_tiet_don_hang` (`maChiTietDonHang`) ON DELETE CASCADE,
  ADD CONSTRAINT `chi_tiet_don_hang_thuoc_tinh_ibfk_2` FOREIGN KEY (`maSanPhamThuocTinh`) REFERENCES `san_pham_thuoc_tinh` (`maSanPhamThuocTinh`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `danh_muc_thuoc_tinh`
--
ALTER TABLE `danh_muc_thuoc_tinh`
  ADD CONSTRAINT `danh_muc_thuoc_tinh_ibfk_1` FOREIGN KEY (`maDanhMuc`) REFERENCES `danh_muc` (`maDanhMuc`),
  ADD CONSTRAINT `danh_muc_thuoc_tinh_ibfk_2` FOREIGN KEY (`maThuocTinh`) REFERENCES `thuoc_tinh` (`maThuocTinh`);

--
-- Các ràng buộc cho bảng `danh_muc_thuong_hieu`
--
ALTER TABLE `danh_muc_thuong_hieu`
  ADD CONSTRAINT `danh_muc_thuong_hieu_ibfk_1` FOREIGN KEY (`maDanhMuc`) REFERENCES `danh_muc` (`maDanhMuc`) ON DELETE CASCADE,
  ADD CONSTRAINT `danh_muc_thuong_hieu_ibfk_2` FOREIGN KEY (`maThuongHieu`) REFERENCES `thuong_hieu` (`maThuongHieu`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `don_hang`
--
ALTER TABLE `don_hang`
  ADD CONSTRAINT `don_hang_ibfk_1` FOREIGN KEY (`maNguoiDung`) REFERENCES `nguoi_dung` (`maNguoiDung`),
  ADD CONSTRAINT `don_hang_ibfk_2` FOREIGN KEY (`maPhuongThucThanhToan`) REFERENCES `phuong_thuc_thanh_toan` (`maPhuongThucThanhToan`),
  ADD CONSTRAINT `don_hang_ibfk_3` FOREIGN KEY (`maDonViVanChuyen`) REFERENCES `don_vi_van_chuyen` (`maDonViVanChuyen`);

--
-- Các ràng buộc cho bảng `gio_hang`
--
ALTER TABLE `gio_hang`
  ADD CONSTRAINT `gio_hang_ibfk_1` FOREIGN KEY (`maNguoiDung`) REFERENCES `nguoi_dung` (`maNguoiDung`),
  ADD CONSTRAINT `gio_hang_ibfk_2` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `gio_hang_thuoc_tinh`
--
ALTER TABLE `gio_hang_thuoc_tinh`
  ADD CONSTRAINT `gio_hang_thuoc_tinh_ibfk_1` FOREIGN KEY (`maGioHang`) REFERENCES `gio_hang` (`maGioHang`) ON DELETE CASCADE,
  ADD CONSTRAINT `gio_hang_thuoc_tinh_ibfk_2` FOREIGN KEY (`maSanPhamThuocTinh`) REFERENCES `san_pham_thuoc_tinh` (`maSanPhamThuocTinh`);

--
-- Các ràng buộc cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  ADD CONSTRAINT `hinh_anh_ibfk_1` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  ADD CONSTRAINT `san_pham_ibfk_1` FOREIGN KEY (`maDanhMuc`) REFERENCES `danh_muc` (`maDanhMuc`) ON DELETE SET NULL,
  ADD CONSTRAINT `san_pham_ibfk_2` FOREIGN KEY (`maThuongHieu`) REFERENCES `thuong_hieu` (`maThuongHieu`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `san_pham_thuoc_tinh`
--
ALTER TABLE `san_pham_thuoc_tinh`
  ADD CONSTRAINT `san_pham_thuoc_tinh_ibfk_1` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`) ON DELETE CASCADE,
  ADD CONSTRAINT `san_pham_thuoc_tinh_ibfk_2` FOREIGN KEY (`maThuocTinh`) REFERENCES `thuoc_tinh` (`maThuocTinh`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
