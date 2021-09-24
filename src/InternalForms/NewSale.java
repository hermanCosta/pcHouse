/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.Login;
import Forms.MainMenu;
import Forms.SalePayment;
import Model.CompletedOrder;
import Model.Computer;
import Model.Customer;
import Model.ProductService;
import Model.Sale;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author HermanCosta
 */
public class NewSale extends javax.swing.JInternalFrame {

    ArrayList firstNames = new ArrayList();
    ArrayList lastNames = new ArrayList();
    Vector vecProducts = new Vector();
    Vector vecQty = new Vector();
    Vector vecUnitPrice = new Vector();
    Vector vecPriceTotal = new Vector();

    Connection con;
    PreparedStatement ps;
    Statement stmt;
    Customer customer;
    ProductService productService;
    Sale sale;
    Computer computer;
    ResultSet rs;
    ResultSetMetaData rsmd;
    CompletedOrder completedOrder;

    String saleNo, firstName, lastName, contactNo, email,
            stringProducts, stringPriceTotal, stringQty, stringUnitPrice, saleDate, status;

    double total, cash, card, changeTotal;
    boolean isSaleDetails = false;

    public NewSale() {
        initComponents();

        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        SwingUtilities.invokeLater(() -> {
            txt_first_name.requestFocus();
        });

        txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);//avoid auto old value by focus loosing

        tableSettings(table_view_products);
        tableSettings(table_view_computers);
        autoID();
        checkEmailFormat();
        accessDbColumn(firstNames, "SELECT * FROM customers", "firstName");
        accessDbColumn(lastNames, "SELECT * FROM customers", "lastName");
        listProductService();
        loadComputerTable();
    }

    public void tableSettings(JTable table) {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewSale.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void autoID() {
        try {
            dbConnection();

            //check saleNo from saleNo table
            String queryCheck = "SELECT saleNo FROM sales";
            ps = con.prepareStatement(queryCheck);
            rs = ps.executeQuery();

            if (rs.next()) {
                String queryMax = "SELECT Max(saleNo) FROM sales";
                ps = con.prepareStatement(queryMax);
                rs = ps.executeQuery();

                while (rs.next()) {
                    long id = Long.parseLong(rs.getString("Max(saleNo)").substring(3, rs.getString("Max(saleNo)").length()));
                    id++;
                    lbl_auto_sale_no.setText("SNO" + String.format("%04d", id));
                }
            } else {
                lbl_auto_sale_no.setText("SNO0001");
            }

            ps.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
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
            con.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(NewSale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void accessDbColumn(ArrayList list, String query, String columnName) {
        try {
            dbConnection();

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString(columnName));
            }

            ps.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(NewSale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cleanFields(JTable table) {
        //Clean all Fields 
        lbl_auto_sale_no.setText("");
        txt_first_name.setText("");
        txt_last_name.setText("");
        txt_contact.setText("");
        txt_email.setText("");
        combo_box_product_service.setSelectedIndex(-1);
        txt_total.setText("");

        txt_first_name.requestFocus();

        //Clean table Fields
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();

        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
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

    public boolean checkEmptyFields() {
        if (txt_first_name.getText().trim().isEmpty() | txt_last_name.getText().trim().isEmpty()
                | txt_contact.getText().trim().isEmpty() | table_view_products.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Sale", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public void getSaleValues() {
        if (txt_first_name.getText().trim().isEmpty() || txt_last_name.getText().trim().isEmpty()
                || txt_contact.getText().trim().isEmpty() || table_view_products.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Sale", JOptionPane.ERROR_MESSAGE);
        } else {
            saleNo = lbl_auto_sale_no.getText();
            firstName = txt_first_name.getText().toUpperCase();
            lastName = txt_last_name.getText().toUpperCase();
            contactNo = txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
            email = txt_email.getText();
            total = Double.parseDouble(txt_total.getText());

            Date date = new java.util.Date();
            Timestamp currentDate = new Timestamp(date.getTime());
            saleDate = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);

            //Empty vector before looping to avoid duplicate values on tableView
            vecProducts.removeAllElements();
            vecQty.removeAllElements();
            vecUnitPrice.removeAllElements();
            vecPriceTotal.removeAllElements();
            //pass table items from faults and products table to vector 

            for (int j = 0; j < table_view_products.getRowCount(); j++) {
                vecProducts.add(table_view_products.getValueAt(j, 0));
                vecQty.add(table_view_products.getValueAt(j, 1));
                vecUnitPrice.add(table_view_products.getValueAt(j, 2));
                vecPriceTotal.add(table_view_products.getValueAt(j, 3));
            }

            // pass vector elemnets to a String splitted by a comma,
            // in sa to save into DB
            stringProducts = vecProducts.toString().replace("[", " ").replace("]", "");
            stringQty = vecQty.toString().replace("[", " ").replace("]", "");
            stringUnitPrice = vecUnitPrice.toString().replace("[", " ").replace("]", "");
            stringPriceTotal = vecPriceTotal.toString().replace("[", " ").replace("]", "");

            sale = new Sale(saleNo, firstName, lastName, contactNo, email, stringProducts,
                    stringQty, stringUnitPrice, stringPriceTotal, total, saleDate, cash, card, changeTotal, status, Login.fullName);
        }
    }

    public void getPriceSum() {
        double sum = 0;
        for (int i = 0; i < table_view_products.getRowCount(); i++) {
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 3).toString());
        }
        txt_total.setText(Double.toString(sum));
    }

    public void searchSale() {
        ArrayList<Computer> computerList = new ArrayList<>();
        String searchComputer = txt_search_computer.getText();

        try {
            dbConnection();

            String query = "SELECT * FROM computers WHERE brand LIKE '%" + searchComputer + "%'"
                    + "OR model LIKE '%" + searchComputer + "%' OR serialNumber LIKE '%" + searchComputer + "%'"
                    + " OR processor LIKE '%" + searchComputer + "%'";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next()) {
                computer = new Computer(rs.getString("brand"), rs.getString("model"), rs.getString("serialNumber"),
                        rs.getString("processor"), rs.getString("ram"), rs.getString("storage"),
                        rs.getString("gpu"), rs.getString("screen"), rs.getString("notes"),
                        rs.getInt("qty"), rs.getDouble("price"));

                computerList.add(computer);
                computer.setComputerId(rs.getInt("computerId"));
            }

            DefaultTableModel dtm = (DefaultTableModel) table_view_computers.getModel();
            dtm.setRowCount(0);

            Object[] row = new Object[9];
            for (int i = 0; i < computerList.size(); i++) {
                row[0] = computerList.get(i).getComputerId();
                row[1] = computerList.get(i).getBrand();
                row[2] = computerList.get(i).getModel();
                row[3] = computerList.get(i).getSerialNumber();
                row[4] = computerList.get(i).getProcessor();
                row[5] = computerList.get(i).getRam();
                row[6] = computerList.get(i).getStorage();
                row[7] = computerList.get(i).getQty();
                row[8] = computerList.get(i).getPrice();

                dtm.addRow(row);
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadComputerTable() {
        ArrayList<Computer> computerList = new ArrayList<>();

        try {
            dbConnection();

            String query = "SELECT * FROM computers";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next()) {
                computer = new Computer(rs.getString("brand"), rs.getString("model"), rs.getString("serialNumber"), rs.getString("processor"),
                        rs.getString("ram"), rs.getString("storage"), rs.getString("gpu"), rs.getString("screen"), rs.getString("notes"),
                        rs.getInt("qty"), rs.getDouble("price"));
                computer.setComputerId(rs.getInt("computerId"));

                computerList.add(computer);
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ComputerList.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableModel dtm = (DefaultTableModel) table_view_computers.getModel();
        dtm.setRowCount(0);

        Object[] row = new Object[9];
        for (int i = 0; i < computerList.size(); i++) {
            row[0] = computerList.get(i).getComputerId();
            row[1] = computerList.get(i).getBrand();
            row[2] = computerList.get(i).getModel();
            row[3] = computerList.get(i).getSerialNumber();
            row[4] = computerList.get(i).getProcessor();
            row[5] = computerList.get(i).getRam();
            row[6] = computerList.get(i).getStorage();
            row[7] = computerList.get(i).getQty();
            row[8] = computerList.get(i).getPrice();
            dtm.addRow(row);
        }
    }

    public boolean saveCustomerIntoDb() {
        boolean isContactNo = false;
        contactNo = txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
        try {
            dbConnection();
            String query = "SELECT contactNo, firstName, lastName FROM customers WHERE contactNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                customer = new Customer(txt_first_name.getText().toUpperCase(), txt_last_name.getText().toUpperCase(), contactNo, txt_email.getText());

                String queryInsert = "INSERT INTO customers (firstName, lastName, contactNo, email) VALUES(?, ?, ?, ?)";
                ps = con.prepareStatement(queryInsert);
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getContactNo());
                ps.setString(4, customer.getEmail());
                ps.executeUpdate();
            } else {
                while (rs.next()) {
                    firstName = rs.getString("firstName");
                    lastName = rs.getString("lastName");
                }

                if (!firstName.equals(txt_first_name.getText())
                        && !lastName.equals(txt_last_name.getText())) {
                    JOptionPane.showMessageDialog(this, "There is another Customer associated with ContactNo " + txt_contact.getText(), "New Customer", JOptionPane.ERROR_MESSAGE);
                    isContactNo = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewSale.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isContactNo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_new_sale = new javax.swing.JDesktopPane();
        panel_sale_details = new javax.swing.JPanel();
        lbl_sale_no = new javax.swing.JLabel();
        lbl_first_name = new javax.swing.JLabel();
        lbl_last_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_auto_sale_no = new javax.swing.JLabel();
        lbl_service_product = new javax.swing.JLabel();
        lbl_price = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_last_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_contact = new javax.swing.JFormattedTextField();
        combo_box_product_service = new javax.swing.JComboBox<>();
        btn_save_sale = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_computers = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();
        txt_search_computer = new javax.swing.JTextField();
        lbl_search_icon = new javax.swing.JLabel();
        btn_computers = new javax.swing.JButton();
        btn_international_number = new javax.swing.JButton();
        btn_copy = new javax.swing.JButton();
        btn_add_product_service = new javax.swing.JButton();
        txt_total = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_sale_details.setPreferredSize(new java.awt.Dimension(1049, 700));

        lbl_sale_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_sale_no.setText("Sale No.");

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");

        lbl_auto_sale_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_auto_sale_no.setText("autoGen");

        lbl_service_product.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_service_product.setText("Service | Product");

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_price.setText("Total €");

        txt_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_first_name.setNextFocusableComponent(txt_last_name);
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
        txt_last_name.setNextFocusableComponent(txt_contact);
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
        txt_email.setNextFocusableComponent(combo_box_product_service);
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
        txt_contact.setNextFocusableComponent(txt_email);
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

        combo_box_product_service.setEditable(true);
        combo_box_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        combo_box_product_service.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select or Type" }));
        combo_box_product_service.setSize(new java.awt.Dimension(96, 30));
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

        btn_save_sale.setBackground(new java.awt.Color(21, 76, 121));
        btn_save_sale.setFont(new java.awt.Font("Lucida Grande", 1, 22)); // NOI18N
        btn_save_sale.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_sale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save.png"))); // NOI18N
        btn_save_sale.setText("Save");
        btn_save_sale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_saleActionPerformed(evt);
            }
        });

        btn_cancel.setBackground(new java.awt.Color(21, 76, 121));
        btn_cancel.setFont(new java.awt.Font("Lucida Grande", 1, 22)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        table_view_computers.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_computers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Serial Number", "Processor", "RAM", "Storage", "Qty", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_computers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_computersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_computers);
        if (table_view_computers.getColumnModel().getColumnCount() > 0) {
            table_view_computers.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_view_computers.getColumnModel().getColumn(0).setMaxWidth(100);
            table_view_computers.getColumnModel().getColumn(7).setPreferredWidth(50);
            table_view_computers.getColumnModel().getColumn(7).setMaxWidth(80);
            table_view_computers.getColumnModel().getColumn(8).setPreferredWidth(100);
            table_view_computers.getColumnModel().getColumn(8).setMaxWidth(150);
        }

        table_view_products.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        table_view_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product | Service", "Qty", "Unit €", "Total €"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
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
        table_view_products.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_view_productsKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(table_view_products);
        if (table_view_products.getColumnModel().getColumnCount() > 0) {
            table_view_products.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_view_products.getColumnModel().getColumn(1).setMaxWidth(200);
            table_view_products.getColumnModel().getColumn(2).setPreferredWidth(80);
            table_view_products.getColumnModel().getColumn(2).setMaxWidth(120);
            table_view_products.getColumnModel().getColumn(3).setPreferredWidth(80);
            table_view_products.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        txt_search_computer.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        txt_search_computer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_computerActionPerformed(evt);
            }
        });
        txt_search_computer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_search_computerKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_search_computerKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_computerKeyReleased(evt);
            }
        });

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N

        btn_computers.setBackground(new java.awt.Color(21, 76, 121));
        btn_computers.setFont(new java.awt.Font("Lucida Grande", 1, 22)); // NOI18N
        btn_computers.setForeground(new java.awt.Color(255, 255, 255));
        btn_computers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_device_list.png"))); // NOI18N
        btn_computers.setText("Computers");
        btn_computers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_computersActionPerformed(evt);
            }
        });

        btn_international_number.setBackground(new java.awt.Color(0, 0, 0));
        btn_international_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_international_number.png"))); // NOI18N
        btn_international_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_international_numberActionPerformed(evt);
            }
        });

        btn_copy.setBackground(new java.awt.Color(0, 0, 0));
        btn_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_copy.png"))); // NOI18N
        btn_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyActionPerformed(evt);
            }
        });

        btn_add_product_service.setBackground(new java.awt.Color(0, 0, 0));
        btn_add_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add_new.png"))); // NOI18N
        btn_add_product_service.setNextFocusableComponent(txt_search_computer);
        btn_add_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_product_serviceActionPerformed(evt);
            }
        });

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_sale_detailsLayout = new javax.swing.GroupLayout(panel_sale_details);
        panel_sale_details.setLayout(panel_sale_detailsLayout);
        panel_sale_detailsLayout.setHorizontalGroup(
            panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_sale_no)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_auto_sale_no))
                            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_email)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sale_detailsLayout.createSequentialGroup()
                                    .addComponent(lbl_first_name)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sale_detailsLayout.createSequentialGroup()
                                    .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sale_detailsLayout.createSequentialGroup()
                                            .addComponent(lbl_last_name)
                                            .addGap(20, 20, 20))
                                        .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                            .addComponent(lbl_contact)
                                            .addGap(11, 11, 11)))
                                    .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                            .addComponent(txt_contact)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_international_number, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                .addComponent(lbl_price)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbl_service_product)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)))))
                .addGap(0, 18, Short.MAX_VALUE))
            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addComponent(btn_save_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_computers, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search_computer, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_sale_detailsLayout.setVerticalGroup(
            panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_auto_sale_no, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_sale_no))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_first_name))
                        .addGap(15, 15, 15)
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_last_name)
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_contact)
                                    .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sale_detailsLayout.createSequentialGroup()
                                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_international_number, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)))
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_email)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_price)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_sale_detailsLayout.createSequentialGroup()
                        .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_add_product_service)
                            .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_service_product)))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 24, Short.MAX_VALUE)
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save_sale)
                    .addComponent(btn_cancel)
                    .addComponent(btn_computers))
                .addGap(18, 18, 18)
                .addGroup(panel_sale_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search_computer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        desktop_pane_new_sale.setLayer(panel_sale_details, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_new_saleLayout = new javax.swing.GroupLayout(desktop_pane_new_sale);
        desktop_pane_new_sale.setLayout(desktop_pane_new_saleLayout);
        desktop_pane_new_saleLayout.setHorizontalGroup(
            desktop_pane_new_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_new_saleLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_sale_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        desktop_pane_new_saleLayout.setVerticalGroup(
            desktop_pane_new_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_new_saleLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_sale_details, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_new_sale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_new_sale, javax.swing.GroupLayout.Alignment.TRAILING)
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
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameActionPerformed

    private void btn_save_saleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_saleActionPerformed
        // TODO add your handling code here:
        getSaleValues();
        if (!saveCustomerIntoDb()) {
            String formatContactNo = txt_contact.getText();
            SalePayment salePayment = new SalePayment(sale, table_view_products, formatContactNo);
            salePayment.setVisible(true);
        }
    }//GEN-LAST:event_btn_save_saleActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed

        int confirmCancelling = JOptionPane.showConfirmDialog(this, "Do you want to cancel this ?", "New Sale",
                JOptionPane.YES_NO_OPTION);
        if (confirmCancelling == 0) {
            new MainMenu().setVisible(true);
        }
    }//GEN-LAST:event_btn_cancelActionPerformed

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

    private void txt_first_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameKeyReleased

    private void txt_last_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyReleased

    private void table_view_computersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_computersMouseClicked
        // TODO add your handling code here:
        //Delete products|Service item of the selected row (Function is called with 2 clicks) 
        int row = table_view_computers.getSelectedRow();
        DefaultTableModel compTableModel = (DefaultTableModel) table_view_computers.getModel();
        DefaultTableModel prodTableModel = (DefaultTableModel) table_view_products.getModel();
        Vector vecComputers = new Vector();
        boolean valid = false;
        String tableProduct = "";

        if (evt.getClickCount() == 2) {
            int computerId = (int) compTableModel.getValueAt(row, 0);
            String brand = compTableModel.getValueAt(row, 1).toString();
            String model = compTableModel.getValueAt(row, 2).toString();
            String serialNumber = compTableModel.getValueAt(row, 3).toString();
            double price = Double.parseDouble(compTableModel.getValueAt(row, 8).toString());

            String newProduct = computerId + "| " + brand + " | " + model + " | " + serialNumber;

            int qty = 0;
            while (!valid) {
                try {
                    qty = Integer.parseInt(JOptionPane.showInputDialog("Enter '" + computer.getBrand() + " | "
                            + computer.getModel() + " | " + computer.getSerialNumber() + "' Qty:"));
                    if (qty > 0) {
                        valid = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Qty must be an Integer!", "Add Computer", JOptionPane.ERROR_MESSAGE);
                }
            }

            double priceTotal = price * qty;
            vecComputers.add(newProduct);
            vecComputers.add(qty);
            vecComputers.add(price);
            vecComputers.add(priceTotal);

            for (int i = 0; i < prodTableModel.getRowCount(); i++) {
                tableProduct = prodTableModel.getValueAt(i, 0).toString();
            }

            if (newProduct.equals(tableProduct)) {
                JOptionPane.showMessageDialog(this, "Item '" + newProduct + "' already added !", "Add Computer", JOptionPane.ERROR_MESSAGE);
            } else {
                prodTableModel.addRow(vecComputers);
            }

            // Sum price column and set into total textField
            getPriceSum();
        }
    }//GEN-LAST:event_table_view_computersMouseClicked

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
        contactNo = txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
        try {
            dbConnection();

            String query = "SELECT * FROM customers WHERE contactNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Customer not found in the Database", "New Customer", JOptionPane.ERROR_MESSAGE);
            } else {
                while (rs.next()) {
                    txt_first_name.setText(rs.getString("firstName"));
                    txt_last_name.setText(rs.getString("lastName"));
                    txt_contact.setText(rs.getString("contactNo"));
                    txt_email.setText(rs.getString("email"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(NewSale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactKeyReleased

    private void combo_box_product_serviceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_box_product_serviceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_product_serviceKeyReleased

    private void combo_box_product_serviceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_box_product_serviceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_product_serviceKeyPressed

    private void combo_box_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_product_serviceActionPerformed

    }//GEN-LAST:event_combo_box_product_serviceActionPerformed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        // TODO add your handling code here:
        //Delete products|Service item of the selected row (Function is called with 2 clicks) 
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();

        if (evt.getClickCount() == 2) {
            int confirmDeletion = JOptionPane.showConfirmDialog(null, "Remove This Item ?", "Remove Product|Service", JOptionPane.YES_NO_OPTION);
            if (confirmDeletion == 0) {
                dtm.removeRow(table_view_products.getSelectedRow());
                // Sum price column and set into total textField
                getPriceSum();
            }
        }
    }//GEN-LAST:event_table_view_productsMouseClicked

    private void txt_search_computerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_computerActionPerformed
        // TODO add your handling code here:
        searchSale();
    }//GEN-LAST:event_txt_search_computerActionPerformed

    private void txt_search_computerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_computerKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_computerKeyTyped

    private void txt_search_computerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_computerKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_search_computerKeyPressed

    private void txt_search_computerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_computerKeyReleased
        // TODO add your handling code here:
        searchSale();
    }//GEN-LAST:event_txt_search_computerKeyReleased

    private void btn_computersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_computersActionPerformed
        // TODO add your handling code here:
        ComputerList computerList = new ComputerList();
        desktop_pane_new_sale.add(computerList).setVisible(true);
    }//GEN-LAST:event_btn_computersActionPerformed

    private void btn_international_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_international_numberActionPerformed
        // TODO add your handling code here:
        txt_contact.setFormatterFactory(null);
        txt_contact.setText("");
        txt_contact.requestFocus();

    }//GEN-LAST:event_btn_international_numberActionPerformed

    private void btn_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyActionPerformed
        // TODO add your handling code here:
        StringSelection stringSelection = new StringSelection(txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, txt_contact.getText() + " Copied to Clipboard");
    }//GEN-LAST:event_btn_copyActionPerformed

    private void btn_add_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_product_serviceActionPerformed
        // TODO add your handling code here:
        // Add selected items to the products table
        ArrayList<String> productsList = new ArrayList<>();
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();
        String selectedItem = combo_box_product_service.getSelectedItem().toString();
        String newProdAdd = "", category = "";
        int qty = 0;
        double unitPrice = 0;
        double totalPrice = 0;
        Vector vector = new Vector();

        productsList.clear();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            productsList.add(dtm.getValueAt(i, 0).toString());
        }

        if (selectedItem.isEmpty() || selectedItem.matches("Select or Type")) {
            JOptionPane.showMessageDialog(this, "Please select a Product | Service!", "Service | Product", JOptionPane.ERROR_MESSAGE);
        } else if (productsList.contains(selectedItem)) {
            JOptionPane.showMessageDialog(this, "Item '" + selectedItem + "' already added !", "Add Computer", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                dbConnection();

                String query = "SELECT * FROM products WHERE productService = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, selectedItem);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "Item not Found!", "Service | Product", JOptionPane.ERROR_MESSAGE);
                } else {
                    while (rs.next()) {
                        newProdAdd = rs.getString("productService");
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
                                JOptionPane.showMessageDialog(this, "Qty must be an Integer!", "Product | Service", JOptionPane.ERROR_MESSAGE);
                            }

                            totalPrice = unitPrice * qty;
                            vector.add(newProdAdd);
                            vector.add(qty);
                            vector.add(unitPrice);
                            vector.add(totalPrice);
                            dtm.addRow(vector);
                        }

                    } else {
                        qty = 1;
                        totalPrice = unitPrice * qty;

                        vector.add(newProdAdd);
                        vector.add(qty);
                        vector.add(unitPrice);
                        vector.add(totalPrice);
                        dtm.addRow(vector);
                    }

                    combo_box_product_service.setSelectedIndex(-1);
                    // Sum price column and set into total textField
                    getPriceSum();
                    ps.close();
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_add_product_serviceActionPerformed

    private void table_view_productsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_view_productsKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            double sum = 0;
            for (int i = 0; i < table_view_products.getRowCount(); i++) {
                double price = Double.parseDouble(table_view_products.getValueAt(i, 3).toString());
                table_view_products.setValueAt(price, i, 3);
                sum += price;
            }
            txt_total.setText(Double.toString(sum));
        }
    }//GEN-LAST:event_table_view_productsKeyReleased

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_product_service;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_computers;
    private javax.swing.JButton btn_copy;
    private javax.swing.JButton btn_international_number;
    private javax.swing.JButton btn_save_sale;
    private javax.swing.JComboBox<String> combo_box_product_service;
    private javax.swing.JDesktopPane desktop_pane_new_sale;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_auto_sale_no;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_sale_no;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JLabel lbl_service_product;
    private javax.swing.JPanel panel_sale_details;
    private javax.swing.JTable table_view_computers;
    private javax.swing.JTable table_view_products;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_search_computer;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
