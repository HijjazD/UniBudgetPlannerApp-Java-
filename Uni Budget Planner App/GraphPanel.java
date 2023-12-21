import javax.swing.*;
import java.awt.*;

public class GraphPanel {
    private int[] dataset;
    private JPanel panel;

    public GraphPanel(int[] dataset) {
        this.dataset = dataset;
        this.panel = createGraphPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createGraphPanel() {
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = 340;
                int height = 500;

                int maxValue = getMaxValue(dataset);
                double yScaleFactor = (double) (height - 40) / maxValue;
                double xScaleFactor = (double) (width - 40) / dataset.length;

                g2d.setColor(Color.BLUE);

                // Draw x-axis
                g2d.drawLine(20, height - 20, width - 20, height - 20);

                // Draw y-axis
                g2d.drawLine(20, 20, 20, height - 20);

                g2d.setColor(Color.RED);

                // Draw bars representing the data points
                for (int i = 0; i < dataset.length; i++) {
                    int x = (int) (i * xScaleFactor) + 20;
                    int barWidth = (int) xScaleFactor - 4;
                    int barHeight = (int) (dataset[i] * yScaleFactor);

                    g2d.fillRect(x, height - 20 - barHeight, barWidth, barHeight);
                }

                g2d.setColor(Color.BLACK);

                // Draw labels on the y-axis
                for (int i = 0; i <= 10; i++) {
                    int y = height - 20 - (int) (i * (maxValue / 10.0) * yScaleFactor);
                    g2d.drawString(String.valueOf(i * (maxValue / 10)), 4, y);
                }

                // Draw labels on the x-axis
                for (int i = 0; i < dataset.length; i++) {
                    int x = (int) (i * xScaleFactor) + 20 + (int) (xScaleFactor / 2);
                    g2d.drawString(String.valueOf(i + 1), x, height - 4);
                }
            }
        };

        return graphPanel;
    }

    private int getMaxValue(int[] dataset) {
        int max = Integer.MIN_VALUE;
        for (int value : dataset) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
