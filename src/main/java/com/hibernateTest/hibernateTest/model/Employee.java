package com.hibernateTest.hibernateTest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private int id;

    @Column(name = "surname")
    @Getter @Setter
    @NotNull
    @Size(min = 2)
    private String surname;

    @Column(name = "name")
    @Getter @Setter
    @NotNull
    @Size(min = 2)
    private String name;

    @Column(name = "fathername")
    @Getter @Setter
    private String fatherName;

    @Column(name = "date_of_berth")
    @Getter @Setter
    @NotBlank
    private String dob;

    @ManyToOne
    @JoinColumn(name = "dep_id")
    @Getter @Setter
    @NotNull
    private Department department;

    public Employee(String surname, String name, String fatherName, String dob, Department department) {
        this.surname = surname;
        this.name = name;
        this.fatherName = fatherName;
        this.dob = dob;
        this.department = department;
    }
}
