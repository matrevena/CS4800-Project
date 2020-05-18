package graphicalPassword;
//Main Author: Peter Giblin
//Matthaw Trevena added all of the userName functionality mainly in initialize()
//Tested / Debugged by: Peter Giblin

/*
The MainGUI class is the first to be run during the program.
It serves as the central user interface where the user can load any of the other functionalities.
This class also loads in the config file and contains the option menu used to modify the config.txt file.
 */

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainGUI {
//Enum to track which mode the program is in
static Mode programMode;

//---Object Properties---
static int menuBarSize = 30;

//Where the program starts from top left of the screen
static int startingX = 100;
static int startingY = 100;

static int optionsWidth = 400;
static int optionsHeight = 500;
static String[] resolutionOptions = {"1920 x 1080","1600 x 900","1280 x 720","960 x 540"};
static int[] windowRes = new int[2];
static int taskBarSize = 20;

//Program color scheme
static Color lightPrimary = new Color(240,240,240);
static Color lightSecondary = new Color(220,220,220);
static Color lightText = new Color (240,240,240);
static Color darkPrimary = new Color(50,50,50);
static Color darkSecondary = new Color(35,35,35);
static Color darkText = new Color (0,0,0);

//Global GUI elements
static JFrame mainFrame = new JFrame(); //Global declaration of the main window
private static JPanel overlayPanel = new JPanel();
static JFrame optionsFrame = new JFrame();
static JComboBox<?> resolutionBox = new JComboBox<Object>(resolutionOptions);

//Global Var
static boolean darkMode = false;

//global variable for an individual user's userName
static String userName = "User";
//-----------------------


	//Main which launches the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainGUI();
					mainFrame.setTitle("Graphical Password - " + userName);
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Creates the application.
	public MainGUI() {
		initialize();
	}
	
	public enum Mode {DEFAULT, CREATE, ENTER};
	
	private void initialize() {
		programMode = Mode.DEFAULT;
		
		//Detect and read the config file
		File config = new File(System.getProperty("user.dir") + File.separator + "config.txt");

		if (config.exists())
			startupLoadConfig();
		else
		{
			windowRes[0] = 1280;
			windowRes[1] = 720;
		}
		
		//Initializes the DisplayPassword class
		DisplayPassword.main();
		
		//Initializes the encryption cipher
		Encryption.initiateCipher();
		
		//Main window
		mainFrame.setResizable(false);
		mainFrame.setBounds(startingX, startingY, windowRes[0], windowRes[1]);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		//Top Menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, windowRes[0], menuBarSize);
		mainFrame.getContentPane().add(menuBar);
		menuBar.setBackground(getSecondaryColor());
		
		//1st MenuBar Category
		JMenu controlsMenu = new JMenu("Controls");
		controlsMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(controlsMenu);
		controlsMenu.setForeground(getTextColor());
		
		JMenuItem loadPassword = new JMenuItem("Load Password");
		loadPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
		controlsMenu.add(loadPassword);
		
		JMenuItem editPassword = new JMenuItem("Edit Password");
		editPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
		controlsMenu.add(editPassword);
		
		controlsMenu.addSeparator();
		
		JMenuItem createPassword = new JMenuItem("Create Password");
		createPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
		controlsMenu.add(createPassword);
		
		JMenuItem deletePassword = new JMenuItem("Delete Password");
		deletePassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
		controlsMenu.add(deletePassword);
		
		//2ed MenuBar Category
		JMenu userMenu = new JMenu("User Settings");
		userMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(userMenu);
		userMenu.setForeground(getTextColor());
		
		JMenuItem options = new JMenuItem("Options");
		options.setFont(new Font("Segoe UI", Font.BOLD, 14));
		userMenu.add(options);
		
		//The panel that holds password creation display shapes
		overlayPanel.setBorder(null);
		overlayPanel.setBounds(0, menuBarSize, windowRes[0], windowRes[1]);
		mainFrame.getContentPane().add(overlayPanel);
		overlayPanel.setLayout(null);
		overlayPanel.setBackground(new Color(0,0,0,0));
		
		//The panel that houses the image label
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		mainPanel.setBounds(0, menuBarSize, windowRes[0], windowRes[1]);
		mainFrame.getContentPane().add(mainPanel);
		mainPanel.setLayout(new OverlayLayout(mainPanel));
		mainPanel.setBackground(getPrimaryColor());
		
		//The label that the image actually is loaded to
		JLabel pictureLabel = new JLabel("");
		pictureLabel.setIcon(null);
		mainPanel.add(pictureLabel);
		
		//Buttons used for password approval
		JButton enterPassBtn = new JButton("Enter Graphical Password");
		enterPassBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(enterPassBtn);
		enterPassBtn.setVisible(false);
		JButton endEnterModeBtn = new JButton("End Enter Mode");
		endEnterModeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(endEnterModeBtn);
		endEnterModeBtn.setVisible(false);
		
		//Event to capture mouse location when the picture is clicked on
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
				CreatePassword.main(directory, mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu, userName);
			}
		});
		
		deletePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				enterPassBtn.setVisible(false);
				
				JOptionPane.showMessageDialog(mainFrame, "Select the password you wish to delete.", "Information", JOptionPane.INFORMATION_MESSAGE);
				LoadPassword.deletePass(mainFrame);
			}
		});
		
		createPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				enterPassBtn.setVisible(false);
				
				String directory = System.getProperty("user.home") + File.separator + "Pictures";
				JOptionPane.showMessageDialog(mainFrame, "First you need to upload an image that will be used for your graphical Password.", "Information", JOptionPane.INFORMATION_MESSAGE);
				CreatePassword.main(directory, mainFrame, mainPanel, overlayPanel, pictureLabel, menuBar, controlsMenu, userMenu, userName);
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				options();
			}
		});
		
		if (!config.exists())
		{
			JOptionPane.showMessageDialog(mainFrame, "Welcome to Graphical Password. Since this is your first time using the program, please enter a username.", "Welcome", JOptionPane.INFORMATION_MESSAGE);
			userName = "User";
			options();
		}
	}
	
	static void startupLoadConfig()
	{
		File configDir = new File(System.getProperty("user.dir") + File.separator + "config" + ".txt");
		
		//Loads the text file to a string
		String fileText = "";
		try
		{
			Scanner configFileScanner = new Scanner(configDir);
			fileText = configFileScanner.useDelimiter("\\Z").next();
			configFileScanner.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		//splits config values which are separated by line breaks
		String[] configValues = fileText.split("\n");
		
		try
		{
			configValues[0] = configValues[0].substring(configValues[0].indexOf(":") + 1, configValues[0].length());
			userName = configValues[0].replace("\r", "").replace("\n", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//Gets the second config value which is for the resolution
		try
		{
			configValues[1] = configValues[1].substring(configValues[1].indexOf(":") + 1, configValues[1].length());
			String[] resolution = configValues[1].split(",");
			int resolutionX = Integer.valueOf(resolution[0]);
			int resolutionY = Integer.valueOf(resolution[1].replace("\r", "").replace("\n", ""));
			windowRes[0] = resolutionX;
			windowRes[1] = resolutionY;
		}
		catch (Exception e)
		{
			windowRes[0] = 1280;
			windowRes[1] = 720;
			e.printStackTrace();
		}
		
		//Gets the third config value which is for the brightness mode
		String darkModeVal = configValues[2].substring(configValues[2].indexOf(":") + 1, configValues[2].length()).replace("\r", "").replace("\n", "");
		if (darkModeVal.equals("true"))
			darkMode = true;
		else
			darkMode = false;
	}
	
	static void options()
	{
		optionsFrame.setAlwaysOnTop(true);
		optionsFrame.setTitle("User Options");
		optionsFrame.setVisible(true);
		optionsFrame.setResizable(false);
		optionsFrame.setBounds(mainFrame.getX() + startingX, mainFrame.getY() + startingY, optionsWidth, optionsHeight);
		
		//Main options panel which contains all other options panels
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBounds(0,0,optionsFrame.getWidth(),optionsFrame.getHeight());
		optionsPanel.setBackground(getPrimaryColor());
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsFrame.add(optionsPanel);
		
		//For formatting
		JPanel optionsPanelFiller1 = new JPanel();
		optionsPanelFiller1.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelFiller1);
		
		//First line of the options panel which is for the user name
		JPanel optionsPanelName = new JPanel();
		optionsPanelName.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelName);
		
		JPanel optionsPanelFiller2 = new JPanel();
		optionsPanelFiller2.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelFiller2);
		
		//Second line of the options panel which is for the resolution
		JPanel optionsPanelRes = new JPanel();
		optionsPanelRes.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelRes);
		
		JPanel optionsPanelFiller3 = new JPanel();
		optionsPanelFiller3.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelFiller3);
		
		//Third line of the options panel which is for the brightness option
		JPanel optionsPanelBright = new JPanel();
		optionsPanelBright.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelBright);
		
		JPanel optionsPanelFiller4 = new JPanel();
		optionsPanelFiller4.setBackground(getPrimaryColor());
		optionsPanelFiller4.setPreferredSize(new Dimension(0, 200));
		optionsPanel.add(optionsPanelFiller4);
		
		//2ed Bottom options panel which is for the save button
		JPanel optionsPanelSave = new JPanel();
		optionsPanelSave.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelSave);
		
		//Bottom options panel for save label
		JPanel optionsPanelLbl = new JPanel();
		optionsPanelLbl.setBackground(getPrimaryColor());
		optionsPanel.add(optionsPanelLbl);
		
		//1.UserName panel
		JLabel userNameLbl = new JLabel("User Name: ");
		userNameLbl.setVisible(true);
		userNameLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		userNameLbl.setForeground(getTextColor());
		optionsPanelName.add(userNameLbl);
		
		JTextField userNameTf = new JTextField(12);
		userNameTf.setText(userName);
		optionsPanelName.add(userNameTf);
		
		//2.Resolution panel
		JLabel resolutionLbl = new JLabel("Program Resolution: ");
		resolutionLbl.setVisible(true);
		resolutionLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		resolutionLbl.setForeground(getTextColor());
		optionsPanelRes.add(resolutionLbl);
		
		resolutionBox.setVisible(true);
		resolutionBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		//Sets the currently selected resolution option to match the program resolution
		for (int i = 0; i < resolutionOptions.length; i++)
		{
			String tempString = resolutionOptions[i].substring(0, resolutionOptions[i].indexOf(" "));
			if (tempString.equals(Integer.toString(windowRes[0])))
			{
				resolutionBox.setSelectedIndex(i);
			}
		}
		
		optionsPanelRes.add(resolutionBox);
		
		//3.Brightness panel
		JLabel brightnessLbl = new JLabel("Brightness: ");
		brightnessLbl.setVisible(true);
		brightnessLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		brightnessLbl.setForeground(getTextColor());
		optionsPanelBright.add(brightnessLbl);
		
		ButtonGroup brightnessGroup = new ButtonGroup();
		JRadioButton lightModeBtn = new JRadioButton("Light");
		JRadioButton darkModeBtn = new JRadioButton("Dark");
		lightModeBtn.setVisible(true);
		darkModeBtn.setVisible(true);
		lightModeBtn.setBackground(getPrimaryColor());
		darkModeBtn.setBackground(getPrimaryColor());
		lightModeBtn.setForeground(getTextColor());
		darkModeBtn.setForeground(getTextColor());
		optionsPanelBright.add(lightModeBtn);
		optionsPanelBright.add(darkModeBtn);
		brightnessGroup.add(lightModeBtn);
		brightnessGroup.add(darkModeBtn);
		
		if (!darkMode)
			lightModeBtn.setSelected(true);
		else
			darkModeBtn.setSelected(true);
		
		//3.2ed Bottom panel
		JButton saveOptionBtn = new JButton("Save Options");
		optionsPanelSave.add(saveOptionBtn);
		
		JButton restartProgramBtn = new JButton("Save and Restart");
		optionsPanelSave.add(restartProgramBtn);
		
		//3.Bottom panel
		JLabel saveMessageLbl = new JLabel("Reload Application to Apply");
		saveMessageLbl.setForeground(getTextColor());
		saveMessageLbl.setBackground(getTextColor());
		optionsPanelLbl.add(saveMessageLbl);
		
		optionsFrame.revalidate();
		optionsFrame.repaint();
		
		saveOptionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				saveOptions(userNameTf, lightModeBtn);
			}
		});
		
		restartProgramBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				saveOptions(userNameTf, lightModeBtn);
				
				try {
					restartApplication();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.exit(0);
			}
		});
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
	
	static void saveOptions(JTextField userNameTf, JRadioButton lightModeBtn)
	{
		//Saves the config file on click of save button
		File configDir = new File(System.getProperty("user.dir") + File.separator + "config.txt");
		Boolean cancelFlag = false;
		String newUserName;
		String[] resArray;
		String darkMode;
		
		//If the user does not enter a user name they are given the default user name "User"
		//If they do enter a user name it must be 4+ characters
		newUserName = userNameTf.getText();
		if (newUserName.length() == 0)
			newUserName = "User";
		else if (newUserName.length() <= 3)
		{
			JOptionPane.showMessageDialog(mainFrame, "User name must be 4+ characters.", "Error", JOptionPane.ERROR_MESSAGE);
			cancelFlag = true;
		}
		
		String resString = (String) resolutionBox.getSelectedItem();
		resArray = resString.split(" x ");
		
		if (lightModeBtn.isSelected())
			darkMode = "false";
		else
			darkMode = "true";
		
		//This flag is currently used only to prevent user names that are too short
		//Can be used to cancel in any case in which there is a save in saving the config
		if (!cancelFlag)
		{
			FileWriter coordFileWriter;
			try {
				coordFileWriter = new FileWriter(configDir);
				coordFileWriter.write("userName:" + newUserName);
				coordFileWriter.write("\n");
				coordFileWriter.write("resolution:" + resArray[0] + "," + resArray[1]);
				coordFileWriter.write("\n");
				coordFileWriter.write("darkMode:" + darkMode);
				coordFileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void setProgramMode(Mode newMode)
	{
			programMode = newMode;
	}
	
	static Mode getProgramMode()
	{
		return programMode;
	}
	
	static JPanel getOverlayPanel()
	{
		return overlayPanel;
	}
	
	static int getMenuBarSize()
	{
		return menuBarSize;
	}
	
	static Color getPrimaryColor()
	{
		if (darkMode)
			return darkPrimary;
		else
			return lightPrimary;
	}
	
	static Color getSecondaryColor()
	{
		if (darkMode)
			return darkSecondary;
		else
			return lightSecondary;
	}
	
	static Color getTextColor()
	{
		if (darkMode)
			return lightText;
		else
			return darkText;
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

//Method from Leo Lewis's article "Programmatically Restart a Java Application"
//https://dzone.com/articles/programmatically-restart-java
	public static void restartApplication() throws IOException {
		try
		{
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib"))
				{
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);
			
			// program main and program arguments
			String[] mainCommand = System.getProperty("sun.java.command").split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar"))
			{
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			}
			else
			{
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try
				{
					Runtime.getRuntime().exec(cmd.toString());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				}
			});
				// exit
				System.exit(0);
			}
		catch (Exception e)
			{
				// something went wrong
				throw new IOException("Error while trying to restart the application", e);
			}
	}
}

