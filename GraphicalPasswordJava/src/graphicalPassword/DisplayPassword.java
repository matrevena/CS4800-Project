package graphicalPassword;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayPassword {
	static int circleCounter = 0;
	static JLabel circleLabel0 = new JLabel("");
	static JLabel circleLabel1 = new JLabel("");
	static JLabel circleLabel2 = new JLabel("");
	static JLabel circleLabel3 = new JLabel("");
	static JLabel circleLabel4 = new JLabel("");
	static JLabel circleLabel5 = new JLabel("");
	static JLabel circleLabel6 = new JLabel("");
	static JLabel circleLabel7 = new JLabel("");
	static JLabel circleLabel8 = new JLabel("");
	static String resourcePath = "/passCreationCircle40x40.png";
	public static void main()
	{		

	}
	
	public static void displayCreationCircles(int x, int y, JPanel overlayPanel, JFrame mainFrame)
	{
		circleLabel0.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel0);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel1.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel1);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel2.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel2);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel3.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel3);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel4.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel4);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel5.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel5);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel6.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel6);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel7.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel7);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});
		circleLabel8.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {overlayPanel.remove(circleLabel8);circleCounter--;mainFrame.revalidate();mainFrame.repaint();}});


		if (circleCounter == 0)
		{
			deleteCreationCircles(overlayPanel, mainFrame);
		}
		if (circleCounter < 9) {
			int width;
			int height;
			
			switch (resourcePath)
			{
			case "/passCreationCircle20x20.png":
				width = 20;
				height = 20;
				break;
			case "/passCreationCircle30x30.png":
				width = 30;
				height = 30;
				break;
			case "/passCreationCircle40x40.png":
				width = 40;
				height = 40;
				break;
			default:
				width = 40;
				height = 40;
			}
			
			switch (circleCounter) {
			case 0:
				circleLabel0.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel0);
				
				circleLabel0.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 1:
				circleLabel1.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel1);
				
				circleLabel1.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 2:
				circleLabel2.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel2);
				
				circleLabel2.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 3:
				circleLabel3.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel3);
				
				circleLabel3.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 4:
				circleLabel4.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel4);
				
				circleLabel4.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 5:
				circleLabel5.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel5);
				
				circleLabel5.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 6:
				circleLabel6.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel6);
				
				circleLabel6.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 7:
				circleLabel7.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel7);
				
				circleLabel7.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 8:
				circleLabel8.setIcon(new ImageIcon(MainGUI.class.getResource(resourcePath)));
				overlayPanel.add(circleLabel8);
				
				circleLabel8.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			default:
				break;
			}
			mainFrame.revalidate();
			mainFrame.repaint();
			if (circleCounter >= 9)
				resetCounter();
			
			circleCounter++;
		}
	}
	
	public static void deleteCreationCircles(JPanel overlayPanel, JFrame mainFrame)
	{
		resetCounter();
		overlayPanel.remove(circleLabel0);
		overlayPanel.remove(circleLabel1);
		overlayPanel.remove(circleLabel2);
		overlayPanel.remove(circleLabel3);
		overlayPanel.remove(circleLabel4);
		overlayPanel.remove(circleLabel5);
		overlayPanel.remove(circleLabel6);
		overlayPanel.remove(circleLabel7);
		overlayPanel.remove(circleLabel8);
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
		default:
			resourcePath = "/passCreationCircle40x40.png";
		}
	}
	
	public static void resetCounter()
	{
		circleCounter = 0;
	}
}