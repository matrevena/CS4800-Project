package graphicalPassword;
//Main Author: Peter Giblin
//Matthaw wrote the initial ApprovePassword class and helped design this version

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import graphicalPassword.MainGUI.Mode;

public class ApprovePassword {
	static int clickCounter = 0;
	static int[][] enterClickCoords = new int[9][2];
	static int[] enterClickSizes = new int[9];
	
	static MouseAdapter captureClicks = new MouseAdapter(){};
	static ActionListener enterPassAct = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
	}};
	static ActionListener endEnterModeAct = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
	}};
		
	void main()
	{
		
	}
	
	public static void initiateEnterMode(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel,
			JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu, JButton enterPassBtn, JButton endEnterModeBtn)
	{
		JOptionPane.showMessageDialog(mainFrame, "Select the password you wish to load.", "Information", JOptionPane.INFORMATION_MESSAGE);
		
		cleanActionListeners(enterPassBtn, endEnterModeBtn, pictureLabel);
		
		String directory = System.getProperty("user.dir") + File.separator + "UserImages";
		String filePath = MainGUI.fileChooser(directory);
		
		File loadedPic = new File(filePath);
		String passName = loadedPic.getName().substring(0, loadedPic.getName().length() - 4);
		
		LoadPassword.loadPicture(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel);
		
		endEnterMode(mainFrame, overlayPanel, pictureLabel, menuBar, userMenu, userMenu, endEnterModeBtn, endEnterModeBtn);
		
		
		if (MainGUI.getProgramMode() == Mode.DEFAULT)
		{
			enterPassBtn.setVisible(true);
			
			mainFrame.revalidate();
			mainFrame.repaint();
			
			enterPassAct = new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					MainGUI.setProgramMode(Mode.ENTER);
					
					DisplayPassword.setResourcePath(3); ////////////////////////////////////TEMP//////////////////////////////////
					
					endEnterModeBtn.setVisible(true);
					
					controlsMenu.setEnabled(false);
					userMenu.setEnabled(false);
					enterPassBtn.setEnabled(false);
					
					mainFrame.revalidate();
					mainFrame.repaint();
					
					String[] loadedString = new String[3];
					loadedString = LoadPassword.loadCoordsFile(passName);
					
					String[] tempClickArray = new String[2];
					
					tempClickArray[0] = loadedString[0];
					tempClickArray[1] = loadedString[1];
					enterClickCoords = LoadPassword.loadCoords(tempClickArray);
					
					enterClickSizes = LoadPassword.loadSizes(loadedString[2]);
					
					int numOfCoords = countArrayCoords(enterClickCoords);
					
					captureClicks = new MouseAdapter(){
						public void mouseClicked(MouseEvent e)
					    {
					    	int x=e.getX();
					        int y=e.getY();
					        
					        if (clickCounter < numOfCoords)
					        {
					        	int [] correctCoords = enterClickCoords[clickCounter];
					        	
					        	int [] checkingCoords = new int[2];
					        	checkingCoords[0] = x;
					        	checkingCoords[1] = y;
					        	
					        	boolean correct = compareCoords(correctCoords, checkingCoords, clickCounter);
					        	
					        	if (!correct)
					        	{
					        		clickCounter = 0;
					        		DisplayPassword.deleteCreationCircles();
					        	}
					        	else
					        		clickCounter++;
					        }
					        if (clickCounter >= numOfCoords)
					        {
					        	clickCounter = 0;
					        	endEnterMode(mainFrame, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu, enterPassBtn, endEnterModeBtn);
					        	
								String textPass;
								textPass = LoadPassword.loadTextPass(passName);
								
					        	JOptionPane.showMessageDialog(mainFrame, "TEMPORARY - Your Password is: " + textPass, "Information", JOptionPane.INFORMATION_MESSAGE);
					        }
					    }
					};		
					
					pictureLabel.removeMouseListener(captureClicks);
					pictureLabel.addMouseListener(captureClicks);
					
					endEnterModeAct = new ActionListener() {
						public void actionPerformed(ActionEvent arg0)
						{
							endEnterMode(mainFrame, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu, enterPassBtn, endEnterModeBtn);
						}
					};
					
					endEnterModeBtn.addActionListener(endEnterModeAct);
				}
			};
			
			enterPassBtn.addActionListener(enterPassAct);
		}
	}
	
	public static void resetCounter()
	{
		clickCounter = 0;
	}
	
	public static boolean compareCoords(int[] correctCoords, int[] checkingCoords, int index)
	{
		if (Math.abs(correctCoords[0] - checkingCoords[0]) <= (enterClickSizes[index]/2))
			if (Math.abs(correctCoords[1] - checkingCoords[1]) <= (enterClickSizes[index]/2))
				return true;
		return false;
	}
	
	public static int[][] removeCoord(int[][] coords, int index)
	{
		coords[index][0] = 0;
		coords[index][1] = 0;
		
		return coords = organize2DArray(coords);
	}
	
	public static int[] removeSize(int[] sizes, int index)
	{
		sizes[index] = 0;
		
		return sizes = organize1DArray(sizes);
	}
	
	public static int[] organize1DArray(int[] sizes)
	{
		for (int i = 0; i < sizes.length - 1; i++)
		{
			if (sizes[i] == 0)
				if (sizes[i+1] == 0)
					return sizes;
				else
				{
					sizes[i] = sizes[i+1];
					sizes[i+1] = 0;
				}
		}
		return sizes;
	}
	
	public static int[][] organize2DArray(int[][] coords)
	{
		for (int i = 0; i < coords.length - 1; i++)
		{
			if (coords[i][0] == 0)
				if (coords[i+1][0] == 0)
					return coords;
				else
				{
					coords[i][0] = coords[i+1][0];
					coords[i][1] = coords[i+1][1];
					coords[i+1][0] = 0;
					coords[i+1][1] = 0;
				}
		}
		return coords;
	}
	
	public static int[] reset1DArray(int[] array)
	{
		for (int i = 0; i<array.length; i++)
			array[i] = 0;
		
		return array;
	}
	
	public static int[][] reset2DArray(int[][] coords)
	{
		for (int i = 0; i<coords.length; i++)
		{
			coords[i][0] = 0;
			coords[i][1] = 0;
		}
		
		return coords;
	}
	
	public static void cleanActionListeners(JButton enterPassBtn, JButton endEnterModeBtn, JLabel pictureLabel)
	{
		enterPassBtn = MainGUI.removeActionListeners(enterPassBtn);
		enterPassBtn = MainGUI.removeActionListeners(endEnterModeBtn);
	}
	
	public static void endEnterMode(JFrame mainFrame, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar,
			JMenu controlsMenu, JMenu userMenu, JButton enterPassBtn, JButton endEnterModeBtn)
	{
		MainGUI.setProgramMode(Mode.DEFAULT);
		
		enterPassBtn.setEnabled(true);
		endEnterModeBtn.setVisible(false);
		controlsMenu.setEnabled(true);
		userMenu.setEnabled(true);
		
		enterPassBtn = MainGUI.removeActionListeners(endEnterModeBtn);
		pictureLabel.removeMouseListener(captureClicks);
		
		DisplayPassword.deleteCreationCircles();
		
		reset2DArray(enterClickCoords);
		reset1DArray(enterClickSizes);
		
		clickCounter = 0;
		
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static int countArrayCoords(int[][] clickCoords)
	{
		int number = 0;
		
		for (int i = 0; i < clickCoords.length; i++)
		{
			if (clickCoords[i][0] == 0)
				return number;
			else
				number++;
		}
		return number;
	}
}
