<?php
include "../../common/ket_noi.php";

// Lấy mã danh mục từ POST
$maDanhMuc = isset($_POST['madanhmuc']) ? $_POST['madanhmuc'] : 0;

if ($maDanhMuc == 0) {
    echo json_encode(["success" => false, "message" => "Thiếu mã danh mục"]);
    exit();
}

// 1. Lấy Thương hiệu
$queryThuongHieu = "SELECT th.maThuongHieu, th.tenThuongHieu 
                    FROM thuong_hieu th
                    INNER JOIN danh_muc_thuong_hieu dmth ON th.maThuongHieu = dmth.maThuongHieu
                    WHERE dmth.maDanhMuc = $maDanhMuc";

$dataThuongHieu = mysqli_query($conn, $queryThuongHieu);
$listThuongHieu = array();
while ($row = mysqli_fetch_assoc($dataThuongHieu)) {
    // Ép kiểu maThuongHieu về int nếu model Java của bạn để là int
    $row['maThuongHieu'] = (int)$row['maThuongHieu'];
    $listThuongHieu[] = $row;
}

// 2. Lấy Thuộc tính và Giá trị
$queryThuocTinh = "SELECT DISTINCT
                        tt.maThuocTinh,
                        tt.tenThuocTinh,
                        sptt.giaTri
                    FROM san_pham sp
                    INNER JOIN san_pham_thuoc_tinh sptt ON sp.maSanPham = sptt.maSanPham
                    INNER JOIN thuoc_tinh tt ON sptt.maThuocTinh = tt.maThuocTinh
                    INNER JOIN danh_muc_thuoc_tinh dmtt ON tt.maThuocTinh = dmtt.maThuocTinh
                    WHERE sp.maDanhMuc = $maDanhMuc
                    ORDER BY tt.maThuocTinh
                    ";

$dataThuocTinh = mysqli_query($conn, $queryThuocTinh);
$tempThuocTinh = array();

while ($row = mysqli_fetch_assoc($dataThuocTinh)) {
    $maTT = $row['maThuocTinh'];
    if (!isset($tempThuocTinh[$maTT])) {
        $tempThuocTinh[$maTT] = [
            'maThuocTinh' => (int)$maTT,
            'tenThuocTinh' => $row['tenThuocTinh'],
            'giaTri' => array()
        ];
    }
    // Mỗi giá trị là 1 Object có key 'value' khớp với SanPhamGiaTriThuocTinh.java
    $tempThuocTinh[$maTT]['giaTri'][] = [
        'value' => $row['giaTri']
    ];
}

// 3. Đóng gói kết quả - QUAN TRỌNG: Key phải khớp với SanPham.java
$arr = [
    'success' => true,
    'message' => "Thành công",
    'thuongHieu' => $listThuongHieu, 
    'sanPhamThuocTinh' => array_values($tempThuocTinh) // Đã đổi key từ thuocTinh -> sanPhamThuocTinh
];

echo json_encode($arr);
?>