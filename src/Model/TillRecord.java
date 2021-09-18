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
public class TillRecord {
    String tillClosingDate, tillOpeningDate, notes;
    double cashInTotal, cashOutTotal;

    public TillRecord(String _tillOpeningDate, double _cashInTotal, double _cashOutTotal, String _notes) {
        this.tillOpeningDate = _tillOpeningDate;
        this.cashInTotal = _cashInTotal;
        this.cashOutTotal = _cashOutTotal;
        this.notes = _notes;
    }

    public String getTillClosingDate() {
        return tillClosingDate;
    }

    public void setTillClosingDate(String tillClosingDate) {
        this.tillClosingDate = tillClosingDate;
    }

    public String getTillOpeningDate() {
        return tillOpeningDate;
    }

    public void setTillOpeningDate(String tillOpeningDate) {
        this.tillOpeningDate = tillOpeningDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getCashInTotal() {
        return cashInTotal;
    }

    public void setCashInTotal(double cashInTotal) {
        this.cashInTotal = cashInTotal;
    }

    public double getCashOutTotal() {
        return cashOutTotal;
    }

    public void setCashOutTotal(double cashOutTotal) {
        this.cashOutTotal = cashOutTotal;
    }
}
