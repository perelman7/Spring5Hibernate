package com.hibernateTest.hibernateTest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DateChange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    @ApiModelProperty(notes = "The database generated department ID") //hidden = trues
    private int id;

    @Column(name = "dateChange")
    @Getter @Setter
    @JsonIgnore
    private LocalDateTime dateChange;

    @Column(name = "dateAdded")
    @Getter @Setter
    @JsonIgnore
    private LocalDateTime dateAdded;

    @Column(name = "version")
    @Getter @Setter
    @JsonIgnore
    private int version;
}
