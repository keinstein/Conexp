/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import util.FileNameMangler;
import util.StringUtil;

public class DocumentRecord {
    FileNameMangler pathFormer;
    boolean hasCorrespondingFile = false;

    public DocumentRecord(Document document, String url) {
        if (null == document) {
            throw new IllegalArgumentException();
        }
        this.document = document;
        this.pathFormer = new FileNameMangler();
        setDocumentUrl(url);
    }

    public void setDocumentUrl(String url) {
        if (!StringUtil.isEmpty(url)) {
            hasCorrespondingFile = true;
            pathFormer.setCurrFileName(url);
        }
    }

    public boolean hasCorrespondingFile() {
        return hasCorrespondingFile;
    }

    public Document getDocument() {
        return document;
    }

    public String getDocumentDirectory() {
        return pathFormer.getOutPath();
    }

    public String getDocumentPath() {
        return pathFormer.getCurrFileName();
    }

    public String getDocumentFileName() {
        return pathFormer.getFileName();
    }

    Document document;
}
