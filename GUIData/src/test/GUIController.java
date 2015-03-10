package test;

import java.awt.event.*;


public class GUIController {

	private Main m_view;
	
	
    GUIController(Main view) {
        m_view  = view;

        m_view.addMnitmNewListener(new mnitmNewHandler());
        
    }

    class mnitmNewHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	// to add here from Main, handler for New menu ite,
        }
    }
    
}
