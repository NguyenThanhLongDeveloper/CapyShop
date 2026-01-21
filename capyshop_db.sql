-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 03, 2026 lúc 02:59 AM
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

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_don_hang_thuoc_tinh`
--

CREATE TABLE `chi_tiet_don_hang_thuoc_tinh` (
  `maChiTietDonHangThuocTinh` int(11) NOT NULL,
  `maChiTietDonHang` int(11) NOT NULL,
  `maSanPhamThuocTinh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_muc_thuoc_tinh`
--

CREATE TABLE `danh_muc_thuoc_tinh` (
  `maDanhMuc` int(11) NOT NULL,
  `maThuocTinh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_muc_thuong_hieu`
--

CREATE TABLE `danh_muc_thuong_hieu` (
  `maDanhMuc` int(11) NOT NULL,
  `maThuongHieu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `trangThai` enum('CHO_XAC_NHAN','DA_XAC_NHAN','DANG_GIAO','DA_GIAO','DA_HUY') DEFAULT 'CHO_XAC_NHAN',
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  `reset_token` varchar(64) DEFAULT NULL,
  `token_expiry` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quang_cao`
--

CREATE TABLE `quang_cao` (
  `maQuangCao` int(11) NOT NULL,
  `hinhAnhQuangCao` varchar(255) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuoc_tinh`
--

CREATE TABLE `thuoc_tinh` (
  `maThuocTinh` int(11) NOT NULL,
  `tenThuocTinh` varchar(100) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  MODIFY `maChiTietDonHang` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `chi_tiet_don_hang_thuoc_tinh`
--
ALTER TABLE `chi_tiet_don_hang_thuoc_tinh`
  MODIFY `maChiTietDonHangThuocTinh` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `danh_muc`
--
ALTER TABLE `danh_muc`
  MODIFY `maDanhMuc` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `don_hang`
--
ALTER TABLE `don_hang`
  MODIFY `maDonHang` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `don_vi_van_chuyen`
--
ALTER TABLE `don_vi_van_chuyen`
  MODIFY `maDonViVanChuyen` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `gio_hang`
--
ALTER TABLE `gio_hang`
  MODIFY `maGioHang` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `gio_hang_thuoc_tinh`
--
ALTER TABLE `gio_hang_thuoc_tinh`
  MODIFY `maGioHangThuocTinh` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  MODIFY `maHinhAnh` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `maNguoiDung` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `phuong_thuc_thanh_toan`
--
ALTER TABLE `phuong_thuc_thanh_toan`
  MODIFY `maPhuongThucThanhToan` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `quang_cao`
--
ALTER TABLE `quang_cao`
  MODIFY `maQuangCao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  MODIFY `maSanPham` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `san_pham_thuoc_tinh`
--
ALTER TABLE `san_pham_thuoc_tinh`
  MODIFY `maSanPhamThuocTinh` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `thuoc_tinh`
--
ALTER TABLE `thuoc_tinh`
  MODIFY `maThuocTinh` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `thuong_hieu`
--
ALTER TABLE `thuong_hieu`
  MODIFY `maThuongHieu` int(11) NOT NULL AUTO_INCREMENT;

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
