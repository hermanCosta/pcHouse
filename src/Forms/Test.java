/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

/**
 *
 * @author user
 */
public class Test extends javax.swing.JFrame {

    /**
     * Creates new form Test
     */
    public Test() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_closing_till_records = new javax.swing.JPanel();
        panel_cash_out = new javax.swing.JPanel();
        lbl_cash_out_total = new javax.swing.JLabel();
        lbl_payments = new javax.swing.JLabel();
        lbl_takes = new javax.swing.JLabel();
        txt_payments = new javax.swing.JTextField();
        txt_takes = new javax.swing.JTextField();
        txt_cash_out_total = new javax.swing.JTextField();
        lbl_other = new javax.swing.JLabel();
        txt_other = new javax.swing.JTextField();
        lbl_till_closing_on = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_closing_till_records.setBackground(new java.awt.Color(255, 255, 255));
        panel_closing_till_records.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        panel_cash_out.setBackground(new java.awt.Color(255, 255, 255));
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
                    .addComponent(txt_takes)
                    .addComponent(txt_cash_out_total, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_other, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_cash_outLayout.createSequentialGroup()
                        .addComponent(txt_payments, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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

        lbl_till_closing_on.setFont(new java.awt.Font("sansserif", 1, 17)); // NOI18N
        lbl_till_closing_on.setText("tillClosingOn");

        panel_totals.setBackground(new java.awt.Color(255, 255, 255));
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
                    .addComponent(txt_adjustments, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(txt_enter_card_total)
                    .addComponent(txt_enter_cash_total)
                    .addComponent(txt_till_total)
                    .addComponent(txt_balance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane_notes, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
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
                                .addComponent(lbl_cash_in_total, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addGap(4, 4, 4))
                            .addGroup(panel_cash_inLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_cash_inLayout.createSequentialGroup()
                                        .addComponent(lbl_cash_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(panel_cash_inLayout.createSequentialGroup()
                                        .addComponent(lbl_card_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(4, 4, 4)))))
                        .addGroup(panel_cash_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_card_total, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cash_total, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cash_in_total, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_cash_inLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_card_total1)
                        .addGap(4, 4, 4)
                        .addComponent(txt_entries_total)))
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

        javax.swing.GroupLayout panel_closing_till_recordsLayout = new javax.swing.GroupLayout(panel_closing_till_records);
        panel_closing_till_records.setLayout(panel_closing_till_recordsLayout);
        panel_closing_till_recordsLayout.setHorizontalGroup(
            panel_closing_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_closing_till_recordsLayout.createSequentialGroup()
                .addGroup(panel_closing_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_closing_till_recordsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_cashier))
                    .addGroup(panel_closing_till_recordsLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(lbl_till_closing_on))
                    .addComponent(panel_totals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_closing_till_recordsLayout.createSequentialGroup()
                        .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel_cash_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_closing_till_recordsLayout.setVerticalGroup(
            panel_closing_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_closing_till_recordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_till_closing_on)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_cashier)
                .addGap(18, 18, 18)
                .addGroup(panel_closing_till_recordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cash_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_totals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 587, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_closing_till_records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_closing_till_records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JScrollPane jScrollPane_notes;
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
    private javax.swing.JLabel lbl_other;
    private javax.swing.JLabel lbl_payments;
    private javax.swing.JLabel lbl_takes;
    private javax.swing.JLabel lbl_till_closing_on;
    private javax.swing.JLabel lbl_till_total;
    private javax.swing.JPanel panel_cash_in;
    private javax.swing.JPanel panel_cash_out;
    private javax.swing.JPanel panel_closing_till_records;
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
