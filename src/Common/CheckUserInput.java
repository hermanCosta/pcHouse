/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author user
 */
public class CheckUserInput {
    
    public void avoidEmptyField(JTextField text)
    {
        text.setInputVerifier(new InputVerifier() {
           
           Border originalBorder;
           
           @Override
           public boolean verify(JComponent input) {
               JTextField comp = (JTextField) input;
               return !comp.getText().trim().isEmpty();
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
}
