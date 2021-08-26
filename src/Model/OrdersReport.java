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
    
    String orderNo, firstName, lastName, productsService;
    double deposit, due, cash, card, changeTotal;
    
    public OrdersReport(String _orderNo, String _firstName, String _lastName, String _productsService,
                double _deposit, double _due, double _cash, double _card, double _changeTotal) {
        
        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.productsService = _productsService;
        this.deposit = _deposit;
        this.due = _due;
        this.cash = _cash;
        this.card = _card;
        this.changeTotal = _changeTotal;
        
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

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCard() {
        return card;
    }

    public void setCard(double card) {
        this.card = card;
    }

    
    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public double getChangeTotal() {
        return changeTotal;
    }

    public void setChangeTotal(double changeTotal) {
        this.changeTotal = changeTotal;
    }
    
    
}
