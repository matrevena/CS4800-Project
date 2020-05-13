package graphicalPassword;
//Main Author: Peter Giblin

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
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

public class LoadPassword {
	void main(String passName, String filePath, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		loadPicture(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel);
		loadTextPass(passName);
		String[] coordsFile = loadCoordsFile(passName);
		loadCoords(coordsFile);
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

		DisplayPassword.resetCounter();
	}
	
	public static Image scalePicture(BufferedImage pic, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		Image img = pic.getScaledInstance(mainFrame.getWidth(), mainFrame.getHeight(), Image.SCALE_SMOOTH);
		
		return img;
	}
	
	static String loadTextPass(String passName)
	{	
		String currentDir = System.getProperty("user.dir");
		
		String encryptedFolderName = "EncryptedData";
		
		File textPassSaveDir = new File(currentDir + File.separator + encryptedFolderName + File.separator + passName + "_TextPass" + ".txt");
		
		byte[] encryptedData = null;
		
		try
		{
			encryptedData = Files.readAllBytes(textPassSaveDir.toPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String password = null;
		try
		{	
			password = Encryption.decryptString(encryptedData, loadEncryptionKey(passName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return password;
	}
	
	public static String[] loadCoordsFile(String passName)
	{
		//The coords file contains both the coords and the resolution
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
		
		return splitString;
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
	
	public static int[][] loadCoords(String[] coords)
	{
		int[][] clickCoords = new int[9][2];
		
		String[] coordsX = coords[0].split(",");
		String[] coordsY = coords[1].split(",");
		
		for (int i = 0; i < clickCoords.length; i++)
		{
			int tempNumX = Integer.valueOf(coordsX[i]);
			clickCoords[i][0] = tempNumX;
			
			int tempNumY = Integer.valueOf(coordsY[i]);
			clickCoords[i][1] = tempNumY;
		}
		
		return clickCoords;
	}
	
	public static int[] loadSizes(String sizes)
	{
		int[] clickSizes = new int[9];
		
		String[] splitSizes = sizes.split(",");
		
		for (int i = 0; i < clickSizes.length; i++)
		{
			int tempSize = Integer.valueOf(splitSizes[i]);
			clickSizes[i] = tempSize;
		}
		
		return clickSizes;
	}
	
	public static int[] loadResolution(String original)
	{
		int[] resolution = new int[2];
		
		String[] splitRes = original.split(",");
		
		for (int i = 0; i < splitRes.length; i++)
		{
			int tempRes = Integer.valueOf(splitRes[i]);
			resolution[i] = tempRes;
		}
		
		return resolution;
	}
	
	public static void deleteAll(JFrame mainFrame)
	{
		String encryptedFolderName = "EncryptedData";
		String picFolderName = "UserImages";
		
		String currentDir = System.getProperty("user.dir");
		String encryptedFilePath = (currentDir + File.separator + encryptedFolderName);
		String picFilePath = (currentDir + File.separator + picFolderName);
		String chosenPassFilePath = MainGUI.fileChooser(picFilePath);
		
		if (chosenPassFilePath != null)
		{
			File loadedPic = new File(chosenPassFilePath);
			String passName = loadedPic.getName().substring(0, loadedPic.getName().length() - 4);
			
			deletePic(passName, picFilePath, mainFrame);
			deleteTextPass(passName, encryptedFilePath, mainFrame);
			deleteCoords(passName, encryptedFilePath, mainFrame);
			deleteEncryptionKey(passName, encryptedFilePath, mainFrame);
		}
	}

	public static void deletePic(String passName, String filePath, JFrame mainFrame)
	{
		File picFile = new File(filePath + File.separator + passName + ".png");

		if (!picFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + " at " + picFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void deleteTextPass(String passName, String filePath, JFrame mainFrame)
	{
		File textPassFile = new File(filePath + File.separator + passName + "_TextPass" + ".txt");

		if (!textPassFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + "_TextPass" + ".txt at " + textPassFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void deleteCoords(String passName, String filePath, JFrame mainFrame)
	{
		File coordsFile = new File(filePath + File.separator + passName + "_Coords" + ".txt");
		
		if (!coordsFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + "_Coords" + ".txt at " + coordsFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void deleteEncryptionKey(String passName, String filePath, JFrame mainFrame)
	{
		File keyFile = new File(filePath + File.separator + passName + "_Key" + ".txt");
		
		if (!keyFile.delete())
			JOptionPane.showMessageDialog(mainFrame, ("Failed to delete: " + passName + "_Key" + ".txt at " + keyFile), "Error", JOptionPane.ERROR_MESSAGE);
	}
}
