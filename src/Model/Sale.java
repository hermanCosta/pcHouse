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

    String saleNo, stringProducts, stringQty, stringUnitPrice, stringPriceTotal, saleDate, status, createdBy;
    double total, cash, card, changeTotal;

    public Sale(String _saleNo, String _firstName, String _lastName, String _contactNo, String _email,
            String _stringProducts, String _stringQty, String _stringUnitPrice, String _stringPriceTotal,
            double _total, String _saleDate, double _cash, double _card, double _changeTotal, String _status, String _createdBy) {
        super(_firstName, _lastName, _contactNo, _email);

        this.saleNo = _saleNo;
        this.stringProducts = _stringProducts;
        this.stringQty = _stringQty;
        this.stringUnitPrice = _stringUnitPrice;
        this.stringPriceTotal = _stringPriceTotal;
        this.total = _total;
        this.saleDate = _saleDate;
        this.cash = _cash;
        this.card = _card;
        this.changeTotal = _changeTotal;
        this.status = _status;
        this.createdBy = _createdBy;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String sellingNo) {
        this.saleNo = sellingNo;
    }

    public String getStringProducts() {
        return stringProducts;
    }

    public void setStringProducts(String stringProducts) {
        this.stringProducts = stringProducts;
    }

    public String getStringQty() {
        return stringQty;
    }

    public void setStringQty(String stringQty) {
        this.stringQty = stringQty;
    }

    public String getStringUnitPrice() {
        return stringUnitPrice;
    }

    public void setStringUnitPrice(String stringUnitPrice) {
        this.stringUnitPrice = stringUnitPrice;
    }

    public String getStringPriceTotal() {
        return stringPriceTotal;
    }

    public void setStringPriceTotal(String stringPriceTotal) {
        this.stringPriceTotal = stringPriceTotal;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
