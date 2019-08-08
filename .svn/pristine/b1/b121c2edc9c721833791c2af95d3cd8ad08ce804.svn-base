/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.sirs.web.common;

import com.sabonay.sirs.web.common.utilities.JSFUtility;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author emma
 */
@ManagedBean
@RequestScoped
public class AdminAccessController {

    /** Creates a new instance of OperatorAccessController */
    public AdminAccessController() {
    }

    private boolean isLogin = false;
    private boolean isAdmin = false;
    private boolean isComplainOnly = false;
    private LoginUser loginUser = null;
    private boolean subUser = false;
    private String loginUsername = "";

    /** Creates a new instance of UserAccessController */

    /**
     * @return the isLogin
     */
    public boolean isIsLogin() {

        LoginUser user = getLoginUser();
        if (user == null) {
            isLogin = false;
        } else {
            isLogin = user.isIsLogin();
        }

        return isLogin;
    }

    /**
     * @param isLogin the isLogin to set
     */
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    /**
     * @return the isAdmin
     */
    public boolean isIsAdmin() {
        LoginUser user = getLoginUser();
        if (user == null) {
            isAdmin = false;
        } else {
            isAdmin = user.isIsAdmin();
        }
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the loginUser
     */
    public LoginUser getLoginUser() {
        try {
            loginUser = (LoginUser) JSFUtility.getSessionValue(ApplicationConstant.ADMIN_USER);
        } catch (Exception e) {
            loginUser = null;
        }
        return loginUser;
    }

    /**
     * @param loginUser the loginUser to set
     */
    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    /**
     * @return the isComplainOnly
     */
    /**
     * @param isComplainOnly the isComplainOnly to set
     */
    public void setIsComplainOnly(boolean isComplainOnly) {
        this.isComplainOnly = isComplainOnly;
    }

    /**
     * @return the subUser
     */
    /**
     * @param subUser the subUser to set
     */
    public void setSubUser(boolean subUser) {
        this.subUser = subUser;
    }

    /**
     * @return the loginUsername
     */
    public String getLoginUsername() {
        try {

        LoginUser user = getLoginUser();
        loginUsername = user.getUserScreenName();
        } catch (Exception e) {
        }
        return loginUsername;
    }

    /**
     * @param loginUsername the loginUsername to set
     */
    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

}
