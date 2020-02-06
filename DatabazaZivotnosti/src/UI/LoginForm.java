package UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginForm {
	public JLabel l2, l3;
	public JTextField t1;
	public JPasswordField p1;
	public JButton btn1;

	public LoginForm() {
		initialize();
	}
	
	private void initialize() {
		JFrame frame = new JFrame("Login");
		l2 = new JLabel("Username: ");
		l3 = new JLabel("Password: ");
		t1 = new JTextField();
		p1 = new JPasswordField();
		btn1 = new JButton("Login");

		l2.setBounds(150, 70, 200, 30);
		l3.setBounds(150, 110, 200, 30);
		t1.setBounds(250, 70, 200, 30);
		p1.setBounds(250, 110, 200, 30);
		btn1.setBounds(380, 170, 70, 30);
		
		btn1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				String uname = t1.getText();
				String pass = p1.getText();
				
				HttpClient client = HttpClient.newHttpClient();
				Builder builder = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/user"));
				builder.header("uname", uname);
				builder.header("pass", pass);
				HttpRequest request = builder.build();
				
				String[] jsonData = {""};
				client.sendAsync(request, BodyHandlers.ofString())
		         .thenApply(HttpResponse::body)
		         .thenAccept((x) -> {jsonData[0] = x;})
		         .join(); 
				
				if (jsonData[0].equals("true")) {
					new Main(uname);
					frame.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(btn1, "Wrong username or password!", "Error message", JOptionPane.ERROR_MESSAGE);
				}
			}
			});

		frame.add(l2);
		frame.add(t1);
		frame.add(l3);
		frame.add(p1);
		frame.add(btn1);

		frame.setSize(600, 400);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new LoginForm();
	}

}