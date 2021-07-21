/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.awt.Color;
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
import javafx.collections.transformation.FilteredList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class OrdersMenu extends javax.swing.JInternalFrame {

    /**
     * Creates new form OrdersMenu
     */
     
     Color defaultColor, mouseEnteredColor;
     Connection con;
     ResultSet result ;
     PreparedStatement pst;
     
     
     
        
    public OrdersMenu() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        defaultColor = new Color(21,76,121);
        mouseEnteredColor = new Color(118,181,197);
        
        tableDataView();
    }
    
    public void connection() 
    {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","hermanhgc","He11m@ns");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tableDataView()
    {
        connection();
        
        try {
            pst = con.prepareStatement("select * from orderDetails");
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c; 
            c = rsd.getColumnCount();
            DefaultTableModel dft = (DefaultTableModel)table_view_orders.getModel();
            dft .setRowCount(0);
            
            while(rs.next())
            {
                Vector v2 = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                    v2.add(rs.getString("orderNo"));
                    v2.add(rs.getString("firstName"));
                    v2.add(rs.getString("lastName"));
                    v2.add(rs.getString("contactNo"));
                    v2.add(rs.getString("deviceBrand"));
                    v2.add(rs.getString("deviceModel"));
                    v2.add(rs.getString("serialNumber"));
                    v2.add(rs.getString("fault"));
                }
                
                dft.addRow(v2);
            }
                    
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchOrder()
    {
        FilteredList<String>
        FilteredList<Object> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txt_search_order.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getFirstName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.

                } else if (String.valueOf(myObject.getLastName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } 

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<myObject> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table_view_orders.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table_view_orders.setItems(sortedData);
    }
    
    
    
    public void lastNameKeyPressed(java.awt.event.KeyEvent evt) {                                    

  try {
            //Class.forName("com.mysql.jdbc.Driver");
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","hermanhgc","He11m@ns");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CreateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    

        try{
        String sql = "SELECT orderNo, firstName, lastName, contactNo, deviceBrand,deviceModel,serialNumber,fault FROM pcHouse "
                + "WHERE lastName LIKE '% " + txt_search_order.getText() + " %' ";
        pst = con.prepareStatement(sql);
        result = pst.executeQuery();
        
        
        orderDetails.setModel(DbUtils.resultSetToTableModel(result));
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
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

        txt_search_order = new javax.swing.JTextField();
        btn_search_order = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setPreferredSize(new java.awt.Dimension(655, 700));

        txt_search_order.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N

        btn_search_order.setBackground(new java.awt.Color(21, 76, 121));
        btn_search_order.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_search_order.setForeground(new java.awt.Color(255, 255, 255));
        btn_search_order.setText("Search");
        btn_search_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_orderActionPerformed(evt);
            }
        });

        table_view_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "OrderNo.", "First Name", "Last Name", "Contact No", "Brand", "Model", "S/N", "Fault"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_view_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_ordersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_view_orders);

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search_order)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addContainerGap(216, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_search_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_search_orderActionPerformed

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked
        // TODO add your handling code here:
        DefaultTableModel d1 = (DefaultTableModel)table_view_orders.getModel();
        int selectedIndex = table_view_orders.getSelectedRow();

        String status = d1.getValueAt(selectedIndex, 7).toString();

        if (status.equals("Completed")) {
            JOptionPane.showMessageDialog(this, "This order has been completed!");
            //JOptionPane.showMessageDialog(this, "New order created successfully!");
        }
        else
        {
            String orderNo = d1.getValueAt(selectedIndex, 0).toString();
            String model = d1.getValueAt(selectedIndex, 2).toString();
            String serialNumber = d1.getValueAt(selectedIndex, 3).toString();
            String price = d1.getValueAt(selectedIndex, 4).toString();
            String deposit = d1.getValueAt(selectedIndex, 5).toString();
            String due = d1.getValueAt(selectedIndex, 6).toString();

            new Billing(orderNo,model,serialNumber,price,deposit,due).setVisible(true);

            this.setVisible(false);
        }
    }//GEN-LAST:event_table_view_ordersMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_search_order;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTextField txt_search_order;
    // End of variables declaration//GEN-END:variables
}
