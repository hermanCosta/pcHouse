/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Model.OrdersReport;
import Model.SalesReport;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class PrintFullReport extends javax.swing.JFrame {

    /**
     * Creates new form Print
     */
    
    String tillClosingDate;
    ArrayList<SalesReport> salesList;
    ArrayList<OrdersReport> ordersList;
    
    public PrintFullReport() {
        initComponents();
    }
    
    public PrintFullReport(String _TillClosingDate, ArrayList<SalesReport> _saleList, ArrayList<OrdersReport> _ordersList )
    {
        initComponents();
        this.tillClosingDate = _TillClosingDate;
        this.salesList = _saleList;
        this.ordersList = _ordersList;
        
        loadOrderToPrint();
        
//        table_view_orders.setOpaque(false);
//        ((DefaultTableCellRenderer)table_view_orders.getDefaultRenderer(Object.class)).setOpaque(false);
//        
//         table_view_sales.setOpaque(false);
//        ((DefaultTableCellRenderer)table_view_sales.getDefaultRenderer(Object.class)).setOpaque(false);

        jScrollPane3.setOpaque(false);
        jScrollPane3.getViewport().setOpaque(false);
        
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        
    }
   
    
    public void loadOrderToPrint()
    {
        //Lists for holding list from the constructor
        ArrayList<OrdersReport> listOrders = ordersList;
        ArrayList<SalesReport> listSales = salesList;
        
        // This Lists hold all values paid by cash and card
        ArrayList<Double> cashList = new ArrayList<>();
        ArrayList<Double> cardList = new ArrayList<>();
        
        //This Lists hold total due of all orders
        ArrayList<Double> orderDueColumn = new ArrayList<>();
        ArrayList<Double> salesTotalColumn = new ArrayList<>();
        
        // Table models
        DefaultTableModel ordersModel= (DefaultTableModel) table_view_orders.getModel();
        ordersModel.setRowCount(0);
        DefaultTableModel salesModel= (DefaultTableModel) table_view_sales.getModel();
        salesModel.setRowCount(0);
        
        // this object holds each range of elements for setting into the table
        Object[] rowOrders = new Object[5];
        for (int i = 0; i < listOrders.size() ; i++)
        {
            rowOrders[0] = listOrders.get(i).getOrderNo();
            rowOrders[1] = listOrders.get(i).getFirstName() + " " + listOrders.get(i).getLastName();;
            rowOrders[2] = listOrders.get(i).getProductsService();
            rowOrders[3] = listOrders.get(i).getDeposit();
            rowOrders[4] = listOrders.get(i).getDue();
            
            ordersModel.addRow(rowOrders);
            
            cashList.add(listOrders.get(i).getCash());
            cardList.add(listOrders.get(i).getCard());
            orderDueColumn.add(listOrders.get(i).getDue());
        }
        
        
        Object[] rowSales = new Object[4];
        for (int i = 0 ; i < listSales.size(); i++)
        {
            rowSales[0] = listSales.get(i).getSaleNo();
            rowSales[1] = listSales.get(i).getFirstName() + " " + listSales.get(i).getLastName();
            rowSales[2] = listSales.get(i).getProductsService();
            rowSales[3] = listSales.get(i).getTotal();
            salesModel.addRow(rowSales);
            
            //cashTotal = Double.parseDouble(listSales.get(i).getTotal());
           cashList.add(listSales.get(i).getCash());
           cardList.add(listSales.get(i).getCard());
           salesTotalColumn.add(listSales.get(i).getTotal());
        }
        
        //Loop the List and sum Cash payments
        double cashTotal = 0;
        for (double d : cashList)
            cashTotal += d;
        
        double cardTotal = 0;
        for (double d : cardList)
            cardTotal += d;
        
        double ordersTotal = 0;
        for (double d : orderDueColumn)
            ordersTotal += d;
        
        double salesTotal = 0;
        for (double d : salesTotalColumn)
            salesTotal += d;
        
        //Gross total (cash&card    
        double grossTotal = cashTotal + cardTotal;
        
        lbl_till_closing_date.setText("Full Report - " + tillClosingDate);
        lbl_print_gross_total.setText("Gross Total................ €" + String.valueOf(grossTotal));
        lbl_print_total_cash.setText("Cash Total.................. €" + String.valueOf(cashTotal));
        lbl_print_total_card.setText("Card Total.................. €" + String.valueOf(cardTotal));
        lbl_orders_total.setText("Orders Total.................... €" + ordersTotal);
        lbl_sales_total.setText("Sales Total...................... €" + salesTotal);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        panel_print_order = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_address = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        lbl_mobile = new javax.swing.JLabel();
        lbl_phone = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_print_gross_total = new javax.swing.JLabel();
        lbl_print_total_cash = new javax.swing.JLabel();
        lbl_print_total_card = new javax.swing.JLabel();
        lbl_till_closing_date = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_orders_total = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lbl_sales_total = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_sales = new javax.swing.JTable();
        btn_print = new javax.swing.JButton();
        lbl_order_print_view = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setPreferredSize(new java.awt.Dimension(595, 842));

        lbl_logo_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_address.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address.setText("12A, Frederick Street North, Dublin 1");

        lbl_land_line_number.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number.setText("(01) 563-9520");

        lbl_mobile_number.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number.setText("(083) 012-8190");

        lbl_mobile.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile.setText("Mobile:");

        lbl_phone.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_phone.setText("Phone:");

        lbl_print_gross_total.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        lbl_print_gross_total.setText("grossTotal");

        lbl_print_total_cash.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        lbl_print_total_cash.setText("totalCash");

        lbl_print_total_card.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        lbl_print_total_card.setText("totalCard");

        lbl_till_closing_date.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_till_closing_date.setText("tillClosingDate");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lbl_orders_total.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lbl_orders_total.setText("ordersTotal");
        lbl_orders_total.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jScrollPane3.setEnabled(false);

        table_view_orders.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        table_view_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order", "Full Name", "Product | Service", "Deposit", "Due"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_ordersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_view_orders);
        if (table_view_orders.getColumnModel().getColumnCount() > 0) {
            table_view_orders.getColumnModel().getColumn(0).setMaxWidth(50);
            table_view_orders.getColumnModel().getColumn(1).setPreferredWidth(30);
            table_view_orders.getColumnModel().getColumn(3).setMaxWidth(70);
            table_view_orders.getColumnModel().getColumn(4).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_orders_total)
                    .addComponent(jScrollPane3))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lbl_orders_total)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lbl_sales_total.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lbl_sales_total.setText("salesTotal");
        lbl_sales_total.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jScrollPane2.setEnabled(false);

        table_view_sales.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        table_view_sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sale", "Full Name", "Product | Service", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jScrollPane2.setViewportView(table_view_sales);
        if (table_view_sales.getColumnModel().getColumnCount() > 0) {
            table_view_sales.getColumnModel().getColumn(0).setMaxWidth(60);
            table_view_sales.getColumnModel().getColumn(1).setPreferredWidth(30);
            table_view_sales.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_sales_total)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lbl_sales_total)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_print_total_card, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_total_cash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_gross_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                        .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                                .addComponent(lbl_mobile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbl_mobile_number))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                                .addComponent(lbl_phone)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbl_land_line_number))
                                            .addComponent(lbl_address, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(5, 5, 5))))
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(2, 2, 2))))
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(lbl_till_closing_date)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_land_line_number)
                            .addComponent(lbl_phone))
                        .addGap(5, 5, 5)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_mobile_number)
                            .addComponent(lbl_mobile))
                        .addGap(5, 5, 5)
                        .addComponent(lbl_address)
                        .addGap(32, 32, 32)))
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_till_closing_date)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(lbl_print_gross_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_print_total_cash)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_print_total_card)
                .addGap(30, 30, 30))
        );

        jScrollPane4.setViewportView(panel_print_order);

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

        lbl_order_print_view.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lbl_order_print_view.setText("Till Closing Print View");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_order_print_view)
                .addGap(106, 106, 106)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_order_print_view))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Till Closing" + this.tillClosingDate);
        
        PageFormat format = printerJob.getPageFormat(null);
        
        printerJob.setPrintable(new Printable() {
            
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                
                if(pageIndex > 0)
                {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D)graphics;
                panel_print_order.paint(graphics2D);
                
              return Printable.PAGE_EXISTS;
            }
        }, format);
        
        boolean returningResult = printerJob.printDialog();
        
        if(returningResult)
        {
            try {
                
                printerJob.print();
                
                JOptionPane.showMessageDialog(this, "Till Closing " + tillClosingDate +" Printed Successfully", "Till Closing", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                
            } catch (PrinterException ex) {
                Logger.getLogger(PrintFullReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void table_view_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_salesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_salesMouseClicked

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_ordersMouseClicked

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
            java.util.logging.Logger.getLogger(PrintFullReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintFullReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintFullReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintFullReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintFullReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_address;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_orders_total;
    private javax.swing.JLabel lbl_phone;
    private javax.swing.JLabel lbl_print_gross_total;
    private javax.swing.JLabel lbl_print_total_card;
    private javax.swing.JLabel lbl_print_total_cash;
    private javax.swing.JLabel lbl_sales_total;
    private javax.swing.JLabel lbl_till_closing_date;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTable table_view_sales;
    // End of variables declaration//GEN-END:variables
}
