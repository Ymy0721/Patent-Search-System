package org.example.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.model.Patent;
import org.example.service.PatentService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdvancedSearchController extends SearchController{
    @FXML
    protected TableView<Patent> resultsTable;

    @FXML
    protected TableColumn<Patent, String> inventionNameColumn;
    @FXML
    protected TableColumn<Patent, String> abstractColumn;
    @FXML
    protected TableColumn<Patent, Date> applicationDateColumn;
    @FXML
    protected TableColumn<Patent, Date> publicationDateColumn;
    @FXML
    protected TableColumn<Patent, String> applicationNumberColumn;
    @FXML
    protected TableColumn<Patent, String> publicationNumberColumn;
    @FXML
    protected TableColumn<Patent, String> ipcClassificationColumn;
    @FXML
    protected TableColumn<Patent, String> documentTypeColumn;
    @FXML
    protected TableColumn<Patent, String> inventorColumn;
    @FXML
    protected TableColumn<Patent, String> applicantColumn;
    @FXML
    protected TableColumn<Patent, String> applicantCountryColumn;
    @FXML
    protected TableColumn<Patent, String> applicantPostalCodeColumn;
    @FXML
    protected TableColumn<Patent, String> agentColumn;
    @FXML
    protected TableColumn<Patent, String> agencyColumn;

    @FXML
    private Label resultsCountLabel;
    @FXML
    private PatentService patentService = new PatentService();
    @FXML
    private UndoManager undoManager = new UndoManager();

    @FXML
    private CheckBox patentTypeInvention;
    @FXML
    private CheckBox patentTypeUtilityModel;
    @FXML
    private CheckBox patentTypeDesign;
    @FXML
    private CheckBox countryCN;
    @FXML
    private CheckBox countryUS;
    @FXML
    private CheckBox countryJP;
    @FXML
    private CheckBox countryDE;
    @FXML
    private CheckBox countryKR;
    @FXML
    private CheckBox countrySE;
    @FXML
    private CheckBox countryFR;
    @FXML
    private CheckBox countryFI;
    @FXML
    private CheckBox countryIL;
    @FXML
    private CheckBox countryNL;
    @FXML
    private CheckBox countryKY;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField applicationNumberField;
    @FXML
    private TextField publicationNumberField;
    @FXML
    private TextField IPCClassificationField;
    @FXML
    private TextField agentField;
    @FXML
    private TextField agencyField;
    @FXML
    private TextField applicantPostalCodeField;
    @FXML
    private TextField inventorField;
    @FXML
    private TextField inventionNameField;
    @FXML
    private TextField abstractTextField;

    // Add more TextField for other search fields
    //继承父类的initialize方法
    public void initialize() {
        super.initialize();
        // Add more initialization code
    }

    // 重写父类的handleSearch方法
    @Override
    public void handleSearch() {
        try {
            String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
            String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
            List<String> patentTypes = new ArrayList<>();
            if (patentTypeInvention.isSelected()) {
                patentTypes.add("发明");
            }
            if (patentTypeUtilityModel.isSelected()) {
                patentTypes.add("实用新型");
            }
            if (patentTypeDesign.isSelected()) {
                patentTypes.add("外观设计");
            }

            List<String> countries = new ArrayList<>();
            if (countryCN.isSelected()) {
                countries.add("CN");
            }
            if (countryUS.isSelected()) {
                countries.add("US");
            }
            if (countryJP.isSelected()) {
                countries.add("JP");
            }
            if (countryDE.isSelected()) {
                countries.add("DE");
            }
            if (countryKR.isSelected()) {
                countries.add("KR");
            }
            if (countrySE.isSelected()) {
                countries.add("SE");
            }
            if (countryFR.isSelected()) {
                countries.add("FR");
            }
            if (countryFI.isSelected()) {
                countries.add("FI");
            }
            if (countryIL.isSelected()) {
                countries.add("IL");
            }
            if (countryNL.isSelected()) {
                countries.add("NL");
            }
            if (countryKY.isSelected()) {
                countries.add("KY");
            }
            String applicationNumber = (applicationNumberField != null && !applicationNumberField.getText().isEmpty()) ? applicationNumberField.getText() : null;
            String publicationNumber = (publicationNumberField != null && !publicationNumberField.getText().isEmpty()) ? publicationNumberField.getText() : null;
            String IPCClassification = (IPCClassificationField != null && !IPCClassificationField.getText().isEmpty()) ? IPCClassificationField.getText() : null;
            String agent = (agentField != null && !agentField.getText().isEmpty()) ? agentField.getText() : null;
            String agency = (agencyField != null && !agencyField.getText().isEmpty()) ? agencyField.getText() : null;
            String applicantPostalCode = (applicantPostalCodeField != null && !applicantPostalCodeField.getText().isEmpty()) ? applicantPostalCodeField.getText() : null;
            String inventor = (inventorField != null && !inventorField.getText().isEmpty()) ? inventorField.getText() : null;
            String inventionName = (inventionNameField != null && !inventionNameField.getText().isEmpty()) ? inventionNameField.getText() : null;
            String abstractText = (abstractTextField != null && !abstractTextField.getText().isEmpty()) ? abstractTextField.getText() : null;

            // 检查所有的检索项是否都为空
            if ((applicationNumber == null || applicationNumber.trim().isEmpty()) &&
                    (publicationNumber == null || publicationNumber.trim().isEmpty()) &&
                    (IPCClassification == null || IPCClassification.trim().isEmpty()) &&
                    (agent == null || agent.trim().isEmpty()) &&
                    (agency == null || agency.trim().isEmpty()) &&
                    (applicantPostalCode == null || applicantPostalCode.trim().isEmpty()) &&
                    (inventor == null || inventor.trim().isEmpty()) &&
                    (inventionName == null || inventionName.trim().isEmpty()) &&
                    (abstractText == null || abstractText.trim().isEmpty())) {
                // 如果所有的检索项都为空，显示红色的提示文字
                resultsCountLabel.setText("请输入检索词！");
                resultsCountLabel.setTextFill(Color.RED);
                return;
            }

            List<Patent> results = patentService.advancedSearchPatents(startDate, endDate, patentTypes, countries, applicationNumber, publicationNumber, IPCClassification, agent, agency, applicantPostalCode, inventor, inventionName, abstractText);
            resultsTable.getItems().setAll(results);
            resultsCountLabel.setText("命中结果数: " + results.size());
            resultsCountLabel.setTextFill(Color.RED);

            allResults = FXCollections.observableArrayList(results);
            updatePagination();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void handleNew() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdvancedSearch.fxml"));
            Stage newSearchStage = new Stage();
            newSearchStage.setScene(new Scene(loader.load()));
            newSearchStage.setTitle("高级检索系统");
            newSearchStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    @FXML
    public void handleSimpleSearch(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Search.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("专利检索系统");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}