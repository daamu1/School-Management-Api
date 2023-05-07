package com.school.repository;

import com.school.entity.Subject;
import com.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    @Query("SELECT s.subjects FROM Teacher s WHERE s.id = :teacherId")
    List<Subject> findSubjectsByTecherId(@RequestParam Long teacherId);
}
