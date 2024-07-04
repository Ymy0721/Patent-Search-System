package org.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.model.Patent;

public class PatentDetailsController {
    @FXML
    private TextField applicationNumberValue;
    @FXML
    private TextField publicationNumberValue;
    @FXML
    private TextField applicationDateValue;
    @FXML
    private TextField publicationDateValue;
    @FXML
    private TextField ipcClassificationValue;
    @FXML
    private TextField documentTypeValue;
    @FXML
    private TextField inventionNameValue;
    @FXML
    private TextField inventorValue;
    @FXML
    private TextField applicantValue;
    @FXML
    private TextField applicantCountryValue;
    @FXML
    private TextField applicantPostalCodeValue;
    @FXML
    private TextField agentValue;
    @FXML
    private TextField agencyValue;
    @FXML
    private TextArea abstractValue;

    public void setPatent(Patent patent) {
        applicationNumberValue.setText(patent.getApplicationNumber());
        publicationNumberValue.setText(patent.getPublicationNumber());
        applicationDateValue.setText(patent.getApplicationDate().toString());
        publicationDateValue.setText(patent.getPublicationDate().toString());
        ipcClassificationValue.setText(patent.getIPCClassification());
        documentTypeValue.setText(patent.getDocumentType());
        inventionNameValue.setText(patent.getInventionName());
        inventorValue.setText(patent.getInventor());
        applicantValue.setText(patent.getApplicant());
        applicantCountryValue.setText(patent.getApplicantCountry());
        abstractValue.setText(patent.getAbstractText());
        applicantPostalCodeValue.setText(patent.getApplicantPostalCode());
        agentValue.setText(patent.getAgent());
        agencyValue.setText(patent.getAgency());
    }
}
