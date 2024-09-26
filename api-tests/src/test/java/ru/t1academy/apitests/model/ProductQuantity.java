package ru.t1academy.apitests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantity {
    private int id;
    private String name;
    private String category;
    private double price;
    private double discount;
    private int quantity;
}
