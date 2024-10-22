package ru.t1academy.apitests.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {
    private String name;
    private String category;
    private double price;
    private double discount;
}
