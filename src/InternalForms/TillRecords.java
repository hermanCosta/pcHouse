/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.PrintFullReport;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author HermanCosta
 */
public class TillRecords extends javax.swing.JInternalFrame {

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public TillRecords() {
        initComponents();

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        loadTillRecordsFromDb();
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

    public void loadTillRecordsFromDb() {
        // TODO add your handling code here:
        String dateFormat = "";

        Date date = date_picker.getDate();
        //String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
        String startDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date.getTime());
        String endDate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date.getTime());
        
        try {
            dbConnection();
            
            String query = "SELECT * FROM tillClosing WHERE date >= ? AND date <= ?";
            ps = con.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            rs = ps.executeQuery();
            
            if (rs.isBeforeFirst())
            {
                while (rs.next()) {
                    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("date"));
                    lbl_till_closed_on.setText("Till Closed on " + dateFormat);
                    lbl_cashier.setText("Cashier " + rs.getString("cashier"));
                    txt_cash_total.setText("€" + String.valueOf(rs.getDouble("cashTotal")));
                    txt_card_total.setText("€" + String.valueOf(rs.getDouble("cardTotal")));
                    txt_entries_total.setText("€" + String.valueOf(rs.getDouble("entriesTotal")));
                    txt_cash_in_total.setText("€" + String.valueOf(rs.getDouble("cashInTotal")));
                    txt_payments.setText("€" + String.valueOf(rs.getDouble("payments")));
                    txt_takes.setText("€" + String.valueOf(rs.getDouble("takes")));
                    txt_other.setText("€" + String.valueOf(rs.getDouble("other")));
                    txt_cash_out_total.setText("€" + String.valueOf(rs.getDouble("cashOutTotal")));
                    txt_till_total.setText("€" + String.valueOf(rs.getDouble("tillTotal")));
                    txt_enter_cash_total.setText("€" + String.valueOf(rs.getDouble("enterCashTotal")));
                    txt_enter_card_total.setText("€" + String.valueOf(rs.getDouble("entercardTotal")));
                    txt_adjustments.setText("€" + String.valueOf(rs.getDouble("adjustments")));
                    txt_balance.setText("€" + String.valueOf(rs.getDouble("balance")));
                    editor_pane_notes.setText(rs.getString("notes"));
                }
            } else {
                dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(date.getTime());
                lbl_till_closed_on.setText("No Till Records on " + dateFormat);
                lbl_cashier.setText("Cashier ");
                clearFields();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TillRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clearFields()
    {
        txt_cash_total.setText("");
        txt_card_total.setText("");
        txt_entries_total.setText("");
        txt_cash_in_total.setText("");
        txt_payments.setText("");
        txt_takes.setText("");
        txt_other.setText("");
        txt_cash_out_total.setText("");
        txt_till_total.setText("");
        txt_enter_cash_total.setText("");
        txt_enter_card_total.setText("");
        txt_adjustments.setText("");
        txt_balance.setText("");
        editor_pane_notes.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_entries = new javax.swing.JDesktopPane();
        panel_till_closing = new javax.swing.JPanel();
        btn_monthly_records = new javax.swing.JButton();
        date_picker = new com.toedter.calendar.JCalendar();
        btn_daily_records = new javax.swing.JButton();
        btn_weekly_records = new javax.swing.JButton();
        btn_print_records = new javax.swing.JButton();
        panel_till_closing_records = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();
        scroll_pane_orders_sales = new javax.swing.JScrollPane();
        panel_till_records = new javax.swing.JPanel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
        panel_records_details = new javax.swing.JPanel();
        panel_cash_out = new javax.swing.JPanel();
        lbl_cash_out_total = new javax.swing.JLabel();
        lbl_payments = new javax.swing.JLabel();
        lbl_takes = new javax.swing.JLabel();
        txt_payments = new javax.swing.JTextField();
        txt_takes = new javax.swing.JTextField();
        txt_cash_out_total = new javax.swing.JTextField();
        lbl_other = new javax.swing.JLabel();
        txt_other = new javax.swing.JTextField();
        panel_totals = new javax.swing.JPanel();
        lbl_enter_card_total = new javax.swing.JLabel();
        lbl_till_total = new javax.swing.JLabel();
        lbl_enter_cash_total = new javax.swing.JLabel();
        txt_till_total = new javax.swing.JTextField();
        txt_enter_cash_total = new javax.swing.JTextField();
        txt_enter_card_total = new javax.swing.JTextField();
        lbl_balance = new javax.swing.JLabel();
        jScrollPane_notes = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();
        txt_balance = new javax.swing.JTextField();
        lbl_adjustments = new javax.swing.JLabel();
        txt_adjustments = new javax.swing.JTextField();
        lbl_cashier = new javax.swing.JLabel();
        panel_cash_in = new javax.swing.JPanel();
        lbl_cash_in_total = new javax.swing.JLabel();
        lbl_cash_total = new javax.swing.JLabel();
        lbl_card_total = new javax.swing.JLabel();
        txt_cash_total = new javax.swing.JTextField();
        txt_card_total = new javax.swing.JTextField();
        txt_cash_in_total = new javax.swing.JTextField();
        lbl_card_total1 = new javax.swing.JLabel();
        txt_entries_total = new javax.swing.JTextField();
        lbl_till_closed_on = new javax.swing.JLabel();

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

        panel_till_closing_records.setBackground(new java.awt.Color(204, 204, 204));

        lbl_order_print_view.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
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

        scroll_pane_orders_sales.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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

        panel_records_details.setBackground(new java.awt.Color(255, 255, 255));
        panel_records_details.setBorder(null);

        panel_cash_out.setBackground(new java.awt.Color(255, 255, 255));
        panel_cash_out.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cash out", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        lbl_cash_out_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_out_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_cash_out_total.setText("Cash out Total");

        lbl_payments.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_payments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_payments.png"))); // NOI18N
        lbl_payments.setText("Payments");

        lbl_takes.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_takes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_takes.png"))); // NOI18N
        lbl_takes.setText("Takes");

        txt_payments.setEditable(false);
        txt_payments.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_payments.setFocusable(false);
        txt_payments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_paymentsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_paymentsKeyReleased(evt);
            }
        });

        txt_takes.setEditable(false);
        txt_takes.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_takes.setFocusable(false);
        txt_takes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_takesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_takesKeyReleased(evt);
            }
        });

        txt_cash_out_total.setEditable(false);
        txt_cash_out_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_cash_out_total.setFocusable(false);

        lbl_other.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_other.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_other.png"))); // NOI18N
        lbl_other.setText("Other    ");

        txt_other.setEditable(false);
        txt_other.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_other.setFocusable(false);
        txt_other.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_otherKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_otherKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_cash_outLayout = new javax.swing.GroupLayout(panel_cash_out);
        panel_cash_out.setLayout(panel_cash_outLayout);
        panel_cash_outLayout.setHorizontalGroup(
            panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_outLayout.createSequentialGroup()
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_takes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_other, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_cash_outLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_cash_out_total))
                    .addComponent(lbl_payments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_takes, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_cash_outLayout.createSequentialGroup()
                        .addComponent(txt_payments, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txt_cash_out_total, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_other)))
        );
        panel_cash_outLayout.setVerticalGroup(
            panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_outLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_payments, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_payments, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_takes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_takes, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_other, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_other, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_cash_out_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cash_out_total, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_totals.setBackground(new java.awt.Color(255, 255, 255));
        panel_totals.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Totals", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        lbl_enter_card_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_enter_card_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_card_total.png"))); // NOI18N
        lbl_enter_card_total.setText("Enter Card Total ");

        lbl_till_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_till_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_till_total.setText("Till Total            ");

        lbl_enter_cash_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_enter_cash_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_total.png"))); // NOI18N
        lbl_enter_cash_total.setText("Enter Cash Total ");

        txt_till_total.setEditable(false);
        txt_till_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_till_total.setFocusable(false);

        txt_enter_cash_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_enter_cash_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_enter_cash_totalActionPerformed(evt);
            }
        });
        txt_enter_cash_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_enter_cash_totalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_enter_cash_totalKeyReleased(evt);
            }
        });

        txt_enter_card_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_enter_card_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_enter_card_totalActionPerformed(evt);
            }
        });
        txt_enter_card_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_enter_card_totalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_enter_card_totalKeyReleased(evt);
            }
        });

        lbl_balance.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_balance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_balance.png"))); // NOI18N
        lbl_balance.setText("Balance   ");

        jScrollPane_notes.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane_notes.setBorder(null);
        jScrollPane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_notes.setBackground(new java.awt.Color(255, 255, 255));
        editor_pane_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N
        editor_pane_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        editor_pane_notes.setFocusCycleRoot(false);
        editor_pane_notes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editor_pane_notesKeyPressed(evt);
            }
        });
        jScrollPane_notes.setViewportView(editor_pane_notes);

        txt_balance.setEditable(false);
        txt_balance.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_balance.setFocusable(false);
        txt_balance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_balanceActionPerformed(evt);
            }
        });

        lbl_adjustments.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_adjustments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_adjustment.png"))); // NOI18N
        lbl_adjustments.setText("Adjusments       ");

        txt_adjustments.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_adjustments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_adjustmentsActionPerformed(evt);
            }
        });
        txt_adjustments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_adjustmentsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_adjustmentsKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_totalsLayout = new javax.swing.GroupLayout(panel_totals);
        panel_totals.setLayout(panel_totalsLayout);
        panel_totalsLayout.setHorizontalGroup(
            panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_totalsLayout.createSequentialGroup()
                .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_till_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lbl_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_adjustments, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_enter_card_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lbl_enter_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_adjustments)
                    .addComponent(txt_balance)
                    .addComponent(txt_enter_cash_total)
                    .addComponent(txt_enter_card_total)
                    .addComponent(txt_till_total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        panel_totalsLayout.setVerticalGroup(
            panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_totalsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane_notes)
                    .addGroup(panel_totalsLayout.createSequentialGroup()
                        .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_till_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_till_total, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_enter_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_enter_cash_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_enter_card_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_enter_card_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_adjustments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_adjustments, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_balance)
                            .addComponent(txt_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        lbl_cashier.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        lbl_cashier.setText("dateCashier");

        panel_cash_in.setBackground(new java.awt.Color(255, 255, 255));
        panel_cash_in.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cash in", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        lbl_cash_in_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_in_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_cash_in_total.setText("Cash in Total");

        lbl_cash_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_total.png"))); // NOI18N
        lbl_cash_total.setText("Cash Total");

        lbl_card_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_card_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_card_total.png"))); // NOI18N
        lbl_card_total.setText("Card Total  ");

        txt_cash_total.setEditable(false);
        txt_cash_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_cash_total.setFocusable(false);

        txt_card_total.setEditable(false);
        txt_card_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_card_total.setFocusable(false);

        txt_cash_in_total.setEditable(false);
        txt_cash_in_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_cash_in_total.setFocusable(false);

        lbl_card_total1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_card_total1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_entries_black.png"))); // NOI18N
        lbl_card_total1.setText("Entries Total ");

        txt_entries_total.setEditable(false);
        txt_entries_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_entries_total.setFocusable(false);

        javax.swing.GroupLayout panel_cash_inLayout = new javax.swing.GroupLayout(panel_cash_in);
        panel_cash_in.setLayout(panel_cash_inLayout);
        panel_cash_inLayout.setHorizontalGroup(
            panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_inLayout.createSequentialGroup()
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbl_card_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cash_in_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_card_total1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_entries_total, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(txt_cash_in_total, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cash_total)
                    .addComponent(txt_card_total, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, 0))
        );
        panel_cash_inLayout.setVerticalGroup(
            panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_inLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cash_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cash_total, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_card_total)
                    .addComponent(txt_card_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_card_total1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_entries_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cash_in_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cash_in_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_records_detailsLayout = new javax.swing.GroupLayout(panel_records_details);
        panel_records_details.setLayout(panel_records_detailsLayout);
        panel_records_detailsLayout.setHorizontalGroup(
            panel_records_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_records_detailsLayout.createSequentialGroup()
                .addGroup(panel_records_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_records_detailsLayout.createSequentialGroup()
                        .addGroup(panel_records_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_records_detailsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_cashier))
                            .addGroup(panel_records_detailsLayout.createSequentialGroup()
                                .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panel_cash_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panel_totals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_records_detailsLayout.setVerticalGroup(
            panel_records_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_records_detailsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_cashier)
                .addGap(18, 18, 18)
                .addGroup(panel_records_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cash_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_totals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        lbl_till_closed_on.setFont(new java.awt.Font("sansserif", 1, 17)); // NOI18N
        lbl_till_closed_on.setText("tillClosedOn");

        javax.swing.GroupLayout panel_till_recordsLayout = new javax.swing.GroupLayout(panel_till_records);
        panel_till_records.setLayout(panel_till_recordsLayout);
        panel_till_recordsLayout.setHorizontalGroup(
            panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_recordsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_till_closed_on)
                .addGap(149, 149, 149))
            .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_till_recordsLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(panel_records_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );
        panel_till_recordsLayout.setVerticalGroup(
            panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_till_closed_on)
                .addContainerGap())
            .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_till_recordsLayout.createSequentialGroup()
                    .addGap(169, 169, 169)
                    .addComponent(panel_records_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(147, Short.MAX_VALUE)))
        );

        scroll_pane_orders_sales.setViewportView(panel_till_records);

        javax.swing.GroupLayout panel_till_closingLayout = new javax.swing.GroupLayout(panel_till_closing);
        panel_till_closing.setLayout(panel_till_closingLayout);
        panel_till_closingLayout.setHorizontalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closingLayout.createSequentialGroup()
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(panel_till_closing_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_till_closingLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(date_picker, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                                .addGap(11, 11, 11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_closingLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_weekly_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_daily_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_monthly_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_print_records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(scroll_pane_orders_sales)))
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
                    .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btn_monthly_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_monthly_recordsActionPerformed
    }//GEN-LAST:event_btn_monthly_recordsActionPerformed

    private void date_pickerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_pickerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_date_pickerPropertyChange

    private void btn_daily_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_daily_recordsActionPerformed
        // TODO add your handling code here:
        
        loadTillRecordsFromDb();
    }//GEN-LAST:event_btn_daily_recordsActionPerformed

    private void btn_weekly_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_weekly_recordsActionPerformed
        // TODO add your handling code here:
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

    private void txt_paymentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_paymentsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_paymentsKeyPressed

    private void txt_paymentsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_paymentsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_paymentsKeyReleased

    private void txt_takesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_takesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_takesKeyPressed

    private void txt_takesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_takesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_takesKeyReleased

    private void txt_otherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_otherKeyPressed

    private void txt_otherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_otherKeyReleased

    private void txt_enter_cash_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enter_cash_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_cash_totalActionPerformed

    private void txt_enter_cash_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_cash_totalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_cash_totalKeyPressed

    private void txt_enter_cash_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_cash_totalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_cash_totalKeyReleased

    private void txt_enter_card_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enter_card_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_card_totalActionPerformed

    private void txt_enter_card_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_card_totalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_card_totalKeyPressed

    private void txt_enter_card_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_card_totalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_card_totalKeyReleased

    private void editor_pane_notesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editor_pane_notesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_editor_pane_notesKeyPressed

    private void txt_balanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_balanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_balanceActionPerformed

    private void txt_adjustmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_adjustmentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adjustmentsActionPerformed

    private void txt_adjustmentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_adjustmentsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adjustmentsKeyPressed

    private void txt_adjustmentsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_adjustmentsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adjustmentsKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_daily_records;
    private javax.swing.JButton btn_monthly_records;
    private javax.swing.JButton btn_print_records;
    private javax.swing.JButton btn_weekly_records;
    private com.toedter.calendar.JCalendar date_picker;
    private javax.swing.JDesktopPane desktop_pane_entries;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JScrollPane jScrollPane_notes;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_adjustments;
    private javax.swing.JLabel lbl_balance;
    private javax.swing.JLabel lbl_card_total;
    private javax.swing.JLabel lbl_card_total1;
    private javax.swing.JLabel lbl_cash_in_total;
    private javax.swing.JLabel lbl_cash_out_total;
    private javax.swing.JLabel lbl_cash_total;
    private javax.swing.JLabel lbl_cashier;
    private javax.swing.JLabel lbl_enter_card_total;
    private javax.swing.JLabel lbl_enter_cash_total;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_other;
    private javax.swing.JLabel lbl_payments;
    private javax.swing.JLabel lbl_takes;
    private javax.swing.JLabel lbl_till_closed_on;
    private javax.swing.JLabel lbl_till_total;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_cash_in;
    private javax.swing.JPanel panel_cash_out;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_records_details;
    private javax.swing.JPanel panel_till_closing;
    private javax.swing.JPanel panel_till_closing_records;
    private javax.swing.JPanel panel_till_records;
    private javax.swing.JPanel panel_totals;
    private javax.swing.JScrollPane scroll_pane_orders_sales;
    private javax.swing.JTextField txt_adjustments;
    private javax.swing.JTextField txt_balance;
    private javax.swing.JTextField txt_card_total;
    private javax.swing.JTextField txt_cash_in_total;
    private javax.swing.JTextField txt_cash_out_total;
    private javax.swing.JTextField txt_cash_total;
    private javax.swing.JTextField txt_enter_card_total;
    private javax.swing.JTextField txt_enter_cash_total;
    private javax.swing.JTextField txt_entries_total;
    private javax.swing.JTextField txt_other;
    private javax.swing.JTextField txt_payments;
    private javax.swing.JTextField txt_takes;
    private javax.swing.JTextField txt_till_total;
    // End of variables declaration//GEN-END:variables
}
