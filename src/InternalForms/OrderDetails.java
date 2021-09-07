/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.DepositPayment;
import Forms.DepositUpdatePayment;
import Forms.PrintOrder;
import Forms.OrderNotes;
import Model.CompletedOrder;
import Model.Customer;
import Model.Order;
import Model.ProductService;
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
 * @author HermanCosta
 */
public class OrderDetails extends javax.swing.JInternalFrame {

    ArrayList firstNames = new ArrayList();
    ArrayList faults = new ArrayList();
    ArrayList lastNames = new ArrayList();

    Vector faultsTable;
    Vector vecUpdateFaults = new Vector();
    Vector vecUpdateProducts = new Vector();
    Vector vecUpdateQty = new Vector();
    Vector vecUpdatePriceTotal = new Vector();
    Vector vecUpdateUnitPrice = new Vector();

    Connection con;
    PreparedStatement ps;
    Statement stmt;
    Customer customer;
    ProductService productService;
    Order order;
    CompletedOrder completedOrder;
    ResultSet rs;
    ResultSetMetaData rsmd;

    String orderNo, firstName, lastName, contactNo, email, deviceBrand,
            deviceModel, serialNumber, importantNotes, stringFaults,
            stringProducts, stringQty, stringUnitPrice, stringPriceTotal, status, issueDate, finishedDate, pickedDate;

    double total, deposit, cashDeposit, cardDeposit, due;
    double cash, card, changeTotal;

    public OrderDetails() {
        initComponents();
    }

    public OrderDetails(Order _order, CompletedOrder _completedOrders) {
        initComponents();
        this.order = _order;
        this.completedOrder = _completedOrders;

        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        finishedDate = new SimpleDateFormat("yyyy/MM/dd").format(currentDate);
        order.setFinishDate(finishedDate);

        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
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
        accessDbColumn(faults, "SELECT * FROM faults", "faultName");
        listProductService();
        loadSelectedOrder();
    }

    public void tableSettings(JTable table) {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
    }

    public void loadSelectedOrder() {
        lbl_auto_order_no.setText(order.getOrderNo());
        txt_first_name.setText(order.getFirstName());
        txt_last_name.setText(order.getLastName());
        txt_contact.setText(order.getContactNo());
        txt_email.setText(order.getEmail());
        txt_brand.setText(order.getBrand());
        txt_model.setText(order.getModel());
        txt_serial_number.setText(order.getSerialNumber());
        editor_pane_important_notes.setText(order.getImportantNotes());
        txt_total.setText(String.valueOf(order.getTotal()));
        txt_deposit.setText(String.valueOf(order.getDeposit()));
        txt_due.setText(String.valueOf(order.getDue()));
        lbl_issue_date.setText("Created on: " + order.getIssueDate() + " - by " + order.getUsername());

        // Pass arrayPrices to a vector and add as a new column
        Vector vecFaults = new Vector();
        Vector vecProducts = new Vector();
        Vector vecQty = new Vector();
        Vector vecUnitPrice = new Vector();
        Vector vecPriceTotal = new Vector();

        vecFaults.addAll(Arrays.asList(order.getStringFaults().split(",")));
        vecProducts.addAll(Arrays.asList(order.getStringProducts().replaceAll("   ", " ").split(",")));
        vecQty.addAll(Arrays.asList(order.getStringQty().replaceAll("  ", " ").split(",")));
        vecUnitPrice.addAll(Arrays.asList(order.getUnitPrice().replaceAll("  ", " ").split(",")));
        vecPriceTotal.addAll(Arrays.asList(order.getPriceTotal().replaceAll("  ", " ").split(",")));

        DefaultTableModel faultsModel = (DefaultTableModel) table_view_faults.getModel();
        DefaultTableModel productsModel = (DefaultTableModel) table_view_products.getModel();
        TableColumnModel tableModel = table_view_products.getColumnModel();

        //Add New Columns into the table_view_products with data as a vector
        faultsModel.addColumn("Faults Description", vecFaults);
        productsModel.addColumn("Product | Service", vecProducts);
        productsModel.addColumn("Qty", vecQty);
        productsModel.addColumn("Unit €", vecUnitPrice);
        productsModel.addColumn("Total €", vecPriceTotal);

        // Set width size for columns through its index
        tableModel.getColumn(1).setMaxWidth(40);
        tableModel.getColumn(2).setMaxWidth(80);
        tableModel.getColumn(3).setMaxWidth(80);

        // Disable editing
        table_view_products.setDefaultEditor(Object.class, null);
        table_view_faults.setDefaultEditor(Object.class, null);
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void autoCompleteFromDb(ArrayList list, String text, JTextField field) {
        String complete = "";
        int start = text.length();
        int last = text.length();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().startsWith(text)) {
                complete = list.get(i).toString();
                last = complete.length();
                break;
            }
        }

        if (last > start) {
            field.setText(complete);
            field.setCaretPosition(last);
            field.moveCaretPosition(start);
        }
    }

    public void listProductService() {
        AutoCompleteDecorator.decorate(combo_box_product_service);

        try {
            dbConnection();
            
            String query = "SELECT productService FROM products";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                combo_box_product_service.addItem(rs.getString("productService"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPriceSum() {
        double sum = 0;
        for (int i = 0; i < table_view_products.getRowCount(); i++) {
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 3).toString());
        }

        txt_total.setText(Double.toString(sum));
        txt_due.setText(txt_total.getText()); //set total to the due field
    }

    public final void accessDbColumn(ArrayList list, String query, String columnName) {
        try {
            dbConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString(columnName));
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void checkEmailFormat() {
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
            public boolean shouldYieldFocus(JComponent input) {
                boolean isValid = verify(input);

                if (!isValid) {
                    originalBorder = originalBorder == null ? input.getBorder() : originalBorder;
                    //input.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                    input.setBorder(new LineBorder(Color.RED));
                } else {
                    if (originalBorder != null) {
                        input.setBorder(originalBorder);
                        originalBorder = null;
                    }
                }
                return isValid;
            }
        });
    }

    public void addEventNote(String updateNote) {
        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        String dateFormat = new SimpleDateFormat("yyyy/MM/dd").format(currentDate);

        try {
            dbConnection();

            String note = "Order tagged as '" + updateNote + "' by " + order.getUsername();
            String user = "System";

            String queryUpdate = "INSERT INTO orderNotes(orderNo, date, note, user) VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(queryUpdate);
            ps.setString(1, order.getOrderNo());
            ps.setString(2, dateFormat);
            ps.setString(3, note); // add new Deposit note
            ps.setString(4, user);
            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DepositPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jScrollPane3 = new javax.swing.JScrollPane();
        table_view_faults = new javax.swing.JTable();
        icon_add_table_view = new javax.swing.JLabel();
        txt_due = new javax.swing.JTextField();
        lbl_issue_date = new javax.swing.JLabel();
        panel_buttons = new javax.swing.JPanel();
        btn_fix = new javax.swing.JButton();
        btn_not_fix = new javax.swing.JButton();
        btn_save_changes = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        btn_notes = new javax.swing.JButton();
        jScrollPane_notes = new javax.swing.JScrollPane();
        editor_pane_important_notes = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

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

        table_view_faults.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        table_view_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        lbl_issue_date.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_issue_date.setText("createdOn");
        lbl_issue_date.setEnabled(false);

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
        jScrollPane_notes.setEnabled(false);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_important_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 16))); // NOI18N
        editor_pane_important_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jScrollPane_notes.setViewportView(editor_pane_important_notes);

        table_view_products.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        table_view_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_view_products.setEnabled(false);
        table_view_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_productsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_products);

        javax.swing.GroupLayout panel_order_detailsLayout = new javax.swing.GroupLayout(panel_order_details);
        panel_order_details.setLayout(panel_order_detailsLayout);
        panel_order_detailsLayout.setHorizontalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_buttons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_order_no)
                                .addGap(12, 12, 12)
                                .addComponent(lbl_auto_order_no)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_fault)
                                .addGap(6, 6, 6))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_issue_date)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(txt_fault, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_first_name)
                                .addGap(18, 18, 18)
                                .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_email)
                                .addGap(12, 12, 12)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_brand)
                                .addGap(12, 12, 12)
                                .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_model)
                                .addGap(6, 6, 6)
                                .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_sn)
                                .addGap(6, 6, 6)
                                .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                        .addComponent(lbl_last_name)
                                        .addGap(20, 20, 20)
                                        .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                        .addComponent(lbl_contact)
                                        .addGap(11, 11, 11)
                                        .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3)
                                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                        .addComponent(lbl_service_product)
                                        .addGap(6, 6, 6)
                                        .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(icon_add_table_view))
                                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                        .addComponent(lbl_due)
                                        .addGap(0, 0, 0)
                                        .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_deposit)
                                        .addGap(0, 0, 0)
                                        .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_price)
                                        .addGap(0, 0, 0)
                                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_order_detailsLayout.setVerticalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txt_fault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_issue_date, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_order_no)
                            .addComponent(lbl_auto_order_no)
                            .addComponent(lbl_fault))))
                .addGap(6, 6, 6)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_first_name))
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_last_name))
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_contact))
                            .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_email))
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_brand))
                            .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_model))
                            .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_sn))
                            .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lbl_service_product))
                            .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(icon_add_table_view))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbl_due))
                            .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lbl_deposit))
                            .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lbl_price))
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(panel_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        desktop_pane_order_details.setLayer(panel_order_details, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_order_detailsLayout = new javax.swing.GroupLayout(desktop_pane_order_details);
        desktop_pane_order_details.setLayout(desktop_pane_order_detailsLayout);
        desktop_pane_order_detailsLayout.setHorizontalGroup(
            desktop_pane_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, 1037, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        desktop_pane_order_detailsLayout.setVerticalGroup(
            desktop_pane_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, 1037, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
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
        for (int i = 0; i < table_view_products.getRowCount(); i++)
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 3).toString());
        
        txt_total.setText(Double.toString(sum));
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_depositKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyReleased
        // TODO add your handling code here:
        //Calculate deposit paid and display due value
        if (txt_deposit.getText() == null || txt_deposit.getText().trim().isEmpty()) {
            txt_due.setText(txt_total.getText());
            deposit = 0.0;
        } else {
            double priceTotal = Double.parseDouble(txt_total.getText());
            deposit = Double.parseDouble(txt_deposit.getText());
            total = priceTotal - deposit;
            txt_due.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_txt_depositKeyReleased

    private void txt_serial_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serial_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serial_numberActionPerformed

    private void txt_first_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch (evt.getKeyCode()) {
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
        if (Character.isLetter(evt.getKeyChar()))
            txt_total.setEditable(false);
        else
            txt_total.setEditable(true);
    }//GEN-LAST:event_txt_totalKeyPressed

    private void txt_depositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyPressed
        // TODO add your handling code here:
        // Accepts number only
        if (Character.isLetter(evt.getKeyChar()))
            txt_deposit.setEditable(false);
        else
            txt_deposit.setEditable(true);
    }//GEN-LAST:event_txt_depositKeyPressed

    private void txt_faultKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_faultKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_faultKeyReleased

    private void txt_faultKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_faultKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch (evt.getKeyCode()) {
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
        Vector faultsVector = new Vector();
        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();

        if (faultText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please add a Fault!", "Faults", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                dbConnection();
                
                String queryCheck = "SELECT * FROM faults WHERE faultName = ? ";
                ps = con.prepareStatement(queryCheck);
                ps.setString(1, faultText);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    int confirmInsertion = JOptionPane.showConfirmDialog(this, "Do you want to add a new fault ?", "Add New Fault", JOptionPane.YES_NO_OPTION);
                    if (confirmInsertion == 0) {
                        String query = "INSERT INTO faults (faultName) VALUES(?)";
                        ps = con.prepareStatement(query);
                        ps.setString(1, faultText);
                        ps.executeUpdate();

                        faultsVector.add(faultText);
                        dtm.addRow(faultsVector);
                        txt_fault.setText("");
                    } else {
                        txt_fault.setText("");
                    }
                } else {
                    faultsVector.add(faultText);
                    dtm.addRow(faultsVector);
                    txt_fault.setText("");
                }

            } catch (SQLException ex) {
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
    }//GEN-LAST:event_combo_box_product_serviceActionPerformed

    private void icon_add_table_viewMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_add_table_viewMousePressed
        // TODO add your handling code here:
        Vector vector = new Vector();
        String selectedItem = combo_box_product_service.getSelectedItem().toString();
        String productName = "";
        int qty = 0;
        double unitPrice = 0;
        String category = "";
        double totalPrice = 0;

        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();

        if (selectedItem.isEmpty() || selectedItem.matches("Select or Type")) {
            JOptionPane.showMessageDialog(null, "Please select a Product | Service!", "Service | Product", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                dbConnection();

                String query = "SELECT * FROM products WHERE productService = ? ";
                ps = con.prepareStatement(query);
                ps.setString(1, selectedItem);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "Item not Found!", "Service | Product", JOptionPane.ERROR_MESSAGE);
                } else {
                    while (rs.next()) {
                        productName = rs.getString("productService");
                        unitPrice = rs.getDouble("price");
                        category = rs.getString("category");
                    }

                    if (category.equals("Product")) {
                        boolean valid = false;
                        while (!valid) {
                            try {
                                qty = Integer.parseInt(JOptionPane.showInputDialog("Enter '" + selectedItem + "' Qty:"));
                                if (qty > 0) {
                                    valid = true;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Qty must be an Integer!", "New Order", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        totalPrice = unitPrice * qty;
                        vector.add(productName);
                        vector.add(qty);
                        vector.add(unitPrice);
                        vector.add(totalPrice);
                        dtm.addRow(vector);

                    } else {
                        qty = 1;
                        totalPrice = unitPrice * qty;

                        vector.add(productName);
                        vector.add(qty);
                        vector.add(unitPrice);
                        vector.add(totalPrice);
                        dtm.addRow(vector);
                    }

                    combo_box_product_service.setSelectedIndex(-1);
                    // Sum price column and set into total textField
                    getPriceSum();
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_icon_add_table_viewMousePressed

    private void table_view_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_faultsMouseClicked
        // TODO add your handling code here:
        //Delete fault item of the selected row (Function is called with 2 clicks) 
        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();

        if (evt.getClickCount() == 2) {
            String selectedFault = table_view_faults.getValueAt(table_view_faults.getSelectedRow(), 0).toString();
            int confirmDeletion = JOptionPane.showConfirmDialog(null, "Remove " + selectedFault + " ?", "Remove Fault", JOptionPane.YES_NO_OPTION);
            if (confirmDeletion == 0) {
                dtm.removeRow(table_view_faults.getSelectedRow());
            }
        }
    }//GEN-LAST:event_table_view_faultsMouseClicked

    private void txt_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_depositActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_depositActionPerformed

    private void txt_dueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dueActionPerformed

    private void txt_last_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch (evt.getKeyCode()) {
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
            String query = "SELECT * FROM customers WHERE contactNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo);
            rs = ps.executeQuery();

            //show a message if a costumer is not found in the db
            if (!rs.isBeforeFirst() && firstName.trim().isEmpty() && lastName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Customer not found in the Database", "New Order", JOptionPane.ERROR_MESSAGE);
            } //add a new costumer if not exist AND fields are not empty
            else if (!rs.isBeforeFirst() && !firstName.trim().isEmpty() && !lastName.trim().isEmpty()) {
                int confirmInsertion = JOptionPane.showConfirmDialog(null, "Do you want to add a new Customer ?", "Add New Customer", JOptionPane.YES_NO_OPTION);
                if (confirmInsertion == 0) {
                    customer = new Customer(firstName, lastName, contactNo, email);
                    String insertQuery = "INSERT INTO customers (firstName, lastName, contactNo, email) VALUES(?, ?, ?, ?)";

                    ps = con.prepareStatement(insertQuery);
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getContactNo());
                    ps.setString(4, customer.getEmail());
                    ps.executeUpdate();
                    txt_brand.requestFocus();
                }
            } else {
                String fillQuery = "Select * from customers WHERE contactNo = ? ";
                ps = con.prepareStatement(fillQuery);
                rs = ps.executeQuery();

                while (rs.next()) {
                    txt_first_name.setText(rs.getString("firstName"));
                    txt_last_name.setText(rs.getString("lastName"));
                    txt_contact.setText(rs.getString("contactNo"));
                    txt_email.setText(rs.getString("email"));
                    int id = rs.getInt("customerID");
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
        int confirmFixing = JOptionPane.showConfirmDialog(this, "Do you really want to Tag Order: "
                + order.getOrderNo() + " as Fixed Order", "Update Order", JOptionPane.YES_NO_OPTION);

        if (confirmFixing == 0) {
            order.setStatus("Fixed");;

            try {
                dbConnection();
                String query = "UPDATE orderDetails SET status = ?, finishDate = ? WHERE orderNo = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, order.getStatus());
                ps.setString(2, order.getFinishDate());
                ps.setString(3, order.getOrderNo());
                ps.executeUpdate();

                addEventNote("Fixed Order");

                FixedOrder fixedOrder = new FixedOrder(order, completedOrder);
                desktop_pane_order_details.removeAll();
                desktop_pane_order_details.add(fixedOrder).setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_fixActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        boolean isOrderDetails = true;
        PrintOrder printOrder = new PrintOrder(order, completedOrder, isOrderDetails);
        printOrder.setVisible(true);
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_not_fixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_not_fixActionPerformed
        // TODO add your handling code here:
        int confirmFixing = JOptionPane.showConfirmDialog(this, "Do you really want to Tag Order: "
                + order.getOrderNo() + " as 'NOT FIXED ORDER' ?", "Update Order", JOptionPane.YES_NO_OPTION);

        if (confirmFixing == 0) {
            order.setStatus("Not Fixed");
            try {
                dbConnection();
                String query = "UPDATE orderDetails SET status = ?, finishDate = ? WHERE orderNo = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, order.getStatus());
                ps.setString(2, order.getFinishDate());
                ps.setString(3, order.getOrderNo());
                ps.executeUpdate();

                addEventNote("Not Fixed Order");

                NotFixedOrder orderNotFixed = new NotFixedOrder(order, completedOrder);
                desktop_pane_order_details.removeAll();
                desktop_pane_order_details.add(orderNotFixed).setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_not_fixActionPerformed

    private void btn_save_changesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_changesActionPerformed
        // Prevent empty fields into the DAtabase
        if (txt_first_name.getText().trim().isEmpty() | txt_last_name.getText().trim().isEmpty()
                | txt_contact.getText().trim().isEmpty() | txt_brand.getText().trim().isEmpty()
                | txt_model.getText().trim().isEmpty() | txt_serial_number.getText().trim().isEmpty()
                | table_view_products.getRowCount() == 0 | table_view_faults.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "Update Order", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date date = new Date();
        Timestamp currentDate = new Timestamp(date.getTime());
        String updateDate = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);

        //Empty vector before looping to avoid duplicate values on tableView
        vecUpdateFaults.removeAllElements();
        vecUpdateProducts.removeAllElements();
        vecUpdateQty.removeAllElements();
        vecUpdateUnitPrice.removeAllElements();
        vecUpdatePriceTotal.removeAllElements();

        //pass table items from faults and products table to vector 
        for (int i = 0; i < table_view_faults.getRowCount(); i++) {
            vecUpdateFaults.add(table_view_faults.getValueAt(i, 0));
        }

        for (int j = 0; j < table_view_products.getRowCount(); j++) {
            vecUpdateProducts.add(table_view_products.getValueAt(j, 0));
            vecUpdateQty.add(table_view_products.getValueAt(j, 1));
            vecUpdateUnitPrice.add(table_view_products.getValueAt(j, 2));
            vecUpdatePriceTotal.add(table_view_products.getValueAt(j, 3));
        }
        //Remove Brackets from the vector to pass to Database  
        stringFaults = vecUpdateFaults.toString().replace("[", "").replace("]", "");
        stringProducts = vecUpdateProducts.toString().replace("[", "").replace("]", "");
        stringQty = vecUpdateQty.toString().replace("[", "").replace("]", "");
        stringUnitPrice = vecUpdateUnitPrice.toString().replace("[", "").replace("]", "");
        stringPriceTotal = vecUpdatePriceTotal.toString().replace("[", "").replace("]", "");

        if (order.getFirstName().equals(txt_first_name.getText()) && order.getLastName().equals(txt_last_name.getText())
                && order.getContactNo().equals(txt_contact.getText()) && order.getEmail().equals(txt_email.getText())
                && order.getBrand().equals(txt_brand.getText()) && order.getModel().equals(txt_model.getText())
                && order.getSerialNumber().equals(txt_serial_number.getText()) && order.getStringFaults().equals(stringFaults)
                && order.getImportantNotes().equals(editor_pane_important_notes.getText()) && order.getStringProducts().equals(stringProducts)
                && order.getStringQty().equals(stringQty) && order.getUnitPrice().equals(stringUnitPrice)
                && order.getPriceTotal().equals(stringPriceTotal) && order.getTotal() == Double.parseDouble(txt_total.getText())
                //&& order.getDeposit() == Double.parseDouble(txt_deposit.getText()) 
                && order.getDue() == Double.parseDouble(txt_due.getText())) {
            JOptionPane.showMessageDialog(null, "No changes to be Updated", "Update Order Details", JOptionPane.ERROR_MESSAGE);

        } else if (Double.parseDouble(txt_deposit.getText()) < order.getDeposit()) {
            JOptionPane.showMessageDialog(this, "Deposit can not be equal or less than " + order.getDeposit() + " !", "Update Order Details", JOptionPane.ERROR_MESSAGE);
        } else {
            order.setFirstName(txt_first_name.getText());
            order.setLastName(txt_last_name.getText());
            order.setContactNo(txt_contact.getText());
            order.setEmail(txt_email.getText());
            order.setBrand(txt_brand.getText());
            order.setModel(txt_model.getText());
            order.setSerialNumber(txt_serial_number.getText());
            order.setStringFaults(stringFaults);
            order.setImportantNotes(editor_pane_important_notes.getText());
            order.setStringProducts(stringProducts);
            order.setStringQty(stringQty);
            order.setUnitPrice(stringUnitPrice);
            order.setPriceTotal(stringPriceTotal);
            order.setTotal(Double.parseDouble(txt_total.getText()));
            order.setDue(Double.parseDouble(txt_due.getText()));
            order.setIssueDate(updateDate);

            int confirmUpdate = JOptionPane.showConfirmDialog(null, "Save changes on order "
                    + order.getOrderNo() + " ?", "Update Order Details", JOptionPane.YES_NO_OPTION);

            if (confirmUpdate == 0) {
                double newDeposit = Double.parseDouble(txt_deposit.getText());

                if (newDeposit > order.getDeposit()) {
                    newDeposit -= order.getDeposit();
                    DepositUpdatePayment depositUpdatePayment = new DepositUpdatePayment(order, completedOrder, newDeposit);
                    depositUpdatePayment.setVisible(true);
                } else {
                    try {
                        String queryUpdate = "UPDATE orderDetails SET firstName = ?, lastName = ? , contactNo = ?, email = ?,"
                                + " deviceBrand = ?, deviceModel = ?, serialNumber = ?, fault = ?, importantNotes = ?, "
                                + "productService = ?, qty = ?, unitPrice = ?, priceTotal = ?, total = ?, due = ?, "
                                + "issueDate = ? WHERE orderNo = ? ";
                        ps = con.prepareStatement(queryUpdate);
                        ps.setString(1, order.getFirstName());
                        ps.setString(2, order.getLastName());
                        ps.setString(3, order.getContactNo());
                        ps.setString(4, order.getEmail());
                        ps.setString(5, order.getBrand());
                        ps.setString(6, order.getModel());
                        ps.setString(7, order.getSerialNumber());
                        ps.setString(8, order.getStringFaults());
                        ps.setString(9, order.getImportantNotes());
                        ps.setString(10, order.getStringProducts());
                        ps.setString(11, order.getStringQty());
                        ps.setString(12, order.getUnitPrice());
                        ps.setString(13, order.getPriceTotal());
                        ps.setDouble(14, order.getTotal());
                        ps.setDouble(15, order.getDue());
                        ps.setString(16, order.getIssueDate());
                        ps.setString(17, order.getOrderNo());
                        ps.executeUpdate();

                        String removeSpace = "UPDATE orderDetails SET fault = REPLACE(fault, '  ', ' '), "
                                + "productService = REPLACE(productService, '  ', ' '), "
                                + "qty = REPLACE(qty, '  ', ' '), "
                                + "unitPrice = REPLACE(unitPrice, '  ', ' '), "
                                + "total = REPLACE(total, '  ', ' ')";
                        
                        ps = con.prepareStatement(removeSpace);
                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Order " + order.getOrderNo() + " Updated successfully!");
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_save_changesActionPerformed

    private void btn_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notesActionPerformed
        // TODO add your handling code here:
        OrderNotes orderNotes = new OrderNotes(order.getOrderNo(), order.getUsername());
        orderNotes.setVisible(true);
    }//GEN-LAST:event_btn_notesActionPerformed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_view_productsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fix;
    private javax.swing.JButton btn_not_fix;
    private javax.swing.JButton btn_notes;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_save_changes;
    private javax.swing.JComboBox<String> combo_box_product_service;
    private javax.swing.JDesktopPane desktop_pane_order_details;
    private javax.swing.JEditorPane editor_pane_important_notes;
    private javax.swing.JLabel icon_add_table_view;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane_notes;
    private javax.swing.JLabel lbl_auto_order_no;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_fault;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_issue_date;
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
