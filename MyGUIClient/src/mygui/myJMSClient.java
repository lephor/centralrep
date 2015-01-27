package mygui;

import java.io.*;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class myJMSClient {

	

	public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
	
	public String URL;
	public String JMS_FACTORY;
	public String QUEUE;
	
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private Queue queue;
	private TextMessage msg;
	
	
	public myJMSClient(String in_url, String in_queue, String in_jms_factory) throws Exception
	{

		URL = in_url;
		JMS_FACTORY = in_jms_factory;
		QUEUE = in_queue;
		
		InitialContext ic = getInitialContext(URL);
		init(ic, QUEUE);
		//readAndSend();
		//close();
		
		
	}
	




public void init(Context ctx, String queueName)
throws NamingException, JMSException
{
qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
qcon = qconFactory.createQueueConnection();
qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
queue = (Queue) ctx.lookup(queueName);
qsender = qsession.createSender(queue);
msg = qsession.createTextMessage();
qcon.start();
}

public void send(String message) throws JMSException {
msg.setText(message);
qsender.send(msg);
}

public void close() throws JMSException {
qsender.close();
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



