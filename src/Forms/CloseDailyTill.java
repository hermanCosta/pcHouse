/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.TillClosing;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author HermanCosta
 */
public class CloseDailyTill extends javax.swing.JFrame {

    PreparedStatement ps;
    Connection con;
    ResultSet rs;
    String dateFormat, tillClosingDate;
    double cashTotal, cardTotal, totalCashIn, enterCardTotal, enterCashTotal, adjustments, balance, cashEntryTotal;
    double payments, takes, other, tillTotal, totalCashOut;
    Date date;
    
    public CloseDailyTill() {
        initComponents();
    }

    public CloseDailyTill(double _cashTotal, double _cardTotal) {
        initComponents();
        this.cashTotal = _cashTotal;
        this.cardTotal = _cardTotal;
        
        date = new Date();
        tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        
        SwingUtilities.invokeLater(() -> {
            txt_enter_cash_total.requestFocus();
        });
        
        
        lbl_till_closing_on.setText("Till Closing on: " + tillClosingDate);
        lbl_date_cashier.setText("Cashier: " + Login.fullName);
        txt_cash_total.setText(String.valueOf(cashTotal));
        txt_card_total.setText(String.valueOf(cardTotal));
        
        loadEntriesTotal();
        loadCashOut();
        
        tillTotal = totalCashIn - totalCashOut;
        txt_till_total.setText(String.valueOf(tillTotal));
        txt_balance.setText(String.valueOf(tillTotal *= -1));
    }
    
    public void loadEntriesTotal()
    {
        
        
        try {
            dbConnection();
            
            String query = "SELECT SUM(value) FROM cashEntry WHERE entryDate >= ?";
            ps = con.prepareStatement(query);
            ps.setString(1, dateFormat);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                cashEntryTotal = rs.getDouble("SUM(value)");
                txt_entries_total.setText(String.valueOf(cashEntryTotal));
            }
            
            totalCashIn = cashTotal + cardTotal + cashEntryTotal;
            txt_cash_in_total.setText(String.valueOf(totalCashIn));
        } catch (SQLException ex) {
            Logger.getLogger(CloseDailyTill.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadCashOut()
    {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
        
        try {
            dbConnection();
            
            String query = "SELECT "
                    + "SUM(CASE WHEN type='Payments' AND outDate >= ? THEN value END) AS 'payments', "
                    + "SUM(CASE WHEN type='Takes' AND outDate >= ? THEN value END) AS 'takes', "
                    + "SUM(CASE WHEN type='Other' AND outDate >= ? THEN value END) AS 'other' FROM cashOut";
            
            ps = con.prepareStatement(query);
            ps.setString(1, dateFormat);
            ps.setString(2, dateFormat);
            ps.setString(3, dateFormat);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                payments = rs.getDouble("payments");
                takes = rs.getDouble("takes");
                other = rs.getDouble("other");
                
                txt_payments.setText(String.valueOf(payments));
                txt_takes.setText(String.valueOf(takes));
                txt_other.setText(String.valueOf(other));
            }
            
            totalCashOut = payments + takes + other;
            txt_cash_out_total.setText(String.valueOf(totalCashOut));
        } catch (SQLException ex) {
            Logger.getLogger(CloseDailyTill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void calcTotalEntered()
    {
        tillTotal = Double.parseDouble(txt_till_total.getText());
        
        if (txt_enter_cash_total.getText().trim().isEmpty() && txt_adjustments.getText().trim().isEmpty() && !txt_enter_card_total.getText().trim().isEmpty()) {
            enterCardTotal = Double.parseDouble(txt_enter_card_total.getText());
            balance = tillTotal - enterCardTotal;
            txt_balance.setText(String.valueOf(balance));
        }
        
        else if (txt_enter_cash_total.getText().trim().isEmpty() && !txt_adjustments.getText().trim().isEmpty() && txt_enter_card_total.getText().trim().isEmpty()) {
            adjustments = Double.parseDouble(txt_adjustments.getText());
            balance = tillTotal - adjustments;
            txt_balance.setText(String.valueOf(balance));
        }
        
        else if (!txt_enter_cash_total.getText().trim().isEmpty() && txt_adjustments.getText().trim().isEmpty() && txt_enter_card_total.getText().trim().isEmpty()) {
            enterCashTotal = Double.parseDouble(txt_enter_cash_total.getText());
            balance = tillTotal - enterCashTotal;
            txt_balance.setText(String.valueOf(balance));
        }
        
        else if (txt_enter_cash_total.getText().trim().isEmpty() && !txt_adjustments.getText().trim().isEmpty() && !txt_enter_card_total.getText().trim().isEmpty()) {
            enterCardTotal = Double.parseDouble(txt_enter_card_total.getText());
            adjustments = Double.parseDouble(txt_adjustments.getText());
            balance = tillTotal - (enterCardTotal + adjustments);
            txt_balance.setText(String.valueOf(balance));
        }
        
        else if (!txt_enter_cash_total.getText().trim().isEmpty() && txt_adjustments.getText().trim().isEmpty() && !txt_enter_card_total.getText().trim().isEmpty()) {
            enterCashTotal = Double.parseDouble(txt_enter_cash_total.getText());
            enterCardTotal = Double.parseDouble(txt_enter_card_total.getText());
            balance = tillTotal - (enterCashTotal + enterCardTotal);
            txt_balance.setText(String.valueOf(balance));
        }
        
        else if (!txt_enter_cash_total.getText().trim().isEmpty() && !txt_adjustments.getText().trim().isEmpty() && txt_enter_card_total.getText().trim().isEmpty()) {
            enterCashTotal = Double.parseDouble(txt_enter_cash_total.getText());
            adjustments = Double.parseDouble(txt_adjustments.getText());
            balance = tillTotal - (enterCashTotal + adjustments);
            txt_balance.setText(String.valueOf(balance));
        }
        
        else if (!txt_enter_cash_total.getText().trim().isEmpty() && !txt_enter_card_total.getText().trim().isEmpty() && !txt_adjustments.getText().trim().isEmpty()) {
            enterCashTotal = Double.parseDouble(txt_enter_cash_total.getText());
            enterCardTotal = Double.parseDouble(txt_enter_card_total.getText());
            adjustments = Double.parseDouble(txt_adjustments.getText());
            balance = tillTotal - (enterCashTotal + enterCardTotal + adjustments);
            txt_balance.setText(String.valueOf(balance));
        }
        else
            balance = Double.parseDouble(txt_till_total.getText());
            txt_balance.setText(String.valueOf(balance *= -1));
        
        
        if (balance == 0) {
            txt_balance.setBackground(Color.green);
            txt_balance.setForeground(Color.black);
        } else {
            txt_balance.setBackground(Color.red);
            txt_balance.setForeground(Color.white);
        }
    }
    
    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
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

        panel_daily_closing_till = new javax.swing.JPanel();
        panel_cash_out = new javax.swing.JPanel();
        lbl_cash_out_total = new javax.swing.JLabel();
        lbl_payments = new javax.swing.JLabel();
        lbl_takes = new javax.swing.JLabel();
        txt_payments = new javax.swing.JTextField();
        txt_takes = new javax.swing.JTextField();
        txt_cash_out_total = new javax.swing.JTextField();
        lbl_other = new javax.swing.JLabel();
        txt_other = new javax.swing.JTextField();
        btn_money_counter = new javax.swing.JButton();
        lbl_till_closing_on = new javax.swing.JLabel();
        btn_cancel = new javax.swing.JButton();
        btn_sales = new javax.swing.JButton();
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
        lbl_date_cashier = new javax.swing.JLabel();
        panel_cash_in = new javax.swing.JPanel();
        lbl_cash_in_total = new javax.swing.JLabel();
        lbl_cash_total = new javax.swing.JLabel();
        lbl_card_total = new javax.swing.JLabel();
        txt_cash_total = new javax.swing.JTextField();
        txt_card_total = new javax.swing.JTextField();
        txt_cash_in_total = new javax.swing.JTextField();
        lbl_card_total1 = new javax.swing.JLabel();
        txt_entries_total = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_daily_closing_till.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        panel_cash_out.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cash out", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        lbl_cash_out_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_out_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_cash_out_total.setText("Cash out Total   €");

        lbl_payments.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_payments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_payments.png"))); // NOI18N
        lbl_payments.setText("Payments         €");

        lbl_takes.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_takes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_takes.png"))); // NOI18N
        lbl_takes.setText("Takes              €");

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
        lbl_other.setText("Other              €");

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
                    .addComponent(lbl_cash_out_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_cash_outLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_other, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_payments, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_takes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cash_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_takes, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(txt_cash_out_total, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_other, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_payments))
                .addContainerGap())
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

        btn_money_counter.setBackground(new java.awt.Color(21, 76, 121));
        btn_money_counter.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        btn_money_counter.setForeground(new java.awt.Color(255, 255, 255));
        btn_money_counter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_money_calc.png"))); // NOI18N
        btn_money_counter.setText("Money Counter");
        btn_money_counter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_money_counterActionPerformed(evt);
            }
        });

        lbl_till_closing_on.setFont(new java.awt.Font("sansserif", 1, 17)); // NOI18N
        lbl_till_closing_on.setText("tillClosingOn");

        btn_cancel.setBackground(new java.awt.Color(21, 76, 121));
        btn_cancel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_sales.setBackground(new java.awt.Color(21, 76, 121));
        btn_sales.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_sales.setForeground(new java.awt.Color(255, 255, 255));
        btn_sales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save.png"))); // NOI18N
        btn_sales.setText("Save");
        btn_sales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salesActionPerformed(evt);
            }
        });

        panel_totals.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Totals", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        lbl_enter_card_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_enter_card_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_card_total.png"))); // NOI18N
        lbl_enter_card_total.setText("Enter Card Total €");

        lbl_till_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_till_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_till_total.setText("Till Total            €");

        lbl_enter_cash_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_enter_cash_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_total.png"))); // NOI18N
        lbl_enter_cash_total.setText("Enter Cash Total €");

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
        lbl_balance.setText("Balance              €");

        jScrollPane_notes.setBorder(null);
        jScrollPane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

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
        txt_balance.setBackground(new java.awt.Color(255, 51, 51));
        txt_balance.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_balance.setForeground(new java.awt.Color(255, 255, 255));
        txt_balance.setFocusable(false);
        txt_balance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_balanceActionPerformed(evt);
            }
        });

        lbl_adjustments.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_adjustments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_adjustment.png"))); // NOI18N
        lbl_adjustments.setText("Adjusments       €");

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
                .addContainerGap()
                .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_adjustments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_enter_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_till_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_enter_card_total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_balance, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_totalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_adjustments, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(txt_enter_card_total)
                    .addComponent(txt_enter_cash_total)
                    .addComponent(txt_till_total)
                    .addComponent(txt_balance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
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
                            .addComponent(txt_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        lbl_date_cashier.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        lbl_date_cashier.setText("dateCashier");

        panel_cash_in.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cash in", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        lbl_cash_in_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_in_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_cash_in_total.setText("Cash in Total €");

        lbl_cash_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_cash_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_total.png"))); // NOI18N
        lbl_cash_total.setText("Cash Total   €");

        lbl_card_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_card_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_card_total.png"))); // NOI18N
        lbl_card_total.setText("Card Total  €");

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
        lbl_card_total1.setText("Entries Total €");

        txt_entries_total.setEditable(false);
        txt_entries_total.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_entries_total.setFocusable(false);

        javax.swing.GroupLayout panel_cash_inLayout = new javax.swing.GroupLayout(panel_cash_in);
        panel_cash_in.setLayout(panel_cash_inLayout);
        panel_cash_inLayout.setHorizontalGroup(
            panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cash_inLayout.createSequentialGroup()
                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cash_inLayout.createSequentialGroup()
                        .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_cash_inLayout.createSequentialGroup()
                                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_cash_inLayout.createSequentialGroup()
                                        .addComponent(lbl_cash_in_total)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(panel_cash_inLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(lbl_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(panel_cash_inLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_card_total, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_card_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(txt_cash_total, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cash_in_total)))
                    .addGroup(panel_cash_inLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_card_total1)
                        .addGap(4, 4, 4)
                        .addComponent(txt_entries_total, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                .addContainerGap())
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

        javax.swing.GroupLayout panel_daily_closing_tillLayout = new javax.swing.GroupLayout(panel_daily_closing_till);
        panel_daily_closing_till.setLayout(panel_daily_closing_tillLayout);
        panel_daily_closing_tillLayout.setHorizontalGroup(
            panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_daily_closing_tillLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_till_closing_on)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_daily_closing_tillLayout.createSequentialGroup()
                .addGroup(panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_daily_closing_tillLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbl_date_cashier))
                    .addGroup(panel_daily_closing_tillLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_daily_closing_tillLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_money_counter, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panel_totals, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel_daily_closing_tillLayout.createSequentialGroup()
                                    .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(panel_cash_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        panel_daily_closing_tillLayout.setVerticalGroup(
            panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_daily_closing_tillLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_till_closing_on)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_date_cashier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cash_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_totals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_daily_closing_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_money_counter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_daily_closing_till, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_daily_closing_till, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void editor_pane_notesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editor_pane_notesKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_editor_pane_notesKeyPressed

    private void btn_salesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salesActionPerformed
        // Gett date from calendar
        
        if (txt_enter_cash_total.getText().trim().isEmpty() && txt_enter_card_total.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(this, "Please check empty fields !", "Close Daily Till", JOptionPane.ERROR_MESSAGE);
        else if (balance != 0)
            JOptionPane.showMessageDialog(this, "Your Balance Must Match to Till Total, Please check !", "Close Daily Till",JOptionPane.ERROR_MESSAGE);
        else 
        {
            int confirmClosingTill = JOptionPane.showConfirmDialog(this, "Do you want to close till on " + tillClosingDate);
            if (confirmClosingTill == 0)
            {
                Timestamp currentDateTime = new Timestamp(date.getTime());
                enterCashTotal = Double.parseDouble(txt_enter_cash_total.getText());
                enterCardTotal = Double.parseDouble(txt_enter_card_total.getText());
                adjustments = Double.parseDouble(txt_adjustments.getText());
                String notes = editor_pane_notes.getText();
                
                try {
                    dbConnection();
                    String query = "INSERT INTO tillClosing (date, cashier, cashTotal, cardTotal, cashInTotal, payments, takes, "
                            + "other, cashOutTotal, tillTotal, enterCashTotal, enterCardTotal, adjustments, balance, notes) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    ps = con.prepareStatement(query);
                    ps.setTimestamp(1, currentDateTime);
                    ps.setString(2, Login.fullName);
                    ps.setDouble(3, cashTotal);
                    ps.setDouble(4, cardTotal);
                    ps.setDouble(5, totalCashIn);
                    ps.setDouble(6, payments);
                    ps.setDouble(7, takes);
                    ps.setDouble(8, other);
                    ps.setDouble(9, totalCashOut);
                    ps.setDouble(10, tillTotal);
                    ps.setDouble(11, enterCashTotal);
                    ps.setDouble(12, enterCardTotal);
                    ps.setDouble(13, adjustments);
                    ps.setDouble(14, balance);
                    ps.setString(15, notes);
                    ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(this, "Till Closed Successfully");
                } catch (SQLException ex) {
                    Logger.getLogger(CloseDailyTill.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_salesActionPerformed

    private void txt_balanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_balanceActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_balanceActionPerformed

    private void txt_enter_cash_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enter_cash_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enter_cash_totalActionPerformed

    private void txt_enter_cash_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_cash_totalKeyReleased
       // TODO add your handling code here:
      calcTotalEntered();
    }//GEN-LAST:event_txt_enter_cash_totalKeyReleased

    private void txt_enter_card_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enter_card_totalActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_enter_card_totalActionPerformed

    private void txt_enter_card_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_card_totalKeyReleased
        // TODO add your handling code here:
       calcTotalEntered();
    }//GEN-LAST:event_txt_enter_card_totalKeyReleased

    private void txt_enter_cash_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_cash_totalKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))
            txt_enter_cash_total.setEditable(false);
        else
            txt_enter_cash_total.setEditable(true);
    }//GEN-LAST:event_txt_enter_cash_totalKeyPressed

    private void txt_enter_card_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_enter_card_totalKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))
            txt_enter_card_total.setEditable(false);
        else
            txt_enter_card_total.setEditable(true);
    }//GEN-LAST:event_txt_enter_card_totalKeyPressed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_money_counterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_money_counterActionPerformed
        // TODO add your handling code here:
        new MoneyCounter().setVisible(true);
    }//GEN-LAST:event_btn_money_counterActionPerformed

    private void txt_adjustmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_adjustmentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adjustmentsActionPerformed

    private void txt_adjustmentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_adjustmentsKeyPressed
        // TODO add your handling code here:
        if (Character.isLetter(evt.getKeyChar()))
            txt_enter_card_total.setEditable(false);
        else
            txt_enter_card_total.setEditable(true);
    }//GEN-LAST:event_txt_adjustmentsKeyPressed

    private void txt_adjustmentsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_adjustmentsKeyReleased
        // TODO add your handling code here:
        calcTotalEntered();
    }//GEN-LAST:event_txt_adjustmentsKeyReleased

    private void txt_paymentsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_paymentsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_paymentsKeyReleased

    private void txt_takesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_takesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_takesKeyReleased

    private void txt_otherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_otherKeyReleased

    private void txt_paymentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_paymentsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_paymentsKeyPressed

    private void txt_takesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_takesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_takesKeyPressed

    private void txt_otherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_otherKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_money_counter;
    private javax.swing.JButton btn_sales;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JScrollPane jScrollPane_notes;
    private javax.swing.JLabel lbl_adjustments;
    private javax.swing.JLabel lbl_balance;
    private javax.swing.JLabel lbl_card_total;
    private javax.swing.JLabel lbl_card_total1;
    private javax.swing.JLabel lbl_cash_in_total;
    private javax.swing.JLabel lbl_cash_out_total;
    private javax.swing.JLabel lbl_cash_total;
    private javax.swing.JLabel lbl_date_cashier;
    private javax.swing.JLabel lbl_enter_card_total;
    private javax.swing.JLabel lbl_enter_cash_total;
    private javax.swing.JLabel lbl_other;
    private javax.swing.JLabel lbl_payments;
    private javax.swing.JLabel lbl_takes;
    private javax.swing.JLabel lbl_till_closing_on;
    private javax.swing.JLabel lbl_till_total;
    private javax.swing.JPanel panel_cash_in;
    private javax.swing.JPanel panel_cash_out;
    private javax.swing.JPanel panel_daily_closing_till;
    private javax.swing.JPanel panel_totals;
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
