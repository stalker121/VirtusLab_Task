package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();



        HashMap<Product, Integer> quantity = new HashMap<Product, Integer>();

        for(int i=0; i< basket.getProducts().size();i++){
            if(!quantity.containsKey(basket.getProducts().get(i))){
                quantity.put(basket.getProducts().get(i),1);
            }else {
                int buff = quantity.get(basket.getProducts().get(i));
                quantity.put(basket.getProducts().get(i),buff + 1);
            }
        }


        for(int i=0; i< quantity.size();i++){
            Product value = (Product) quantity.keySet().toArray()[i];
            receiptEntries.add(new ReceiptEntry(value, quantity.get(value)));
        }

        return new Receipt(receiptEntries);
    }
}
