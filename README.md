# ChessProject
Using Java to create a functional Chess game
# Java Chess Game

A complete console-based chess game implemented in Java, featuring two-player gameplay with full chess rules including check, checkmate, stalemate, and move validation.

## Features

- **Complete Chess Rules**: All standard chess piece movements and captures
- **Check Detection**: Automatically detects when a king is in check
- **Checkmate & Stalemate**: Proper end-game condition detection
- **Move Validation**: Prevents illegal moves and moves that put your own king in check
- **Capture Tracking**: Displays captured pieces and announces captures
- **Algebraic Notation**: Uses standard chess notation for moves (e.g., E2 E4)
- **King Side Castle**: (O-O) & Queen Side Castle (O-O-O)
- **Pawn to Queen Promotion**: All Pawns that reach otherside becomes Queens

## How to Run the Game

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line terminal

### Compilation and Execution

1. **Clone the repository** (if from GitHub):
   ```bash
   git clone https://github.com/yourusername/chessProject.git
   cd chessProject
   
2. Compile the game:
   ```bash
   javac -d bin src/**/*.java src/main.java
   
3. Run the game:

   ```bash
   java -cp bin main
   
Alternative Compilation (if the above doesn't work)
Windows:

   ```bash
   javac -d bin src\*.java src\**\*.java
   java -cp bin main
   ```
How to Play
Starting the Game: The game begins with white pieces moving first.

Making Moves:

Enter moves in the format: [FROM] [TO]

Example: E2 E4 moves the piece from E2 to E4

Example: G1 F3 moves the knight from G1 to F3

Game Features:

The board automatically updates after each move

Captures are announced with sound effects in the console

Check warnings are displayed when a king is threatened

The game ends with checkmate, stalemate, or king capture

Piece Symbols:

wp, bp - White/Black Pawn

wR, bR - White/Black Rook

wN, bN - White/Black Knight

wB, bB - White/Black Bishop

wQ, bQ - White/Black Queen

wK, bK - White/Black King

O-O - King Side Castle

O-O-O - Queen Side Castle

## " " - Checkerboard Pattern

Game Rules Implemented
✅ All basic piece movements

✅ Check detection and prevention

✅ Checkmate and stalemate detection

✅ Capture mechanics

✅ Move validation (cannot expose king to check)

✅ Turn-based gameplay

✅ King capture ends game immediately & Check Mate

✅ Pawn to Queen Promo

✅ King Side & Queen Side Castling
