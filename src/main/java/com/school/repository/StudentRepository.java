package com.school.repository;

import com.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query(value = "SELECT COUNT(*) FROM student  WHERE student.class_id = :classId", nativeQuery = true)
    int countStudentsByClassId(@Param("classId") Long classId);
}
