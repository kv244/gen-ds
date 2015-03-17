package test;

// Currently, the engines are not packed into the jar and
// will have to be loaded separately by the driver.
// TODO beware of driver.new and set engine, may need to modify driver behavior
// as i dont want to open the default store when selecting new file

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Frame;


public class View {
	
	private JFrame frame;
	private JLabel lblNewLabel;
	private JTextField textKey;
	
	private JMenuItem mnitmAbout;
	private JMenuItem mnitmNew;
	private JMenuItem mnitmOpen;
	private JMenuItem mnitmSave;
	private JMenuItem mnitmQuit;
	private JTree tree;
	
	private JTextArea textAreaValue;
	
	private JButton btnNew; 
	private JButton btnDel;
	private JButton btnUpd;
	
	
	/**
	 * Create the application. -- init form
	 */
	public View() {
		initialize();
	}

	// accessor for external main program
	public void setFrame() {
		this.frame.setVisible(true);
	}
	
	public Frame getFrame() {
		return this.frame;
	}
		
	// Handler accessors
	// TODO can this be refactored
	public void addNewHandler(ActionListener newHandler){
		mnitmNew.addActionListener(newHandler);
	}
	
	public void addAboutHandler(ActionListener aboutHandler){
		mnitmAbout.addActionListener(aboutHandler);
	}
	
	public void addOpenHandler(ActionListener openHandler){
		mnitmOpen.addActionListener(openHandler);
	}
	
	public void addSaveHandler(ActionListener saveHandler){
		mnitmSave.addActionListener(saveHandler);
	}
	
	public void addQuitHandler(ActionListener quitHandler){
		mnitmQuit.addActionListener(quitHandler);
	}
	
	public void addBtnNewHandler(ActionListener btnNewHandler){
		btnNew.addActionListener(btnNewHandler);
	}

	public void addBtnDelHandler(ActionListener btnDelHandler){
		btnDel.addActionListener(btnDelHandler);
	}
	
	public void addBtnUpdHandler(ActionListener btnUpdHandler){
		btnUpd.addActionListener(btnUpdHandler);
	}
	
	public void setStatus(String status){
		this.lblNewLabel.setText(status);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 * 
	 * File: New, Open, Save
	 * About --> Info
	 * Bottom: file name, engine, result of save|load
	 * 
	 */
	
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 638, 474);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("cViewer");
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(true);
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnFile.setMnemonic(KeyEvent.VK_F); // does not do much on Mac OS X
		
		mnitmNew = new JMenuItem("New");
		mnitmNew.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_N, (java.awt.event.InputEvent.SHIFT_MASK )));
		mnitmOpen = new JMenuItem("Open");
		mnitmOpen.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_O, (java.awt.event.InputEvent.SHIFT_MASK )));
		mnitmSave = new JMenuItem("Save");
		mnitmSave.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, (java.awt.event.InputEvent.SHIFT_MASK )));
		mnitmQuit = new JMenuItem("Quit");
		mnitmQuit.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, (java.awt.event.InputEvent.SHIFT_MASK )));
		
		mnFile.add(mnitmNew);
		mnFile.add(mnitmOpen);
		mnFile.add(mnitmSave);
		mnFile.add(mnitmQuit);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		mnitmAbout = new JMenuItem("About");
		mnAbout.add(mnitmAbout);
		mnitmAbout.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, (java.awt.event.InputEvent.SHIFT_MASK )));
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root node");
		tree = new JTree(rootNode);
		tree.setBounds(5, 5, 620, 340);
		rootNode.add(new DefaultMutableTreeNode("Blair"));
		rootNode.add(new DefaultMutableTreeNode("Lincoln"));
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("Nebraska");
		childNode.add(new DefaultMutableTreeNode("Omaha"));
		rootNode.add(childNode);
		
		//TODO add 
		
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
		btnNew.setBounds(470, 359, 80, 30);
		frame.getContentPane().add(btnNew);
		
		btnDel = new JButton("Del");
		btnDel.setToolTipText("Delete selected item in tree");
		btnDel.setBounds(552, 395, 80, 30);
		frame.getContentPane().add(btnDel);
		
		btnUpd = new JButton("Update");
		btnUpd.setToolTipText("Update existing item");
		btnUpd.setBounds(552, 359, 80, 30);
		frame.getContentPane().add(btnUpd);
		
		textAreaValue = new JTextArea(); // TODO provide accessor
		textAreaValue.setBounds(230, 359, 228, 50);
		frame.getContentPane().add(textAreaValue);
		
		setStatus("Running");
	}
}
