# 📘 CẨM NANG TOÀN DIỆN VỀ PROJECT LOMBOK TRONG JAVA & AUTOMATION TESTING

> **Nguyên tắc biên soạn:** Mỗi thành phần đều có cấu trúc: **[Ví Dụ Code Định Nghĩa Class] ➡️ [Ví Dụ Việc Sử Dụng Thực Tế (Usage)] ➡️ [Kết Quả / Output Thực Tế]**.

---

## 📑 MỤC LỤC

1. [Lombok là gì? Bản chất công nghệ cốt lõi](#1-lombok-là-gì-bản-chất-công-nghệ-cốt-lõi)
2. [Tại sao cần dùng Lombok? (So sánh Trước & Sau)](#2-tại-sao-cần-dùng-lombok-so-sánh-trước--sau)
3. [Chi tiết Các Annotations: Code Định Nghĩa & Cách Sử Dụng](#3-chi-tiết-các-annotations-code-định-nghĩa--cách-sử-dụng)
   - 3.1. `@Getter` & `@Setter`
   - 3.2. `@ToString` (In thông tin đối tượng & Điều tra Assert fail)
   - 3.3. `@EqualsAndHashCode` (So sánh giá trị 2 Object)
   - 3.4. Bộ ba Constructor: `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`
   - 3.5. `@Data` (Combo toàn diện) & `@Value` (Lớp bất biến)
   - 3.6. `@Builder` (Fluent Builder Pattern cho Test Data)
   - 3.7. `@UtilityClass` (Dành cho Constants & Helpers)
   - 3.8. `@SneakyThrows` (Xử lý Exception ngầm không cần try-catch)
   - 3.9. `@Log4j2` / `@Slf4j` (Ghi log tự động)
   - 3.10. `@NonNull` (Kiểm tra null tham số)
4. [Kịch bản Thực Chiến Trong Framework Selenium / TestNG](#4-kịch-bản-thực-chiến-trong-framework-selenium--testng)
5. [Cấu hình Maven & IDE](#5-cấu-hình-maven--ide)
6. [Cạm bẫy & Best Practices cần nhớ](#6-cạm-bẫy--best-practices-cần-nhớ)

---

## 1. LOMBOK LÀ GÌ? BẢN CHẤT CÔNG NGHỆ CỐT LÕI

* **Định nghĩa:** Project Lombok là thư viện Java tự động sinh mã boilerplate (`getter`, `setter`, `constructor`, `toString`, `builder`...) thông qua các Annotations.
* **Cơ chế JSR 269:** Lombok can thiệp vào giai đoạn biên dịch (**Compile-time**) trên cây cú pháp trừu tượng (**Abstract Syntax Tree - AST**) của `javac`.
* **Zero Runtime Overhead:** Bytecode hoàn chỉnh được sinh trực tiếp vào file `.class`. Khi chạy (Runtime), hiệu năng **đạt 100% như code viết tay thuần**, không tốn thêm RAM hay CPU.

---

## 2. TẠI SAO CẦN DÙNG LOMBOK? (SO SÁNH TRƯỚC & SAU)

| Chưa dùng Lombok (Java Thuần) | Đã dùng Lombok |
| :--- | :--- |
| Tốn **100 - 150 dòng code** chỉ để viết getter, setter, constructor, toString cho 1 Model. | Chỉ cần **1 dòng annotation** (`@Data` hoặc `@Builder`). |
| Khi thêm 1 trường dữ liệu mới: phải generate lại toàn bộ getter, setter, constructor, toString. | Thêm trường dữ liệu: Lombok **tự động cập nhật ngầm** tất cả hàm tương ứng. |
| Code rối mắt, khó phân biệt đâu là dữ liệu chính, đâu là mã râu ria. | Code sạch (Clean code), dễ đọc, dễ bảo trì. |

---

## 3. CHI TIẾT CÁC ANNOTATIONS: CODE ĐỊNH NGHĨA & CÁCH SỬ DỤNG

---

### 3.1. `@Getter` & `@Setter`
> Tự động sinh hàm `getX()` và `setX(...)` cho các thuộc tính.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter // Tự động sinh Getter cho tất cả field trong class
@Setter // Tự động sinh Setter cho tất cả field trong class
public class ProjectDTO {

    private String title;
    private double budget;

    // Field này chỉ cho phép đọc trong cùng package
    @Getter(AccessLevel.PACKAGE)
    private String internalCode;

    // Field này KHÔNG sinh setter (bất biến sau khi gán)
    @Setter(AccessLevel.NONE)
    private String createdDate = "2026-09-12";
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế (Trong Test / Page):
```java
@Test
public void testProjectGetterSetter() {
    ProjectDTO project = new ProjectDTO();

    // 1. Sử dụng các hàm Setter do Lombok sinh ra
    project.setTitle("CRM Automation");
    project.setBudget(5000.0);
    // project.setCreatedDate("..."); // ❌ LỖI BIÊN DỊCH: Hàm setCreatedDate không tồn tại vì có @Setter(AccessLevel.NONE)

    // 2. Sử dụng các hàm Getter do Lombok sinh ra
    System.out.println("Tên dự án: " + project.getTitle());
    System.out.println("Ngân sách: " + project.getBudget());
    System.out.println("Ngày tạo: " + project.getCreatedDate());

    // 3. Sử dụng trong Assertions của TestNG
    Assert.assertEquals(project.getTitle(), "CRM Automation", "Tên dự án không khớp!");
}
```

---

### 3.2. `@ToString`
> Tự động sinh hàm `toString()` biểu diễn toàn bộ tên trường và giá trị của Object dạng chuỗi text.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.models;

import lombok.ToString;

@ToString
public class UserAccount {

    private String username;
    private String email;
    private String role;

    @ToString.Exclude // 🔒 Loại trừ trường nhạy cảm: không in mật khẩu ra log/console
    private String password;

    public UserAccount(String username, String email, String role, String password) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế (Trong Test / Log / Assert):
```java
@Test
public void testUserToString() {
    UserAccount user = new UserAccount("admin", "admin@example.com", "Manager", "Secret@123");

    // Trường hợp 1: In trực tiếp ra Console hoặc file Log
    LogUtils.info("Thông tin tài khoản đang test: " + user);

    // Trường hợp 2: Khi câu lệnh Assert trong TestNG bị FAIL
    UserAccount expectedUser = new UserAccount("admin", "admin@example.com", "Admin", "Secret@123");
    Assert.assertEquals(user, expectedUser, "Thông tin tài khoản không đúng kỳ vọng!");
}
```

👉 **Kết Quả / Output Hiển Thị Thực Tế:**
* **Console / Log hiển thị:** (Password đã được giấu an toàn nhờ `@ToString.Exclude`):
  ```text
  Thông tin tài khoản đang test: UserAccount(username=admin, email=admin@example.com, role=Manager)
  ```
* **Khi Assert FAIL trong TestNG Report / Allure:**
  ```text
  java.lang.AssertionError: Thông tin tài khoản không đúng kỳ vọng!
  Expected :UserAccount(username=admin, email=admin@example.com, role=Admin)
  Actual   :UserAccount(username=admin, email=admin@example.com, role=Manager)
  ```
  *(Nhìn vào log thấy ngay lệch ở trường `role`: kỳ vọng `Admin` nhưng thực tế là `Manager`! Nếu KHÔNG có `@ToString`, TestNG chỉ báo 2 địa chỉ ô nhớ vô nghĩa như `UserAccount@5e265ba4` vs `UserAccount@4f1a23b`).*

---

### 3.3. `@EqualsAndHashCode`
> Tự động so sánh hai Object theo **giá trị dữ liệu thực tế** bên trong, thay vì so sánh địa chỉ ô nhớ mặc định của Java.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class ClientInfo {
    private String companyName;
    private String vatNumber;
    private String phone;
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testCompareTwoObjects() {
    // Tạo 2 đối tượng độc lập ở 2 vùng nhớ khác nhau nhưng cùng nội dung dữ liệu
    ClientInfo client1 = new ClientInfo("Acme Corp", "VAT123", "0988123456");
    ClientInfo client2 = new ClientInfo("Acme Corp", "VAT123", "0988123456");

    // 1. So sánh bằng equals()
    boolean isEqual = client1.equals(client2);
    System.out.println("client1 bằng client2? " + isEqual); // In ra: true

    // 2. Dùng trong TestNG Assert
    Assert.assertEquals(client1, client2, "Dữ liệu 2 client không trùng khớp!");

    // 3. Dùng trong Set để tự động lọc bản ghi trùng lặp
    Set<ClientInfo> clientSet = new HashSet<>();
    clientSet.add(client1);
    clientSet.add(client2);
    System.out.println("Số lượng client duy nhất trong Set: " + clientSet.size()); // In ra: 1 (đã tự loại trùng!)
}
```

---

### 3.4. Bộ Ba Constructor: `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`
> Tự động sinh các hàm dựng không tham số, đầy đủ tham số, hoặc tham số bắt buộc.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor       // Tạo: ProjectItem()
@AllArgsConstructor      // Tạo: ProjectItem(id, code, title, price)
@RequiredArgsConstructor // Tạo: ProjectItem(id, code) -> chỉ gồm final và @NonNull
public class ProjectItem {

    private final int id;          // Trường final (bắt buộc)
    @NonNull private String code;  // Trường @NonNull (bắt buộc)
    private String title;          // Trường optional
    private double price;          // Trường optional
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testConstructors() {
    // 1. Dùng @NoArgsConstructor: Rất quan trọng khi dùng Jackson / Gson parse từ file JSON
    ProjectItem p1 = new ProjectItem();

    // 2. Dùng @RequiredArgsConstructor: Chỉ cần truyền các trường bắt buộc
    ProjectItem p2 = new ProjectItem(101, "PRJ_001");

    // 3. Dùng @AllArgsConstructor: Truyền đầy đủ tất cả các trường
    ProjectItem p3 = new ProjectItem(102, "PRJ_002", "Website CRM", 8000.0);
}
```

---

### 3.5. `@Data` (Combo Toàn Diện) & `@Value` (Lớp Bất Biến)

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.models;

import lombok.Data;
import lombok.Value;

// Dành cho Model dữ liệu bình thường (có thể sửa đổi)
@Data // Tương đương: @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
public class TaskData {
    private String taskTitle;
    private String assignedTo;
    private String status;
}

// Dành cho Dữ liệu cấu hình bất biến (Không cho phép thay đổi sau khi tạo)
@Value // Biến toàn bộ class thành final, tất cả field là private final, chỉ sinh Getter (không sinh Setter)
public class EnvironmentConfig {
    String baseUrl;
    int timeoutSeconds;
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testDataAndValue() {
    // Sử dụng @Data: Đầy đủ getter, setter, toString
    TaskData task = new TaskData();
    task.setTaskTitle("Test Login UI");
    task.setStatus("In Progress");
    LogUtils.info("Task hiện tại: " + task); // Tự động gọi toString()

    // Sử dụng @Value: Bất biến và an toàn tuyệt đối
    EnvironmentConfig env = new EnvironmentConfig("https://rise.anhtester.com", 30);
    System.out.println("URL: " + env.getBaseUrl());
    // env.setTimeoutSeconds(10); // ❌ LỖI: Class @Value không có hàm setter nào!
}
```

---

### 3.6. `@Builder` (Fluent Builder Pattern cho Test Data)
> Tạo đối tượng linh hoạt theo chuỗi hàm chaining, không cần nhớ thứ tự tham số.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ProjectTestData {

    private String title;
    private String client;
    private String price;
    private String startDate;
    private String deadline;

    @Builder.Default // 🌟 Đặt giá trị mặc định nếu người dùng không truyền
    private String status = "Open";

    @Builder.Default
    private String label = "AI_automation";
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testBuilderPattern() {
    // Trường hợp 1: Tạo dự án đầy đủ thông tin
    ProjectTestData fullProject = ProjectTestData.builder()
            .title("Full Features Project")
            .client("Acme Corp")
            .price("10000")
            .startDate("2026-09-01")
            .deadline("2026-09-30")
            .status("Completed")
            .build();

    // Trường hợp 2: Tạo dự án chỉ với các trường bắt buộc (các trường khác tự lấy mặc định)
    ProjectTestData simpleProject = ProjectTestData.builder()
            .title("Required Only Project")
            .price("2000")
            .build(); // status tự động là "Open", label tự động là "AI_automation"

    // 3. Truyền trực tiếp vào hàm Page Object
    projectPage.addNewProject(simpleProject);
}
```

👉 **Kết Quả / Output Hiển Thị:**
```text
simpleProject: ProjectTestData(title=Required Only Project, client=null, price=2000, startDate=null, deadline=null, status=Open, label=AI_automation)
```

---

### 3.7. `@UtilityClass` (Dành Cho Constants & Helpers)
> Dành cho các class chỉ chứa hàm tĩnh (`SystemHelpers`, `DecodeUtils`, `DriverManager`...).

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.helpers;

import lombok.experimental.UtilityClass;
import java.io.File;

@UtilityClass // 🌟 Lombok tự động:
              // 1. Biến class thành final
              // 2. Tự sinh private constructor ném Exception nếu cố tình "new SystemHelpers()"
              // 3. Tự động biến TẤT CẢ methods và fields bên trong thành STATIC
public class SystemHelpers {

    // Không cần gõ từ khóa "static", Lombok tự biến thành static!
    private final String USER_DIR = System.getProperty("user.dir");

    public String getCurrentDir() {
        return USER_DIR + File.separator;
    }

    public void createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testUtilityClass() {
    // 1. Gọi trực tiếp tên hàm qua ClassName (vì đã tự động là static)
    String currentPath = SystemHelpers.getCurrentDir();
    System.out.println("Thư mục hiện tại: " + currentPath);

    SystemHelpers.createFolder(currentPath + "exports/screenshots");

    // 2. Chống khởi tạo sai:
    // SystemHelpers helper = new SystemHelpers(); // ❌ LỖI BIÊN DỊCH NGAY: Constructor SystemHelpers() is private
}
```

---

### 3.8. `@SneakyThrows` (Xử Lý Exception Ngầm Không Cần Try-Catch)
> Loại bỏ sạch sẽ các khối `try-catch` lồng cồng kềnh khi đọc file, parse JSON, hoặc `Thread.sleep`.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.helpers;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class FileHelpers {

    // 🌟 Không cần khai báo: "throws IOException, FileNotFoundException"
    // 🌟 Không cần bọc khối try/catch ném RuntimeException rườm rà
    @SneakyThrows
    public String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                content.append(buffer, 0, read);
            }
        }
        return content.toString();
    }

    @SneakyThrows
    public void pause(long milliseconds) {
        Thread.sleep(milliseconds); // Không cần bọc try-catch InterruptedException
    }
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế (Trong Test Class):
```java
@Test
public void testReadFile() {
    // Gọi hàm đọc file trực tiếp, code sạch sẽ không cần try-catch
    String data = FileHelpers.readFileContent("src/test/resources/config/data.properties");
    Assert.assertTrue(data.contains("URL_CRM"), "File data.properties không chứa key URL_CRM!");

    FileHelpers.pause(2000);
}
```

---

### 3.9. `@Log4j2` / `@Slf4j` (Ghi Log Tự Động)
> Tự động inject biến `log` chuẩn mực mà không cần gõ `LoggerFactory.getLogger(...)`.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.services;

import lombok.extern.log4j.Log4j2; // hoặc lombok.extern.slf4j.Slf4j

@Log4j2 // 🌟 Lombok tự động sinh: private static final Logger log = LogManager.getLogger(AuthService.class);
public class AuthService {

    public boolean login(String email, String password) {
        log.info("Bắt đầu đăng nhập cho tài khoản: {}", email);

        if (password == null || password.isEmpty()) {
            log.error("Mật khẩu không được để trống!");
            return false;
        }

        log.info("Đăng nhập thành công cho user: {}", email);
        return true;
    }
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testLogging() {
    AuthService auth = new AuthService();
    auth.login("admin@example.com", "123456");
}
```

👉 **Kết Quả / Output Hiển Thị Trên Console / Log:**
```text
2026-09-12 14:15:00 [INFO ] [AuthService] - Bắt đầu đăng nhập cho tài khoản: admin@example.com
2026-09-12 14:15:01 [INFO ] [AuthService] - Đăng nhập thành công cho user: admin@example.com
```

---

### 3.10. `@NonNull` (Kiểm Tra Null Tham Số)
> Ngăn chặn lỗi `NullPointerException` khó truy vết bằng cách bắt lỗi ngay tại cửa vào hàm.

#### 1️⃣ Ví Dụ Code Định Nghĩa Class:
```java
package com.practiceCRM.keywords;

import lombok.NonNull;
import org.openqa.selenium.By;

public class ActionKeywords {

    // Đánh dấu @NonNull vào tham số
    public static void clickElement(@NonNull By by, @NonNull String elementName) {
        System.out.println("Đang click vào phần tử: " + elementName);
        // thực hiện click...
    }
}
```

#### 2️⃣ Ví Dụ Việc Sử Dụng Thực Tế:
```java
@Test
public void testNonNullValidation() {
    // Trường hợp 1: Truyền đúng
    ActionKeywords.clickElement(By.id("btnSubmit"), "Nút Submit");

    // Trường hợp 2: Vô tình truyền null vào tham số
    ActionKeywords.clickElement(null, "Nút Login");
}
```

👉 **Kết Quả / Output Bắt Lỗi Rõ Ràng:**
```text
java.lang.NullPointerException: by is marked non-null but is null
    at com.practiceCRM.keywords.ActionKeywords.clickElement(ActionKeywords.java:10)
```
*(Chỉ rõ ngay lập tức tham số `by` bị null tại dòng nào, không bị crash sâu bên trong Selenium).*

---

## 4. KỊCH BẢN THỰC CHIẾN TRONG FRAMEWORK SELENIUM / TESTNG

### Tình huống: Tái cấu trúc hàm tạo dự án có 8 tham số rời rạc

#### ❌ Cách làm cũ trong Page Object:
```java
// Hàm nhận tới 8 tham số String:
public void addNewProject(String title, String type, String client, String price, 
                          String desc, String startDate, String deadline, String label) {
    setText(inputTitle, title);
    // ...
}

// Khi gọi trong Test: Nhìn rất rối và cực kỳ dễ nhầm vị trí giữa các chuỗi rỗng:
projectPage.addNewProject("Dự án A", "", "", "5000", "Mô tả", "2026-09-01", "2026-09-30", "Label1");
```

#### ✅ Cách làm mới với Lombok `@Builder` + `@Data`:
```java
// 1. Định nghĩa DTO
@Data
@Builder
public class ProjectDTO {
    private String title;
    private String type;
    private String client;
    private String price;
    private String description;
    private String startDate;
    private String deadline;
    private String label;
}

// 2. Viết hàm trong Page Object chỉ nhận 1 Object duy nhất:
public void addNewProject(ProjectDTO project) {
    if (project.getTitle() != null) setText(inputTitle, project.getTitle());
    if (project.getPrice() != null) setText(inputPrice, project.getPrice());
    if (project.getDescription() != null) setText(inputDesc, project.getDescription());
    clickElement(btnSubmit);
}

// 3. Sử dụng trong Test Class (Cực kỳ sáng sủa và rõ ràng):
@Test
public void testCreateProject() {
    ProjectDTO data = ProjectDTO.builder()
            .title("Dự án Automation")
            .price("8000")
            .description("Dự án thử nghiệm Lombok")
            .build();

    projectPage.addNewProject(data);
}
```

---

## 5. CẤU HÌNH MAVEN & IDE

### 1. Maven `pom.xml`
```xml
<!-- Dependency Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.42</version>
    <scope>provided</scope>
</dependency>

<!-- Cấu hình Compiler Plugin nhận diện Annotation Processor -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.14.1</version>
    <configuration>
        <source>17</source>
        <target>17</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.42</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### 2. Cài đặt trên IDE
* **IntelliJ IDEA:** Vào **Settings** (`Cmd + ,` hoặc `Ctrl + Alt + S`) ➡️ **Build, Execution, Deployment** ➡️ **Compiler** ➡️ **Annotation Processors** ➡️ Tích chọn **Enable annotation processing**.
* **VS Code:** Cài đặt Extension **Lombok Annotations Support for VS Code**.

---

## 6. CẠM BẪY & BEST PRACTICES CẦN NHỚ

1. **Tên cột cố định (Excel Header / Key JSON):** Dùng `public static final String` (Constants) thay vì dùng Lombok Getter.
2. **`@Builder` làm mất Default Constructor:** Khi dùng `@Builder` với các thư viện serialize như Jackson, luôn bổ sung thêm cả **`@NoArgsConstructor` + `@AllArgsConstructor`**.
3. **Giá trị mặc định trong `@Builder`:** Phải đánh dấu **`@Builder.Default`**, nếu không giá trị gán sẵn sẽ bị gán đè thành `null`.
4. **Kế thừa Class cha:** Luôn khai báo `@ToString(callSuper = true)` và `@EqualsAndHashCode(callSuper = true)` để in và so sánh được các trường của lớp cha.
