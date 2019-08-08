/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

/**
 *
 * @author emma
 */
public class CommonUtils {
    static MySQLConnector dbConnection = new MySQLConnector();
    public String processUrl(String cattype, int contentid, String ptitle, String directlink) {
        String url = "";
        try {
            if (directlink.length() > 4) {
                url = directlink;
            } else {
                url = "?" + urlFriendlyData(ptitle) + "&" + cattype + "=" + contentid;
            }
        } catch (NullPointerException e) {
            directlink = "";
            url = "?" + urlFriendlyData(ptitle) + "&" + cattype + "=" + contentid;
        }

        return url;
    }

    public String getSmallContents(String content, int offset) {
        String rr = strip_tags(content);
        String str = "";
        if (rr.length() <= offset) {
            str = rr;
        } else {
            str = rr.substring(0, offset);

            int startFrom = 0;
            //echo  $startFrom." From ".$sttr;
            int lastDot = str.lastIndexOf(".");
            int lastSpace = str.lastIndexOf(" ");
            int to = 0;
            if (lastDot > lastSpace) {
                to = lastDot;
            } else {
                to = lastSpace;
            }

            str = str.substring(0, to);

        }
        if (str.length() == 0) {
            if (rr.length() == 0) {
                str = "";
            } else {
                int lastDot = rr.lastIndexOf(".");
                int lastSpace = rr.lastIndexOf(" ");
                int m = lastSpace;
                if (lastDot > lastSpace) {
                    m = lastDot;
                }
                str = rr.substring(0, m);
            }
        }
        return str;
    }

    public String urlFriendlyData(String url) {
        url = url.replaceAll("\\W", "-");
        url = url.replaceAll("--|---", "-");
        url = url.toLowerCase();
        return url;
    }

    public String addLimitValues(int offset, int numToShow, boolean showAll) {
        String newQuery = "";
        if (showAll == false) {
            newQuery = " limit " + offset + " ,  " + numToShow + " ";
        }
        return newQuery;
    }

    public String addOrderBy(String by) {
        by = by.toLowerCase();
        String orderBy = "";

        return null;
    }

    public String addSlashes(String str) {
        if (str == null) {
            return "";
        }

        StringBuffer s = new StringBuffer((String) str);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') {
                s.insert(i++, '\\');
            }
        }
        return s.toString();

    }

    public String strip_tags(String htmlString) {
        String noHTMLString = htmlString.replaceAll("\\<.*?>", "");
        return noHTMLString;
    }

    public static String adminUpdateLinks(int pageid) {
        return "<a href='http://localhost/sabonay/admin/?page=content&itemid=" + pageid + "' class='update' title='Update' target='_blank' ><img src='theme/default/navs/note.gif' /></a>";
    }

    public static int pageRowNo(){
        try {
            String q = "select settingvalue from settings where settingname='" + ApplicationConstant.NO_OF_ROWS_TO_DISPLAY + "'";
            ResultSet rs = dbConnection.processQuery(q);
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException sQLException) {
        }
        return 10; // Default Value of Page Row
    }

//    public static Object GetSQLValueString(Object theValue, Object theType) {
//        if ("text".equals(theType)) {
//            theValue = (theValue != "") ? "'" + StringEscapeUtils.escapeSql(String.valueOf(theValue)) + "'" : "NULL";
//        } else {
//            if ("date".equals(theType)) {
//                theValue = (theValue != "") ? "'" + theValue + "'" : "NULL";
//            } else {
//                theValue = StringEscapeUtils.escapeSql(String.valueOf(theValue));
//            }
//        }
//        return theValue;
//    }

    public static boolean isset(String value) {
        if (null == value) {
            return false;
        } else {
            return true;
        }
    }

    public static String ucfirst(String text) { // Convert first character of sentence to upper case (eg. Kofi is good)
        if (null != text && !"".equals(text)) {
            return String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1);
        }

        return ""; 
    }

    public static String strchr(String text, String search) {
        //The strchr() function is in PHP and it searches for the first occurrence of a string inside another string.
        //Example: strchr("Hello world. This is God's world", "world") returns   world. This is God's world
        return text.substring(text.toLowerCase().lastIndexOf(search.toLowerCase()), text.length());
    }

    public static int strripos(String text, String search) {
        //The strripos() function is in PHP and it finds the position of the last occurrence of a string inside another string.
        //Example: strchr("Hello world. This is God's world", "world") returns  28
        return text.indexOf(text.substring(text.lastIndexOf(search.toLowerCase()), text.length()));
    }

    public static String createComboList(int num, String selected) {
        String d = "";
        for (int i = 0; i < num; i++) {
            String ot = String.valueOf(i);
            if (i < 10) {
                ot = "0" + i;
            }
            d += "<option value='" + ot + "' ";
            if (selected.equals(String.valueOf(i))) {
                d += "selected='selected'";
            }
            d += ">" + ot + "</option>";
        }

        return d;
    }
    
    public static String stripUslessCharacters(String inputText){
        String newscontent = inputText.replace("�", "\"");
        newscontent = newscontent.replace("�", "'");
        newscontent = newscontent.replace("�", "-");
        newscontent = newscontent.replace("�", "'");
        newscontent = newscontent.replace("�", "'");
        newscontent = newscontent.replace("  ", " ");
        newscontent = newscontent.replace("�", "&cent;");
        newscontent = newscontent.replace("�", "&pound;");
        newscontent = newscontent.replace("�", "&agrave;");
        newscontent = newscontent.replace("�", "&Agrave;");
        newscontent = newscontent.replace("�", "&Aacute;");
        newscontent = newscontent.replace("�", "&aacute;");
        newscontent = newscontent.replace("�", "&Acirc;");
        newscontent = newscontent.replace("�", "&acirc;");
        newscontent = newscontent.replace("�", "&AElig;");
        newscontent = newscontent.replace("�", "&aelig;");
        newscontent = newscontent.replace("�", "&Ccedil;");
        newscontent = newscontent.replace("�", "&ccedil;");
        newscontent = newscontent.replace("�", "&atilde;");
        newscontent = newscontent.replace("�", "&Atilde;");
        newscontent = newscontent.replace("�", "&ecirc;");
        newscontent = newscontent.replace("�", "&Ecirc;");
        newscontent = newscontent.replace("�", "&Eacute;");
        newscontent = newscontent.replace("�", "&eacute;");
        newscontent = newscontent.replace("�", "&Egrave;");
        newscontent = newscontent.replace("�", "&egrave;");
        
        return newscontent;
    }

    public static BufferedImage convertFileToImage(File imageFile) {
        if (imageFile == null) {
            return null;
        }

        if (imageFile.isDirectory()) {
            return null;
        }

        try {
            return ImageIO.read(imageFile);
        } catch (IOException ex) {

            return null;
        } 
    }

    public static String createThumb(String sourceimg, int preferedSize) {
        String filetype = strchr(sourceimg, ".").substring(1);
        if ("jpg".equals(filetype)) {
            filetype = "jpeg";
        }
        
        return "";
    }
    
    public static String urlFriendly(String location, String replaceWith){
        return location.replaceAll("[\\W]", replaceWith);
    }
    
    public static int getLastResource() throws SQLException{
        String q = "select resourceid from resources order by resourceid desc limit 0,1";
        ResultSet rs = dbConnection.processQuery(q);
        
        if (rs.next()){
            return rs.getInt(1);
        }
        
        return 0;
    }
    
    public static String getSettingValue(String settingKey){
        try {
            String q = "select settingvalue from settings where settingname='" + settingKey + "'";
            ResultSet rs = dbConnection.processQuery(q);
            
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException sQLException) {
            
        }
        return null;
    }
    
    public static final int SITE_ID = 0; 
//    public static void main(String[] args) {
//        String sourceimg = "application/vnd.openxmlformats-officedocument.wordprocessingml.document!";
//        
//        System.out.println("" + sourceimg.indexOf("application"));
//        System.out.println("" + strripos(sourceimg, "vnd.openxmlformats-officedocument"));
//    }
}
