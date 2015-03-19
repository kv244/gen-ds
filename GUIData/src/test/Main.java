package test;

// TODO Main
// refactor file load and engine check in driver?
// check all load states (finalize... if file loaded, etc) [aka finish]
// check engine properties... HS replace if already exists?

import java.awt.EventQueue;

import javax.swing.*;

public class Main {

public static void main(String[] args) {
	
	View view = new View();
	Controller 	controller = new Controller(view);
	        	        
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
