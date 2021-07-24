/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registering;

/**
 *
 * @author user
 */
public class Order extends Device {
    String orderNo, status;
    double deposit, due;
    
    
    public Order(String orderNo, String firstName, String lastName, String email, String brand, String model, String serialNumber, String fault, String importantNotes, double deposit, double due, String status) {
        super(brand, model, serialNumber, fault, importantNotes, firstName, lastName, email);
        this.orderNo = orderNo;
        this.status = status;
        this.deposit = deposit;
        this.due = due;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
