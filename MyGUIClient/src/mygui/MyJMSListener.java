package mygui;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.swing.JTextPane;

public class MyJMSListener implements MessageListener {

	
	public final static String URL ="t3://192.168.1.25:7001";
	public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
	public final static String JMS_FACTORY="MyJMSConnectionFactory";
	public final static String QUEUE="MyJMSQueue";
	
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueReceiver qreceiver;
	private Queue queue;
	private boolean quit = false;
	
	JTextPane txtpane;
	
	
	public MyJMSListener(JTextPane txtp) throws Exception
	{

		txtpane = txtp;
		InitialContext ic = getInitialContext(URL);

		init(ic, QUEUE);
		
		synchronized(this)
		{
			txtpane.setText(txtpane.getText() +  "\r\n" + "JMS listens to incoming Messages ... ");
		}
		// Wait until a "quit" message has been received.

	}
	



	public void onMessage(Message msg)
	{
	
	try 
	{
		
	String msgText;
	if (msg instanceof TextMessage)
	{
	msgText = ((TextMessage)msg).getText();
	}
	else
	{
	msgText = msg.toString();
	}
	
	synchronized(this)
	{
		txtpane.setText(txtpane.getText() + "\r\n" + msgText);
	}
	
	}
	
	catch (JMSException jmse)
	{
	jmse.printStackTrace();
	}
	
	}
	
	
	public void init(Context ctx, String queueName) throws NamingException, JMSException
	{
	qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
	qcon = qconFactory.createQueueConnection();
	qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	queue = (Queue) ctx.lookup(queueName);
	qreceiver = qsession.createReceiver(queue);
	qreceiver.setMessageListener(this);
	qcon.start();
	}

	public void close()throws JMSException
	{
	qreceiver.close();
	qsession.close();
	qcon.close();
	}


	private static InitialContext getInitialContext(String url) throws NamingException
	{
	Hashtable env = new Hashtable();
	env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
	env.put(Context.PROVIDER_URL, url);
	return new InitialContext(env);
	}
	


}
