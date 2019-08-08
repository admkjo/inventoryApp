/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Customer;
import com.sabonay.sirs.ejb.entities.CustomerGroup;
import com.sabonay.sirs.ejb.entities.Job;
import com.sabonay.sirs.web.common.ApplicationConstant;
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
public class CustomerController implements Serializable{
    private List<Customer> customerList = new ArrayList<Customer>();
    private SelectItem[] customerGroupSelect;
    private Customer customer = new Customer();
    private Customer selectedCustomer = new Customer();
    private Boolean customerIsAdd = true;
    private String statusButton = "Disabled";
    private List<Job> jobList = new ArrayList<Job>();
    private String customerSearch = "";
    
     //<editor-fold  desc="Customer Button Actions" > 
    public void customerAdd() {
        try {
            if (!validateCustomer()){
                return;
            }
            customer.setStatus('1');
            SirsDataSource.dataSource().customerCreate(customer);
            JSFUtility.infoMessage("Customer added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New Customer");
        } 
    }
 
    public void customerUpdate() {
        try {
            SirsDataSource.dataSource().customerUpdate(customer);
            customerReset();
            JSFUtility.infoMessage("Customer Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update Customer");
        }
    }
    
    private boolean validateCustomer() {
        if (null == customer.getCustomerName() || "".equals(customer.getCustomerName())) {
            JSFUtility.errorMessage("Customer Name required.");
            return false;
        }
//        if (null == customer.getPhoneNumber() || "".equals(customer.getPhoneNumber())) {
//            JSFUtility.errorMessage("Customer Mobile Phone required.");
//            return false;
//        }

        return true;
    }
    
    public void handleCustomerSelection(){   
        customer = selectedCustomer;
        if (customer.getStatus() == '1'){ 
            statusButton = "Disable";
        }else{
            statusButton = "Enable";
        }
        customerIsAdd = false;
    }

    public String customerReset() {
        customer = new Customer();
        customerIsAdd = true;
        return "";
    }

    public String customerDelete() {
        try {
            String msg = "";
            if (customer.getStatus() == '1'){
                customer.setStatus('0');
                msg = "Disabled";
            }else{
                customer.setStatus('1');
                msg = "Enabled";
            }
            
            SirsDataSource.dataSource().customerUpdate(customer);
            customerReset();
            JSFUtility.infoMessage("Customer " + msg+" Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Disabled Customer");
        }
        return "";
    }
    
    public void TransactionHistory(){
        
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
    //</editor-fold >
    
    public void searchCustomers(){
        customerList = getCustomerList();
    }

    public List<Customer> getCustomerList() {
        customerList = SirsDataSource.dataSource().customersSearch(customerSearch, true);
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getCustomerIsAdd() {
        return customerIsAdd;
    }

    public void setCustomerIsAdd(Boolean customerIsAdd) {
        this.customerIsAdd = customerIsAdd;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public String getStatusButton() {
        return statusButton;
    }

    public void setStatusButton(String statusButton) {  
        this.statusButton = statusButton;
    }

    public List<Job> getJobList() { 
        jobList = SirsDataSource.dataSource().jobFindByAttribute("billTo", String.valueOf(selectedCustomer.getCustomerId()), ApplicationConstant.STRING);
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public String getCustomerSearch() {
        return customerSearch;
    }

    public void setCustomerSearch(String customerSearch) {
        this.customerSearch = customerSearch;
    }
}