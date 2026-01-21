<?php
include "../../common/ket_noi.php";

$maSanPham = $_POST['masanpham'];

$query = "SELECT * FROM `san_pham` WHERE `maSanPham` = $maSanPham";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    // 1. Lấy album ảnh
    $query_album = "SELECT hinhAnh FROM `hinh_anh` WHERE `maSanPham` = ".$row['maSanPham'];
    $data_album = mysqli_query($conn, $query_album);
    $album = array();
    while ($row_album = mysqli_fetch_assoc($data_album)) {
        $album[] = array('hinhAnh' => $row_album['hinhAnh']);
    }
    $row['album'] = $album;

    // 2. Lấy Thuộc tính (THAY ĐỔI Ở ĐÂY)
    // Phải SELECT thêm sptt.maSanPhamThuocTinh
    $query_tt = "SELECT sptt.maSanPhamThuocTinh, tt.tenThuocTinh, sptt.giaTri 
                FROM `san_pham_thuoc_tinh` sptt
                JOIN `thuoc_tinh` tt ON sptt.maThuocTinh = tt.maThuocTinh
                WHERE sptt.maSanPham = ".$row['maSanPham'];

    $data_tt = mysqli_query($conn, $query_tt);
    $temp_thuoc_tinh = array();

    while ($row_tt = mysqli_fetch_assoc($data_tt)) {
        $ten = $row_tt['tenThuocTinh'];
        
        if (!isset($temp_thuoc_tinh[$ten])) {
            $temp_thuoc_tinh[$ten] = array();
        }
        
        // Trả về thêm maSanPhamThuocTinh để Android lưu lại ID này
        $temp_thuoc_tinh[$ten][] = array(
            "maSanPhamThuocTinh" => (int)$row_tt['maSanPhamThuocTinh'], 
            "giaTri" => $row_tt['giaTri']
        );
    }

    $final_thuoc_tinh = array();
    foreach ($temp_thuoc_tinh as $ten => $danh_sach_obj) {
        $final_thuoc_tinh[] = array(
            'tenThuocTinh' => $ten,
            'giaTri' => $danh_sach_obj
        );
    }
    $row['thuocTinh'] = $final_thuoc_tinh;

    $result[] = $row;
}

echo json_encode(['success' => true, 'result' => $result]);
?>