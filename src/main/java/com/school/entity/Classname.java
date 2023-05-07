package com.school.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classname")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Classname {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private int totalNoSection;
    private String schoolName;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "class_teacher", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JsonIgnore
    private List<Teacher> teacher;
    @OneToMany(mappedBy = "className", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Student> student;

    public Classname(List<Teacher> teacher) {
        this.teacher = teacher;
    }

    public void addStudent(Student students) {

        if (student == null) {
            student = new ArrayList<Student>();
        }

        student.add(students);

        students.setClassName(this);
    }

    public void addTeacher(Teacher teacher1) {

        if (teacher == null) {
            teacher = new ArrayList<Teacher>();
        }

        teacher.add(teacher1);
        new Classname(teacher);
    }


}
