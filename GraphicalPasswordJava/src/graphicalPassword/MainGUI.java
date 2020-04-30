package graphicalPassword;
//Main Author: Peter Giblin

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainGUI {
private static JFrame mainFrame = new JFrame(); //Global declaration of the main window
private static JPanel overlayPanel = new JPanel();
static Mode programMode;

	//Main which launches the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainGUI();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}
	
	public enum Mode {DEFAULT, CREATE, ENTER};
	
	private void initialize() {
		programMode = Mode.DEFAULT;
		DisplayPassword.main();
		
		Encryption.initiateCipher();
		//Main window
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 1280, 762);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		//Top Menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1264, 21);
		mainFrame.getContentPane().add(menuBar);
		
		JMenu controlsMenu = new JMenu("Controls");
		menuBar.add(controlsMenu);
		
		JMenuItem loadPassword = new JMenuItem("Load Password");
		controlsMenu.add(loadPassword);
		
		JMenuItem editPassword = new JMenuItem("Edit Password");
		controlsMenu.add(editPassword);
		
		controlsMenu.addSeparator();
		
		JMenuItem createPassword = new JMenuItem("Create Password");
		controlsMenu.add(createPassword);
		
		JMenuItem deletePassword = new JMenuItem("Delete Password");
		controlsMenu.add(deletePassword);
		
		JMenu userMenu = new JMenu("User Settings");
		menuBar.add(userMenu);
		
		JMenuItem test = new JMenuItem("Test");
		userMenu.add(test);
		
		//The panel that holds password creation display circles
		overlayPanel.setBorder(null);
		overlayPanel.setBounds(0, 20, 1280, 720);
		mainFrame.getContentPane().add(overlayPanel);
		overlayPanel.setLayout(null);
		overlayPanel.setBackground(new Color(0,0,0,0));
		
		//The panel that houses the image label
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		mainPanel.setBounds(0, 20, 1280, 720);
		mainFrame.getContentPane().add(mainPanel);
		mainPanel.setLayout(new OverlayLayout(mainPanel));
		mainPanel.setBackground(new Color(0,0,0,0));
		
		//The label that the image actually is loaded to
		JLabel pictureLabel = new JLabel("");
		pictureLabel.setIcon(null);
		mainPanel.add(pictureLabel);
		
		//Buttons used for password approval
		JButton enterPassBtn = new JButton("Enter Graphical Password");
		menuBar.add(enterPassBtn);
		enterPassBtn.setVisible(false);
		JButton endEnterModeBtn = new JButton("End Enter Mode");
		menuBar.add(endEnterModeBtn);
		endEnterModeBtn.setVisible(false);
		
		//Event to capture mouse location when the picture is clicked
		pictureLabel.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		        int x=e.getX();
		        int y=e.getY();
		        
		        DisplayPassword.validateClick(x, y, overlayPanel, mainFrame);
		        mainFrame.revalidate();
		        mainFrame.repaint();
		    }
		});
		
		loadPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				ApprovePassword.initiateEnterMode(mainFrame, overlayPanel, mainPanel, pictureLabel, menuBar, controlsMenu, userMenu, enterPassBtn, endEnterModeBtn);
			}
		});
		
		editPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				enterPassBtn.setVisible(false);
				
				String directory = System.getProperty("user.dir") + File.separator + "UserImages";
				JOptionPane.showMessageDialog(mainFrame, "Select the password you wish to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
				CreatePassword.main(directory, mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu);
			}
		});
		
		deletePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				enterPassBtn.setVisible(false);
				
				JOptionPane.showMessageDialog(mainFrame, "Select the password you wish to delete.", "Information", JOptionPane.INFORMATION_MESSAGE);
				LoadPassword.deleteAll(mainFrame);
			}
		});
		
		createPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				enterPassBtn.setVisible(false);
				
				String directory = System.getProperty("user.home") + File.separator + "Pictures";
				JOptionPane.showMessageDialog(mainFrame, "First you need to upload an image that will be used for your graphical Password.", "Information", JOptionPane.INFORMATION_MESSAGE);
				CreatePassword.main(directory, mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu);
			}
		});
		


		
		
		test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				
			}
		});
	}
	
	static void setProgramMode(Mode newMode)
	{
			programMode = newMode;
	}
	
	static Mode getProgramMode()
	{
		return programMode;
	}
	
	static String fileChooser(String directory)
	{
		//Open Java file chooser
		JFileChooser jFileChooser = new JFileChooser(directory);
		int returnVal = jFileChooser.showOpenDialog(jFileChooser);
		
		//Checks if file is valid and set image to image label
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			String filePath = jFileChooser.getSelectedFile().getAbsolutePath(); //String from user file path input
			return filePath;
		}
		
		return null;
	}
	
	static JFrame getMainFrame()
	{
		return mainFrame;
	}
	
	static JPanel getOverlayPanel()
	{
		return overlayPanel;
	}
	
	static JButton removeActionListeners(JButton button)
	{
		for (ActionListener actionListener : button.getActionListeners())
		{
			button.removeActionListener(actionListener);
		}
		return button;
	}
	
	static JLabel removeMouseListeners(JLabel pictureLabel)
	{
		for (MouseListener actionListener : pictureLabel.getMouseListeners())
		{
			pictureLabel.removeMouseListener(actionListener);
		}
		return pictureLabel;
	}
}

