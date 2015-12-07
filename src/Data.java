package sunseeker.Battery;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Kai Gray
 * 
 * formats and labels data
 */
public class Data {
	
	//there is a battery 0
	private int battery = -1;
	//battery attributes
	private double voltage = 0;
	private double current = 0;
	//voltage stats.
	private static double min = 10;
	private static double max = 0;
	//list of all cells and data
	private static ArrayList<Data> cellList = new ArrayList<Data>();
	
	// battery object
	public Data(int battery,double voltage,double current){
		
		//battery #
		setBattery(battery);
		//prevents amp data from destroying amp data
		if(voltage != 0){
			setVoltage(voltage);
		}
		//prevents voltage data from destroying amp data
		if(current != 0){
			setCurrent(current);
		}
		
	}
	
	//create battery list
	public void cellList() throws Exception{
		
		//index for data modifications
		int index = this.getBattery();
		//trips if the index of cellList does not exist
		boolean add = false;
		double volt, amps;
		
		try{
			
			volt = cellList.get(index).getVoltage();
			if(volt < 0){
				volt = this.getVoltage();
			}
			cellList.get(index).setVoltage(volt);
			
		}catch(Exception e){add = true;}
		try{
			
			amps = cellList.get(index).getCurrent();
			if(amps < 0){
				amps = this.getCurrent();
			}
			cellList.get(index).setCurrent(amps);
			
		}catch(Exception e){add = true;}
		
		//creates no data object in cellList
		if(add){
			cellList.add(this);
		}
		
		//determines min and max values
		max(getVoltage());
		min(getVoltage());
		
	}
	
	//stores data to text file.
	public void store(File output) throws IOException{
		
		FileWriter print = new FileWriter(output,true);
		print.write(this.getBattery() + " Voltage: " + this.getVoltage() + " Current: " + this.getCurrent());
		print.write("\n");
		print.close();
		
	}
	
	
	public static double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public static double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	public static ArrayList<Data> getCellList(){
		return cellList;
	}
	public double getVoltage() {
		return voltage;
	}
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	public double getCurrent() {
		return current;
	}
	public void setCurrent(double current) {
		this.current = current;
	}
	
	// min, max and difference calculation
	public void min(double a){
		if(a != 0){
			if(a < getMin()){
				setMin(a);
			}
		}
	}
	public void max(double a){
		if(a != 0){
			if(a > getMax()){
				setMax(a);
			}
		}
	}
	public static double diff(){
		return getMax() - getMin();
	}

}
