package org.learning.shopping.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
public class ItemForm {

    @Min(1)
    private Integer quantity;

    @NotEmpty
    private String productId;
}
