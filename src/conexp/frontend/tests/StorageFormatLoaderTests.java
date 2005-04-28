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


	public static void  testLoadStorageFormatList() {
		makeStorageFormatLoader(realresource).loadStorageFormatList();
	}

	private static StorageFormatLoader makeStorageFormatLoader(ResourceBundle resourceBundle){
		return new StorageFormatLoader(resourceBundle);
	}

	private static StorageFormatManager makeStorageFormatManager() {
		return new StorageFormatManager();
	}
	
	public static void testLoadStorageFormats(){
        makeStorageFormatLoader(realresource).loadStorageFormats(makeStorageFormatManager());
	}

}
