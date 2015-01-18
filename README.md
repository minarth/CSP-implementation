# Solving puzzles as constraints satisfaction problem

## Assingment
Your task is to write an algorithm which solves a drawn crossword puzzle. The drawn crossword puzzles are brain teasers, in which there is a legend with number placed around the grid used to draw a picture. Each number in the legend sets the number of the following boxes filled with colour which belongs to the given number. The following rules hold:

- between two blocks of the boxes filled with the same colour there is always at least one empty box
- between the boxes filled with different colour there does not have to be an empty box
- the order of the numbers in the legend sets the order of the blocks of the boxes

Input

The program is going to read the file which it gets as the first argument and which has got the following format:

NUMBER_OF_ROWS,NUMBER_OF_COLUMNS
CONSTRAINTS_FOR_ROW_1
CONSTRAINTS_FOR_ROW_2
...
CONSTRAINTS_FOR_ROW_M
CONSTRAINTS_FOR_COLUMN_1
CONSTRAINTS_FOR_COLUMN_2
...
CONSTRAINTS_FOR_COLUMN_N
where each constraint has the format:

COLOR_1,NUMBER_1,COLOR_2,NUMBER_2,...,NUMBER_K
where the field “color” applies to the following number and is in a format of the character which represents the colour you will use for drawing (for example '#'). The field “number” sets the size of the given block.

# how does it work?
Simple implementation of CSP solver with backtrack search for painted puzzles. Variables are the
  spaces between filled connected tiles in rows implemented as linked list and
  constraints are rules for columns
 
  Variables are picked with very simple heuristics - unused longest in rows is
  trying
 
  domains are possible length of space, which are cut of, with condition, that
  they cannot overcome some threshold (which decreases with filled row)
 