package santhosh;

/**
 * MySwing: Advanced Swing Utilites
 * Copyright (C) 2005  Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Santhosh Kumar T
 * @email santhosh.tekuri@gmail.com
 */
public class IOUtil{
    public static void pump(InputStream in, OutputStream out, boolean closeIn, boolean closeOut) throws IOException{
        byte[] bytes = new byte[1024];
        int read;
        try{
            while((read=in.read(bytes))!= -1)
                out.write(bytes, 0, read);
        }finally{
            if(closeIn)
                in.close();
            if(closeOut)
                out.close();
        }
    }
}
