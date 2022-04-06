package com.virtuslab.internship.RESTAPI;

import java.util.ArrayList;
import java.util.List;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;


@RestController
public class Controller {
    ProductDb productDb = new ProductDb();

    Product milk = productDb.getProduct("Milk");
    Product bread = productDb.getProduct("Bread");
    Product apple = productDb.getProduct("Apple");
    Basket cart;

    {
        {
            cart = new Basket();
            cart.addProduct(milk);
            cart.addProduct(milk);
            cart.addProduct(bread);
            cart.addProduct(apple);
            //cart.addProduct(apple);

        }
    }
    ReceiptGenerator receiptGenerator = new ReceiptGenerator();
    // When
    Receipt receipt = receiptGenerator.generate(cart);

    @GetMapping("/receipt")
    public Receipt get() {

        receipt = receiptGenerator.generate(cart);
        return  receipt;
    }


    /*
    @PostMapping(
            value = "/createReceipt", consumes = "application/json", produces = "application/json")
    public Receipt createReceipt() {
        receipt = receiptGenerator.generate(cart);
        return receipt;
    }

    @PostMapping(
            value = "/updateReceipt", consumes = "application/json", produces = "application/json")
    public Receipt updateReceipt(@RequestBody Receipt receipt, HttpServletResponse response) {
        response.setHeader("Location", String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/findPerson/" + receiptGenerator.generate(cart))));

        return receiptGenerator.generate(cart);
    }

     */

}
