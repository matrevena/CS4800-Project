package graphicalPassword;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

public class LoadPassword {
	void main()
	{
		//TODO construct main
	}
	
	static String loadTextPass(String passName)
	{	
		String currentDir = System.getProperty("user.dir");
		
		String encryptedFolderName = "EncryptedData";
		
		File textPassSaveDir = new File(currentDir + File.separator + encryptedFolderName + File.separator + passName + "_TextPass" + ".txt");
		
		byte[] encryptedData = null;
		try
		{
			encryptedData = FileUtils.readFileToByteArray(textPassSaveDir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String password = null;
		try
		{
			System.out.println("LOAD ByteData: " + encryptedData);///////////////////////////////////////////////////////////////
			String encryptedString = new String(encryptedData);
			System.out.println("LOAD String: " + encryptedString);////////////////////////////////////////////////////////
			password = Encryption.decryptString(encryptedData, loadEncryptionKey(passName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return password;
	}
	
	static SecretKey loadEncryptionKey(String passName)
	{
		SecretKey key;
		
		String currentDir = System.getProperty("user.dir");
		String encryptedFolderName = "EncryptedData";
		
		File keySaveDir = new File(currentDir + File.separator + encryptedFolderName + File.separator + passName + "_Key" + ".txt");
	    
		String encodedKey = "";
		try
		{
			Scanner keyFileScanner = new Scanner(keySaveDir);
			encodedKey = keyFileScanner.useDelimiter("\\Z").next();
			keyFileScanner.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		return key;
	}
	
	public static int[][] loadCoords(String passName)
	{
		String currentDir = System.getProperty("user.dir");
		String encryptedFolderName = "EncryptedData";
		
		File textPassSaveDir = new File(currentDir + File.separator + encryptedFolderName + File.separator + passName + "_Coords" + ".txt");
		
		String fileText = "";
		try
		{
			Scanner coordFileScanner = new Scanner(textPassSaveDir);
			fileText = coordFileScanner.useDelimiter("\\Z").next();
			coordFileScanner.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		String[] splitString = fileText.split(" ");
		
		String[] coordsX = splitString[0].split(",");
		String[] coordsY = splitString[1].split(",");
		
		int[][] clickCoords = new int[9][2];
		
		for (int i = 0; i <= clickCoords.length - 1; i++)
		{
			int tempNumX = Integer.valueOf(coordsX[i]);
			clickCoords[i][0] = tempNumX;
			
			int tempNumY = Integer.valueOf(coordsY[i]);
			clickCoords[i][1] = tempNumY;
		}
		
		return clickCoords;
	}
	
	static void loadPicture(String filePath, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		BufferedImage pic = null;
		try
		{
			pic = ImageIO.read(new File(filePath));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Image img = scalePicture(pic, mainFrame, mainPanel, overlayPanel, pictureLabel);
		
		pictureLabel.setIcon(new ImageIcon(img));
		
		//Temp
		DisplayPassword.resetCounter();/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	public static Image scalePicture(BufferedImage pic, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		//TODO Add aspect ratio option
		//Scales the size of the uploaded image
		
		//Other possible method
		//pic = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		
		Image img = pic.getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
		
		return img;
		
		/* Properties that need to adjusted depending on the image size, will also need to implement a cap on size and make images scale down
		mainFrame.setBounds(100, 100, 1298, 780);
		mainPanel.setBounds(0, 20, 1280, 720);
		pictureLabel.setBounds(0, 20, 1280, 720);
		*/
	}
	
	public static void deleteAll(JFrame mainFrame)
	{
		String encryptedFolderName = "EncryptedData";
		String picFolderName = "UserImages";
		
		String currentDir = System.getProperty("user.dir");
		String encryptedFilePath = (currentDir + File.separator + encryptedFolderName);
		String picFilePath = (currentDir + File.separator + picFolderName);
		String chosenPassFilePath = SavePassword.fileChooser(picFilePath);
;		File loadedPic = new File(chosenPassFilePath);
		String passName = loadedPic.getName().substring(0, loadedPic.getName().length() - 4);
		
		deleteTextPass(passName, encryptedFilePath, mainFrame);
		deleteKey(passName, encryptedFilePath, mainFrame);
		deleteCoords(passName, encryptedFilePath, mainFrame);
		deletePic(passName, picFilePath, mainFrame);
	}

	public static void deleteTextPass(String passName, String filePath, JFrame mainFrame)
	{
		File textPassFile = new File(filePath + File.separator + passName + "_TextPass" + ".txt");

		if (!textPassFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + "_TextPass" + ".txt at " + textPassFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void deleteKey(String passName, String filePath, JFrame mainFrame)
	{
		File keyFile = new File(filePath + File.separator + passName + "_Key" + ".txt");
		
		if (!keyFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + "_Key" + ".txt at " + keyFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void deleteCoords(String passName, String filePath, JFrame mainFrame)
	{
		File coordsFile = new File(filePath + File.separator + passName + "_Coords" + ".txt");
		
		if (!coordsFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + "_Coords" + ".txt at " + coordsFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	public static void deletePic(String passName, String filePath, JFrame mainFrame)
	{
		File picFile = new File(filePath + File.separator + passName + ".png");

		if (!picFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + " at " + picFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
