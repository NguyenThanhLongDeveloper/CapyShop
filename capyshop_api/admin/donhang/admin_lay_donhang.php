<?php
include "../../common/ket_noi.php";

$trangThai = isset($_POST['trangThai']) ? $_POST['trangThai'] : "";
$result = array();

// Build query
$query = "SELECT dh.*, nd.hinhAnhNguoiDung, nd.hoTenNguoiDung AS tenNguoiDung,
                 pt.tenPhuongThucThanhToan,
                 dv.tenDonViVanChuyen, dv.giaDonViVanChuyen AS phiVanChuyen
          FROM don_hang dh
          INNER JOIN nguoi_dung nd ON dh.maNguoiDung = nd.maNguoiDung
          LEFT JOIN phuong_thuc_thanh_toan pt ON dh.maPhuongThucThanhToan = pt.maPhuongThucThanhToan
          LEFT JOIN don_vi_van_chuyen dv ON dh.maDonViVanChuyen = dv.maDonViVanChuyen";

if (!empty($trangThai)) {
    $query .= " WHERE dh.trangThai = '$trangThai'";
}

$query .= " ORDER BY dh.maDonHang DESC";

$data = mysqli_query($conn, $query);

while ($row = mysqli_fetch_assoc($data)) {
    // Get Order Details for each order
    $maDonHang = $row['maDonHang'];
    $queryChiTiet = "SELECT ctdh.*, sp.tenSanPham, sp.hinhAnhSanPham 
                     FROM chi_tiet_don_hang ctdh
                     INNER JOIN san_pham sp ON ctdh.maSanPham = sp.maSanPham
                     WHERE ctdh.maDonHang = '$maDonHang'";
    
    $dataChiTiet = mysqli_query($conn, $queryChiTiet);
    $chiTietDonHang = array();
    
    while ($rowChiTiet = mysqli_fetch_assoc($dataChiTiet)) {
        // Fetch Attributes (Logic copied from user_lay_donhang.php)
        $maChiTietDonHang = $rowChiTiet['maChiTietDonHang'];
        
        $sqlTT = "SELECT spt.maThuocTinh, tt.tenThuocTinh, spt.giaTri
            FROM chi_tiet_don_hang_thuoc_tinh cttt
            JOIN san_pham_thuoc_tinh spt ON cttt.maSanPhamThuocTinh = spt.maSanPhamThuocTinh
            JOIN thuoc_tinh tt ON spt.maThuocTinh = tt.maThuocTinh
            WHERE cttt.maChiTietDonHang = $maChiTietDonHang
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

        $rowChiTiet['thuocTinh'] = array_values($thuocTinhMap);
        $chiTietDonHang[] = $rowChiTiet;
    }
    
    $row['adminChitietdonhang'] = $chiTietDonHang;
    $result[] = $row;
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
        'result' => $result
    ];
}

print_r(json_encode($arr));
?>
