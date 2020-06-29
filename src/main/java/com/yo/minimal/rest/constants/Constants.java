package com.yo.minimal.rest.constants;

public final class Constants {

    public Constants() {
    }

    // Constante de precios
    public static final String defaultPrice = "0.00";

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
}
