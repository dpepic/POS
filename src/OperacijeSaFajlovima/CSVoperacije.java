package OperacijeSaFajlovima;
import java.io.*;
import java.util.*;

public class CSVoperacije 
{
	public Stack<String[]> rezultat;
	
	public void ucitajFajl(String imeFajla)
	{
		BufferedReader citacFajla = null;
		this.rezultat = new Stack<String[]>();
		try
		{
			citacFajla = new BufferedReader(new FileReader(imeFajla));
			
			while (citacFajla.ready())
			{
				String red = citacFajla.readLine();
				this.rezultat.push(red.split(";"));
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
	
	public void upisUfajl(String imeFajla, boolean nastavi, Stack<String> redoviZaUpis)
	{
		BufferedWriter upisivac = null;
			
		try
		{
			upisivac = new BufferedWriter(new FileWriter(imeFajla, nastavi));
			while (!redoviZaUpis.isEmpty())
			{
				upisivac.write(redoviZaUpis.pop() + System.lineSeparator());
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
