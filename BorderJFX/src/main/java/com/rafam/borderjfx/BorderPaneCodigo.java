/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafam.borderjfx;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author rafam
 */
public class BorderPaneCodigo extends BorderPane{

    private StackPane contenedor;
    private Text texto;
    
    public BorderPaneCodigo() {
       cargarSeccionSuperior();
       cargarSeccionInferior();
       cargarLadoIzquierdo();
       cargarLadoDerecho();
       cargarSeccionCentral();
    }

    private void cargarSeccionSuperior() {
        this.texto = new Text("TOP");
        this.texto.setFont(Font.font("Cambria",FontWeight.BOLD,32));
        this.texto.setFill(Color.WHITE);
        this.contenedor = new StackPane(this.texto);
        this.contenedor.setStyle("-fx-background-color: blue;");
        this.contenedor.setPrefHeight(80);
        this.setTop(this.contenedor);
    }

    private void cargarSeccionInferior() {
        this.texto = new Text("BOTTOM");
        this.texto.setFont(Font.font("Cambria",FontWeight.BOLD,22));
        this.texto.setFill(Color.WHITE);
        this.contenedor = new StackPane(this.texto);
        this.contenedor.setStyle("-fx-background-color: blue;");
        this.contenedor.setPrefHeight(50);
        this.setBottom(this.contenedor);
    }

    private void cargarLadoIzquierdo() {
       this.texto = new Text("LEFT");
       this.texto.setFont(Font.font("Cambria",FontWeight.BOLD,22));
       this.texto.setFill(Color.WHITE);
       this.contenedor = new StackPane(this.texto);
       this.contenedor.setStyle("-fx-background-color: red;");
       this.contenedor.setMinSize(150, Region.USE_COMPUTED_SIZE);
       this.setLeft(this.contenedor);
    }

    private void cargarLadoDerecho() {
       this.texto = new Text("RIGHT");
       this.texto.setFont(Font.font("Cambria",FontWeight.BOLD,22));
       this.texto.setFill(Color.WHITE);
       this.contenedor = new StackPane(this.texto);
       this.contenedor.setStyle("-fx-background-color: red;");
       this.contenedor.setMinSize(150, Region.USE_COMPUTED_SIZE);
       this.setRight(this.contenedor);
    }

    private void cargarSeccionCentral() {
       this.contenedor = new StackPane();
       this.contenedor.setStyle("-fx-background-color: yellow;");
       this.setCenter(this.contenedor);
    }
    
}
