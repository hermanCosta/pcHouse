/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HermanCosta
 */
public class ProductsList extends javax.swing.JInternalFrame {

    Color defaultColor, mouseEnteredColor;
    Connection con;
    PreparedStatement ps;
    ProductService productService;
    DefaultTableModel dtm;
    ResultSet rs;
    ResultSetMetaData rsmd;
    Vector vector;
    Statement statement;
    int productId;
    int id;
    String addCategory = "";

    @SuppressWarnings("unchecked")

    public ProductsList() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        table_view_products_list.setRowHeight(25);
        table_view_products_list.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
        defaultColor = new Color(21, 76, 121);
        mouseEnteredColor = new Color(118, 181, 197);

        displayProductList();
    }

    public final void displayProductList() {
        try {
            dbConnection();

            String query = "SELECT * FROM products ORDER BY productService ASC LIMIT 17";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            int count;
            count = rsmd.getColumnCount();
            dtm = (DefaultTableModel) table_view_products_list.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                vector = new Vector();
                for (int i = 1; i <= count; i++) {
                    vector.add(rs.getInt("productId"));
                    vector.add(rs.getString("productService"));
                    vector.add(rs.getDouble("price"));
                    vector.add(rs.getInt("qty"));
                    vector.add(rs.getString("notes"));
                    vector.add(rs.getString("category"));
                }

                dtm.addRow(vector);
            }
            
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchProductService() {

        String searchProduct = txt_product_service_list.getText();

        try {
            String query = "SELECT * FROM products WHERE productService LIKE '%" + searchProduct + "%' ORDER BY productService ASC";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            int count;
            count = rsmd.getColumnCount();
            dtm = (DefaultTableModel) table_view_products_list.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                vector = new Vector();
                for (int i = 1; i <= count; i++) {
                    vector.add(rs.getInt("productId"));
                    vector.add(rs.getString("productService"));
                    vector.add(rs.getDouble("price"));
                    vector.add(rs.getInt("qty"));
                    vector.add(rs.getString("notes"));
                    vector.add(rs.getString("category"));
                }
                dtm.addRow(vector);
            }
            
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearFields() {
        txt_product_service_list.setText("");
        txt_add_price.setText("");
        txt_add_qty.setText("");
        txt_add_notes.setText("");
        combo_box_category.setSelectedIndex(0);
        displayProductList();
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_product_list = new javax.swing.JDesktopPane();
        panel_product_list = new javax.swing.JPanel();
        panel_buttons = new javax.swing.JPanel();
        btn_edit_product_service = new javax.swing.JButton();
        btn_add_product_service = new javax.swing.JButton();
        txt_delete_product_service = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();
        btn_device_list = new javax.swing.JButton();
        panel_new_product = new javax.swing.JPanel();
        txt_product_service_list = new javax.swing.JTextField();
        txt_add_price = new javax.swing.JTextField();
        txt_add_qty = new javax.swing.JTextField();
        txt_add_notes = new javax.swing.JTextField();
        combo_box_category = new javax.swing.JComboBox<>();
        lbl_category = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_products_list = new javax.swing.JTable();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));

        btn_edit_product_service.setBackground(new java.awt.Color(21, 76, 121));
        btn_edit_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        btn_edit_product_service.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save_changes.png"))); // NOI18N
        btn_edit_product_service.setText("Update");
        btn_edit_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_product_serviceActionPerformed(evt);
            }
        });

        btn_add_product_service.setBackground(new java.awt.Color(21, 76, 121));
        btn_add_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        btn_add_product_service.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add_new.png"))); // NOI18N
        btn_add_product_service.setText("Add");
        btn_add_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_product_serviceActionPerformed(evt);
            }
        });

        txt_delete_product_service.setBackground(new java.awt.Color(21, 76, 121));
        txt_delete_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        txt_delete_product_service.setForeground(new java.awt.Color(255, 255, 255));
        txt_delete_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        txt_delete_product_service.setText("Delete");
        txt_delete_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_delete_product_serviceActionPerformed(evt);
            }
        });

        btn_clear_fields.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        btn_clear_fields.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_clear.png"))); // NOI18N
        btn_clear_fields.setText("Clear Fields");
        btn_clear_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fieldsActionPerformed(evt);
            }
        });

        btn_device_list.setBackground(new java.awt.Color(21, 76, 121));
        btn_device_list.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        btn_device_list.setForeground(new java.awt.Color(255, 255, 255));
        btn_device_list.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_device_list.png"))); // NOI18N
        btn_device_list.setText("Computers");
        btn_device_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_device_listActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_buttonsLayout = new javax.swing.GroupLayout(panel_buttons);
        panel_buttons.setLayout(panel_buttonsLayout);
        panel_buttonsLayout.setHorizontalGroup(
            panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buttonsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_edit_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_delete_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_device_list, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_buttonsLayout.setVerticalGroup(
            panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_buttonsLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_edit_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_delete_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_device_list, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_new_product.setBackground(new java.awt.Color(255, 255, 255));

        txt_product_service_list.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_product_service_list.setBorder(javax.swing.BorderFactory.createTitledBorder("Product | Service"));
        txt_product_service_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_product_service_listActionPerformed(evt);
            }
        });
        txt_product_service_list.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_product_service_listKeyReleased(evt);
            }
        });

        txt_add_price.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_price.setBorder(javax.swing.BorderFactory.createTitledBorder("Price €"));
        txt_add_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_add_priceActionPerformed(evt);
            }
        });
        txt_add_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_add_priceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_add_priceKeyReleased(evt);
            }
        });

        txt_add_qty.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_qty.setBorder(javax.swing.BorderFactory.createTitledBorder("Qty"));
        txt_add_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_add_qtyActionPerformed(evt);
            }
        });
        txt_add_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_add_qtyKeyPressed(evt);
            }
        });

        txt_add_notes.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_notes.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));
        txt_add_notes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_add_notesActionPerformed(evt);
            }
        });

        combo_box_category.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        combo_box_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Product", "Service", "Other" }));
        combo_box_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_categoryActionPerformed(evt);
            }
        });

        lbl_category.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        lbl_category.setText("Category");

        javax.swing.GroupLayout panel_new_productLayout = new javax.swing.GroupLayout(panel_new_product);
        panel_new_product.setLayout(panel_new_productLayout);
        panel_new_productLayout.setHorizontalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addComponent(txt_product_service_list, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_price, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_box_category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_new_productLayout.createSequentialGroup()
                        .addComponent(lbl_category)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_new_productLayout.setVerticalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_product_service_list, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_price, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_qty, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_notes, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addComponent(lbl_category)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_box_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_view_products_list.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_products_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product | Service", "Price €", "Qty", "Notes", "Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_products_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                table_view_products_listMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_products_listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_view_products_list);
        if (table_view_products_list.getColumnModel().getColumnCount() > 0) {
            table_view_products_list.getColumnModel().getColumn(0).setMaxWidth(60);
            table_view_products_list.getColumnModel().getColumn(2).setMaxWidth(90);
            table_view_products_list.getColumnModel().getColumn(3).setMaxWidth(80);
            table_view_products_list.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        javax.swing.GroupLayout panel_product_listLayout = new javax.swing.GroupLayout(panel_product_list);
        panel_product_list.setLayout(panel_product_listLayout);
        panel_product_listLayout.setHorizontalGroup(
            panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_product_listLayout.createSequentialGroup()
                .addGap(0, 25, Short.MAX_VALUE)
                .addGroup(panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_new_product, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(17, 17, 17))
        );
        panel_product_listLayout.setVerticalGroup(
            panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_product_listLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panel_new_product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        desktop_pane_product_list.setLayer(panel_product_list, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_product_listLayout = new javax.swing.GroupLayout(desktop_pane_product_list);
        desktop_pane_product_list.setLayout(desktop_pane_product_listLayout);
        desktop_pane_product_listLayout.setHorizontalGroup(
            desktop_pane_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_product_listLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_product_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        desktop_pane_product_listLayout.setVerticalGroup(
            desktop_pane_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_product_list, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(desktop_pane_product_list)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(desktop_pane_product_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_product_service_listActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_product_service_listActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_product_service_listActionPerformed

    private void txt_add_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_add_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_qtyActionPerformed

    private void btn_edit_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_product_serviceActionPerformed
        // TODO add your handling code here:
        if (txt_product_service_list.getText().trim().isEmpty() || txt_add_price.getText().trim().isEmpty()
                || txt_add_qty.getText().trim().isEmpty() || combo_box_category.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please check empty fields !", "Product List", JOptionPane.ERROR_MESSAGE);
        } else {

            String productName = txt_product_service_list.getText();
            double price = Double.parseDouble(txt_add_price.getText());
            int qty = Integer.parseInt(txt_add_qty.getText());
            String notes = txt_add_notes.getText();
            String category = combo_box_category.getSelectedItem().toString();

            if (category.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please, select a Category", "Product List", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    dbConnection();

                    String queryCheck = "SELECT * FROM products WHERE productService = ?";
                    ps = con.prepareStatement(queryCheck);
                    ps.setString(1, productName);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        if (productName.equals(rs.getString("productService")) && price == rs.getDouble("price") && qty == rs.getInt("qty")
                                && notes.equals(rs.getString("notes")) && category.equals(rs.getString("category"))) {
                            JOptionPane.showMessageDialog(this, "No changes to be updated !", "Product List", JOptionPane.ERROR_MESSAGE);
                        } else {
                            int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Editing " + productName + " ?",
                                    "Edit Product|Service", JOptionPane.YES_NO_OPTION);
                            if (confirmEditing == 0) {
                                String query = "UPDATE products SET productService = ?, price = ?, qty = ?, notes = ? WHERE productId = ?";
                                ps = con.prepareStatement(query);
                                ps.setString(1, productName);
                                ps.setDouble(2, price);
                                ps.setInt(3, qty);
                                ps.setString(4, notes);
                                ps.setInt(5, id);
                                ps.executeUpdate();

                                displayProductList();
                                clearFields();
                            } else {
                                displayProductList();
                                clearFields();
                            }
                        }
                    }

                    ps.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_edit_product_serviceActionPerformed

    private void btn_add_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_product_serviceActionPerformed
        // TODO add your handling code here:
        if (txt_product_service_list.getText().trim().isEmpty() || txt_add_price.getText().trim().isEmpty()
                || txt_add_qty.getText().trim().isEmpty() || combo_box_category.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please check empty fields !", "Product List", JOptionPane.ERROR_MESSAGE);
        } else {

            String addProductService = txt_product_service_list.getText();
            double addPrice = Double.parseDouble(txt_add_price.getText());
            int addQty = Integer.parseInt(txt_add_qty.getText());
            String addNotes = txt_add_notes.getText();
            addCategory = combo_box_category.getSelectedItem().toString();

            productService = new ProductService(addProductService, addPrice, addQty, addNotes, addCategory);

            try {
                dbConnection();
                String queryCheck = "SELECT productService FROM products WHERE productService = ? ";
                ps = con.prepareStatement(queryCheck);
                ps.setString(1, addProductService);
                rs = ps.executeQuery();

                if (rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, addProductService + " already exist in the Database !", "Products List", JOptionPane.ERROR_MESSAGE);
                } else {
                    String queryInsert = "INSERT INTO products(productService, price, qty, notes, category) VALUES(?, ?, ?, ?, ?)";
                    ps = con.prepareStatement(queryInsert);

                    ps.setString(1, productService.getProductService());
                    ps.setDouble(2, productService.getPrice());
                    ps.setInt(3, productService.getQty());
                    ps.setString(4, productService.getNotes());
                    ps.setString(5, productService.getCategory());
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, productService.getProductService() + " added successfully!");

                    displayProductList();
                    clearFields();
                }

                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btn_add_product_serviceActionPerformed

    private void txt_delete_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_delete_product_serviceActionPerformed
        // TODO add your handling code here:
        int selectedRow = table_view_products_list.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a item to Delete !", "Delete Product | Service", JOptionPane.ERROR_MESSAGE);
        } else {
            selectedRow = table_view_products_list.getSelectedRow();

            dtm = (DefaultTableModel) table_view_products_list.getModel();
            productId = Integer.parseInt(dtm.getValueAt(selectedRow, 0).toString());

            String productName = dtm.getValueAt(selectedRow, 1).toString();

            int confirmDeletion = JOptionPane.showConfirmDialog(this, "Do you really want to delete '"
                    + productName + "' from Database ?", "Delete Product|Service", JOptionPane.YES_NO_OPTION);

            if (confirmDeletion == 0) {
                try {
                    dbConnection();

                    String query = "DELETE FROM products WHERE productId = ? ";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, productId);
                    ps.executeUpdate();
                    dtm.removeRow(table_view_products_list.getSelectedRow());
                    displayProductList();
                    
                    ps.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txt_delete_product_serviceActionPerformed

    private void txt_product_service_listKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_product_service_listKeyReleased
        // TODO add your handling code here:
        searchProductService();
    }//GEN-LAST:event_txt_product_service_listKeyReleased

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    private void table_view_products_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_products_listMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int selectedRow = table_view_products_list.getSelectedRow();
            dtm = (DefaultTableModel) table_view_products_list.getModel();
            table_view_products_list.getModel().addTableModelListener(table_view_products_list);
            id = (Integer) dtm.getValueAt(selectedRow, 0);

            txt_product_service_list.setText(dtm.getValueAt(selectedRow, 1).toString());
            txt_add_price.setText(dtm.getValueAt(selectedRow, 2).toString());
            txt_add_qty.setText(dtm.getValueAt(selectedRow, 3).toString());
            txt_add_notes.setText(dtm.getValueAt(selectedRow, 4).toString());
            combo_box_category.setSelectedItem(dtm.getValueAt(selectedRow, 5).toString());;
        }
    }//GEN-LAST:event_table_view_products_listMouseClicked

    private void txt_add_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add_priceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_priceKeyReleased

    private void txt_add_priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add_priceKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))
            txt_add_price.setEditable(false);
        else
            txt_add_price.setEditable(true);
    }//GEN-LAST:event_txt_add_priceKeyPressed

    private void txt_add_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add_qtyKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))
            txt_add_qty.setEditable(false);
        else
            txt_add_qty.setEditable(true);
    }//GEN-LAST:event_txt_add_qtyKeyPressed

    private void txt_add_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_add_notesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_notesActionPerformed

    private void table_view_products_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_products_listMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_products_listMousePressed

    private void combo_box_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_categoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_categoryActionPerformed

    private void txt_add_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_add_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_priceActionPerformed

    private void btn_device_listActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_device_listActionPerformed
        // TODO add your handling code here:
        ComputerList computers = new ComputerList();
        desktop_pane_product_list.add(computers).setVisible(true);
    }//GEN-LAST:event_btn_device_listActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_product_service;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_device_list;
    private javax.swing.JButton btn_edit_product_service;
    private javax.swing.JComboBox<String> combo_box_category;
    private javax.swing.JDesktopPane desktop_pane_product_list;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_category;
    private javax.swing.JPanel panel_buttons;
    private javax.swing.JPanel panel_new_product;
    private javax.swing.JPanel panel_product_list;
    private javax.swing.JTable table_view_products_list;
    private javax.swing.JTextField txt_add_notes;
    private javax.swing.JTextField txt_add_price;
    private javax.swing.JTextField txt_add_qty;
    private javax.swing.JButton txt_delete_product_service;
    private javax.swing.JTextField txt_product_service_list;
    // End of variables declaration//GEN-END:variables
}
