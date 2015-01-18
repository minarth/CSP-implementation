package cz.cvut.zui.munchmar;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *  State of puzzle
 * Class for checkin' consistency and correctness
 * Also prints correct puzzle into file
 * @author munchmar
 */
public class State {

    private int columns, rows;
    private Row[] radky;

    public State(int columns, int rows, RuleLine[] rulesRows) {
        this.columns = columns;
        this.rows = rows;
        //this.rulesColumns = rulesColumns;
        this.radky = new Row[rows];

        for (int i = 0; i < rows; i++) {
            radky[i] = new Row(columns, rulesRows[i]);
        }

    }

    public Row[] getRows() {
        return radky;
    }

    public boolean isConsistent(RuleLine[] rulesColumns) {

        RuleLine[] actualColoring = new RuleLine[columns];
        boolean foundColor;
        char var;
        for (int i = 0; i < columns; i++) {
            actualColoring[i] = new RuleLine();

            // Transform rows into actualColoring
            // Go through whole column and add colors
            for (int j = 0; j < rows; j++) {
                foundColor = false;
                var = radky[j].getAt(i);
                
                if (var != '_') {
                    for (int k = 0; k < actualColoring[i].size(); k++) {
                        if (var == actualColoring[i].get(k).getColor()) {
                            actualColoring[i].get(k).addCount(1);
                            foundColor = true;
                            break;
                        }
                    }

                    if (!foundColor) {
                        actualColoring[i].add(new Rule(var, 1));
                    }
                }
            }

            if (actualColoring[i].size() > rulesColumns[i].size()) {
                return false;
            }

            for (int j = 0; j < actualColoring[i].size(); j++) {
                foundColor = false;
                for (int k = 0; k < rulesColumns[i].size(); k++) {
                    if (actualColoring[i].get(j).getColor() == rulesColumns[i].get(k).getColor()) {
                        foundColor = true;
                        if (actualColoring[i].get(j).getCount() > rulesColumns[i].get(k).getCount()) {
                            return false;
                        } else {
                            break;
                        }
                    }
                }

                if (!foundColor) {
                    return false;
                }
            }
        }
        return true;

    }

    // Returns a list of filled connected tiles in the column
    private ArrayList<Rule> filledTiles(int column) {
        ArrayList<Rule> result = new ArrayList<>();
        int length = 0;
        char currentColor = '\0';
        for (int i = 0; i < rows; i++) {
            if (radky[i].getAt(column) == '_') {
                if (length != 0) {
                    result.add(new Rule(currentColor, length));
                    currentColor = '\0';
                    length = 0;
                }
            } else if (length != 0) {
                if (radky[i].getAt(column) == currentColor) {
                    length++;
                } else {
                    result.add(new Rule(currentColor, length));
                    length = 1;
                    currentColor = radky[i].getAt(column);
                }

            } else {
                length++;
                currentColor = radky[i].getAt(column);
            }
        }

        if (length != 0) {
            result.add(new Rule(currentColor, length));
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (Row r : radky) {
            result += r + "\n";
        }

        return result;
    }

    /**
     * Function that checks correctness of state. Returns true, if all rules are
     * satisfied
     *
     * @return
     */
    public boolean checkCorrectness(RuleLine[] rulesColumns) {
        ArrayList<Rule> realityOfColumn;

        for (int i = 0; i < columns; i++) {
            realityOfColumn = filledTiles(i);
            if (realityOfColumn.size() != rulesColumns[i].size()) {
                return false;
            }

            for (int j = 0; j < realityOfColumn.size(); j++) {
                if (!realityOfColumn.get(j).equals(rulesColumns[i].get(j))) {
                    return false;
                }
            }

        }

        return true;
    }

    public void printPuzzle(PrintWriter wr) {
        String tmp;

        for (Row r : radky) {
            tmp = "";
            for (int i = 0; i < columns; i++) {
                tmp += r.getAt(i);
            }
            wr.println(tmp);
            
        }
    }
    
    public void printPuzzle() {
        String tmp;
        
        for (Row r : radky) {
            tmp = "";
            for (int i = 0; i < columns; i++) {
                tmp += r.getAt(i);
            }
            System.out.println(tmp);
            
        }
        
        System.out.println("-------");
    }
}
