package mi.paquete.progra;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class Frames extends JFrame implements ActionListener{
	
	JMenuBar mb;
	JMenu file;
	JMenuItem selectFrom;
	JMenuItem orderByCodigo;
	JMenuItem orderByNombre;
	public static JTextArea ta;
	String text = "";
	
	public void createFrame() {
		selectFrom = new JMenuItem("Seleccione archivo");
		selectFrom.addActionListener(this);
		
		orderByCodigo = new JMenuItem("Ordenar por Codigo");
		orderByCodigo.addActionListener(this);
		
		orderByNombre = new JMenuItem("Ordenar por Nombre");
		orderByNombre.addActionListener(this);
		
		file = new JMenu("File");
		file.add(selectFrom);
		file.add(orderByCodigo);
		file.add(orderByNombre);
		
		mb = new JMenuBar();
		mb.setBounds(0,0,800,20);
		mb.add(file);
		
		ta = new JTextArea(800,800);
		ta.setBounds(0,20,800,800);
		
		add(mb);
		add(ta);
		
		this.setTitle("Orden de datos");
		this.setLocation(200, 100);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == selectFrom){
		    JFileChooser fc = new JFileChooser();
		    int i = fc.showOpenDialog(this);
		    
		    if(i == JFileChooser.APPROVE_OPTION){
		        File f = fc.getSelectedFile();
		        String filePath = f.getPath();
		        App.fromFile = filePath;
		        text = "";
		        text = text + "Archivo de origen: " + App.fromFile + "\n"; 
		        ta.setText(text);
		    }
		}
		
		if (e.getSource() == orderByCodigo || e.getSource() == orderByNombre) {
			try {
				if (App.fromFile != "") {
					this.execute(e);
					
				} else {
					text = text + "Debe seleccionar un archivo para poder continuar"+ "\n"; ta.setText(text);
					
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void execute(ActionEvent e) throws FileNotFoundException, IOException {
		ManageDocument document = new ManageDocument();
		
		Tree<String> orderByCodigo = new AVLTree<>();
		Tree<String> orderByNombre = new AVLTree<>();
		
		if (e.getSource() == this.orderByCodigo) {
			App.toFile = App.fromFile.replaceFirst(".txt", "").replaceFirst(".TXT", "") + "_por_codigo.txt";
			
		} else if (e.getSource() == this.orderByNombre) {
			App.toFile = App.fromFile.replaceFirst(".txt", "").replaceFirst(".TXT", "") + "_por_nombre.txt";
			
		}
		text = text + "---------------------------" + "\n"; ta.setText(text);
		text = text + "Archivo de destino: " + App.toFile + "\n";  ta.setText(text);
		text = text + "\n"; ta.setText(text);
		text = text + "Procesando..." + "\n"; ta.setText(text);
		text = text + "\n"; ta.setText(text);
		
		
		String fromFile = App.fromFile;
		String toFile = App.toFile;
		
		List<String> documentList = new ArrayList<>();
		List<Curso> cursotList = new ArrayList<>();
		
		documentList = document.getContent(fromFile);
		
		if (documentList != null) {
			for (Object line : documentList) {
				Curso curso = new Curso(line.toString());
				cursotList.add(curso);
			}
		}
		
		if (cursotList != null) {
			for (Curso curso : cursotList) {
				
				String codigo = curso.getCodigo() + String.join("", Collections.nCopies(30, " ")).substring(curso.getCodigo().length());
				String nombre = curso.getNombre() + String.join("", Collections.nCopies(30, " ")).substring(curso.getNombre().length());
				String seccion = curso.getSeccion() + String.join("", Collections.nCopies(30, " ")).substring(curso.getSeccion().length());
				
				if (e.getSource() == this.orderByCodigo) {
					orderByCodigo.insert(codigo + nombre + seccion, curso);
					
				} else if (e.getSource() == this.orderByNombre) {
					orderByNombre.insert(nombre + seccion, curso);
					
				} else {
					// nada
				}
				
			}
		}
		
		document.deleteContent(toFile);
		
		if (e.getSource() == this.orderByCodigo) {
			orderByCodigo.traverse(toFile);
			text = text + "Ejecutado correctamente" + "\n"; ta.setText(text);
			
		} else if (e.getSource() == this.orderByNombre) {
			orderByNombre.traverse(toFile);
			text = text + "Ejecutado correctamente" + "\n"; ta.setText(text);
			
		}
	}
}
