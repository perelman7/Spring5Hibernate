package com.hibernateTest.hibernateTest.model;

import lombok.*;

import javax.persistence.*;

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
    private String surname;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "fathername")
    @Getter @Setter
    private String fatherName;

    @Column(name = "date_of_berth")
    @Getter @Setter
    private String dob;

    @ManyToOne
    @JoinColumn(name = "dep_id")
    @Getter @Setter
    private Department department;

    public Employee(String surname, String name, String fatherName, String dob, Department department) {
        this.surname = surname;
        this.name = name;
        this.fatherName = fatherName;
        this.dob = dob;
        this.department = department;
    }
}
