# 📘 HƯỚNG DẪN KHỞI TẠO DỰ ÁN MỚI TỪ FRAMEWORK DEMO

> Framework gốc: **AutomationFrameworkSelenium** by Anh Tester  
> Ngôn ngữ: **Java** | Build tool: **Maven** | Test runner: **TestNG** | Report: **Allure + ExtentReports**

---

## 🗂️ CẤU TRÚC THƯ MỤC TỔNG QUAN

```
demo/
├── pom.xml                              ← Cấu hình Maven (groupId, artifactId, dependencies)
├── HUONG_DAN_DU_AN_MOI.md              ← File hướng dẫn này
├── src/
│   ├── main/
│   │   ├── java/com/anhtester/         ← Core framework (KHÔNG cần sửa nhiều)
│   │   │   ├── annotations/            ← Custom annotations
│   │   │   ├── config/                 ← Đọc file config
│   │   │   ├── constants/              ← Hằng số (đường dẫn file)
│   │   │   ├── driver/                 ← Quản lý WebDriver
│   │   │   ├── helpers/                ← Helper classes (Excel, JSON, PDF...)
│   │   │   ├── keywords/               ← WebUI keywords (click, type, verify...)
│   │   │   ├── reports/                ← Allure & ExtentReports
│   │   │   └── utils/                  ← Tiện ích chung
│   │   └── resources/
│   │       └── log4j2.properties       ← Cấu hình log
│   └── test/
│       ├── java/com/anhtester/
│       │   ├── common/                 ← BaseTest (setup/teardown)
│       │   ├── listeners/              ← TestNG Listeners
│       │   └── projects/               ← ⭐ CODE TEST CỦA DỰ ÁN
│       │       └── crm/                ← Ví dụ dự án CRM (tham khảo)
│       │           ├── models/         ← Data model (POJO)
│       │           ├── pages/          ← Page Object classes
│       │           └── testcases/      ← Test classes
│       └── resources/
│           ├── config/                 ← ⭐ CẤU HÌNH CHÍNH
│           │   ├── config.properties   ← URL, browser, timeout, report...
│           │   └── data.properties     ← Tài khoản, đường dẫn file data
│           ├── objects/                ← ⭐ LOCATORS (XPath, CSS...)
│           ├── suites/                 ← ⭐ TestNG XML suite files
│           ├── testdataCRM/            ← File Excel/JSON dữ liệu test (mẫu)
│           └── datajson/               ← Dữ liệu JSON
```

---

## ✅ DANH SÁCH CÁC FILE CẦN THAY ĐỔI KHI BẮT ĐẦU DỰ ÁN MỚI

---

### 1️⃣ `pom.xml` — Thông tin Maven Project

**Vị trí:** `demo/pom.xml`

| Thẻ XML | Mô tả | Ví dụ thay đổi |
|---|---|---|
| `<groupId>` | Tên tổ chức/công ty | `com.yourcompany` |
| `<artifactId>` | Tên dự án | `project-automation` |
| `<version>` | Phiên bản | `1.0.0` |
| `<name>` | Tên hiển thị | `My Project Automation` |
| `<url>` | URL repo | `https://github.com/yourorg/yourproject` |
| `<description>` | Mô tả dự án | `Automation for My Project` |
| `<developer>/<name>` | Tên tác giả | `Your Name` |
| `<developer>/<email>` | Email tác giả | `your@email.com` |

```xml
<!-- Thay đổi các dòng này trong pom.xml -->
<groupId>com.yourcompany</groupId>
<artifactId>project-automation</artifactId>
<version>1.0.0</version>
<name>My Project Automation</name>
<url>https://github.com/yourorg/yourproject</url>
<description>Automation for My Project</description>
```

---

### 2️⃣ `config.properties` — Cấu hình hệ thống chính ⭐ QUAN TRỌNG NHẤT

**Vị trí:** `src/test/resources/config/config.properties`

| Key | Mô tả | Cần thay đổi? |
|---|---|---|
| `AUTHOR` | Tên tác giả | ✅ Có |
| `BROWSER` | Trình duyệt: `chrome`, `firefox`, `edge`, `safari` | ✅ Có |
| `HEADLESS` | Chạy ẩn (true/false) | ✅ Có |
| `URL_CRM` | URL website cần test | ✅ **Bắt buộc** — thay bằng URL dự án mới |
| `TARGET` | `local` hoặc `remote` | ✅ Có |
| `REMOTE_URL` | URL Selenium Grid (nếu dùng remote) | Nếu cần |
| `PROJECT_NAME` | Tên hiển thị trên báo cáo | ✅ Có |
| `REPORT_TITLE` | Tiêu đề báo cáo | ✅ Có |
| `SEND_REPORT_TO_TELEGRAM` | Gửi báo cáo Telegram: `yes`/`no` | Nếu cần |
| `TELEGRAM_TOKEN` | Token bot Telegram | Nếu dùng Telegram |
| `TELEGRAM_CHATID` | Chat ID Telegram | Nếu dùng Telegram |
| `WAIT_IMPLICIT` | Thời gian chờ implicit (giây) | Tùy chỉnh |
| `WAIT_EXPLICIT` | Thời gian chờ explicit (giây) | Tùy chỉnh |
| `WAIT_PAGE_LOADED` | Thời gian chờ load page (giây) | Tùy chỉnh |
| `SCREENSHOT_PASSED_TCS` | Chụp ảnh khi pass: `yes`/`no` | Tùy chọn |
| `SCREENSHOT_FAILED_TCS` | Chụp ảnh khi fail: `yes`/`no` | Tùy chọn |

**Ví dụ sau khi thay:**
```properties
AUTHOR = Your Name
BROWSER = chrome
HEADLESS = false
URL_CRM = https://your-project-url.com
TARGET = local
PROJECT_NAME = Your Project Automation
REPORT_TITLE = Test Report | Your Project
```

---

### 3️⃣ `data.properties` — Tài khoản và đường dẫn file dữ liệu

**Vị trí:** `src/test/resources/config/data.properties`

| Key | Mô tả | Cần thay đổi? |
|---|---|---|
| `EXCEL_DATA_FILE_PATH` | Đường dẫn file Excel test data | ✅ Có |
| `JSON_DATA_FILE_PATH` | Đường dẫn file JSON | ✅ Có |
| `email` | Email đăng nhập mặc định | ✅ **Bắt buộc** |
| `password` | Mật khẩu mặc định | ✅ **Bắt buộc** |

**Ví dụ sau khi thay:**
```properties
EXCEL_DATA_FILE_PATH = src/test/resources/testdata/TestData.xlsx
email = admin@yourproject.com
password = yourpassword123
```

---

### 4️⃣ Tạo thư mục dự án mới trong `projects/`

**Vị trí:** `src/test/java/com/anhtester/projects/`

Tạo package mới theo tên dự án, ví dụ: `yourproject`

```
projects/
└── yourproject/           ← Tạo mới package này
    ├── models/            ← Data model (POJO class) — chứa dữ liệu test
    ├── pages/             ← Page Object — mỗi page 1 file Java
    └── testcases/         ← Test class — kế thừa BaseTest
```

**Tham khảo cấu trúc có sẵn:**
- Models: `projects/crm/models/SignInModel.java`
- Pages: `projects/crm/pages/SignInPage.java`
- Testcases: `projects/crm/testcases/SignInTest.java`

---

### 5️⃣ File locators (properties) trong `objects/`

**Vị trí:** `src/test/resources/objects/`

Tạo file `.properties` mới chứa locators cho dự án:
```
objects/
└── yourproject_locators.properties   ← Tạo file mới
```

**Format locator:**
```properties
# Trang Login
login.txt_username = //input[@name='username']
login.txt_password = //input[@name='password']
login.btn_signin = //button[@type='submit']

# Trang Dashboard
dashboard.lbl_welcome = //h1[contains(text(),'Welcome')]
```

---

### 6️⃣ TestNG Suite XML

**Vị trí:** `src/test/resources/suites/`

Tạo file XML suite mới cho dự án:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="YourProject Suite" verbose="1" parallel="classes" thread-count="3">

    <listeners>
        <listener class-name="com.anhtester.listeners.TestListener"/>
        <listener class-name="com.anhtester.listeners.ExtentReportManager"/>
    </listeners>

    <test name="Login Test" preserve-order="true">
        <classes>
            <class name="com.anhtester.projects.yourproject.testcases.SignInTest"/>
        </classes>
    </test>

</suite>
```

---

### 7️⃣ File test data (Excel/JSON)

**Vị trí:** `src/test/resources/`

Tạo thư mục riêng cho dữ liệu test:
```
testdata/                    ← Thư mục mới
├── LoginData.xlsx
├── TestData.xlsx
└── ...
```

Cập nhật lại đường dẫn trong `data.properties`.

---

## 🔄 QUY TRÌNH KHỞI TẠO DỰ ÁN MỚI (STEP BY STEP)

```
Bước 1: Copy thư mục "demo" → đổi tên theo tên dự án
Bước 2: Sửa pom.xml (groupId, artifactId, name)
Bước 3: Sửa config.properties (URL, browser, tên báo cáo)
Bước 4: Sửa data.properties (email, password, đường dẫn file)
Bước 5: Tạo package mới trong projects/ (models, pages, testcases)
Bước 6: Tạo file locators .properties trong objects/
Bước 7: Tạo TestNG suite XML trong suites/
Bước 8: Tạo thư mục testdata/ với file Excel/JSON
Bước 9: Xóa hoặc giữ lại code CRM/CMS mẫu (tùy nhu cầu)
Bước 10: Chạy thử 1 test case để xác nhận setup đúng
```

---

## 🗑️ CÓ THỂ XÓA KHI KHÔNG CẦN (Dữ liệu demo)

| Thư mục/File | Mô tả |
|---|---|
| `src/test/java/.../projects/crm/` | Code test CRM mẫu |
| `src/test/java/.../projects/cms/` | Code test CMS mẫu |
| `src/test/resources/testdataCRM/` | File Excel/TXT dữ liệu CRM |
| `src/test/resources/testdataCMS/` | File Excel dữ liệu CMS |
| `src/test/resources/objects/crm_locators.properties` | Locators CRM |
| `src/test/resources/suites/CRM/` | Suite XML CRM |
| `src/test/resources/suites/CMS/` | Suite XML CMS |
| `src/test/java/.../practice/` | Các test luyện tập |

---

## ⚙️ CÁC FILE CORE FRAMEWORK (KHÔNG CẦN SỬA)

Các file sau là phần lõi của framework, **không cần chỉnh sửa** cho dự án mới:

| Package | Mô tả |
|---|---|
| `keywords/WebUI.java` | Thư viện hàm tương tác browser |
| `driver/DriverManager.java` | Quản lý WebDriver thread-safe |
| `helpers/` | Excel, JSON, PDF, Database helpers |
| `reports/` | Allure & ExtentReports |
| `config/ConfigFactory.java` | Đọc config.properties |
| `listeners/` | TestNG Listeners tự động |
| `common/BaseTest.java` | Base class cho tất cả test |

---

## 📌 GHI CHÚ QUAN TRỌNG

> ⚠️ **Đừng sửa code trong `src/main/java/`** trừ khi muốn nâng cấp framework core.

> ⚠️ **Package name:** Nếu đổi `groupId` trong `pom.xml`, cần **refactor package** trong tất cả file Java (hiện tại là `com.anhtester`). Có thể dùng IntelliJ → Refactor → Rename để đổi toàn bộ.

> ✅ **BaseTest:** Mọi test class đều phải `extends BaseTest` để được khởi tạo driver và report tự động.

> ✅ **Page Object:** Mỗi Page Object class nên `extends BaseTest` hoặc nhận `WebDriver` qua constructor.

> ✅ **Locators trong file .properties:** Đọc qua `ObjectRepository` helper — không hardcode XPath trong code Java.

---

*Framework Demo — Khởi tạo từ AutomationFrameworkSelenium by Anh Tester*
