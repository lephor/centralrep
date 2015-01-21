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
		try
		{
			jmslistner = new MyJMSListener(textPaneReceived);
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
				      p.put(Context.PROVIDER_URL,"t3://localhost:7001"); //"t3://192.168.1.25:7001" "t3://localhost:7001"
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
				proxy.setEndpoint("http://192.168.1.25:7001/MyWebServicesProj/MyWebServiceService");
				
				try
				{
					textPane2.setText(textPane2.getText() + "\r\n" + String.valueOf(proxy.add(5, 6)));
				}
				catch (Exception e)
				{
					textPane2.setText(textPane2.getText() + "\r\n" + e.getMessage());
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
						jmscli = new myJMSClient(); 
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
				jdbc_point = new JDBC_Exec("direct");
				table1.setModel(jdbc_point.getResultSetTableModel("select * from scott.emp"));
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
				jdbc_point = new JDBC_Exec("connection_pool");
				table1.setModel(jdbc_point.getResultSetTableModel("select * from scott.emp"));
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
		

	}
}
