package org.example.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.example.model.Patent;
import org.example.service.PatentService;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PatentManageController extends AdvancedSearchController {
    private PatentService patentService;

    @FXML
    private Button searchButton;

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<String> editModeComboBox;

    private String currentEditMode;

    @FXML
    private TableView<Patent> resultsTable; // 请将?替换为你的数据类型


    public PatentManageController() {
        this.patentService = new PatentService();
    }

    //继承父类的initialize方法
    public void initialize() {
        super.initialize();

        // Set the default edit mode
        editModeComboBox.setValue("检索专利");
        handleEditModeChange(null);
    }
    @Override
    public void handleNew() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Management.fxml"));
            Stage newSearchStage = new Stage();
            newSearchStage.setScene(new Scene(loader.load()));
            newSearchStage.setTitle("专利管理系统");
            newSearchStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditModeChange(ActionEvent event) {
        currentEditMode = editModeComboBox.getValue();
        switch (currentEditMode) {
            case "检索专利":
                // Handle search mode
                // Enable the search button
                searchButton.setDisable(false);
                // Hide the submit button
                submitButton.setVisible(false);
                break;
            case "插入专利":
                // Handle insert mode
                // Initialize the results table
                resultsTable.getItems().clear();
                // Add 5 empty editable rows
                for (int i = 0; i < 20; i++) {
                    resultsTable.getItems().add(new Patent());
                }
                // Make the table editable
                makeColumnsEditable();
                // Make the applicationNumber, publicationNumber, applicationDate, and publicationDate columns editable
                applicationNumberColumn.setEditable(true);
                publicationNumberColumn.setEditable(true);
                applicationDateColumn.setEditable(true);
                publicationDateColumn.setEditable(true);
                // Disable the search button
                searchButton.setDisable(true);
                // Show the submit button
                submitButton.setVisible(true);
                break;
            case "修改专利":
                // Handle update mode
                // Make the table editable
                makeColumnsEditable();
                // Make the applicationNumber, publicationNumber, applicationDate, and publicationDate columns non-editable
                applicationNumberColumn.setEditable(false);
                publicationNumberColumn.setEditable(false);
                applicationDateColumn.setEditable(false);
                publicationDateColumn.setEditable(false);
                // Enable the search button
                searchButton.setDisable(false);
                // Show the submit button
                submitButton.setVisible(true);
                break;
            case "删除专利":
                // Handle delete mode
                // Cancel any ongoing edits
                resultsTable.edit(-1, null);
                // Make the table non-editable
                resultsTable.setEditable(false);
                // Enable the search button
                searchButton.setDisable(false);
                // Show the submit button
                submitButton.setVisible(true);
                break;
        }
    }

    @FXML
    public void handleSubmit(ActionEvent event) {
        if (currentEditMode == null || currentEditMode.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "请选择编辑模式！");
            return;
        }
        ObservableList<Patent> items = resultsTable.getItems();
        switch (currentEditMode) {
            case "插入专利":
                // Submit insert action
                List<Patent> nonEmptyItems = new ArrayList<>();
                for (Patent item : items) {
                    // Check if the patent is non-empty
                    if ((item.getApplicationNumber() != null && !item.getApplicationNumber().isEmpty()) &&
                            (item.getPublicationNumber() != null && !item.getPublicationNumber().isEmpty()) &&
                            (item.getApplicationDate() != null) &&
                            (item.getPublicationDate() != null)
                    ) {
                        nonEmptyItems.add(item);
                    }
                }
                if (nonEmptyItems.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "申请号、公开号、申请时间、公开时间不能为空！");
                } else {
                    // Check if any item already exists in the database
                    boolean isDuplicate = false;
                    for (Patent item : nonEmptyItems) {
                        try {
                            if (patentService.patentExists(item)) {
                                isDuplicate = true;
                                break;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    if (isDuplicate) {
                        showAlert(Alert.AlertType.ERROR, "您插入的专利已存在，请勿重复插入！");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "您确定要插入" + nonEmptyItems.size() + "条专利吗？");
                        alert.setHeaderText(null);  // 移除header
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {

                                for (Patent item : nonEmptyItems) {
                                    try {
                                        // 检查日期是否为null，然后再转换
                                        java.util.Date applicationDate = item.getApplicationDate() != null ?
                                                new java.util.Date(item.getApplicationDate().getTime()) : null;
                                        java.util.Date publicationDate = item.getPublicationDate() != null ?
                                                new java.util.Date(item.getPublicationDate().getTime()) : null;

                                        // 然后设置这些日期到item中（如果它们不是null）
                                        if (applicationDate != null) item.setApplicationDate(new java.sql.Date(applicationDate.getTime()));
                                        if (publicationDate != null) item.setPublicationDate(new java.sql.Date(publicationDate.getTime()));

                                        patentService.insertPatent(item);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }
                break;
            case "修改专利":
                // Submit update action
                if (items.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "没有可以修改的数据！");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "您确定要修改专利吗？");
                    alert.setHeaderText(null);  // 移除header
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // Update the items in the database
                            for (Patent patent : items) {
                                try {
                                    patentService.updatePatent(patent);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                break;
            case "删除专利":
                // Submit delete action
                ObservableList<Patent> selectedItems = resultsTable.getSelectionModel().getSelectedItems();

                if (selectedItems.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "没有选中任何专利进行删除操作！");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "您确定要删除选中的" + selectedItems.size() + "条专利吗？");
                    alert.setHeaderText(null);  // 移除header
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            for (Patent patent : selectedItems) {
                                try {
                                    patentService.deletePatent(patent);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            resultsTable.getItems().removeAll(selectedItems);
                        }
                    });
                }
                break;
        }
    }

    StringConverter<java.util.Date> dateConverter = new StringConverter<java.util.Date>() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public String toString(java.util.Date date) {
            if (date != null) {
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
            } else {
                return "";
            }
        }

        @Override
        public java.util.Date fromString(String string) {
            if (string != null && !string.isEmpty()) {
                LocalDate date = LocalDate.parse(string, formatter);
                return java.sql.Date.valueOf(date);
            } else {
                return null;
            }
        }
    };
    public void makeColumnsEditable() {
        // Make the TableView editable
        resultsTable.setEditable(true);


        // Make the columns editable
        Callback<TableColumn<Patent, java.util.Date>, TableCell<Patent, java.util.Date>> cellFactory
                = DateEditingCell::new;

        applicationDateColumn.setCellFactory(cellFactory);
        applicationDateColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setApplicationDate(event.getNewValue());
        });

        publicationDateColumn.setCellFactory(cellFactory);
        publicationDateColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setPublicationDate(event.getNewValue());
        });



        applicationNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        applicationNumberColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setApplicationNumber(event.getNewValue());
        });

        publicationNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        publicationNumberColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setPublicationNumber(event.getNewValue());
        });

        inventionNameColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        inventionNameColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setInventionName(event.getNewValue());
        });

        abstractColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        abstractColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setAbstractText(event.getNewValue());
        });

        ipcClassificationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        ipcClassificationColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setIPCClassification(event.getNewValue());
        });

        documentTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        documentTypeColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setDocumentType(event.getNewValue());
        });

        inventorColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        inventorColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setInventor(event.getNewValue());
        });

        applicantColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        applicantColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setApplicant(event.getNewValue());
        });

        applicantCountryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        applicantCountryColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setApplicantCountry(event.getNewValue());
        });

        applicantPostalCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        applicantPostalCodeColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setApplicantPostalCode(event.getNewValue());
        });

        agentColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        agentColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setAgent(event.getNewValue());
        });

        agencyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        agencyColumn.setOnEditCommit(event -> {
            Patent patent = event.getRowValue();
            patent.setAgency(event.getNewValue());
        });
    }
}

class DateEditingCell extends TableCell<Patent, java.util.Date> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DatePicker datePicker = new DatePicker();

    public DateEditingCell(TableColumn<Patent, java.util.Date> column) {
        this.datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return object.format(formatter);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        });

        this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isEditing()) {
                commitEdit(newValue == null ? null : java.sql.Date.valueOf(newValue));
            }
        });
    }

    @Override
    public void commitEdit(java.util.Date newValue) {
        java.sql.Date sqlDate = newValue != null ? new java.sql.Date(newValue.getTime()) : null;
        super.commitEdit(sqlDate);

        Patent patent = getTableView().getItems().get(getIndex());
        patent.setApplicationDate(sqlDate);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        datePicker.setValue(getItem() == null ? null : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        setGraphic(datePicker);

        // Add a focus listener to the date picker
        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // If the date picker loses focus, commit the edit
            if (!newValue) {
                commitEdit(datePicker.getValue() == null ? null : java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        });
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(java.util.Date item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                datePicker.setValue(item == null ? null : item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                setGraphic(datePicker);
            } else {
                setContentDisplay(ContentDisplay.TEXT_ONLY);
                if (item != null) {
                    java.util.Date utilDate = new java.util.Date(item.getTime());
                    setText(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter));
                } else {
                    setText("");
                }
            }
        }
    }
}