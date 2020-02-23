package graphicalPassword;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.EventQueue;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI {
private JFrame mainFrame; //Global declaration of the main window

	//Main which launches the aplication
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
		mainFrame.setBounds(100, 100, 1298, 780);
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
		
		//The panel that houses the image label
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		mainPanel.setBounds(0, 20, 1280, 720);
		mainFrame.getContentPane().add(mainPanel);
		
		//The label that the image actually is loaded to
		JLabel pictureLabel = new JLabel("");
		pictureLabel.setIcon(null);
		mainPanel.add(pictureLabel);
		
		//Temporary upload image button
		Button button = new Button("Upload Image");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				FileChooser.main(mainFrame, mainPanel, pictureLabel);
			}
		});
		menuBar.add(button);
	}
}

