import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Main {

	private JFrame frmMain;
	private JTextField tFromDate;
	private JTextField tToDate;
	private JTextField tCustomer;
	private JTextField tPartNumber;
	private JTextField tPartPosition;
	private JTextField tCompositionNumber;
	private JTextField tOrderNumber;
	private final JSeparator separator = new JSeparator();
	
	private JLabel lFromDate;
	private JLabel lToDate;
	private JLabel lCustomer;
	private JLabel lPartNumber;
	private JLabel lPartPosition;
	private JLabel lCompositionNumber;
	private JLabel lOrderNumber;
	
	private JMenu menu;
	private JMenuItem mnUploadFile;
	private JMenuItem mnLOG;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmNewMenuItem_2;
	
	/*Koso*/
	private static String whereFilter = "";
	private JList<?> list;
	private JScrollPane listScroller;
	private String[] dataForJlist;
	private ArrayList<String[]> backData = new ArrayList<String[]>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
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
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setTitle("Main");
		frmMain.setBounds(0, 0, 1920, 1080);
		frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmMain.setJMenuBar(menuBar);
		
		menu = new JMenu("Menu");
		menu.setOpaque(true);
		menu.setForeground(Color.YELLOW);
		menu.setBackground(Color.BLACK);
		menu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(menu);
		
		mnUploadFile = new JMenuItem("Make structure");
		mnUploadFile.setHorizontalAlignment(SwingConstants.LEFT);
		mnUploadFile.setBackground(Color.yellow);
		mnUploadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameMakeStructure();
			}
			
		});
		//menuBar.add(mnUploadFile);
		menu.add(mnUploadFile);
		
		mnLOG = new JMenuItem("LOG");
		mnLOG.setHorizontalAlignment(SwingConstants.LEFT);
		mnLOG.setBackground(Color.GREEN);
		mnLOG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameLog();
			}
		});
		//menuBar.add(mnLOG);
		menu.add(mnLOG);
		
		mnNewMenu_1 = new JMenu("Language");
		mnNewMenu_1.setOpaque(true);
		mnNewMenu_1.setForeground(Color.GREEN);
		mnNewMenu_1.setBackground(Color.BLACK);
		mnNewMenu_1.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("SLK");
		mntmNewMenuItem.setBackground(Color.CYAN);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguageSlovak();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("ENG");
		mntmNewMenuItem_1.setBackground(Color.RED);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguageEnglish();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_2 = new JMenuItem("Log out");
		mntmNewMenuItem_2.setOpaque(true);
		mntmNewMenuItem_2.setBackground(Color.BLACK);
		mntmNewMenuItem_2.setHorizontalAlignment(SwingConstants.LEFT);
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
		
		lFromDate = new JLabel("From Date:");
		lFromDate.setBounds(31, 61, 91, 16);
		frmMain.getContentPane().add(lFromDate);
		
		lToDate = new JLabel("To Date:");
		lToDate.setBounds(180, 61, 91, 16);
		frmMain.getContentPane().add(lToDate);
		
		
		tFromDate = new JTextField();
		tFromDate.setBounds(101, 58, 74, 22);
		frmMain.getContentPane().add(tFromDate);
		tFromDate.setColumns(10);
		
		tToDate = new JTextField();
		tToDate.setColumns(10);
		tToDate.setBounds(241, 58, 74, 22);
		frmMain.getContentPane().add(tToDate);
		
		lCustomer = new JLabel("Customer:");
		lCustomer.setBounds(31, 97, 74, 16);
		frmMain.getContentPane().add(lCustomer);
		
		tCustomer = new JTextField();
		tCustomer.setBounds(101, 94, 214, 22);
		frmMain.getContentPane().add(tCustomer);
		tCustomer.setColumns(10);
		
		lPartNumber = new JLabel("Part number:");
		lPartNumber.setBounds(369, 100, 91, 16);
		frmMain.getContentPane().add(lPartNumber);
		
		tPartNumber = new JTextField();
		tPartNumber.setColumns(10);
		tPartNumber.setBounds(461, 97, 116, 22);
		frmMain.getContentPane().add(tPartNumber);
		
		lPartPosition = new JLabel("Part position:");
		lPartPosition.setBounds(369, 61, 76, 16);
		frmMain.getContentPane().add(lPartPosition);
		
		lCompositionNumber = new JLabel("Composition number:");
		lCompositionNumber.setBounds(626, 64, 142, 16);
		frmMain.getContentPane().add(lCompositionNumber);
		
		lOrderNumber = new JLabel("Order number:");
		lOrderNumber.setBounds(626, 102, 142, 16);
		frmMain.getContentPane().add(lOrderNumber);
		
		tPartPosition = new JTextField();
		tPartPosition.setBounds(461, 62, 116, 22);
		frmMain.getContentPane().add(tPartPosition);
		tPartPosition.setColumns(10);
		
		tCompositionNumber = new JTextField();
		tCompositionNumber.setColumns(10);
		tCompositionNumber.setBounds(767, 61, 116, 22);
		frmMain.getContentPane().add(tCompositionNumber);
		
		tOrderNumber = new JTextField();
		tOrderNumber.setColumns(10);
		tOrderNumber.setBounds(767, 99, 116, 22);
		frmMain.getContentPane().add(tOrderNumber);
		
		JButton buttonFilter = new JButton("Filter");
		buttonFilter.setBounds(933, 78, 97, 25);
		
		frmMain.getContentPane().add(buttonFilter);
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(0, 139, 1920, 2);
		frmMain.getContentPane().add(separator);
		frmMain.setVisible(true);
		
		/*********************************************************KOSO*********************************************************************/
		JButton btnBack = new JButton("<<<");
		btnBack.setBounds(100, 150, 100, 50);
		frmMain.getContentPane().add(btnBack);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(250, 150, 100, 50);
		frmMain.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(360, 150, 100, 50);
		frmMain.getContentPane().add(btnDelete);
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(470, 150, 100, 50);
		frmMain.getContentPane().add(btnExport);
		
		JButton btnImport = new JButton("Import");
		btnImport.setBounds(580, 150, 100, 50);
		frmMain.getContentPane().add(btnImport);
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int width = screenSize.width;
				int height = screenSize.height;

				
				String[] ar = {"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three",
								"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three",
								"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three",
								"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three"};
				
				frmMain.getContentPane().remove(listScroller);
	        	
	        	list = new JList(ar); 
	        	
	    		listScroller = new JScrollPane(list);
	    		listScroller.setPreferredSize(new Dimension(250, 80));
	    		listScroller.setBounds(100, 200, width-500, height-300);
	    		frmMain.getContentPane().add(listScroller);
	    		frmMain.validate();
				
			}
			
		});
		
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed button: Update    Selected value: " + getSelectedItem());
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed button: Delete    Selected value: " + getSelectedItem());
			}
		});
		
		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed button: Export    Selected value: " + getSelectedItem());
			}
		});
		
		btnImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed button: Inport    Selected value: " + getSelectedItem());				
			}
		});
		
		buttonFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				boolean from = false;
				boolean to = false;
				whereFilter = "";
				String[] values = {tFromDate.getText(), tToDate.getText(), tCustomer.getText(), tPartNumber.getText(), tPartPosition.getText(), tCompositionNumber.getText(), tOrderNumber.getText()};
				String[] varNames = {"Date", "Date", "Kunde", "Zeichnungsnummer", "Bezeichnung", "zostava", "nrNumber"};
				
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				
					
			    try {
			    	if (tFromDate.getText().length() != 0) {
			    		Date datum1 = format.parse(tFromDate.getText());
			    		values[0] = format2.format(datum1);
			    		from = true;
			        }
			        if (tToDate.getText().length() != 0) {
			        	format.parse(tToDate.getText());
			        	Date datum2 = format.parse(tToDate.getText());
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
						else if (i > 1) {
							whereFilter = whereFilter +  varNames[i] + " = " + "'" + values[i] + "'" + " AND ";
						}
					}
				}
				
				if (whereFilter != "") {
					whereFilter = whereFilter.substring(0, whereFilter.length() - 5);
				}
				
				System.out.println("\"" +  whereFilter + "\"");
				/*
				 * Tu sa ma volat DB a potom sa vola createData, to vola makeJList, je to tu kvoli testovaniu
				 * */
				makeJList();
				
			}
		});
	}
	
	private void setLanguageSlovak() {
		lFromDate.setText("Dátum od:");
		lToDate.setText("Dátum do:");
		lCustomer.setText("Zákazník:");
		lPartNumber.setText("Èíslo dielu:");
		lPartPosition.setText("Pozícia dielu:");
		lCompositionNumber.setText("Èíslo zostavy:");
		lOrderNumber.setText("Èíslo objednávky:");

		mnUploadFile.setText("Vytvor Štruktúru");
		mnLOG.setText("LOG");
		mnNewMenu_1.setText("Jazyk");
		mntmNewMenuItem_2.setText("Odhlási� sa");
		
	}
	
	private void setLanguageEnglish() {
		lFromDate.setText("From Date:");
		lToDate.setText("To Date:");
		lCustomer.setText("Customer:");
		lPartNumber.setText("Part number:");
		lPartPosition.setText("Part position:");
		lCompositionNumber.setText("Composition number:");
		lOrderNumber.setText("Order number:");

		mnUploadFile.setText("Make structure");
		mnLOG.setText("LOG");
		mnNewMenu_1.setText("Language");
		mntmNewMenuItem_2.setText("Log out");
		
	}
	
	/********************************KOSO***************************/
	
	public static String getWhereFilter() {
		return whereFilter;
	}
	
	private String getSelectedItem () {
		try {
			String selected = list.getSelectedValue().toString();
			return selected;
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "No item selected");
			return "q";
		}
	}
	
	private String[] createData () {
		/*Tu treba adapter na zavolanie DB
		 * Po kliknuti na tlacitlo FILTER sa vytvori vstup pre WHERE podmienku, ak nie je nic, tak sa da true
		 * odtial sa nacitaju vsetky produkty a z nich sa vyberie string, ten sa dostane sem a vrati to do Jlistu
		 * */
		dataForJlist = null;
		makeJList();
		return null;
	}
	
	private void makeJList () {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		
		String[] ar = {"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three",
						"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three",
						"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three",
						"one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three"};
		
		String[] data = {"dokumenty", "testy", "jedna", "tajtrlik"};
		//ar a data sú len na ukážku
		list = new JList(ar); 
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setBounds(100, 200, width-500, height-300);
		frmMain.getContentPane().add(listScroller);
		frmMain.repaint();
		frmMain.validate();

		list.addMouseListener(new MouseAdapter() {
		    
			public void mouseClicked(MouseEvent evt) {
		        list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	
		        	backData.add(ar);
		        	frmMain.getContentPane().remove(listScroller);
		        	
		        	list = new JList(data); 
		        	
		    		listScroller = new JScrollPane(list);
		    		listScroller.setPreferredSize(new Dimension(250, 80));
		    		listScroller.setBounds(100, 200, width-500, height-300);
		    		frmMain.getContentPane().add(listScroller);
		    		frmMain.validate();

		            System.out.println("clicked");
		            //int index = list.locationToIndex(evt.getPoint());
		        } 
		    }
		});
	}
	
	
	
}
