<?php
include "../../common/ket_noi.php";

$maNguoiDung = $_POST['manguoidung'];

$sql = "
SELECT 
    gh.maGioHang,
    gh.maSanPham,
    gh.soLuong,
    sp.tenSanPham,
    sp.hinhAnhSanPham,
    sp.giaSanPham
FROM gio_hang gh
JOIN san_pham sp ON gh.maSanPham = sp.maSanPham
WHERE gh.maNguoiDung = $maNguoiDung
";

$data = mysqli_query($conn, $sql);
$result = [];

while ($row = mysqli_fetch_assoc($data)) {

    $maGioHang = $row['maGioHang'];

    // ===== LẤY THUỘC TÍNH =====
    $sqlTT = "
        SELECT 
            spt.maThuocTinh,
            tt.tenThuocTinh,
            spt.giaTri
        FROM gio_hang_thuoc_tinh ghtt
        JOIN san_pham_thuoc_tinh spt 
            ON ghtt.maSanPhamThuocTinh = spt.maSanPhamThuocTinh
        JOIN thuoc_tinh tt 
            ON spt.maThuocTinh = tt.maThuocTinh
        WHERE ghtt.maGioHang = $maGioHang
    ";

    $dataTT = mysqli_query($conn, $sqlTT);
    $thuocTinhMap = [];

    while ($tt = mysqli_fetch_assoc($dataTT)) {
        $maTT = $tt['maThuocTinh'];

        if (!isset($thuocTinhMap[$maTT])) {
            $thuocTinhMap[$maTT] = [
                'maThuocTinh' => (int)$maTT,
                'tenThuocTinh' => $tt['tenThuocTinh'],
                'giaTri' => []
            ];
        }

        $thuocTinhMap[$maTT]['giaTri'][] = [
            'value' => $tt['giaTri']
        ];
    }

    $row['thuocTinh'] = array_values($thuocTinhMap);
    $result[] = $row;
}

echo json_encode([
    'success' => true,
    'message' => 'Lấy giỏ hàng thành công',
    'result' => $result
], JSON_UNESCAPED_UNICODE);
