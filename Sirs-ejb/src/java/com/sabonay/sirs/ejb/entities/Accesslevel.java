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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emma
 */
@Entity
@Table(name = "accesslevel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accesslevel.findAll", query = "SELECT a FROM Accesslevel a"),
    @NamedQuery(name = "Accesslevel.findByAcid", query = "SELECT a FROM Accesslevel a WHERE a.acid = :acid"),
    @NamedQuery(name = "Accesslevel.findByLevelname", query = "SELECT a FROM Accesslevel a WHERE a.levelname = :levelname"),
    @NamedQuery(name = "Accesslevel.findByLeveldesc", query = "SELECT a FROM Accesslevel a WHERE a.leveldesc = :leveldesc")})
public class Accesslevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "acid")
    private String acid;
    @Size(max = 255)
    @Column(name = "levelname")
    private String levelname;
    @Size(max = 255)
    @Column(name = "leveldesc")
    private String leveldesc;
    @OneToMany(mappedBy = "accessLevel")
    private Collection<AdminUser> adminUserCollection;

    public Accesslevel() {
    }

    public Accesslevel(String acid) {
        this.acid = acid;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getLeveldesc() {
        return leveldesc;
    }

    public void setLeveldesc(String leveldesc) {
        this.leveldesc = leveldesc;
    }

    @XmlTransient
    public Collection<AdminUser> getAdminUserCollection() {
        return adminUserCollection;
    }

    public void setAdminUserCollection(Collection<AdminUser> adminUserCollection) {
        this.adminUserCollection = adminUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acid != null ? acid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accesslevel)) {
            return false;
        }
        Accesslevel other = (Accesslevel) object;
        if ((this.acid == null && other.acid != null) || (this.acid != null && !this.acid.equals(other.acid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.Accesslevel[ acid=" + acid + " ]";
    }
    
}
