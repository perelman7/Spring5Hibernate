package com.hibernateTest.hibernateTest.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Department extends DateChange{

    @Column(name = "name_dep")
    @Getter @Setter
    @NotNull
    @Size(min = 2, max = 25)
    @ApiModelProperty(notes = "Department`s name is required field with size from 2 to 25 characters")
    private String depName;

    @Column(name = "description")
    @Getter @Setter
    @NotNull
    @Size(min = 2, max = 25)
    @ApiModelProperty(notes = "Department`s description is required field with size from 2 to 25 characters")
    private String description;

    public Department(String name, String description) {
        this.depName = name;
        this.description = description;
    }

    public Department(int id, String name, String description) {
        this.depName = name;
        this.description = description;
        super.setId(id);
    }
}
