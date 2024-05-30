package jcolonia.daw2023.junio;

import javax.swing.table.DefaultTableModel;

class ModeloTablaPaíses extends DefaultTableModel {
	private static final Class[] TIPOS_COLUMNAS = { Integer.class, String.class, String.class, String.class };
	private static final boolean[] COLUMNAS_EDITABLES = { true, true, true, true };
	private static final String[] NOMBRES_COLUMNAS = { "#", "País", "Capital", "Idioma" };

	public ModeloTablaPaíses() {
		super(NOMBRES_COLUMNAS, 0);
	}

	@Override
	public Class<?> getColumnClass(int columna) {
		return TIPOS_COLUMNAS[columna];
	}

	@Override
	public boolean isCellEditable(int fila, int columna) {
		return COLUMNAS_EDITABLES[columna];
	}

	public void addRow(País nuevoPaís) {
		addRow(new Object[] { nuevoPaís.id(), nuevoPaís.nombre(), nuevoPaís.capital(), nuevoPaís.idioma() });
	}

	public void vaciar() {
		setRowCount(0);
	}
}
