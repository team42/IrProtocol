package GUI;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import Model.TripLockedTime;

//* @author Nicolai
 
public class TaxiMenuCanvas extends JPanel {

    public TaxiMenu taxiMenu = new TaxiMenu();

    public TaxiMenuCanvas() {
        initComponents();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        taxiMenu.draw(g);
    }

    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
    }

    public void UP() {
        taxiMenu.Up();
        repaint();
    }

    public void DW() {
        taxiMenu.Down();
        repaint();
    }
    
    public void setTripList(ArrayList<TripLockedTime> tripList) {
    	taxiMenu.setTripList(tripList);
    	repaint();
    }
}
