/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameUI;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Ville
 */
public class DialogBox extends Pane {
    Label textArea;
    
    public DialogBox(String text) {
        this.textArea = new Label();
        textArea.setText(text);
        this.getStyleClass().add("pane");
        //textArea.getStyleClass().add("textarea");
        this.getChildren().add(textArea);
        this.setWidth(textArea.getWidth());
        textArea.setLayoutY(10);
        textArea.setLayoutX(10);
        textArea.setFont(Font.font ("Times New Roman", 16));
        textArea.setTextFill(Color.WHITE);
        
    }
    
    public void test() {
        System.out.println(this.getStyleClass().toString());
    }
     
}
