/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.OrderNotes;
import Forms.OrderRefundReceipt;
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
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.text.MaskFormatter;

/**
 *
 * @author HermanCosta
 */
public class OrderRefund extends javax.swing.JInternalFrame {

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

    public OrderRefund() {
        initComponents();
    }

    OrderRefund(Order _order, CompletedOrder _completedOrders) {
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
        loadContactNo();
    }

    public void tableSettings(JTable table) {
        table.getTableHeader().setEnabled(false);
        table.setRowHeight(25);
        table.getTableHeader().setForeground(Color.gray);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setForeground(Color.gray);
    }
    
    public void loadContactNo() {
        switch (order.getContactNo().length()) {
            case 9:
                JFormattedTextField landLineFormat  = new JFormattedTextField(createFormatter("(##) ###-####"));
                landLineFormat.setText(order.getContactNo());
                txt_contact.setText(landLineFormat.getText());
                break;
            case 10:
                JFormattedTextField mobileFormat  = new JFormattedTextField(createFormatter("(###) ###-####"));
                mobileFormat.setText(order.getContactNo());
                txt_contact.setText(mobileFormat.getText());
                break;
            default:
                txt_contact.setText(order.getContactNo());
                break;
        }
    }
    
    protected MaskFormatter createFormatter(String s) {
    MaskFormatter formatter = null;
    try {
        formatter = new MaskFormatter(s);
    } catch (java.text.ParseException exc) {
        System.err.println("formatter is bad: " + exc.getMessage());
        System.exit(-1);
    }
    return formatter;
    }

    public void loadSelectedOrder() {
        DefaultTableModel faultsModel = (DefaultTableModel) table_view_faults.getModel();
        faultsModel.setRowCount(0);
        DefaultTableModel productsModel = (DefaultTableModel) table_view_products.getModel();
        TableColumnModel tableModel = table_view_products.getColumnModel();

        completedOrders.setCash(Math.abs(completedOrders.getCash()));
        completedOrders.setCard(Math.abs(completedOrders.getCard()));
        completedOrders.setCashDeposit(Math.abs(completedOrders.getCashDeposit()));
        completedOrders.setCardDeposit(Math.abs(completedOrders.getCardDeposit()));

        if (completedOrders.getCash() == 0 && completedOrders.getCashDeposit() == 0)
            lbl_refunded_by.setText("Refunded by Card: ???" + (completedOrders.getCard() + completedOrders.getCardDeposit()));
        else if (completedOrders.getCard() == 0 && completedOrders.getCardDeposit() == 0)
            lbl_refunded_by.setText("Refunded by Cash: ???" + ((completedOrders.getCash() + completedOrders.getCashDeposit()) - completedOrders.getChangeTotal()));
        else
            lbl_refunded_by.setText("Refunded by Cash: ???" + ((completedOrders.getCash() + completedOrders.getCashDeposit()) - completedOrders.getChangeTotal())
                    + " | Card: ???" + (completedOrders.getCard() + completedOrders.getCardDeposit()));

        lbl_order_status.setText("Order Refunded");
        lbl_date.setText("date: " + order.getRefundDate());
        lbl_order_created_on.setText("Created on: " + order.getIssueDate() + " - by " + order.getUsername());
        lbl_order_paid_on.setText("Paid on: " + order.getPickDate());
        lbl_auto_order_no.setText(order.getOrderNo());
        txt_first_name.setText(order.getFirstName());
        txt_last_name.setText(order.getLastName());
        txt_email.setText(order.getEmail());
        txt_brand.setText(order.getBrand());
        txt_model.setText(order.getModel());
        txt_sn.setText(order.getSerialNumber());
        editor_pane_important_notes.setText(order.getImportantNotes());
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
        for (Object objFaults : arrayFaults)
            faultsModel.addRow(new Object[]{objFaults});

        // Pass arrayPrices to a vector and add as a new column
        Vector vecProducts = new Vector();
        Vector vecQty = new Vector();
        Vector vecUnitPrice = new Vector();
        Vector vecPriceTotal = new Vector();

        vecProducts.removeAllElements();
        vecQty.removeAllElements();
        vecUnitPrice.removeAllElements();
        vecPriceTotal.removeAllElements();

        vecProducts.addAll(Arrays.asList(arrayProducts));
        vecQty.addAll(Arrays.asList(arrayQty));
        vecUnitPrice.addAll(Arrays.asList(arrayUnitPrice));
        vecPriceTotal.addAll(Arrays.asList(arrayPriceTotal));

        //Add New Columns into the table_view_products with data as a vector
        productsModel.addColumn("Product | Service", vecProducts);
        productsModel.addColumn("Qty", vecQty);
        productsModel.addColumn("Unit ???", vecUnitPrice);
        productsModel.addColumn("Total ???", vecPriceTotal);

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
            Logger.getLogger(OrderRefund.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_refund_order = new javax.swing.JDesktopPane();
        panel_order_details = new javax.swing.JPanel();
        btn_print = new javax.swing.JButton();
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
        btn_notes = new javax.swing.JButton();
        lbl_order_created_on = new javax.swing.JLabel();
        panel_order_status = new javax.swing.JPanel();
        lbl_order_status = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_refunded_by = new javax.swing.JLabel();
        jScrollPane_notes = new javax.swing.JScrollPane();
        editor_pane_important_notes = new javax.swing.JEditorPane();
        lbl_order_paid_on = new javax.swing.JLabel();
        txt_contact = new javax.swing.JFormattedTextField();

        setMaximumSize(new java.awt.Dimension(1049, 827));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        desktop_pane_refund_order.setPreferredSize(new java.awt.Dimension(1049, 700));

        panel_order_details.setPreferredSize(new java.awt.Dimension(1049, 700));

        btn_print.setBackground(new java.awt.Color(21, 76, 121));
        btn_print.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_order_no.setText("Order Number");
        lbl_order_no.setEnabled(false);

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");
        lbl_first_name.setEnabled(false);

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");
        lbl_last_name.setEnabled(false);

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");
        lbl_contact.setEnabled(false);

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");
        lbl_email.setEnabled(false);

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_brand.setText("Device Brand");
        lbl_brand.setEnabled(false);

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_model.setText("Device Model");
        lbl_model.setEnabled(false);

        lbl_sn.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_sn.setText("Serial Number");
        lbl_sn.setEnabled(false);

        lbl_auto_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_auto_order_no.setText("autoGen");
        lbl_auto_order_no.setEnabled(false);

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_price.setText("Total ???");
        lbl_price.setEnabled(false);

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_deposit.setText("Deposit ???");
        lbl_deposit.setEnabled(false);

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_due.setText("Due ???");
        lbl_due.setEnabled(false);

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

        txt_model.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_model.setEnabled(false);
        txt_model.setFocusable(false);
        txt_model.setPreferredSize(new java.awt.Dimension(63, 20));

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

        lbl_order_created_on.setBackground(new java.awt.Color(204, 204, 204));
        lbl_order_created_on.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_order_created_on.setText("orderCreatedOn");
        lbl_order_created_on.setEnabled(false);

        panel_order_status.setBackground(new java.awt.Color(51, 51, 51));
        panel_order_status.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_order_status.setBackground(new java.awt.Color(0, 102, 102));
        lbl_order_status.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_status.setForeground(java.awt.Color.white);
        lbl_order_status.setText("orderStatus");
        panel_order_status.add(lbl_order_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 4, 385, -1));

        lbl_date.setForeground(java.awt.Color.white);
        lbl_date.setText("date");
        panel_order_status.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 32, -1, -1));

        lbl_refunded_by.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_refunded_by.setForeground(new java.awt.Color(255, 255, 255));
        lbl_refunded_by.setText("refundedBy");
        lbl_refunded_by.setToolTipText("");
        lbl_refunded_by.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        panel_order_status.add(lbl_refunded_by, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 410, -1));

        jScrollPane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setEnabled(false);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_important_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 16))); // NOI18N
        editor_pane_important_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        editor_pane_important_notes.setEnabled(false);
        jScrollPane_notes.setViewportView(editor_pane_important_notes);

        lbl_order_paid_on.setBackground(new java.awt.Color(204, 204, 204));
        lbl_order_paid_on.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_order_paid_on.setText("orderPaidOn");
        lbl_order_paid_on.setEnabled(false);

        txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
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

        javax.swing.GroupLayout panel_order_detailsLayout = new javax.swing.GroupLayout(panel_order_details);
        panel_order_details.setLayout(panel_order_detailsLayout);
        panel_order_detailsLayout.setHorizontalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_order_status, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_first_name)
                                .addGap(12, 12, 12)
                                .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_last_name)
                                .addGap(15, 15, 15)
                                .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_contact)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_contact))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_email)
                                .addGap(6, 6, 6)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_brand)
                                .addGap(6, 6, 6)
                                .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_model)
                                .addGap(6, 6, 6)
                                .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_sn)
                                .addGap(6, 6, 6)
                                .addComponent(txt_sn, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_due)
                                .addGap(0, 0, 0)
                                .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_deposit)
                                .addGap(0, 0, 0)
                                .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_price)
                                .addGap(0, 0, 0)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_order_no)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                        .addGap(136, 136, 136)
                                        .addComponent(lbl_auto_order_no)
                                        .addGap(374, 374, 374))
                                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                        .addComponent(lbl_order_created_on, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_order_paid_on, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(btn_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_order_detailsLayout.setVerticalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(panel_order_status, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbl_auto_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbl_order_no))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_order_created_on)
                        .addComponent(lbl_order_paid_on)))
                .addGap(7, 7, 7)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_first_name))
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_last_name))
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_contact)
                            .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_email))
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_brand))
                            .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_model))
                            .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_sn))
                            .addComponent(txt_sn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_due)
                                    .addComponent(lbl_deposit)
                                    .addComponent(lbl_price)))))))
        );

        desktop_pane_refund_order.setLayer(panel_order_details, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_refund_orderLayout = new javax.swing.GroupLayout(desktop_pane_refund_order);
        desktop_pane_refund_order.setLayout(desktop_pane_refund_orderLayout);
        desktop_pane_refund_orderLayout.setHorizontalGroup(
            desktop_pane_refund_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
        );
        desktop_pane_refund_orderLayout.setVerticalGroup(
            desktop_pane_refund_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_refund_order, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_refund_order, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
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
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_depositKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyReleased
        // TODO add your handling code here:
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

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        OrderRefundReceipt refundReceipt = new OrderRefundReceipt(order, completedOrders, txt_contact.getText());
        refundReceipt.setVisible(true);
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notesActionPerformed
        // TODO add your handling code here:
        OrderNotes orderNotes = new OrderNotes(order.getOrderNo(), order.getUsername());
        orderNotes.setVisible(true);
    }//GEN-LAST:event_btn_notesActionPerformed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_notes;
    private javax.swing.JButton btn_print;
    private javax.swing.JDesktopPane desktop_pane_refund_order;
    private javax.swing.JEditorPane editor_pane_important_notes;
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
    private javax.swing.JLabel lbl_order_paid_on;
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
