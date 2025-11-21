package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog window for customizing chess game appearance.
 * Allows users to change board colors and themes.
 */
public class SettingsDialog extends JDialog {
    private ChessGUI chessGUI;
    private JComboBox<String> boardThemeCombo;
    private JComboBox<String> boardSizeCombo;
    private JButton applyButton;
    private JButton cancelButton;
    private JButton resetButton;

    private Color originalLightColor;
    private Color originalDarkColor;

    // Predefined color themes
    private static final String[] THEMES = { "Classic", "Green", "Blue", "Gray", "Purple" };
    private static final Color[][] THEME_COLORS = {
            { new Color(240, 217, 181), new Color(181, 136, 99) }, // Classic
            { new Color(238, 238, 210), new Color(118, 150, 86) }, // Green
            { new Color(173, 216, 230), new Color(70, 130, 180) }, // Blue
            { new Color(200, 200, 200), new Color(100, 100, 100) }, // Gray
            { new Color(216, 191, 216), new Color(147, 112, 219) } // Purple
    };

    /**
     * Constructs the settings dialog.
     *
     * @param parent   the parent frame
     * @param chessGUI the main chess GUI
     */
    public SettingsDialog(Frame parent, ChessGUI chessGUI) {
        super(parent, "Game Settings", true);
        this.chessGUI = chessGUI;
        this.originalLightColor = chessGUI.getLightSquareColor();
        this.originalDarkColor = chessGUI.getDarkSquareColor();

        initializeDialog();
        setupEventHandlers();
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Create settings panels
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(createBoardSettingsPanel());
        mainPanel.add(createControlPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createBoardSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Board Settings"));

        JLabel themeLabel = new JLabel("Board Theme:");
        boardThemeCombo = new JComboBox<>(THEMES);
        boardThemeCombo.setSelectedIndex(0); // Classic

        JLabel sizeLabel = new JLabel("Board Size:");
        String[] sizes = { "Small", "Medium", "Large" };
        boardSizeCombo = new JComboBox<>(sizes);
        boardSizeCombo.setSelectedIndex(1); // Medium

        panel.add(themeLabel);
        panel.add(boardThemeCombo);
        panel.add(sizeLabel);
        panel.add(boardSizeCombo);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        applyButton = new JButton("Apply");
        cancelButton = new JButton("Cancel");
        resetButton = new JButton("Reset to Default");

        panel.add(applyButton);
        panel.add(cancelButton);
        panel.add(resetButton);

        return panel;
    }

    private void setupEventHandlers() {
        // Apply button
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySettings();
            }
        });

        // Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelSettings();
            }
        });

        // Reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSettings();
            }
        });

        // Theme combo box - preview changes
        boardThemeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewTheme();
            }
        });
    }

    private void applySettings() {
        // Apply board colors
        int themeIndex = boardThemeCombo.getSelectedIndex();
        if (themeIndex >= 0 && themeIndex < THEME_COLORS.length) {
            Color lightColor = THEME_COLORS[themeIndex][0];
            Color darkColor = THEME_COLORS[themeIndex][1];
            chessGUI.setBoardColors(lightColor, darkColor);
        }

        JOptionPane.showMessageDialog(this,
                "Settings applied successfully!",
                "Settings Applied",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private void cancelSettings() {
        // Restore original colors
        chessGUI.setBoardColors(originalLightColor, originalDarkColor);
        dispose();
    }

    private void resetSettings() {
        boardThemeCombo.setSelectedIndex(0); // Classic
        boardSizeCombo.setSelectedIndex(1); // Medium

        // Preview classic theme
        previewTheme();
    }

    private void previewTheme() {
        int themeIndex = boardThemeCombo.getSelectedIndex();
        if (themeIndex >= 0 && themeIndex < THEME_COLORS.length) {
            Color lightColor = THEME_COLORS[themeIndex][0];
            Color darkColor = THEME_COLORS[themeIndex][1];
            chessGUI.setBoardColors(lightColor, darkColor);
        }
    }
}