package cz.cvut.zui.munchmar;

/**
 * Linked list of spaces between filled tiles
 * @author munchmar
 */
public class Row {

    private Variable first;         // First variable in row
    private Variable last;          // Last variable in row
    private int filled = 0;         // Number of filled tiles
    private int length;             // Length of a row
    private int numberOfnodes;      // Length of linked list
    private RuleLine rules;         // Rules that generates this Row

    public Row(int length, RuleLine rules) {
        this.length = length;
        this.rules = rules;
        numberOfnodes = rules.size();
        this.generateRow();
    }

    public int getNumberOfnodes() {
        return numberOfnodes;
    }
    
    public boolean isFilled() {
        return (length == filled);
    }

    private void generateRow() {
        int i = 0;
        Variable tmp;
        int sum = 0;
        for (Rule r : rules) {
            sum += r.getCount();
        }
        
        for (Rule r : rules) {
            if (i == 0) {
                tmp = new Variable(0, null, r.getColor(), r.getCount(), 0, length - sum);
                first = tmp;
            } else {
                if (last.getColor() == r.getColor()) {
                    tmp = new Variable(1, last, r.getColor(), r.getCount(), 
                            last.getLength() + last.getPosition(), length - sum);
                    filled++;
                } else {
                    tmp = new Variable(0, last, r.getColor(), r.getCount(), 
                            last.getLength() + last.getPosition(), length - sum);
                }

                last.setNext(tmp);
            }
            
            filled += r.getCount();
            last = tmp;
            i++;

        }
    }

    @Override
    public String toString() {
        String result = isFilled() + " ";
        Variable goThrough = first;

        while (goThrough != null) {
            result += goThrough + ", ";
            goThrough = goThrough.getNext();
        }
        return result;
    }

    public char getAt(int i) {
        Variable tmp = first;
        if (i >= length) {
            return '\0';
        }
        
        while (tmp != null) {
            if (tmp.getNext() != null && tmp.getNext().getPosition() > i) {
                break;
            } else {
                tmp = tmp.getNext();
            }
        }
        
        if (tmp != null) {
            return tmp.getAt(i);
        } else {
            return last.getAt(i);
        }

        
    }
    
    public Variable getVariableAt(int i) {
        if (i >= numberOfnodes) {
            return null;
        }
        Variable tmp = first;
        
        for (int j = 0; j < i; j++) {
          
            tmp = tmp.getNext();
           
        }
        
        return tmp;
    }
    
}
