package graphicalPassword;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SavePassword {
	void main()
	{
		
	}
	
	static void savePass(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, String passName, String textPass)
	{
		String currentDir = System.getProperty("user.dir");
		
		// Will be used to detect and eventually read a config file
		/*
		File config = new File(currentDir + File.separator + "config.txt");

		if (config.exists() == true)
		{
			JOptionPane.showMessageDialog(mainFrame, "Here:" + currentDir, "Information:", JOptionPane.INFORMATION_MESSAGE);
		}
		*/
		
		//Save Image
		String picExtension = "png";
		String picFolderName = "UserImages";
		File picFolderDir = new File(currentDir + File.separator + picFolderName);
		
		if (picFolderDir.exists() != true)
		{
			picFolderDir.mkdir();
		}
		
		File picSaveDir = new File(currentDir + File.separator + picFolderName + File.separator + passName + "." + picExtension);

		ImageIcon icon = (ImageIcon)pictureLabel.getIcon();
		BufferedImage pic = (BufferedImage)(icon.getImage());
		
		try
		{
			ImageIO.write(pic, picExtension, picSaveDir);
		} 
		catch(IOException e)
		{
			System.out.println("Write error for " + picSaveDir.getPath() + ": " + e.getMessage());
		}
		
		//Save Text Password
		String textPassFolderName = "EncryptedData";
		File textPassFolderDir = new File(currentDir + File.separator + textPassFolderName);
		
		if (textPassFolderDir.exists() != true)
		{
			textPassFolderDir.mkdir();
		}
		
		File textPassSaveDir = new File(currentDir + File.separator + textPassFolderName + File.separator + passName + "_Encrypted" + ".txt");
		File keySaveDir = new File(currentDir + File.separator + textPassFolderName + File.separator + passName + "_Key" + ".txt");
		
		try
		{
			SecretKey encrytionKey = Encryption.generateKey("AES");
			Cipher crypto;
			crypto = Cipher.getInstance("AES");
			byte[] encryptedData = Encryption.encryptString(textPass, encrytionKey, crypto);
			String encryptedString = new String(encryptedData);
			String encodedKey = Base64.getEncoder().encodeToString(encrytionKey.getEncoded());
			//String decrypted = Encryption.decryptString(encryptedData, MyKey, Crypto);
			//System.out.println(decrypted);
			
			FileWriter passFileWriter = new FileWriter(textPassSaveDir);
		    passFileWriter.write(encryptedString);
		    passFileWriter.close();
		    
			FileWriter keyFileWriter = new FileWriter(keySaveDir);
		    keyFileWriter.write(encodedKey);
		    keyFileWriter.close();
		}
		catch(Exception e) 
		{
			System.out.println("Encyption Error: " + e);
		}
		
	}
	
	static String fileChooser(String directory)
	{
		//Open Java file chooser
		JFileChooser jFileChooser = new JFileChooser(directory);
		int returnVal = jFileChooser.showOpenDialog(jFileChooser);
		
		//Checks if file is valid and set image to image label
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			String filePath = jFileChooser.getSelectedFile().getAbsolutePath(); //String from user file path input
			return filePath;
		}
		
		return null;
	}
}
