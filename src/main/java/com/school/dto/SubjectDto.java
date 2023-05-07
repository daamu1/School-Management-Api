package com.school.dto;

import com.school.entity.Subject;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectDto {
    private Long id;
    private String subjectName;
    private int totolNoUnit;

    public SubjectDto(Subject subject) {
        this.id = subject.getId() ;
        this.subjectName=subject.getSubjectName();
        this.totolNoUnit=subject.getTotolNoUnit();
    }
}