/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.ejb.sessionbeans;

import com.sabonay.sirs.ejb.entities.AdminUser;
import com.sabonay.sirs.ejb.entities.Customer;
import com.sabonay.sirs.ejb.entities.CustomerGroup;
import com.sabonay.sirs.ejb.entities.CustomerMaterialGroup;
import com.sabonay.sirs.ejb.entities.Debtor;
import com.sabonay.sirs.ejb.entities.Job;
import com.sabonay.sirs.ejb.entities.JobDetail;
import com.sabonay.sirs.ejb.entities.Material;
import com.sabonay.sirs.ejb.entities.Payment;
import com.sabonay.sirs.ejb.entities.Settings;
import com.sabonay.sirs.ejb.entities.custom.MonthlyPaymentADT;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 *
 * @author emma
 */
@Stateless
public class SirsSessionBean {

    @PersistenceContext(unitName = "SirsExtended-ejbPU")
    private EntityManager em;

    // <editor-fold defaultstate="collapsed" desc=" AdminUser Persistence Functionalities">
    public Integer adminUserCreate(AdminUser adminUser) {
        try {
            em.persist(adminUser);
            em.flush();
            return adminUser.getAdminUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean adminUserDelete(AdminUser adminUser) {
        try {
            em.remove(em.merge(adminUser));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean adminUserUpdate(AdminUser adminUser) {
        try {
            em.merge(adminUser);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public AdminUser adminUserLogin(String username, String password) {
        List<AdminUser> listOfAdminUser = null;
        String qryString = "";

        try {
            qryString = " SELECT e FROM AdminUser e where e.username =:user and e.userPassword=:pass";

            listOfAdminUser = (List<AdminUser>) em.createQuery(qryString).setParameter("user", username).setParameter("pass", password).getResultList();
            if (listOfAdminUser.size() > 0) {
                return listOfAdminUser.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AdminUser adminUserFind(Integer adminUserId) {
        try {

            AdminUser adminUser = em.find(AdminUser.class, adminUserId);
            return adminUser;
        } catch (Exception e) {
            return null;
        }
    }

    public List<AdminUser> adminUserFindByAttribute(String adminUserAttribute, Object attributeValue, String fieldType) {
        List<AdminUser> listOfAdminUser = null;

        String qryString = "";

        qryString = "SELECT e FROM AdminUser e ";
        qryString += "WHERE e." + adminUserAttribute + " =:adminUserAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfAdminUser = (List<AdminUser>) em.createQuery(qryString).setParameter("adminUserAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfAdminUser = (List<AdminUser>) em.createQuery(qryString).setParameter("adminUserAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfAdminUser = (List<AdminUser>) em.createQuery(qryString).setParameter("adminUserAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfAdminUser;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AdminUser> adminUserGetAll() {
        List<AdminUser> listOfAdminUser = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM AdminUser e";

            listOfAdminUser = (List<AdminUser>) em.createQuery(qryString).getResultList();

            return listOfAdminUser;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Customer Persistence Functionalities">
    public Long customerCreate(Customer customer) {
        try {
            em.persist(customer);
            em.flush();
            return customer.getCustomerId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean customerDelete(Customer customer) {
        try {
            em.remove(em.merge(customer));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean customerUpdate(Customer customer) {
        try {
            em.merge(customer);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Customer customerFind(Long customerId) {
        try {

            Customer customer = em.find(Customer.class, customerId);
            return customer;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Customer> customerFindByAttribute(String customerAttribute, Object attributeValue, String fieldType) {
        List<Customer> listOfCustomer = null;

        String qryString = "";

        qryString = "SELECT e FROM Customer e ";
        qryString += "WHERE e." + customerAttribute + " =:customerAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfCustomer = (List<Customer>) em.createQuery(qryString).setParameter("customerAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfCustomer = (List<Customer>) em.createQuery(qryString).setParameter("customerAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfCustomer = (List<Customer>) em.createQuery(qryString).setParameter("customerAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfCustomer;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> customerGetAll(boolean viewAll) {
        List<Customer> listOfCustomer = null;

        String qryString = "";

        try {
            if (viewAll) {
                qryString = "SELECT e FROM Customer e";
            } else {
                qryString = "SELECT e FROM Customer e WHERE e.status='1'";
            }


            listOfCustomer = (List<Customer>) em.createQuery(qryString).getResultList();

            return listOfCustomer;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer customerSearch(String searchtext) {
        List<Customer> listOfCustomer = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Customer e WHERE (e.customerName like '" + searchtext + "%' or e.phoneNumber='" + searchtext + "') and e.status='1'";


            listOfCustomer = (List<Customer>) em.createQuery(qryString).getResultList();

            if (!listOfCustomer.isEmpty() && listOfCustomer.size() > 0) {
                return listOfCustomer.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> customersSearch(String searchtext, boolean searchAll) {
        List<Customer> listOfCustomer = null;

        String qryString = "";

        try {
            if (searchAll) {
                qryString = "SELECT e FROM Customer e WHERE (e.customerName like '" + searchtext + "%' or e.phoneNumber='" + searchtext + "')";
            } else {
                qryString = "SELECT e FROM Customer e WHERE (e.customerName like '" + searchtext + "%' or e.phoneNumber='" + searchtext + "') and e.status='1'";
            }

            listOfCustomer = (List<Customer>) em.createQuery(qryString).getResultList();

            return listOfCustomer; 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer customerSearchSpecific(String searchtext) {
        List<Customer> listOfCustomer = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Customer e WHERE (e.customerName = '" + searchtext + "' or e.phoneNumber='" + searchtext + "') and e.status='1'";

            listOfCustomer = (List<Customer>) em.createQuery(qryString).getResultList();

            if (!listOfCustomer.isEmpty() && listOfCustomer.size() > 0) {
                return listOfCustomer.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" CustomerMaterialGroup Persistence Functionalities">
    public Integer customerMaterialGroupCreate(CustomerMaterialGroup customerMaterialGroup) {
        try {
            em.persist(customerMaterialGroup);
            em.flush();
            return customerMaterialGroup.getGroupMaterialId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean customerMaterialGroupDelete(CustomerMaterialGroup customerMaterialGroup) {
        try {
            em.remove(em.merge(customerMaterialGroup));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean customerMaterialGroupUpdate(CustomerMaterialGroup customerMaterialGroup) {
        try {
            em.merge(customerMaterialGroup);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public CustomerMaterialGroup customerMaterialGroupFind(Integer customerMaterialGroupId) {
        try {

            CustomerMaterialGroup customerMaterialGroup = em.find(CustomerMaterialGroup.class, customerMaterialGroupId);
            return customerMaterialGroup;
        } catch (Exception e) {
            return null;
        }
    }

    public List<CustomerMaterialGroup> customerMaterialGroupFindByAttribute(String customerMaterialGroupAttribute, Object attributeValue, String fieldType) {
        List<CustomerMaterialGroup> listOfCustomerMaterialGroup = null;

        String qryString = "";

        qryString = "SELECT e FROM CustomerMaterialGroup e ";
        qryString += "WHERE e." + customerMaterialGroupAttribute + " =:customerMaterialGroupAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfCustomerMaterialGroup = (List<CustomerMaterialGroup>) em.createQuery(qryString).setParameter("customerMaterialGroupAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfCustomerMaterialGroup = (List<CustomerMaterialGroup>) em.createQuery(qryString).setParameter("customerMaterialGroupAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfCustomerMaterialGroup = (List<CustomerMaterialGroup>) em.createQuery(qryString).setParameter("customerMaterialGroupAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfCustomerMaterialGroup;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CustomerMaterialGroup> customerMaterialGroupGetAll(boolean viewAll) {
        List<CustomerMaterialGroup> listOfCustomerMaterialGroup = null;

        String qryString = "";

        try {
            if (viewAll) {
                qryString = "SELECT e FROM CustomerMaterialGroup e";
            } else {
                qryString = "SELECT e FROM CustomerMaterialGroup e WHERE e.status='1'";
            }


            listOfCustomerMaterialGroup = (List<CustomerMaterialGroup>) em.createQuery(qryString).getResultList();

            return listOfCustomerMaterialGroup;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CustomerMaterialGroup customerMaterialGroupFindByMaterial(CustomerGroup cg, Integer materialid) {
        List<CustomerMaterialGroup> listOfCustomerMaterialGroup = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM CustomerMaterialGroup e WHERE e.groupId=:cg AND e.material.materialId = " + materialid;


            listOfCustomerMaterialGroup = (List<CustomerMaterialGroup>) em.createQuery(qryString).setParameter("cg", cg).getResultList();

//            System.out.println("listOfCustomerMaterialGroup...." + listOfCustomerMaterialGroup.size());

            if (listOfCustomerMaterialGroup.size() > 0) {
                return listOfCustomerMaterialGroup.get(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" CustomerGroup Persistence Functionalities">
    public Integer customerGroupCreate(CustomerGroup customerGroup) {
        try {
            em.persist(customerGroup);
            em.flush();
            return customerGroup.getGroupId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean customerGroupDelete(CustomerGroup customerGroup) {
        try {
            em.remove(em.merge(customerGroup));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean customerGroupUpdate(CustomerGroup customerGroup) {
        try {
            em.merge(customerGroup);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public CustomerGroup customerGroupFind(Integer customerMaterialGroupId) {
        try {

            CustomerGroup customerGroup = em.find(CustomerGroup.class, customerMaterialGroupId);
            return customerGroup;
        } catch (Exception e) {
            return null;
        }
    }

    public List<CustomerGroup> customerGroupFindByAttribute(String customerMaterialGroupAttribute, Object attributeValue, String fieldType) {
        List<CustomerGroup> listOfCustomerMaterialGroup = null;

        String qryString = "";

        qryString = "SELECT e FROM CustomerGroup e ";
        qryString += "WHERE e." + customerMaterialGroupAttribute + " =:customerMaterialGroupAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfCustomerMaterialGroup = (List<CustomerGroup>) em.createQuery(qryString).setParameter("customerMaterialGroupAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfCustomerMaterialGroup = (List<CustomerGroup>) em.createQuery(qryString).setParameter("customerMaterialGroupAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfCustomerMaterialGroup = (List<CustomerGroup>) em.createQuery(qryString).setParameter("customerMaterialGroupAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfCustomerMaterialGroup;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CustomerGroup> customerGroupGetAll(boolean viewAll) {
        List<CustomerGroup> listOfCustomerMaterialGroup = null;

        String qryString = "";

        try {
            if (viewAll) {
                qryString = "SELECT e FROM CustomerGroup e";
            } else {
                qryString = "SELECT e FROM CustomerGroup e WHERE e.status='1'";
            }


            listOfCustomerMaterialGroup = (List<CustomerGroup>) em.createQuery(qryString).getResultList();

            return listOfCustomerMaterialGroup;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Job Persistence Functionalities">

    public Long jobCreate(Job job) {
        try {
            job.setUpdated("NO");
            job.setDeleted("NO");
            em.persist(job);
            em.flush();
            return job.getJobId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean jobDelete(Job job) {
        try {
            em.remove(em.merge(job));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean jobUpdate(Job job) {
        try {
            em.merge(job);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Job jobFind(Integer jobId) {
        try {

            Job job = em.find(Job.class, jobId);
            return job;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Job> jobFindByAttribute(String jobAttribute, Object attributeValue, String fieldType) {
        List<Job> listOfJob = null;

        String qryString = "";

        qryString = "SELECT e FROM Job e ";
        qryString += "WHERE e." + jobAttribute + " =:jobAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfJob = (List<Job>) em.createQuery(qryString).setParameter("jobAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfJob = (List<Job>) em.createQuery(qryString).setParameter("jobAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfJob = (List<Job>) em.createQuery(qryString).setParameter("jobAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfJob;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Job> jobGetAll(boolean viewToday, Date viewDate, boolean incudeLogicallyDeleted, boolean sortDecending) {
        List<Job> listOfJob = null;

        String qryString = "";

        try {
            if (incudeLogicallyDeleted) {
                if (viewToday) {
                    qryString = "SELECT e FROM Job e WHERE e.jobDate LIKE '" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date())) + "%' " + (sortDecending == true ? "ORDER BY e.jobId DESC" : "");
                } else {
                    qryString = "SELECT e FROM Job e " + (sortDecending == true ? "ORDER BY e.jobId DESC" : "");
                }
            } else {
                if (viewToday) {
                    qryString = "SELECT e FROM Job e WHERE e.deleted='NO' AND e.jobDate LIKE '" + (new SimpleDateFormat("yyyy-MM-dd").format(viewDate)) + "%' " + (sortDecending == true ? "ORDER BY e.jobId DESC" : "");
                } else {
                    qryString = "SELECT e FROM Job e WHERE e.deleted='NO' " + (sortDecending == true ? "ORDER BY e.jobId DESC" : "");
                }
            }


            listOfJob = (List<Job>) em.createQuery(qryString).getResultList();

            return listOfJob;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" JobDetail Persistence Functionalities">
    public Long jobDetailCreate(JobDetail jobDetail) {
        try {
            jobDetail.setUpdated("NO");
            jobDetail.setDeleted("NO");
            em.persist(jobDetail);
            em.flush();
            return jobDetail.getJobDetailId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean jobDetailDelete(JobDetail jobDetail) {
        try {
            em.remove(em.merge(jobDetail));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean jobDetailUpdate(JobDetail jobDetail) {
        try {
            em.merge(jobDetail);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public JobDetail jobDetailFind(Integer jobDetailId) {
        try {

            JobDetail jobDetail = em.find(JobDetail.class, jobDetailId);
            return jobDetail;
        } catch (Exception e) {
            return null;
        }
    }

    public List<JobDetail> jobDetailFindByAttribute(String jobDetailAttribute, Object attributeValue, String fieldType) {
        List<JobDetail> listOfJobDetail = null;

        String qryString = "";

        qryString = "SELECT e FROM JobDetail e ";
        qryString += "WHERE e." + jobDetailAttribute + " =:jobDetailAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfJobDetail = (List<JobDetail>) em.createQuery(qryString).setParameter("jobDetailAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfJobDetail = (List<JobDetail>) em.createQuery(qryString).setParameter("jobDetailAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfJobDetail = (List<JobDetail>) em.createQuery(qryString).setParameter("jobDetailAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfJobDetail;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<JobDetail> jobDetailGetAll(boolean viewAll) {
        List<JobDetail> listOfJobDetail = null;

        String qryString = "";

        try {
            if (viewAll) {
                qryString = "SELECT e FROM JobDetail e";
            } else {
                qryString = "SELECT e FROM JobDetail e WHERE e.status='1'";
            }


            listOfJobDetail = (List<JobDetail>) em.createQuery(qryString).getResultList();

            return listOfJobDetail;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<JobDetail> jobDetailByJob(Job job) {
        List<JobDetail> listOfJobDetail = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM JobDetail e WHERE e.jobId=:job";

            listOfJobDetail = (List<JobDetail>) em.createQuery(qryString).setParameter("job", job).getResultList();

            return listOfJobDetail;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Payment> paymentDetailByJob(Job job) {
        List<Payment> listOfPaymentDetail = null;

        String qryString = "";
//
        try {
            qryString = "SELECT e FROM Payment e WHERE e.job=:job";
//
            listOfPaymentDetail = (List<Payment>) em.createQuery(qryString).setParameter("job", job).getResultList();

            return listOfPaymentDetail;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Material Persistence Functionalities">
    public Integer materialCreate(Material material) {
        try {
            em.persist(material);
            em.flush();
            return material.getMaterialId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean materialDelete(Material material) {
        try {
            em.remove(em.merge(material));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean materialUpdate(Material material) {
        try {
            em.merge(material);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Material materialFind(Integer materialId) {
        try {

            Material material = em.find(Material.class, materialId);
            return material;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Material> materialFindByAttribute(String materialAttribute, Object attributeValue, String fieldType) {
        List<Material> listOfMaterial = null;

        String qryString = "";

        qryString = "SELECT e FROM Material e ";
        qryString += "WHERE e." + materialAttribute + " =:materialAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfMaterial = (List<Material>) em.createQuery(qryString).setParameter("materialAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfMaterial = (List<Material>) em.createQuery(qryString).setParameter("materialAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfMaterial = (List<Material>) em.createQuery(qryString).setParameter("materialAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfMaterial;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Material> materialGetAll(boolean viewAll) {
        List<Material> listOfMaterial = null;

        String qryString = "";

        try {
            if (viewAll) {
                qryString = "SELECT e FROM Material e";
            } else {
                qryString = "SELECT e FROM Material e WHERE e.status='1'";
            }


            listOfMaterial = (List<Material>) em.createQuery(qryString).getResultList();

            return listOfMaterial;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Debtor Persistence Functionalities">

    public Long debtorCreate(Debtor debtor) {
        try {
            em.persist(debtor);
            em.flush();
            return debtor.getDebtorId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean debtorDelete(Debtor debtor) {
        try {
            em.remove(em.merge(debtor));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean debtorUpdate(Debtor debtor) {
        try {
            em.merge(debtor);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Debtor debtorFind(Integer debtorId) {
        try {

            Debtor debtor = em.find(Debtor.class, debtorId);
            return debtor;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Debtor> debtorFindByAttribute(String debtorAttribute, Object attributeValue, String fieldType) {
        List<Debtor> listOfDebtor = null;

        String qryString = "";

        qryString = "SELECT e FROM Debtor e ";
        qryString += "WHERE e." + debtorAttribute + " =:debtorAttribute and e.balance >0 and e.job.deleted='NO' ORDER BY e.debtorId DESC";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfDebtor = (List<Debtor>) em.createQuery(qryString).setParameter("debtorAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfDebtor = (List<Debtor>) em.createQuery(qryString).setParameter("debtorAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfDebtor = (List<Debtor>) em.createQuery(qryString).setParameter("debtorAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfDebtor;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Debtor> debtorGetAll(boolean viewAll, boolean sortDecending) {
        List<Debtor> listOfDebtor = null;

        String qryString = "";

        try {
            if (viewAll) {
                qryString = "SELECT e FROM Debtor e WHERE e.balance >0" + (sortDecending == true ? "ORDER BY e.debtorId DESC" : "");
            } else {
                qryString = "SELECT e FROM Debtor e WHERE e.job.deleted='NO' and e.balance >0 " + (sortDecending == true ? "ORDER BY e.debtorId DESC" : "");
            }


            listOfDebtor = (List<Debtor>) em.createQuery(qryString).getResultList();

            return listOfDebtor;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Payment Persistence Functionalities">

    public Long paymentCreate(Payment payment) {
        try {
            em.persist(payment);
            em.flush();
            return payment.getPaymentId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean paymentDelete(Payment payment) {
        try {
            em.remove(em.merge(payment));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean paymentUpdate(Payment payment) {
        try {
            em.merge(payment);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Payment paymentFind(Integer paymentId) {
        try {

            Payment payment = em.find(Payment.class, paymentId);
            return payment;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Payment> paymentFindByAttribute(String paymentAttribute, Object attributeValue, String fieldType) {
        List<Payment> listOfPayment = null;

        String qryString = "";

        qryString = "SELECT e FROM Payment e ";
        qryString += "WHERE e." + paymentAttribute + " =:paymentAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfPayment = (List<Payment>) em.createQuery(qryString).setParameter("paymentAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfPayment = (List<Payment>) em.createQuery(qryString).setParameter("paymentAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfPayment = (List<Payment>) em.createQuery(qryString).setParameter("paymentAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfPayment;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Payment> paymentGetAllByCustomer(String customer) {
        List<Payment> listOfPayment = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Payment e WHERE e.job.customer.customerName LIKE '" + customer + "%' ORDER BY e.paymentId DESC";
            
            listOfPayment = (List<Payment>) em.createQuery(qryString).getResultList();
            return listOfPayment;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> paymentGetAll(boolean viewOnlyToday, boolean includeLogicallyDeleted, Date transactionDate) {
        List<Payment> listOfPayment = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted) {
                if (viewOnlyToday) {
                    //qryString = "SELECT e FROM Payment e WHERE e.paymentDate=:pdate"; 
                    qryString = "SELECT e FROM Payment e WHERE e.paymentDate LIKE '" + (new SimpleDateFormat("yyyy-MM-dd").format(transactionDate)) + "%' ORDER BY e.paymentId DESC";
                    //listOfPayment = (List<Payment>) em.createQuery(qryString).setParameter("pdate", (Date) paydate, TemporalType.DATE).getResultList();
                } else {
                    qryString = "SELECT e FROM Payment e ORDER BY e.paymentId DESC";
                    //listOfPayment = (List<Payment>) em.createQuery(qryString).getResultList();
                }
            } else {
                if (viewOnlyToday) {
                    //qryString = "SELECT e FROM Payment e WHERE e.paymentDate=:pdate"; 
                    qryString = "SELECT e FROM Payment e WHERE e.job.deleted='NO' AND e.paymentDate LIKE '" + (new SimpleDateFormat("yyyy-MM-dd").format(transactionDate)) + "%' ORDER BY e.paymentId DESC";
                    //listOfPayment = (List<Payment>) em.createQuery(qryString).setParameter("pdate", (Date) paydate, TemporalType.DATE).getResultList();
                } else {
                    qryString = "SELECT e FROM Payment e WHERE e.job.deleted='NO' ORDER BY e.paymentId DESC";
                    //listOfPayment = (List<Payment>) em.createQuery(qryString).getResultList();
                }
            }


            listOfPayment = (List<Payment>) em.createQuery(qryString).getResultList();
            return listOfPayment;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Payment paymentFindByJob(Long jobId) {
        List<Payment> listOfPayment = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Payment e WHERE e.job.jobId=:jobId";


            listOfPayment = (List<Payment>) em.createQuery(qryString).setParameter("jobId", jobId).getResultList();

            if (listOfPayment.size() > 0) {
                return listOfPayment.get(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Settings Persistence Functionalities">

    public boolean settingsUpdate(Settings settings) {
        try {
            em.merge(settings);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public List<Settings> settingsFindByAttribute(String settingsAttribute, Object attributeValue, String fieldType) {
        List<Settings> listOfSettings = null;

        String qryString = "";

        qryString = "SELECT e FROM Settings e ";
        qryString += "WHERE e." + settingsAttribute + " =:settingsAttribute ";

        try {

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSettings = (List<Settings>) em.createQuery(qryString).setParameter("settingsAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfSettings = (List<Settings>) em.createQuery(qryString).setParameter("settingsAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSettings = (List<Settings>) em.createQuery(qryString).setParameter("settingsAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSettings;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Settings> settingsGetAll() {
        List<Settings> listOfSettings = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Settings e WHERE e.adminDisplay='1'";



            listOfSettings = (List<Settings>) em.createQuery(qryString).getResultList();

            return listOfSettings;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String settingsGetByKey(String settingname) {
        List<Settings> listOfSettings = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Settings e WHERE e.settingname='" + settingname + "'";



            listOfSettings = (List<Settings>) em.createQuery(qryString).getResultList();

            if (listOfSettings.size() > 0) {
                return listOfSettings.get(0).getSettingvalue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>

    public List<MonthlyPaymentADT> customMonthlyReport(String fromDate, String toDate) {
        List<MonthlyPaymentADT> monthlyPaymentList = new ArrayList<MonthlyPaymentADT>();
        String qryString = "";

        qryString = "SELECT DATE_FORMAT(job_date,'%M') AS monthly_period, DATE_FORMAT(job_date,'%Y-%m-%d') AS job_date, SUM(amount_due) AS actual_amount_due, SUM(discount) AS discount, SUM(amount_due) - SUM(discount) AS nominal_amount_due, SUM(payment.amount_paid) - SUM(change_given) AS amount_paid, (SUM(amount_due) - SUM(discount)) - (SUM(payment.amount_paid) - SUM(change_given)) AS balance ";
        qryString += "FROM job JOIN payment ON job.job_id=payment.job ";
        qryString += "WHERE DATE_FORMAT(job_date,'%Y-%m-%d') >='" + fromDate + "' AND DATE_FORMAT(job_date,'%Y-%m-%d') <='" + toDate + "' ";
        qryString += "GROUP BY DATE_FORMAT(job_date,'%m-%d-%Y') ";
        qryString += "ORDER BY DATE_FORMAT(job_date,'%m-%d-%Y') ";

        try {
            List<Object[]> queryresults = em.createNativeQuery(qryString).getResultList();
            //System.out.println(queryresults);
            if (queryresults != null) {
                for (Object[] object : queryresults) {
                    MonthlyPaymentADT adt = new MonthlyPaymentADT();

                    adt.setMonthlyPeriod(object[0].toString());
                    adt.setJobDate(java.sql.Date.valueOf((object[1].toString())));
                    adt.setActualAmountDue(Double.valueOf(object[2].toString()));
                    adt.setDiscount(Double.valueOf(object[3].toString()));
                    adt.setNominalAmountDue(Double.valueOf(object[4].toString()));
                    adt.setAmountPaid(Double.valueOf(object[5].toString()));
                    adt.setBalance(Double.valueOf(object[6].toString()));
                    monthlyPaymentList.add(adt);
                }

                return monthlyPaymentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MonthlyPaymentADT> customYearlyReport(String fromDate, String toDate) {
        List<MonthlyPaymentADT> monthlyPaymentList = new ArrayList<MonthlyPaymentADT>();
        String qryString = "";

        qryString = "SELECT DATE_FORMAT(job_date,'%Y-%m-%d') AS job_date, SUM(amount_due) AS actual_amount_due, SUM(discount) AS discount, SUM(amount_due) - SUM(discount) AS nominal_amount_due, SUM(payment.amount_paid) - SUM(change_given) AS amount_paid, (SUM(amount_due) - SUM(discount)) - (SUM(payment.amount_paid) - SUM(change_given)) AS balance ";
        qryString += "FROM job JOIN payment ON job.job_id=payment.job ";
        qryString += "WHERE DATE_FORMAT(job_date,'%Y-%m-%d') >='" + fromDate + "' AND DATE_FORMAT(job_date,'%Y-%m-%d') <='" + toDate + "' ";
        qryString += "GROUP BY DATE_FORMAT(job_date,'%m-%Y') ";
        qryString += "ORDER BY DATE_FORMAT(job_date,'%m-%Y') ";

        System.out.println("");

        try {
            List<Object[]> queryresults = em.createNativeQuery(qryString).getResultList();
            //System.out.println(queryresults);
            if (queryresults != null) {
                for (Object[] object : queryresults) {
                    MonthlyPaymentADT adt = new MonthlyPaymentADT();

                    adt.setJobDate(java.sql.Date.valueOf((object[0].toString())));
                    adt.setActualAmountDue(Double.valueOf(object[1].toString()));
                    adt.setDiscount(Double.valueOf(object[2].toString()));
                    adt.setNominalAmountDue(Double.valueOf(object[3].toString()));
                    adt.setAmountPaid(Double.valueOf(object[4].toString()));
                    adt.setBalance(Double.valueOf(object[5].toString()));
                    monthlyPaymentList.add(adt);
                }

                return monthlyPaymentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
