package UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.awt.Color;

public class FrameMakeStructure {

	private JFrame frmVytvorenieStruktury;
	private JTextField txt;
	private JTextField tznummer;
	private JTextField tKunde;
	private JTextField tBezeichnung;
	private JTextField tCisloObjednavky;
	private JTextField tZostava;

	String User = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMakeStructure window = new FrameMakeStructure("");
					window.frmVytvorenieStruktury.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */	
	public FrameMakeStructure(String user) {
		User = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVytvorenieStruktury = new JFrame();
		frmVytvorenieStruktury.setBackground(Color.WHITE);
		frmVytvorenieStruktury.setTitle("Make structure");
		frmVytvorenieStruktury.setSize(600, 400);
		frmVytvorenieStruktury.getContentPane().setLayout(null);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					uploadFile();
				}
		});
		button.setBounds(410, 58, 97, 25);
		frmVytvorenieStruktury.getContentPane().add(button);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmVytvorenieStruktury.setVisible(false);
				
				submit();
			}
		});
		btnSubmit.setBounds(433, 262, 123, 55);
		frmVytvorenieStruktury.getContentPane().add(btnSubmit);
		
		JLabel lblFile = new JLabel("File:");
		lblFile.setBounds(51, 62, 56, 16);
		frmVytvorenieStruktury.getContentPane().add(lblFile);
		
		txt = new JTextField();
		txt.setText("No File selected");
		txt.setBounds(79, 59, 314, 22);
		frmVytvorenieStruktury.getContentPane().add(txt);
		txt.setColumns(10);
		
		JLabel lblAutomatic = new JLabel("Automatic:");
		lblAutomatic.setBounds(31, 23, 79, 16);
		frmVytvorenieStruktury.getContentPane().add(lblAutomatic);
		
		JLabel lblManual = new JLabel("Manual:");
		lblManual.setBounds(31, 148, 56, 16);
		frmVytvorenieStruktury.getContentPane().add(lblManual);
		
		JLabel lblNewLabel = new JLabel("Zeichnungsnummer:");
		lblNewLabel.setBounds(51, 177, 123, 16);
		frmVytvorenieStruktury.getContentPane().add(lblNewLabel);
		
		JLabel lblKunde = new JLabel("Kunde:");
		lblKunde.setBounds(51, 206, 56, 16);
		frmVytvorenieStruktury.getContentPane().add(lblKunde);
		
		JLabel lblBezeichnung = new JLabel("Bezeichnung:");
		lblBezeichnung.setBounds(51, 236, 79, 16);
		frmVytvorenieStruktury.getContentPane().add(lblBezeichnung);
		
		tznummer = new JTextField();
		tznummer.setBounds(186, 174, 173, 22);
		frmVytvorenieStruktury.getContentPane().add(tznummer);
		tznummer.setColumns(10);
		
		tKunde = new JTextField();
		tKunde.setBounds(186, 206, 173, 22);
		frmVytvorenieStruktury.getContentPane().add(tKunde);
		tKunde.setColumns(10);
		
		tBezeichnung = new JTextField();
		tBezeichnung.setBounds(186, 236, 173, 22);
		frmVytvorenieStruktury.getContentPane().add(tBezeichnung);
		tBezeichnung.setColumns(10);
		
		JLabel lblCisloObjednavky = new JLabel("Cislo objednavky:");
		lblCisloObjednavky.setBounds(51, 272, 105, 16);
		frmVytvorenieStruktury.getContentPane().add(lblCisloObjednavky);
		
		JLabel lblZostava = new JLabel("Zostava:");
		lblZostava.setBounds(51, 301, 56, 16);
		frmVytvorenieStruktury.getContentPane().add(lblZostava);
		
		tCisloObjednavky = new JTextField();
		tCisloObjednavky.setBounds(186, 266, 173, 22);
		frmVytvorenieStruktury.getContentPane().add(tCisloObjednavky);
		tCisloObjednavky.setColumns(10);
		
		tZostava = new JTextField();
		tZostava.setBounds(186, 298, 173, 22);
		frmVytvorenieStruktury.getContentPane().add(tZostava);
		tZostava.setColumns(10);
		
		frmVytvorenieStruktury.setResizable(false);
		frmVytvorenieStruktury.setVisible(true);
		
	}
	
	private void submit() {
		System.out.println("zavolal");
		String source = txt.getText();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Zeichnungsnummer", tznummer.getText()));
		params.add(new BasicNameValuePair("Kunde", tKunde.getText()));
		params.add(new BasicNameValuePair("Bezeichnung", tBezeichnung.getText()));
		params.add(new BasicNameValuePair("Zostava", tZostava.getText()));
		params.add(new BasicNameValuePair("Cislo", tCisloObjednavky.getText()));
		params.add(new BasicNameValuePair("User", User));
		

		// Post data to the server
		HttpPost httppost = new HttpPost("http://localhost:8080/DatabazaZivotnosti/rest/data/newProdukt");
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (rightUntil(source, '\\').equals(source)) {
			//new ExcelReader.readExcel(source);
		} else {
			//new Produkt(tBezeichnung.getText(),tKunde.getText(),tznummer.getText(), tCisloObjednavky.getText(), DATA_SOURCE, tZostava.getText())
		}
	}
	
	/**
	 * Vrati podretaz od posledneho vyskytu zadaneho znaku az po koniec retazca
	 */
	private String rightUntil(String ret, Character ch) {
		StringBuffer sb = new StringBuffer();
		for (int i = ret.length()-1;i >= 0; i--) {
			if (ret.charAt(i) == ch) {
				break;
			}
			sb.append(ret.charAt(i));
		}
		return sb.reverse().toString();
	}

	private void uploadFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frmVytvorenieStruktury);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			
			txt.setText(selectedFile.getAbsolutePath());
		}
	}
	
//	Funkcie na pracu so subormi
//
//	private void copyFile(String source, String destination) {
//		Path sourcepath = Paths.get(source);
//		Path destinationepath = Paths.get(destination);
//		try {
//			Files.copy(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void deleteFile(String filePath) {
//		Path path = Paths.get(filePath);
//		try {
//			Files.delete(path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void deleteEmptyDirectory(String dirPath) {
//		Path path = Paths.get(dirPath);
//		try {
//			Files.delete(path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void deleteNonEmptyDirectory(String dirPath) {
//		Path path = Paths.get(dirPath);
//		try {
//			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void copyEmptyDirectory(String source, String destination) {
//		Path sourcepath = Paths.get(source);
//		Path destinationepath = Paths.get(destination);
//		try {
//			Files.copy(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void copyNonEmptyDirectory(String source, String destination) {
//		Path sourcepath = Paths.get(source);
//		Path destinationepath = Paths.get(destination);
//		try {
//			Files.walk(sourcepath)
//					.forEach(s -> copy(s, destinationepath.resolve(sourcepath.relativize(s))));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	static void copy(Path source, Path dest) {
//		try {
//			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}
//
//	private void moveFile(String source, String destination) {
//		Path sourcepath = Paths.get(source);
//		Path destinationepath = Paths.get(destination);
//
//		try {
//			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void moveEmptyDirectorypublic(String source, String destination) {
//		Path sourcepath = Paths.get(source);
//		Path destinationepath = Paths.get(destination);
//		try {
//			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void moveNonEmptyDirectorypublic(String source, String destination) {
//		Path sourcepath = Paths.get(source);
//		Path destinationepath = Paths.get(destination);
//		try {
//			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
