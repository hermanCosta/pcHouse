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
public class SalesReport {
    String saleNo, saleFirstName, saleLastName, saleTotal, salePayDate, saleCash, saleCard;
    
    
    public SalesReport(String _saleNo, String _saleFirstName, 
            String _saleLastName, String _saleTotal, String _salePayDate, String _saleCash, String _saleCard) {
        
        this.saleNo = _saleNo;
        this.saleFirstName = _saleFirstName;
        this.saleLastName = _saleLastName;
        this.saleTotal = _saleTotal;
        this.salePayDate = _salePayDate;
        this.saleCash = _saleCash;
        this.saleCard = _saleCard;
    }
          
    
    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public String getSaleFirstName() {
        return saleFirstName;
    }

    public void setSaleFirstName(String saleFirstName) {
        this.saleFirstName = saleFirstName;
    }

    public String getSaleLastName() {
        return saleLastName;
    }

    public void setSaleLastName(String saleLastName) {
        this.saleLastName = saleLastName;
    }

    public String getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(String saleTotal) {
        this.saleTotal = saleTotal;
    }

    public String getSalePayDate() {
        return salePayDate;
    }

    public void setSalePayDate(String salePayDate) {
        this.salePayDate = salePayDate;
    }

    public String getSaleCash() {
        return saleCash;
    }

    public void setSaleCash(String saleCash) {
        this.saleCash = saleCash;
    }

    public String getSaleCard() {
        return saleCard;
    }

    public void setSaleCard(String saleCard) {
        this.saleCard = saleCard;
    }
    
}
