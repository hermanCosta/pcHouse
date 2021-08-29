/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Model.OrderReport;
import Model.SaleReport;
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
    ArrayList<SaleReport> salesList;
    ArrayList<OrderReport> ordersList;
    
    public PrintFullReport() {
        initComponents();
    }
    
    public PrintFullReport(String _TillClosingDate, ArrayList<SaleReport> _saleList, ArrayList<OrderReport> _ordersList )
    {
        initComponents();
        this.tillClosingDate = _TillClosingDate;
        this.salesList = _saleList;
        this.ordersList = _ordersList;

        scroll_pane_orders.setOpaque(false);
        scroll_pane_orders.getViewport().setOpaque(false);
        
        scroll_pane_sales.setOpaque(false);
        scroll_pane_sales.getViewport().setOpaque(false);
        
        loadOrderToPrint();
        
    }
   
    
    public void loadOrderToPrint()
    {
        //Lists for holding list from the constructor
        ArrayList<OrderReport> listOrders = ordersList;
        ArrayList<SaleReport> listSales = salesList;
        
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

            if (listOrders.get(i).getTotal() == 0)
                orderDueColumn.add(listOrders.get(i).getDeposit());
            
            // check if there's negative values, pass due + deposit if true, else, pass as normal to the list
            if (listOrders.get(i).getTotal() < 0)
            {
                rowOrders[3] = 0.0;
                rowOrders[4] = listOrders.get(i).getTotal();
                orderDueColumn.add(listOrders.get(i).getTotal());
                
                cashList.add(listOrders.get(i).getCash() + listOrders.get(i).getCashDeposit() - listOrders.get(i).getChangeTotal());
                cardList.add(listOrders.get(i).getCard() + listOrders.get(i).getCardDeposit());
            }
            else
            {
                rowOrders[3] = listOrders.get(i).getDeposit();
                rowOrders[4] = listOrders.get(i).getDue();
                
                orderDueColumn.add(listOrders.get(i).getDue()); 
                cashList.add(listOrders.get(i).getCash() - listOrders.get(i).getChangeTotal());
                cardList.add(listOrders.get(i).getCard());
            }
            
            ordersModel.addRow(rowOrders);
        }
        
        
        Object[] rowSales = new Object[4];
        for (int i = 0 ; i < listSales.size(); i++)
        {
            rowSales[0] = listSales.get(i).getSaleNo();
            rowSales[1] = listSales.get(i).getFirstName() + " " + listSales.get(i).getLastName();
            rowSales[2] = listSales.get(i).getProductsService();
            rowSales[3] = listSales.get(i).getTotal();
            salesModel.addRow(rowSales);
            
           cashList.add(listSales.get(i).getCash() - listSales.get(i).getChangeTotal());
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
        double grossTotal = ordersTotal + salesTotal;
        
        
        lbl_full_report.setText("Full Report - " + tillClosingDate);
        lbl_orders_total.setText("Orders Total ............................... €" + ordersTotal);
        lbl_sales_total.setText("Sales Total ................................. €" + salesTotal);
        lbl_print_gross_total.setText("Gross Total ................ €" + String.valueOf(grossTotal));
        lbl_print_total_cash.setText("Cash Total .................. €" + String.valueOf(cashTotal));
        lbl_print_total_card.setText("Card Total .................. €" + String.valueOf(cardTotal));
        
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_pane_full_report = new javax.swing.JScrollPane();
        panel_print_order = new javax.swing.JPanel();
        lbl_print_gross_total = new javax.swing.JLabel();
        lbl_print_total_cash = new javax.swing.JLabel();
        lbl_print_total_card = new javax.swing.JLabel();
        panel_orders = new javax.swing.JPanel();
        lbl_orders_total = new javax.swing.JLabel();
        scroll_pane_orders = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        panel_sales = new javax.swing.JPanel();
        scroll_pane_sales = new javax.swing.JScrollPane();
        table_view_sales = new javax.swing.JTable();
        lbl_sales_total = new javax.swing.JLabel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon1 = new javax.swing.JLabel();
        lbl_land_line_number1 = new javax.swing.JLabel();
        lbl_mobile_number1 = new javax.swing.JLabel();
        lbl_address1 = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_full_report = new javax.swing.JLabel();
        panel_print_view = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setPreferredSize(new java.awt.Dimension(595, 842));

        lbl_print_gross_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_gross_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_print_gross_total.setText("grossTotal");

        lbl_print_total_cash.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_total_cash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_total.png"))); // NOI18N
        lbl_print_total_cash.setText("totalCash");

        lbl_print_total_card.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_total_card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_card_total.png"))); // NOI18N
        lbl_print_total_card.setText("totalCard");

        panel_orders.setBackground(new java.awt.Color(255, 255, 255));
        panel_orders.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lbl_orders_total.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_orders_total.setText("ordersTotal");
        lbl_orders_total.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        scroll_pane_orders.setEnabled(false);

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
        scroll_pane_orders.setViewportView(table_view_orders);
        if (table_view_orders.getColumnModel().getColumnCount() > 0) {
            table_view_orders.getColumnModel().getColumn(0).setMaxWidth(50);
            table_view_orders.getColumnModel().getColumn(1).setPreferredWidth(30);
            table_view_orders.getColumnModel().getColumn(3).setMaxWidth(70);
            table_view_orders.getColumnModel().getColumn(4).setMaxWidth(70);
        }

        javax.swing.GroupLayout panel_ordersLayout = new javax.swing.GroupLayout(panel_orders);
        panel_orders.setLayout(panel_ordersLayout);
        panel_ordersLayout.setHorizontalGroup(
            panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ordersLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_pane_orders, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_ordersLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_orders_total)
                        .addGap(20, 20, 20)))
                .addGap(0, 0, 0))
        );
        panel_ordersLayout.setVerticalGroup(
            panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ordersLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scroll_pane_orders, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lbl_orders_total)
                .addContainerGap())
        );

        panel_sales.setBackground(new java.awt.Color(255, 255, 255));
        panel_sales.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        scroll_pane_sales.setEnabled(false);

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
        scroll_pane_sales.setViewportView(table_view_sales);
        if (table_view_sales.getColumnModel().getColumnCount() > 0) {
            table_view_sales.getColumnModel().getColumn(0).setMaxWidth(60);
            table_view_sales.getColumnModel().getColumn(1).setPreferredWidth(30);
            table_view_sales.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        lbl_sales_total.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_sales_total.setText("salesTotal");
        lbl_sales_total.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout panel_salesLayout = new javax.swing.GroupLayout(panel_sales);
        panel_sales.setLayout(panel_salesLayout);
        panel_salesLayout.setHorizontalGroup(
            panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_salesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_pane_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_salesLayout.createSequentialGroup()
                        .addComponent(lbl_sales_total)
                        .addGap(16, 16, 16)))
                .addGap(0, 0, 0))
        );
        panel_salesLayout.setVerticalGroup(
            panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_salesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scroll_pane_sales, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lbl_sales_total)
                .addContainerGap())
        );

        panel_header.setBackground(new java.awt.Color(255, 255, 255));

        lbl_logo_icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_land_line_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_phone_number.png"))); // NOI18N
        lbl_land_line_number1.setText("+353 (01) 563-9520");

        lbl_mobile_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_mobile_number.png"))); // NOI18N
        lbl_mobile_number1.setText("+353 (83) 012-8190");

        lbl_address1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address1.setText("12A, Frederick Street North, Dublin 1");

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_headerLayout.createSequentialGroup()
                        .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_mobile_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_land_line_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_address1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lbl_land_line_number1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_mobile_number1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_address1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_full_report.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_full_report.setText("Full Report - 23/08/2021");

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_print_total_card, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_print_total_cash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_print_gross_total, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
                        .addComponent(panel_sales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_orders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_full_report)
                .addGap(183, 183, 183))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_full_report)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_orders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(panel_sales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_print_gross_total)
                .addGap(0, 0, 0)
                .addComponent(lbl_print_total_cash)
                .addGap(0, 0, 0)
                .addComponent(lbl_print_total_card)
                .addGap(20, 20, 20))
        );

        scroll_pane_full_report.setViewportView(panel_print_order);

        panel_print_view.setBackground(new java.awt.Color(204, 204, 204));

        lbl_order_print_view.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_print_view.setText("Till Closing Print View");

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

        javax.swing.GroupLayout panel_print_viewLayout = new javax.swing.GroupLayout(panel_print_view);
        panel_print_view.setLayout(panel_print_viewLayout);
        panel_print_viewLayout.setHorizontalGroup(
            panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_viewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_order_print_view)
                .addGap(115, 115, 115)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_print_viewLayout.setVerticalGroup(
            panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_viewLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_order_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scroll_pane_full_report, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addComponent(panel_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane_full_report, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
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

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_full_report;
    private javax.swing.JLabel lbl_land_line_number1;
    private javax.swing.JLabel lbl_logo_icon1;
    private javax.swing.JLabel lbl_mobile_number1;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_orders_total;
    private javax.swing.JLabel lbl_print_gross_total;
    private javax.swing.JLabel lbl_print_total_card;
    private javax.swing.JLabel lbl_print_total_cash;
    private javax.swing.JLabel lbl_sales_total;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_orders;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JPanel panel_print_view;
    private javax.swing.JPanel panel_sales;
    private javax.swing.JScrollPane scroll_pane_full_report;
    private javax.swing.JScrollPane scroll_pane_orders;
    private javax.swing.JScrollPane scroll_pane_sales;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTable table_view_sales;
    // End of variables declaration//GEN-END:variables
}
