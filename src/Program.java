package sunseeker.Battery;

import gnu.io.*;
import java.io.File;
import java.rmi.AccessException;
import java.util.Scanner;

/**
 * @author Kai Gray
 *
 * Reads data from serial port, and battery data.
 */
public class Program {
	
	//some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;
    //EventListener
    final static int DATA_AVAILABLE = 1;
    
	public static void main(String[] args)throws Exception{
		int count = 0;
		//file reader and logging
		
		//test file
		//File input = new File("ASCam.txt");
		//log file
		//File output = new File("output.txt");
		Scanner in = new Scanner(System.in);
		
		Reciever data = new Reciever();
		
		boolean incoming = false;
		
		try{
			//find and connect to port
			data.searchForPorts();
			data.connect();

			incoming = data.initIOStream(data.getSerialPort());

			//event listener
			SerialPortEvent event = new SerialPortEvent(data.getSerialPort(),DATA_AVAILABLE,data.isConnected(),incoming);

			//one day it will get data from port
			while(data.isConnected()){

				//port data
				data.serialEvent(event);

				//decodes data
				//getLogAllText is a terrible name for anything.
				for(int i = 0; i < data.getCounter();i++){
					Decoder.reader(data.getALogText(i)).cellList();
				}
				//prints data
				for(int i = 0; i < Data.getCellList().size()*2;i++){
					print(Data.getCellList().get(i));
				}

				if(Data.getMin() != 10)
					System.out.printf("\nVoltage:\nMax: %.4f\nMin: %.4f\nDiffernece: %.5f",Data.getMax(),Data.getMin(),Data.diff());
				//				System.out.println("\nContinue? [Y/n]");
				//				if(in.nextLine() == "n"){
				//					data.setConnected(false);
				//				}
				
				count++;
				if(count == 10000){
					throw new SecurityException("");
				}
			}

		}catch(SecurityException e){
			System.out.println("STOP");
		}catch(Exception e){
			System.out.println(" Exception: " + e);
			e.printStackTrace();
		}
		//disconnect from port and close scanner
		data.disconnect();
		in.close();
	}

	//prints cell values
	public static void print(Data cell){
		System.out.printf("#%d - Voltage: %.4f Amps: %5f\n",cell.getBattery(),cell.getVoltage(),cell.getCurrent());
	}
	
}
