package canvas.util;

import conexp.frontend.latticeeditor.ILatticeExporter;
import canvas.IScreenImageProvider;
import util.imagecodecs.ImageCodecsFactory;
import util.imagecodecs.ImageCodec;
import util.StringUtil;

import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 16:07:30
 */
public class RasterImageLatticeExporter implements ILatticeExporter {
    private final IScreenImageProvider screenImageProvider;

    public RasterImageLatticeExporter(IScreenImageProvider screenImageProvider) {
        this.screenImageProvider = screenImageProvider;
    }


    ImageCodecsFactory getImageCodecsFactory() {
        return util.ServiceRegistry.imageCodecsFactory();
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
