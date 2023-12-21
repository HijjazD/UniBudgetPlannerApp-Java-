import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class IncomeDetails {
    private String title;
    private String date;
    private String deposit;
    protected String incomeFileName;
    private int ID;

    private JPanel incomeDetailsPanel;

    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel amountLabel;

    private BufferedWriter incomeWriter;
    private BufferedReader reader;

    public IncomeDetails(String incomeFileName, int ID) {
        this.ID = ID;
        this.incomeFileName = incomeFileName;
        incomeDetailsPanel = new JPanel();
        incomeDetailsPanel.setLayout(new BoxLayout(incomeDetailsPanel, BoxLayout.Y_AXIS));

        JPanel panel1 = createActivityPanel();
        JPanel panel3 = createButtonPanel();

        // Set size of each panel
        panel1.setPreferredSize(new Dimension(350, 150));
        panel3.setPreferredSize(new Dimension(350, 100));

        // Set panel colors
        panel1.setBackground(Color.RED);
        panel3.setBackground(Color.GREEN);

        // Add all panels to the main panel
        incomeDetailsPanel.add(panel1);
        incomeDetailsPanel.add(panel3);

        // Add margin between sets of panels
        incomeDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private JPanel createActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 150));

        // Create labels
        titleLabel = new JLabel("Set Income Title:");
        dateLabel = new JLabel("Set Income Date:");
        amountLabel = new JLabel("Set Income Amount(RM):");

        // Create text fields
        JTextField titleTextField = new JTextField();
        JTextField dateTextField = new JTextField();
        JTextField amountTextField = new JTextField();

        // Create button
        JButton submitButton = new JButton("Submit");

        // Create sub-panel for labels and text fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.add(titleLabel);
        inputPanel.add(titleTextField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateTextField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountTextField);
        inputPanel.setBackground(Color.RED);
        inputPanel.setPreferredSize(new Dimension(330, 120));

        // Create sub-panel for the submit button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.setBackground(Color.RED);
        buttonPanel.setPreferredSize(new Dimension(330, 30));

        // Create sub-panel to hold both sub-panels
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.add(inputPanel);
        subPanel.add(buttonPanel);

        // Add the sub-panel to the main panel
        panel.add(subPanel, BorderLayout.CENTER);

        // Add ActionListener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = titleTextField.getText();
                date = dateTextField.getText();
                deposit = amountTextField.getText();
                saveIncomeToTextFile();

                // Remove components from panel1
                inputPanel.removeAll();
                buttonPanel.removeAll();

                // Create labels for displaying the data
                JLabel titleLabel = new JLabel("Title: " + title);
                JLabel dateLabel = new JLabel("Date: " + date);
                JLabel amountLabel = new JLabel("Amount(RM): " + deposit);

                // Add the labels to the inputPanel
                inputPanel.add(titleLabel);
                inputPanel.add(dateLabel);
                inputPanel.add(amountLabel);

                // Refresh the panel
                panel.revalidate();
                panel.repaint();
            }
        });

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create "Delete" button
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);

        // Create "Update" button
        JButton updateButton = new JButton("Update");

        buttonPanel.add(updateButton);

        return buttonPanel;
    }

    private JPanel activityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 150));

        // Create labels
        titleLabel = new JLabel("Title: " + title);
        dateLabel = new JLabel("Date: " + date);
        amountLabel = new JLabel("Amount(RM): " + deposit);

        // Create sub-panel for labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(3, 1));
        labelPanel.add(titleLabel);
        labelPanel.add(dateLabel);
        labelPanel.add(amountLabel);
        labelPanel.setBackground(Color.RED);

        // Add the label panel to the main panel
        panel.add(labelPanel, BorderLayout.CENTER);

        return panel;
    }

    private void saveIncomeToTextFile() {
        String data = ID + ", " + title + ", " + date + ", " + deposit;
        saveIncomeDataToFile(data);
    }

    protected void saveIncomeDataToFile(String data) {
        openIncomeFile(incomeFileName);
        try {
            incomeWriter.write(data);
            incomeWriter.newLine();
            incomeWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openIncomeFile(String fileName) {
        // Open the writer in append mode
        try {
            incomeWriter = new BufferedWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JPanel getMainPanel() {
        return incomeDetailsPanel;
    }
}
