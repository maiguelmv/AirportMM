/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import view.MainWindow;

 *
 * @author CLINICA DE DEUDAS
 */
import view.MainWindow;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        System.setProperty("flatlaf.useNativeLibrary", "false");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        java.awt.EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }
}


