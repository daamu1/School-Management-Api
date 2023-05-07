package com.school.controller;

import com.school.dto.AddressDto;
import com.school.entity.Address;
import com.school.service.AddressImpl;
import com.school.service.implementation.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class AddressController {
    @Autowired(required = true)
    private AddressService addressService;
    private Address address;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    public List<AddressDto> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/students/{studentId}/addresses")
    public List<AddressDto> getStudentAddressById( @PathVariable Long studentId) {
        return addressService.getStudentAddressById( studentId);
    }

    @GetMapping("/teachers/{teacherId}/addresses")
    public List<AddressDto> getTeacherAddressById( @PathVariable Long teacherId) {
        return addressService.getTeacherAddressById (teacherId);
    }

    @PostMapping("/students/{studentId}/addresses")
    public void addNewStudentAddress(@PathVariable Long studentId, @RequestBody Address address) {
        addressService.addNewStudentAddresss(studentId, address);
    }

    @PostMapping("/teachers/{teacherId}/addresses")
    public void addNewTeacherAddress(@PathVariable Long teacherId, @RequestBody Address address) {
        addressService.addNewTeacherAddresss(teacherId, address);
    }

    @PutMapping("/students/{studentId}/addresses/{addresssId}")
    public void updateStudentDetails(@RequestBody Address address, @PathVariable Long studentId, @PathVariable Long addresssId) {
        addressService.updateStudentAddress(studentId, addresssId, address);

    }

    @PutMapping("/teachers/{teacherId}/addresses/{addresssId}")
    public void updateTeacherDetails(@RequestBody Address address, @PathVariable Long teacherId, @PathVariable Long addresssId) {
        addressService.updateTeacherAddress(teacherId, addresssId, address);
    }

    @DeleteMapping("/students/{studentId}/addresses/{addresssId}")
    public void deleteStudentAddress(@PathVariable Long studentId, @PathVariable Long addresssId) {
        addressService.deleteStudentAddress(studentId, addresssId);
    }

    @DeleteMapping("/teachers/{teacherId}/addresses/{addresssId}")
    public void deleteTeacherAddress(@PathVariable Long teacherId, @PathVariable Long addresssId) {
        addressService.deleteTeacherAddress(teacherId, addresssId);
    }
}
