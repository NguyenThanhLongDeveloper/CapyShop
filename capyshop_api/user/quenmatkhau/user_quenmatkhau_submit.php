<?php
include "../../common/ket_noi.php";

$message = "Có lỗi xảy ra.";
$success = false;

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST['submit_password'])) {

    $email = $_POST['email'] ?? '';
    $token = $_POST['token'] ?? '';
    $newPassword = $_POST['password'] ?? '';

    if (!empty($email) && !empty($token) && !empty($newPassword)) {

        $sql = "SELECT token_expiry FROM nguoi_dung WHERE email = ? AND reset_token = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ss", $email, $token);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows === 1) {
            $row = $result->fetch_assoc();

            if (time() <= strtotime($row['token_expiry'])) {

                $matKhauMaHoa = password_hash($newPassword, PASSWORD_DEFAULT);
                $resetToken = null;
                $tokenExpiry = null;

                $update_sql = "
                    UPDATE nguoi_dung
                    SET matKhau = ?, reset_token = ?, token_expiry = ?
                    WHERE email = ? AND reset_token = ?
                ";

                $update_stmt = $conn->prepare($update_sql);
                $update_stmt->bind_param(
                    "sssss",
                    $matKhauMaHoa,
                    $resetToken,
                    $tokenExpiry,
                    $email,
                    $token
                );

                if ($update_stmt->execute()) {
                    $success = true;
                    $message = "Đặt lại mật khẩu thành công.";
                }
                $update_stmt->close();
            } else {
                $message = "Token đã hết hạn.";
            }
        } else {
            $message = "Token không hợp lệ.";
        }
        $stmt->close();
    }
}
?>
<!-- HTML giữ nguyên -->


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Đặt Lại Mật Khẩu</title>
    <style>
        /* CSS cơ bản */
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .container { background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); width: 350px; text-align: center; }
        h2 { color: #333; margin-bottom: 20px; }
        p.status { color: <?php echo $success ? '#5cb85c' : '#d9534f'; ?>; font-weight: bold; font-size: 1.1em; }
        p.instruction { color: #777; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Kết Quả Đặt Lại</h2>
        <p class="status"><?php echo htmlspecialchars($message); ?></p>
        <?php if ($success): ?>
            <p class="instruction">Bạn có thể đóng cửa sổ trình duyệt này và đăng nhập vào ứng dụng di động với mật khẩu mới.</p>
        <?php else: ?>
            <p class="instruction">Vui lòng quay lại màn hình khôi phục trên ứng dụng để thử lại.</p>
        <?php endif; ?>
    </div>
</body>
</html>

<?php
$conn->close();
?>