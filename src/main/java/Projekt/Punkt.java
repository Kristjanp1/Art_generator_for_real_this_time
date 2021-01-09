package Projekt; /*************************************************
 * Random Art Generator
 *
 * Autorid: Elina Meier, Kristjan Pühvel
 *
 * Tartu Ülikool
 * kevadsemester 2020
 ************************************************/

public class Punkt {

    private double x;
    private double y;

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * meetod leiab rekursiivse puu joonistamise jaoks joonele uue lõpp-punkti
     * @param oksaKaugus
     * @param nurk
     * @param tase
     * @return tagastab punkti x ja y koordinaadid
     */
    public Punkt leiaUusLopp(double oksaKaugus, double nurk, int tase) {
        double uusX = x + (Math.cos(Math.toRadians(nurk)) * tase * oksaKaugus);
        double uusY = y - (Math.sin(Math.toRadians(nurk)) * tase * oksaKaugus);
        Punkt punkt = new Punkt(uusX, uusY);
        return punkt;
    }
}