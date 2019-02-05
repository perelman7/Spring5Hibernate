package com.hibernateTest.hibernateTest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "changerows")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ChangeRow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "username")
    @Getter @Setter
    private String username;

    @Column(name = "table_name")
    @Getter @Setter
    private String tableName;

    @Column(name = "method_name")
    @Getter @Setter
    private String methodName;

    @Column(name = "old_value")
    @Getter @Setter
    private String oldValue;

    @Column(name = "new_value")
    @Getter @Setter
    private String newValue;

    @Column(name = "version")
    @Getter @Setter
    private int version;

    @Column(name = "date_change")
    @Getter @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateChange;
}
