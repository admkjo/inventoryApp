/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.ejb.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emma
 */
@Entity
@Table(name = "job_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JobDetail.findAll", query = "SELECT j FROM JobDetail j"),
    @NamedQuery(name = "JobDetail.findByJobDetailId", query = "SELECT j FROM JobDetail j WHERE j.jobDetailId = :jobDetailId"),
    @NamedQuery(name = "JobDetail.findByFileName", query = "SELECT j FROM JobDetail j WHERE j.fileName = :fileName"),
    @NamedQuery(name = "JobDetail.findByMaterialPrice", query = "SELECT j FROM JobDetail j WHERE j.materialPrice = :materialPrice"),
    @NamedQuery(name = "JobDetail.findByWidth", query = "SELECT j FROM JobDetail j WHERE j.width = :width"),
    @NamedQuery(name = "JobDetail.findByHeight", query = "SELECT j FROM JobDetail j WHERE j.height = :height"),
    @NamedQuery(name = "JobDetail.findByMeasure", query = "SELECT j FROM JobDetail j WHERE j.measure = :measure"),
    @NamedQuery(name = "JobDetail.findByQuantity", query = "SELECT j FROM JobDetail j WHERE j.quantity = :quantity"),
    @NamedQuery(name = "JobDetail.findBySubtotal", query = "SELECT j FROM JobDetail j WHERE j.subtotal = :subtotal"),
    @NamedQuery(name = "JobDetail.findByUpdated", query = "SELECT j FROM JobDetail j WHERE j.updated = :updated"),
    @NamedQuery(name = "JobDetail.findByDeleted", query = "SELECT j FROM JobDetail j WHERE j.deleted = :deleted")})
public class JobDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "job_detail_id")
    private Long jobDetailId;
    @Size(max = 255)
    @Column(name = "file_name")
    private String fileName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "material_price")
    private Double materialPrice;
    @Column(name = "width")
    private Double width;
    @Column(name = "height")
    private Double height;
    @Size(max = 2)
    @Column(name = "measure")
    private String measure;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "subtotal")
    private Double subtotal;
    @Size(max = 3)
    @Column(name = "updated")
    private String updated;
    @Size(max = 3)
    @Column(name = "deleted")
    private String deleted;
    @JoinColumn(name = "material", referencedColumnName = "material_id")
    @ManyToOne
    private Material material;
    @JoinColumn(name = "job_id", referencedColumnName = "job_id")
    @ManyToOne
    private Job jobId;

    public JobDetail() {
    }

    public JobDetail(Long jobDetailId) {
        this.jobDetailId = jobDetailId;
    }

    public Long getJobDetailId() {
        return jobDetailId;
    }

    public void setJobDetailId(Long jobDetailId) {
        this.jobDetailId = jobDetailId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Double getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(Double materialPrice) {
        this.materialPrice = materialPrice;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Job getJobId() {
        return jobId;
    }

    public void setJobId(Job jobId) {
        this.jobId = jobId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobDetailId != null ? jobDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JobDetail)) {
            return false;
        }
        JobDetail other = (JobDetail) object;
        if ((this.jobDetailId == null && other.jobDetailId != null) || (this.jobDetailId != null && !this.jobDetailId.equals(other.jobDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.JobDetail[ jobDetailId=" + jobDetailId + " ]";
    }
    
}
