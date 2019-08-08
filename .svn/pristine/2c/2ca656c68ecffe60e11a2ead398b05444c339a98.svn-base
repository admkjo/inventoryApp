/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Accesslevel;
import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import com.sabonay.sirs.web.common.utilities.SecurityHash;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author emma
 *
 */ 
@ManagedBean
@SessionScoped
public class UserController implements Serializable{
    private List<AdminUser> adminUserList = new ArrayList<AdminUser>();
    private AdminUser adminUser = new AdminUser();
    private AdminUser selectedAdminUser = new AdminUser();
    private Boolean adminUserIsAdd = true;
    private String statusButton = "Disabled"; 
    private String password = null;
    private String userLevel = null;
    
     //<editor-fold  desc="AdminUser Button Actions" >  
    public void adminUserAdd() {
        try {
            if (!validateAdminUser()){ 
                return;
            }
            adminUser.setStatus('1');
            adminUser.setAccessLevel(new Accesslevel(userLevel));
            if (null == password || "".equals(password)){
                 
            }else{
                adminUser.setUserPassword(SecurityHash.MD5HashEncryption(password));
            }
            
            SirsDataSource.dataSource().adminUserCreate(adminUser);
            JSFUtility.infoMessage("AdminUser added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New AdminUser"); 
        } 
    }
 
    public void adminUserUpdate() {
        try {
            adminUser.setAccessLevel(new Accesslevel(userLevel));
            if (null == password || "".equals(password)){
                 
            }else{
                adminUser.setUserPassword(SecurityHash.MD5HashEncryption(password));
            }
            SirsDataSource.dataSource().adminUserUpdate(adminUser);
            adminUserReset();
            JSFUtility.infoMessage("AdminUser Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update AdminUser");
        }
    }
    
    private boolean validateAdminUser() { 
        if (null == adminUser.getUsername() || "".equals(adminUser.getUsername())) {
            JSFUtility.errorMessage("Username required.");
            return false;
        }
        if (null == adminUser.getFullName() || "".equals(adminUser.getFullName())) {
            JSFUtility.errorMessage("User Fullname required.");
            return false;
        }
        if (null == password || "".equals(password)) {
            JSFUtility.errorMessage("User Password required.");
            return false;
        }

        return true;
    }
    
    public void handleAdminUserSelection(){  
        adminUser = selectedAdminUser;
        userLevel= adminUser.getAccessLevel().getAcid();
        password = null;
        if (adminUser.getStatus() == '1'){  
            statusButton = "Disable";
        }else{
            statusButton = "Enable";
        }
        adminUserIsAdd = false;
    }

    public String adminUserReset() {
        adminUser = new AdminUser();
        userLevel = null;
        adminUserIsAdd = true;
        return "";
    }

    public String adminUserDelete() {
        try {
            String msg = "";
            if (adminUser.getStatus() == '1'){
                adminUser.setStatus('0');
                msg = "Disabled";
            }else{
                adminUser.setStatus('1');
                msg = "Enabled";
            }
            
            SirsDataSource.dataSource().adminUserUpdate(adminUser);
            adminUserReset();
            JSFUtility.infoMessage("AdminUser " + msg+" Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Delete AdminUser");
        }
        return "";
    }
    //</editor-fold >

    public List<AdminUser> getAdminUserList() {
        adminUserList = SirsDataSource.dataSource().adminUserGetAll();
        return adminUserList;
    }

    public void setAdminUserList(List<AdminUser> adminUserList) {
        this.adminUserList = adminUserList;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public Boolean getAdminUserIsAdd() {
        return adminUserIsAdd;
    }

    public void setAdminUserIsAdd(Boolean adminUserIsAdd) {
        this.adminUserIsAdd = adminUserIsAdd;
    }

    public AdminUser getSelectedAdminUser() {
        return selectedAdminUser;
    }

    public void setSelectedAdminUser(AdminUser selectedAdminUser) {
        this.selectedAdminUser = selectedAdminUser;
    }

    public String getStatusButton() {
        return statusButton;
    }

    public void setStatusButton(String statusButton) {
        this.statusButton = statusButton;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}