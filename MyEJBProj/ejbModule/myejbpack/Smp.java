package myejbpack;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class Smp
 * 
 *  HLLEJB#myejbpack.SmpRemote
 */
@Stateless(mappedName = "HLLEJB")
@LocalBean
public class Smp implements SmpRemote, SmpLocal {

    /**
     * Default constructor. 
     */
    public Smp() {
        // TODO Auto-generated constructor stub
    }
    
    public String Hello (String ins)
	{
		return "Hello from WLS EJB, " + ins;
	}

}
