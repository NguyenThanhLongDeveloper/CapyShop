<?php
include "../../common/ket_noi.php";
$timKiemSanPham = $_POST['timkiemsanpham'];

if (empty($timKiemSanPham)) {
    $arr = [
        'success' => false,
        'message' => "không thành công",
    ];  
} else {
    
    $query = "SELECT * FROM `san_pham` WHERE `tenSanPham` LIKE '%$timKiemSanPham%'";
    $data = mysqli_query($conn, $query);
    $result = array();

    while ($row = mysqli_fetch_assoc($data)) {
        # code...
        $result[] = ($row);
    }

    if (!empty($result)) {
        # code...
        $arr = [
            'success' => true,
            'message' => "thanh công",
            'result' => $result
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "không thành công",
            'result' => $result
        ];
    }

}

print_r(json_encode($arr));


?>