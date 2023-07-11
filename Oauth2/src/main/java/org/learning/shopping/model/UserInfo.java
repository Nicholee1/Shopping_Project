package org.learning.shopping.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class UserInfo {
    @Id
    long id;
    String username;
    String password;

    @OneToMany(targetEntity = Role.class,mappedBy = "id",fetch = FetchType.EAGER)
    List<Role> roles;
}
