package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

	/* Koso */
	private static String whereFilter = "";
	private JList<?> list;
	private JScrollPane listScroller;
	private String[] dataForJlist;
	private ArrayList<String[]> backData = new ArrayList<String[]>();
	private String verzia = "";

	private String noItemSelected = "No item selected";
	String badDateFormat = "Bad date format. Please enter a date in the form DD.MM.RRRR";
	String dateIsGreater = "The first date is greater than the second date";

	String User = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main("");///////// spytat sa marosa
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
	public Main(String user) {
		User = user;
		/*
		 * 
		 * 
		 * HttpClient client = HttpClient.newHttpClient(); Builder builder =
		 * HttpRequest.newBuilder().GET()
		 * .uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/giveLink"
		 * )); builder.header("nazov", "skuska.txt"); builder.header("id_verzie", "38");
		 * HttpRequest request = builder.build();
		 * 
		 * String[] jsonData = { "" }; client.sendAsync(request,
		 * BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept((x) -> {
		 * jsonData[0] = x; }).join(); System.out.println(jsonData[0]);
		 */

		/*
		 * File file = new File("F:\\HowToBasic.txt"); HttpEntity entity =
		 * MultipartEntityBuilder.create().addPart("file", new FileBody(file)).build();
		 * 
		 * HttpPost reques = new
		 * HttpPost("http://localhost:8080/DatabazaZivotnosti/rest/data/nahraj_subor");
		 * reques.setEntity(entity);
		 * 
		 * CloseableHttpClient clien = HttpClientBuilder.create().build(); try {
		 * HttpResponse response = (HttpResponse) new
		 * DefaultHttpClient().execute(reques); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

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
				new FrameMakeStructure(User);
			}

		});
		// menuBar.add(mnUploadFile);
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
		// menuBar.add(mnLOG);
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

		/*********************************************************
		 * KOSO
		 *********************************************************************/
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
		
		JButton btnSetDate = new JButton("Set date of testing");
		btnSetDate.setBounds(800, 150, 150, 50);
		frmMain.getContentPane().add(btnSetDate);
		
		JButton btnSetNum = new JButton("Test number");
		btnSetNum.setBounds(950, 150, 150, 50);
		frmMain.getContentPane().add(btnSetNum);
		
		btnSetDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getSelectedItem();
				/*
				HttpClient client = HttpClient.newHttpClient();
				Builder builder = HttpRequest.newBuilder().GET()
						.uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/allprodukt"));
				for (int i = 0; i < values.length; i++) {
					builder.header(varNames[i], values[i]);
				}
				builder.header("uname", User);
				HttpRequest request = builder.build(); /*dopisat okna na ziskanie datumu*/
				//dopyt nastavenai do DB
			}
		});
		
		btnSetNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getSelectedItem();
				// dopyt nastavenia do DB
				
			}
		});

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (backData.size() > 0) {
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					int width = screenSize.width;
					int height = screenSize.height;

					dataForJlist = backData.get(backData.size() - 1);
					backData.remove(backData.size() - 1);

					frmMain.getContentPane().remove(listScroller);

					list = new JList(dataForJlist);
					listScroller = new JScrollPane(list);
					listScroller.setPreferredSize(new Dimension(250, 80));
					listScroller.setBounds(100, 200, width - 500, height - 300);
					frmMain.getContentPane().add(listScroller);
					frmMain.validate();
					listener(list);
				}

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

				String[] values = { tFromDate.getText(), tToDate.getText(), tCustomer.getText(), tPartNumber.getText(),
						tPartPosition.getText(), tCompositionNumber.getText(), tOrderNumber.getText() };
				String[] varNames = { "FromDate", "ToDate", "Kunde", "Zeichnungsnummer", "Bezeichnung", "zostava",
						"nrNumber" };
				ArrayList<String> pom = new ArrayList<String>();

				HttpClient client = HttpClient.newHttpClient();
				Builder builder = HttpRequest.newBuilder().GET()
						.uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/allprodukt"));
				for (int i = 0; i < values.length; i++) {
					builder.header(varNames[i], values[i]);
				}
				builder.header("uname", User);
				HttpRequest request = builder.build();

				String[] jsonData = { "" };
				client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept((x) -> {
					jsonData[0] = x;
				}).join();

				try {
					Object obj = new JSONParser().parse(jsonData[0]);
					JSONArray zoznam = (JSONArray) obj;
					for (Object x : zoznam) {
						JSONObject j = (JSONObject) x;
						pom.add(j.get("zeichnungsnummer").toString());
					}
				} catch (org.json.simple.parser.ParseException e1) {
					e1.printStackTrace();
				}

				dataForJlist = new String[pom.size()];
				dataForJlist = pom.toArray(dataForJlist);

				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int width = screenSize.width;
				int height = screenSize.height;

				JList list = new JList(dataForJlist);

				listScroller = new JScrollPane(list);
				listScroller.setPreferredSize(new Dimension(250, 80));
				listScroller.setBounds(100, 200, width - 500, height - 300);
				frmMain.getContentPane().add(listScroller);
				frmMain.repaint();
				frmMain.validate();

				listener(list);

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

		dateIsGreater = "Prvý dátum je väčší ako druhý dátum";
		badDateFormat = "Zlý formát dátumu. Zadajte dátum v tvare DD.MM.RRRR";
		noItemSelected = "Nezvolili ste žiadnu zložku/súbor";

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

		noItemSelected = "No item selected";
		badDateFormat = "Bad date format. Please enter a date in the form DD.MM.RRRR";
		dateIsGreater = "The first date is greater than the second date";

	}

	/******************************** KOSO ***************************/

	public static String getWhereFilter() {
		return whereFilter;
	}

	private String getSelectedItem() {
		try {
			String selected = list.getSelectedValue().toString();
			return selected;
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, noItemSelected);
			return "q";
		}
	}

	private void createData(String text) {
		ArrayList<String> pom = new ArrayList<String>();

		HttpClient client = HttpClient.newHttpClient();
		Builder builder = HttpRequest.newBuilder().GET()
				.uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/verzie"));
		builder.header("zeich", text);
		HttpRequest request = builder.build();

		String[] jsonData = { "" };
		client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept((x) -> {
			jsonData[0] = x;
		}).join();

		try {
			Object obj = new JSONParser().parse(jsonData[0]);
			JSONArray zoznam = (JSONArray) obj;
			if (zoznam.size() != 0) {
				for (Object x : zoznam) {
					JSONObject j = (JSONObject) x;
					pom.add(j.get("zeichnungsnummer").toString());
				}
			}
		} catch (org.json.simple.parser.ParseException e1) {
			e1.printStackTrace();
		}

		dataForJlist = new String[pom.size()];
		dataForJlist = pom.toArray(dataForJlist);
	}

	private void makeJList() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		JList list = new JList(dataForJlist);
		
		frmMain.getContentPane().remove(listScroller);
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setBounds(100, 200, width - 500, height - 300);
		frmMain.getContentPane().add(listScroller);
		frmMain.repaint();
		frmMain.validate();

		listener(list);

	}

	private static String separator(String text, String varName) {
		String result = "";
		String[] parts = text.split(",");
		for (int i = 0; i < parts.length; i++) {
			result = result + varName + " = '" + parts[i].trim() + "' OR ";
		}
		result = result.substring(0, result.length() - 4);
		return result;
	}

	private void listener(JList list) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		list.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				Object o = null;
				List<String> najdene_subory = new ArrayList<String>();

				if (evt.getClickCount() == 2) {

					int index = list.locationToIndex(evt.getPoint());

					if (index >= 0) {
						o = list.getModel().getElementAt(index);
					}

					if (backData.size() == 0) {
						backData.add(dataForJlist);
						createData(o.toString());

						frmMain.getContentPane().remove(listScroller);
						list = new JList(dataForJlist);
						listScroller = new JScrollPane(list);
						listScroller.setPreferredSize(new Dimension(250, 80));
						listScroller.setBounds(100, 200, width - 500, height - 300);
						frmMain.getContentPane().add(listScroller);
						frmMain.validate();
						listener(list);

					} else if (backData.size() == 1) {// mame verzie
						
						HttpClient client = HttpClient.newHttpClient();
						Builder builder = HttpRequest.newBuilder().GET()
								.uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/verziaID"));
						builder.header("zeich", o.toString());
						HttpRequest request = builder.build();

						String[] jsonData = { "" };
						client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept((x) -> {
							jsonData[0] = x;
						}).join();
						
						verzia = jsonData[0];
						backData.add(dataForJlist);
						
						dataForJlist = vnutornaStruktura();
						list = new JList(dataForJlist);

						makeJList();

					} else if (o != null && o.toString().equals("Dokumentácia")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("dokumentacia", verzia, false);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();

					} else if (o != null && o.toString().equals("Fotodokumentácia")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("fotodokumentacia", verzia, false);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();

					} else if (o != null && o.toString().equals("Iterácia cylinder")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("iteracia_cylinder", verzia, false);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();

					} else if (o != null && o.toString().equals("Program")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("program", verzia, false);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();

					} else if (o != null && o.toString().equals("Signály")) {
						backData.add(dataForJlist);
						dataForJlist = strukturaSignaly();
						list = new JList(dataForJlist);
						makeJList();
					} else if (o != null && o.toString().equals("Originálny")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("originalny", verzia, true);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();
					} else if (o != null && o.toString().equals("Editovaný")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("editovany", verzia, true);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();
					} else if (o != null && o.toString().equals("Signál")) {
						backData.add(dataForJlist);
						najdene_subory = create_request("signal", verzia, true);
						dataForJlist = new String[najdene_subory.size()];
						dataForJlist = najdene_subory.toArray(dataForJlist);
						makeJList();

					}

					String pooom = "";
					for (int i = 0; i < backData.size(); i++) {
						String[] a = backData.get(i);
						for (int j = 0; j < a.length; j++) {
							pooom += a[j];
						}
						pooom += ";";
					}

					System.out.println(pooom);

					listener(list);
				}
			}
		});
	}

	private List<String> create_request(String typ, String id_verzie, Boolean signal) {
		List<String> pom = new ArrayList<String>();
		if (signal == false) {
			HttpClient client = HttpClient.newHttpClient();
			Builder builder = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/get_subory"));
			builder.header("typ", typ);
			builder.header("id_verzie", id_verzie);
			HttpRequest request = builder.build();

			String[] jsonData = { "" };
			client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept((x) -> {
				jsonData[0] = x;
			}).join();
			
			
			try {
				Object obj = new JSONParser().parse(jsonData[0]);
				JSONArray zoznam = (JSONArray) obj;
				if (zoznam.size() != 0) {
					for (Object x : zoznam) {
						JSONObject j = (JSONObject) x;
						pom.add(j.get("dokument_nazov").toString());
					}
				}
			} catch (org.json.simple.parser.ParseException e1) {
				e1.printStackTrace();
			}
		} else {
			HttpClient client = HttpClient.newHttpClient();
			Builder builder = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/get_signal"));
			builder.header("typ", typ);
			builder.header("id_verzie", id_verzie);
			HttpRequest request = builder.build();

			String[] jsonData = { "" };
			client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept((x) -> {
				jsonData[0] = x;
			}).join();
			
			System.out.println(jsonData[0] + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			try {
				Object obj = new JSONParser().parse(jsonData[0]);
				JSONArray zoznam = (JSONArray) obj;
				if (zoznam.size() != 0) {
					for (Object x : zoznam) {
						JSONObject j = (JSONObject) x;
						pom.add(j.get("subor").toString());
					}
				}
			} catch (org.json.simple.parser.ParseException e1) {
				e1.printStackTrace();
			}
		}
		return pom;
	}

	private String[] vnutornaStruktura() {
		String[] x = { "Dokumentácia", "Fotodokumentácia", "Iterácia cylinder", "Program", "Signály" };
		return x;
	}

	private String[] strukturaSignaly() {
		String[] x = { "Originálny", "Editovaný", "Signál" };
		return x;
	}

	public String getUser() {
		return User;
	}

}