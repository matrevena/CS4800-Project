package graphicalPassword;

import javax.swing.ImageIcon;
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
	public static void main( int x, int y, JPanel overlayPanel)
	{		
		if (circleCounter == 0)
		{
			overlayPanel.remove(circleLabel0);
			overlayPanel.remove(circleLabel1);
			overlayPanel.remove(circleLabel2);
			overlayPanel.remove(circleLabel3);
			overlayPanel.remove(circleLabel4);
			overlayPanel.remove(circleLabel5);
			overlayPanel.remove(circleLabel6);
			overlayPanel.remove(circleLabel7);
			overlayPanel.remove(circleLabel8);
			overlayPanel.revalidate();
			overlayPanel.repaint();
		}
		if (circleCounter < 9) {
			int width = 50;
			int height = 50;
			
			/* old code can be used for different size circles
			int width = circleLabel0.getPreferredSize().width;
			int height = circleLabel0.getPreferredSize().height;
			*/
			
			switch (circleCounter) {
			case 0:
				circleLabel0.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel0);
				
				circleLabel0.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 1:
				circleLabel1.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel1);
				
				circleLabel1.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 2:
				circleLabel2.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel2);
				
				circleLabel2.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 3:
				circleLabel3.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel3);
				
				circleLabel3.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 4:
				circleLabel4.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel4);
				
				circleLabel4.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 5:
				circleLabel5.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel5);
				
				circleLabel5.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 6:
				circleLabel6.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel6);
				
				circleLabel6.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 7:
				circleLabel7.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel7);
				
				circleLabel7.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			case 8:
				circleLabel8.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
				overlayPanel.add(circleLabel8);
				
				circleLabel8.setBounds(x-(width/2), y-(height/2), width, height);
				break;
			}
			
			circleCounter++;
		}
	}
	
	public static void resetCounter()
	{
		circleCounter = 0;
	}
}