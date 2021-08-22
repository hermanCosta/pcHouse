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
    
    String orderNo, orderFirstName, orderLastName, orderTotal, orderPayDate, orderCash, orderCard;
    
    public OrdersReport(String _orderNo, String _orderFirstName, String _orderLastName, String _orderTotal, 
            String _orderPayDate, String _orderCash, String _orderCard) {
        
        this.orderNo = _orderNo;
        this.orderFirstName = _orderFirstName;
        this.orderLastName = _orderLastName;
        this.orderTotal = _orderTotal;
        this.orderPayDate = _orderPayDate;
        this.orderCash = _orderCash;
        this.orderCard = _orderCard;
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderFirstName() {
        return orderFirstName;
    }

    public void setOrderFirstName(String orderFirstName) {
        this.orderFirstName = orderFirstName;
    }

    public String getOrderLastName() {
        return orderLastName;
    }

    public void setOrderLastName(String orderLastName) {
        this.orderLastName = orderLastName;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderPayDate() {
        return orderPayDate;
    }

    public void setOrderPayDate(String orderPayDate) {
        this.orderPayDate = orderPayDate;
    }

    public String getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(String orderCash) {
        this.orderCash = orderCash;
    }

    public String getOrderCard() {
        return orderCard;
    }

    public void setOrderCard(String orderCard) {
        this.orderCard = orderCard;
    }
    
}
