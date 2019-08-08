/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.common.utilities;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.LoginUser;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sabonay
 */
public class JSFUtility {

    public JSFUtility() {
    }
    int counter = 0;

    public static Object getManageBean(String beanName, FacesContext fc) {
        if (fc == null) {
        } else {
            fc = getCurrentContext();
        }

        return fc.getApplication().getELResolver().getValue(fc.getELContext(), null, beanName);
    }

    public static FacesContext getCurrentContext() {
        return FacesContext.getCurrentInstance();
    }

    public static FacesContext currentContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext externalContext() {
        return currentContext().getExternalContext();
    }

    public static ValueExpression createValueExpression(String elExpression, Class type) {
        return currentContext().getApplication().getExpressionFactory().createValueExpression(currentContext().getELContext(), "#{" + elExpression + "}", type);
    }

    public SelectItem[] createMenuItems(List<Object> objList, boolean needInitial) {
        counter = 0;
        SelectItem[] items;

        if (needInitial) {
            items = new SelectItem[objList.size() + 1];
            items[counter] = new SelectItem("", "-- Please Select--");
            counter++;
        } else {
            items = new SelectItem[objList.size()];
        }

        for (Object slist : objList) {
            items[counter] = new SelectItem(slist, slist.toString());
            counter++;
        }
        return items;
    }

    public static void addMessage(FacesMessage.Severity severity, String message) {
        FacesMessage fm = new FacesMessage(severity, message, message);
        getCurrentContext().addMessage(null, fm);
    }

    public static void warnMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, message);
    }

    public static void infoMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }

    public static void errorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message);
    }

    public static void fatalMessage(String message) {
        addMessage(FacesMessage.SEVERITY_FATAL, message);
    }

    public static HttpSession getSession() {

        HttpSession session = (HttpSession) externalContext().getSession(true);
        return session;
    }

    public static void putSessionValue(String name, Object val) {
        //System.out.println(name + " : " + val);
        getSession().setAttribute(name, val);

    }

    public static Object getSessionValue(String name) {
        return getSession().getAttribute(name);

    }

    public static void destroySession() {
        getSession().invalidate();
    }

    public static void redirectPage(FacesContext context, String formOutcome) {
        context.responseComplete();
        //System.out.println(context + " REdirecting to " + formOutcome);
        try {
            context.getExternalContext().redirect("/main.xhtml");
        } catch (Exception e) {
        }

    }

//    public static AdminUser loginAdminUser() {
//        LoginUser loginUser = (LoginUser) JSFUtility.getSessionValue(ApplicationConstant.ADMIN_USER);
//        
//
//        return adminUser;
//    }

    public static String browserLocale() {
        String language = "";
        boolean considered = false;
        try {
            language = (String) getSessionValue(ApplicationConstant.USER_LANGUAGE);
            considered = true;
        } catch (Exception e) {
            System.out.println("Error : No language Set");

        }

        if ((language == null)) {
            //System.out.println("Entering Browser Browser");
            Map headerRFequest = externalContext().getRequestHeaderMap();
            String languages = (String) headerRFequest.get("Accept-Language");
            String lang;
            //System.out.println("Browser Language : "+languages);
            lang = checkLanguage(languages);
            putSessionValue(ApplicationConstant.USER_LANGUAGE, lang);
            return lang;
        } else {
            return language;
        }

    }

    public static String checkLanguage(String langues) {

        if (langues.contentEquals("fr")) {
            return ApplicationConstant.FRENCH;
        } else {
            return ApplicationConstant.ENGLISH;
        }

    }
    private static ResourceBundle bundle = null;

    public static ResourceBundle getBundle() {
        if (bundle == null) {
            try {
                bundle = currentContext().getApplication().getResourceBundle(currentContext(), "msg");

            } catch (Exception e) {
                System.out.println("Bundle Not Found : " + e.toString());
                e.printStackTrace();
            }
        }

        return bundle;
    }

    public static String getResourceBundleKeyValue(String key) {

        String result = "";
        try {
            //System.out.println("Key : "+key);


            result = getBundle().getString(key);
        } catch (Exception e) {
            System.out.println("Resource Not Found : " + key);
        }


        return result;
    }

    public static String pageFullPath() {

        return externalContext().getRequestPathInfo();
    }

    public static String pageRelativePath() {

        String path = externalContext().getRequestPathInfo();

        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static boolean createCookie(String cookieName, String cookieValue) {
        try {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            HttpServletResponse res = (HttpServletResponse) externalContext().getResponse();
            res.addCookie(cookie);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static String getCookie(String cookieName) {
        HttpServletRequest request = (HttpServletRequest) externalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        boolean cookieFound = false;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
//doSomethingWith(cookie.getValue());
                    System.out.println("cookie : " + cookie.getValue());
                    try {

                        return cookie.getValue();
                    } catch (Exception e) {
                    }

                    cookieFound = true;
                }
            }
        }

        return "";

    }
}
