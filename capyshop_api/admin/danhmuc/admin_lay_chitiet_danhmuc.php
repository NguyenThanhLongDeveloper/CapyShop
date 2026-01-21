<?php
include "../../common/ket_noi.php";

$maDanhMuc = $_POST['maDanhMuc'];

// 1. Get Category Basic Info
$query = "SELECT * FROM danh_muc WHERE maDanhMuc = '$maDanhMuc'";
$data = mysqli_query($conn, $query);
$result = array();

if ($row = mysqli_fetch_assoc($data)) {
    $result = $row;
    
    // 2. Get Associated Brands (Thuong Hieu)
    $queryThuongHieu = "SELECT th.tenThuongHieu 
                        FROM danh_muc_thuong_hieu dmth 
                        JOIN thuong_hieu th ON dmth.maThuongHieu = th.maThuongHieu 
                        WHERE dmth.maDanhMuc = '$maDanhMuc'";
    $dataThuongHieu = mysqli_query($conn, $queryThuongHieu);
    $thuongHieuArr = [];
    while ($rowTH = mysqli_fetch_assoc($dataThuongHieu)) {
        $thuongHieuArr[] = $rowTH['tenThuongHieu'];
    }
    $result['danhSachThuongHieu'] = $thuongHieuArr;

    // 3. Get Associated Attributes (Thuoc Tinh)
    $queryThuocTinh = "SELECT tt.tenThuocTinh 
                       FROM danh_muc_thuoc_tinh dmtt 
                       JOIN thuoc_tinh tt ON dmtt.maThuocTinh = tt.maThuocTinh 
                       WHERE dmtt.maDanhMuc = '$maDanhMuc'";
    $dataThuocTinh = mysqli_query($conn, $queryThuocTinh);
    $thuocTinhArr = [];
    while ($rowTT = mysqli_fetch_assoc($dataThuocTinh)) {
        $thuocTinhArr[] = $rowTT['tenThuocTinh'];
    }
    $result['danhSachThuocTinh'] = $thuocTinhArr;

    $arr = [
        'success' => true,
        'message' => "Thành công",
        'result' => [$result]
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không tìm thấy danh mục",
        'result' => null
    ];
}

print_r(json_encode($arr));
?>
