# SudokuSolver
A program able to solve simple sudokus
This program uses the "Test.txt" file as the sudoku to be solved, it is formated as a csv file with the "|" as the delimiter.
The way it works is very similar to a normal human aproach; it checks for each spot all possible choices for the digit.
If this spot is empty (0) and there is only one possible choice, it replacese it.
