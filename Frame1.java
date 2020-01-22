import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

public class Frame1 {

	private JFrame frmMain;
	private JTextField fromDate;
	private JTextField toDate;
	private JTextField customer;
	private JTextField partNumber;
	private JTextField partPosition;
	private JTextField compositionNumber;
	private JTextField orderNumber;
	private final JSeparator separator = new JSeparator();
	
	/*Koso*/
	private static String whereFilter = "";

	/**
	 * Launch the application.
	 */
	
	public static String getWhereFilter() {
		return whereFilter;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setTitle("Main");
		frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmMain.setJMenuBar(menuBar);
		
		JMenuItem mnUploadFile = new JMenuItem("Upload File");
		mnUploadFile.setBackground(Color.WHITE);
		mnUploadFile.setForeground(Color.BLACK);
		mnUploadFile.setPreferredSize(new Dimension(10, 20));
		mnUploadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Frame2(frmMain);
				//frmMain.setVisible(false);
			}
			
		});
		menuBar.add(mnUploadFile);
		
		JMenuItem mnNewMenu = new JMenuItem("LOG");
		menuBar.setPreferredSize(new Dimension(10, 20));
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Language");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("SLK");
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("ENG");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Log out");
		mntmNewMenuItem_2.setForeground(Color.RED);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new LoginForm();
				frmMain.setVisible(false);
			}
			});
		
		menuBar.add(mntmNewMenuItem_2);
		frmMain.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Filter");
		lblNewLabel.setBounds(12, 13, 1169, 24);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frmMain.getContentPane().add(lblNewLabel);
		
		JLabel lblDateFrom = new JLabel("From Date:");
		lblDateFrom.setBounds(31, 61, 91, 16);
		frmMain.getContentPane().add(lblDateFrom);
		
		JLabel lblDateTo = new JLabel("To Date:");
		lblDateTo.setBounds(187, 61, 91, 16);
		frmMain.getContentPane().add(lblDateTo);
		
		fromDate = new JTextField();
		fromDate.setBounds(101, 58, 74, 22);
		frmMain.getContentPane().add(fromDate);
		fromDate.setColumns(10);
		
		toDate = new JTextField();
		toDate.setColumns(10);
		toDate.setBounds(241, 58, 74, 22);
		frmMain.getContentPane().add(toDate);
		
		JLabel lblNewLabel_1 = new JLabel("Customer:");
		lblNewLabel_1.setBounds(31, 97, 74, 16);
		frmMain.getContentPane().add(lblNewLabel_1);
		
		customer = new JTextField();
		customer.setBounds(101, 94, 214, 22);
		frmMain.getContentPane().add(customer);
		customer.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Part number:");
		lblNewLabel_2.setBounds(369, 100, 91, 16);
		frmMain.getContentPane().add(lblNewLabel_2);
		
		partNumber = new JTextField();
		partNumber.setColumns(10);
		partNumber.setBounds(461, 97, 116, 22);
		frmMain.getContentPane().add(partNumber);
		
		JLabel lblNewLabel_3 = new JLabel("Part position:");
		lblNewLabel_3.setBounds(369, 61, 76, 16);
		frmMain.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Composition number:");
		lblNewLabel_4.setBounds(626, 64, 142, 16);
		frmMain.getContentPane().add(lblNewLabel_4);
		
		JLabel lblOrderNumber = new JLabel("Order number:");
		lblOrderNumber.setBounds(626, 102, 142, 16);
		frmMain.getContentPane().add(lblOrderNumber);
		
		partPosition = new JTextField();
		partPosition.setBounds(461, 62, 116, 22);
		frmMain.getContentPane().add(partPosition);
		partPosition.setColumns(10);
		
		compositionNumber = new JTextField();
		compositionNumber.setColumns(10);
		compositionNumber.setBounds(767, 61, 116, 22);
		frmMain.getContentPane().add(compositionNumber);
		
		orderNumber = new JTextField();
		orderNumber.setColumns(10);
		orderNumber.setBounds(767, 99, 116, 22);
		frmMain.getContentPane().add(orderNumber);
		
		JButton btnNewButton = new JButton("Filter");
		btnNewButton.setBounds(933, 78, 97, 25);
		
		/*Dopisovaná funkcia Kosom*/
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				boolean from = false;
				boolean to = false;
				whereFilter = "";
				String[] values = {fromDate.getText(), toDate.getText(), customer.getText(), partNumber.getText(), partPosition.getText(), compositionNumber.getText(), orderNumber.getText()};
				String[] varNames = {"DateFrom", "DateTo", "Kunde", "Zeichnungsnummer", "Bezeichnung", "zostava", "nrNumber"};
				
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				
					
			    try {
			    	if (fromDate.getText().length() != 0) {
			    		Date datum1 = format.parse(fromDate.getText());
			    		values[0] = format2.format(datum1);
			    		from = true;
			        }
			        if (toDate.getText().length() != 0) {
			        	format.parse(toDate.getText());
			        	Date datum2 = format.parse(toDate.getText());
			    		values[1] = format2.format(datum2);
			        	to = true;
			        }

			    } catch (ParseException e1) {
			        JOptionPane.showMessageDialog(null, "Zlý formát dátumu. Zadajte dátum v tvare DD.MM.RRRR");
			    }
			    
				
				for (int i=0; i < 7; i++) {
					if (values[i].length() > 0) {
						if (i == 0 && from) {
							whereFilter = varNames[i] + " >= " + "'" + values[i] + "'::DATE" + " AND ";
						}
						else if (i == 1 && to) {
							whereFilter = whereFilter + varNames[i] + " <= " + "'" + values[i] + "'" + "::DATE" + " AND ";
						}
						else {
							whereFilter = whereFilter +  varNames[i] + " = " + "'" + values[i] + "'" + " AND ";
						}
					}
				}
				
				if (whereFilter != "") {
					whereFilter = whereFilter.substring(0, whereFilter.length() - 5);
				}
				
				//System.out.println("\"" +  whereFilter + "\"");
				
			}
		});
		
		
		frmMain.getContentPane().add(btnNewButton);
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(0, 139, 10000, 2);
		frmMain.getContentPane().add(separator);
		frmMain.setVisible(true);
	}
}
