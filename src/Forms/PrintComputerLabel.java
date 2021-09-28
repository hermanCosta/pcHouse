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
    }
    
    public void loadOrderToPrint()
    {
        
        txt_brand_model_screen.setText(computer.getBrand() + " " + computer.getModel() + " - " + computer.getScreen());
        editor_pane_computer.setText("\n" + editor_pane_computer.getText() + "Processor:\t\t" + computer.getProcessor() + "\n");
        editor_pane_computer.setText(editor_pane_computer.getText() + "Graphics: \t\t" + computer.getGpu() + "\n");
        editor_pane_computer.setText(editor_pane_computer.getText() + "Memory:\t\t" + computer.getRam() + "\n");
        editor_pane_computer.setText(editor_pane_computer.getText() + "Storage:\t\t" + computer.getStorage() + "\n");
        int price = (int) computer.getPrice();
        txt_price.setText(price + " euro");
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
                panel_computer_specs.paint(graphics2D);
                
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
        panel_computer_specs = new javax.swing.JPanel();
        lbl_icon = new javax.swing.JLabel();
        separator_header = new javax.swing.JSeparator();
        separator_footer = new javax.swing.JSeparator();
        lbl_warranty = new javax.swing.JLabel();
        scroll_editorpane_computer = new javax.swing.JScrollPane();
        editor_pane_computer = new javax.swing.JEditorPane();
        txt_brand_model_screen = new javax.swing.JTextField();
        txt_price = new javax.swing.JTextField();
        lbl_computer_specs_print_view = new javax.swing.JPanel();
        lbl_order_print_view1 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_print_order.setBackground(new java.awt.Color(255, 255, 255));
        panel_print_order.setMaximumSize(new java.awt.Dimension(590, 500));
        panel_print_order.setPreferredSize(new java.awt.Dimension(590, 500));

        panel_computer_specs.setBackground(new java.awt.Color(255, 255, 255));
        panel_computer_specs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_computer_specs.setMaximumSize(new java.awt.Dimension(555, 200));

        lbl_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_slogan_small.png"))); // NOI18N

        lbl_warranty.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        lbl_warranty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_warranty.setText("6 Months Warranty");

        scroll_editorpane_computer.setBorder(null);
        scroll_editorpane_computer.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_editorpane_computer.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        editor_pane_computer.setBorder(null);
        editor_pane_computer.setSize(new java.awt.Dimension(112, 30));
        scroll_editorpane_computer.setViewportView(editor_pane_computer);

        txt_brand_model_screen.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_brand_model_screen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_brand_model_screen.setText("brandModelScreen");
        txt_brand_model_screen.setBorder(null);

        txt_price.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        txt_price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_price.setText("price");
        txt_price.setBorder(null);

        javax.swing.GroupLayout panel_computer_specsLayout = new javax.swing.GroupLayout(panel_computer_specs);
        panel_computer_specs.setLayout(panel_computer_specsLayout);
        panel_computer_specsLayout.setHorizontalGroup(
            panel_computer_specsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_computer_specsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_computer_specsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_computer_specsLayout.createSequentialGroup()
                        .addComponent(lbl_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_brand_model_screen, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(10, Short.MAX_VALUE))
                    .addGroup(panel_computer_specsLayout.createSequentialGroup()
                        .addGroup(panel_computer_specsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scroll_editorpane_computer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                            .addComponent(separator_header, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_computer_specsLayout.createSequentialGroup()
                        .addGroup(panel_computer_specsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_warranty, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                            .addComponent(txt_price, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separator_footer, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_computer_specsLayout.setVerticalGroup(
            panel_computer_specsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_computer_specsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_computer_specsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_icon)
                    .addComponent(txt_brand_model_screen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator_header, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scroll_editorpane_computer, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(separator_footer, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_warranty)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_print_orderLayout = new javax.swing.GroupLayout(panel_print_order);
        panel_print_order.setLayout(panel_print_orderLayout);
        panel_print_orderLayout.setHorizontalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_print_orderLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(panel_computer_specs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_print_orderLayout.setVerticalGroup(
            panel_print_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_print_orderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panel_computer_specs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        lbl_computer_specs_print_view.setBackground(new java.awt.Color(204, 204, 204));

        lbl_order_print_view1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbl_order_print_view1.setText("Computer Specs Print View");

        javax.swing.GroupLayout lbl_computer_specs_print_viewLayout = new javax.swing.GroupLayout(lbl_computer_specs_print_view);
        lbl_computer_specs_print_view.setLayout(lbl_computer_specs_print_viewLayout);
        lbl_computer_specs_print_viewLayout.setHorizontalGroup(
            lbl_computer_specs_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lbl_computer_specs_print_viewLayout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(lbl_order_print_view1)
                .addGap(73, 73, 73))
        );
        lbl_computer_specs_print_viewLayout.setVerticalGroup(
            lbl_computer_specs_print_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbl_computer_specs_print_viewLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(lbl_order_print_view1)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btn_print.setBackground(new java.awt.Color(21, 76, 121));
        btn_print.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_print.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_print_order, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_computer_specs_print_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_computer_specs_print_view, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_print_order, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_btn_printActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JEditorPane editor_pane_computer;
    private javax.swing.JPanel lbl_computer_specs_print_view;
    private javax.swing.JLabel lbl_icon;
    private javax.swing.JLabel lbl_order_print_view1;
    private javax.swing.JLabel lbl_warranty;
    private javax.swing.JPanel panel_computer_specs;
    private javax.swing.JPanel panel_print_order;
    private javax.swing.JScrollPane scroll_editorpane_computer;
    private javax.swing.JSeparator separator_footer;
    private javax.swing.JSeparator separator_header;
    private javax.swing.JTextField txt_brand_model_screen;
    private javax.swing.JTextField txt_price;
    // End of variables declaration//GEN-END:variables
}
