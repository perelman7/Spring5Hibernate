package com.hibernateTest.hibernateTest.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Employee extends DateChange implements Serializable {

    @Column(name = "surname")
    @Getter @Setter
    @NotNull
    @Size(min = 2)
    @ApiModelProperty(notes = "Employees`s surname is required field with min size 2 characters")
    private String surname;

    @Column(name = "name")
    @Getter @Setter
    @NotNull
    @Size(min = 2)
    @ApiModelProperty(notes = "Employees`s name is required field with min size 2 characters")
    private String name;

    @Column(name = "fathername")
    @Getter @Setter
    @ApiModelProperty(notes = "Employees`s father name is not required")
    private String fatherName;

    @Column(name = "date_of_berth")
    @Getter @Setter
    @NotBlank
    @ApiModelProperty(notes = "Employees`s date of berth is required, format: yyyy-mm-dd")
    private String dob;

    @ManyToOne
    @JoinColumn(name = "dep_id")
    @Getter @Setter
    @NotNull
    @ApiModelProperty(notes = "Employees`s department is required and must be connect with real entity from table \"department\", relations between tables many to one")
    private Department department;

    public Employee(String surname, String name, String fatherName, String dob, Department department) {
        this.surname = surname;
        this.name = name;
        this.fatherName = fatherName;
        this.dob = dob;
        this.department = department;
    }
}
