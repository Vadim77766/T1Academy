package ru.t1academy.apitests.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class ShoppingCartRes {
    private List<ProductQuantity> cart;
    @JsonProperty("total_price")
    private double totalPrice;
    @JsonProperty("total_discount")
    private double totalDiscount;
}
