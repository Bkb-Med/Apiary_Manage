package Apiaries;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * @author boukbab Med
 * 
 */
public class tableRender implements TableCellRenderer {
        public String type_C;
        private double margin ;
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
        @SuppressWarnings("null")
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
			Object cellvalue;
                        cellvalue = table.getModel().getValueAt(row,column);
                        double rowvalue=0;
                        try{
                            if(!cellvalue.equals("no data")&& cellvalue!=null ){
                                rowvalue = Double.parseDouble(cellvalue.toString());
                            }else {

                                // c.setBackground(Color.WHITE);
                                return c;}}catch(NullPointerException e){}
                        
			Color color=null;
                        Color fcolor=null;
			if (rowvalue > margin) {
				color = type();
                                fcolor=Color.white;
			} else if(rowvalue<0)
                        {
				color=new Color(30,144,255);
                                fcolor=Color.white;
			}else{
                             color =Color.WHITE;
                             fcolor=Color.black;
                        }
			c.setBackground(color);
                        c.setForeground(fcolor);
		return c;
	}
        public Color type(){
            Color Ctype=null;
            if(type_C.equals(Constants.typecolumn_temp)){
                margin=Constants.marg_temp;
                Ctype=new Color(255,69,0);
               
            }else if(type_C.equals(Constants.typecolumn_traffic)){
                margin=Constants.marg_traffic;
                 Ctype=new Color(0, 128, 0);
            
            }
            return Ctype;
        }
}
       


