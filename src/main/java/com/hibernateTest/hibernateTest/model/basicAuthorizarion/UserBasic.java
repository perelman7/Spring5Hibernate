package com.hibernateTest.hibernateTest.model.basicAuthorizarion;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "userbasic")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserBasic {

    @Id
    @Column(name = "username")
    @Getter @Setter
    private String username;

    @Column(name = "passwd")
    @Getter @Setter
    private String passwd;

    @Column(name = "enable")
    @Getter @Setter
    private boolean enable;
}
