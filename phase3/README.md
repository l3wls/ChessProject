Java Chess Game - The Brexicos
Team Information

Team Name: The Brexicos
Team Members:

Lewis Figueroa
Marcio Bratfisch


Semester: Fall 2024
Course Number: CS 3354
Section: R01

Key Components:

Main.java - Application entry point
ChessGame.java - Game logic controller
Board.java - Board state management
Piece Classes - Individual piece movement rules
ChessGUI.java - Graphical user interface
Position.java - Board coordinate utility


How to Compile and Run
Prerequisites

- Java Development Kit (JDK) 11 or higher
- Terminal/Command Prompt

Compilation Instructions
Option 1: Command Line (Mac/Linux)

# Navigate to project directory
cd path/to/phase3

# Compile all Java files
javac -d bin -sourcepath src src/Main.java

# Run the game
java -cp bin Main

Option 2: Using an IDE (Eclipse/IntelliJ)

Open the project in your IDE
Set src as the source folder
# Run Main.java

phase3/
├── src/
│   ├── Main.java
│   ├── game/
│   │   └── ChessGame.java
│   ├── board/
│   │   └── Board.java
│   ├── pieces/
│   │   ├── Piece.java
│   │   ├── King.java
│   │   ├── Queen.java
│   │   ├── Rook.java
│   │   ├── Bishop.java
│   │   ├── Knight.java
│   │   └── Pawn.java
│   ├── gui/
│   │   ├── ChessGUI.java
│   │   ├── BoardPanel.java
│   │   ├── GameHistoryPanel.java
│   │   └── SettingsDialog.java
│   └── utils/
│       └── Position.java
├── bin/
│   └── (compiled .class files)
├── images/
│   └── (screenshots and class diagrams txt)
└── README.md

Features Checklist
Phase 1: Basic Implementation

 8x8 chessboard display
 All 6 piece types implemented (King, Queen, Rook, Bishop, Knight, Pawn)
 Basic piece movement
 Terminal User Interface
 Click-to-move functionality

Phase 2: GUI 

 Colored square highlighting
 Turn indicator display
 Captured pieces display
 Move history panel
 Menu bar (New Game, Settings, Exit)
 Customizable board colors
 Board size options (Small, Medium, Large)
 Check/Checkmate popup notifications
 Terminal output for captures and game status

Phase 3: Full Chess Rules

 Check detection
 Checkmate detection
 Pawn promotion (auto-promotes to Queen)
 Move history tracking
 Undo functionality
 Piece Logic
