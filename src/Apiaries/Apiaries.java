
package Apiaries;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author boukbab
 */
public class Apiaries {
    private int Id;
    private String Apia_ref;
    private String Apia_date;
    private String Apia_time;
    private String[] columnsName;
    private Object[] tableLines;
    public Apiaries(){
     
    }
    
    public void Fill_Tale(String path){
        File file = new File(path);
        if(file.length()!=0){
            try {
                 BufferedReader br = new BufferedReader(new FileReader(file));
                 String firstLine = br.readLine().trim();
                 columnsName = firstLine.split(",");
                 tableLines = br.lines().toArray();          
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
        columnsName= new String[0];
        tableLines= new String[0];
        }
    }
    
    public String [] getTableCulumns()
    {return columnsName;}
    public Object [] getTableRows()
    {return tableLines;}
    public String[] dir(String path){
        File file = new File(path);
        String[] directories;
        directories = file.list((File current, String name) -> new File(current, name).isDirectory());
        return directories;
    }
    
    public String Remove_apiary(File file){
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    Remove_apiary(f);
                }
            }
        }
        file.delete();   
        return "apiary removed successfuly";
    }
    public String Edit_apiary(String oldloc,String location ,String oldname,String api_name,String nb_fame){
        File dir = new File(Constants.ABSULUTE_PATH+oldloc);
        File newDir = new File(dir.getParent() + "/" + location);
        dir.renameTo(newDir);
        dir = new File(Constants.ABSULUTE_PATH+"/" + location+"/"+oldname);
        newDir = new File(dir.getParent() +"/" + api_name);
        dir.renameTo(newDir);
        
        return "All data updated successfully";
    }
    public String Add_apiary(String Date, String time,String location,String reference,String nbFrmes) throws IOException{
        File file = new File(Constants.ABSULUTE_PATH+"/"+location);
        String msg="";
          if (file.exists() && file.isDirectory()){
            msg= "location already exist";
          }else{
              boolean bool = file.mkdir();   
               if(bool)msg= "Apiary created successfully";
           //  System.out.println("Sorry couldn’t create specified directory");
          }
        
        String [] tm=time.split(":");
        file= new File(Constants.ABSULUTE_PATH+"/"+location+"/"+reference+"_"+Date+"_"+tm[0]+"_"+tm[1]);
        boolean bool = file.mkdir();        
        File newFile = new File(Constants.ABSULUTE_PATH+"/"+location+"/"+reference+"_"+Date+"_"+tm[0]+"_"+tm[1]+"/"+"TTdata.csv");
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        newFile = new File(Constants.ABSULUTE_PATH+"/"+location+"/"+reference+"_"+Date+"_"+tm[0]+"_"+tm[1]+"/"+"Wdata.csv");
        writer = new BufferedWriter(new FileWriter(newFile));
         if(bool){msg= "Apiary created successfully";}
    
    return msg;
    }
    
    private DefaultCategoryDataset createDataset(String series,int column_data) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
     for (Object tableLine : tableLines) {
                        String line = tableLine.toString().trim();
                        String[] dataRow = line.split(","); 
                       if(dataRow.length>3) dataset.addValue( Integer.parseInt(dataRow[column_data]) , series ,  dataRow[1] );
                               }

      return dataset;
   }
    
    public ChartPanel chartpanel(String loc,String apia){
     Fill_Tale(Constants.ABSULUTE_PATH+"/"+loc+"/"+apia+"/TTdata.csv");
     CategoryPlot plot = new CategoryPlot();
      LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
      lineRenderer.setStroke(new BasicStroke(2.0f));
     // lineRenderer.setPaint(  );
      plot.setDataset(0, createDataset("temp °C",2));
      plot.setRenderer(0, lineRenderer);

      // Add the second dataset and render as lines
       BarRenderer baRenderer = new BarRenderer();
      Color color = new Color(36, 143, 36);
       baRenderer.setSeriesFillPaint(0,  color);
	
        baRenderer.setMaximumBarWidth(0.01);
      plot.setDataset(1, createDataset("traffic",3));
      plot.setRenderer(1, baRenderer);

      // Set Axis
      plot.setDomainAxis(new CategoryAxis("Time"));
      plot.setRangeAxis(new NumberAxis("temp °C / traffic"));

      JFreeChart chart = new JFreeChart(plot);
      chart.setTitle("Temperature | Traffic in the apiary");
     
      ChartPanel panel = new ChartPanel(chart);
     
     
     
     
    return  panel;
    }
    public int getId(){return Id;}
    public String getRef(){return Apia_ref;}
    public String getDate(){return Apia_date;}
    public String getTime(){return Apia_time;}
    
}
