package graphicalPassword;
//Main Author: Peter Giblin

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import graphicalPassword.MainGUI.Mode;

public class DisplayPassword {
	static JFrame mainFrame = MainGUI.mainFrame;
	static JPanel overlayPanel = MainGUI.getOverlayPanel();
	
	//Array that is used to store which visible circles corresponds to which click coordinate
	//The user is able to delete circles in the middle of their combination
	//So this array was made to have each visible circle point to the proper coordinate
	static int[] circleCounter = new int[9];
	
	//Objects which are generated and hidden when the user changes their click combination during creation and approval
	static JLabel circleLabel0 = new JLabel("");
	static JLabel circleLabel1 = new JLabel("");
	static JLabel circleLabel2 = new JLabel("");
	static JLabel circleLabel3 = new JLabel("");
	static JLabel circleLabel4 = new JLabel("");
	static JLabel circleLabel5 = new JLabel("");
	static JLabel circleLabel6 = new JLabel("");
	static JLabel circleLabel7 = new JLabel("");
	static JLabel circleLabel8 = new JLabel("");
	
	static JLabel numLabel1 = new JLabel("1");
	static JLabel numLabel2 = new JLabel("2");
	static JLabel numLabel3 = new JLabel("3");
	static JLabel numLabel4 = new JLabel("4");
	static JLabel numLabel5 = new JLabel("5");
	static JLabel numLabel6 = new JLabel("6");
	static JLabel numLabel7 = new JLabel("7");
	static JLabel numLabel8 = new JLabel("8");
	static JLabel numLabel9 = new JLabel("9");
	
	//Defaults click size the size 3 of 4
	static String resourcePath = "/passCreationCircle40x40.png";
	public static void main()
	{
		overlayPanel.add(circleLabel0);
		overlayPanel.add(circleLabel1);
		overlayPanel.add(circleLabel2);
		overlayPanel.add(circleLabel3);
		overlayPanel.add(circleLabel4);
		overlayPanel.add(circleLabel5);
		overlayPanel.add(circleLabel6);
		overlayPanel.add(circleLabel7);
		overlayPanel.add(circleLabel8);
		circleLabel0.setVisible(true);
		circleLabel1.setVisible(true);
		circleLabel2.setVisible(true);
		circleLabel3.setVisible(true);
		circleLabel4.setVisible(true);
		circleLabel5.setVisible(true);
		circleLabel6.setVisible(true);
		circleLabel7.setVisible(true);
		circleLabel8.setVisible(true);
		overlayPanel.add(numLabel1);
		overlayPanel.add(numLabel2);
		overlayPanel.add(numLabel3);
		overlayPanel.add(numLabel4);
		overlayPanel.add(numLabel5);
		overlayPanel.add(numLabel6);
		overlayPanel.add(numLabel7);
		overlayPanel.add(numLabel8);
		overlayPanel.add(numLabel9);
		numLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel1.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel1.setForeground(Color.white);
		numLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel2.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel2.setForeground(Color.white);
		numLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel3.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel3.setForeground(Color.white);
		numLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel4.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel4.setForeground(Color.white);
		numLabel5.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel5.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel5.setForeground(Color.white);
		numLabel6.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel6.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel6.setForeground(Color.white);
		numLabel7.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel7.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel7.setForeground(Color.white);
		numLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel8.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel8.setForeground(Color.white);
		numLabel9.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel9.setFont(new Font("Tahoma", Font.BOLD, 20));
		numLabel9.setForeground(Color.white);
		
		//When circles are clicked they delete themselves
		circleLabel0.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel0.setVisible(false);deleteCircle(circleCounter[0], 0);}});
		circleLabel1.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel1.setVisible(false);deleteCircle(circleCounter[1], 1);}});
		circleLabel2.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel2.setVisible(false);deleteCircle(circleCounter[2], 2);}});
		circleLabel3.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel3.setVisible(false);deleteCircle(circleCounter[3], 3);}});
		circleLabel4.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel4.setVisible(false);deleteCircle(circleCounter[4], 4);}});
		circleLabel5.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel5.setVisible(false);deleteCircle(circleCounter[5], 5);}});
		circleLabel6.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel6.setVisible(false);deleteCircle(circleCounter[6], 6);}});
		circleLabel7.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel7.setVisible(false);deleteCircle(circleCounter[7], 7);}});
		circleLabel8.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {circleLabel8.setVisible(false);deleteCircle(circleCounter[8], 8);}}); 
	}
	
	public static void displayCreationCircles(int x, int y)
	{
		//Initializing a counter
		int currentCircle = 9;
		
		//Loops through counter array to find first empty index
		for (int i = 0; i < circleCounter.length; i++)
			if (circleCounter[i] == 0)
			{
				currentCircle = i;
				break;
			}

		if (currentCircle < 9) {
			int size;
			
			switch (resourcePath)
			{
			case "/passCreationCircle20x20.png":
				size = 20;
				break;
			case "/passCreationCircle30x30.png":
				size = 30;
				break;
			case "/passCreationCircle40x40.png":
				size = 40;
				break;
			case "/passCreationCircle50x50.png":
				size = 50;
				break;
			default:
				size = 0;
			}
			
			switch (currentCircle) {
			case 0:
				circleLabel0.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel0.setVisible(true);
				circleLabel0.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 1:
				circleLabel1.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel1.setVisible(true);
				circleLabel1.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 2:
				circleLabel2.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel2.setVisible(true);
				circleLabel2.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 3:
				circleLabel3.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel3.setVisible(true);
				circleLabel3.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 4:
				circleLabel4.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel4.setVisible(true);
				circleLabel4.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 5:
				circleLabel5.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel5.setVisible(true);
				circleLabel5.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 6:
				circleLabel6.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel6.setVisible(true);
				circleLabel6.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 7:
				circleLabel7.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel7.setVisible(true);
				circleLabel7.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 8:
				circleLabel8.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				circleLabel8.setVisible(true);
				circleLabel8.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			default:
				break;
			}
			
			findEmpty();
			
			mainFrame.revalidate();
			mainFrame.repaint();
		}
	}
	
	public static void validateClick(int x, int y, JPanel overlayPanel, JFrame mainFrame)
	{
		Mode mode = MainGUI.getProgramMode();
		if (mode == Mode.CREATE || mode == Mode.ENTER)
		displayCreationCircles(x, y);
	}
	
	
	public static int getLargest()
	{
		int largestNum = 0;
		for (int i = 0; i < circleCounter.length; i++)
			if (largestNum < circleCounter[i])
				largestNum = circleCounter[i];
		
		return largestNum;
	}
	
	public static void findEmpty()
	{
		int largestNum = getLargest();
		
		for (int i = 0; i < circleCounter.length; i++)
			if (circleCounter[i] == 0)
			{
				circleCounter[i] = largestNum + 1;
				break;
			}
	}
	
	public static void deleteCircleCounterIndex(int index)
	{
		for (int i = 0; i < circleCounter.length; i++)
			if (circleCounter[i] != 0 && circleCounter[index] < circleCounter[i])
				circleCounter[i] = circleCounter[i] - 1;
		
		circleCounter[index] = 0;
	}
	
	public static void deleteCircle(int coordIndex, int counterIndex)
	{
		if (MainGUI.getProgramMode() == Mode.CREATE)
		{
			deleteCreationNums();
			
			deleteCircleCounterIndex(counterIndex);
			
			int [][] clickCoords = CreatePassword.getCoords();
			int [] clickSizes = CreatePassword.getSizes();
			
			clickCoords = ApprovePassword.removeCoord(clickCoords, coordIndex - 1);
			clickSizes = ApprovePassword.removeSize(clickSizes, coordIndex - 1);
			
			CreatePassword.setCoords(clickCoords);
			CreatePassword.setSizes(clickSizes);
			
			displayCircleNums();
		}
		
		if (MainGUI.getProgramMode() == Mode.ENTER)
		{
			ApprovePassword.resetCounter();
    		deleteCreationCircles();
		}
		
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static void deleteCreationCircles()
	{
		resetCounter();
		circleLabel0.setVisible(false);
		circleLabel1.setVisible(false);
		circleLabel2.setVisible(false);
		circleLabel3.setVisible(false);
		circleLabel4.setVisible(false);
		circleLabel5.setVisible(false);
		circleLabel6.setVisible(false);
		circleLabel7.setVisible(false);
		circleLabel8.setVisible(false);
		deleteCreationNums();
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static void deleteCreationNums()
	{
		numLabel1.setVisible(false);
		numLabel2.setVisible(false);
		numLabel3.setVisible(false);
		numLabel4.setVisible(false);
		numLabel5.setVisible(false);
		numLabel6.setVisible(false);
		numLabel7.setVisible(false);
		numLabel8.setVisible(false);
		numLabel9.setVisible(false);
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static void setResourcePath(int path)
	{
		switch (path)
		{
		case 1:
			resourcePath = "/passCreationCircle20x20.png";
			break;
		case 2:
			resourcePath = "/passCreationCircle30x30.png";
			break;
		case 3:
			resourcePath = "/passCreationCircle40x40.png";
			break;
		case 4:
			resourcePath = "/passCreationCircle50x50.png";
			break;
		default:
			resourcePath = "/passCreationCircle40x40.png";
		}
	}
	
	public static int getCircleSize()
	{
		int size = 0;
		switch (resourcePath)
		{
		case "/passCreationCircle20x20.png":
			size = 20;
			break;
		case "/passCreationCircle30x30.png":
			size = 30;
			break;
		case "/passCreationCircle40x40.png":
			size = 40;
			break;
		case "/passCreationCircle50x50.png":
			size = 50;
			break;
		default:
			size = 0;
		}
		return size;
	}
	
	public static void resetCounter()
	{
		for (int i = 0; i < circleCounter.length; i++)
			circleCounter[i] = 0;
	}
	
	public static int countCircles()
	{
		int count = 0;
		for (int i = 0; i < circleCounter.length; i++)
			if (circleCounter[i] != 0)
				count++;
		return count;
	}

	public static void displayCircleNums()
	{
		int[][] clickCoords = CreatePassword.getCoords();
		int[] clickSizes = CreatePassword.getSizes();
		
		for (int i = 0; i < circleCounter.length; i++)
		{
			switch (circleCounter[i])
			{
			case 1:
				numLabel1.setVisible(true);
				numLabel1.setBounds(clickCoords[0][0]-(clickSizes[0]/2), clickCoords[0][1]-(clickSizes[0]/2), clickSizes[0], clickSizes[0]);
				break;
			case 2:
				numLabel2.setVisible(true);
				numLabel2.setBounds(clickCoords[1][0]-(clickSizes[1]/2), clickCoords[1][1]-(clickSizes[1]/2), clickSizes[1], clickSizes[1]);
				break;
			case 3:
				numLabel3.setVisible(true);
				numLabel3.setBounds(clickCoords[2][0]-(clickSizes[2]/2), clickCoords[2][1]-(clickSizes[2]/2), clickSizes[2], clickSizes[2]);
				break;
			case 4:
				numLabel4.setVisible(true);
				numLabel4.setBounds(clickCoords[3][0]-(clickSizes[3]/2), clickCoords[3][1]-(clickSizes[3]/2), clickSizes[3], clickSizes[3]);
				break;
			case 5:
				numLabel5.setVisible(true);
				numLabel5.setBounds(clickCoords[4][0]-(clickSizes[4]/2), clickCoords[4][1]-(clickSizes[4]/2), clickSizes[4], clickSizes[4]);
				break;
			case 6:
				numLabel6.setVisible(true);
				numLabel6.setBounds(clickCoords[5][0]-(clickSizes[5]/2), clickCoords[5][1]-(clickSizes[5]/2), clickSizes[5], clickSizes[5]);
				break;
			case 7:
				numLabel7.setVisible(true);
				numLabel7.setBounds(clickCoords[6][0]-(clickSizes[6]/2), clickCoords[6][1]-(clickSizes[6]/2), clickSizes[6], clickSizes[6]);
				break;
			case 8:
				numLabel8.setVisible(true);
				numLabel8.setBounds(clickCoords[7][0]-(clickSizes[7]/2), clickCoords[7][1]-(clickSizes[7]/2), clickSizes[7], clickSizes[7]);
				break;
			case 9:
				numLabel9.setVisible(true);
				numLabel9.setBounds(clickCoords[8][0]-(clickSizes[8]/2), clickCoords[8][1]-(clickSizes[8]/2), clickSizes[8], clickSizes[8]);
				break;
			default:
				break;
			}
		}
	}
}