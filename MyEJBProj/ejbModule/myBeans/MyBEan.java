package myBeans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;


/**
 * Session Bean implementation class MyBEan
 */
@Stateless
@LocalBean
public class MyBEan implements MyBEanRemote {

    /**
     * Default constructor. 
     */
    public MyBEan() {
        // TODO Auto-generated constructor stub
    }
    
	public String Hello (String ins)
	{
		
		return "Hi from WLS " +  " , " + ins;
	}

}
