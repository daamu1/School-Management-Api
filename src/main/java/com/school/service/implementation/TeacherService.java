package com.school.service.implementation;

import com.school.dto.TeacherDto;
import com.school.entity.Classname;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.exception.ClassNotFoundException;
import com.school.exception.TeacherNotFoundException;
import com.school.repository.ClassRepository;
import com.school.repository.SubjectRepository;
import com.school.repository.TeacherRepository;
import com.school.service.TeacherImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService implements TeacherImpl {
    @Autowired(required = true)
    private TeacherRepository teacherRepository;
    @Autowired(required = true)
    private SubjectRepository subjectRepository;
    @Autowired(required = true)
    private ClassRepository classRepository;

    public TeacherService() {
    }

    @Override
    public List<TeacherDto> getAllTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherDto teacherDto = new TeacherDto(teacher);
            teacherDtos.add(teacherDto);
        }
        return teacherDtos;
    }

    @Override
    public TeacherDto getTeacherById(Long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            throw new TeacherNotFoundException("Given Teacher id " + teacherId + " is invalid ");
        } else {
            TeacherDto teacherDto = new TeacherDto(teacherOptional.get());
            return teacherDto;
        }
    }


    @Override
    @Transactional
    public void addNewTeacher(Long classId, Teacher teacher) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassNotFoundException("This Class Id is invalid: " + classId));
            classname.addTeacher(teacher);
            classRepository.save(classname);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Failed to add Teacher: " + ex.getMessage());
        }
    }


    @Override
    @Transactional
    public void updateTeacherDetail(Long classId, Long teacherId, Teacher teacher) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassNotFoundException("Given class id " + classId + " is invalid"));
            Teacher teacher1 = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Given teacher id " + teacherId + " is invalid"));
            teacher1.setTeacherName(teacher.getTeacherName());
            teacher1.setAge(teacher.getAge());
            teacher1.setClassname(teacher.getClassname());
            teacher1.setAddresses(teacher.getAddresses());
            teacher1.setSubjects(teacher.getSubjects());
            teacherRepository.save(teacher1);
            classname.getTeacher().add(teacher1);
            classRepository.save(classname);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the teacher detail");
        }
    }


    @Override
    @Transactional
    public void deleteTeacherById(Long classId, Long teacherId) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassNotFoundException("Given class id " + classId + " is invalid"));
            Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Given teacher id " + teacherId + " is invalid"));

            teacherRepository.delete(teacher);
        } catch (ClassNotFoundException | TeacherNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting teacher from class", e);
        }
    }


    @Override
    @Transactional
    public List<Subject> totalSubjectTeachesesByTecher(Long teacherId) {
        try {
            Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                    new TeacherNotFoundException("Given Teacher id " + teacherId + " is invalid "));
            return teacherRepository.findSubjectsByTecherId(teacherId);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching subjects taught by teacher with id " + teacherId, e);
        }
    }

}
