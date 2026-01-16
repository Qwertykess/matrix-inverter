package main;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class WindowInterface {
    private static JFrame windowFrame = new JFrame();

    // Constructor
    WindowInterface() {
        mainPanel();
    }

    // Method for panels
    public void mainPanel (){
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        
        // Size of matrix -- input size
        JPanel inputSizePanel = new JPanel();
        JTextField matrixSize = new JTextField(5);

        inputSizePanel.add(new JLabel("Size of Matrix: "));
        inputSizePanel.add(matrixSize);
        bodyPanel.add(inputSizePanel);
        
        

        // Matrix inputs -- input values
        JPanel matrixPanel = new JPanel();
        matrixPanel.setBorder(createSelectionBorder("Matrix: "));
        JPanel matrixInput = new JPanel(new GridLayout(8,8,10,10));

        for (int i = 0; i < 8*8; i++){
            matrixInput.add(new JTextField(Integer.toString(i), 5));
        }
        
        matrixPanel.add(matrixInput);
        bodyPanel.add(matrixPanel);

        // Using coofactors or RREF?
        JPanel methodPanel = new JPanel();
        String[] methodTypes = {"Adjoint Method", "Row-Reduction Method"};
        JComboBox<String> methodField = new JComboBox<>(methodTypes);

        methodPanel.add(new JLabel("Inversion Method: "));
        methodPanel.add(methodField);
        bodyPanel.add(methodPanel);

        // Calculate and clear buttons
        JPanel buttonPanel = new JPanel();

        JButton calcBtn = new JButton("CALCULATE");
        JButton clearBtn = new JButton("CLEAR");

        buttonPanel.add(calcBtn);
        buttonPanel.add(clearBtn);

        bodyPanel.add(buttonPanel);

        windowFrame.add(bodyPanel);
        
        windowFrame.setLocationRelativeTo(null);
        windowFrame.pack();
        windowFrame.setVisible(true);
    }

    private static Border createSelectionBorder(String borderTitle){
        return BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(borderTitle),
            BorderFactory.createEmptyBorder(5,5,5,5)
        );
    }
}
