package test;

// This is the View -- named main for historic reasons
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
	
	private JMenuItem mnitmAbout;
	private JMenuItem mnitmNew;
	private JMenuItem mnitmOpen;
	private JMenuItem mnitmSave;
	private JMenuItem mnitmQuit;
	private JTree tree;
	
	private JTextArea textAreaValue;
	
	private JButton btnNew; //TODO provide accessor
	private JButton btnDel;
	private JButton btnUpdate;
	
	
	/**
	 * Create the application. -- init form
	 */
	public Main() {
		initialize();
	}

	// accessor for external main program
	public void setFrame(){
		this.frame.setVisible(true);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 * 
	 * File: New, Open, Save
	 * About --> Info
	 * Bottom: file name, engine, result of save|load
	 * 
	 */
	
	
	// Handler accessors
	// TODO can this be refactored
	public void addNewHandler(ActionListener newHandler){
		mnitmNew.addActionListener(newHandler);
	}
	
	public void addAboutHandler(ActionListener aboutHandler){
		mnitmAbout.addActionListener(aboutHandler);
	}
	
	public void addOpenHandler(ActionListener openHandler){
		mnitmAbout.addActionListener(openHandler);
	}
	
	public void addSaveHandler(ActionListener saveHandler){
		mnitmAbout.addActionListener(saveHandler);
	}
	
	public void addQuitHandler(ActionListener quitHandler){
		mnitmAbout.addActionListener(quitHandler);
	}
	
	public void addBtnNewHandler(ActionListener btnNewHandler){
		btnNew.addActionListener(btnNewHandler);
	}
	
	public void addBtnDelHandler(ActionListener btnDelHandler){
		btnNew.addActionListener(btnDelHandler);
	}
	
	public void addBtnUpdHandler(ActionListener btnUpdHandler){
		btnNew.addActionListener(btnUpdHandler);
	}
	
	
	
	private void initialize() {
		
		this.dDriver = null; //TODO replace...
		
		frame = new JFrame();
		frame.setBounds(100, 100, 638, 474);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnFile.setMnemonic(KeyEvent.VK_F);
		
		mnitmNew = new JMenuItem("New");
		mnitmOpen = new JMenuItem("Open");
		mnitmSave = new JMenuItem("Save");
		mnitmQuit = new JMenuItem("Quit");
		
		mnFile.add(mnitmNew);
		mnFile.add(mnitmOpen);
		mnFile.add(mnitmSave);
		mnFile.add(mnitmQuit);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		mnitmAbout = new JMenuItem("About");
		mnAbout.add(mnitmAbout);
		
		frame.getContentPane().setLayout(null);
		
		tree = new JTree();
		tree.setBounds(6, 6, 617, 341);
		
		frame.getContentPane().add(tree);
		
		lblNewLabel = new JLabel( "Status" );
		lblNewLabel.setToolTipText("Status"); //TODO provide accessor
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
		
		btnNew = new JButton("New");
		btnNew.setToolTipText("Add new item");
		btnNew.setMnemonic('N');
		
		btnNew.setBounds(470, 359, 80, 30);
		frame.getContentPane().add(btnNew);
		
		btnDel = new JButton("Del");
		btnDel.setToolTipText("Delete selected item in tree");
		btnDel.setBounds(552, 395, 80, 30);
		frame.getContentPane().add(btnDel);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setToolTipText("Update existing item");
		btnUpdate.setBounds(552, 359, 80, 30);
		frame.getContentPane().add(btnUpdate);
		
		textAreaValue = new JTextArea(); // TODO provide accessor
		textAreaValue.setBounds(230, 359, 228, 50);
		frame.getContentPane().add(textAreaValue);
	}
}
