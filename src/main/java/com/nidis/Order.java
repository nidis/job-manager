package com.nidis;

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

    public Order(Long id, String customerName, String products) {
        this.id = id;
        this.customerName = customerName;
        this.products = products;
    }
}
