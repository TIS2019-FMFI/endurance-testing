package UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class FrameLog {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameLog window = new FrameLog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public FrameLog() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(600, 436);
		frame.setTitle("LOG");
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 570, 340);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		
		HttpClient client = HttpClient.newHttpClient();
		Builder builder = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/log"));
		HttpRequest request = builder.build();
		
		String[] jsonData = {""};
		client.sendAsync(request, BodyHandlers.ofString())
         .thenApply(HttpResponse::body)
         .thenAccept((x) -> {jsonData[0] = x;})
         .join(); 
		
		try {
			Object obj = new JSONParser().parse(jsonData[0]);
			JSONArray zoznam = (JSONArray) obj;
			for(Object x : zoznam) {
				JSONObject j = (JSONObject) x;
				textArea.append("pouzivatel " + j.get("user") + " zmenil " + j.get("updated_what") + ", vykonal " + j.get("updated_how") + ", dňa " + j.get("updated_at") + "\n");
			}
		} catch (org.json.simple.parser.ParseException e1) {
			e1.printStackTrace();
		}
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(458, 366, 102, 25);
		btnExport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Export file");
				
				int userSelection = fileChooser.showSaveDialog(frame);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    
				    try{
			            String content = textArea.getText();
			            String path= fileToSave.getAbsolutePath();
			            File file = new File(path);

			            if (!file.exists()) {
			                file.createNewFile();
			            }

			            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			            BufferedWriter bw = new BufferedWriter(fw);

			            bw.write(content);

			            bw.close();
			            
			            JOptionPane.showMessageDialog(frame, "Successful export to: "+ fileToSave.getAbsolutePath());
			        }
			        catch(Exception e1){
			            System.out.println(e);
			        }
				}
			}
		});
		frame.getContentPane().add(btnExport);
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	@Deprecated
	private void fakeLogData(JTextArea ta) {
		for (int i = 0; i < 100; i++) {
			ta.append("2020-01-28 uzivatel " + (i % 5) + " - vykonal [uprava]\n");
		}
	}
}
