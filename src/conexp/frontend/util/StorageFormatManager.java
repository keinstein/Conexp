/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.util;

import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import util.Assert;
import util.gui.fileselector.AllSupportedFormatsFilter;
import util.gui.fileselector.GenericFileFilter;

import java.util.ArrayList;
import java.util.List;

public class StorageFormatManager {
    private List formats = new ArrayList();

    public int registeredFormatCount() {
        return formats.size();
    }

    public boolean isEmpty() {
        return 0 == registeredFormatCount();
    }

    protected DocumentLoader getLoader(int index) {
        return getStorageFormatRecord(index).getLoader();
    }

    private StorageFormatRecord getStorageFormatRecord(int index) {
        return ((StorageFormatRecord) formats.get(index));
    }

    private void setStorageFormatRecord(int index, StorageFormatRecord formatRec) {
        formats.set(index, formatRec);
    }

    private int findStorageFormatRecord(String ext) {
        int size = registeredFormatCount();
        for (int i = 0; i < size; i++) {
            StorageFormatRecord record = getStorageFormatRecord(i);
            if (record.acceptsExtension(ext)) {
                return i;
            }
        }
        return -1;
    }

    public void registerStorageFormat(StorageFormatRecord record) {
        int recIndex = findStorageFormatRecord(record.getExtension());
        if (-1 != recIndex) {
            setStorageFormatRecord(recIndex, record);
        } else {
            formats.add(record);
        }
    }

    private int loaderCount() {
        int ret = 0;
        for (int i = registeredFormatCount(); --i >= 0;) {
            if (getStorageFormatRecord(i).hasLoader()) {
                ret++;
            }
        }
        return ret;
    }

    private int writerCount() {
        int ret = 0;
        for (int i = registeredFormatCount(); --i >= 0;) {
            if (getStorageFormatRecord(i).hasWriter()) {
                ret++;
            }
        }
        return ret;
    }

    private boolean allowAllSupported = false;

    public boolean isAllowAllSupported() {
        return allowAllSupported;
    }

    protected void setAllowAllSupported(boolean allowAllSupported) {
        this.allowAllSupported = allowAllSupported;
    }

    public GenericFileFilter[] getLoadFilters() {
        final GenericFileFilter[] rawLoadFilters = getRawLoadFilters();
        if (allowAllSupported) {
            return AllSupportedFormatsFilter.allowAllSupported(rawLoadFilters);
        }
        return rawLoadFilters;
    }

    private GenericFileFilter[] getRawLoadFilters() {
        int size = loaderCount();
        GenericFileFilter[] ret = new GenericFileFilter[size];
        int currRec = 0;
        int bound = registeredFormatCount();
        for (int i = 0; i < bound; i++) {
            StorageFormatRecord currFormat = getStorageFormatRecord(i);
            if (currFormat.hasLoader()) {
                ret[currRec] = currFormat.getFilter();
                currRec++;
            }
        }
        return ret;
    }

    public DocumentLoader getLoader(String ext) {
        int recIndex = findStorageFormatRecord(ext);
        if (-1 != recIndex) {
            return getStorageFormatRecord(recIndex).getLoader();
        }
        return null;
    }

    public DocumentLoader getDefaultLoader() {
        if (null != defaultFormat) {
            return defaultFormat.getLoader();
        }
        for (int i = 0; i < registeredFormatCount(); i++) {
            StorageFormatRecord currFormat = getStorageFormatRecord(i);
            if (currFormat.hasLoader()) {
                return currFormat.getLoader();
            }
        }
        return null;
    }

    public GenericFileFilter[] getStoreFilters() {
        int size = writerCount();
        GenericFileFilter[] ret = new GenericFileFilter[size];
        int currRec = 0;
        int bound = registeredFormatCount();
        for (int i = 0; i < bound; i++) {
            StorageFormatRecord currFormat = getStorageFormatRecord(i);
            if (currFormat.hasWriter()) {
                ret[currRec] = currFormat.getFilter();
                currRec++;
            }
        }
        return ret;
    }

    public DocumentWriter getWriter(String ext) {
        int recIndex = findStorageFormatRecord(ext);
        if (-1 != recIndex) {
            return getStorageFormatRecord(recIndex).getWriter();
        }
        return null;
    }

    private StorageFormatRecord defaultFormat;

    public void registerDefaultStorageFormat(StorageFormatRecord format) {
        Assert.isTrue(format.getLoader() != null);
        Assert.isTrue(format.getWriter() != null);
        Assert.isTrue(format.getExtension() != null);
        registerStorageFormat(format);
        defaultFormat = format;
    }

    public String getDefaultExtension() {
        return defaultFormat.getExtension();
    }

    public DocumentWriter getDefaultWriter() {
        if (null != defaultFormat) {
            return defaultFormat.getWriter();
        }
        for (int i = 0; i < registeredFormatCount(); i++) {
            StorageFormatRecord currFormat = getStorageFormatRecord(i);
            if (currFormat.hasWriter()) {
                return currFormat.getWriter();
            }
        }
        return null;
    }
}
