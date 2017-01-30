package net.sf.gogui.gogui;



import java.net.*; 
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A simple Swing-based client for the capitalization server.
 * It has a main frame window with a text field for entering
 * strings and a textarea to see the results of capitalizing
 * them.
 */
public class IgsServer {

    //BufferedReader in;
    PrintWriter out;
    Socket socket;
    String test;
    DataInputStream c;
    String inputString;
    SendInput sendInput;
    ReciveInput reciveInput;
    Scanner in;
    
    int testloop;

    Socket me;
    PrintWriter toGogui;
    BufferedReader toIgs;
    String room;

    
    private String host;
    private String port;
    private static String goColor;
    private static String user;
    private static String password;
    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public IgsServer() throws UnknownHostException, IOException{


        goColor ="b";
        testloop = 1;
        room = "room igs server. ";



        socket = new Socket("igs.joyjoy.net", 6969);
        //socket = new Socket("127.0.0.1", 9898);
        out = new PrintWriter(socket.getOutputStream(), true);
        c = new DataInputStream(socket.getInputStream());
        sendInput = new SendInput();
        reciveInput = new ReciveInput();


        me = new Socket("127.0.0.1", 9898);
        toIgs = new BufferedReader(
                new InputStreamReader(me.getInputStream()));
        toGogui = new PrintWriter(me.getOutputStream(), true);


        
        // Layout GUI
        
    /**
     * Implements the connection logic by prompting the end user for
     * the server's IP address, connecting, setting up streams, and
     * consuming the welcome messages from the server.  The Capitalizer
     * protocol says that the server sends three lines of text to the
     * client immediately after establishing a connection.
     */
    }
    public void connectToServer() throws IOException { 
        reciveInput.start(); 
        sendInput.start();       
    }
    public void setHost(String host){
        this.host = host;
    }
    public String getHost(){
        return host;
    }

    public void setPort(String port){
        this.port = port;
    }
    public String getPort(){
        return port;
    }

    public void setUser(String user){

        this.user = user;
    }
    public String getUser(){

        return user;
    }

    public void setPassword(String password){

        this.password = password; 

    }
    public String getPassword(){

        return password;
    }


    public static String getGoColor(){
        return goColor;
    }



    public void move(String move){

        out.println(move);

    }




    class SendInput extends Thread{


        public SendInput(){
            
            in = new Scanner(System.in);
        }

        public void run(){

            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            try{
                System.out.println("CONNECTING");
                IgsServer aaa = new IgsServer();
                out.println(aaa.getUser());
                out.println(aaa.getPassword());
            }
            catch(Exception e){
                e.printStackTrace();
            }


            while(true){
                inputString = in.nextLine();
                out.println(inputString);

            }

        }
    }

    class ReciveInput extends Thread{


        public ReciveInput(){

        }

        public void run() {

            try{

                while(true){
                
                    read();

                }
            }
            catch(IOException e){}
            //catch(InterruptedException e){}

        }

        public void read() throws IOException{

            test = c.readLine();
            checkCommand(test);
            System.out.println(test);


            if(test.length() > 0){
                int index = test.length()-(test.length()-1);
                if(test.substring(0,index).contains("7")){
                    room = room+"<br>"+test;
                }
            }



            //if(test.contains("Match[19x19] in 10 minutes requested with top99963 as")){
                //out.println("match top99963 W 19 10 10");
            //}

        }

        public void toJoin() throws InterruptedException{

            sendInput.join();
        }

        


        public void checkCommand(String input){

            if(input.contains("Match[19x19] in 10 minutes requested with top99963 as Black.")){
                out.println("match top99963 W 19 10 10");
                goColor = "w";
            }
            else if(input.contains("Match[19x19] in 10 minutes requested with top99963 as White.")){
                out.println("match top99963 B 19 10 10");
                goColor = "b";
            }
            else if(input.contains("(B): ")&&goColor=="w"){
                String result = input.substring(11,input.length());
                toGogui.println("play w "+result);

            }
            else if(input.contains("(W): ")&&goColor=="b"){
                String result = input.substring(11,input.length());
                toGogui.println("play b "+result);
            }

            else if(input.contains("addresschange")){
                
                toGogui.println("this is a room");
                toGogui.println(room);

            }

            else if(input.contains("Please use say to talk to your opponent")){

                toGogui.println("let's play naja");
            }

            else if(input.contains("has resigned the game")){

                toGogui.println("resigned the game");
            }
            else if(input.contains("Welcome to IGS at igs.joyjoy.net 6969")){
                toGogui.println("IGS Server START");
            }
             
            else{
                //toGogui.println(test);
            }
        }
    }


}
