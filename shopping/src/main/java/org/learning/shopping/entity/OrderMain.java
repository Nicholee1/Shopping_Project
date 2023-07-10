package org.learning.shopping.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate //仅更新对象中修改过且有值的字段，此时就需要用到@DynamicUpdate注解。
public class OrderMain implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @NotEmpty
    private String buyerAddress;

    @NotEmpty
    private String buyerEmail;

    @NotEmpty
    private String buyerName;

    @NotEmpty
    private String buyerPhone;

    @NotNull
    private BigDecimal orderAmount;

    @NotNull
    private Integer orderStatus;

    //自动生成时间戳
    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "orderMain",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<ProductInOrder> products=new HashSet<>();

    @Override
    public String toString() {
        return "OrderMain{" +
                "orderId=" + orderId +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", buyerPhone='" + buyerPhone + '\'' +
                ", orderAmount=" + orderAmount +
                ", orderStatus=" + orderStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public OrderMain(User buyer){
        this.buyerEmail=buyer.getEmail();
        this.buyerName=buyer.getName();
        this.buyerAddress=buyer.getAddress();
        this.buyerPhone=buyer.getPhone();
        this.orderStatus=0;
        this.orderAmount=buyer.getCart().getProducts().stream().map(item->item.getProductPrice().multiply(new BigDecimal(item.getCount())))
                .reduce(BigDecimal::add).orElse(new BigDecimal(0));
    }
}
