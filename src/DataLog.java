package sunseeker.Battery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;

/**
 * @author Kai Gray
 *
 * Write data to file
 */
public class DataLog {
	
	private int counter = 0;
	private static byte message;
	private static boolean busy;
	private static boolean processing;
	public DataLog(){
		
	}
	public boolean checkFile(Scanner in){
		
		//return true writes to the end of file
		return true;
	}
	public void writeToFile(Scanner in){
		
		try{
			File file = new File("output.txt");
			FileWriter write = new FileWriter(file,checkFile(in));
			
			while(in.hasNextLine()){
				write.write(in.nextLine());
			}
			
		}catch(FileNotFoundException e){
			System.out.println("Failed to find file");
		}catch(Exception e){
			System.out.println("Failed do to " + e);
		}
		
	}
	
	
	public static byte getMessage() {
		return message;
	}
	public static void setMessage(byte messag) {
		message = messag;
	}
	public static boolean isBusy() {
		return busy;
	}
	public static void setBusy(boolean bsy) {
		busy = bsy;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public void addToCounter(){
		this.counter++;
	}
	public static void setProcessing(boolean process) {
		processing = process;
		
	}
	public static boolean isProcessing() {
		// TODO Auto-generated method stub
		return processing;
	}
	
}
