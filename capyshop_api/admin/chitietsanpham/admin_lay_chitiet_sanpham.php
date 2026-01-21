<?php
include "../../common/ket_noi.php";

$maSanPham = $_POST['maSanPham'];

// 1. Get Basic Info + Brand Name
$query = "SELECT sp.*, th.tenThuongHieu 
          FROM `san_pham` sp 
          LEFT JOIN `thuong_hieu` th ON sp.maThuongHieu = th.maThuongHieu 
          WHERE sp.`maSanPham` = $maSanPham";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    // 2. Get Album Images
    $query_album = "SELECT hinhAnh FROM `hinh_anh` WHERE `maSanPham` = " . $row['maSanPham'];
    $data_album = mysqli_query($conn, $query_album);
    $album = array();
    while ($row_album = mysqli_fetch_assoc($data_album)) {
        $album[] = array('hinhAnh' => $row_album['hinhAnh']);
    }
    $row['album'] = $album;

    // 3. Get Attributes
    $query_tt = "SELECT sptt.maSanPhamThuocTinh, tt.tenThuocTinh, sptt.giaTri 
                FROM `san_pham_thuoc_tinh` sptt
                JOIN `thuoc_tinh` tt ON sptt.maThuocTinh = tt.maThuocTinh
                WHERE sptt.maSanPham = " . $row['maSanPham'];

    $data_tt = mysqli_query($conn, $query_tt);
    $temp_thuoc_tinh = array();

    while ($row_tt = mysqli_fetch_assoc($data_tt)) {
        $ten = $row_tt['tenThuocTinh'];
        if (!isset($temp_thuoc_tinh[$ten])) {
            $temp_thuoc_tinh[$ten] = array();
        }
        $temp_thuoc_tinh[$ten][] = array(
            'maSanPhamThuocTinh' => (int)$row_tt['maSanPhamThuocTinh'],
            'giaTri' => $row_tt['giaTri']
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

if (!empty($result)) {
    echo json_encode(['success' => true, 'result' => $result, 'message' => "Lấy chi tiết thành công"]);
} else {
    echo json_encode(['success' => false, 'message' => "Không tìm thấy sản phẩm"]);
}
?>
