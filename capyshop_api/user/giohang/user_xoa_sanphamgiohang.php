<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];
$maGioHang = $_POST['magiohang'];

// Xóa thuộc tính trước
mysqli_query($conn, "DELETE FROM gio_hang_thuoc_tinh WHERE maGioHang = $maGioHang");

// Xóa giỏ hàng
$query = "DELETE FROM gio_hang WHERE maGioHang = $maGioHang AND maNguoiDung = $maNguoiDung";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => 'Đã xóa sản phẩm khỏi giỏ hàng'
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Xóa sản phẩm thất bại'
    ];
}

echo json_encode($arr, JSON_UNESCAPED_UNICODE);
