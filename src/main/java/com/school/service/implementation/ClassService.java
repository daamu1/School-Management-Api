package com.school.service.implementation;

import com.school.dto.ClassDto;
import com.school.entity.Classname;
import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.repository.ClassRepository;
import com.school.repository.StudentRepository;
import com.school.service.ClassImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService implements ClassImpl {
    @Autowired(required = true)
    private ClassRepository classRepository;
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
    public Classname getClassById(Long id) {
        Classname classname = classRepository.findById(id).get();
        if (classname == null) {
            throw new RuntimeException("This Class Id is Valid " + id);
        }
        return classname;
    }

    @Override
    public void addNewClass(Classname classname) {
        classRepository.save(classname);

    }

    @Override
    public void updateClassDetails(Long id, Classname classnam) {
        Classname classname = classRepository.findById(id).get();
        if (classname == null) {
            throw new RuntimeException("This Class Does not Exists " + id);
        } else {
            classname.setClassName(classnam.getClassName());
            classname.setSchoolName(classnam.getSchoolName());
            classname.setTeacher(classnam.getTeacher());
            classname.setStudent(classnam.getStudent());
            classRepository.save(classname);
        }
    }

    @Override
    public void deleteClassById(Long id) {
        Classname classname = classRepository.findById(id).get();
        if (classname == null) {
            throw new RuntimeException("This Class Id is Valid " + id);
        } else {
            classRepository.delete(classname);
        }
    }

    @Override
    public Long totalNoOfStudent(Long classId) {
        Classname classname = classRepository.findById(classId).get();
        Long total;
        if (classname == null) {
            throw new RuntimeException("This Class Id is Valid " + classId);
        } else {
            total = studentRepository.count();
        }
        return total;
    }

    @Override
    public void addNewStudent(Long classId, Student student) {
        Classname classname = classRepository.findById(classId).get();
        Long total;
        if (classname == null) {
            throw new RuntimeException("This Class Id is Valid " + classId);
        } else {
            classname.addStudent(student);
            classRepository.save(classname);
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
    public List<Student> totalStudents(Long classId) {
        Classname classname = classRepository.findById(classId).get();
        Long total;
        if (classname == null) {
            throw new RuntimeException("This Class Id is Valid " + classId);
        } else {
            return classname.getStudent();
        }
    }
}

