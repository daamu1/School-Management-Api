package com.school.service;

import com.school.dto.AddressDto;
import com.school.entity.Address;
import com.school.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AddressImpl {
    public List<AddressDto> getAllAddress();

    public  List<AddressDto> getStudentAddressById(Long studentId );
    public List<AddressDto> getTeacherAddressById(Long teacherId );

    public void addNewStudentAddresss(Long studentId,Address address);
    public void addNewTeacherAddresss(Long teacherId,Address address);

    public  void updateStudentAddress(Long studentId,Long addressId,Address address);
    public  void updateTeacherAddress(Long teacherId,Long addressId, Address address);
    public void deleteStudentAddress(Long userId,Long addressId);
    public void deleteTeacherAddress(Long teacherId,Long addressId);

}
