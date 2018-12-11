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

public class GlaviProzor {

	private JFrame frame;
	private JTable table;

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 25, 347, 191);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
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
		btnObrisiArtikal.setBounds(258, 243, 121, 23);
		frame.getContentPane().add(btnObrisiArtikal);

		for (Artikal neki: Artikal.sviArtikli)
		{
			((DefaultTableModel)table.getModel()).addRow(neki.zaPrikaz);
		}

	}
}
