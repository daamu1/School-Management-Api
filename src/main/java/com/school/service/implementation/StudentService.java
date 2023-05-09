package com.school.service.implementation;

import com.school.dto.StudentDto;
import com.school.entity.Classname;
import com.school.entity.Student;
import com.school.exception.ClassNotFoundException;
import com.school.exception.StudentNotFoundException;
import com.school.repository.ClassRepository;
import com.school.repository.StudentRepository;
import com.school.service.StudentImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentImpl {
    @Autowired(required = true)
    private final StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student student : students) {
            StudentDto studentDto = new StudentDto(student);
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException("Given StudentId " + id + " does not exist");
        } else {
            Student student = optionalStudent.get();
            StudentDto studentDto = new StudentDto(student);
            return studentDto;
        }
    }

    @Override
    @Transactional
    public void addNewStudent(Long classId, Student student) {
        try {
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassNotFoundException("Class not found with id: " + classId));
            classname.addStudent(student);
            classRepository.save(classname);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to add student: " + ex.getMessage(), ex);
        }
    }

    @Override
    @Transactional
    public void updateStudentDetails(Long classId, Long studentId, Student student) {
        try {
            Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("No student found with id " + studentId));
            Classname classname = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("No class found with id " + classId));
            if (!existingStudent.getClassName().equals(classname)) {
                throw new RuntimeException("Given student does not belong to class " + classId);
            }
            existingStudent.setStudentName(student.getStudentName());
            existingStudent.setFatherName(student.getFatherName());
            existingStudent.setMotherName(student.getMotherName());
            studentRepository.save(existingStudent);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error updating student details: " + ex.getMessage());
        }
    }

    @Override
    public void deleteStudentById(Long classId, Long studentId) {
        try {
            Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("No student found with id " + studentId));

            Classname classname = classRepository.findById(classId).orElseThrow(() -> new ClassNotFoundException("No class found with id " + classId));

            if (!existingStudent.getClassName().equals(classname)) {
                throw new RuntimeException("Given student does not belong to class " + classId);
            } else {
                studentRepository.delete(existingStudent);
            }
        } catch (ClassNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting student details", ex);
        }
    }


    @Override
    public int countTotalNoStudent(Long classId) {
        return studentRepository.countStudentsByClassId(classId);
    }
}
