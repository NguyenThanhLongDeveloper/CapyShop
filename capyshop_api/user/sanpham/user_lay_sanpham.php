<?php
include "../../common/ket_noi.php";

$trang = $_POST['trang'];
$quantity = 10; // Tăng lên 10 cho đẹp giao diện
$from = ($trang - 1) * $quantity; 
$maDanhMuc = $_POST['madanhmuc'];

// Chỉ lấy sản phẩm theo danh mục, bỏ lọc thương hiệu
$query = "SELECT * FROM `san_pham` WHERE `maDanhMuc` = $maDanhMuc LIMIT $from, $quantity";

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "Thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Hết dữ liệu",
        'result' => []
    ];
}

echo json_encode($arr); // Dùng json_encode để Android đọc được dễ dàng
?>