package com.school.service.implementation;

import com.school.dto.SubjectDto;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.repository.SubjectRepository;
import com.school.repository.TeacherRepository;
import com.school.service.SubjectImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements SubjectImpl {
    @Autowired(required = true)
    private SubjectRepository subjectRepository;
@Autowired
private TeacherRepository teacherRepository;
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> getAllSubject() {
        List<Subject> subjects=subjectRepository.findAll();
        List<SubjectDto>subjectDtos=new ArrayList<>();
        for(Subject subject:subjects)
        {
            SubjectDto subjectDto=new SubjectDto(subject);
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    @Override
    public SubjectDto getSubjectById(Long id) {
        Subject subject= subjectRepository.findById(id).get();
        if(subject==null)
        {
            throw new  RuntimeException(" this subject does not exists ");
        }
        else {
            SubjectDto subjectDto=new SubjectDto(subject);
            return subjectDto;
        }
    }

    @Override
    public void addNewSubject(Long teacherId,Subject subject) {
        Teacher teacher =teacherRepository.findById(teacherId).get();
        if(teacher==null)
        {
            throw  new RuntimeException(" this techer id "+teacherId+" does not exists");
        }
        else {
            teacher.getSubjects().add(subject);
            teacher.addSubject(subject);
            teacherRepository.save(teacher);
            subjectRepository.save(subject);

        }
    }

    @Override
    public void updateSubjectDetail(Long techerId, Long subjectId, Subject subject) {
        Teacher teacher =teacherRepository.findById(techerId).get();
        Subject subject1=subjectRepository.findById(subjectId).get();
        if(teacher==null)
        {
            throw  new RuntimeException(" this techer id "+techerId+" does not exists");
        }
        else  if (subject1==null){
            throw  new RuntimeException(" this Subject id "+subjectId+" does not exists");
        }
        else if(subject1.getTeacher()==teacher)
        {
            subject1.setSubjectName(subject.getSubjectName());
            subject1.setTeacher(teacher);
            subject1.setTotolNoUnit(subject.getTotolNoUnit());
            subjectRepository.save(subject1);
        }
    }


    @Override
    public void deleteSubjectById(Long id) {
        Subject subject1=subjectRepository.findById(id).get();
        if (subject1==null){
            throw  new RuntimeException(" this Subject id "+id+" does not exists");
        }
        else {
            subjectRepository.delete(subject1);
        }
    }
}
