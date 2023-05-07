package com.school.service.implementation;

import com.school.dto.StudentDto;
import com.school.entity.Classname;
import com.school.entity.Student;
import com.school.exception.StudentNotFoundException;
import com.school.repository.ClassRepository;
import com.school.repository.StudentRepository;
import com.school.service.StudentImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StudentService implements StudentImpl {
    @Autowired(required = true)
    private StudentRepository studentRepository;
@Autowired
private ClassRepository classRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudent() {
        List<Student> students=studentRepository.findAll();
        List<StudentDto> studentDtos=new ArrayList<>();
        for(Student student:students)
        {
            StudentDto studentDto=new StudentDto(student);
            studentDtos.add(studentDto);
        }
        return  studentDtos;
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).get();
        if (student == null) {
           throw  new StudentNotFoundException("Given StudentId " + id + " there are no Student Available ");
        }
        else {
            StudentDto studentDto=new StudentDto(student);
        return studentDto;
        }
    }

    @Override
    @Transactional
    public void addNewStudent(Long classId,Student student) {
        Classname classname = classRepository.findById(classId).get();
        if (classname == null) {
            throw new RuntimeException("This Class Id is  not Valid " + classId);
        } else {
            classname.addStudent(student);
            classRepository.save(classname);
        }
    }

    @Override
    @Transactional
    public  void updateStudentDetails( Long classId,Long studentId, Student dtudent) {
        Student student = studentRepository.findById(studentId).get();
        Classname classname = classRepository.findById(classId).get();
        if (classname == null) {
            throw new RuntimeException("This Class Id is  not Valid " + classId);
        } else if (student == null) {
            System.out.println("Given StudentId " + studentId + " there are no Student Available ");
        }
        else if (student.getClassName()==classname)
        {
            student.setStudentName(dtudent.getStudentName());
            student.setFatherName(dtudent.getFatherName());
            student.setMotherName(dtudent.getMotherName());
            classRepository.save(classname);
        }
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = studentRepository.findById(id).get();
        if (student == null) {
            System.out.println("Given StudentId " + id + " there are no Student Available ");
        }
        else {
            studentRepository.delete(student);
        }
    }

    @Override
    public int countTotalNoStudent(Long classId) {
        return studentRepository.countStudentsByClassId(classId);
    }
}
