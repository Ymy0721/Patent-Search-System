package org.example.dao;

import com.sun.javafx.scene.control.SelectedCellsMap;
import javafx.scene.control.TablePositionBase;
import org.example.model.Patent;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatentDao {
    public List<Patent> searchPatents(String keyword, String startDate, String endDate) throws SQLException {
        List<Patent> patents = new ArrayList<>();
        String sql = "SELECT * FROM patents WHERE (InventionName LIKE ? OR MATCH(Abstract) AGAINST(? IN NATURAL LANGUAGE MODE))";
        boolean hasStartDate = startDate != null;
        boolean hasEndDate = endDate != null;

        if (hasStartDate) {
            sql += " AND ApplicationDate >= ?";
        }
        if (hasEndDate) {
            sql += " AND ApplicationDate <= ?";
        }

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            int index = 3;
            if (hasStartDate) {
                stmt.setString(index++, startDate);
            }
            if (hasEndDate) {
                stmt.setString(index++, endDate);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patent patent = new Patent();
                patent.setId(rs.getInt("id"));
                patent.setApplicationNumber(rs.getString("ApplicationNumber"));
                patent.setPublicationNumber(rs.getString("PublicationNumber"));
                patent.setApplicationDate(rs.getDate("ApplicationDate"));
                patent.setPublicationDate(rs.getDate("PublicationDate"));
                patent.setIPCClassification(rs.getString("IPCClassification"));
                patent.setApplicant(rs.getString("Applicant"));
                patent.setInventor(rs.getString("Inventor"));
                patent.setInventionName(rs.getString("InventionName"));
                patent.setApplicantPostalCode(rs.getString("ApplicantPostalCode"));
                patent.setAgent(rs.getString("Agent"));
                patent.setAgency(rs.getString("Agency"));
                patent.setDocumentType(rs.getString("DocumentType"));
                patent.setApplicantCountry(rs.getString("ApplicantCountry"));
                patent.setAbstractText(rs.getString("Abstract"));
                patents.add(patent);
            }
        }
        return patents;
    }

    public List<Patent> advancedSearchPatents(String startDate, String endDate, List<String> patentTypes, List<String> countries, String applicationNumber, String publicationNumber, String IPCClassification, String agent, String agency, String applicantPostalCode, String inventor, String inventionName, String abstractText) throws SQLException {
        List<Patent> patents = new ArrayList<>();
        String sql = "SELECT * FROM patents WHERE 1=1";
        boolean hasStartDate = startDate != null;
        boolean hasEndDate = endDate != null;

        if (hasStartDate) {
            sql += " AND ApplicationDate >= ?";
        }
        if (hasEndDate) {
            sql += " AND ApplicationDate <= ?";
        }

        if (!patentTypes.isEmpty()) {
            sql += " AND DocumentType IN (" + String.join(", ", Collections.nCopies(patentTypes.size(), "?")) + ")";
        }
        if (!countries.isEmpty()) {
            sql += " AND ApplicantCountry IN (" + String.join(", ", Collections.nCopies(countries.size(), "?")) + ")";
        }
        if (applicationNumber != null) {
            sql += " AND ApplicationNumber LIKE ?";
        }
        if (publicationNumber != null) {
            sql += " AND PublicationNumber LIKE ?";
        }
        if (IPCClassification != null) {
            sql += " AND IPCClassification LIKE ?";
        }
        if (agent != null) {
            sql += " AND Agent LIKE ?";
        }
        if (agency != null) {
            sql += " AND Agency LIKE ?";
        }
        if (applicantPostalCode != null) {
            sql += " AND ApplicantPostalCode LIKE ?";
        }
        if (inventor != null) {
            sql += " AND Inventor LIKE ?";
        }
        if (inventionName != null) {
            sql += " AND MATCH(InventionName) AGAINST(? IN NATURAL LANGUAGE MODE)";
        }
        if (abstractText != null) {
            sql += " AND MATCH(Abstract) AGAINST(? IN NATURAL LANGUAGE MODE)";
        }

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            if (hasStartDate) {
                stmt.setString(index++, startDate);
            }
            if (hasEndDate) {
                stmt.setString(index++, endDate);
            }
            for (String patentType : patentTypes) {
                stmt.setString(index++, patentType);
            }
            for (String country : countries) {
                stmt.setString(index++, country);
            }
            if (applicationNumber != null) {
                stmt.setString(index++, "%" + applicationNumber + "%");
            }
            if (publicationNumber != null) {
                stmt.setString(index++, "%" + publicationNumber + "%");
            }
            if (IPCClassification != null) {
                stmt.setString(index++, IPCClassification + "%");
            }
            if (agent != null) {
                stmt.setString(index++, agent + "%");
            }
            if (agency != null) {
                stmt.setString(index++, agency + "%");
            }
            if (applicantPostalCode != null) {
                stmt.setString(index++, applicantPostalCode + "%");
            }
            if (inventor != null) {
                stmt.setString(index++, inventor + "%");
            }
            if (inventionName != null) {
                stmt.setString(index++, inventionName);
            }
            if (abstractText != null) {
                stmt.setString(index++, abstractText);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patent patent = new Patent();
                patent.setId(rs.getInt("id"));
                patent.setApplicationNumber(rs.getString("ApplicationNumber"));
                patent.setPublicationNumber(rs.getString("PublicationNumber"));
                patent.setApplicationDate(rs.getDate("ApplicationDate"));
                patent.setPublicationDate(rs.getDate("PublicationDate"));
                patent.setIPCClassification(rs.getString("IPCClassification"));
                patent.setApplicant(rs.getString("Applicant"));
                patent.setInventor(rs.getString("Inventor"));
                patent.setInventionName(rs.getString("InventionName"));
                patent.setApplicantPostalCode(rs.getString("ApplicantPostalCode"));
                patent.setAgent(rs.getString("Agent"));
                patent.setAgency(rs.getString("Agency"));
                patent.setDocumentType(rs.getString("DocumentType"));
                patent.setApplicantCountry(rs.getString("ApplicantCountry"));
                patent.setAbstractText(rs.getString("Abstract"));
                patents.add(patent);
            }
        }
        return patents;
    }

    public void insertPatent(Patent patent) throws SQLException {
        String sql = "INSERT INTO patents (ApplicationNumber, PublicationNumber, ApplicationDate, PublicationDate, IPCClassification, Applicant, Inventor, InventionName, ApplicantPostalCode, Agent, Agency, DocumentType, ApplicantCountry, Abstract) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patent.getApplicationNumber());
            stmt.setString(2, patent.getPublicationNumber());
            stmt.setDate(3, patent.getApplicationDate());
            stmt.setDate(4, patent.getPublicationDate());
            stmt.setString(5, patent.getIPCClassification());
            stmt.setString(6, patent.getApplicant());
            stmt.setString(7, patent.getInventor());
            stmt.setString(8, patent.getInventionName());
            stmt.setString(9, patent.getApplicantPostalCode());
            stmt.setString(10, patent.getAgent());
            stmt.setString(11, patent.getAgency());
            stmt.setString(12, patent.getDocumentType());
            stmt.setString(13, patent.getApplicantCountry());
            stmt.setString(14, patent.getAbstractText());
            stmt.executeUpdate();
        }
    }

    public void updatePatent(Patent patent) throws SQLException {
        String sql = "UPDATE patents SET ApplicationNumber = ?, PublicationNumber = ?, ApplicationDate = ?, PublicationDate = ?, IPCClassification = ?, Applicant = ?, Inventor = ?, InventionName = ?, ApplicantPostalCode = ?, Agent = ?, Agency = ?, DocumentType = ?, ApplicantCountry = ?, Abstract = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patent.getApplicationNumber());
            stmt.setString(2, patent.getPublicationNumber());
            stmt.setDate(3, patent.getApplicationDate());
            stmt.setDate(4, patent.getPublicationDate());
            stmt.setString(5, patent.getIPCClassification());
            stmt.setString(6, patent.getApplicant());
            stmt.setString(7, patent.getInventor());
            stmt.setString(8, patent.getInventionName());
            stmt.setString(9, patent.getApplicantPostalCode());
            stmt.setString(10, patent.getAgent());
            stmt.setString(11, patent.getAgency());
            stmt.setString(12, patent.getDocumentType());
            stmt.setString(13, patent.getApplicantCountry());
            stmt.setString(14, patent.getAbstractText());
            stmt.setInt(15, patent.getId());
            stmt.executeUpdate();
        }
    }
    public void deletePatent(Patent patent) throws SQLException {
        String sql = "DELETE FROM patents WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patent.getId());
            stmt.executeUpdate();
        }
    }
    public boolean patentExists(Patent patent) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patents WHERE applicationNumber = ? AND publicationNumber = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patent.getApplicationNumber());
            stmt.setString(2, patent.getPublicationNumber());
            // Set more parameters based on your unique fields

            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        }
    }
}
