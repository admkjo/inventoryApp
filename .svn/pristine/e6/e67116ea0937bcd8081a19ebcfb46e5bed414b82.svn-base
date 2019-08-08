/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Material;
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
public class MaterialController implements Serializable{
    private List<Material> materialList = new ArrayList<Material>();
    private Material material = new Material();
    private Material selectedMaterial = new Material();
    private Boolean materialIsAdd = true;
    
     //<editor-fold  desc="Material Button Actions" >  
    public void materialAdd() {
        try {
            if (!validateMaterial()){
                return;
            }
            material.setStatus('1');
            SirsDataSource.dataSource().materialCreate(material);
            JSFUtility.infoMessage("Material added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New Material");
        } 
    }
 
    public void materialUpdate() {
        try {
            SirsDataSource.dataSource().materialUpdate(material);
            materialReset();
            JSFUtility.infoMessage("Material Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update Material");
        }
    }
    
    private boolean validateMaterial() {
        if (null == material.getMaterialName() || "".equals(material.getMaterialName())) {
            JSFUtility.errorMessage("Material Name required.");
            return false;
        }
        if (null == material.getMaterialCost() || material.getMaterialCost() == 0.0) {
            JSFUtility.errorMessage("Material Cost required.");
            return false;
        }

        return true;
    }
    
    public void handleMaterialSelection(){  
        material = selectedMaterial;
        materialIsAdd = false;
    }

    public String materialReset() {
        material = new Material();
        materialIsAdd = true;
        return "";
    }

    public String materialDelete() {
        try {
            SirsDataSource.dataSource().materialDelete(material);
            materialReset();
            JSFUtility.infoMessage("Material Deleted Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Delete Material");
        }
        return "";
    }
    //</editor-fold >

    public List<Material> getMaterialList() {
        materialList = SirsDataSource.dataSource().materialGetAll(true);
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Boolean getMaterialIsAdd() {
        return materialIsAdd;
    }

    public void setMaterialIsAdd(Boolean materialIsAdd) {
        this.materialIsAdd = materialIsAdd;
    }

    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }
}