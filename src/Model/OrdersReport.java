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
public class OrdersReport {
    
    String orderNo, firstName, lastName, productsService, due, cash, card, deposit;
    
    public OrdersReport(String _orderNo, String _orderFirstName, String _orderLastName, String _productsService, String _orderTotal, 
             String _deposit, String _orderCash, String _orderCard) {
        
        this.orderNo = _orderNo;
        this.firstName = _orderFirstName;
        this.lastName = _orderLastName;
        this.productsService = _productsService;
        this.due = _orderTotal;
        this.deposit = _deposit;
        this.cash = _orderCash;
        this.card = _orderCard;
        
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
