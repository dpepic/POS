package interfejs;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import v2.*;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GlaviProzor {

	private JFrame frame;
	private JTable table;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JButton btnUnesiArtikal;

	public void podesiFrame(boolean prikaz)
	{
		frame.setVisible(prikaz);
	}

	/**
	 * Create the application.
	 */
	public GlaviProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 354);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 293);
		frame.getContentPane().add(tabbedPane);

		panel = new JPanel();
		tabbedPane.addTab("Artikli", null, panel, null);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 399, 142);
		panel.add(scrollPane);

		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] 
						{
						},
						new String[] {
								"Naziv artikla", "Na lageru", "Nabavna cena", "Prodajna cena"
						}
				) {
			Class[] columnTypes = new Class[] {
					String.class, Integer.class, Double.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(125);
		table.getColumnModel().getColumn(3).setPreferredWidth(114);
		scrollPane.setViewportView(table);

		JButton btnObrisiArtikal = new JButton("Obrisi artikal");
		btnObrisiArtikal.setBounds(10, 154, 135, 23);
		panel.add(btnObrisiArtikal);

		btnUnesiArtikal = new JButton("Unesi Artikal");
		btnUnesiArtikal.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				UnosArtikala prozorZaUnos = new UnosArtikala(table);
				prozorZaUnos.prikaziProzor();
	
			}
		});
		btnUnesiArtikal.setBounds(10, 188, 135, 23);
		panel.add(btnUnesiArtikal);
		btnObrisiArtikal.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				if (table.getSelectedRows().length != 0)
				{
					int odg = JOptionPane.showConfirmDialog(frame, "Sigurni ste?");

					if (odg == 0)
					{
						while (table.getSelectedRows().length > 0)
						{	 
							Artikal.sviArtikli.remove(table.getSelectedRows()[0]);
							((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRows()[0]);
						}
					}
				}
			}
		});
		
		Sistem.ucitajArtikleUtabelu(table);
		
	}
	
	
}
