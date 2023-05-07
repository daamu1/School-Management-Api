package com.school.service;

import com.school.dto.SubjectDto;
import com.school.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubjectImpl {
   public  List<SubjectDto> getAllSubject();

    public SubjectDto getSubjectById(Long id);

    public void addNewSubject(Long techerId, Subject subject);

    public  void updateSubjectDetail(Long techerId,Long subjectId, Subject subject);

    public void deleteSubjectById(Long id);


}
