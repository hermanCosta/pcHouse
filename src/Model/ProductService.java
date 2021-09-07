/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author HermanCosta
 */
public class ProductService {

    String productService;
    double price;
    int qty;
    String notes;
    String category;

    public ProductService(String _serviceProduct, double _price, int _qty, String _notes, String _category) {
        this.productService = _serviceProduct;
        this.price = _price;
        this.qty = _qty;
        this.notes = _notes;
        this.category = _category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
