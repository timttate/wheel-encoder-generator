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

    private int stripes;
    private double degree;
    private int innerDiameter;
    private int outerDiameter;

    public double getDegree()
    {
        return degree;
    }

    public void setResolution(int s)
    {
        stripes = s;
        degree = 360.0 / stripes;
    }

    public int getResolution()
    {
        return stripes;
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
}
