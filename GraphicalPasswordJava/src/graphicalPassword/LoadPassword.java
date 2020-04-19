package graphicalPassword;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LoadPassword {
	void main()
	{

	}
	
	static SecretKey loadKey(String passName, SecretKey key)
	{
		String currentDir = System.getProperty("user.dir");
		
		String textPassFolderName = "EncryptedData";
		
		File textPassSaveDir = new File(currentDir + File.separator + textPassFolderName + File.separator + passName + "_Encrypted" + ".txt");
		File keySaveDir = new File(currentDir + File.separator + textPassFolderName + File.separator + passName + "_Key" + ".txt");
	    
		String encodedKey = "";

		try
		{
			encodedKey = new Scanner(keySaveDir).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
		
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		
		return key;
	}
}
