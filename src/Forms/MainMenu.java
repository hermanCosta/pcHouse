/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.NewOrder;
import InternalForms.OrderList;
import InternalForms.TillClosing;
import InternalForms.ProductsList;
import InternalForms.Customers;
import InternalForms.Faults;
import InternalForms.NewSale;
import InternalForms.Sales;
import Model.Customer;
import java.awt.Color;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author user
 */
public class MainMenu extends javax.swing.JFrame {
    /**
     * Creates new form MainMenu
     */
    
    public static JDesktopPane mainMenuPane;
    Color defaultColor, clickedColor;
    
    public MainMenu() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        defaultColor = new Color(21,76,121);
        clickedColor = new Color(118,181,197);
        
        expandHome();
        //defaultColorToAll();
        mainMenuPane = desktop_pane_main_menu; 
    }
    
    public void defaultColorToAll()
    {
        panel_home.setBackground(defaultColor);
        panel_products.setBackground(defaultColor);
        panel_reports.setBackground(defaultColor);
        
        label_home.setBackground(defaultColor);
        label_products.setBackground(defaultColor);
        label_reports.setBackground(defaultColor);
    }
            
    public void expandHome()
    {
        panel_new_order.setVisible(false);
        panel_check_existing.setVisible(false);
        
        panel_products_list.setVisible(false);
        panel_new_sale.setVisible(false);
        panel_sales.setVisible(false);
        
        
        panel_customers.setVisible(false);
        panel_close_till.setVisible(false);
        panel_faults.setVisible(false);
        
        //Set default color to outer labels
        panel_home.setBackground(clickedColor);
        panel_orders.setBackground(defaultColor);
        panel_products.setBackground(defaultColor);
        panel_reports.setBackground(defaultColor);
    }
    

    public void expandOrders()
    {
        panel_check_existing.setVisible(true);
        panel_new_order.setVisible(true);
        
        //Set default color to the inner labels
        panel_check_existing.setBackground(defaultColor);
        panel_new_order.setBackground(defaultColor);
        
        //Set default color to the outer labels
        panel_home.setBackground(defaultColor);
        panel_orders.setBackground(clickedColor);
        panel_products.setBackground(defaultColor);
        panel_reports.setBackground(defaultColor);
        
        //Colapse Products
        panel_products_list.setVisible(false);
        panel_new_sale.setVisible(false);
        panel_sales.setVisible(false);
        
        //Colapse Reports
        panel_customers.setVisible(false);
        panel_close_till.setVisible(false);
        panel_faults.setVisible(false);
           
        
    }
    
    public void expandProducts()
    {
        panel_products_list.setVisible(true);
        panel_new_sale.setVisible(true);
        panel_sales.setVisible(true);
        
        //Set default color to the labels
        panel_products_list.setBackground(defaultColor);
        panel_sales.setBackground(defaultColor);
        
        //Set default color to the outer labels
        panel_home.setBackground(defaultColor);
        panel_orders.setBackground(defaultColor);
        panel_products.setBackground(clickedColor);
        panel_reports.setBackground(defaultColor);
                
        //Colapse Orders
        panel_check_existing.setVisible(false);
        panel_new_order.setVisible(false);
        
        //Colapse Reports
        panel_customers.setVisible(false);
        panel_close_till.setVisible(false);
        panel_faults.setVisible(false);
    }
    
    public void expandReports()
    {
        panel_close_till.setVisible(true);
        panel_customers.setVisible(true);
        panel_faults.setVisible(true);
        
        //Set default color to the labels
        panel_customers.setBackground(defaultColor);
        panel_close_till.setBackground(defaultColor);
        
        //Set default color to the outer labels
        panel_home.setBackground(defaultColor);
        panel_orders.setBackground(defaultColor);
        panel_products.setBackground(defaultColor);
        panel_reports.setBackground(clickedColor);
      
           
        //Colapse Products
        panel_check_existing.setVisible(false);
        panel_new_sale.setVisible(false);
        panel_new_order.setVisible(false);
        
        //Colapse Orders
        panel_products_list.setVisible(false);
        panel_sales.setVisible(false);
        
    }
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    public void setMouseEnteredColor(JLabel label)
    {
        label.setBackground(new Color(165,177,194));
        label.setOpaque(true);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_window = new javax.swing.JPanel();
        panel_header = new javax.swing.JPanel();
        panel_menu_bar = new javax.swing.JPanel();
        panel_home = new javax.swing.JPanel();
        label_home = new javax.swing.JLabel();
        panel_products = new javax.swing.JPanel();
        label_products = new javax.swing.JLabel();
        panel_reports = new javax.swing.JPanel();
        label_reports = new javax.swing.JLabel();
        panel_check_existing = new javax.swing.JPanel();
        label_check_existing = new javax.swing.JLabel();
        panel_new_order = new javax.swing.JPanel();
        label_new_order = new javax.swing.JLabel();
        panel_products_list = new javax.swing.JPanel();
        label_products_list = new javax.swing.JLabel();
        panel_sales = new javax.swing.JPanel();
        label_sales = new javax.swing.JLabel();
        panel_close_till = new javax.swing.JPanel();
        label_close_till = new javax.swing.JLabel();
        panel_customers = new javax.swing.JPanel();
        label_customers = new javax.swing.JLabel();
        panel_orders = new javax.swing.JPanel();
        label_orders = new javax.swing.JLabel();
        panel_faults = new javax.swing.JPanel();
        label_faults = new javax.swing.JLabel();
        panel_new_sale = new javax.swing.JPanel();
        label_new_sale = new javax.swing.JLabel();
        desktop_pane_main_menu = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(21, 76, 121));

        panel_header.setBackground(new java.awt.Color(6, 57, 112));

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panel_menu_bar.setBackground(new java.awt.Color(21, 76, 121));

        panel_home.setBackground(new java.awt.Color(21, 76, 121));
        panel_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_homeMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_homeMouseClicked(evt);
            }
        });

        label_home.setBackground(new java.awt.Color(255, 255, 255));
        label_home.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_home.setForeground(new java.awt.Color(255, 255, 255));
        label_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_home.png"))); // NOI18N
        label_home.setText("  Home");
        label_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_homeMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_homeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_homeLayout = new javax.swing.GroupLayout(panel_home);
        panel_home.setLayout(panel_homeLayout);
        panel_homeLayout.setHorizontalGroup(
            panel_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_homeLayout.createSequentialGroup()
                .addComponent(label_home, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_homeLayout.setVerticalGroup(
            panel_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_homeLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(label_home))
        );

        panel_products.setBackground(new java.awt.Color(21, 76, 121));
        panel_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_productsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_productsMouseClicked(evt);
            }
        });

        label_products.setBackground(new java.awt.Color(255, 255, 255));
        label_products.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_products.setForeground(new java.awt.Color(255, 255, 255));
        label_products.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_products.png"))); // NOI18N
        label_products.setText(" Products");
        label_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_productsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_productsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_productsLayout = new javax.swing.GroupLayout(panel_products);
        panel_products.setLayout(panel_productsLayout);
        panel_productsLayout.setHorizontalGroup(
            panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(label_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_productsLayout.setVerticalGroup(
            panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_productsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(label_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_reports.setBackground(new java.awt.Color(21, 76, 121));
        panel_reports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_reportsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_reportsMouseClicked(evt);
            }
        });

        label_reports.setBackground(new java.awt.Color(255, 255, 255));
        label_reports.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_reports.setForeground(new java.awt.Color(255, 255, 255));
        label_reports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_reports.png"))); // NOI18N
        label_reports.setText(" Reports");
        label_reports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_reportsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_reportsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_reportsLayout = new javax.swing.GroupLayout(panel_reports);
        panel_reports.setLayout(panel_reportsLayout);
        panel_reportsLayout.setHorizontalGroup(
            panel_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_reportsLayout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(label_reports, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_reportsLayout.setVerticalGroup(
            panel_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_reportsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(label_reports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_check_existing.setBackground(new java.awt.Color(21, 76, 121));
        panel_check_existing.setPreferredSize(new java.awt.Dimension(160, 32));
        panel_check_existing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_check_existingMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_check_existingMouseClicked(evt);
            }
        });

        label_check_existing.setBackground(new java.awt.Color(255, 255, 255));
        label_check_existing.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_check_existing.setForeground(new java.awt.Color(255, 255, 255));
        label_check_existing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_check_existing.png"))); // NOI18N
        label_check_existing.setText("Check Existing");
        label_check_existing.setToolTipText("");
        label_check_existing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_check_existingMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                label_check_existingMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_check_existingMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_check_existingMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_check_existingMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_check_existingLayout = new javax.swing.GroupLayout(panel_check_existing);
        panel_check_existing.setLayout(panel_check_existingLayout);
        panel_check_existingLayout.setHorizontalGroup(
            panel_check_existingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_check_existing, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        panel_check_existingLayout.setVerticalGroup(
            panel_check_existingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_check_existing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_new_order.setBackground(new java.awt.Color(21, 76, 121));
        panel_new_order.setPreferredSize(new java.awt.Dimension(160, 32));
        panel_new_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_new_orderMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_new_orderMouseClicked(evt);
            }
        });

        label_new_order.setBackground(new java.awt.Color(255, 255, 255));
        label_new_order.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_new_order.setForeground(new java.awt.Color(255, 255, 255));
        label_new_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_new_order.png"))); // NOI18N
        label_new_order.setText("New Order");
        label_new_order.setToolTipText("");
        label_new_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_new_orderMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_new_orderMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_new_orderMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_new_orderMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_new_orderLayout = new javax.swing.GroupLayout(panel_new_order);
        panel_new_order.setLayout(panel_new_orderLayout);
        panel_new_orderLayout.setHorizontalGroup(
            panel_new_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_new_orderLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(label_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_new_orderLayout.setVerticalGroup(
            panel_new_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_new_order, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_products_list.setBackground(new java.awt.Color(21, 76, 121));
        panel_products_list.setPreferredSize(new java.awt.Dimension(160, 32));
        panel_products_list.setRequestFocusEnabled(false);
        panel_products_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_products_listMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_products_listMouseClicked(evt);
            }
        });

        label_products_list.setBackground(new java.awt.Color(255, 255, 255));
        label_products_list.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_products_list.setForeground(new java.awt.Color(255, 255, 255));
        label_products_list.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_product_list.png"))); // NOI18N
        label_products_list.setText("Products List");
        label_products_list.setPreferredSize(new java.awt.Dimension(168, 32));
        label_products_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_products_listMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_products_listMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_products_listMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_products_listMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_products_listLayout = new javax.swing.GroupLayout(panel_products_list);
        panel_products_list.setLayout(panel_products_listLayout);
        panel_products_listLayout.setHorizontalGroup(
            panel_products_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_products_listLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_products_list, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
        );
        panel_products_listLayout.setVerticalGroup(
            panel_products_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_products_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_sales.setBackground(new java.awt.Color(21, 76, 121));
        panel_sales.setPreferredSize(new java.awt.Dimension(160, 32));
        panel_sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_salesMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_salesMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_salesMouseExited(evt);
            }
        });

        label_sales.setBackground(new java.awt.Color(255, 255, 255));
        label_sales.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_sales.setForeground(new java.awt.Color(255, 255, 255));
        label_sales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_sellings.png"))); // NOI18N
        label_sales.setText("Sales");
        label_sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_salesMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                label_salesMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_salesMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_salesMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_salesMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_salesLayout = new javax.swing.GroupLayout(panel_sales);
        panel_sales.setLayout(panel_salesLayout);
        panel_salesLayout.setHorizontalGroup(
            panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_salesLayout.createSequentialGroup()
                .addGap(0, 62, Short.MAX_VALUE)
                .addComponent(label_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_salesLayout.setVerticalGroup(
            panel_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_sales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_close_till.setBackground(new java.awt.Color(21, 76, 121));
        panel_close_till.setPreferredSize(new java.awt.Dimension(160, 32));
        panel_close_till.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_close_tillMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_close_tillMouseClicked(evt);
            }
        });

        label_close_till.setBackground(new java.awt.Color(255, 255, 255));
        label_close_till.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_close_till.setForeground(new java.awt.Color(255, 255, 255));
        label_close_till.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_close_till.png"))); // NOI18N
        label_close_till.setText(" Close Till");
        label_close_till.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_close_tillMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_close_tillMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_close_tillMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_close_tillMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_close_tillLayout = new javax.swing.GroupLayout(panel_close_till);
        panel_close_till.setLayout(panel_close_tillLayout);
        panel_close_tillLayout.setHorizontalGroup(
            panel_close_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_close_tillLayout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addComponent(label_close_till, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_close_tillLayout.setVerticalGroup(
            panel_close_tillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_close_till, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_customers.setBackground(new java.awt.Color(21, 76, 121));
        panel_customers.setPreferredSize(new java.awt.Dimension(233, 32));
        panel_customers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_customersMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_customersMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_customersMouseExited(evt);
            }
        });

        label_customers.setBackground(new java.awt.Color(255, 255, 255));
        label_customers.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_customers.setForeground(new java.awt.Color(255, 255, 255));
        label_customers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon-customer.png"))); // NOI18N
        label_customers.setText("Customers");
        label_customers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_customersMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                label_customersMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_customersMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_customersMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_customersMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_customersLayout = new javax.swing.GroupLayout(panel_customers);
        panel_customers.setLayout(panel_customersLayout);
        panel_customersLayout.setHorizontalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customersLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(label_customers, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_customersLayout.setVerticalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_customers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_orders.setBackground(new java.awt.Color(21, 76, 121));
        panel_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_ordersMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_ordersMouseClicked(evt);
            }
        });

        label_orders.setBackground(new java.awt.Color(255, 255, 255));
        label_orders.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_orders.setForeground(new java.awt.Color(255, 255, 255));
        label_orders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_orders.png"))); // NOI18N
        label_orders.setText(" Orders");
        label_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_ordersMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ordersMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_ordersLayout = new javax.swing.GroupLayout(panel_orders);
        panel_orders.setLayout(panel_ordersLayout);
        panel_ordersLayout.setHorizontalGroup(
            panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ordersLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(label_orders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_ordersLayout.setVerticalGroup(
            panel_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_orders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_faults.setBackground(new java.awt.Color(21, 76, 121));
        panel_faults.setPreferredSize(new java.awt.Dimension(233, 32));
        panel_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_faultsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_faultsMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_faultsMouseExited(evt);
            }
        });

        label_faults.setBackground(new java.awt.Color(255, 255, 255));
        label_faults.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_faults.setForeground(new java.awt.Color(255, 255, 255));
        label_faults.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_not_fix.png"))); // NOI18N
        label_faults.setText("Faults");
        label_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_faultsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                label_faultsMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_faultsMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_faultsMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_faultsMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_faultsLayout = new javax.swing.GroupLayout(panel_faults);
        panel_faults.setLayout(panel_faultsLayout);
        panel_faultsLayout.setHorizontalGroup(
            panel_faultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_faultsLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(label_faults, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        panel_faultsLayout.setVerticalGroup(
            panel_faultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_faults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_new_sale.setBackground(new java.awt.Color(21, 76, 121));
        panel_new_sale.setPreferredSize(new java.awt.Dimension(160, 32));
        panel_new_sale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_new_saleMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_new_saleMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_new_saleMouseExited(evt);
            }
        });

        label_new_sale.setBackground(new java.awt.Color(255, 255, 255));
        label_new_sale.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        label_new_sale.setForeground(new java.awt.Color(255, 255, 255));
        label_new_sale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_new_sale.png"))); // NOI18N
        label_new_sale.setText("New Sale");
        label_new_sale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_new_saleMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                label_new_saleMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_new_saleMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_new_saleMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_new_saleMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_new_saleLayout = new javax.swing.GroupLayout(panel_new_sale);
        panel_new_sale.setLayout(panel_new_saleLayout);
        panel_new_saleLayout.setHorizontalGroup(
            panel_new_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_new_saleLayout.createSequentialGroup()
                .addGap(0, 34, Short.MAX_VALUE)
                .addComponent(label_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_new_saleLayout.setVerticalGroup(
            panel_new_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_new_sale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_menu_barLayout = new javax.swing.GroupLayout(panel_menu_bar);
        panel_menu_bar.setLayout(panel_menu_barLayout);
        panel_menu_barLayout.setHorizontalGroup(
            panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_reports, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_orders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_menu_barLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_products_list, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panel_new_order, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel_check_existing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(panel_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_home, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_menu_barLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_close_till, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_customers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_faults, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panel_menu_barLayout.setVerticalGroup(
            panel_menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_menu_barLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel_home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panel_orders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_check_existing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panel_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_products_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_reports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_close_till, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_customers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_faults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        desktop_pane_main_menu.setBackground(new java.awt.Color(255, 255, 255));
        desktop_pane_main_menu.setToolTipText("");
        desktop_pane_main_menu.setMaximumSize(new java.awt.Dimension(1049, 700));
        desktop_pane_main_menu.setSize(new java.awt.Dimension(1049, 700));

        javax.swing.GroupLayout panel_windowLayout = new javax.swing.GroupLayout(panel_window);
        panel_window.setLayout(panel_windowLayout);
        panel_windowLayout.setHorizontalGroup(
            panel_windowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_windowLayout.createSequentialGroup()
                .addGroup(panel_windowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_windowLayout.createSequentialGroup()
                        .addComponent(panel_menu_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(desktop_pane_main_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_windowLayout.setVerticalGroup(
            panel_windowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_windowLayout.createSequentialGroup()
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(panel_windowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_menu_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_windowLayout.createSequentialGroup()
                        .addComponent(desktop_pane_main_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_window, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_window, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void label_homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_homeMousePressed
        // TODO add your handling code here:
     
    }//GEN-LAST:event_label_homeMousePressed

    private void panel_homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_homeMousePressed
 
    }//GEN-LAST:event_panel_homeMousePressed

    private void label_productsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_productsMousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_label_productsMousePressed

    private void panel_productsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_productsMousePressed
        // TODO add your handling code here:
    
    }//GEN-LAST:event_panel_productsMousePressed

    private void label_reportsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_reportsMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_label_reportsMousePressed

    private void panel_reportsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_reportsMousePressed
        // TODO add your handling code here:
  
    }//GEN-LAST:event_panel_reportsMousePressed

    private void label_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_homeMouseClicked
        // TODO add your handling code here:
       expandHome();
       new MainMenu().setVisible(true);
    }//GEN-LAST:event_label_homeMouseClicked

    private void panel_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_homeMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_panel_homeMouseClicked

    private void label_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_productsMouseClicked
        // TODO add your handling code here:
        expandProducts();
    }//GEN-LAST:event_label_productsMouseClicked

    private void label_reportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_reportsMouseClicked
        // TODO add your handling code here:
        expandReports();
    }//GEN-LAST:event_label_reportsMouseClicked

    private void panel_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_productsMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_panel_productsMouseClicked

    private void panel_reportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_reportsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_reportsMouseClicked

    private void label_check_existingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_check_existingMousePressed

    private void label_check_existingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseClicked
        // TODO add your handling code here:
        OrderList orderlist = new OrderList();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(orderlist).setVisible(true);
        
        panel_check_existing.setBackground(clickedColor);
        panel_new_order.setBackground(defaultColor);
    }//GEN-LAST:event_label_check_existingMouseClicked

    private void label_check_existingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_check_existingMouseExited

    private void label_check_existingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_check_existingMouseEntered

    private void panel_check_existingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_check_existingMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_check_existingMousePressed

    private void panel_check_existingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_check_existingMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_check_existingMouseClicked

    private void label_new_orderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_orderMousePressed

    private void label_new_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMouseClicked
        // TODO add your handling code here:
        NewOrder newOrder = new NewOrder();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(newOrder).setVisible(true);
        
        panel_check_existing.setBackground(defaultColor);
        panel_new_order.setBackground(clickedColor);
    }//GEN-LAST:event_label_new_orderMouseClicked

    private void label_new_orderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_orderMouseExited

    private void label_new_orderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_orderMouseEntered

    private void panel_new_orderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_orderMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_new_orderMousePressed

    private void panel_new_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_orderMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_panel_new_orderMouseClicked

    private void label_products_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_products_listMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_products_listMousePressed

    private void label_products_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_products_listMouseClicked
        // TODO add your handling code here:
        //Open ProductsLis Class
        ProductsList productsList = new ProductsList();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(productsList).setVisible(true);
        
        panel_products_list.setBackground(clickedColor);
        panel_sales.setBackground(defaultColor);
        panel_new_sale.setBackground(defaultColor);
    }//GEN-LAST:event_label_products_listMouseClicked

    private void label_products_listMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_products_listMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_products_listMouseExited

    private void label_products_listMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_products_listMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_products_listMouseEntered

    private void panel_products_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_products_listMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_products_listMousePressed

    private void panel_products_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_products_listMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_products_listMouseClicked

    private void label_salesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_salesMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_salesMousePressed

    private void label_salesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_salesMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_label_salesMouseReleased

    private void label_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_salesMouseClicked
        // TODO add your handling code here:
        Sales sales = new Sales();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(sales).setVisible(true);
        
        panel_products_list.setBackground(defaultColor);
        panel_sales.setBackground(clickedColor);
        panel_new_sale.setBackground(defaultColor);
    }//GEN-LAST:event_label_salesMouseClicked

    private void label_salesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_salesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_salesMouseExited

    private void label_salesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_salesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_salesMouseEntered

    private void panel_salesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_salesMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_salesMousePressed

    private void panel_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_salesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_salesMouseClicked

    private void panel_salesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_salesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_salesMouseExited

    private void label_close_tillMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_close_tillMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_close_tillMousePressed

    private void label_close_tillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_close_tillMouseClicked
        // TODO add your handling code here:
        TillClosing closeTill = new TillClosing();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(closeTill).setVisible(true);
        
        panel_customers.setBackground(defaultColor);
        panel_close_till.setBackground(clickedColor);
        panel_faults.setBackground(defaultColor);
    }//GEN-LAST:event_label_close_tillMouseClicked

    private void label_close_tillMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_close_tillMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_close_tillMouseExited

    private void label_close_tillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_close_tillMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_close_tillMouseEntered

    private void panel_close_tillMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_close_tillMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_close_tillMousePressed

    private void panel_close_tillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_close_tillMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_panel_close_tillMouseClicked

    private void label_customersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_customersMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_customersMousePressed

    private void label_customersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_customersMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_label_customersMouseReleased

    private void label_customersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_customersMouseClicked
        // TODO add your handling code here:
        Customers customers = new Customers();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(customers).setVisible(true);
        
        panel_customers.setBackground(clickedColor);
        panel_close_till.setBackground(defaultColor);
        panel_faults.setBackground(defaultColor);
    }//GEN-LAST:event_label_customersMouseClicked

    private void label_customersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_customersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_customersMouseExited

    private void label_customersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_customersMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_customersMouseEntered

    private void panel_customersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_customersMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_customersMousePressed

    private void panel_customersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_customersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_customersMouseClicked

    private void panel_customersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_customersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_customersMouseExited

    private void label_ordersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ordersMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_ordersMousePressed

    private void label_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ordersMouseClicked
        // TODO add your handling code here:
        expandOrders();
    }//GEN-LAST:event_label_ordersMouseClicked

    private void panel_ordersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_ordersMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_ordersMousePressed

    private void panel_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_ordersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_ordersMouseClicked

    private void label_check_existingMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_label_check_existingMouseReleased

    private void label_faultsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_faultsMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_faultsMousePressed

    private void label_faultsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_faultsMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_label_faultsMouseReleased

    private void label_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_faultsMouseClicked
        // TODO add your handling code here:
        Faults faults = new Faults();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(faults).setVisible(true);
        
        panel_customers.setBackground(defaultColor);
        panel_close_till.setBackground(defaultColor);
        panel_faults.setBackground(clickedColor);
        
       
    }//GEN-LAST:event_label_faultsMouseClicked

    private void label_faultsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_faultsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_faultsMouseExited

    private void label_faultsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_faultsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_faultsMouseEntered

    private void panel_faultsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_faultsMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_faultsMousePressed

    private void panel_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_faultsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_faultsMouseClicked

    private void panel_faultsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_faultsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_faultsMouseExited

    private void label_new_saleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_saleMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_saleMousePressed

    private void label_new_saleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_saleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_saleMouseReleased

    private void label_new_saleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_saleMouseClicked
        // TODO add your handling code here:
        NewSale newSale = new NewSale();
        desktop_pane_main_menu.removeAll();
        desktop_pane_main_menu.add(newSale).setVisible(true);
        
        panel_products_list.setBackground(defaultColor);
        panel_sales.setBackground(defaultColor);
        panel_new_sale.setBackground(clickedColor);
        
    }//GEN-LAST:event_label_new_saleMouseClicked

    private void label_new_saleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_saleMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_saleMouseExited

    private void label_new_saleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_saleMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_new_saleMouseEntered

    private void panel_new_saleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_saleMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_new_saleMousePressed

    private void panel_new_saleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_saleMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_new_saleMouseClicked

    private void panel_new_saleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_saleMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_new_saleMouseExited
    
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop_pane_main_menu;
    private javax.swing.JLabel label_check_existing;
    private javax.swing.JLabel label_close_till;
    private javax.swing.JLabel label_customers;
    private javax.swing.JLabel label_faults;
    private javax.swing.JLabel label_home;
    private javax.swing.JLabel label_new_order;
    private javax.swing.JLabel label_new_sale;
    private javax.swing.JLabel label_orders;
    private javax.swing.JLabel label_products;
    private javax.swing.JLabel label_products_list;
    private javax.swing.JLabel label_reports;
    private javax.swing.JLabel label_sales;
    private javax.swing.JPanel panel_check_existing;
    private javax.swing.JPanel panel_close_till;
    private javax.swing.JPanel panel_customers;
    private javax.swing.JPanel panel_faults;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_home;
    private javax.swing.JPanel panel_menu_bar;
    private javax.swing.JPanel panel_new_order;
    private javax.swing.JPanel panel_new_sale;
    private javax.swing.JPanel panel_orders;
    private javax.swing.JPanel panel_products;
    private javax.swing.JPanel panel_products_list;
    private javax.swing.JPanel panel_reports;
    private javax.swing.JPanel panel_sales;
    private javax.swing.JPanel panel_window;
    // End of variables declaration//GEN-END:variables
}
