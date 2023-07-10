package org.learning.shopping.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

    @Id
    private String productId;

    @ColumnDefault("0")
    private Integer categoryType;

    @CreationTimestamp
    private LocalDateTime createTime;


    private String productDescription;


    private String productIcon;

    @NotEmpty
    private String productName;

    @NotNull
    private BigDecimal productPrice;

    @ColumnDefault("0")
    private Integer productStatus;

    @NotNull
    @Min(0)
    private Integer productStock;

    @UpdateTimestamp
    private LocalDateTime updateTime;



}
