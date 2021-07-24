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
    
   String serviceProduct;
   double price;

    public ProductService(String serviceProduct, double price) {
        this.serviceProduct = serviceProduct;
        this.price = price;
    }

    public String getServiceProduct() {
        return serviceProduct;
    }

    public void setServiceProduct(String serviceProduct) {
        this.serviceProduct = serviceProduct;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
