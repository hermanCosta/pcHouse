/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.PrintComputerLabel;
import Model.Computer;
import com.sun.glass.events.KeyEvent;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ComputerList extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewOrder
     */
    ArrayList brands = new ArrayList();
    ArrayList models = new ArrayList();
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Statement stmt;
    Computer computer;
    String brand, model, serialNumber, processor, ram, storage, gpu, screen, notes;
    int computerId, qty;
    double price;
    boolean isTableEmpty = false;
    
    public ComputerList() {
        initComponents();
        
        //Remove borders
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        tableSettings(table_view_computers);
        accessDbColumn(brands, "SELECT * FROM computers", "brand");
        accessDbColumn(models, "SELECT * FROM computers", "model");
        
        loadComputerTable();
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
            Logger.getLogger(ComputerList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadComputerTable()
    {
        ArrayList<Computer> list = new ArrayList<>();
        
        try {
            dbConnection();
            
            String query = "SELECT * FROM computers";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            
            while (rs.next())
            {
                computer = new Computer(rs.getString("brand"), rs.getString("model"), rs.getString("serialNumber"), rs.getString("processor"), 
                rs.getString("ram"), rs.getString("storage"), rs.getString("gpu"), rs.getString("screen"), rs.getString("notes"), 
                        rs.getInt("qty"), rs.getDouble("price"));
                computer.setComputerId(rs.getInt("computerId"));
                
                list.add(computer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComputerList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel dtm = (DefaultTableModel)table_view_computers.getModel();
        dtm.setRowCount(0);
        
        Object[] row = new Object[6];
        for (int i = 0 ;i < list.size() ; i++)
        {
                row[0] = list.get(i).getComputerId();
                row[1] = list.get(i).getBrand();
                row[2] = list.get(i).getModel();
                row[3] = list.get(i).getSerialNumber();
                row[4] = list.get(i).getQty();
                row[5] = list.get(i).getPrice();
                
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
            Logger.getLogger(ComputerList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getTextFields()
    {
        brand = txt_brand.getText();
        model = txt_model.getText();
        serialNumber = txt_serial_number.getText();
        processor = txt_processor.getText();
        ram = txt_ram.getText();
        storage = txt_storage.getText();
        gpu = txt_gpu.getText();
        screen = txt_screen.getText();
        notes = editor_pane_notes.getText();
        qty = Integer.parseInt(txt_qty.getText());
        price = Double.parseDouble(txt_price.getText());
        
        computer = new Computer(brand, model, serialNumber, processor, ram, storage, gpu, screen, notes, qty, price);
    }
    public void cleanFields()
    {
        //Clean all Fields 
            txt_brand.setText("");
            txt_model.setText("");
            txt_serial_number.setText("");
            txt_processor.setText("");
            txt_ram.setText("");
            txt_storage.setText("");
            txt_gpu.setText("");
            txt_screen.setText("");
            editor_pane_notes.setText("");
            txt_qty.setText("");
            txt_price.setText("");
            
            txt_brand.requestFocus();
    }
    
    public void saveComputerIntoDb()
    {
//            brand = txt_brand.getText();
//            model = txt_model.getText();
//            serialNumber = txt_serial_number.getText();
//            processor = txt_processor.getText();
//            ram = txt_ram.getText();
//            storage = txt_storage.getText();
//            gpu = txt_gpu.getText();
//            screen = txt_screen.getText();
//            notes = editor_pane_notes.getText();
//            qty = Integer.parseInt(txt_qty.getText());
//            price = Double.parseDouble(txt_price.getText());
            
        
            try {
                
                dbConnection();
                    
                getTextFields();
                
                int confirmInsertion = JOptionPane.showConfirmDialog(this, "Do you want to add a Computer " + computer.getBrand() 
                        + " " + computer.getModel() + " ?", "Add New Computer", JOptionPane.YES_NO_OPTION);
                if(confirmInsertion == 0)
                {
                    String insertQuery = "INSERT INTO computers(brand, model, serialNumber, processor, ram, storage, gpu, screen, "
                            + " notes, qty, price) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    ps = con.prepareStatement(insertQuery);
                    ps.setString(1, computer.getBrand());
                    ps.setString(2, computer.getModel());
                    ps.setString(3, computer.getSerialNumber());
                    ps.setString(4, computer.getProcessor());
                    ps.setString(5, computer.getRam());
                    ps.setString(6, computer.getStorage());
                    ps.setString(7, computer.getGpu());
                    ps.setString(8, computer.getScreen());
                    ps.setString(9, computer.getNotes());
                    ps.setInt(10, computer.getQty());
                    ps.setDouble(11, computer.getPrice());
                    
                    ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(this, computer.getBrand() + " " + computer.getModel() + " added Successfully");
                    cleanFields();
                    loadComputerTable();
                } 
            
        } catch (SQLException ex) {
            Logger.getLogger(ComputerList.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop_pane_computers = new javax.swing.JDesktopPane();
        panel_computers = new javax.swing.JPanel();
        lbl_brand = new javax.swing.JLabel();
        lbl_model = new javax.swing.JLabel();
        lbl_serial_number = new javax.swing.JLabel();
        lbl_peocessor = new javax.swing.JLabel();
        txt_brand = new javax.swing.JTextField();
        txt_model = new javax.swing.JTextField();
        txt_processor = new javax.swing.JTextField();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_computers = new javax.swing.JTable();
        btn_delete = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();
        lbl_ram = new javax.swing.JLabel();
        txt_ram = new javax.swing.JTextField();
        lbl_storage = new javax.swing.JLabel();
        txt_storage = new javax.swing.JTextField();
        lbl_gpu = new javax.swing.JLabel();
        txt_gpu = new javax.swing.JTextField();
        lbl_screen = new javax.swing.JLabel();
        txt_screen = new javax.swing.JTextField();
        lbl_qty = new javax.swing.JLabel();
        txt_qty = new javax.swing.JTextField();
        lbl_price = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        txt_serial_number = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();
        btn_clear_fields1 = new javax.swing.JButton();
        txt_search_computer = new javax.swing.JTextField();
        lbl_search_icon = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1049, 700));
        setSize(new java.awt.Dimension(1049, 700));

        panel_computers.setPreferredSize(new java.awt.Dimension(1049, 700));

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_brand.setText("Brand");

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_model.setText("Model");

        lbl_serial_number.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_serial_number.setText("S/N");

        lbl_peocessor.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_peocessor.setText("Processor");

        txt_brand.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_brandActionPerformed(evt);
            }
        });
        txt_brand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_brandKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_brandKeyReleased(evt);
            }
        });

        txt_model.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_model.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_modelActionPerformed(evt);
            }
        });
        txt_model.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_modelKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_modelKeyReleased(evt);
            }
        });

        txt_processor.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_processor.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_processor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_processorActionPerformed(evt);
            }
        });
        txt_processor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_processorKeyPressed(evt);
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
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        table_view_computers.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_computers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Serial Number", "Qty", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            table_view_computers.getColumnModel().getColumn(0).setPreferredWidth(40);
            table_view_computers.getColumnModel().getColumn(0).setMaxWidth(80);
            table_view_computers.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_view_computers.getColumnModel().getColumn(1).setMaxWidth(150);
            table_view_computers.getColumnModel().getColumn(4).setPreferredWidth(50);
            table_view_computers.getColumnModel().getColumn(4).setMaxWidth(150);
            table_view_computers.getColumnModel().getColumn(5).setPreferredWidth(100);
            table_view_computers.getColumnModel().getColumn(5).setMaxWidth(150);
        }

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

        lbl_ram.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_ram.setText("RAM");

        txt_ram.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_ram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ramActionPerformed(evt);
            }
        });
        txt_ram.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ramKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ramKeyReleased(evt);
            }
        });

        lbl_storage.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_storage.setText("Storage");

        txt_storage.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_storage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_storageActionPerformed(evt);
            }
        });
        txt_storage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_storageKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_storageKeyReleased(evt);
            }
        });

        lbl_gpu.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_gpu.setText("GPU");

        txt_gpu.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_gpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gpuActionPerformed(evt);
            }
        });
        txt_gpu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gpuKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_gpuKeyReleased(evt);
            }
        });

        lbl_screen.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_screen.setText("Screen");

        txt_screen.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_screen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_screenActionPerformed(evt);
            }
        });
        txt_screen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_screenKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_screenKeyReleased(evt);
            }
        });

        lbl_qty.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_qty.setText("Qty");

        txt_qty.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qtyActionPerformed(evt);
            }
        });
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_qtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_qtyKeyReleased(evt);
            }
        });

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_price.setText("Price €");

        txt_price.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_priceActionPerformed(evt);
            }
        });
        txt_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_priceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_priceKeyReleased(evt);
            }
        });

        txt_serial_number.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
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

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        editor_pane_notes.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));
        editor_pane_notes.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jScrollPane1.setViewportView(editor_pane_notes);

        btn_clear_fields1.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields1.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        btn_clear_fields1.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_clear_fields1.setText("Print Label");
        btn_clear_fields1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fields1ActionPerformed(evt);
            }
        });

        txt_search_computer.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        txt_search_computer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_computerActionPerformed(evt);
            }
        });
        txt_search_computer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_computerKeyReleased(evt);
            }
        });

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N
        lbl_search_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_search_iconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_computersLayout = new javax.swing.GroupLayout(panel_computers);
        panel_computers.setLayout(panel_computersLayout);
        panel_computersLayout.setHorizontalGroup(
            panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_computersLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_computersLayout.createSequentialGroup()
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_clear_fields1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_computersLayout.createSequentialGroup()
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_computersLayout.createSequentialGroup()
                                .addComponent(lbl_storage)
                                .addGap(18, 18, 18)
                                .addComponent(txt_storage, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_computersLayout.createSequentialGroup()
                                    .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_screen)
                                        .addComponent(lbl_gpu))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txt_gpu, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_screen, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel_computersLayout.createSequentialGroup()
                                            .addComponent(lbl_price)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_computersLayout.createSequentialGroup()
                                    .addComponent(lbl_brand)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_brand))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_computersLayout.createSequentialGroup()
                                    .addComponent(lbl_model)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_model))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_computersLayout.createSequentialGroup()
                                    .addComponent(lbl_serial_number)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel_computersLayout.createSequentialGroup()
                                        .addComponent(lbl_ram)
                                        .addGap(35, 35, 35)
                                        .addComponent(txt_ram, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel_computersLayout.createSequentialGroup()
                                        .addComponent(lbl_peocessor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_processor, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panel_computersLayout.createSequentialGroup()
                                .addComponent(lbl_qty)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_computersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_search_computer, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
        );
        panel_computersLayout.setVerticalGroup(
            panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_computersLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search_computer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_computersLayout.createSequentialGroup()
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_brand))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_model)
                            .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_serial_number)
                            .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_peocessor)
                            .addComponent(txt_processor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ram)
                            .addComponent(txt_ram, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_storage)
                            .addComponent(txt_storage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_gpu)
                            .addComponent(txt_gpu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_screen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_screen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_qty)
                            .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_price))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panel_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear_fields1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        desktop_pane_computers.setLayer(panel_computers, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktop_pane_computersLayout = new javax.swing.GroupLayout(desktop_pane_computers);
        desktop_pane_computers.setLayout(desktop_pane_computersLayout);
        desktop_pane_computersLayout.setHorizontalGroup(
            desktop_pane_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktop_pane_computersLayout.createSequentialGroup()
                .addComponent(panel_computers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        desktop_pane_computersLayout.setVerticalGroup(
            desktop_pane_computersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_computers, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desktop_pane_computers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop_pane_computers, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_processorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_processorActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_processorActionPerformed

    private void txt_modelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_modelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_modelActionPerformed

    private void txt_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_brandActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_txt_brandActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
       if (txt_brand.getText().trim().isEmpty() || txt_model.getText().trim().isEmpty() || 
                txt_serial_number.getText().trim().isEmpty() || txt_processor.getText().trim().isEmpty() || 
               txt_ram.getText().trim().isEmpty() || txt_storage.getText().trim().isEmpty() || txt_gpu.getText().trim().isEmpty() ||  
               txt_screen.getText().trim().isEmpty() || txt_qty.getText().trim().isEmpty() || txt_price.getText().trim().isEmpty()) 

           JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Computer", JOptionPane.ERROR_MESSAGE);

       else
        {
            
            if (isTableEmpty)
            {
                
                    
            //add a new computer if not exists AND fields are not empty
                if (computer.getBrand().equals(brand) && computer.getModel().equals(model) && computer.getSerialNumber().equals(serialNumber))
                       JOptionPane.showMessageDialog(this, "Computer already exist with Serial Number " + computer.getSerialNumber() + " into Database", "New Comoputer", JOptionPane.ERROR_MESSAGE);

               else
               {
                   saveComputerIntoDb();
               } 
            }
            
            else
                saveComputerIntoDb();
                
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        
        if (txt_brand.getText().trim().isEmpty() || txt_model.getText().trim().isEmpty() || 
                txt_serial_number.getText().trim().isEmpty() || txt_processor.getText().trim().isEmpty() || 
               txt_ram.getText().trim().isEmpty() || txt_storage.getText().trim().isEmpty() || txt_gpu.getText().trim().isEmpty() ||  
               txt_screen.getText().trim().isEmpty() || txt_qty.getText().trim().isEmpty() || txt_price.getText().trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Computer", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            brand = txt_brand.getText();
            model = txt_model.getText();
            serialNumber = txt_serial_number.getText();
            processor = txt_processor.getText();
            ram = txt_ram.getText();
            storage = txt_storage.getText();
            gpu = txt_gpu.getText();
            screen = txt_screen.getText();
            notes = editor_pane_notes.getText();
            qty = Integer.parseInt(txt_qty.getText());
            price = Double.parseDouble(txt_price.getText());
      
            try {
                
                dbConnection(); 

                       if( computer.getBrand().equals(brand) && computer.getModel().equals(model) && computer.getSerialNumber().equals(serialNumber) 
                              && computer.getProcessor().equals(processor)  && computer.getRam().equals(ram) && computer.getStorage().equals(storage)
                              && computer.getGpu().equals(gpu) && computer.getScreen().equals(screen) && computer.getNotes().equals(notes)
                              && computer.getQty() == qty && computer.getPrice() == price) 
                       {
                           JOptionPane.showMessageDialog(null, "No changes to be updated !", "Update Computer", JOptionPane.ERROR_MESSAGE);
                       }
                       else 
                        {
                            int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Updating '" + brand + " " + model + "' ?", 
                                    "Update Computer", JOptionPane.YES_NO_OPTION);
                            
                            if(confirmEditing == 0)
                            {
                                String query = "UPDATE computers SET brand = ?, model = ?, serialNumber = ?, processor = ?, ram = ?, "
                                        + "storage = ?, gpu = ?, screen = ?, notes = ?, qty = ?, price = ? WHERE computerId = ?";
                                ps = con.prepareStatement(query);
                                ps.setString(1, brand);
                                ps.setString(2, model);
                                ps.setString(3, serialNumber);
                                ps.setString(4, processor);
                                ps.setString(5, ram);
                                ps.setString(6, storage);
                                ps.setString(7, gpu);
                                ps.setString(8, screen);
                                ps.setString(9, notes);
                                ps.setInt(10, qty);
                                ps.setDouble(11, price);
                                ps.setInt(12, computer.getComputerId());
                                
                                ps.executeUpdate();

                                loadComputerTable();
                                cleanFields();
                            }
                            else
                            {
                                loadComputerTable();
                                cleanFields();
                            }
                    }

            } catch (SQLException ex) {
                    Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void txt_brandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_brandKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_BACKSPACE:
                break;
            
            case KeyEvent.VK_ENTER:
                txt_brand.setText(txt_brand.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_brand.getText();
                    autoCompleteFromDb(brands, text, txt_brand);
            });
        }
    }//GEN-LAST:event_txt_brandKeyPressed

    private void txt_processorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_processorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_processorKeyPressed

    private void txt_brandKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_brandKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_brandKeyReleased

    private void txt_modelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_modelKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_modelKeyReleased

    private void txt_modelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_modelKeyPressed
        // TODO add your handling code here:
        //Sugest autoComplete firstNames from Database
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_BACKSPACE:
            break;

            case KeyEvent.VK_ENTER:
            txt_model.setText(txt_model.getText());
            break;
            default:
            EventQueue.invokeLater(() -> {
                String text = txt_model.getText();
                autoCompleteFromDb(models, text, txt_model);
            });
        }
    }//GEN-LAST:event_txt_modelKeyPressed

    private void table_view_computersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_computersMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2)
        {
            try {
                dbConnection();
                DefaultTableModel dtm = (DefaultTableModel) table_view_computers.getModel();
                
                if (dtm.getRowCount() == 0)
                {
                    isTableEmpty = true;
                }
                int row = table_view_computers.getSelectedRow();
                
                int id = (int) dtm.getValueAt(row, 0);
                String query = "SELECT * FROM computers WHERE computerId = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                while (rs.next())
                {
                    computer = new Computer(rs.getString("brand"), rs.getString("model"), rs.getString("serialNumber"), 
                            rs.getString("processor"), rs.getString("ram"), rs.getString("storage"), rs.getString("gpu"), 
                            rs.getString("screen"), rs.getString("notes"), rs.getInt("qty"), rs.getDouble("price"));
                    
                    computer.setComputerId(id);
                }
                
                txt_brand.setText(computer.getBrand());
                txt_model.setText(computer.getModel());
                txt_serial_number.setText(computer.getSerialNumber());
                txt_processor.setText(computer.getProcessor());
                txt_ram.setText(computer.getRam());
                txt_storage.setText(computer.getStorage());
                txt_gpu.setText(computer.getGpu());
                txt_screen.setText(computer.getScreen());
                editor_pane_notes.setText(computer.getNotes());
                txt_qty.setText(String.valueOf(computer.getQty()));
                txt_price.setText(String.valueOf(computer.getPrice()));
                
                    } catch (SQLException ex) {
                        Logger.getLogger(ComputerList.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
        }
    }//GEN-LAST:event_table_view_computersMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
         if (txt_brand.getText().trim().isEmpty() || txt_model.getText().trim().isEmpty() || 
                txt_serial_number.getText().trim().isEmpty() || txt_processor.getText().trim().isEmpty() || 
               txt_ram.getText().trim().isEmpty() || txt_storage.getText().trim().isEmpty() || txt_gpu.getText().trim().isEmpty() ||  
               txt_screen.getText().trim().isEmpty() || txt_qty.getText().trim().isEmpty() || txt_price.getText().trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Computer", JOptionPane.ERROR_MESSAGE);
        }
         
        else
        {
            try {
                
                dbConnection(); 

                            int confirmEditing = JOptionPane.showConfirmDialog(this, "Confirm Deletion on '" + computer.getBrand() 
                                    + " " + computer.getModel() + "' ?", "Delete Computer", JOptionPane.YES_NO_OPTION);
                            
                            if(confirmEditing == 0)
                            {
                                String query = "DELETE FROM computers WHERE computerId = ?";
                                ps = con.prepareStatement(query);
                                ps.setInt(1, computer.getComputerId());
                                ps.executeUpdate();

                                loadComputerTable();
                                cleanFields();
                            }
                            else
                            {
                                loadComputerTable();
                                cleanFields();
                            }

            } catch (SQLException ex) {
                    Logger.getLogger(ProductsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        // TODO add your handling code here:
        cleanFields();
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    private void txt_ramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ramActionPerformed

    private void txt_ramKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ramKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ramKeyPressed

    private void txt_ramKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ramKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ramKeyReleased

    private void txt_storageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_storageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_storageActionPerformed

    private void txt_storageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_storageKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_storageKeyPressed

    private void txt_storageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_storageKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_storageKeyReleased

    private void txt_gpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gpuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpuActionPerformed

    private void txt_gpuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gpuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpuKeyPressed

    private void txt_gpuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gpuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpuKeyReleased

    private void txt_screenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_screenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_screenActionPerformed

    private void txt_screenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_screenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_screenKeyPressed

    private void txt_screenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_screenKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_screenKeyReleased

    private void txt_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_qtyActionPerformed

    private void txt_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            
            txt_price.setEditable(false);
        }
        else
        {
            txt_price.setEditable(true);
        }       
    }//GEN-LAST:event_txt_qtyKeyPressed

    private void txt_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_qtyKeyReleased

    private void txt_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_priceActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_priceActionPerformed

    private void txt_priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_priceKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            
            txt_price.setEditable(false);
        }
        else
        {
            txt_price.setEditable(true);
        }       
    }//GEN-LAST:event_txt_priceKeyPressed

    private void txt_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_priceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_priceKeyReleased

    private void txt_serial_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serial_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serial_numberActionPerformed

    private void txt_serial_numberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serial_numberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serial_numberKeyPressed

    private void txt_serial_numberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serial_numberKeyReleased
        // TODO add your handling code here:
       txt_serial_number.setText(txt_serial_number.getText().toUpperCase());
    }//GEN-LAST:event_txt_serial_numberKeyReleased

    private void btn_clear_fields1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fields1ActionPerformed
        // TODO add your handling code here:
         if (txt_brand.getText().trim().isEmpty() || txt_model.getText().trim().isEmpty() || 
                txt_serial_number.getText().trim().isEmpty() || txt_processor.getText().trim().isEmpty() || 
               txt_ram.getText().trim().isEmpty() || txt_storage.getText().trim().isEmpty() || txt_gpu.getText().trim().isEmpty() ||  
               txt_screen.getText().trim().isEmpty() || txt_qty.getText().trim().isEmpty() || txt_price.getText().trim().isEmpty()) 

           JOptionPane.showMessageDialog(this, "Please, check Empty fields", "Print Label", JOptionPane.ERROR_MESSAGE);
         
       else
        {
            new PrintComputerLabel(computer).setVisible(true);
        }
    }//GEN-LAST:event_btn_clear_fields1ActionPerformed

    private void txt_search_computerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_computerActionPerformed

    }//GEN-LAST:event_txt_search_computerActionPerformed

    private void txt_search_computerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_computerKeyReleased
        // TODO add your handling code here:
        ArrayList<Computer> compList = new ArrayList<>();
        String searchComp = txt_search_computer.getText();

        try {
            dbConnection();

            String query = "SELECT * FROM computers WHERE brand LIKE '%" + searchComp + "%'"
            + "OR model LIKE '%" + searchComp + "%' OR serialNumber LIKE '%" + searchComp + "%'"
            + " OR price LIKE '%" + searchComp + "%'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next())
            {
                computer = new Computer(rs.getString("brand"), rs.getString("model"), rs.getString("serialNumber"), 
                            rs.getString("processor"), rs.getString("ram"), rs.getString("storage"), rs.getString("gpu"), 
                            rs.getString("screen"), rs.getString("notes"), rs.getInt("qty"), rs.getDouble("price"));
                
                computer.setComputerId(rs.getInt("computerId"));
                compList.add(computer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Computer.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableModel dtm = (DefaultTableModel)table_view_computers.getModel();
        dtm.setRowCount(0);

        Object[] row = new Object[6];
        for (int i = 0 ;i < compList.size() ; i++)
        {
            row[0] = compList.get(i).getComputerId();
            row[1] = compList.get(i).getBrand();
            row[2] = compList.get(i).getModel();
            row[3] = compList.get(i).getSerialNumber();
            row[4] = compList.get(i).getQty();
            row[5] = compList.get(i).getPrice();

            dtm.addRow(row);
        }
    }//GEN-LAST:event_txt_search_computerKeyReleased

    private void lbl_search_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_search_iconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_search_iconMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_clear_fields1;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_update;
    private javax.swing.JDesktopPane desktop_pane_computers;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_gpu;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_peocessor;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_qty;
    private javax.swing.JLabel lbl_ram;
    private javax.swing.JLabel lbl_screen;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JLabel lbl_serial_number;
    private javax.swing.JLabel lbl_storage;
    private javax.swing.JPanel panel_computers;
    private javax.swing.JTable table_view_computers;
    private javax.swing.JTextField txt_brand;
    private javax.swing.JTextField txt_gpu;
    private javax.swing.JTextField txt_model;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_processor;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_ram;
    private javax.swing.JTextField txt_screen;
    private javax.swing.JTextField txt_search_computer;
    private javax.swing.JTextField txt_serial_number;
    private javax.swing.JTextField txt_storage;
    // End of variables declaration//GEN-END:variables
}
