package jcolonia.daw2023.junio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BocetoVentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 20240530000L;
	private JPanel panelGeneral;
	private JTabbedPane panelPestañas;
	private JPanel panelPrincipal;
	private ModeloTablaPaíses modeloPaíses;
	private JPanel panelBotones;
	private JButton botónInsertar;
	private JScrollPane panelTablaDeslizante;
	private JTable tablaPaíses;
	private JTextField textoPais;
	private ConsultaPaises2 consulta;
	private JTextField textoAviso;
	private JButton botonReset;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] argumentos) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(() -> {
			try {
				BocetoVentanaPrincipal frame = new BocetoVentanaPrincipal();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BocetoVentanaPrincipal() {
		consulta = new ConsultaPaises2();
		initialize();
	}

	private void initialize() {
		setTitle("Ventana países");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 411);
		panelGeneral = new JPanel();
		panelGeneral.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelGeneral);
		panelGeneral.setLayout(new BorderLayout(0, 0));
		panelGeneral.add(getPanelPestañas(), BorderLayout.CENTER);
		panelGeneral.add(getTextoAviso(), BorderLayout.SOUTH);
	}

	private JTabbedPane getPanelPestañas() {
		if (panelPestañas == null) {
			panelPestañas = new JTabbedPane(JTabbedPane.TOP);
			panelPestañas.setBorder(new EmptyBorder(10, 10, 10, 10));
			panelPestañas.addTab("Listado", null, getPanelPrincipal(), null);

		}
		return panelPestañas;
	}

	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setBorder(new EmptyBorder(10, 0, 0, 0));
			panelPrincipal.setLayout(new BorderLayout(0, 10));

			panelPrincipal.add(getPanelBotones(), BorderLayout.SOUTH);
			panelPrincipal.add(getPanelTablaDeslizante(), BorderLayout.CENTER);
		}
		return panelPrincipal;
	}

	private JScrollPane getPanelTablaDeslizante() {
		if (panelTablaDeslizante == null) {
			panelTablaDeslizante = new JScrollPane();
			panelTablaDeslizante.setBackground(Color.ORANGE);
			panelTablaDeslizante.setViewportView(getTablaPaíses());
		}
		return panelTablaDeslizante;
	}

	private JTable getTablaPaíses() {
		if (tablaPaíses == null) {
			tablaPaíses = new JTable();
			tablaPaíses.setFillsViewportHeight(true);
			tablaPaíses.setShowVerticalLines(true);
			tablaPaíses.setShowHorizontalLines(true);
			tablaPaíses.setGridColor(new Color(255, 228, 181));
			tablaPaíses.setBorder(new LineBorder(new Color(0, 0, 0)));
			tablaPaíses.setAutoCreateRowSorter(true);
			tablaPaíses.setModel(getModeloPaíses());
		}
		return tablaPaíses;
	}

	public ModeloTablaPaíses getModeloPaíses() {
		if (modeloPaíses == null) {
			modeloPaíses = new ModeloTablaPaíses();
		}
		return modeloPaíses;
	}

	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setBorder(new EmptyBorder(0, 0, 0, 0));
			panelBotones.setLayout(new GridLayout(0, 1, 0, 0));
			panelBotones.add(getTextoPais());
			panelBotones.add(getBotónInsertar());
			panelBotones.add(getBotonReset());
		}
		return panelBotones;
	}

	private JButton getBotónInsertar() {
		if (botónInsertar == null) {
			botónInsertar = new JButton("Insertar");
			botónInsertar.addActionListener(new BotónInsertarActionListener());
			botónInsertar.setMnemonic(KeyEvent.VK_I);
		}
		return botónInsertar;
	}

	private class BotónInsertarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ModeloTablaPaíses modelo = getModeloPaíses();
			String nombre;
			nombre = getTextoPais().getText();
			try {
				consulta.consultaPais(nombre);
				modelo.addRow(consulta.getPais());
				lanzarAviso("");
			} catch (BDException ex) {
				lanzarAviso(ex.getMessage());
			}
			
		}
	}
	private class BtnNewButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ModeloTablaPaíses modelo = getModeloPaíses();
			modelo.vaciar();
			consulta.setId(0);
			popupAviso("Tabla restablecida correctamente");
		}
	}
	private JTextField getTextoPais() {
		if (textoPais == null) {
			textoPais = new JTextField();
			textoPais.setHorizontalAlignment(SwingConstants.CENTER);
			textoPais.setColumns(10);
		}
		return textoPais;
	}
	private JTextField getTextoAviso() {
		if (textoAviso == null) {
			textoAviso = new JTextField();
			textoAviso.setHorizontalAlignment(SwingConstants.CENTER);
			textoAviso.setColumns(10);
		}
		return textoAviso;
	}
	
	public void lanzarAviso(String mensaje) {
		getTextoAviso().setText(mensaje);
	}
	
	public void popupAviso(String mensaje) {
		JOptionPane.showMessageDialog(getPanelPestañas(), mensaje, "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private JButton getBotonReset() {
		if (botonReset == null) {
			botonReset = new JButton("Reset");
			botonReset.addActionListener(new BtnNewButtonActionListener());
		}
		return botonReset;
	}
}
