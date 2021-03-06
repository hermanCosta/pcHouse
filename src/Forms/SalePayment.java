/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.NewOrder;
import Model.Sale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author HermanCosta
 */
public class SalePayment extends javax.swing.JFrame {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Sale sale;
    JTable tableViewProducts;
    double deposit, total, cash, card, totalPaid, changeTotal;
    String saleNo, firstName, lastName, contactNo, email, stringProducts, stringQty, stringUnitprice, 
            stringPriceTotal, saleDate, formatContactNo;
    
    public SalePayment() {
        initComponents();
    }

    public SalePayment(Sale _sale, JTable _tableViewProducts, String _formatContactNo) {
        initComponents();
        setResizable(false);  
        this.sale = _sale;
        this.tableViewProducts = _tableViewProducts;
        this.formatContactNo = _formatContactNo;
        
        lbl_sale_no.setText(sale.getSaleNo());
        lbl_total.setText(String.valueOf(sale.getTotal()));
    }
    
    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","root","hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateProductQty()
    {
        for (int i = 0; i < tableViewProducts.getRowCount() ; i++)
        {
            String cellProduct = tableViewProducts.getValueAt(i, 0).toString();
            int cellQty = (int) tableViewProducts.getValueAt(i, 1);

            try {
                dbConnection();
                String queryCheckProduct = "SELECT qty, category FROM products WHERE productService = ?";
                ps = con.prepareStatement(queryCheckProduct);
                ps.setString(1, cellProduct);
                rs = ps.executeQuery();
                
                while(rs.next())
                {
                    if (rs.getString("category").equals("Product"))
                    {
                        int productQty = rs.getInt("qty");
                        int updateProdQty = productQty - cellQty;

                        String queryUpdate = "UPDATE products SET qty = ? WHERE productService = ?";
                        ps = con.prepareStatement(queryUpdate);
                        ps.setInt(1, updateProdQty);
                        ps.setString(2, cellProduct);
                        ps.executeUpdate();
                    }
                }
                
                if (cellProduct.contains("|"))
                {
                    String[] split = cellProduct.split("|");
                    int computerId = Integer.parseInt(split[0]);
                    
                    
                    String queryCompQty = "SELECT qty FROM computers WHERE computerId = ?";
                    ps = con.prepareStatement(queryCompQty);
                    ps.setInt(1, computerId);
                    rs = ps.executeQuery();
                    
                    while (rs.next())
                    {
                        int compQty = rs.getInt("qty");
                        int updateCompQty = compQty - cellQty;
                        System.out.println("Old Comp Qty: " + compQty);
                    
                        String queryUpdateCompQty = "UPDATE computers SET qty = ? WHERE computerId = ?";
                        ps = con.prepareStatement(queryUpdateCompQty);
                        ps.setInt(1, updateCompQty);
                        ps.setInt(2, computerId);
                        ps.executeUpdate();
                    }
                    
                    ps.close();
                    con.close(); 
                }
            } catch (SQLException ex) {
                Logger.getLogger(SalePayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addNoteEvent() {
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        String dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentDate);

        try {
            dbConnection();

            String note = "Sale done by " + sale.getUsername();
            String user = "System";

            String queryUpdate = "INSERT INTO orderNotes(orderNo, date, note, user) VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(queryUpdate);
            ps.setString(1, sale.getSaleNo());
            ps.setString(2, dateFormat);
            ps.setString(3, note);
            ps.setString(4, user);
            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DepositPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_billing = new javax.swing.JPanel();
        lbl_order_payment = new javax.swing.JLabel();
        btn_pay = new javax.swing.JButton();
        lbl_sale_date = new javax.swing.JLabel();
        lbl_sale_no = new javax.swing.JLabel();
        txt_card = new javax.swing.JTextField();
        lbl_cash = new javax.swing.JLabel();
        lbl_card = new javax.swing.JLabel();
        txt_cash = new javax.swing.JTextField();
        panel_deposit = new javax.swing.JPanel();
        lbl_sale_total = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        panel_deposit2 = new javax.swing.JPanel();
        lbl_change??? = new javax.swing.JLabel();
        lbl_change = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_billing.setBackground(new java.awt.Color(21, 76, 121));

        lbl_order_payment.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbl_order_payment.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_payment.setText("Payment");

        btn_pay.setBackground(new java.awt.Color(21, 76, 121));
        btn_pay.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_pay.setForeground(new java.awt.Color(255, 255, 255));
        btn_pay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_pay.png"))); // NOI18N
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

        lbl_sale_date.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_sale_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_sale_date.setText("Sale No: ");

        lbl_sale_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_sale_no.setForeground(new java.awt.Color(255, 255, 255));
        lbl_sale_no.setText("saleNo");

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
        lbl_cash.setText("Cash ???");

        lbl_card.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_card.setForeground(new java.awt.Color(255, 255, 255));
        lbl_card.setText("Card ???");

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

        panel_deposit.setBackground(new java.awt.Color(21, 76, 121));
        panel_deposit.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_sale_total.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        lbl_sale_total.setForeground(new java.awt.Color(255, 255, 255));
        lbl_sale_total.setText("Sale Total ???");

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(255, 255, 255));
        lbl_total.setText("total");

        javax.swing.GroupLayout panel_depositLayout = new javax.swing.GroupLayout(panel_deposit);
        panel_deposit.setLayout(panel_depositLayout);
        panel_depositLayout.setHorizontalGroup(
            panel_depositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_depositLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_sale_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_depositLayout.setVerticalGroup(
            panel_depositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_depositLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_depositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_sale_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        panel_deposit2.setBackground(new java.awt.Color(255, 153, 153));
        panel_deposit2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_change???.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        lbl_change???.setForeground(new java.awt.Color(255, 255, 255));
        lbl_change???.setText("Change ???");

        lbl_change.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_change.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panel_deposit2Layout = new javax.swing.GroupLayout(panel_deposit2);
        panel_deposit2.setLayout(panel_deposit2Layout);
        panel_deposit2Layout.setHorizontalGroup(
            panel_deposit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_deposit2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_change???)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_change, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_deposit2Layout.setVerticalGroup(
            panel_deposit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_deposit2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_deposit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_change???, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_change, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_billingLayout = new javax.swing.GroupLayout(panel_billing);
        panel_billing.setLayout(panel_billingLayout);
        panel_billingLayout.setHorizontalGroup(
            panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_billingLayout.createSequentialGroup()
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_billingLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_billingLayout.createSequentialGroup()
                                .addComponent(lbl_sale_date)
                                .addGap(47, 47, 47)
                                .addComponent(lbl_sale_no))
                            .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_billingLayout.createSequentialGroup()
                                    .addComponent(lbl_card)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_card))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_billingLayout.createSequentialGroup()
                                    .addComponent(lbl_cash)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_cash))
                                .addComponent(panel_deposit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel_deposit2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_billingLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btn_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel_billingLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(lbl_order_payment)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panel_billingLayout.setVerticalGroup(
            panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_billingLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_order_payment)
                .addGap(30, 30, 30)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_sale_date)
                    .addComponent(lbl_sale_no))
                .addGap(34, 34, 34)
                .addComponent(panel_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_cash)
                    .addComponent(txt_cash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_billingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_card)
                    .addComponent(txt_card))
                .addGap(56, 56, 56)
                .addComponent(btn_pay, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(45, 45, 45)
                .addComponent(panel_deposit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
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
        sale.setStatus("Paid");
        boolean isPaid = false;
        
        if (txt_cash.getText().isEmpty() && txt_card.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Values can not be Empty !", "Payment",  JOptionPane.ERROR_MESSAGE);
            return;
        } else if (txt_cash.getText().isEmpty() && !txt_card.getText().isEmpty())
        {
            sale.setCard(Double.parseDouble(txt_card.getText()));
            
            if (sale.getCard() > sale.getTotal())
            {
                JOptionPane.showMessageDialog(this, "Payment by Card can't be greater than Total Due !", "Payment",  JOptionPane.ERROR_MESSAGE); 
                return;
            }
            else if ((sale.getTotal() - sale.getCard()) <= 0) 
            {
                isPaid = true; 
                sale.setCash(0);
            }
        }
        
        else if (!txt_cash.getText().isEmpty() && txt_card.getText().isEmpty())
        {
            sale.setCash(Double.parseDouble(txt_cash.getText()));
            if ((sale.getTotal() - sale.getCash()) <= 0)
            {
                isPaid = true;
                sale.setCard(0);
            }
        }
        else if (!txt_cash.getText().isEmpty() && !txt_card.getText().isEmpty())
        {
            sale.setCash(Double.parseDouble(txt_cash.getText()));
            sale.setCard(Double.parseDouble(txt_card.getText()));
            
            totalPaid = sale.getCash() + sale.getCard();
            if (sale.getCard() > sale.getTotal())
            {
                JOptionPane.showMessageDialog(this, "Payment by Card can't be greater than Total Due !", "Payment",  JOptionPane.ERROR_MESSAGE); 
                return;
            } 
            else if ((sale.getTotal() - totalPaid) <= 0)
            {
               isPaid = true;
            }
        }
        
        if (isPaid)
        {
            changeTotal = (sale.getCash() + sale.getCard()) - sale.getTotal();
            lbl_change.setText(String.valueOf(changeTotal));
            sale.setChangeTotal(changeTotal);
            
         int confirmPayment = JOptionPane.showConfirmDialog(this, "Confirm payment on Sale " 
                 + sale.getSaleNo() + " ?", "Payment", JOptionPane.YES_NO_OPTION);
         
          if (confirmPayment == 0)  
          {
            try {
              dbConnection();

                   String query = "INSERT INTO sales(saleNo, firstName, lastName, contactNo, "
                           + "email, productService, qty, unitPrice, priceTotal, total, saleDate, cash, card, changeTotal, status, createdBy) "
                           + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                  ps = con.prepareStatement(query);
                  ps.setString(1, sale.getSaleNo());
                  ps.setString(2, sale.getFirstName());
                  ps.setString(3, sale.getLastName()); 
                  ps.setString(4, sale.getContactNo());
                  ps.setString(5, sale.getEmail());
                  ps.setString(6, sale.getStringProducts());
                  ps.setString(7, sale.getStringQty());
                  ps.setString(8, sale.getStringUnitPrice());
                  ps.setString(9, sale.getStringPriceTotal());
                  ps.setDouble(10, sale.getTotal());
                  ps.setString(11, sale.getSaleDate());
                  ps.setDouble(12, sale.getCash());
                  ps.setDouble(13, sale.getCard());
                  ps.setDouble(14, sale.getChangeTotal());
                  ps.setString(15, sale.getStatus());
                  ps.setString(16, sale.getUsername());
                  ps.executeUpdate();
                 
                JOptionPane.showMessageDialog(this,sale.getSaleNo() + " Paid Successfully", "Payment",  JOptionPane.INFORMATION_MESSAGE);
              
                updateProductQty();
                addNoteEvent();
                        
                boolean isSaleDetails = false;
                SaleReceipt saleReceipt =  new SaleReceipt(sale, isSaleDetails, formatContactNo);
                saleReceipt.setVisible(true);

                ps.close();
                con.close();
                this.dispose();

              } catch (SQLException ex) {
                Logger.getLogger(SalePayment.class.getName()).log(Level.SEVERE, null, ex);
                
              }
          }
        }
        else
        {
           JOptionPane.showMessageDialog(this, "Values don't match! please, check !", "Order Payment",  JOptionPane.ERROR_MESSAGE);
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
         if (Character.isLetter(evt.getKeyChar())) 
            txt_card.setEditable(false);
        else
            txt_card.setEditable(true);
        
    }//GEN-LAST:event_txt_cashKeyPressed

    private void txt_cardKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cardKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar())) 
            txt_card.setEditable(false);
        else
            txt_card.setEditable(true);
    }//GEN-LAST:event_txt_cardKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_pay;
    private javax.swing.JLabel lbl_card;
    private javax.swing.JLabel lbl_cash;
    private javax.swing.JLabel lbl_change;
    private javax.swing.JLabel lbl_change???;
    private javax.swing.JLabel lbl_order_payment;
    private javax.swing.JLabel lbl_sale_date;
    private javax.swing.JLabel lbl_sale_no;
    private javax.swing.JLabel lbl_sale_total;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel panel_billing;
    private javax.swing.JPanel panel_deposit;
    private javax.swing.JPanel panel_deposit2;
    private javax.swing.JTextField txt_card;
    private javax.swing.JTextField txt_cash;
    // End of variables declaration//GEN-END:variables
}
