package graphicalPassword;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreatePassword {
	public static void main(JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel, JMenuBar menuBar, JMenu controlsMenu, JMenu userMenu) //The three GUI elements that need to be resized depending on the picture
	{	
		String filePath = fileChooser();
		
		if (filePath != null)
		{
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
			textPasswordTfR.setEnabled(false);
			JTextField passNameTfR = new JTextField();
			JTextField textPasswordTfL = new JTextField();
			textPasswordTfL.setEnabled(false);
			JTextField passNameTfL = new JTextField();
			
			scaleImage(filePath, mainFrame, mainPanel, overlayPanel, pictureLabel);
			
			userCustomization(mainFrame, mainPanel, overlayPanel, pictureLabel, creationPanelR, creationPanelL, passNameTfR, textPasswordTfR, passNameTfL, textPasswordTfL);
			
			endCreationBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					menuBar.remove(savePassBtn);
					menuBar.remove(endCreationBtn);
					overlayPanel.remove(creationPanelR);
					overlayPanel.remove(creationPanelL);
					controlsMenu.setEnabled(true);
					userMenu.setEnabled(true);
					mainFrame.revalidate();
					mainFrame.repaint();
				}
			});
			
			savePassBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					if (creationPanelR.isVisible())
					{
						if (passNameTfR.getText().length() >= 4 && passNameTfR.getText().length() <= 20)
						{
							//Code for saving custom text password
							menuBar.remove(savePassBtn);
							menuBar.remove(endCreationBtn);
							overlayPanel.remove(creationPanelR);
							overlayPanel.remove(creationPanelL);
							controlsMenu.setEnabled(true);
							userMenu.setEnabled(true);
							mainFrame.revalidate();
							mainFrame.repaint();
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
							//Code for saving custom text password
							menuBar.remove(savePassBtn);
							menuBar.remove(endCreationBtn);
							overlayPanel.remove(creationPanelR);
							overlayPanel.remove(creationPanelL);
							controlsMenu.setEnabled(true);
							userMenu.setEnabled(true);
							mainFrame.revalidate();
							mainFrame.repaint();
						}
						else
						{
							JOptionPane.showMessageDialog(mainFrame, "Please enter a name for your graphical password that is 4 to 20 letter and numbers long.", "Graphical Password Name Required", JOptionPane.ERROR_MESSAGE);
						}
					}
					//TODO save pass file
				}
			});
		}

		//save graphical password
		
	}
	
	private static String fileChooser()
	{
		//Open Java file chooser
		final JFileChooser jFileChooser = new JFileChooser();
		int returnVal = jFileChooser.showOpenDialog(jFileChooser);
		
		//Checks if file is valid and set image to image label
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			String filePath = jFileChooser.getSelectedFile().getAbsolutePath(); //String from user file path input
			return filePath;
		}
		
		return null;
	}

	public static void scaleImage(String filePath, JFrame mainFrame, JPanel mainPanel, JPanel overlayPanel, JLabel pictureLabel)
	{
		BufferedImage img = null; //Sets image based on filePath catches exceptions
		try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TODO Add aspect ratio option
		//Scales the size of the uploaded image
		Image tmp = img.getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
		img = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
		
		pictureLabel.setIcon(new ImageIcon(img));
		
		DisplayPassword.resetCounter();
		
		/* Properties that need to adjusted depending on the image size, will also need to implement a cap on size and make images scale down
		mainFrame.setBounds(100, 100, 1298, 780);
		mainPanel.setBounds(0, 20, 1280, 720);
		pictureLabel.setBounds(0, 20, 1280, 720);
		*/
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
		nestedPanelR2.add(sizeOption1BtnR);
		
		JButton sizeOption2BtnR = new JButton();
		sizeOption2BtnR.setBorder(null);
		sizeOption2BtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
		nestedPanelR2.add(sizeOption2BtnR);
		
		JButton sizeOption3BtnR = new JButton();
		sizeOption3BtnR.setBorder(null);
		sizeOption3BtnR.setPreferredSize(new Dimension((int)(creationPanelR.getWidth()*sizeRatio), (int)(creationPanelR.getHeight()*.5)));
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
		nestedPanelL2.add(sizeOption1BtnL);
		
		JButton sizeOption2BtnL = new JButton();
		sizeOption2BtnL.setBorder(null);
		sizeOption2BtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sizeRatio), (int)(creationPanelL.getHeight()*.5)));
		nestedPanelL2.add(sizeOption2BtnL);
		
		JButton sizeOption3BtnL = new JButton();
		sizeOption3BtnL.setBorder(null);
		sizeOption3BtnL.setPreferredSize(new Dimension((int)(creationPanelL.getWidth()*sizeRatio), (int)(creationPanelL.getHeight()*.5)));
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
					textPasswordTfR.setEnabled(true);
					textPasswordTfL.setEnabled(true);
					randomPassBtnR.setText("-");
					randomPassBtnL.setText("-");
					mainFrame.revalidate();
					mainFrame.repaint();
				}
				else if (textPasswordTfR.isEnabled() == true)
				{
					textPasswordTfR.setEnabled(false);
					textPasswordTfL.setEnabled(false);
					textPasswordTfR.setText("");
					textPasswordTfL.setText("");
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
	
	public static void validateClick(int x, int y, JPanel overlayPanel)
	{
		//add if statement
		
		DisplayPassword.main(x, y, overlayPanel);
	}

	public static void createPassword() {
		// TODO Auto-generated method stub
		
	}
}
