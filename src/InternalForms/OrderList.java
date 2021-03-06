/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.Login;
import Model.CompletedOrder;
import Model.Order;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HermanCosta
 */
public class OrderList extends javax.swing.JInternalFrame {

    Color defaultColor, mouseEnteredColor;
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    String tableStatus;
    Order order;
    CompletedOrder completedOrder;

    public OrderList() {
        initComponents();

        table_view_orders.setRowHeight(25);
        table_view_orders.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));

        //Force remove border
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI UserUi = (BasicInternalFrameUI) this.getUI();
        UserUi.setNorthPane(null);

        SwingUtilities.invokeLater(() -> {
            txt_search_order.requestFocus();
        });

        defaultColor = new Color(21, 76, 121);
        mouseEnteredColor = new Color(118, 181, 197);

        showRecentOrders();
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<Order> loadOrderList() {
        ArrayList<Order> ordersList = new ArrayList();
        try {
            dbConnection();
            String query = "SELECT * FROM orderDetails ORDER BY orderNo DESC LIMIT 19";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                order = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                        rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"),
                        rs.getString("importantNotes"), rs.getString("fault"), rs.getString("productService"), rs.getString("qty"),
                        rs.getString("unitPrice"), rs.getString("priceTotal"), rs.getDouble("total"), rs.getDouble("deposit"),
                        rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"), rs.getString("status"),
                        rs.getString("issueDate"), rs.getString("finishDate"), rs.getString("pickDate"), rs.getString("refundDate"), Login.fullName);

                ordersList.add(order);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ordersList;
    }

    public final void showRecentOrders() {
        ArrayList<Order> list = loadOrderList();
        label_latest_orders_created.setVisible(true);

        DefaultTableModel dtm = (DefaultTableModel) table_view_orders.getModel();
        dtm.setRowCount(0);

        Object[] orderRow = new Object[8];
        for (int i = 0; i < list.size(); i++) {
            orderRow[0] = list.get(i).getOrderNo();
            orderRow[1] = list.get(i).getFirstName();
            orderRow[2] = list.get(i).getLastName();

            switch (list.get(i).getContactNo().length()) {
                case 9:
                    String landLine = list.get(i).getContactNo().replaceFirst("(\\d{2})(\\d{3})(\\d+)", "($1) $2-$3");
                    orderRow[3] = landLine;
                    break;
                case 10:
                    String mobile = list.get(i).getContactNo().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                    orderRow[3] = mobile;
                    break;
                default:
                    orderRow[3] = list.get(i).getContactNo();
                    break;
            }

            orderRow[4] = list.get(i).getBrand();
            orderRow[5] = list.get(i).getModel();
            orderRow[6] = list.get(i).getSerialNumber();
            orderRow[7] = list.get(i).getStatus();

            dtm.addRow(orderRow);

            if (dtm.getRowCount() == 19) {
                break;
            }
        }
    }

    public void searchOrder() {
        ArrayList<Order> searchList = new ArrayList();
        String searchOrder = txt_search_order.getText();
        DefaultTableModel dtm = (DefaultTableModel) table_view_orders.getModel();
        dtm.setRowCount(0);

        if (searchOrder.isEmpty()) {
            showRecentOrders();
            txt_search_order.setVisible(true);
        } else {
            label_latest_orders_created.setVisible(false);

            try {
                dbConnection();

                String query = "SELECT * FROM orderDetails WHERE orderNo LIKE '%" + searchOrder + "%' "
                        + "OR firstName LIKE '%" + searchOrder + "%' OR lastName LIKE '%" + searchOrder + "%' "
                        + "OR contactNo LIKE '%" + searchOrder + "%'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next()) {
                    order = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                            rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"),
                            rs.getString("importantNotes"), rs.getString("fault"), rs.getString("productService"), rs.getString("qty"),
                            rs.getString("unitPrice"), rs.getString("priceTotal"), rs.getDouble("total"), rs.getDouble("deposit"),
                            rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"), rs.getString("status"),
                            rs.getString("issueDate"), rs.getString("finishDate"), rs.getString("pickDate"), rs.getString("refundDate"), Login.fullName);

                    searchList.add(order);
                }

                Object[] orderRow = new Object[8];
                for (int i = 0; i < searchList.size(); i++) {
                    orderRow[0] = searchList.get(i).getOrderNo();
                    orderRow[1] = searchList.get(i).getFirstName();
                    orderRow[2] = searchList.get(i).getLastName();

                    switch (searchList.get(i).getContactNo().length()) {
                        case 9:
                            String landLine = searchList.get(i).getContactNo().replaceFirst("(\\d{2})(\\d{3})(\\d+)", "($1) $2-$3");
                            orderRow[3] = landLine;
                            break;
                        case 10:
                            String mobile = searchList.get(i).getContactNo().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                            orderRow[3] = mobile;
                            break;
                        default:
                            orderRow[3] = searchList.get(i).getContactNo();
                            break;
                    }

                    orderRow[4] = searchList.get(i).getBrand();
                    orderRow[5] = searchList.get(i).getModel();
                    orderRow[6] = searchList.get(i).getSerialNumber();
                    orderRow[7] = searchList.get(i).getStatus();

                    dtm.addRow(orderRow);
                }

            } catch (SQLException ex) {
                Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openSelectedOrder() {
        DefaultTableModel dtm = (DefaultTableModel) table_view_orders.getModel();
        int orderSelected = table_view_orders.getSelectedRow();
        String selectedOrderNo = dtm.getValueAt(orderSelected, 0).toString();

        try {
            dbConnection();

            String query = "SELECT * FROM orderDetails WHERE orderNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, selectedOrderNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                order = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                        rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"), rs.getString("importantNotes"),
                        rs.getString("fault"), rs.getString("productService"), rs.getString("qty"), rs.getString("unitPrice"), rs.getString("priceTotal"),
                        rs.getDouble("total"), rs.getDouble("deposit"), rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"),
                        rs.getString("status"), rs.getString("issueDate"), rs.getString("finishDate"), rs.getString("pickDate"),
                        rs.getString("refundDate"), Login.fullName);
            }

            String queryPayDate = "SELECT * FROM completedOrders WHERE orderNo = ?";
            ps = con.prepareStatement(queryPayDate);
            ps.setString(1, selectedOrderNo);
            rs = ps.executeQuery();

            if (!rs.next()) {
                completedOrder = new CompletedOrder("", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, "", "");
            } else {
                do {
                    completedOrder = new CompletedOrder(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"),
                            "", "", rs.getString("brand"), rs.getString("model"),
                            "", rs.getDouble("total"), rs.getDouble("deposit"), rs.getDouble("due"),
                            rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"), rs.getDouble("cashDeposit"),
                            rs.getDouble("cardDeposit"), rs.getString("payDate"), rs.getString("status"));
                } while (rs.next());
            }

            switch (order.getStatus()) {
                case "In Progress":
                    OrderDetails orderDetails = new OrderDetails(order, completedOrder);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(orderDetails).setVisible(true);
                    break;

                case "Not Fixed":
                    NotFixedOrder orderNotFixed = new NotFixedOrder(order, completedOrder);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(orderNotFixed).setVisible(true);
                    break;

                case "Refunded":
                    OrderRefund refundOrder = new OrderRefund(order, completedOrder);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(refundOrder).setVisible(true);
                    break;

                default:
                    FixedOrder fixedOrder = new FixedOrder(order, completedOrder);
                    desktop_pane_order_list.removeAll();
                    desktop_pane_order_list.add(fixedOrder).setVisible(true);
                    break;
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_order_list = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        txt_search_order = new javax.swing.JTextField();
        label_latest_orders_created = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_orders1 = new javax.swing.JTable();
        lbl_search_icon = new javax.swing.JLabel();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        getContentPane().setLayout(null);

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
        label_latest_orders_created.setText("Orders Created Recently");

        jScrollPane1.setVerifyInputWhenFocusTarget(false);

        table_view_orders.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        table_view_orders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_view_ordersKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_view_orders);
        if (table_view_orders.getColumnModel().getColumnCount() > 0) {
            table_view_orders.getColumnModel().getColumn(0).setPreferredWidth(90);
            table_view_orders.getColumnModel().getColumn(0).setMaxWidth(120);
            table_view_orders.getColumnModel().getColumn(3).setPreferredWidth(150);
            table_view_orders.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        jScrollPane2.setVerifyInputWhenFocusTarget(false);

        table_view_orders1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_orders1.setModel(new javax.swing.table.DefaultTableModel(
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
        table_view_orders1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_ordersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_orders1);
        if (table_view_orders1.getColumnModel().getColumnCount() > 0) {
            table_view_orders1.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(lbl_search_icon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(label_latest_orders_created))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(13, 13, 13)
                .addComponent(label_latest_orders_created)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        desktop_pane_order_list.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_order_listLayout = new javax.swing.GroupLayout(desktop_pane_order_list);
        desktop_pane_order_list.setLayout(desktop_pane_order_listLayout);
        desktop_pane_order_listLayout.setHorizontalGroup(
            desktop_pane_order_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        desktop_pane_order_listLayout.setVerticalGroup(
            desktop_pane_order_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(desktop_pane_order_list);
        desktop_pane_order_list.setBounds(0, 0, 1049, 663);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked
        if (evt.getClickCount() == 2) {
            openSelectedOrder();
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

    private void table_view_ordersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_view_ordersKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            openSelectedOrder();
        }
    }//GEN-LAST:event_table_view_ordersKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop_pane_order_list;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_latest_orders_created;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTable table_view_orders1;
    private javax.swing.JTextField txt_search_order;
    // End of variables declaration//GEN-END:variables
}
