<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];
$maDonHang = $_POST['madonhang'];
$trangThai = $_POST['trangthai']; //'DA_HUY'

if (empty($maDonHang) || empty($trangThai)) {
    $arr = [
        'success' => false,
        'message' => "Thiếu thông tin"
    ];
    print_r(json_encode($arr));
    exit();
}

$query = "UPDATE `don_hang` SET `trangThai`='$trangThai' WHERE  `maNguoiDung`='$maNguoiDung' AND `maDonHang`='$maDonHang'";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Thành công"
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công"
    ];
}

print_r(json_encode($arr));
?>
