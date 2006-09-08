/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package canvas.util;

import canvas.IScreenImageProvider;
import util.IExporter;
import util.ServiceRegistry;
import util.StringUtil;
import util.imagecodecs.ImageCodec;
import util.imagecodecs.ImageCodecsFactory;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class RasterImageExporter implements IExporter {
    private final IScreenImageProvider screenImageProvider;

    public RasterImageExporter(IScreenImageProvider screenImageProvider) {
        this.screenImageProvider = screenImageProvider;
    }


    private static ImageCodecsFactory getImageCodecsFactory() {
        return ServiceRegistry.imageCodecsFactory();
    }

    public String[][] getDescriptions() {
        return getImageCodecsFactory().getDescriptions();
    }

    public boolean accepts(String path) {
        return getImageCodecsFactory().accepts(path);
    }

    public void performExportService(String path) throws IOException {
        ImageCodec codec = getImageCodecsFactory().makeCodec(StringUtil.getExtension(path));
        if (null == codec) {

        }

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
        try {
            codec.saveToStream(screenImageProvider.getScreenImage(), out);
        } finally {
            out.flush();
            out.close();
        }
    }
}
