package com.school.dto;

import com.school.entity.Classname;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClassDto {
    private Long id;
    private String className;
    private int totalNoSection;
    private String schoolName;
    public ClassDto(Classname classname)
    {
        this.id=classname.getId();
        this.className=classname.getClassName();
        this.schoolName=classname.getSchoolName();
        this.totalNoSection=classname.getTotalNoSection();
    }
}
