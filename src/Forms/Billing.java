/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.CompletedOrder;
import InternalForms.FinishedOrder;
import InternalForms.NewOrder;
import InternalForms.Receipt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class Billing extends javax.swing.JFrame {

    Connection con;
    PreparedStatement ps;
    
    String orderNo, firstName,  lastName, contactNo, email,  deviceBrand,  
           deviceModel,  serialNumber, importantNotes, stringFaults, 
           stringProducts, stringPrices, issueDate, finishedDate, payDate; 
    double total, deposit, due, cash, card;
    
    public Billing() {
        initComponents();
    }

    public Billing(String _orderNo, String _firstName, String _lastName, String _contactNo, String _email, 
            String _deviceBrand, String _deviceModel, String _serialNumber, String _importantNotes, 
            String _stringFaults, String _stringProducts, String _stringPrices, double _total, double _deposit, 
            double _due, String _issueDate, String _finishedDate) {

        initComponents();

        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.contactNo = _contactNo;
        this.email = _email;
        this.deviceBrand = _deviceBrand;
        this.deviceModel = _deviceModel;
        this.serialNumber = _serialNumber;
        this.importantNotes = _importantNotes;
        this.issueDate = _issueDate;
        this.stringFaults = _stringFaults;
        this.stringProducts = _stringProducts;
        this.stringPrices = _stringPrices;
        this.total = _total;
        this.deposit = _deposit;
        this.due = _due;
        this.finishedDate = _finishedDate;

        lbl_order_no.setText(this.orderNo);
        lbl_total.setText(String.valueOf(this.total));
        lbl_deposit.setText(String.valueOf(this.deposit));
        lbl_due.setText(String.valueOf(this.due));
    }

    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","root","hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void completeOrder() {
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panel_billing = new javax.swing.JPanel();
        lbl_order_payment = new javax.swing.JLabel();
        lbl_order_total = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        btn_pay = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbl_order_no = new javax.swing.JLabel();
        txt_card = new javax.swing.JTextField();
        lbl_cash = new javax.swing.JLabel();
        lbl_card = new javax.swing.JLabel();
        txt_cash = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lbl_total_due = new javax.swing.JLabel();
        lbl_deposit = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbl_total_due1 = new javax.swing.JLabel();
        lbl_due = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_billing.setBackground(new java.awt.Color(21, 76, 121));

        lbl_order_payment.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbl_order_payment.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_payment.setText("Order Payment");

        lbl_order_total.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_order_total.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_total.setText("Order Total: ");

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(255, 255, 255));
        lbl_total.setText("total");

        btn_pay.setBackground(new java.awt.Color(0, 153, 51));
        btn_pay.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_pay.setText("Pay");
        btn_pay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_payMouseClicked(evt);
            }
        });
        btn_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_payActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Order No: ");

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_order_no.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_no.setText("orderNo");

        txt_card.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        txt_card.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cardActionPerformed(evt);
            }
        });
        txt_card.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cardKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cardKeyReleased(evt);
            }
        });

        lbl_cash.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_cash.setForeground(new java.awt.Color(255, 255, 255));
        lbl_cash.setText("Cash €");

        lbl_card.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_card.setForeground(new java.awt.Color(255, 255, 255));
        lbl_card.setText("Card €");

        txt_cash.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        txt_cash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cashActionPerformed(evt);
            }
        });
        txt_cash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cashKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cashKeyReleased(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));

        lbl_total_due.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_total_due.setForeground(new java.awt.Color(255, 255, 255));
        lbl_total_due.setText("Deposit Paid €");

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_deposit.setForeground(new java.awt.Color(255, 255, 255));
        lbl_deposit.setText("deposit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_total_due)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_total_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));

        lbl_total_due1.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_total_due1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_total_due1.setText("Total Due €");

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_due.setForeground(new java.awt.Color(255, 255, 255));
        lbl_due.setText("due");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_total_due1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_total_due1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lbl_due))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout panel_billingLayout = new javax.swing.GroupLayout(panel_billing);
        panel_billing.setLayout(panel_billingLayout);
        panel_billingLayout.setHorizontalGroup(
            panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_billingLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_billingLayout.createSequentialGroup()
                        .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_order_total)
                            .addComponent(jLabel5))
                        .addGap(29, 29, 29)
                        .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_order_no)
                            .addComponent(lbl_total)))
                    .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_billingLayout.createSequentialGroup()
                            .addComponent(lbl_card)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_card))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_billingLayout.createSequentialGroup()
                            .addComponent(lbl_cash)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_cash))
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_billingLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btn_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_billingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_order_payment)
                .addGap(59, 59, 59))
        );
        panel_billingLayout.setVerticalGroup(
            panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_billingLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_order_payment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_order_no))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_order_total)
                    .addComponent(lbl_total))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_cash)
                    .addComponent(txt_cash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_card)
                    .addComponent(txt_card))
                .addGap(56, 56, 56)
                .addComponent(btn_pay, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(112, 112, 112))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_billing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_billing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cardActionPerformed

    private void txt_cardKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cardKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cardKeyReleased

    private void btn_payMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_payMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_payMouseClicked

    private void btn_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_payActionPerformed
        // TODO add your handling code here:
        Date date = new Date();
        Timestamp currentDateTime = new Timestamp(date.getTime());
        payDate = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(currentDateTime);
        
        
        double totalPaid = 0;
        
        if (txt_cash.getText().isEmpty())
        {
            card = Double.parseDouble(txt_card.getText());
            totalPaid = card;
        }
        else if (txt_card.getText().isEmpty())
        {
            cash = Double.parseDouble(txt_cash.getText());
            totalPaid = cash;
        }
        else
        {
            cash = Double.parseDouble(txt_cash.getText());
            card = Double.parseDouble(txt_card.getText());
            totalPaid = cash + card;
        }
        
        due  = due - totalPaid;
        
        if (due == 0)
        {
          JOptionPane.showMessageDialog(null, "Order: " + orderNo + " paid Successfully", "Order Payment",  JOptionPane.INFORMATION_MESSAGE);
          try {
            dbConnection();

            String queryUpdate = "UPDATE orderDetails SET status = 'Completed' WHERE orderNo = ?";
            ps = con.prepareStatement(queryUpdate);
            ps.setString(1, orderNo);
            ps.executeUpdate();
            
            String queryInsert = "INSERT INTO completedOrders VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(queryInsert);
            ps.setString(1, orderNo);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, stringProducts);
            ps.setDouble(5, total);
            ps.setDouble(6, due);
            ps.setDouble(7, totalPaid);
            ps.setString(8, payDate);
            ps.executeUpdate();

            } catch (SQLException ex) {
            Logger.getLogger(Billing.class.getName()).log(Level.SEVERE, null, ex);
            }

          this.dispose();
          
          new CompletedOrder(orderNo, firstName, lastName, contactNo, email, deviceBrand, deviceModel, 
                  serialNumber, importantNotes, stringFaults, stringProducts, stringPrices, total, 
                  deposit, due, issueDate, finishedDate,payDate).setVisible(true);
          
          FinishedOrder finishedOrder = new FinishedOrder();
          finishedOrder.getComponent(3);
          
          
          
          
                  
        }
        else
        {
           JOptionPane.showMessageDialog(null, "Values don't match! please, have a check !", "Order Payment",  JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_payActionPerformed

    private void txt_cashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cashActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cashActionPerformed

    private void txt_cashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cashKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cashKeyReleased

    private void txt_cashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cashKeyPressed
         // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            
            txt_cash.setEditable(false);
        }
        else
        {
            txt_cash.setEditable(true);
        }       
    }//GEN-LAST:event_txt_cashKeyPressed

    private void txt_cardKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cardKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            
            txt_card.setEditable(false);
        }
        else
        {
            txt_card.setEditable(true);
        }       
    }//GEN-LAST:event_txt_cardKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Billing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_pay;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl_card;
    private javax.swing.JLabel lbl_cash;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_order_payment;
    private javax.swing.JLabel lbl_order_total;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_total_due;
    private javax.swing.JLabel lbl_total_due1;
    private javax.swing.JPanel panel_billing;
    private javax.swing.JTextField txt_card;
    private javax.swing.JTextField txt_cash;
    // End of variables declaration//GEN-END:variables
}
