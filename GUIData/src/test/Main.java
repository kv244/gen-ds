package test;

// TODO Main
// refactor file load and engine check in driver?
// check engine properties... HS replace if already exists?

import java.awt.EventQueue;

public class Main {

public static void main(String[] args) {
	
	View view = new View();
	Controller 	controller = new Controller(view); 
		//needs to be instantiated for the handlers to work
	        	        
   	EventQueue.invokeLater(new Runnable() {
   			public void run() {
   				try {
   					view.setFrame();
   				} catch (Exception e) {
   					e.printStackTrace();
   				}
   			}
   		});
   	}
}