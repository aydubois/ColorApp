package audrey.colorapp.controller;

import audrey.colorapp.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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

    private  TextField[]  textFields  ;
    private  Slider[]  sliders  ;
    private  String[] colors;
    private final Border borderRed = new Border(new BorderStroke(Paint.valueOf("#FF0000"),
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Creation de tableaux des différents éléments => simplification
        this.textFields = new TextField[]{textFieldRed, textFieldGreen, textFieldBlue};
        this.sliders = new Slider[]{sliderRed, sliderGreen, sliderBlue};
        this.colors = new String[]{"red", "green", "blue"};

        //Init couleur
        try{
            this.color = new Color(255,255,255);
        }catch(IllegalArgumentException e){
            System.err.println("Error ====> "+ e.getMessage());
        }

        // Init TextFields
        this.updateTextFieldFromColor();
        //Initialisation des sliders
        this.initSliders();
        //Mise à jour du panneau coloré
        this.updatePane();

        //Ajout des  != events TextFields + Sliders
        this.addEventsRGB();
        this.addEventInputHex();
        this.addEventSliders();

    }
    private void updateTextFieldFromColor(){
        this.textFieldRed.setText(String.valueOf(this.color.getRed()));
        this.textFieldGreen.setText(String.valueOf(this.color.getGreen()));
        this.textFieldBlue.setText(String.valueOf(this.color.getBlue()));
        this.textFieldHex.setText(this.color.getHexValue());
    }
    private void initSliders(){
        for (Slider slider : sliders) {
            slider.setMin(0);
            slider.setMax(255);
        }
        this.updateSliderFromColor();
    }

    private void updateSliderFromColor() {
        this.sliderRed.setValue(this.color.getRed());
        this.sliderGreen.setValue(this.color.getGreen());
        this.sliderBlue.setValue(this.color.getBlue());
    }


    private void addEventsRGB(){
        for (int i = 0; i < textFields.length; i++) {
            int finalI = i;
            textFields[i].setOnKeyPressed(keyEvent -> {

                // Suppr des caractères non voulus
                String charactersTextField = textFields[finalI].getText();
                if (!charactersTextField.matches("\\d*")) {
                    charactersTextField = charactersTextField.replaceAll("[^\\d]", "");
                    textFields[finalI].setText(charactersTextField);
                }

                this.updateColorFromTextFieldOrSlider(true);
            });
        }
    }

    private void updateColorFromTextFieldOrSlider(Boolean fromText){
        int value;
        for (int i = 0; i < textFields.length; i++) {
            //Get value
            try{
                value = fromText ? Integer.parseInt(textFields[i].getText()) : (int)Math.round(sliders[i].getValue());
            }catch (NumberFormatException e){
                value = 0;
            }

            //Set value
            try{
                switch (colors[i]){
                    case "red":
                        this.color.setRed(value);
                        if(!fromText)
                            this.textFieldRed.setText(String.valueOf(value));
                        break;
                    case "green":
                        this.color.setGreen(value);
                        if(!fromText)
                            this.textFieldGreen.setText(String.valueOf(value));
                        break;
                    case "blue":
                        this.color.setBlue(value);
                        if(!fromText)
                            this.textFieldBlue.setText(String.valueOf(value));
                        break;
                    default:
                        break;
                }

                // Update all other elements
                if(fromText){
                    textFields[i].setBorder(null); // suppression du border rouge
                    this.updateSliderFromColor();
                }
                this.textFieldHex.setText(this.color.getHexValue());
                this.updatePane();

            }catch(IllegalArgumentException e){
                if(!fromText){
                    this.textFields[i].setBorder(this.borderRed);
                }
            }
        }
    }
    private void addEventSliders(){
        for (int i = 0; i < sliders.length; i++) {
            int finalI = i;
            sliders[i].setOnMouseDragged(mouseEvent -> {
                int value = (int)Math.round(sliders[finalI].getValue());
                updateColorFromTextFieldOrSlider(false);
                //this.changeColorValue(value,null, colors[finalI]);
            });
            sliders[i].setOnMouseClicked(mouseEvent -> {
                int value = (int)Math.round(sliders[finalI].getValue());
                updateColorFromTextFieldOrSlider(false);
//                this.changeColorValue(value,null, colors[finalI]);
            });
        }
    }
   /* private void changeColorValue(int value,TextField textField, String color){
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

            this.updateSliderFromColor();
            if(textField != null){
                textField.setBorder(null);
            }
            this.textFieldHex.setText(this.color.getHexValue());
            this.updatePane();
        }catch(IllegalArgumentException e){
            if(textField != null){
                textField.setBorder(new Border(new BorderStroke(Paint.valueOf("#FF0000"), //RED
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }*/
    private void addEventInputHex(){
        this.textFieldHex.setOnKeyPressed(keyEvent -> {
            try{
                this.color.setHexValue(this.textFieldHex.getText().toUpperCase(Locale.ROOT));
                this.textFieldHex.setBorder(null);
                this.updateSliderFromColor();
                this.updateTextFieldFromColor();

                /*this.changeColorValue(this.color.getRed(), null, "red");
                this.changeColorValue(this.color.getGreen(), null, "green");
                this.changeColorValue(this.color.getBlue(), null, "blue");*/
            }catch(IllegalArgumentException e ){
                this.textFieldHex.setBorder(borderRed);
            }
        });
    }


    private void updatePane(){
        this.paneColor.setBackground(new Background(new BackgroundFill(Paint.valueOf(this.color.getHexValue()), null, null)));
    }
}
