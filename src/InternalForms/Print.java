/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalForms;

import Forms.PrintOrder;
import static com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Pdf;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.qoppa.pdfWriter.PDFPrinterJob;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.text.DefaultCaret;

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
        //printOrderDetails();
        
        DefaultCaret caret = (DefaultCaret) txt_area_print_important_notes.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
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
        
        lbl_print_issued_date.setText(this._issuedDate);
        lbl_print_order_no.setText(this._orderNo);
        lbl_print_first_name.setText(this._firstName);
        lbl_print_last_name.setText(this._lastName);
        lbl_print_contact.setText(this._contactNo);
        lbl_print_email.setText(this._email);
        lbl_print_brand.setText(this._deviceBrand);
        lbl_print_model.setText(this._deviceModel);
        lbl_print_sn.setText(this._serialNumber);
        txt_area_print_important_notes.setText(this._importantNotes);
        lbl_print_total_products.setText("€" + String.valueOf(this._total));
        lbl_print_deposit.setText("€" + String.valueOf(this._deposit));
        lbl_print_due.setText("€" + String.valueOf(this._due));
        
       
//        DefaultTableModel printFaultsTable = (DefaultTableModel) table_view_print_faults.getModel();
//        printFaultsTable.setRowCount(0);
//        Vector faults = new Vector();
//        System.out.println(printFaultsTable);
//        
        for (int i = 0; i < _tableFaults.getRowCount(); i++)
        {
            String fault = (String) _tableFaults.getValueAt(i, NORMAL);
            txt_area_print_faults.setText(txt_area_print_faults.getText() +"- " + fault + "\n");
        }
        
       // DefaultTableModel printProductsTable = (DefaultTableModel) table_view_print_products.getModel();
        ///printProductsTable.setRowCount(0);
       // Vector products = new Vector();
        
        for (int i = 0; i < _tableProducts.getRowCount(); i++)
        {
            String product = (String) _tableProducts.getValueAt(i, 0);
            double price = (Double) _tableProducts.getValueAt(i, 1);
            txt_area_print_products.setText(txt_area_print_products.getText() + product + "\t\t" + "€" + price +"\n");
            //txt_area_print_products.setText(txt_area_print_products.getText() + String.format("%-40s%-10s%n",product, price));
            //product + ".....................€" + price + "\n"));
        }
        
        printOrderDetails();
        
        
    }
    
    
    public void createPDF()
    {
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x= j.showSaveDialog(this);
        
        if(x == JFileChooser.APPROVE_OPTION)
        {
            path = j.getSelectedFile().getPath();
        }
        
        Document document = new Document();
        
      
            
        try {
            
            PdfWriter.getInstance(document, new FileOutputStream(path + "order.pdf"));
            
            document.open();
            
            document.add(new Paragraph("Order number: "+ this._orderNo + "                   " + "Issued: " + this._issuedDate));
            
            document.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbl_print_order_no = new javax.swing.JLabel();
        lbl_customer_name = new javax.swing.JLabel();
        lbl_print_first_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_brand = new javax.swing.JLabel();
        lbl_model = new javax.swing.JLabel();
        lbl_sn = new javax.swing.JLabel();
        lbl_print_last_name = new javax.swing.JLabel();
        lbl_print_contact = new javax.swing.JLabel();
        lbl_order = new javax.swing.JLabel();
        lbl_print_email = new javax.swing.JLabel();
        lbl_print_brand = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_area_print_important_notes = new javax.swing.JTextArea();
        lbl_print_model = new javax.swing.JLabel();
        lbl_print_sn = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_print_total_products = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_issued = new javax.swing.JLabel();
        lbl_print_issued_date = new javax.swing.JLabel();
        lbl_due = new javax.swing.JLabel();
        lbl_print_deposit = new javax.swing.JLabel();
        lbl_deposit = new javax.swing.JLabel();
        lbl_print_due = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_area_print_products = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_area_print_faults = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setPreferredSize(new java.awt.Dimension(850, 820));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel2.setText("12A, Frederick Street North, Dublin 1");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel3.setText("01-563-9520");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel4.setText("083-012-8190");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel5.setText("Address:");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel6.setText("Mobile:");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jLabel7.setText("Phone:");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        lbl_print_order_no.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        lbl_print_order_no.setText("OrderNo");

        lbl_customer_name.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_customer_name.setText("Costumer Name :");

        lbl_print_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_print_first_name.setText("First Name");

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No. :");

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email :");

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_brand.setText("Device Brand :");

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_model.setText("Device Model :");

        lbl_sn.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_sn.setText("Serial Number :");

        lbl_print_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_print_last_name.setText("Last Name");

        lbl_print_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_contact.setText("Contact No.");

        lbl_order.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        lbl_order.setText("Order:");

        lbl_print_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_email.setText("Email");

        lbl_print_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_brand.setText("Device Brand");

        txt_area_print_important_notes.setEditable(false);
        txt_area_print_important_notes.setColumns(20);
        txt_area_print_important_notes.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_area_print_important_notes.setRows(5);
        txt_area_print_important_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 15))); // NOI18N
        txt_area_print_important_notes.setFocusTraversalKeysEnabled(false);
        txt_area_print_important_notes.setFocusable(false);
        txt_area_print_important_notes.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(txt_area_print_important_notes);

        lbl_print_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_model.setText("Device Model");

        lbl_print_sn.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_sn.setText("Serial Number");

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        lbl_print_total_products.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_total_products.setText("Total");

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_total.setText("Total ");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel12.setText("Customer Signature");

        lbl_issued.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_issued.setText("Issue Date:");

        lbl_print_issued_date.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lbl_print_issued_date.setText("issuedDate");

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_due.setText("Total Due");

        lbl_print_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_deposit.setText("Deposit");

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_deposit.setText("Deposit Paid");

        lbl_print_due.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_print_due.setText("Due");

        txt_area_print_products.setEditable(false);
        txt_area_print_products.setColumns(20);
        txt_area_print_products.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_area_print_products.setRows(5);
        txt_area_print_products.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product | Service", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 15))); // NOI18N
        txt_area_print_products.setRequestFocusEnabled(false);
        jScrollPane3.setViewportView(txt_area_print_products);

        txt_area_print_faults.setEditable(false);
        txt_area_print_faults.setColumns(20);
        txt_area_print_faults.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_area_print_faults.setRows(5);
        txt_area_print_faults.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fault", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 15))); // NOI18N
        txt_area_print_faults.setFocusable(false);
        txt_area_print_faults.setRequestFocusEnabled(false);
        jScrollPane4.setViewportView(txt_area_print_faults);

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addComponent(lbl_order)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_print_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addComponent(lbl_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_print_first_name)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_print_last_name))
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_issued)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_print_issued_date)
                        .addGap(188, 188, 188))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                                        .addComponent(lbl_sn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_print_sn))
                                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_brand)
                                            .addComponent(lbl_contact))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_print_brand)
                                            .addComponent(lbl_print_contact)))
                                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                                        .addComponent(lbl_model)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_print_model))
                                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                                        .addComponent(lbl_email)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_print_email, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                                .addGap(61, 61, 61)))
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_total)
                                    .addComponent(lbl_due)
                                    .addComponent(lbl_deposit))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_print_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                    .addComponent(lbl_print_total_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(20, 20, 20))))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_print_orderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_order)
                            .addComponent(lbl_print_order_no)))
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_print_issued_date, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_issued, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_customer_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_print_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_email)
                            .addComponent(lbl_print_email))
                        .addGap(18, 18, 18)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_contact)
                            .addComponent(lbl_print_contact))
                        .addGap(18, 18, 18)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_brand)
                            .addComponent(lbl_print_brand))
                        .addGap(18, 18, 18)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_model)
                            .addComponent(lbl_print_model))
                        .addGap(18, 18, 18)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_sn)
                            .addComponent(lbl_print_sn))
                        .addGap(18, 18, 18)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addComponent(lbl_total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_print_deposit)
                            .addComponent(lbl_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_print_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(22, 22, 22))
                            .addGroup(panel_print_orderLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel12)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addComponent(lbl_print_total_products)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_customer_name;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_issued;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_order;
    private javax.swing.JLabel lbl_print_brand;
    private javax.swing.JLabel lbl_print_contact;
    private javax.swing.JLabel lbl_print_deposit;
    private javax.swing.JLabel lbl_print_due;
    private javax.swing.JLabel lbl_print_email;
    private javax.swing.JLabel lbl_print_first_name;
    private javax.swing.JLabel lbl_print_issued_date;
    private javax.swing.JLabel lbl_print_last_name;
    private javax.swing.JLabel lbl_print_model;
    private javax.swing.JLabel lbl_print_order_no;
    private javax.swing.JLabel lbl_print_sn;
    private javax.swing.JLabel lbl_print_total_products;
    private javax.swing.JLabel lbl_sn;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JTextArea txt_area_print_faults;
    private javax.swing.JTextArea txt_area_print_important_notes;
    private javax.swing.JTextArea txt_area_print_products;
    // End of variables declaration//GEN-END:variables
}
