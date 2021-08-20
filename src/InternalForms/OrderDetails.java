/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.OrderNotes;
import Registering.Customer;
import Registering.Order;
import Registering.ProductService;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


/**
 *
 * @author user
 */
public class OrderDetails extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewOrder
     */
    ArrayList firstNames = new ArrayList();
    ArrayList faults = new ArrayList();
    ArrayList lastNames = new ArrayList();
    
    Vector faultsTable;
    Vector vecUpdateFaults = new Vector();
    Vector vecUpdateProducts = new Vector();
    Vector vecUpdatePrices = new Vector();
    
    Connection con;
    PreparedStatement ps;
    Statement stmt;
    Customer customer;
    ProductService productService;
    Order order;
    ResultSet rs;
    ResultSetMetaData rsmd;
    
    String orderNo, firstName,  lastName, contactNo, email,  deviceBrand,  
           deviceModel,  serialNumber, importantNotes, stringFaults, 
           stringProducts, stringPrices, status, issueDate, finishedDate, pickedDate; 

    double total, deposit, due;
    
    
    public OrderDetails() {
        initComponents();
        
    }

    public OrderDetails(String _orderNo, String _firstName, String _lastName, String _contactNo, String _email, 
            String _deviceBrand, String _deviceModel, String _serialNumber, String _importantNotes, 
            String _stringFaults, String _stringProducts, String _stringPrices, double _total, double _deposit, 
            double _due, String _status, String _issueDate, String _pickedDate) {
        
        initComponents();
        
        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.contactNo = _contactNo;
        this.email = _email;
        this.deviceBrand = _deviceBrand;
        this.deviceModel = _deviceModel;
        this.serialNumber = _serialNumber;
        this.importantNotes = _importantNotes;
        this.issueDate = _issueDate;
        this.stringFaults = _stringFaults;
        this.stringProducts = _stringProducts;
        this.stringPrices = _stringPrices;
        this.total = _total;
        this.deposit = _deposit;
        this.due = _due;
        this.status = _status;
        this.pickedDate = _pickedDate;
        
        Date date = new Date();
        Timestamp currentDateTime = new Timestamp(date.getTime());
        finishedDate = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(currentDateTime);
        
        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);//avoid auto old value by focus loosing
        jScrollPane_notes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        tableSettings(table_view_faults);
        tableSettings(table_view_products);
        checkEmailFormat();
        accessDbColumn(firstNames, "SELECT * FROM customers", "firstName");
        accessDbColumn(lastNames, "SELECT * FROM customers", "lastName");
        accessDbColumn(faults, "SELECT * FROM faults","faultName");
        listProductService();
        loadSelectedOrder();
        
    }
    
    public void tableSettings (JTable table)
    {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
    }

   public void loadSelectedOrder()
   {
        DefaultTableModel faultsModel = (DefaultTableModel) table_view_faults.getModel();
        faultsModel.setRowCount(0);
        DefaultTableModel productsModel = (DefaultTableModel) table_view_products.getModel();
        TableColumnModel model = table_view_products.getColumnModel();
       
        lbl_auto_order_no.setText(this.orderNo);
        txt_first_name.setText(this.firstName);
        txt_last_name.setText(this.lastName);
        txt_contact.setText(this.contactNo);
        txt_email.setText(this.email);
        txt_brand.setText(this.deviceBrand);
        txt_model.setText(this.deviceModel);
        txt_serial_number.setText(this.serialNumber);
        editor_pane_notes.setText(this.importantNotes);
        txt_total.setText(String.valueOf(this.total));
        txt_deposit.setText(String.valueOf(this.deposit));
        txt_due.setText(String.valueOf(this.due));
        lbl_issued_date_time.setText("created on: " + this.issueDate);
        
        // Array for holding database String 
        String[] arrayFaults = stringFaults.split(",");
        String[] arrayProducts = stringProducts.split(",");
        String[] arrayPrices = stringPrices.split(",");
        Vector vecPrices = new Vector();
        
        //Iterate arrayProducts and pass elements to faults table
        for (Object objFaults : arrayFaults)
        {
            faultsModel.addRow(new Object[] {objFaults});
        }
        
        //Iterate arrayProducts and pass elements to Products table
        for (Object objProducts : arrayProducts)
        {
            productsModel.addRow(new Object[] {objProducts});
        }  
        
        // Pass arrayPrices to a vector and add as a new column
        vecPrices.addAll(Arrays.asList(arrayPrices)); 
        productsModel.addColumn("Price", vecPrices);
            
        model.getColumn(1).setMaxWidth(80);
   }
   
    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","root","hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void autoCompleteFromDb(ArrayList list, String text, JTextField field)
    {
        String complete = "";
        int start = text.length();
        int last = text.length();
        
        for(int i = 0 ; i < list.size() ; i++)
        {
            if(list.get(i).toString().startsWith(text))
            {
                complete = list.get(i).toString();
                last = complete.length();
                break;
            }
        }
        
        if(last > start)
        {
            field.setText(complete);
            field.setCaretPosition(last);
            field.moveCaretPosition(start);
        }
    }
    
    public void listProductService()
    {
        AutoCompleteDecorator.decorate(combo_box_product_service);

        try {
            dbConnection();
            String query = "SELECT productService FROM products";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                combo_box_product_service.addItem(rs.getString("productService"));
            }
            con.close();
            ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getPriceSum()
    {
        double sum = 0;
        for(int i = 0; i < table_view_products.getRowCount(); i++)
        {
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 1).toString());
        }
        
        txt_total.setText(Double.toString(sum));
        txt_due.setText(txt_total.getText()); //set total to the due field
    }
    
    public final void accessDbColumn(ArrayList list, String query, String columnName)
    {
        try {
            dbConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
                while (rs.next())
                {
                    list.add(rs.getString(columnName));
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void checkEmailFormat()
    {
        txt_email.setInputVerifier(new InputVerifier() {
           
           Border originalBorder;
           String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
           String email = txt_email.getText();
           
           
           @Override
           public boolean verify(JComponent input) {
               JTextField comp = (JTextField) input;
               //return !comp.getText().trim().isEmpty();
               return comp.getText().matches(emailFormat) | comp.getText().trim().isEmpty();
           }
           
           @Override
           public boolean shouldYieldFocus(JComponent input)
           {
               boolean isValid = verify(input);
               
               if (!isValid)
               {
                   originalBorder = originalBorder == null ? input.getBorder() : originalBorder;
                  //input.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                  input.setBorder(new LineBorder(Color.RED));
               } 
               else 
               {
                    if(originalBorder != null) {
                        input.setBorder(originalBorder);
                        originalBorder = null;
                    }
                }
                return isValid;
            }
        });
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        desktop_pane_order_details = new javax.swing.JDesktopPane();
        panel_order_details = new javax.swing.JPanel();
        lbl_order_no = new javax.swing.JLabel();
        lbl_first_name = new javax.swing.JLabel();
        lbl_last_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_brand = new javax.swing.JLabel();
        lbl_model = new javax.swing.JLabel();
        lbl_sn = new javax.swing.JLabel();
        lbl_fault = new javax.swing.JLabel();
        lbl_auto_order_no = new javax.swing.JLabel();
        lbl_service_product = new javax.swing.JLabel();
        lbl_price = new javax.swing.JLabel();
        lbl_deposit = new javax.swing.JLabel();
        lbl_due = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_last_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_contact = new javax.swing.JFormattedTextField();
        txt_brand = new javax.swing.JTextField();
        txt_model = new javax.swing.JTextField();
        txt_serial_number = new javax.swing.JTextField();
        txt_fault = new javax.swing.JTextField();
        combo_box_product_service = new javax.swing.JComboBox<>();
        txt_deposit = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_view_faults = new javax.swing.JTable();
        icon_add_table_view = new javax.swing.JLabel();
        txt_due = new javax.swing.JTextField();
        lbl_issued_date_time = new javax.swing.JLabel();
        panel_buttons = new javax.swing.JPanel();
        btn_fix = new javax.swing.JButton();
        btn_not_fix = new javax.swing.JButton();
        btn_save_changes = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        btn_notes = new javax.swing.JButton();
        jScrollPane_notes = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        desktop_pane_order_details.setBackground(new java.awt.Color(255, 255, 255));
        desktop_pane_order_details.setPreferredSize(new java.awt.Dimension(1049, 700));

        panel_order_details.setPreferredSize(new java.awt.Dimension(1049, 700));

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_order_no.setText("Order Number");

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_brand.setText("Device Brand");

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_model.setText("Device Model");

        lbl_sn.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_sn.setText("Serial Number");

        lbl_fault.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_fault.setText("Faults");

        lbl_auto_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_auto_order_no.setText("autoGen");

        lbl_service_product.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_service_product.setText("Service | Product");

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_price.setText("Total €");

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_deposit.setText("Deposit €");

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_due.setText("Due €");

        txt_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_first_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_first_nameActionPerformed(evt);
            }
        });
        txt_first_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_first_nameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_first_nameKeyReleased(evt);
            }
        });

        txt_last_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_last_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_last_nameActionPerformed(evt);
            }
        });
        txt_last_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_last_nameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_last_nameKeyReleased(evt);
            }
        });

        txt_email.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_email.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emailKeyPressed(evt);
            }
        });

        try {
            txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(0##) ###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_contact.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactActionPerformed(evt);
            }
        });
        txt_contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactKeyReleased(evt);
            }
        });

        txt_brand.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_brand.setMinimumSize(new java.awt.Dimension(63, 20));
        txt_brand.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_brandActionPerformed(evt);
            }
        });

        txt_model.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_model.setPreferredSize(new java.awt.Dimension(63, 20));

        txt_serial_number.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_serial_number.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_serial_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_serial_numberActionPerformed(evt);
            }
        });
        txt_serial_number.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_serial_numberKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_serial_numberKeyReleased(evt);
            }
        });

        txt_fault.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_fault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_faultActionPerformed(evt);
            }
        });
        txt_fault.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_faultKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_faultKeyReleased(evt);
            }
        });

        combo_box_product_service.setEditable(true);
        combo_box_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        combo_box_product_service.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select or Type" }));
        combo_box_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_product_serviceActionPerformed(evt);
            }
        });
        combo_box_product_service.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                combo_box_product_serviceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_box_product_serviceKeyReleased(evt);
            }
        });

        txt_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_deposit.setForeground(new java.awt.Color(51, 51, 255));
        txt_deposit.setText("0");
        txt_deposit.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_depositActionPerformed(evt);
            }
        });
        txt_deposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_depositKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_depositKeyReleased(evt);
            }
        });

        txt_total.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_total.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        txt_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_totalKeyPressed(evt);
            }
        });

        table_view_products.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        table_view_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product | Service"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_productsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_products);

        table_view_faults.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        table_view_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_faultsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_view_faults);

        icon_add_table_view.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add_to_product_table.png"))); // NOI18N
        icon_add_table_view.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                icon_add_table_viewMousePressed(evt);
            }
        });

        txt_due.setEditable(false);
        txt_due.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_due.setForeground(new java.awt.Color(255, 0, 51));
        txt_due.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dueActionPerformed(evt);
            }
        });

        lbl_issued_date_time.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_issued_date_time.setText("createdOn");

        btn_fix.setBackground(new java.awt.Color(0, 153, 102));
        btn_fix.setFont(new java.awt.Font("Lucida Grande", 1, 22)); // NOI18N
        btn_fix.setForeground(new java.awt.Color(255, 255, 255));
        btn_fix.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_fix_order.png"))); // NOI18N
        btn_fix.setText("Fix");
        btn_fix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fixActionPerformed(evt);
            }
        });

        btn_not_fix.setBackground(new java.awt.Color(255, 51, 51));
        btn_not_fix.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_not_fix.setForeground(new java.awt.Color(255, 255, 255));
        btn_not_fix.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_not_fix.png"))); // NOI18N
        btn_not_fix.setText("Not Fixed");
        btn_not_fix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_not_fixActionPerformed(evt);
            }
        });

        btn_save_changes.setBackground(new java.awt.Color(21, 76, 121));
        btn_save_changes.setFont(new java.awt.Font("Lucida Grande", 1, 17)); // NOI18N
        btn_save_changes.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_changes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save_changes.png"))); // NOI18N
        btn_save_changes.setText("Save Changes");
        btn_save_changes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_changesActionPerformed(evt);
            }
        });

        btn_print.setBackground(new java.awt.Color(21, 76, 121));
        btn_print.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_notes.setBackground(new java.awt.Color(21, 76, 121));
        btn_notes.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_notes.setForeground(new java.awt.Color(255, 255, 255));
        btn_notes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_notes.png"))); // NOI18N
        btn_notes.setText("Notes");
        btn_notes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_notesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_buttonsLayout = new javax.swing.GroupLayout(panel_buttons);
        panel_buttons.setLayout(panel_buttonsLayout);
        panel_buttonsLayout.setHorizontalGroup(
            panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buttonsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btn_fix, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_not_fix, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_save_changes, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_buttonsLayout.setVerticalGroup(
            panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buttonsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_fix, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_not_fix, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save_changes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jScrollPane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 16))); // NOI18N
        editor_pane_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        editor_pane_notes.setEnabled(false);
        jScrollPane_notes.setViewportView(editor_pane_notes);

        javax.swing.GroupLayout panel_order_detailsLayout = new javax.swing.GroupLayout(panel_order_details);
        panel_order_details.setLayout(panel_order_detailsLayout);
        panel_order_detailsLayout.setHorizontalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_order_no)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_auto_order_no))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_first_name)
                            .addGap(18, 18, 18)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                                    .addComponent(lbl_last_name)
                                    .addGap(20, 20, 20))
                                .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                    .addComponent(lbl_contact)
                                    .addGap(11, 11, 11)))
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_contact, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(txt_last_name))))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_model)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_brand)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbl_issued_date_time)
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_sn)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_serial_number, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jScrollPane_notes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_service_product)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(icon_add_table_view))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_due)
                        .addGap(0, 0, 0)
                        .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(lbl_deposit)
                        .addGap(0, 0, 0)
                        .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_price)
                        .addGap(0, 0, 0)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_fault)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_fault, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panel_buttons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_order_detailsLayout.setVerticalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_issued_date_time, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_fault)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_auto_order_no, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_order_no, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addComponent(txt_fault, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(icon_add_table_view)
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_service_product)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_deposit)
                                .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_price)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbl_due))))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_first_name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_last_name)
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_contact)
                            .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_email)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_brand))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_model))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_sn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(panel_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        desktop_pane_order_details.setLayer(panel_order_details, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_order_detailsLayout = new javax.swing.GroupLayout(desktop_pane_order_details);
        desktop_pane_order_details.setLayout(desktop_pane_order_detailsLayout);
        desktop_pane_order_detailsLayout.setHorizontalGroup(
            desktop_pane_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
        );
        desktop_pane_order_detailsLayout.setVerticalGroup(
            desktop_pane_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(desktop_pane_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_last_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_last_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameActionPerformed

    private void txt_first_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_first_nameActionPerformed
    }//GEN-LAST:event_txt_first_nameActionPerformed

    private void txt_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_brandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_brandActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
        
        double sum = 0;
        for(int i = 0; i < table_view_products.getRowCount(); i++)
        {
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 1).toString());
        }
        
        txt_total.setText(Double.toString(sum));
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_depositKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyReleased
        // TODO add your handling code here:

        //Calculate deposit paid and display due value
        if (txt_deposit.getText() == null || txt_deposit.getText().trim().isEmpty())
        {
            txt_due.setText(txt_total.getText());
            txt_deposit.setText(Double.toString(0));
        }
        else
        {
            double priceTotal = Double.parseDouble(txt_total.getText());
            deposit = Double.parseDouble(txt_deposit.getText());
            total = priceTotal - deposit;

            txt_due.setText(Double.toString(total));
        }
    }//GEN-LAST:event_txt_depositKeyReleased

    private void txt_serial_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serial_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serial_numberActionPerformed

    private void txt_first_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_BACKSPACE:
                break;
            
            case KeyEvent.VK_ENTER:
                txt_first_name.setText(txt_first_name.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_first_name.getText();
                    autoCompleteFromDb(firstNames, text, txt_first_name);
            });
        }
    }//GEN-LAST:event_txt_first_nameKeyPressed

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailKeyPressed

    private void txt_serial_numberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serial_numberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serial_numberKeyPressed

    private void txt_serial_numberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serial_numberKeyReleased
        // TODO add your handling code here:
        txt_serial_number.setText(txt_serial_number.getText().toUpperCase());
    }//GEN-LAST:event_txt_serial_numberKeyReleased

    private void txt_first_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameKeyReleased

    private void txt_last_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyReleased

    private void txt_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalKeyPressed
         // TODO add your handling code here:
        // Accepts number only 
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            
            txt_total.setEditable(false);
        }
        else
        {
            txt_total.setEditable(true);
        }       
    }//GEN-LAST:event_txt_totalKeyPressed

    private void txt_depositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyPressed
        // TODO add your handling code here:
        // Accepts number only
        char c = evt.getKeyChar();
        
        if(Character.isLetter(c))
        {
            txt_deposit.setEditable(false);
        }
        else
        {
            txt_deposit.setEditable(true);
        }
    }//GEN-LAST:event_txt_depositKeyPressed

    private void txt_faultKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_faultKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_faultKeyReleased

    private void txt_faultKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_faultKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_BACKSPACE:
            break;

            case KeyEvent.VK_ENTER:
            txt_fault.setText(txt_fault.getText());
            break;
            default:
            EventQueue.invokeLater(() -> {
                String text = txt_fault.getText();
                autoCompleteFromDb(faults, text, txt_fault);
            });
        }
    }//GEN-LAST:event_txt_faultKeyPressed

    private void txt_faultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_faultActionPerformed
        // TODO add your handling code here:
        String faultText = txt_fault.getText();
        Vector faultsList = new Vector();
        DefaultTableModel dtm = (DefaultTableModel)table_view_faults.getModel();
        
        if (faultText.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please add a Fault!", "Faults", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try 
            {
                dbConnection();
                String queryCheck = "SELECT * FROM faults WHERE faultName = '" +faultText+ "'";
                ps = con.prepareStatement(queryCheck);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst())
                {
                    int confirmInsertion = JOptionPane.showConfirmDialog(null, "Do you want to add a new fault ?", "Add New Fault", JOptionPane.YES_NO_OPTION);
                    if(confirmInsertion == 0)
                    {
                        String query = "INSERT INTO faults (faultName) VALUES(?)";
                        ps = con.prepareStatement(query);
                        ps.setString(1, faultText);
                        ps.executeUpdate();

                        faultsList.add(faultText);
                        dtm.addRow(faultsList);
                        txt_fault.setText("");
                    }
                    else
                    {
                        txt_fault.setText("");
                    }
                }
                else
                {
                    faultsList.add(faultText);
                    dtm.addRow(faultsList);
                    txt_fault.setText("");
                }

            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txt_faultActionPerformed

    private void combo_box_product_serviceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_box_product_serviceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_product_serviceKeyPressed

    private void combo_box_product_serviceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_box_product_serviceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_product_serviceKeyReleased

    private void combo_box_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_product_serviceActionPerformed
//        addProductServiceToTable();
    }//GEN-LAST:event_combo_box_product_serviceActionPerformed

    private void icon_add_table_viewMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_add_table_viewMousePressed
        // TODO add your handling code here:
        Vector vector = new Vector();
        String productName = combo_box_product_service.getSelectedItem().toString();
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();
        
        if(productName.isEmpty() || productName.matches("Select or Type"))
        {
            JOptionPane.showMessageDialog(null, "Please select a Product | Service!", "Service | Product", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try 
            {
                dbConnection();
                String query = "SELECT * FROM products WHERE productService = '" + productName + "'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                
                if (!rs.isBeforeFirst())
                {
                   JOptionPane.showMessageDialog(null, "Item not found!", "Service | Product", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    while (rs.next())
                    {
                        vector.add(rs.getString("productService"));
                        vector.add(rs.getDouble("price"));
                    }

                    dtm.addRow(vector);
                    combo_box_product_service.setSelectedIndex(-1);
                    
                    // Sum price column and set into total textField
                    getPriceSum();
                }
            } catch (SQLException ex) {
                    Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_icon_add_table_viewMousePressed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        // TODO add your handling code here:
        //Delete products|Service item of the selected row (Function is called with 2 clicks) 
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();
        
        if(evt.getClickCount() == 2)
        {
          int confirmDeletion = JOptionPane.showConfirmDialog(null, "Delete This Item ?", "Delete Product|Service", JOptionPane.YES_NO_OPTION);
          if(confirmDeletion == 0)
          {
              dtm.removeRow(table_view_products.getSelectedRow());
              // Sum price column and set into total textField
              getPriceSum();
          }
        }
    }//GEN-LAST:event_table_view_productsMouseClicked

    private void table_view_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_faultsMouseClicked
        // TODO add your handling code here:
        //Delete fault item of the selected row (Function is called with 2 clicks) 
        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();
        
        if(evt.getClickCount() == 2)
        {
          int confirmDeletion = JOptionPane.showConfirmDialog(null, "Delete This Item ?", "Delete Faults", JOptionPane.YES_NO_OPTION);
          if(confirmDeletion == 0)
              dtm.removeRow(table_view_faults.getSelectedRow());
        }
    }//GEN-LAST:event_table_view_faultsMouseClicked

    private void txt_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_depositActionPerformed
        // TODO add your handling code here:
                //Calculate deposit paid and display due value
    }//GEN-LAST:event_txt_depositActionPerformed

    private void txt_dueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dueActionPerformed

    private void txt_last_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_BACKSPACE:
            break;

            case KeyEvent.VK_ENTER:
            txt_last_name.setText(txt_last_name.getText());
            break;
            default:
            EventQueue.invokeLater(() -> {
                String text = txt_last_name.getText();
                autoCompleteFromDb(lastNames, text, txt_last_name);
            });
        }
    }//GEN-LAST:event_txt_last_nameKeyPressed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        
        firstName = txt_first_name.getText();
        lastName = txt_last_name.getText();
        contactNo = txt_contact.getText();
        email = txt_email.getText();
        
        try {
            dbConnection();
            String query = "SELECT * FROM customers WHERE contactNo = '" +contactNo+ "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            //show a message if a costumer is not found in the db
            if (!rs.isBeforeFirst() && firstName.trim().isEmpty() && lastName.trim().isEmpty())
                JOptionPane.showMessageDialog(null, "Customer not found in the Database", "New Order", JOptionPane.ERROR_MESSAGE);
            
            //add a new costumer if not exist AND fields are not empty
            else if (!rs.isBeforeFirst() && !firstName.trim().isEmpty() && !lastName.trim().isEmpty())
            {
                int confirmInsertion = JOptionPane.showConfirmDialog(null, "Do you want to add a new Customer ?", "Add New Customer", JOptionPane.YES_NO_OPTION);
                if(confirmInsertion == 0)
                {
                    customer = new Customer(firstName, lastName, contactNo, email);
                    String insertQuery = "INSERT INTO customers (firstName, lastName, contactNo, email) VALUES(?, ?, ?, ?)";
                    
                    ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getContactNo());
                    ps.setString(4, customer.getEmail());
                    ps.executeUpdate();
                    txt_brand.requestFocus();
                    
                    rs = ps.getGeneratedKeys();
                    if (rs.next())
                        customer.setCustomerID(rs.getInt(1));
                }
                else
                {
                    txt_first_name.setText("");
                    txt_last_name.setText("");
                    txt_contact.setText("");
                    txt_email.setText("");
                }
            } 
            else
            {
                String fillQuery = "Select * from customers WHERE contactNo = '" +contactNo+ "'";
                ps = con.prepareStatement(fillQuery);
                rs = ps.executeQuery();
                
                while(rs.next())
                {
                    txt_first_name.setText(rs.getString("firstName"));
                    txt_last_name.setText(rs.getString("lastName"));
                    txt_contact.setText(rs.getString("contactNo"));
                    txt_email.setText(rs.getString("email"));
                    int id = rs.getInt("customerID");
                    System.out.println("Customer ID: " + id);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactKeyReleased

    private void btn_fixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fixActionPerformed
        // TODO add your handling code here:
        
       int confirmFixing = JOptionPane.showConfirmDialog(null, "Do you really want to Tag Order: " 
               + orderNo + " as Fixed Order", "Update Order", JOptionPane.YES_NO_OPTION);
       
       if (confirmFixing == 0)
       {
           status = "Fixed";
           
            try {
                dbConnection();
                String query = "UPDATE orderDetails SET status = '" + status +"', finishedDate = '" 
                        + finishedDate +"' WHERE orderNo = '"+ orderNo + "'";
                
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                
                OrderFixed fixedOrder = new OrderFixed(orderNo, firstName, lastName, contactNo, email, deviceBrand,
                        deviceModel, serialNumber, importantNotes, stringFaults, stringProducts, stringPrices, total,
                        deposit, due, status, issueDate, finishedDate, pickedDate);
                
                desktop_pane_order_details.removeAll();
                desktop_pane_order_details.add(fixedOrder).setVisible(true);
                
            } catch (SQLException ex) {
                Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }//GEN-LAST:event_btn_fixActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        
        new Print(orderNo, firstName, lastName, contactNo, email, deviceBrand, deviceModel, 
                serialNumber, stringFaults, importantNotes, table_view_products, total, 
                deposit, due, issueDate).setVisible(true);
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_not_fixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_not_fixActionPerformed
        // TODO add your handling code here:
       int confirmFixing = JOptionPane.showConfirmDialog(null, "Do you really want to Tag Order: "
               + orderNo + " as 'NOT FIXABLE ORDER' ?", "Update Order", JOptionPane.YES_NO_OPTION);
       
       if (confirmFixing == 0)
       {
           status = "Not Fixable";
            try {
                dbConnection();
                String query = "UPDATE orderDetails SET status = '" + status + "', finishedDate = '" + finishedDate +"'"
                        + "WHERE orderNo = '"+ orderNo + "'";
                
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                
                OrderNotFixed orderNotFixed = new OrderNotFixed(orderNo, firstName, lastName, contactNo, email, deviceBrand,
                        deviceModel, serialNumber, importantNotes, stringFaults, stringProducts, stringPrices, total,
                        deposit, due, status, issueDate, finishedDate, pickedDate);
                
                desktop_pane_order_details.removeAll();
                desktop_pane_order_details.add(orderNotFixed).setVisible(true);
                
            } catch (SQLException ex) {
                Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }//GEN-LAST:event_btn_not_fixActionPerformed

    private void btn_save_changesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_changesActionPerformed
        // Prevent empty fields into the DAtabase
        if (txt_first_name.getText().trim().isEmpty() | txt_last_name.getText().trim().isEmpty() | 
                txt_contact.getText().trim().isEmpty() | txt_brand.getText().trim().isEmpty() | 
                txt_model.getText().trim().isEmpty() | txt_serial_number.getText().trim().isEmpty() | 
                table_view_products.getRowCount() == 0 | table_view_faults.getRowCount() == 0)  
        {
            JOptionPane.showMessageDialog(null, "Please, check Empty fields", "Update Order", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
        
            firstName = txt_first_name.getText();
            lastName = txt_last_name.getText();
            contactNo = txt_contact.getText();
            email = txt_email.getText();
            deviceBrand = txt_brand.getText();
            deviceModel = txt_model.getText();
            serialNumber = txt_serial_number.getText();
            importantNotes = editor_pane_notes.getText();
            total = Double.parseDouble(txt_total.getText());
            deposit = Double.parseDouble(txt_deposit.getText());
            due = Double.parseDouble(txt_due.getText());

            java.util.Date date = new java.util.Date();
            java.sql.Timestamp currentDateTime = new java.sql.Timestamp(date.getTime());
            String updateDate = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(currentDateTime);

            //Empty vector before looping, this is useful for comparing values from tableView and database
            vecUpdateFaults.removeAllElements();
            vecUpdateProducts.removeAllElements();
            vecUpdatePrices.removeAllElements();

             //pass table items from faults and products table to vector 
            for(int i = 0; i < table_view_faults.getRowCount(); i++)
            {
                 vecUpdateFaults.add(table_view_faults.getValueAt(i, 0));
            }

            for(int j = 0; j < table_view_products.getRowCount(); j++)
            {
                vecUpdateProducts.add(table_view_products.getValueAt(j, 0));
                vecUpdatePrices.add(table_view_products.getValueAt(j, 1));
            }


              //Remove Brackets from the vector to pass to Database  
             stringFaults = vecUpdateFaults.toString().replace("[", "").replace("]", "");
             stringProducts = vecUpdateProducts.toString().replace("[", "").replace("]", "");
             stringPrices = vecUpdatePrices.toString().replace("[", "").replace("]", "");

            String tableFaults = stringFaults.replace(",","").replace(" ", "");
            String tableProducts = stringProducts.replace(",", "").replace(" ", "");
            String tablePrices = stringPrices.replace(",", "").replace(" ", "");

            try 
            {
                dbConnection();

                String queryCheck = "SELECT * FROM orderDetails WHERE orderNo = '" + orderNo + "'";
                ps = con.prepareStatement(queryCheck);
                rs = ps.executeQuery();

                while (rs.next())
                {
                    // Get DB strings convert into range of characters for comparing to the tableView ones
                    String dbStringFaults = rs.getString("fault").replace(",", "").replace(" ","");
                    String dbStringProducts = rs.getString("productService").replace(",", "").replace(" ","");
                    String dbStringPrices = rs.getString("price").replace(",", "").replace(" ","");

                    // IF all values are the same, dont do update
                    if(firstName.equals(rs.getString("firstName")) && lastName.equals(rs.getString("lastName"))
                            && contactNo.equals(rs.getString("contactNo")) && email.equals(rs.getString("email"))
                            && deviceBrand.equals(rs.getString("deviceBrand")) && deviceModel.equals(rs.getString("deviceModel"))
                            && serialNumber.equals(rs.getString("serialNumber")) && tableFaults.equals(dbStringFaults)
                            && importantNotes.equals(rs.getString("importantNotes")) && tableProducts.equals(dbStringProducts) 
                            && deposit == rs.getDouble("deposit") && total == rs.getDouble("total") && tablePrices.equals(dbStringPrices) 
                            && deposit == rs.getDouble("deposit") && total == rs.getDouble("total") && due == rs.getDouble("due"))
                    {
                       JOptionPane.showMessageDialog(null, "No changes to be Updated", "Update Order Details", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        int confirmUpdate = JOptionPane.showConfirmDialog(null, "Do you really want to save changes on order " 
                                + this.orderNo + " ?", "Update Order Details", JOptionPane.YES_NO_OPTION);

                        if (confirmUpdate == 0)
                        {
                            String queryUpdate = "UPDATE orderDetails SET firstName ='" + firstName + "',"
                                    + "lastName ='" + lastName + "', contactNo ='" + contactNo + "',"
                                    +"email ='" + email + "', deviceBrand ='" + this.deviceBrand +"'," 
                                    +"deviceModel ='" + deviceModel + "', serialNumber ='" + serialNumber + "',"
                                    +"fault = '" + stringFaults + "', importantNotes = '" + importantNotes +"'," 
                                    +"productService ='" + stringProducts + "', price ='" + stringPrices +"',"
                                    +"deposit = '" + this.deposit +"', total ='" + this.total + "',"
                                    +"due = '" + this.due + "',issuedDate ='" + updateDate + "'"
                                    + " WHERE orderNo = '" + this.orderNo + "'";

                            ps = con.prepareStatement(queryUpdate);
                            ps.executeUpdate();

                            JOptionPane.showMessageDialog(this,  "Order " + orderNo + " Updated Successfully!");
                        }
                    } 
                }
            } catch (SQLException ex) 
            {
                Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_save_changesActionPerformed

    private void btn_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notesActionPerformed
        // TODO add your handling code here:
        OrderNotes orderNotes = new OrderNotes(orderNo);
        orderNotes.setVisible(true);
        
    }//GEN-LAST:event_btn_notesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fix;
    private javax.swing.JButton btn_not_fix;
    private javax.swing.JButton btn_notes;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_save_changes;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> combo_box_product_service;
    private javax.swing.JDesktopPane desktop_pane_order_details;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JLabel icon_add_table_view;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane_notes;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbl_auto_order_no;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_fault;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_issued_date_time;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_service_product;
    private javax.swing.JLabel lbl_sn;
    private javax.swing.JPanel panel_buttons;
    private javax.swing.JPanel panel_order_details;
    private javax.swing.JTable table_view_faults;
    private javax.swing.JTable table_view_products;
    private javax.swing.JTextField txt_brand;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_deposit;
    private javax.swing.JTextField txt_due;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fault;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_model;
    private javax.swing.JTextField txt_serial_number;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
