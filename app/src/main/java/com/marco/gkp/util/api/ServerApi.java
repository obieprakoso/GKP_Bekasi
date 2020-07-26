package com.marco.gkp.util.api;

public class ServerApi {

    private static final String ROOT_URL = "http://192.168.43.32:8080/";

    public static final String URL_LOGIN = ROOT_URL + "gkp_bekasi_api/login.php";
    public static final String URL_VIEW_ALL_JAMAAT = ROOT_URL + "gkp_bekasi_api/jemaat_new.php";
    public static final String URL_VIEW_DETAIL_JAMAAT = ROOT_URL + "gkp_bekasi_api/detail_jamaat.php?offset=";
    public static final String URL_VIEW_ALL_KATEGORIAL = ROOT_URL + "gkp_bekasi_api/kategorial.php?offset=";
    public static final String URL_VIEW_DETAIL_KATEGORIAL = ROOT_URL + "gkp_bekasi_api/detail_kategorial.php?offset=";
    public static final String URL_VIEW_ALL_NON_KATEGORIAL = ROOT_URL + "gkp_bekasi_api/non_kategorial.php?offset=";
    public static final String URL_VIEW_DETAIL_NON_KATEGORIAL = ROOT_URL + "gkp_bekasi_api/detail_non_kategorial.php?offset=";
    public static final String URL_VIEW_ALL_EWARTA = ROOT_URL + "gkp_bekasi_api/new_ewarta.php?offset=";
    public static final String URL_VIEW_DETAIL_EWARTA = ROOT_URL + "gkp_bekasi_api/detail_ewarta.php?offset=";


}
