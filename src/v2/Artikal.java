package v2;
import java.util.*;

public class Artikal 
{
	String naziv;
	double nabavnaCena;
	double prodajnaCena;
	Integer marzaProc;
	double marza;
	Integer porezProc;
	double porez;
	int stanje;
	merneJedinice jedinica;
	
	public static Stack<Artikal> sviArtikli = Sistem.ucitajArtikle();
	
	public Object[] zaPrikaz;
	
	public String dajNaziv()
	{
		return this.naziv;
	}
	
	public Artikal(String naz, double nC, int mP, int pP, merneJedinice j, int kol)
	{
		if ((mP < 5 || mP > 200) || (pP < 0))
		{
			System.out.println("Nekorektna marza ili porez!!");
			System.exit(-1);
		}
		
		this.naziv = naz;
		this.nabavnaCena = nC;
		this.marzaProc = mP;
		this.porezProc = pP;
		this.jedinica = j;
		this.stanje = kol;
		
		this.marza = this.nabavnaCena * this.marzaProc * Math.pow(10 , - 2);
		this.porez = (this.nabavnaCena + this.marza) * this.porezProc * Math.pow(10 , - 2);
		this.prodajnaCena = this.nabavnaCena + this.marza + this.porez;
		
		zaPrikaz = new Object[4];
		zaPrikaz[0] = this.naziv;
		zaPrikaz[1] = this.stanje;
		zaPrikaz[2] = this.nabavnaCena;
		zaPrikaz[3] = this.prodajnaCena;
	}
	
	public void oduzmiKolicinu(int kol)
	{
		if (this.stanje < kol)
		{
			System.out.println("Previse!!");
			System.exit(-1);
		} else
		{
			this.stanje -= kol;
		}
	}
	
	public double dajCenu(boolean porez, boolean marza)
	{
		if (porez == true && marza == true)
		{
			return this.nabavnaCena + this.marza + this.porez;
		} else if (porez == false && marza == false)
		{
			return this.nabavnaCena;
		} else if (porez == false)
		{
			return this.nabavnaCena + this.marza; 
		} else
		{
			return -1;
		}
	}
	
	public void stampajArtikal()
	{
		System.out.println("Naziv: " + this.naziv +
				           "   Nabavna cena: " + this.nabavnaCena +
				           "   Marza: " + this.marzaProc + "%" +
				           "   Porez: " + this.porezProc + "%" +
				           "   Stanje: " + this.stanje);
	}
}
