package org.example.service;

import org.example.dao.PatentDao;
import org.example.model.Patent;

import java.sql.SQLException;
import java.util.List;

public class PatentService {
    private PatentDao patentDao = new PatentDao();

    public List<Patent> searchPatents(String keyword, String startDate, String endDate) throws SQLException {
        return patentDao.searchPatents(keyword, startDate, endDate);
    }
    public List<Patent> advancedSearchPatents(String startDate, String endDate, List<String> patentType, List<String> country, String applicationNumber, String publicationNumber, String IPCClassification, String agent, String agency, String applicantPostalCode, String inventor, String inventionName, String abstractText) throws SQLException {
        return patentDao.advancedSearchPatents(startDate, endDate, patentType, country, applicationNumber, publicationNumber, IPCClassification, agent, agency, applicantPostalCode, inventor, inventionName, abstractText);
    }
    public void insertPatent(Patent patent) throws SQLException {
        patentDao.insertPatent(patent);

    }

    public void updatePatent(Patent patent) throws SQLException {
        patentDao.updatePatent(patent);
    }

    public void deletePatent(Patent patent) throws SQLException {
        patentDao.deletePatent(patent);
    }
    public boolean patentExists(Patent patent) throws SQLException {
        return patentDao.patentExists(patent);
    }
}
