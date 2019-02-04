package com.hibernateTest.hibernateTest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class MainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    @ApiModelProperty(notes = "The database generated department ID")
    private int id;

    @Column(name = "date_change")
    @Getter @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateChange;

    @Column(name = "date_added")
    @Getter @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateAdded;

    @Column(name = "version")
    @Getter @Setter
    private int version;
}
