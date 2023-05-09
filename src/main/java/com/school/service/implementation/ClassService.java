package com.school.service.implementation;

import com.school.dto.ClassDto;
import com.school.entity.Classname;
import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.exception.ClassNotFoundException;
import com.school.repository.ClassRepository;
import com.school.repository.StudentRepository;
import com.school.service.ClassImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClassService implements ClassImpl {
    @Autowired(required = true)
    private final ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    @Override
    public List<ClassDto> getAllClass() {
        List<Classname> classnames = classRepository.findAll();
        List<ClassDto> classDtos = new ArrayList<>();
        for (Classname classname : classnames) {
            ClassDto classDto = new ClassDto(classname);
            classDtos.add(classDto);
        }
        return classDtos;
    }

    @Override
    public Classname getClassById(Long id) throws ClassNotFoundException {
        Optional<Classname> optionalClassname = classRepository.findById(id);
        if (optionalClassname.isPresent()) {
            return optionalClassname.get();
        } else {
            throw new ClassNotFoundException("Class not found with id: " + id);
        }
    }


    @Override
    public void addNewClass(Classname classname) {
        classRepository.save(classname);

    }

    @Override
    public void updateClassDetails(Long classId, Classname classname1) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassCastException("Class not found with id: " + classId));
            classname.setClassName(classname1.getClassName());
            classname.setSchoolName(classname1.getSchoolName());
            classname.setTeacher(classname1.getTeacher());
            classname.setStudent(classname1.getStudent());
            classRepository.save(classname);
        } catch (ClassCastException e) {
            throw new RuntimeException("Error updating class details: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error updating class details: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteClassById(Long id) {
        try {
            Classname classname = classRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("Class not found with id: " + id));
            classRepository.delete(classname);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Failed to delete class with id: " + id, ex);
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }


    @Override
    public Long totalNoOfStudent(Long classId) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() ->
                    new IllegalArgumentException("Class Id is invalid: " + classId));
            return studentRepository.count();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get total number of students for class: " + classId, ex);
        }
    }



    @Override
    public List<Student> totalStudents(Long classId) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassNotFoundException("This Class Id is not Valid " + classId));
            return classname.getStudent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch students for classId " + classId+" "+ e);
        }
    }

}

