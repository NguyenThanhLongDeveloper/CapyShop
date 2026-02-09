<?php
include "../../common/ket_noi.php";

// Nhận dữ liệu từ client
$email = $_POST['email'];
$soDienThoai = $_POST['sodienthoai'];
$hoTenNguoiDung = $_POST['hotennguoidung'];
$matKhau = $_POST['matkhau'];
$uId = $_POST['uid'];

$query = 'SELECT * FROM `nguoi_dung` WHERE `email` = "'.$email.'"';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);

if ($numrow > 0) {
    // Email đã tồn tại
    $arr = [
        'success' => false,
        'message' => 'Email đã tồn tại !'
    ];
} else {
    $matKhauMaHoa = password_hash($matKhau, PASSWORD_DEFAULT);

    // insert dữ liệu vào database
    $query = '
        INSERT INTO `nguoi_dung`
        (`hoTenNguoiDung`, `email`, `soDienThoai`, `matKhau`, `vaiTro`, `trangThai`, `uId`)
        VALUES
        ("'.$hoTenNguoiDung.'", "'.$email.'", "'.$soDienThoai.'", "'.$matKhauMaHoa.'", "NGUOI_MUA", "HOAT_DONG", "'.$uId.'")';

    $data = mysqli_query($conn, $query);

    if ($data == true) {
        $arr = [
            'success' => true,
            'message' => 'Đăng ký thành công'
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Đăng ký thất bại'
        ];
    }
}

// Trả JSON về client
print_r(json_encode($arr));
?>
