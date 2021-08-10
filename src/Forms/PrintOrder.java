/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import static com.mysql.cj.MysqlType.JSON;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import javax.swing.JTextPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;





/**
 *
 * @author user
 */
public class PrintOrder extends javax.swing.JFrame {

    /**
     * Creates new form print
     */
    public PrintOrder() {
        initComponents();
    }
    
    
    public PageFormat getPageFormat(PrinterJob pj)
    {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        
        double bodyHeight = 8.0;
        double headerHeight = 2.0;
        double footerHeight = 2.0;
        double width  = cm_to_pp(8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));
        
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        return pf;
        
    }
    
    protected static double cm_to_pp(double cm)
    {
        return toPPI(cm * 0.393600787);
    }
    protected static double toPPI(double inch)
    {
        return inch * 72d;
    }
   
    
//    public class BillPrintable implements Printable
//    {
//        @Override
//        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
//        throws PrintException
//        {
//            int result = NO_SUCH_PAGE;
//            if (pageIndex)
//            {
//                Graphics2D g2d = (Graphics2D) (Graphics) graphics;
//                double width = pageFormat.getImageableWidth();
//                g2d.translate(int) pageFormat.getImageableX(), (int) pageFormat.getImageableY();
//                
//                try
//                {
//                    int y = 20;
//                    int yShift = 10;
//                    int headerRectHeight = 15;
//                    
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                
//                result = PAGE_EXISTS;
//            }
//            
//            return result;
//        }
//    }
    
    String orderNo;
    String firstName;
    String lastName;
    String contact;
    String email;
    String deviceBrand;
    String deviceModel;
    String serialNumber;
    String importantNotes;
    ArrayList faults;
    ArrayList productsServices;
    ArrayList prices;
    double deposit;
    double due;
    
    PrintOrder(String _orderNo, String _firstName, String _lastName, String _contact, String _email, String _deviceBrand,
               String _deviceModel, String _serialNumber, ArrayList _faults, String _importantNotes, ArrayList _productsServices,
               ArrayList _prices, double _deposit, double _due) {
        
    
        initComponents();
        
        this.orderNo = _orderNo;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.contact = _contact;
        this.email = _email;
        this.deviceBrand = _deviceBrand;
        this.deviceModel = _deviceModel;
        this.serialNumber = _serialNumber;
        this.importantNotes = _importantNotes;
        this.productsServices = _productsServices;
        this.prices = _prices;
        this.deposit = _deposit;
        this.due = _due;
        
        txt_panel_print.setText(txt_panel_print.getText() + "Repair Order");
        txt_panel_print.setText(txt_panel_print.getText() + "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Order No " + this.orderNo +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Customer Name: " + this.firstName + " " + this.lastName + "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Contact No: " + this.contact +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Email Address: " + this.email +  "\n");
        
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Brand: " + this.deviceBrand +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Model: " + this.deviceModel +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "S/N: " + this.serialNumber +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Important Notes: " + this.importantNotes +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Product|Service: " + this.productsServices +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Price: €" + this.prices +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Deposit Paid: €" + this.deposit +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Due: €" + this.due +  "\n");
        
        txt_panel_print.setText(txt_panel_print.getText() + "**********************************************\n");
        txt_panel_print.setText(txt_panel_print.getText() + "**********************************************\n");
        txt_panel_print.setText(txt_panel_print.getText() + "********Thank you for trusting us*************\n");
        
        
        PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();
                    attr_set.add(MediaSizeName.ISO_A4);
                    attr_set.add(Sides.DUPLEX);
       // PrintSupport.printComponent(txt_panel_print);
        
//        try
//        {
//            boolean complete  = txt_panel_print.print();
//            if (complete) {
//                JOptionPane.showMessageDialog(null, "Done", "Information", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Printing", "Printer",JOptionPane.ERROR_MESSAGE);
//            }
//            
//        } catch (PrinterException ex) {
//                    
//                    JOptionPane.showMessageDialog(null, ex);
//                    }
//                    
        
        
        
//        try {
//            txt_panel_print.print();
//            
//            } catch (PrinterException ex) {
//            Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
        
        
        
        
        
                
    }
 
            
        
        
        

    


    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt_panel_print = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_print_faults = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_panel_print1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_panel_print.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jScrollPane1.setViewportView(txt_panel_print);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        table_print_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Faults"
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
        jScrollPane2.setViewportView(table_print_faults);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Product | Service", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(70);
        }

        txt_panel_print1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jScrollPane4.setViewportView(txt_panel_print1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 550, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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
            java.util.logging.Logger.getLogger(PrintOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintOrder().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable table_print_faults;
    private javax.swing.JTextPane txt_panel_print;
    private javax.swing.JTextPane txt_panel_print1;
    // End of variables declaration//GEN-END:variables
    
    
    
    private PrinterJob createPDFPrinterJob(PDDocument pdfDocument) {
        
        
    PrinterJob printJob = PrinterJob.getPrinterJob();

    PageFormat pageFormat = new PageFormat();
    //pageFormat.setOrientation(PageFormat.PORTRAIT);

    Paper paper = new Paper();
    pageFormat.setPaper(paper);
    
    //printJob.setPrintable(new PDPageable(pdfDocument), pageFormat);
    printJob.setPageable(new PDFPageable(pdfDocument));

    return printJob;
}
    
    
   
    
    private void printOrderDetails(JTextPane panel)
    {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Print Order Details");
        
        printerJob.defaultPage().getPaper();
        
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                if(pageIndex > 0)
                {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D)graphics;
                graphics2D.translate(pageFormat.getImageableX() * 2, pageFormat.getImageableY() * 2);
                graphics2D.scale(0.5, 0.5);
                
                
              txt_panel_print.paint(graphics2D);
              return Printable.PAGE_EXISTS;
            }
        });
        
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
}



/* print to PDF *need to test

        File file = new File("path/to/pdf");
                DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
                PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                attr.add(MediaSizeName.ISO_A4);
                FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
                
                Doc doc = new SimpleDoc(fis, flavor, null);
                DocPrintJob job = printService.createPrintJob();
        try {
            job.print(doc, attr);
        } catch (PrintException ex) {
            Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fis.close();
        
        } catch (IOException ex) {
            Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
