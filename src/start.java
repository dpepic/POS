import v2.*;
import java.util.*;

import interfejs.GlaviProzor;

import java.awt.EventQueue;
import java.io.*;
import java.text.ParseException;

public class start {

	public static void main(String[] args) throws IOException, ParseException
	{
		String unos = "q";
		System.out.println("Dobrodosli u (vrlo verovatno) najgori POS sistem u univerzumu!");
		System.out.println("==============================================================\n");
		
		System.out.println("Momenat, dok ucitam bazu artikala i racuna...\n");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlaviProzor window = new GlaviProzor();
					window.podesiFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		do
		{
			Sistem.stampajMeni();
			unos = Sistem.procitajUnos();
			
			switch(unos)
			{
			    case "1":
			    	Sistem.unesiArtikal();
			    	break;
				case "2":
					Sistem.stampajArtikle();
					break;
				case "3":
					Sistem.unesiRacun();
					break;
				case "4":
					Sistem.stampajRacune();
			}
		}while (!unos.equals("q"));
		
		Sistem.upisiArtikle();
		Sistem.upisiRacune();
		System.out.println("Caos!!!");
		System.exit(0);
	}

}
