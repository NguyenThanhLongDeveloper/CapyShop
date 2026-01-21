<?php
include "../../common/ket_noi.php";

$maDanhMuc = $_POST['maDanhMuc'] ?? 0;

if ($maDanhMuc > 0) {
    // Join thuong_hieu and danh_muc_thuong_hieu to filter brands by category
    $query = "SELECT t.maThuongHieu, t.tenThuongHieu 
              FROM `thuong_hieu` t 
              JOIN `danh_muc_thuong_hieu` dt ON t.maThuongHieu = dt.maThuongHieu 
              WHERE dt.maDanhMuc = '$maDanhMuc'";
    
    $data = mysqli_query($conn, $query);
    $arr = array();

    while ($row = mysqli_fetch_assoc($data)) {
        array_push($arr, new ThuongHieu(
            $row['maThuongHieu'],
            $row['tenThuongHieu']
        ));
    }

    if (!empty($arr)) {
        echo json_encode(["success" => true, "result" => $arr, "message" => "Thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Không có thương hiệu nào cho danh mục này"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Thiếu mã danh mục"]);
}

class ThuongHieu {
    public $maThuongHieu;
    public $tenThuongHieu;

    function __construct($maThuongHieu, $tenThuongHieu) {
        $this->maThuongHieu = $maThuongHieu;
        $this->tenThuongHieu = $tenThuongHieu;
    }
}
?>
