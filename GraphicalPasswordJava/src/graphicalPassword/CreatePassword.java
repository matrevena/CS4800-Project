package graphicalPassword;
//Main Author: Peter Giblin
//Darren Suon wrote getRandomPass()

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

import graphicalPassword.MainGUI.Mode;

public class CreatePassword {
	//Array saves the x and y coords of the user's chosen click locations click
	static int[][] clickCoords = new int[9][2];
	//Array saves the size of each of user's chosen click circles click
	static int[] clickSizes = new int[9];
	
	public static void main(String directory, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu)
	{	
		String filePath = MainGUI.fileChooser(directory);
		
		initiateEdit(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu);		
	}
	
	public static void initiateEdit(String filePath, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu)
	{
		if (filePath != null)
		{
			MainGUI.setProgramMode(Mode.CREATE);
			
			JButton savePassBtn = new JButton("Save Graphical Password");
			savePassBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
			menuBar.add(savePassBtn);
			
			JButton endCreationBtn = new JButton("End Password Creation");
			endCreationBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
			menuBar.add(endCreationBtn);
			
			//Disables some tool bar options during creation
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
			//Sets the name of the uploaded image as the graphical password name (can be changed by the user)
			passNameTfR.setText(loadedPic.getName().substring(0, loadedPic.getName().length() - 4));
			passNameTfL.setText(loadedPic.getName().substring(0, loadedPic.getName().length() - 4));
			
			LoadPassword.loadPicture(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel);
			
			//The side menus which have options for the user during password creation
			customizationMenu(mainFrame, mainPanel, overlayPanel, pictureLabel, creationPanelR, creationPanelL, passNameTfR, textPasswordTfR, passNameTfL, textPasswordTfL);
			
			MouseAdapter captureClicks = new MouseAdapter() {
				public void mouseClicked(MouseEvent e)
			    {
			    	int x=e.getX();
			        int y=e.getY();
			        
			        //Loops through the clickCoords array checking for empty spots
			        for (int i = 0; i<clickCoords.length; i++)
			        {
			        	if (clickCoords [i][0] == 0)
			        	{
			        		//Saves the size of each click
			        		clickSizes[i] = DisplayPassword.getCircleSize();
			        		
			        		clickCoords[i][0] = x;
			        		clickCoords[i][1] = y;
			        		
			        		DisplayPassword.displayCircleNums();
			        		
			        		break;
			        	}
			        }
			    }
			};
			
			//Done to prevent duplicate mouse listeners
			pictureLabel.removeMouseListener(captureClicks);
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
					
					//The user must select at least 4 click areas for their graphical password combo
					if (numOfCoords >= 4)
					{
						//Check which creation options menu is currently visible
						if (creationPanelR.isVisible())
						{
							//Only allows password names of 4 to 20 and text passwords of 8 to 20
							String passName = passNameTfR.getText();
							if (passName.length() >= 4 && passName.length() <= 20)
							{
								String textPass = textPasswordTfR.getText();
								if (textPass.length() >= 8 && textPass.length() <= 20)
								{
									SavePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel, passName, textPass, filePath, clickCoords, clickSizes);
									
									endCreation(mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar,
											controlsMenu, userMenu, savePassBtn, endCreationBtn, creationPanelR, creationPanelL, captureClicks);
								}
								else
								{
									JOptionPane.showMessageDialog(mainFrame, "Your text password must be 8 to 20 characters long.",
											"Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
								}
							}
							else
							{
								JOptionPane.showMessageDialog(mainFrame, "Please enter a name for your graphical password that is 4 to 20 letter and numbers long.",
										"Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
							}
						}
						else if (creationPanelL.isVisible())
						{
							//Only allows password names of 4 to 20 and text passwords of 8 to 20
							String passName = passNameTfL.getText();
							if (passName.length() >= 4 && passName.length() <= 20)
							{
								String textPass = textPasswordTfL.getText();
								if (textPass.length() >= 8 && textPass.length() <= 20)
								{
									SavePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel, passName, textPass, filePath, clickCoords, clickSizes);
									
									endCreation(mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar,
											controlsMenu, userMenu, savePassBtn, endCreationBtn, creationPanelR, creationPanelL, captureClicks);
								}
								else
								{
									JOptionPane.showMessageDialog(mainFrame, "Your text password must be 8 to 20 characters long.",
											"Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
								}
							}
							else
							{
								JOptionPane.showMessageDialog(mainFrame, "Please enter a name for your graphical password that is 4 to 20 letter and numbers long.",
										"Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					else
						JOptionPane.showMessageDialog(mainFrame, "You must select 4 to 9 click location.", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}
	
	public static void customizationMenu(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JPanel creationPanelR,
			JPanel creationPanelL, JTextField passNameTfR, JTextField textPasswordTfR, JTextField passNameTfL, JTextField textPasswordTfL)
	{	
		//The side menus which have options for the user during password creation
		int menuWidth = 420;
		int menuHeight = 120;
		int x = mainFrame.getWidth() - menuWidth;
		int y = 0;
		creationPanelR.setBounds(x, y, menuWidth, menuHeight);
		creationPanelL.setBounds(0, y, menuWidth, menuHeight);
		
		creationPanelR.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		creationPanelL.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		
		creationPanelR.setVisible(true);
		creationPanelL.setVisible(false);
		
		double sideBtnSize = .10;
		double sizeRatio = ((1 - sideBtnSize)/4);
		
		//Right Options Menu Elements
		JButton swapSideBtnR = new JButton("<");
		swapSideBtnR.setBorder(null);
		swapSideBtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sideBtnSize), (creationPanelR.getHeight())));
		creationPanelR.add(swapSideBtnR);
		
		//Nested panel are used for formatting
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
		
		JButton sizeOption4BtnR = new JButton();
		sizeOption4BtnR.setBorder(null);
		sizeOption4BtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		sizeOption4BtnR.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
		nestedPanelR2.add(sizeOption4BtnR);
		
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
		//Nested panel are used for formatting
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
		
		JButton sizeOption4BtnL = new JButton();
		sizeOption4BtnL.setBorder(null);
		sizeOption4BtnL.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		sizeOption4BtnL.setIcon(new ImageIcon(MainGUI.class.getResource("/passCreationCircle50x50.png")));
		nestedPanelL2.add(sizeOption4BtnL);
		
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
		
		//Default button is show as selected
		sizeOption3BtnR.setEnabled(false);
		sizeOption3BtnL.setEnabled(false);
		
		creationPanelL.setVisible(false);
		
		mainFrame.revalidate();
		mainFrame.repaint();
		
		ActionListener size1BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				sizeOption1BtnR.setEnabled(false);
				sizeOption1BtnL.setEnabled(false);
				sizeOption2BtnR.setEnabled(true);
				sizeOption2BtnL.setEnabled(true);
				sizeOption3BtnR.setEnabled(true);
				sizeOption3BtnL.setEnabled(true);
				sizeOption4BtnR.setEnabled(true);
				sizeOption4BtnL.setEnabled(true);
				DisplayPassword.setResourcePath(1);
			}
		};
		
		ActionListener size2BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				sizeOption1BtnR.setEnabled(true);
				sizeOption1BtnL.setEnabled(true);
				sizeOption2BtnR.setEnabled(false);
				sizeOption2BtnL.setEnabled(false);
				sizeOption3BtnR.setEnabled(true);
				sizeOption3BtnL.setEnabled(true);
				sizeOption4BtnR.setEnabled(true);
				sizeOption4BtnL.setEnabled(true);
				DisplayPassword.setResourcePath(2);
			}
		};
		
		ActionListener size3BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				sizeOption1BtnR.setEnabled(true);
				sizeOption1BtnL.setEnabled(true);
				sizeOption2BtnR.setEnabled(true);
				sizeOption2BtnL.setEnabled(true);
				sizeOption3BtnR.setEnabled(false);
				sizeOption3BtnL.setEnabled(false);
				sizeOption4BtnR.setEnabled(true);
				sizeOption4BtnL.setEnabled(true);
				DisplayPassword.setResourcePath(3);
			}
		};
		
		ActionListener size4BtnEvent = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				sizeOption1BtnR.setEnabled(true);
				sizeOption1BtnL.setEnabled(true);
				sizeOption2BtnR.setEnabled(true);
				sizeOption2BtnL.setEnabled(true);
				sizeOption3BtnR.setEnabled(true);
				sizeOption3BtnL.setEnabled(true);
				sizeOption4BtnR.setEnabled(false);
				sizeOption4BtnL.setEnabled(false);
				DisplayPassword.setResourcePath(4);
			}
		};
		
		sizeOption1BtnR.addActionListener(size1BtnEvent);
		sizeOption1BtnL.addActionListener(size1BtnEvent);
		sizeOption2BtnR.addActionListener(size2BtnEvent);
		sizeOption2BtnL.addActionListener(size2BtnEvent);
		sizeOption3BtnR.addActionListener(size3BtnEvent);
		sizeOption3BtnL.addActionListener(size3BtnEvent);
		sizeOption4BtnR.addActionListener(size4BtnEvent);
		sizeOption4BtnL.addActionListener(size4BtnEvent);
		
		//Hides the right side and displays the left side
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
		
		//Hides the left side and displays the right side
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
		
		//If the random password button is pressed for the first time it clears the random pass and lets the user enter their own text pass
		//On the second click it regenerates a new random pass and disables the text box again
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
	
	public static int[][] getCoords()
	{
		return clickCoords;
	}
	
	public static void setCoords(int[][] newCoords)
	{
		clickCoords = newCoords;
	}
	
	public static int[] getSizes()
	{
		return clickSizes;
	}
	
	public static void setSizes(int[] newCoords)
	{
		clickSizes = newCoords;
	}
	
	public static void endCreation(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar,
			JMenu controlsMenu, JMenu userMenu, JButton savePassBtn, JButton endCreationBtn, JPanel creationPanelR, JPanel creationPanelL, MouseAdapter captureClicks)
	{
		MainGUI.setProgramMode(Mode.DEFAULT);
		
		clickCoords = ApprovePassword.reset2DArray(clickCoords);
		clickSizes = ApprovePassword.reset1DArray(clickSizes);
		
		menuBar.remove(savePassBtn);
		menuBar.remove(endCreationBtn);
		
		overlayPanel.remove(creationPanelR);
		overlayPanel.remove(creationPanelL);
		
		controlsMenu.setEnabled(true);
		userMenu.setEnabled(true);
		
		pictureLabel.setIcon(null);
		DisplayPassword.setResourcePath(3);
		
		DisplayPassword.deleteCreationCircles();
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
