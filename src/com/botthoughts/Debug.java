/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.botthoughts;

/**
 *
 * @author Michael Shimniok
 */
public class Debug extends Throwable {
    private static boolean on=true;
    private static int level = 0;
    public static Debug debug = new Debug();

    public static void on(boolean isOn) {
        Debug.on = isOn;
    }

    public static void setLevel(int newLevel) {
        Debug.level = newLevel;
    }

    public static void println(String message) {
        if (on) {
            StackTraceElement st = new Throwable().fillInStackTrace().getStackTrace()[1];
            String theMethod = st.getMethodName();
            String theClass = st.getClassName();
            int theLine = st.getLineNumber();
            String theFile = st.getFileName();

            System.out.println(theFile + ":"+ theLine + " " + theClass + "." + theMethod + "(): " + message);
        }
    }
}
