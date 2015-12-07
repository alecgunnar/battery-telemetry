package sunseeker.Battery;

import java.io.IOException;

/**
 * @author Kai Gray
 *
 * Decodes and formats data
 */
public class Decoder {
	
	
	//decodes data based upon headers
	public static Data reader(String line) throws IOException{
		String[] dataType = new String[3];
		dataType[1] = "";
		int dec = 0;
		double volt = 0, amps = -1;

		
		try{
			System.out.println("IT's going in!");
			dataType = line.split(" ");

			dec = hexToDec(dataType[2]);
			
			//1 and 2 denotes the half of the pack
			//LT is voltage voltage
			if(dataType[0].equals("LT1") || dataType[0].equals("LT2")){
				volt = voltage(dec);
			}
			//AD is Amps being pulled
			else if(dataType[0].equals("AD1") || dataType[0].equals("AD2")){
				amps = amps(dec);
			}
			//ISH amps in?
			else if(dataType[0].equals("ISH")){
				System.out.println("ISH: " + ampsIn(dec));
			}
			//error messages
			//reads hex code
			else{
				errors(dataType[2]);
			}
			
		}catch(Exception e){System.out.println("Error catch of type " + e);e.printStackTrace();}
		
		Data cell = new Data(Integer.parseInt(dataType[1]),volt,amps);
		
		return cell;
	}
	
	//hexadecimal to Decimal
	public static int hexToDec(String hexString){
	    return Integer.parseInt(hexString,16);
	}
	
	//calculations
	public static double amps(int dec){
		return (2.5 * dec / 16777216);
	}
	public static double ampsIn(int dec){
		return (50*2.5*dec/16777216);
	}
	public static double voltage(int dec){
		return ((dec - 512) * 0.0015);
	}
	
	//error printout
	public static void errors(String err){
		if(err.equals("06B00000")){
			System.out.println("Current");
		}
		else if(err.equals("06800000")){
			System.out.println("CTS/RTS");
		}
		else if(err.equals("06E00000")){
			System.out.println("CAN Out");
		}
		else{
			System.out.println("Unknown Error");
		}
		
	}
	
	
	
}
