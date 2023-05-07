package com.school.repository;

import com.school.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("SELECT s.addresses FROM Student s WHERE s.id = :studentId")
    List<Address> findAddressesByStudentId(@RequestParam Long studentId);
    @Query("SELECT t.addresses FROM Teacher t WHERE t.id = :teacherId")
    List<Address> findAddressesByTeacherId(@RequestParam Long teacherId);

}
