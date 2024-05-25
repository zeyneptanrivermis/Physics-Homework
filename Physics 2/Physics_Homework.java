// @ZEYNEP TANRIVERMIS
// @20220808038
// @Computer Engineering (first year student)

// I used ChatGPT for this sections
// Plot 2 dimensional image of the matrix by appropriate graphing tools.
// Plot V for i=[1, 9] and j=0 x direction, V vs x
// Plot V for diagonal direction i;j =[1;1] to [9,9], V vs r (r is in diagonal direction)

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ElectricPotential {

    // Function to calculate electric potential at a point due to a point charge
    public static double calculatePotential(int charge, double distance) {
        double k = 8.99e9; // Coulomb's constant in N*m^2/C^2
        return k * charge / distance;
    }

    public static void main(String[] args) {
        int charge = Integer.parseInt(args[0].substring(args[0].length() - 2)); // Last two digits of the ID number in nC

        int rows = 10;
        int cols = 10;
        double[][] matrix = new double[rows][cols];

        // Calculate potential at each point in the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) {
                    continue; // Skip the point charge itself
                }
                double distance = Math.sqrt(Math.pow(i * 0.1, 2) + Math.pow(j * 0.1, 2)); // Distance from point charge
                matrix[i][j] = calculatePotential(charge, distance);
            }
        }

        // Plot 2D image of the matrix
        BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Scale the potential values to colors
        double maxPotential = matrix[0][0];
        double minPotential = matrix[0][0];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maxPotential = Math.max(maxPotential, matrix[i][j]);
                minPotential = Math.min(minPotential, matrix[i][j]);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float colorValue = (float) ((matrix[i][j] - minPotential) / (maxPotential - minPotential));
                Color color = Color.getHSBColor(colorValue, 1, 1);
                g.setColor(color);
                g.fillRect(j, i, 1, 1);
            }
        }

        // Save the image
        try {
            File output = new File("electric_potential.png");
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("*****************************************");

        // Second question (Calculating the electric field at a point P)
        // Constants
        double Q = 1e-6; // Total charge on the ring (Coulombs)
        double R = 0.1; // Radius of the ring (meters)

        // Array of distances (x) from the center of the ring along its axis
        double[] distances = {0.01, 0.05, 0.1, 0.2, 0.5, 1.0};

        // Calculate and print the electric field at each distance
        for (double x : distances) {
            double Ex = electricFieldOnAxis(k, Q, R, x);
            System.out.printf("Electric field at x = %.2f m: %.3e N/C%n", x, Ex);
        }
    }

    // Method to calculate the electric field on the axis of a ring of charge
    public static double electricFieldOnAxis(double k, double Q, double R, double x) {
        return (k * Q * x) / Math.pow(R * R + x * x, 1.5);
    }
}
