<?php
include "../../common/ket_noi.php";

$tenDonViVanChuyen = $_POST['tenDonViVanChuyen'];
$giaDonViVanChuyen = $_POST['giaDonViVanChuyen'];
$trangThai = isset($_POST['trangThai']) ? $_POST['trangThai'] : "HOAT_DONG";

$query = "INSERT INTO `don_vi_van_chuyen`(`tenDonViVanChuyen`, `giaDonViVanChuyen`, `trangThai`) VALUES ('$tenDonViVanChuyen', '$giaDonViVanChuyen', '$trangThai')";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Thêm đơn vị vận chuyển thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Thêm đơn vị vận chuyển thất bại: " . mysqli_error($conn),
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
