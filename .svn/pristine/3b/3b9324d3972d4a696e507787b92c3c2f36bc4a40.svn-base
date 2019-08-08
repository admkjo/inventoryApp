/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.ejb.entities.Debtor;
import com.sabonay.sirs.ejb.entities.custom.MonthlyPaymentADT;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.LoginUser;
import com.sabonay.sirs.web.common.reports.SirsReportManager;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ReportController implements Serializable{
    private List<Debtor> debtorList = new ArrayList<Debtor>();
    private double totalAmountDue;
    private double totalDiscount;
    private double totalAmountPaid;
    private double balanceTotal;
    private String datePattern = "dd-MMM-yyyy";
    private String datePatternP = "yyyy-MM-dd";
    private Date fromDate;
    private Date toDate;

    private AdminUser getLoginUser() {
        try {
            return (AdminUser) ((LoginUser) JSFUtility.getSessionValue(ApplicationConstant.ADMIN_USER)).getUserLogin();
        } catch (Exception e) {
            return null; 
        } 
    }
    public void printDebtorList(){
        //Print Sales Invoice / POS Receipt
        SirsReportManager.getInstance().loadSirsDefaultParameters();
        SirsReportManager.getInstance().addToDefaultParameters("reportName", "Debtors' Listing");
        
        determineTotal();
        
        SirsReportManager.getInstance().addToDefaultParameters("totalAmountDue", totalAmountDue);
        SirsReportManager.getInstance().addToDefaultParameters("totalDiscount", totalDiscount);
        SirsReportManager.getInstance().addToDefaultParameters("totalAmountPaid", totalAmountPaid);
        SirsReportManager.getInstance().addToDefaultParameters("totalBalanceOwing", balanceTotal);
//
        SirsReportManager.getInstance().addToDefaultParameters("user", getLoginUser().getUsername());

        SirsReportManager.getInstance().showReport(getDebtorList(), getClass().getResourceAsStream(SirsReportManager.DEBTORS_LIST));
    }
    
    public void printMonthlyReport(){
        SirsReportManager.getInstance().loadSirsDefaultParameters();
        SirsReportManager.getInstance().addToDefaultParameters("reportName", "Monthly Transactions Report from " + (new SimpleDateFormat(datePattern).format(fromDate)) + " to " +  (new SimpleDateFormat(datePattern).format(toDate)));

//        SirsReportManager.getInstance().addToDefaultParameters("totalAmountDue", totalAmountDue);
//        SirsReportManager.getInstance().addToDefaultParameters("totalDiscount", totalDiscount);
//        SirsReportManager.getInstance().addToDefaultParameters("totalAmountPaid", totalAmountPaid);
//        SirsReportManager.getInstance().addToDefaultParameters("totalBalanceOwing", balanceTotal);
//
        SirsReportManager.getInstance().addToDefaultParameters("user", getLoginUser().getUsername());
        
        Calendar c = Calendar.getInstance(); 
        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        toDate = c.getTime();
        List<MonthlyPaymentADT> list = SirsDataSource.dataSource().customMonthlyReport(new SimpleDateFormat(datePatternP).format(fromDate), new SimpleDateFormat(datePatternP).format(toDate));        
        //System.out.println("list..." + list); 
        SirsReportManager.getInstance().showReport(list, getClass().getResourceAsStream(SirsReportManager.MONTHLY_TRANSACTION_LIST));
    }
    
    private void determineTotal(){
        for (Debtor debtor : getDebtorList()) {
            totalAmountDue+=debtor.getJob().getAmountDue();
            totalDiscount+=debtor.getJob().getDiscount();
            totalAmountPaid+=debtor.getJob().getAmountPaid();
            balanceTotal+=debtor.getBalance();
        }
    }
    
    public List<Debtor> getDebtorList() { 
        debtorList = SirsDataSource.dataSource().debtorGetAll(false, true);
        return debtorList;
    }

    public void setDebtorList(List<Debtor> debtorList) { 
        this.debtorList = debtorList;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}