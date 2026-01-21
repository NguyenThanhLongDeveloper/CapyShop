<?php
include "../../common/ket_noi.php";

$query = "
    SELECT 
        maPhuongThucThanhToan,
        tenPhuongThucThanhToan
    FROM phuong_thuc_thanh_toan
    WHERE trangThai = 'HOAT_DONG'
    ORDER BY maPhuongThucThanhToan ASC
";

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "Lấy phương thức thanh toán thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Lấy phương thức thanh toán thất bại",
        'result' => []
    ];
}

echo json_encode($arr);

