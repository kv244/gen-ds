package test;

// TODO add model
// the drivers' setStore will attempt to load the default file,
// so this should be handled
// other is with New file, defer setting the store until save is clicked
// and handle the case if the file exists here (delete or rename) to avoid
// loading it
// the driver is initialized by loading a file, when it picks up the engine from the file
// or by New, when it uses what the user chose
// TODO how to populate tree, i --> root, value under it? also refresh cases


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dynaLoad.driver; 

public class Controller {

	private View m_view;
	private driver dDriver = null;
	private boolean engineSet = false;
	private String engine = null;
	
	// Ctor
	
    Controller(View pView) {
        m_view  = pView;

        m_view.addAboutHandler(new aboutHandler());
        m_view.addNewHandler(new newHandler());
        m_view.addQuitHandler(new quitHandler());
        m_view.addOpenHandler(new openHandler());
        m_view.addSaveHandler(new saveHandler());
        
        m_view.addBtnDelHandler(new btnDelHandler());
        m_view.addBtnNewHandler(new btnNewHandler());
        m_view.addBtnUpdHandler(new btnUpdHandler());    
    }

    // menu handlers
    
    /* New
     * If a file in memory, ask to save first - commit lazily as above
     * Ask file name and engine
     * TODO dynamically determine engines, defer to driver to see what files are
     * available
     * TODO ask to save file, what is the indicator? 
     */
    public class newHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	engine = JOptionPane.showInputDialog("Choose an engine:\n1. HS \n2. AL");
        	
        	if(engine.compareTo("1") == 0)
        		engine = "dynaLoad.HashTableStruct";
        	if(engine.compareTo("2") == 0)
        		engine = "dynaLoad.ArrayListStruct";
        	
        	if( engine.startsWith("dynaLoad")) {
        		dDriver = new driver(engine);
        		System.out.println(engine);
        	} else {
        		dDriver = null;
        	}
        }
    }
    
    /* Quit
     * Prompt to save if file in memory.
     */
    class quitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("quit clicked");
        }
    }
    
    /*Save
     * 
     */
    class saveHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("save clicked");
        }
    }
    
    /* Open
     * If no file in memory, this will attempt to open the chosen file and set the
     * driver to the file's original engine. Then it will call the renderer for the data.
     * If file load fails or not magic, nothing happens - memory and tree empty.
     */
    class openHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	java.awt.FileDialog fGet = new java.awt.FileDialog(m_view.getFrame(), 
        			"Choose file");
        	fGet.setVisible(true);
        	String file = fGet.getFile();
        	if(file != null) {
        		file = fGet.getDirectory() + file;
        		engine = driver.checkStorageEngine(file); 
        		m_view.setStatus( engine != null ? "Using " + engine : "File not compliant, or not found" );
        		
        		// TODO complete
        		
        	} else { m_view.setStatus("Open canceled"); }
        }
    }
    
    class aboutHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("Using " + dDriver.getVersion()); //TODO replace with static call
        	// also, if driver loaded, show engine
        }
    }
    
    // button handlers
    
    class btnUpdHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		m_view.setStatus("bUpd clicked");
    	}
    }
    
    class btnDelHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		m_view.setStatus("bDel clicked");
    	}
    }
    
    class btnNewHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		
    		JOptionPane choice = new JOptionPane(null);
    		engineSet = true;
    	}
    }
}
