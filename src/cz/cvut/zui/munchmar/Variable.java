package cz.cvut.zui.munchmar;

import java.util.ArrayList;

/**
 * Class that will keep empty spaces between colored tiles, variable for CSP
 *
 * @author munchmar
 */
public class Variable {

    private int minimalLength;              // minimal space length
    private int currentLength;              // current space length
    private Variable before;                // Variable before this one
    private Variable next;                  // Variable after this one
    private boolean isUsed = false;
    private char colorAfterThisSpace;       // Color of tile after this space
    private int lengthOfFilledAfter;        // Length of color field after this space
    private int position;                   // Position of beginning of this block (space + color)
    private ArrayList<Integer> domain;   //Domain of possible lenghts of space

    public Variable(int defaultLength, Variable before, char colorAfterThisSpace,
            int lengthOfFilledAfter, int position, int maximalSpaceLength) {
        this.minimalLength = defaultLength;
        this.currentLength = defaultLength;
        this.before = before;
        this.next = null;
        this.colorAfterThisSpace = colorAfterThisSpace;
        this.lengthOfFilledAfter = lengthOfFilledAfter;
        this.position = position;
        domain = defaultDomain(maximalSpaceLength);

    }

    private ArrayList<Integer> defaultDomain(int maximum) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = minimalLength; i <= maximum; i++) {
            tmp.add(i);
        }
        return tmp;
    }

    /*public void setBefore(Variable var) {
        this.before = var;
    }*/

    public int getnextValueFromDomain() {
        return domain.remove(position);
    }

    public ArrayList<Integer> getDomain() {
        return domain;
    }

    private void updateFollowPositions() {
        this.setPosition(position);
    }

    public void setPosition(int position) {
        this.position = position;
        if (this.next != null) {
            this.next.setPosition(position + this.getLength());
            //System.out.println("used");
        }
    }

    public char getAt(int i) {
        i = i - position;
        if (!isUsed) {
            return '_';
        }
        if ((i >= currentLength) && (i < (currentLength + lengthOfFilledAfter))) {
            return colorAfterThisSpace;
        }
        return '_';
    }

    public void resetSpaceLength() {
        
        currentLength = minimalLength;
        updateFollowPositions();
        isUsed = false;
    }

    public void setSpaceLength(int t) {
        currentLength = t;
        isUsed = true;
        updateFollowPositions();
        
    }

    public int getSpaceLength() {
        return currentLength;
    }

    public int getLength() {
        return currentLength + lengthOfFilledAfter;
    }

    public int getLengthOfFilledAfter() {
        return lengthOfFilledAfter;
    }

    public int getPosition() {
        return position;
    }

    public void setNext(Variable next) {
        this.next = next;
    }

    public Variable getNext() {
        return next;
    }

    @Override
    public String toString() {
        return "{current:" + currentLength + ", used:"
                + isUsed + ", length:" + lengthOfFilledAfter + 
                ", pos:" + position + " , dmain: " + domain +'}';
    }

    public String toStringActive() {
        if (isUsed) {
            return toString();
        } else {
            return null;
        }
    }

    public char getColor() {
        return colorAfterThisSpace;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
