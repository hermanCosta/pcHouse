/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Model.Fault;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HermanCosta
 */
public class Faults extends javax.swing.JInternalFrame {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Statement stmt;
    Fault fault;
    String faultName;

    public Faults() {
        initComponents();

        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        tableSettings(table_view_faults);
        loadFaultTable();
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
            Logger.getLogger(Faults.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadFaultTable() {
        ArrayList<Fault> list = new ArrayList<>();

        try {
            dbConnection();

            String query = "SELECT * FROM faults";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next()) {
                fault = new Fault(rs.getString("faultName"));
                fault.setFaultId(rs.getInt("faultId"));
                list.add(fault);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Faults.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();
        dtm.setRowCount(0);

        Object[] row = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getFaultId();
            row[1] = list.get(i).getFault();

            dtm.addRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_faults = new javax.swing.JDesktopPane();
        panel_customers = new javax.swing.JPanel();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_faults = new javax.swing.JTable();
        txt_fault = new javax.swing.JTextField();
        lbl_search_icon = new javax.swing.JLabel();
        btn_delete = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_customers.setPreferredSize(new java.awt.Dimension(1049, 700));

        btn_add.setBackground(new java.awt.Color(21, 76, 121));
        btn_add.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add_new.png"))); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_update.setBackground(new java.awt.Color(21, 76, 121));
        btn_update.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save_changes.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        table_view_faults.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fault Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_faultsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_faults);
        if (table_view_faults.getColumnModel().getColumnCount() > 0) {
            table_view_faults.getColumnModel().getColumn(0).setPreferredWidth(40);
            table_view_faults.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        txt_fault.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        txt_fault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_faultActionPerformed(evt);
            }
        });
        txt_fault.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_faultKeyReleased(evt);
            }
        });

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N
        lbl_search_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_search_iconMouseClicked(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(21, 76, 121));
        btn_delete.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_clear_fields.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_clear_fields.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_clear.png"))); // NOI18N
        btn_clear_fields.setText("ClearFields");
        btn_clear_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_customersLayout = new javax.swing.GroupLayout(panel_customers);
        panel_customers.setLayout(panel_customersLayout);
        panel_customersLayout.setHorizontalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_fault, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_customersLayout.createSequentialGroup()
                                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        panel_customersLayout.setVerticalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fault, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_clear_fields)
                    .addComponent(btn_delete)
                    .addComponent(btn_update)
                    .addComponent(btn_add))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        desktop_pane_faults.setLayer(panel_customers, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_faultsLayout = new javax.swing.GroupLayout(desktop_pane_faults);
        desktop_pane_faults.setLayout(desktop_pane_faultsLayout);
        desktop_pane_faultsLayout.setHorizontalGroup(
            desktop_pane_faultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_faultsLayout.createSequentialGroup()
                .addComponent(panel_customers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        desktop_pane_faultsLayout.setVerticalGroup(
            desktop_pane_faultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_customers, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_faults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_faults, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
        if (txt_fault.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Fault", JOptionPane.ERROR_MESSAGE);
        } else {
            faultName = txt_fault.getText();

            try {
                dbConnection();

                //add a new customer if not exists AND fields are not empty
                if (!fault.getFault().equals(faultName)) {
                    fault = new Fault(faultName);

                    int confirmInsertion = JOptionPane.showConfirmDialog(this, "Do you want to add fault " + fault.getFault() + " ?", "Add New Fault", JOptionPane.YES_NO_OPTION);
                    if (confirmInsertion == 0) {
                        String insertQuery = "INSERT INTO faults (faultName) VALUES(?)";

                        ps = con.prepareStatement(insertQuery);
                        ps.setString(1, fault.getFault());
                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(this, "'" + fault.getFault() + "' added Successfully");
                        loadFaultTable();
                        txt_fault.setText("");
                    }
                } 
                
                else {
                    JOptionPane.showMessageDialog(this, "Fault '" + fault.getFault() + "' already exist into Database !", "New Fault", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Faults.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed

        if (txt_fault.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please, check Empty field", "New Fault", JOptionPane.ERROR_MESSAGE);
            
        } else {
            faultName = txt_fault.getText();

            try {

                dbConnection();

                if (fault.getFault().equals(faultName)) {
                    JOptionPane.showMessageDialog(null, "No changes to be updated !", "Update Fault", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Updating '" + faultName + "' ?",
                            "Update Fault", JOptionPane.YES_NO_OPTION);

                    if (confirmEditing == 0) {
                        String query = "UPDATE faults SET faultName = ? WHERE faultId = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, faultName);
                        ps.setInt(2, fault.getFaultId());
                        ps.executeUpdate();

                        loadFaultTable();
                        txt_fault.setText("");
                    } else {
                        loadFaultTable();
                        txt_fault.setText("");
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void txt_faultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_faultActionPerformed

    }//GEN-LAST:event_txt_faultActionPerformed

    private void txt_faultKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_faultKeyReleased
        // TODO add your handling code here:
        ArrayList<Fault> faultList = new ArrayList<>();
        String faultText = txt_fault.getText();

        try {
            dbConnection();

            String query = "SELECT * FROM faults WHERE faultName LIKE '%" + faultText + "%'";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next()) {
                fault = new Fault(rs.getString("faultName"));
                fault.setFaultId(rs.getInt("faultId"));
                faultList.add(fault);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Faults.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();
        dtm.setRowCount(0);

        Object[] row = new Object[2];
        for (int i = 0; i < faultList.size(); i++) {
            row[0] = faultList.get(i).getFaultId();
            row[1] = faultList.get(i).getFault();

            dtm.addRow(row);
        }
    }//GEN-LAST:event_txt_faultKeyReleased

    private void table_view_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_faultsMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();
            int row = table_view_faults.getSelectedRow();

            int id = (int) dtm.getValueAt(row, 0);
            String faultText = dtm.getValueAt(row, 1).toString();

            txt_fault.setText(faultText);

            fault = new Fault(faultText);
            fault.setFaultId(id);

        }
    }//GEN-LAST:event_table_view_faultsMouseClicked

    private void lbl_search_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_search_iconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_search_iconMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (txt_fault.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please, check Empty field", "New Fault", JOptionPane.ERROR_MESSAGE);
        } else {
            try {

                dbConnection();

                int confirmEditing = JOptionPane.showConfirmDialog(this, "Confirm Deletion '" + fault.getFault() + "' ?",
                        "Update Fault", JOptionPane.YES_NO_OPTION);

                if (confirmEditing == 0) {
                    String query = "DELETE FROM faults WHERE faultId = ?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, fault.getFaultId());
                    ps.executeUpdate();

                    loadFaultTable();
                    txt_fault.setText("");
                } else {
                    loadFaultTable();
                    txt_fault.setText("");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        // TODO add your handling code here:
        txt_fault.setText("");
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_update;
    private javax.swing.JDesktopPane desktop_pane_faults;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JPanel panel_customers;
    private javax.swing.JTable table_view_faults;
    private javax.swing.JTextField txt_fault;
    // End of variables declaration//GEN-END:variables
}
