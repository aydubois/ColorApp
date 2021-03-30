package audrey.colorapp.controller;

import audrey.colorapp.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ColorController implements Initializable {

    private Color color;
    @FXML
    private Slider sliderRed;
    @FXML
    private Slider sliderGreen;
    @FXML
    private Slider sliderBlue;
    @FXML
    private TextField textFieldRed;
    @FXML
    private TextField textFieldGreen;
    @FXML
    private TextField textFieldBlue;
    @FXML
    private TextField textFieldHex;
    @FXML
    private Pane paneColor;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Instanciation de la couleur
        try{
            this.color = new Color(255,255,255);
        }catch(IllegalArgumentException e){
            System.out.println("Error ====> "+ e.getMessage());
        }
        // Initialisation de tous les input à 0
        this.textFieldRed.setText(String.valueOf(this.color.getRed()));
        this.textFieldGreen.setText(String.valueOf(this.color.getGreen()));
        this.textFieldBlue.setText(String.valueOf(this.color.getBlue()));
        this.textFieldHex.setText(this.color.getHexValue());
        //Mise à jour du panneau coloré
        this.paneColor.setBackground(new Background(new BackgroundFill(Paint.valueOf(this.color.getHexValue()), null, null)));
        //Initialisation des sliders
        this.initSliders(this.sliderRed);
        this.initSliders(this.sliderGreen);
        this.initSliders(this.sliderBlue);
        //Ajustement de la barre
        this.adjustValueSliders();

        //Ajout des events sur les inputs RGB
        this.addEventInputRGB(this.textFieldRed, "red");
        this.addEventInputRGB(this.textFieldGreen, "green");
        this.addEventInputRGB(this.textFieldBlue, "blue");

        //Ajout de l'event sur l'input Hex
        this.addEventInputHex();

        //Ajout de l'event sur les sliders
        this.addEventSliders(this.sliderRed, "red");
        this.addEventSliders(this.sliderGreen, "green");
        this.addEventSliders(this.sliderBlue, "blue");

    }
    private void initSliders(Slider slider){
        slider.setMin(0);
        slider.setMax(255);
    }

    private void adjustValueSliders() {
        this.sliderRed.setValue(this.color.getRed());
        this.sliderGreen.setValue(this.color.getGreen());
        this.sliderBlue.setValue(this.color.getBlue());
    }

    private void addEventInputRGB(TextField textField, String color){
        textField.setOnKeyPressed(keyEvent -> {
            // Suppression des caractères non voulus
            String charactersTextField = textField.getText();
            if (!charactersTextField.matches("\\d*")) {
                charactersTextField = charactersTextField.replaceAll("[^\\d]", "");
                textField.setText(charactersTextField);
            }
            int value = 0;
            try {
                value = Integer.parseInt(charactersTextField);
            }catch (NumberFormatException e){
                value = 0;
            }
            this.changeColorValue(value,textField, color);
        });
    }

    private void addEventSliders(Slider slider, String color){
        slider.setOnMouseDragged(mouseEvent -> {
            int value = (int)Math.round(slider.getValue());
            System.out.println("VALUE SLIDER ===>"+slider.getValue());
            this.changeColorValue(value,null, color);
        });
        slider.setOnMouseClicked(mouseEvent -> {
            int value = (int)Math.round(slider.getValue());
            System.out.println("VALUE SLIDER ===>"+slider.getValue());
            this.changeColorValue(value,null, color);
        });
    }
    private void changeColorValue(int value,TextField textField, String color){
        try{

            switch (color){
                case "red":
                    this.color.setRed(value);
                    if(textField == null)
                        this.textFieldRed.setText(String.valueOf(value));
                    break;
                case "green":
                    this.color.setGreen(value);
                    if(textField == null)
                        this.textFieldGreen.setText(String.valueOf(value));
                    break;
                case "blue":
                    this.color.setBlue(value);
                    if(textField == null)
                        this.textFieldBlue.setText(String.valueOf(value));
                    break;
                default:
                    break;
            }

            this.adjustValueSliders();
            if(textField != null){
                textField.setBorder(null);
            }
            this.textFieldHex.setText(this.color.getHexValue());
            this.paneColor.setBackground(new Background(new BackgroundFill(Paint.valueOf(this.color.getHexValue()), null, null)));
        }catch(IllegalArgumentException e){
            if(textField != null){
                textField.setBorder(new Border(new BorderStroke(Paint.valueOf("#FF0000"), //RED
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }
    private void addEventInputHex(){
        this.textFieldHex.setOnKeyPressed(keyEvent -> {
            try{
                this.color.setHexValue(this.textFieldHex.getText().toUpperCase(Locale.ROOT));
                this.textFieldHex.setBorder(null);
                this.adjustValueSliders();
                this.changeColorValue(this.color.getRed(), null, "red");
                this.changeColorValue(this.color.getGreen(), null, "green");
                this.changeColorValue(this.color.getBlue(), null, "blue");
            }catch(IllegalArgumentException e ){
                this.textFieldHex.setBorder(new Border(new BorderStroke(Paint.valueOf("#FF0000"), //RED
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        });
    }
}
