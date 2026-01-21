<?php
include "../../common/ket_noi.php";

$maDonViVanChuyen = $_POST['maDonViVanChuyen'];
$tenDonViVanChuyen = isset($_POST['tenDonViVanChuyen']) ? $_POST['tenDonViVanChuyen'] : "";
$giaDonViVanChuyen = isset($_POST['giaDonViVanChuyen']) ? $_POST['giaDonViVanChuyen'] : "";
$trangThai = isset($_POST['trangThai']) ? $_POST['trangThai'] : "";

$updateFields = [];
if (!empty($tenDonViVanChuyen)) {
    $updateFields[] = "`tenDonViVanChuyen`='$tenDonViVanChuyen'";
}
if ($giaDonViVanChuyen !== "") {
    $updateFields[] = "`giaDonViVanChuyen`='$giaDonViVanChuyen'";
}
if (!empty($trangThai)) {
    $updateFields[] = "`trangThai`='$trangThai'";
}

if (empty($updateFields)) {
    echo json_encode(['success' => false, 'message' => "Không có thông tin cập nhật"]);
    exit();
}

$query = "UPDATE `don_vi_van_chuyen` SET " . implode(", ", $updateFields) . " WHERE `maDonViVanChuyen`='$maDonViVanChuyen'";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Cập nhật thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Cập nhật thất bại: " . mysqli_error($conn),
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
