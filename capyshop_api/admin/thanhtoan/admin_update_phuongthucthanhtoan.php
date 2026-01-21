<?php
include "../../common/ket_noi.php";

$maPhuongThucThanhToan = $_POST['maPhuongThucThanhToan'];
$trangThai = $_POST['trangThai']; // 'HOAT_DONG' or 'TAM_TAT'

// Check if data is provided
if (empty($maPhuongThucThanhToan) || empty($trangThai)) {
    $arr = [
        'success' => false,
        'message' => "Thiếu thông tin"
    ];
    print_r(json_encode($arr));
    exit();
}

$query = "UPDATE `phuong_thuc_thanh_toan` SET `trangThai`='$trangThai' WHERE `maPhuongThucThanhToan`='$maPhuongThucThanhToan'";
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
