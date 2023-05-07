package com.school.repository;

import com.school.entity.Classname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Classname,Long> {
    @Query(value = "SELECT COUNT(*) FROM students WHERE s.class_id = :classId", nativeQuery = true)
    int countStudentsByClassId(@Param("classId") Long classId);
}
