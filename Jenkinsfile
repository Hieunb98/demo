pipeline {
    // SỬ DỤNG DOCKER AGENT: Môi trường đóng gói sẵn Maven 3.9 + Java 17 LTS (Eclipse Temurin)
    // Tự động kéo image về, tạo container chạy test và tự huỷ sau khi xong (Ephemeral Agent)
    agent {
        docker {
            image 'maven:3.9-eclipse-temurin-17'
            args '-v $HOME/.m2:/root/.m2' // Mount cache Maven để các lần sau chạy nhanh hơn
        }
    }

    // CẤU HÌNH DỌN DẸP LỊCH SỬ BUILD (Pipeline as Code)
    // Tự động giữ lại tối đa 10 lần chạy gần nhất và lưu trữ trong tối đa 7 ngày
    options {
        buildDiscarder(logRotator(daysToKeepStr: '7', numToKeepStr: '10'))
        disableConcurrentBuilds() // Không cho chạy trùng 2 build cùng lúc trên 1 nhánh
    }

    // 1. CÁC THAM SỐ ĐẦU VÀO (Đồng bộ chuẩn 100% với file maven.yml của GitHub Actions)
    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'edge', 'firefox'],
            description: 'Chọn trình duyệt kiểm thử'
        )
        choice(
            name: 'SUITE',
            choices: [
                'SuiteAll.xml',
                'CRM/SignIn-parallel-methods.xml',
                'CRM/Dashboard-parallel-methods.xml',
                'CRM/TopBarTest-parallel-methods.xml',
                'CRM/Project-parallel-methods.xml'
            ],
            description: 'Chọn Test Suite XML muốn chạy'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Chạy ngầm không mở giao diện (Headless mode)'
        )
        choice(
            name: 'TARGET',
            choices: ['remote', 'local'],
            description: 'Môi trường thực thi (remote: Selenium Grid Docker / local: máy host)'
        )
    }

    environment {
        // Đường dẫn suite XML tương ứng
        SUITE_PATH = "src/test/resources/suites/${params.SUITE}"
    }

    stages {
        stage('1. Checkout Source Code') {
            steps {
                echo '📥 Đang kéo mã nguồn mới nhất từ Git Repository...'
                checkout scm
            }
        }

        stage('2. Execute Automation Tests') {
            steps {
                echo "🚀 Bắt đầu thực thi Test Suite: ${params.SUITE}"
                echo "Trình duyệt: ${params.BROWSER} | Headless: ${params.HEADLESS} | Target: ${params.TARGET}"

                // Chạy Maven với các tham số tương tự GitHub Actions
                // Thêm -Dmaven.test.failure.ignore=true để khi có test fail vẫn tiếp tục sinh Report và lưu video/ảnh
                sh """
                    mvn clean test --file pom.xml \
                        -DsuiteXmlFile="${env.SUITE_PATH}" \
                        -DBROWSER="${params.BROWSER}" \
                        -DHEADLESS="${params.HEADLESS}" \
                        -DTARGET="${params.TARGET}" \
                        -Dmaven.test.failure.ignore=true
                """
            }
        }

        stage('3. Generate Allure Report HTML') {
            steps {
                echo '📊 Khởi tạo báo cáo Allure Report...'
                // Tạo báo cáo HTML từ file pom.xml
                sh 'mvn allure:report --file pom.xml -Dmaven.test.failure.ignore=true'
            }
        }
    }

    // 4. CÁC HÀNH ĐỘNG SAU KHI TEST XONG (POST ACTIONS)
    post {
        always {
            echo '📦 Đang thu thập Báo cáo và Artifacts...'

            // 4.1. Publish Allure Report trực tiếp lên Jenkins Dashboard
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            // 4.2. Lưu trữ Extent Report, Screenshots và Videos làm Artifacts trên Jenkins
            archiveArtifacts artifacts: '''
                exports/ExtentReports/**,
                exports/ExportData/Images/**,
                exports/ExportData/Videos/**,
                target/surefire-reports/**
            ''', allowEmptyArchive: true

            // 4.3. Gửi thông báo tổng kết và đính kèm file ExtentReports lên Telegram Bot
            script {
                try {
                    withCredentials([
                        string(credentialsId: 'telegram-bot-token', variable: 'TELEGRAM_TOKEN'),
                        string(credentialsId: 'telegram-bot-id', variable: 'TELEGRAM_CHATID')
                    ]) {
                        sh '''
                            BUILD_STATUS="${currentBuild.currentResult}"
                            STATUS_EMOJI="✅ PASS TOÀN BỘ"
                            if [ "$BUILD_STATUS" != "SUCCESS" ]; then
                                STATUS_EMOJI="❌ CÓ TEST THẤT BẠI / LỖI"
                            fi

                            NOW=$(date '+%d/%m/%Y %H:%M:%S')
                            ALLURE_URL="${BUILD_URL}allure"

                            # 1. Gửi tin nhắn tóm tắt qua HTTP API Telegram
                            MESSAGE="🚀 <b>KẾT QUẢ AUTOMATION TEST CRM (JENKINS)</b> 🚀%0A"
                            MESSAGE="${MESSAGE}Trạng thái: <b>${STATUS_EMOJI}</b>%0A"
                            MESSAGE="${MESSAGE}━━━━━━━━━━━━━━━━━━━%0A"
                            MESSAGE="${MESSAGE}🔖 Build: <b>#${BUILD_NUMBER} (${JOB_NAME})</b>%0A"
                            MESSAGE="${MESSAGE}🕒 Thời gian: <b>${NOW}</b>%0A"
                            MESSAGE="${MESSAGE}🌐 Suite: <b>${SUITE}</b> | Browser: <b>${BROWSER}</b>%0A"
                            MESSAGE="${MESSAGE}━━━━━━━━━━━━━━━━━━━%0A"
                            MESSAGE="${MESSAGE}🔗 <a href=\\"${ALLURE_URL}\\">Xem Allure Report trên Jenkins</a>"

                            curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage" \
                                -d chat_id="${TELEGRAM_CHATID}" \
                                -d text="${MESSAGE}" \
                                -d parse_mode="HTML" || true

                            # 2. Gửi đính kèm file ExtentReports.html nếu tồn tại
                            REPORT_FILE="exports/ExtentReports/ExtentReports.html"
                            if [ -f "$REPORT_FILE" ]; then
                                curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendDocument" \
                                    -F "chat_id=${TELEGRAM_CHATID}" \
                                    -F "document=@${REPORT_FILE}" \
                                    -F "caption=📊 Báo cáo chi tiết ExtentReports (Build #${BUILD_NUMBER})" || true
                            fi
                        '''
                    }
                } catch (Exception e) {
                    echo "ℹ️ Bỏ qua gửi Telegram: Chưa cấu hình Credential 'telegram-bot-token' hoặc 'telegram-chat-id' trên Jenkins."
                }
            }

            // 4.4. Dọn sạch file rác trong workspace sau khi đã lưu trữ xong báo cáo
            cleanWs()

            echo '✅ Hoàn tất pipeline kiểm thử tự động!'
        }

        success {
            echo '🎉 Chúc mừng! Toàn bộ test cases trong Suite đã PASS thành công!'
        }

        failure {
            echo '❌ Cảnh báo! Pipeline thực thi thất bại hoặc gặp lỗi hệ thống.'
        }
    }
}