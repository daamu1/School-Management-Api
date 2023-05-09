package com.school.service;

import com.school.dto.StudentDto;
import com.school.entity.Student;
import com.school.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StudentImpl {
    public List<StudentDto> getAllStudent();

    public StudentDto getStudentById(Long id);

    public void addNewStudent(Long classId,Student student);

    public  void updateStudentDetails( Long classId,Long studentId, Student dtudent);

    public void deleteStudentById( Long classId,Long studentId);
    public  int countTotalNoStudent(Long classId);
}
