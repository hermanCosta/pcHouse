/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.Login;
import Forms.PrintFullReport;
import Model.CashEntry;
import java.awt.Color;
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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CashEntries extends javax.swing.JInternalFrame {

    PreparedStatement ps;
    Connection con;
    ResultSet rs;
    ResultSetMetaData rsmd;
    CashEntry cashEntry;
    Date datePicked;
    String cashEntriesDate;

    public CashEntries(Date _date) {
        initComponents();
        this.datePicked = _date;

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        tableSettings(table_view_cash_entry_records);
        loadRecordsOfTheDay();
    }

    public void tableSettings(JTable table) {
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 12));

        scroll_pane_table_cash_entry.setOpaque(false);
        scroll_pane_table_cash_entry.getViewport().setOpaque(false);
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CashEntries.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadRecordsOfTheDay() {
        // TODO add your handling code here:
        ArrayList<CashEntry> recordsList = new ArrayList<>();
        double cashEntriesTotal = 0;

        String startDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date_picker.getDate());
        String endDate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date_picker.getDate());
        String dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(date_picker.getDate());

        try {
            dbConnection();

            String query = "SELECT * FROM cashEntry WHERE entryDate >= ? AND entryDate <= ? ORDER BY entryDate DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            rs = ps.executeQuery();

            DefaultTableModel dtm = (DefaultTableModel) table_view_cash_entry_records.getModel();
            dtm.setRowCount(0);

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    String entryDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("entryDate"));
                    cashEntry = new CashEntry(rs.getDouble("value"), entryDate, rs.getString("notes"), rs.getString("user"));

                    recordsList.add(cashEntry);
                }

                Object[] row = new Object[4];
                for (int i = 0; i < recordsList.size(); i++) {
                    row[0] = recordsList.get(i).getValue();
                    row[1] = recordsList.get(i).getEntryDate();
                    row[2] = recordsList.get(i).getNotes();
                    row[3] = recordsList.get(i).getUser();
                    dtm.addRow(row);
                }

                for (int i = 0; i < dtm.getRowCount(); i++) {
                    cashEntriesTotal += (double) dtm.getValueAt(i, 0);
                }

                lbl_entry_records.setText("Cash Entries Records on " + dateFormat + " !");
                lbl_entry_records.setForeground(Color.black);
                lbl_cash_entries_total.setText("Cash Entries Total ............. ??? " + String.valueOf(cashEntriesTotal));
                lbl_cash_entries_total.setVisible(true);
            } else {
                lbl_entry_records.setText("No Cash Entries Records on " + dateFormat + " !");
                lbl_entry_records.setForeground(Color.red);
                lbl_cash_entries_total.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashEntries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_entries = new javax.swing.JDesktopPane();
        panel_till_closing = new javax.swing.JPanel();
        btn_monthly_records = new javax.swing.JButton();
        scroll_pane_orders_sales = new javax.swing.JScrollPane();
        panel_cash_records = new javax.swing.JPanel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
        lbl_cash_entries_total = new javax.swing.JLabel();
        lbl_entry_records = new javax.swing.JLabel();
        panel_table_view = new javax.swing.JPanel();
        scroll_pane_table_cash_entry = new javax.swing.JScrollPane();
        table_view_cash_entry_records = new javax.swing.JTable();
        date_picker = new com.toedter.calendar.JCalendar();
        btn_daily_records = new javax.swing.JButton();
        btn_weekly_records = new javax.swing.JButton();
        panel_cash_record = new javax.swing.JPanel();
        txt_enter_value = new javax.swing.JTextField();
        txt_notes = new javax.swing.JTextField();
        lbl_enter_value = new javax.swing.JLabel();
        lbl_notes = new javax.swing.JLabel();
        btn_register_entry = new javax.swing.JButton();
        btn_print_records = new javax.swing.JButton();

        setBorder(null);
        setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1049, 700));

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

        scroll_pane_orders_sales.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel_cash_records.setBackground(new java.awt.Color(255, 255, 255));
        panel_cash_records.setPreferredSize(new java.awt.Dimension(595, 842));

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

        lbl_cash_entries_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_entries_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_entries_black.png"))); // NOI18N
        lbl_cash_entries_total.setText("cashEntriesTotal");

        lbl_entry_records.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_entry_records.setText("cashEntryRecords");

        panel_table_view.setBackground(new java.awt.Color(255, 255, 255));
        panel_table_view.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        scroll_pane_table_cash_entry.setEnabled(false);

        table_view_cash_entry_records.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        table_view_cash_entry_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Value ???", "Entry Date", "Notes", "User"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_cash_entry_records.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_cash_entry_recordsMouseClicked(evt);
            }
        });
        scroll_pane_table_cash_entry.setViewportView(table_view_cash_entry_records);
        if (table_view_cash_entry_records.getColumnModel().getColumnCount() > 0) {
            table_view_cash_entry_records.getColumnModel().getColumn(0).setPreferredWidth(60);
            table_view_cash_entry_records.getColumnModel().getColumn(0).setMaxWidth(150);
            table_view_cash_entry_records.getColumnModel().getColumn(1).setPreferredWidth(130);
            table_view_cash_entry_records.getColumnModel().getColumn(1).setMaxWidth(180);
            table_view_cash_entry_records.getColumnModel().getColumn(3).setPreferredWidth(80);
            table_view_cash_entry_records.getColumnModel().getColumn(3).setMaxWidth(180);
        }

        javax.swing.GroupLayout panel_table_viewLayout = new javax.swing.GroupLayout(panel_table_view);
        panel_table_view.setLayout(panel_table_viewLayout);
        panel_table_viewLayout.setHorizontalGroup(
            panel_table_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_pane_table_cash_entry, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );
        panel_table_viewLayout.setVerticalGroup(
            panel_table_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_table_viewLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scroll_pane_table_cash_entry, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_cash_recordsLayout = new javax.swing.GroupLayout(panel_cash_records);
        panel_cash_records.setLayout(panel_cash_recordsLayout);
        panel_cash_recordsLayout.setHorizontalGroup(
            panel_cash_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_recordsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panel_cash_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_table_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cash_entries_total, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cash_recordsLayout.createSequentialGroup()
                .addContainerGap(223, Short.MAX_VALUE)
                .addComponent(lbl_entry_records, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap(223, Short.MAX_VALUE))
        );
        panel_cash_recordsLayout.setVerticalGroup(
            panel_cash_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_recordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_entry_records)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_table_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_cash_entries_total, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        scroll_pane_orders_sales.setViewportView(panel_cash_records);

        date_picker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        date_picker.setFont(new java.awt.Font("sansserif", 0, 11)); // NOI18N
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

        panel_cash_record.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_enter_value.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_enter_value.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_enter_valueActionPerformed(evt);
            }
        });
        txt_enter_value.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_enter_valueKeyReleased(evt);
            }
        });

        txt_notes.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_notes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_notesActionPerformed(evt);
            }
        });

        lbl_enter_value.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        lbl_enter_value.setText("Enter Value ???");

        lbl_notes.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        lbl_notes.setText("Notes");

        javax.swing.GroupLayout panel_cash_recordLayout = new javax.swing.GroupLayout(panel_cash_record);
        panel_cash_record.setLayout(panel_cash_recordLayout);
        panel_cash_recordLayout.setHorizontalGroup(
            panel_cash_recordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_recordLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cash_recordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_enter_value)
                    .addComponent(txt_enter_value, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cash_recordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cash_recordLayout.createSequentialGroup()
                        .addComponent(lbl_notes)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_cash_recordLayout.createSequentialGroup()
                        .addComponent(txt_notes)
                        .addContainerGap())))
        );
        panel_cash_recordLayout.setVerticalGroup(
            panel_cash_recordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_recordLayout.createSequentialGroup()
                .addGroup(panel_cash_recordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_notes)
                    .addComponent(lbl_enter_value))
                .addGap(0, 0, 0)
                .addGroup(panel_cash_recordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_notes)
                    .addComponent(txt_enter_value, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_register_entry.setBackground(new java.awt.Color(0, 153, 102));
        btn_register_entry.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_register_entry.setForeground(new java.awt.Color(255, 255, 255));
        btn_register_entry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save.png"))); // NOI18N
        btn_register_entry.setText("Register Entry");
        btn_register_entry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_register_entryActionPerformed(evt);
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

        javax.swing.GroupLayout panel_till_closingLayout = new javax.swing.GroupLayout(panel_till_closing);
        panel_till_closing.setLayout(panel_till_closingLayout);
        panel_till_closingLayout.setHorizontalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_closingLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addComponent(panel_cash_record, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_register_entry, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_monthly_records, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_weekly_records, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_daily_records, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(date_picker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                            .addComponent(btn_print_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_till_closingLayout.setVerticalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closingLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_register_entry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_cash_record, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addComponent(date_picker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_daily_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_weekly_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_monthly_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_print_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
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

    private void btn_monthly_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_monthly_recordsActionPerformed
        cashEntriesDate = new SimpleDateFormat("yyyy-MM-dd").format(date_picker.getDate());
        String month = new SimpleDateFormat("MMMM").format(date_picker.getDate());
        ArrayList<CashEntry> recordsList = new ArrayList<>();
        double cashEntriesTotal = 0;

        try {
            dbConnection();

            String query = "SELECT * FROM cashEntry WHERE MONTH(entryDate) = MONTH(?) ORDER BY entryDate DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, cashEntriesDate);
            rs = ps.executeQuery();

            DefaultTableModel dtm = (DefaultTableModel) table_view_cash_entry_records.getModel();
            dtm.setRowCount(0);

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    String dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("entryDate"));
                    cashEntry = new CashEntry(rs.getDouble("value"), dateFormat, rs.getString("notes"), rs.getString("user"));

                    recordsList.add(cashEntry);
                }
                // Table models
                Object[] row = new Object[4];
                for (int i = 0; i < recordsList.size(); i++) {
                    row[0] = recordsList.get(i).getValue();
                    row[1] = recordsList.get(i).getEntryDate();
                    row[2] = recordsList.get(i).getNotes();
                    row[3] = recordsList.get(i).getUser();
                    dtm.addRow(row);
                }

                for (int i = 0; i < dtm.getRowCount(); i++) {
                    cashEntriesTotal += (double) dtm.getValueAt(i, 0);
                }

                lbl_entry_records.setText("Cash Entries Records In " + month + " !");
                lbl_entry_records.setForeground(Color.black);
                lbl_cash_entries_total.setText("Cash Entries Total ............. ???" + String.valueOf(cashEntriesTotal));
                lbl_cash_entries_total.setVisible(true);
            } else {

                lbl_entry_records.setText("No Cash Entries Records In " + month + " !");
                lbl_entry_records.setForeground(Color.red);
                lbl_cash_entries_total.setVisible(false);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CashEntries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_monthly_recordsActionPerformed

    private void date_pickerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_pickerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_date_pickerPropertyChange

    private void btn_daily_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_daily_recordsActionPerformed
        // TODO add your handling code here:
        loadRecordsOfTheDay();
    }//GEN-LAST:event_btn_daily_recordsActionPerformed

    private void btn_weekly_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_weekly_recordsActionPerformed
        // TODO add your handling code here:
        cashEntriesDate = new SimpleDateFormat("yyyy-MM-dd").format(date_picker.getDate());
        int weekYear = date_picker.getCalendar().get(Calendar.WEEK_OF_YEAR);
        ArrayList<CashEntry> recordsList = new ArrayList<>();
        double cashEntriesTotal = 0;

        try {
            dbConnection();

            String query = "SELECT * FROM cashEntry WHERE WEEK(entryDate, 0) = WEEK(?, 0) ORDER BY entryDate DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, cashEntriesDate);
            rs = ps.executeQuery();

            DefaultTableModel dtm = (DefaultTableModel) table_view_cash_entry_records.getModel();
            dtm.setRowCount(0);

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    String dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("entryDate"));
                    cashEntry = new CashEntry(rs.getDouble("value"), dateFormat, rs.getString("notes"), rs.getString("user"));

                    recordsList.add(cashEntry);
                }
                // Table models
                Object[] row = new Object[4];
                for (int i = 0; i < recordsList.size(); i++) {
                    row[0] = recordsList.get(i).getValue();
                    row[1] = recordsList.get(i).getEntryDate();
                    row[2] = recordsList.get(i).getNotes();
                    row[3] = recordsList.get(i).getUser();
                    dtm.addRow(row);
                }

                for (int i = 0; i < dtm.getRowCount(); i++) {
                    cashEntriesTotal += (double) dtm.getValueAt(i, 0);
                }

                lbl_entry_records.setText("Cash Entries Records on Week " + weekYear + " !");
                lbl_entry_records.setForeground(Color.black);
                lbl_cash_entries_total.setText("Cash Entries Total ............. ???" + String.valueOf(cashEntriesTotal));
                lbl_cash_entries_total.setVisible(true);
            } else {
                lbl_entry_records.setText("No Cash Entries on Week " + weekYear + " !");
                lbl_entry_records.setForeground(Color.red);
                lbl_cash_entries_total.setVisible(false);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CashEntries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_weekly_recordsActionPerformed

    private void txt_enter_valueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enter_valueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_valueActionPerformed

    private void txt_enter_valueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_valueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_valueKeyReleased

    private void txt_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_notesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_notesActionPerformed

    private void btn_register_entryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_register_entryActionPerformed
        // TODO add your handling code here:
        if (txt_enter_value.getText().trim().isEmpty() || txt_notes.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please check empty fields !", "Cash Entry", JOptionPane.ERROR_MESSAGE);
        } else {
            double value = Double.parseDouble(txt_enter_value.getText());
            String notes = txt_notes.getText();

            Date date = new Date();
            Timestamp currentDateTime = new Timestamp(date.getTime());

            int confirmEntryRegistering = JOptionPane.showConfirmDialog(this, "Confirm Entry of ???" + value + " ?", "Cash Entry", JOptionPane.YES_NO_OPTION);

            if (confirmEntryRegistering == 0) {
                try {
                    dbConnection();
                    String query = "INSERT INTO cashEntry (value, entryDate, notes, user) VALUES(?, ?, ?, ?)";
                    ps = con.prepareStatement(query);
                    ps.setDouble(1, value);
                    ps.setTimestamp(2, currentDateTime);
                    ps.setString(3, notes);
                    ps.setString(4, Login.fullName);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Cash entry registered successfully !");
                    txt_enter_value.setText("");
                    txt_notes.setText("");

                    loadRecordsOfTheDay();

                } catch (SQLException ex) {
                    Logger.getLogger(CashEntries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_register_entryActionPerformed

    private void btn_print_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_recordsActionPerformed
        // TODO add your handling code here:
        // Get date from calendar
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("CashEntryRecords" + tillClosingDate);
        PageFormat format = printerJob.getPageFormat(null);

        printerJob.setPrintable(new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D) graphics;
                panel_cash_records.paint(graphics2D);

                return Printable.PAGE_EXISTS;
            }
        }, format);

        boolean returningResult = printerJob.printDialog();

        if (returningResult) {
            try {

                printerJob.print();

                JOptionPane.showMessageDialog(this, "Entry Records Printed Successfully", "Cash Entry", JOptionPane.INFORMATION_MESSAGE);

            } catch (PrinterException ex) {
                Logger.getLogger(PrintFullReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_print_recordsActionPerformed

    private void table_view_cash_entry_recordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_cash_entry_recordsMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_table_view_cash_entry_recordsMouseClicked

    private void date_pickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_date_pickerMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_date_pickerMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_daily_records;
    private javax.swing.JButton btn_monthly_records;
    private javax.swing.JButton btn_print_records;
    private javax.swing.JButton btn_register_entry;
    private javax.swing.JButton btn_weekly_records;
    private com.toedter.calendar.JCalendar date_picker;
    private javax.swing.JDesktopPane desktop_pane_entries;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_cash_entries_total;
    private javax.swing.JLabel lbl_enter_value;
    private javax.swing.JLabel lbl_entry_records;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_notes;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_cash_record;
    private javax.swing.JPanel panel_cash_records;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_table_view;
    private javax.swing.JPanel panel_till_closing;
    private javax.swing.JScrollPane scroll_pane_orders_sales;
    private javax.swing.JScrollPane scroll_pane_table_cash_entry;
    private javax.swing.JTable table_view_cash_entry_records;
    private javax.swing.JTextField txt_enter_value;
    private javax.swing.JTextField txt_notes;
    // End of variables declaration//GEN-END:variables
}
