/*
 * Created on Mar 22, 2003
 *
 * the goal of this class is to load the different storage loader 
 * */
package conexp.frontend.tests;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import conexp.frontend.ResourceLoader;
import conexp.frontend.StorageFormatLoader;
import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;

/**
 * @author Julien Tane
 *
 * Class to load automatically all the Storage Format into a list
 */
public class StorageFormatLoaderTests extends TestCase{
	
	static ResourceBundle resources = null; 

	String propertiesstring = "";

	static {
		resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextDocManager");  //$NON-NLS-1$
	}
	

	public void  testLoadStorageFormatList() {
		setUp();
		String[] loadstorageformatlist = getStorageFormatLoader().loadStorageFormatList();
	}
	
	protected void setUp(){
		ResourceBundle res=null;
		try{// TODO the StringBufferInputStream is depreacated should be replaced by something else
			 res =new PropertyResourceBundle(new StringBufferInputStream(propertiesstring));
		}catch (IOException e){
			System.out.println("the resource bundle for the test could not be loaded "+e);
		}
		resources = res;
	}
	
	private StorageFormatLoader getStorageFormatLoader(){
		return new StorageFormatLoader(getResourceBundle());
	}
	
	private ResourceBundle getResourceBundle(){
		return resources;
	}


	/**
	 * returns a storageformatloader
	 * @return StorageFormatManager
	 */
	private StorageFormatManager getStorageFormatManager() {
		return new StorageFormatManager();
	}
	
	public void testcreateStorageFormatRecord(String description, String  extension, String docloader, String docwriter) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		setUp();		
		StorageFormatRecord stfmtrecord =getStorageFormatLoader().createStorageFormatRecord(description, extension,docloader, docwriter);
		assertNotNull(stfmtrecord);
		assertEquals("createStorageFormat  does not correctly set the desctiption ", stfmtrecord.getExtension(), extension);
		assertEquals("createStorageFormat  does not correctly set the extensions ", stfmtrecord.getExtension(), extension);
		assertEquals("createStorageFormat  does not correctly set the DocumentLoader", stfmtrecord.getLoader(), docloader); 
		assertEquals("createStorageFormat  does not correctly set theDocumentWriter ", stfmtrecord.getWriter(), docwriter);
	}

	public void testLoadStorageFormats(){
		setUp();
		StorageFormatManager storageformatmanager = getStorageFormatManager();
		StorageFormatLoader formatLoader =  getStorageFormatLoader();
		formatLoader.loadStorageFormats(storageformatmanager);		
	}

}
