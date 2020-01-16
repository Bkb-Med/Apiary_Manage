package Apiaries;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;


public class progCell  extends JProgressBar implements TableCellRenderer {


    public progCell(int min, int max) {
        super(min, max);
        this.setStringPainted(true);
        this.setBackground(Color.WHITE);
        this.setForeground(new Color(0,128,0));
        
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        try{
        if(!value.equals("no data")&& value!=null){
        this.setValue( Integer.parseInt(value.toString()) );
        UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
        UIManager.put("ProgressBar.selectionBackground", Color.black);
        return this;}
        else {return null;}}catch(NullPointerException e){}
        return null;
    }


        
    
}


