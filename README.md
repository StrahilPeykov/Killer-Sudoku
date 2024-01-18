# Killer Sudoku Puzzle Assistant

Assignment G2 2023Q2 58

This is a puzzle assistant for the popular logic puzzle game, Killer Sudoku. The game is an extension of a regular sudoku puzzle, with the added constraint that every cell is a part of a cage, indicated by colors. The cells must be added up to the sum of its cage, and numbers cannot repeat within a cage or within a single row, column or 3x3 region.

This project is an assignment for G2 2022Q2 58, developed by:
* Strahil Peykov (1678167)
* Mila van Bokhoven ()
* Tunay Ata Gök ()
* Borna Simic ()
* Ole Wouters ()

The Puzzle Assistant has the following features:
* Opening a file with puzzle description
* A graphical view of the puzzle state
* Clicking on cells and/or entering a symbol in a selected cell to change its state (i.e. fill or empty a cell)
* Undo and redo
* Applying reasoners to fill forced cells
* Starting a backtracking solver, in a background thread
* Switching between solving, editing, and viewing (read-only) mode

To run the Puzzle Assistant, you will need to select the kdoku.zgr file to see a visualization of a killer sudoku puzzle. The project is built using Java, and it uses various design patterns such as Command for undo-redo, reasoners, backtracker; Strategy or Template Method for reasoners; and Composite for compound commands as well as compound reasoners. Additionally, it utilizes the Model-View-Controller architecture and has a package ypa with package-info.java describing your YPA, package ypa.model with multiple cooperating ADT classes that define the model, package ypa.gui with your YPA GUI, package ypa.command with an Undo-Redo facility, package ypa.reasoning with automatic reasoners and package ypa.solvers with a backtracking solver.

To run the project, you will need to have Java installed on your machine and use an IDE to run the main class. The project also includes JUnit 5 test cases for core non-GUI classes, ypa.model, ypa.command, ypa.reasoning and ypa.solvers, to ensure the proper functioning of the Puzzle Assistant.

Overall, the goal of this project is to provide an interactive and user-friendly solution for solving and creating Killer sudoku puzzles. With its various features and design patterns, it aims to make the solving process more efficient and enjoyable.
