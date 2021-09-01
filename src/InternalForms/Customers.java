/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.MainMenu;
import Forms.SalePayment;
import Model.Customer;
import Model.ProductService;
import Model.Sale;
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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author user
 */
public class Customers extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewOrder
     */
    ArrayList firstNames = new ArrayList();
    ArrayList lastNames = new ArrayList();
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Statement stmt;
    Customer customer;
    String firstName, lastName, contactNo, email;
    
    
    public Customers() {
        initComponents();
        
        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);//avoid auto old value by focus loosing
        
        tableSettings(table_view_customers);
        checkEmailFormat();
        accessDbColumn(firstNames, "SELECT * FROM customers", "firstName");
        accessDbColumn(lastNames, "SELECT * FROM customers", "lastName");
        
        loadCustomerTable();
    }

    public void tableSettings (JTable table)
    {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 14));
    }
    
    public void dbConnection() 
    {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse","root","hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCustomerTable()
    {
        ArrayList<Customer> list = new ArrayList<>();
        
        try {
            dbConnection();
            
            String query = "SELECT * FROM customers LIMIT 20";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            
            while (rs.next())
            {
                customer = new Customer(rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"), rs.getString("email"));
                
                list.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel dtm = (DefaultTableModel)table_view_customers.getModel();
        dtm.setRowCount(0);
        
        Object[] row = new Object[4];
        for (int i = 0 ;i < list.size() ; i++)
        {
                row[0] = list.get(i).getFirstName();
                row[1] = list.get(i).getLastName();
                row[2] = list.get(i).getContactNo();
                row[3] = list.get(i).getEmail();
                
            dtm.addRow(row);
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
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cleanFields()
    {
        //Clean all Fields 
            txt_first_name.setText("");
            txt_last_name.setText("");
            txt_contact.setText("");
            txt_email.setText("");
            txt_first_name.requestFocus();
        
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
    
    public boolean checkEmptyFields()
    {
        if (txt_first_name.getText().trim().isEmpty() || txt_last_name.getText().trim().isEmpty() || 
                txt_contact.getText().trim().isEmpty() || table_view_customers.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "Please, check Empty fields", "New Customer", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_customers = new javax.swing.JDesktopPane();
        panel_customers = new javax.swing.JPanel();
        lbl_first_name = new javax.swing.JLabel();
        lbl_last_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_last_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_contact = new javax.swing.JFormattedTextField();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_customers = new javax.swing.JTable();
        txt_search_customer = new javax.swing.JTextField();
        lbl_search_icon = new javax.swing.JLabel();
        btn_delete = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_customers.setPreferredSize(new java.awt.Dimension(1049, 700));

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");

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

        btn_add.setBackground(new java.awt.Color(21, 76, 121));
        btn_add.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add_new.png"))); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_update.setBackground(new java.awt.Color(21, 76, 121));
        btn_update.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save_changes.png"))); // NOI18N
        btn_update.setText("Edit");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        table_view_customers.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        table_view_customers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "Last Name", "Contact", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_customers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_customersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_customers);
        if (table_view_customers.getColumnModel().getColumnCount() > 0) {
            table_view_customers.getColumnModel().getColumn(0).setPreferredWidth(120);
            table_view_customers.getColumnModel().getColumn(0).setMaxWidth(150);
            table_view_customers.getColumnModel().getColumn(1).setPreferredWidth(120);
            table_view_customers.getColumnModel().getColumn(1).setMaxWidth(150);
            table_view_customers.getColumnModel().getColumn(2).setPreferredWidth(100);
            table_view_customers.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        txt_search_customer.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        txt_search_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_customerActionPerformed(evt);
            }
        });
        txt_search_customer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_customerKeyReleased(evt);
            }
        });

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N
        lbl_search_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_search_iconMouseClicked(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(21, 76, 121));
        btn_delete.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_clear_fields.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_clear_fields.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_clear.png"))); // NOI18N
        btn_clear_fields.setText("ClearFields");
        btn_clear_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_customersLayout = new javax.swing.GroupLayout(panel_customers);
        panel_customers.setLayout(panel_customersLayout);
        panel_customersLayout.setHorizontalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addComponent(lbl_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customersLayout.createSequentialGroup()
                            .addComponent(lbl_first_name)
                            .addGap(18, 18, 18)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customersLayout.createSequentialGroup()
                            .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customersLayout.createSequentialGroup()
                                    .addComponent(lbl_last_name)
                                    .addGap(20, 20, 20))
                                .addGroup(panel_customersLayout.createSequentialGroup()
                                    .addComponent(lbl_contact)
                                    .addGap(11, 11, 11)))
                            .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_contact, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(txt_last_name))))
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_search_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_customersLayout.setVerticalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_first_name))
                        .addGap(18, 18, 18)
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_last_name)
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_contact)
                            .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_email)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_add)
                            .addComponent(btn_update))
                        .addGap(18, 18, 18)
                        .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_delete)
                            .addComponent(btn_clear_fields)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        desktop_pane_customers.setLayer(panel_customers, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_customersLayout = new javax.swing.GroupLayout(desktop_pane_customers);
        desktop_pane_customers.setLayout(desktop_pane_customersLayout);
        desktop_pane_customersLayout.setHorizontalGroup(
            desktop_pane_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_customersLayout.createSequentialGroup()
                .addComponent(panel_customers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        desktop_pane_customersLayout.setVerticalGroup(
            desktop_pane_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_customers, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_customers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_customers, javax.swing.GroupLayout.Alignment.TRAILING)
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

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
       if (txt_first_name.getText().trim().isEmpty() || txt_last_name.getText().trim().isEmpty() || 
                txt_contact.getText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Costumer", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            firstName = txt_first_name.getText();
            lastName = txt_last_name.getText();
            contactNo = txt_contact.getText();
            email = txt_email.getText();

            customer = new Customer(firstName, lastName, contactNo, email);
            
            
            try {
                
            dbConnection();
            
            String query = "SELECT * FROM customers WHERE contactNo = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo);
            rs = ps.executeQuery();
            
            //show a message if a costumer is not found in the db
            if (!rs.isBeforeFirst() && firstName.trim().isEmpty() && lastName.trim().isEmpty())
                JOptionPane.showMessageDialog(this, "Customer not found in the Database", "New Customer", JOptionPane.ERROR_MESSAGE);
            
            //add a new customer if not exists AND fields are not empty
            else if (!rs.isBeforeFirst() && !firstName.trim().isEmpty() && !lastName.trim().isEmpty())
            {
                int confirmInsertion = JOptionPane.showConfirmDialog(null, "Do you want to add a new Customer ?", "Add New Customer", JOptionPane.YES_NO_OPTION);
                if(confirmInsertion == 0)
                {
                    customer = new Customer(firstName, lastName, contactNo, email);
                    
                    String insertQuery = "INSERT INTO customers (firstName, lastName, contactNo, email) VALUES(?, ?, ?, ?)";
                    
                    ps = con.prepareStatement(insertQuery);
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getContactNo());
                    ps.setString(4, customer.getEmail());
                    ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(this, "Customer " + customer.getFirstName() + " " + customer.getLastName());
                            
                } 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
            
        if (txt_first_name.getText().trim().isEmpty() || txt_last_name.getText().trim().isEmpty() || 
                txt_contact.getText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Costumer", JOptionPane.ERROR_MESSAGE);
        }
        else
        {

            firstName = txt_first_name.getText();
            lastName = txt_last_name.getText();
            contactNo = txt_contact.getText();
            email = txt_email.getText();
            
      
            try {
                dbConnection(); 

                    while (rs.next())
                    {
//                       if( customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName) && customer.getContactNo().equals(contactNo) && customer.getEmail().equals(email))
//                       {
//                           JOptionPane.showMessageDialog(null, "No changes to be updated !", "Product List", JOptionPane.ERROR_MESSAGE);
//                       }
//                       else 
//                        {
                            int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Editing " + firstName + " ?", 
                                    "Edit Product|Service", JOptionPane.YES_NO_OPTION);
                            if(confirmEditing == 0)
                            {
                                String query = "UPDATE products SET productService = ?, price = ?, qty = ?, notes = ? WHERE productId = ?";
                                ps = con.prepareStatement(query);
                                ps.setString(1, firstName);
                                ps.setString(2, lastName);
                                ps.setString(3, contactNo);
                                ps.setString(4, email);
                                ps.executeUpdate();

                                loadCustomerTable();
                                cleanFields();
                            }
                            else
                            {
                                loadCustomerTable();
                                cleanFields();
                            }
                        //}
                    }

            } catch (SQLException ex) {
                    Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btn_updateActionPerformed

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

    private void txt_first_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_first_nameKeyReleased

    private void txt_last_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameKeyReleased

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
        
       
    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactKeyReleased

    private void txt_search_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_customerActionPerformed
     
    }//GEN-LAST:event_txt_search_customerActionPerformed

    private void txt_search_customerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_customerKeyReleased
        // TODO add your handling code here:
        ArrayList<Customer> customerList = new ArrayList<>();   
        String searchCustomer = txt_search_customer.getText();
        
        try {
            dbConnection();
            
             String query = "SELECT * FROM customers WHERE firstName LIKE '%" + searchCustomer + "%'"
                     + "OR lastName LIKE '%" + searchCustomer + "%' OR contactNo LIKE '%" + searchCustomer + "%'"
                     + " OR email LIKE '%" + searchCustomer + "%'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            
            while (rs.next())
            {
                customer = new Customer(rs.getString("firstName"), rs.getString("lastName"), rs.getString("contactNo"), rs.getString("email"));
                
                customerList.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel dtm = (DefaultTableModel)table_view_customers.getModel();
        dtm.setRowCount(0);
        
        Object[] row = new Object[4];
        for (int i = 0 ;i < customerList.size() ; i++)
        {
                row[0] = customerList.get(i).getFirstName();
                row[1] = customerList.get(i).getLastName();
                row[2] = customerList.get(i).getContactNo();
                row[3] = customerList.get(i).getEmail();
                
            dtm.addRow(row);
        }
    }//GEN-LAST:event_txt_search_customerKeyReleased

    private void table_view_customersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_customersMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2)
        {
            DefaultTableModel dtm = (DefaultTableModel) table_view_customers.getModel();
            int row = table_view_customers.getSelectedRow();
            
            String fname = dtm.getValueAt(row, 0).toString();
            String lname = dtm.getValueAt(row, 1).toString();
            String contact = dtm.getValueAt(row, 2).toString();
            String mail = dtm.getValueAt(row, 3).toString();
            
            
            txt_first_name.setText(fname);
            txt_last_name.setText(lname);
            txt_contact.setText(contact);
            txt_email.setText(mail);
        }
        
    }//GEN-LAST:event_table_view_customersMouseClicked

    private void lbl_search_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_search_iconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_search_iconMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        // TODO add your handling code here:
        cleanFields();
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_update;
    private javax.swing.JDesktopPane desktop_pane_customers;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JPanel panel_customers;
    private javax.swing.JTable table_view_customers;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_search_customer;
    // End of variables declaration//GEN-END:variables
}
