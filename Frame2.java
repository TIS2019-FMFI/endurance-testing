import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Frame2 {

	private JFrame frame;
	private JTextField txt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame2 window = new Frame2();
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
	
	public Frame2() {
		initialize(new JFrame());
	}
	
	public Frame2(JFrame frm) {
		initialize(frm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame frm) {
		frame = new JFrame();
		frame.setTitle("UploadFile");
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					uploadFile();
				}
		});
		button.setBounds(110, 131, 97, 25);
		frame.getContentPane().add(button);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Frame1();
				frame.setVisible(false);
				frm.setVisible(false);

				String source = txt.getText();
				
				@Deprecated // testovacie path
				String destination = "C:\\Users\\Koso\\Downloads\\AA_477015_11_outer_bush_zaoblene_IT (2).xlsx";// + rightUntil(source, '\\'); // neskor konstanta
				/*
				 * Komentár od Kosa:
				 * neviem, čo robí rightUntil,  klepol som tam iba cestu natvrdo, všetko sa vykoná správne, excel prejde, všetky exceptions,
				 * napr. ak súbor je otvorený alebo neexistuje, je zlá cesta bla bla bla sa vykonajú
				 * */
				ExcelReader.readExcel(destination);
				System.out.println(ExcelReader.getNr());
			    System.out.println(ExcelReader.getIndex());

//				System.out.println(source);
//				System.out.println(destination);
//				System.out.println(rightUntil(source, '\\'));
				copyFile(source, destination);
			}
		});
		btnSubmit.setBounds(327, 131, 97, 25);
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblFile = new JLabel("File:");
		lblFile.setBounds(77, 102, 56, 16);
		frame.getContentPane().add(lblFile);
		
		txt = new JTextField();
		txt.setText("No File selected");
		txt.setBounds(110, 99, 314, 22);
		frame.getContentPane().add(txt);
		txt.setColumns(10);
		
		frame.setVisible(true);
	}
	
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
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			
			txt.setText(selectedFile.getAbsolutePath());
		}
	}

	// ---------------------------------------------FILES---------------------------------------------------------------------//
	private void deleteFile(String filePath) {
		Path path = Paths.get(filePath);
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteEmptyDirectory(String dirPath) {
		Path path = Paths.get(dirPath);
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteNonEmptyDirectory(String dirPath) {
		Path path = Paths.get(dirPath);
		try {
			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void copyFile(String source, String destination) {
		Path sourcepath = Paths.get(source);
		Path destinationepath = Paths.get(destination);
		try {
			Files.copy(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void copyEmptyDirectory(String source, String destination) {
		Path sourcepath = Paths.get(source);
		Path destinationepath = Paths.get(destination);
		try {
			Files.copy(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void copyNonEmptyDirectory(String source, String destination) {
		Path sourcepath = Paths.get(source);
		Path destinationepath = Paths.get(destination);
		try {
			Files.walk(sourcepath)
					.forEach(s -> copy(s, destinationepath.resolve(sourcepath.relativize(s))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void copy(Path source, Path dest) {
		try {
			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void moveFile(String source, String destination) {
		Path sourcepath = Paths.get(source);
		Path destinationepath = Paths.get(destination);

		try {
			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void moveEmptyDirectorypublic(String source, String destination) {
		Path sourcepath = Paths.get(source);
		Path destinationepath = Paths.get(destination);
		try {
			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void moveNonEmptyDirectorypublic(String source, String destination) {
		Path sourcepath = Paths.get(source);
		Path destinationepath = Paths.get(destination);
		try {
			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// ---------------------------------------------FILES---------------------------------------------------------------------//
	    
}
