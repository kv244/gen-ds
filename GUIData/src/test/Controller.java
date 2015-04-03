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
// TODO reengineering:
// use structure to associate key with node index
// no need to find node at update/delete

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.JFileChooser;
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
	private String engine = null;
	private String rootSelected = "root";
	private enum buttonAction{ _NEW, _TREE, _NONE }
	private buttonAction updateState;
	
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
        
        updateState = buttonAction._NONE;
    }

    // menu handlers
    
    /* New
     * If a file in memory, ask to save first - commit lazily as above
     * Ask file name and engine
     * TODO dynamically determine engines, defer to driver to see what files are
     * available
     * dDriver null means no file has been loaded yet
     * otherwise, if new file or file loaded|even already saved, 
     * dDriver will always be not null
     */
    public class newHandler implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
        	m_view.setItem("");
			m_view.setKey("");
        	if(dDriver != null) {
        		int response = JOptionPane.showConfirmDialog(null, "Data in memory, click yes to quit");
        		if(response != JOptionPane.YES_OPTION)
        			return;
        		// delete trees, same problem as when loading, fail silently now TODO
        		// TODO - refactor the dDriver == null check and tree deletion from below
    			m_view.getRoot().removeAllChildren();
    			try { m_view.treeRepaint(null); } catch(Exception x) {} // same error as elsewhere
    			dDriver = null;
    			m_view.setStatus("Tree empty");
        	}
        	
        	engine = JOptionPane.showInputDialog("Choose an engine:\n1. HS \n2. AL");
        	
        	if(engine.compareTo("1") == 0)
        		engine = "dynaLoad.HashTableStruct";
        	if(engine.compareTo("2") == 0)
        		engine = "dynaLoad.ArrayListStruct";
        	
        	if(engine.startsWith("dynaLoad")) {
        		dDriver = new driver(engine);
        		m_view.setStatus("Using " + engine);
        	} else {
        		m_view.setStatus("Engine not recognized");
        		return;
        	} // TODO this has to be modified to use any number of engines
        	
        	// have to set the file name
        	// setstore also tries to open the file so we have to make sure file does not exist
        	
        	JFileChooser fSave = new JFileChooser();
        	int retVal = fSave.showSaveDialog(null);
        	if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = fSave.getSelectedFile();
                if(file.exists()) {
                    m_view.setStatus("File already exists, use open or choose a new name");
                    return;
                }

                m_view.setStatus("Using: " + file.getName());
                try {
                	dDriver.setStore(file.getPath());
                	m_view.setRoot(file.getPath()); 
                	updateState = buttonAction._NONE;
                } catch(Exception x) { 
                	m_view.setStatus("Failed to use file " + file.getName() + ", " + x.getMessage()); 
                	dDriver = null; 
                }
        	}
        }
    }
    
    /* Quit
     */
    public class quitHandler implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
        	if(dDriver!=null) {
        		int response = JOptionPane.showConfirmDialog(null, "Data in memory, click yes to quit");
        		if(response != JOptionPane.YES_OPTION)
        			return;
        	}
        	System.exit(0);
        }
    }
    
    /* Save
     */
    public class saveHandler implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
        	m_view.setItem("");
			m_view.setKey("");
        	if(dDriver == null) {
        		m_view.setStatus("Open or create a file first");
        		return;
        	}
        	try {
            	
        		if(dDriver.getSize() == 0) {
            		m_view.setStatus("No data to save");
            		return;
            	} else {
            		m_view.setStatus(dDriver.commit()); 
            	}
        	} catch(Exception x) { m_view.setStatus("Error saving " + x.getMessage()); }
        }
    }
    
    /* Open
     * If no file in memory, this will attempt to open the chosen file and set the
     * driver to the file's original engine. Then it will call the renderer for the data.
     * If file load fails or not magic, nothing happens - memory and tree empty.
     */
    public class openHandler implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
        	m_view.setItem("");
			m_view.setKey("");
        	if(dDriver!=null) {
        		int response = JOptionPane.showConfirmDialog(null, "Data in memory, click yes to discard and load other");
        		if(response != JOptionPane.YES_OPTION)
        			return;
        		else {
        			//m_view.getTree().setSelectionPath(null); fails
        			m_view.getRoot().removeAllChildren();
        			try { m_view.treeRepaint(null); } catch(Exception x) {} // same error as elsewhere
        			dDriver = null;
        			m_view.setStatus("Tree empty");
        		}
        		// TODO this should also destroy the items stored in memory - I could iterate and destroy each individually
        	}
        	
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
        				m_view.setRoot(file); 
	        		} else {
	        			m_view.setStatus("File is unreadable"); // can add data to it though
	        		}
	        		
        		} catch(Exception x) { //TODO this still does not get the correct message from the underlying error
        			m_view.setStatus("Engine: " + engine + " error " + x.getClass().getName());
        		}
        	} else { m_view.setStatus("Open canceled"); }
        }     
    }
    
    // parent class method, seen by the embedded classes
    // transforms data item to printable string
    // use the reverse to build di from string
    protected String makeNodeText(dataItem di) {
		return di.toString();
    }
    
    protected dataItem getNodeText(String text) {
    	return dataItem.toDi(text);
    }

    // finds the node with the given key
    // used by delete button handler
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
    
    public class aboutHandler implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
        	String out = "Using " + driver.getVersion();
        	try { 
        		if(dDriver != null)
        			out += "; " + dDriver.getEngine();
        	} catch(Exception x){}
        	m_view.setStatus(out);
        }
    }
    
    // button handlers
    // this handles both insert and update
    // in both data structure and tree
    // need to capture state - if most recent clicked was New
    // or if it was a tree click
    // this has to deal with issue #1, newline in text
    public class btnUpdHandler implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
    		if(dDriver == null) {
    			m_view.setStatus("No file in memory");
    			return;
    		}
        	String out = "";
        	switch(updateState) {
        	case _NEW:
        		// add is - just add new node
        		// TODO test if same (existing) key inserted for both engines
        		try {
        			dataItem di = new dataItem(m_view.getItem(), Integer.parseInt(m_view.getKey()));
        			m_view.addNode(makeNodeText(di)); // this can be refactored to just use di.toString, but just in case
        			dDriver.add(di.getKey(), di.getItem());
        			updateState = buttonAction._NONE;
        			out = "Inserted";
        		} catch(NumberFormatException x) { out = "Key cannot be parsed"; }
        		catch(Exception x) { out = "Error inserting node " + x.getMessage(); }
        		break;
        	case _TREE:
        		// replace is - find node and set object to new value
        		// TODO refactor to use tree index insert (with data structure)
        		try {
        			dataItem di = new dataItem(m_view.getItem(), Integer.parseInt(m_view.getKey())); // TODO to refactor? move di before switch
        			DefaultMutableTreeNode replaced = findNode(di.getKey());
        			replaced.setUserObject(makeNodeText(di)); 
        			m_view.treeRepaint(replaced);
        			dDriver.del(Integer.parseInt(m_view.getKey())); 
        			dDriver.add(di.getKey(), di.getItem());
        			out = "Updated";
        		} catch(Exception x) { out = "Error updating node " + x.getMessage(); }
        		break;
        	default:
        		out = "nothing";
        		break;
        	}
        	m_view.setItem("");
			m_view.setKey("");
        	m_view.setStatus(out);
    	}
    }
    
    // delete
    // this relies on data being in the text box
    // deletion from data structure, and from node 
    // (not transactional...)
    public class btnDelHandler implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
    		if(dDriver == null) {
    			m_view.setStatus("No file in memory");
    			return;
    		}
    		int i = -1;
    		updateState = buttonAction._NONE; //resets the state for update
    		try {
    			i = Integer.parseInt(m_view.getKey());
    			dDriver.del(i);
    			delNode(i);
    			m_view.setItem("");
    			m_view.setKey("");
    		} catch(NumberFormatException x) { m_view.setStatus("Nothing to delete"); }
    		catch(Exception x) { m_view.setStatus("Error deleting " + x.getMessage()); }
    	}

        // this removes the node identified by key, action of delete button handler
     	// TODO reengineer for faster search
     	private void delNode(int key) {
    		if(dDriver == null) {
    			m_view.setStatus("No file in memory");
    			return;
    		}
     		DefaultMutableTreeNode ptrn = findNode(key); // method moved to parent class
     		DefaultTreeModel model = (DefaultTreeModel)m_view.getTree().getModel();
     		
     		if(ptrn != null && ptrn.getParent() != null) {
     			//m_view.getTree().setSelectionPath(null); // this is the supposed fix but it too throws an error
     			try {
     				model.removeNodeFromParent(ptrn);
     			} catch(NullPointerException x) { m_view.setStatus("Internal error, delete completed"); }
     			m_view.setStatus("Removed " + Integer.toString(key));
     		} else { m_view.setStatus("Unusual deletion state"); } // if getting other error....
     	}
    }
    
    // new item
    // functionality implemented in the update handler
    public class btnNewHandler implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
    		if(dDriver == null) {
    			m_view.setStatus("No file in memory");
    			return;
    		}
    		int attemptKey = 0;
    		try {
    			attemptKey = dDriver.getSize();
    		} catch(Exception x) {}
    		m_view.setKey(Integer.toString(attemptKey));
    		m_view.setItem("add item...");
    		m_view.setKeyReadOnly(false); 
    		updateState = buttonAction._NEW;
    	}
    }
    
    // tree event handler
    // occurs when clicking on tree - open item in text box or
    // nothing if clicking the header
    public class treeClickHandler implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			String selectionPath = e.getNewLeadSelectionPath().toString();
			String textItem = "";
			String textKey = "";
			int startOfNode = selectionPath.indexOf(", ");
			
			if(startOfNode != -1) { // non root selected
				selectionPath = selectionPath.substring(startOfNode+2);
				selectionPath = selectionPath.replace(']', '\0');
				
				try { // toDi will throw null exception if failed
					// TODO add handler for null - defer to driver, not handled here
					m_view.setKeyReadOnly(true); // disable editing of key
					textItem = dataItem.toDi(selectionPath).getItem();
					textKey = Integer.toString(dataItem.toDi(selectionPath).getKey());
					m_view.setStatus("Updateable");
					updateState = buttonAction._TREE; 
				} catch(Exception x){ m_view.setStatus("Cannot parse " + selectionPath); }
				finally { // setting to blank if non readable
					m_view.setItem(textItem);
					m_view.setKey(textKey);
				}
			} else { 
				m_view.setStatus(rootSelected); 
				updateState = buttonAction._NONE; 
				m_view.setKeyReadOnly(false); // enable editing of key	
			}
		}
    }
}
