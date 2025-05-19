import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordCounter extends JFrame {
    private JTextArea textArea;
    private JButton countButton;
    private JLabel resultLabel;
    private JLabel charCountLabel;
    private JButton loadFileButton;

    public WordCounter() {
        setTitle("Word Counter");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        countButton = new JButton("Count Words");
        resultLabel = new JLabel("Word Count: 0");
        charCountLabel = new JLabel("Character Count: 0");
        loadFileButton = new JButton("Load File");

        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                int wordCount = countWords(text);
                int charCount = countChars(text);
                resultLabel.setText("Word Count: " + wordCount);
                charCountLabel.setText("Character Count: " + charCount);
            }
        });

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(WordCounter.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileChooser.getSelectedFile();
                    try {
                        java.nio.file.Path path = file.toPath();
                        String content = new String(java.nio.file.Files.readAllBytes(path));
                        textArea.setText(content);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(WordCounter.this, "Failed to load file: " + ex.getMessage());
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(countButton);
        bottomPanel.add(resultLabel);
        bottomPanel.add(charCountLabel);
        bottomPanel.add(loadFileButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    private int countChars(String text) {
        if (text == null) return 0;
        return text.length();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordCounter wc = new WordCounter();
            wc.setVisible(true);
        });
    }
}
