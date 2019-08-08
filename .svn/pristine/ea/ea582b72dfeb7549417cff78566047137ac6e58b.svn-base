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
@Table(name = "customer_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerGroup.findAll", query = "SELECT c FROM CustomerGroup c"),
    @NamedQuery(name = "CustomerGroup.findByGroupId", query = "SELECT c FROM CustomerGroup c WHERE c.groupId = :groupId"),
    @NamedQuery(name = "CustomerGroup.findByGroupName", query = "SELECT c FROM CustomerGroup c WHERE c.groupName = :groupName")})
public class CustomerGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "group_id")
    private Integer groupId;
    @Size(max = 255)
    @Column(name = "group_name")
    private String groupName;
    @OneToMany(mappedBy = "groupId")
    private Collection<CustomerMaterialGroup> customerMaterialGroupCollection;

    public CustomerGroup() {
    }

    public CustomerGroup(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @XmlTransient
    public Collection<CustomerMaterialGroup> getCustomerMaterialGroupCollection() {
        return customerMaterialGroupCollection;
    }

    public void setCustomerMaterialGroupCollection(Collection<CustomerMaterialGroup> customerMaterialGroupCollection) {
        this.customerMaterialGroupCollection = customerMaterialGroupCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerGroup)) {
            return false;
        }
        CustomerGroup other = (CustomerGroup) object;
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.CustomerGroup[ groupId=" + groupId + " ]";
    }
    
}
