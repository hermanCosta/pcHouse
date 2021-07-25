/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registering;

/**
 *
 * @author user
 */
public class ProductService {
    
   String productService;
   double price;
   int qty;
   String notes;
   
    public ProductService(String serviceProduct, double price) {
        this.productService = serviceProduct;
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    

    public String getProductService() {
        return productService;
    }

    public void setProductService(String serviceProduct) {
        this.productService = serviceProduct;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
