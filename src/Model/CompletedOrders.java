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
public class CompletedOrders {
    String orderNo, firstName, lastName, stringProducts, payDate;
    double total, deposit, due, cash, card, changeTotal;

    public CompletedOrders(String _orderNo, String _firstName, String _lastName, String _stringProducts, 
            double _total, double _deposit, double _due, String _payDate, double _cash, double _card, double _changeTotal) {
        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.stringProducts = _stringProducts;
        this.total = _total;
        this.deposit = _deposit;
        this.due = _due;
        this.payDate = _payDate;
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

    public String getStringProducts() {
        return stringProducts;
    }

    public void setStringProducts(String stringProducts) {
        this.stringProducts = stringProducts;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public double getChangeTotal() {
        return changeTotal;
    }

    public void setChangeTotal(double changeTotal) {
        this.changeTotal = changeTotal;
    }
    
    
    
}
