package myejbpack;

import javax.ejb.Remote;

@Remote
public interface SmpRemote {

	public String Hello (String ins);
	
}
