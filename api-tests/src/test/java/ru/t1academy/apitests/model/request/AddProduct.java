package ru.t1academy.apitests.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddProduct {
    @JsonProperty("product_id")
    private int productId;
    public int quantity;
}
