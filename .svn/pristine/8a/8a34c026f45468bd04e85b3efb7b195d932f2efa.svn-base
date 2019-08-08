/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.controller;

import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Job;
import com.sabonay.sirs.web.common.utilities.JSFUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author emma
 *
 */ 
@ManagedBean
@SessionScoped
public class JobController implements Serializable{
    private List<Job> jobList = new ArrayList<Job>();
    private Job job = new Job();
    private Job selectedJob = new Job();
    private Boolean jobIsAdd = true;
    
     //<editor-fold  desc="Job Button Actions" >  
    public void jobAdd() {
        try {
            //job.setStatus('1');
            SirsDataSource.dataSource().jobCreate(job);
            JSFUtility.infoMessage("Job added Scessfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Add New Job");
        } 
    }
 
    public void jobUpdate() {
        try {
            SirsDataSource.dataSource().jobUpdate(job);
            jobReset();
            JSFUtility.infoMessage("Job Updated Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Update Job");
        }
    }
    
    public void handleJobSelection(){  
        job = selectedJob;
        jobIsAdd = false;
    }

    public String jobReset() {
        job = new Job();
        jobIsAdd = true;
        return "";
    }

    public String jobDelete() {
        try {
            SirsDataSource.dataSource().jobDelete(job);
            jobReset();
            JSFUtility.infoMessage("Job Deleted Successfully");
        } catch (Exception e) {
            JSFUtility.warnMessage("Unable to Delete Job");
        }
        return "";
    }
    //</editor-fold >

    public List<Job> getJobList() {
        //jobList = SirsDataSource.dataSource().jobGetAll(true,true);
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Boolean getJobIsAdd() {
        return jobIsAdd;
    }

    public void setJobIsAdd(Boolean jobIsAdd) {
        this.jobIsAdd = jobIsAdd;
    }

    public Job getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Job selectedJob) {
        this.selectedJob = selectedJob;
    }
}