<?php
include "../../common/ket_noi.php";

$vaiTro   = $_POST['vaitro'];

$query = "SELECT * FROM `nguoi_dung` WHERE `vaiTro` = '$vaiTro' AND `token` IS NOT NULL";

$data = mysqli_query($conn, $query);

if ($data) {
// Lặp qua dữ liệu để đưa vào mảng result
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = $row;
    }

    if (!empty($result)) {
        $arr = [
            'success' => true,
            'message' => 'Lấy danh sách thành công',
            'result' => $result
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Không tìm thấy người dùng hoặc token rỗng',
            'result' => []
        ];
    }
} else {
    $arr = [
        'success' => false,
        'message' => 'Lỗi truy vấn SQL',
        'result' => []
    ];
}

echo json_encode($arr, JSON_UNESCAPED_UNICODE);