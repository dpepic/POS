package v2;
import java.util.*;

public class Racun 
{
	Hashtable<Artikal, Integer> artikli = new Hashtable<Artikal, Integer>();
	Integer rabat;
	double total;
	Date datum = null;
	int brOvogRacuna;
	
	static int brRacuna = 1;
	
	public static Stack<Racun> sviRacuni = Sistem.ucitajRacune(Artikal.sviArtikli);
	
	public Racun(int rab)
	{
		this.rabat = rab;
		brOvogRacuna = brRacuna;
		brRacuna += 1;
	}
	
	public Racun(int br, Date datum, int rab, Hashtable<Artikal, Integer> arti )
	{
		this.artikli = arti;
		this.datum = datum;
		this.brOvogRacuna = br;
		this.rabat = rab;
		this.saberiRacun();
		brRacuna += 1;
	}
	
	public void dodajArtikal(Artikal art, int kolicina)
	{
		if (artikli.containsKey(art))
		{
			artikli.put(art, artikli.get(art) + kolicina);
		} else
		{
			artikli.put(art, kolicina);
		}
	}
	
	public void saberiRacun()
	{
		this.datum = new Date();
		this.total = 0;
		for (Artikal art: artikli.keySet())
		{
			this.total += art.dajCenu(true, true) * artikli.get(art);
		}
		if (rabat != 0)
		{
			this.total -= this.total * this.rabat * Math.pow(10 , - 2);
		}
	}
	
	public void stampajRacun()
	{
		System.out.println("Racun broj: " + this.brOvogRacuna + "\tVreme izdavanja: " + Sistem.formatDatuma.format(datum));
		System.out.println("======================================================");
		
		for (Artikal art: artikli.keySet())
		{
			System.out.println(art.naziv + "  ---  " + artikli.get(art) + " " + art.jedinica 
					           + " ---- " + art.dajCenu(true, true) * artikli.get(art) + " din");
		}
		System.out.println("======================================================");
		
		if (rabat != 0)
		{
			System.out.println("Rabat: " + this.rabat + "%");
		} 
		
		System.out.println("Total: " + this.total);
	}
	
	public void stampajStavke()
	{
		for (Artikal art: artikli.keySet())
		{
			System.out.println(art.naziv + "  ---  " + artikli.get(art) + " " + art.jedinica 
					           + " ---- " + art.dajCenu(true, true) * artikli.get(art) + " din");
		}
	}
}
