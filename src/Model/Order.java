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
    String orderNo, importantNotes,status, issuedDate, fault, productService, prices;
    double deposit, due, total;
    
    
    public Order(String _orderNo, String _firstName, String _lastName, String _contactNo, String _email, String _brand, String _model, String _serialNumber, String _importantNotes, String _fault, String _productService, String _prices, double _total, double _deposit, double _due, String _status, String _issuedDate) {
        super(_brand, _model, _serialNumber, _firstName, _lastName, _contactNo, _email);
        this.orderNo = _orderNo;
        this.fault = _fault;
        this.importantNotes = _importantNotes;
        this.productService = _productService;
        this.prices = _prices;
        this.total = _total;
        this.deposit = _deposit;
        this.due = _due;
        this.status = _status;
        this.issuedDate = _issuedDate;
        
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }
    
     public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
    
    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
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
    
    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }
}
