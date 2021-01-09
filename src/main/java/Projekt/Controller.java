package Projekt; /*************************************************
 * Random Art Generator
 *
 * Autorid: Elina Meier, Kristjan Pühvel
 *
 * Tartu Ülikool
 * kevadsemester 2020
 ************************************************/

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Controller {
    @FXML
    private TextField input;
    public long loodudSeed = 0;

    /**
     * Kõik mis toimub peale avalehel enteri vajatamist
     * @throws TühiSisendErind
     * @throws IOException
     */
    public void onEnter() throws TühiSisendErind, IOException {
        String sisend = input.getText();
        if (!sisend.isEmpty()) {
            this.loodudSeed = FxProjekt.pseudorandom(sisend);
            input.clear();

            //Sisend kirjutatakse faili
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("Logifail.txt", true), StandardCharsets.UTF_8))) {
                bw.write(sisend+"\n");
            }

            //Enteri vajutamise peale avatakse uus aken
            Pane peaPane = new Pane();
            Scene stseen = new Scene(peaPane, 1080, 720);
            Stage peaLava = new Stage();

            String seedstring = Long.toString(loodudSeed);
            System.out.println(seedstring);
            int[] seedarray = new int[seedstring.length()];
            for (int i = 0; i < seedstring.length(); i++) {
                seedarray[i] = Integer.parseInt(String.valueOf(seedstring.charAt(i)));
            }

            //Taustavärvid
            float r = Float.parseFloat(seedstring.substring(0, 3)) / 1000;
            float e = Float.parseFloat(seedstring.substring(4, 7)) / 1000;
            float g = Float.parseFloat(seedstring.substring(7, 10)) / 1000;
            float b = Float.parseFloat(seedstring.substring(10, 13)) / 1000;
            float c = Float.parseFloat(seedstring.substring(13, 16)) / 1000;

            Stop[] stops = new Stop[]{new Stop(0, Color.color(r, g, b)),
                    new Stop(1, Color.color(r, e, c))};
            Stop[] stops2 = new Stop[]{new Stop(0, Color.color(b, r, c)),
                    new Stop(1, Color.color(c, r, b))};

            LinearGradient LG = new LinearGradient(0, 0, 0, 0, true, CycleMethod.NO_CYCLE, stops);

            float startX = Float.parseFloat(seedstring.substring(11, 13)) / 100;
            float startY = Float.parseFloat(seedstring.substring(1, 3)) / 100;
            float endX = Float.parseFloat(seedstring.substring(2, 4)) / 100;
            float endY = Float.parseFloat(seedstring.substring(3, 5)) / 100;

            if (seedarray[0] % 3 == 1) LG = new LinearGradient(startX, startY, endX, endY, true, CycleMethod.REPEAT, stops2);
            if (seedarray[1] % 3 == 2) LG = new LinearGradient(startX, startY, endX, endY, true, CycleMethod.NO_CYCLE, stops2);
            if (seedarray[2] % 3 == 0) LG = new LinearGradient(startX, startY, endX, endY, true, CycleMethod.REFLECT, stops2);

            Rectangle ruut = new Rectangle(1080,720, LG);
            peaPane.getChildren().add(ruut);

            double laius = peaPane.getWidth();
            double korgus = peaPane.getHeight();

            //Rekursiivne puu
            Punkt algusPunkt = new Punkt(Float.parseFloat(seedstring.substring(13, 16)) / 2, Float.parseFloat(seedstring.substring(16, 19)) % korgus);
            FxProjekt.joonistaPuu(peaPane, algusPunkt, 90, Integer.parseInt(seedstring.substring(10, 11)));
            //System.out.println(peaPane.getChildren().size());

            //Jooned
            for (int i = 0; i < 255; i++) {
                r = Float.parseFloat(seedstring.substring(0, 3)) / 1000;
                b = Float.parseFloat(seedstring.substring(10, 13)) / 1000;
                c = Float.parseFloat(seedstring.substring(13, 16)) / 1000;
                stops2 = new Stop[]{new Stop(0, Color.color(b, r, c)), new Stop(1, Color.color(c, r, b))};
                LG = new LinearGradient(Float.parseFloat(seedstring.substring(11, 13)) / 100,
                        Float.parseFloat(seedstring.substring(1, 3)) / 100,
                        Float.parseFloat(seedstring.substring(2, 4)) / 100,
                        Float.parseFloat(seedstring.substring(3, 5)) / 100, true, CycleMethod.NO_CYCLE, stops2);

                CubicCurve cubic = new CubicCurve(Double.parseDouble(seedstring.substring(11, 16)) % laius,
                        Double.parseDouble(seedstring.substring(12, 18)) % korgus,
                        Float.parseFloat(seedstring.substring(13, 14)) % korgus,
                        Float.parseFloat(seedstring.substring(4, 6)) / korgus,
                        Float.parseFloat(seedstring.substring(11, 14)),
                        Float.parseFloat(seedstring.substring(15, 18)),
                        Float.parseFloat(seedstring.substring(10, 13)) % korgus,
                        Float.parseFloat(seedstring.substring(13, 16)) % laius);
                cubic.setStroke(LG);
                cubic.setFill(null);
                peaPane.getChildren().add(cubic);
                seedstring = FxProjekt.pseudorandom(seedstring) + String.valueOf(1);
            }
            peaLava.setTitle("My Random Artwork");
            peaLava.setScene(stseen);
            peaLava.show();
        } else {
            throw new TühiSisendErind("Palun sisestage midagi!");
        }
    }

    /**
     * Vajutades 'BACK' nuppu loeb logifailist viimase sisendi ja kuvab selle uuesti tekstiväljale
     * @throws IOException
     */
    public void tagasi() throws IOException {
        String viimaneSisend = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("Logifail.txt"), StandardCharsets.UTF_8))){
            String rida = br.readLine();
            while (rida!=null) {
                viimaneSisend = rida;
                rida = br.readLine();
            }
        }
        input.setText(viimaneSisend);
    }
}
