package com.school.service.implementation;

import com.school.dto.SubjectDto;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.exception.SubjectNotFoundException;
import com.school.exception.TeacherNotFoundException;
import com.school.repository.SubjectRepository;
import com.school.repository.TeacherRepository;
import com.school.service.SubjectImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements SubjectImpl {
    @Autowired(required = true)
    private final SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> getAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDto subjectDto = new SubjectDto(subject);
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    @Override
    public SubjectDto getSubjectById(Long subjectId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if (!optionalSubject.isPresent()) {
            throw new SubjectNotFoundException("Subject not found with ID: " + subjectId);
        }

        Subject subject = optionalSubject.get();
        SubjectDto subjectDto = new SubjectDto(subject);
        return subjectDto;
    }


    @Override
    public void addNewSubject(Long teacherId, Subject subject) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher with ID " + teacherId + " does not exist");
        }

        Teacher teacher = optionalTeacher.get();
        teacher.getSubjects().add(subject);
        teacher.addSubject(subject);

        try {
            teacherRepository.save(teacher);
            subjectRepository.save(subject);
        } catch (Exception e) {
            // Handle any database-related exceptions here
            throw new RuntimeException("An error occurred while saving the teacher and subject" + " " + e);
        }
    }


    @Override
    @Transactional
    public void updateSubjectDetail(Long teacherId, Long subjectId, Subject subject) {
        try {
            Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Teacher with ID " + teacherId + " not found"));
            Subject subject1 = subjectRepository.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException("Subject with ID " + subjectId + " not found"));

            if (!subject1.getTeacher().equals(teacher)) {
                throw new IllegalArgumentException("Teacher with ID " + teacherId + " is not assigned to subject with ID " + subjectId);
            }

            subject1.setSubjectName(subject.getSubjectName());
            subject1.setTotolNoUnit(subject.getTotolNoUnit());
            subjectRepository.save(subject1);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating subject details: " + ex.getMessage() + " " + ex);
        }
    }


    @Override
    public void deleteSubjectById(Long techerId, Long subjectId) {
        try {
            Teacher teacher = teacherRepository.findById(techerId).orElseThrow(() -> new TeacherNotFoundException("Teacher with ID " + techerId + " not found"));
            Subject subject1 = subjectRepository.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException("Subject with ID " + subjectId + " not found"));

            if (!subject1.getTeacher().equals(teacher)) {
                throw new IllegalArgumentException("Teacher with ID " + techerId + " is not assigned to subject with ID " + subjectId);
            }
            subjectRepository.delete(subject1);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while deleting subject details: " + ex.getMessage() + " " + ex);
        }
    }
}
