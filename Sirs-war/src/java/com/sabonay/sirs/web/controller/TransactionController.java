/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.ejb.entities.Customer;
import com.sabonay.sirs.ejb.entities.CustomerMaterialGroup;
import com.sabonay.sirs.ejb.entities.Debtor;
import com.sabonay.sirs.ejb.entities.Job;
import com.sabonay.sirs.ejb.entities.JobDetail;
import com.sabonay.sirs.ejb.entities.Material;
import com.sabonay.sirs.ejb.entities.Payment;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.LoginUser;
import com.sabonay.sirs.web.common.reports.SirsReportManager;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import com.sabonay.sirs.web.common.utilities.NumberFormatter;
import com.sabonay.sirs.web.wrapper.StockItem;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author emma
 *
 */
@ManagedBean
@SessionScoped
public class TransactionController implements Serializable {

    private SelectItem[] materialSelect;
    private Material material = null;
    private List<StockItem> stockItemList = new ArrayList<StockItem>();
    private List<Job> jobTodayList = new ArrayList<Job>();
    private List<Job> jobGeneralList = new ArrayList<Job>();
    private List<JobDetail> jobDetailList = new ArrayList<JobDetail>();
    private StockItem stockItem = null;
    private Integer itemCount = 0;
    boolean stockExists = false;
    private Job job = new Job();
    private Job selectedJob = new Job();
    private Payment payment = new Payment();
    private String customerType = ApplicationConstant.NEW_CUSTOMER;
    private String searchCriteria = null;
    private String searchText = null;
    private Customer customer = new Customer();
    private Date transactionDate = new Date();
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfReportDate = new SimpleDateFormat("dd-MMM-yyyy");
    private Double newAmountDue = 0.0;
    private Boolean jobIsAdd = true;
    private Boolean proceedJobPersistence = true;
    private List<Debtor> debtorList = new ArrayList<Debtor>();
    private Boolean debtorFoundInSearch = false;
    private Boolean recallDebitPayment = false;
    private Debtor selectedDebtor = null;
    private Payment selectedPayment = new Payment();
    private List<Payment> paymentTodayList = new ArrayList<Payment>();
    private List<Payment> paymentGeneralList = new ArrayList<Payment>();
    private String jobTransactionView = "JOB";
    private Double jobAmountPaid = 0.0;
    private String customerSearch = "";

    public AdminUser getLoginUser() {
        try {
            return (AdminUser) ((LoginUser) JSFUtility.getSessionValue(ApplicationConstant.ADMIN_USER)).getUserLogin();
        } catch (Exception e) {
            return null;
        }
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

    public void handleStockChange() {
        if (null != material) {
            itemCount = stockItemList.size();

            stockItem = new StockItem();
            stockItem.setCount(stockItemList.size() + 1);
            stockItem.setDescription(material.getMaterialName());
            stockItem.setMaterialType(material.getMaterialType());
            stockItem.setFileName("");
            stockItem.setQuantity(1);

            if (null != customer) {
                if (null != customer.getCustomerGroup()) {
                    CustomerMaterialGroup cmg = SirsDataSource.dataSource().customerMaterialGroupFindByMaterial(customer.getCustomerGroup(), material.getMaterialId());
                    if (null != cmg) {
                        stockItem.setMaterialCost(cmg.getNewPrice());
                        stockItem.setLineTotal(cmg.getNewPrice() * 1);
                    } else {
                        stockItem.setMaterialCost(material.getMaterialCost());
                        stockItem.setLineTotal(material.getMaterialCost() * 1);
                    }

                } else {
                    stockItem.setMaterialCost(material.getMaterialCost());
                    stockItem.setLineTotal(material.getMaterialCost() * 1);
                }
            } else {
                stockItem.setMaterialCost(material.getMaterialCost());
                stockItem.setLineTotal(material.getMaterialCost() * 1);
            }



            stockItem.setMaterial(material);

            stockItemList.add(stockItem);
        }
    }

    public double sumUpJobSales() {
        double s = 0.0;
        for (Iterator<StockItem> it = stockItemList.iterator(); it.hasNext();) {
            StockItem stockitem = it.next();
            s += stockitem.getLineTotal();
        }
        job.setAmountDue(s);

        return s;
    }

    public String getInvoiceNo() {
        return ApplicationConstant.ComputeInvoiceNo();
    }

    public void savePrint() throws SQLException {
//        try {
        //Persist Job


        double balance = 0.0;
        if (ApplicationConstant.ORDER.equals(job.getJobType())) {
            if (!validateTransaction()) {
                return;
            }

            job.setJobType(ApplicationConstant.ORDER);

            balance = NumberFormatter.formatDoubleNum(getBalance(), 1);
            if (!proceedJobPersistence) {
                proceedJobPersistence = true;
                JSFUtility.errorMessage("Please save this Client since Transaction is on Credit.");
                return;
            }

            if (balance == 0.0) {
                job.setAmountPaid(NumberFormatter.formatDoubleNum(payment.getAmountPaid() - payment.getChangeGiven(), 1));
            } else {
                job.setAmountPaid(NumberFormatter.formatDoubleNum(payment.getAmountPaid(), 1));
            }
        } else {
            if (!validateQuotationTransaction()) {
                return;
            }
            job.setJobType(ApplicationConstant.QUOTATION);
            job.setAmountPaid(0.0);
        }
        Date jobDate = DateTimeUtils.parseDate(sdfDate.format(transactionDate) + " " + sdfTime.format(new Date()), "yyyy-MM-dd hh:mm:ss");
        //Date jobDate = DateTimeUtils.stringToDate(sdfDate.format(transactionDate) + " " + sdfTime.format(new Date()));


        job.setJobDate(jobDate);
        job.setInvoiceNo(ApplicationConstant.ComputeInvoiceNo());

        job.setDiscount(0.0);
        job.setAmountDue(NumberFormatter.formatDoubleNum(job.getAmountDue(), 1));


        job.setCreatedBy(getLoginUser().getUsername());
        if (null == customer || null == customer.getCustomerId()) {
            job.setBillTo(CreateClient().toJSONString());
        } else {
            job.setBillTo(String.valueOf(customer.getCustomerId()));
        }

        SirsDataSource.dataSource().jobCreate(job);
        int newinvoice = Integer.parseInt(ApplicationConstant.ComputeInvoiceNo()) + 1;
        ApplicationConstant.SaveNewInvoice(String.valueOf(newinvoice));

        //Create Debtor
        if (balance > 0) {
            Debtor debtor = new Debtor();
            debtor.setJob(job);
            debtor.setCustomer(customer);
            debtor.setBalance(balance); //Initial Debit Balance
            debtor.setDueDate(jobDate);

            SirsDataSource.dataSource().debtorCreate(debtor);
        }

        //Persist Payment only for Proforma Invoice/Order
        if (ApplicationConstant.ORDER.equals(job.getJobType())) {
            payment.setJob(job);
            payment.setPaymentReference(getPaymentReference());
            payment.setBalance(balance);
            payment.setChangeGiven(NumberFormatter.formatDoubleNum(payment.getChangeGiven(), 1));
            payment.setPaymentDate(jobDate);
            payment.setCollectedBy(getLoginUser().getUsername());



            SirsDataSource.dataSource().paymentCreate(payment);
        }


        //Persist Job Details
        for (StockItem stockitem : stockItemList) {
            JobDetail jobDetail = new JobDetail();
            jobDetail.setJobId(job);
            jobDetail.setMaterial(stockitem.getMaterial());
            jobDetail.setFileName(stockitem.getFileName());
            jobDetail.setMaterialPrice(stockitem.getMaterialCost());
            jobDetail.setWidth(stockitem.getMaterialWidth());
            jobDetail.setHeight(stockitem.getMaterialHeight());
            jobDetail.setMeasure(stockitem.getUnitOfMeasure());
            jobDetail.setQuantity(stockitem.getQuantity());
            jobDetail.setSubtotal(NumberFormatter.formatDoubleNum(stockitem.getLineTotal(), 1));

            SirsDataSource.dataSource().jobDetailCreate(jobDetail);

        }
        //JSFUtility.infoMessage("Please wait while printer is being initialized...");
        printInvoice(balance); //Print Invoice 
        cancelTransaction(); //Reset form
        JSFUtility.infoMessage("Transaction Completed. Sending invoice to printer...");
//        } catch (Exception e) {
//            try {
//                Double amt = Double.parseDouble(payment.getAmountPaid().toString());
//            } catch (Exception ex) {
//                JSFUtility.errorMessage("Please enter amount paid for this transaction.");
//                return;
//            }
//            JSFUtility.errorMessage("Could not complete transaction. Contact Administrator");
//        }
    }

    public void saveOnly() {
        try {
            //Persist Job
            if (!validateTransaction()) {
                return;
            }

            Date jobDate = DateTimeUtils.parseDate(sdfDate.format(transactionDate) + " " + sdfTime.format(new Date()), "yyyy-MM-dd hh:mm:ss");
            double balance = NumberFormatter.formatDoubleNum(getBalance(), 1);
            if (!proceedJobPersistence) {
                JSFUtility.errorMessage("Client must be a customer before he can be allowed credit.");
                return;
            }


            job.setJobDate(jobDate);
            job.setInvoiceNo(ApplicationConstant.ComputeInvoiceNo());
            //job.setJobType(ApplicationConstant.ORDER);
            job.setDiscount(0.0);
            job.setAmountDue(NumberFormatter.formatDoubleNum(job.getAmountDue(), 1));
            if (balance == 0.0) {
                job.setAmountPaid(NumberFormatter.formatDoubleNum(payment.getAmountPaid() - payment.getChangeGiven(), 1));
            } else {
                job.setAmountPaid(NumberFormatter.formatDoubleNum(payment.getAmountPaid(), 1));
            }
            job.setCreatedBy(getLoginUser().getUsername());
            if (null == customer || null == customer.getCustomerId()) {
                job.setBillTo(CreateClient().toJSONString());
            } else {
                job.setBillTo(String.valueOf(customer.getCustomerId()));
            }

            SirsDataSource.dataSource().jobCreate(job);
            int newinvoice = Integer.parseInt(ApplicationConstant.ComputeInvoiceNo()) + 1;
            ApplicationConstant.SaveNewInvoice(String.valueOf(newinvoice));

            //Create Debtor
            if (balance > 0) {
                Debtor debtor = new Debtor();
                debtor.setJob(job);
                debtor.setCustomer(customer);
                debtor.setBalance(balance); //Initial Debit Balance
                debtor.setDueDate(jobDate);

                SirsDataSource.dataSource().debtorCreate(debtor);
            }


            //Persist Payment
            payment.setJob(job);
            payment.setPaymentReference(getPaymentReference());
            payment.setBalance(balance);
            payment.setPaymentDate(jobDate);
            payment.setChangeGiven(NumberFormatter.formatDoubleNum(payment.getChangeGiven(), 1));
            payment.setCollectedBy(getLoginUser().getUsername());

            SirsDataSource.dataSource().paymentCreate(payment);

            //Persist Job Details
            for (StockItem stockitem : stockItemList) {
                JobDetail jobDetail = new JobDetail();
                jobDetail.setJobId(job);
                jobDetail.setMaterial(stockitem.getMaterial());
                jobDetail.setFileName(stockitem.getFileName());
                jobDetail.setMaterialPrice(stockitem.getMaterial().getMaterialCost());
                jobDetail.setWidth(stockitem.getMaterialWidth());
                jobDetail.setHeight(stockitem.getMaterialHeight());
                jobDetail.setMeasure(stockitem.getUnitOfMeasure());
                jobDetail.setQuantity(stockitem.getQuantity());
                jobDetail.setSubtotal(NumberFormatter.formatDoubleNum(stockitem.getLineTotal(), 1));

                SirsDataSource.dataSource().jobDetailCreate(jobDetail);

            }
            cancelTransaction(); //Reset form
            JSFUtility.infoMessage("Transaction Completed and Saved Successfully.");
        } catch (Exception e) {
            try {
                Double amt = Double.parseDouble(payment.getAmountPaid().toString());
            } catch (Exception ex) {
                JSFUtility.errorMessage("Please enter amount paid for this transaction.");
                return;
            }
            JSFUtility.errorMessage("Could not complete transaction. Contact Administrator");
        }
    }

    public void debtPaymentSavePrint() {
        try {
            if (!validateBalancePayment() && ApplicationConstant.ORDER.equals(job.getJobType())) {
                return;
            }
            if(ApplicationConstant.ORDER.equals(job.getJobType())){
            Date paydate = DateTimeUtils.parseDate(sdfDate.format(transactionDate) + " " + sdfTime.format(new Date()), "yyyy-MM-dd hh:mm:ss");
            //Persist Payment
            job = selectedJob;
            job.setJobDate(paydate);
            payment.setJob(selectedJob);
            //payment.setPaymentReference(getPaymentReference());
            if (selectedDebtor.getBalance() > payment.getAmountPaid()) {
                payment.setBalance(NumberFormatter.formatDoubleNum(selectedDebtor.getBalance() - payment.getAmountPaid(), 1));
                payment.setChangeGiven(0.0);
                payment.setPaymentReference("Part Payment of Transaction ON CREDIT");
//                double bal = Double.parseDouble(String.valueOf(payment.getBalance()).substring(0, 4));
//                System.out.println("bal..." + bal);
                if (payment.getBalance() == 0.0) {
                    payment.setBalance(0.0);
                    payment.setPaymentReference("Full Payment of Transaction ON CREDIT");
                    selectedDebtor.setBalance(0.0);
                } else {
                    payment.setPaymentReference("Part Payment of Transaction ON CREDIT");
                    selectedDebtor.setBalance(payment.getBalance());
                }
            } else {
                payment.setBalance(0.0);
                payment.setPaymentReference("Full Payment of Transaction ON CREDIT");
                selectedDebtor.setBalance(payment.getBalance());
            }
            payment.setPaymentDate(paydate);
            payment.setCollectedBy(getLoginUser().getUsername());

            selectedJob.setAmountPaid((jobAmountPaid + payment.getAmountPaid()) - payment.getChangeGiven());

            SirsDataSource.dataSource().jobUpdate(selectedJob);

            SirsDataSource.dataSource().paymentCreate(payment);
            SirsDataSource.dataSource().debtorUpdate(selectedDebtor);

            StockListFromJobList();   
            printInvoice(payment.getBalance());
            cancelTransaction(); //Reset form
            JSFUtility.infoMessage("Recalled Transaction Paid Off Successfully. Sending invoice to printer...");
            }
            
            
            else if(ApplicationConstant.QUOTATION.equals(job.getJobType())){
             Date paydate = DateTimeUtils.parseDate(sdfDate.format(transactionDate) + " " + sdfTime.format(new Date()), "yyyy-MM-dd hh:mm:ss");
            //Persist Payment
            job = selectedJob;
            job.setJobDate(paydate);
            payment.setJob(selectedJob);
            //payment.setPaymentReference(getPaymentReference());
            if (selectedDebtor.getBalance() > payment.getAmountPaid()) {
                payment.setBalance(NumberFormatter.formatDoubleNum(selectedDebtor.getBalance() - payment.getAmountPaid(), 1));
                payment.setChangeGiven(0.0);
                payment.setPaymentReference("Part Payment of Transaction ON CREDIT");
//                double bal = Double.parseDouble(String.valueOf(payment.getBalance()).substring(0, 4));
//                System.out.println("bal..." + bal);
                if (payment.getBalance() == 0.0) {
                    payment.setBalance(0.0);
                    payment.setPaymentReference("Full Payment of Transaction ON CREDIT");
                    selectedDebtor.setBalance(0.0);
                } else {
                    payment.setPaymentReference("Part Payment of Transaction ON CREDIT");
                    selectedDebtor.setBalance(payment.getBalance());
                }
            } else {
                payment.setBalance(0.0);
                payment.setPaymentReference("Full Payment of Transaction ON CREDIT");
                selectedDebtor.setBalance(payment.getBalance());
            }
            payment.setPaymentDate(paydate);
            payment.setCollectedBy(getLoginUser().getUsername());

            selectedJob.setAmountPaid((jobAmountPaid + payment.getAmountPaid()) - payment.getChangeGiven());

            SirsDataSource.dataSource().jobUpdate(selectedJob);

//            SirsDataSource.dataSource().paymentCreate(payment);
            SirsDataSource.dataSource().debtorUpdate(selectedDebtor);

            StockListFromJobList();
            printInvoice(payment.getBalance());
            cancelTransaction(); //Reset form
            JSFUtility.infoMessage("Recalled Transaction Paid Off Successfully. Sending invoice to printer...");
               
            }
            } catch (Exception e) {
            JSFUtility.errorMessage("Could not complete transaction. Contact Administrator");
        }
    }

    public void debtPaymentSaveOnly() {
        try {
            if (!validateBalancePayment()  && ApplicationConstant.ORDER.equals(job.getJobType())) {
                return;
            }
            Date paydate = DateTimeUtils.parseDate(sdfDate.format(transactionDate) + " " + sdfTime.format(new Date()), "yyyy-MM-dd hh:mm:ss");
            //Persist Payment
            payment.setJob(selectedJob);
            //payment.setPaymentReference(getPaymentReference());
            if (selectedDebtor.getBalance() > payment.getAmountPaid()) {
                payment.setBalance(NumberFormatter.formatDoubleNum(selectedDebtor.getBalance() - payment.getAmountPaid(), 1));
                payment.setChangeGiven(0.0);
                if (payment.getBalance() == 0.0) {
                    payment.setBalance(0.0);
                    payment.setPaymentReference("Full Payment of Transaction ON CREDIT");
                    selectedDebtor.setBalance(0.0);
                } else {
                    payment.setPaymentReference("Part Payment of Transaction ON CREDIT");
                    selectedDebtor.setBalance(payment.getBalance());
                }
            } else {
                payment.setBalance(0.0);
                payment.setPaymentReference("Full Payment of Transaction ON CREDIT");
                selectedDebtor.setBalance(payment.getBalance());
            }
            payment.setPaymentDate(paydate);
            payment.setCollectedBy(getLoginUser().getUsername());

            selectedJob.setAmountPaid((jobAmountPaid + payment.getAmountPaid()) - payment.getChangeGiven());

            SirsDataSource.dataSource().jobUpdate(selectedJob);
            SirsDataSource.dataSource().paymentCreate(payment);
            SirsDataSource.dataSource().debtorUpdate(selectedDebtor);

            cancelTransaction(); //Reset form
            JSFUtility.infoMessage("Recalled Transaction Paid Off Successfully.");
        } catch (Exception e) {
            JSFUtility.errorMessage("Could not complete transaction. Contact Administrator");
        }
    }

    private boolean validateBalancePayment() {
        if (payment.getAmountPaid() == null) {
            JSFUtility.errorMessage("Please enter amount paid for this transaction.");
            return false;
//        } else if (payment.getAmountPaid() == 0.0) {
//            JSFUtility.errorMessage("Please enter amount paid for this transaction.");
//            return false; 
        }


        return true;
    }

    private boolean validateQuotationTransaction() {
        if (null == stockItemList || stockItemList.isEmpty()) {
            JSFUtility.errorMessage("There are no item(s) to be saved for this transaction.");
            return false;
        } else if (job.getAmountDue() == 0.0) {
            JSFUtility.errorMessage("This transaction has no value.");
            return false;
        }

        return true;
    }

    private boolean validateTransaction() {
        if (null == stockItemList || stockItemList.isEmpty()) {
            JSFUtility.errorMessage("There are no item(s) to be saved for this transaction.");
            return false;
        } else if (null == customer.getCustomerName() || "".equals(customer.getCustomerName())) {
            JSFUtility.errorMessage("Customer Name required.");
            return false;
        } else if (job.getAmountDue() == 0.0) {
            JSFUtility.errorMessage("This transaction has no value.");
            return false;
        } else if (payment.getAmountPaid() == null) {
            JSFUtility.errorMessage("Please enter amount paid for this transaction.");
            return false;
        }

        return true;
    }

    private String getPaymentReference() {
        if (job.getAmountDue() > payment.getAmountPaid()) {
            return "Job ON CREDIT with part payment.";
        } else if (payment.getAmountPaid() == 0.0) {
            return "Job ON CREDIT with no initial payment.";
        } else if (payment.getAmountPaid() >= job.getAmountDue()) {
            return "Full Payment of Job";
        }
        return null;
    }

    private double getBalance() {
        try {
//            System.out.println("payment.getAmountPaid()..." + payment.getAmountPaid());
//            System.out.println("............" + (job.getAmountDue() - payment.getAmountPaid()));
            if (NumberFormatter.formatDoubleNum(job.getAmountDue(), 1) > NumberFormatter.formatDoubleNum(payment.getAmountPaid(), 1)) {
                //There is going to be a debtor
                payment.setChangeGiven(0.0); //There is no change to give

                //Client must be a customer before he can owe a debt            
                if (null == customer || null == customer.getCustomerId()) { //Raise an error message if client is not a customer
                    proceedJobPersistence = false;
                    return 0.0;
                } else {
                    proceedJobPersistence = true;
                }

                return NumberFormatter.formatDoubleNum(job.getAmountDue() - payment.getAmountPaid(), 1);
            }
        } catch (Exception e) {
            System.out.println("TransactionController:getBalance():Error... " + e);
        }
        return 0.0;
    }

    private void StockListFromJobList() {
        int cnt = 0;
        stockItemList = new ArrayList<StockItem>();
        for (JobDetail jd : jobDetailList) {
            StockItem si = new StockItem();
            si.setCount(++cnt);
            si.setDescription(jd.getFileName());
            si.setUnitOfMeasure(jd.getMeasure());
            si.setQuantity(jd.getQuantity());
            si.setMaterialType(jd.getMaterial().getMaterialType());
            si.setFileName(jd.getFileName());
            si.setMaterialWidth(jd.getWidth());
            si.setMaterialHeight(jd.getHeight());
            si.setMaterialCost(NumberFormatter.formatDoubleNum(jd.getMaterialPrice(), 1));
            si.setMaterial(jd.getMaterial());
            si.setSubTotal(NumberFormatter.formatDoubleNum(jd.getSubtotal(), 1));

            stockItemList.add(si);
        }
    }

    private void printInvoice(double balance) {
        //Print Sales Invoice / POS Receipt
        SirsReportManager.getInstance().loadSirsDefaultParameters();
//        SirsReportManager.getInstance().addToDefaultParameters("reportName", "Sales Invoice");

        SirsReportManager.getInstance().addToDefaultParameters("customerName", customer == null ? "Cash Customer" : customer.getCustomerName());
        SirsReportManager.getInstance().addToDefaultParameters("customerAddress", (customer == null || "".equals(customer.getPostalAddress())) ? "None" : customer.getPostalAddress());

        SirsReportManager.getInstance().addToDefaultParameters("invoiceNo", job.getInvoiceNo());
        SirsReportManager.getInstance().addToDefaultParameters("receiptDate", job.getJobDate());
        SirsReportManager.getInstance().addToDefaultParameters("tenderType", paymentModeLookup(payment.getPaymentMode()));

        SirsReportManager.getInstance().addToDefaultParameters("subTotal", job.getAmountDue());
        SirsReportManager.getInstance().addToDefaultParameters("discount", job.getDiscount());
        SirsReportManager.getInstance().addToDefaultParameters("netTotal", job.getAmountDue() - job.getDiscount());
        SirsReportManager.getInstance().addToDefaultParameters("payment", payment.getAmountPaid());
        SirsReportManager.getInstance().addToDefaultParameters("change", payment.getChangeGiven());
        SirsReportManager.getInstance().addToDefaultParameters("balanceOwing", payment.getBalance());
        SirsReportManager.getInstance().addToDefaultParameters("paymentRef", payment.getPaymentReference());

        SirsReportManager.getInstance().addToDefaultParameters("cashier", job.getCreatedBy());

        if (ApplicationConstant.ORDER.equals(job.getJobType())) {  
            if (balance > 0) {
               
                System.out.println("got herr bal > 0");
                SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_CREDIT));
                SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_CC_CREDIT));
                SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_CREDIT_TRIPLICATE));
            } else {
                              System.out.println("got herr bal < 0");
  SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE));
                SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_CC));
                SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_TRIPLICATE));
            }
        }else{
                          System.out.println("got herr else");
  SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_CREDIT));
        }



        //SirsReportManager.getInstance().printReportDirectToPrinter(transactionDetailList, getClass().getResourceAsStream(SirsReportManager.POS_RECEIPT));
    }

    private void rePrintInvoice() throws ParseException {
        //Print Sales Invoice / POS Receipt
        SirsReportManager.getInstance().loadSirsDefaultParameters();
//        SirsReportManager.getInstance().addToDefaultParameters("reportName", "Sales Invoice");

        SirsReportManager.getInstance().addToDefaultParameters("customerName", selectedJob.getCustomer() == null ? "Cash Customer" : selectedJob.getCustomer().getCustomerName());
        SirsReportManager.getInstance().addToDefaultParameters("customerAddress", selectedJob.getCustomer() == null ? "None" : selectedJob.getCustomer().getPostalAddress());

        SirsReportManager.getInstance().addToDefaultParameters("invoiceNo", selectedJob.getInvoiceNo());
        SirsReportManager.getInstance().addToDefaultParameters("receiptDate", selectedJob.getJobDate());
        SirsReportManager.getInstance().addToDefaultParameters("tenderType", paymentModeLookup(payment.getPaymentMode()));

        SirsReportManager.getInstance().addToDefaultParameters("subTotal", selectedJob.getAmountDue());
        SirsReportManager.getInstance().addToDefaultParameters("discount", selectedJob.getDiscount());
        SirsReportManager.getInstance().addToDefaultParameters("netTotal", selectedJob.getAmountDue() - selectedJob.getDiscount());
        SirsReportManager.getInstance().addToDefaultParameters("payment", payment.getAmountPaid());
        SirsReportManager.getInstance().addToDefaultParameters("change", payment.getChangeGiven());
        SirsReportManager.getInstance().addToDefaultParameters("balanceOwing", payment.getBalance());
        SirsReportManager.getInstance().addToDefaultParameters("paymentRef", payment.getPaymentReference());

        SirsReportManager.getInstance().addToDefaultParameters("cashier", selectedJob.getCreatedBy());

        SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE));
        SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_CC));
        SirsReportManager.getInstance().printReportDirectToPrinter(stockItemList, getClass().getResourceAsStream(SirsReportManager.POS_SALES_INVOICE_TRIPLICATE));
        //SirsReportManager.getInstance().printReportDirectToPrinter(transactionDetailList, getClass().getResourceAsStream(SirsReportManager.POS_RECEIPT));
    }

    public void paymentReportPrint() {
        //Print Sales Invoice / POS Receipt
        SirsReportManager.getInstance().loadSirsDefaultParameters();
        SirsReportManager.getInstance().addToDefaultParameters("reportName", "Daily Payment Report for " + sdfReportDate.format(transactionDate));

        //SirsReportManager.getInstance().addToDefaultParameters("totalAmountDue", totalAmountDue()); 
        SirsReportManager.getInstance().addToDefaultParameters("totalBalance", totalPaymentBalance());
        SirsReportManager.getInstance().addToDefaultParameters("totalAmountPaid", totalPaymentAmountPaid());
//
        SirsReportManager.getInstance().addToDefaultParameters("user", getLoginUser().getUsername());

        SirsReportManager.getInstance().printReportDirectToPrinter(paymentTodayList, getClass().getResourceAsStream(SirsReportManager.POS_DAILY_PAYMENT_LIST));
    }

    public void jobReportPrint() {
        //Print Sales Invoice / POS Receipt
        SirsReportManager.getInstance().loadSirsDefaultParameters();
        SirsReportManager.getInstance().addToDefaultParameters("reportName", "Daily Jobs Report for " + sdfReportDate.format(transactionDate));

        SirsReportManager.getInstance().addToDefaultParameters("totalAmountDue", totalAmountDue());
        SirsReportManager.getInstance().addToDefaultParameters("totalDiscount", totalDiscount());
        SirsReportManager.getInstance().addToDefaultParameters("totalAmountPaid", totalPaymentAmountPaid());
//
        SirsReportManager.getInstance().addToDefaultParameters("user", getLoginUser().getUsername());

        SirsReportManager.getInstance().printReportDirectToPrinter(jobTodayList, getClass().getResourceAsStream(SirsReportManager.POS_DAILY_JOBS_LIST));
    }

    private JSONObject CreateClient() {
        JSONObject obj = new JSONObject();
        obj.put("name", customer.getCustomerName());
        obj.put("phone", customer.getPhoneNumber());
        obj.put("address", customer.getPostalAddress());
        obj.put("email", customer.getEmail());
        return obj;
    }

    private String paymentModeLookup(String pmode) {
        if ("CASH".equals(pmode)) {
            return "Cash Payment";
        }
        if ("MOMO".equals(pmode)) {
            return "Mobile Money Payment";
        }
        if ("BAAC".equals(pmode)) {
            return "Bank Transfer";
        }
        if ("CHEQ".equals(pmode)) {
            return "Cheque Payment";
        }
        return "N/A";
    }

    public void cancelTransaction() {
        try {
            stockItemList = new ArrayList<StockItem>();
            job = null;
            customerType = ApplicationConstant.NEW_CUSTOMER;
            customer = new Customer();
            stockExists = false;
            itemCount = 0;
            searchText = null;
            searchCriteria = null;
            material = null;
            proceedJobPersistence = true;
            payment = new Payment();
            debtorFoundInSearch = false;
            recallDebitPayment = false;
            selectedDebtor = null;
            jobTransactionView = "JOB";
            transactionDate = new Date();

            job = new Job();


        } catch (Exception e) {
        }
    }

    public String removeStockItem(Material material) {
        try {
            StockItem stockitem = null;
            for (Iterator<StockItem> it = stockItemList.iterator(); it.hasNext();) {
                stockitem = it.next();
                if (material.getMaterialId().equals(stockitem.getMaterial().getMaterialId())) {
                    stockExists = true;
                    break;
                }
            }
            if (stockExists) {
                stockItemList.remove(stockitem);
                stockExists = false;
                this.material = null;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void searchCustomer() {
        if (null == searchText || "".equals(searchText)) {
            customer = null;
            debtorFoundInSearch = false;
        } else {
            customer = SirsDataSource.dataSource().customerSearch(searchText);
            //If found customer then check whether customer is owing us

            debtorList = SirsDataSource.dataSource().debtorFindByAttribute("customer", customer, ApplicationConstant.STRING);

            if (null != debtorList && !debtorList.isEmpty()) {
                debtorFoundInSearch = true;
            } else {
                debtorFoundInSearch = false;
            }
        }
    }

    public void recallDebitForPayment(Debtor debtor) {
        recallDebitPayment = true;
        selectedDebtor = debtor;
        jobDetailList = null;
        selectedJob = debtor.getJob();
        payment = new Payment();

        jobAmountPaid = selectedJob.getAmountPaid();
    }

    public void saveClientCustomer() {
        try {
            if (!validateCustomer()) {
                return;
            }

            customer.setCustomerType(customerType);
            customer.setStatus('1');
            SirsDataSource.dataSource().customerCreate(customer);
            JSFUtility.infoMessage("New client saved as customer successfully.");
        } catch (Exception e) {
            System.out.println("e..." + e.toString());
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
        if (null != SirsDataSource.dataSource().customerSearchSpecific(customer.getCustomerName())
                && ApplicationConstant.NEW_CUSTOMER.equals(customerType)) {
            JSFUtility.warnMessage("There already exists a customer with name '" + customer.getCustomerName() + "'. Please change name.");
            return false;
        }

        return true;
    }

    public Double totalAmountDue() {
        Double a = 0.0;
        if (jobTodayList != null && !jobTodayList.isEmpty()) {
            for (Job j : jobTodayList) {
                a += j.getAmountDue();
            }
        }

        return a;
    }

    public Double totalJobAmountPaid() {
        Double a = 0.0;
        if (jobTodayList != null && !jobTodayList.isEmpty()) {
            for (Job j : jobTodayList) {
                a += j.getAmountPaid();
            }
        }

        return a;
    }

    public Double totalPaymentAmountPaid() {
        Double a = 0.0;
        if (paymentTodayList != null && !paymentTodayList.isEmpty()) {
            for (Payment p : paymentTodayList) {
                a += (p.getAmountPaid() - p.getChangeGiven());
            }
        }

        return a;
    }

    public Double totalPaymentBalance() {
        Double a = 0.0;
        if (paymentTodayList != null && !paymentTodayList.isEmpty()) {
            for (Payment p : paymentTodayList) {
                a += p.getBalance();
            }
        }

        return a;
    }

    public Double totalDiscount() {
        Double a = 0.0;
        for (Job j : jobTodayList) {
            a += j.getDiscount();
        }

        return a;
    }

    public Double totalDebtorBalance() {
        Double a = 0.0;
        if (debtorList != null && !debtorList.isEmpty()) {
            for (Debtor d : debtorList) {
                a += d.getBalance();
            }
        }

        return a;
    }

    public void handleTransactionViewChange() {
        //jobTransactionView = "";
        //System.out.println("Hi there");
    }

    public void handleClientTypeChange() {
        customer = new Customer();
        searchCriteria = null;
        debtorFoundInSearch = false;
        debtorList = new ArrayList<Debtor>();
        searchText = null;
        recallDebitPayment = false;
    }

    public void handleAmountPaidChange() {
        payment.setChangeGiven(NumberFormatter.formatDoubleNum(payment.getAmountPaid() - job.getAmountDue(), 1));
    }

    public void handleBalancePaidChange() {
        payment.setChangeGiven(NumberFormatter.formatDoubleNum(payment.getAmountPaid() - selectedDebtor.getBalance(), 1));
    }

    public void handleNewAmountPaidChange() {
        payment.setChangeGiven(NumberFormatter.formatDoubleNum(job.getAmountPaid() - newAmountDue, 1));
    }

    public void handleDiscountChange() {
        newAmountDue = NumberFormatter.formatDoubleNum(selectedJob.getAmountDue() - selectedJob.getDiscount(), 1);
        //selectedJob.setAmountPaid(0.0);
        //selectedJob.setChangeGive(0.0); 
    }

    public void handleJobSelection() {
        job = selectedJob;
        newAmountDue = NumberFormatter.formatDoubleNum(selectedJob.getAmountDue() - selectedJob.getDiscount(), 1);
        payment = selectedJob.getPayment();
        jobIsAdd = false;

        //System.out.println("....." + payment);
    }

    public void handlePaySelection() {
        //System.out.println("Hi there How are you.......");
        job = selectedPayment.getJob();
        selectedJob = job;
        newAmountDue = NumberFormatter.formatDoubleNum(selectedJob.getAmountDue() - selectedJob.getDiscount(), 1);
        jobIsAdd = false;
        //System.out.println("......" + selectedPayment);
    }

    public void adminSavePrint() {
        try {
            selectedJob.setModifiedBy(getLoginUser().getUsername());
            selectedJob.setLastModifiedDate(DateTimeUtils.getCurSqlDate());
            payment.setAmountPaid(NumberFormatter.formatDoubleNum(payment.getAmountPaid(), 1));
            if (payment.getChangeGiven() > 0) {
                payment.setBalance(0.0);
                payment.setPaymentReference("Full Payment of Transaction");
            } else {
                payment.setBalance(NumberFormatter.formatDoubleNum(selectedJob.getAmountDue() - selectedJob.getDiscount() - payment.getAmountPaid(), 1));
                payment.setChangeGiven(0.0);
            }

            Debtor d;
            List<Debtor> dList = SirsDataSource.dataSource().debtorFindByAttribute("job", selectedJob, ApplicationConstant.STRING);
            if (dList != null && !dList.isEmpty()) {
                d = dList.get(0);

                d.setBalance(payment.getBalance());
                SirsDataSource.dataSource().debtorUpdate(d);
            }


            SirsDataSource.dataSource().jobUpdate(selectedJob);
            SirsDataSource.dataSource().paymentUpdate(payment);
            JSFUtility.infoMessage("Please wait while printer is being initialized...");
            StockListFromJobList();
            rePrintInvoice();
            JSFUtility.infoMessage("Transaction Saved Successfully. Sending invoice to printer...");
        } catch (Exception e) {
        }
    }

    public void adminSaveOnly() {
        try {
            selectedJob.setModifiedBy(getLoginUser().getUsername());
            selectedJob.setLastModifiedDate(DateTimeUtils.getCurSqlDate());

            payment.setAmountPaid(NumberFormatter.formatDoubleNum(payment.getAmountPaid(), 1));
            if (payment.getChangeGiven() > 0) {
                payment.setBalance(0.0);
                payment.setPaymentReference("Full Payment of Transaction");
            } else {
                payment.setBalance(NumberFormatter.formatDoubleNum(selectedJob.getAmountDue() - selectedJob.getDiscount() - payment.getAmountPaid(), 1));
                payment.setChangeGiven(0.0);
            }

            Debtor d;
            List<Debtor> dList = SirsDataSource.dataSource().debtorFindByAttribute("job", selectedJob, ApplicationConstant.STRING);
            if (dList != null && !dList.isEmpty()) {
                d = dList.get(0);

                d.setBalance(payment.getBalance());
                SirsDataSource.dataSource().debtorUpdate(d);
            }


            SirsDataSource.dataSource().jobUpdate(selectedJob);
            SirsDataSource.dataSource().paymentUpdate(payment);
            JSFUtility.infoMessage("Transaction Saved Successfully.");
        } catch (Exception e) {
        }
    }

    public void deleteTransaction() {
        try {
            if (null == selectedJob.getDeletionReason() || "".equals(selectedJob.getDeletionReason())) {
                JSFUtility.warnMessage("Cancelation was NOT SUCCESSFUL because you did not provide any reason for deletion.");
                return;
            }
            selectedJob.setDeleted("YES");
            selectedJob.setDeletedBy(getLoginUser().getUsername());
            selectedJob.setLastDeletedDate(DateTimeUtils.getCurSqlDate());
            SirsDataSource.dataSource().jobUpdate(selectedJob);

            JSFUtility.infoMessage("Transaction with Invoice # " + selectedJob.getInvoiceNo() + " has been canceled.");
        } catch (Exception e) {
        }
    }

    public void setMaterialSelect(SelectItem[] materialSelect) {
        this.materialSelect = materialSelect;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<StockItem> getStockItemList() {
        return stockItemList;
    }

    public void setStockItemList(List<StockItem> stockItemList) {
        this.stockItemList = stockItemList;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<Job> getJobTodayList() {
        jobTodayList = SirsDataSource.dataSource().jobGetAll(true, transactionDate, false, true);
        return jobTodayList;
    }

    public void setJobTodayList(List<Job> jobTodayList) {
        this.jobTodayList = jobTodayList;
    }

    public Job getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Job selectedJob) {
        this.selectedJob = selectedJob;
    }

    public Boolean getJobIsAdd() {
        return jobIsAdd;
    }

    public void setJobIsAdd(Boolean jobIsAdd) {
        this.jobIsAdd = jobIsAdd;
    }

    public List<JobDetail> getJobDetailList() {
        jobDetailList = SirsDataSource.dataSource().jobDetailByJob(selectedJob);
        StockListFromJobList(); 

        return jobDetailList;
    }

    public void setJobDetailList(List<JobDetail> jobDetailList) {
        this.jobDetailList = jobDetailList;
    }

    public Double getNewAmountDue() {
        return newAmountDue;
    }

    public void setNewAmountDue(Double newAmountDue) {
        this.newAmountDue = newAmountDue;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    public Double getDebtorInSearchTotalBalance(){
        double s = 0.0;
        for (Debtor d : debtorList) {
            s+=d.getBalance();
        }
        return s;
    }

    public Boolean getDebtorFoundInSearch() {
        return debtorFoundInSearch;
    }

    public void setDebtorFoundInSearch(Boolean debtorFoundInSearch) {
        this.debtorFoundInSearch = debtorFoundInSearch;
    }

    public List<Debtor> getDebtorList() {
        return debtorList;
    }

    public void setDebtorList(List<Debtor> debtorList) {
        this.debtorList = debtorList;
    }

    public Boolean getRecallDebitPayment() {
        return recallDebitPayment;
    }

    public void setRecallDebitPayment(Boolean recallDebitPayment) {
        this.recallDebitPayment = recallDebitPayment;
    }

    public Debtor getSelectedDebtor() {
        return selectedDebtor;
    }

    public void setSelectedDebtor(Debtor selectedDebtor) {
        this.selectedDebtor = selectedDebtor;
    }

    public Payment getSelectedPayment() {
        return selectedPayment;
    }

    public void setSelectedPayment(Payment selectedPayment) {
        this.selectedPayment = selectedPayment;
    }

    public List<Payment> getPaymentTodayList() { 
        paymentTodayList = SirsDataSource.dataSource().paymentGetAll(true, false, transactionDate);
        //jobTodayList = SirsDataSource.dataSource().jobGetAll(true, transactionDate, false, true);
        return paymentTodayList;
    }

    public void setPaymentTodayList(List<Payment> paymentTodayList) {
        this.paymentTodayList = paymentTodayList;
    }
    
    public void searchCustomerTransaction(){
        
        //sSystem.out.println("customerSearch..." + customerSearch);
    }

    public List<Payment> getPaymentGeneralList() {
        if ("".equalsIgnoreCase(customerSearch)){
            paymentGeneralList = SirsDataSource.dataSource().paymentGetAll(false, true, transactionDate);
        }else{
            paymentGeneralList = SirsDataSource.dataSource().paymentGetAllByCustomer(customerSearch);
        }
        return paymentGeneralList;
    }

    public void setPaymentGeneralList(List<Payment> paymentGeneralList) {
        this.paymentGeneralList = paymentGeneralList;
    }

    public String getJobTransactionView() {
        return jobTransactionView;
    }

    public void setJobTransactionView(String jobTransactionView) {
        this.jobTransactionView = jobTransactionView;
    }

    public List<Job> getJobGeneralList() {
        jobGeneralList = SirsDataSource.dataSource().jobGetAll(false, null, false, true);
        return jobGeneralList;
    }

    public void setJobGeneralList(List<Job> jobGeneralList) {
        this.jobGeneralList = jobGeneralList;
    }

    public String getCustomerSearch() {
        return customerSearch;
    }

    public void setCustomerSearch(String customerSearch) {
        this.customerSearch = customerSearch;
    }
}
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sabonay.sirs.ejb.entities;
//
//import com.sabonay.sirs.ejb.common.SirsDataSource;
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Date;
//import javax.persistence.Basic;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Transient;
//import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
///**
// *
// * @author emma
// */
//@Entity
//@Table(name = "job")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
//    @NamedQuery(name = "Job.findByJobId", query = "SELECT j FROM Job j WHERE j.jobId = :jobId"),
//    @NamedQuery(name = "Job.findByJobDescription", query = "SELECT j FROM Job j WHERE j.jobDescription = :jobDescription"),
//    @NamedQuery(name = "Job.findByBillTo", query = "SELECT j FROM Job j WHERE j.billTo = :billTo"),
//    @NamedQuery(name = "Job.findByJobDate", query = "SELECT j FROM Job j WHERE j.jobDate = :jobDate"),
//    @NamedQuery(name = "Job.findByInvoiceNo", query = "SELECT j FROM Job j WHERE j.invoiceNo = :invoiceNo"),
//    @NamedQuery(name = "Job.findByJobType", query = "SELECT j FROM Job j WHERE j.jobType = :jobType"),
//    @NamedQuery(name = "Job.findByDiscount", query = "SELECT j FROM Job j WHERE j.discount = :discount"),
//    @NamedQuery(name = "Job.findByAmountDue", query = "SELECT j FROM Job j WHERE j.amountDue = :amountDue"),
//    @NamedQuery(name = "Job.findByCreatedBy", query = "SELECT j FROM Job j WHERE j.createdBy = :createdBy"),
//    @NamedQuery(name = "Job.findByModifiedBy", query = "SELECT j FROM Job j WHERE j.modifiedBy = :modifiedBy"),
//    @NamedQuery(name = "Job.findByDeletedBy", query = "SELECT j FROM Job j WHERE j.deletedBy = :deletedBy"),
//    @NamedQuery(name = "Job.findByLastModifiedDate", query = "SELECT j FROM Job j WHERE j.lastModifiedDate = :lastModifiedDate"),
//    @NamedQuery(name = "Job.findByLastDeletedDate", query = "SELECT j FROM Job j WHERE j.lastDeletedDate = :lastDeletedDate"),
//    @NamedQuery(name = "Job.findByUpdated", query = "SELECT j FROM Job j WHERE j.updated = :updated"),
//    @NamedQuery(name = "Job.findByDeleted", query = "SELECT j FROM Job j WHERE j.deleted = :deleted")})
//public class Job implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "job_id")
//    private Long jobId;
//    @Size(max = 255)
//    @Column(name = "job_description")
//    private String jobDescription;
//    @Size(max = 255)
//    @Column(name = "bill_to")
//    private String billTo;
//    @Column(name = "job_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date jobDate;
//    @Size(max = 50)
//    @Column(name = "invoice_no")
//    private String invoiceNo;
//    @Size(max = 10)
//    @Column(name = "job_type")
//    private String jobType;
//    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(name = "discount")
//    private Double discount;
//    @Column(name = "amount_due")
//    private Double amountDue;
//    @Size(max = 20)
//    @Column(name = "created_by")
//    private String createdBy;
//    @Size(max = 20)
//    @Column(name = "modified_by")
//    private String modifiedBy;
//    @Size(max = 20)
//    @Column(name = "deleted_by")
//    private String deletedBy;
//    @Column(name = "last_modified_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//    @Column(name = "last_deleted_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastDeletedDate;
//    @Lob
//    @Size(max = 65535)
//    @Column(name = "deletion_reason")
//    private String deletionReason;
//    @Size(max = 3)
//    @Column(name = "updated")
//    private String updated;
//    @Size(max = 3)
//    @Column(name = "deleted")
//    private String deleted;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobId")
//    private Collection<Debtor> debtorCollection;
//    @OneToMany(mappedBy = "jobId")
//    private Collection<Payment> paymentCollection;
//    @OneToMany(mappedBy = "jobId")
//    private Collection<JobDetail> jobDetailCollection;
//
//    public Job() {
//    }
//
//    public Job(Long jobId) {
//        this.jobId = jobId;
//    }
//
//    public Long getJobId() {
//        return jobId;
//    }
//
//    public void setJobId(Long jobId) {
//        this.jobId = jobId;
//    }
//
//    public String getJobDescription() {
//        return jobDescription;
//    }
//
//    public void setJobDescription(String jobDescription) {
//        this.jobDescription = jobDescription;
//    }
//
//    public String getBillTo() throws ParseException {
//        try {
//            Long customerid = Long.parseLong(billTo);
//            Customer cust = SirsDataSource.dataSource().customerFind(customerid);
//            String phone = " <span class='cphone'>(" + cust.getPhoneNumber() + ")</span>";
//            billTo = cust.getCustomerName() + phone;
//        } catch (Exception e) {
//            //billTo is not an existing customer
//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse(billTo); //JSON String
//            JSONObject jsonObject = (JSONObject) obj;
//            
//            String name = (String) jsonObject.get("name");
//            String ph = (String) jsonObject.get("phone");
//            
//            billTo = name + " " + " <span class='cphone'>(" + ph + ")</span>";
//        }
//
//        return billTo;
//    }
//
//    public void setBillTo(String billTo) {
//        this.billTo = billTo;
//    }
//
//    public Date getJobDate() {
//        return jobDate;
//    }
//
//    public void setJobDate(Date jobDate) {
//        this.jobDate = jobDate;
//    }
//
//    public String getInvoiceNo() {
//        return invoiceNo;
//    }
//
//    public void setInvoiceNo(String invoiceNo) {
//        this.invoiceNo = invoiceNo;
//    }
//
//    public String getJobType() {
//        return jobType;
//    }
//
//    public void setJobType(String jobType) {
//        this.jobType = jobType;
//    }
//
//    public Double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(Double discount) {
//        this.discount = discount;
//    }
//
//    public Double getAmountDue() {
//        return amountDue;
//    }
//
//    public void setAmountDue(Double amountDue) {
//        this.amountDue = amountDue;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public String getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(String modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }
//
//    public String getDeletedBy() {
//        return deletedBy;
//    }
//
//    public void setDeletedBy(String deletedBy) {
//        this.deletedBy = deletedBy;
//    }
//
//    public Date getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public void setLastModifiedDate(Date lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
//
//    public Date getLastDeletedDate() {
//        return lastDeletedDate;
//    }
//
//    public void setLastDeletedDate(Date lastDeletedDate) {
//        this.lastDeletedDate = lastDeletedDate;
//    }
//
//    public String getDeletionReason() {
//        return deletionReason;
//    }
//
//    public void setDeletionReason(String deletionReason) {
//        this.deletionReason = deletionReason;
//    }
//
//    public String getUpdated() {
//        return updated;
//    }
//
//    public void setUpdated(String updated) {
//        this.updated = updated;
//    }
//
//    public String getDeleted() {
//        return deleted;
//    }
//
//    public void setDeleted(String deleted) {
//        this.deleted = deleted;
//    }
//
//    @XmlTransient
//    public Collection<Debtor> getDebtorCollection() {
//        return debtorCollection;
//    }
//
//    public void setDebtorCollection(Collection<Debtor> debtorCollection) {
//        this.debtorCollection = debtorCollection;
//    }
//
//    @XmlTransient
//    public Collection<Payment> getPaymentCollection() {
//        return paymentCollection;
//    }
//
//    public void setPaymentCollection(Collection<Payment> paymentCollection) {
//        this.paymentCollection = paymentCollection;
//    }
//
//    @XmlTransient
//    public Collection<JobDetail> getJobDetailCollection() {
//        return jobDetailCollection;
//    }
//
//    public void setJobDetailCollection(Collection<JobDetail> jobDetailCollection) {
//        this.jobDetailCollection = jobDetailCollection;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (jobId != null ? jobId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Job)) {
//            return false;
//        }
//        Job other = (Job) object;
//        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.sabonay.sirs.ejb.entities.Job[ jobId=" + jobId + " ]";
//    }
//    @Transient
//    private String fileNameShort;
//    @Transient
//    private Customer customer;
//
//    public String getFileNameShort() {
//        fileNameShort = !"".equals(jobDescription) ? jobDescription.substring(0, 10) + "..." : "";
//        return fileNameShort;
//    }
//
//    public void setFileNameShort(String fileNameShort) {
//        this.fileNameShort = fileNameShort;
//    }
//
//    public Customer getCustomer() {
//        Customer cust = null;
//        try {
//            Long customerid = Long.parseLong(billTo);
//            cust = SirsDataSource.dataSource().customerFind(customerid);
//        } catch (Exception e) {
//            //billTo is not an existing customer
//            billTo = "";
//        }
//        return cust;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//}
