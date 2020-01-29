import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
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
		
		
		fakeLogData(textArea);
		
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
