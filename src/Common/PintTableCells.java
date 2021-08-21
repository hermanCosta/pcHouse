package Common;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Herman
 */
public class PintTableCells extends DefaultTableCellRenderer {

/**
 * 
 */
private static final long   serialVersionUID    = 1L;
public static final float R = 0.9f;
public static final float G = 0.5f; 
public static final float B = 0.8f;

@Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
 super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
    Color c = Color.WHITE;
    Object text = table.getValueAt(row, column);
    
    if (text != null && "Fixed".equals(text.toString())){
        // RGB
        c = new Color(204,255,204);
        //table.getValueAt(row, 7);
        
    }
    if(text != null && "Not Fixed".equals(text.toString())){
       // c = new Color(255,153,153);
       table.getValueAt(row, 7);
       table.setForeground(Color.red);
    }
    if(text != null && "In Progress".equals(text.toString())){
        //c = new Color(255,255,204);
        table.getValueAt(row, 7);
        table.setForeground(Color.MAGENTA);
    }
       setBackground(c);      

   if (isSelected){
          setBackground(table.getSelectionBackground());    
  }
    return this;
}
}