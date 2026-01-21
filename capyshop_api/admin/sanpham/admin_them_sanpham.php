<?php
include "../../common/ket_noi.php";

$tenSanPham = $_POST['tenSanPham'] ?? "";
$hinhAnhSanPham = $_POST['hinhAnhSanPham'] ?? "";
$giaSanPham = $_POST['giaSanPham'] ?? 0;
$moTaSanPham = $_POST['moTaSanPham'] ?? "";
$soLuongTon = $_POST['soLuongTon'] ?? 0;
$maDanhMuc = $_POST['maDanhMuc'] ?? 0; // Kept ?? 0 for safety, as the instruction snippet removed it but it's good practice.

// JSON Strings from Android
$albumSanPham = $_POST['albumSanPham'] ?? "[]"; // Kept ?? "[]" for safety, as the instruction snippet removed it but it's good practice.
$thuocTinhSanPham = $_POST['thuocTinhSanPham'] ?? "[]"; // Kept ?? "[]" for safety, as the instruction snippet removed it but it's good practice.

if (empty($tenSanPham)) {
    echo json_encode(["success" => false, "message" => "Tên sản phẩm không được để trống"]);
    exit();
}


    // Get maThuongHieu
    $maThuongHieu = $_POST['maThuongHieu'] ?? 0;

    // 1. Insert into san_pham
    $query = "INSERT INTO `san_pham` 
              (`tenSanPham`, `hinhAnhSanPham`, `giaSanPham`, `moTaSanPham`, `soLuongTon`, `maDanhMuc`, `maThuongHieu`) 
              VALUES ('$tenSanPham', '$hinhAnhSanPham', '$giaSanPham', '$moTaSanPham', '$soLuongTon', '$maDanhMuc', '$maThuongHieu')";
    
    $data = mysqli_query($conn, $query);
    
    if ($data) {
        $maSanPhamMoi = mysqli_insert_id($conn);

    // 2. Handle Album (List of objects: [{"hinhAnh": "url"}])
    $albumArr = json_decode($albumSanPham, true);
    if (!empty($albumArr)) {
        foreach ($albumArr as $item) {
            $hinh = $item['hinhAnh'];
            $query_album = "INSERT INTO `hinh_anh` (`maSanPham`, `hinhAnh`) VALUES ('$maSanPhamMoi', '$hinh')";
            mysqli_query($conn, $query_album);
        }
    }

    // 3. Handle Thuoc Tinh
    // Structure: [{"tenThuocTinh": "Size", "giaTri": [{"giaTri": "S"}]}]
    $ttArr = json_decode($thuocTinhSanPham, true);
    if (!empty($ttArr)) {
        foreach ($ttArr as $tt) {
            $tenTT = $tt['tenThuocTinh'];
            // Check if attribute name exists, if not create it
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

            // Insert values into san_pham_thuoc_tinh
            foreach ($tt['giaTri'] as $giaTriObj) {
                $giaTri = $giaTriObj['giaTri'];
                $query_sptt = "INSERT INTO `san_pham_thuoc_tinh` (`maSanPham`, `maThuocTinh`, `giaTri`)
                               VALUES ('$maSanPhamMoi', '$maTT', '$giaTri')";
                mysqli_query($conn, $query_sptt);
            }
        }
    }

    echo json_encode(["success" => true, "message" => "Thêm sản phẩm thành công"]);
} else {
    echo json_encode(["success" => false, "message" => "Thêm sản phẩm thất bại: " . mysqli_error($conn)]);
}
?>
