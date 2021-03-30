package audrey.colorapp.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {
    // A Mettre dans une library externe.
    private int red;
    private int green;
    private int blue;
    private String hexValue;
    private static  final String INTERVAL_NB_ERROR = "Les paramètres doivent être compris entre 0 et 255.";
    private static final String HEX_ERROR = "Le paramètre donné n'est pas un code héxadécimal.";
    private static final String NULL_ERROR  = "Le paramètre attendu ne peut pas être null";

    /**
     *
     * @param red - number between 0 and 255, representing the colour red
     * @param green - number between 0 and 255, representing the colour green
     * @param blue - number between 0 and 255, representing the colour blue
     * @throws IllegalArgumentException when numbers < 0 or numbers > 255
     */
    public Color(int red, int green, int blue) throws IllegalArgumentException {
        if(!checkNbInterval(red) ||!checkNbInterval(green) || !checkNbInterval(blue)){
            throw new IllegalArgumentException(INTERVAL_NB_ERROR);
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.changeHexValue();
    }

    /**
     * @param hexValue - String representing a hexadecimal code - example : "#5496C8"
     * @throws IllegalArgumentException when hexValue doesn't match with "^#([A-F0-9]{6})$"
     */
    public Color(String hexValue) throws  IllegalArgumentException, NullPointerException {
        if("".equals(hexValue) || hexValue == null || !checkHexValue(hexValue)){
            throw new IllegalArgumentException(HEX_ERROR);
        }
        this.hexValue = hexValue;
        this.changeRGB();
    }

    public int getRed() {
        return red;
    }

    /**
     * @param red - number between 0 and 255, representing the colour red
     * @throws IllegalArgumentException when red < 0 or numbers > 255
     */
    public void setRed(int red)throws IllegalArgumentException {
        if(!checkNbInterval(red) ){
            throw new IllegalArgumentException(INTERVAL_NB_ERROR);
        }
        this.red = red;
        this.changeHexValue();
    }

    public int getGreen() {
        return green;
    }

    /**
     * @param green - number between 0 and 255, representing the colour green
     * @throws IllegalArgumentException when green < 0 or numbers > 255
     */
    public void setGreen(int green)throws IllegalArgumentException {
        if(!checkNbInterval(green)){
            throw new IllegalArgumentException(INTERVAL_NB_ERROR);
        }
        this.green = green;
        this.changeHexValue();

    }

    public int getBlue() {
        return blue;
    }


    /**
     * @param blue - number between 0 and 255, representing the colour blue
     * @throws IllegalArgumentException when blue < 0 or numbers > 255
     */
    public void setBlue(int blue)throws IllegalArgumentException {
        if(!checkNbInterval(blue)){
            throw new IllegalArgumentException(INTERVAL_NB_ERROR);
        }
        this.blue = blue;
        this.changeHexValue();

    }

    public String getHexValue() {
        return hexValue;
    }


    /**
     * @param hexValue - String representing a hexadecimal code - example : "#5496C8"
     * @throws IllegalArgumentException when hexValue doesn't match with "^#([A-F0-9]{6})$"
     */
    public void setHexValue(String hexValue) throws IllegalArgumentException{
        if("".equals(hexValue) || hexValue == null || !checkHexValue(hexValue)){
            throw new IllegalArgumentException(HEX_ERROR);
        }
        this.hexValue = hexValue;
        this.changeRGB();

    }

    public String toString(){
        return "[value="+this.hexValue+", r="+this.red+", g="+this.green+", b="+this.blue+"]";
    }


    public boolean checkHexValue(String hexValue){
        Pattern pattern = Pattern.compile("^#([A-F0-9]{6})$");
        Matcher matcher = pattern.matcher(hexValue);
        return matcher.matches();
    }

    public boolean checkNbInterval(int numberColor){
        return numberColor >= 0 && numberColor <= 255;
    }

    private void changeHexValue(){
        this.hexValue = String.format("#%02X%02X%02X", this.red, this.green, this.blue);
    }

    private void changeRGB(){
        this.red = Integer.valueOf( this.hexValue.substring( 1, 3 ), 16 );
        this.green = Integer.valueOf( this.hexValue.substring( 3, 5 ), 16 );
        this.blue = Integer.valueOf( this.hexValue.substring( 5, 7 ), 16 );
    }
}
