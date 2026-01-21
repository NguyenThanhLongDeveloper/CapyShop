<?php
include "../../common/ket_noi.php";

$query = "SELECT * FROM nguoi_dung WHERE vaiTro = 'NGUOI_MUA' ORDER BY maNguoiDung DESC";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "Thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không có dữ liệu",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
