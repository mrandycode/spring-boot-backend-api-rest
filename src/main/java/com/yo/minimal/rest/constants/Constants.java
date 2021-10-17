package com.yo.minimal.rest.constants;

public final class Constants {

    public Constants() {
    }

    // Constantes para manejo de función UPLOAD IMAGE
    public static final String custoType = "PHOTO_CUSTOMER";
    public static final String itemType = "PHOTO_ITEM";
    public static final String noDispImage = "nodisp.png";

    // Constantes para el manejo de imágenes
    public final static String UPLOADS_FOLDER_CUST = System.getProperty("pathPhotoCustomer");
    public final static String UPLOADS_FOLDER_ITEM = System.getProperty("pathPhotoItem");
    public final static String PHOTO_ITEM_NOT_DISP = System.getProperty("fotonodispItem");
    public final static String PHOTO_CUSTO_NOT_DISP = System.getProperty("fotonodisp");

    // Constantes para manejo de facturas.
    public final static String TYPE_INVOICE_INVOICE = "I";
    public final static String TYPE_INVOICE_REFUND = "R";
    public final static String TYPE_INVOICE_REFUND_PROCESSED = "RP";

    // Código y mensaje de respuestas:
    public final static String COD_OK_EXECUTE = "201";
    public final static String MSG_OK_EXECTUTE = "Ejecución Exitosa";

    // Valor de CorsOrigin
    public final static String URL_ORIGIN = System.getProperty("originUrl");
    public final static String URL_ORIGIN_ECOMMERCE = System.getProperty("originUrlECommerce");

    // Valores para imágenes

    public final static String MESSAGE_IMAGE_RESIZE_SUCCESS = "Se ha re-dimensionado la imagen con éxito ";
    public final static String MESSAGE_IMAGE_RESIZE_ERROR = "Se ha producido un error al re-dimensionar la imagen: ";
    public final static int IMAGE_WIDTH_SMALL = 300;
    public final static int IMAGE_HEIGHT_SMALL = 300;

    // STORED PROCEDURES
    public final static String QUERY_INVOICE_WITH_PAYMENTS = "CALL SP_GET_INVOICES_WITH_PAYMENTS";
    public final static String SP_GET_INVOICES = "CALL SP_GET_INVOICES(:type)";

}
