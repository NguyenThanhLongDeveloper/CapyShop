<?php
include "../../common/ket_noi.php";

$maDanhMuc = $_POST['maDanhMuc'];

$query = "DELETE FROM `danh_muc` WHERE `maDanhMuc`=$maDanhMuc";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Xóa danh mục thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Xóa danh mục thất bại",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
