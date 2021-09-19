/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Model.CompletedOrder;
import Model.Customer;
import Model.ProductService;
import Model.Sale;
import com.sun.glass.events.KeyEvent;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HermanCosta
 */
public class Sales extends javax.swing.JInternalFrame {

    ArrayList firstNames = new ArrayList();
    ArrayList lastNames = new ArrayList();

    Vector vecProducts = new Vector();
    Vector vecQty = new Vector();
    Vector vecUnitPrice = new Vector();
    Vector vecPriceTotal = new Vector();

    Connection con;
    PreparedStatement ps;
    Statement stmt;
    Customer customer;
    ProductService productService;
    Sale Sale;
    Sale sale;
    ResultSet rs;
    ResultSetMetaData rsmd;
    CompletedOrder completedOrder;

    String saleNo, firstName, lastName, contactNo, email,
            stringProducts, stringPriceTotal, stringQty, stringUnitPrice, saleDate, status;

    double total, cash, card, changeTotal;
    boolean isSaleDetails = false;

    public Sales() {
        initComponents();

        //Remove border
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        SwingUtilities.invokeLater(() -> {
            txt_search_sale.requestFocus();
        });

        tableSettings(table_view_sales);
        showRecentSales();
    }

    public void tableSettings(JTable table) {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchSale() {
        ArrayList<Sale> saleList = new ArrayList<>();
        String searchSale = txt_search_sale.getText();

        try {
            dbConnection();

            String query = "SELECT * FROM sales WHERE firstName LIKE '%" + searchSale + "%'"
                    + "OR lastName LIKE '%" + searchSale + "%' OR contactNo LIKE '%" + searchSale + "%'"
                    + " OR email LIKE '%" + searchSale + "%'";
            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next()) {
                sale = new Sale(rs.getString("saleNo"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("contactNo"), rs.getString("email"), rs.getString("productService"),
                        rs.getString("qty"), rs.getString("unitPrice"), rs.getString("priceTotal"),
                        rs.getDouble("total"), rs.getString("saleDate"), rs.getDouble("cash"), rs.getDouble("card"),
                        rs.getDouble("changeTotal"), rs.getString("status"), rs.getString("createdBy"));

                saleList.add(sale);

            }

            DefaultTableModel dtm = (DefaultTableModel) table_view_sales.getModel();
            dtm.setRowCount(0);

            Object[] row = new Object[8];
            for (int i = 0; i < saleList.size(); i++) {
                row[0] = saleList.get(i).getSaleNo();
                row[1] = saleList.get(i).getFirstName(); 
                row[2] = saleList.get(i).getLastName();
                row[3] = saleList.get(i).getContactNo();
                row[4] = saleList.get(i).getStringProducts();
                row[5] = saleList.get(i).getStringQty();
                row[6] = saleList.get(i).getTotal();
                row[7] = saleList.get(i).getStatus();

                dtm.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showRecentSales() {
        ArrayList<Sale> list = new ArrayList<>();
        try {
            dbConnection();

            String query = "SELECT * FROM sales ORDER BY saleNo DESC LIMIT 19";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                sale = new Sale(rs.getString("saleNo"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("contactNo"), rs.getString("email"), rs.getString("productService"),
                        rs.getString("qty"), rs.getString("unitPrice"), rs.getString("priceTotal"),
                        rs.getDouble("total"), rs.getString("saleDate"), rs.getDouble("cash"), rs.getDouble("card"),
                        rs.getDouble("changeTotal"), rs.getString("status"), rs.getString("createdBy"));

                list.add(sale);
            }

            DefaultTableModel dtm = (DefaultTableModel) table_view_sales.getModel();
            dtm.setRowCount(0);

            Object[] row = new Object[8];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getSaleNo();
                row[1] = list.get(i).getFirstName();
                row[2] = list.get(i).getLastName();
                row[3] = list.get(i).getContactNo();
                row[4] = list.get(i).getStringProducts();
                row[5] = list.get(i).getStringQty();
                row[6] = list.get(i).getTotal();
                row[7] = list.get(i).getStatus();

                dtm.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openSelectedSale() {
        int row = table_view_sales.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) table_view_sales.getModel();

        
            saleNo = dtm.getValueAt(row, 0).toString();

            try {

                dbConnection();

                String query = "SELECT * FROM sales WHERE saleNo = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, saleNo);
                rs = ps.executeQuery();

                while (rs.next()) {
                    sale = new Sale(rs.getString("saleNo"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("contactNo"), rs.getString("email"), rs.getString("productService"), rs.getString("qty"),
                            rs.getString("unitPrice"), rs.getString("priceTotal"), rs.getDouble("total"), rs.getString("saleDate"),
                            rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"), rs.getString("status"), rs.getString("createdBy"));

                    if (rs.getString("status").equals("Paid")) {
                        SaleDetails saleDetails = new SaleDetails(sale);
                        //desktop_pane_sales.removeAll();
                        desktop_pane_sales.add(saleDetails).setVisible(true);
                    } else {
                        SaleRefund saleRefund = new SaleRefund(sale);
                        //desktop_pane_sales.removeAll();
                        desktop_pane_sales.add(saleRefund).setVisible(true);
                    }
                }

                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_sales = new javax.swing.JDesktopPane();
        panel_sale_details = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_sales = new javax.swing.JTable();
        txt_search_sale = new javax.swing.JTextField();
        lbl_search_icon = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_sale_details.setPreferredSize(new java.awt.Dimension(1049, 700));

        table_view_sales.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SNO", "First Name", "Last Name", "Contact No", "Product | Service", "Qty", "Total €", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_salesMouseClicked(evt);
            }
        });
        table_view_sales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_view_salesKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_sales);
        if (table_view_sales.getColumnModel().getColumnCount() > 0) {
            table_view_sales.getColumnModel().getColumn(0).setPreferredWidth(80);
            table_view_sales.getColumnModel().getColumn(0).setMaxWidth(150);
            table_view_sales.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_view_sales.getColumnModel().getColumn(1).setMaxWidth(200);
            table_view_sales.getColumnModel().getColumn(2).setPreferredWidth(100);
            table_view_sales.getColumnModel().getColumn(2).setMaxWidth(200);
            table_view_sales.getColumnModel().getColumn(3).setPreferredWidth(150);
            table_view_sales.getColumnModel().getColumn(3).setMaxWidth(200);
            table_view_sales.getColumnModel().getColumn(5).setPreferredWidth(100);
            table_view_sales.getColumnModel().getColumn(5).setMaxWidth(200);
            table_view_sales.getColumnModel().getColumn(6).setMaxWidth(80);
            table_view_sales.getColumnModel().getColumn(7).setPreferredWidth(90);
            table_view_sales.getColumnModel().getColumn(7).setMaxWidth(150);
        }

        txt_search_sale.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        txt_search_sale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_saleActionPerformed(evt);
            }
        });
        txt_search_sale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_search_saleKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_search_saleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_saleKeyReleased(evt);
            }
        });

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N

        javax.swing.GroupLayout panel_sale_detailsLayout = new javax.swing.GroupLayout(panel_sale_details);
        panel_sale_details.setLayout(panel_sale_detailsLayout);
        panel_sale_detailsLayout.setHorizontalGroup(
            panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panel_sale_detailsLayout.setVerticalGroup(
            panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search_sale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        desktop_pane_sales.setLayer(panel_sale_details, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_salesLayout = new javax.swing.GroupLayout(desktop_pane_sales);
        desktop_pane_sales.setLayout(desktop_pane_salesLayout);
        desktop_pane_salesLayout.setHorizontalGroup(
            desktop_pane_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_salesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_sale_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        desktop_pane_salesLayout.setVerticalGroup(
            desktop_pane_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_salesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_sale_details, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_sales, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_search_saleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_saleKeyReleased
        // TODO add your handling code here:
        searchSale();
    }//GEN-LAST:event_txt_search_saleKeyReleased

    private void txt_search_saleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_saleKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_saleKeyPressed

    private void txt_search_saleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_saleKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_saleKeyTyped

    private void txt_search_saleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_saleActionPerformed
        // TODO add your handling code here:
        searchSale();
    }//GEN-LAST:event_txt_search_saleActionPerformed

    private void table_view_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_salesMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            openSelectedSale();
        }
        
    }//GEN-LAST:event_table_view_salesMouseClicked

    private void table_view_salesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_view_salesKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            openSelectedSale();   
        }
    }//GEN-LAST:event_table_view_salesKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop_pane_sales;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JPanel panel_sale_details;
    private javax.swing.JTable table_view_sales;
    private javax.swing.JTextField txt_search_sale;
    // End of variables declaration//GEN-END:variables
}
