package test;

// TODO Main
// refactor file load and engine check in driver?
// check engine properties... HS replace if already exists?
//
// To run: java -cp GUIDynaload.jar;.\* test.Main
// does not work on Mac... but it works from GUI after adding the classpath to manifest
// Or update Manifest: http://stackoverflow.com/questions/250166/noclassdeffounderror-while-trying-to-run-my-jar-with-java-exe-jar-whats-wron

import java.awt.EventQueue;

public class Main {

public static void main(String[] args) {
	
	View view = new View();
	@SuppressWarnings("unused")
	Controller controller = new Controller(view); 
	        	        
   	EventQueue.invokeLater(new Runnable() {
   			@Override
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