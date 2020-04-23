package graphicalPassword;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreatePassword {
	static boolean creationMode = false;
	public static void main(String directory, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu) //The three GUI elements that need to be resized depending on the picture
	{	
		String filePath = SavePassword.fileChooser(directory);
		
		initiateEdit(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu);		
	}
	
	public static void initiateEdit(String filePath, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu)
	{
		if (filePath != null)
		{
			creationMode = true;
			
			JButton savePassBtn = new JButton("Save Graphical Password");
			menuBar.add(savePassBtn);
			
			JButton endCreationBtn = new JButton("End Password Creation");
			menuBar.add(endCreationBtn);
			
			controlsMenu.setEnabled(false);
			userMenu.setEnabled(false);
			
			JPanel creationPanelR = new JPanel();
			JPanel creationPanelL = new JPanel();
			overlayPanel.add(creationPanelR);
			overlayPanel.add(creationPanelL);
			
			JTextField textPasswordTfR = new JTextField();
			textPasswordTfR.setText(getRandomPass());
			textPasswordTfR.setEnabled(false);
			JTextField passNameTfR = new JTextField();
			JTextField textPasswordTfL = new JTextField();
			textPasswordTfL.setEnabled(false);
			JTextField passNameTfL = new JTextField();
			
			File loadedPic = new File(filePath);
			passNameTfR.setText(loadedPic.getName().substring(0, loadedPic.getName().length() - 4));
			passNameTfL.setText(loadedPic.getName().substring(0, loadedPic.getName().length() - 4));
			
			LoadPassword.loadPicture(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel);
			
			userCustomization(mainFrame, mainPanel, overlayPanel, pictureLabel, creationPanelR, creationPanelL, passNameTfR, textPasswordTfR, passNameTfL, textPasswordTfL);
			
			int[][] clickCoords = new int[9][2];
			
			MouseAdapter captureClicks = new MouseAdapter() {
				public void mouseClicked(MouseEvent e)
			    {
			    	int x=e.getX();
			        int y=e.getY();
			        
			        System.out.print("Read: ");//////////////////////////////////////////////////////////////////////////////////////////////////
			        for (int i = 0; i<clickCoords.length; i++)
			        {
			        	System.out.print("[" + i + "]");//////////////////////////////////////////////////////////////////////////////////////////////////
			        	if (clickCoords [i][0] == 0)
			        	{
			        		clickCoords[i][0] = x;
			        		clickCoords[i][1] = y;
			        		System.out.println("");
			        		System.out.println("Overwrote: " + "[" + i + " = " + clickCoords[i][0] + ", " + clickCoords[i][1] + "]");//////////////////////////////////////////////////////////////////////////////////////////////////
			        		break;
			        	}
			        }
			    }
			};
			
			pictureLabel.addMouseListener(captureClicks);
			
			endCreationBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					endCreation(mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar,
							controlsMenu, userMenu, savePassBtn, endCreationBtn, creationPanelR, creationPanelL, captureClicks);
				}
			});
			
			
			
			savePassBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					int numOfCoords = ApprovePassword.countArrayCoords(clickCoords);
					if (numOfCoords >= 4)
					{
						if (creationPanelR.isVisible())
						{
							if (passNameTfR.getText().length() >= 4 && passNameTfR.getText().length() <= 20)
							{
								String passName = passNameTfR.getText();
								
								String textPass = textPasswordTfR.getText();
								
								SavePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel, passName, textPass, filePath, clickCoords);
								
								endCreation(mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar,
										controlsMenu, userMenu, savePassBtn, endCreationBtn, creationPanelR, creationPanelL, captureClicks);
							}
							else
							{
								JOptionPane.showMessageDialog(mainFrame, "Please enter a name for your graphical password that is 4 to 20 letter and numbers long.", "Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
							}
						}
						else if (creationPanelL.isVisible())
						{
							if (passNameTfL.getText().length() >= 4 && passNameTfL.getText().length() <= 20)
							{
								String passName = passNameTfL.getText();
								
								String textPass = textPasswordTfL.getText();
								
								SavePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel, passName, textPass, filePath, clickCoords);
								
								endCreation(mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar,
										controlsMenu, userMenu, savePassBtn, endCreationBtn, creationPanelR, creationPanelL, captureClicks);
							}
							else
							{
								JOptionPane.showMessageDialog(mainFrame, "Please enter a name for your graphical password that is 4 to 20 letter and numbers long.", "Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					else
						JOptionPane.showMessageDialog(mainFrame, "You must select 4 to 9 click location.", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}
	
	public static void userCustomization(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JPanel creationPanelR, JPanel creationPanelL, JTextField passNameTfR, JTextField textPasswordTfR, JTextField passNameTfL, JTextField textPasswordTfL)
	{	
		creationPanelR.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		creationPanelL.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		
		creationPanelR.setVisible(true);
		creationPanelL.setVisible(false);
		
		int width = 420;
		int height = 100;
		int x = mainFrame.getWidth()-width;
		int y = 1;
		creationPanelR.setBounds(x, y, width, height);
		creationPanelL.setBounds(-6, y, width, height);
		
		double sideBtnSize = .10;
		double sizeRatio = ((.99 - sideBtnSize)/4);
		
		//Right Options Menu Elements
		JButton swapSideBtnR = new JButton("<");
		swapSideBtnR.setBorder(null);
		swapSideBtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sideBtnSize), (creationPanelR.getHeight())));
		creationPanelR.add(swapSideBtnR);
		
		JPanel nestedPanelR1 = new JPanel();
		nestedPanelR1.setLayout(new BoxLayout(nestedPanelR1, BoxLayout.Y_AXIS));
		creationPanelR.add(nestedPanelR1);
		
		JPanel nestedPanelR2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelR1.add(nestedPanelR2);
		
		JPanel nestedPanelR3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelR1.add(nestedPanelR3);
		
		JButton sizeOption1BtnR = new JButton();
		sizeOption1BtnR.setBorder(null);
		sizeOption1BtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		sizeOption1BtnR.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle20x20.png")));
		nestedPanelR2.add(sizeOption1BtnR);
		
		JButton sizeOption2BtnR = new JButton();
		sizeOption2BtnR.setBorder(null);
		sizeOption2BtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		sizeOption2BtnR.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle30x30.png")));
		nestedPanelR2.add(sizeOption2BtnR);
		
		JButton sizeOption3BtnR = new JButton();
		sizeOption3BtnR.setBorder(null);
		sizeOption3BtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		sizeOption3BtnR.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle40x40.png")));
		nestedPanelR2.add(sizeOption3BtnR);
		
		JButton resetClicksBtnR = new JButton();
		resetClicksBtnR.setBorder(null);
		resetClicksBtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		nestedPanelR2.add(resetClicksBtnR);
		
		JButton randomPassBtnR = new JButton();
		randomPassBtnR.setBorder(null);
		randomPassBtnR.setText("X");
		randomPassBtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*(sizeRatio*.75)), (int)(creationPanelR.getHeight()*.5)));
		nestedPanelR3.add(randomPassBtnR);
		
		JPanel nestedPanelR4 = new JPanel();
		nestedPanelR4.setLayout(new BoxLayout(nestedPanelR4, BoxLayout.Y_AXIS));
		nestedPanelR3.add(nestedPanelR4);
		
		JPanel nestedPanelR5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelR4.add(nestedPanelR5);
		
		JPanel nestedPanelR6 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelR4.add(nestedPanelR6);
		
		JLabel userPassLblR = new JLabel(" Custom Text Password:         ");
		userPassLblR.setFont(new Font("Arial", Font.BOLD, 11));
		nestedPanelR5.add(userPassLblR);
		
		textPasswordTfR.setColumns(12);
		nestedPanelR5.add(textPasswordTfR);
		
		JLabel passNameLblR = new JLabel(" Graphical Password Name:     ");
		passNameLblR.setFont(new Font("Arial", Font.BOLD, 11));
		nestedPanelR6.add(passNameLblR);
		
		passNameTfR.setColumns(12);
		nestedPanelR6.add(passNameTfR);
		
		//Left Options Menu Elements
		JPanel nestedPanelL1 = new JPanel();
		nestedPanelL1.setLayout(new BoxLayout(nestedPanelL1, BoxLayout.Y_AXIS));
		creationPanelL.add(nestedPanelL1);
		
		JPanel nestedPanelL2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelL1.add(nestedPanelL2);
		
		JPanel nestedPanelL3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelL1.add(nestedPanelL3);
		
		JButton sizeOption1BtnL = new JButton();
		sizeOption1BtnL.setBorder(null);
		sizeOption1BtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sizeRatio), (int)(creationPanelL.getHeight()*.5)));
		sizeOption1BtnL.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle20x20.png")));
		nestedPanelL2.add(sizeOption1BtnL);
		
		JButton sizeOption2BtnL = new JButton();
		sizeOption2BtnL.setBorder(null);
		sizeOption2BtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sizeRatio), (int)(creationPanelL.getHeight()*.5)));
		sizeOption2BtnL.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle30x30.png")));
		nestedPanelL2.add(sizeOption2BtnL);
		
		JButton sizeOption3BtnL = new JButton();
		sizeOption3BtnL.setBorder(null);
		sizeOption3BtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sizeRatio), (int)(creationPanelL.getHeight()*.5)));
		sizeOption3BtnL.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle40x40.png")));
		nestedPanelL2.add(sizeOption3BtnL);
		
		JButton resetClicksBtnL = new JButton();
		resetClicksBtnL.setBorder(null);
		resetClicksBtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sizeRatio), (int)(creationPanelL.getHeight()*.5)));
		nestedPanelL2.add(resetClicksBtnL);
		
		JButton randomPassBtnL = new JButton();
		randomPassBtnL.setBorder(null);
		randomPassBtnL.setText("X");
		randomPassBtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*(sizeRatio*.75)), (int)(creationPanelL.getHeight()*.5)));
		nestedPanelL3.add(randomPassBtnL);
		
		JPanel nestedPanelL4 = new JPanel();
		nestedPanelL4.setLayout(new BoxLayout(nestedPanelL4, BoxLayout.Y_AXIS));
		nestedPanelL3.add(nestedPanelL4);
		
		JPanel nestedPanelL5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelL4.add(nestedPanelL5);
		
		JPanel nestedPanelL6 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		nestedPanelL4.add(nestedPanelL6);
		
		JLabel userPassLblL = new JLabel(" Custom Text Password:         ");
		userPassLblL.setFont(new Font("Arial", Font.BOLD, 11));
		nestedPanelL5.add(userPassLblL);
		
		textPasswordTfL.setColumns(11);
		nestedPanelL5.add(textPasswordTfL);	
		
		JLabel passNameLblL = new JLabel(" Graphical Password Name:     ");
		passNameLblL.setFont(new Font("Arial", Font.BOLD, 11));
		nestedPanelL6.add(passNameLblL);
		
		passNameTfL.setColumns(11);
		nestedPanelL6.add(passNameTfL);
		
		JButton swapSideBtnL = new JButton(">");
		swapSideBtnL.setBorder(null);
		swapSideBtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sideBtnSize), (creationPanelL.getHeight())));
		creationPanelL.add(swapSideBtnL);
		
		creationPanelL.setVisible(false);
		
		mainFrame.revalidate();
		mainFrame.repaint();
		
		ActionListener size1BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayPassword.setResourcePath(1);
			}
		};
		
		ActionListener size2BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayPassword.setResourcePath(2);
			}
		};
		
		ActionListener size3BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayPassword.setResourcePath(3);
			}
		};
		
		sizeOption1BtnR.addActionListener(size1BtnEvent);
		sizeOption1BtnL.addActionListener(size1BtnEvent);
		sizeOption2BtnR.addActionListener(size2BtnEvent);
		sizeOption2BtnL.addActionListener(size2BtnEvent);
		sizeOption3BtnR.addActionListener(size3BtnEvent);
		sizeOption3BtnL.addActionListener(size3BtnEvent);
		
		swapSideBtnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				creationPanelL.setVisible(true);
				creationPanelR.setVisible(false);
				
				passNameTfL.setText(passNameTfR.getText());
				textPasswordTfL.setText(textPasswordTfR.getText());
				
				mainFrame.revalidate();
				mainFrame.repaint();
			}
		});
		
		swapSideBtnL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				creationPanelR.setVisible(true);
				creationPanelL.setVisible(false);
				
				passNameTfR.setText(passNameTfL.getText());
				textPasswordTfR.setText(textPasswordTfL.getText());
				
				mainFrame.revalidate();
				mainFrame.repaint();
			}
		});
		
		ActionListener randomPassE = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textPasswordTfR.isEnabled() == false)
				{
					textPasswordTfR.setText("");
					textPasswordTfL.setText("");
					textPasswordTfR.setEnabled(true);
					textPasswordTfL.setEnabled(true);
					randomPassBtnR.setText("-");
					randomPassBtnL.setText("-");
					mainFrame.revalidate();
					mainFrame.repaint();
				}
				else if (textPasswordTfR.isEnabled() == true)
				{
					textPasswordTfR.setText(CreatePassword.getRandomPass());
					textPasswordTfL.setText(textPasswordTfR.getText());
					textPasswordTfR.setEnabled(false);
					textPasswordTfL.setEnabled(false);
					randomPassBtnR.setText("X");
					randomPassBtnL.setText("X");
					mainFrame.revalidate();
					mainFrame.repaint();
				}

			}
		};
		
		randomPassBtnR.addActionListener(randomPassE);
		randomPassBtnL.addActionListener(randomPassE);
	}
	
	public static void validateClick(int x, int y, JPanel overlayPanel, JFrame mainFrame)
	{
		if (creationMode == true)
		DisplayPassword.displayCreationCircles(x, y, overlayPanel, mainFrame);
		
	}
	public static void endCreation(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar,
			JMenu controlsMenu, JMenu userMenu, JButton savePassBtn, JButton endCreationBtn, JPanel creationPanelR, JPanel creationPanelL, MouseAdapter captureClicks)
	{
		creationMode = false;
		menuBar.remove(savePassBtn);
		menuBar.remove(endCreationBtn);
		
		overlayPanel.remove(creationPanelR);
		overlayPanel.remove(creationPanelL);
		
		controlsMenu.setEnabled(true);
		userMenu.setEnabled(true);
		
		pictureLabel.setIcon(null);
		DisplayPassword.setResourcePath(3);
		
		DisplayPassword.deleteCreationCircles(overlayPanel, mainFrame);
		pictureLabel.removeMouseListener(captureClicks);
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static String getRandomPass()
	{
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?\"))";
		String RandomPW = "";
		int length = 12 +  (int)(Math.random()*(4));         
		
		
		Random rand = new Random();
		char[] password = new char[length];
		
		for (int i = 0; i < length ; i++) {
			 
			password[i] =  characters.charAt(rand.nextInt(characters.length())); 
			RandomPW += password[i];    
		} 
		
		return RandomPW;
	}
}
