# Chess Game - Phase 2: GUI Implementation

## Overview
This is Phase 2 of the Java Chess Game project, featuring a complete Graphical User Interface (GUI) built using Java Swing. This phase focuses on visual aspects and basic board functionality without implementing full chess rules.

## Project Structure
```
chessProject/phase2/
├── src/
│   ├── gui/           # All GUI classes
│   ├── board/         # Simplified board logic
│   ├── pieces/        # Piece representations
│   ├── utils/         # Utility classes
│   ├── game/          # Game flow management
│   └── Main.java      # Application entry point
└── bin/               # Compiled class files
```

## Core Requirements Implemented

### 1. Chessboard GUI
- 8x8 chessboard with alternating light and dark squares
- Customizable color themes (Classic, Green, Blue, Gray, Purple)
- Visual coordinates with file labels (A-H) and rank labels (1-8)
- Professional appearance with bordered squares and consistent styling

### 2. Chess Pieces Display
- Unicode chess symbols for all piece types (no external images required)
- Clear visual distinction between white and black pieces
- Proper initial placement following standard chess setup
- Scalable font rendering for different board sizes

### 3. Piece Movement
- Click-to-move functionality - select piece then destination
- Visual selection feedback - highlighted squares in yellow
- Any piece can move anywhere - no validation rules in Phase 2
- Real-time board updates - immediate visual feedback

### 4. Capturing Pieces
- Automatic capture detection when moving to occupied squares
- Visual removal of captured pieces from the board
- Capture tracking in the game history panel
- Capture sound indicators (system beep)

### 5. Endgame Notification
- King capture detection - game recognizes when a king is captured
- Winner declaration popup - clear announcement of game result
- Game state management - prevents further moves after game over

## Extra GUI Features Implemented

### Extra Feature 1: Menu Bar with Game Controls
- **New Game** - Resets the board to initial state
- **Save Game** - Placeholder for future implementation
- **Load Game** - Placeholder for future implementation
- **Settings** - Access to customization options
- **Exit** - Clean application termination
- **Help/About** - Project information and features list

### Extra Feature 2: Settings Window for Customization
- **Board Theme Selection** - Multiple color schemes:
  - Classic (cream/brown)
  - Green (light green/dark green)
  - Blue (light blue/dark blue)
  - Gray (light gray/dark gray)
  - Purple (light purple/dark purple)
- **Board Size Options** - Small, Medium, Large
- **Real-time Preview** - See changes before applying
- **Reset to Default** - Quick restoration of original settings

### Extra Feature 3: Game History Panel with Undo
- **Move History** - Complete record of all moves made
- **Captured Pieces List** - Visual display of captured pieces
- **Undo Functionality** - Revert to previous game state
- **Clear History** - Reset move tracking
- **Auto-scrolling** - Always shows most recent moves

## Additional Features (Beyond Requirements)

### Visual Enhancements
- Professional UI Layout - Clean, organized interface
- Responsive Design - Adapts to different window sizes
- Consistent Styling - Unified color scheme and fonts
- Visual Feedback - Highlighting, borders, and spacing

### User Experience
- Intuitive Controls - Easy-to-understand interaction model
- Immediate Feedback - Instant visual updates
- Error Prevention - Clear selection states
- Accessible Design - High contrast and readable text

## Technical Implementation

### Architecture
- **Model-View-Controller Pattern** - Separation of concerns
- **Event-Driven Design** - Responsive to user interactions
- **Object-Oriented Structure** - Modular, maintainable code
- **Swing Components** - Standard Java GUI framework

### Key Classes
- `ChessGUI` - Main application window and controller
- `BoardPanel` - Chess board display and interaction
- `GameHistoryPanel` - Move tracking and undo functionality
- `SettingsDialog` - User customization interface
- `PieceImages` - Unicode symbol management
- `ChessGame` - Simplified game logic engine

## How to Run

### Compilation
```bash
cd phase2
javac -d bin src/**/*.java src/Main.java
```

### Execution
```bash
java -cp bin Main
```

### Alternative Compilation (if above fails)
```bash
find src -name "*.java" > sources.txt
javac -d bin @sources.txt
```

## Phase 2 Focus
This implementation focuses exclusively on visual aspects and basic functionality as required by Phase 2 specifications. **No chess movement rules or validation are enforced** - any piece can move to any square.

## Future Enhancements (Phase 3)
- Implement full chess rules and move validation
- Add check and checkmate detection
- Implement special moves (castling, en passant, pawn promotion)
- Add timer functionality
- Implement save/load game features
- Add multiplayer support