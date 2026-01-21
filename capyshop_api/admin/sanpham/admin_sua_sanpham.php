<?php
include "../../common/ket_noi.php";

$maSanPham = $_POST['maSanPham'] ?? 0;
$tenSanPham = $_POST['tenSanPham'] ?? "";
$hinhAnhSanPham = $_POST['hinhAnhSanPham'] ?? "";
$giaSanPham = $_POST['giaSanPham'] ?? 0;
$moTaSanPham = $_POST['moTaSanPham'] ?? "";
$soLuongTon = $_POST['soLuongTon'] ?? 0;
$maDanhMuc = $_POST['maDanhMuc'] ?? 0;

// JSON Strings
$albumSanPham = $_POST['albumSanPham'] ?? "[]";
$thuocTinhSanPham = $_POST['thuocTinhSanPham'] ?? "[]";

if ($maSanPham == 0 || empty($tenSanPham)) {
    echo json_encode(["success" => false, "message" => "Dữ liệu không hợp lệ"]);
    exit();
}

$maThuongHieu = $_POST['maThuongHieu'] ?? 0;

// 1. Cập nhật thông tin cơ bản
$query = "UPDATE `san_pham` SET 
            `tenSanPham` = '$tenSanPham', 
            `hinhAnhSanPham` = '$hinhAnhSanPham', 
            `giaSanPham` = '$giaSanPham', 
            `moTaSanPham` = '$moTaSanPham', 
            `soLuongTon` = '$soLuongTon', 
            `maDanhMuc` = '$maDanhMuc', 
            `maThuongHieu` = '$maThuongHieu' 
          WHERE `maSanPham` = '$maSanPham'";

$data = mysqli_query($conn, $query);

if ($data) {
    // 2. Cập nhật Album (Xóa cũ thêm mới)
    $albumArr = json_decode($albumSanPham, true);
    if (!empty($albumArr)) {
        mysqli_query($conn, "DELETE FROM `hinh_anh` WHERE `maSanPham` = '$maSanPham'");
        foreach ($albumArr as $item) {
            $hinh = $item['hinhAnh'];
            $query_album = "INSERT INTO `hinh_anh` (`maSanPham`, `hinhAnh`) VALUES ('$maSanPham', '$hinh')";
            mysqli_query($conn, $query_album);
        }
    }

    // 3. Cập nhật Thuộc tính (Xóa cũ thêm mới)
    $ttArr = json_decode($thuocTinhSanPham, true);
    if (!empty($ttArr)) {
        mysqli_query($conn, "DELETE FROM `san_pham_thuoc_tinh` WHERE `maSanPham` = '$maSanPham'");
        foreach ($ttArr as $tt) {
            $tenTT = $tt['tenThuocTinh'];
            
            $query_check_tt = "SELECT maThuocTinh FROM `thuoc_tinh` WHERE `tenThuocTinh` = '$tenTT'";
            $res_check = mysqli_query($conn, $query_check_tt);
            if (mysqli_num_rows($res_check) > 0) {
                $row_tt = mysqli_fetch_assoc($res_check);
                $maTT = $row_tt['maThuocTinh'];
            } else {
                mysqli_query($conn, "INSERT INTO `thuoc_tinh` (`tenThuocTinh`) VALUES ('$tenTT')");
                $maTT = mysqli_insert_id($conn);
            }

            // LINK ATTRIBUTE TO CATEGORY (danh_muc_thuoc_tinh)
            // Check if link exists
            $query_check_link = "SELECT * FROM `danh_muc_thuoc_tinh` WHERE `maDanhMuc` = '$maDanhMuc' AND `maThuocTinh` = '$maTT'";
            $res_link = mysqli_query($conn, $query_check_link);
            if (mysqli_num_rows($res_link) == 0) {
                // Link it
                mysqli_query($conn, "INSERT INTO `danh_muc_thuoc_tinh` (`maDanhMuc`, `maThuocTinh`) VALUES ('$maDanhMuc', '$maTT')");
            }

            foreach ($tt['giaTri'] as $giaTriObj) {
                $giaTri = $giaTriObj['giaTri'];
                $query_sptt = "INSERT INTO `san_pham_thuoc_tinh` (`maSanPham`, `maThuocTinh`, `giaTri`) 
                               VALUES ('$maSanPham', '$maTT', '$giaTri')";
                mysqli_query($conn, $query_sptt);
            }
        }
    }

    echo json_encode(["success" => true, "message" => "Cập nhật sản phẩm thành công"]);
} else {
    echo json_encode(["success" => false, "message" => "Cập nhật sản phẩm thất bại: " . mysqli_error($conn)]);
}
?>
