package Projekt; /*************************************************
 * Random Art Generator
 *
 * Autorid: Elina Meier, Kristjan Pühvel
 *
 * Tartu Ülikool
 * kevadsemester 2020
 ************************************************/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.abs;

public class FxProjekt extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sd.fxml"));
        stage.setTitle("Random Art Generator");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    }

    /**
     * meetod leiab antud sisendist numbritest koosneva seedi
     * @param seed
     * @return tagastab 20-kohalise numbrikombinatsiooni
     */
    public static long pseudorandom(String seed) {
        Random random = new Random();
        long numericseed = 0;
        char[] seedarray = seed.toCharArray();
        for (char c : seedarray) {
            numericseed += Character.getNumericValue(c);
        }
        random.setSeed(numericseed);
        long generated = abs(random.nextLong());
        char[] array = String.valueOf(generated).toCharArray();
        for (char c : array) {
            if (c != '0' & c != '1') {
                generated = generated * (Long.parseLong(String.valueOf(c)));
                break;
            }
        }
        array = String.valueOf(generated).toCharArray();
        if (array.length > 20) array = Arrays.copyOf(array, 20);
        generated = Long.parseLong(String.valueOf(array));
        return abs(generated);
    }

    /**
     * meetod joonsitab rekursiivselt puu
     * @param peaPane
     * @param algusPunkt
     * @param nurk
     * @param tase
     */
    public static void joonistaPuu(Pane peaPane, Punkt algusPunkt, double nurk, int tase) {
        if (tase <= 0) {
            return;
        }
        Punkt loppPunkt = algusPunkt.leiaUusLopp(10, nurk, tase);
        Line joon = new Line(algusPunkt.getX(), algusPunkt.getY(), loppPunkt.getX(), loppPunkt.getY());
        joon.setStrokeWidth(tase);

        Random rand = new Random();
        double r = rand.nextDouble();
        double g = rand.nextDouble();
        double b = rand.nextDouble();
        double o = rand.nextDouble();
        joon.setStroke(Color.color(r, g, b, o));

        peaPane.getChildren().add(joon);
        joonistaPuu(peaPane, loppPunkt, nurk - 20 - Math.random() * 10, tase - 1);
        joonistaPuu(peaPane, loppPunkt, nurk - 5, tase - 1);
        joonistaPuu(peaPane, loppPunkt, nurk + 20 + Math.random() * 20, tase - 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
