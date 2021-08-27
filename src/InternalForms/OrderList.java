/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Model.Order;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        String tableStatus;
        Order order;
       
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
        
        
        showRecentOrders();
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
    
    
    public ArrayList<Order> loadOrderList()
    {
        ArrayList<Order> orderList = new ArrayList();
        
        try {
            dbConnection();
            ps = con.prepareStatement("SELECT * FROM orderDetails ORDER BY orderNo DESC");
            rs = ps.executeQuery();
            
            while (rs.next())
            {
               order = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                    rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"), 
                       rs.getString("importantNotes"), rs.getString("fault"), rs.getString("productService"), rs.getString("qty"),
                       rs.getString("unitPrice"), rs.getString("priceTotal"), rs.getDouble("total"), rs.getDouble("deposit"), 
                       rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"), rs.getString("status"),
                       rs.getString("issueDate"), rs.getString("finishDate"), rs.getString("pickDate"), rs.getString("refundDate"));
                
               orderList.add(order);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return orderList;
    }

    
    public final void showRecentOrders()
    {
        label_latest_orders_created.setVisible(true);
        
        ArrayList<Order> list = loadOrderList();
        
        DefaultTableModel dtm = (DefaultTableModel)table_view_orders.getModel();
        dtm.setRowCount(0);
        
        Object[] orderRow = new Object[8];
        for (int i = 0 ; i < list.size() ; i++)
        {
            orderRow[0] = list.get(i).getOrderNo();
            orderRow[1] = list.get(i).getFirstName();
            orderRow[2] = list.get(i).getLastName();
            orderRow[3] = list.get(i).getContactNo();
            orderRow[4] = list.get(i).getBrand();
            orderRow[5] = list.get(i).getModel();
            orderRow[6] = list.get(i).getSerialNumber();
            orderRow[7] = list.get(i).getStatus();
            
            dtm.addRow(orderRow);
            if (dtm.getRowCount() == 19)
            {
                break;
            }
        }
    }
    
    public void searchOrder() {
        
        String searchOrder = txt_search_order.getText();
        
        if(searchOrder.isEmpty())
        {
            showRecentOrders();
            txt_search_order.setVisible(true);
        }
        else{
            
            label_latest_orders_created.setVisible(false);
         
         try {
            ps = con.prepareStatement("SELECT * FROM orderDetails WHERE orderNo LIKE '%" + searchOrder + "%' "
                    + "OR firstName LIKE '%" + searchOrder + "%' OR lastName LIKE '%" + searchOrder + "%' "
                            + "OR contactNo LIKE '%" + searchOrder + "%' LIMIT 15");
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
        getContentPane().setLayout(null);

        desktop_pane_order_list.setBackground(new java.awt.Color(255, 255, 255));
        desktop_pane_order_list.setMaximumSize(new java.awt.Dimension(1049, 827));

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

        label_latest_orders_created.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        label_latest_orders_created.setForeground(new java.awt.Color(255, 255, 255));
        label_latest_orders_created.setText("Orders Created Recently");

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
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

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

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search.png"))); // NOI18N

        desktop_pane_order_list.setLayer(txt_search_order, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktop_pane_order_list.setLayer(label_latest_orders_created, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktop_pane_order_list.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktop_pane_order_list.setLayer(lbl_search_icon, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_order_listLayout = new javax.swing.GroupLayout(desktop_pane_order_list);
        desktop_pane_order_list.setLayout(desktop_pane_order_listLayout);
        desktop_pane_order_listLayout.setHorizontalGroup(
            desktop_pane_order_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_order_listLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(desktop_pane_order_listLayout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(label_latest_orders_created))
            .addGroup(desktop_pane_order_listLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        desktop_pane_order_listLayout.setVerticalGroup(
            desktop_pane_order_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_order_listLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(desktop_pane_order_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(label_latest_orders_created)
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(desktop_pane_order_list);
        desktop_pane_order_list.setBounds(0, 0, 1049, 663);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked

        if(evt.getClickCount() == 2)
        {
            double cash = 0, card = 0, changeTotal = 0;
            
            dbConnection();
            DefaultTableModel dtm = (DefaultTableModel)table_view_orders.getModel();
            int orderSelected = table_view_orders.getSelectedRow();
            String selectedOrderNo = dtm.getValueAt(orderSelected, 0).toString();
        
            try {
                String query = "SELECT * FROM orderDetails WHERE orderNo = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, selectedOrderNo);
                rs = ps.executeQuery();
                
                while(rs.next())
                {
                   order  = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                   rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"), rs.getString("importantNotes"),
                   rs.getString("fault"), rs.getString("productService"), rs.getString("qty"),rs.getString("unitPrice"), rs.getString("priceTotal"),
                   rs.getDouble("total"), rs.getDouble("deposit"), rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"),
                   rs.getString("status"), rs.getString("issueDate"), rs.getString("finishDate"), rs.getString("pickDate"),
                   rs.getString("refundDate"));
                   
                }
                
                String queryPayDate = "SELECT * FROM completedOrders WHERE orderNo = ? ";
                ps = con.prepareStatement(queryPayDate);
                ps.setString(1, selectedOrderNo);
                rs = ps.executeQuery();
                
                while (rs.next())
                {
                    cash = rs.getDouble("cash");
                    card = rs.getDouble("card");
                    changeTotal = rs.getDouble("changeTotal");
                }
                
            
            switch (order.getStatus()) {
                case "In Progress":
                    OrderDetails orderDetails = new OrderDetails(order, cash, card, changeTotal);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(orderDetails).setVisible(true);
                    break;
                case "Not Fixed":
//                    OrderNotFixed orderNotFixed = new OrderNotFixed(orderNo, firstName, lastName, contactNo,
//                    NotFixedOrder orderNotFixed = new NotFixedOrder(orderNo, firstName, lastName, contactNo,
//                            email, deviceBrand, deviceModel, serialNumber, importantNotes, stringFaults, stringProducts, stringQty,
//                             stringUnitPrice, stringPriceTotal, total, deposit, cashDeposit, cardDeposit, due, status, issueDate, finishedDate, cash, card, pickedDate);
                     NotFixedOrder orderNotFixed = new NotFixedOrder(order);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(orderNotFixed).setVisible(true);
                    break;
                    
                case "Refunded":
//                   RefundOrder refundOrder = new RefundOrder(orderNo, firstName, lastName, contactNo, email, deviceBrand,
//                        deviceModel, serialNumber, importantNotes, stringFaults, stringProducts, stringQty, stringUnitPrice,
//                        stringPriceTotal, total, deposit, cashDeposit, cardDeposit, due, status, issueDate, finishedDate, pickedDate, cash, card, refundDate);
                   RefundOrder refundOrder = new RefundOrder(order);
                   desktop_pane_order_list.removeAll();
                   desktop_pane_order_list.add(refundOrder).setVisible(true);
                   break;
                default:
                    FixedOrder fixedOrder = new FixedOrder(order, cash, card, changeTotal);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(fixedOrder).setVisible(true);
                    break;
            }
            } catch (SQLException ex) {
                Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
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
