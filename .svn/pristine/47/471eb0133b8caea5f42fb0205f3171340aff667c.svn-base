/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.CustomerGroup;
import com.sabonay.sirs.ejb.entities.CustomerMaterialGroup;
import com.sabonay.sirs.ejb.entities.Material;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author emma
 *
 */
@ManagedBean
@SessionScoped
public class CustomerMaterialGroupController implements Serializable {

    private SelectItem[] materialSelect;
    private SelectItem[] customerGroupSelect;
    private List<CustomerGroup> customerGroupList = new ArrayList<CustomerGroup>();
    private Material material = null;
    private List<CustomerMaterialGroup> customerMaterialGroupList = new ArrayList<CustomerMaterialGroup>();
    private CustomerMaterialGroup customerMaterialGroup = new CustomerMaterialGroup();
    private CustomerMaterialGroup selectedCustomerMaterialGroup = new CustomerMaterialGroup();
    private CustomerGroup customerGroup = new CustomerGroup();
    private CustomerGroup selectedCustomerGroup = new CustomerGroup();
    private Boolean customerMaterialGroupIsAdd = true;
    private Boolean customerGroupIsAdd = true;
    private Double oldPrice;
    
    public void customerGroupAdd() {
        try {
            if (null == customerGroup.getGroupName()) {
                return;
            }
            SirsDataSource.dataSource().customerGroupCreate(customerGroup);
            JSFUtility.infoMessage("CustomerGroup added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New CustomerGroup");
        }
    }
    
    public void customerGroupUpdate() {
        try {
            SirsDataSource.dataSource().customerGroupUpdate(customerGroup);
            customerGroupReset();
            JSFUtility.infoMessage("CustomerGroup Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update CustomerGroup");
        }
    }
    
    public String customerGroupReset() {
        customerGroup = new CustomerGroup();
        customerGroupIsAdd = true;
        return "";
    }

    //<editor-fold  desc="CustomerMaterialGroup Button Actions" >  
    public void customerMaterialGroupAdd() {
        try {
            if (!validateCustomerMaterialGroup()) {
                return;
            }
            customerMaterialGroup.setStatus('1');
            SirsDataSource.dataSource().customerMaterialGroupCreate(customerMaterialGroup);
            JSFUtility.infoMessage("CustomerMaterialGroup added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New CustomerMaterialGroup");
        }
    }

    public void customerMaterialGroupUpdate() {
        try {
            SirsDataSource.dataSource().customerMaterialGroupUpdate(customerMaterialGroup);
            customerMaterialGroupReset();
            JSFUtility.infoMessage("CustomerMaterialGroup Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update CustomerMaterialGroup");
        }
    }

    private boolean validateCustomerMaterialGroup() {
        if (null == customerMaterialGroup.getGroupId()) {
            JSFUtility.errorMessage("Please select customer group.");
            return false;
        } 
        if (null == customerMaterialGroup.getMaterial()) {
            JSFUtility.errorMessage("Please select material type.");
            return false;
        } 
        if (null == customerMaterialGroup.getNewPrice() || customerMaterialGroup.getNewPrice() == 0.0) {
            JSFUtility.errorMessage("Please enter new material Cost for selected group.");
            return false;
        }

        return true;
    }

    public void handleMaterialSelect() {
        oldPrice = customerMaterialGroup.getMaterial().getMaterialCost();
    }
    
    public void handleCustomerGroupSelection(){
        customerGroup = selectedCustomerGroup;
        customerGroupIsAdd = false;
    }

    public void handleCustomerMaterialGroupSelection() {
        customerMaterialGroup = selectedCustomerMaterialGroup;
        oldPrice = selectedCustomerMaterialGroup.getMaterial().getMaterialCost();
        customerMaterialGroupIsAdd = false;
    }

    public String customerMaterialGroupReset() {
        customerMaterialGroup = new CustomerMaterialGroup();
        oldPrice = 0.0;
        customerMaterialGroupIsAdd = true;
        return "";
    }

    public String customerMaterialGroupDelete() {
        try {
            SirsDataSource.dataSource().customerMaterialGroupDelete(customerMaterialGroup);
            customerMaterialGroupReset();
            JSFUtility.infoMessage("CustomerMaterialGroup Deleted Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Delete CustomerMaterialGroup");
        }
        return "";
    }
    //</editor-fold >

    public List<CustomerMaterialGroup> getCustomerMaterialGroupList() {
        customerMaterialGroupList = SirsDataSource.dataSource().customerMaterialGroupGetAll(true);
        return customerMaterialGroupList;
    }

    public void setCustomerMaterialGroupList(List<CustomerMaterialGroup> customerMaterialGroupList) {
        this.customerMaterialGroupList = customerMaterialGroupList;
    }

    public CustomerMaterialGroup getCustomerMaterialGroup() {
        return customerMaterialGroup;
    }

    public void setCustomerMaterialGroup(CustomerMaterialGroup customerMaterialGroup) {
        this.customerMaterialGroup = customerMaterialGroup;
    }

    public Boolean getCustomerMaterialGroupIsAdd() {
        return customerMaterialGroupIsAdd;
    }

    public void setCustomerMaterialGroupIsAdd(Boolean customerMaterialGroupIsAdd) {
        this.customerMaterialGroupIsAdd = customerMaterialGroupIsAdd;
    }

    public CustomerMaterialGroup getSelectedCustomerMaterialGroup() {
        return selectedCustomerMaterialGroup;
    }

    public void setSelectedCustomerMaterialGroup(CustomerMaterialGroup selectedCustomerMaterialGroup) {
        this.selectedCustomerMaterialGroup = selectedCustomerMaterialGroup;
    }

    public SelectItem[] getMaterialSelect() {
        try {
            List<Material> acList = SirsDataSource.dataSource().materialGetAll(false);
            materialSelect = new SelectItem[acList.size()];

            int counter = 0;
            for (Iterator<Material> it = acList.iterator(); it.hasNext();) {
                Material sto = it.next();
                materialSelect[counter] = new SelectItem(sto, sto.getMaterialName());
                counter++;
            }
        } catch (Exception e) {
            materialSelect = new SelectItem[1];
            materialSelect[0] = new SelectItem("0", "No Material Available");
        }
        return materialSelect;

    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public List<CustomerGroup> getCustomerGroupList() {
        customerGroupList = SirsDataSource.dataSource().customerGroupGetAll(true);
        return customerGroupList;
    }

    public void setCustomerGroupList(List<CustomerGroup> customerGroupList) {
        this.customerGroupList = customerGroupList;
    }

    public SelectItem[] getCustomerGroupSelect() {
        try {
            List<CustomerGroup> acList = SirsDataSource.dataSource().customerGroupGetAll(true);
            customerGroupSelect = new SelectItem[acList.size()];

            int counter = 0;
            for (Iterator<CustomerGroup> it = acList.iterator(); it.hasNext();) {
                CustomerGroup sto = it.next();
                customerGroupSelect[counter] = new SelectItem(sto, sto.getGroupName());
                counter++;
            }
        } catch (Exception e) {
            customerGroupSelect = new SelectItem[1]; 
            customerGroupSelect[0] = new SelectItem("0", "No Group Available");
        }
        return customerGroupSelect;
    }

    public void setCustomerGroupSelect(SelectItem[] customerGroupSelect) {
        this.customerGroupSelect = customerGroupSelect;
    }

    public CustomerGroup getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(CustomerGroup customerGroup) {
        this.customerGroup = customerGroup;
    }

    public CustomerGroup getSelectedCustomerGroup() {
        return selectedCustomerGroup;
    }

    public void setSelectedCustomerGroup(CustomerGroup selectedCustomerGroup) {
        this.selectedCustomerGroup = selectedCustomerGroup;
    }

    public Boolean getCustomerGroupIsAdd() {
        return customerGroupIsAdd;
    }

    public void setCustomerGroupIsAdd(Boolean customerGroupIsAdd) {
        this.customerGroupIsAdd = customerGroupIsAdd;
    }
}