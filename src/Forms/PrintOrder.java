/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
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
    
    Paper paper = new Paper();
    
    
    String _orderNo;
    String _firstName;
    String _lastName;
    String _contact;
    String _email;
    String _deviceBrand;
    String _deviceModel;
    String _serialNumber;
    String _fault;
    String _importantNotes;
    String _serviceProduct;
    String _price;
    String _deposit;
    String _due;
    
    PrintOrder(String orderNo, String firstName, String lastName, String contact, String email, String deviceBrand,
               String deviceModel, String serialNumber, String fault, String importantNotes, String serviceProduct,
               String price, String deposit, String due) {
        
    
        initComponents();
        
        this._orderNo = orderNo;
        this._firstName = firstName;
        this._lastName = lastName;
        this._contact = contact;
        this._email = email;
        this._deviceBrand = deviceBrand;
        this._deviceModel = deviceModel;
        this._serialNumber = serialNumber;
        this._importantNotes = importantNotes;
        this._serviceProduct = serviceProduct;
        this._price = price;
        this._deposit = deposit;
        this._due = due;
        
        txt_panel_print.setText(txt_panel_print.getText() + "**********************************************\n");
        txt_panel_print.setText(txt_panel_print.getText() + "******************PCHOUSE*********************\n");
        txt_panel_print.setText(txt_panel_print.getText() + "\n");
        
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Order No: " + _orderNo +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Customer Name: " + _firstName + " " + _lastName + "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Contact No: " + _contact +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Email Address: " + _email +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Brand: " + _deviceBrand +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Model: " + _deviceModel +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "S/N: " + _serialNumber +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Important Notes: " + _importantNotes +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Service|Product: " + _serviceProduct +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Price: €" + _price +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Deposit Paid: €" + _deposit +  "\n");
        txt_panel_print.setText(txt_panel_print.getText() + " " + "Due: €" + _due +  "\n");
        
        txt_panel_print.setText(txt_panel_print.getText() + "**********************************************\n");
        txt_panel_print.setText(txt_panel_print.getText() + "**********************************************\n");
        txt_panel_print.setText(txt_panel_print.getText() + "********Thank you for trusting us*************\n");
        
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_panel_print.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jScrollPane1.setViewportView(txt_panel_print);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txt_panel_print;
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