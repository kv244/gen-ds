package test;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;

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
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.tree.TreeNode;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultTreeModel;

public class View {
	
	private JFrame frame;
	private JLabel lblNewLabel;
	private JTextField textKey;
	
	private JMenuItem mnitmAbout;
	private JMenuItem mnitmNew;
	private JMenuItem mnitmOpen;
	private JMenuItem mnitmSave;
	private JMenuItem mnitmQuit;
	
	private JTextArea textAreaValue;
	
	private JButton btnNew; 
	private JButton btnDel;
	private JButton btnUpd;
	private JTree tree;
	
	private DefaultMutableTreeNode rootNode;
	
	private char _magic = '~';
	private char _newline = '\n'; // TODO multiplatform?
	
	/**
	 * Inititialize form
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
	
	public void addQuitHandler(ActionListener quitHandler) {
		mnitmQuit.addActionListener(quitHandler);
	}
	
	public void addBtnNewHandler(ActionListener btnNewHandler) {
		btnNew.addActionListener(btnNewHandler);
	}

	public void addBtnDelHandler(ActionListener btnDelHandler) {
		btnDel.addActionListener(btnDelHandler);
	}
	
	public void addBtnUpdHandler(ActionListener btnUpdHandler) {
		btnUpd.addActionListener(btnUpdHandler);
	}
	
	public void setStatus(String status) {
		this.lblNewLabel.setText(status);
	}
	
	// tree accessors
	
	// View has no knowledge of data structure
	public void addNode(String text) {
		//TODO trying this, and it provides index, so it can be refactored for deletes etx
		//rootNode.add(new DefaultMutableTreeNode(text));
		
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		model.insertNodeInto(new DefaultMutableTreeNode(text), rootNode, rootNode.getChildCount());
	}
	
	public void treeRepaint(DefaultMutableTreeNode node) {
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		if(node==null)model.reload();
		else model.reload(node);
	}
	
	// used by the controller mostly to remove nodes
	public DefaultMutableTreeNode getRoot() {
		return rootNode;
	}
	
	public JTree getTree() {
		return tree;
	}

	public void addTreeClickHandler(TreeSelectionListener tl) {
		this.tree.addTreeSelectionListener(tl);
	}
	
	public void setRoot(String text) {
		rootNode.setUserObject(text);
	}
	
	// text box accessors
	// replaces newline with magic
	// TODO trap NullPointer?
	public String getItem() {
		return scrubNewLine(this.textAreaValue.getText());
	}
	
	private String scrubNewLine(String parm) {
		String newString = parm.replace(_newline, _magic);
		return newString;
	}
	
	public String getKey() {		
		return this.textKey.getText();
	}
	
	// replaces magic with newline
	public void setItem(String text) {
		this.textAreaValue.setText(scrubMagic(text));
	}
	
	private String scrubMagic(String parm) {
		String newString = parm.replace(_magic, _newline);
		return newString;
	}
	
	public void setKey(String text) {
		this.textKey.setText(text);
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
		frame.setBounds(100, 100, 640, 475);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		
		rootNode = new DefaultMutableTreeNode();
		rootNode = new DefaultMutableTreeNode("No data");
		
		lblNewLabel = new JLabel( "Status" );
		lblNewLabel.setToolTipText("Status"); 
		lblNewLabel.setBounds(6, 408, 544, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblKey = new JLabel("Key:");
		lblKey.setBounds(6, 359, 61, 16);
		frame.getContentPane().add(lblKey);
		
		textKey = new JTextField();
		textKey.setBounds(38, 359, 134, 28);
		frame.getContentPane().add(textKey);
		textKey.setColumns(10);
		
		JLabel lblValue = new JLabel("Item:");
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
				
		tree = new JTree(rootNode);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(620, 340);
		scrollPane.setLocation(5, 5);
	
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(tree);
		
		JScrollPane scrollPaneTextArea = new JScrollPane();
		scrollPaneTextArea.setBounds(245, 360, 200, 50);
		frame.getContentPane().add(scrollPaneTextArea);
		textAreaValue = new JTextArea(); 
		textAreaValue.setBounds(246, 361, 198, 48);
		scrollPaneTextArea.setViewportView(textAreaValue);
		//frame.getContentPane().add(textAreaValue);
		
		
		setStatus("Running");
	}
}
