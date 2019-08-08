/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.common;

import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author emma
 */
public class ApplicationConstant {

    public static String STRING = "STRING";
    public static String NUMBER = "NUMBER";
    public static String LOGIN_USER = "LOGIN_USER";
    public static String ADMIN_USER = "ADMIN_USER";
    public static String IS_ADMIN = "IS_ADMIN";
    public static String IS_LOGIN = "IS_LOGIN";
    public static String ACTIVE = "ACTIVE";
    public static String APPICATION_CODE = "60Capital";
    public static String ZERO = "0";
    public static String NUMBER_FORMAT_15 = "00000000000000";
    public static String NUMBER_FORMAT_4 = "0000";
    public static String NAME = "name";
    public static String TEL = "tel";
    public static String EMAIL = "email";
    public static String DEFAULT_PASSWORD = "012345";
    public static String SETTING = "setting";
    public static String ACTIVE_COUNTRY = "active_country";
    public static String IDENTIFICATION_TYPE = "id_type";
    public static String ENGLISH = "en";
    public static String FRENCH = "fr";
    public static String USER_LANGUAGE = "CURRENT_LANGUAGE";
    public static String FACES_REDIRECT = "?faces-redirect=true";
    public static char YES_STATUS = 'Y';
    public static char NO_STATUS = 'N';
    public static String FEET = "F";
    public static String INCH = "I";
    public static String NEW_CUSTOMER = "NC";
    public static String EXISTING_CUSTOMER = "EC";
    public static String CUSTOMER_NAME = "CN";
    public static String CUSTOMER_MOBILE = "CM";
    public static String LAST_INVOICE = "LAST_INVOICE";
    public static String ORDER = "ORDER";
    public static String QUOTATION = "QUOTATION";
    public static String STANDARD = "S";
    public static String MEASURED = "M";
    
    
    public static String COMPANY_NAME = "COMPANY_NAME";
    public static String MOTTO = "MOTTO";
    public static String CONTACT_ADDRESS = "CONTACT_ADDRESS";    
    public static String CONTACT_TEL = "CONTACT_TEL";
    
    
    
    final static String DATABASE_NAME ="sirsext"; 
    final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
    final static String DATABASE_USER ="sabonay";
    final static String DATABASE_PASSWORD ="qdeerq$2012" ;
    
    public static final String NO_OF_ROWS_TO_DISPLAY = "ADMIN_PAGE_ROW_NO";    
    public static final String SABONAY_EDUCATION_LINK = "SABONAY_EDUCATION_LINK";
    public static final String STAFF_MAIL_URL = "STAFF_MAIL_URL";

    public static final String SERVER_ROOT_PATH = System.getProperty("com.sun.aas.instanceRoot") + File.separator + "docroot";
    public static final String RESOURCE_DIRECTORY = CommonUtils.getSettingValue("RESOURCE_DIRECTORY");
    public static final String RESOURCES = "../" + CommonUtils.getSettingValue("RESOURCE_DIRECTORY") + "/";
    public static final String SITE_URL = CommonUtils.getSettingValue("SITE_URL");
    
    
    public static String FORM_CATEGORY_ADD = "frmcatadd";
    public static String FORM_CATEGORY_UPDATE = "frmcatupdate";
    public static String FORM_CONTENT_UPDATE = "frmcontentUpdate";
    public static String FORM_CONTENT_ADD_PAGE = "frmContentAdd";
    public static String FORM_RESOURCE_UPDATE = "updateResourceFrm";
    
    
    public static String ComputeInvoiceNo(){
        String invoice = CommonUtils.getSettingValue(LAST_INVOICE);
        return invoice;
    }
    
    public static void SaveNewInvoice(String newinvoice) throws SQLException{
        MySQLConnector mysql = new MySQLConnector();
        String statement = "Update settings Set settingvalue='" + newinvoice + "' Where settingname = '" + LAST_INVOICE +"'";
        mysql.processSQLStatement(statement);
    }
}
