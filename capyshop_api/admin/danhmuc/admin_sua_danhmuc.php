<?php
include "../../common/ket_noi.php";

$maDanhMuc = $_POST['maDanhMuc'];
$tenDanhMuc = $_POST['tenDanhMuc'];
$hinhAnhDanhMuc = $_POST['hinhAnhDanhMuc'];
$danhSachThuongHieu = isset($_POST['danhSachThuongHieu']) ? json_decode($_POST['danhSachThuongHieu'], true) : [];
$danhSachThuocTinh = isset($_POST['danhSachThuocTinh']) ? json_decode($_POST['danhSachThuocTinh'], true) : [];

$query = "UPDATE `danh_muc` SET `tenDanhMuc`='$tenDanhMuc',`hinhAnhDanhMuc`='$hinhAnhDanhMuc' WHERE `maDanhMuc`=$maDanhMuc";
$data = mysqli_query($conn, $query);

if ($data) {
    // 1. Xóa liên kết cũ
    $deleteTH = "DELETE FROM danh_muc_thuong_hieu WHERE maDanhMuc = '$maDanhMuc'";
    mysqli_query($conn, $deleteTH);
    $deleteTT = "DELETE FROM danh_muc_thuoc_tinh WHERE maDanhMuc = '$maDanhMuc'";
    mysqli_query($conn, $deleteTT);

    // 2. Thêm liên kết mới (Logic giống thêm mới)
    // Xử lý Thương Hiệu
    if (!empty($danhSachThuongHieu)) {
        foreach ($danhSachThuongHieu as $tenThuongHieu) {
            $tenThuongHieu = trim($tenThuongHieu);
            $checkQuery = "SELECT maThuongHieu FROM thuong_hieu WHERE tenThuongHieu = '$tenThuongHieu'";
            $checkResult = mysqli_query($conn, $checkQuery);
            
            if (mysqli_num_rows($checkResult) > 0) {
                $row = mysqli_fetch_assoc($checkResult);
                $maThuongHieu = $row['maThuongHieu'];
            } else {
                $insertQuery = "INSERT INTO thuong_hieu (tenThuongHieu) VALUES ('$tenThuongHieu')";
                mysqli_query($conn, $insertQuery);
                $maThuongHieu = mysqli_insert_id($conn);
            }
            $linkQuery = "INSERT INTO danh_muc_thuong_hieu (maDanhMuc, maThuongHieu) VALUES ('$maDanhMuc', '$maThuongHieu')";
            mysqli_query($conn, $linkQuery);
        }
    }

    // Xử lý Thuộc Tính
    if (!empty($danhSachThuocTinh)) {
        foreach ($danhSachThuocTinh as $tenThuocTinh) {
            $tenThuocTinh = trim($tenThuocTinh);
            $checkQuery = "SELECT maThuocTinh FROM thuoc_tinh WHERE tenThuocTinh = '$tenThuocTinh'";
            $checkResult = mysqli_query($conn, $checkQuery);
            
            if (mysqli_num_rows($checkResult) > 0) {
                $row = mysqli_fetch_assoc($checkResult);
                $maThuocTinh = $row['maThuocTinh'];
            } else {
                $insertQuery = "INSERT INTO thuoc_tinh (tenThuocTinh) VALUES ('$tenThuocTinh')";
                mysqli_query($conn, $insertQuery);
                $maThuocTinh = mysqli_insert_id($conn);
            }
            $linkQuery = "INSERT INTO danh_muc_thuoc_tinh (maDanhMuc, maThuocTinh) VALUES ('$maDanhMuc', '$maThuocTinh')";
            mysqli_query($conn, $linkQuery);
        }
    }

    $arr = [
        'success' => true,
        'message' => "Cập nhật danh mục thành công",
        'result' => []
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Cập nhật danh mục thất bại",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
