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
public class SalesReport {
    String saleNo, firstName, lastName, productsService, due, cash, card;
    
    
    public SalesReport(String _saleNo, String _saleFirstName, String _saleLastName, 
            String _productsService,  String _saleTotal, String _saleCash, String _saleCard) {
        
        this.saleNo = _saleNo;
        this.firstName = _saleFirstName;
        this.lastName = _saleLastName;
        this.productsService = _productsService;
        this.due = _saleTotal;
        this.cash = _saleCash;
        this.card = _saleCard;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProductsService() {
        return productsService;
    }

    public void setProductsService(String productsService) {
        this.productsService = productsService;
    }
    
    public String getTotal() {
        return due;
    }

    public void setTotal(String total) {
        this.due = total;
    }

    
    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

}
