/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wheelencodergenerator;

import java.util.*;
import java.io.*;

/**
 *
 * @author Michael Shimniok
 */
public class WheelEncoder {

    private int type;
    private int numbering; // Gray or Binary
    private int resolution;
    private int innerDiameter;
    private int outerDiameter;
    private boolean indexTrack;
    private boolean quadratureTrack;
    private File file;

    public static int ABSOLUTE=0;
    public static int STANDARD=1;
    public static int GRAY=0;
    public static int BINARY=1;

    private Properties p;

    public WheelEncoder() {
        type = STANDARD;
        resolution = 32;
        innerDiameter = 20;
        outerDiameter = 50;
        indexTrack = false;
        quadratureTrack = false;
        p = new Properties();
        file = null;
    }

    /* loadEncoder
     *
     * Loads encoder data as a properties file just cuz it's easy to do
     */
    public WheelEncoder(File file) throws IOException
    {
        p = new Properties();
        this.file = file;
        try {
            p.load(new FileInputStream(file));
            loadProperties();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /* save
     *
     * Saves encoder data as a properties file just cuz it's easy to do.
     */
    public void save(File file) throws IOException
    {
        System.out.println("save() -- enter");
        storeProperties();
        System.out.println("save() -- done storing properties");
        try {
            System.out.println("save() -- enter try block");
            p.store(new FileOutputStream(file), "Wheel Encoder Settings");
            System.out.println("save() -- called store");
            this.file = file;
            System.out.println("save() -- end of try block");
        } catch (IOException e) {
            p = new Properties(); // Mark as unsaved
            System.out.println("WheelEncoder.save() IOException: '"+e.getMessage()+"'");
            throw new IOException(e.getMessage());
        }
    }

    //TODO: debug output for all catch blocks

    public void setType(int t)
    {
        if (t == ABSOLUTE || t == STANDARD)
            type = t;
    }

    public int getType()
    {
        return type;
    }

    public void setNumbering(int n)
    {
        if (n == GRAY || n == BINARY) {
            numbering = n;
        }
    }

    public int getNumbering()
    {
        return numbering;
    }

    public int getTrackCount()
    {
        int tracks = 0;
        
        if (type == ABSOLUTE) {
            tracks = resolution;
        }
        else if (type == STANDARD) {
            tracks = 1;
            if (indexTrack) tracks++;
            if (quadratureTrack) tracks++;
        }

        return tracks;
    }

    public double getDegree()
    {
        double d=0.0;
        if (type == STANDARD) {
            // The standard encoder has one track and
            // the resolution specifies the number of stripes
            // directly
            d = 360.0 / resolution; 
        }
        return d;
    }

    public double getDegree(int whichTrack)
    {
        double d=0.0;

        if (type == ABSOLUTE) {
            // With an absolute encoder (gray or binary), the resolution
            // defines the total number of tracks. The resolution for a given
            // track is dependent on the track number.  A 3 track absolute
            // encoder has 2^1 stripes on the inner track, 2^2 on the middle
            // track and 2^3 on the outer track
            d = 360.0 / Math.pow(2, resolution - whichTrack);
        }
        else if (type == STANDARD) {
            // TODO: Low: fix this to give degrees for index track? Quad track?
            d = getDegree();
        }
        return d;
    }

    public void setResolution(int s)
    {
        resolution = s;
    }

    public int getResolution()
    {
        return resolution;
    }

    public void setInnerDiameter(int d)
    {
        innerDiameter = d;
    }

    public int getInnerDiameter()
    {
        return innerDiameter;
    }

    public void setOuterDiameter(int d)
    {
        outerDiameter = d;
    }

    public int getOuterDiameter()
    {
        return outerDiameter;
    }

    public void setQuadratureTrack(boolean q)
    {
        quadratureTrack = q;
    }

    public boolean hasQuadratureTrack()
    {
        return quadratureTrack;
    }

    public int getQuadratureTrack()
    {
       int track = -1;

        if (indexTrack == true && quadratureTrack == true) {
            track = 1;
        }
        else if (quadratureTrack == true) {
            track = 1;
        }
        return track;
    }

    public void setIndexTrack(boolean i)
    {
        indexTrack = i;
    }

    public boolean hasIndexTrack()
    {
        return indexTrack;
    }

    public int getIndexTrack()
    {
        int track = -1;

        if (indexTrack == true && quadratureTrack == true) {
            track = 2;
        }
        else if (indexTrack == true) {
            track = 1;
        }

        return track;
    }

    // TODO: Low: Would be nice to do this more flexibly as Enumeration
    /* isChanged
     * 
     * Check every parameter against the property list, which represents
     * what has been saved to disk.  If they match, then there's no change
     * If there are any differences, then the data in memory is different
     * than the data on disk.
     *
     * The try-catch block does error checking on the file load
     */
    public boolean isChanged()
    {
        boolean outcome = false;

        try {
            outcome =
            !(
                // type has to match, as well as the common attributes
                (type == Integer.parseInt(p.getProperty("encoder.type")) &&
                 resolution == Integer.parseInt(p.getProperty("encoder.resolution")) &&
                 innerDiameter == Integer.parseInt(p.getProperty("encoder.innerDiameter")) &&
                 outerDiameter == Integer.parseInt(p.getProperty("encoder.outerDiameter"))
                 ) && (
                    // Absolute or Standard attributes have to match
                    (type == ABSOLUTE &&
                     numbering == Integer.parseInt(p.getProperty("encoder.numbering"))
                     ) ||
                    (type == STANDARD &&
                     indexTrack == Boolean.parseBoolean(p.getProperty("encoder.indexTrack")) &&
                     quadratureTrack == Boolean.parseBoolean(p.getProperty("encoder.quadratureTrack"))
                     )
                 )
             );
        } catch (Exception e) {
            // If p == null or any of the properties are blank, then the encoder
            // hasn't been saved yet
            //System.out.println("WheelEncoder.isChanged() -- " + e.getMessage());
            outcome = true;
        }

        return outcome;
    }

    /* storeProperties()
     *
     * The properties object represents what is stored on disk. It's only called from
     * the routine used to save the object's data to disk.
     */
    private void storeProperties()
    {
        p.setProperty("encoder.type", Integer.toString(type));
        p.setProperty("encoder.numbering", Integer.toString(numbering));
        p.setProperty("encoder.resolution", Integer.toString(resolution));
        p.setProperty("encoder.innerDiameter", Integer.toString(innerDiameter));
        p.setProperty("encoder.outerDiameter", Integer.toString(outerDiameter));
        p.setProperty("encoder.indexTrack", Boolean.toString(indexTrack));
        p.setProperty("encoder.quadratureTrack", Boolean.toString(quadratureTrack));
    }

    /* loadProperties()
     *
     * The properties object represents what is stored on disk. Load the properties
     * file into the properties object, then immediately set the object's attributes
     * to the corresponding properties.
     */
    private void loadProperties()
    {
        type = Integer.parseInt(p.getProperty("encoder.type"));
        numbering = Integer.parseInt(p.getProperty("encoder.numbering"));
        resolution = Integer.parseInt(p.getProperty("encoder.resolution"));
        innerDiameter = Integer.parseInt(p.getProperty("encoder.innerDiameter"));
        outerDiameter = Integer.parseInt(p.getProperty("encoder.outerDiameter"));
        indexTrack = Boolean.parseBoolean(p.getProperty("encoder.indexTrack"));
        quadratureTrack = Boolean.parseBoolean(p.getProperty("encoder.quadratureTrack"));
    }
}
