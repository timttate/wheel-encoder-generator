/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.botthoughts;

import java.awt.FileDialog;
import java.awt.Component;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author mes
 */
public class JFileChooser extends javax.swing.JFileChooser
{
    private FileDialog fd;
    private static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));
    private JFileFilter ff = null;
    private String directory = null;
    private String file = null;

    // TODO: automatically append file extension if not supplied?

    /* approveSelection() 
     * 
     * The native JFileChooser doesn't prompt if the file exists, but whoever
     * uses this code might find it convenient to be able to just call this
     * without separately checking for file exists on Windows, but not on Mac.
     */
    @Override
    public void approveSelection()
    {
        System.out.println("approveSelection() -- "+this.getSelectedFile());
        // This probably will only be called on non-OSX since we're not
        // calling super.showDialog() etc on that platform
        if (this.getSelectedFile().exists() && this.getDialogType() == SAVE_DIALOG) {
            int response = JOptionPane.showConfirmDialog(this.getRootPane(),
                "Replace existing file " + this.getSelectedFile().getName() + "?", "Replace?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE );
            if (response == JOptionPane.NO_OPTION) {
                this.setSelectedFile(null);
                this.fireActionPerformed(CANCEL_SELECTION);
            }
        }
    }

    public void setFileFilter(com.botthoughts.JFileFilter ff)
    {
        if (MAC_OS_X) {
            this.ff = ff;
        } else {
            super.setFileFilter(ff);
        }
    }

    @Override
    public void setSelectedFile(File f)
    {
        if (MAC_OS_X) {
            this.directory = f.getParent();
            this.file = f.getName();
        } else {
            super.setSelectedFile(f);
        }
    }

    @Override
    public int showOpenDialog(Component frame)
    {
        int result = CANCEL_OPTION;

        if (MAC_OS_X) {
            fd = new FileDialog((JFrame) frame, "Open", FileDialog.LOAD);
            fd.setFilenameFilter(this.ff);
            fd.setDirectory(this.directory);
            fd.setFile(this.file);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                result = APPROVE_OPTION;
                this.directory = fd.getDirectory();
                this.file = fd.getFile();
            } else {
                result = CANCEL_OPTION;
                this.file = null;
            }
        } else {
            result = super.showOpenDialog(frame);
        }

        return result;
    }

    
    @Override
    public int showSaveDialog(Component frame)
    {
        int result = CANCEL_OPTION;

        if (MAC_OS_X) {
            fd = new FileDialog((JFrame) frame, "Save", FileDialog.SAVE);
            fd.setFilenameFilter(this.ff);
            fd.setDirectory(this.directory);
            fd.setFile(this.file);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                result = APPROVE_OPTION;
                this.directory = fd.getDirectory();
                this.file = fd.getFile();
            } else {
                result = CANCEL_OPTION;
                this.file = null;
            }
        } else {
            result = super.showSaveDialog(frame);
        }
        
        return result;
    }

    @Override
    public File getSelectedFile()
    {
        File file = null;

        if (MAC_OS_X) {
            if (this.file != null)
                file = new File(this.directory, this.file);
        } else {
            file = super.getSelectedFile();
        }
        return file;
    }
}
