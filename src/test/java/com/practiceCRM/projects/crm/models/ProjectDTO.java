package com.practiceCRM.projects.crm.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProjectDTO - Data Transfer Object chứa dữ liệu của 1 dự án (Project),
 * phục vụ cho việc đọc từ file Excel import, DataProvider hoặc tạo mới dự án.
 * Đảm bảo tính Thread-Safe khi chạy test song song (Parallel execution).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private String title;
    private String projectType;
    private String client;
    private String description;
    private String labels;
    private String startDate;
    private String deadline;
    private String status;
    private String price;
    private String projectMembers;
}
