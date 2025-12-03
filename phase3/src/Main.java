import javax.swing.SwingUtilities;
import gui.ChessGUI;

/**
 * Main class for Phase 3 - GUI Chess Game.
 * This class launches the graphical version of the chess game.
 */
public class Main {

    /**
     * Main entry point for the chess game GUI.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        // Run the GUI creation on the Swing event-dispatch thread
        // This ensures thread safety for all UI operations.
        SwingUtilities.invokeLater(() -> {

            // Create the main Chess GUI window
            ChessGUI gui = new ChessGUI();

            // Make the window visible on the screen
            gui.setVisible(true);
        });
    }
}
