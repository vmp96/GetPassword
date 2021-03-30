import java.util.HashSet;
import java.security.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class GetPassword{
	
	public static void instructions(int status){
		System.out.println("---ABOUT---");
		System.out.println("GetPassword uses a cryptographically strong random number generator to produce a random string.");
		System.out.println("---USE---\nGetPassword length [return option] [symbols]");
		System.out.println("\t-length: Positive integer");
		System.out.println("\t-return option: 0:(Default)Prints to stdout and clipboard. 1:Prints to stdout. 2:Copies to clipboard");
		System.out.println("\t-symbols: Set of acceptable \"special\" characters. Must be a subset of default. Unexpected characters are ignored. Defaults to: !*&@%+\\/'!#$^?:,(){}[]~`_");
		System.out.println("\nBy Vincent Pino");
		System.exit(status);
	}
	
	public static int getOptions(String arg){
		int options = 0;
		try{
			options = Integer.parseInt(arg);
			}catch(NumberFormatException e){
				System.out.println("Invalid return option");
				System.out.println(e.toString());
				System.exit(1);
			}
			if(options < 0 || options > 2){
				System.out.println("Invalid return option");
				System.exit(1);
			}
			return options;
	}
	
	//checks user supplied argument against the allowed special characters.
	//uses hashset to easily eliminate any duplicate entries
	public static String getChars(String arg){
		HashSet<String> specialCharacters = new HashSet<String>();
		String defaultSpecialChars = "!*&@%+\\/'!#$^?:,(){}[]~`_";
		for(int i = 0; i< arg.length(); i++){
			if(defaultSpecialChars.indexOf(arg.charAt(i))!=-1){
				specialCharacters.add(arg.substring(i,i+1));
			}
		}
		
		String ret = "";
		for (String i : specialCharacters) {
			ret += i;
		}
		return ret;
		
	}
	
	
	protected static String generatePassword(int length, String specialChars) throws NoSuchAlgorithmException, NoSuchProviderException{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		//Force self seeding to create more entropy
		byte[] rand = new byte[length];
		sr.nextBytes(rand);
		
		//Generates integers that are mapped to ascii codes from [! - ~] (all special characters and alphanumerics)
		//Intergers that match the allowed characters are added to return value. 
		String ret = "";
		int i = 0;
		while(i < length){
			int gen = 33 + sr.nextInt(86);
			if(!Character.isLetterOrDigit(gen) && specialChars.indexOf(gen) == -1){
				continue;
			}
			ret+=(char)gen;
			i++;
			
		}
		return ret;
	}

	public static void main(String args[]){
		if(args.length == 0){
			instructions(0);		
		}
		if(args.length > 3){
			instructions(1);
		}
		
		int passwordLength = 0;
		try{
			passwordLength = Integer.parseInt(args[0]);
		}catch(NumberFormatException e){
			instructions(1);
		}
		
		int options = 0;
		if(args.length >= 2){
			options = getOptions(args[1]);
		}
		
		String specialCharacters = "@%+\\/'!#$^?:,(){}[]~`-_.";
		if(args.length==3){
			specialCharacters = getChars(args[2]);
		}
		
		String pw = "";
		try{
			pw = generatePassword(passwordLength, specialCharacters);
		}catch(NoSuchAlgorithmException e){
			System.out.println(e.toString());
		}catch(NoSuchProviderException e){
			System.out.println(e.toString());
		}
		if(options != 1){
			StringSelection selection = new StringSelection(pw);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			if(options == 2){
				String outString = "";
				while(outString.length() < passwordLength){
					outString+="*";
				}
			System.out.println(outString + " copied to clipboard");
			}
		}
		if(options == 1){
			System.out.println(pw);
		}
		if(options == 0){
			System.out.println(pw + " copied to clipboard");
		}
		
		//System.out.println("length: "+passwordLength);
		//System.out.println("options: "+options);
		//System.out.println("special chars: "+specialCharacters);
		
	
		
		
	
	}


}