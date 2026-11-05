# Toy Words – Từ vựng tiếng Anh chủ đề đồ chơi (Android)

Ứng dụng cho bé 8 tuổi: 18 đồ chơi có hình minh họa, phiên âm, nghĩa tiếng Việt và câu ví dụ.
- Chạm vào thẻ đồ chơi -> đọc từ tiếng Anh.
- Chạm vào khung câu ví dụ (▶) -> đọc cả câu.
- Công tắc "Đọc chậm" -> đọc chậm hơn để bé đọc theo.
Dùng bộ đọc giọng nói (Text-to-Speech) có sẵn của Android, không cần internet sau khi đã cài giọng đọc.

## Cách chạy
1. Cài Android Studio (bản Koala trở lên), chọn File > Open và mở thư mục này.
2. Đợi Gradle Sync hoàn tất (lần đầu cần internet để tải thư viện).
3. Cắm điện thoại (bật Gỡ lỗi USB) hoặc tạo máy ảo, bấm nút Run.
4. Tạo file APK để cài: Build > Build Bundle(s) / APK(s) > Build APK(s).

## Nếu máy không phát ra tiếng
Vào Cài đặt > Ngôn ngữ > Chuyển văn bản thành giọng nói > chọn "Dịch vụ giọng nói của Google",
rồi tải dữ liệu giọng "Tiếng Anh (Hoa Kỳ)". Kiểm tra âm lượng phương tiện.

## Tạo file APK không cần cài Android Studio (dùng GitHub miễn phí)
1. Đăng ký/đăng nhập github.com, bấm New repository, đặt tên (ví dụ toywords), chọn Public hoặc Private, bấm Create.
2. Bấm "uploading an existing file", kéo TOÀN BỘ nội dung thư mục ToyWordsApp đã giải nén vào
   (nhớ thư mục ẩn .github; nếu không thấy, hãy bật hiện file ẩn), rồi bấm Commit changes.
3. Vào tab Actions > "Build APK" > đợi khoảng 3–5 phút đến khi có dấu tích xanh.
   (Nếu chưa tự chạy: bấm Run workflow.)
4. Bấm vào lần chạy đó, kéo xuống mục Artifacts, tải "ToyWords-apk" (file zip chứa app-debug.apk).
5. Chép app-debug.apk sang máy tính bảng, mở file và cho phép "Cài đặt ứng dụng không rõ nguồn" khi được hỏi.
