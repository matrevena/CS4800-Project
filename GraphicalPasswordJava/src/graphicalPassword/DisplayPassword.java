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
	
	//Array that is used to store which visible shapes corresponds to which click coordinate
	//The user is able to delete shapes in the middle of their combination
	//So this array was made to have each visible shape point to the proper coordinate
	static int[] clickCounter = new int[9];
	
	//Objects which are generated and hidden when the user changes their click combination during creation and approval
	static JLabel shapeLabel0 = new JLabel("");
	static JLabel shapeLabel1 = new JLabel("");
	static JLabel shapeLabel2 = new JLabel("");
	static JLabel shapeLabel3 = new JLabel("");
	static JLabel shapeLabel4 = new JLabel("");
	static JLabel shapeLabel5 = new JLabel("");
	static JLabel shapeLabel6 = new JLabel("");
	static JLabel shapeLabel7 = new JLabel("");
	static JLabel shapeLabel8 = new JLabel("");
	
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
	static String resourcePath = "/passCreationSquare40x40.png";
	public static void main()
	{
		overlayPanel.add(shapeLabel0);
		overlayPanel.add(shapeLabel1);
		overlayPanel.add(shapeLabel2);
		overlayPanel.add(shapeLabel3);
		overlayPanel.add(shapeLabel4);
		overlayPanel.add(shapeLabel5);
		overlayPanel.add(shapeLabel6);
		overlayPanel.add(shapeLabel7);
		overlayPanel.add(shapeLabel8);
		shapeLabel0.setVisible(true);
		shapeLabel1.setVisible(true);
		shapeLabel2.setVisible(true);
		shapeLabel3.setVisible(true);
		shapeLabel4.setVisible(true);
		shapeLabel5.setVisible(true);
		shapeLabel6.setVisible(true);
		shapeLabel7.setVisible(true);
		shapeLabel8.setVisible(true);
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
		
		//When shapes are clicked they delete themselves
		shapeLabel0.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel0.setVisible(false);deleteShape(clickCounter[0], 0);}});
		shapeLabel1.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel1.setVisible(false);deleteShape(clickCounter[1], 1);}});
		shapeLabel2.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel2.setVisible(false);deleteShape(clickCounter[2], 2);}});
		shapeLabel3.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel3.setVisible(false);deleteShape(clickCounter[3], 3);}});
		shapeLabel4.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel4.setVisible(false);deleteShape(clickCounter[4], 4);}});
		shapeLabel5.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel5.setVisible(false);deleteShape(clickCounter[5], 5);}});
		shapeLabel6.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel6.setVisible(false);deleteShape(clickCounter[6], 6);}});
		shapeLabel7.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel7.setVisible(false);deleteShape(clickCounter[7], 7);}});
		shapeLabel8.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {shapeLabel8.setVisible(false);deleteShape(clickCounter[8], 8);}}); 
	}
	
	public static void displayCreationShapes(int x, int y)
	{
		//Initializing a counter
		int currentShape = 9;
		
		//Loops through counter array to find first empty index
		for (int i = 0; i < clickCounter.length; i++)
			if (clickCounter[i] == 0)
			{
				currentShape = i;
				break;
			}

		if (currentShape < 9) {
			int size;
			
			switch (resourcePath)
			{
			case "/passCreationSquare20x20.png":
				size = 20;
				break;
			case "/passCreationSquare30x30.png":
				size = 30;
				break;
			case "/passCreationSquare40x40.png":
				size = 40;
				break;
			case "/passCreationSquare50x50.png":
				size = 50;
				break;
			default:
				size = 0;
			}
			
			switch (currentShape) {
			case 0:
				shapeLabel0.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel0.setVisible(true);
				shapeLabel0.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 1:
				shapeLabel1.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel1.setVisible(true);
				shapeLabel1.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 2:
				shapeLabel2.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel2.setVisible(true);
				shapeLabel2.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 3:
				shapeLabel3.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel3.setVisible(true);
				shapeLabel3.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 4:
				shapeLabel4.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel4.setVisible(true);
				shapeLabel4.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 5:
				shapeLabel5.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel5.setVisible(true);
				shapeLabel5.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 6:
				shapeLabel6.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel6.setVisible(true);
				shapeLabel6.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 7:
				shapeLabel7.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel7.setVisible(true);
				shapeLabel7.setBounds(x-(size/2), y-(size/2), size, size);
				break;
			case 8:
				shapeLabel8.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				shapeLabel8.setVisible(true);
				shapeLabel8.setBounds(x-(size/2), y-(size/2), size, size);
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
		displayCreationShapes(x, y);
	}
	
	
	public static int getLargest()
	{
		int largestNum = 0;
		for (int i = 0; i < clickCounter.length; i++)
			if (largestNum < clickCounter[i])
				largestNum = clickCounter[i];
		
		return largestNum;
	}
	
	public static void findEmpty()
	{
		int largestNum = getLargest();
		
		for (int i = 0; i < clickCounter.length; i++)
			if (clickCounter[i] == 0)
			{
				clickCounter[i] = largestNum + 1;
				break;
			}
	}
	
	public static void deleteClickCounterIndex(int index)
	{
		for (int i = 0; i < clickCounter.length; i++)
			if (clickCounter[i] != 0 && clickCounter[index] < clickCounter[i])
				clickCounter[i] = clickCounter[i] - 1;
		
		clickCounter[index] = 0;
	}
	
	public static void deleteShape(int coordIndex, int counterIndex)
	{
		if (MainGUI.getProgramMode() == Mode.CREATE)
		{
			deleteCreationNums();
			
			deleteClickCounterIndex(counterIndex);
			
			int [][] clickCoords = CreatePassword.getCoords();
			int [] clickSizes = CreatePassword.getSizes();
			
			clickCoords = ApprovePassword.removeCoord(clickCoords, coordIndex - 1);
			clickSizes = ApprovePassword.removeSize(clickSizes, coordIndex - 1);
			
			CreatePassword.setCoords(clickCoords);
			CreatePassword.setSizes(clickSizes);
			
			displayShapeNums();
		}
		
		if (MainGUI.getProgramMode() == Mode.ENTER)
		{
			ApprovePassword.resetCounter();
    		deleteCreationShapes();
		}
		
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static void deleteCreationShapes()
	{
		resetCounter();
		shapeLabel0.setVisible(false);
		shapeLabel1.setVisible(false);
		shapeLabel2.setVisible(false);
		shapeLabel3.setVisible(false);
		shapeLabel4.setVisible(false);
		shapeLabel5.setVisible(false);
		shapeLabel6.setVisible(false);
		shapeLabel7.setVisible(false);
		shapeLabel8.setVisible(false);
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
			resourcePath = "/passCreationSquare20x20.png";
			break;
		case 2:
			resourcePath = "/passCreationSquare30x30.png";
			break;
		case 3:
			resourcePath = "/passCreationSquare40x40.png";
			break;
		case 4:
			resourcePath = "/passCreationSquare50x50.png";
			break;
		default:
			resourcePath = "/passCreationSquare40x40.png";
		}
	}
	
	public static int getShapeSize()
	{
		int size = 0;
		switch (resourcePath)
		{
		case "/passCreationSquare20x20.png":
			size = 20;
			break;
		case "/passCreationSquare30x30.png":
			size = 30;
			break;
		case "/passCreationSquare40x40.png":
			size = 40;
			break;
		case "/passCreationSquare50x50.png":
			size = 50;
			break;
		default:
			size = 0;
		}
		return size;
	}
	
	public static void resetCounter()
	{
		for (int i = 0; i < clickCounter.length; i++)
			clickCounter[i] = 0;
	}
	
	public static int countShapes()
	{
		int count = 0;
		for (int i = 0; i < clickCounter.length; i++)
			if (clickCounter[i] != 0)
				count++;
		return count;
	}

	public static void displayShapeNums()
	{
		int[][] clickCoords = CreatePassword.getCoords();
		int[] clickSizes = CreatePassword.getSizes();
		
		for (int i = 0; i < clickCounter.length; i++)
		{
			switch (clickCounter[i])
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