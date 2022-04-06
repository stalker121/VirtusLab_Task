package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FifteenPercentDiscountTest {

    @Test
    void shouldApply15PercentDiscountWhenPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        var steak= productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereals, 3));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var discount15 = new FifteenPercentDiscount();
        var discount10 = new TenPercentDiscount();
        var expectedTotalPrice = cereals.price().multiply(BigDecimal.valueOf(3)).add(steak.price()).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount15 = discount15.apply(receipt);
        var receiptAfterDiscount10 = discount10.apply(receiptAfterDiscount15);



        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount10.totalPrice());
        assertEquals(2, receiptAfterDiscount10.discounts().size()); //2 because it is receipt after 15% discount and 10% discount
    }

    @Test
    void shouldApply15PercentDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        var milk = productDb.getProduct("Milk");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereals, 3));
        receiptEntries.add(new ReceiptEntry(milk, 1));

        var receipt = new Receipt(receiptEntries);
        var discount15 = new FifteenPercentDiscount();
        var discount10 = new TenPercentDiscount();
        var expectedTotalPrice = cereals.price().multiply(BigDecimal.valueOf(3)).add(milk.price()).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount15 = discount15.apply(receipt);
        var receiptAfterDiscount10 = discount10.apply(receiptAfterDiscount15);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount10.totalPrice()); // 10% discount did not apply, but 15% did
        assertEquals(1, receiptAfterDiscount10.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 3));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var discount15 = new FifteenPercentDiscount();
        var discount10 = new TenPercentDiscount();
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(3)).add(cereals.price().multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount15 = discount15.apply(receipt);
        var receiptAfterDiscount10 = discount10.apply(receiptAfterDiscount15);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount10.totalPrice());
        assertEquals(1, receiptAfterDiscount10.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var discount15 = new FifteenPercentDiscount();
        var discount10 = new TenPercentDiscount();
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(1)).add(cereals.price().multiply(BigDecimal.valueOf(2)));

        // When
        var receiptAfterDiscount15 = discount15.apply(receipt);
        var receiptAfterDiscount10 = discount10.apply(receiptAfterDiscount15);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount10.totalPrice());
        assertEquals(0, receiptAfterDiscount10.discounts().size());
    }
}
