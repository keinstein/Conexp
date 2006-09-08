/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.ImageIcon;
import java.net.URL;


public class MyImageIcon extends ImageIcon {
//	public float getAlignmentY() { return 0.5f; }

    public MyImageIcon(URL url) {
        super(url);
    }
}
