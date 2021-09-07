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
public class OrderReport {

    String orderNo, firstName, lastName, productsService;
    double deposit, total, due, cash, card, changeTotal, cashDeposit, cardDeposit;

    public OrderReport(String _orderNo, String _firstName, String _lastName, String _productsService,
            double _deposit, double _total, double _due, double _cash, double _card, double _changeTotal,
            double _cashDeposit, double _cardDeposit) {

        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.productsService = _productsService;
        this.deposit = _deposit;
        this.total = _total;
        this.due = _due;
        this.cash = _cash;
        this.card = _card;
        this.changeTotal = _changeTotal;
        this.cashDeposit = _cashDeposit;
        this.cardDeposit = _cardDeposit;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
