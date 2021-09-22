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
 * @author HermanCosta
 */
public class PrintOrder extends javax.swing.JFrame {

    /**
     * Creates new form Print
     */
    Order order;
    CompletedOrder completedOrder;
    boolean isOrderDetails;
    String formatContactNo;
    
    public PrintOrder() {
        initComponents();
    }
    
    public PrintOrder(Order _order, CompletedOrder _completedOrder, boolean _isOrderDetails, String _formatContactNo) {
        initComponents();
        setResizable(false);  
        this.order = _order;
        this.isOrderDetails = _isOrderDetails;
        this.completedOrder = _completedOrder;
        this.formatContactNo = _formatContactNo;
        
        loadOrderToPrint();
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING) {
            backToPreviousFrame();
        }
    }
    
    public void loadOrderToPrint()
    {
        lbl_print_issue_date.setText("Date: " + order.getIssueDate());
        lbl_print_order_no.setText("Order: " + order.getOrderNo());
        lbl_print_first_name.setText("Customer name: " + order.getFirstName() + " " + order.getLastName());
        lbl_print_contact.setText("Contact no.: " + formatContactNo);
        lbl_print_email.setText("Email: " + order.getEmail());
        lbl_print_brand.setText("Device brand: " + order.getBrand());
        lbl_print_model.setText("Device model: " + order.getModel());
        lbl_print_sn.setText("S/N: " + order.getSerialNumber());
        
        if (order.getImportantNotes().trim().isEmpty())
        {
            lbl_important_notes.setVisible(false);
            scroll_pane_important_notes.setVisible(false);
        }
        else 
        {
            lbl_important_notes.setVisible(true);
            scroll_pane_important_notes.setVisible(true);
            txt_pane_important_notes.setText(order.getImportantNotes());
        }
        
        txt_pane_print_faults.setText(order.getStringFaults());
        lbl_print_total_products.setText("Total: €" + String.valueOf(order.getTotal()));
        lbl_print_due.setText("Due: €" + String.valueOf(order.getDue()));
        
        if (order.getDeposit() == 0)
            lbl_print_deposit.setText("Deposit paid: €" +order.getDeposit());
        else if (order.getCashDeposit() == 0)
            lbl_print_deposit.setText("Deposit paid: €" + String.valueOf(order.getCardDeposit()) + " by Card");
        else if (order.getCardDeposit() == 0)
            lbl_print_deposit.setText("Deposit paid: €" + String.valueOf(order.getCashDeposit()) + " by Cash ");
        else 
            lbl_print_deposit.setText("Deposit paid: €" + String.valueOf(order.getCashDeposit()) + " by Cash " +
            " | €" + String.valueOf(order.getCardDeposit() + " by Card"));
       
        String[] arrayProducts = order.getStringProducts().split(",");
        String[] arrayQty = order.getStringQty().split(",");
        String[] arrayUnitPrice = order.getUnitPrice().split(",");
        String[] arrayPriceTotal = order.getPriceTotal().split(",");
        
        for (String s : arrayProducts)
            txt_pane_products.setText(txt_pane_products.getText() + " - " + s + "\n");
        
        for (String s : arrayQty)
            txt_pane_qty.setText(txt_pane_qty.getText() + s + "\n");
        
        for (String s : arrayUnitPrice)
            txt_pane_unit_price.setText(txt_pane_unit_price.getText() + "€" + s + "\n");
        
        for (String s : arrayPriceTotal)
            txt_pane_total.setText(txt_pane_total.getText() + "€" + s + "\n");
    }

    public void backToPreviousFrame()
    {
        if (isOrderDetails)
        {
            OrderDetails orderDetails = new OrderDetails(order, completedOrder);
            MainMenu.mainMenuDesktopPane.removeAll();
            MainMenu.mainMenuDesktopPane.add(orderDetails).setVisible(true);
        }
        
        else
        {
            NewOrder newOrder = new NewOrder();
            MainMenu.mainMenuDesktopPane.removeAll();
            MainMenu.mainMenuDesktopPane.add(newOrder).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        panel_print_order = new javax.swing.JPanel();
        lbl_print_order_no = new javax.swing.JLabel();
        lbl_print_first_name = new javax.swing.JLabel();
        lbl_print_contact = new javax.swing.JLabel();
        lbl_print_email = new javax.swing.JLabel();
        lbl_print_brand = new javax.swing.JLabel();
        lbl_print_model = new javax.swing.JLabel();
        lbl_print_sn = new javax.swing.JLabel();
        lbl_print_total_products = new javax.swing.JLabel();
        lbl_print_issue_date = new javax.swing.JLabel();
        lbl_important_notes = new javax.swing.JLabel();
        scroll_pane_important_notes = new javax.swing.JScrollPane();
        txt_pane_important_notes = new javax.swing.JTextPane();
        lbl_faults = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_pane_print_faults = new javax.swing.JTextPane();
        lbl_print_deposit = new javax.swing.JLabel();
        lbl_print_due = new javax.swing.JLabel();
        line_customer_signature = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon1 = new javax.swing.JLabel();
        lbl_land_line_number1 = new javax.swing.JLabel();
        lbl_mobile_number1 = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
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
        btn_print = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();

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

        lbl_print_issue_date.setFont(new java.awt.Font("Lucida Grande", 0, 9)); // NOI18N
        lbl_print_issue_date.setText("issueDate ");
        lbl_print_issue_date.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lbl_important_notes.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_important_notes.setText("Important Notes:");

        scroll_pane_important_notes.setBorder(null);
        scroll_pane_important_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_pane_important_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll_pane_important_notes.setEnabled(false);

        txt_pane_important_notes.setEditable(false);
        txt_pane_important_notes.setBorder(null);
        txt_pane_important_notes.setFont(new java.awt.Font("Lucida Grande", 0, 9)); // NOI18N
        txt_pane_important_notes.setFocusable(false);
        scroll_pane_important_notes.setViewportView(txt_pane_important_notes);

        lbl_faults.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_faults.setText("Fault:");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setEnabled(false);

        txt_pane_print_faults.setEditable(false);
        txt_pane_print_faults.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        txt_pane_print_faults.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(txt_pane_print_faults);

        lbl_print_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_deposit.setText("deposit");

        lbl_print_due.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_due.setText("due");

        line_customer_signature.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setText("Customer Signature");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("Repair Order");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
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
                .addGap(3, 3, 3)
                .addGroup(panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_productsLayout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10)
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_print_total_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbl_faults, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(scroll_pane_important_notes)
                                    .addComponent(lbl_print_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_order_no, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_print_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_email, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                                    .addComponent(lbl_print_brand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_sn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_important_notes, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addComponent(panel_products, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 16, Short.MAX_VALUE))))
                    .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lbl_print_issue_date)
                        .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(jLabel2)
                .addGap(10, 243, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(230, 230, 230))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addComponent(line_customer_signature, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_print_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_print_issue_date))
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
                .addGap(18, 18, 18)
                .addComponent(lbl_important_notes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane_important_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_faults)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbl_print_total_products)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_print_deposit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_print_due)
                .addGap(65, 65, 65)
                .addComponent(line_customer_signature, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jScrollPane4.setViewportView(panel_print_order);

        btn_print.setBackground(new java.awt.Color(21, 76, 121));
        btn_print.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        lbl_order_print_view.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbl_order_print_view.setText("Repair Order Print View");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_order_print_view)
                .addGap(105, 105, 105))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_order_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Order" + order.getOrderNo());
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
                
                JOptionPane.showMessageDialog(this, "Order " + order.getOrderNo() + " Printed Successfully", "Print Order", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                backToPreviousFrame();
                
            } catch (PrinterException ex) {
                Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_faults;
    private javax.swing.JLabel lbl_important_notes;
    private javax.swing.JLabel lbl_land_line_number1;
    private javax.swing.JLabel lbl_logo_icon1;
    private javax.swing.JLabel lbl_mobile_number1;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_print_brand;
    private javax.swing.JLabel lbl_print_contact;
    private javax.swing.JLabel lbl_print_deposit;
    private javax.swing.JLabel lbl_print_due;
    private javax.swing.JLabel lbl_print_email;
    private javax.swing.JLabel lbl_print_first_name;
    private javax.swing.JLabel lbl_print_issue_date;
    private javax.swing.JLabel lbl_print_model;
    private javax.swing.JLabel lbl_print_order_no;
    private javax.swing.JLabel lbl_print_sn;
    private javax.swing.JLabel lbl_print_total_products;
    private javax.swing.JLabel lbl_product_service;
    private javax.swing.JLabel lbl_qty;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_unit_price;
    private javax.swing.JSeparator line_customer_signature;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JPanel panel_products;
    private javax.swing.JScrollPane scroll_pane_important_notes;
    private javax.swing.JTextPane txt_pane_important_notes;
    private javax.swing.JTextPane txt_pane_print_faults;
    private javax.swing.JTextPane txt_pane_products;
    private javax.swing.JTextPane txt_pane_qty;
    private javax.swing.JTextPane txt_pane_total;
    private javax.swing.JTextPane txt_pane_unit_price;
    // End of variables declaration//GEN-END:variables
}
