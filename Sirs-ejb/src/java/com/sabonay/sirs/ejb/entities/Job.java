/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.ejb.entities;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author emma
 */
@Entity
@Table(name = "job")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
    @NamedQuery(name = "Job.findByJobId", query = "SELECT j FROM Job j WHERE j.jobId = :jobId"),
    @NamedQuery(name = "Job.findByJobDescription", query = "SELECT j FROM Job j WHERE j.jobDescription = :jobDescription"),
    @NamedQuery(name = "Job.findByBillTo", query = "SELECT j FROM Job j WHERE j.billTo = :billTo"),
    @NamedQuery(name = "Job.findByJobDate", query = "SELECT j FROM Job j WHERE j.jobDate = :jobDate"),
    @NamedQuery(name = "Job.findByInvoiceNo", query = "SELECT j FROM Job j WHERE j.invoiceNo = :invoiceNo"),
    @NamedQuery(name = "Job.findByJobType", query = "SELECT j FROM Job j WHERE j.jobType = :jobType"),
    @NamedQuery(name = "Job.findByDiscount", query = "SELECT j FROM Job j WHERE j.discount = :discount"),
    @NamedQuery(name = "Job.findByAmountDue", query = "SELECT j FROM Job j WHERE j.amountDue = :amountDue"),
    @NamedQuery(name = "Job.findByCreatedBy", query = "SELECT j FROM Job j WHERE j.createdBy = :createdBy"),
    @NamedQuery(name = "Job.findByModifiedBy", query = "SELECT j FROM Job j WHERE j.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Job.findByDeletedBy", query = "SELECT j FROM Job j WHERE j.deletedBy = :deletedBy"),
    @NamedQuery(name = "Job.findByLastModifiedDate", query = "SELECT j FROM Job j WHERE j.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "Job.findByLastDeletedDate", query = "SELECT j FROM Job j WHERE j.lastDeletedDate = :lastDeletedDate"),
    @NamedQuery(name = "Job.findByUpdated", query = "SELECT j FROM Job j WHERE j.updated = :updated"),
    @NamedQuery(name = "Job.findByDeleted", query = "SELECT j FROM Job j WHERE j.deleted = :deleted")})
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "job_id")
    private Long jobId;
    @Size(max = 255)
    @Column(name = "job_description")
    private String jobDescription;
    @Size(max = 255)
    @Column(name = "bill_to")
    private String billTo;
    @Column(name = "job_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobDate;
    @Size(max = 50)
    @Column(name = "invoice_no")
    private String invoiceNo;
    @Size(max = 10)
    @Column(name = "job_type")
    private String jobType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private Double discount;
    @Column(name = "amount_due")
    private Double amountDue;
    @Column(name = "amount_paid")
    private Double amountPaid;
    @Size(max = 20)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 20)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Size(max = 20)
    @Column(name = "deleted_by")
    private String deletedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "last_deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDeletedDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "deletion_reason")
    private String deletionReason;
    @Size(max = 3)
    @Column(name = "updated")
    private String updated;
    @Size(max = 3)
    @Column(name = "deleted")
    private String deleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private Collection<Debtor> debtorCollection;
    @OneToMany(mappedBy = "job")
    private Collection<Payment> paymentCollection;
    @OneToMany(mappedBy = "jobId")
    private Collection<JobDetail> jobDetailCollection;

    public Job() {
    }

    public Job(Long jobId) {
        this.jobId = jobId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getBillTo() throws ParseException { 
        try {
            Long customerid = Long.parseLong(billTo);
            Customer cust = SirsDataSource.dataSource().customerFind(customerid);
            String phone = " <span class='cphone'>(" + cust.getPhoneNumber() + ")</span>";
            billTo = cust.getCustomerName() + phone;
        } catch (Exception e) {
            //billTo is not an existing customer
            try {
                //System.out.println("billto..." + billTo);
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(billTo); //JSON String 
                JSONObject jsonObject = (JSONObject) obj;
                
                String name = (String) jsonObject.get("name");
                String ph = (String) jsonObject.get("phone");
                
                billTo = name + " " + " <span class='cphone'>(" + ph + ")</span>";
            } catch (ParseException parseException) {
            }
        }

        return billTo;
    }

    public void setBillTo(String billTo) { 
        this.billTo = billTo;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLastDeletedDate() {
        return lastDeletedDate;
    }

    public void setLastDeletedDate(Date lastDeletedDate) {
        this.lastDeletedDate = lastDeletedDate;
    }

    public String getDeletionReason() {
        return deletionReason;
    }

    public void setDeletionReason(String deletionReason) {
        this.deletionReason = deletionReason;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @XmlTransient
    public Collection<Debtor> getDebtorCollection() {
        return debtorCollection;
    }

    public void setDebtorCollection(Collection<Debtor> debtorCollection) {
        this.debtorCollection = debtorCollection;
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    @XmlTransient
    public Collection<JobDetail> getJobDetailCollection() {
        return jobDetailCollection;
    }

    public void setJobDetailCollection(Collection<JobDetail> jobDetailCollection) {
        this.jobDetailCollection = jobDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobId != null ? jobId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.Job[ jobId=" + jobId + " ]";
    }
    @Transient
    private Customer customer;
    @Transient
    private Payment payment;

    public Customer getCustomer() throws ParseException {
        Customer cust = null;
        try {
            Long customerid = Long.parseLong(billTo);
            cust = SirsDataSource.dataSource().customerFind(customerid);
        } catch (Exception e) {
            //billTo is not an existing customer
            if (null != billTo) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(billTo); //JSON String
                JSONObject jsonObject = (JSONObject) obj;


                String name = (String) jsonObject.get("name");
                String ph = (String) jsonObject.get("phone");
                String address = (String) jsonObject.get("address");
                String email = (String) jsonObject.get("email");
                cust = new Customer(null, name, ph, address, email);
            }
        }
        return cust;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payment getPayment() {
        payment = SirsDataSource.dataSource().paymentFindByJob(jobId);
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
