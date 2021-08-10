/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.ProductList;
import Registering.ProductService;
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
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Herman Costa
 */
public class ProductsList extends javax.swing.JInternalFrame {

    /**
     * Creates new form OrdersMenu
     */
    
     Color defaultColor, mouseEnteredColor;
     Connection connection;
     PreparedStatement preparedStatement;
     ProductService productService;
     DefaultTableModel defaultTableModel;
     ResultSet resultSet;
     ResultSetMetaData resultSetMetaData;
     Vector vector;
     Statement statement;
     String productId;
     String ID;

    @SuppressWarnings("unchecked")
     
    public ProductsList() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        table_view_products_list.setRowHeight(25);
        table_view_products_list.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
        defaultColor = new Color(21,76,121);
        mouseEnteredColor = new Color(118,181,197);
        
        displayProductList();
        avoidEmptyField(txt_product_service_list,"product|service");
        avoidEmptyField(txt_add_price, "price");
    }

    public final void displayProductList()
    {
        dbConnection();
        
         try {
             
            String query = "SELECT * FROM productService"; 
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            
            int count; 
            count = resultSetMetaData.getColumnCount();
            defaultTableModel = (DefaultTableModel)table_view_products_list.getModel();
            defaultTableModel.setRowCount(0);
            
            while(resultSet.next())
            {
                vector = new Vector();
                for(int i = 1; i <= count; i++)
                {
                    vector.add(resultSet.getInt("productId"));
                    vector.add(resultSet.getString("productService"));
                    vector.add(resultSet.getDouble("price"));
                    vector.add(resultSet.getInt("qty"));
                    vector.add(resultSet.getString("notes"));
                }
                
                defaultTableModel.addRow(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addNewProductService()
    {
        
        String addProductService = txt_product_service_list.getText();
        double addPrice = Double.parseDouble(txt_add_price.getText());
        int addQty =  Integer.parseInt(txt_add_qty.getText());
        String addNotes = txt_add_notes.getText();
        
        productService = new ProductService(addProductService, addPrice);
        productService.setQty(addQty);
        productService.setNotes(addNotes);
        
        dbConnection();
        
        try {
            String query = "INSERT INTO productService(productService, price, qty, notes)VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productService.getProductService());
            preparedStatement.setDouble(2, productService.getPrice());
            preparedStatement.setInt(3, productService.getQty());
            preparedStatement.setString(4, productService.getNotes());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            
            displayProductList();
            clearFields();
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void searchProductService() {
        
        String searchProduct = txt_product_service_list.getText();
         
         try {
            String query = "SELECT * FROM productService WHERE productService LIKE '%" + searchProduct + "%'"; 
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            
            int count; 
            count = resultSetMetaData.getColumnCount();
            defaultTableModel = (DefaultTableModel)table_view_products_list.getModel();
            defaultTableModel .setRowCount(0);
            
            while(resultSet.next())
            {
                vector = new Vector();
                for(int i = 1; i <= count; i++)
                {
                    vector.add(resultSet.getInt("productId"));
                    vector.add(resultSet.getString("productService"));
                    vector.add(resultSet.getDouble("price"));
                    vector.add(resultSet.getInt("qty"));
                    vector.add(resultSet.getString("notes"));
                }
                defaultTableModel.addRow(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteProductService()
    {
        dbConnection();
        
        int selectedRow = table_view_products_list.getSelectedRow();
        defaultTableModel = (DefaultTableModel)table_view_products_list.getModel();
        productId = defaultTableModel.getValueAt(selectedRow, 0).toString();
        
        
        int confirmDeletion = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?", "Delete Product|Service", JOptionPane.YES_NO_OPTION);
        if(confirmDeletion == 0)
        {
            try {
                
                String query = "DELETE FROM productService WHERE productId = '" + productId + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                defaultTableModel.removeRow(table_view_products_list.getSelectedRow());
                displayProductList();

            } catch (SQLException ex) {
                Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void clearFields()
    {
        txt_product_service_list.setText("");
        txt_add_price.setText("");
        txt_add_qty.setText("");
        txt_add_notes.setText("");
        
        displayProductList();
    }
        
     public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              connection = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","hermanhgc","He11m@ns");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_products_list = new javax.swing.JTable();
        panel_new_product = new javax.swing.JPanel();
        txt_product_service_list = new javax.swing.JTextField();
        txt_add_price = new javax.swing.JTextField();
        txt_add_qty = new javax.swing.JTextField();
        txt_add_notes = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btn_edit_product_service = new javax.swing.JButton();
        btn_add_product_service = new javax.swing.JButton();
        txt_delete_product_service = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));

        table_view_products_list.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        table_view_products_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Product | Service", "Price €", "Qty", "Notes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            table_view_products_list.getColumnModel().getColumn(0).setMaxWidth(50);
            table_view_products_list.getColumnModel().getColumn(2).setMaxWidth(80);
            table_view_products_list.getColumnModel().getColumn(3).setMaxWidth(80);
        }

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

        javax.swing.GroupLayout panel_new_productLayout = new javax.swing.GroupLayout(panel_new_product);
        panel_new_product.setLayout(panel_new_productLayout);
        panel_new_productLayout.setHorizontalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addComponent(txt_product_service_list, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_price, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_notes)
                .addGap(0, 0, 0))
        );
        panel_new_productLayout.setVerticalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_product_service_list, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(txt_add_price, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_qty, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_notes, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btn_edit_product_service.setBackground(new java.awt.Color(21, 76, 121));
        btn_edit_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_edit_product_service.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit_product_service.setText("Update");
        btn_edit_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_product_serviceActionPerformed(evt);
            }
        });

        btn_add_product_service.setBackground(new java.awt.Color(21, 76, 121));
        btn_add_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_add_product_service.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_product_service.setText("Add");
        btn_add_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_product_serviceActionPerformed(evt);
            }
        });

        txt_delete_product_service.setBackground(new java.awt.Color(21, 76, 121));
        txt_delete_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txt_delete_product_service.setForeground(new java.awt.Color(255, 255, 255));
        txt_delete_product_service.setText("Delete");
        txt_delete_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_delete_product_serviceActionPerformed(evt);
            }
        });

        btn_clear_fields.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_clear_fields.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields.setText("Clear Fields");
        btn_clear_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_edit_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_delete_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_edit_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_delete_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(panel_new_product, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(panel_new_product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 72, Short.MAX_VALUE))
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
        editProductService();
    }//GEN-LAST:event_btn_edit_product_serviceActionPerformed

    private void btn_add_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_product_serviceActionPerformed
        // TODO add your handling code here:
        addNewProductService();
    }//GEN-LAST:event_btn_add_product_serviceActionPerformed

    private void txt_delete_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_delete_product_serviceActionPerformed
        // TODO add your handling code here:
        deleteProductService();
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
        int count  = evt.getClickCount();
        if(count == 2)
        {
            int selectedRow = table_view_products_list.getSelectedRow();
            defaultTableModel = (DefaultTableModel)table_view_products_list.getModel();
            table_view_products_list.getModel().addTableModelListener(table_view_products_list);
            ID = defaultTableModel.getValueAt(selectedRow, 0).toString();

            txt_product_service_list.setText(defaultTableModel.getValueAt(selectedRow, 1).toString());
            txt_add_price.setText(defaultTableModel.getValueAt(selectedRow, 2).toString());
            txt_add_qty.setText(defaultTableModel.getValueAt(selectedRow, 3).toString());
            txt_add_notes.setText(defaultTableModel.getValueAt(selectedRow, 4).toString());
            
        }
       
        else
        {
            clearFields();
        }
    }//GEN-LAST:event_table_view_products_listMouseClicked

    private void txt_add_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add_priceKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_add_priceKeyReleased

    private void txt_add_priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add_priceKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            txt_add_price.setEditable(false);
        }
        else
        {
            txt_add_price.setEditable(true);
        }
    }//GEN-LAST:event_txt_add_priceKeyPressed

    private void txt_add_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add_qtyKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            txt_add_qty.setEditable(false);
        }
        else
        {
            txt_add_qty.setEditable(true);
        }
    }//GEN-LAST:event_txt_add_qtyKeyPressed

    private void txt_add_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_add_notesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_notesActionPerformed

    private void table_view_products_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_products_listMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_products_listMousePressed

    public void editProductService()
    {
        dbConnection();
        
        String productName = txt_product_service_list.getText();
        double price = Double.parseDouble(txt_add_price.getText());
        int qty = Integer.parseInt(txt_add_qty.getText());
        String notes = txt_add_notes.getText();
        
        
        int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Editing ?" + productName, "Edit Product|Service", JOptionPane.YES_NO_OPTION);
        if(confirmEditing == 0)
        {
            try {
                
                String query = "UPDATE productService SET productService = '" + productName +  "', price = " + price + ", qty = " + qty + ", notes = '" + notes + "' WHERE productId = '" + ID +"'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                    
                displayProductList();
                clearFields();
                    
            } catch (SQLException ex) {
                Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            displayProductList();
            clearFields();
        }
    }
    
    public final void avoidEmptyField(JTextField textField, String title)
    {
        textField.setInputVerifier(new InputVerifier() {
           
           Border originalBorder;
           
           @Override
           public boolean verify(JComponent input) {
               JTextField comp = (JTextField) input;
               return !comp.getText().trim().isEmpty();
           }
           
           @Override
           public boolean shouldYieldFocus(JComponent input)
           {
               boolean isValid = verify(input);
               
               if (!isValid)
               {
                   originalBorder = originalBorder == null ? input.getBorder() : originalBorder;
                   input.setBorder(javax.swing.BorderFactory.createTitledBorder(originalBorder, title, TitledBorder.ABOVE_BOTTOM, TitledBorder.BOTTOM,Font.getFont("Lucida Grande"), Color.red));
               } 
               else 
               {
                    if(originalBorder != null) {
                        input.setBorder(originalBorder);
                        originalBorder = null;
                    }
                }
                return isValid;
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_product_service;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_edit_product_service;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_new_product;
    private javax.swing.JTable table_view_products_list;
    private javax.swing.JTextField txt_add_notes;
    private javax.swing.JTextField txt_add_price;
    private javax.swing.JTextField txt_add_qty;
    private javax.swing.JButton txt_delete_product_service;
    private javax.swing.JTextField txt_product_service_list;
    // End of variables declaration//GEN-END:variables
}
