package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

import static com.virtuslab.internship.product.Product.Type.GRAINS;

public class FifteenPercentDiscount {
    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        int grainProducts =0;
        for(int i =0; i<receipt.entries().size();i++){
            if(receipt.entries().get(i).product().type().equals(GRAINS)) grainProducts = grainProducts + receipt.entries().get(i).quantity();
        }
        if (grainProducts >= 3) return true;
        else return false;
    }
}
