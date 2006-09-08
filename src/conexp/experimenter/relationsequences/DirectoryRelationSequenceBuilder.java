/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.experimenter.framework.RelationSequenceSet;
import conexp.frontend.io.ContextReaderFactory;
import util.DataFormatException;
import util.FileNameMangler;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;



public class DirectoryRelationSequenceBuilder {
    private DirectoryRelationSequenceBuilder() {
    }

    public static RelationSequenceSet buildRelationSequenceSet(String directoryPath, String extension, ContextReaderFactory contextReaderFactory) throws IOException, DataFormatException {
        RelationSequenceSet relationSequenceSet = new RelationSequenceSet();
        String[] names = getListOfContextNames(directoryPath, extension);
        directoryPath = FileNameMangler.normalizeDirectoryName(directoryPath);
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String fileName = directoryPath + name;
            relationSequenceSet.addRelationSequence(new ContextLoadingRelationGenerationStrategy(fileName, contextReaderFactory));
        }

        return relationSequenceSet;
    }

    public static String[] getListOfContextNames(String dirPath, final String extension) {
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory");
        }
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return extension.equals(FileNameMangler.getFileExtension(name));
            }
        };
        return dir.list(filter);
    }


}
