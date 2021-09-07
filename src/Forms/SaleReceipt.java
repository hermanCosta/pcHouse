/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.SaleDetails;
import InternalForms.NewSale;
import Model.Sale;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class SaleReceipt extends javax.swing.JFrame {

    Sale sale;
    boolean isSaleDetails;

    public SaleReceipt() {
        initComponents();
    }

    public SaleReceipt(Sale _sale, boolean _isSaleDetails) {
        initComponents();
        this.sale = _sale;
        this.isSaleDetails = _isSaleDetails;

        loadOrderToPrint();
    }

    public void loadOrderToPrint() {
        lbl_print_order_no.setText("Order: " + sale.getSaleNo());
        lbl_print_first_name.setText("Customer name: " + sale.getFirstName() + " " + sale.getLastName());
        lbl_print_contact.setText("Contact no.: " + sale.getContactNo());
        lbl_print_email.setText("Email: " + sale.getEmail());
        lbl_print_total_products.setText("Total: €" + String.valueOf(sale.getTotal()));
        lbl_date.setText("Date: " + sale.getSaleDate());
        lbl_change.setText("Change: €" + sale.getChangeTotal());

        if (sale.getCash() == 0)
            lbl_paid_by.setText("Paid by Card: €" + sale.getCard());
        else if (sale.getCard() == 0)
            lbl_paid_by.setText("Paid by Cash: €" + sale.getCash());
        else
            lbl_paid_by.setText("Paid by Cash: €" + sale.getCash() + " | Card: €" + sale.getCard());

        String[] arrayProducts = sale.getStringProducts().split(",");
        String[] arrayQty = sale.getStringQty().split(",");
        String[] arrayUnitPrice = sale.getStringUnitPrice().split(",");
        String[] arrayPriceTotal = sale.getStringPriceTotal().split(",");

        for (String s : arrayProducts)
            txt_pane_products.setText(txt_pane_products.getText() + " - " + s + " \n");

        for (String s : arrayQty)
            txt_pane_qty.setText(txt_pane_qty.getText() + s + "\n");

        for (String s : arrayUnitPrice)
            txt_pane_unit_price.setText(txt_pane_unit_price.getText() + "€" + s + "\n");

        for (String s : arrayPriceTotal)
            txt_pane_total.setText(txt_pane_total.getText() + "€" + s + "\n");
    }

    public void backToPreviousFrame() {
        if (isSaleDetails) {
            SaleDetails saleDetails = new SaleDetails(sale);
            MainMenu.mainMenuDesktopPane.removeAll();
            MainMenu.mainMenuDesktopPane.add(saleDetails).setVisible(true);
            
        } else {
            NewSale newSale = new NewSale();
            MainMenu.mainMenuDesktopPane.removeAll();
            MainMenu.mainMenuDesktopPane.add(newSale).setVisible(true);
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            backToPreviousFrame();
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
        lbl_print_total_products = new javax.swing.JLabel();
        lbl_receipt = new javax.swing.JLabel();
        lbl_paid_by = new javax.swing.JLabel();
        lbl_change = new javax.swing.JLabel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon1 = new javax.swing.JLabel();
        lbl_land_line_number1 = new javax.swing.JLabel();
        lbl_mobile_number1 = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        panel_products = new javax.swing.JPanel();
        lbl_product_service = new javax.swing.JLabel();
        lbl_unit_price = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        txt_pane_unit_price = new javax.swing.JTextPane();
        lbl_qty = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txt_pane_qty = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        txt_pane_products = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        txt_pane_total = new javax.swing.JTextPane();
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

        lbl_print_total_products.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_total_products.setText("totalProducts");

        lbl_receipt.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_receipt.setText("Payment Receipt");

        lbl_paid_by.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lbl_paid_by.setText("paidBy");

        lbl_change.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lbl_change.setText("change");

        panel_header.setBackground(new java.awt.Color(255, 255, 255));

        lbl_logo_icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_land_line_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_phone_number.png"))); // NOI18N
        lbl_land_line_number1.setText("+353 (01) 563-9520");

        lbl_mobile_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_mobile_number.png"))); // NOI18N
        lbl_mobile_number1.setText("+353 (83) 012-8190");

        lbl_address1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_address.png"))); // NOI18N
        lbl_address1.setText("12A, Frederick Street North, Dublin 1");

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_headerLayout.createSequentialGroup()
                        .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_mobile_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_land_line_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_address1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lbl_land_line_number1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_mobile_number1)
                        .addGap(0, 0, 0)
                        .addComponent(lbl_address1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_date.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lbl_date.setText("date");

        panel_products.setBackground(new java.awt.Color(255, 255, 255));

        lbl_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_product_service.setText("Product | Service");

        lbl_unit_price.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_unit_price.setText("Unit €");

        jScrollPane12.setBorder(null);
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane12.setDoubleBuffered(true);
        jScrollPane12.setEnabled(false);

        txt_pane_unit_price.setEditable(false);
        txt_pane_unit_price.setBorder(null);
        txt_pane_unit_price.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_unit_price.setFocusable(false);
        txt_pane_unit_price.setOpaque(false);
        jScrollPane12.setViewportView(txt_pane_unit_price);

        lbl_qty.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_qty.setText("Qty");

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_total.setText("Total €");

        jScrollPane11.setBorder(null);
        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane11.setDoubleBuffered(true);
        jScrollPane11.setEnabled(false);

        txt_pane_qty.setEditable(false);
        txt_pane_qty.setBorder(null);
        txt_pane_qty.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_qty.setFocusable(false);
        txt_pane_qty.setOpaque(false);
        jScrollPane11.setViewportView(txt_pane_qty);

        jScrollPane8.setBorder(null);
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setToolTipText("");
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.setDoubleBuffered(true);
        jScrollPane8.setEnabled(false);

        txt_pane_products.setEditable(false);
        txt_pane_products.setBorder(null);
        txt_pane_products.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_products.setFocusable(false);
        txt_pane_products.setOpaque(false);
        jScrollPane8.setViewportView(txt_pane_products);

        jScrollPane10.setBorder(null);
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane10.setDoubleBuffered(true);
        jScrollPane10.setEnabled(false);

        txt_pane_total.setEditable(false);
        txt_pane_total.setBorder(null);
        txt_pane_total.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_total.setFocusable(false);
        txt_pane_total.setOpaque(false);
        jScrollPane10.setViewportView(txt_pane_total);

        javax.swing.GroupLayout panel_productsLayout = new javax.swing.GroupLayout(panel_products);
        panel_products.setLayout(panel_productsLayout);
        panel_productsLayout.setHorizontalGroup(
            panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_product_service)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_qty)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_unit_price))
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_productsLayout.createSequentialGroup()
                        .addComponent(lbl_total)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10))
                .addGap(6, 6, 6))
        );
        panel_productsLayout.setVerticalGroup(
            panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_product_service)
                    .addComponent(lbl_qty)
                    .addComponent(lbl_unit_price)
                    .addComponent(lbl_total))
                .addGap(4, 4, 4)
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane10)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_print_order_no, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_total_products, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addComponent(lbl_paid_by, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_change, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(lbl_receipt)
                .addGap(217, 217, 217))
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_receipt)
                .addGap(18, 18, 18)
                .addComponent(lbl_print_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_contact)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_email)
                .addGap(18, 18, 18)
                .addComponent(panel_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_print_total_products)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_paid_by)
                .addGap(3, 3, 3)
                .addComponent(lbl_change)
                .addGap(3, 3, 3)
                .addComponent(lbl_date)
                .addContainerGap(80, Short.MAX_VALUE))
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
        printerJob.setJobName("Sale" + sale.getSaleNo());

        PageFormat format = printerJob.getPageFormat(null);

        printerJob.setPrintable(new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D) graphics;
                panel_print_order.paint(graphics2D);

                return Printable.PAGE_EXISTS;
            }
        }, format);

        boolean returningResult = printerJob.printDialog();

        if (returningResult) {
            try {
                printerJob.print();

                JOptionPane.showMessageDialog(this, "Receipt: " + sale.getSaleNo() + " Printed Successfully", "Payment Receipt", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                backToPreviousFrame();

            } catch (PrinterException ex) {
                Logger.getLogger(SaleReceipt.class.getName()).log(Level.SEVERE, null, ex);
                this.dispose();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_change;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_land_line_number1;
    private javax.swing.JLabel lbl_logo_icon1;
    private javax.swing.JLabel lbl_mobile_number1;
    private javax.swing.JLabel lbl_paid_by;
    private javax.swing.JLabel lbl_payment_print_view;
    private javax.swing.JLabel lbl_print_contact;
    private javax.swing.JLabel lbl_print_email;
    private javax.swing.JLabel lbl_print_first_name;
    private javax.swing.JLabel lbl_print_order_no;
    private javax.swing.JLabel lbl_print_total_products;
    private javax.swing.JLabel lbl_product_service;
    private javax.swing.JLabel lbl_qty;
    private javax.swing.JLabel lbl_receipt;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_unit_price;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JPanel panel_products;
    private javax.swing.JTextPane txt_pane_products;
    private javax.swing.JTextPane txt_pane_qty;
    private javax.swing.JTextPane txt_pane_total;
    private javax.swing.JTextPane txt_pane_unit_price;
    // End of variables declaration//GEN-END:variables
}
