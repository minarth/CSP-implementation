/*

 */
package cz.cvut.zui.munchmar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Simple implementation of CSP solver for painted puzzles Variables are the
 * spaces between filled connected tiles in rows implemented as linked list and
 * constraints are rules for columns
 *
 * Variables are picked with very simple heuristics - unused longest in rows is
 * trying
 *
 * domains are possible length of space, which are cut of, with condition, that
 * they cannot overcome some threshold (which decreases with filled row)
 *
 * @author munchmar
 */
public class CSPMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int rows, columns;
        String[] tmp;
        String filename;

        RuleLine[] rulesColumns, rulesRows;

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            // Name of output file
            filename = args[0];
            filename = filename.replace(".txt", "_ooutput.txt");

            String sCurrentLine = br.readLine();
            tmp = sCurrentLine.split(",");
            rows = Integer.parseInt(tmp[0]);
            columns = Integer.parseInt(tmp[1]);

            rulesColumns = new RuleLine[columns];
            rulesRows = new RuleLine[rows];


            int i = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                if (i < rows) {
                    rulesRows[i] = readLine(sCurrentLine);
                } else {
                    rulesColumns[i - rows] = readLine(sCurrentLine);
                }

                i++;
            }

            State state = new State(columns, rows, rulesRows);
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            backtrackSearch(state, new CSP(rulesColumns, columns, rows), writer);
            writer.close();
        } catch (IOException e) {
        }
    }

    private static RuleLine readLine(String oneLine) {
        String[] line = oneLine.split(",");
        RuleLine result = new RuleLine();

        for (int i = 0; i < line.length; i = i + 2) {
            result.add(new Rule(line[i].charAt(0), Integer.parseInt(line[i + 1])));
        }

        return result;
    }

    /**
     * Method for solving Constraint satisfactory problem
     *
     * @param state State of puzzle
     * @param csp constraints
     * @param wr file writer object
     * @return
     */
    private static State backtrackSearch(State state, CSP csp, PrintWriter wr) {

        // Is correct? Good!
        if (state.checkCorrectness(csp.getConstraints())) {
            state.printPuzzle(wr);
            wr.println();
            return null;

        }

        // Select Variable
        Variable var = csp.getNext(state.getRows());
        if (var == null) {
            return null;
        }

        //ForEach value in ordered Domain -> try
        for (int i : var.getDomain()) {

            // If it is possible
            if ((i + var.getLengthOfFilledAfter() + var.getPosition()) <= csp.getColumns()) {
                // Assign
                var.setSpaceLength(i);

                

                // Check consistency
                if (state.isConsistent(csp.getBetterConstraints())) {
                    // Work through
                    State s = backtrackSearch(state, csp, wr);

                    // Outcome is not failure
                    if (s != null) {
                        s.printPuzzle(wr);
                        return s;
                    }
                }

                // reset assignment
                var.resetSpaceLength();
            }
        }
        return null;
    }
}