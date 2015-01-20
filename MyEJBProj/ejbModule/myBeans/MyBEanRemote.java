package myBeans;

import javax.ejb.Remote;

@Remote
public interface MyBEanRemote {
	
	public String Hello (String ins);


}
