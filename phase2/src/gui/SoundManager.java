package gui;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages sound effects for the chess game.
 * Uses system beeps as placeholders for actual sound files.
 */
public class SoundManager {
    private Map<String, Integer> soundFrequencies;
    private boolean soundsEnabled;

    /**
     * Constructs the sound manager and initializes sound mappings.
     */
    public SoundManager() {
        soundFrequencies = new HashMap<>();
        soundsEnabled = true;
        initializeSounds();
    }

    /**
     * Initializes the sound frequency mappings.
     */
    private void initializeSounds() {
        soundFrequencies.put("move", 440); // A4
        soundFrequencies.put("capture", 880); // A5
        soundFrequencies.put("check", 659); // E5
        soundFrequencies.put("checkmate", 523); // C5
        soundFrequencies.put("newgame", 1047); // C6
        soundFrequencies.put("save", 784); // G5
        soundFrequencies.put("load", 1047); // C6
    }

    /**
     * Plays a sound effect using system beep.
     *
     * @param soundName the name of the sound to play
     */
    public void playSound(String soundName) {
        if (!soundsEnabled) {
            return;
        }

        try {
            // Use system beep as placeholder
            Toolkit.getDefaultToolkit().beep();

            // In a full implementation, you would play actual sound files here
            // based on the soundName parameter
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    /**
     * Enables or disables all sound effects.
     *
     * @param enabled true to enable sounds, false to disable
     */
    public void setSoundsEnabled(boolean enabled) {
        this.soundsEnabled = enabled;
    }

    /**
     * Checks if sounds are currently enabled.
     *
     * @return true if sounds are enabled, false otherwise
     */
    public boolean isSoundsEnabled() {
        return soundsEnabled;
    }
}