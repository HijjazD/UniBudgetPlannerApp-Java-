import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VisualizationPage extends JPanel {
    private UniBudgetPlannerApp app;
    private JComboBox<String> monthDropdown;
    private JButton generateGraphButton;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    public VisualizationPage(UniBudgetPlannerApp app) {
        this.app = app;
        visualizationPage();
    }

    protected void visualizationPage() {
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel visualizationPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        visualizationPanel.setBackground(Color.BLACK);

        // Daily expenses button
        JButton dailyExpensesButton = app.createFunctionButton("Daily Expenses", new Color(75, 83, 32), "daily.png");
        dailyExpensesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDailyExpensesPage();
            }
        });
        visualizationPanel.add(dailyExpensesButton);

        // Monthly expenses button
        JButton monthlyExpensesButton = app.createFunctionButton("Monthly Expenses", new Color(75, 83, 32), "monthly.png");
        monthlyExpensesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMonthlyExpensesPage();
            }
        });
        visualizationPanel.add(monthlyExpensesButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.showHomePage();
            }
        });

        add(backButton, BorderLayout.NORTH);
        add(visualizationPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void showDailyExpensesPage() {
        removeAll();
        setLayout(new BorderLayout());

        JPanel dailyExpensesPanel = new JPanel();

        // Create month dropdown
        monthDropdown = new JComboBox<>(months);

        // Create the generate graph button
        generateGraphButton = new JButton("Generate Graph");
        dailyExpensesPanel.add(generateGraphButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizationPage(); // Go back to the ExpensesPage
            }
        });
        dailyExpensesPanel.add(backButton);

        add(monthDropdown, BorderLayout.NORTH);
        add(dailyExpensesPanel, BorderLayout.CENTER);

        setupListeners();

        revalidate();
        repaint();
    }

private void showMonthlyExpensesPage() {
    removeAll();
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    JPanel monthlyExpensesPanel = new JPanel();

    // Create month dropdown
    //monthDropdown = new JComboBox<>(months);

    // Create the generate graph button
    JButton generateMonthlyGraphButton = new JButton("Generate Graph");
    monthlyExpensesPanel.add(generateMonthlyGraphButton);

    // Back Button
    JButton backButton = new JButton("Back");
    backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            visualizationPage(); // Go back to the ExpensesPage
        }
    });
    //monthlyExpensesPanel.add(backButton);

    add(backButton, BorderLayout.NORTH);
    add(monthlyExpensesPanel, BorderLayout.CENTER);

    generateMonthlyGraphButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //String selectedMonth = (String) monthDropdown.getSelectedItem();
            int[] dataset = getDataSetForMonthlyGraph();
            GraphPanel graphPanel = new GraphPanel(dataset);
            showMonthlyGraph(graphPanel.getPanel()); // Get the JPanel from GraphPanel
        }
    });

    revalidate();
    repaint();
}


    private void setupListeners() {
        generateGraphButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMonth = (String) monthDropdown.getSelectedItem();
                int[] dataset = getDatasetForDailyGraph(selectedMonth);
                GraphPanel graphPanel = new GraphPanel(dataset);
                showDailyGraph(graphPanel.getPanel()); // Get the JPanel from GraphPanel
            }
        });
    }

private int[] getDatasetForDailyGraph(String month) {
    java.util.List<Integer> datasetList = new java.util.ArrayList<>();
    java.util.Map<Integer, Integer> dataMap = new java.util.TreeMap<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    try {
        java.util.List<String> lines = new java.util.ArrayList<>();

        // Read and combine data from all six files
        for (String filename : Arrays.asList("transport.txt", "health.txt", "housing.txt", "food&drink.txt", "self_care.txt", "shopping.txt")) {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        }

        // Sort the lines based on the month in ascending order
        Collections.sort(lines, new Comparator<String>() {
            @Override
            public int compare(String line1, String line2) {
                String[] parts1 = line1.split(",");
                String[] parts2 = line2.split(",");

                String dateString1 = parts1[2].trim();
                String dateString2 = parts2[2].trim();

                try {
                    Date date1 = dateFormat.parse(dateString1);
                    Date date2 = dateFormat.parse(dateString2);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(date2);
                    int calMonth1 = cal1.get(Calendar.MONTH);
                    int calMonth2 = cal2.get(Calendar.MONTH);

                    return Integer.compare(calMonth1, calMonth2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return 0;
            }
        });

        // Clear the existing content of GraphDataSet.txt
        java.io.FileWriter fw = new java.io.FileWriter("GraphDataSet.txt");
        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);

        for (String sortedLine : lines) {
            bw.write(sortedLine);
            bw.newLine();

            String[] parts = sortedLine.split(",");
            if (parts.length >= 4) {
                String dateString = parts[2].trim();
                int amount = Integer.parseInt(parts[3].trim());
                try {
                    Date date = dateFormat.parse(dateString);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int calMonth = cal.get(Calendar.MONTH);
                    if (calMonth == getMonthIndex(month)) {
                        datasetList.add(amount);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        bw.close();
        fw.close();

        BufferedReader reader = new BufferedReader(new FileReader("GraphDataSet.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[3].trim());
                try {
                    Date date = dateFormat.parse(parts[2].trim());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int days = calendar.get(Calendar.DAY_OF_YEAR);
                    if (x >= 0 && y >= 0) {
                        dataMap.put(days, y);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    int[] dataset = new int[datasetList.size()];
    for (int i = 0; i < datasetList.size(); i++) {
        dataset[i] = datasetList.get(i);
    }

    return dataset;
}


private int[] getDataSetForMonthlyGraph() {
    Map<String, Integer> monthlyTotals = new HashMap<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

    try (BufferedReader reader = new BufferedReader(new FileReader("GraphDataSet.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            if (parts.length == 4) {
                int id = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                String dateString = parts[2].trim();
                int deposit = Integer.parseInt(parts[3].trim());

                Date date = dateFormat.parse(dateString);
                String dataMonth = new SimpleDateFormat("MMMM").format(date);

                if (!monthlyTotals.containsKey(dataMonth)) {
                    monthlyTotals.put(dataMonth, 0);
                }

                monthlyTotals.put(dataMonth, monthlyTotals.get(dataMonth) + deposit);
            }
        }
    } catch (IOException | ParseException e) {
        e.printStackTrace();
    }

    int[] dataset = new int[12];
    for (int i = 0; i < 12; i++) {
        String monthName = months[i];
        int total = monthlyTotals.getOrDefault(monthName, 0);
        dataset[i] = total;
    }

    return dataset;
}




    private void showDailyGraph(JPanel graphPanel) {
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDailyExpensesPage();
            }
        });

        add(backButton, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void showMonthlyGraph(JPanel graphPanel) {
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMonthlyExpensesPage();
            }
        });

        add(backButton, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private int getMonthIndex(String month) {
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(month)) {
                return i;
            }
        }
        return -1; // Month not found
    }
}
