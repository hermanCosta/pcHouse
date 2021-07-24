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
public class Device extends Costumer {
    String brand, model, serialNumber, fault, importantNotes;

    public Device(String brand, String model, String serialNumber, String fault, String importantNotes, String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.fault = fault;
        this.importantNotes = importantNotes;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }
    
    public String getImportantNotes() {
        return importantNotes;
    }

    public void setImportantNotes(String importantNotes) {
        this.importantNotes = importantNotes;
    }


}
