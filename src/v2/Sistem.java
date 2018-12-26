package v2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.text.*;
import OperacijeSaFajlovima.CSVoperacije;

public class Sistem 
{
	static DateFormat formatDatuma = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	
	public static void ucitajArtikleUtabelu(JTable tabelaZaUnos)
	{
		((DefaultTableModel)tabelaZaUnos.getModel()).getDataVector().removeAllElements();
		for (Artikal neki: Artikal.sviArtikli)
		{
			((DefaultTableModel)tabelaZaUnos.getModel()).addRow(neki.zaPrikaz);
		}
	}
	
	public static void upisiArtikle()
	{
		Stack<String> zaUpis = new Stack<String>();

		for (Artikal art: Artikal.sviArtikli)
		{
			zaUpis.push(art.naziv + ";" + art.nabavnaCena + ";" + art.stanje + ";" +
					art.jedinica + ";" + art.marzaProc + ";" + art.porezProc);
		}

		CSVoperacije upis = new CSVoperacije();
		upis.upisUfajl("artikli.csv", false, zaUpis);
	}

	public static void upisiRacune()
	{
		Stack<String> zaUpis = new Stack<String>();
		for (Racun rac: Racun.sviRacuni)
		{	
			String artikli = ""; 
			for (Artikal art: rac.artikli.keySet())
			{
				artikli += art.naziv + ";" + rac.artikli.get(art) + ";";
			}
			zaUpis.push(rac.brOvogRacuna + ";" + formatDatuma.format(rac.datum) + ";" + rac.rabat + ";" + artikli);
		}

		CSVoperacije upis = new CSVoperacije();
		upis.upisUfajl("racuni.csv", false, zaUpis);
	}

	public static Stack<Racun> ucitajRacune(Stack<Artikal> artikli)
	{
		Stack<Racun> rezultat = new Stack<Racun>();
		CSVoperacije ucitavamoRacune = new CSVoperacije();
		ucitavamoRacune.ucitajFajl("racuni.csv");
		while (!ucitavamoRacune.rezultat.isEmpty())
		{
			int brojRacuna = Integer.parseInt(ucitavamoRacune.rezultat.peek()[0]);
			Date datum = null;
			try
			{
				datum = formatDatuma.parse(ucitavamoRacune.rezultat.peek()[1]);
			} catch (ParseException e)
			{
				System.out.println("Greska sa datumom!");
				System.exit(-1);
			}
			int rabat = Integer.parseInt(ucitavamoRacune.rezultat.peek()[2]);

			Hashtable<Artikal, Integer> artikliHash = new Hashtable<Artikal, Integer>();
			for (int i = 4; i < ucitavamoRacune.rezultat.peek().length; i += 2)
			{
				for (Artikal art: artikli)
				{
					if (art.naziv.equals(ucitavamoRacune.rezultat.peek()[i-1]))
					{
						artikliHash.put(art, Integer.parseInt(ucitavamoRacune.rezultat.peek()[i]));
					}
				}
			}

			ucitavamoRacune.rezultat.pop();
			rezultat.push(new Racun(brojRacuna, datum, rabat, artikliHash));
		}

		return rezultat;
	}

	public static Stack<Artikal> ucitajArtikle()
	{
		Stack<Artikal> rezultat = new Stack<Artikal>();
		CSVoperacije ucitavamoArtikle = new CSVoperacije();
		ucitavamoArtikle.ucitajFajl("artikli.csv");
		while (!ucitavamoArtikle.rezultat.isEmpty())
		{
			String naziv = ucitavamoArtikle.rezultat.peek()[0];
			Double nCena = Double.parseDouble(ucitavamoArtikle.rezultat.peek()[1]);
			int kolicina = Integer.parseInt(ucitavamoArtikle.rezultat.peek()[2]);
			merneJedinice jed = merneJedinice.valueOf(ucitavamoArtikle.rezultat.peek()[3]);
			Integer mP = Integer.parseInt(ucitavamoArtikle.rezultat.peek()[4]);
			Integer pP = Integer.parseInt(ucitavamoArtikle.rezultat.pop()[5]);
			rezultat.push(new Artikal(naziv, nCena, mP, pP, jed, kolicina));
		}

		return rezultat;
	}

	public static void stampajMeni()
	{
		System.out.print("Izaberite:\n"
				+ "1) Unos artikla\n"
				+ "2) Provera artikala\n"
				+ "3) Unos racuna\n"
				+ "4) Izlistavanje racuna\n"
				+ "q) Izlaz iz programa\n\n"
				+ "Vas izbor: ");
	}

	public static void stampajRacune()
	{
		for (Racun rac: Racun.sviRacuni)
		{
			rac.stampajRacun();
			System.out.println("\n");
		}
	}

	public static void stampajArtikle()
	{
		for (Artikal art: Artikal.sviArtikli)
		{
			art.stampajArtikal();
		}
	}
	public static String procitajUnos() throws IOException
	{
		String ulaz = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ulaz = br.readLine();
		if (ulaz.length() > 1)
		{
			System.out.println("Nope!");
			return null;
		} else
		{
			return ulaz;
		}
	}

	public static void unesiArtikal() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Unesite naziv artikla: ");
		String naziv = br.readLine();
		System.out.print("Unesite novo stanje artikla: ");
		int stanje = Integer.parseInt(br.readLine());
		System.out.print("Unesite mernu jedinicu artikla: ");
		merneJedinice jed = merneJedinice.valueOf(br.readLine());
		System.out.print("Unesite nabavnu cenu artikla: ");
		double nC = Double.parseDouble(br.readLine());
		System.out.print("Unesite marzu artikla: ");
		int marzaP = Integer.parseInt(br.readLine());
		System.out.print("Unesite porez artikla: ");
		int porezP = Integer.parseInt(br.readLine());

		Artikal art = new Artikal(naziv, nC, marzaP, porezP, jed, stanje);

		for (Artikal arti: Artikal.sviArtikli)
		{
			if (arti.naziv.equals(art.naziv))
			{
				Artikal.sviArtikli.remove(arti);
			}
		}

		Artikal.sviArtikli.add(art);
	}

	public static Racun unesiRacun() throws IOException
	{
		String ulaz = null;
		Racun rac = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Unesite rabat u % ili 0: ");
		ulaz = br.readLine();
		rac = new Racun(Integer.parseInt(ulaz));
		do
		{
			System.out.print("Unesi ime artikla: ");
			ulaz = br.readLine();
			System.out.print("Unesi kolicinu: ");
			int kolicina = Integer.parseInt(br.readLine());

			for (Artikal art: Artikal.sviArtikli)
			{
				if (art.naziv.equals(ulaz))
				{
					art.oduzmiKolicinu(kolicina);
					rac.dodajArtikal(art, kolicina);
				}
			}

			System.out.println("Trenutno na racunu: ");
			rac.stampajStavke();
			System.out.print("Zelite da nastavite? (y/n): ");
			ulaz = br.readLine();
		} while (!ulaz.equals("n"));
		rac.saberiRacun();
		return rac;
	}

}
