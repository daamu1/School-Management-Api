package com.school.controller;

import com.school.dto.ClassDto;
import com.school.entity.Classname;
import com.school.entity.Student;
import com.school.service.implementation.ClassService;
import com.school.service.implementation.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ClassController {
    @Autowired(required = true)
    private ClassService classService;
    @Autowired(required = true)
    private StudentService service;

    public ClassController(StudentService service) {
        this.service = service;
    }

    public ClassController() {
    }

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public List<ClassDto> getAllclasses() {
        return classService.getAllClass();
    }

    @GetMapping("/classes/{classId}")
    public Classname getClassById(@PathVariable Long classId) throws ClassNotFoundException {
        return classService.getClassById(classId);
    }

    @PostMapping("/newclasses")
    public void addNewClass(@RequestBody Classname classname) {
        classService.addNewClass(classname);
    }
    
    @PutMapping("/classes/{classId}")
    public void updateClassDetails(@RequestBody Classname classname, @PathVariable Long classId) {
        classService.updateClassDetails(classId, classname);
    }

    @DeleteMapping("/classes/{classId}")
    public void deleteClass(@PathVariable Long classId) {
        classService.deleteClassById(classId);
    }

    @GetMapping("/classes/ts/{classId}")
    public int students(@PathVariable Long classId) {
        return service.countTotalNoStudent(classId);
    }

    @GetMapping("/classes/listofstudents/{classId}")
    public List<Student> totalStudents(@PathVariable Long classId) {
        return  classService.totalStudents(classId);
    }
    }
