package ru.t1academy.apitests.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.t1academy.apitests.model.ProductQuantity;

import java.util.List;
@Data
public class ShoppingCart {
    private List<ProductQuantity> cart;
    @JsonProperty("total_price")
    private double totalPrice;
    @JsonProperty("total_discount")
    private double totalDiscount;
}
