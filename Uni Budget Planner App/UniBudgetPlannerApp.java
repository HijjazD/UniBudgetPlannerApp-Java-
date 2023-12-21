import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UniBudgetPlannerApp extends JFrame{
    private static final int WIDTH = 365;
    private static final int HEIGHT = 650;

    public UniBudgetPlannerApp() {
        setTitle("Uni Budget Planner");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        showHomePage();
    }

    protected void showHomePage() {
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Income Button
        JButton incomeButton = createFunctionButton("Income", Color.GREEN, "income.png");
        incomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFunctionPage("Income");
            }
        });
        mainPanel.add(incomeButton);

        // Expenses Button
        JButton expensesButton = createFunctionButton("Expenses", Color.ORANGE, "expenses.png");
        expensesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFunctionPage("Expenses");
            }
        });
        mainPanel.add(expensesButton);

        // Summary Button
        JButton summaryButton = createFunctionButton("Summary", Color.BLUE, "summary.png");
        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFunctionPage("Summary");
            }
        });
        mainPanel.add(summaryButton);

        // Visualization Button
        JButton visualizationButton = createFunctionButton("Visualization", Color.RED, "visualization.png");
        visualizationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFunctionPage("Visualization");
            }
        });
        mainPanel.add(visualizationButton);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showFunctionPage(String functionName) {
        getContentPane().removeAll();

        if (functionName.equals("Income")) {
            IncomePage incomePage = new IncomePage(this);
            getContentPane().add(incomePage, BorderLayout.CENTER);
        } else if (functionName.equals("Expenses")) {
            ExpensesPage expensesPage = new ExpensesPage(this);
            getContentPane().add(expensesPage, BorderLayout.CENTER);
        } else if (functionName.equals("Summary")) {
            SummaryPage summaryPage = new SummaryPage(this);
            getContentPane().add(summaryPage, BorderLayout.CENTER);
        } else if (functionName.equals("Visualization")) {
            VisualizationPage visualizationPage = new VisualizationPage(this);
            getContentPane().add(visualizationPage, BorderLayout.CENTER);
        }

        revalidate();
        repaint();
    }

    protected JButton createFunctionButton(String label, Color color, String iconName) {
        JButton button = new JButton("<html><center>" + label + "</center></html>");
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        ImageIcon icon = new ImageIcon(iconName);
        Image scaledIcon = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledIcon));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UniBudgetPlannerApp().setVisible(true);
            }
        });
    }
}
