/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.CreateOrder;
import Registering.ProductService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author user
 */
public class ProductsList extends javax.swing.JInternalFrame {

    /**
     * Creates new form OrdersMenu
     */
    
     Color defaultColor, mouseEnteredColor;
     Connection connection;
     PreparedStatement preparedStatement;
     ProductService productService;
     
    public ProductsList() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        table_view_products_list.setRowHeight(25);
        table_view_products_list.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
        //table_view_products_list.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table_view_products_list.getColumnModel().getColumn(3).setPreferredWidth(table_view_products_list.getColumnModel().getColumn(3).getPreferredWidth()+50);
        
        defaultColor = new Color(21,76,121);
        mouseEnteredColor = new Color(118,181,197);
        
        displayProductList();
    }

    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              connection = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","hermanhgc","He11m@ns");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public final void displayProductList()
    {
         dbConnection();
        
         try {
            preparedStatement = connection.prepareStatement("SELECT * FROM productService");
                                        
            ResultSet resultSet = preparedStatement.executeQuery();
            
            ResultSetMetaData rsd = resultSet.getMetaData();
            int c; 
            c = rsd.getColumnCount();
            DefaultTableModel defaultTableModel = (DefaultTableModel)table_view_products_list.getModel();
            defaultTableModel.setRowCount(0);
            
            while(resultSet.next())
            {
                Vector vector = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                    vector.add(resultSet.getString("productService"));
                    vector.add(resultSet.getDouble("price"));
                    vector.add(resultSet.getInt("qty"));
                    vector.add(resultSet.getString("notes"));
                }
                defaultTableModel.addRow(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void saveIntoDB()
    {
        
        String addProductService = txt_add_product_service.getText();
        double addPrice = Double.parseDouble(txt_add_price.getText());
        int addQty =  Integer.parseInt(txt_add_qty.getText());
        String addNotes = txt_add_notes.getText();
        
        productService = new ProductService(addProductService, addPrice);
        productService.setQty(addQty);
        productService.setNotes(addNotes);
        
        dbConnection();
        
        
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO productService(productService, price, qty, notes)VALUES(?,?,?,?)");
            preparedStatement.setString(1, productService.getProductService());
            preparedStatement.setDouble(2, productService.getPrice());
            preparedStatement.setInt(3, productService.getQty());
            preparedStatement.setString(4, productService.getNotes());
            
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            
            displayProductList();
            
            txt_add_product_service.setText("");
            txt_add_price.setText("");
            txt_add_qty.setText("");
            txt_add_notes.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void searchProductService() {
        
        String searchProduct = txt_search_product.getText();
        
        
       
         
         try {
            preparedStatement = connection.prepareStatement("SELECT * FROM productService WHERE productService LIKE '%" + searchProduct + "%'");
                                          
                                       // + "WHERE orderNo LIKE '%" + searchOrder + "%' " 
                                        //+ "OR firstName LIKE '%" + searchOrder + "%' "
                                        //+ "OR lastName LIKE '%" + searchOrder + "%' ");
                                       // + "OR contactNo LIKE '%" + searchOrder + "%' ");
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            ResultSetMetaData rsd = resultSet.getMetaData();
            int c; 
            c = rsd.getColumnCount();
            DefaultTableModel defaultTableModel = (DefaultTableModel)table_view_products_list.getModel();
            defaultTableModel .setRowCount(0);
            
            while(resultSet.next())
            {
                Vector vector = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                    vector.add(resultSet.getString("productService"));
                    vector.add(resultSet.getDouble("price"));
                    vector.add(resultSet.getInt("qty"));
                    vector.add(resultSet.getString("notes"));
                }
                defaultTableModel.addRow(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/**        
    public void resizeColumnWidth(JTable table) {
        
        
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 15; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
            width = Math.max(width, table.getColumnModel().getColumn(column).getPreferredWidth());
        }
        if(width > 300)
            width=300;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}
**/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_search_product = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_products_list = new javax.swing.JTable();
        label_search_product = new javax.swing.JLabel();
        panel_new_product = new javax.swing.JPanel();
        txt_add_product_service = new javax.swing.JTextField();
        txt_add_price = new javax.swing.JTextField();
        txt_add_qty = new javax.swing.JTextField();
        txt_add_notes = new javax.swing.JTextField();
        btn_save_product = new javax.swing.JButton();
        label_title = new javax.swing.JLabel();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(677, 700));

        txt_search_product.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        txt_search_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_productActionPerformed(evt);
            }
        });
        txt_search_product.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_search_productKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_productKeyReleased(evt);
            }
        });

        table_view_products_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product | Service", "Price", "Stock", "Notes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_view_products_list);

        label_search_product.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        label_search_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_product.png"))); // NOI18N

        txt_add_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_product_service.setBorder(javax.swing.BorderFactory.createTitledBorder("Product | Service"));
        txt_add_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_add_product_serviceActionPerformed(evt);
            }
        });

        txt_add_price.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_price.setBorder(javax.swing.BorderFactory.createTitledBorder("Price"));

        txt_add_qty.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_qty.setBorder(javax.swing.BorderFactory.createTitledBorder("Qty"));
        txt_add_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_add_qtyActionPerformed(evt);
            }
        });

        txt_add_notes.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_add_notes.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));

        btn_save_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save.png"))); // NOI18N
        btn_save_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_productActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_new_productLayout = new javax.swing.GroupLayout(panel_new_product);
        panel_new_product.setLayout(panel_new_productLayout);
        panel_new_productLayout.setHorizontalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txt_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_price, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_add_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save_product, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        panel_new_productLayout.setVerticalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_add_product_service, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_price, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_qty, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(txt_add_notes, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_save_product, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        label_title.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        label_title.setText("Add a New Product | Service");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 120, Short.MAX_VALUE)
                .addComponent(label_search_product)
                .addGap(0, 0, 0)
                .addComponent(txt_search_product, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel_new_product, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(label_title))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(label_title)
                .addGap(5, 5, 5)
                .addComponent(panel_new_product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search_product, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_search_product, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_search_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_productActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_productActionPerformed

    private void txt_add_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_add_product_serviceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_product_serviceActionPerformed

    private void txt_add_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_add_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_add_qtyActionPerformed

    private void btn_save_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_productActionPerformed
        // TODO add your handling code here:
        saveIntoDB();
    }//GEN-LAST:event_btn_save_productActionPerformed

    private void txt_search_productKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_productKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_search_productKeyPressed

    private void txt_search_productKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_productKeyReleased
        // TODO add your handling code here:
        searchProductService();
    }//GEN-LAST:event_txt_search_productKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_save_product;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_search_product;
    private javax.swing.JLabel label_title;
    private javax.swing.JPanel panel_new_product;
    private javax.swing.JTable table_view_products_list;
    private javax.swing.JTextField txt_add_notes;
    private javax.swing.JTextField txt_add_price;
    private javax.swing.JTextField txt_add_product_service;
    private javax.swing.JTextField txt_add_qty;
    private javax.swing.JTextField txt_search_product;
    // End of variables declaration//GEN-END:variables
}
