package test;

// Currently, the engines are not packed into the jar and
// will have to be loaded separately by the driver.
// TODO beware of driver.new and set engine, may need to modify driver behavior
// as i dont want to open the default store when selecting new file


import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import dynaLoad.*;

public class Main {

	private JFrame frame;
	private JLabel lblNewLabel;
	private driver dDriver;
	private static String _about;
	private static String _defaultEngine = "dynaLoad.HashTableStruct"; 
	private JTextField textKey;
	
	private ActionListener newHandler;
	private ActionListener saveHandler;
	private ActionListener openHandler;
	private ActionListener quitHandler;
	private ActionListener aboutHandler;

	
	public void setFrame(){
		this.frame.setVisible(true);
	}
	
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * File: New, Open, Save
	 * About --> Info
	 * Bottom: file name, engine, result of save|load
	 * 
	 */
	
	
	private JMenuItem mnitmNew;
	//mnitmNew.addActionListener(newHandler);
	public void addMnitmNewListener(ActionListener newHandler){
		mnitmNew.addActionListener(newHandler);
	}
	
	private void initialize() {
		
		this.dDriver = null;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 638, 474);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnFile.setMnemonic(KeyEvent.VK_F);
		
		
		mnitmNew = new JMenuItem("New");
		
		JMenuItem mnitmOpen = new JMenuItem("Open");
		mnitmOpen.addActionListener(openHandler);
		
		JMenuItem mnitmSave = new JMenuItem("Save");
		mnitmSave.addActionListener(saveHandler);
		
		JMenuItem mnitmQuit = new JMenuItem("Quit");
		mnitmQuit.addActionListener(quitHandler);
		
		mnFile.add(mnitmNew);
		mnFile.add(mnitmOpen);
		mnFile.add(mnitmSave);
		mnFile.add(mnitmQuit);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mnitmAbout = new JMenuItem("About");
		mnitmAbout.addActionListener(aboutHandler);
		mnAbout.add(mnitmAbout);
		
		frame.getContentPane().setLayout(null);
		
		JTree tree = new JTree();
		tree.setBounds(6, 6, 617, 341);
		
		frame.getContentPane().add(tree);
		
		lblNewLabel = new JLabel( "Status" );
		lblNewLabel.setToolTipText("Status");
		lblNewLabel.setBounds(6, 408, 617, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblKey = new JLabel("Key:");
		lblKey.setBounds(6, 359, 61, 16);
		frame.getContentPane().add(lblKey);
		
		textKey = new JTextField();
		textKey.setBounds(38, 359, 134, 28);
		frame.getContentPane().add(textKey);
		textKey.setColumns(10);
		
		JLabel lblValue = new JLabel("Value:");
		lblValue.setBounds(184, 365, 61, 16);
		frame.getContentPane().add(lblValue);
		
		JButton btnNew = new JButton("New");
		btnNew.setToolTipText("Add new item");
		btnNew.setMnemonic('N');
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNew.setBounds(470, 359, 80, 30);
		frame.getContentPane().add(btnNew);
		
		JButton btnDel = new JButton("Del");
		btnDel.setToolTipText("Delete selected item in tree");
		btnDel.setBounds(552, 395, 80, 30);
		frame.getContentPane().add(btnDel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setToolTipText("Update existing item");
		btnUpdate.setBounds(552, 359, 80, 30);
		frame.getContentPane().add(btnUpdate);
		
		JTextArea textAreaValue = new JTextArea();
		textAreaValue.setBounds(230, 359, 228, 50);
		frame.getContentPane().add(textAreaValue);
		
		// TODO is there a better way to do this

		newHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println( e.toString() ); // handle new
			}
		};
		
		openHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println( e.toString() ); // handle open
			}
		};
		
		saveHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println( e.toString() ); // handle save
			}
		};
		
		quitHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println( e.toString() ); // handle quit
			}
		};
		
		aboutHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// TODO recompile to fix error 
				// use message box instead
				
				String[] engines = driver.getEngines();
				String out = "";
				for( String s : engines ){
					out += s + "; "; 
				}
				out = "Engines: " + out + " v." + dDriver.getVersion();	
				
				try{
					_about = out;
					JOptionPane.showMessageDialog( null, _about );
				}
				catch( Exception x ){
					_about = x.getMessage();
				}
			}
		};
	}
}
