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
public class SaleReport {

    String saleNo, firstName, lastName, productsService;
    double total, cash, card, changeTotal;

    public SaleReport(String _saleNo, String _firstName, String _lastName,
            String _productsService, double _total, double _cash, double _card, double _changeTotal) {

        this.saleNo = _saleNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.productsService = _productsService;
        this.total = _total;
        this.cash = _cash;
        this.card = _card;
        this.changeTotal = _changeTotal;

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
