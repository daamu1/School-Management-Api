package com.school.dto;

import com.school.entity.Address;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDto {
    private  Long id;
    private String village;
    private  String district;
    private  String pincode;
    public AddressDto(Address address)
    {
        this.id=address.getId();
        this.district=address.getDistrict();
        this.village=address.getVillage();
        this.pincode=address.getPincode();
    }
}
