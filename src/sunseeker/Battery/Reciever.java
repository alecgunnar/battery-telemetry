package sunseeker.Battery;

import gnu.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.AccessException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TooManyListenersException;

/**
 * @author Kai Gray
 *
 * Pulls data from serial port
 */
public class Reciever implements SerialPortEventListener{

	//this is the object that contains the opened port
    private CommPortIdentifier selectedPortIdentifier = null;
    private SerialPort serialPort = null;
	
	
    //map the port names to CommPortIdentifiers
    private HashMap<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();
    //for containing the ports that will be found
	private Enumeration<?> ports = null;
    
    //input and output streams for sending and receiving data
    private InputStream input = null;
    private OutputStream output = null;

    //just a boolean flag that i use for enabling
    //and disabling buttons depending on whether the program
    //is connected to a serial port or not
    private boolean bConnected = false;

    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;

    //a string for recording what goes on in the program
    //this string is written to the GUI
    String logText = "";
    private String[] logAllText = new String[300];
    private int counter = 0;
    
    public Reciever(){
    	
    	setConnected(false);
    	setPorts(null);
    	setSerialPort(null);
    	setInput(null);
    	setOutput(null);
    	
    }

	public void searchForPorts(){
		
		
		ports = CommPortIdentifier.getPortIdentifiers();
		
	        while (ports.hasMoreElements())
	        {
	            CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();

	            //get only serial ports
	            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
	            {
	                portMap.put(curPort.getName(), curPort);
	                this.setPortMap(portMap);
	                this.setSelectedPortIdentifier(curPort);
	                System.out.println(getPortMap());
	            }
	        }
	    }
	    

	 public void connect() throws Exception{
		 
	        String selectedPort = new String();
	        Scanner prtCh = new Scanner(System.in);
	        System.out.println("Select COM port. (COM1,COM2,COM3, or COM4");
	        selectedPort = prtCh.nextLine();
	        prtCh.close();
	        selectedPortIdentifier = portMap.get(selectedPort);

	        CommPort commPort = null;

	        try
	        {
	            //the method below returns an object of type CommPort  String may be issue
	            commPort = selectedPortIdentifier.open("BatteryBoard",TIMEOUT);
	            
	            //the CommPort object can be casted to a SerialPort object
	            serialPort = (SerialPort)commPort;
	            this.setSerialPort(serialPort);
	            
	            //continue reading ports
	            this.setConnected(true);

	            //confirm connection
	            System.out.println(selectedPort + " opened successfully.");
	            
	        }
	        catch (PortInUseException e)
	        {
	        	System.out.println(selectedPort + " is in use. (" + e.toString() + ")");
	            throw new PortInUseException();
	        }
	        catch (Exception e)
	        {
	        	setLogText("Failed to open " + selectedPort + "(" + e.toString() + ")");
	        }
	    }
	 //open the input and output streams
	    //pre style="font-size: 11px;": an open port
	    //post: initialized input and output streams for use to communicate data
	    public boolean initIOStream(SerialPort serial)
	    {

	        try {
	            //retrieve raw data
	            input = serial.getInputStream();
	            this.setInput(input);

	            return true;
	        }
	        catch (IOException e) {
	            System.out.println("I/O Streams failed to open. (" + e.toString() + ")");
	            return false;
	        }
	    }
	    
	    //starts the event listener that knows whenever data is available to be read
	    //pre style="font-size: 11px;": an open serial port
	    //post: an event listener for the serial port that knows when data is received
	    public void initListener()
	    {
	        try
	        {
	            serialPort.addEventListener(this);
	            serialPort.notifyOnDataAvailable(true);
	        }
	        catch (TooManyListenersException e)
	        {
	            System.out.println("Too many listeners. (" + e.toString() + ")");
	        }
	    }
	    //disconnect the serial port
	    //pre style="font-size: 11px;": an open serial port
	    //post: closed serial port
	    public void disconnect()
	    {
	        //close the serial port
	        try
	        {
	        	System.out.println("Disconnecting...");
	            serialPort.removeEventListener();
	            input.close();
	            serialPort.close();
	            setConnected(false);
	            System.out.println("Disconnect Successful");
	        }
	        catch (Exception e)
	        {
	            System.out.println("Failed to disconnect.");
	            System.out.println("Failure caused by " + e.toString());
	            e.printStackTrace();
	        }
	    }
	    //what happens when data is received
	    //pre style="font-size: 11px;": serial event is triggered
	    //post: processing on the data it reads
	    public void serialEvent(SerialPortEvent event) {
	    	
	    	String line = new String();
	    	String all = new String();
	    	String temp = new String();
	    	byte[] singleData;
	    	int hold = 0;
	    	char a;
	    	String[] read;
	    	
	    	if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE)
	        {
	    		try{
	    			
	    			while(input.available() > 0){
	    				line += input.read();
	    			}
	    			all = line.getBytes("UTF-8").toString();
	    			System.out.println(all);
	    			
	    			all = new String(line.getBytes("UTF-8"),"US-ASCII");
	    			if(!all.equals(null))
	    			System.out.println(all);
	    		    
	    			
	    		    System.out.println(output);
	    		
	    		}catch (Exception e){

	    			System.out.println("Failed to read data. (" + e.toString() + ")");
	    			e.printStackTrace();
	    		}
	        }

	    }

	    public void setConnected(boolean connect){
	    	bConnected = connect;
	    }

		public HashMap<String, CommPortIdentifier> getPortMap() {
			return portMap;
		}

		public void setPortMap(HashMap<String, CommPortIdentifier> portMap) {
			this.portMap = portMap;
		}

		public CommPortIdentifier getSelectedPortIdentifier() {
			return selectedPortIdentifier;
		}

		public void setSelectedPortIdentifier(CommPortIdentifier selectedPortIdentifier) {
			this.selectedPortIdentifier = selectedPortIdentifier;
		}

		public SerialPort getSerialPort() {
			return serialPort;
		}

		public void setSerialPort(SerialPort serialPort) {
			this.serialPort = serialPort;
		}

		public InputStream getInput() {
			return input;
		}

		public void setInput(InputStream input) {
			this.input = input;
		}

		public OutputStream getOutput() {
			return output;
		}

		public void setOutput(OutputStream output) {
			this.output = output;
		}

		public boolean isConnected() {
			return bConnected;
		}

		public String getLogText() {
			return logText;
		}

		public void setLogText(String logText) {
			this.logText = logText;
		}
		
		public void addLogText(String logText){
			this.logText += logText;
		}

		public Enumeration<?> getPorts() {
			return ports;
		}

		public void setPorts(Enumeration<?> ports) {
			this.ports = ports;
		}

		public String[] getLogAllText() {
			return logAllText;
		}
		public String getALogText(int i){
			return logAllText[i];
		}

		public void setLogAllText(String log,int i) {
			this.logAllText[i] = log;
		}

		public int getCounter() {
			return counter;
		}

		public void setCounter(int counter) {
			this.counter = counter;
		}
		
		
	
}
	
