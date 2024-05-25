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
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Physics_Homework {
    public static void main(String[] args) {

        // First question
        // Constants
        double k = 8.9875517923e9; // Coulomb's constant
        int q = 1; // Charge at the point

        // Creating a matrix 10x10
        double[][] potentials = new double[10][10];

        // Calculating the potential at each point in the matrix
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!(i == 0 && j == 0)) { // Skip the point charge itself
                    double distance = Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
                    double potential = k * q / distance;
                    potentials[i][j] = potential;
                } else {
                    potentials[i][j] = 0; // Assign a potential of zero at the location of the point charge
                }
            }
        }

        // Save data to a file
        try {
            FileWriter writer = new FileWriter("potentials.txt");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    writer.write("Potential at point [" + i + ", " + j + "]: " + potentials[i][j] + "\n");
                }
            }
            writer.close();
            System.out.println("Potentials saved to potentials.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create an image of the potential matrix
        int width = 500; // Width of the image
        int height = 500; // Height of the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Draw the potentials as a heatmap
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int colorValue = (int) (255 * potentials[i][j] / (k * q / Math.sqrt(2))); // Normalize color value
                colorValue = Math.min(255, colorValue); // Clamp to 255
                g2d.setColor(new Color(colorValue, 0, 0)); // Red color gradient
                g2d.fillRect(i * (width / 10), j * (height / 10), width / 10, height / 10);
            }
        }
        g2d.dispose();

        // Save the image to a file
        try {
            ImageIO.write(image, "png", new File("potentials.png"));
            System.out.println("Image saved to potentials.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Plot V for i = [1, 9] and j = 0 (x direction, V vs x)
        try {
            FileWriter writer = new FileWriter("V_vs_x.txt");
            for (int i = 1; i < 10; i++) {
                writer.write("V[" + i + ",0]: " + potentials[i][0] + "\n");
            }
            writer.close();
            System.out.println("V vs x data saved to V_vs_x.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Plot V for diagonal direction i = [1, 1] to [9, 9], V vs r
        try {
            FileWriter writer = new FileWriter("V_vs_r.txt");
            for (int i = 1; i < 10; i++) {
                writer.write("V[" + i + "," + i + "]: " + potentials[i][i] + "\n");
            }
            writer.close();
            System.out.println("V vs r data saved to V_vs_r.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Identifying equipotential points
        try {
            FileWriter writer = new FileWriter("equipotential_points.txt");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int m = 0; m < 10; m++) {
                        for (int n = 0; n < 10; n++) {
                            if (i != m || j != n) {
                                if (Math.abs(potentials[i][j] - potentials[m][n]) < 1e-6) {
                                    writer.write("Equipotential points: [" + i + "," + j + "] and [" + m + "," + n + "] with potential " + potentials[i][j] + "\n");
                                }
                            }
                        }
                    }
                }
            }
            writer.close();
            System.out.println("Equipotential points saved to equipotential_points.txt");
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
