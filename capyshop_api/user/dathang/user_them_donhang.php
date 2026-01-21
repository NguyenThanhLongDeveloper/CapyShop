<?php
include "../../common/ket_noi.php";

// ========================
// LẤY DỮ LIỆU POST
// ========================
$maNguoiDung = $_POST['manguoidung'];
$maPhuongThucThanhToan = $_POST['maphuongthucthanhtoan'];
$maDonViVanChuyen = $_POST['madonvivanchuyen'];
$hoTenNguoiDung = $_POST['hovatennguoidung'];
$soDienThoai = $_POST['sodienthoai'];
$diaChi = $_POST['diachi'];
$tongTien = $_POST['tongtien'];
$tongSoLuong = $_POST['tongsoluong'];
$chiTietDonHangJson = $_POST['chitietdonhang'];



// ========================
// VALIDATE
// ========================
if (
    empty($maNguoiDung) ||
    empty($maPhuongThucThanhToan) ||
    empty($maDonViVanChuyen) ||
    empty($hoTenNguoiDung) ||
    empty($soDienThoai) ||
    empty($diaChi) ||
    empty($chiTietDonHangJson)
) {
    echo json_encode([
        'success' => false,
        'message' => 'Thiếu dữ liệu đầu vào'
    ]);
    exit;
}

// Escape chuỗi
$hoTenNguoiDung = mysqli_real_escape_string($conn, $hoTenNguoiDung);
$soDienThoai = mysqli_real_escape_string($conn, $soDienThoai);
$diaChi = mysqli_real_escape_string($conn, $diaChi);

// Decode JSON
$chiTietDonHang = json_decode($chiTietDonHangJson, true);
if (!$chiTietDonHang) {
    echo json_encode([
        'success' => false,
        'message' => 'JSON chi tiết đơn hàng không hợp lệ'
    ]);
    exit;
}

try {

    // ========================
    // BẮT ĐẦU TRANSACTION
    // ========================
    mysqli_begin_transaction($conn);

    // ========================
    // KIỂM TRA & KHÓA TỒN KHO
    // ========================
    foreach ($chiTietDonHang as $item) {

        $maSanPham = (int)$item['maSanPham'];
        $tenSanPham = $item['tenSanPham'];
        $soLuongDat = (int)$item['soLuong'];

        $queryKho = "
            SELECT soLuongTon 
            FROM san_pham 
            WHERE maSanPham = $maSanPham
            FOR UPDATE
        ";

        $resultKho = mysqli_query($conn, $queryKho);
        if (!$resultKho || mysqli_num_rows($resultKho) == 0) {
            throw new Exception("($tenSanPham) không tồn tại ");
        }

        $rowKho = mysqli_fetch_assoc($resultKho);
        $soLuongTon = (int)$rowKho['soLuongTon'];

        if ($soLuongTon < $soLuongDat) {
            throw new Exception("$tenSanPham không đủ số lượng tồn kho");
        }
    }

    // ========================
    // INSERT DON_HANG
    // ========================
    $queryDonHang = "
        INSERT INTO don_hang (
        maNguoiDung,
        maPhuongThucThanhToan,
        maDonViVanChuyen,
        hoTenNguoiDung,
        soDienThoai,
        diaChi,
        tongTien,
        tongSoLuong,
        trangThai -- Sửa từ trangThaiDonHang thành trangThai
    ) VALUES (
        $maNguoiDung,
        $maPhuongThucThanhToan,
        $maDonViVanChuyen,
        '$hoTenNguoiDung',
        '$soDienThoai',
        '$diaChi',
        $tongTien,
        $tongSoLuong,
        'CHO_XAC_NHAN'
    )
    ";

    if (!mysqli_query($conn, $queryDonHang)) {
        throw new Exception("Lỗi tạo đơn hàng");
    }

    $maDonHang = mysqli_insert_id($conn);

    // ========================
    // INSERT CHI TIẾT + TRỪ KHO
    // ========================
    foreach ($chiTietDonHang as $item) {
    $maSanPham = (int)$item['maSanPham'];
    $giaSanPham = (int)$item['giaSanPham'];
    $soLuong = (int)$item['soLuong'];
    
    // Lấy danh sách thuộc tính từ JSON Android (Key là 'thuocTinh')
    $thuocTinhList = $item['thuocTinh'] ?? [];

    // 1. Insert vào bảng chi_tiet_don_hang
    $queryCT = "INSERT INTO chi_tiet_don_hang (maDonHang, maSanPham, giaSanPham, soLuong) 
                VALUES ($maDonHang, $maSanPham, $giaSanPham, $soLuong)";
    if (!mysqli_query($conn, $queryCT)) {
        throw new Exception("Lỗi chi tiết đơn hàng cho sản phẩm ID: $maSanPham");
    }
    $maChiTietDonHang = mysqli_insert_id($conn);

    // 2. Xử lý tìm maSanPhamThuocTinh khớp với DB
    foreach ($thuocTinhList as $tt) {
        $maThuocTinh = (int)$tt['maThuocTinh'];
        
        // Vì giaTri gửi từ Android là mảng lồng: "giaTri":[{"value":"Đen"}]
        // Chúng ta lấy giá trị đầu tiên
        $giaTriThucTe = mysqli_real_escape_string($conn, $tt['giaTri'][0]['value']);

        // TRUY VẤN KIỂM TRA: Tìm maSanPhamThuocTinh từ các bảng liên quan
        $queryFindId = "
            SELECT sptt.maSanPhamThuocTinh 
            FROM san_pham_thuoc_tinh sptt
            JOIN thuoc_tinh tt ON sptt.maThuocTinh = tt.maThuocTinh
            WHERE sptt.maSanPham = $maSanPham 
              AND sptt.maThuocTinh = $maThuocTinh 
              AND sptt.giaTri = '$giaTriThucTe'
            LIMIT 1
        ";

        $resFind = mysqli_query($conn, $queryFindId);
        if ($resFind && mysqli_num_rows($resFind) > 0) {
            $rowId = mysqli_fetch_assoc($resFind);
            $maSanPhamThuocTinhHopLe = $rowId['maSanPhamThuocTinh'];

            // INSERT vào bảng chi_tiet_don_hang_thuoc_tinh
            $queryInsertTT = "
                INSERT INTO chi_tiet_don_hang_thuoc_tinh (maChiTietDonHang, maSanPhamThuocTinh)
                VALUES ($maChiTietDonHang, $maSanPhamThuocTinhHopLe)
            ";
            mysqli_query($conn, $queryInsertTT);
        } else {
            // Tùy chọn: Nếu không tìm thấy thuộc tính khớp, bạn có thể bỏ qua hoặc báo lỗi
            // throw new Exception("Thuộc tính '$giaTriThucTe' không hợp lệ cho sản phẩm này.");
        }
    }

    // 3. Trừ tồn kho (như cũ)
    $queryTruKho = "UPDATE san_pham SET soLuongTon = soLuongTon - $soLuong WHERE maSanPham = $maSanPham";
    mysqli_query($conn, $queryTruKho);
}

    // ========================
    // COMMIT
    // ========================
    mysqli_commit($conn);

    echo json_encode([
        'success' => true,
        'message' => 'Đặt hàng thành công',
        'maDonHang' => $maDonHang
    ]);

} catch (Exception $e) {

    mysqli_rollback($conn);

    echo json_encode([
        'success' => false,
        'message' =>  $e->getMessage()
    ]);
}
