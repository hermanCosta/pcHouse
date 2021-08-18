/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.Billing;
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
public class OrderList extends javax.swing.JInternalFrame {
        /**
         * Creates new form OrdersMenu
         */
        Color defaultColor, mouseEnteredColor;
        Connection con;
        ResultSet rs ;
        PreparedStatement ps;
       
    public OrderList() {
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
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void recentOrders()
    {
        label_latest_orders_created.setVisible(true);
        dbConnection();
        
         try {
            ps = con.prepareStatement("SELECT * FROM orderDetails ORDER BY orderNo DESC LIMIT 15 ");
                                        
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
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
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
        desktop_pane_order_list = new javax.swing.JDesktopPane();
        txt_search_order = new javax.swing.JTextField();
        label_latest_orders_created = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        lbl_search_icon = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jMenu1.setText("jMenu1");

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));

        desktop_pane_order_list.setBackground(new java.awt.Color(255, 255, 255));
        desktop_pane_order_list.setMaximumSize(new java.awt.Dimension(1049, 827));
        desktop_pane_order_list.setSize(new java.awt.Dimension(1049, 700));
        desktop_pane_order_list.setLayout(null);

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
        desktop_pane_order_list.add(txt_search_order);
        txt_search_order.setBounds(110, 30, 860, 35);

        label_latest_orders_created.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        label_latest_orders_created.setForeground(new java.awt.Color(255, 255, 255));
        label_latest_orders_created.setText("Orders Created Recently");
        desktop_pane_order_list.add(label_latest_orders_created);
        label_latest_orders_created.setBounds(360, 80, 253, 25);

        jScrollPane1.setVerifyInputWhenFocusTarget(false);

        table_view_orders.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
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
            table_view_orders.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        desktop_pane_order_list.add(jScrollPane1);
        jScrollPane1.setBounds(20, 130, 1010, 520);

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons-search.png"))); // NOI18N
        desktop_pane_order_list.add(lbl_search_icon);
        lbl_search_icon.setBounds(80, 30, 30, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_order_list, javax.swing.GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop_pane_order_list, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void payOrder()
    {
        
        DefaultTableModel dm = (DefaultTableModel)table_view_orders.getModel();
        int selectedIndex = table_view_orders.getSelectedRow();

        String orderSelected = dm.getValueAt(selectedIndex, 1).toString();

        if (orderSelected.equals("Completed")) {
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

           // new Billing(orderNo,model,serialNumber,price,deposit,due).setVisible(true);

            this.setVisible(false);
        }
    }
    
    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked

        if(evt.getClickCount() == 2)
        {
        String orderNo = "";
        String firstName = "";
        String lastName = "";
        String contactNo = "";
        String email = "";
        String deviceBrand = "";
        String deviceModel = "";
        String serialNumber = "";
        String importantNotes ="";
        String faults = "";
        String productService = "";
        String price = "";
        double total = 0;
        double deposit = 0;
        double due = 0;
        String issueDate = "";
        String status = "";
        String finishedDate = "";
        String payDate = "";
        
        dbConnection();
        DefaultTableModel dtm = (DefaultTableModel)table_view_orders.getModel();
        int orderSelected = table_view_orders.getSelectedRow();
        String selectedOrderNo = dtm.getValueAt(orderSelected, 0).toString();
        
            try {
                String query = "SELECT * FROM orderDetails WHERE orderNo ='" + selectedOrderNo + "'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                
                while(rs.next())
                {
                   orderNo = rs.getString("orderNo");
                   firstName = rs.getString("firstName");
                   lastName = rs.getString("lastName");
                   contactNo = rs.getString("contactNo");
                   email = rs.getString("email");
                   deviceBrand = rs.getString("deviceBrand");
                   deviceModel = rs.getString("deviceModel");
                   serialNumber = rs.getString("serialNumber");
                   importantNotes = rs.getString("importantNotes");
                   faults = rs.getString("fault");
                   productService = rs.getString("productService");
                   price = rs.getString("price");
                   total = rs.getDouble("total");
                   deposit = rs.getDouble("deposit");
                   due = rs.getDouble("due");
                   issueDate = rs.getString("issuedDate");
                   status = rs.getString("status");
                   finishedDate = rs.getString("finishedDate");
                }
                String queryPayDate = "SELECT payDate FROM completedOrders WHERE orderNo ='" + selectedOrderNo + "'";
                ps = con.prepareStatement(queryPayDate);
                rs = ps.executeQuery();
                while (rs.next())
                payDate = rs.getString("payDate");
                
            } catch (SQLException ex) {
                Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (status.equals("In Progress"))
            {
                OrderDetails orderDetails = new OrderDetails(orderNo, firstName, lastName, contactNo, 
                        email, deviceBrand, deviceModel, serialNumber, importantNotes, faults, productService, 
                        price, total, deposit, due, status, issueDate);
            
                desktop_pane_order_list.add(orderDetails).setVisible(true);
            }
            
            else if (status.equals("Fixed") || status.equals("Not Fixable"))
            {
                FinishedOrder fixedOrder = new FinishedOrder(orderNo, firstName, lastName, contactNo, 
                        email, deviceBrand, deviceModel, serialNumber, importantNotes, faults, productService, 
                        price, total, deposit, due, status, issueDate, finishedDate);
            
                desktop_pane_order_list.add(fixedOrder).setVisible(true);
            }
            else
            {
                CompletedOrder completedOrder = new CompletedOrder(orderNo, firstName, lastName, contactNo, 
                        email, deviceBrand, deviceModel, serialNumber, importantNotes, faults, 
                        productService, price, total, deposit, due, issueDate, finishedDate, payDate);
                
                desktop_pane_order_list.removeAll();
                desktop_pane_order_list.add(completedOrder).setVisible(true);
            }
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
    private javax.swing.JDesktopPane desktop_pane_order_list;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_latest_orders_created;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTextField txt_search_order;
    // End of variables declaration//GEN-END:variables
}
