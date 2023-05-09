package com.school.service.implementation;

import com.school.dto.AddressDto;
import com.school.entity.Address;
import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.exception.AddressNotFoundException;
import com.school.exception.StudentNotFoundException;
import com.school.exception.TeacherNotFoundException;
import com.school.repository.AddressRepository;
import com.school.repository.StudentRepository;
import com.school.repository.TeacherRepository;
import com.school.service.AddressImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements AddressImpl {

    @Autowired(required = true)
    private AddressRepository addressRepository;
    @Autowired(required = true)
    private StudentRepository studentRepository;
    @Autowired(required = true)
    private TeacherRepository teacherRepository;

    public AddressService() {
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto addressDto = new AddressDto(address);
            addressDtos.add(addressDto);
        }
        return addressDtos;
    }

    @Override
    public List<AddressDto> getStudentAddressById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException("Student not found for id " + studentId);
        } else {
            List<Address> addresses = addressRepository.findAddressesByStudentId(studentId);
            if (addresses.isEmpty()) {
                throw new AddressNotFoundException("No addresses found for student with ID " + studentId);
            }
            List<AddressDto> addressDtos = new ArrayList<>();
            for (Address address : addresses) {
                AddressDto addressDto = new AddressDto(address);
                addressDtos.add(addressDto);
            }
            return addressDtos;
        }
    }

    @Override
    public List<AddressDto> getTeacherAddressById(Long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (!optionalTeacher.isPresent()) {
            throw new TeacherNotFoundException("Teacher is not available on this Id " + teacherId);
        }

        List<Address> addresses = addressRepository.findAddressesByTeacherId(teacherId);
        if (addresses.isEmpty()) {
            throw new AddressNotFoundException("No addresses found for teacher with ID " + teacherId);
        }

        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto addressDto = new AddressDto(address);
            addressDtos.add(addressDto);
        }
        return addressDtos;
    }


    @Override
    @Transactional
    public void addNewStudentAddresss(Long studentId, Address address) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isEmpty()) {
                throw new StudentNotFoundException("This Student id is not valid " + studentId);
            } else {
                Student student = optionalStudent.get();
                student.addAddress(address);
                studentRepository.save(student);
                // addressRepository.save(address);
            }
        } catch (DataAccessException ex) {
            // This catch block will handle any exception related to database access, such as JPA exceptions
            throw new RuntimeException("An error occurred while trying to access the database"+"  "  + ex);
        }
    }



    @Override
    @Transactional
    public void addNewTeacherAddresss(Long teacherId, Address address) throws TeacherNotFoundException {
        try {
            Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("This Teacher Id is not Valid " + teacherId));
            teacher.addAddress(address);
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding the teacher address."+" "+ e);

        }
    }

    @Override
    @Transactional
    public void updateStudentAddress(Long studentId, Long addressId, Address addresse) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("This Student id is not valid " + studentId));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("This Address Id is Not a valid Address Id " + addressId));

        if (address.getStudent() == null || !address.getStudent().equals(student)) {
            throw new IllegalArgumentException("This address does not belong to this student.");
        }

        try {
            address.setVillage(addresse.getVillage());
            address.setDistrict(addresse.getDistrict());
            address.setPincode(addresse.getPincode());
            addressRepository.save(address);
        } catch (DataAccessException ex) {
            throw new RuntimeException("An error occurred while updating student address.", ex);
        }
    }



    @Override
    @Transactional
    public void updateTeacherAddress(Long teacherId, Long addressId, Address addres) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                new TeacherNotFoundException("Teacher not found with id: " + teacherId));
        Address address = addressRepository.findById(addressId).orElseThrow(() ->
                new AddressNotFoundException("Address not found with id: " + addressId));
        if (!address.getTeacher().equals(teacher)) {
            throw new RuntimeException("Address with id " + addressId + " is not associated with teacher with id " + teacherId);
        }
        address.setVillage(addres.getVillage());
        address.setDistrict(addres.getDistrict());
        address.setPincode(addres.getPincode());
        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteStudentAddress(Long studentId, Long addressId)  {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("This Student id is not valid " + studentId));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("This Address Id is Not a valid Address Id " + addressId));

        if (address.getStudent() == null || !address.getStudent().equals(student)) {
            throw new IllegalArgumentException("This address does not belong to this student.");
        }

        try {
            addressRepository.delete(address);
        } catch (DataAccessException ex) {
            throw new RuntimeException("An error occurred while deleting student address.", ex);
        }
    }


    @Override
    @Transactional
    public void deleteTeacherAddress(Long teacherId, Long addressId) throws TeacherNotFoundException, AddressNotFoundException, DataAccessException {
        try {
            Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("This Teacher Id is not Valid " + teacherId));
            Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("This AddressId is Not a valid Address Id " + addressId));
            if (address.getTeacher() == teacher) {
                addressRepository.delete(address);
            } else {
                throw new AddressNotFoundException("Address with Id " + addressId + " does not belong to Teacher with Id " + teacherId);
            }
        } catch (DataAccessException ex) {
            throw new RuntimeException("An error occurred while attempting to delete teacher address: " + ex.getMessage()+"  "+ ex);
        }
    }


}

