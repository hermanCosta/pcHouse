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
public class Fault {
    int faultID;
    String fault;

    public Fault(String fault) {
        this.fault = fault;
    }

    public int getFaultID() {
        return faultID;
    }

    public void setFaultID(int faultID) {
        this.faultID = faultID;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }
    
    
    
    
    
}
