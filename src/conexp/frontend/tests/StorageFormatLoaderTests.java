package conexp.frontend.tests;

import conexp.frontend.ResourceLoader;
import conexp.frontend.StorageFormatLoader;
import conexp.frontend.util.StorageFormatManager;
import junit.framework.TestCase;

import java.util.ResourceBundle;

public class StorageFormatLoaderTests extends TestCase{
	
	static ResourceBundle realresource = null;

	static {
		realresource = ResourceLoader.getResourceBundle("conexp/frontend/resources/ContextDocManager");  //$NON-NLS-1$
	}


	public void  testLoadStorageFormatList() {
		makeStorageFormatLoader(realresource).loadStorageFormatList();
	}

	private StorageFormatLoader makeStorageFormatLoader(ResourceBundle resourceBundle){
		return new StorageFormatLoader(resourceBundle);
	}

	private StorageFormatManager makeStorageFormatManager() {
		return new StorageFormatManager();
	}
	
	public void testLoadStorageFormats(){
        makeStorageFormatLoader(realresource).loadStorageFormats(makeStorageFormatManager());
	}

}
