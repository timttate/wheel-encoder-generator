/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wheelencodergenerator;

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

    public static int ABSOLUTE=0;
    public static int STANDARD=1;
    public static int GRAY=0;
    public static int BINARY=1;

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
            // TODO: fix this to give degrees for index track? Quad track?
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
}
