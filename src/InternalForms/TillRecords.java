/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.PrintFullReport;
import Forms.PrintTillRecord;
import Model.TillRecord;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class TillRecords extends javax.swing.JInternalFrame {

    PreparedStatement ps;
    Connection con;
    ResultSet rs;
    TillRecord tillRecord;
    Date date;

    public TillRecords() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        table_view_till_records.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 12));
        scroll_pane_table_till_records.setOpaque(false);
        scroll_pane_table_till_records.getViewport().setOpaque(false);
        
        date = new Date();
        loadWeeklyRecordsFromDb();
    }
    
    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TillRecords.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadWeeklyRecordsFromDb() {
        // TODO add your handling code here:
        ArrayList<TillRecord> tillRecordsList = new ArrayList<>();
        //String dateFormat = "";
        double cashInTotal = 0, cashOutTotal = 0;
        DefaultTableModel dtm = (DefaultTableModel) table_view_till_records.getModel();
        dtm.setRowCount(0);
        try {
            dbConnection();
            
            String query = "SELECT * FROM tillClosing WHERE WEEKOFYEAR(tillOpeningDate) = WEEKOFYEAR(NOW()) ORDER BY date DESC";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            if (!rs.isBeforeFirst())
            {
                JOptionPane.showMessageDialog(this, "No Till Records on this week !");
            }
            else {
                while (rs.next()) {
                    //dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("date"));
                    
                    tillRecord = new TillRecord(rs.getString("tillOpeningDate"), rs.getDouble("cashInTotal"), rs.getDouble("cashOutTotal"), rs.getString("notes"));
                    lbl_till_closing_records.setText("Till Closing Records");
                    
                    tillRecordsList.add(tillRecord);
                }
                
                Object[] row = new Object[4];
                for (int i = 0; i < tillRecordsList.size(); i++)
                {
                    row[0] = tillRecordsList.get(i).getTillOpeningDate();
                    row[1] = tillRecordsList.get(i).getCashInTotal();
                    row[2] = tillRecordsList.get(i).getCashOutTotal();
                    row[3] = tillRecordsList.get(i).getNotes();
                    dtm.addRow(row);
                }
                
                for (int i = 0; i < dtm.getRowCount(); i++)
                {
                    cashInTotal += (double) dtm.getValueAt(i, 1);
                    cashOutTotal += (double) dtm.getValueAt(i, 2);
                }
                
                lbl_cash_in_total.setText("Cash In Records Total " + cashInTotal);
                lbl_cash_out_total.setText("Cash Out Records Total " + cashOutTotal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TillRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_entries = new javax.swing.JDesktopPane();
        panel_till_closing = new javax.swing.JPanel();
        date_picker = new com.toedter.calendar.JCalendar();
        btn_daily_records = new javax.swing.JButton();
        btn_weekly_records = new javax.swing.JButton();
        btn_print_records = new javax.swing.JButton();
        panel_till_closing_records = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();
        scroll_pane_till_records = new javax.swing.JScrollPane();
        panel_till_records = new javax.swing.JPanel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
        lbl_till_closing_records = new javax.swing.JLabel();
        lbl_cash_in_total = new javax.swing.JLabel();
        lbl_cash_out_total = new javax.swing.JLabel();
        panel_table_records = new javax.swing.JPanel();
        scroll_pane_table_till_records = new javax.swing.JScrollPane();
        table_view_till_records = new javax.swing.JTable();
        btn_monthly_records = new javax.swing.JButton();

        setBorder(null);
        setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1049, 700));

        date_picker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        date_picker.setMaxSelectableDate(new java.util.Date(253370775693000L));
        date_picker.setPreferredSize(new java.awt.Dimension(400, 200));
        date_picker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                date_pickerMouseClicked(evt);
            }
        });
        date_picker.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_pickerPropertyChange(evt);
            }
        });

        btn_daily_records.setBackground(new java.awt.Color(21, 76, 121));
        btn_daily_records.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_daily_records.setForeground(new java.awt.Color(255, 255, 255));
        btn_daily_records.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_daily_record.png"))); // NOI18N
        btn_daily_records.setText("Daily Records");
        btn_daily_records.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_daily_recordsActionPerformed(evt);
            }
        });

        btn_weekly_records.setBackground(new java.awt.Color(21, 76, 121));
        btn_weekly_records.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_weekly_records.setForeground(new java.awt.Color(255, 255, 255));
        btn_weekly_records.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_weekly_record.png"))); // NOI18N
        btn_weekly_records.setText("Weekly Records");
        btn_weekly_records.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_weekly_recordsActionPerformed(evt);
            }
        });

        btn_print_records.setBackground(new java.awt.Color(21, 76, 121));
        btn_print_records.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_print_records.setForeground(new java.awt.Color(255, 255, 255));
        btn_print_records.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_print_records.setText("Print Records");
        btn_print_records.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_recordsActionPerformed(evt);
            }
        });

        panel_till_closing_records.setBackground(new java.awt.Color(21, 76, 121));
        panel_till_closing_records.setForeground(new java.awt.Color(255, 255, 255));

        lbl_order_print_view.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_print_view.setForeground(new java.awt.Color(255, 255, 255));
        lbl_order_print_view.setText("Till Closing Records");

        javax.swing.GroupLayout panel_till_closing_recordsLayout = new javax.swing.GroupLayout(panel_till_closing_records);
        panel_till_closing_records.setLayout(panel_till_closing_recordsLayout);
        panel_till_closing_recordsLayout.setHorizontalGroup(
            panel_till_closing_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closing_recordsLayout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(lbl_order_print_view)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_till_closing_recordsLayout.setVerticalGroup(
            panel_till_closing_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_closing_recordsLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lbl_order_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
        );

        scroll_pane_till_records.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel_till_records.setBackground(new java.awt.Color(255, 255, 255));
        panel_till_records.setPreferredSize(new java.awt.Dimension(595, 842));

        panel_header.setBackground(new java.awt.Color(255, 255, 255));

        lbl_logo_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_land_line_number.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_phone_number.png"))); // NOI18N
        lbl_land_line_number.setText("+353 (01) 563-9520");

        lbl_mobile_number.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_mobile_number.png"))); // NOI18N
        lbl_mobile_number.setText("+353 (83) 012-8190");

        lbl_address1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_address.png"))); // NOI18N
        lbl_address1.setText("12A, Frederick Street North, Dublin 1");

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_mobile_number, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_land_line_number, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_address1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_headerLayout.createSequentialGroup()
                .addComponent(line_header)
                .addContainerGap())
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lbl_land_line_number)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_mobile_number)
                        .addGap(0, 0, 0)
                        .addComponent(lbl_address1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_till_closing_records.setFont(new java.awt.Font("sansserif", 1, 17)); // NOI18N
        lbl_till_closing_records.setText("Till Closing Records");

        lbl_cash_in_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_in_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_cash_in_total.setText("cashInTotal");

        lbl_cash_out_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_out_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_cash_out_total.setText("cashOutTotal");

        panel_table_records.setBackground(new java.awt.Color(255, 255, 255));
        panel_table_records.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        panel_table_records.setPreferredSize(new java.awt.Dimension(458, 554));

        scroll_pane_table_till_records.setEnabled(false);

        table_view_till_records.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        table_view_till_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Cash In", "Cash Out", "Notes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_till_records.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_till_recordsMouseClicked(evt);
            }
        });
        scroll_pane_table_till_records.setViewportView(table_view_till_records);
        if (table_view_till_records.getColumnModel().getColumnCount() > 0) {
            table_view_till_records.getColumnModel().getColumn(0).setPreferredWidth(120);
            table_view_till_records.getColumnModel().getColumn(0).setMaxWidth(200);
            table_view_till_records.getColumnModel().getColumn(1).setPreferredWidth(80);
            table_view_till_records.getColumnModel().getColumn(1).setMaxWidth(120);
            table_view_till_records.getColumnModel().getColumn(2).setPreferredWidth(80);
            table_view_till_records.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        javax.swing.GroupLayout panel_table_recordsLayout = new javax.swing.GroupLayout(panel_table_records);
        panel_table_records.setLayout(panel_table_recordsLayout);
        panel_table_recordsLayout.setHorizontalGroup(
            panel_table_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_pane_table_till_records, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
        );
        panel_table_recordsLayout.setVerticalGroup(
            panel_table_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_table_recordsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scroll_pane_table_till_records, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_till_recordsLayout = new javax.swing.GroupLayout(panel_till_records);
        panel_till_records.setLayout(panel_till_recordsLayout);
        panel_till_recordsLayout.setHorizontalGroup(
            panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_recordsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_till_closing_records)
                .addGap(207, 207, 207))
            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_cash_out_total)
                    .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cash_in_total)
                    .addComponent(panel_table_records, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panel_till_recordsLayout.setVerticalGroup(
            panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_till_closing_records)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_table_records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_cash_in_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_cash_out_total)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        scroll_pane_till_records.setViewportView(panel_till_records);

        btn_monthly_records.setBackground(new java.awt.Color(21, 76, 121));
        btn_monthly_records.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_monthly_records.setForeground(new java.awt.Color(255, 255, 255));
        btn_monthly_records.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_monthly_record.png"))); // NOI18N
        btn_monthly_records.setText("Monthly Records");
        btn_monthly_records.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_monthly_recordsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_till_closingLayout = new javax.swing.GroupLayout(panel_till_closing);
        panel_till_closing.setLayout(panel_till_closingLayout);
        panel_till_closingLayout.setHorizontalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closingLayout.createSequentialGroup()
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_till_closingLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(date_picker, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                                .addGap(11, 11, 11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_closingLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_monthly_records, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_weekly_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_daily_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_print_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(scroll_pane_till_records, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(panel_till_closing_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_till_closingLayout.setVerticalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closingLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panel_till_closing_records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addComponent(date_picker, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_daily_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_weekly_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_monthly_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_print_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scroll_pane_till_records, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        desktop_pane_entries.setLayer(panel_till_closing, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_entriesLayout = new javax.swing.GroupLayout(desktop_pane_entries);
        desktop_pane_entries.setLayout(desktop_pane_entriesLayout);
        desktop_pane_entriesLayout.setHorizontalGroup(
            desktop_pane_entriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_till_closing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        desktop_pane_entriesLayout.setVerticalGroup(
            desktop_pane_entriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_till_closing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_entries)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_entries)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void date_pickerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_pickerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_date_pickerPropertyChange

    private void btn_daily_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_daily_recordsActionPerformed
        // TODO add your handling code here:
        String tillClosingDate = "";

        Date datePicker = date_picker.getDate();
        String tillOpeningDate = new SimpleDateFormat("dd/MM/yyyy").format(datePicker.getTime());
//        String startDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(datePicker.getTime());
//        String endDate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(datePicker.getTime());
        
        try {
            dbConnection();
            
            //String query = "SELECT * FROM tillClosing WHERE tillOpeningDate >= ? AND date <= ?";
            String query = "SELECT * FROM tillClosing WHERE tillOpeningDate = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, tillOpeningDate);
            //ps.setString(2, endDate);
            rs = ps.executeQuery();
            
            if (!rs.isBeforeFirst())
            {
                //tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(datePicker.getTime());
                JOptionPane.showMessageDialog(this, "No Till records on " + tillOpeningDate + "!");
            }
            else
            {
                while (rs.next())
                {
                    tillClosingDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("date"));

                    PrintTillRecord printTillRecord = new PrintTillRecord(tillClosingDate, rs.getString("cashier"),
                        rs.getString("tillOpeningDate"), rs.getDouble("cashTotal"), rs.getDouble("cardTotal"), rs.getDouble("entriesTotal"), 
                        rs.getDouble("cashInTotal"), rs.getDouble("payments"), rs.getDouble("takes"), rs.getDouble("other"),
                        rs.getDouble("cashOutTotal"), rs.getDouble("tillTotal"), rs.getDouble("enterCashTotal"), 
                        rs.getDouble("enterCardTotal"), rs.getDouble("adjustments"), rs.getDouble("balance"), rs.getString("notes"));
                    
                    printTillRecord.setVisible(true);
                }
            }   
        } catch (SQLException ex) {
            Logger.getLogger(TillRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_daily_recordsActionPerformed

    private void btn_weekly_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_weekly_recordsActionPerformed
        // TODO add your handling code here:
        loadWeeklyRecordsFromDb();
    }//GEN-LAST:event_btn_weekly_recordsActionPerformed

    private void btn_print_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_recordsActionPerformed
        // TODO add your handling code here:
        // Get date from calendar
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("ClosingTillRecord" + tillClosingDate);
        PageFormat format = printerJob.getPageFormat(null);

        printerJob.setPrintable(new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D) graphics;
                panel_till_records.paint(graphics2D);

                return Printable.PAGE_EXISTS;
            }
        }, format);

        boolean returningResult = printerJob.printDialog();

        if (returningResult) {
            try {

                printerJob.print();

                JOptionPane.showMessageDialog(this, "Till Records Printed Successfully");

            } catch (PrinterException ex) {
                Logger.getLogger(PrintFullReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_print_recordsActionPerformed

    private void date_pickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_date_pickerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_date_pickerMouseClicked

    private void table_view_till_recordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_till_recordsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_till_recordsMouseClicked

    private void btn_monthly_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_monthly_recordsActionPerformed
        // TODO add your handling code here:
        ArrayList<TillRecord> tillRecordsList = new ArrayList<>();
        String dateFormat = "";
        double cashInTotal = 0, cashOutTotal = 0;
        DefaultTableModel dtm = (DefaultTableModel) table_view_till_records.getModel();
        dtm.setRowCount(0);
        try {
            dbConnection();
            
            String query = "SELECT * FROM tillClosing WHERE MONTH(tillOpeningDate) = MONTH(NOW()) ORDER BY date DESC";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            if (!rs.isBeforeFirst())
            {
                JOptionPane.showMessageDialog(this, "No Till Records on this Month !");
                
            }
            else {
                while (rs.next()) {
                   dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("tillOpeningDate"));
                    
                    tillRecord = new TillRecord(dateFormat, rs.getDouble("cashInTotal"), rs.getDouble("cashOutTotal"), rs.getString("notes"));
                    lbl_till_closing_records.setText("Till Closing Records");
                    
                    tillRecordsList.add(tillRecord);
                    
                    System.out.print("Till Opening Date: " + tillRecord.getTillOpeningDate());
                }
                
                Object[] row = new Object[4];
                for (int i = 0; i < tillRecordsList.size(); i++)
                {
                    row[0] = tillRecordsList.get(i).getTillOpeningDate();
                    row[1] = tillRecordsList.get(i).getCashInTotal();
                    row[2] = tillRecordsList.get(i).getCashOutTotal();
                    row[3] = tillRecordsList.get(i).getNotes();
                    dtm.addRow(row);
                }
                
                for (int i = 0; i < dtm.getRowCount(); i++)
                {
                    cashInTotal += (double) dtm.getValueAt(i, 1);
                    cashOutTotal += (double) dtm.getValueAt(i, 2);
                }
                
                lbl_cash_in_total.setText("Cash In Records Total " + cashInTotal);
                lbl_cash_out_total.setText("Cash Out Records Total " + cashOutTotal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TillRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_monthly_recordsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_daily_records;
    private javax.swing.JButton btn_monthly_records;
    private javax.swing.JButton btn_print_records;
    private javax.swing.JButton btn_weekly_records;
    private com.toedter.calendar.JCalendar date_picker;
    private javax.swing.JDesktopPane desktop_pane_entries;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_cash_in_total;
    private javax.swing.JLabel lbl_cash_out_total;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_till_closing_records;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_table_records;
    private javax.swing.JPanel panel_till_closing;
    private javax.swing.JPanel panel_till_closing_records;
    private javax.swing.JPanel panel_till_records;
    private javax.swing.JScrollPane scroll_pane_table_till_records;
    private javax.swing.JScrollPane scroll_pane_till_records;
    private javax.swing.JTable table_view_till_records;
    // End of variables declaration//GEN-END:variables
}
