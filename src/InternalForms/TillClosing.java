/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.CloseDailyTill;
import Forms.Login;
import Forms.MainMenu;
import Forms.MoneyCounter;
import Forms.PrintFullReport;
import Model.CompletedOrder;
import Model.Order;
import Model.OrderReport;
import Model.Sale;
import Model.SaleReport;
import java.awt.Color;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HermanCosta
 */
public class TillClosing extends javax.swing.JInternalFrame {

    Color defaultColor, mouseEnteredColor;
    PreparedStatement ps;
    Connection con;
    ResultSet rs;
    OrderReport ordersReport;
    SaleReport salesReport;
    Order order;
    CompletedOrder completedOrder;
    Sale sale;

    ArrayList<Double> refundList = new ArrayList<>();
    double refundTotal;

    double ordersCashTotal, ordersCardTotal, salesCardTotal, salesCashTotal;

    public TillClosing() {
        initComponents();

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        defaultColor = new Color(21, 76, 121);
        mouseEnteredColor = new Color(118, 181, 197);

        scroll_pane_table_orders.setOpaque(false);
        scroll_pane_table_orders.getViewport().setOpaque(false);

        scroll_pane_table_sales.setOpaque(false);
        scroll_pane_table_sales.getViewport().setOpaque(false);

        loadOrdersReportOfTheDay();
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

    public ArrayList<SaleReport> loadSalesList() {
        ArrayList<SaleReport> salesList = new ArrayList<>();

        try {
            // TODO add your handling code here:
            dbConnection();
            Date date = date_picker.getDate();
            String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

            String querySale = "SELECT * FROM sales WHERE saleDate = ?";
            ps = con.prepareStatement(querySale);
            ps.setString(1, selectedDate);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Before adding to the list take the changeTotal deducted from cash
                salesReport = new SaleReport(rs.getString("saleNo"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("productService"), rs.getDouble("total"), rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"));

                salesList.add(salesReport);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salesList;
    }

    public ArrayList<CompletedOrder> loadOrdersList() {
        ArrayList<CompletedOrder> ordersList = new ArrayList<>();

        try {
            dbConnection();
            //Date date = date_picker.getDate();
            String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date_picker.getDate());

            String queryOrders = "SELECT * FROM completedOrders WHERE payDate = ?";
            ps = con.prepareStatement(queryOrders);
            ps.setString(1, selectedDate);
            rs = ps.executeQuery();

            while (rs.next()) {
                completedOrder = new CompletedOrder(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"),
                        "", "", rs.getString("brand"), rs.getString("model"),
                        "", rs.getDouble("total"), rs.getDouble("deposit"), rs.getDouble("due"),
                        rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"), rs.getDouble("cashDeposit"),
                        rs.getDouble("cardDeposit"), rs.getString("payDate"), rs.getString("status"));

                ordersList.add(completedOrder);

            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersList;
    }

    public void loadOrdersReportOfTheDay() {
        panel_orders.setVisible(true);
        panel_sales.setVisible(false);

        // Get Current date for checking cash entries
        //Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(date_picker.getDate());

        //Lists for holding list from the constructor
        ArrayList<CompletedOrder> listOrders = loadOrdersList();

        // This Lists hold all values paid by cash and card
        ArrayList<Double> cashList = new ArrayList<>();
        ArrayList<Double> cardList = new ArrayList<>();

        refundList.clear();

        //This Lists hold total due of all orders
        ArrayList<Double> orderDueColumn = new ArrayList<>();

        // Table models
        DefaultTableModel ordersModel = (DefaultTableModel) table_view_orders.getModel();
        ordersModel.setRowCount(0);

        // this object holds each range of elements for setting into the table
        Object[] rowOrders = new Object[5];
        for (int i = 0; i < listOrders.size(); i++) {
            rowOrders[0] = listOrders.get(i).getOrderNo();
            rowOrders[1] = listOrders.get(i).getFirstName() + " " + listOrders.get(i).getLastName();
            rowOrders[2] = listOrders.get(i).getBrand() + " | " + listOrders.get(i).getModel();

            if (listOrders.get(i).getTotal() == 0) {
                orderDueColumn.add(listOrders.get(i).getDeposit());

                if (listOrders.get(i).getTotal() == 0 && listOrders.get(i).getDeposit() < 0) {
                    refundList.add(listOrders.get(i).getDeposit());
                }
            }
            // check if there's negative values, pass due + deposit if true, else, pass as normal to the list
            // set deposit row = 0, and dont get changeTotal for calculation
            if (listOrders.get(i).getTotal() < 0) {
                rowOrders[3] = 0.0;
                rowOrders[4] = listOrders.get(i).getTotal();
                orderDueColumn.add(listOrders.get(i).getTotal());

                cashList.add(listOrders.get(i).getCash() + listOrders.get(i).getCashDeposit() + listOrders.get(i).getChangeTotal());
                cardList.add(listOrders.get(i).getCard() + listOrders.get(i).getCardDeposit());

                refundList.add(listOrders.get(i).getTotal());
            } else {
                rowOrders[3] = listOrders.get(i).getDeposit();
                rowOrders[4] = listOrders.get(i).getDue();

                orderDueColumn.add(listOrders.get(i).getDue());
                cardList.add(listOrders.get(i).getCard());
                cashList.add(listOrders.get(i).getCash() - listOrders.get(i).getChangeTotal());
            }

            ordersModel.addRow(rowOrders);
        }

        //Loop the List and sum Cash payments
        ordersCashTotal = 0;
        ordersCardTotal = 0;
        for (double d : cashList) {
            ordersCashTotal += d;
        }

        for (double d : cardList) {
            ordersCardTotal += d;
        }

        double ordersTotal = 0;
        for (double d : orderDueColumn) {
            ordersTotal += d;
        }

        double ordersRefundTotal = 0;
        for (double d : refundList) {
            ordersRefundTotal += d;
        }

        lbl_report_date.setText("Orders Report - " + tillClosingDate);
        lbl_print_gross_total.setText("Gross Orders Total ............. €" + String.valueOf(ordersTotal));
        lbl_print_total_cash.setText("Cash Total ......................... €" + String.valueOf(ordersCashTotal));
        lbl_print_total_card.setText("Card Total ......................... €" + String.valueOf(ordersCardTotal));
        lbl_print_refunds.setText("Refunds ............................ €" + String.valueOf(ordersRefundTotal));
    }

    public void loadSalesOfTheDay() {
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        panel_sales.setVisible(true);
        panel_orders.setVisible(false);

        //Lists for holding list from the constructor
        ArrayList<SaleReport> listSales = loadSalesList();

        // This Lists hold all values paid by cash and card
        ArrayList<Double> cashList = new ArrayList<>();
        ArrayList<Double> cardList = new ArrayList<>();

        //This Lists hold total due of all orders
        ArrayList<Double> salesTotalColumn = new ArrayList<>();

        // Table models
        DefaultTableModel ordersModel = (DefaultTableModel) table_view_orders.getModel();
        ordersModel.setRowCount(0);
        DefaultTableModel salesModel = (DefaultTableModel) table_view_sales.getModel();
        salesModel.setRowCount(0);

        refundList.clear();

        Object[] rowSales = new Object[4];
        for (int i = 0; i < listSales.size(); i++) {
            rowSales[0] = listSales.get(i).getSaleNo();
            rowSales[1] = listSales.get(i).getFirstName() + " " + listSales.get(i).getLastName();
            rowSales[2] = listSales.get(i).getProductsService();
            rowSales[3] = listSales.get(i).getTotal();
            salesModel.addRow(rowSales);
            salesTotalColumn.add(listSales.get(i).getTotal());
            cardList.add(listSales.get(i).getCard());

            if (listSales.get(i).getTotal() < 0) {
                cashList.add(listSales.get(i).getCash() + listSales.get(i).getChangeTotal());
                refundList.add(listSales.get(i).getTotal());
            } else {
                cashList.add(listSales.get(i).getCash() - listSales.get(i).getChangeTotal());
            }
        }

        //Loop the List and sum Cash payments
        salesCashTotal = 0;
        salesCardTotal = 0;
        for (double d : cashList) {
            salesCashTotal += d;
        }

        for (double d : cardList) {
            salesCardTotal += d;
        }

        double salesTotal = 0;
        for (double d : salesTotalColumn) {
            salesTotal += d;
        }

        double salesRefundTotal = 0;
        for (double d : refundList) {
            salesRefundTotal += d;
        }

        //Gross total (cash&card    
        double salesGrossTotal = salesTotal;

        lbl_report_date.setText("Sales Report - " + tillClosingDate);
        lbl_print_gross_total.setText("Gross Sales Total .............. €" + String.valueOf(salesGrossTotal));
        lbl_print_total_cash.setText("Cash Total ........................ €" + String.valueOf(salesCashTotal));
        lbl_print_total_card.setText("Card Total ........................ €" + String.valueOf(salesCardTotal));
        lbl_print_refunds.setText("Refunds ........................... €" + String.valueOf(salesRefundTotal));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_till_closing = new javax.swing.JDesktopPane();
        panel_till_closing = new javax.swing.JPanel();
        btn_orders = new javax.swing.JButton();
        btn_full_report = new javax.swing.JButton();
        btn_sales = new javax.swing.JButton();
        panel_print_view = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();
        scroll_pane_orders_sales = new javax.swing.JScrollPane();
        panel_print_order = new javax.swing.JPanel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
        lbl_print_gross_total = new javax.swing.JLabel();
        lbl_print_total_cash = new javax.swing.JLabel();
        lbl_print_total_card = new javax.swing.JLabel();
        panel_orders_sales = new javax.swing.JPanel();
        panel_orders = new javax.swing.JPanel();
        scroll_pane_table_orders = new javax.swing.JScrollPane();
        table_view_orders = new javax.swing.JTable();
        panel_sales = new javax.swing.JPanel();
        scroll_pane_table_sales = new javax.swing.JScrollPane();
        table_view_sales = new javax.swing.JTable();
        lbl_report_date = new javax.swing.JLabel();
        lbl_print_refunds = new javax.swing.JLabel();
        date_picker = new com.toedter.calendar.JCalendar();
        btn_print = new javax.swing.JButton();
        btn_close_dayling_till = new javax.swing.JButton();
        btn_money_counter = new javax.swing.JButton();
        btn_cash_out = new javax.swing.JButton();
        btn_till_records = new javax.swing.JButton();
        btn_cash_in = new javax.swing.JButton();

        setBorder(null);
        setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1049, 700));

        panel_till_closing.setPreferredSize(new java.awt.Dimension(1049, 676));

        btn_orders.setBackground(new java.awt.Color(21, 76, 121));
        btn_orders.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_orders.setForeground(new java.awt.Color(255, 255, 255));
        btn_orders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_orders.setText("Orders");
        btn_orders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ordersActionPerformed(evt);
            }
        });

        btn_full_report.setBackground(new java.awt.Color(21, 76, 121));
        btn_full_report.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_full_report.setForeground(new java.awt.Color(255, 255, 255));
        btn_full_report.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_full_report.setText("Print Full Report");
        btn_full_report.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_full_reportActionPerformed(evt);
            }
        });

        btn_sales.setBackground(new java.awt.Color(21, 76, 121));
        btn_sales.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_sales.setForeground(new java.awt.Color(255, 255, 255));
        btn_sales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_sales.setText("Sales");
        btn_sales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salesActionPerformed(evt);
            }
        });

        panel_print_view.setBackground(new java.awt.Color(204, 204, 204));

        lbl_order_print_view.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_order_print_view.setText("Till Closing Print View");

        javax.swing.GroupLayout panel_print_viewLayout = new javax.swing.GroupLayout(panel_print_view);
        panel_print_view.setLayout(panel_print_viewLayout);
        panel_print_viewLayout.setHorizontalGroup(
            panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_viewLayout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(lbl_order_print_view)
                .addContainerGap(307, Short.MAX_VALUE))
        );
        panel_print_viewLayout.setVerticalGroup(
            panel_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_viewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_order_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        scroll_pane_orders_sales.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setPreferredSize(new java.awt.Dimension(595, 842));

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
                .addGap(0, 0, 0)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_mobile_number, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_land_line_number, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_address1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
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
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_print_gross_total.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_gross_total.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_gross_total.png"))); // NOI18N
        lbl_print_gross_total.setText("grossTotal");

        lbl_print_total_cash.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_total_cash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_total.png"))); // NOI18N
        lbl_print_total_cash.setText("totalCash");

        lbl_print_total_card.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_total_card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_card_total.png"))); // NOI18N
        lbl_print_total_card.setText("totalCard");

        panel_orders_sales.setBackground(new java.awt.Color(255, 255, 255));
        panel_orders_sales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_orders.setBackground(new java.awt.Color(255, 255, 255));
        panel_orders.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        panel_orders.setPreferredSize(new java.awt.Dimension(540, 520));

        scroll_pane_table_orders.setEnabled(false);

        table_view_orders.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        table_view_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order", "Full Name", "Brand | Model", "Deposit", "Due"
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
        scroll_pane_table_orders.setViewportView(table_view_orders);
        if (table_view_orders.getColumnModel().getColumnCount() > 0) {
            table_view_orders.getColumnModel().getColumn(0).setMaxWidth(80);
            table_view_orders.getColumnModel().getColumn(3).setMaxWidth(80);
            table_view_orders.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        javax.swing.GroupLayout panel_ordersLayout = new javax.swing.GroupLayout(panel_orders);
        panel_orders.setLayout(panel_ordersLayout);
        panel_ordersLayout.setHorizontalGroup(
            panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ordersLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(scroll_pane_table_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_ordersLayout.setVerticalGroup(
            panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ordersLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scroll_pane_table_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        panel_orders_sales.add(panel_orders, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 490));

        panel_sales.setBackground(new java.awt.Color(255, 255, 255));
        panel_sales.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        scroll_pane_table_sales.setEnabled(false);
        scroll_pane_table_sales.setPreferredSize(new java.awt.Dimension(540, 500));
        scroll_pane_table_sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scroll_pane_table_salesMouseClicked(evt);
            }
        });

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
        scroll_pane_table_sales.setViewportView(table_view_sales);
        if (table_view_sales.getColumnModel().getColumnCount() > 0) {
            table_view_sales.getColumnModel().getColumn(0).setMaxWidth(80);
            table_view_sales.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        javax.swing.GroupLayout panel_salesLayout = new javax.swing.GroupLayout(panel_sales);
        panel_sales.setLayout(panel_salesLayout);
        panel_salesLayout.setHorizontalGroup(
            panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_pane_table_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel_salesLayout.setVerticalGroup(
            panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_salesLayout.createSequentialGroup()
                .addComponent(scroll_pane_table_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panel_orders_sales.add(panel_sales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 490));

        lbl_report_date.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_report_date.setText("ordersReport");

        lbl_print_refunds.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_refunds.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_refund_black.png"))); // NOI18N
        lbl_print_refunds.setText("totalRefunds");

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_print_total_cash, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_print_gross_total, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_print_total_card, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_print_refunds, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_report_date)
                .addGap(177, 177, 177))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_report_date)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_print_gross_total, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbl_print_total_cash)
                .addGap(0, 0, 0)
                .addComponent(lbl_print_total_card)
                .addGap(0, 0, 0)
                .addComponent(lbl_print_refunds)
                .addContainerGap())
        );

        scroll_pane_orders_sales.setViewportView(panel_print_order);

        date_picker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        date_picker.setPreferredSize(new java.awt.Dimension(400, 200));
        date_picker.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_pickerPropertyChange(evt);
            }
        });

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

        btn_close_dayling_till.setBackground(new java.awt.Color(21, 76, 121));
        btn_close_dayling_till.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_close_dayling_till.setForeground(new java.awt.Color(255, 255, 255));
        btn_close_dayling_till.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_close_day_till.png"))); // NOI18N
        btn_close_dayling_till.setText("Close Daily Till");
        btn_close_dayling_till.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_close_dayling_tillActionPerformed(evt);
            }
        });

        btn_money_counter.setBackground(new java.awt.Color(21, 76, 121));
        btn_money_counter.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_money_counter.setForeground(new java.awt.Color(255, 255, 255));
        btn_money_counter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_money_calc.png"))); // NOI18N
        btn_money_counter.setText("Money Counter");
        btn_money_counter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_money_counterActionPerformed(evt);
            }
        });

        btn_cash_out.setBackground(new java.awt.Color(255, 51, 51));
        btn_cash_out.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_cash_out.setForeground(new java.awt.Color(255, 255, 255));
        btn_cash_out.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_out.png"))); // NOI18N
        btn_cash_out.setText("Cash Out");
        btn_cash_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cash_outActionPerformed(evt);
            }
        });

        btn_till_records.setBackground(new java.awt.Color(21, 76, 121));
        btn_till_records.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_till_records.setForeground(new java.awt.Color(255, 255, 255));
        btn_till_records.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_till_records.png"))); // NOI18N
        btn_till_records.setText("Till Records");
        btn_till_records.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_till_recordsActionPerformed(evt);
            }
        });

        btn_cash_in.setBackground(new java.awt.Color(0, 153, 102));
        btn_cash_in.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_cash_in.setForeground(new java.awt.Color(255, 255, 255));
        btn_cash_in.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cash_entries.png"))); // NOI18N
        btn_cash_in.setText("Cash Entries");
        btn_cash_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cash_inActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_till_closingLayout = new javax.swing.GroupLayout(panel_till_closing);
        panel_till_closing.setLayout(panel_till_closingLayout);
        panel_till_closingLayout.setHorizontalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closingLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addComponent(panel_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_till_closingLayout.createSequentialGroup()
                                .addComponent(btn_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_full_report, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_till_closingLayout.createSequentialGroup()
                                .addComponent(btn_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cash_out, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_till_closingLayout.createSequentialGroup()
                                .addComponent(btn_money_counter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_till_records, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(date_picker, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_close_dayling_till, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_till_closingLayout.setVerticalGroup(
            panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_till_closingLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_till_closingLayout.createSequentialGroup()
                        .addComponent(date_picker, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_close_dayling_till, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_full_report, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_cash_out, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cash_in, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(panel_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_till_records, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_money_counter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        desktop_pane_till_closing.setLayer(panel_till_closing, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_till_closingLayout = new javax.swing.GroupLayout(desktop_pane_till_closing);
        desktop_pane_till_closing.setLayout(desktop_pane_till_closingLayout);
        desktop_pane_till_closingLayout.setHorizontalGroup(
            desktop_pane_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_till_closingLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_till_closing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        desktop_pane_till_closingLayout.setVerticalGroup(
            desktop_pane_till_closingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_till_closingLayout.createSequentialGroup()
                .addComponent(panel_till_closing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(desktop_pane_till_closing)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_till_closing)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ordersActionPerformed
        // get date from calendar
        //Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(date_picker.getDate());

        // Get Current date for checking cash entries
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());

        if (loadOrdersList().isEmpty() || date_picker.getDate().after(currentDate)) {
            JOptionPane.showMessageDialog(this, "No Orders completed on " + tillClosingDate + " !");
            loadOrdersReportOfTheDay();
        } else {
            loadOrdersReportOfTheDay();
        }
    }//GEN-LAST:event_btn_ordersActionPerformed

    private void btn_full_reportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_full_reportActionPerformed
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        ArrayList<SaleReport> listSales = loadSalesList();
        ArrayList<CompletedOrder> listOrders = loadOrdersList();

        Date dateFullReport = new Date();
        Timestamp currentDateFull = new Timestamp(dateFullReport.getTime());

        if (pickedDate.after(currentDateFull) || listSales.isEmpty() && listOrders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Orders or Sales on " + tillClosingDate + " !");
        } else {
            PrintFullReport printFullReport = new PrintFullReport(tillClosingDate, listSales, listOrders);
            printFullReport.setVisible(true);
        }
    }//GEN-LAST:event_btn_full_reportActionPerformed

    private void btn_salesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salesActionPerformed
        // Gett date from calendar
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        // Get Current date for checking cash entries
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());

        if (loadSalesList().isEmpty() || pickedDate.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "No Sales on " + tillClosingDate + " !");
            lbl_report_date.setText("Sales Report - " + tillClosingDate);
            loadSalesOfTheDay();
        } else {
            loadSalesOfTheDay();
        }

    }//GEN-LAST:event_btn_salesActionPerformed

    private void date_pickerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_pickerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_date_pickerPropertyChange

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        // Get date from calendar
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Till Closing" + tillClosingDate);
        PageFormat format = printerJob.getPageFormat(null);

        printerJob.setPrintable(new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D) graphics;
                panel_print_order.paint(graphics2D);

                return Printable.PAGE_EXISTS;
            }
        }, format);

        boolean returningResult = printerJob.printDialog();

        if (returningResult) {
            try {

                printerJob.print();

                JOptionPane.showMessageDialog(this, "Till Closing " + tillClosingDate + " Printed Successfully", "Till Closing", JOptionPane.INFORMATION_MESSAGE);

            } catch (PrinterException ex) {
                Logger.getLogger(PrintFullReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel dtm = (DefaultTableModel) table_view_orders.getModel();
            int orderSelected = table_view_orders.getSelectedRow();
            String selectedOrderNo = dtm.getValueAt(orderSelected, 0).toString();

            try {
                dbConnection();
                String queryOrder = "SELECT * FROM orderDetails WHERE orderNo = ? ";
                ps = con.prepareStatement(queryOrder);
                ps.setString(1, selectedOrderNo);
                rs = ps.executeQuery();

                while (rs.next()) {
                    order = new Order(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"),
                            rs.getString("email"), rs.getString("deviceBrand"), rs.getString("deviceModel"), rs.getString("serialNumber"), rs.getString("importantNotes"),
                            rs.getString("fault"), rs.getString("productService"), rs.getString("qty"), rs.getString("unitPrice"), rs.getString("priceTotal"),
                            rs.getDouble("total"), rs.getDouble("deposit"), rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"), rs.getDouble("due"),
                            rs.getString("status"), rs.getString("issueDate"), rs.getString("finishDate"), rs.getString("pickDate"),
                            rs.getString("refundDate"), Login.fullName);
                }

                String queryCompletedOrder = "SELECT * FROM completedOrders WHERE orderNo = ?";
                ps = con.prepareStatement(queryCompletedOrder);
                ps.setString(1, selectedOrderNo);
                rs = ps.executeQuery();

                while (rs.next()) {
                    completedOrder = new CompletedOrder(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"),
                            "", "", rs.getString("brand"), rs.getString("model"),
                            "", rs.getDouble("total"), rs.getDouble("deposit"), rs.getDouble("due"),
                            rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"), rs.getDouble("cashDeposit"),
                            rs.getDouble("cardDeposit"), rs.getString("payDate"), rs.getString("status"));
                }

                switch (order.getStatus()) {

                    case "Refunded":
                        OrderRefund refundOrder = new OrderRefund(order, completedOrder);
                        desktop_pane_till_closing.add(refundOrder).setVisible(true);
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.expandOrders();
                        break;

                    case "Not Fixed":
                        NotFixedOrder notFixed = new NotFixedOrder(order, completedOrder);
                        desktop_pane_till_closing.add(notFixed).setVisible(true);
                        break;

                    case "In Progress":
                        OrderDetails orderDetails = new OrderDetails(order, completedOrder);
                        desktop_pane_till_closing.add(orderDetails).setVisible(true);
                        break;

                    default:
                        FixedOrder fixedOrder = new FixedOrder(order, completedOrder);
                        desktop_pane_till_closing.add(fixedOrder).setVisible(true);
                        break;
                }

                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_table_view_ordersMouseClicked

    private void table_view_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_salesMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel dtm = (DefaultTableModel) table_view_sales.getModel();
            int orderSelected = table_view_sales.getSelectedRow();
            String selectedSaleNo = dtm.getValueAt(orderSelected, 0).toString();

            try {
                dbConnection();

                String queryOrder = "SELECT * FROM sales WHERE saleNo = ? ";
                ps = con.prepareStatement(queryOrder);
                ps.setString(1, selectedSaleNo);
                rs = ps.executeQuery();

                while (rs.next()) {
                    sale = new Sale(rs.getString("saleNo"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("contactNo"), rs.getString("email"),
                            rs.getString("productService"), rs.getString("qty"), rs.getString("unitPrice"),
                            rs.getString("priceTotal"), rs.getDouble("total"), rs.getString("saleDate"),
                            rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"),
                            rs.getString("status"), rs.getString("createdBy"));

                    if (rs.getString("status").equals("Paid")) {
                        SaleDetails saleDetails = new SaleDetails(sale);
                        desktop_pane_till_closing.add(saleDetails).setVisible(true);
                    } else {
                        SaleRefund saleRefund = new SaleRefund(sale);
                        desktop_pane_till_closing.add(saleRefund).setVisible(true);
                    }
                }

                ps.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_table_view_salesMouseClicked

    private void scroll_pane_table_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scroll_pane_table_salesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_scroll_pane_table_salesMouseClicked

    private void btn_close_dayling_tillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_close_dayling_tillActionPerformed
        // TODO add your handling code here:
        loadOrdersReportOfTheDay();
        loadSalesOfTheDay();

        Calendar calendar = Calendar.getInstance();
        Date pickedDate = date_picker.getDate();
        String startDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(pickedDate);
        String endDate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(pickedDate);
        String tillOpeningDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -8);

        try {
            dbConnection();
            String queryTillClosing = "SELECT * FROM tillClosing where tillOpeningDate >= ? AND tillOpeningDate <= ?";
            ps = con.prepareStatement(queryTillClosing);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No Till Pending to Close on " + tillOpeningDate + " !");
            } else if (pickedDate.before(cal.getTime()) || pickedDate.after(calendar.getTime())) {
                JOptionPane.showMessageDialog(this, tillOpeningDate + " is not a valid date to Close The Till !", "Till Closing",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                double cashTotal = ordersCashTotal + salesCashTotal;
                double cardTotal = ordersCardTotal + salesCardTotal;
                CloseDailyTill closeDailyTill = new CloseDailyTill(pickedDate, cashTotal, cardTotal);
                closeDailyTill.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_close_dayling_tillActionPerformed

    private void btn_money_counterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_money_counterActionPerformed
        // TODO add your handling code here:
        new MoneyCounter().setVisible(true);
    }//GEN-LAST:event_btn_money_counterActionPerformed

    private void btn_cash_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cash_outActionPerformed
        // TODO add your handling code here:
        CashOuts cashOut = new CashOuts(date_picker.getDate());
        desktop_pane_till_closing.add(cashOut).setVisible(true);
    }//GEN-LAST:event_btn_cash_outActionPerformed

    private void btn_till_recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_till_recordsActionPerformed
        // TODO add your handling code here:
        TillRecords tillRecords = new TillRecords();
        desktop_pane_till_closing.add(tillRecords).setVisible(true);
    }//GEN-LAST:event_btn_till_recordsActionPerformed

    private void btn_cash_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cash_inActionPerformed
        // TODO add your handling code here:
        CashEntries cashEntry = new CashEntries(date_picker.getDate());
        desktop_pane_till_closing.add(cashEntry).setVisible(true);
    }//GEN-LAST:event_btn_cash_inActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cash_in;
    private javax.swing.JButton btn_cash_out;
    private javax.swing.JButton btn_close_dayling_till;
    private javax.swing.JButton btn_full_report;
    private javax.swing.JButton btn_money_counter;
    private javax.swing.JButton btn_orders;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_sales;
    private javax.swing.JButton btn_till_records;
    private com.toedter.calendar.JCalendar date_picker;
    private javax.swing.JDesktopPane desktop_pane_till_closing;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_print_gross_total;
    private javax.swing.JLabel lbl_print_refunds;
    private javax.swing.JLabel lbl_print_total_card;
    private javax.swing.JLabel lbl_print_total_cash;
    private javax.swing.JLabel lbl_report_date;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_orders;
    private javax.swing.JPanel panel_orders_sales;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JPanel panel_print_view;
    private javax.swing.JPanel panel_sales;
    private javax.swing.JPanel panel_till_closing;
    private javax.swing.JScrollPane scroll_pane_orders_sales;
    private javax.swing.JScrollPane scroll_pane_table_orders;
    private javax.swing.JScrollPane scroll_pane_table_sales;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTable table_view_sales;
    // End of variables declaration//GEN-END:variables
}
