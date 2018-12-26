package com.hibernateTest.hibernateTest.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Getter @Setter
    private int id;

    @Column(name = "name_dep")
    @Getter @Setter
    private String name;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
