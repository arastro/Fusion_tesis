package us.example.android.fusion_tesis.UI;

/**
 * Created by DCA-20 on 17/05/2017.
 */

public class Ruta {
    private static final Ruta ourInstance = new Ruta();

    // Cada ruta tiene comentado en que actividad se es usada

    public static final String CAMBIO_CLAVE_URL = "http://ceramicapiga.com/tesis/changePassword.php"; // ActividadCambioClave
    public static final String GET_SITE_INFO_URL = "http://ceramicapiga.com/tesis/get_site_info.php";   // ActividadDetalleSitio
    public static final String MAKE_RATING_URL = "http://ceramicapiga.com/tesis/makeRating.php";    // ActividadDetalleSitio
    public static final String GET_BY_TAG_URL = "http://ceramicapiga.com/tesis/get_by_tag.php"; // ActividadCategoriaSeleccionada
    public static final String GET_15_SITES_URL = "http://ceramicapiga.com/tesis/get15sites.php"; // ActividadListaCompleta
    public static final String GET_RECOM_SITES_URL = "http://ceramicapiga.com/tesis/getRecomSites.php"; //ActividadListaCompleta
    public static final String LOGIN_URL = "http://ceramicapiga.com/tesis/login.php"; // ActividadLogin
    public static final String BUSCAR_SITE_URL = "http://ceramicapiga.com/tesis/searchSite.php"; //FragmentoPrincipal
    public static final String REGISTRAR_USER_URL = "http://ceramicapiga.com/tesis/registration.php"; //ActividadRegistro
    public static final String GET_5_SITE_URL = "http://ceramicapiga.com/tesis/get5sites.php"; //ActividadPrincipal


    public static Ruta getInstance() {
        return ourInstance;
    }

    private Ruta() {
    }
}
