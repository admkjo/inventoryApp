/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.LoginUser;
import com.sabonay.sirs.web.common.MySQLConnector;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author emma
 *
 */
@ManagedBean
public class StartupController {

    MySQLConnector dbConnection = new MySQLConnector();
    private static final Logger LOG = Logger.getLogger(StartupController.class.getName());
    private String page = null;

    private AdminUser getLoginUser() {
        try {
            return (AdminUser) ((LoginUser) JSFUtility.getSessionValue(ApplicationConstant.ADMIN_USER)).getUserLogin();
        } catch (Exception e) {
            return null;
        }
    }

    public String templateFile() throws SQLException {  
        //Get the Http ServletRequest 
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        //Get the Page from the url page eg ?page=5134 
        page = request.getParameter("page");

        //Check if the page has a value
        if (null == page || "".equals(page)) {
            //Default page/home page
            if (null != getLoginUser()) {
               page = "transaction";
            }else{
                page = "transaction";  
            }  

            return "theme/template/" + ApplicationTheme() + "/c_temp.xhtml";
        } 
        
        System.out.println("page..." + page);

        return "theme/template/" + ApplicationTheme() + "/c_temp.xhtml";

    }

    public String[][] ApplicationSettings() throws SQLException {

        String[][] siteSettings = new String[50][2];
        String q = "select * from settings";
        ResultSet rs = dbConnection.processQuery(q);
        int counter = 0;

        while (rs.next()) {
            siteSettings[counter][0] = rs.getString(2);
            siteSettings[counter][1] = rs.getString(3);
            counter += 1;
        }

        return siteSettings;
    }

    private String ApplicationTheme() throws SQLException {
        String q = "select * from settings where settingname='CURRENTTHEME'";
        ResultSet rs = dbConnection.processQuery(q);
        if (rs.next()) {
            return rs.getString(3);
        }

        return null;
    }

    public String getApplicationSettings(int x, int y) throws SQLException {
        return ApplicationSettings()[x][y];
    }

    public String getCurrenDate() {
        return (new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()));
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}