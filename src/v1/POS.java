package v1;

import java.io.*;
import java.util.*;

public class POS 
{
	public static void main(String[] args) throws IOException 
	{
		Vector<String[]> artikli = new Vector<String[]>();
		Vector<String[]> racuni = new Vector<String[]>();

		ucitajIzFajla(artikli, "artikliV1.csv");
		ucitajIzFajla(racuni, "racuniV1.csv");

		String ulaz = "q";
		do
		{
			System.out.print("Izaberite:\n"
					+ "1) Unos artikla\n"
					+ "2) Provera artikala\n"
					+ "3) Unos racuna\n"
					+ "4) Izlistavanje racuna\n"
					+ "q) Izlaz iz programa\n\n"
					+ "Vas izbor: ");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			ulaz = br.readLine();
			if (ulaz.length() > 1)
			{
				System.out.println("Nope!");
				return;
			} 

			switch(ulaz)
			{
			case "1":
				artikli.add(unesiArtikal(artikli));
				break;
			case "2":
				stampajArtikle(artikli);
				break;
			case "3":
				racuni.add(unesiRacun(artikli, racuni.size()+1));
				break;
			case "4":
				stampajRacune(racuni, artikli);
			}
		}while(!ulaz.equals("q"));
		
		zapisiUfajl(artikli, "artikliV1.csv");
		zapisiUfajl(racuni, "racuniV1.csv");
	}

	public static void stampajRacune(Vector<String[]> racuni, Vector<String[]> artikli)
	{
		for (String[] racun: racuni)
		{
			System.out.println("Racun broj: " + racun[0] + "\tVreme izdavanja: " + "Nem' pojma :D");
			System.out.println("======================================================");
			for (int i = 4; i < racun.length; i += 2)
			{
				String naziv = racun[i-1];
				int kol = Integer.parseInt(racun[i]);
				String jed = infoArtikal(artikli, racun[i-1], "jedinica" );

				double nabavnaCena = Double.parseDouble(infoArtikal(artikli, racun[i-1], "nabavna" ));
				int marzaProc = Integer.parseInt(infoArtikal(artikli, racun[i-1], "marza" ));
				int porezProc = Integer.parseInt(infoArtikal(artikli, racun[i-1], "porez" ));
				double marza = nabavnaCena * marzaProc * Math.pow(10 , - 2);
				double porez = (nabavnaCena + marza) * porezProc * Math.pow(10 , - 2);
				double cena = (nabavnaCena + marza + porez) * kol;
				System.out.println(naziv + "  ---  " + kol + " " + jed 
						+ " ---- " + cena + " din");
			}
			System.out.println("======================================================");
			if (!racun[2].equals("0"))
			{
				System.out.println("Rabat: " + racun[2] + "%");
			} 

			System.out.println("Total: " + racun[1]);
			System.out.println("\n");
		}
		
	}

	public static String[] unesiArtikal(Vector<String[]> artikli) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Unesite naziv artikla: ");
		String naziv = br.readLine();
		System.out.print("Unesite novo stanje artikla: ");
		int stanje = Integer.parseInt(br.readLine());
		System.out.print("Unesite mernu jedinicu artikla: ");
		String jed = br.readLine();
		System.out.print("Unesite nabavnu cenu artikla: ");
		double nC = Double.parseDouble(br.readLine());
		System.out.print("Unesite marzu artikla: ");
		int marzaP = Integer.parseInt(br.readLine());
		System.out.print("Unesite porez artikla: ");
		int porezP = Integer.parseInt(br.readLine());
		String artikal = naziv + ";" + nC + ";" + stanje + ";" + jed + ";"
				+ marzaP + ";" + porezP;
		
		for (int i = 0; i < artikli.size(); i++)
		{
			if (artikli.get(i)[0].equals(naziv))
			{
				artikli.remove(i);
			}
		}
		
		return artikal.split(";");
	}

	public static String[] unesiRacun(Vector<String[]> artikli, int brRac) throws IOException
	{
		String ulaz = null;
		String racun = "";
		String artikliLista = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Unesite rabat u % ili 0: ");
		int rabat = Integer.parseInt(br.readLine());
		double total = 0;

		do
		{
			System.out.print("Unesi ime artikla: ");
			String ime = br.readLine();
			System.out.print("Unesi kolicinu: ");
			int kolicina = Integer.parseInt(br.readLine());
			for (int i = 0; i < artikli.size(); i++)
			{
				if (artikli.get(i)[0].equals(ime))
				{
					if (Integer.parseInt(artikli.get(i)[2]) > kolicina)
					{
						artikli.get(i)[2] = String.valueOf(Integer.parseInt(artikli.get(i)[2]) - kolicina);
						artikliLista += ime + ";" + kolicina + ";";
					}
				}
			}

			System.out.println("Trenutno na racunu: ");
			for (int i = 1; i < artikliLista.split(";").length; i += 2)
			{
				String naziv = artikliLista.split(";")[i-1];
				int kol = Integer.parseInt(artikliLista.split(";")[i]);
				String jed = infoArtikal(artikli, artikliLista.split(";")[i-1], "jedinica" );

				double nabavnaCena = Double.parseDouble(infoArtikal(artikli, artikliLista.split(";")[i-1], "nabavna" ));
				int marzaProc = Integer.parseInt(infoArtikal(artikli, artikliLista.split(";")[i-1], "marza" ));
				int porezProc = Integer.parseInt(infoArtikal(artikli, artikliLista.split(";")[i-1], "porez" ));
				double marza = nabavnaCena * marzaProc * Math.pow(10 , - 2);
				double porez = (nabavnaCena + marza) * porezProc * Math.pow(10 , - 2);
				double cena = (nabavnaCena + marza + porez) * kol;
				total += cena;
				System.out.println(naziv + "  ---  " + kol + " " + jed 
						+ " ---- " + cena + " din");
			}
			System.out.print("Zelite da nastavite? (y/n): ");
			ulaz = br.readLine();
		} while (!ulaz.equals("n"));

		if (rabat != 0)
		{
			total -= total * rabat * Math.pow(10 , - 2);
		}

		racun += brRac + ";" + total + ";" + rabat + ";" + artikliLista;

		return racun.split(";");
	}

	public static String infoArtikal(Vector<String[]> artikli, String naziv, String sta)
	{
		String[] artikal = null;
		for (String[] art: artikli)
		{
			if (art[0].equals(naziv))
			{
				artikal = art;
			}
		}

		if (artikal == null)
		{
			return null;
		}

		switch (sta)
		{
		case "nabavna":
			return artikal[1];
		case "stanje":
			return artikal[2];
		case "jedinica":
			return artikal[3];
		case "marza":
			return artikal[4];
		case "porez":
			return artikal[5];	
		}
		return null;
	}
	
	public static void stampajArtikle(Vector<String[]> artikli)
	{
		for (String[] art: artikli)
		{
			System.out.println("Naziv: " + art[0] +
					"   Nabavna cena: " + art[1] +
					"   Marza: " + art[4] + "%" +
					"   Porez: " + art[5] + "%" +
					"   Stanje: " + art[2]);
		}
	}

	public static void ucitajIzFajla(Vector<String[]> gde, String naziv)
	{
		BufferedReader citacFajla = null;
		try
		{
			citacFajla = new BufferedReader(new FileReader(naziv));

			while (citacFajla.ready())
			{
				String red = citacFajla.readLine();
				gde.add(red.split(";"));
			}

		} catch (IOException e)
		{
			System.out.println("Doslo je do greske!!\n" + e);
			System.exit(-1);
		} finally
		{
			try
			{
				citacFajla.close();
			} catch (IOException e)
			{
				System.out.println("Uffff, ne mogu ni da zatvorim :'(\n" + e);
				System.exit(-1);
			}
		}
	}
	
	public static void zapisiUfajl(Vector<String[]> sta, String gde)
	{
		BufferedWriter upisivac = null;
		
		try
		{
			upisivac = new BufferedWriter(new FileWriter(gde, false));
			for(String[] red: sta)
			{
				String spojeno = "";
				for(String element: red)
				{
					spojeno += element + ";";
				}
				upisivac.write(spojeno + System.lineSeparator());
			}
			
		} catch (IOException e)
		{
			System.out.println("Doslo je do greske!!\n" + e);
			System.exit(-1);
		} finally
		{
			try
			{
				upisivac.close();
			} catch (IOException e)
			{
				System.out.println("Uffff, ne mogu ni da zatvorim :'(\n" + e);
				System.exit(-1);
			}
		}
	}
}
