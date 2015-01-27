package mygui;

import java.awt.EventQueue;




import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import myBeans.MyBEanRemote;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Properties;

import myWebServices.*;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainWindow {

	private JFrame frmTechTester; // test comment 1
	JTextPane textPane1;
	JTextPane textPane2;          // test comment 2 3
	private JTable table1;
	JTextPane textPaneReceived;
	
	MyBEanRemote rifs;
	myJMSClient jmscli;
	MyJMSListener jmslistner;
	MyWebServiceProxy proxy;
	JDBC_Exec jdbc_point;
	private JTextField txtWlsurl;
	private JTextField txtConnFact;
	private JTextField txtQueue;
	private JTextField txtOracleurl;
	private JTextField txtSQL;
	private JTextField txtConnPool;
	
	Properties appSettings = null; 
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmTechTester.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
		try {
			appSettings = new Properties();
			InputStream input = null;
			input =  new FileInputStream("techtester.properties.xml");	 
			// load a properties file
			appSettings.loadFromXML(input);
			
			txtWlsurl.setText(appSettings.getProperty("Wlsurl"));
			txtConnFact.setText(appSettings.getProperty("ConnFact"));
			txtQueue.setText(appSettings.getProperty("Queue"));
			txtOracleurl.setText(appSettings.getProperty("Oracleurl"));
			txtSQL.setText(appSettings.getProperty("SQL"));
			txtConnPool.setText(appSettings.getProperty("ConnPool"));
			
			input.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try
		{
			jmslistner = new MyJMSListener("t3://" + appSettings.getProperty("Wlsurl"), appSettings.getProperty("Queue"), appSettings.getProperty("ConnFact"), textPaneReceived);
		}
		catch (Exception e)
		{
			textPaneReceived.setText(textPaneReceived.getText() + "\r\n" + e.getMessage());
		}
		
		}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTechTester = new JFrame();
		frmTechTester.setTitle("Tech Tester");
		frmTechTester.setBounds(100, 100, 577, 399);
		frmTechTester.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmTechTester.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("call EJB", null, panel, null);
		panel.setLayout(null);
		
		JButton btnCallEjb = new JButton("call EJB");
		btnCallEjb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				/////////////
				
				
				textPane1.setText(textPane1.getText() + "\r\n" + "Starting ...");
				  

				    try
				    {
				      Properties p=new Properties();
				      p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
				      p.put(Context.PROVIDER_URL,"t3://" + appSettings.getProperty("Wlsurl")); //"t3://192.168.1.25:7001" "t3://localhost:7001"
				      InitialContext ctx=new InitialContext(p);
				      rifs=(MyBEanRemote)ctx.lookup("java:global.MyWLSApp.MyEJBProj.MyBEan!myBeans.MyBEanRemote");
				      textPane1.setText(textPane1.getText() + "\r\n" + rifs.Hello("ejb client"));
				    }
				    catch(Exception e)
				    {
				    	textPane1.setText(textPane1.getText() + "\r\n" + e.getMessage());
				    }
				    
				    
				
				////////////
			}
		});
		btnCallEjb.setBounds(34, 86, 108, 23);
		panel.add(btnCallEjb);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(179, 84, 339, 220);
		panel.add(scrollPane);
		
		textPane1 = new JTextPane();
		scrollPane.setViewportView(textPane1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("call Web Service", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnCallWebService = new JButton("call Web Service");
		btnCallWebService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				/////////
				
				proxy = new MyWebServiceProxy();
				proxy.setEndpoint("http://" +  appSettings.getProperty("Wlsurl") + "/MyWebServicesProj/MyWebServiceService");
				
				try
				{
					textPane2.setText(textPane2.getText() + "\r\n" + String.valueOf(proxy.add(5, 6)));
				}
				catch (Exception e)
				{
					textPane2.setText(textPane2.getText() + "\r\n" + e.getMessage());
					textPane2.setText(textPane2.getText() + "\r\n" + "http://" +  appSettings.getProperty("Wlsurl") + "/MyWebServicesProj/MyWebServiceService" + " failed");
					
				}
				
				/////////
			}
		});
		btnCallWebService.setBounds(10, 79, 159, 23);
		panel_1.add(btnCallWebService);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(179, 77, 339, 220);
		panel_1.add(scrollPane_1);
		
		textPane2 = new JTextPane();
		scrollPane_1.setViewportView(textPane2);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("JMS AOQ", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(30, 131, 183, 165);
		panel_2.add(scrollPane_3);
		
		final JTextPane textPaneSent = new JTextPane();
		scrollPane_3.setViewportView(textPaneSent);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setBounds(308, 130, 183, 165);
		panel_2.add(scrollPane_4);
		
		textPaneReceived = new JTextPane();
		scrollPane_4.setViewportView(textPaneReceived);
		
		JButton btnSendMessage = new JButton("Send message");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (textPaneSent.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Text to send cannot be empty", "InfoBox: " + "Action required", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else
				{
					try
					{						
						jmscli = new myJMSClient("t3://" + appSettings.getProperty("Wlsurl"), appSettings.getProperty("Queue"), appSettings.getProperty("ConnFact")); 
						jmscli.send(textPaneSent.getText());
						jmscli.close();
					}
					catch (Exception e)
					{
						textPaneReceived.setText(textPaneReceived.getText() + "\r\n" + e.getMessage());
					}
				}
			}
		});
		btnSendMessage.setBounds(40, 61, 141, 23);
		panel_2.add(btnSendMessage);
		
		JLabel lblTextToSend = new JLabel("Text to send");
		lblTextToSend.setBounds(30, 106, 111, 14);
		panel_2.add(lblTextToSend);
		
		JLabel lblReceivedText = new JLabel("Received text");
		lblReceivedText.setBounds(308, 106, 141, 14);
		panel_2.add(lblReceivedText);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("JDBC", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblDirect = new JLabel("Direct");
		lblDirect.setBounds(63, 62, 46, 14);
		panel_3.add(lblDirect);
		
		JLabel lblOverWlsConnection = new JLabel("Over WLS connection pool");
		lblOverWlsConnection.setBounds(290, 62, 173, 14);
		panel_3.add(lblOverWlsConnection);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
				jdbc_point = new JDBC_Exec(appSettings.getProperty("ConnPool"),appSettings.getProperty("Wlsurl"),appSettings.getProperty("Oracleurl"), "direct");
				table1.setModel(jdbc_point.getResultSetTableModel(appSettings.getProperty("SQL")));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				//JDBC_Exec jdbc_point = new  JDBC_Exec();
				//jdbc_point.CreateConnection();
			}
		});
		btnSelect.setBounds(42, 103, 89, 23);
		panel_3.add(btnSelect);
		
		JButton button = new JButton("Select");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				///////////////////
				try
				{
				jdbc_point = new JDBC_Exec(appSettings.getProperty("ConnPool"),appSettings.getProperty("Wlsurl"),appSettings.getProperty("Oracleurl"), "connection_pool");
				table1.setModel(jdbc_point.getResultSetTableModel(appSettings.getProperty("SQL")));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		button.setBounds(314, 103, 89, 23);
		panel_3.add(button);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(29, 154, 491, 168);
		panel_3.add(scrollPane_2);
		
		table1 = new JTable();
		table1.setFillsViewportHeight(true);
		scrollPane_2.setViewportView(table1);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Threads", null, panel_4, null);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Config", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblWlsAddresseg = new JLabel("WLS url (e.g. 192.168.1.25:7001)");
		lblWlsAddresseg.setBounds(43, 48, 240, 14);
		panel_5.add(lblWlsAddresseg);
		
		JLabel lblOracle = new JLabel("Oracle url (e.g. 192.168.1.25:1522:orc)");
		lblOracle.setBounds(44, 149, 211, 14);
		panel_5.add(lblOracle);
		
		JLabel lblSqlSelectStatemet = new JLabel("SQL Select statement");
		lblSqlSelectStatemet.setBounds(44, 174, 195, 14);
		panel_5.add(lblSqlSelectStatemet);
		
		JLabel lblNewLabel = new JLabel("JMS Connection factory");
		lblNewLabel.setBounds(43, 73, 196, 14);
		panel_5.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("JMS Queue");
		lblNewLabel_1.setBounds(43, 98, 136, 14);
		panel_5.add(lblNewLabel_1);
		
		txtWlsurl = new JTextField();
		txtWlsurl.setBounds(276, 45, 211, 20);
		panel_5.add(txtWlsurl);
		txtWlsurl.setColumns(10);
		
		txtConnFact = new JTextField();
		txtConnFact.setColumns(10);
		txtConnFact.setBounds(276, 73, 211, 20);
		panel_5.add(txtConnFact);
		
		txtQueue = new JTextField();
		txtQueue.setColumns(10);
		txtQueue.setBounds(276, 98, 211, 20);
		panel_5.add(txtQueue);
		
		txtOracleurl = new JTextField();
		txtOracleurl.setColumns(10);
		txtOracleurl.setBounds(276, 146, 211, 20);
		panel_5.add(txtOracleurl);
		
		txtSQL = new JTextField();
		txtSQL.setColumns(10);
		txtSQL.setBounds(276, 171, 211, 20);
		panel_5.add(txtSQL);
		
		JLabel lblWlsoracleConnectionPool = new JLabel("WLS-Oracle Connection Pool");
		lblWlsoracleConnectionPool.setBounds(43, 199, 196, 14);
		panel_5.add(lblWlsoracleConnectionPool);
		
		txtConnPool = new JTextField();
		txtConnPool.setBounds(276, 196, 211, 20);
		panel_5.add(txtConnPool);
		txtConnPool.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				////////////////////////
				
				try {
					OutputStream output = new FileOutputStream("techtester.properties.xml")	;
					
					appSettings.setProperty("Wlsurl", txtWlsurl.getText()); 
					appSettings.setProperty("ConnFact",txtConnFact.getText());
					appSettings.setProperty("Queue",txtQueue.getText());
					appSettings.setProperty("Oracleurl",txtOracleurl.getText());
					appSettings.setProperty("SQL",txtSQL.getText());
					appSettings.setProperty("ConnPool",txtConnPool.getText());
					
					appSettings.storeToXML(output, "TechTester Settings");
					
					output.close();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				////////////////////////
				
			}
		});
		btnSave.setBounds(43, 254, 89, 23);
		panel_5.add(btnSave);
		

	}
}
