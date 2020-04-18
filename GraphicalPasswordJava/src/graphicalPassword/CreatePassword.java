package graphicalPassword;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreatePassword {
	public static void main(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		
		pictureLabel.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	//int clickCount = 0;
		    	
		    	int x=e.getX();
		        int y=e.getY();
		       /* 
		        if(clickCount == 1)
		        {
		        	int pass1xMIN = x;
		        	int pass1yMIN = y;
		        	System.out.println(pass1xMIN + ',' + pass1yMIN);
		        	CreatePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel);
		        }
		        if(clickCount == 2)
		        {
		        	int pass1xMAX = x;
		        	int pass1yMAX = y;
		        	System.out.println(pass1xMAX + ',' + pass1yMAX);
		        	CreatePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel);
		        }
		        clickCount++;	*/
		        mainFrame.repaint();
		        
		    }
		   
		});
		
	}
	
	public static void validateClick(int x, int y, JPanel overlayPanel)
	{
		//add if statement
		
		DisplayPassword.main(x, y, overlayPanel);
	}
}
