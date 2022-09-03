package com.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {		
		String path = App.class.getResource("").getPath(); 
		String filename = ".//HR.txt";
		BufferedReader in= new BufferedReader(new FileReader(path+filename));
		
        String str;
        while ((str =in.readLine()) != null) {
            System.out.println(str);
            Thread.sleep(150);
        }
		
        System.out.println();
			
		MainUI mainUI = new MainUI();
		mainUI.menu();
	}

}

