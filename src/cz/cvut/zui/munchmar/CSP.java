package cz.cvut.zui.munchmar;

/**
 *
 * @author munchmar
 */
public class CSP {

    private RuleLine[] constraints;
    private int columns, rows;
    private boolean fail = false;
    private RuleLine[] betterConstraints;

    
    public Variable getNext(Row[] variables) {
        Variable tmp = null;
        Variable inLoopTmp;

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < variables[j].getNumberOfnodes(); i++) {
                inLoopTmp = variables[j].getVariableAt(i);
                if (!inLoopTmp.isUsed()) {
                    if (tmp == null) {
                        tmp = inLoopTmp;
                    } else if (tmp.getLengthOfFilledAfter() < inLoopTmp.getLengthOfFilledAfter()) {
                        tmp = inLoopTmp;
                    }
                }
            }
        }

        return tmp;

    }

    public CSP(RuleLine[] constraints, int columns, int rows) {
        this.constraints = constraints;
        this.columns = columns;
        this.rows = rows;
        this.betterConstraints = getColorsInColumns();
    }

    public RuleLine[] getConstraints() {
        return constraints;
    }

    public RuleLine[] getBetterConstraints() {
        return betterConstraints;
    }

    public boolean isFail() {
        return fail;
    }

    public void printConstraints() {
        for (RuleLine line : constraints) {
            System.out.println(line);
        }
    }

    public int getColumns() {
        return columns;
    }

    private RuleLine[] getColorsInColumns() {

        RuleLine[] result = new RuleLine[columns];
        int j, k;
        for (int i = 0; i < columns; i++) {
            result[i] = new RuleLine();

            // copy constraints into result
            for (Rule r : constraints[i]) {
                result[i].add(new Rule(r.getColor(), r.getCount()));
            }

            // go through list and merge same colors
            j = 0;
            while (j < result[i].size()) {
                k = j + 1;
                while (k < result[i].size()) {
                    if (result[i].get(j).getColor() == result[i].get(k).getColor()) {
                        result[i].get(j).addCount(result[i].get(k).getCount());
                        result[i].remove(k);
                    } else {
                        k++;
                    }
                }
                j++;
            }
        }

        return result;
    }
}
