package com.practiceCRM.projects.crm.models;

import com.poiji.annotation.ExcelCellName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProjectDTO - Data Transfer Object chứa dữ liệu của 1 dự án (Project),
 * phục vụ cho việc đọc từ file Excel import, DataProvider hoặc tạo mới dự án.
 * Đảm bảo tính Thread-Safe khi chạy test song song (Parallel execution).
 * Hỗ trợ map tự động trực tiếp từ file Excel với thư viện Poiji qua @ExcelCellName.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @ExcelCellName("Title")
    private String title;

    @ExcelCellName("Project Type")
    private String projectType;

    @ExcelCellName("Client")
    private String client;

    @ExcelCellName("Description")
    private String description;

    @ExcelCellName("Labels")
    private String labels;

    @ExcelCellName("Start Date")
    private String startDate;

    @ExcelCellName("Deadline")
    private String deadline;

    @ExcelCellName("Status")
    private String status;

    @ExcelCellName("Price")
    private String price;

    @ExcelCellName("Project Members")
    private String projectMembers;
}
