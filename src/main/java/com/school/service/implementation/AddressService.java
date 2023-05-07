package com.school.service.implementation;

import com.school.dto.AddressDto;
import com.school.entity.Address;
import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.exception.StudentNotFoundException;
import com.school.repository.AddressRepository;
import com.school.repository.StudentRepository;
import com.school.repository.TeacherRepository;
import com.school.service.AddressImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public AddressService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public AddressService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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
        Student student = studentRepository.findById(studentId).get();
        if (student == null) {
            throw new StudentNotFoundException(" Student is Not available on this id " + studentId);
        } else {
            List<Address> addresses = addressRepository.findAddressesByStudentId(studentId);
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
        Teacher teacher = teacherRepository.findById(teacherId).get();
        if (teacher == null) {
            throw new RuntimeException("Teacher is not availabe on this  Id " + teacherId);
        } else {
            List<Address> addresses = addressRepository.findAddressesByTeacherId(teacherId);
            List<AddressDto> addressDtos = new ArrayList<>();
            for (Address address : addresses) {
                AddressDto addressDto = new AddressDto(address);
                addressDtos.add(addressDto);
            }
            return addressDtos;
        }
    }

    @Override
    public void addNewStudentAddresss(Long studentId, Address address) {
        Student student = studentRepository.findById(studentId).get();
        if (student == null) {
            throw new RuntimeException("This Student id is not valid " + studentId);
        } else {
            student.addAddress(address);
            studentRepository.save(student);
//            addressRepository.save(address);
        }

    }

    @Override
    public void addNewTeacherAddresss(Long teacherId, Address address) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        if (teacher == null) {
            throw new RuntimeException("This Teacher Id is not Valid " + teacherId);
        } else {
            teacher.addAddress(address);
            teacherRepository.save(teacher);
        }
    }

    @Override
    @Transactional
    public void updateStudentAddress(Long studentId, Long addressId, Address addresse) {
        Student student = studentRepository.findById(studentId).get();
        if (student == null) {
            throw new RuntimeException("This Student id is not valid " + studentId);
        }
        Address address = addressRepository.findById(addressId).get();
        if (address == null) {
            throw new RuntimeException("This Address Id is Not a valid Address Id " + addressId);
        } else if (address.getStudent() == student) {
            address.setVillage(addresse.getVillage());
            address.setDistrict(addresse.getDistrict());
            address.setPincode(addresse.getPincode());
            addressRepository.save(address);
        } else {
            throw new RuntimeException("Something went Wrong !!!!");
        }

    }

    @Override
    @Transactional
    public void updateTeacherAddress(Long teacherId, Long addressId, Address addres) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        if (teacher == null) {
            throw new RuntimeException("This Teacher Id is not Valid " + teacherId);
        }
        Address address = addressRepository.findById(addressId).get();
        if (address == null) {
            throw new RuntimeException("This AddressId is Not a valid Address Id " + addressId);
        } else if (address.getTeacher() == teacher) {
            address.setVillage(addres.getVillage());
            address.setDistrict(addres.getDistrict());
            address.setPincode(address.getPincode());
            addressRepository.save(address);
        } else {
            throw new RuntimeException("Something went Wrong !!!!");
        }

    }

    @Override
    public void deleteStudentAddress(Long userId, Long addressId) {
        Student student = studentRepository.findById(userId).get();
        Address address = addressRepository.findById(addressId).get();
        if (student == null) {
            throw new RuntimeException("This Student id is not valid " + userId);
        } else if (address == null) {
            throw new RuntimeException("This Address Id is Not a valid Address Id " + addressId);
        } else if (address.getStudent() == student) {
            addressRepository.delete(address);
        }
    }

    @Override
    public void deleteTeacherAddress(Long teacherId, Long addressId) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Address address = addressRepository.findById(addressId).get();
        if (teacher == null) {
            throw new RuntimeException("This Teacher Id is not Valid " + teacherId);
        } else if (address == null) {
            throw new RuntimeException("This AddressId is Not a valid Address Id " + addressId);
        } else if (address.getTeacher() == teacher) {
            addressRepository.delete(address);
        }
    }
}

