import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private static final int MIN = 1, MAX = 100, MAX_ATTEMPTS = 7;
    private final Random random = new Random();
    private final JTextField input = new JTextField(9);
    private final JButton guessButton = new JButton("Make a Guess");
    private final JButton newGameButton = new JButton("New Game");
    private final JLabel message = new JLabel("", SwingConstants.CENTER);
    private final JLabel attempts = new JLabel("", SwingConstants.CENTER);
    private final JLabel score = new JLabel("", SwingConstants.CENTER);
    private final JTextArea history = new JTextArea(7, 27);
    private int secret, used, rounds, wins;
    private boolean finished;

    public NumberGuessingGameGUI() {
        super("Number Guessing Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 510);
        setMinimumSize(new Dimension(460, 470));
        setLocationRelativeTo(null);
        buildUI();
        startRound();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        root.setBackground(new Color(245, 248, 255));
        setContentPane(root);

        JLabel title = new JLabel("NUMBER GUESSING GAME", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 23));
        title.setForeground(new Color(37, 63, 122));
        JLabel subtitle = new JLabel("Can you find the secret number?", SwingConstants.CENTER);

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 5));
        header.setOpaque(false);
        header.add(title);
        header.add(subtitle);
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        message.setFont(new Font("SansSerif", Font.BOLD, 15));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        message.setBorder(BorderFactory.createEmptyBorder(12, 0, 10, 0));
        attempts.setAlignmentX(Component.CENTER_ALIGNMENT);
        score.setFont(new Font("SansSerif", Font.BOLD, 13));
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        score.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        controls.setOpaque(false);
        input.setFont(new Font("SansSerif", Font.PLAIN, 17));
        input.setHorizontalAlignment(JTextField.CENTER);
        controls.add(input);
        controls.add(guessButton);

        history.setEditable(false);
        history.setLineWrap(true);
        history.setWrapStyleWord(true);
        history.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(history);
        scroll.setBorder(BorderFactory.createTitledBorder("Guess history"));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(420, 145));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));

        JPanel newPanel = new JPanel();
        newPanel.setOpaque(false);
        newPanel.add(newGameButton);

        center.add(message);
        center.add(attempts);
        center.add(score);
        center.add(Box.createVerticalStrut(8));
        center.add(controls);
        center.add(Box.createVerticalStrut(8));
        center.add(scroll);
        center.add(Box.createVerticalStrut(8));
        center.add(newPanel);
        root.add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("Use each clue to narrow down the number.", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 11));
        root.add(footer, BorderLayout.SOUTH);

        guessButton.addActionListener(e -> makeGuess());
        input.addActionListener(e -> makeGuess());
        newGameButton.addActionListener(e -> startRound());
    }

    private void startRound() {
        secret = random.nextInt(MAX - MIN + 1) + MIN;
        used = 0;
        finished = false;
        rounds++;
        message.setForeground(new Color(37, 63, 122));
        message.setText("I'm thinking of a number from 1 to 100.");
        attempts.setText("Attempts left: " + MAX_ATTEMPTS);
        score.setText("Wins: " + wins + "  |  Rounds: " + rounds);
        history.setText("");
        input.setText("");
        input.setEnabled(true);
        guessButton.setEnabled(true);
        input.requestFocusInWindow();
    }

    private void makeGuess() {
        if (finished) return;
        int value;
        try {
            value = Integer.parseInt(input.getText().trim());
        } catch (NumberFormatException ex) {
            showMessage("Enter a whole number, such as 42.", true);
            return;
        }
        if (value < MIN || value > MAX) {
            showMessage("Your number must be between 1 and 100.", true);
            return;
        }

        used++;
        history.append("Attempt " + used + ": " + value + " — ");
        if (value == secret) {
            history.append("Correct!\n");
            wins++;
            finished = true;
            showMessage("Correct! You found it in " + used + (used == 1 ? " attempt!" : " attempts!"), false);
            finishRound();
        } else if (value < secret) {
            history.append("Too low.\n");
            showMessage("Too low — try a higher number!", false);
            updateAttempts();
        } else {
            history.append("Too high.\n");
            showMessage("Too high — try a lower number!", false);
            updateAttempts();
        }
        score.setText("Wins: " + wins + "  |  Rounds: " + rounds);
        input.setText("");
        if (!finished) input.requestFocusInWindow();
    }

    private void showMessage(String text, boolean error) {
        message.setText(text);
        message.setForeground(error ? new Color(170, 55, 45) : new Color(37, 63, 122));
    }

    private void updateAttempts() {
        attempts.setText("Attempts left: " + (MAX_ATTEMPTS - used));
        if (used >= MAX_ATTEMPTS) {
            finished = true;
            showMessage("No attempts left! The number was " + secret + ".", true);
            finishRound();
        }
    }

    private void finishRound() {
        attempts.setText("Round complete — click New Game to try again.");
        input.setEnabled(false);
        guessButton.setEnabled(false);
        history.append("\nSecret number: " + secret + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new NumberGuessingGameGUI().setVisible(true);
        });
    }
}
