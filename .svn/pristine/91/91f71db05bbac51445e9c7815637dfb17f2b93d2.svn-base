/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emma
 */
@Entity
@Table(name = "debtor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Debtor.findAll", query = "SELECT d FROM Debtor d"),
    @NamedQuery(name = "Debtor.findByDebtorId", query = "SELECT d FROM Debtor d WHERE d.debtorId = :debtorId"),
    @NamedQuery(name = "Debtor.findByBalance", query = "SELECT d FROM Debtor d WHERE d.balance = :balance"),
    @NamedQuery(name = "Debtor.findByDueDate", query = "SELECT d FROM Debtor d WHERE d.dueDate = :dueDate")})
public class Debtor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "debtor_id")
    private Long debtorId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance")
    private Double balance;
    @Column(name = "due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @JoinColumn(name = "job", referencedColumnName = "job_id")
    @ManyToOne(optional = false)
    private Job job;
    @JoinColumn(name = "customer", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customer;

    public Debtor() {
    }

    public Debtor(Long debtorId) {
        this.debtorId = debtorId;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (debtorId != null ? debtorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Debtor)) {
            return false;
        }
        Debtor other = (Debtor) object;
        if ((this.debtorId == null && other.debtorId != null) || (this.debtorId != null && !this.debtorId.equals(other.debtorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.Debtor[ debtorId=" + debtorId + " ]";
    }
    
}
