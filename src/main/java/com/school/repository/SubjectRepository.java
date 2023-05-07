package com.school.repository;

import com.school.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    @Query(value = "SELECT s.* FROM subjects s JOIN teacher_subject ts ON s.subject_id = ts.subject_id WHERE ts.teacher_id =teacherId ", nativeQuery = true)
    List<Subject> findByTeacherId(@Param("teacherId") Long teacherId);
}
