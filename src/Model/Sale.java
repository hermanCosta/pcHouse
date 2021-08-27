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
    
    String saleNo, productService, qty, unitPrice, priceTotal, saleDate;
    double total;
    
    public Sale(String _saleNo, String _firstName, String _lastName, String _contactNo, String _email,
            String _productService, String _qty, String _unitPrice, String _priceTotal, 
            double _total, String _saleDate) {
        super(_firstName, _lastName, _contactNo, _email);
        
        this.saleNo = _saleNo;
        this.productService = _productService;
        this.qty = _qty;
        this.unitPrice = _unitPrice;
        this.total = _total;
        this.saleDate = _saleDate;
        
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String sellingNo) {
        this.saleNo = sellingNo;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String price) {
        this.priceTotal = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String sellingDate) {
        this.saleDate = sellingDate;
    }
}
