package test;

import java.awt.EventQueue;

import javax.swing.*;

public class CMain {

public static void main(String[] args) {
	Main       		view       = new Main();
	GUIController 	controller = new GUIController(view);
	        	        
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
