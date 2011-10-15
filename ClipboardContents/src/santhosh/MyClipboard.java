package santhosh;

import java.lang.reflect.Method;

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
public class MyClipboard{

//    // Workaround for bug
//    //
//    // http://developer.java.sun.com/developer/bugParade/bugs/4818143.html
//    //
//    // sun.awt.datatransfer.ClipboardTransferable.getClipboardData() can hang
//    // for very long time (maxlong == eternity).  We tries to avoid the hang by
//    // access the system clipboard from a separate thread.  If the hang happens
//    // the thread will wait for the system clipboard forever but not the whole
//    // IDE.  See also NbClipboard
//    private static ThreadLocal CLIPBOARD_FORBIDDEN = new ThreadLocal ();
//
//    /** Convinces Swing components that they should use special clipboard
//     * and not Toolkit.getSystemClipboard.
//     *
//     * @param clip clipboard to use
//     */
//    public static void makeSwingUseSpecialClipboard (java.awt.datatransfer.Clipboard clip) {
//        try {
//            javax.swing.JComponent source = new javax.swing.JPanel ();
//            CLIPBOARD_FORBIDDEN.set (clip);
//            javax.swing.TransferHandler.getPasteAction ().actionPerformed (
//                new java.awt.event.ActionEvent (source, 0, "")
//            );
//            javax.swing.TransferHandler.getCopyAction ().actionPerformed (
//                new java.awt.event.ActionEvent (source, 0, "")
//            );
//            javax.swing.TransferHandler.getCutAction ().actionPerformed (
//                new java.awt.event.ActionEvent (source, 0, "")
//            );
//            if (! (CLIPBOARD_FORBIDDEN.get () instanceof TopSecurityManager) ) {
//                System.err.println("Cannot install our clipboard to swing components, TopSecurityManager is not the security manager"); // NOI18N
//                return;
//            }
//
//            Class appContextClass = Class.forName ("sun.awt.AppContext"); // NOI18N
//            Method getAppContext = appContextClass.getMethod ("getAppContext", new Class[0]); // NOI18N
//            Object appContext = getAppContext.invoke (null, new Object[0]);
//            Class c = appContext.getClass();
//
//            Class actionClass = javax.swing.TransferHandler.getCopyAction ().getClass ();
//            java.lang.reflect.Field sandboxKeyField = actionClass.getDeclaredField ("SandboxClipboardKey"); // NOI18N
//            sandboxKeyField.setAccessible (true);
//            Object value = sandboxKeyField.get (null);
//
//            Method put = appContextClass.getMethod ("put", new Class[] { Object.class, Object.class }); // NOI18N
//            put.invoke (appContext, new Object[] { value, clip });
//        } catch (ThreadDeath ex) {
//            throw ex;
//        } catch (Throwable t) {
//            t.printStackTrace();
//        } finally {
//            CLIPBOARD_FORBIDDEN.set (null);
//        }
//    }

}
