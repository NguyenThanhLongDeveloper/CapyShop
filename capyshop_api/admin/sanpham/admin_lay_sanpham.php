<?php
include "../../common/ket_noi.php";

$query = "SELECT sp.*, dm.tenDanhMuc 
          FROM san_pham sp 
          LEFT JOIN danh_muc dm ON sp.maDanhMuc = dm.maDanhMuc 
          ORDER BY sp.maSanPham DESC";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $maSP = $row['maSanPham'];

    // 1. Fetch Album
    $query_album = "SELECT hinhAnh FROM `hinh_anh` WHERE `maSanPham` = $maSP";
    $data_album = mysqli_query($conn, $query_album);
    $album = array();
    while ($row_album = mysqli_fetch_assoc($data_album)) {
        $album[] = array('hinhAnh' => $row_album['hinhAnh']);
    }

    // 2. Fetch Attributes
    $query_tt = "SELECT sptt.maSanPhamThuocTinh, tt.tenThuocTinh, sptt.giaTri 
                 FROM `san_pham_thuoc_tinh` sptt
                 JOIN `thuoc_tinh` tt ON sptt.maThuocTinh = tt.maThuocTinh
                 WHERE sptt.maSanPham = $maSP";
    $data_tt = mysqli_query($conn, $query_tt);
    $temp_tt = array();
    while ($row_tt = mysqli_fetch_assoc($data_tt)) {
        $ten = $row_tt['tenThuocTinh'];
        if (!isset($temp_tt[$ten])) {
            $temp_tt[$ten] = array();
        }
        $temp_tt[$ten][] = array(
            'maSanPhamThuocTinh' => (int)$row_tt['maSanPhamThuocTinh'],
            'giaTri' => $row_tt['giaTri']
        );
    }

    $thuocTinh = array();
    foreach ($temp_tt as $ten => $giaTriArray) {
        $thuocTinh[] = array(
            'tenThuocTinh' => $ten,
            'giaTri' => $giaTriArray
        );
    }

    $result[] = array(
        'maSanPham' => (int)$row['maSanPham'],
        'tenSanPham' => $row['tenSanPham'],
        'hinhAnhSanPham' => $row['hinhAnhSanPham'],
        'giaSanPham' => (float)$row['giaSanPham'],
        'moTaSanPham' => $row['moTaSanPham'],
        'maDanhMuc' => (int)$row['maDanhMuc'],
        'maThuongHieu' => (int)$row['maThuongHieu'],
        'soLuongTon' => (int)$row['soLuongTon'],
        'tenDanhMuc' => $row['tenDanhMuc'],
        'album' => $album,
        'thuocTinh' => $thuocTinh
    );
}

$arr = [
    'success' => !empty($result),
    'message' => !empty($result) ? "thành công" : "không có dữ liệu",
    'result' => $result
];

echo json_encode($arr);
?>
