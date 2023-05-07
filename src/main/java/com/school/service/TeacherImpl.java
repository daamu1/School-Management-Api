package com.school.service;

import com.school.dto.TeacherDto;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TeacherImpl {
    public List<TeacherDto> getAllTeacher();

    public  TeacherDto getTeacherById(Long teacherId);

    public void addNewTeacher(Long classId,Teacher teacher);

    public  void updateTeacherDetail(Long classId,Long teacherId, Teacher teacher);

    public void deleteTeacherById(Long classId,Long techerId);
    public List<Subject> totalSubjectTeachesesByTecher(Long teacherId);
}
