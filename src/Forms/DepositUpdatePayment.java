/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.NewOrder;
import InternalForms.OrderDetails;
import Model.CompletedOrder;
import Model.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class DepositUpdatePayment extends javax.swing.JFrame {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Order order;
    double newDeposit;
    CompletedOrder completedOrder;

    public DepositUpdatePayment() {
        initComponents();
    }

    public DepositUpdatePayment(Order _order, CompletedOrder _completedOrder, double _newDeposit) {
        initComponents();

        this.order = _order;
        this.completedOrder = _completedOrder;
        this.newDeposit = _newDeposit;

        lbl_order_no.setText(this.order.getOrderNo());
        lbl_total.setText(String.valueOf(this.order.getTotal()));
        lbl_deposit.setText(String.valueOf(this.newDeposit));
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDepositNote(String payMethod) {
        try {
            dbConnection();

            String depositNote = "€" + newDeposit + " Deposit, paid by " + payMethod;
            String user = "System";

            String queryDeposit = "INSERT INTO orderNotes(orderNo, date, note, user) VALUES(?, ?, ?, ?)";

            ps = con.prepareStatement(queryDeposit);
            ps.setString(1, order.getOrderNo());
            ps.setString(2, order.getIssueDate());
            ps.setString(3, depositNote);
            ps.setString(4, user);
            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DepositUpdatePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void payDeposit(double cash, double card) {

        try {
            dbConnection();

            String queryDepositInsert = "INSERT INTO completedOrders(orderNo, firstName, lastName, "
                    + "brand, model, total, deposit, due, cash, card, changeTotal, cashDeposit, "
                    + "cardDeposit, payDate, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            ps = con.prepareStatement(queryDepositInsert);
            ps.setString(1, order.getOrderNo());
            ps.setString(2, order.getFirstName());
            ps.setString(3, order.getLastName());
            ps.setString(4, order.getBrand());
            ps.setString(5, order.getModel());
            ps.setDouble(6, 0);
            ps.setDouble(7, newDeposit);
            ps.setDouble(8, 0);
            ps.setDouble(9, cash);
            ps.setDouble(10, card);
            ps.setDouble(11, 0);
            ps.setDouble(12, 0);
            ps.setDouble(13, 0);
            ps.setString(14, order.getIssueDate());
            ps.setString(15, "Deposit");
            ps.executeUpdate();

            order.setDeposit((order.getDeposit() + newDeposit));
            OrderDetails orderDetails = new OrderDetails(order, completedOrder);
            MainMenu.mainMenuDesktopPane.removeAll();
            MainMenu.mainMenuDesktopPane.add(orderDetails).setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(DepositPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_billing = new javax.swing.JPanel();
        lbl_order_payment = new javax.swing.JLabel();
        lbl_order_total = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        btn_pay_by_cash = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbl_order_no = new javax.swing.JLabel();
        panel_deposit = new javax.swing.JPanel();
        lbl_deposit€ = new javax.swing.JLabel();
        lbl_deposit = new javax.swing.JLabel();
        btn_pay_by_card = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_billing.setBackground(new java.awt.Color(21, 76, 121));

        lbl_order_payment.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbl_order_payment.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_payment.setText("Deposit Payment");

        lbl_order_total.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_order_total.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_total.setText("Order Total: ");

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(255, 255, 255));
        lbl_total.setText("total");

        btn_pay_by_cash.setBackground(new java.awt.Color(21, 76, 121));
        btn_pay_by_cash.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_pay_by_cash.setForeground(new java.awt.Color(255, 255, 255));
        btn_pay_by_cash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_pay_by_cash.png"))); // NOI18N
        btn_pay_by_cash.setText("Pay by Cash");
        btn_pay_by_cash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_pay_by_cashMouseClicked(evt);
            }
        });
        btn_pay_by_cash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pay_by_cashActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Order No: ");

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_order_no.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_no.setText("orderNo");

        panel_deposit.setBackground(new java.awt.Color(255, 102, 102));
        panel_deposit.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_deposit€.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_deposit€.setForeground(new java.awt.Color(255, 255, 255));
        lbl_deposit€.setText("Deposit €");

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_deposit.setForeground(new java.awt.Color(255, 255, 255));
        lbl_deposit.setText("deposit");

        javax.swing.GroupLayout panel_depositLayout = new javax.swing.GroupLayout(panel_deposit);
        panel_deposit.setLayout(panel_depositLayout);
        panel_depositLayout.setHorizontalGroup(
            panel_depositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_depositLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_deposit€)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_depositLayout.setVerticalGroup(
            panel_depositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_depositLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_depositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_deposit€, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        btn_pay_by_card.setBackground(new java.awt.Color(21, 76, 121));
        btn_pay_by_card.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_pay_by_card.setForeground(new java.awt.Color(255, 255, 255));
        btn_pay_by_card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_pay_by_card.png"))); // NOI18N
        btn_pay_by_card.setText("Pay by Card");
        btn_pay_by_card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_pay_by_cardMouseClicked(evt);
            }
        });
        btn_pay_by_card.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pay_by_cardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_billingLayout = new javax.swing.GroupLayout(panel_billing);
        panel_billing.setLayout(panel_billingLayout);
        panel_billingLayout.setHorizontalGroup(
            panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_billingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_order_payment)
                .addGap(47, 47, 47))
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
                    .addComponent(panel_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_billingLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_pay_by_card, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_pay_by_cash, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
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
                .addComponent(panel_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btn_pay_by_cash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_pay_by_card, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(79, 79, 79))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_billing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panel_billing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_pay_by_cashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_pay_by_cashMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pay_by_cashMouseClicked

    private void btn_pay_by_cashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pay_by_cashActionPerformed
        // TODO add your handling code here:
        int confirmCardDeposit = JOptionPane.showConfirmDialog(this, "Confirm €" + newDeposit + " Deposit Payment by CASH ?", "Confirm Payment", JOptionPane.YES_NO_OPTION);
        if (confirmCardDeposit == 0) {
            order.setCashDeposit((order.getCashDeposit() + newDeposit));

            try {
                dbConnection();

                String queryUpdate = "UPDATE orderDetails SET firstName =?, lastName =?, contactNo =?, "
                        + "email =?, deviceBrand =?, deviceModel =?, serialNumber =?, importantNotes =?, fault =?, "
                        + "productService =?, qty =?, unitPrice =?, priceTotal =?, total =?, deposit = deposit + ?, cashDeposit = cashDeposit + ?, "
                        + "cardDeposit =?, due =?, status =?, issueDate =? WHERE orderNo = ?";
                
                ps = con.prepareStatement(queryUpdate);
                ps.setString(1, order.getFirstName());
                ps.setString(2, order.getLastName());
                ps.setString(3, order.getContactNo());
                ps.setString(4, order.getEmail());
                ps.setString(5, order.getBrand());
                ps.setString(6, order.getModel());
                ps.setString(7, order.getSerialNumber());
                ps.setString(8, order.getImportantNotes());
                ps.setString(9, order.getStringFaults());
                ps.setString(10, order.getStringProducts());
                ps.setString(11, order.getStringQty());
                ps.setString(12, order.getUnitPrice());
                ps.setString(13, order.getPriceTotal());
                ps.setDouble(14, order.getTotal());
                ps.setDouble(15, newDeposit); // sum deposit field to new received
                ps.setDouble(16, newDeposit); // sum cashDeposit field to the new received
                ps.setDouble(17, order.getCardDeposit());
                ps.setDouble(18, order.getDue());
                ps.setString(19, order.getStatus());
                ps.setString(20, order.getIssueDate());
                ps.setString(21, order.getOrderNo());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Order " + order.getOrderNo() + " Updated successfully!");

                String removeSpace = "UPDATE orderDetails SET fault = REPLACE(fault, '  ', ' '), "
                        + "productService = REPLACE(productService, '  ', ' '), "
                        + "qty = REPLACE(qty, '  ', ' '), "
                        + "unitPrice = REPLACE(unitPrice, '  ', ' '), "
                        + "total = REPLACE(total, '  ', ' ')";
                
                ps = con.prepareStatement(removeSpace);
                ps.executeUpdate();

                addDepositNote("Cash");
                payDeposit(newDeposit, 0);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_pay_by_cashActionPerformed

    private void btn_pay_by_cardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_pay_by_cardMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pay_by_cardMouseClicked

    private void btn_pay_by_cardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pay_by_cardActionPerformed
        // TODO add your handling code here:
        int confirmCardDeposit = JOptionPane.showConfirmDialog(this, "Confirm €" + newDeposit + " Deposit Payment by CARD ?", "Confirm Payment", JOptionPane.YES_NO_OPTION);
        if (confirmCardDeposit == 0) {
            
            order.setCardDeposit((order.getCardDeposit() + newDeposit));

            try {
                dbConnection();

                String queryUpdate = "UPDATE orderDetails SET firstName =?, lastName =?, contactNo =?, "
                        + "email =?, deviceBrand =?, deviceModel =?, serialNumber =?, importantNotes =?, fault =?, "
                        + "productService =?, qty =?, unitPrice =?, priceTotal =?, total =?, deposit = deposit + ?, cashDeposit =?, "
                        + "cardDeposit = cardDeposit + ?, due =?, status =?, issueDate =? WHERE orderNo = ?";
                
                ps = con.prepareStatement(queryUpdate);

                ps.setString(1, order.getFirstName());
                ps.setString(2, order.getLastName());
                ps.setString(3, order.getContactNo());
                ps.setString(4, order.getEmail());
                ps.setString(5, order.getBrand());
                ps.setString(6, order.getModel());
                ps.setString(7, order.getSerialNumber());
                ps.setString(8, order.getImportantNotes());
                ps.setString(9, order.getStringFaults());
                ps.setString(10, order.getStringProducts());
                ps.setString(11, order.getStringQty());
                ps.setString(12, order.getUnitPrice());
                ps.setString(13, order.getPriceTotal());
                ps.setDouble(14, order.getTotal());
                ps.setDouble(15, newDeposit); //sum deposit field
                ps.setDouble(16, order.getCashDeposit());
                ps.setDouble(17, newDeposit); // sum deposit paid by card
                ps.setDouble(18, order.getDue());
                ps.setString(19, order.getStatus());
                ps.setString(20, order.getIssueDate());
                ps.setString(21, order.getOrderNo());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Order " + order.getOrderNo() + " Updated successfully!");

                String removeSpace = "UPDATE orderDetails SET fault = REPLACE(fault, '  ', ' '), "
                        + "productService = REPLACE(productService, '  ', ' '), "
                        + "qty = REPLACE(qty, '  ', ' '), "
                        + "unitPrice = REPLACE(unitPrice, '  ', ' '), "
                        + "total = REPLACE(total, '  ', ' ')";
                
                ps = con.prepareStatement(removeSpace);
                ps.executeUpdate();

                addDepositNote("Card");
                payDeposit(0, newDeposit);
                this.dispose();
                
            } catch (SQLException ex) {
                Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_pay_by_cardActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_pay_by_card;
    private javax.swing.JButton btn_pay_by_cash;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_deposit€;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_order_payment;
    private javax.swing.JLabel lbl_order_total;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel panel_billing;
    private javax.swing.JPanel panel_deposit;
    // End of variables declaration//GEN-END:variables
}
