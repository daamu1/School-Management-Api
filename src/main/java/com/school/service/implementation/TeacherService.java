package com.school.service.implementation;

import com.school.dto.TeacherDto;
import com.school.entity.Classname;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.repository.ClassRepository;
import com.school.repository.SubjectRepository;
import com.school.repository.TeacherRepository;
import com.school.service.TeacherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService implements TeacherImpl {
    @Autowired(required = true)
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ClassRepository classRepository;

    public TeacherService() {
    }

    public TeacherService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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
    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        if (teacher == null) {
            throw new RuntimeException("Given Teacher id " + id + " is invalid ");
        } else {
            TeacherDto teacherDto = new TeacherDto(teacher);
            return teacherDto;
        }


    }

    @Override
    public void addNewTeacher(Long classId, Teacher teacher) {
        Classname classname = classRepository.findById(classId).get();
        Long total;
        if (classname == null) {
            throw new RuntimeException("This Class Id is Valid " + classId);
        } else {
            classname.addTeacher(teacher);
            classRepository.save(classname);
        }
    }

    @Override
    public void updateTeacherDetail(Long classId, Long teacherId, Teacher teacher) {
        Classname classname = classRepository.findById(classId).get();
        Teacher teacher1 = teacherRepository.findById(teacherId).get();
        if (classname == null) {
            throw new RuntimeException("Given claass id " + classId + " is invalid ");
        } else if (teacher1 == null) {
            throw new RuntimeException("Given Teacher id " + teacherId + " is invalid ");
        } else {
            teacher1.setTeacherName(teacher.getTeacherName());
            teacher1.setAge(teacher.getAge());
            teacher1.setClassname(teacher.getClassname());
            teacher1.setAddresses(teacher.getAddresses());
            teacher1.setSubjects(teacher.getSubjects());
            teacherRepository.save(teacher1);
            classname.getTeacher().add(teacher1);
            classRepository.save(classname);
        }

    }

    @Override
    public void deleteTeacherById(Long classId, Long id) {
        Classname classname = classRepository.findById(classId).get();
        Teacher teacher1 = teacherRepository.findById(id).get();
        if (classname == null) {
            throw new RuntimeException("Given claass id " + classId + " is invalid ");
        } else if (teacher1 == null) {
            throw new RuntimeException("Given Teacher id " + id + " is invalid ");
        } else  {
            teacherRepository.delete(teacher1);
        }
    }

    @Override
    public List<Subject> totalSubjectTeachesesByTecher(Long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        if (teacher == null) {
            throw new RuntimeException("Given Teacher id " + id + " is invalid ");
        } else {
            return teacherRepository.findSubjectsByTecherId(id);
        }
    }
}
