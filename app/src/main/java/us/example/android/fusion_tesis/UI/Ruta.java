package us.example.android.fusion_tesis.UI;

/**
 * Created by DCA-20 on 17/05/2017.
 */

public class Ruta {
    private static final Ruta ourInstance = new Ruta();
    public static final String CAMBIO_CLAVE_URL = "http://ceramicapiga.com/tesis/changePassword.php";
    public static final String GET_SITE_INFO_URL = "http://ceramicapiga.com/tesis/get_site_info.php";
    public static final String MAKE_RATING_URL ="http://ceramicapiga.com/tesis/makeRating.php";


    public static Ruta getInstance() {
        return ourInstance;
    }

    private Ruta() {
    }
}
