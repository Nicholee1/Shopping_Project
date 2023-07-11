package org.learning.shopping.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role",schema = "oauth2")
public class Role {
    @Id
    long id;
    String name;
    long version;
}
