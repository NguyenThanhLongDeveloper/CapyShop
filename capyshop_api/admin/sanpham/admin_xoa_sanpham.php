<?php
include "../../common/ket_noi.php";

$maSanPham = $_POST['maSanPham'] ?? 0;

if ($maSanPham == 0) {
    echo json_encode(["success" => false, "message" => "Mã sản phẩm không hợp lệ"]);
    exit();
}

// Xóa các bảng liên quan trước (Constraint/Clean-up)
mysqli_query($conn, "DELETE FROM `hinh_anh` WHERE `maSanPham` = '$maSanPham'");
mysqli_query($conn, "DELETE FROM `san_pham_thuoc_tinh` WHERE `maSanPham` = '$maSanPham'");

$query = "DELETE FROM `san_pham` WHERE `maSanPham` = '$maSanPham'";
$data = mysqli_query($conn, $query);

if ($data) {
    echo json_encode(["success" => true, "message" => "Xóa sản phẩm thành công"]);
} else {
    echo json_encode(["success" => false, "message" => "Xóa sản phẩm thất bại: " . mysqli_error($conn)]);
}
?>
