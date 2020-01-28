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

import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class Frame1 {

	private JFrame frmMain;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private final JSeparator separator = new JSeparator();

	/**
	 * Launch the application.
	 */
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
		
		JMenuItem mnUploadFile = new JMenuItem("Vytvorenie struktury");
		mnUploadFile.setBackground(Color.yellow);
		mnUploadFile.setPreferredSize(new Dimension(10, 20));
		mnUploadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Frame2();
			}
			
		});
		menuBar.add(mnUploadFile);
		
		JMenuItem mnLOG = new JMenuItem("LOG");
		mnLOG.setBackground(Color.cyan);
		menuBar.setPreferredSize(new Dimension(10, 20));
		mnLOG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameLog();
			}
		});
		menuBar.add(mnLOG);
		
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
		
		textField = new JTextField();
		textField.setBounds(101, 58, 74, 22);
		frmMain.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(241, 58, 74, 22);
		frmMain.getContentPane().add(textField_1);
		
		JLabel lblNewLabel_1 = new JLabel("Customer:");
		lblNewLabel_1.setBounds(31, 97, 74, 16);
		frmMain.getContentPane().add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(101, 94, 214, 22);
		frmMain.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Part number:");
		lblNewLabel_2.setBounds(369, 100, 91, 16);
		frmMain.getContentPane().add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(461, 97, 116, 22);
		frmMain.getContentPane().add(textField_3);
		
		JLabel lblNewLabel_3 = new JLabel("Part position:");
		lblNewLabel_3.setBounds(369, 61, 76, 16);
		frmMain.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Composition number:");
		lblNewLabel_4.setBounds(626, 64, 142, 16);
		frmMain.getContentPane().add(lblNewLabel_4);
		
		JLabel lblOrderNumber = new JLabel("Order number:");
		lblOrderNumber.setBounds(626, 102, 142, 16);
		frmMain.getContentPane().add(lblOrderNumber);
		
		textField_4 = new JTextField();
		textField_4.setBounds(461, 62, 116, 22);
		frmMain.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(767, 61, 116, 22);
		frmMain.getContentPane().add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(767, 99, 116, 22);
		frmMain.getContentPane().add(textField_6);
		
		JButton btnNewButton = new JButton("Filter");
		btnNewButton.setBounds(933, 78, 97, 25);
		
		frmMain.getContentPane().add(btnNewButton);
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(0, 139, 10000, 2);
		frmMain.getContentPane().add(separator);
		frmMain.setVisible(true);
	}
}
