<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';

include "../../common/ket_noi.php";

header('Content-Type: application/json');

$arr = [
    'success' => false,
    'message' => "Yêu cầu không hợp lệ hoặc thiếu email.",
    'result'  => []
];

$email = $_POST['email'] ?? '';

if ($_SERVER["REQUEST_METHOD"] === "POST" && !empty($email)) {

    $sql = "SELECT maNguoiDung FROM nguoi_dung WHERE email = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result_db = $stmt->get_result();

    if ($result_db->num_rows === 0) {
        $arr['message'] = "Email không tồn tại.";
    } else {

        $resetToken = bin2hex(random_bytes(32));
        $expiryTime = date("Y-m-d H:i:s", time() + 3600);

        $update_sql = "UPDATE nguoi_dung SET reset_token = ?, token_expiry = ? WHERE email = ?";
        $update_stmt = $conn->prepare($update_sql);
        $update_stmt->bind_param("sss", $resetToken, $expiryTime, $email);
        $update_stmt->execute();
        $update_stmt->close();

        $mail = new PHPMailer(true);

        try {
            $mail->isSMTP();
            $mail->Host       = "smtp.gmail.com";
            $mail->SMTPAuth   = true;
            $mail->Username   = "nguyenthanhlong.developer@gmail.com";
            $mail->Password   = "dyyg uhdn lugy ugvh";
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_SMTPS;
            $mail->Port       = 465;
            $mail->CharSet    = "utf-8";

            $mail->setFrom("nguyenthanhlong.developer@gmail.com", "Capy Shop");
            $mail->addAddress($email);
            $mail->isHTML(true);
            $mail->Subject = "Khôi phục mật khẩu Capy Shop";

            $link = "http://192.168.0.102/capyshop_api/user/quenmatkhau/user_quenmatkhau_reset.php?email=$email&token=$resetToken";
            $mail->Body = "Nhấn vào link sau để đặt lại mật khẩu (hết hạn sau 1 giờ):<br><a href='$link'>$link</a>";

            $mail->send();

            $arr = [
                'success' => true,
                'message' => "Đã gửi link khôi phục mật khẩu.",
                'result'  => []
            ];
        } catch (Exception $e) {
            $arr['message'] = "Gửi mail thất bại.";
        }
    }
    $stmt->close();
}

echo json_encode($arr);
$conn->close();
?>
