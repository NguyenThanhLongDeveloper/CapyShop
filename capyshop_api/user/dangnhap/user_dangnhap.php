<?php
include "../../common/ket_noi.php";

$email   = $_POST['email'];
$matKhau = $_POST['matkhau'];

// 1. Lấy người dùng theo email 
$query = '
    SELECT 
        maNguoiDung,
        hoTenNguoiDung,
        hinhAnhNguoiDung,
        email,
        soDienThoai,
        diaChi,
        matKhau,
        vaiTro,
        trangThai,
        thoiGianTao
    FROM nguoi_dung
    WHERE email = "'.$email.'"
      AND trangThai = "HOAT_DONG"
    LIMIT 1
';

$data = mysqli_query($conn, $query);

if ($row = mysqli_fetch_assoc($data)) {

    // 2. So khớp mật khẩu đã mã hoá
    if (password_verify($matKhau, $row['matKhau'])) {

        // 3. XÓA mật khẩu trước khi trả JSON
        unset($row['matKhau']);

        $arr = [
            'success' => true,
            'message' => 'Đăng nhập thành công',
            'result'  => [$row]
        ];

    } else {
        // Sai mật khẩu
        $arr = [
            'success' => false,
            'message' => 'mật khẩu không chính xác',
            'result'  => []
        ];
    }

} else {
    // Không tìm thấy email hoặc tài khoản bị khóa
    $arr = [
        'success' => false,
        'message' => 'Email hoặc mật khẩu không chính xác',
        'result'  => []
    ];
}

echo json_encode($arr);
?>
