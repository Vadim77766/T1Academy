package ru.t1academy.apitests.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddProductReq {
    @JsonProperty("product_id")
    private int productId;
    public int quantity;
}
