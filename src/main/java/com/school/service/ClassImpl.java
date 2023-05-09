package com.school.service;

import com.school.dto.ClassDto;
import com.school.entity.Classname;
import com.school.entity.Student;
import com.school.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassImpl {
    public List<ClassDto> getAllClass();

    public Classname getClassById(Long id) throws ClassNotFoundException;

    public void addNewClass(Classname classname);

    public void updateClassDetails(Long id, Classname classname);

    public void deleteClassById(Long id);

    public Long totalNoOfStudent(Long classId);

    public List<Student> totalStudents(Long classId);
}
