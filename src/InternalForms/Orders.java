/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.Billing;
import Forms.ProductList;
import java.awt.Color;
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
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class Orders extends javax.swing.JInternalFrame {
        /**
         * Creates new form OrdersMenu
         */
        Color defaultColor, mouseEnteredColor;
        Connection con;
        ResultSet rs ;
        PreparedStatement ps;
       
    public Orders() {
        initComponents();
        
        
        table_view_orders.setRowHeight(25);
        table_view_orders.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
                
        //Force remove border
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI UserUi = (BasicInternalFrameUI) this.getUI();
        UserUi.setNorthPane(null);
        
        defaultColor = new Color(21,76,121);
        mouseEnteredColor = new Color(118,181,197);
        
        
        recentOrders();
    }
    
    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","hermanhgc","He11m@ns");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void recentOrders()
    {
        label_latest_orders_created.setVisible(true);
         dbConnection();
        
         try {
            ps = con.prepareStatement("SELECT * FROM orderDetails ORDER BY orderNo DESC LIMIT 15 ");
                                        
            ResultSet rs = ps.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c; 
            c = rsd.getColumnCount();
            DefaultTableModel defaultTableModel = (DefaultTableModel)table_view_orders.getModel();
            defaultTableModel .setRowCount(0);
            
            while(rs.next())
            {
                Vector vector = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                    vector.add(rs.getString("orderNo"));
                    vector.add(rs.getString("firstName"));
                    vector.add(rs.getString("lastName"));
                    vector.add(rs.getString("contactNo"));
                    vector.add(rs.getString("deviceBrand"));
                    vector.add(rs.getString("deviceModel"));
                    vector.add(rs.getString("serialNumber"));
                    vector.add(rs.getString("status"));
                }
                defaultTableModel.addRow(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void searchOrder() {
        
        String searchOrder = txt_search_order.getText();
        
        if(searchOrder.isEmpty())
        {
            recentOrders();
            txt_search_order.getAction().setEnabled(false);
        }
        else{
            
            label_latest_orders_created.setVisible(false);
            label_latest_orders_created.setVisible(false);dbConnection();
         
         try {
            ps = con.prepareStatement("SELECT * FROM orderDetails WHERE orderNo LIKE '%" + searchOrder + "%' OR firstName LIKE '%" + searchOrder + "%' OR lastName LIKE '%" + searchOrder + "%' OR contactNo LIKE '%" + searchOrder + "%' LIMIT 15");
                                          
            rs = ps.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c; 
            c = rsd.getColumnCount();
            DefaultTableModel defaultTableModel = (DefaultTableModel)table_view_orders.getModel();
            defaultTableModel .setRowCount(0);
            
            while(rs.next())
            {
                Vector vector = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                    vector.add(rs.getString("orderNo"));
                    vector.add(rs.getString("firstName"));
                    vector.add(rs.getString("lastName"));
                    vector.add(rs.getString("contactNo"));
                    vector.add(rs.getString("deviceBrand"));
                    vector.add(rs.getString("deviceModel"));
                    vector.add(rs.getString("serialNumber"));
                    vector.add(rs.getString("status"));
                }
                defaultTableModel.addRow(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu1 = new javax.swing.JMenu();
        txt_search_order = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        label_latest_orders_created = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jMenu1.setText("jMenu1");

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));

        txt_search_order.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        txt_search_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_orderActionPerformed(evt);
            }
        });
        txt_search_order.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_search_orderKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_search_orderKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_orderKeyReleased(evt);
            }
        });

        jScrollPane1.setVerifyInputWhenFocusTarget(false);

        table_view_orders.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        table_view_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order", "First Name", "Last Name", "Contact", "Brand", "Model", "S/N", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_ordersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_view_orders);
        if (table_view_orders.getColumnModel().getColumnCount() > 0) {
            table_view_orders.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        label_latest_orders_created.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        label_latest_orders_created.setText("Orders Created Recently");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(label_latest_orders_created))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_latest_orders_created, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked
        // TODO add your handling code here:
        DefaultTableModel dm = (DefaultTableModel)table_view_orders.getModel();
        int selectedIndex = table_view_orders.getSelectedRow();

        String status = dm.getValueAt(selectedIndex, 7).toString();

        if (status.equals("Completed")) {
            JOptionPane.showMessageDialog(this, "This order has been completed!");
        }
        else
        {
            String orderNo = dm.getValueAt(selectedIndex, 0).toString();
            String model = dm.getValueAt(selectedIndex, 2).toString();
            String serialNumber = dm.getValueAt(selectedIndex, 3).toString();
            String price = dm.getValueAt(selectedIndex, 4).toString();
            String deposit = dm.getValueAt(selectedIndex, 5).toString();
            String due = dm.getValueAt(selectedIndex, 6).toString();

            new Billing(orderNo,model,serialNumber,price,deposit,due).setVisible(true);

            this.setVisible(false);
        }
    }//GEN-LAST:event_table_view_ordersMouseClicked

    private void txt_search_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_orderActionPerformed
        // TODO add your handling code here:
        searchOrder();
    }//GEN-LAST:event_txt_search_orderActionPerformed

    private void txt_search_orderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_orderKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_search_orderKeyPressed

    private void txt_search_orderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_orderKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_orderKeyTyped

    private void txt_search_orderKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_orderKeyReleased
        // TODO add your handling code here:
        searchOrder();
    }//GEN-LAST:event_txt_search_orderKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_latest_orders_created;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTextField txt_search_order;
    // End of variables declaration//GEN-END:variables
}
