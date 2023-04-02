package sample;

import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

import javax.swing.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private ImageView image;

    @FXML
    private Text temp_astetta;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
        //lähetä pyyntö käyttäjän syöttämän kaupungin säästä
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                //pyynnön lähettämiseen käytetään yksilöllistä käyttäjäavainta, jonka saa rekisteröitymällä palveluun Openweathermap
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=8066c9a27709b44856c320fd3aa3cb4e&units=metric");
                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp_astetta.setText("astetta: " + obj.getJSONObject("main").getDouble("temp"));
                    temp_feels.setText("tuntuu kuin: " + obj.getJSONObject("main").getDouble("feels_like"));
                    temp_min.setText("alin: " + obj.getJSONObject("main").getDouble("temp_min"));
                    temp_max.setText("ylin: " + obj.getJSONObject("main").getDouble("temp_max"));
                }
            }
        });

    }

    private static String getUrlContent(String urlAdress) {
        //osoite URL-osoitteella
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                content.append(line + "\n");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Tätä kaupunkia ei löytynyt!");
            ex.printStackTrace();


        }
        return content.toString();
    }
}
