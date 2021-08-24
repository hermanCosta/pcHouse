/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class OrderReceipt extends javax.swing.JFrame {

    /**
     * Creates new form Print
     */
    
    double deposit, due, total, cash, card;
    String orderNo, firstName, lastName, contactNo, email, deviceBrand, deviceModel, 
            stringProducts, stringPrices, serialNumber, payDate;
    
    public OrderReceipt() {
        initComponents();
    }
    
    public OrderReceipt(String _orderNo, String _firstName, String _lastName, String _contactNo, String _email, 
            String _deviceBrand, String _deviceModel, String _serialNumber, String _stringProducts, String _stringPrices, 
            double _total, double _deposit, double _due, String _payDate, double _cash, double _card) {
        
        initComponents();
        
        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.contactNo = _contactNo;
        this.email = _email;
        this.deviceBrand = _deviceBrand;
        this.deviceModel = _deviceModel;
        this.serialNumber = _serialNumber;
        this.stringProducts = _stringProducts;
        this.stringPrices = _stringPrices;
        this.total = _total;
        this.payDate = _payDate;
        this.cash = _cash;
        this.card = _card;
        
        loadOrderToPrint();
    }
    
    public void loadOrderToPrint()
    {
        System.out.println("Prices: " +stringPrices);
        lbl_print_order_no.setText("Order: " + orderNo);
        lbl_print_first_name.setText("Customer name: " + firstName + " " + lastName);
        lbl_print_contact.setText("Contact no.: " + contactNo);
        lbl_print_email.setText("Email: " + email);
        lbl_print_brand.setText("Device brand: " + deviceBrand);
        lbl_print_model.setText("Device model: " + deviceModel);
        lbl_print_sn.setText("S/N: " + serialNumber);
        lbl_print_total_products.setText("Total: €" + String.valueOf(total));
        lbl_order_picked_on.setText("Order picked on: " + payDate);
        
        
        if (cash == 0)
        {
            lbl_paid_by.setText("Paid by Card: €" + card);
        }
        else if (card == 0)
        {
            lbl_paid_by.setText("Paid by Cash: €" + cash);
        }
        else
        {
            lbl_paid_by.setText("Paid by Cash: €" + cash + " | Card: €" +card);
        }
        
        
        String[] arrayProducts = stringProducts.split(",");
        String[] arrayPrices = stringPrices.split(",");
        for (String s : arrayProducts)
        {
            txt_pane_products.setText(txt_pane_products.getText() + " - " + s + " \n");
        }
        
        for (String s : arrayPrices)
        {
            txt_pane_prices.setText(txt_pane_prices.getText() + "€" + s + "\n");
        }
        
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_print_order = new javax.swing.JPanel();
        lbl_print_order_no = new javax.swing.JLabel();
        lbl_print_first_name = new javax.swing.JLabel();
        lbl_print_contact = new javax.swing.JLabel();
        lbl_print_email = new javax.swing.JLabel();
        lbl_print_brand = new javax.swing.JLabel();
        lbl_print_model = new javax.swing.JLabel();
        lbl_print_sn = new javax.swing.JLabel();
        lbl_print_total_products = new javax.swing.JLabel();
        lbl_product_service = new javax.swing.JLabel();
        lbl_receipt = new javax.swing.JLabel();
        lbl_paid_by = new javax.swing.JLabel();
        lbl_order_picked_on = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txt_pane_products = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        txt_pane_prices = new javax.swing.JTextPane();
        lbl_price = new javax.swing.JLabel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon1 = new javax.swing.JLabel();
        lbl_land_line_number1 = new javax.swing.JLabel();
        lbl_mobile_number1 = new javax.swing.JLabel();
        lbl_address1 = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        lbl_payment_print_view = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setPreferredSize(new java.awt.Dimension(595, 842));

        lbl_print_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_order_no.setText("orderNo");

        lbl_print_first_name.setText("fullName");

        lbl_print_contact.setText("contactNo");

        lbl_print_email.setText("emailAddress");

        lbl_print_brand.setText("deviceBrand");

        lbl_print_model.setText("deviceModel");

        lbl_print_sn.setText("serialNumber");

        lbl_print_total_products.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_total_products.setText("totalProducts");

        lbl_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_product_service.setText("Product | Service");

        lbl_receipt.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_receipt.setText("Payment Receipt");

        lbl_paid_by.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lbl_paid_by.setText("paidBy");

        lbl_order_picked_on.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lbl_order_picked_on.setText("issueDate");

        jScrollPane8.setBorder(null);
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setToolTipText("");
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txt_pane_products.setEditable(false);
        txt_pane_products.setBorder(null);
        txt_pane_products.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_products.setFocusable(false);
        txt_pane_products.setOpaque(false);
        jScrollPane8.setViewportView(txt_pane_products);

        jScrollPane11.setBorder(null);
        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txt_pane_prices.setEditable(false);
        txt_pane_prices.setBorder(null);
        txt_pane_prices.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_prices.setFocusable(false);
        txt_pane_prices.setOpaque(false);
        jScrollPane11.setViewportView(txt_pane_prices);

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_price.setText("Price");

        panel_header.setBackground(new java.awt.Color(255, 255, 255));

        lbl_logo_icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_land_line_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_phone_number.png"))); // NOI18N
        lbl_land_line_number1.setText("+353 (01) 563-9520");

        lbl_mobile_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_mobile_number.png"))); // NOI18N
        lbl_mobile_number1.setText("+353 (83) 012-8190");

        lbl_address1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address1.setText("12A, Frederick Street North, Dublin 1");

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_headerLayout.createSequentialGroup()
                        .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_mobile_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_land_line_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_address1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addComponent(lbl_land_line_number1)
                        .addGap(5, 5, 5)
                        .addComponent(lbl_mobile_number1)
                        .addGap(5, 5, 5)
                        .addComponent(lbl_address1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_print_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_order_no, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_brand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_sn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_total_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_paid_by, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_order_picked_on, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8)
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addComponent(lbl_product_service)
                                .addGap(283, 283, 283)))
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_price)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addComponent(lbl_receipt)
                        .addGap(217, 217, 217))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lbl_receipt)
                .addGap(21, 21, 21)
                .addComponent(lbl_print_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_contact)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_email)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_brand)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_model)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_sn)
                .addGap(30, 30, 30)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_product_service)
                    .addComponent(lbl_price))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_print_total_products)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_paid_by)
                .addGap(3, 3, 3)
                .addComponent(lbl_order_picked_on)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(21, 76, 121));
        jButton1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lbl_payment_print_view.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lbl_payment_print_view.setText("Payment Receipt Print View");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_payment_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_payment_print_view))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Receipt Order" + this.orderNo);
        
        PageFormat format = printerJob.getPageFormat(null);
        
        printerJob.setPrintable(new Printable() {
            
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                
                if(pageIndex > 0)
                {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D)graphics;
                panel_print_order.paint(graphics2D);
                
              return Printable.PAGE_EXISTS;
            }
        }, format);
        
        boolean returningResult = printerJob.printDialog();
        
        if(returningResult)
        {
            try {
                printerJob.print();
                
                JOptionPane.showMessageDialog(this, "Receipt: " + orderNo + " Printed Successfully", "Payment Receipt", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

            } catch (PrinterException ex) {
                Logger.getLogger(OrderReceipt.class.getName()).log(Level.SEVERE, null, ex);
                this.dispose();
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(OrderReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderReceipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_land_line_number1;
    private javax.swing.JLabel lbl_logo_icon1;
    private javax.swing.JLabel lbl_mobile_number1;
    private javax.swing.JLabel lbl_order_picked_on;
    private javax.swing.JLabel lbl_paid_by;
    private javax.swing.JLabel lbl_payment_print_view;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_print_brand;
    private javax.swing.JLabel lbl_print_contact;
    private javax.swing.JLabel lbl_print_email;
    private javax.swing.JLabel lbl_print_first_name;
    private javax.swing.JLabel lbl_print_model;
    private javax.swing.JLabel lbl_print_order_no;
    private javax.swing.JLabel lbl_print_sn;
    private javax.swing.JLabel lbl_print_total_products;
    private javax.swing.JLabel lbl_product_service;
    private javax.swing.JLabel lbl_receipt;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JTextPane txt_pane_prices;
    private javax.swing.JTextPane txt_pane_products;
    // End of variables declaration//GEN-END:variables
}
