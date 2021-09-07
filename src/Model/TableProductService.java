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
public class TableProductService {

    String productService, qty, unitPrice, priceTotal;

    public TableProductService(String _productService, String _qty, String _unitPrice, String _priceTotal) {

        this.productService = _productService;
        this.qty = _qty;
        this.unitPrice = _unitPrice;
        this.priceTotal = _priceTotal;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
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

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
