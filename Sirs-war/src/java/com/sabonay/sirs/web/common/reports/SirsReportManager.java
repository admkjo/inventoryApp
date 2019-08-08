/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.common.reports;

import com.sabonay.modules.jasperreporting.JasperReportManager;
import com.sabonay.modules.jasperreporting.ReportDesignFileType;
import com.sabonay.modules.jasperreporting.ReportOutputEnvironment; 
import com.sabonay.modules.jasperreporting.ReportOutputFileType;
import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.LoginUser;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**  
 *
 * @author JESUS
 */
public class SirsReportManager extends JasperReportManager implements Serializable { 
    public static Map<String, Object> rptParam = new HashMap<String, Object>();
    String msg = "";

    //private SirsUserData userData = JsfUtil.getManagedBean("userdata");

//    privat
    private static final String REPORT_BASE_DIR = "/com/sabonay/sirs/web/common/reports/";
    private static SirsReportManager reportManager = new SirsReportManager();
    public static final String LIST_OF_PRODUCT = REPORT_BASE_DIR + "list_of_product.jasper";
    public static final String PRODUCT_CATEGORIES = REPORT_BASE_DIR + "product_category.jasper";
    public static final String POS_RECEIPT = REPORT_BASE_DIR + "pos.jasper";
    public static final String STOCK_LIST = REPORT_BASE_DIR + "rpt_stk_stock_list.jasper";
    public static final String STOCK_DAILY_SUMMARY = REPORT_BASE_DIR + "rpt_stk_daily_stock_summary.jasper";
    
    public static final String POS_SALES_INVOICE = REPORT_BASE_DIR + "rpt_sales_invoice.jasper";
    public static final String POS_SALES_INVOICE_CC = REPORT_BASE_DIR + "rpt_sales_invoice_cc.jasper";
    public static final String POS_SALES_INVOICE_TRIPLICATE = REPORT_BASE_DIR + "rpt_sales_invoice_tc.jasper";
    
    public static final String POS_SALES_INVOICE_CREDIT = REPORT_BASE_DIR + "rpt_sales_invoice_credit.jasper";
    public static final String POS_SALES_INVOICE_CC_CREDIT = REPORT_BASE_DIR + "rpt_sales_invoice_cc_credit.jasper";
    public static final String POS_SALES_INVOICE_CREDIT_TRIPLICATE = REPORT_BASE_DIR + "rpt_sales_invoice_credit_triplicate.jasper";
    
    
    
    public static final String STOCK_STOCK_PRICE_LIST = REPORT_BASE_DIR + "rpt_stk_price_list.jasper";
    public static final String STOCK_STOCK_PRODUCT_LIST = REPORT_BASE_DIR + "rpt_stk_product_list.jasper";
    public static final String POS_DAILY_PAYMENT_LIST = REPORT_BASE_DIR + "rpt_sales_daily_sales.jasper";   
    public static final String POS_DAILY_JOBS_LIST = REPORT_BASE_DIR + "rpt_sales_daily_jobs.jasper";
    public static final String DEBTORS_LIST = REPORT_BASE_DIR + "rpt_debtor_list.jasper";
    public static final String MONTHLY_TRANSACTION_LIST = REPORT_BASE_DIR + "rpt_monthly_transaction.jasper";

    public static final String POS_SALES_INVOICE_EDITED = REPORT_BASE_DIR + "rpt_sales_invoice_edited.jasper";

    static {
        reportManager.setReportOutput(ReportOutputFileType.PDF);
        reportManager.setReportEnvironment(ReportOutputEnvironment.WEB_APPLICATION);
        reportManager.setReportFileType(ReportDesignFileType.INPUTSTREAM);
    }

    private SirsReportManager() { 
    }

//    public void printDirectToPrinter(Collection reportData, InputStream inputStream) {
//        this.reportData = reportData;
//        createJasperPrint(inputStream);
//    }

    public static SirsReportManager getInstance() {
        return reportManager;
    }
    
    
    private AdminUser getLoginUser() {
        try {
            return (AdminUser) ((LoginUser) JSFUtility.getSessionValue(ApplicationConstant.ADMIN_USER)).getUserLogin();
        } catch (Exception e) {
            return null;
        }
    }

    public void loadSirsDefaultParameters(){
        String companyName = SirsDataSource.dataSource().settingsGetByKey(ApplicationConstant.COMPANY_NAME);
        String motto = SirsDataSource.dataSource().settingsGetByKey(ApplicationConstant.MOTTO);
        String address = SirsDataSource.dataSource().settingsGetByKey(ApplicationConstant.CONTACT_ADDRESS);
        String tel = SirsDataSource.dataSource().settingsGetByKey(ApplicationConstant.CONTACT_TEL);
        String user = getLoginUser().getUsername();
        
        reportManager.resetReportParametersToDefault();
        reportManager.addToDefaultParameters("subCompanyName", companyName);
        reportManager.addToDefaultParameters("motto", motto);
        //.addToDefaultParameters("phyaddress", uac.getSirsuserdata().getCompany().getPhyaddress());
        //reportManager.addToDefaultParameters("physlocator", uac.getSirsuserdata().getCompany().getPhylocator());
        reportManager.addToDefaultParameters("postalAddress", address);
        reportManager.addToDefaultParameters("tel", tel);
        reportManager.addToDefaultParameters("user", user);
    } 
}
