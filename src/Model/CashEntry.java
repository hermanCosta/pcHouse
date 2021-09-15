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
public class CashEntry {
    String entryDate, notes;
    double value;
    int cashEntryId;

    public CashEntry(double _value, String _entryDate, String _notes) {
        this.value = _value;
        this.entryDate = _entryDate;
        this.notes = _notes;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCashEntryId() {
        return cashEntryId;
    }

    public void setCashEntryId(int cashEntryId) {
        this.cashEntryId = cashEntryId;
    }
}
