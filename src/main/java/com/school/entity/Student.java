package com.school.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String fatherName;
    private String motherName;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JsonIgnore
    private List<Address> addresses;
    @ManyToOne
    @JoinColumn(name = "class_Id")
    @JsonIgnore
    private Classname className;

    public void addAddress(Address address) {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        addresses.add(address);
        address.setStudent(this);
    }
}
