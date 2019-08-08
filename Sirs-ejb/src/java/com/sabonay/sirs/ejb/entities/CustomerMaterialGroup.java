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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emma
 */
@Entity
@Table(name = "customer_material_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerMaterialGroup.findAll", query = "SELECT c FROM CustomerMaterialGroup c"),
    @NamedQuery(name = "CustomerMaterialGroup.findByGroupMaterialId", query = "SELECT c FROM CustomerMaterialGroup c WHERE c.groupMaterialId = :groupMaterialId"),
    @NamedQuery(name = "CustomerMaterialGroup.findByNewPrice", query = "SELECT c FROM CustomerMaterialGroup c WHERE c.newPrice = :newPrice"),
    @NamedQuery(name = "CustomerMaterialGroup.findByStatus", query = "SELECT c FROM CustomerMaterialGroup c WHERE c.status = :status")})
public class CustomerMaterialGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "group_material_id")
    private Integer groupMaterialId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "new_price")
    private Double newPrice;
    @Column(name = "status")
    private Character status;
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    @ManyToOne
    private CustomerGroup groupId;
    @JoinColumn(name = "material", referencedColumnName = "material_id")
    @ManyToOne
    private Material material;

    public CustomerMaterialGroup() {
    }

    public CustomerMaterialGroup(Integer groupMaterialId) {
        this.groupMaterialId = groupMaterialId;
    }

    public Integer getGroupMaterialId() {
        return groupMaterialId;
    }

    public void setGroupMaterialId(Integer groupMaterialId) {
        this.groupMaterialId = groupMaterialId;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public CustomerGroup getGroupId() {
        return groupId;
    }

    public void setGroupId(CustomerGroup groupId) {
        this.groupId = groupId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupMaterialId != null ? groupMaterialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerMaterialGroup)) {
            return false;
        }
        CustomerMaterialGroup other = (CustomerMaterialGroup) object;
        if ((this.groupMaterialId == null && other.groupMaterialId != null) || (this.groupMaterialId != null && !this.groupMaterialId.equals(other.groupMaterialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.CustomerMaterialGroup[ groupMaterialId=" + groupMaterialId + " ]";
    }
    
}
