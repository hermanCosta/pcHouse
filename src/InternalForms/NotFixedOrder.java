/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.DepositPayment;
import Forms.OrderNotes;
import Model.CompletedOrder;
import Model.Customer;
import Model.Order;
import Model.ProductService;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author HermanCosta
 */
public class NotFixedOrder extends javax.swing.JInternalFrame {

    ArrayList firstNames = new ArrayList();
    ArrayList faults = new ArrayList();
    ArrayList lastNames = new ArrayList();

    Vector faultsTable;
    Vector vecUpdateFaults = new Vector();
    Vector vecUpdateProducts = new Vector();
    Vector vecUpdatePrices = new Vector();

    Connection con;
    PreparedStatement ps;
    Statement stmt;
    Customer customer;
    ProductService productService;
    Order order;
    CompletedOrder completedOrders;
    ResultSet rs;
    ResultSetMetaData rsmd;

    public NotFixedOrder() {
        initComponents();

    }

    NotFixedOrder(Order _order, CompletedOrder _completedOrders) {
        initComponents();
        this.order = _order;
        this.completedOrders = _completedOrders;

        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);//avoid auto old value by focus loosing

        tableSettings(table_view_faults);
        tableSettings(table_view_products);
        loadSelectedOrder();
    }

    public void tableSettings(JTable table) {
        table.getTableHeader().setEnabled(false);
        table.setRowHeight(25);
        table.getTableHeader().setForeground(Color.gray);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setForeground(Color.gray);
    }

    public void loadSelectedOrder() {
        DefaultTableModel faultsModel = (DefaultTableModel) table_view_faults.getModel();
        faultsModel.setRowCount(0);
        DefaultTableModel productsModel = (DefaultTableModel) table_view_products.getModel();
        TableColumnModel tableModel = table_view_products.getColumnModel();

        if (order.getPickDate() != null && !order.getPickDate().trim().isEmpty()) {
            lbl_order_picked_on.setVisible(true);
            lbl_order_picked_on.setText("Picked on: " + order.getPickDate());

            if (order.getCashDeposit() == 0 && order.getCardDeposit() == 0)
                lbl_refunded_by.setVisible(false);
            else if (order.getCashDeposit() == 0)
                lbl_refunded_by.setText("Deposit Refunded by Card: €" + order.getCardDeposit());
            else
                lbl_refunded_by.setText("Deposit Refunded by Cash: €" + order.getCashDeposit());

            btn_undo.setVisible(false);
            btn_pick_up.setVisible(false);
            btn_notes.setVisible(false);
            btn_notes1.setVisible(true);
        } else {
            lbl_order_picked_on.setVisible(false);
            lbl_refunded_by.setVisible(false);
            btn_undo.setVisible(true);
            btn_pick_up.setVisible(true);
            btn_notes.setVisible(true);
            btn_notes1.setVisible(false);
        }

        lbl_order_status.setText("Order Not Fixed");
        lbl_date.setText("date: " + order.getFinishDate());
        lbl_order_created_on.setText("Created on: " + order.getIssueDate() + " - by " + order.getUsername());
        lbl_auto_order_no.setText(order.getOrderNo());
        txt_first_name.setText(order.getFirstName());
        txt_last_name.setText(order.getLastName());
        txt_contact.setText(order.getContactNo());
        txt_email.setText(order.getEmail());
        txt_brand.setText(order.getBrand());
        txt_model.setText(order.getModel());
        txt_sn.setText(order.getSerialNumber());
        editor_pane_notes.setText(order.getImportantNotes());
        txt_total.setText(String.valueOf(order.getTotal()));
        txt_deposit.setText(String.valueOf(order.getDeposit()));
        txt_due.setText(String.valueOf(order.getDue()));

        // Array for holding database String 
        String[] arrayFaults = order.getStringFaults().split(",");
        String[] arrayProducts = order.getStringProducts().split(",");
        String[] arrayQty = order.getStringQty().split(",");
        String[] arrayUnitPrice = order.getUnitPrice().split(",");
        String[] arrayPriceTotal = order.getPriceTotal().split(",");

        //Iterate arrayProducts and pass elements to faults table
        for (Object objFaults : arrayFaults) {
            faultsModel.addRow(new Object[]{objFaults});
        }

        // Pass arrayPrices to a vector and add as a new column
        Vector vecProducts = new Vector();
        Vector vecQty = new Vector();
        Vector vecUnitPrice = new Vector();
        Vector vecPriceTotal = new Vector();

        vecProducts.addAll(Arrays.asList(arrayProducts));
        vecQty.addAll(Arrays.asList(arrayQty));
        vecUnitPrice.addAll(Arrays.asList(arrayUnitPrice));
        vecPriceTotal.addAll(Arrays.asList(arrayPriceTotal));

        //Add New Columns into the table_view_products with data as a vector
        productsModel.addColumn("Product | Service", vecProducts);
        productsModel.addColumn("Qty", vecQty);
        productsModel.addColumn("Unit €", vecUnitPrice);
        productsModel.addColumn("Total €", vecPriceTotal);

        // Set width size for columns through its index
        tableModel.getColumn(1).setMaxWidth(40);
        tableModel.getColumn(2).setMaxWidth(80);
        tableModel.getColumn(3).setMaxWidth(80);
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NotFixedOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNoteEvent(String updateNote) {
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        String dateFormat = new SimpleDateFormat("yyyy/MM/dd").format(currentDate);

        try {
            dbConnection();

            String note = "Order tagged as '" + updateNote + "' by " + order.getUsername();
            String user = "System";

            String queryUpdate = "INSERT INTO orderNotes(orderNo, date, note, user) VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(queryUpdate);
            ps.setString(1, order.getOrderNo());
            ps.setString(2, dateFormat);
            ps.setString(3, note);
            ps.setString(4, user);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DepositPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_not_fixed_order = new javax.swing.JDesktopPane();
        panel_order_details = new javax.swing.JPanel();
        btn_pick_up = new javax.swing.JButton();
        btn_notes1 = new javax.swing.JButton();
        lbl_order_no = new javax.swing.JLabel();
        lbl_first_name = new javax.swing.JLabel();
        lbl_last_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_brand = new javax.swing.JLabel();
        lbl_model = new javax.swing.JLabel();
        lbl_sn = new javax.swing.JLabel();
        lbl_auto_order_no = new javax.swing.JLabel();
        lbl_price = new javax.swing.JLabel();
        lbl_deposit = new javax.swing.JLabel();
        lbl_due = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_last_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_contact = new javax.swing.JFormattedTextField();
        txt_brand = new javax.swing.JTextField();
        txt_model = new javax.swing.JTextField();
        txt_sn = new javax.swing.JTextField();
        txt_deposit = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_view_faults = new javax.swing.JTable();
        txt_due = new javax.swing.JTextField();
        btn_undo = new javax.swing.JButton();
        btn_notes = new javax.swing.JButton();
        lbl_order_created_on = new javax.swing.JLabel();
        panel_order_status = new javax.swing.JPanel();
        lbl_order_status = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_order_picked_on = new javax.swing.JLabel();
        lbl_refunded_by = new javax.swing.JLabel();
        jScrollPane_notes = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();

        setMaximumSize(new java.awt.Dimension(1049, 827));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        desktop_pane_not_fixed_order.setPreferredSize(new java.awt.Dimension(1049, 700));

        panel_order_details.setPreferredSize(new java.awt.Dimension(1049, 700));
        panel_order_details.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pick_up.setBackground(new java.awt.Color(255, 51, 51));
        btn_pick_up.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_pick_up.setForeground(new java.awt.Color(255, 255, 255));
        btn_pick_up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_not_fix.png"))); // NOI18N
        btn_pick_up.setText("Pick Up");
        btn_pick_up.setToolTipText("");
        btn_pick_up.setFocusable(false);
        btn_pick_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pick_upActionPerformed(evt);
            }
        });
        panel_order_details.add(btn_pick_up, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 70, 180, 50));

        btn_notes1.setBackground(new java.awt.Color(21, 76, 121));
        btn_notes1.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_notes1.setForeground(new java.awt.Color(255, 255, 255));
        btn_notes1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_notes.png"))); // NOI18N
        btn_notes1.setText("Notes");
        btn_notes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_notes1ActionPerformed(evt);
            }
        });
        panel_order_details.add(btn_notes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 70, 180, 50));

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_order_no.setText("Order Number");
        lbl_order_no.setEnabled(false);
        panel_order_details.add(lbl_order_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 93, -1, -1));

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");
        lbl_first_name.setEnabled(false);
        panel_order_details.add(lbl_first_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 130, -1, -1));

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");
        lbl_last_name.setEnabled(false);
        panel_order_details.add(lbl_last_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 172, -1, -1));

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");
        lbl_contact.setEnabled(false);
        panel_order_details.add(lbl_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 214, -1, -1));

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");
        lbl_email.setEnabled(false);
        panel_order_details.add(lbl_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 256, -1, -1));

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_brand.setText("Device Brand");
        lbl_brand.setEnabled(false);
        panel_order_details.add(lbl_brand, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 298, -1, -1));

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_model.setText("Device Model");
        lbl_model.setEnabled(false);
        panel_order_details.add(lbl_model, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 340, -1, -1));

        lbl_sn.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_sn.setText("Serial Number");
        lbl_sn.setEnabled(false);
        panel_order_details.add(lbl_sn, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 382, -1, -1));

        lbl_auto_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_auto_order_no.setText("autoGen");
        lbl_auto_order_no.setEnabled(false);
        panel_order_details.add(lbl_auto_order_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 89, -1, 24));

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_price.setText("Total €");
        lbl_price.setEnabled(false);
        panel_order_details.add(lbl_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(856, 570, -1, -1));

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_deposit.setText("Deposit €");
        lbl_deposit.setEnabled(false);
        panel_order_details.add(lbl_deposit, new org.netbeans.lib.awtextra.AbsoluteConstraints(639, 570, -1, -1));

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_due.setText("Due €");
        lbl_due.setEnabled(false);
        panel_order_details.add(lbl_due, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 570, -1, -1));

        txt_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_first_name.setActionCommand("null");
        txt_first_name.setEnabled(false);
        txt_first_name.setFocusable(false);
        txt_first_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_first_nameActionPerformed(evt);
            }
        });
        txt_first_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_first_nameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_first_nameKeyReleased(evt);
            }
        });
        panel_order_details.add(txt_first_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 125, 300, 30));

        txt_last_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_last_name.setEnabled(false);
        txt_last_name.setFocusable(false);
        txt_last_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_last_nameActionPerformed(evt);
            }
        });
        txt_last_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_last_nameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_last_nameKeyReleased(evt);
            }
        });
        panel_order_details.add(txt_last_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 167, 300, 30));

        txt_email.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_email.setEnabled(false);
        txt_email.setFocusable(false);
        txt_email.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emailKeyPressed(evt);
            }
        });
        panel_order_details.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 251, 348, 30));

        try {
            txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(0##) ###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_contact.setEnabled(false);
        txt_contact.setFocusable(false);
        txt_contact.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactActionPerformed(evt);
            }
        });
        txt_contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactKeyReleased(evt);
            }
        });
        panel_order_details.add(txt_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 209, 300, 30));

        txt_brand.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_brand.setEnabled(false);
        txt_brand.setFocusable(false);
        txt_brand.setMinimumSize(new java.awt.Dimension(63, 20));
        txt_brand.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_brandActionPerformed(evt);
            }
        });
        panel_order_details.add(txt_brand, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 293, 286, 30));

        txt_model.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_model.setEnabled(false);
        txt_model.setFocusable(false);
        txt_model.setPreferredSize(new java.awt.Dimension(63, 20));
        panel_order_details.add(txt_model, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 335, 284, 30));

        txt_sn.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_sn.setEnabled(false);
        txt_sn.setFocusable(false);
        txt_sn.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_sn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_snActionPerformed(evt);
            }
        });
        txt_sn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_snKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_snKeyReleased(evt);
            }
        });
        panel_order_details.add(txt_sn, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 377, 278, 30));

        txt_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_deposit.setForeground(new java.awt.Color(51, 51, 255));
        txt_deposit.setText("0");
        txt_deposit.setEnabled(false);
        txt_deposit.setFocusable(false);
        txt_deposit.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_depositActionPerformed(evt);
            }
        });
        txt_deposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_depositKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_depositKeyReleased(evt);
            }
        });
        panel_order_details.add(txt_deposit, new org.netbeans.lib.awtextra.AbsoluteConstraints(728, 567, 110, 30));

        txt_total.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_total.setEnabled(false);
        txt_total.setFocusable(false);
        txt_total.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        txt_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_totalKeyPressed(evt);
            }
        });
        panel_order_details.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(921, 567, 110, 30));

        table_view_products.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        table_view_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_view_products.setEnabled(false);
        table_view_products.setFocusable(false);
        table_view_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_productsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_products);

        panel_order_details.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 335, 575, 224));

        table_view_faults.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        table_view_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fault Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_faults.setEnabled(false);
        table_view_faults.setFocusable(false);
        table_view_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_faultsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_view_faults);

        panel_order_details.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 135, 575, 188));

        txt_due.setEditable(false);
        txt_due.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_due.setForeground(new java.awt.Color(255, 0, 51));
        txt_due.setEnabled(false);
        txt_due.setFocusable(false);
        txt_due.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dueActionPerformed(evt);
            }
        });
        panel_order_details.add(txt_due, new org.netbeans.lib.awtextra.AbsoluteConstraints(511, 567, 110, 30));

        btn_undo.setBackground(new java.awt.Color(21, 76, 121));
        btn_undo.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_undo.setForeground(new java.awt.Color(255, 255, 255));
        btn_undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_undo.png"))); // NOI18N
        btn_undo.setText("Undo");
        btn_undo.setFocusable(false);
        btn_undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_undoActionPerformed(evt);
            }
        });
        panel_order_details.add(btn_undo, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 68, 180, 50));

        btn_notes.setBackground(new java.awt.Color(21, 76, 121));
        btn_notes.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_notes.setForeground(new java.awt.Color(255, 255, 255));
        btn_notes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_notes.png"))); // NOI18N
        btn_notes.setText("Notes");
        btn_notes.setFocusable(false);
        btn_notes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_notesActionPerformed(evt);
            }
        });
        panel_order_details.add(btn_notes, new org.netbeans.lib.awtextra.AbsoluteConstraints(654, 68, 180, 50));

        lbl_order_created_on.setBackground(new java.awt.Color(204, 204, 204));
        lbl_order_created_on.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_order_created_on.setText("orderCreatedOn");
        lbl_order_created_on.setEnabled(false);
        panel_order_details.add(lbl_order_created_on, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 66, 399, -1));

        panel_order_status.setBackground(new java.awt.Color(255, 102, 102));
        panel_order_status.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_order_status.setBackground(new java.awt.Color(0, 102, 102));
        lbl_order_status.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_status.setForeground(java.awt.Color.white);
        lbl_order_status.setText("orderStatus");
        panel_order_status.add(lbl_order_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 3, 385, -1));

        lbl_date.setForeground(java.awt.Color.white);
        lbl_date.setText("date");
        panel_order_status.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 29, -1, -1));

        lbl_order_picked_on.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_picked_on.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_picked_on.setText("orderPickedOn");
        lbl_order_picked_on.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        panel_order_status.add(lbl_order_picked_on, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 3, 329, -1));

        lbl_refunded_by.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        lbl_refunded_by.setForeground(new java.awt.Color(255, 255, 255));
        lbl_refunded_by.setText("refundedBy");
        panel_order_status.add(lbl_refunded_by, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, -1, -1));

        panel_order_details.add(panel_order_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 6, 1010, 50));

        jScrollPane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setEnabled(false);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 16))); // NOI18N
        editor_pane_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        editor_pane_notes.setEnabled(false);
        jScrollPane_notes.setViewportView(editor_pane_notes);

        panel_order_details.add(jScrollPane_notes, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 419, 399, 178));

        desktop_pane_not_fixed_order.setLayer(panel_order_details, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_not_fixed_orderLayout = new javax.swing.GroupLayout(desktop_pane_not_fixed_order);
        desktop_pane_not_fixed_order.setLayout(desktop_pane_not_fixed_orderLayout);
        desktop_pane_not_fixed_orderLayout.setHorizontalGroup(
            desktop_pane_not_fixed_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        desktop_pane_not_fixed_orderLayout.setVerticalGroup(
            desktop_pane_not_fixed_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_not_fixed_order, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_not_fixed_order, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_last_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_last_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameActionPerformed

    private void txt_first_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_first_nameActionPerformed

    }//GEN-LAST:event_txt_first_nameActionPerformed

    private void txt_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_brandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_brandActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
        double sum = 0;
        for (int i = 0; i < table_view_products.getRowCount(); i++)
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 1).toString());

        txt_total.setText(Double.toString(sum));
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_depositKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyReleased
        // TODO add your handling code here:
        //Calculate deposit paid and display due value
        if (txt_deposit.getText() == null || txt_deposit.getText().trim().isEmpty()) {
            txt_due.setText(txt_total.getText());
            txt_deposit.setText(Double.toString(0));
        } else {
            double priceTotal = Double.parseDouble(txt_total.getText());
            double deposit = Double.parseDouble(txt_deposit.getText());
            double total = priceTotal - deposit;

            txt_due.setText(Double.toString(total));
        }
    }//GEN-LAST:event_txt_depositKeyReleased

    private void txt_snActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_snActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_snActionPerformed

    private void txt_first_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
    }//GEN-LAST:event_txt_first_nameKeyPressed

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailKeyPressed

    private void txt_snKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_snKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_snKeyPressed

    private void txt_snKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_snKeyReleased
        // TODO add your handling code here:
        txt_sn.setText(txt_sn.getText().toUpperCase());
    }//GEN-LAST:event_txt_snKeyReleased

    private void txt_first_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_first_nameKeyReleased

    private void txt_last_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyReleased

    private void txt_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))

            txt_total.setEditable(false);
        else
            txt_total.setEditable(true);
    }//GEN-LAST:event_txt_totalKeyPressed

    private void txt_depositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))
            txt_deposit.setEditable(false);
        else
            txt_deposit.setEditable(true);
    }//GEN-LAST:event_txt_depositKeyPressed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_productsMouseClicked

    private void table_view_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_faultsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_faultsMouseClicked

    private void txt_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_depositActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_depositActionPerformed

    private void txt_dueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dueActionPerformed

    private void txt_last_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyPressed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactKeyReleased

    private void btn_pick_upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pick_upActionPerformed
        // TODO add your handling code here:
        int confirmUndoing = JOptionPane.showConfirmDialog(null, "Confirm Picking Order: "
                + order.getOrderNo() + " ?", "Order Not Fixed", JOptionPane.YES_NO_OPTION);
        if (confirmUndoing == 0) {
            try {
                dbConnection();

                java.util.Date date = new java.util.Date();
                java.sql.Timestamp currentDateTime = new java.sql.Timestamp(date.getTime());
                String pickDate = new SimpleDateFormat("dd/MM/yyyy").format(currentDateTime);
                order.setPickDate(pickDate);

                String query = "UPDATE orderDetails SET pickDate = ? WHERE orderNo = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, order.getPickDate());
                ps.setString(2, order.getOrderNo());
                ps.executeUpdate();

                lbl_order_picked_on.setText("Picked on: " + order.getPickDate());
                lbl_order_picked_on.setVisible(true);
                btn_undo.setVisible(false);
                btn_pick_up.setVisible(false);
                btn_notes.setVisible(false);
                btn_notes1.setVisible(true);

                addNoteEvent("Picked");

                if (order.getDeposit() > 0) {
                    double negativeDeposit = order.getDeposit();
                    double negativeCash = order.getCashDeposit();
                    double negativeCard = order.getCardDeposit();

                    order.setDeposit(negativeDeposit *= -1);
                    order.setCashDeposit(negativeCash *= -1);
                    order.setCardDeposit(negativeCard *= -1);

                    String queryDepositRefund = "INSERT INTO completedOrders(orderNo, firstName, lastName, "
                            + "brand, model, total, deposit, due, cash, card, changeTotal, cashDeposit, "
                            + "cardDeposit, payDate, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    ps = con.prepareStatement(queryDepositRefund);
                    ps.setString(1, order.getOrderNo());
                    ps.setString(2, order.getFirstName());
                    ps.setString(3, order.getLastName());
                    ps.setString(4, order.getBrand());
                    ps.setString(5, order.getModel());
                    ps.setDouble(6, 0);
                    ps.setDouble(7, order.getDeposit());
                    ps.setDouble(8, 0);
                    ps.setDouble(9, order.getCashDeposit());
                    ps.setDouble(10, order.getCardDeposit());
                    ps.setDouble(11, 0);
                    ps.setDouble(12, 0);
                    ps.setDouble(13, 0);
                    ps.setString(14, order.getPickDate());
                    ps.setString(15, order.getStatus());
                    ps.executeUpdate();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NotFixedOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_pick_upActionPerformed

    private void btn_undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_undoActionPerformed
        // TODO add your handling code here:
        int confirmUndoing = JOptionPane.showConfirmDialog(this, "Do you really want to 'UNDO' Order: "
                + order.getOrderNo() + " ?", "Undo Order", JOptionPane.YES_NO_OPTION);
        if (confirmUndoing == 0) {
            String status = "In Progress";
            order.setStatus(status);

            try {
                dbConnection();

                String query = "UPDATE orderDetails SET status = ?, finishDate = ? WHERE orderNo = ? ";
                ps = con.prepareStatement(query);
                ps.setString(1, order.getStatus());
                ps.setString(2, null);
                ps.setString(3, order.getOrderNo());
                ps.executeUpdate();

                addNoteEvent(status);

                OrderDetails orderDetails = new OrderDetails(order, completedOrders);
                desktop_pane_not_fixed_order.removeAll();
                desktop_pane_not_fixed_order.add(orderDetails).setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(NotFixedOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_undoActionPerformed

    private void btn_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notesActionPerformed
        // TODO add your handling code here:
        OrderNotes orderNotes = new OrderNotes(order.getOrderNo(), order.getUsername());
        orderNotes.setVisible(true);
    }//GEN-LAST:event_btn_notesActionPerformed

    private void btn_notes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notes1ActionPerformed
        // TODO add your handling code here:
        OrderNotes orderNotes = new OrderNotes(order.getOrderNo(), order.getUsername());
        orderNotes.setVisible(true);
    }//GEN-LAST:event_btn_notes1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_notes;
    private javax.swing.JButton btn_notes1;
    private javax.swing.JButton btn_pick_up;
    private javax.swing.JButton btn_undo;
    private javax.swing.JDesktopPane desktop_pane_not_fixed_order;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane_notes;
    private javax.swing.JLabel lbl_auto_order_no;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_order_created_on;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_order_picked_on;
    private javax.swing.JLabel lbl_order_status;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_refunded_by;
    private javax.swing.JLabel lbl_sn;
    private javax.swing.JPanel panel_order_details;
    private javax.swing.JPanel panel_order_status;
    private javax.swing.JTable table_view_faults;
    private javax.swing.JTable table_view_products;
    private javax.swing.JTextField txt_brand;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_deposit;
    private javax.swing.JTextField txt_due;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_model;
    private javax.swing.JTextField txt_sn;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
