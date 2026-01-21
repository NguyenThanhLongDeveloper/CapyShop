<?php
include "../../common/ket_noi.php";

$hinhAnhQuangCao = $_POST['hinhAnhQuangCao'];

$query = "INSERT INTO `quang_cao`(`hinhAnhQuangCao`) VALUES ('$hinhAnhQuangCao')";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Thêm quảng cáo thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Thêm quảng cáo thất bại",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
