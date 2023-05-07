package com.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teacherName;
    private int age;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "class_teacher", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
    @JsonIgnore
    private List<Classname> classname;
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Address> addresses;
    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Subject> subjects;

    public void addAddress(Address address) {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        addresses.add(address);
        address.setTeacher(this);
    }

    public void addSubject(Subject subject) {
        if (subjects == null) {
            subjects = new ArrayList<Subject>();
        }
        subjects.add(subject);
        subject.setTeacher(this);
    }
}
