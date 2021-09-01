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
public class CompletedOrder extends Device {
    String orderNo, payDate, status;
    double total, deposit, due, cash, card, changeTotal, cashDeposit, cardDeposit;
    
    public CompletedOrder(String _orderNo, String _firstName, String _lastName, String _contactNo, String _email, 
            String _brand, String _model, String _serialNumber, double _total, double _deposit, 
            double _due, double _cash, double _card, double _changeTotal, double _cashDeposit, 
            double _cardDeposit, String _payDate, String _status) {
        super(_firstName, _lastName, _contactNo, _email, _brand, _model, _serialNumber);
    
    
//    public CompletedOrder(String _orderNo, String _firstName, String _lastName, String _stringProducts, 
//            double _total, double _deposit, double _due, String _payDate, double _cash, double _card, 
//            double _changeTotal, double _cashDeposit, double _cardDeposit) {
        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.contactNo = _contactNo;
        this.email = _email;
        this.brand = _brand;
        this.model = _model;
        this.serialNumber = _serialNumber;
        this.total = _total;
        this.deposit = _deposit;
        this.due = _due;
        this.cash = _cash;
        this.card = _card;
        this.changeTotal = _changeTotal;
        this.cashDeposit = _cashDeposit;
        this.cardDeposit = _cardDeposit;
        this.payDate = _payDate;
        this.status = _status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(double cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public double getCardDeposit() {
        return cardDeposit;
    }

    public void setCardDeposit(double cardDeposit) {
        this.cardDeposit = cardDeposit;
    }

    
    
}
