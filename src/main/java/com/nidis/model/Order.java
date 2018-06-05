package com.nidis.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Order {
    private Long id;
    private String customerName;
    private String products;
    private Double totalPrice;  //BigDecimal would be better, but...

    public Order(Long id, String customerName, String products, Double totalPrice) {
        this.id = id;
        this.customerName = customerName;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
