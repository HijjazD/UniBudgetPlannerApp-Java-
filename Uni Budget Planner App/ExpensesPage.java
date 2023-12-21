import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ExpensesPage extends JPanel {
    private UniBudgetPlannerApp app;
    private JPanel expensesTypePanel;
    private JPanel plusButtonPanel;
    private JScrollPane scrollPane;
    private String currentFileName;

    public ExpensesPage(UniBudgetPlannerApp app) {
        this.app = app;
        expensesPage();
    }

    protected void expensesPage() {
        removeAll();
        setLayout(new BorderLayout());

        JPanel expensesPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Transport Button
        JButton transButton = app.createFunctionButton("Transport", Color.BLUE, "transport.png");
        transButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = "transport.txt";
                currentFileName = fileName;
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                } catch (IOException a) {
                    a.printStackTrace();
                }
                showExpensesTypePage(fileName);
            }
        });
        expensesPanel.add(transButton);

	//Health Care button
		JButton healthButton = app.createFunctionButton("Health Care", Color.BLUE, "health_care.png");
		healthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                		String fileName = "health.txt";
                		currentFileName = fileName;
                		try {
                    			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                		} catch (IOException a) {
                    			a.printStackTrace();
                		}
                			showExpensesTypePage(fileName);
			}
		});
		expensesPanel.add(healthButton);

	//Housing Button
	JButton housingButton = app.createFunctionButton("Housing", Color.BLUE, "housing.png");
		housingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                		String fileName = "housing.txt";
                		currentFileName = fileName;
                		try {
                    			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                		} catch (IOException a) {
                    			a.printStackTrace();
                		}
                			showExpensesTypePage(fileName);
			}
		});
		expensesPanel.add(housingButton);


	//food & drink button
		JButton foodButton = app.createFunctionButton("Food & Drink", Color.BLUE, "food&drink.png");
		foodButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                		String fileName = "food&drink.txt";
                		currentFileName = fileName;
                		try {
                    			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                		} catch (IOException a) {
                    			a.printStackTrace();
                		}
                			showExpensesTypePage(fileName);
			}
		});
		expensesPanel.add(foodButton);

	//Self Care button
		JButton selfButton = app.createFunctionButton("Self Care", Color.BLUE, "self_care.png");
		selfButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                		String fileName = "self_care.txt";
                		currentFileName = fileName;
                		try {
                    			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                		} catch (IOException a) {
                    			a.printStackTrace();
                		}
                			showExpensesTypePage(fileName);
			}
		});
		expensesPanel.add(selfButton);

	//Shopping button
		JButton shopButton = app.createFunctionButton("Shopping", Color.BLUE, "shopping.png");
		shopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                		String fileName = "shopping.txt";
                		currentFileName = fileName;
                		try {
                    			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                		} catch (IOException a) {
                    			a.printStackTrace();
                		}
                			showExpensesTypePage(fileName);
			}
		});
		expensesPanel.add(shopButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.showHomePage();
            }
        });

        add(backButton, BorderLayout.NORTH);
        add(expensesPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void showExpensesTypePage(String fileName) {
        removeAll();

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                expensesPage(); // Go back to the ExpensesPage
            }
        });

        if (scrollPane == null) {
            scrollPane = createExpensesTypePage();
            readExistingData(fileName);
        }

        // Create plus button panel
        plusButtonPanel = new JPanel();
        JButton plusButton = new JButton("+");
        plusButton.addActionListener(e -> addNewExpensesDetails());
        plusButtonPanel.add(plusButton);

        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.NORTH);
        add(plusButtonPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private JScrollPane createExpensesTypePage() {
        expensesTypePanel = new JPanel();
        expensesTypePanel.setLayout(new BoxLayout(expensesTypePanel, BoxLayout.Y_AXIS));

        // Create scroll pane and add expensesTypePanel to it
        scrollPane = new JScrollPane(expensesTypePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    private void addNewExpensesDetails() {
        // Create new instance of ExpensesDetails
        int index = expensesTypePanel.getComponentCount();
        ExpensesDetails expensesDetails = new ExpensesDetails(currentFileName, index);

        // Add ExpenseDetails to the main panel
        expensesTypePanel.add(expensesDetails.getMainPanel());

        // Refresh the scroll pane to update the scroll bar
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void readExistingData(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 4) {
                    int id = Integer.parseInt(data[0]);
                    String title = data[1];
                    String date = data[2];
                    String deposit = data[3];

                    displayData(id, title, date, deposit, fileName);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayData(int ID, String title, String date, String deposit, String fileName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 150));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create "Delete" button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteData(ID, title, date, deposit, fileName); // Call the method to delete the data
                expensesTypePanel.remove(panel); // Remove the panel from the main panel
                expensesTypePanel.remove(buttonPanel); // Remove the button panel from the main panel
                expensesTypePanel.revalidate(); // Revalidate the main panel
                expensesTypePanel.repaint(); // Repaint the main panel
            }
        });
        buttonPanel.add(deleteButton);

        // Create "Update" button
        JButton updateButton = new JButton("Update");
       	updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData(ID, title, date, deposit, fileName);
            }
        });
        buttonPanel.add(updateButton);

        // Create labels
        JLabel idLabel = new JLabel("ID: " + ID);
        JLabel titleLabel = new JLabel("Title: " + title);
        JLabel dateLabel = new JLabel("Date: " + date);
        JLabel amountLabel = new JLabel("Amount(RM): " + deposit);

        // Create sub-panel for labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(4, 1));
        labelPanel.add(idLabel);
        labelPanel.add(titleLabel);
        labelPanel.add(dateLabel);
        labelPanel.add(amountLabel);
        labelPanel.setBackground(Color.RED);

        // Add the label panel to the main panel
        panel.add(labelPanel, BorderLayout.CENTER);

        expensesTypePanel.add(panel);
        expensesTypePanel.add(buttonPanel, BorderLayout.CENTER);
    }

private void deleteData(int ID, String title, String date, String deposit, String fileName) {
    try {
        File inputFile = new File(fileName);
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean deleted = false; // Flag to track if a line was deleted

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(", ");
            if (data.length >= 4) {
                int existingID = Integer.parseInt(data[0]);
                String existingTitle = data[1];
                String existingDate = data[2];
                String existingDeposit = data[3];

                // Check if the line matches the data to be deleted
                if (existingID == ID && existingTitle.equals(title) && existingDate.equals(date) && existingDeposit.equals(deposit)) {
                    deleted = true; // Set the flag to indicate that a line was deleted
                    continue; // Skip writing this line to the temporary file
                }
            }
            writer.write(line); // Write the line to the temporary file
            writer.newLine();
        }

        writer.flush();
        writer.close();
        reader.close();

        if (deleted) {
            // Copy the contents of the temporary file back to the original file
            try (InputStream inputStream = new FileInputStream(tempFile);
                 OutputStream outputStream = new FileOutputStream(inputFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            if (tempFile.delete()) {
                System.out.println("Data deleted successfully from file: " + fileName);
            } else {
                System.out.println("Failed to delete the temporary file: " + tempFile.getAbsolutePath());
            }
        } else {
            System.out.println("No matching data found to delete in file: " + fileName);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void updateData(int ID, String title, String date, String deposit, String fileName) {
        try {
            File inputFile = new File(fileName);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 4) {
                    int existingID = Integer.parseInt(data[0]);
                    String existingTitle = data[1];
                    String existingDate = data[2];
                    String existingDeposit = data[3];

                    // Check if the line matches the data to be updated
                    if (existingID == ID && existingTitle.equals(title) && existingDate.equals(date) && existingDeposit.equals(deposit)) {
                        // Show a dialog to get the updated values from the user
                        String newTitle = JOptionPane.showInputDialog("Enter the new title:", title);
                        String newDate = JOptionPane.showInputDialog("Enter the new date:", date);
                        String newDeposit = JOptionPane.showInputDialog("Enter the new deposit amount:", deposit);

                        // Use the updated values or keep the existing values if the user didn't enter anything
                        String updatedLine = existingID + ", " + (newTitle != null ? newTitle : existingTitle) + ", " +
                                (newDate != null ? newDate : existingDate) + ", " +
                                (newDeposit != null ? newDeposit : existingDeposit);

                        writer.write(updatedLine);
                    } else {
                        writer.write(line); // Write the line to the temporary file
                    }
                }
                writer.newLine();
            }

            writer.flush();
            writer.close();
            reader.close();

            // Copy the contents of the temporary file back to the original file
            try (InputStream inputStream = new FileInputStream(tempFile);
                 OutputStream outputStream = new FileOutputStream(inputFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            if (tempFile.delete()) {
                System.out.println("Data updated successfully in file: " + fileName);
            } else {
                System.out.println("Failed to delete the temporary file: " + tempFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private double calculateTotal(String fileName) {
    double total = 0.0;

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(", ");
            if (data.length >= 4) {
                double deposit = Double.parseDouble(data[3]);
                total += deposit;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return total;
}

public double calculateTransportTotal() {
    String fileName = "transport.txt";
    return calculateTotal(fileName);
}

public double calculateHealthTotal() {
    String fileName = "health.txt";
    return calculateTotal(fileName);
}

public double calculateHousingTotal() {
    String fileName = "housing.txt";
    return calculateTotal(fileName);
}
public double calculateFoodDrinkTotal() {
    String fileName = "food&drink.txt";
    return calculateTotal(fileName);
}
public double calculateSelfCareTotal() {
    String fileName = "self_care.txt";
    return calculateTotal(fileName);
}
public double calculateShoppingTotal() {
    String fileName = "shopping.txt";
    return calculateTotal(fileName);
}
}
