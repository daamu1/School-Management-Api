package com.school.controller;


import com.school.dto.SubjectDto;
import com.school.entity.Subject;
import com.school.service.SubjectImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class SubjectController {
    @Autowired(required = true)
    private final SubjectImpl subjectImpl;

    public SubjectController(SubjectImpl subject) {
        this.subjectImpl = subject;
    }

    @GetMapping("/subjects")
    public List<SubjectDto> getAllSubjects() {
        return subjectImpl.getAllSubject();
    }

    @GetMapping("/subjects/{subjectId}")
    public SubjectDto getSubjectsById(@PathVariable Long subjectId) {
      return      subjectImpl.getSubjectById(subjectId);
    }

    @PostMapping("{techerId}/subjects")
    public void addNewSubject(@PathVariable Long techerId, @RequestBody Subject subject) {
        subjectImpl.addNewSubject(techerId, subject);
    }

    @PutMapping("{teacherId}/subjects/{subjectId}")
    public void updateSubjectDetails(@PathVariable Long teacherId, @RequestBody Subject subject, @PathVariable Long subjectId) {
        subjectImpl.updateSubjectDetail(teacherId, subjectId, subject);
    }

    @DeleteMapping("/subjects/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId) {
        subjectImpl.deleteSubjectById(subjectId);
    }
}
