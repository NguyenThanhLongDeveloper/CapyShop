<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];
$hoVaTen = $_POST['hovaten'];
$soDienThoai = $_POST['sodienthoai'];
$diaChi = $_POST['diachi'];
$hinhAnh = $_POST['hinhanh'] ?? ""; // New parameter

if (!empty($hinhAnh)) {
    $query = "UPDATE nguoi_dung SET hoTenNguoiDung = '$hoVaTen', soDienThoai = '$soDienThoai', diaChi = '$diaChi', hinhAnhNguoiDung = '$hinhAnh' WHERE maNguoiDung = '$maNguoiDung'";
} else {
    $query = "UPDATE nguoi_dung SET hoTenNguoiDung = '$hoVaTen', soDienThoai = '$soDienThoai', diaChi = '$diaChi' WHERE maNguoiDung = '$maNguoiDung'";
}

if (mysqli_query($conn, $query)) {
    $arr = [
        'success' => true,
        'message' => "Thành công",
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công",
    ];
}
$query1 = "SELECT * FROM `nguoi_dung` WHERE `maNguoiDung` = $maNguoiDung";
$data = mysqli_query($conn, $query1);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
        $result[] = $row;
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "Thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công",
        'result' => []
    ];
}

echo json_encode($arr); // Dùng json_encode để Android đọc được dễ dàng
?>
