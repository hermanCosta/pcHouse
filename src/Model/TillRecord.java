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
    String date, notes;
    double cashInTotal, cashOutTotal;

    public TillRecord(String _date, double _cashInTotal, double _cashOutTotal, String _notes) {
        this.date = _date;
        this.cashInTotal = _cashInTotal;
        this.cashOutTotal = _cashOutTotal;
        this.notes = _notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
