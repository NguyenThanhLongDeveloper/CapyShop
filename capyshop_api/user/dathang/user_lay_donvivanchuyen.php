<?php
include "../../common/ket_noi.php";

$query = "
    SELECT 
        maDonViVanChuyen,
        tenDonViVanChuyen,
        giaDonViVanChuyen
    FROM don_vi_van_chuyen
    WHERE trangThai = 'HOAT_DONG'
    ORDER BY giaDonViVanChuyen ASC
";

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "Lấy đơn vị vận chuyển thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Lấy đơn vi vận chuyển thất bại",
        'result' => []
    ];
}

echo json_encode($arr);
