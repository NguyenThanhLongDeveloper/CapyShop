<?php
include "../../common/ket_noi.php";

$maDonViVanChuyen = $_POST['maDonViVanChuyen'];

$query = "DELETE FROM `don_vi_van_chuyen` WHERE `maDonViVanChuyen` = '$maDonViVanChuyen'";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Xóa đơn vị vận chuyển thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Xóa đơn vị vận chuyển thất bại",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
