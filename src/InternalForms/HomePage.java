/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.Login;
import Model.Order;
import Model.ProductService;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class HomePage extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewOrder
     */
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Statement stmt;
    ProductService productService;
    Order order;
    String faultName;
    Color defaultColor, clickedColor;
    
    
    public HomePage() {
        initComponents();
        
        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        defaultColor = new Color(21,76,121);
        clickedColor = new Color(118,181,197);
        
        tableSettings(table_view_products_stock);
        tableSettings(table_view_orders_fixed);
        
        loadProductsStockTable();
        loadOrdersFixed();
    }

    public void tableSettings (JTable table)
    {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
    }
    
    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","root","hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadProductsStockTable()
    {
        ArrayList<ProductService> productList = new ArrayList<>();
        try {
            dbConnection();
            
            String query = "SELECT * FROM products WHERE category = 'Product' AND qty <= 2";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            
            while (rs.next())
            {
                productService = new ProductService(rs.getString("productService"), rs.getDouble("price"), 
                        rs.getInt("qty"), rs.getString("notes"), rs.getString("category"));
                
                productList.add(productService);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel dtm = (DefaultTableModel)table_view_products_stock.getModel();
        dtm.setRowCount(0);
        
        Object[] row = new Object[2];
        for (int i = 0 ; i < productList.size() ; i++)
        {
                row[0] = productList.get(i).getQty();
                row[1] = productList.get(i).getProductService();
                
            dtm.addRow(row);
        }
    }
    
    public void loadOrdersFixed()
    {
        ArrayList<Order> orderList = new ArrayList<>();
        
        try {
            dbConnection();
            
            String query = "SELECT * FROM orderDetails WHERE status = 'Fixed' AND finishDate <= NOW() - INTERVAL 3 MONTH";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                String dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("finishDate"));
                
                order = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                        rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"), 
                           rs.getString("importantNotes"), rs.getString("fault"), rs.getString("productService"), rs.getString("qty"),
                           rs.getString("unitPrice"), rs.getString("priceTotal"), rs.getDouble("total"), rs.getDouble("deposit"), 
                           rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"), rs.getString("status"),
                           rs.getString("issueDate"), dateFormat, rs.getString("pickDate"), rs.getString("refundDate"), Login.fullName);

                orderList.add(order);
            }
            
            DefaultTableModel dtm = (DefaultTableModel)table_view_orders_fixed.getModel();
            dtm.setRowCount(0);

            Object[] row = new Object[4];
            for (int i = 0; i < orderList.size() ; i++)
            {
                row[0] = orderList.get(i).getOrderNo();
                row[1] = orderList.get(i).getFirstName() + " " + orderList.get(i).getLastName();
                row[2] = orderList.get(i).getContactNo();
                row[3] = orderList.get(i).getFinishDate();

                dtm.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_home_page = new javax.swing.JDesktopPane();
        panel_customers = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_products_stock = new javax.swing.JTable();
        panel_buttons = new javax.swing.JPanel();
        btn_new_order = new javax.swing.JButton();
        btn_new_sale = new javax.swing.JButton();
        btn_products_list = new javax.swing.JButton();
        btn_orders = new javax.swing.JButton();
        btn_sales = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_view_orders_fixed = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_customers.setPreferredSize(new java.awt.Dimension(1049, 700));

        table_view_products_stock.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_products_stock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Qty", "Product Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_products_stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_products_stockMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_products_stock);
        if (table_view_products_stock.getColumnModel().getColumnCount() > 0) {
            table_view_products_stock.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_view_products_stock.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        btn_new_order.setBackground(new java.awt.Color(21, 76, 121));
        btn_new_order.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_new_order.setForeground(new java.awt.Color(255, 255, 255));
        btn_new_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_new_order.png"))); // NOI18N
        btn_new_order.setText("New Order");
        btn_new_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_orderActionPerformed(evt);
            }
        });
        btn_new_order.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_new_orderKeyPressed(evt);
            }
        });

        btn_new_sale.setBackground(new java.awt.Color(21, 76, 121));
        btn_new_sale.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_new_sale.setForeground(new java.awt.Color(255, 255, 255));
        btn_new_sale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_new_sale.png"))); // NOI18N
        btn_new_sale.setText("New Sale");
        btn_new_sale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_saleActionPerformed(evt);
            }
        });

        btn_products_list.setBackground(new java.awt.Color(21, 76, 121));
        btn_products_list.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_products_list.setForeground(new java.awt.Color(255, 255, 255));
        btn_products_list.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_product_list.png"))); // NOI18N
        btn_products_list.setText("Products List");
        btn_products_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_products_listActionPerformed(evt);
            }
        });

        btn_orders.setBackground(new java.awt.Color(21, 76, 121));
        btn_orders.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_orders.setForeground(new java.awt.Color(255, 255, 255));
        btn_orders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_check_existing.png"))); // NOI18N
        btn_orders.setText("Orders");
        btn_orders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ordersActionPerformed(evt);
            }
        });

        btn_sales.setBackground(new java.awt.Color(21, 76, 121));
        btn_sales.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_sales.setForeground(new java.awt.Color(255, 255, 255));
        btn_sales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_sellings.png"))); // NOI18N
        btn_sales.setText("Sales");
        btn_sales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_buttonsLayout = new javax.swing.GroupLayout(panel_buttons);
        panel_buttons.setLayout(panel_buttonsLayout);
        panel_buttonsLayout.setHorizontalGroup(
            panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buttonsLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(btn_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_products_list, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        panel_buttonsLayout.setVerticalGroup(
            panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buttonsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_products_list, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        table_view_orders_fixed.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_orders_fixed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order", "FullName", "Contact No", "Date of Fixing"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_orders_fixed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_orders_fixedMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_view_orders_fixed);
        if (table_view_orders_fixed.getColumnModel().getColumnCount() > 0) {
            table_view_orders_fixed.getColumnModel().getColumn(0).setPreferredWidth(80);
            table_view_orders_fixed.getColumnModel().getColumn(0).setMaxWidth(150);
            table_view_orders_fixed.getColumnModel().getColumn(2).setPreferredWidth(140);
            table_view_orders_fixed.getColumnModel().getColumn(2).setMaxWidth(150);
            table_view_orders_fixed.getColumnModel().getColumn(3).setPreferredWidth(120);
            table_view_orders_fixed.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel1.setText("Products Stock below or  stockout");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel2.setText("Orders  Fixed over three months");

        javax.swing.GroupLayout panel_customersLayout = new javax.swing.GroupLayout(panel_customers);
        panel_customers.setLayout(panel_customersLayout);
        panel_customersLayout.setHorizontalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(186, 186, 186))
        );
        panel_customersLayout.setVerticalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(panel_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        desktop_pane_home_page.setLayer(panel_customers, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_home_pageLayout = new javax.swing.GroupLayout(desktop_pane_home_page);
        desktop_pane_home_page.setLayout(desktop_pane_home_pageLayout);
        desktop_pane_home_pageLayout.setHorizontalGroup(
            desktop_pane_home_pageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_home_pageLayout.createSequentialGroup()
                .addComponent(panel_customers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        desktop_pane_home_pageLayout.setVerticalGroup(
            desktop_pane_home_pageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_customers, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_home_page, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_home_page, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_view_products_stockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_products_stockMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_products_stockMouseClicked

    private void btn_new_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_orderActionPerformed
        // TODO add your handling code here:
        NewOrder newOrder = new NewOrder();
        //desktop_pane_home_page.removeAll();
        desktop_pane_home_page.add(newOrder).setVisible(true);
        
        desktop_pane_home_page.setBackground(defaultColor);
        desktop_pane_home_page.setBackground(clickedColor);
    }//GEN-LAST:event_btn_new_orderActionPerformed

    private void btn_new_orderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_new_orderKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_new_orderKeyPressed

    private void btn_new_saleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_saleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_new_saleActionPerformed

    private void btn_products_listActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_products_listActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_products_listActionPerformed

    private void btn_ordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ordersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ordersActionPerformed

    private void btn_salesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesActionPerformed

    private void table_view_orders_fixedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_orders_fixedMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_orders_fixedMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_new_order;
    private javax.swing.JButton btn_new_sale;
    private javax.swing.JButton btn_orders;
    private javax.swing.JButton btn_products_list;
    private javax.swing.JButton btn_sales;
    private javax.swing.JDesktopPane desktop_pane_home_page;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panel_buttons;
    private javax.swing.JPanel panel_customers;
    private javax.swing.JTable table_view_orders_fixed;
    private javax.swing.JTable table_view_products_stock;
    // End of variables declaration//GEN-END:variables
}
