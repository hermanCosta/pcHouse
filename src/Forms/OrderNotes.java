/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Model.OrderNote;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HermanCosta
 */
public class OrderNotes extends javax.swing.JFrame {

    /**
     * Creates new form OrderNotes
     */
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    String orderNo, noteDate, note, user;
    OrderNote orderNote;

    public OrderNotes() {
        initComponents();
    }

    public OrderNotes(String _orderNo, String _user) {
        initComponents();
        setResizable(false);
        this.orderNo = _orderNo;
        this.user = _user;

        loadTableNotes();
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderNotes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadTableNotes() {
        ArrayList<OrderNote> noteList = new ArrayList();
        DefaultTableModel dtm = (DefaultTableModel) table_view_notes.getModel();
        dtm.setRowCount(0);

        try {
            dbConnection();

            lbl_notes_from_order.setText("Notes From " + orderNo);

            String query = "SELECT * FROM orderNotes WHERE noteId IN (SELECT noteId FROM orderNotes WHERE orderNo = ?) ";
            ps = con.prepareStatement(query);
            ps.setString(1, orderNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                orderNote = new OrderNote(rs.getString("date"), rs.getString("note"), rs.getString("user"));
                noteList.add(orderNote);
            }

            Object[] noteRow = new Object[3];
            for (int i = 0; i < noteList.size(); i++) {
                noteRow[0] = noteList.get(i).getDate();
                noteRow[1] = noteList.get(i).getNote();
                noteRow[2] = noteList.get(i).getUser();

                dtm.addRow(noteRow);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(OrderNotes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_notes_from_order = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_notes = new javax.swing.JTable();
        txt_note = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_notes_from_order.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_notes_from_order.setText("notesFromOrderNo");

        table_view_notes.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        table_view_notes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Note", "User"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_notes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_notesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_view_notes);
        if (table_view_notes.getColumnModel().getColumnCount() > 0) {
            table_view_notes.getColumnModel().getColumn(0).setPreferredWidth(150);
            table_view_notes.getColumnModel().getColumn(0).setMaxWidth(200);
            table_view_notes.getColumnModel().getColumn(2).setPreferredWidth(120);
            table_view_notes.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        txt_note.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        txt_note.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_note)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(lbl_notes_from_order)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_notes_from_order)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(txt_note, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_noteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noteActionPerformed

        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        noteDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentDate);
        note = txt_note.getText();

        int confirmInsertion = JOptionPane.showConfirmDialog(this, "Do you want to add note: " + note + " to the Database ?", "Order Notes", JOptionPane.YES_OPTION);
        if (confirmInsertion == 0) {

            try {
                dbConnection();

                String queryInsert = "INSERT INTO orderNotes (orderNo, date, note, user) VALUES(?, ?, ?, ?)";
                ps = con.prepareStatement(queryInsert);
                ps.setString(1, orderNo);
                ps.setString(2, noteDate);
                ps.setString(3, note);
                ps.setString(4, user);
                ps.executeUpdate();

                ps.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(OrderNotes.class.getName()).log(Level.SEVERE, null, ex);
            }

            txt_note.setText("");
            loadTableNotes();
        }
    }//GEN-LAST:event_txt_noteActionPerformed

    private void table_view_notesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_notesMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel dtm = (DefaultTableModel) table_view_notes.getModel();

            int selectedRow = table_view_notes.getSelectedRow();
            String selectedNote = dtm.getValueAt(selectedRow, 1).toString();
            String selectedUser = dtm.getValueAt(selectedRow, 2).toString();

            if (selectedUser.equals("System")) {
                JOptionPane.showMessageDialog(this, "Notes from System are not allowed to delete !", "Order Notes", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirmDeletion = JOptionPane.showConfirmDialog(null, "Do you want to Delete note " + selectedNote + " ?", "Order Notes", JOptionPane.YES_NO_OPTION);
                if (confirmDeletion == 0) {
                    try {
                        dbConnection();
                        String queryDelete = "DELETE FROM orderNotes WHERE orderNo = ? AND note = ?";
                        ps = con.prepareStatement(queryDelete);
                        ps.setString(1, orderNo);
                        ps.setString(2, selectedNote);
                        ps.executeUpdate();

                        loadTableNotes();

                        ps.close();
                        con.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(OrderNotes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_table_view_notesMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_notes_from_order;
    private javax.swing.JTable table_view_notes;
    private javax.swing.JTextField txt_note;
    // End of variables declaration//GEN-END:variables
}
