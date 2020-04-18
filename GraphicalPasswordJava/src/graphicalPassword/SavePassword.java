package graphicalPassword;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	
	static void savePass(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, String passName)
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
