/*
 * Created on Mar 22, 2003
 *
 * the goal of this class is to load the different storage loader 
 * */
package conexp.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;


import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;

/**
 * @author Julien Tane
 *
 * Class to load automatically all the Storage Format into a list
 */
public class StorageFormatLoader {

	public final static String  STORAGE_FORMAT_LIST_KEY = "conexp.storage.formats";

	ResourceBundle resources = null;
	
	/**
	 * 
	 */
	public StorageFormatLoader( ResourceBundle resources) {
		super();
		this.resources = resources;
	}

	public String[]  loadStorageFormatList() {
		String[] array =null;
		List list = new ArrayList();
		String keys= null;
		try {
		 keys=resources.getString(STORAGE_FORMAT_LIST_KEY);
		} catch (RuntimeException e) {
			
			e.printStackTrace();
		}
		return getStringArray(keys, ","); 
	}
	
	private String[] getStringArray(String keys,String sep){
	String[] stringarray=null;
	List list = new ArrayList();
	String key = null;
			for (StringTokenizer tok = new StringTokenizer(keys,sep); tok.hasMoreTokens();){
				key = tok.nextToken();
				key =key.trim();
				list.add(key);
			}
		return (String[]) list.toArray(new String[0]);
	}

	private Class loadClass(String classtoload)throws ClassNotFoundException{
		return Class.forName(classtoload);
	}

	public StorageFormatRecord createStorageFormatRecord(String description, String  extension, String docloader, String docwriter) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
			return new StorageFormatRecord(description, extension,(DocumentLoader) loadClass( docloader).newInstance(), (DocumentWriter)loadClass(docwriter).newInstance());
	}

	public void loadStorageFormats(StorageFormatManager storageforamtmanager){
		String[] storageformatlist = loadStorageFormatList();
		if (storageformatlist == null) {System.out.println("null"); return;}
		StorageFormatRecord sr= null;
		String[] sd =null;// storage definition
		for (int i =0; i< storageformatlist.length; i++){
			try {
				sd = getStringArray(resources.getString(storageformatlist[i]),",");
				sr =createStorageFormatRecord(sd[0],sd[1],sd[2],sd[3]);
				storageforamtmanager.registerStorageFormat(sr);
			} catch (Exception e) {
				System.err.println("The following StorageFormat could not be registered:"+ sd[0]+" due to Exception "+e);
			}	
		}	
	}
}
