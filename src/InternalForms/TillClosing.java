/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Common.DBConnection;
import Forms.PrintFullReport;
import Model.OrderReport;
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
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class TillClosing extends javax.swing.JInternalFrame {

    /**
     * Creates new form OrdersMenu
     */
    
    Color defaultColor, mouseEnteredColor;
    PreparedStatement ps;
    Statement st;
    Connection con;
    ResultSet rs;
    OrderReport ordersReport;
    SaleReport salesReport;
    
    ArrayList<Double> refundList = new ArrayList<>();
    double refundTotal;
    //Date pickedDate; //= date_picker.getDate();
    //String tillClosingDate; // = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);
    
    public TillClosing() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        defaultColor = new Color(21,76,121);
        mouseEnteredColor = new Color(118,181,197);
        
        scroll_pane_table_orders.setOpaque(false);
        scroll_pane_table_orders.getViewport().setOpaque(false);
        
        scroll_pane_table_sales.setOpaque(false);
        scroll_pane_table_sales.getViewport().setOpaque(false);
        
        loadOrdersReportOfTheDay();
        
    }
    
     public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","root","hellmans");
              st = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public ArrayList<SaleReport> loadSalesList()
     {
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
            
            while (rs.next())
            {
                // Before adding to the list take the changeTotal deducted from cash
//                double cash = rs.getDouble("cash") - rs.getDouble("changeTotal");
                salesReport = new SaleReport(rs.getString("saleNo"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("productService"), rs.getDouble("total"), rs.getDouble("cash"), rs.getDouble("card"), rs.getDouble("changeTotal"));
                
                salesList.add(salesReport);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
        }
         return salesList;
     }
    
     public ArrayList<OrderReport> loadOrdersList()
     {
        ArrayList<OrderReport> ordersList = new ArrayList<>();
         
        try {
            dbConnection();
            Date date = date_picker.getDate();
            String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
            
            String queryOrders = "SELECT * FROM completedOrders WHERE payDate = ?";
            ps = con.prepareStatement(queryOrders);
            ps.setString(1, selectedDate);
            rs = ps.executeQuery();
            
                while (rs.next())
                {
                    /*
                        public OrderReport(String _orderNo, String _firstName, String _lastName, String _productsService,
                double _deposit, double _total, double _due, double _cash, double _card, double _changeTotal,
                double _cashDeposit, double _cardDeposit) {
                    */
                   //double cash = rs.getDouble("cash") - rs.getDouble("changeTotal");
                   ordersReport = new OrderReport(rs.getString("orderNo"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("productService"), rs.getDouble("deposit"), rs.getDouble("total"), rs.getDouble("due"), rs.getDouble("cash"), 
                           rs.getDouble("card"), rs.getDouble("changeTotal"), rs.getDouble("cashDeposit"), rs.getDouble("cardDeposit"));

                   ordersList.add(ordersReport);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(TillClosing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersList;
     }
     
     public void loadOrdersReportOfTheDay()
      {
        panel_orders.setVisible(true);
        panel_sales.setVisible(false);
        
        // Get Current date for checking cash entries
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);
        
        //Lists for holding list from the constructor
        ArrayList<OrderReport> listOrders = loadOrdersList();
        // This Lists hold all values paid by cash and card
        ArrayList<Double> cashList = new ArrayList<>();
        ArrayList<Double> cardList = new ArrayList<>();
        //ArrayList<Double> refundList = new ArrayList<>();
        
        refundList.clear();

        //This Lists hold total due of all orders
        ArrayList<Double> orderDueColumn = new ArrayList<>();

        // Table models
        DefaultTableModel ordersModel= (DefaultTableModel) table_view_orders.getModel();
        ordersModel.setRowCount(0);
            
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
            // set deposit row = 0, and dont get changeTotal for calculation
            if (listOrders.get(i).getTotal() < 0)
            {
                rowOrders[3] = 0.0;
                rowOrders[4] = listOrders.get(i).getTotal();
                orderDueColumn.add(listOrders.get(i).getTotal());
                
                cashList.add(listOrders.get(i).getCash() + listOrders.get(i).getCashDeposit() + listOrders.get(i).getChangeTotal());
                cardList.add(listOrders.get(i).getCard() + listOrders.get(i).getCardDeposit());
                refundList.add(listOrders.get(i).getTotal());
            }
            else
            {
                rowOrders[3] = listOrders.get(i).getDeposit();
                rowOrders[4] = listOrders.get(i).getDue();
                
                orderDueColumn.add(listOrders.get(i).getDue()); 
                cardList.add(listOrders.get(i).getCard());
                cashList.add(listOrders.get(i).getCash() - listOrders.get(i).getChangeTotal());
            }
            
                ordersModel.addRow(rowOrders);
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
            
            double refundTotal = 0;
            for (double d : refundList)
                refundTotal += d;

            //Gross total (cash&card    
            double grossTotal = ordersTotal;

            lbl_till_closing_date.setText("Orders Report - " + tillClosingDate);
            lbl_print_gross_total.setText("Gross Orders Total ............. €" + String.valueOf(grossTotal));
            lbl_print_total_cash.setText("Cash Total ......................... €" + String.valueOf(cashTotal));
            lbl_print_total_card.setText("Card Total ......................... €" + String.valueOf(cardTotal));
            lbl_print_refunds.setText("Refunds ............................ €" + String.valueOf(refundTotal));
            
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_calendar = new javax.swing.JPanel();
        date_picker = new com.toedter.calendar.JCalendar();
        btn_orders = new javax.swing.JButton();
        btn_full_report = new javax.swing.JButton();
        btn_sales = new javax.swing.JButton();
        panel_print_view = new javax.swing.JPanel();
        lbl_order_print_view = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
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
        lbl_till_closing_date = new javax.swing.JLabel();
        lbl_print_refunds = new javax.swing.JLabel();
        lbl_full_report = new javax.swing.JLabel();

        setBorder(null);
        setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1049, 700));

        panel_calendar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_calendar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_picker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        date_picker.setPreferredSize(new java.awt.Dimension(400, 200));
        date_picker.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_pickerPropertyChange(evt);
            }
        });
        panel_calendar.add(date_picker, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, -1, 227));

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
                .addContainerGap(89, Short.MAX_VALUE)
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

        lbl_till_closing_date.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_till_closing_date.setText("fullReport");

        lbl_print_refunds.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_refunds.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_refund_black.png"))); // NOI18N
        lbl_print_refunds.setText("totalRefunds");

        lbl_full_report.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_full_report.setText("Full Report - 23/08/2021");

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
                .addComponent(lbl_till_closing_date)
                .addGap(190, 190, 190))
            .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_print_orderLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lbl_full_report)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_till_closing_date)
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
            .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_print_orderLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lbl_full_report)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        scroll_pane_orders_sales.setViewportView(panel_print_order);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_calendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btn_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btn_full_report, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_calendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_full_report, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll_pane_orders_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ordersActionPerformed
        
//         panel_orders.setVisible(true);
//        panel_sales.setVisible(false);
        // get date from calendar
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);
        
        // Get Current date for checking cash entries
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        
        if (loadOrdersList().isEmpty() || pickedDate.after(currentDate))
        {
            JOptionPane.showMessageDialog(this, "No Cash Entries on " + tillClosingDate + " !" , "Till Closing", JOptionPane.ERROR_MESSAGE);
            loadOrdersReportOfTheDay();
        }
        else
        {
           loadOrdersReportOfTheDay();
        }
    }//GEN-LAST:event_btn_ordersActionPerformed

    private void btn_full_reportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_full_reportActionPerformed
        Date pickedDate = pickedDate = date_picker.getDate();
        String tillClosingDate = new SimpleDateFormat("dd/MM/yyyy").format(pickedDate);
        
        ArrayList<SaleReport> listSales = loadSalesList();
        ArrayList<OrderReport> listOrders = loadOrdersList();
        
        
        Date dateFullReport = new Date();
        Timestamp currentDateFull = new Timestamp(dateFullReport.getTime());
        
        
        if (pickedDate.after(currentDateFull) || listSales.isEmpty() && listOrders.isEmpty())
            JOptionPane.showMessageDialog(this, "No Cash Entries on " + tillClosingDate + " !" , "Till Closing", JOptionPane.ERROR_MESSAGE);
        else
        {
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
        
        panel_sales.setVisible(true);
        panel_orders.setVisible(false);
        
        //Lists for holding list from the constructor
        ArrayList<SaleReport> listSales = loadSalesList();
        
        if (listSales.isEmpty() || pickedDate.after(currentDate))
            
        {
            JOptionPane.showMessageDialog(this, "No Cash Entries on " + tillClosingDate + " !" , "Till Closing", JOptionPane.ERROR_MESSAGE);
            lbl_till_closing_date.setText("Sales Report - " + tillClosingDate);
        }
            
        else
        {
            
            // This Lists hold all values paid by cash and card
            ArrayList<Double> cashList = new ArrayList<>();
            ArrayList<Double> cardList = new ArrayList<>();
            

            //This Lists hold total due of all orders
            ArrayList<Double> salesTotalColumn = new ArrayList<>();

            // Table models
            DefaultTableModel ordersModel= (DefaultTableModel) table_view_orders.getModel();
            ordersModel.setRowCount(0);
            DefaultTableModel salesModel= (DefaultTableModel) table_view_sales.getModel();
            salesModel.setRowCount(0);

            refundList.clear();

            Object[] rowSales = new Object[4];
            for (int i = 0 ; i < listSales.size(); i++)
            {
                rowSales[0] = listSales.get(i).getSaleNo();
                rowSales[1] = listSales.get(i).getFirstName() + " " + listSales.get(i).getLastName();
                rowSales[2] = listSales.get(i).getProductsService();
                rowSales[3] = listSales.get(i).getTotal();
                salesModel.addRow(rowSales);
                salesTotalColumn.add(listSales.get(i).getTotal());
                cardList.add(listSales.get(i).getCard());
                
                if (listSales.get(i).getTotal() < 0)
                {
                    cashList.add(listSales.get(i).getCash() + listSales.get(i).getChangeTotal());
                    refundList.add(listSales.get(i).getTotal());
                }
                    
                else
                    cashList.add(listSales.get(i).getCash() - listSales.get(i).getChangeTotal());
            }

            //Loop the List and sum Cash payments
            double cashTotal = 0;
            for (double d : cashList)
                cashTotal += d;

            double cardTotal = 0;
            for (double d : cardList)
                cardTotal += d;

            double salesTotal = 0;
            for (double d : salesTotalColumn)
                salesTotal += d;
            
            double refundTotal = 0;
            for (double d : refundList)
                refundTotal += d;
            
            //Gross total (cash&card    
            double grossTotal = salesTotal;

            lbl_till_closing_date.setText("Sales Report - " + tillClosingDate);
            lbl_print_gross_total.setText("Gross Sales Total .............. €" + String.valueOf(grossTotal));
            lbl_print_total_cash.setText("Cash Total ........................ €" + String.valueOf(cashTotal));
            lbl_print_total_card.setText("Card Total ........................ €" + String.valueOf(cardTotal));
            lbl_print_refunds.setText("Refunds ........................... €" + String.valueOf(refundTotal));
            
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

            } catch (PrinterException ex) {
                Logger.getLogger(PrintFullReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void table_view_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_ordersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_ordersMouseClicked

    private void table_view_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_salesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_salesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_full_report;
    private javax.swing.JButton btn_orders;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_sales;
    private com.toedter.calendar.JCalendar date_picker;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_full_report;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_order_print_view;
    private javax.swing.JLabel lbl_print_gross_total;
    private javax.swing.JLabel lbl_print_refunds;
    private javax.swing.JLabel lbl_print_total_card;
    private javax.swing.JLabel lbl_print_total_cash;
    private javax.swing.JLabel lbl_till_closing_date;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_calendar;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_orders;
    private javax.swing.JPanel panel_orders_sales;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JPanel panel_print_view;
    private javax.swing.JPanel panel_sales;
    private javax.swing.JScrollPane scroll_pane_orders_sales;
    private javax.swing.JScrollPane scroll_pane_table_orders;
    private javax.swing.JScrollPane scroll_pane_table_sales;
    private javax.swing.JTable table_view_orders;
    private javax.swing.JTable table_view_sales;
    // End of variables declaration//GEN-END:variables
}
