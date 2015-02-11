package myejbpack;

import javax.ejb.Local;

@Local
public interface SmpLocal {
	
	public String Hello (String ins);


}
