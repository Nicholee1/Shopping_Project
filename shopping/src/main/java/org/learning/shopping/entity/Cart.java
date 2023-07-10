package org.learning.shopping.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Cart implements Serializable {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private User user;

    //mappedBy: cart“一方”放弃所有权，避免多条语句的插入删除
    //orphanRemoval = true: 当“多方”的数据没有与“一方”cart关联时，就删除掉“多方”的数据
    // CascadeType.ALL只会在remove时才会删除掉“多方”的数据，products的数据只有和cart关联时才有意义，所以需要orphanRemoval
    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<ProductInOrder> products=new HashSet<>();

    public Cart(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + cartId +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
