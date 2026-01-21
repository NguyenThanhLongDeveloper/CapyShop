<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];
$maSanPham = $_POST['masanpham'];
$soLuong = $_POST['soluong'];
$thuocTinh = $_POST['thuoctinh']; // JSON: [1, 3]

$mangThuocTinhMoi = json_decode($thuocTinh, true);
sort($mangThuocTinhMoi); // Sắp xếp để so sánh chính xác

// Bước 1: Tìm tất cả các giỏ hàng của user này với sản phẩm này
$query = "SELECT maGioHang FROM gio_hang WHERE maNguoiDung = $maNguoiDung AND maSanPham = $maSanPham";
$result = mysqli_query($conn, $query);

$maGioHangTonTai = null;

while ($row = mysqli_fetch_assoc($result)) {
    $idGH = $row['maGioHang'];
    
    // Lấy các thuộc tính của dòng giỏ hàng này
    $query_tt = "SELECT maSanPhamThuocTinh FROM gio_hang_thuoc_tinh WHERE maGioHang = $idGH";
    $res_tt = mysqli_query($conn, $query_tt);
    
    $mangThuocTinhCu = [];
    while ($row_tt = mysqli_fetch_assoc($res_tt)) {
        $mangThuocTinhCu[] = (int)$row_tt['maSanPhamThuocTinh'];
    }
    sort($mangThuocTinhCu);

    // Bước 2: So sánh 2 mảng thuộc tính
    if ($mangThuocTinhMoi === $mangThuocTinhCu) {
        $maGioHangTonTai = $idGH;
        break; // Tìm thấy dòng trùng khớp hoàn toàn
    }
}

if ($maGioHangTonTai) {
    // TRƯỜNG HỢP 1: Khớp hoàn toàn -> Cập nhật số lượng
    $query_update = "UPDATE gio_hang SET soLuong = soLuong + $soLuong WHERE maGioHang = $maGioHangTonTai";
    $final_res = mysqli_query($conn, $query_update);
} else {
    // TRƯỜNG HỢP 2: Không khớp thuộc tính hoặc chưa có SP này -> Thêm mới
    $query_insert = "INSERT INTO gio_hang (maNguoiDung, maSanPham, soLuong) VALUES ($maNguoiDung, $maSanPham, $soLuong)";
    if (mysqli_query($conn, $query_insert)) {
        $newIdGH = mysqli_insert_id($conn);
        foreach ($mangThuocTinhMoi as $idSptt) {
            mysqli_query($conn, "INSERT INTO gio_hang_thuoc_tinh (maGioHang, maSanPhamThuocTinh) VALUES ($newIdGH, $idSptt)");
        }
        $final_res = true;
    }
}

if ($final_res) {
    $arr = [
        'success' => true,
        'message' => "Đã cập nhật giỏ hàng",
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công",
    ];
}

print_r(json_encode($arr));
?>