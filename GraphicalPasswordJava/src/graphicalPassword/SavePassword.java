package graphicalPassword;
//Main Author: Peter Giblin

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

public class SavePassword {
	static void main(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, String passName, String textPass, String filePath, int [][] clickCoords, int[] clickSizes)
	{
		String currentDir = System.getProperty("user.dir");
		String encryptedFolderName = "EncryptedData";
		
		savePic(passName, pictureLabel, currentDir, filePath);
		
		SecretKey encryptionKey = Encryption.generateKey("AES");
		
		saveTextPass(passName, textPass, encryptionKey, currentDir, encryptedFolderName);
		saveEncryptionKey(passName, encryptionKey, currentDir, encryptedFolderName);
		saveCoords(passName, clickCoords, clickSizes, currentDir, encryptedFolderName);
	}
	
	static void savePic(String passName, JLabel pictureLabel, String currentDir, String filePath)
	{
		String picExtension = "png";
		String picFolderName = "UserImages";
		
		File picFolderDir = new File(currentDir + File.separator + picFolderName);
		if (picFolderDir.exists() != true)
		{
			picFolderDir.mkdir();
		}
		
		File picSaveDir = new File(currentDir + File.separator + picFolderName + File.separator + passName + "." + picExtension);
		
		BufferedImage pic = null;
		try
		{
			pic = ImageIO.read(new File(filePath));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			ImageIO.write(pic, picExtension, picSaveDir);
		} 
		catch(IOException e)
		{
			System.out.println("Write error for " + picSaveDir.getPath() + ": " + e.getMessage());
		}
	}
	
	public static void saveCoords(String passName, int[][] clickCoords, int[] clickSizes, String currentDir, String coordFolderName)
	{
		File coordFolderDir = new File(currentDir + File.separator + coordFolderName);
		
		if (coordFolderDir.exists() != true)
		{
			coordFolderDir.mkdir();
		}
		
		File textPassSaveDir = new File(currentDir + File.separator + coordFolderName + File.separator + passName + "_Coords" + ".txt");
		
		String csvCoordsX = "";
		String csvCoordsY = "";
		
		//Adds first 8 coordinates out of 9 to a save file format
		for (int i = 0; i < clickCoords.length - 1; i++)
		{
			int tempNumX = clickCoords[i][0];
			csvCoordsX = csvCoordsX + tempNumX + ",";
			
			int tempNumY = clickCoords[i][1];
			csvCoordsY = csvCoordsY + tempNumY + ",";
		}
		//Adds last number without a comma
		int tempNumX = clickCoords[clickCoords.length - 1][0];
		csvCoordsX = csvCoordsX + tempNumX;
		
		int tempNumY = clickCoords[clickCoords.length - 1][1];
		csvCoordsY = csvCoordsY + tempNumY;
		
		String csvSizes = "";
		
		//Adds first 8 coordinates out of 9 to a save file format
		for (int i = 0; i < clickSizes.length - 1; i++)
		{
			int csvSizeNum = clickSizes[i];
			csvSizes = csvSizes + csvSizeNum + ",";
		}
		//Adds last number without a comma
		int csvSizeNum = clickSizes[clickCoords.length - 1];
		csvSizes = csvSizes + csvSizeNum;
		
		int windowWidth = MainGUI.mainFrame.getWidth();
		int windowHeight = MainGUI.mainFrame.getHeight();
		
		FileWriter coordFileWriter;
		try {
			coordFileWriter = new FileWriter(textPassSaveDir);
			coordFileWriter.write(csvCoordsX);
			coordFileWriter.write(" ");
			coordFileWriter.write(csvCoordsY);
			coordFileWriter.write(" ");
			coordFileWriter.write(csvSizes);
			coordFileWriter.write(" ");
			coordFileWriter.write(windowWidth + "," + windowHeight);
			coordFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTextPass(String passName, String textPass, SecretKey encryptionKey, String currentDir, String textPassFolderName)
	{
		File textPassFolderDir = new File(currentDir + File.separator + textPassFolderName);
		
		if (textPassFolderDir.exists() != true)
		{
			textPassFolderDir.mkdir();
		}
		
		File textPassSaveDir = new File(currentDir + File.separator + textPassFolderName + File.separator + passName + "_TextPass" + ".txt");
		
		byte[] encryptedData = Encryption.encryptString(textPass, encryptionKey);
		
		try {
			FileUtils.writeByteArrayToFile(textPassSaveDir, encryptedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveEncryptionKey(String passName, SecretKey encryptionKey, String currentDir, String keyFolderName)
	{
		File keySaveDir = new File(currentDir + File.separator + keyFolderName + File.separator + passName + "_Key" + ".txt");
		
		String encodedKey = Base64.getEncoder().encodeToString(encryptionKey.getEncoded());
		
		FileWriter keyFileWriter;
		try {
			keyFileWriter = new FileWriter(keySaveDir);
			keyFileWriter.write(encodedKey);
		    keyFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
