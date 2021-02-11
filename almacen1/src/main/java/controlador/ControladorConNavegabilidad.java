/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.LayoutPane;

/**
 *
 * @author rafam
 */
public abstract class ControladorConNavegabilidad {
    
    protected LayoutPane layout;
    
    public void setLayout(LayoutPane layout) {
        this.layout = layout;
    }

}

