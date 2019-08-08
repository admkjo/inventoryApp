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
@Table(name = "paper_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaperType.findAll", query = "SELECT p FROM PaperType p"),
    @NamedQuery(name = "PaperType.findByPaperId", query = "SELECT p FROM PaperType p WHERE p.paperId = :paperId"),
    @NamedQuery(name = "PaperType.findByPaperName", query = "SELECT p FROM PaperType p WHERE p.paperName = :paperName"),
    @NamedQuery(name = "PaperType.findByPaperWidth", query = "SELECT p FROM PaperType p WHERE p.paperWidth = :paperWidth"),
    @NamedQuery(name = "PaperType.findByPaperHeight", query = "SELECT p FROM PaperType p WHERE p.paperHeight = :paperHeight"),
    @NamedQuery(name = "PaperType.findByPaperMeasure", query = "SELECT p FROM PaperType p WHERE p.paperMeasure = :paperMeasure")})
public class PaperType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "paper_id")
    private Integer paperId;
    @Size(max = 50)
    @Column(name = "paper_name")
    private String paperName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "paper_width")
    private Double paperWidth;
    @Column(name = "paper_height")
    private Double paperHeight;
    @Size(max = 6)
    @Column(name = "paper_measure")
    private String paperMeasure;

    public PaperType() {
    }

    public PaperType(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Double getPaperWidth() {
        return paperWidth;
    }

    public void setPaperWidth(Double paperWidth) {
        this.paperWidth = paperWidth;
    }

    public Double getPaperHeight() {
        return paperHeight;
    }

    public void setPaperHeight(Double paperHeight) {
        this.paperHeight = paperHeight;
    }

    public String getPaperMeasure() {
        return paperMeasure;
    }

    public void setPaperMeasure(String paperMeasure) {
        this.paperMeasure = paperMeasure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paperId != null ? paperId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaperType)) {
            return false;
        }
        PaperType other = (PaperType) object;
        if ((this.paperId == null && other.paperId != null) || (this.paperId != null && !this.paperId.equals(other.paperId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.sirs.ejb.entities.PaperType[ paperId=" + paperId + " ]";
    }
    
}
