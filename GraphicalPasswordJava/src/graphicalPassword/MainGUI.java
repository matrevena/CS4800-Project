package graphicalPassword;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.EventQueue;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MainGUI {
private JFrame mainFrame; //Global declaration of the main window

	//Main which launches the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Main window
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 1280, 762);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		//Top Menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1264, 21);
		mainFrame.getContentPane().add(menuBar);
		
		//Combo box that houses all controls
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setToolTipText("Controls");
		menuBar.add(comboBox);
		
		//The panel that holds password creation display circles
		JPanel overlayPanel = new JPanel();
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
		
		//Event to capture mouse location when the picture is clicked
		pictureLabel.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		        int x=e.getX();
		        int y=e.getY();
		        //System.out.println(x+","+y);			//TEST
		        
		        //CreatePassword.validateClick(x, y, overlayPanel);
		        mainFrame.repaint();
		    }
		});
		
		//Temporary upload image button
		Button button = new Button("Upload Image");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				FileChooser.main(mainFrame, mainPanel, overlayPanel, pictureLabel);
			}
		});
		menuBar.add(button);
		//Temporary Password Creation
		Button createButton = new Button("Create Password Boxes"); 
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				CreatePassword.main(mainFrame, mainPanel, overlayPanel, pictureLabel);
			}
		});
		menuBar.add(createButton);
		
	}
}

