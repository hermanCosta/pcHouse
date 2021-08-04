/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registering;

import org.json.JSONArray;

/**
 *
 * @author user
 */
public class Order extends Device {
    String orderNo, status, importantNotes;
    double deposit, due;
    java.sql.Timestamp issuedDate;
    JSONArray faults;
    JSONArray productsServices;
    JSONArray prices;
    
    
    public Order(String orderNo, String firstName, String lastName, String contactNo, String email, String brand, String model, String serialNumber, JSONArray faults, String importantNotes,JSONArray productsServices, JSONArray prices, double deposit, double due, String status, java.sql.Timestamp issuedDate) {
        super(brand, model, serialNumber, firstName, lastName, contactNo, email);
        this.orderNo = orderNo;
        this.faults = faults;
        this.productsServices = productsServices;
        this.prices = prices;
        this.importantNotes = importantNotes;
        this.deposit = deposit;
        this.due = due;
        this.status = status;
        this.issuedDate = issuedDate;
        
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public JSONArray getFaults() {
        return faults;
    }

    public void setFaults(JSONArray faults) {
        this.faults = faults;
    }

    public JSONArray getProductsServices() {
        return productsServices;
    }

    public void setProductsServices(JSONArray productsServices) {
        this.productsServices = productsServices;
    }

    public JSONArray getPrices() {
        return prices;
    }

    public void setPrices(JSONArray prices) {
        this.prices = prices;
    }
    

    public String getImportantNotes() {
        return importantNotes;
    }

    public void setImportantNotes(String importantNotes) {
        this.importantNotes = importantNotes;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public java.sql.Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(java.sql.Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }
}
