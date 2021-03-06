/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HermanCosta
 */
public class PrintTillRecord extends javax.swing.JFrame {

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    String tillClosingDate, cashier, tillOpeningDate, notes;
    double cashTotal, cardTotal, entriesTotal, cashInTotal,
            enterCashTotal, enterCardTotal, adjustments, balance,
            cashEntryTotal, payments, takes, other, tillTotal, cashOutTotal;

    public PrintTillRecord() {
        initComponents();
    }

    public PrintTillRecord(String _tillClosingDate, String _cashier, String _tillOpeningDate, double _cashTotal, double _cardTotal,
            double _entriesTotal, double _cashInTotal, double _payments, double _takes, double _other,
            double _cashOutTotal, double _tillTotal, double _enterCashTotal, double _enterCardTotal,
            double _adjustments, double _balance, String _notes) {
        initComponents();
        setResizable(false);

        this.tillClosingDate = _tillClosingDate;
        this.cashier = _cashier;
        this.tillOpeningDate = _tillOpeningDate;
        this.cashTotal = _cashTotal;
        this.cardTotal = _cardTotal;
        this.entriesTotal = _entriesTotal;
        this.cashInTotal = _cashInTotal;
        this.payments = _payments;
        this.takes = _takes;
        this.other = _other;
        this.cashOutTotal = _cashOutTotal;
        this.tillTotal = _tillTotal;
        this.enterCashTotal = _enterCashTotal;
        this.enterCardTotal = _enterCardTotal;
        this.adjustments = _adjustments;
        this.balance = _balance;
        this.notes = _notes;

        loadTillRecordsFromDb();
    }

    public void loadTillRecordsFromDb() {
        // TODO add your handling code here:
        lbl_till_opening_date.setText("Till Opening on " + tillOpeningDate);
        lbl_cashier.setText("Cashier: " + cashier);
        lbl_till_closing_date.setText("Till Closing Date: " + tillClosingDate);
        lbl_cash_total.setText("Cash Total........................ ???" + String.valueOf(cashTotal));
        lbl_card_total.setText("Card Total........................ ???" + String.valueOf(cardTotal));
        lbl_entries_total.setText("Entries Total..................... ???" + String.valueOf(entriesTotal));
        lbl_cash_in_total.setText("Cash In Total.................... ???" + String.valueOf(cashInTotal));
        lbl_payments.setText("Payments........................... ???" + String.valueOf(payments));
        lbl_takes.setText("Takes................................. ???" + String.valueOf(takes));
        lbl_other.setText("Other................................. ???" + String.valueOf(other));
        lbl_cash_out_total.setText("Cash Out Total................... ???" + String.valueOf(cashOutTotal));
        lbl_till_total.setText("Till Total.................................................................................... ???" + String.valueOf(tillTotal));
        lbl_enter_cash_total.setText("Entered Cash Total...................................................................... ???" + String.valueOf(enterCashTotal));
        lbl_enter_card_total.setText("Entered Card Total...................................................................... ???" + String.valueOf(enterCardTotal));
        lbl_adjustments.setText("Adjustments............................................................................... ???" + String.valueOf(adjustments));
        lbl_balance.setText("Balance............................................................................................................. ???" + String.valueOf(balance));

        if (notes.trim().isEmpty()) {
            lbl_notes.setVisible(false);
        } else {
            lbl_notes.setVisible(true);
            editor_pane_notes.setText(notes);
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

        panel_print_view = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        scroll_pane_till_closing = new javax.swing.JScrollPane();
        panel_till_records = new javax.swing.JPanel();
        lbl_till_opening_date = new javax.swing.JLabel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        lbl_address = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        panel_cash_out = new javax.swing.JPanel();
        lbl_cash_out_total = new javax.swing.JLabel();
        lbl_payments = new javax.swing.JLabel();
        lbl_takes = new javax.swing.JLabel();
        lbl_other = new javax.swing.JLabel();
        panel_cash_in = new javax.swing.JPanel();
        lbl_cash_in_total = new javax.swing.JLabel();
        lbl_cash_total = new javax.swing.JLabel();
        lbl_card_total = new javax.swing.JLabel();
        lbl_entries_total = new javax.swing.JLabel();
        panel_totals = new javax.swing.JPanel();
        lbl_enter_card_total = new javax.swing.JLabel();
        lbl_till_total = new javax.swing.JLabel();
        lbl_enter_cash_total = new javax.swing.JLabel();
        lbl_balance = new javax.swing.JLabel();
        lbl_adjustments = new javax.swing.JLabel();
        lbl_cashier = new javax.swing.JLabel();
        lbl_cash_in_details = new javax.swing.JLabel();
        lbl_cash_out_details = new javax.swing.JLabel();
        scroll_pane_notes = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();
        lbl_totals = new javax.swing.JLabel();
        lbl_notes = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_till_closing_date = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(660, 700));

        panel_print_view.setBackground(new java.awt.Color(204, 204, 204));

        lbl_order_print_view.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_print_view.setText("Till Closing Print View");

        javax.swing.GroupLayout panel_print_viewLayout = new javax.swing.GroupLayout(panel_print_view);
        panel_print_view.setLayout(panel_print_viewLayout);
        panel_print_viewLayout.setHorizontalGroup(
            panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_viewLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(lbl_order_print_view)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_print_viewLayout.setVerticalGroup(
            panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_print_viewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_order_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        btn_print.setBackground(new java.awt.Color(21, 76, 121));
        btn_print.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        panel_till_records.setBackground(new java.awt.Color(255, 255, 255));
        panel_till_records.setPreferredSize(new java.awt.Dimension(595, 842));

        lbl_till_opening_date.setFont(new java.awt.Font("sansserif", 1, 17)); // NOI18N
        lbl_till_opening_date.setText("tillOpeningDate");

        panel_header.setBackground(new java.awt.Color(255, 255, 255));

        lbl_logo_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_land_line_number.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_phone_number.png"))); // NOI18N
        lbl_land_line_number.setText("+353 (01) 563-9520");

        lbl_mobile_number.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_mobile_number.png"))); // NOI18N
        lbl_mobile_number.setText("+353 (83) 012-8190");

        lbl_address.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_address.png"))); // NOI18N
        lbl_address.setText("12A, Frederick Street North, Dublin 1");

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(line_header)
                .addContainerGap())
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_mobile_number, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_land_line_number, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_address, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(lbl_address)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel_cash_out.setBackground(new java.awt.Color(255, 255, 255));
        panel_cash_out.setBorder(null);

        lbl_cash_out_total.setFont(new java.awt.Font("Lucida Grande", 3, 15)); // NOI18N
        lbl_cash_out_total.setText("cashOutTotal");

        lbl_payments.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_payments.setText("payments");

        lbl_takes.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_takes.setText("takes");

        lbl_other.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_other.setText("other");

        javax.swing.GroupLayout panel_cash_outLayout = new javax.swing.GroupLayout(panel_cash_out);
        panel_cash_out.setLayout(panel_cash_outLayout);
        panel_cash_outLayout.setHorizontalGroup(
            panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_outLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_cash_out_total, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(lbl_other, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_takes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_payments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_cash_outLayout.setVerticalGroup(
            panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_outLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lbl_payments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_takes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_other)
                .addGap(18, 18, 18)
                .addComponent(lbl_cash_out_total)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_cash_in.setBackground(new java.awt.Color(255, 255, 255));
        panel_cash_in.setBorder(null);

        lbl_cash_in_total.setFont(new java.awt.Font("Lucida Grande", 3, 15)); // NOI18N
        lbl_cash_in_total.setText("cashInTotal");

        lbl_cash_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_total.setText("cashTotal");

        lbl_card_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_card_total.setText("cardTotal");

        lbl_entries_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_entries_total.setText("entriesTotal");

        javax.swing.GroupLayout panel_cash_inLayout = new javax.swing.GroupLayout(panel_cash_in);
        panel_cash_in.setLayout(panel_cash_inLayout);
        panel_cash_inLayout.setHorizontalGroup(
            panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_inLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_cash_in_total, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(lbl_entries_total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_card_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cash_total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_cash_inLayout.setVerticalGroup(
            panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_inLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lbl_cash_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_card_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_entries_total)
                .addGap(18, 18, 18)
                .addComponent(lbl_cash_in_total)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        panel_totals.setBackground(new java.awt.Color(255, 255, 255));
        panel_totals.setBorder(null);

        lbl_enter_card_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_enter_card_total.setText("enterCardTotal");

        lbl_till_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_till_total.setText("tillTotal");

        lbl_enter_cash_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_enter_cash_total.setText("enterCashTotal");

        lbl_balance.setFont(new java.awt.Font("Lucida Grande", 3, 15)); // NOI18N
        lbl_balance.setText("balance");

        lbl_adjustments.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_adjustments.setText("adjustments");

        javax.swing.GroupLayout panel_totalsLayout = new javax.swing.GroupLayout(panel_totals);
        panel_totals.setLayout(panel_totalsLayout);
        panel_totalsLayout.setHorizontalGroup(
            panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_totalsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_balance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_till_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_enter_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_enter_card_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_adjustments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_totalsLayout.setVerticalGroup(
            panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_totalsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lbl_till_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_enter_cash_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_enter_card_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_adjustments)
                .addGap(18, 18, 18)
                .addComponent(lbl_balance)
                .addContainerGap())
        );

        lbl_cashier.setText("cashier");

        lbl_cash_in_details.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        lbl_cash_in_details.setText("Cash In Details");

        lbl_cash_out_details.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        lbl_cash_out_details.setText("Cash Out Details");

        scroll_pane_notes.setBackground(new java.awt.Color(255, 255, 255));
        scroll_pane_notes.setBorder(null);
        scroll_pane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_pane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll_pane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_notes.setEditable(false);
        editor_pane_notes.setBackground(new java.awt.Color(255, 255, 255));
        editor_pane_notes.setBorder(null);
        editor_pane_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        editor_pane_notes.setFocusCycleRoot(false);
        editor_pane_notes.setPreferredSize(new java.awt.Dimension(570, 165));
        editor_pane_notes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editor_pane_notesKeyPressed(evt);
            }
        });
        scroll_pane_notes.setViewportView(editor_pane_notes);

        lbl_totals.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        lbl_totals.setText("Totals");

        lbl_notes.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        lbl_notes.setText("Notes");

        lbl_till_closing_date.setText("tillClosingDate");
        lbl_till_closing_date.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout panel_till_recordsLayout = new javax.swing.GroupLayout(panel_till_records);
        panel_till_records.setLayout(panel_till_recordsLayout);
        panel_till_recordsLayout.setHorizontalGroup(
            panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_recordsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_till_opening_date)
                .addGap(151, 151, 151))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_till_recordsLayout.createSequentialGroup()
                .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_till_recordsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_till_recordsLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel_totals, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scroll_pane_notes)
                            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                                .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_notes)
                                    .addComponent(lbl_totals)
                                    .addGroup(panel_till_recordsLayout.createSequentialGroup()
                                        .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_cash_in_details))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_cash_out_details)
                                            .addComponent(panel_cash_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                                .addComponent(lbl_cashier)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_till_closing_date, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))))
                .addGap(21, 21, 21))
        );
        panel_till_recordsLayout.setVerticalGroup(
            panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_recordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_till_opening_date)
                .addGap(30, 30, 30)
                .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_cashier)
                    .addComponent(lbl_till_closing_date))
                .addGap(30, 30, 30)
                .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_cash_out_details)
                    .addComponent(lbl_cash_in_details))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cash_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbl_totals)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_totals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbl_notes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scroll_pane_till_closing.setViewportView(panel_till_records);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scroll_pane_till_closing, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane_till_closing, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void editor_pane_notesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editor_pane_notesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_editor_pane_notesKeyPressed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        // Get date from calendar
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
                this.dispose();

            } catch (PrinterException ex) {
                Logger.getLogger(PrintFullReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbl_address;
    private javax.swing.JLabel lbl_adjustments;
    private javax.swing.JLabel lbl_balance;
    private javax.swing.JLabel lbl_card_total;
    private javax.swing.JLabel lbl_cash_in_details;
    private javax.swing.JLabel lbl_cash_in_total;
    private javax.swing.JLabel lbl_cash_out_details;
    private javax.swing.JLabel lbl_cash_out_total;
    private javax.swing.JLabel lbl_cash_total;
    private javax.swing.JLabel lbl_cashier;
    private javax.swing.JLabel lbl_enter_card_total;
    private javax.swing.JLabel lbl_enter_cash_total;
    private javax.swing.JLabel lbl_entries_total;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_notes;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_other;
    private javax.swing.JLabel lbl_payments;
    private javax.swing.JLabel lbl_takes;
    private javax.swing.JLabel lbl_till_closing_date;
    private javax.swing.JLabel lbl_till_opening_date;
    private javax.swing.JLabel lbl_till_total;
    private javax.swing.JLabel lbl_totals;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_cash_in;
    private javax.swing.JPanel panel_cash_out;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_print_view;
    private javax.swing.JPanel panel_till_records;
    private javax.swing.JPanel panel_totals;
    private javax.swing.JScrollPane scroll_pane_notes;
    private javax.swing.JScrollPane scroll_pane_till_closing;
    // End of variables declaration//GEN-END:variables
}
