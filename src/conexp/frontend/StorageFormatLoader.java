/*
 * Created on Mar 22, 2003
 *
 * the goal of this class is to load the different storage loader
 * */
package conexp.frontend;

import conexp.frontend.util.StorageFormatManager;
import conexp.frontend.util.StorageFormatRecord;
import util.StringUtil;
import util.collection.CollectionFactory;

import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @author Julien Tane
 *         <p/>
 *         Class to load automatically all the Storage Format into a list
 */
public class StorageFormatLoader {

    private final static String STORAGE_FORMAT_LIST_KEY = "conexp.storage.formats";

    private ResourceBundle resources = null;

    public StorageFormatLoader(ResourceBundle resources) {
        super();
        this.resources = resources;
    }

    public String[] loadStorageFormatList() {
        String keys = resources.getString(STORAGE_FORMAT_LIST_KEY);
        return getStringArray(keys, ",");
    }

    //todo: have a look at StringUtil or just create class PropertyUtils

    private static String[] getStringArray(String keys, String sep) {
        List list = CollectionFactory.createDefaultList();
        keys = StringUtil.safeTrim(keys); //for safety
        for (StringTokenizer tok = new StringTokenizer(keys, sep); tok.hasMoreTokens();) {
            String key = tok.nextToken();
            key = key.trim();
            list.add(key);
        }
        return (String[]) list.toArray(new String[list.size()]);
    }


    private static StorageFormatRecord createStorageFormatRecord(String description, String extension, String docloader, String docwriter) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return new StorageFormatRecord(description, extension, (DocumentLoader) safeLoadClass(docloader), (DocumentWriter) safeLoadClass(docwriter));
    }

    private static Object safeLoadClass(String docloader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        docloader = StringUtil.safeTrim(docloader);
        if (StringUtil.isEmpty(docloader)) {
            return null;
        }
//todo: think about replacemant with    ReflectHelper.createClassByName()
        return Class.forName(docloader).newInstance();
    }

    public void loadStorageFormats(StorageFormatManager storageforamtmanager) {
        String[] storageformatlist = loadStorageFormatList();
        //removed check for null,  because such situation can't appear : Sergey
        //todo: Julian, please remove this two comments, when you read them
        for (int i = 0; i < storageformatlist.length; i++) {
            try {
                String[] sd = getStringArray(resources.getString(storageformatlist[i]), ",");
                StorageFormatRecord sr = createStorageFormatRecord(sd[0], sd[1], sd[2], sd[3]);
                storageforamtmanager.registerStorageFormat(sr);
            } catch (Exception e) {
                //may be here better replace it with some general log concole.

                //removed reference to sd[0], because it can lead to null pointer exception, when resource
                //bundle throws an exception . Sergey
                //todo: Julian, please remove this three comments, when you read them

                System.err.println("The following StorageFormat not be registered due to Exception " + e);
            }
        }
    }
}
