/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.LoginUser;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import com.sabonay.sirs.web.common.utilities.SecurityHash;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author emma
 */
@ManagedBean
@RequestScoped
public class userLogin {

    private String username;
    private String password;
    private String email;

    /**
     * Creates a new instance of UserLogin
     */
    public userLogin() {
    }

    public String adminLogin() {
        try {
            if ("".equals(username) || "".equals(password)) {
                JSFUtility.warnMessage("Please enter your username and password.");
                return "index.xhtml";
            }
            String pwd = SecurityHash.MD5HashEncryption(password);
            AdminUser admin = SirsDataSource.dataSource().adminUserLogin(username, pwd);
            if (admin == null) {
                JSFUtility.errorMessage("Wrong User Credentials.");
                return "index.xhtml";
            } else {

                if ('0' == admin.getStatus()) {
                    JSFUtility.errorMessage("Your account has been disabled. Please contact administrator.");
                    return "index.xhtml";
                }

                LoginUser user = new LoginUser();
                user.setAccessFor(admin.getAccessLevel().getAcid());
                user.setUserLogin(admin);
                user.setUserScreenName(admin.getUsername());
                user.setIsAdmin("admin".equals(admin.getAccessLevel().getAcid()) ? true : false);
                user.setIsLogin(true);

                JSFUtility.putSessionValue(ApplicationConstant.ADMIN_USER, user);

                admin.setLastLogin(DateTimeUtils.getCurSqlDate());
                SirsDataSource.dataSource().adminUserUpdate(admin);

                if (user.isIsAdmin()) {
                    return "day-sales.xhtml" + ApplicationConstant.FACES_REDIRECT; 
                }

                return "";
                //return "member-account.xhtml" + ApplicationConstant.FACES_REDIRECT;
            }
        } catch (Exception e) {
            JSFUtility.warnMessage(JSFUtility.getResourceBundleKeyValue("WRONG_LOGIN"));
            return "index.xhtml";
        }
    }

    public String logoutClient() {
        JSFUtility.putSessionValue(ApplicationConstant.LOGIN_USER, null);
        JSFUtility.destroySession();
        return "index.xhtml" + ApplicationConstant.FACES_REDIRECT;
    }

    public String redirectPage(String url) {
        if (url.equals("password")) {
            return "password.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("user")) {
            return "member-account.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("joinnow")) {
            return "register.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("upgradenow")) {
            return "upgrade.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("fcp-reg")) {
            return "fcp-reg.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("bank-reg")) {
            return "bank-reg.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("bank-upg")) {
            return "bank-upg.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("fcp-upg")) {
            return "fcp-upg.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("dl")) {
            return "downlines.xhtml?faces-redirect=true";
            //return "dl.xhtml?faces-redirect=true&appl=60c&cmm=" + SCapitalUtils.loggedinCapitalMember().getMobileNumber();
        } else if (url.equals("urb")) {
            return "benefit-and-investment.xhtml" + ApplicationConstant.FACES_REDIRECT;
        } else if (url.equals("acc")) {
            return "account-transaction.xhtml" + ApplicationConstant.FACES_REDIRECT;
        }

        return "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
