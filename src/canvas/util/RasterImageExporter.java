package canvas.util;

import canvas.IScreenImageProvider;
import util.IExporter;
import util.StringUtil;
import util.ServiceRegistry;
import util.imagecodecs.ImageCodec;
import util.imagecodecs.ImageCodecsFactory;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 16:07:30
 */
public class RasterImageExporter implements IExporter {
    private final IScreenImageProvider screenImageProvider;

    public RasterImageExporter(IScreenImageProvider screenImageProvider) {
        this.screenImageProvider = screenImageProvider;
    }


    static ImageCodecsFactory getImageCodecsFactory() {
        return ServiceRegistry.imageCodecsFactory();
    }

    public String[][] getDescriptions() {
        return getImageCodecsFactory().getDescriptions();
    }

    public boolean accepts(String path) {
        return getImageCodecsFactory().accepts(path);
    }

    public void performExportService(String path) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
        try {
            ImageCodec codec = getImageCodecsFactory().makeCodec(StringUtil.getExtension(path));
            codec.saveToStream(screenImageProvider.getScreenImage(), out);
        } finally {
            out.flush();
            out.close();
        }
    }
}
