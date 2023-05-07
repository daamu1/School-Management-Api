package com.school.dto;

import com.school.entity.Student;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDto {
    private Long id;
    private String studentName;
    private String fatherName;
    private String motherName;
    public StudentDto(Student student)
    {
        this.id=student.getId();
        this.fatherName=student.getFatherName();
        this.studentName=student.getStudentName();
        this.motherName=student.getMotherName();
    }
}
