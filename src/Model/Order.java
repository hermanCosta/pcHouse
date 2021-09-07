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
public class Order extends Device {
    String orderNo, importantNotes, stringFaults, stringProducts, stringQty, unitPrice, priceTotal, status, 
            issueDate, finishDate, pickDate, refundDate, userName;
    double deposit, cashDeposit, cardDeposit, due, total ;
    
    
    public Order(String _orderNo, String _firstName, String _lastName, String _contactNo, String _email, 
            String _brand, String _model, String _serialNumber, String _importantNotes, String _stringFaults, 
            String _stringProducts, String _stringQty, String _unitPrice, String _priceTotal, double _total, 
            double _deposit, double _cashDeposit, double _cardDeposit,double _due, String _status, String _issuedDate, 
            String _finishDate, String _pickDate, String _refundDate, String _username) {
        super(_brand, _model, _serialNumber, _firstName, _lastName, _contactNo, _email);
        
        this.orderNo = _orderNo;
        this.stringFaults = _stringFaults;
        this.importantNotes = _importantNotes;
        this.stringProducts = _stringProducts;
        this.stringQty = _stringQty;
        this.unitPrice = _unitPrice;
        this.priceTotal = _priceTotal;
        this.total = _total;
        this.deposit = _deposit;
        this.cashDeposit = _cashDeposit;
        this.cardDeposit = _cardDeposit;
        this.due = _due;
        this.status = _status;
        this.issueDate = _issuedDate;
        this.finishDate = _finishDate;
        this.pickDate = _pickDate;
        this.refundDate = _refundDate;
        this.userName = _username;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getStringFaults() {
        return stringFaults;
    }

    public void setStringFaults(String stringFaults) {
        this.stringFaults = stringFaults;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }
    
    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
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
    
    public String getImportantNotes() {
        return importantNotes;
    }

    public void setImportantNotes(String importantNotes) {
        this.importantNotes = importantNotes;
    }
    
    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issuedDate) {
        this.issueDate = issuedDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }
}
