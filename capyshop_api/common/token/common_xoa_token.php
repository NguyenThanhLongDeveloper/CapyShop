<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];

$query = "UPDATE nguoi_dung 
          SET token = ''
          WHERE maNguoiDung = $maNguoiDung";

$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => 'Cập nhật thành công'
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Cập nhật thất bại'
    ];
}

echo json_encode($arr, JSON_UNESCAPED_UNICODE);