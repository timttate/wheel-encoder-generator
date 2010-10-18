/*
 * WheelEncoderGeneratorApp.java
 */

package wheelencodergenerator;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class WheelEncoderGeneratorApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new WheelEncoderGeneratorView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of WheelEncoderGeneratorApp
     */
    public static WheelEncoderGeneratorApp getApplication() {
        return Application.getInstance(WheelEncoderGeneratorApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        // Mac Specific stuff
        System.setProperty("apple.laf.useScreenMenuBar","true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WheelEncoderGenerator");
        System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
        //System.setProperty("apple.awt.fileDialogForDirectories", "true");
        // TODO: OSX Dock Icon and other Mac goodies?
        System.out.println("launching app...");
        launch(WheelEncoderGeneratorApp.class, args);
    }
}