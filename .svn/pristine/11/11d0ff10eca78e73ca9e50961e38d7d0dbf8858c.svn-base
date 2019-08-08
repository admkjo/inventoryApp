/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Debtor;
import com.sabonay.sirs.ejb.entities.Job;
import com.sabonay.sirs.ejb.entities.JobDetail;
import com.sabonay.sirs.ejb.entities.Payment;
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
public class DebtorController implements Serializable {

    private List<Debtor> debtorList = new ArrayList<Debtor>();
    private Debtor debtor = new Debtor();
    private List<JobDetail> jobListDetail = new ArrayList<JobDetail>();
    private List<Payment> paymentListDetail = new ArrayList<Payment>();
    private Debtor selectedDebtor = new Debtor();
    private Boolean debtorIsAdd = true;

    //<editor-fold  desc="Debtor Button Actions" >  
    public void debtorAdd() {
        try {
            SirsDataSource.dataSource().debtorCreate(debtor);
            JSFUtility.infoMessage("Debtor added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New Debtor");
        }
    }

    public void debtorUpdate() {
        try {
            SirsDataSource.dataSource().debtorUpdate(debtor);
            debtorReset();
            JSFUtility.infoMessage("Debtor Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update Debtor");
        }
    }

//    private boolean validateDebtor() {
//        if (null == debtor.getDebtorName() || "".equals(debtor.getDebtorName())) {
//            JSFUtility.errorMessage("Debtor Name required.");
//            return false;
//        }
//        if (null == debtor.getDebtorCost() || debtor.getDebtorCost() == 0.0) {
//            JSFUtility.errorMessage("Debtor Cost required.");
//            return false;
//        }
//
//        return true;
//    }
    public void handleDebtorSelection() {
        debtor = selectedDebtor;
        debtorIsAdd = false;
    }

    public String debtorReset() {
        debtor = new Debtor();
        debtorIsAdd = true;
        return "";
    }

    public String debtorDelete() {
        try {
            SirsDataSource.dataSource().debtorDelete(debtor);
            debtorReset();
            JSFUtility.infoMessage("Debtor Deleted Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Delete Debtor");
        }
        return "";
    }
    //</editor-fold >

    public List<Debtor> getDebtorList() {
        debtorList = SirsDataSource.dataSource().debtorGetAll(false, true);
        return debtorList;
    }

    public double getTotalBalance() {
        double a = 0.0;
        for (Debtor d : debtorList) {
            a += d.getBalance();
        }
        return a;
    }

    public void setDebtorList(List<Debtor> debtorList) {
        this.debtorList = debtorList;
    }

    public Debtor getDebtor() {
        return debtor;
    }

    public void setDebtor(Debtor debtor) {
        this.debtor = debtor;
    }

    public Boolean getDebtorIsAdd() {
        return debtorIsAdd;
    }

    public void setDebtorIsAdd(Boolean debtorIsAdd) {
        this.debtorIsAdd = debtorIsAdd;
    }

    public List<Payment> getPaymentListDetail() {
        paymentListDetail = SirsDataSource.dataSource().paymentDetailByJob(selectedDebtor.getJob());
        return paymentListDetail;
    }

    public void setPaymentListDetail(List<Payment> paymentListDetail) {
        this.paymentListDetail = paymentListDetail;
    }

    public Debtor getSelectedDebtor() {
        return selectedDebtor;
    }

    public void setSelectedDebtor(Debtor selectedDebtor) {
        this.selectedDebtor = selectedDebtor;
    }

    public List<JobDetail> getJobListDetail() {
        jobListDetail = SirsDataSource.dataSource().jobDetailByJob(selectedDebtor.getJob());
        return jobListDetail;
    }

    public void setJobListDetail(List<JobDetail> jobListDetail) {
        this.jobListDetail = jobListDetail;
    }

}
