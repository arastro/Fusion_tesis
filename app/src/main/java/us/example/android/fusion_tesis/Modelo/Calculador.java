package us.example.android.fusion_tesis.Modelo;

/**
 * Created by Edgar on 7/05/2017.
 */

public class Calculador {
    public static double Redondear(double numero)
    {
        return Math.rint(numero*100)/100;
    }
}
