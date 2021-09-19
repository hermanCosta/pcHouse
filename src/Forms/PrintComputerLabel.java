/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import InternalForms.ComputerList;
import Model.Computer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HermanCosta
 */
public class PrintComputerLabel extends javax.swing.JFrame {

    Computer computer;
    boolean isSaleDetails;
    
    public PrintComputerLabel() {
        initComponents();
    }

    public PrintComputerLabel(Computer _computer) {
        initComponents();
        setResizable(false);  
        this.computer = _computer;
        
        loadOrderToPrint();
        print();
    }
    
    public void loadOrderToPrint()
    {
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Computer ID: " + computer.getComputerId() + "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Band: " + computer.getBrand()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Model: " + computer.getModel()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " S/N: " + computer.getSerialNumber()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Processor: " + computer.getProcessor()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " RAM Memory: " + computer.getRam()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Storage: " + computer.getStorage()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Graphic (GPU): " + computer.getGpu()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Screen: " + computer.getScreen()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Price €: " + computer.getPrice()+ "\n");
        txt_pane_print_computer.setText(txt_pane_print_computer.getText() + " Notes: " + computer.getNotes()+ "\n");
    }
    
    public void backToPreviousFrame()
    {
        if (isSaleDetails)
        {
            ComputerList computerList = new ComputerList();
            MainMenu.mainMenuDesktopPane.removeAll();
            MainMenu.mainMenuDesktopPane.add(computerList).setVisible(true);
        }
    }
    
    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING) {
            backToPreviousFrame();
        }
    }

    public void print()
    {
     PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Computer" + computer.getComputerId());
        PageFormat format = printerJob.getPageFormat(null);
        
        printerJob.setPrintable(new Printable() {
            
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                
                if(pageIndex > 0)
                {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D)graphics;
                panel_print_order.paint(graphics2D);
                
              return Printable.PAGE_EXISTS;
            }
        }, format);
        
        boolean returningResult = printerJob.printDialog();
        
        if(returningResult)
        {
            try {
                
                printerJob.print();
                
                JOptionPane.showMessageDialog(this, "Computer " + computer.getComputerId() + " Printed Successfully");
                
                this.dispose();
                backToPreviousFrame();
                
            } catch (PrinterException ex) {
                Logger.getLogger(PrintOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_print_order = new javax.swing.JPanel();
        panel_header = new javax.swing.JPanel();
        lbl_logo_icon1 = new javax.swing.JLabel();
        lbl_land_line_number1 = new javax.swing.JLabel();
        lbl_mobile_number1 = new javax.swing.JLabel();
        line_header = new javax.swing.JSeparator();
        lbl_address1 = new javax.swing.JLabel();
        panel_products = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txt_pane_print_computer = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setPreferredSize(new java.awt.Dimension(595, 842));

        panel_header.setBackground(new java.awt.Color(255, 255, 255));

        lbl_logo_icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_header.png"))); // NOI18N

        lbl_land_line_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_land_line_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_phone_number.png"))); // NOI18N
        lbl_land_line_number1.setText("+353 (01) 563-9520");

        lbl_mobile_number1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_mobile_number1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_mobile_number.png"))); // NOI18N
        lbl_mobile_number1.setText("+353 (83) 012-8190");

        lbl_address1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        lbl_address1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_address.png"))); // NOI18N
        lbl_address1.setText("12A, Frederick Street North, Dublin 1");

        javax.swing.GroupLayout panel_headerLayout = new javax.swing.GroupLayout(panel_header);
        panel_header.setLayout(panel_headerLayout);
        panel_headerLayout.setHorizontalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_headerLayout.createSequentialGroup()
                        .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_mobile_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_land_line_number1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_address1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        panel_headerLayout.setVerticalGroup(
            panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_headerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo_icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_headerLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lbl_land_line_number1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_mobile_number1)
                        .addGap(0, 0, 0)
                        .addComponent(lbl_address1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel_products.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane8.setBorder(null);
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setToolTipText("");
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.setDoubleBuffered(true);
        jScrollPane8.setEnabled(false);

        txt_pane_print_computer.setEditable(false);
        txt_pane_print_computer.setBorder(null);
        txt_pane_print_computer.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        txt_pane_print_computer.setFocusable(false);
        txt_pane_print_computer.setOpaque(false);
        jScrollPane8.setViewportView(txt_pane_print_computer);

        javax.swing.GroupLayout panel_productsLayout = new javax.swing.GroupLayout(panel_products);
        panel_products.setLayout(panel_productsLayout);
        panel_productsLayout.setHorizontalGroup(
            panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_productsLayout.setVerticalGroup(
            panel_productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_productsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("Computer Specs");

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGroup(panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_print_orderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_print_orderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panel_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addContainerGap(236, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(237, Short.MAX_VALUE))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(panel_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_address1;
    private javax.swing.JLabel lbl_land_line_number1;
    private javax.swing.JLabel lbl_logo_icon1;
    private javax.swing.JLabel lbl_mobile_number1;
    private javax.swing.JSeparator line_header;
    private javax.swing.JPanel panel_header;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JPanel panel_products;
    private javax.swing.JTextPane txt_pane_print_computer;
    // End of variables declaration//GEN-END:variables
}
