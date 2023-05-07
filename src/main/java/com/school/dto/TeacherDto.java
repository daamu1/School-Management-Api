package com.school.dto;

import com.school.entity.Teacher;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherDto {
    private Long id;
    private String teacherName;
    private int age;
    public  TeacherDto(Teacher teacher)
    {
        this.id=teacher.getId();
        this.age=teacher.getAge();
        this.teacherName=teacher.getTeacherName();
    }
}
