package graphicalPassword;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Encryption
{
	private static final String UNICODE_FORMAT = "UTF-8";
 
	public static void main(String text)
	{
		 try 
		 {
			 SecretKey MyKey = generateKey("AES");
			 Cipher Crypto;
			 Crypto = Cipher.getInstance("AES");
			 byte[] encryptedData = encryptString(text, MyKey, Crypto);
			 String encryptedString = new String(encryptedData);
			 System.out.println(encryptedString);
			 String decrypted = decryptString(encryptedData, MyKey, Crypto);
			 System.out.println(decrypted);
		 }
		 catch(Exception e) 
		 {
			 System.out.println("Encyption Error");
		 }
	}

	public static SecretKey generateKey(String encryptionType)
	{
		try
		{
			KeyGenerator kG = KeyGenerator.getInstance(encryptionType);
			SecretKey mK  = kG.generateKey();
			return mK;
		}
		catch(Exception e)
		{
			System.out.println("Key Generation Error: " + e);
		}
		{	
	 return null;
		}
	
	}
	public static byte[]  encryptString(String dataToEncrypt, SecretKey mK, Cipher cipher)
	{
		try
		{
			byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
			
			cipher.init(Cipher.ENCRYPT_MODE, mK);
			byte[] textEncrypted = cipher.doFinal(text);
			
			return textEncrypted;
		}
			
				catch(Exception e)
		{
			
			return null;
		}
	
	}

	public static String  decryptString(byte[] dataToDecrypt, SecretKey mK, Cipher cipher)
	{
		try 
		{
			cipher.init(Cipher.DECRYPT_MODE, mK);
			byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
			String result = new String(textDecrypted);
			
			return result;
		}
		catch(Exception e)
		{
			System.out.println(e);
			
			return null;	
		}
	}
}

