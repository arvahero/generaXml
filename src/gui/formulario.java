/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author rocke
 */
public class formulario extends javax.swing.JFrame {
    
    JFileChooser chooser;
    JButton botonRuta;
    
    public formulario() {
        initComponents();
    }
    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formulario().setVisible(true);
            }
        });
    }

    private void initComponents() {
        chooser = new JFileChooser();
        botonRuta = new JButton("Seleccionar");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void run() {
                new formulario().setVisible(true);
            }
    
}
