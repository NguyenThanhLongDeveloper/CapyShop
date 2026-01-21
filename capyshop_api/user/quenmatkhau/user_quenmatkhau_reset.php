<?php
include "../../common/ket_noi.php";

$email = $_GET['email'] ?? '';
$token = $_GET['token'] ?? '';

$message = "";
$isValidToken = false;

if (empty($email) || empty($token)) {
    $message = "Liên kết không hợp lệ.";
} else {

    $sql = "SELECT token_expiry FROM nguoi_dung WHERE email = ? AND reset_token = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $email, $token);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows === 1) {
        $row = $result->fetch_assoc();

        if (time() > strtotime($row['token_expiry'])) {
            $message = "Liên kết đã hết hạn.";
        } else {
            $isValidToken = true;
        }
    } else {
        $message = "Token không hợp lệ.";
    }
    $stmt->close();
}
?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Lại Mật Khẩu</title>

    <!-- ICON -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        p.error {
            color: #d9534f;
            font-weight: bold;
        }

        /* ===== PASSWORD WRAPPER ===== */
        .password-wrapper {
            position: relative;
            width: 100%;
            margin-top: 10px;
        }

        .password-wrapper input {
            width: 100%;
            padding: 10px 40px 10px 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .password-wrapper i {
            position: absolute;
            top: 50%;
            right: 12px;
            transform: translateY(-50%);
            cursor: pointer;
            color: #777;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #D2B48C;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background-color: #B8A07A;
        }
    </style>
</head>

<body>
<div class="container">

    <?php if (!empty($message)): ?>
        <h2>Thông Báo</h2>
        <p class="error"><?php echo htmlspecialchars($message); ?></p>

    <?php elseif ($isValidToken): ?>

        <h2>Nhập Mật Khẩu Mới</h2>

        <form method="post" action="user_quenmatkhau_submit.php">

            <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
            <input type="hidden" name="token" value="<?php echo htmlspecialchars($token); ?>">

            <p style="text-align:left;">Mật khẩu mới:</p>

            <div class="password-wrapper">
                <input
                    type="password"
                    name="password"
                    id="password"
                    placeholder="Mật khẩu mới"
                    required
                >
                <i class="fa-solid fa-eye" id="togglePassword"></i>
            </div>

            <input type="submit" name="submit_password" value="ĐẶT LẠI MẬT KHẨU">
        </form>

    <?php endif; ?>

</div>

<script>
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");

    togglePassword.addEventListener("click", function () {
        const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
        passwordInput.setAttribute("type", type);
        this.classList.toggle("fa-eye-slash");
    });
</script>

</body>
</html>


<?php
$conn->close();
?>