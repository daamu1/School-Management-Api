package com.school.controller;

import com.school.dto.StudentDto;
import com.school.entity.Student;
import com.school.service.StudentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class StudentController {
    @Autowired(required = true)
    private  StudentImpl students;

    public StudentController(StudentImpl student) {
        this.students = student;
    }

    @GetMapping("/students")
    public List<StudentDto> getAllStudent()
    {
        return students.getAllStudent();
    }
    @GetMapping("/students/{studentId}")
    public StudentDto getStudentById(@PathVariable Long studentId)
    {
        return students.getStudentById(studentId);
    }
    @PostMapping("{classId}/students")
    public void addNewStudent(@PathVariable Long classId,@RequestBody Student student)
    {
        students.addNewStudent(classId,student);
    }
    @PutMapping("{classId}/students/{studentId}")
    public  void updateStudentDetails(@RequestBody Student student,@PathVariable Long studentId,@PathVariable Long classId)
    {
        students.updateStudentDetails(classId,studentId,student);
    }
    @DeleteMapping("{classId}/students/{studentId}")
    public  void deleteStudent(@PathVariable Long classId,@PathVariable Long studentId)
    {
        students.deleteStudentById(classId,studentId);
    }
}
