package graphicalPassword;
//Main Author: Peter Giblin
//Matthaw Trevena wrote the initial ApprovePassword class and helped design this version
//Tested / Debugged by: Peter Giblin

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
	//Global Variables
	static int clickCounter = 0;
	static int[][] enterClickCoords = new int[9][2];
	static int[] enterClickSizes = new int[9];
	
	//Global actions listener points for creation and deletion without duplication
	static MouseAdapter captureClicks = new MouseAdapter(){};
	static ActionListener enterPassAct = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
	}};
	static ActionListener endEnterModeAct = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
	}};
	
	
	void main(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel,
			JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu, JButton enterPassBtn, JButton endEnterModeBtn)
	{
		initiateEnterMode( mainFrame, mainPanel, overlayPanel, pictureLabel,
				menuBar, controlsMenu, userMenu, enterPassBtn, endEnterModeBtn);
	}
	
	public static void initiateEnterMode(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel,
			JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu, JButton enterPassBtn, JButton endEnterModeBtn)
	{
		JOptionPane.showMessageDialog(mainFrame, "Select the password you wish to load.", "Information", JOptionPane.INFORMATION_MESSAGE);
		
		cleanActionListeners(enterPassBtn, endEnterModeBtn, pictureLabel);
		
		String directory = System.getProperty("user.dir") + File.separator + "UserImages";
		String filePath = MainGUI.fileChooser(directory);
		
		endEnterMode(mainFrame, overlayPanel, pictureLabel, menuBar, userMenu, userMenu, endEnterModeBtn, endEnterModeBtn);
		
		//Loads the chosen graphical password's picture and shows a button to start enter mode
		if (MainGUI.getProgramMode() == Mode.DEFAULT && filePath != null)
		{
			File loadedPic = new File(filePath);
			String passName = loadedPic.getName().substring(0, loadedPic.getName().length() - 4);
			String passUser = passName.substring(0, passName.indexOf("_")).replace("\r", "").replace("\n", "").replace(" ", "");
			
			if(passUser.contentEquals(MainGUI.userName))
			{
				LoadPassword.loadPicture(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel);
				
				enterPassBtn.setVisible(true);
				
				mainFrame.revalidate();
				mainFrame.repaint();
				
				enterPassAct = new ActionListener() {
					public void actionPerformed(ActionEvent arg0)
					{
						MainGUI.setProgramMode(Mode.ENTER);
						
						//Default
						DisplayPassword.setResourcePath(3);
						
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
						
						int [] savedResolution = LoadPassword.loadResolution(loadedString[3]);
						
						int currentWidth = MainGUI.mainFrame.getWidth();
						int currentHeight = MainGUI.mainFrame.getHeight();
						//Adjusts the clickCoordinates to the current resolution of the image
						//Because the user can save a password in one resolution and load it in another
						if (savedResolution[0] != currentWidth || savedResolution[1] != currentHeight)
						{
							float differenceX = (float)currentWidth/savedResolution[0];
							float differenceY = (float)currentHeight/savedResolution[1];
							for (int i = 0; i < enterClickCoords.length; i++)
								if (enterClickCoords[i][0] != 0)
								{
									enterClickCoords[i][0] = Math.round(enterClickCoords[i][0] * differenceX);
									enterClickCoords[i][1] = Math.round(enterClickCoords[i][1] * differenceY);
								}
								else
									break;
						}
						
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
						        	
						        	//If a single click is wrong the user has to restart
						        	if (!correct)
						        	{
						        		clickCounter = 0;
						        		DisplayPassword.deleteCreationShapes();
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
									
						        	JOptionPane.showMessageDialog(mainFrame, "Your Password is: " + textPass, "Information", JOptionPane.INFORMATION_MESSAGE);
						        }
						    }
						};		
						
						//Done to prevent duplicate mouse listeners
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
			else
			{
				JOptionPane.showMessageDialog(mainFrame, "You have tried to load a password belonging to a different user.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
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
		
		DisplayPassword.deleteCreationShapes();
		
		reset2DArray(enterClickCoords);
		reset1DArray(enterClickSizes);
		
		clickCounter = 0;
		
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static int countArrayCoords(int[][] clickCoords)
	{
		int number = 0;
		
		//Counts until an empty index is found
		for (int i = 0; i < clickCoords.length; i++)
		{
			if (clickCoords[i][0] == 0)
				return number;
			else
				number++;
		}
		return number;
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
		//Finds and removes empty indexes
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
		//Finds and removes empty indexes
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
}
