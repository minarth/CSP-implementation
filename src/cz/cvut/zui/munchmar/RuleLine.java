package cz.cvut.zui.munchmar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minarth
 */
public class RuleLine extends ArrayList<Rule> {
    
    private int coloredCount = -1;

    public int getColoredCount() {
        if (this.coloredCount == -1) {
            int sum = 0;
            for (Rule r : this) {
                sum += r.getCount();
            }
            this.coloredCount = sum;
            return sum;
        } else {
            return this.coloredCount;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for(Rule r : this) {
            result += r.toString() + "\n";
        }
        
        return result;
    }    
}
