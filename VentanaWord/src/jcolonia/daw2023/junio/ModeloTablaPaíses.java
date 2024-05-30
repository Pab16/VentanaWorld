package jcolonia.daw2023.junio;

import javax.swing.table.DefaultTableModel;

class ModeloTablaPaíses extends DefaultTableModel {
	private static final Class[] TIPO_COLUMNAS = { String.class, String.class,
			String.class };
	private static final boolean[] COLUMNAS_EDITABLES = { true, true, true };
	private static final String[] NOMBRES_COLUMNA = { "País", "Capital",
			"Idioma" };

	public ModeloTablaPaíses() {
		super(NOMBRES_COLUMNA, 0);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return TIPO_COLUMNAS[columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return COLUMNAS_EDITABLES[column];
	}

	public void addRow(País nuevoPaís) {
		addRow(new String[] { nuevoPaís.nombre(), nuevoPaís.capital(),
				nuevoPaís.idioma() });
	}

	public void vaciar() {
		setRowCount(0);
	}
}
