<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['maNguoiDung'];
$trangThai = $_POST['trangThai']; // 'HOAT_DONG' or 'KHOA'

$query = "UPDATE `nguoi_dung` SET `trangThai`='$trangThai' WHERE `maNguoiDung`='$maNguoiDung'";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Cập nhật trạng thái thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Cập nhật thất bại",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
