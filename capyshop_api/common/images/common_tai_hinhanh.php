<?php
// API xử lý Upload hình ảnh dùng chung (Common)
$target_dir = "./"; // Gốc là common/images/

// Nhận tham số type để phân loại thư mục (danhmuc, sanpham, user, ...)
$type = isset($_POST['type']) ? $_POST['type'] : "";

if (!empty($type)) {
    $target_dir .= $type . "/";
    // Kiểm tra và tạo thư mục nếu chưa tồn tại
    if (!file_exists($target_dir)) {
        mkdir($target_dir, 0777, true);
    }
}

// Kiểm tra quyền ghi
if (!is_writable($target_dir)) {
    $arr = [
        "success" => false,
        "message" => "Thư mục " . $target_dir . " không có quyền ghi.",
        "name" => ""
    ];
    echo json_encode($arr);
    exit;
}

if (isset($_FILES["file"])) {
    $name = basename($_FILES["file"]["name"]);
    $extension = pathinfo($name, PATHINFO_EXTENSION);
    $new_name = "img_" . uniqid() . "." . $extension;
    $target_file = $target_dir . $new_name;

    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
        // Trả về tên file kèm theo folder (ví dụ: sanpham/img_123.jpg)
        $return_name = (!empty($type) ? $type . "/" : "") . $new_name;
        $arr = [
            "success" => true,
            "message" => "Upload thành công",
            "name" => $return_name
        ];
    } else {
        $arr = [
            "success" => false,
            "message" => "Lỗi khi lưu file.",
            "name" => ""
        ];
    }
} else {
    $arr = [
        "success" => false,
        "message" => "Server không nhận được file.",
        "name" => ""
    ];
}

echo json_encode($arr);
?>
