package com.school.controller;


import com.school.dto.TeacherDto;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.service.TeacherImpl;
import com.school.service.implementation.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class TeacherController {
    @Autowired(required = true)
    private TeacherService teachers;

    public TeacherController(TeacherService teachers) {
        this.teachers = teachers;
    }

    @GetMapping("/teachers")
    public List<TeacherDto> getAllTeacher() {
        return teachers.getAllTeacher();
    }

    @GetMapping("/teachers/{teacherId}")
    public TeacherDto getTeacherById(@PathVariable Long teacherId) {
        return teachers.getTeacherById(teacherId);
    }

    @PostMapping("{classId}/teachers")
    public void addNewTeacher( @PathVariable Long classId,@RequestBody Teacher teacher) {
        teachers.addNewTeacher(classId,teacher);
    }

    @PutMapping("{classId}/teachers/{teacherId}")
    public void updateTeacherDetails(@PathVariable Long classId,@RequestBody Teacher teacher, @PathVariable Long teacherId) {
        teachers.updateTeacherDetail(classId,teacherId, teacher);
    }

    @DeleteMapping("{classId}/teachers/{teacherId}")
    public void deleteStudent(@PathVariable Long classId,@PathVariable Long teacherId) {
        teachers.deleteTeacherById(classId,teacherId);
    }

    @GetMapping("/teachers/{teacherId}/subjects")
    public  List<Subject> techerTechParticulerClass(@PathVariable Long teacherId)
    {
        return  teachers.totalSubjectTeachesesByTecher(teacherId);
    }

}
