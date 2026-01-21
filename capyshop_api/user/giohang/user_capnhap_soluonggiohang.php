<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];
$maGioHang = $_POST['magiohang'];
$soLuong   = $_POST['soluong'];

$query = "UPDATE gio_hang 
          SET soLuong = $soLuong
          WHERE maGioHang = $maGioHang AND maNguoiDung = $maNguoiDung";

$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => 'Cập nhật số lượng thành công'
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Cập nhật số lượng thất bại'
    ];
}

echo json_encode($arr, JSON_UNESCAPED_UNICODE);
