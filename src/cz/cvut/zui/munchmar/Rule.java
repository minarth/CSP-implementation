package cz.cvut.zui.munchmar;

/**
 *  Class for encapsulation of Rule
 * @author munchmar
 */
public class Rule {
    private char color;
    private int count;
    

    Rule(char color, int count) {
        this.color = color;
        this.count = count;
    }

    @Override
    public String toString() {
        return "(" + color + ", " + count + ')';
    }

    public int getCount() {
        return count;
    }
    
    public char getColor() {
        return this.color;
    }
    
    public void addCount(int count) {
        this.count += count;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.color;
        hash = 67 * hash + this.count;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rule other = (Rule) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.count != other.count) {
            return false;
        }
        return true;
    }
    
    
    
}
