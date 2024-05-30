package jcolonia.daw2023.junio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ConsultaPaises2 {

	Vector<String[]> lista = new Vector<String[]>();
	public ConsultaPaises2() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Busca un pais en la base de datos y devuelve sus características.
	 * @param pais
	 * @return infoPais la información de los paises
	 * @throws BDException 
	 */
	public void consultaPais(String pais) throws BDException {
		String fuente = "jdbc:sqlite:world2.db";
		String consulta = String.format("SELECT * from Country WHERE Name LIKE ?");
		
		try(	Connection conexión = DriverManager.getConnection(fuente);
				Statement sentenciaSQL = conexión.createStatement();
				PreparedStatement preparaciónSQL = conexión.prepareStatement(consulta);
				){
			
			preparaciónSQL.setString(1, pais);
			ResultSet loteDatos = preparaciónSQL.executeQuery();
			sentenciaSQL.setQueryTimeout(5);
			rellenarLista(loteDatos);
			

		}catch(SQLException ex) {
			System.err.printf("Error: %s \n--%s--\n%s",
					"No se ha creado la base de datos SQLite",
					ex.getLocalizedMessage());
			ex.getStackTrace();
			System.exit(1);
		}	
	}
	
	/**
	 * Rellena la lista con un conjunto de características de un país: nombre, capital e idioma 
	 * 
	 * @param loteDatos
	 * @throws BDException
	 * @throws SQLException
	 */
	public void rellenarLista(ResultSet loteDatos) throws BDException, SQLException {
		String nombre = "", capital = "", idioma = "";
		while (loteDatos.next()) {
			nombre = loteDatos.getString("Name");
			capital = loteDatos.getString("Capital");
			idioma += loteDatos.getString("Language");
			idioma += " ";
		}
		
		if(nombre != "") {
			String infoPais[] = {nombre, capital, idioma};
			lista.add(infoPais);
		}else {
			throw new BDException("Country not found!");
		}
	}
	
	public Vector<String[]> getLista() {
		return lista;
	}
	
	public void vaciarLista() {
		lista.removeAllElements();
	}
	
	public static void main(String[] args) {
		try {
			ConsultaPaises2 consulta = new ConsultaPaises2();
			Vector<String[]> a = new Vector<String[]>();
			consulta.consultaPais("Spain");
			a = consulta.getLista();
			System.out.println(a.toString());
			
		} catch (BDException e) {
			e.printStackTrace();
		}
	}
}
