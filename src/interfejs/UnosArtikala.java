package interfejs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import v2.Artikal;
import v2.Sistem;
import v2.merneJedinice;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UnosArtikala {

	private JFrame frame;
	private JTextField txtNaziv;
	private JTextField txtStanje;
	private JTextField txtNabavna;
	private JTextField txtMarza;
	private JTextField txtPorez;
	private JTable tabelaZaUnos;
	
	public void prikaziProzor()
	{
		this.frame.setVisible(true);
		txtNaziv.setText("");
		txtStanje.setText("");
		txtNabavna.setText("");
		txtMarza.setText("");
		txtPorez.setText("");
	}


	/**
	 * Create the application.
	 */
	
	
	public UnosArtikala(JTable tabela) 
	{
		this.tabelaZaUnos = tabela;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 383, 374);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNazivArtikla = new JLabel("Naziv artikla:");
		lblNazivArtikla.setBounds(24, 27, 92, 14);
		frame.getContentPane().add(lblNazivArtikla);
		
		JLabel lblStanje = new JLabel("Stanje:");
		lblStanje.setBounds(24, 55, 92, 14);
		frame.getContentPane().add(lblStanje);
		
		JLabel lblMernaJedinica = new JLabel("Merna jedinica:");
		lblMernaJedinica.setBounds(24, 88, 92, 14);
		frame.getContentPane().add(lblMernaJedinica);
		
		JLabel lblNabavna = new JLabel("Nabavna:");
		lblNabavna.setBounds(24, 122, 92, 14);
		frame.getContentPane().add(lblNabavna);
		
		JLabel lblMarza = new JLabel("Marza:");
		lblMarza.setBounds(24, 163, 92, 14);
		frame.getContentPane().add(lblMarza);
		
		JLabel lblPorez = new JLabel("Porez:");
		lblPorez.setBounds(24, 188, 92, 14);
		frame.getContentPane().add(lblPorez);
		
		txtNaziv = new JTextField();
		txtNaziv.setBounds(104, 24, 86, 20);
		frame.getContentPane().add(txtNaziv);
		txtNaziv.setColumns(10);
		
		txtStanje = new JTextField();
		txtStanje.setBounds(104, 55, 86, 20);
		frame.getContentPane().add(txtStanje);
		txtStanje.setColumns(10);
		
		JComboBox cmbMerne = new JComboBox();
		String[] mJ = new String[merneJedinice.values().length];
		for (int x = 0; x < mJ.length; x++)
		{
			mJ[x] = merneJedinice.values()[x].toString(); 
		}
		cmbMerne.setModel(new DefaultComboBoxModel(mJ));
		cmbMerne.setBounds(126, 85, 64, 20);
		frame.getContentPane().add(cmbMerne);
		
		txtNabavna = new JTextField();
		txtNabavna.setBounds(126, 119, 86, 20);
		frame.getContentPane().add(txtNabavna);
		txtNabavna.setColumns(10);
		
		txtMarza = new JTextField();
		txtMarza.setBounds(126, 160, 86, 20);
		frame.getContentPane().add(txtMarza);
		txtMarza.setColumns(10);
		
		txtPorez = new JTextField();
		txtPorez.setBounds(126, 185, 86, 20);
		frame.getContentPane().add(txtPorez);
		txtPorez.setColumns(10);
		
		JButton btnUnos = new JButton("Unos");
		btnUnos.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String naziv = txtNaziv.getText();
				Double nC = Double.parseDouble(txtNabavna.getText());
				int marzaP = Integer.parseInt(txtMarza.getText());
				int porezP = Integer.parseInt(txtPorez.getText());
				int stanje = Integer.parseInt(txtStanje.getText());
				merneJedinice jed = merneJedinice.valueOf(cmbMerne.getSelectedItem().toString());
				
				Artikal art = new Artikal(naziv, nC, marzaP, porezP, jed, stanje);

				for (Artikal arti: Artikal.sviArtikli)
				{
					if (arti.naziv.equals(art.naziv))
					{
						Artikal.sviArtikli.remove(arti);
					}
				}
				
				Artikal.sviArtikli.add(art);
				Sistem.ucitajArtikleUtabelu(tabelaZaUnos);
				frame.dispose();
			}
		});
		btnUnos.setBounds(142, 268, 89, 23);
		frame.getContentPane().add(btnUnos);
		
		
	}
}
