package test;

// TODO add model

import java.awt.event.*;

import javax.swing.JOptionPane;

import dynaLoad.driver;


public class GUIController {

	private Main m_view;
	
    GUIController(Main view) {
        m_view  = view;

        m_view.addNewHandler(new newHandler());
        m_view.addAboutHandler(new aboutHandler());
        m_view.addQuitHandler(new quitHandler());
        m_view.addOpenHandler(new openHandler());
        m_view.addSaveHandler(new saveHandler());
        
        m_view.addBtnDelHandler(new btnDelHandler());
        m_view.addBtnNewHandler(new btnNewHandler());
        m_view.addBtnUpdHandler(new btnUpdHandler());
        
    }

    // menu handlers
    
    class newHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        }
    }
    
    class quitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        }
    }
    
    class saveHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        }
    }
    
    class openHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        }
    }
    
    class aboutHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
				
			// TODO recompile to fix error 
			
        	/*
        	
			String[] engines = driver.getEngines();
        	
			String out = "";
			for( String s : engines ){
				out += s + "; "; 
			}
			out = "Engines: " + out + " v." + dDriver.getVersion();	
				
			try {
				_about = out;
				JOptionPane.showMessageDialog( null, _about );
			} catch( Exception x ) {
				_about = x.getMessage();
			}
			*/
		}
    }
    
    // button handlers
    
    class btnUpdHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		
    	}
    }
    
    class btnDelHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		
    	}
    }
    
    class btnNewHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		
    	}
    }
    
}
