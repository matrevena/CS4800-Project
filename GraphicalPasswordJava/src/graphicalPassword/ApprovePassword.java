package graphicalPassword;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ApprovePassword {
	static boolean enterMode = false;
	static int clickCounter = 0;
	static MouseAdapter captureClicks = new MouseAdapter(){};
	
	void main()
	{
		
	}
	
	public static void initiateEnterMode(int[][] clickCoords, String textPass, JFrame mainFrame, JPanel overlayPanel, JLabel pictureLabel,
			JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu, JButton enterPassBtn, JButton endEnterModeBtn)
	{
		//TEMP////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int size = 50;
		//TEMP////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		enterMode = true;
		
		controlsMenu.setEnabled(false);
		userMenu.setEnabled(false);
		enterPassBtn.setEnabled(false);
		
		mainFrame.revalidate();
		mainFrame.repaint();

		int numOfCoords = countArrayCoords(clickCoords);
		
		captureClicks = new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
		    {
		    	int x=e.getX();
		        int y=e.getY();
		        
		        if (clickCounter < numOfCoords)
		        {
		        	int [] correctCoords = clickCoords[clickCounter];
		        	
		        	int [] checkingCoords = new int[2];
		        	checkingCoords[0] = x;
		        	checkingCoords[1] = y;
		        	
		        	boolean correct = compareCoords(correctCoords, checkingCoords, size);
		        	
		        	if (!correct)
		        	{
		        		clickCounter = 0;
		        		DisplayPassword.deleteCreationCircles(overlayPanel, mainFrame);
		        	}
		        	else
		        		clickCounter++;
		        }
		        if (clickCounter >= numOfCoords)
		        {
		        	clickCounter = 0;
		        	pictureLabel.removeMouseListener(captureClicks);
		        	endEnterMode(mainFrame, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu, enterPassBtn, endEnterModeBtn);
		        	JOptionPane.showMessageDialog(mainFrame, "TEMPORARY - Your Password is: " + textPass, "Information", JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		};
		
		pictureLabel.addMouseListener(captureClicks);
		
		endEnterModeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				pictureLabel.removeMouseListener(captureClicks);
				endEnterMode(mainFrame, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu, enterPassBtn, endEnterModeBtn);
			}
		});
	}
	
	public static boolean compareCoords(int[] correctCoords, int[] checkingCoords, int size)
	{
		if (Math.abs(correctCoords[0] - checkingCoords[0]) <= (size/2))
			if (Math.abs(correctCoords[1] - checkingCoords[1]) <= (size/2))
				return true;
		return false;
	}
	
	public static int[][] removeCoord(int[][] clickCoords, int index)
	{
		clickCoords[index][0] = 0;
		clickCoords[index][1] = 0;
		
		return clickCoords;
	}
	
	public static int[][] resetCoords(int[][] clickCoords)
	{
		for (int i = 0; i<clickCoords.length; i++)
		{
			clickCoords[i][0] = 0;
			clickCoords[i][1] = 0;
		}
		
		return clickCoords;
	}
	
	public static void validateClick(int x, int y, JPanel overlayPanel, JFrame mainFrame)
	{
		if (enterMode == true)
		DisplayPassword.displayCreationCircles(x, y, overlayPanel, mainFrame);
	}
	
	public static void endEnterMode(JFrame mainFrame, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar,
			JMenu controlsMenu, JMenu userMenu, JButton enterPassBtn, JButton endEnterModeBtn)
	{
		enterMode = false;
		enterPassBtn.setEnabled(true);
		endEnterModeBtn.setVisible(false);
		controlsMenu.setEnabled(true);
		userMenu.setEnabled(true);
		DisplayPassword.deleteCreationCircles(overlayPanel, mainFrame);
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static int countArrayCoords(int[][] array)
	{
		int number = 0;
		for (int i = 0; i < array.length; i++)
		{
			if (array[i][0] == 0)
				break;
			else
				number++;
		}
		return number;
	}
	
	public static void setEnterMode(boolean state)
	{
		enterMode = state;
	}
}
