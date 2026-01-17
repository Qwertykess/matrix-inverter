import javax.swing.*;
import java.awt.*;

public class MatrixGUI extends JFrame {

    private JTextField[][] fields;
    private JTextArea outputArea;
    private int size;

    private JLabel detLabel;
    private JLabel singuLabel;

    public MatrixGUI() {
        String input = JOptionPane.showInputDialog("Enter n-by-n matrix size (max 10):");
        if (input == null) System.exit(0);

        try {
            size = Integer.parseInt(input);
            if (size <= 0 || size > 10) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid size!");
            System.exit(0);
        }

        this.setTitle("Matrix Inverter");
        this.setMinimumSize(new Dimension(600, 400));
        this.setLayout(new BorderLayout(5, 5));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel matrixPanel = new JPanel(new GridLayout(size, size, 2, 2));
        fields = new JTextField[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                fields[i][j] = new JTextField(4);
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                fields[i][j].setFont(new Font("Arial", Font.PLAIN, 16));
                matrixPanel.add(fields[i][j]);
            }

        outputArea = new JTextArea(8, 15);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        detLabel = new JLabel("Determinant: ");
        detLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

        singuLabel = new JLabel("Status: ");
        singuLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton invertButton = new JButton("Invert Matrix");
        invertButton.addActionListener(e -> invertMatrix());

        this.add(matrixPanel, BorderLayout.CENTER);
        this.add(invertButton, BorderLayout.SOUTH);
        JPanel rightPanel = new JPanel();

        detLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        singuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(detLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(singuLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(scrollPane);

        this.add(rightPanel, BorderLayout.EAST);


        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }


    
    private void invertMatrix(){
        double[][] matrix = new double[size][size];
        try {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    matrix[i][j] = Double.parseDouble(fields[i][j].getText());

            double det = MatrixOperations.determinant(matrix, size);
            detLabel.setText(
                    String.format("Determinant: %.0f", det)
            );

            if (Math.abs(det) < 1e-9) {
                singuLabel.setText("Status: Singular matrix");
                singuLabel.setForeground(Color.RED);
                outputArea.setText("");
                return;
            } else {
                singuLabel.setText("Status: Invertible");
                singuLabel.setForeground(new Color(0, 128, 0));
            }

            double[][] augmented = MatrixOperations.augmenting(matrix, size);
            MatrixOperations.RREFmatrix(augmented, size);
            double[][] inverse = MatrixOperations.extractInverse(augmented);


            outputArea.setText("");
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++)
                    outputArea.append(String.format("%8.2f", inverse[i][j]));
                outputArea.append("\n");
            }


        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

}
