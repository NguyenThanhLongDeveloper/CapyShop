<?php
include "../../common/ket_noi.php";

$maQuangCao = $_POST['maQuangCao'];

$query = "DELETE FROM `quang_cao` WHERE `maQuangCao` = '$maQuangCao'";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Xóa quảng cáo thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Xóa quảng cáo thất bại",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
