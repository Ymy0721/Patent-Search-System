package org.example.model;

import java.io.Serializable;
import java.util.Date;

public class Patent implements Serializable  {
    private int id;
    private String applicationNumber;
    private String publicationNumber;
    private Date applicationDate;
    private Date publicationDate;
    private String IPCClassification;
    private String applicant;
    private String inventor;
    private String inventionName;
    private String applicantPostalCode;
    private String agent;
    private String agency;
    private String documentType;
    private String applicantCountry;
    private String abstractText;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public java.sql.Date getApplicationDate() {
        return (java.sql.Date) applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public java.sql.Date getPublicationDate() {
        return (java.sql.Date) publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIPCClassification() {
        return IPCClassification;
    }

    public void setIPCClassification(String IPCClassification) {
        this.IPCClassification = IPCClassification;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getInventionName() {
        return inventionName;
    }

    public void setInventionName(String inventionName) {
        this.inventionName = inventionName;
    }

    public String getApplicantPostalCode() {
        return applicantPostalCode;
    }

    public void setApplicantPostalCode(String applicantPostalCode) {
        this.applicantPostalCode = applicantPostalCode;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getApplicantCountry() {
        return applicantCountry;
    }

    public void setApplicantCountry(String applicantCountry) {
        this.applicantCountry = applicantCountry;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public Patent copy() {
        Patent copy = new Patent();
        copy.setApplicationNumber(this.getApplicationNumber());
        copy.setPublicationNumber(this.getPublicationNumber());
        copy.setApplicationDate(this.getApplicationDate());
        copy.setPublicationDate(this.getPublicationDate());
        copy.setIPCClassification(this.getIPCClassification());
        copy.setApplicant(this.getApplicant());
        copy.setInventor(this.getInventor());
        copy.setInventionName(this.getInventionName());
        copy.setApplicantPostalCode(this.getApplicantPostalCode());
        copy.setAgent(this.getAgent());
        copy.setAgency(this.getAgency());
        copy.setDocumentType(this.getDocumentType());
        copy.setApplicantCountry(this.getApplicantCountry());
        copy.setAbstractText(this.getAbstractText());
        return copy;
    }
}
