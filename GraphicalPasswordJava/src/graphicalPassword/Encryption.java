package graphicalPassword;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Encryption
{
	static Cipher cipher;
	
	private static final String UNICODE_FORMAT = "UTF-8";
	public static void main(String text, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		 try 
		 {
			 SecretKey encryptionKey = generateKey("AES");
			 System.out.println("Test Key: " + encryptionKey);////////////////////////////////////////////////////////
			 byte[] encryptedData = encryptString(text, encryptionKey);
			 System.out.println("TEST encryptedData: " + encryptedData);
			 String encryptedString = new String(encryptedData);
			 System.out.println("TEST encryptedString: " + encryptedString);
			 String decrypted;
			 
			 String passName = "test";
			 
			 int[][] clickCoords = new int[9][2];
			 
			 String filePath = SavePassword.fileChooser(System.getProperty("user.home") + File.separator + "Pictures");
			 //SavePassword.saveTextPass(passName, text, encryptionKey, currentDir, textPassFolderName);
			 //SavePassword.saveEncryptionKey(passName, encryptionKey, currentDir, textPassFolderName);
			 //SavePassword.saveCoords(passName, clickCoords, currentDir, textPassFolderName);
			 SavePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel, passName, text, filePath, clickCoords);
			 
			 decrypted = LoadPassword.loadTextPass(passName);
			 
			 System.out.println("TEST Decrypted: " + decrypted);
		 }
		 catch(Exception e) 
		 {
			 e.printStackTrace();
		 }
	}

	public static SecretKey generateKey(String encryptionType)
	{
		try
		{
			KeyGenerator kG = KeyGenerator.getInstance(encryptionType);
			SecretKey key  = kG.generateKey();
			return key;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public static byte[] encryptString(String dataToEncrypt, SecretKey key)
	{
		try
		{
			byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] textEncrypted = cipher.doFinal(text);
			
			return textEncrypted;
		}
			catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	
	}

	public static String decryptString(byte[] dataToDecrypt, SecretKey key)
	{
		//System.out.println("dataToDecrypt: " + dataToDecrypt);
		//System.out.println("SecretKey: " + key);
		try 
		{
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
			System.out.println("textDecrypted: " + textDecrypted);
			String result = new String(textDecrypted);
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;	
		}
	}
	
	public static void initiateCipher()
	{
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	public static Cipher getCipher()
	{
		return cipher;
	}
}

