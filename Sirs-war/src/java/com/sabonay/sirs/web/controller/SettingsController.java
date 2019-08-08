/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Settings;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
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
public class SettingsController implements Serializable{
    private List<Settings> settingsList = new ArrayList<Settings>();
    private Settings settings = new Settings();
    private Settings selectedSettings = new Settings();
    private Boolean settingsIsAdd = true;
    
     //<editor-fold  desc="Settings Button Actions" > 
 
    public void settingsUpdate() {
        try {
            SirsDataSource.dataSource().settingsUpdate(settings);
            settingsReset();
            JSFUtility.infoMessage("Settings Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update Settings");
        }
    }
    
    public void handleSettingsSelection(){  
        settings = selectedSettings;
        settingsIsAdd = false;
    }

    public String settingsReset() {
        settings = new Settings();
        settingsIsAdd = true;
        return "";
    }
    //</editor-fold >

    public List<Settings> getSettingsList() {
        settingsList = SirsDataSource.dataSource().settingsGetAll();
        return settingsList;
    }

    public void setSettingsList(List<Settings> settingsList) {
        this.settingsList = settingsList;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Boolean getSettingsIsAdd() {
        return settingsIsAdd;
    }

    public void setSettingsIsAdd(Boolean settingsIsAdd) {
        this.settingsIsAdd = settingsIsAdd;
    }

    public Settings getSelectedSettings() {
        return selectedSettings;
    }

    public void setSelectedSettings(Settings selectedSettings) {
        this.selectedSettings = selectedSettings;
    }
}