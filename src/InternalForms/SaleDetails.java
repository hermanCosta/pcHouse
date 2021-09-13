/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.DepositPayment;
import Forms.OrderNotes;
import Forms.SalePayment;
import Forms.SaleReceipt;
import Forms.SaleRefundReceipt;
import Model.Customer;
import Model.ProductService;
import Model.Sale;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SaleDetails extends javax.swing.JInternalFrame {

    ArrayList firstNames = new ArrayList();
    ArrayList lastNames = new ArrayList();
    Connection con;
    PreparedStatement ps;
    Customer customer;
    ProductService productService;
    Sale sale;
    ResultSet rs;
    ResultSetMetaData rsmd;

    String saleNo, firstName, lastName, contactNo, email, stringProducts, stringQty,
            stringUnitPrice, stringPriceTotal, saleDate;
    double total, cash = 0, card = 0, change;

    public SaleDetails() {
        initComponents();
    }

    public SaleDetails(Sale _sale) {
        initComponents();
        this.sale = _sale;

        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        tableSettings(table_view_products);
        loadSaleDetails();
    }

    public void tableSettings(JTable table) {
        table.getTableHeader().setEnabled(false);
        table.setRowHeight(25);
        table.getTableHeader().setForeground(Color.gray);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setForeground(Color.gray);
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SaleDetails.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadSaleDetails() {
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();
        TableColumnModel model = table_view_products.getColumnModel();
        dtm.setRowCount(0);

        lbl_auto_order_no.setText(sale.getSaleNo());
        txt_first_name.setText(sale.getFirstName());
        txt_last_name.setText(sale.getLastName());
        txt_contact.setText(sale.getContactNo());
        txt_email.setText(sale.getEmail());
        txt_total.setText(String.valueOf(sale.getTotal()));
        lbl_sale_paid_on.setText("Sale Date: " + sale.getSaleDate());

        if (sale.getCash() == 0)
            lbl_paid_by.setText("Paid by Card: €" + sale.getCard());
        else if (sale.getCard() == 0)
            lbl_paid_by.setText("Paid by Cash: €" + sale.getCash() + " | Change: €" + sale.getChangeTotal());
        else
            lbl_paid_by.setText("Paid by Cash: €" + sale.getCash() + " | Card: €" + sale.getCard() + " | Change: €" + sale.getChangeTotal());

        String[] arrayProducts = sale.getStringProducts().split(",");
        String[] arrayQty = sale.getStringQty().split(",");
        String[] arrayUnitPrice = sale.getStringUnitPrice().split(",");
        String[] arrayPriceTotal = sale.getStringPriceTotal().split(",");

        for (Object objProducts : arrayProducts)
            dtm.addRow(new Object[]{objProducts});

        Vector vecProducts = new Vector();
        Vector vecQty = new Vector();
        Vector vecUnitPrice = new Vector();
        Vector vecPriceTotal = new Vector();

        vecProducts.addAll(Arrays.asList(arrayProducts));
        vecQty.addAll(Arrays.asList(arrayQty));
        vecUnitPrice.addAll(Arrays.asList(arrayUnitPrice));
        vecPriceTotal.addAll(Arrays.asList(arrayPriceTotal));

        dtm.addColumn("Product | Service", vecProducts);
        dtm.addColumn("Qty", vecQty);
        dtm.addColumn("Unit €", vecUnitPrice);
        dtm.addColumn("Total €", vecPriceTotal);

        model.getColumn(1).setMaxWidth(40);
        model.getColumn(2).setMaxWidth(80);
        model.getColumn(3).setMaxWidth(80);
    }

    public void updateProductQty() {
        for (int i = 0; i < table_view_products.getRowCount(); i++) {
            String cellProduct = table_view_products.getValueAt(i, 0).toString().replaceFirst(" ", "");
            String cellQty = table_view_products.getValueAt(i, 1).toString().replaceFirst(" ", "");

            try {
                dbConnection();
                String queryCheck = "SELECT * FROM products WHERE productService = ?";
                ps = con.prepareStatement(queryCheck);
                ps.setString(1, cellProduct);
                rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getString("category").equals("Product")) {
                        int productQty = rs.getInt("qty");
                        int updateProdQty = Integer.parseInt(cellQty) + productQty;

                        String queryUpdate = "UPDATE products SET qty = ? WHERE productService = ?";
                        ps = con.prepareStatement(queryUpdate);
                        ps.setInt(1, updateProdQty);
                        ps.setString(2, cellProduct);
                        ps.executeUpdate();
                    }
                }

                if (cellProduct.contains("|")) {
                    String[] split = cellProduct.split("|");
                    int computerId = Integer.parseInt(split[0]);

                    String queryCompQty = "SELECT qty FROM computers WHERE computerId = ?";
                    ps = con.prepareStatement(queryCompQty);
                    ps.setInt(1, computerId);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        int compQty = rs.getInt("qty");
                        int updateCompQty = Integer.parseInt(cellQty) + compQty;

                        String queryUpdateCompQty = "UPDATE computers SET qty = ? WHERE computerId = ?";
                        ps = con.prepareStatement(queryUpdateCompQty);
                        ps.setInt(1, updateCompQty);
                        ps.setInt(2, computerId);
                        ps.executeUpdate();
                    }
                }

                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SalePayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addEventNote(String updateNote) {
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        String dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentDate);

        try {
            dbConnection();

            String note = "Sale tagged as '" + updateNote + "' by " + sale.getUsername();
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

        panel_order_details = new javax.swing.JPanel();
        lbl_order_no = new javax.swing.JLabel();
        lbl_first_name = new javax.swing.JLabel();
        lbl_last_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_auto_order_no = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_last_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_contact = new javax.swing.JFormattedTextField();
        txt_total = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btn_notes = new javax.swing.JButton();
        btn_refund = new javax.swing.JButton();
        panel_sale_status = new javax.swing.JPanel();
        lbl_sale_paid_on = new javax.swing.JLabel();
        lbl_paid_by = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_order_details.setPreferredSize(new java.awt.Dimension(1049, 700));

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_order_no.setText("Sale No.");
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

        lbl_auto_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_auto_order_no.setText("autoGen");
        lbl_auto_order_no.setEnabled(false);

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_total.setText("Total €");
        lbl_total.setEnabled(false);

        txt_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_first_name.setEnabled(false);
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

        try {
            txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(0##) ###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_contact.setEnabled(false);
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

        txt_total.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_total.setEnabled(false);
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
        table_view_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_productsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_products);

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

        btn_refund.setBackground(new java.awt.Color(0, 0, 0));
        btn_refund.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_refund.setForeground(new java.awt.Color(255, 255, 255));
        btn_refund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_refund.png"))); // NOI18N
        btn_refund.setText("Refund");
        btn_refund.setFocusable(false);
        btn_refund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refundActionPerformed(evt);
            }
        });

        panel_sale_status.setBackground(new java.awt.Color(0, 153, 102));

        lbl_sale_paid_on.setBackground(new java.awt.Color(0, 102, 102));
        lbl_sale_paid_on.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_sale_paid_on.setForeground(java.awt.Color.white);
        lbl_sale_paid_on.setText("salePaidOn");

        lbl_paid_by.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        lbl_paid_by.setForeground(java.awt.Color.white);
        lbl_paid_by.setText("paidBy");

        javax.swing.GroupLayout panel_sale_statusLayout = new javax.swing.GroupLayout(panel_sale_status);
        panel_sale_status.setLayout(panel_sale_statusLayout);
        panel_sale_statusLayout.setHorizontalGroup(
            panel_sale_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sale_statusLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(panel_sale_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_sale_paid_on, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_paid_by, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_sale_statusLayout.setVerticalGroup(
            panel_sale_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sale_statusLayout.createSequentialGroup()
                .addComponent(lbl_sale_paid_on)
                .addGap(5, 5, 5)
                .addComponent(lbl_paid_by)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_order_detailsLayout = new javax.swing.GroupLayout(panel_order_details);
        panel_order_details.setLayout(panel_order_detailsLayout);
        panel_order_detailsLayout.setHorizontalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_order_no)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_auto_order_no))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_email)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                                    .addComponent(lbl_first_name)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                                            .addComponent(lbl_last_name)
                                            .addGap(20, 20, 20))
                                        .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                            .addComponent(lbl_contact)
                                            .addGap(11, 11, 11)))
                                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_contact, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(txt_last_name))))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_total)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(30, 30, 30)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(btn_refund, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btn_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(panel_sale_status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 22, Short.MAX_VALUE))
        );
        panel_order_detailsLayout.setVerticalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_sale_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refund, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_auto_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_order_no))
                        .addGap(18, 18, 18)))
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_first_name))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_last_name)
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_contact)
                            .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_email)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_total)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(385, 385, 385))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
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
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_first_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameKeyPressed

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailKeyPressed

    private void txt_first_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameKeyReleased

    private void txt_last_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyReleased

    private void txt_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalKeyPressed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_productsMouseClicked

    private void txt_last_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyPressed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed

    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boolean isSaleDetails = true;
        SaleReceipt saleReceipt = new SaleReceipt(sale, isSaleDetails);
        saleReceipt.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notesActionPerformed
        // TODO add your handling code here:
        OrderNotes orderNotes = new OrderNotes(sale.getSaleNo(), sale.getUsername());
        orderNotes.setVisible(true);
    }//GEN-LAST:event_btn_notesActionPerformed

    private void btn_refundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refundActionPerformed
        // TODO add your handling code here:
        int confirmRefund = JOptionPane.showConfirmDialog(this, "Do you really want to Refund " + sale.getSaleNo() + " ?",
                "Confirm Refund", JOptionPane.YES_NO_OPTION);

        if (confirmRefund == 0) {
            try {
                dbConnection();
                Date date = new Date();
                Timestamp currentDate = new Timestamp(date.getTime());
                String refundDate = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);

                sale.setStatus("Refunded");
                sale.setSaleDate(refundDate);

                cash = sale.getCash();
                card = sale.getCard();
                total = sale.getTotal();

                sale.setCash(cash *= -1);
                sale.setCard(card *= -1);
                sale.setTotal(total *= -1);

                sale = new Sale(sale.getSaleNo(), sale.getFirstName(), sale.getLastName(), sale.getContactNo(), sale.getEmail(),
                        sale.getStringProducts(), sale.getStringQty(), sale.getStringUnitPrice(), sale.getStringPriceTotal(), sale.getTotal(),
                        sale.getSaleDate(), sale.getCash(), sale.getCard(), sale.getChangeTotal(), sale.getStatus(), sale.getUsername());

                String queryInsert = "INSERT INTO sales(saleNo, firstName, lastName, contactNo, email, productService, qty, unitPrice, priceTotal, total, "
                        + "saleDate, cash, card, changeTotal, status, createdBy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(queryInsert);
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
                updateProductQty();

                JOptionPane.showMessageDialog(this, sale.getSaleNo() + "Refunded Successfully!");

                addEventNote("Refunded");

                SaleRefundReceipt saleRefundReceipt = new SaleRefundReceipt(sale);
                saleRefundReceipt.setVisible(true);

                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SaleDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_refundActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_notes;
    private javax.swing.JButton btn_refund;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_auto_order_no;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_paid_by;
    private javax.swing.JLabel lbl_sale_paid_on;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel panel_order_details;
    private javax.swing.JPanel panel_sale_status;
    private javax.swing.JTable table_view_products;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
