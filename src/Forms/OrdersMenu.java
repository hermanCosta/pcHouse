/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.awt.Color;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author user
 */
public class OrdersMenu extends javax.swing.JInternalFrame {

    /**
     * Creates new form OrdersMenu
     */
     Color defaultColor, mouseEnteredColor;
       
        
    public OrdersMenu() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        defaultColor = new Color(21,76,121);
        mouseEnteredColor = new Color(118,181,197);
    }
    
    public void openCreateOrder()
    {
        CreateOrder createOrder = new CreateOrder();
        createOrder.setVisible(true);
    }
    
    public void openNewOrder()
    {
        
        NewOrder newOrder = new NewOrder();
        panel_order_menu.removeAll();
        panel_order_menu.add(newOrder).setVisible(true);
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_order_menu = new javax.swing.JPanel();
        panel_new_order = new javax.swing.JPanel();
        label_new_order = new javax.swing.JLabel();
        panel_check_existing = new javax.swing.JPanel();
        label_check_existing = new javax.swing.JLabel();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(660, 400));

        panel_order_menu.setPreferredSize(new java.awt.Dimension(734, 609));

        panel_new_order.setBackground(new java.awt.Color(21, 76, 121));
        panel_new_order.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel_new_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_new_orderMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_new_orderMouseClicked(evt);
            }
        });

        label_new_order.setBackground(new java.awt.Color(255, 255, 255));
        label_new_order.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_new_order.setForeground(new java.awt.Color(255, 255, 255));
        label_new_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_new_order.png"))); // NOI18N
        label_new_order.setText(" New Order");
        label_new_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_new_orderMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_new_orderMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_new_orderMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_new_orderMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_new_orderLayout = new javax.swing.GroupLayout(panel_new_order);
        panel_new_order.setLayout(panel_new_orderLayout);
        panel_new_orderLayout.setHorizontalGroup(
            panel_new_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_new_orderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(label_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panel_new_orderLayout.setVerticalGroup(
            panel_new_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_new_orderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        panel_check_existing.setBackground(new java.awt.Color(21, 76, 121));
        panel_check_existing.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel_check_existing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_check_existingMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_check_existingMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_check_existingMouseExited(evt);
            }
        });

        label_check_existing.setBackground(new java.awt.Color(255, 255, 255));
        label_check_existing.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_check_existing.setForeground(new java.awt.Color(255, 255, 255));
        label_check_existing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_check_existing.png"))); // NOI18N
        label_check_existing.setText("Check Existing");
        label_check_existing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_check_existingMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                label_check_existingMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_check_existingMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_check_existingMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_check_existingMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panel_check_existingLayout = new javax.swing.GroupLayout(panel_check_existing);
        panel_check_existing.setLayout(panel_check_existingLayout);
        panel_check_existingLayout.setHorizontalGroup(
            panel_check_existingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_check_existingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_check_existing, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
        );
        panel_check_existingLayout.setVerticalGroup(
            panel_check_existingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_check_existingLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(label_check_existing, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_order_menuLayout = new javax.swing.GroupLayout(panel_order_menu);
        panel_order_menu.setLayout(panel_order_menuLayout);
        panel_order_menuLayout.setHorizontalGroup(
            panel_order_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_menuLayout.createSequentialGroup()
                .addComponent(panel_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_check_existing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 251, Short.MAX_VALUE))
        );
        panel_order_menuLayout.setVerticalGroup(
            panel_order_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_menuLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(panel_order_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_check_existing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_new_order, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(470, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_order_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void label_new_orderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMousePressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_label_new_orderMousePressed

    private void label_new_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMouseClicked
        // TODO add your handling code here:
        openNewOrder();
    }//GEN-LAST:event_label_new_orderMouseClicked

    private void panel_new_orderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_orderMousePressed
        // TODO add your handling code here:
        openNewOrder();
    }//GEN-LAST:event_panel_new_orderMousePressed

    private void panel_new_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_new_orderMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_panel_new_orderMouseClicked

    private void label_check_existingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_label_check_existingMousePressed

    private void label_check_existingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_label_check_existingMouseClicked

    private void panel_check_existingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_check_existingMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_check_existingMousePressed

    private void panel_check_existingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_check_existingMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_check_existingMouseClicked

    private void label_new_orderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMouseEntered
        // TODO add your handling code here:
        panel_new_order.setBackground(mouseEnteredColor);
    }//GEN-LAST:event_label_new_orderMouseEntered

    private void label_new_orderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_new_orderMouseExited
        // TODO add your handling code here:
        panel_new_order.setBackground(defaultColor);
    }//GEN-LAST:event_label_new_orderMouseExited

    private void label_check_existingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseEntered
        // TODO add your handling code here:
        panel_check_existing.setBackground(mouseEnteredColor);
    }//GEN-LAST:event_label_check_existingMouseEntered

    private void label_check_existingMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_label_check_existingMouseReleased

    private void panel_check_existingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_check_existingMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_check_existingMouseExited

    private void label_check_existingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_check_existingMouseExited
        // TODO add your handling code here:
        panel_check_existing.setBackground(defaultColor);
    }//GEN-LAST:event_label_check_existingMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label_check_existing;
    private javax.swing.JLabel label_new_order;
    private javax.swing.JPanel panel_check_existing;
    private javax.swing.JPanel panel_new_order;
    private javax.swing.JPanel panel_order_menu;
    // End of variables declaration//GEN-END:variables
}
