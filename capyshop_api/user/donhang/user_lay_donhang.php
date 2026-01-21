<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];
$trangThai   = $_POST['trangthai'];

if (empty($trangThai)) {
    // Nếu trạng thái rỗng, chỉ lọc theo mã tài khoản (Lấy tất cả đơn hàng của User)
    $query = 'SELECT * FROM `don_hang` WHERE `maNguoiDung` = "'.$maNguoiDung.'" ORDER BY maDonHang DESC';
} else {
    // Nếu có trạng thái cụ thể, lọc theo cả mã tài khoản và trạng thái
    $query = 'SELECT * FROM `don_hang` WHERE `maNguoiDung` = "'.$maNguoiDung.'" AND `trangThai` = "'.$trangThai.'" ORDER BY maDonHang DESC';
}

$data = mysqli_query($conn, $query);
$result = [];

while ($row = mysqli_fetch_assoc($data)) {
    // KHỞI TẠO BIẾN CHI TIẾT TẠI ĐÂY (Để làm mới cho mỗi đơn hàng)
    $chiTiet = []; 

    $truyvan = 'SELECT * FROM `chi_tiet_don_hang` INNER JOIN san_pham ON chi_tiet_don_hang.maSanPham = san_pham.maSanPham WHERE chi_tiet_don_hang.maDonHang = "'.$row['maDonHang'].'"';
    $data1 = mysqli_query($conn, $truyvan);

    while ($row1 = mysqli_fetch_assoc($data1)) {
        $maChiTietDonHang = $row1['maChiTietDonHang'];

        // 3. LẤY THUỘC TÍNH (Màu sắc, kích thước...) CỦA SẢN PHẨM ĐÓ
        $sqlTT = "SELECT spt.maThuocTinh,tt.tenThuocTinh,spt.giaTri
            FROM chi_tiet_don_hang_thuoc_tinh cttt
            JOIN san_pham_thuoc_tinh spt ON cttt.maSanPhamThuocTinh = spt.maSanPhamThuocTinh
            JOIN thuoc_tinh tt ON spt.maThuocTinh = tt.maThuocTinh
            WHERE cttt.maChiTietDonHang = $maChiTietDonHang
        ";

        $dataTT = mysqli_query($conn, $sqlTT);
        $thuocTinhMap = [];

        while ($tt = mysqli_fetch_assoc($dataTT)) {
            $maTT = $tt['maThuocTinh'];

            if (!isset($thuocTinhMap[$maTT])) {
                $thuocTinhMap[$maTT] = [
                    'maThuocTinh' => (int)$maTT,
                    'tenThuocTinh' => $tt['tenThuocTinh'],
                    'giaTri' => []
                ];
            }

            $thuocTinhMap[$maTT]['giaTri'][] = [
                'value' => $tt['giaTri']
            ];
        }

        // ĐÃ SỬA: Thống nhất gán vào $row1 và đưa vào mảng $chiTiet
        $row1['thuocTinh'] = array_values($thuocTinhMap);
        $chiTiet[] = $row1; 
    }

    // ĐÃ SỬA: Gán mảng $chiTiet đã chứa thuộc tính vào đơn hàng
    $row['chitietdonhang'] = $chiTiet;
    $result[] = $row;
}

// Trả về kết quả JSON
echo json_encode([
    'success' => !empty($result),
    'message' => !empty($result) ? 'Lấy đơn hàng thành công' : 'Không có đơn hàng nào',
    'result' => $result
], JSON_UNESCAPED_UNICODE);
?>