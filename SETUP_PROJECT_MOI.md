# 🚀 Hướng Dẫn Setup Project Automation Mới

> Framework: Selenium Java + TestNG + Allure + ExtentReports  
> Tác giả template: _(điền tên bạn)_

---

## 📋 Checklist Khi Bắt Đầu Project Mới

Sau khi clone/copy framework này về, hãy lần lượt sửa các mục sau:

---

## 1️⃣ `pom.xml` — Thông tin project

| Trường | Ý nghĩa | Ví dụ |
|---|---|---|
| `<groupId>` | Tên package gốc (theo tên công ty/team) | `com.mycompany` |
| `<artifactId>` | Tên project, không có khoảng trắng | `crm-automation` |
| `<version>` | Phiên bản hiện tại | `1.0.0` |
| `<module>` | Tên module con (thư mục con trong project) | `crm-module` |
| `<name>` | Tên hiển thị của project | `CRM Automation Framework` |
| `<url>` | Link GitHub của project | `https://github.com/team/crm-auto` |
| `<description>` | Mô tả ngắn mục đích của project | `Automation test cho hệ thống CRM` |
| `<developer><name>` | Họ tên tester/dev phụ trách | `Nguyen Van A` |
| `<developer><email>` | Email liên hệ | `vana@company.com` |
| `<developer><organization>` | Website/tên công ty | `https://company.com` |
| `<developer><organizationUrl>` | GitHub cá nhân hoặc tổ chức | `https://github.com/vana` |

📌 **File:** [`pom.xml`](pom.xml)

---

## 2️⃣ `config.properties` — Cấu hình hệ thống

| Key | Ý nghĩa | Ví dụ |
|---|---|---|
| `AUTHOR` | Tên tester phụ trách | `Nguyen Van A` |
| `BROWSER` | Trình duyệt mặc định | `chrome` / `firefox` / `edge` |
| `HEADLESS` | Chạy ẩn không hiện UI | `true` / `false` |
| `URL_CRM` | URL trang đăng nhập CRM | `https://crm.yoursite.com/login` |
| `URL_CMS_ADMIN` | URL trang admin CMS | `https://cms.yoursite.com/admin` |
| `URL_CMS_USER` | URL trang user CMS | `https://cms.yoursite.com` |
| `TARGET` | Môi trường chạy | `local` / `remote` |
| `REMOTE_URL` | IP máy chủ Selenium Grid | `192.168.1.100` |
| `REMOTE_PORT` | Port của Selenium Grid | `4444` |
| `PROJECT_NAME` | Tên hiển thị trên báo cáo | `CRM Test - QA Team` |
| `REPORT_TITLE` | Tiêu đề file báo cáo | `Report \| CRM Test` |
| `SEND_REPORT_TO_TELEGRAM` | Gửi báo cáo qua Telegram | `yes` / `no` |
| `TELEGRAM_TOKEN` | Token của Telegram Bot | _(lấy từ BotFather)_ |
| `TELEGRAM_CHATID` | Chat ID nhận báo cáo | _(lấy bằng getUpdates API)_ |

📌 **File:** [`src/test/resources/config/config.properties`](src/test/resources/config/config.properties)

---

## 3️⃣ `data.properties` — Dữ liệu test

| Key | Ý nghĩa | Ví dụ |
|---|---|---|
| `email` | Email tài khoản test mặc định | `tester@yoursite.com` |
| `password` | Mật khẩu tài khoản test | `P@ssword123` |
| `product_P01` | Tên sản phẩm test thứ nhất (đúng khớp DB) | `Bộ quà Tết 2025` |
| `product_P02` | Tên sản phẩm test thứ hai | `Bánh kẹo cao cấp` |

> ⚠️ Không commit file này lên Git nếu chứa tài khoản thật — thêm vào `.gitignore`.

📌 **File:** [`src/test/resources/config/data.properties`](src/test/resources/config/data.properties)

---

## 4️⃣ `config.json` — Cấu hình bổ sung

```json
{
  "url": "https://your-site.com/login",
  "browser": "chrome",
  "button": "//button[@id='btn-login']"
}
```

| Key | Ý nghĩa | Ví dụ |
|---|---|---|
| `url` | URL trang đăng nhập | `https://site.com/login` |
| `browser` | Trình duyệt | `chrome` |
| `button` | XPath nút đăng nhập | `//button[@id='login']` |

📌 **File:** [`src/test/resources/config/config.json`](src/test/resources/config/config.json)

---

## 5️⃣ Kiểm tra lại module trong `pom.xml`

Nếu project có module con (ví dụ tên thư mục là `mymodule`), cập nhật trong pom.xml:
```xml
<modules>
    <module>mymodule</module>
</modules>
```
Và trong thư mục đó cũng cần có `pom.xml` riêng với `<parent>` trỏ về project gốc.

---

## ✅ Checklist Nhanh

- [ ] Sửa `groupId`, `artifactId`, `version` trong `pom.xml`
- [ ] Sửa tên/email developer trong `pom.xml`
- [ ] Điền URL ứng dụng vào `config.properties`
- [ ] Điền tên project/report vào `config.properties`
- [ ] Điền `AUTHOR` vào `config.properties`
- [ ] Điền `email`, `password` test vào `data.properties`
- [ ] Điền tên sản phẩm vào `data.properties`
- [ ] Cập nhật `config.json` với URL và XPath đúng
- [ ] Đổi tên `<module>` khớp với tên thư mục thực tế
- [ ] Chạy `mvn clean install` để kiểm tra setup thành công

---

## 📦 Cấu Trúc Thư Mục

```
demo/
├── pom.xml                          ← 🔧 Sửa thông tin project
├── SETUP_PROJECT_MOI.md             ← 📖 File này
├── src/
│   └── test/
│       └── resources/
│           ├── config/
│           │   ├── config.properties  ← 🔧 Sửa URL, tên, author
│           │   ├── data.properties    ← 🔧 Sửa tài khoản, sản phẩm
│           │   └── config.json        ← 🔧 Sửa URL, button xpath
│           ├── suites/              ← TestNG suite XML
│           ├── testdataCRM/         ← Dữ liệu test module CRM
│           └── testdataCMS/         ← Dữ liệu test module CMS
```
