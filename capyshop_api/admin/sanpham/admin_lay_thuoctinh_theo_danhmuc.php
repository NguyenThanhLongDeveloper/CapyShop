<?php
include "../../common/ket_noi.php";

$maDanhMuc = $_POST['maDanhMuc'] ?? 0;

if ($maDanhMuc == 0) {
    echo json_encode(["success" => false, "message" => "Thiếu mã danh mục"]);
    exit();
}

$query = "SELECT tt.maThuocTinh, tt.tenThuocTinh 
          FROM thuoc_tinh tt 
          JOIN danh_muc_thuoc_tinh dmtt ON tt.maThuocTinh = dmtt.maThuocTinh 
          WHERE dmtt.maDanhMuc = '$maDanhMuc'";

$result = mysqli_query($conn, $query);

$arr = [];
while ($row = mysqli_fetch_assoc($result)) {
    $item = [
        'maThuocTinh' => $row['maThuocTinh'],
        'tenThuocTinh' => $row['tenThuocTinh']
    ];
    array_push($arr, $item);
}

echo json_encode([
    "success" => true,
    "message" => "Lấy danh sách thành công",
    "result" => $arr
]);
?>
