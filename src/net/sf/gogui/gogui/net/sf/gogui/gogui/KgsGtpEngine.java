package net.sf.gogui.gogui;



import org.igoweb.igoweb.client.gtp.*;
import com.gokgs.client.gtp.*;
import java.io.*;
import java.util.*;
import java.net.*;

 
/**
 * @author redcrow
 */
public class KgsGtpEngine{
	

    int a = 1;
    String command;
   
    int check = 0;
 	

    public KgsGtpEngine() {
	
		
		
  		//Thread thread = new EasyThread(a);      
		Thread thread2 = new KgsThread();
		thread2.start();
		//thread.start();
		

    }

    public String send(){

    	System.out.println("SEND : "+ command);
    	check = 0;
    	return command;
    }

    public int checkInt(){
    	return check;
    }


    public void go(){
	//obj.go();
    }

  }  
	
	






class KgsThread extends Thread {



	GtpClient obj;
	File file;
    FileWriter writer;




	public KgsThread(){
		//file = new File("/home/nonpawit/Downloads/testKgs");
		
	}

	public void run() {

		try{

			

		
			Properties props = new Properties();
			String logName = "Pachi";
			props.setProperty("engine", "pachi");
			props.setProperty("name", "Paaaagi");
			props.setProperty("password", "9z7zq4");
			props.setProperty("room", "Computer Go");
			props.setProperty("mode", "custom");
			props.setProperty("rules.boardSize", "9");
			props.setProperty("talk", "   TEST BOT");
			props.setProperty("reconnect", "true");
			props.setProperty("verbose", "t");
			Options options;


			
			Process process = Runtime.getRuntime().exec("/home/nonpawit/Documents/SocketStream/Patop.jar");
			
			
	
			OutputStream out = process.getOutputStream();
			InputStream in = process.getInputStream();
			InputStream err = process.getErrorStream();


			
			options = new Options(props,logName);
			obj  = new GtpClient(in,out,options);
			obj.go();

			

			
			
			
		}
		catch(IOException e){}



	}
}




