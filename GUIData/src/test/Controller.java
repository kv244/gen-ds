package test;

// TODO add model

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import dynaLoad.*;

public class Controller {

	private View m_view;
	@SuppressWarnings("unused")
	//private driver dDriver; //TODO not used yet, needs recompile
	
	// Ctor
	
    Controller(View pView) {
        m_view  = pView;

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
    
    public class newHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("new clicked");
        }
    }
    
    class quitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("quit clicked");
        }
    }
    
    class saveHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("save clicked");
        }
    }
    
    class openHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	java.awt.FileDialog fGet = new java.awt.FileDialog(m_view.getFrame(), "Choose file");
        	fGet.setVisible(true);
        	m_view.setStatus(fGet.getFile());
        }
    }
    
    class aboutHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("about clicked");
			// TODO recompile to fix error 
			
        	/* This uses message box
        	
			String[] engines = driver.getEngines();
        	
			String out = "";
			for( String s : engines ){
				out += s + "; "; 
			}
			out = "Engines: " + out + " v." + dDriver.getVersion();	
				
			try {
				
				JOptionPane.showMessageDialog( null, out );
			} catch( Exception x ) {
				out = x.getMessage();
			}
			*/
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
    		m_view.setStatus("bNew clicked");
    	}
    }
}
