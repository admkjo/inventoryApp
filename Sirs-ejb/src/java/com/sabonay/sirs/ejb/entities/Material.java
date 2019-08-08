/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.ejb.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emma
 */
@Entity
@Table(name = "material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m"),
    @NamedQuery(name = "Material.findByMaterialId", query = "SELECT m FROM Material m WHERE m.materialId = :materialId"),
    @NamedQuery(name = "Material.findByMaterialName", query = "SELECT m FROM Material m WHERE m.materialName = :materialName"),
    @NamedQuery(name = "Material.findByMaterialType", query = "SELECT m FROM Material m WHERE m.materialType = :materialType"),
    @NamedQuery(name = "Material.findByMaterialCost", query = "SELECT m FROM Material m WHERE m.materialCost = :materialCost"),
    @NamedQuery(name = "Material.findByStatus", query = "SELECT m FROM Material m WHERE m.status = :status")})
public class Material implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "material_id")
    private Integer materialId;
    @Size(max = 255)
    @Column(name = "material_name")
    private String materialName;
    @Size(max = 2)
    @Column(name = "material_type")
    private String materialType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "material_cost")
    private Double materialCost;
    @Column(name = "status")
    private Character status;
    @OneToMany(mappedBy = "material")
    private Collection<CustomerMaterialGroup> customerMaterialGroupCollection;
    @OneToMany(mappedBy = "material")
    private Collection<JobDetail> jobDetailCollection;

    public Material() {
    }

    public Material(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(Double materialCost) {
        this.materialCost = materialCost;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<CustomerMaterialGroup> getCustomerMaterialGroupCollection() {
        return customerMaterialGroupCollection;
    }

    public void setCustomerMaterialGroupCollection(Collection<CustomerMaterialGroup> customerMaterialGroupCollection) {
        this.customerMaterialGroupCollection = customerMaterialGroupCollection;
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
        hash += (materialId != null ? materialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.materialId == null && other.materialId != null) || (this.materialId != null && !this.materialId.equals(other.materialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.Material[ materialId=" + materialId + " ]";
    }
    
}
