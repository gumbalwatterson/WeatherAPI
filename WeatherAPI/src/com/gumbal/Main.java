package com.gumbal;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.example.Example;
import com.google.gson.Gson;

public class Main {

	private JFrame frame;
	private List <String>days= new ArrayList<>();
	private List <String> listOfIcons = new ArrayList<>();
	private JButton bt1;
	private JButton bt2;
	private JButton bt3;
	private JButton bt4;
	private JButton bt5;
	private JButton bt6;
	private JButton bt7;
	private JLabel labelTitle;
	private JLabel labelTitle2;
	private JLabel labelTitle3;
	private JLabel labelTitle4;
	private JLabel labelTitle5;
	private JLabel labelTitle6;
	private JLabel labelTitle7;
	private JLabel info;
	private JLabel info2;
	private JLabel info3;
	private JLabel info4;
	private JLabel info5;
	private JLabel info6;
	private JLabel info7;
	private HashMap<String, Component> components  =  new HashMap<>();
	private HashMap<String ,String> mapOfWeatherInfo = new HashMap<>();
	boolean changeaction;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/*
	 * calling and getting data from end point API services
	 */
	
	private void apiData() throws IOException {
		
		

        String cityname = JOptionPane.showInputDialog(null, "enter city name");
  

        URL latlongapi = new URL("http://open.mapquestapi.com/geocoding/v1/address?key=*************&location="+cityname);

	      URLConnection c = latlongapi.openConnection();
	      BufferedReader reader = new BufferedReader(
	        new InputStreamReader(c.getInputStream())
	      );

	      String line;
	      String json="";
	      while ((line = reader.readLine()) != null)
	      {
	    	  json+= line;
	
	      }
	      reader.close();
	      
	      System.out.println(json);
	      Gson gson2 = new Gson();
	      
	      //below we are getting latitude and longitude values
	      
	     com.latlog.Example ex =gson2.fromJson(json, com.latlog.Example.class);
	     String latitude = String.valueOf(ex.getResults().get(0).getLocations().get(0).getDisplayLatLng().getLat());
	     String longitude  = String.valueOf(ex.getResults().get(0).getLocations().get(0).getDisplayLatLng().getLng());
		     
	     System.out.println("latitude and longitude values are: " + latitude + " "  + longitude);

	     
	     // below we are making new api call for weather data using latitude and longitude values
	     
  URL weatherApi = new URL
("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&units=metric&cnt=7&appid=**************");

  StringBuilder result = new StringBuilder();
  URLConnection con = weatherApi.openConnection();
  BufferedReader reader2 = new BufferedReader(
    new InputStreamReader(con.getInputStream())
  );

  String line2="";
  while ((line2 = reader2.readLine()) != null)
  {
      result.append(line2);
  }
  
  System.out.println(result);
  reader2.close();


Gson gson = new Gson();

Example example =gson.fromJson(result.toString(), Example.class);

String weatherInfo=""; // string used to gather all weather informations for a day


// below we are looping through the  7 days

for(int i =0 ; i<example.getDaily().size()-1; i++) {

long l = ((long)example.getDaily().get(i).getDt())*1000;
Date date =new Date(l);

// current date( day of month , month , year)

String currentDate =  new SimpleDateFormat("dd.MM.yyyy", new Locale("en")).format(date);
String dayOfWeek =  new SimpleDateFormat("EEEE", new Locale("en")).format(date);

((JLabel)components.get("labelTitle"+i)).setText(dayOfWeek);


// weather conditions
  
 weatherInfo += "<html>" +example.getDaily().get(i).getWeather().get(0).getDescription();	


 // weather icon
//http://openweathermap.org/img/wn/10d@2x.png
  

System.out.println("icon for " +i +" " +example.getDaily().get(i).getWeather().get(0).getIcon());

String iconWeather = example.getDaily().get(i).getWeather().get(0).getIcon();

((JButton) components.get("bt"+i)).setIcon(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/"+iconWeather+".png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));




 //  Atmospheric pressure 

weatherInfo += ("<br/>Preassure: " +example.getDaily().get(i).getPressure() +" hPa");


 //  humidity 
 
 
weatherInfo += ("<br/>Humidity: " + example.getDaily().get(i).getHumidity() +" %</html>?" );


 //  temperature in morning , day evening and night 
 

 
weatherInfo +="<html><br/>morning temperature: <br/>" + example.getDaily().get(i).getTemp().getMorn() +" Celsius";

weatherInfo +="<br/>day temperature: <br/>" + example.getDaily().get(i).getTemp().getDay() +" Celsius";

weatherInfo +="<br/>evening temperature: <br/>" + example.getDaily().get(i).getTemp().getEve() +" Celsius";

weatherInfo +="<br/>night temperature: <br/>" + example.getDaily().get(i).getTemp().getNight() +" Celsius</html>";

mapOfWeatherInfo.put("info"+i, weatherInfo);

// first display the first part of the weather info string
((JLabel)components.get("info"+i)).setText(weatherInfo.split("\\?")[0]);

weatherInfo ="";
}

		
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws MalformedURLException 
	 */
	private void initialize() {
		
		try {
			
		frame = new JFrame();
		frame.setBounds(100, 100, 1010, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		InputStream inputstream = Main.class.getResourceAsStream("/background5.jpg"); 
		BufferedImage bufimage =null;	 
		try {
			bufimage =ImageIO.read(inputstream);
		  
		 
		}catch(Exception e) {
			 e.printStackTrace();
		 }
	
		ImagePanel panel = new ImagePanel(bufimage);
	
		
		 frame.getContentPane().add(panel);

		 	bt1 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/01d.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt1.setBorder(BorderFactory.createEmptyBorder());
		    bt1.setContentAreaFilled(false); 
		    bt1.setBounds(10, 11, 128, 128);
		    bt1.setSelected(false);
		 
		   
		    components.put("bt0", bt1);
		    
		    panel.setOpaque(false);
		    panel.add(bt1);
		   
		
		labelTitle = new JLabel("New");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle.setBounds(10, 150, 128, 37);
		components.put("labelTitle0", labelTitle);
	    
		panel.add(labelTitle);
		
		info = new JLabel("new label");
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setVerticalAlignment(SwingConstants.TOP);
		info.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info.setBounds(10, 198, 128, 143);
		components.put("info0", info);
		
		panel.add(info);
	
		
			bt2 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/01d.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt2.setBorder(BorderFactory.createEmptyBorder());
		    bt2.setContentAreaFilled(false);
		    
		    bt2.setBounds(148, 11, 128, 128);
		  
		    components.put("bt1", bt2);
		    
		    panel.add(bt2);
		    
		
		    bt3 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/10d@2x.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt3.setBorder(BorderFactory.createEmptyBorder());
		    bt3.setContentAreaFilled(false);
		    
		    bt3.setBounds(286, 11, 128, 128);
		 
		    components.put("bt2", bt3);
		    
		    panel.add(bt3);
		    
		
		    bt4 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/10d@2x.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt4.setBorder(BorderFactory.createEmptyBorder());
		    bt4.setContentAreaFilled(false);
		    
		    bt4.setBounds(424, 11, 128, 128);
		
		    components.put("bt3", bt4);
		    
		    panel.add(bt4);		    
		    
		    
		    bt5 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/10d@2x.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt5.setBorder(BorderFactory.createEmptyBorder());
		    bt5.setContentAreaFilled(false);
		    
		    bt5.setBounds(562, 11, 128, 128);
		    
		    components.put("bt4", bt5);
		    
		    panel.add(bt5);
		    
		
		    bt6 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/10d@2x.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt6.setBorder(BorderFactory.createEmptyBorder());
		    bt6.setContentAreaFilled(false);
		    
		    bt6.setBounds(700, 11, 128, 128);
		   
		    components.put("bt5", bt6);
		    
		    panel.add(bt6);
		    
		    
		    bt7 = new JButton(new ImageIcon(((new ImageIcon(
		        new URL("http://openweathermap.org/img/wn/10d@2x.png"))
		        .getImage()
		        .getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)))));
		    bt7.setBorder(BorderFactory.createEmptyBorder());
		    bt7.setContentAreaFilled(false);
		 
		    bt7.setBounds(838, 11, 128, 128);
		   
		    components.put("bt6", bt7);
	    
		    panel.add(bt7);
		
		
		labelTitle2 = new JLabel("New");
		labelTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle2.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle2.setBounds(148, 150, 128, 37);
		components.put("labelTitle1", labelTitle2);
	    
		panel.add(labelTitle2);
	    
		
		labelTitle3 = new JLabel("New");
		labelTitle3.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle3.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle3.setBounds(286, 150, 128, 37);
		components.put("labelTitle2", labelTitle3);
	    
		panel.add(labelTitle3);
		
		labelTitle4 = new JLabel("New");
		labelTitle4.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle4.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle4.setBounds(424, 150, 128, 37);
		components.put("labelTitle3", labelTitle4);
	    
		panel.add(labelTitle4);
		
		labelTitle5 = new JLabel("New");
		labelTitle5.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle5.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle5.setBounds(562, 150, 128, 37);
		components.put("labelTitle4", labelTitle5);
	    
		panel.add(labelTitle5);
		
		labelTitle6  = new JLabel("New");
		labelTitle6.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle6.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle6.setBounds(700, 150, 128, 37);
		components.put("labelTitle5", labelTitle6);
	    
		panel.add(labelTitle6);
		
		labelTitle7 = new JLabel("New");
		labelTitle7.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle7.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitle7.setBounds(838, 150, 128, 37);
		components.put("labelTitle6", labelTitle7);
	    
		panel.add(labelTitle7);
		
		info2 = new JLabel("New label");
		info2.setVerticalAlignment(SwingConstants.TOP);
		info2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info2.setHorizontalAlignment(SwingConstants.CENTER);
		info2.setBounds(148, 198, 128, 143);
		components.put("info1", info2);
	    
		panel.add(info2);
		
		info3 = new JLabel("New label");
		info3.setVerticalAlignment(SwingConstants.TOP);
		info3.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info3.setHorizontalAlignment(SwingConstants.CENTER);
		info3.setBounds(286, 198, 128, 143);
		components.put("info2", info3);
	    
		panel.add(info3);
		
		info4 = new JLabel("New label");
		info4.setVerticalAlignment(SwingConstants.TOP);
		info4.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info4.setHorizontalAlignment(SwingConstants.CENTER);
		info4.setBounds(424, 198, 128, 143);
		components.put("info3", info4);
	    
		panel.add(info4);
		
		info5 = new JLabel("New label");
		info5.setVerticalAlignment(SwingConstants.TOP);
		info5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info5.setHorizontalAlignment(SwingConstants.CENTER);
		info5.setBounds(562, 198, 128, 143);
		components.put("info4", info5);
	    
		panel.add(info5);
		
		info6 = new JLabel("New label");
		info6.setVerticalAlignment(SwingConstants.TOP);
		info6.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info6.setHorizontalAlignment(SwingConstants.CENTER);
		info6.setBounds(700, 198, 128, 143);
		components.put("info5", info6);
	    
		panel.add(info6);
		
		info7 = new JLabel("New label");
		info7.setVerticalAlignment(SwingConstants.TOP);
		info7.setFont(new Font("Times New Roman", Font.BOLD, 12));
		info7.setHorizontalAlignment(SwingConstants.CENTER);
		info7.setBounds(838, 198, 128, 143);
		components.put("info6", info7);
	    
		panel.add(info7);
		
	
		
		apiData();
		
		System.out.println("before");
	Timer timer = new Timer(5000, new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	              
	            	if(changeaction) {
	                    updateInfo("second");
	                    changeaction = false;
	                } else {
	                    
	                	updateInfo("first");
	                    
	                	changeaction = true;
	                }
	            
	            
	            }
	        });
	        timer.start();
	    
	        System.out.println("after");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	private void updateInfo(String partOfString) {
		
		if(partOfString.equalsIgnoreCase("first")) {
		
			for(int i =0 ; i<mapOfWeatherInfo.size(); i++) {
				
	((JLabel)components.get("info"+i)).setText(mapOfWeatherInfo.get("info"+i).split("\\?")[0]);
		
			}
		
			}else {
	
				for(int i =0 ; i<mapOfWeatherInfo.size(); i++) {
					
	((JLabel)components.get("info"+i)).setText(mapOfWeatherInfo.get("info"+i).split("\\?")[1]);
						
				}
		
		}
		
	} // end of updateInfo method
	
}


class ImagePanel extends JPanel {

	  private Image img;
	  
	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }
  
	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
		
	  }

	}
