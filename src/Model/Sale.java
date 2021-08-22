/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author user
 */
public class Sale extends Customer {
    
    String sellingNo, productService, prices, sellingDate;
    double total;
    
    public Sale(String _sellingNo, String _firstName, String _lastName, String _contactNo, String _email, String _productService,
                    String _prices, double _total, String _sellingDate) {
        super(_firstName, _lastName, _contactNo, _email);
        
        this.sellingNo = _sellingNo;
        this.productService = _productService;
        this.prices = _prices;
        this.total = _total;
        this.sellingDate = _sellingDate;
        
    }

    public String getSaleNo() {
        return sellingNo;
    }

    public void setSellingNo(String sellingNo) {
        this.sellingNo = sellingNo;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String price) {
        this.prices = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }
    
    
    
    
    
}
