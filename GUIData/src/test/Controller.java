package test;

// the drivers' setStore will attempt to load the default file,
// so this should be handled
// other is with New file, defer setting the store until save is clicked
// and handle the case if the file exists here (delete or rename) to avoid
// loading it
// the driver is initialized by loading a file, when it picks up the engine from the file
// or by New, when it uses what the user chose

// GUI: (p.1)
// double clicking an existing item in the tree will put it in the text box
// clicking save (update) will update the item, if an item has been chosen, or message otherwise
// clicking new will clear the text boxes, to be updated/inserted by save (update)
// clicking delete will delete the highlighted item in the tree (if any), or message otherwise
// TODO handle case when clicked but no document in memory (p.2)
// affected:
// 	update
// 	delete

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import dynaLoad.driver; 
import dynaLoad.dataItem;

public class Controller {

	private View m_view;
	private driver dDriver = null;
	private boolean engineSet = false;
	private String engine = null;
	private String rootSelected = "root";
	
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
        m_view.addTreeClickHandler(new treeClickHandler());
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
        	
        	if(engine.startsWith("dynaLoad")) {
        		dDriver = new driver(engine);
        		System.out.println(engine);
        	} else {
        		dDriver = null;
        	}
        	//TODO implement new file
        }
    }
    
    /* Quit
     * TODO Prompt to save if file in memory.
     */
    public class quitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	System.exit(0);
        }
    }
    
    /*Save
     * 
     */
    public class saveHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("save clicked");
        }
    }
    
    /* Open
     * If no file in memory, this will attempt to open the chosen file and set the
     * driver to the file's original engine. Then it will call the renderer for the data.
     * If file load fails or not magic, nothing happens - memory and tree empty.
     */
    public class openHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	java.awt.FileDialog fGet = new java.awt.FileDialog(m_view.getFrame(), 
        			"Choose file");
        	fGet.setVisible(true);
        	String file = fGet.getFile();
        	if(file != null) {
        		file = fGet.getDirectory() + file;
        		engine = driver.checkStorageEngine(file); 
        		m_view.setStatus(engine != null 
        				? "Loading..." 
        				: "File not compliant, or not found");
        		
        		if(engine == null) return;
        		
        		// TODO add state logic
        		
        		// basic tree loading:
        		// create new driver
        		// defer loading to driver
        		// iterate using driver to populate tree
        		// the check above is unnecessary as the driver will do it,
        		// but i have to get the engine from file -- TODO refactor?
        		
        		dDriver = new driver(engine);
        		dataItem di = null;
        		int size = 0;
        		
        		try {
	        		if((size = dDriver.setStore(file)) > 0) { // this call will load the file
	        			dDriver.iterReset();
	        			while((di = dDriver.iter()) != null) {
	        				m_view.addNode(makeNodeText(di));
	        			}
	        			rootSelected = engine + "(" + Integer.toString(size)+")";
	        			m_view.setStatus(rootSelected);
        				m_view.setRoot(file); // TODO refresh?
	        		}
        		} catch(Exception x) {
        			m_view.setStatus("Engine: " + engine + " error " + x.getMessage());
        		}
        	} else { m_view.setStatus("Open canceled"); }
        }     
    }
    
    // parent class method, seen by the embedded classes
    // transforms data item to printable string
    // use the reverse to build di form string
    protected String makeNodeText(dataItem di) {
		return di.toString();
    }
    
    protected dataItem getNodeText(String text) {
    	return dataItem.toDi(text);
    }

    public class aboutHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	m_view.setStatus("Using " + driver.getVersion()); 
        	// also, if driver loaded, show engine
        }
    }
    
    // button handlers
    public class btnUpdHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		m_view.setStatus("bUpd clicked");
    	}
    }
    
    // delete
    // this relies on data being in the text box
    // TODO test
    public class btnDelHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		m_view.setStatus("bDel clicked");
    		int i = -1;
    		
    		try {
    			i = Integer.parseInt(m_view.getKey());
    			dDriver.del(i);
    			delNode(i);
    		} catch(NumberFormatException x) { m_view.setStatus("Nothing to delete"); }
    		catch(Exception x) { m_view.setStatus("Error deleting " + x.getMessage()); }
    	}
    	
    	// finds the node with the given key
    	private DefaultMutableTreeNode findNode(int key) {
    		DefaultMutableTreeNode ptrn;
    		Enumeration<DefaultMutableTreeNode> nodes = m_view.getRoot().children(); 
     		while(nodes.hasMoreElements()) {
     			ptrn = nodes.nextElement();
     			if(dataItem.toDi(ptrn.toString()).getKey() == key)
     				return ptrn;
     		}
    		return null;
    	}
    	
        // this removes the node identified by key
     	// TODO there might be a better way of doing this,
     	// make node index = key?
     	private void delNode(int key) {
     		DefaultMutableTreeNode ptrn = findNode(key);
     		DefaultTreeModel model = (DefaultTreeModel)m_view.getTree().getModel();
     		
     		if(ptrn != null && ptrn.getParent() != null) {
     			/*
     			m_view.getTree().setSelectionPath(null);
     			model.removeNodeFromParent(ptrn);
     			*/
     			m_view.removeNode(ptrn); // push to thread?
     			m_view.setStatus("Removed " + Integer.toString(key));
     		} else { m_view.setStatus("Unusual deletion state"); }
     	}
    }
    
    // new item
    // functionality implemented in the update handler
    public class btnNewHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		m_view.setKey("add key...");
    		m_view.setItem("add item...");
    	}
    }
    
    // tree event handler
    // occurs when clicking on tree
    // handle clicking the header
    public class treeClickHandler implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			String selectionPath = e.getNewLeadSelectionPath().toString();
			String textItem = "";
			String textKey = "";
			int startOfNode = selectionPath.indexOf(", ");
			
			if(startOfNode != -1) { // non root selected
				selectionPath = selectionPath.substring(startOfNode+2);
				selectionPath = selectionPath.replace(']', '\0');
				
				try { // will throw null exception if failed
					// TODO add handler for null - defer to driver
					textItem = dataItem.toDi(selectionPath).getItem();
					textKey = Integer.toString(dataItem.toDi(selectionPath).getKey());
					m_view.setStatus(selectionPath);
				} catch(Exception x){ m_view.setStatus("Cannot parse " + selectionPath); }
				m_view.setItem(textItem);
				m_view.setKey(textKey);
			} else { m_view.setStatus(rootSelected); }
		}
    }
}
