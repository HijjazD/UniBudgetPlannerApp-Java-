import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SummaryPage extends JPanel {
    private UniBudgetPlannerApp app;
    private JLabel totalExpensesLabel;
    private JLabel totalIncomeLabel;

    public SummaryPage(UniBudgetPlannerApp app) {
        this.app = app;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 1, 10, 10));
        setBackground(Color.LIGHT_GRAY);

        // Create and configure your summary page UI components here
        totalExpensesLabel = createLabel("Total Expenses: RM");
        add(totalExpensesLabel);

        ExpensesPage expensesPage = new ExpensesPage(app);

        double transportTotal = expensesPage.calculateTransportTotal();
        double healthTotal = expensesPage.calculateHealthTotal();
        double housingTotal = expensesPage.calculateHousingTotal();
        double foodDrinkTotal = expensesPage.calculateFoodDrinkTotal();
        double selfCareTotal = expensesPage.calculateSelfCareTotal();
        double shoppingTotal = expensesPage.calculateShoppingTotal();

        double totalExpenses = transportTotal + healthTotal + housingTotal + foodDrinkTotal + selfCareTotal + shoppingTotal;
        totalExpensesLabel.setText("Total Expenses: RM " + totalExpenses);

        // Individual expense labels
        String[] expenseCategories = {"Transport", "Health Care", "Housing", "Food & Drink", "Self Care", "Shopping"};
        double[] expenseValues = {transportTotal, healthTotal, housingTotal, foodDrinkTotal, selfCareTotal, shoppingTotal};

        for (int i = 0; i < expenseCategories.length; i++) {
            JLabel expenseLabel = createLabel(expenseCategories[i] + ": RM" + expenseValues[i]);
            add(expenseLabel);
        }

        // Create a gap between Shopping and Total Income
        add(Box.createVerticalStrut(10));

        totalIncomeLabel = createLabel("Total Income: RM");
        add(totalIncomeLabel);

        IncomePage incomePage = new IncomePage(app);  // Instantiate IncomePage here

        double scholarshipTotal = incomePage.calculateScholarshipTotal();
        double loanTotal = incomePage.calculateLoanTotal();
        double allowanceTotal = incomePage.calculateAllowanceTotal();

        double totalIncome = allowanceTotal + scholarshipTotal + loanTotal;
        totalIncomeLabel.setText("Total Income: RM " + totalIncome);

        String[] incomeSources = {"Allowance", "Scholarship", "Loan"};
        double[] incomeValues = {allowanceTotal, scholarshipTotal, loanTotal};

        for (int i = 0; i < incomeSources.length; i++) {
            JLabel incomeLabel = createLabel(incomeSources[i] + ": RM" + incomeValues[i]);
            add(incomeLabel);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.showHomePage();
            }
        });

        add(backButton);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    // Method to update the total expenses and total income labels
    public void updateSummary(double totalExpenses, double totalIncome) {
        totalExpensesLabel.setText("Total Expenses: RM " + totalExpenses);
        totalIncomeLabel.setText("Total Income: RM " + totalIncome);
    }
}
