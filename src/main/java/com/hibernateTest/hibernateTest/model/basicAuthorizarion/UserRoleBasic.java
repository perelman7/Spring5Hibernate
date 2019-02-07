package com.hibernateTest.hibernateTest.model.basicAuthorizarion;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_role_basic")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRoleBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "role")
    @Getter @Setter
    private String role;

    @ManyToOne
    @JoinColumn(name = "username")
    @Getter @Setter
    @NotNull
    private UserBasic userBasic;
}
