/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.PrintOrder;
import com.qoppa.pdfWriter.PDFPrinterJob;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class Print extends javax.swing.JFrame {

    /**
     * Creates new form Print
     */
    
    JTable _tableFaults;
    JTable _tableProducts;
    String _orderNo;
    String _firstName;
    String _lastName;
    String _contactNo;
    String _email;
    String _deviceBrand;
    String _deviceModel;
    String _serialNumber;
    String _importantNotes;
    double _deposit;
    double _due;
    double _total;
    String _issuedDate;
    
    public Print() {
        initComponents();
    }
    
    Print(String orderNo, String firstName, String lastName, String contactNo, String email, String deviceBrand, 
            String deviceModel, String serialNumber, JTable tableFaults, String importantNotes, JTable tableProducts, 
            double total, double deposit, double due, String issuedDate) {
        
        initComponents();
        
        txt_area_product_service.setBorder(null);
        txt_pane_faults.setCaretPosition(0);
        //printOrderDetails();
        
        //DefaultCaret caret = (DefaultCaret) txt_area_print_important_notes.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        this._orderNo = orderNo;
        this._firstName = firstName;
        this._lastName = lastName;
        this._contactNo = contactNo;
        this._email = email;
        this._deviceBrand = deviceBrand;
        this._deviceModel = deviceModel;
        this._serialNumber = serialNumber;
        this._importantNotes = importantNotes;
        this._deposit = deposit;
        this._due = due;
        this._tableFaults = tableFaults;
        this._tableProducts = tableProducts;
        this._total = total;
        this._issuedDate = issuedDate;
        
        lbl_print_issued_date.setText("Date: " + this._issuedDate);
        lbl_print_order_no.setText("Order: " + this._orderNo);
        lbl_print_first_name.setText("Customer name: " + this._firstName + " " + this._lastName);
        //lbl_print_last_name.setText(this._lastName);
        lbl_print_contact.setText("Contact no.: " + this._contactNo);
        lbl_print_email.setText("Email:" + this._email);
        lbl_print_brand.setText("Device brand: " + this._deviceBrand);
        lbl_print_model.setText("Device model: " + this._deviceModel);
        lbl_print_sn.setText("S/N: " + this._serialNumber);
        txt_pane_important_notes.setText(this._importantNotes);
        lbl_print_total_products.setText("Total: €" + String.valueOf(this._total));
        lbl_print_deposit.setText("Deposit paid: €" + String.valueOf(this._deposit));
        lbl_print_due.setText("Due:     €" + String.valueOf(this._due));
        
       
//        DefaultTableModel printFaultsTable = (DefaultTableModel) table_view_print_faults.getModel();
//        printFaultsTable.setRowCount(0);
//        Vector faults = new Vector();
//        System.out.println(printFaultsTable);
//        
        for (int i = 0; i < _tableFaults.getRowCount(); i++)
        {
            String fault = (String) _tableFaults.getValueAt(i, NORMAL);
            if (_tableFaults.getRowCount() == 0)  {
                txt_pane_faults.setText(txt_pane_faults.getText() + fault + ".");
            }
            else {
            txt_pane_faults.setText(txt_pane_faults.getText() + fault + ",   ");
            }
        }
        
       // DefaultTableModel printProductsTable = (DefaultTableModel) table_view_print_products.getModel();
        ///printProductsTable.setRowCount(0);
       // Vector products = new Vector();
        
        for (int i = 0; i < _tableProducts.getRowCount(); i++)
        {
            String product = (String) _tableProducts.getValueAt(i, 0);
            double price = (Double) _tableProducts.getValueAt(i, 1);
            txt_area_product_service.setText(txt_area_product_service.getText() + " - " + product + "\t\t\t" + "€" + price +"\n");
        }
        
        printOrderDetails();    
    }
    
    
    public void printPDF()
    {
        PDFPrinterJob printer = (PDFPrinterJob)PDFPrinterJob.getPrinterJob();
         printer.setPrintable((Printable) new Print());
         printer.setCopies(1);
         
        try {
            printer.print("/Users/HermanCosta/Desktop/pcHouse/mydoc.pdf");
            System.out.println("Done!");
            
        } catch (PrinterException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    
      private void printOrderDetails()
    {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Print Order Details");
        
        PageFormat format = printerJob.getPageFormat(null);
        //printerJob.defaultPage().getPaper();
        
        printerJob.setPrintable(new Printable() {
            
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                
                if(pageIndex > 0)
                {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D)graphics;
                
                //this.Paint(graphics2D);
               // graphics2D.translate(pageFormat.getImageableX() * 2, pageFormat.getImageableY() * 2);
                //graphics2D.scale(0.5, 0.5);
                
                    
             panel_print_order.paint(graphics2D);
              return Printable.PAGE_EXISTS;
            }
        }, format);
        
        boolean returningResult = printerJob.printDialog();
        
        //MessageFormat footer = new MessageFormat("Page - {0}");
        
        if(returningResult)
        {
            try {
                printerJob.print();

            } catch (PrinterException ex) {
                Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.dispose();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_print_order = new javax.swing.JPanel();
        lbl_logo_icon = new javax.swing.JLabel();
        lbl_address = new javax.swing.JLabel();
        lbl_land_line_number = new javax.swing.JLabel();
        lbl_mobile_number = new javax.swing.JLabel();
        lbl_mobile = new javax.swing.JLabel();
        lbl_phone = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_print_order_no = new javax.swing.JLabel();
        lbl_print_first_name = new javax.swing.JLabel();
        lbl_print_contact = new javax.swing.JLabel();
        lbl_print_email = new javax.swing.JLabel();
        lbl_print_brand = new javax.swing.JLabel();
        lbl_print_model = new javax.swing.JLabel();
        lbl_print_sn = new javax.swing.JLabel();
        lbl_print_total_products = new javax.swing.JLabel();
        lbl_print_issued_date = new javax.swing.JLabel();
        lbl_important_notes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_pane_important_notes = new javax.swing.JTextPane();
        lbl_faults = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_pane_faults = new javax.swing.JTextPane();
        lbl_product_service = new javax.swing.JLabel();
        lbl_print_deposit = new javax.swing.JLabel();
        lbl_print_due = new javax.swing.JLabel();
        line_customer_signature = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_area_product_service = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        line_header.setForeground(new java.awt.Color(0, 0, 0));

        lbl_print_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_order_no.setText("orderNo");

        lbl_print_first_name.setText("fullName");

        lbl_print_contact.setText("contactNo");

        lbl_print_email.setText("emailAddress");

        lbl_print_brand.setText("deviceBrand");

        lbl_print_model.setText("deviceModel");

        lbl_print_sn.setText("serialNumber");

        lbl_print_total_products.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_total_products.setText("totalProducts");

        lbl_print_issued_date.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        lbl_print_issued_date.setText("issuedDate");

        lbl_important_notes.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_important_notes.setText("Important Notes:");

        jScrollPane1.setBorder(null);

        txt_pane_important_notes.setEditable(false);
        txt_pane_important_notes.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        txt_pane_important_notes.setFocusable(false);
        jScrollPane1.setViewportView(txt_pane_important_notes);

        lbl_faults.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_faults.setText("Fault:");

        jScrollPane2.setBorder(null);

        txt_pane_faults.setEditable(false);
        txt_pane_faults.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_pane_faults.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(txt_pane_faults);

        lbl_product_service.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_product_service.setText("Product | Service:");

        lbl_print_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_deposit.setText("deposit");

        lbl_print_due.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_print_due.setText("due");

        line_customer_signature.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setText("Customer Signature");

        jScrollPane3.setBorder(null);

        txt_area_product_service.setEditable(false);
        txt_area_product_service.setColumns(20);
        txt_area_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        txt_area_product_service.setRows(5);
        jScrollPane3.setViewportView(txt_area_product_service);

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(232, 232, 232))
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_print_orderLayout.createSequentialGroup()
                            .addGap(119, 119, 119)
                            .addComponent(line_customer_signature, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_print_orderLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_print_issued_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_print_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_print_order_no, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_print_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_print_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_print_email, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                                .addComponent(lbl_print_brand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbl_print_sn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_due, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_deposit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_print_orderLayout.createSequentialGroup()
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
                                                    .addComponent(lbl_address, javax.swing.GroupLayout.Alignment.TRAILING)))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                                            .addComponent(lbl_faults, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_product_service, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(lbl_important_notes))
                                    .addComponent(jScrollPane3)
                                    .addComponent(lbl_print_total_products, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbl_logo_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
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
                .addGap(4, 4, 4)
                .addComponent(lbl_print_issued_date)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_contact)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_email)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_brand)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_model)
                .addGap(4, 4, 4)
                .addComponent(lbl_print_sn)
                .addGap(18, 18, 18)
                .addComponent(lbl_important_notes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_faults)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_product_service)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_print_total_products)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_print_deposit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_print_due)
                .addGap(70, 70, 70)
                .addComponent(line_customer_signature, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addGap(1069, 1069, 1069))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Print().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_address;
    private javax.swing.JLabel lbl_faults;
    private javax.swing.JLabel lbl_important_notes;
    private javax.swing.JLabel lbl_land_line_number;
    private javax.swing.JLabel lbl_logo_icon;
    private javax.swing.JLabel lbl_mobile;
    private javax.swing.JLabel lbl_mobile_number;
    private javax.swing.JLabel lbl_phone;
    private javax.swing.JLabel lbl_print_brand;
    private javax.swing.JLabel lbl_print_contact;
    private javax.swing.JLabel lbl_print_deposit;
    private javax.swing.JLabel lbl_print_due;
    private javax.swing.JLabel lbl_print_email;
    private javax.swing.JLabel lbl_print_first_name;
    private javax.swing.JLabel lbl_print_issued_date;
    private javax.swing.JLabel lbl_print_model;
    private javax.swing.JLabel lbl_print_order_no;
    private javax.swing.JLabel lbl_print_sn;
    private javax.swing.JLabel lbl_print_total_products;
    private javax.swing.JLabel lbl_product_service;
    private javax.swing.JSeparator line_customer_signature;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JTextArea txt_area_product_service;
    private javax.swing.JTextPane txt_pane_faults;
    private javax.swing.JTextPane txt_pane_important_notes;
    // End of variables declaration//GEN-END:variables
}
