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
