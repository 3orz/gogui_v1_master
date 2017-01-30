import java.io.*;
import java.util.*;
import java.net.*;

public class ReadWrite{

	//Version 3.0 KgsGtp vs Engine and Send to GUI by ReadWrite 
    public static void main(String argsp[]){
    	//BufferedWriter gui_writeToGui = null;
    	Recive send = null;


		try {
			String commnadOfEngine, commnadOfKgsGtp;

			//pachi debuglevel=0, random engine (for fast move)
			Process process = Runtime.getRuntime().exec(new String[]{"/home/parnmet/Desktop/pachi-master/pachi","-d","0"});
			
			//read command from engine
			BufferedReader e_readFromEngine = new BufferedReader(new InputStreamReader(process.getInputStream()));
			//write command to engine 
			PrintWriter e_writeToEngine = new PrintWriter(process.getOutputStream());	
			//read command from KgsGtp
			Scanner sc = new Scanner(System.in);
			//send result to KgsGtp
			PrintWriter kgs_writeToKgs = new PrintWriter(System.out);

			//Print output to GoGui(FILE)		
			//gui_writeToGui = new BufferedWriter(new FileWriter("/home/parnmet/Desktop/ReadWrite/writeToGui.txt"));

			//Print output to GoGui
			send = new Recive();
		
			for(;;){
				//kgs to Engine
				while (true){
					commnadOfKgsGtp = sc.nextLine();
					if("list_commands".equals(commnadOfKgsGtp)){
						//gui_writeToGui.write(commnadOfKgsGtp+"\n");
						//gui_writeToGui.flush();
						send.connectToServer(commnadOfKgsGtp+"\n");
						e_writeToEngine.write("name");
						e_writeToEngine.write("\n");
						e_writeToEngine.flush();
						break;
					}
					//gui_writeToGui.write(commnadOfKgsGtp+"\n");
					//gui_writeToGui.flush();
					send.connectToServer(commnadOfKgsGtp);

					e_writeToEngine.write(commnadOfKgsGtp+"\n");
					e_writeToEngine.flush();

					if("quit".equals(commnadOfKgsGtp)) System.exit(0);
					break;
				}

				//Engine to kgs
				while(true){
					commnadOfEngine = e_readFromEngine.readLine();

					//gui_writeToGui.write(commnadOfEngine+"\n\n");
					//gui_writeToGui.flush();
					if(commnadOfEngine.length() == 4)
						send.connectToServer("play w" +commnadOfEngine.substring(1,4)+"\n");

					kgs_writeToKgs.print(commnadOfEngine+"\n\n");
					e_readFromEngine.readLine();
					kgs_writeToKgs.flush();
					break;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		// finally {
		// 	try {
		// 		if (gui_writeToGui != null){
		// 			gui_writeToGui.close();
		// 		}
		// 	} catch (IOException ex) {
		// 		ex.printStackTrace();
		// 	}
		// }
    }
}

class Recive {

    private BufferedReader in;
    private PrintWriter out;


    public void connectToServer(String a) throws IOException {

        // Get the server address from a dialog box.
        //System.out.println(InetAddress.getLocalHost());

        // Make connection and initialize streams
        Socket socket = new Socket("127.0.0.1", 9898);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        // Consume the initial welcoming messages from the server

        out.println(a);
       
    }


}