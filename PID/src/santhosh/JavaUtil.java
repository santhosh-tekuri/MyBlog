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
import java.io.File;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;
import java.lang.management.ManagementFactory;

/**
 * @author Santhosh Kumar T
 * @email santhosh.tekuri@gmail.com
 */
public class JavaUtil{
    public static String getPID() throws IOException{
        String pid = System.getProperty("pid"); //NOI18N
        if(pid==null){
            String cmd[];
            File tempFile = null;
            if(System.getProperty("os.name").toLowerCase().indexOf("windows")==-1)
                cmd = new String[]{ "/bin/sh", "-c", "echo $$ $PPID" }; //NOI18N
            else{
                // getpids.exe is taken from http://www.scheibli.com/projects/getpids/index.html (GPL)
                tempFile = File.createTempFile("getpids", "exe"); //NOI18N
                // extract the embedded getpids.exe file from the jar and save it to above file
                IOUtil.pump(JavaUtil.class.getResourceAsStream("getpids.exe"), new FileOutputStream(tempFile), true, true); //NOI18N
                cmd = new String[]{ tempFile.getAbsolutePath() };
            }
            if(cmd!=null){
                Process p = Runtime.getRuntime().exec(cmd);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                IOUtil.pump(p.getInputStream(), bout, false, true);
                if(tempFile!=null)
                    tempFile.delete();

                StringTokenizer stok = new StringTokenizer(bout.toString());
                stok.nextToken(); // this is pid of the process we spanned
                pid = stok.nextToken();
                if(pid!=null)
                    System.setProperty("pid", pid); //NOI18N
            }
        }
        return pid;
    }

    public static void main(String[] args) throws IOException{
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        String[]Ids = pid.split("@");
        Long osProcessId = Long.valueOf(Ids[0]);
        System.out.println("pid:"+osProcessId);
        System.out.println(getPID());
    }
}
