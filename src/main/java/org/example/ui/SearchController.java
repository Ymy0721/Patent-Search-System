package org.example.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.Cell;
import org.example.model.Patent;
import org.example.service.PatentService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.scene.control.TableColumn;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchController {
    @FXML
    private TextField keywordField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    protected Button searchButton;


    @FXML
    private TableView<Patent> resultsTable;

    @FXML
    private TableColumn<Patent, String> inventionNameColumn;
    @FXML
    private TableColumn<Patent, String> abstractColumn;
    @FXML
    private TableColumn<Patent, Date> applicationDateColumn;
    @FXML
    private TableColumn<Patent, Date> publicationDateColumn;
    @FXML
    private TableColumn<Patent, String> applicationNumberColumn;
    @FXML
    private TableColumn<Patent, String> publicationNumberColumn;
    @FXML
    private TableColumn<Patent, String> ipcClassificationColumn;
    @FXML
    private TableColumn<Patent, String> documentTypeColumn;
    @FXML
    private TableColumn<Patent, String> inventorColumn;
    @FXML
    private TableColumn<Patent, String> applicantColumn;
    @FXML
    private TableColumn<Patent, String> applicantCountryColumn;
    @FXML
    private TableColumn<Patent, String> applicantPostalCodeColumn;
    @FXML
    private TableColumn<Patent, String> agentColumn;
    @FXML
    private TableColumn<Patent, String> agencyColumn;

    @FXML
    private Label resultsCountLabel;
    @FXML
    private final PatentService patentService = new PatentService();
    @FXML
    private final UndoManager undoManager = new UndoManager();

    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<Integer> itemsPerPageComboBox;

    protected ObservableList<Patent> allResults;
    private int itemsPerPage = 20;

    // 初始化页面
    @FXML
    public void initialize() {

        configureTable(resultsTable);

        resultsTable.setMinHeight(Region.USE_COMPUTED_SIZE);
        resultsTable.setPrefHeight(Region.USE_COMPUTED_SIZE);
        resultsTable.setMaxHeight(Region.USE_COMPUTED_SIZE);

        resultsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        itemsPerPageComboBox.getItems().addAll(10, 20, 50);
        itemsPerPageComboBox.setValue(itemsPerPage);
        itemsPerPageComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            itemsPerPage = newVal;
            updatePagination();
        });
        pagination.setPageCount(0);
    }

    public void updatePagination() {
        if (allResults == null || allResults.isEmpty()) {
            pagination.setPageCount(0);
        } else {
            int totalPages = (int) Math.ceil((double) allResults.size() / itemsPerPage);
            pagination.setPageCount(totalPages);
            pagination.setPageFactory(this::createPage);
        }
    }

    public Node createPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, allResults.size());
        resultsTable.getItems().setAll(allResults.subList(fromIndex, toIndex));
        return new Pane();
    }



    private void configureTable(TableView<Patent> table) {
        inventionNameColumn.setCellValueFactory(new PropertyValueFactory<>("inventionName"));
        abstractColumn.setCellValueFactory(new PropertyValueFactory<>("abstractText"));
        applicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("applicationDate"));
        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        applicationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("applicationNumber"));
        publicationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("publicationNumber"));
        ipcClassificationColumn.setCellValueFactory(new PropertyValueFactory<>("IPCClassification"));
        documentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        inventorColumn.setCellValueFactory(new PropertyValueFactory<>("inventor"));
        applicantColumn.setCellValueFactory(new PropertyValueFactory<>("applicant"));
        applicantCountryColumn.setCellValueFactory(new PropertyValueFactory<>("applicantCountry"));
        applicantPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("applicantPostalCode"));
        agentColumn.setCellValueFactory(new PropertyValueFactory<>("agent"));
        agencyColumn.setCellValueFactory(new PropertyValueFactory<>("agency"));

        // 设置表格列的宽度随着表格的宽度自动调整
        for (TableColumn<Patent, ?> column : table.getColumns()) {
            column.prefWidthProperty().bind(table.widthProperty().divide(table.getColumns().size()));
        }
    }


    // 简单检索框
    @FXML
    public void handleSearch() {
        String keyword = keywordField.getText();
        if (keyword == null || keyword.trim().isEmpty()) {
            // 如果关键词输入框为空，显示红色的提示文字
            resultsCountLabel.setText("请输入检索词！");
            resultsCountLabel.setTextFill(Color.RED);
            return;
        }

        String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;

        try {
            List<Patent> results = patentService.searchPatents(keyword, startDate, endDate);
            resultsTable.getItems().setAll(results);
            // 更新结果数
            resultsCountLabel.setText("命中结果数: " + results.size());
            resultsCountLabel.setTextFill(Color.RED); // 将结果数颜色设置为红色

            // 在搜索后重新配置分页
            allResults = FXCollections.observableArrayList(results);
            updatePagination();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 新建检索窗口
    @FXML
    public void handleNew() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Search.fxml"));
            Stage newSearchStage = new Stage();
            newSearchStage.setScene(new Scene(loader.load()));
            newSearchStage.setTitle("专利检索系统");
            newSearchStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 导出为xlsx文件
    @FXML
    public void handleExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File initialDirectory = new File("D:/data_test"); // 设置初始目录
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs(); // 如果目录不存在，则创建它
        }
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("导出");

                // 写入列名
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < resultsTable.getColumns().size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(resultsTable.getColumns().get(i).getText());
                }

                // 遍历每一行
                for (int i = 0; i < allResults.size(); i++) { // 修改这里，从allResults中获取数据
                    Patent patent = allResults.get(i); // 修改这里，从allResults中获取数据
                    Row row = sheet.createRow(i + 1);

                    // 遍历每一个单元格
                    for (int j = 0; j < resultsTable.getColumns().size(); j++) {
                        Cell cell = row.createCell(j);
                        Object data = resultsTable.getColumns().get(j).getCellData(patent);
                        if (data != null) {
                            cell.setCellValue(data.toString());
                        }
                    }
                }

                workbook.write(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 退出
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        // Get the Scene from the Node
        Scene scene = searchButton.getScene();

        // Get the Window from the Scene
        Window window = scene.getWindow();

        // Cast the Window to Stage
        Stage stage = (Stage) window;

        // Close the stage
        stage.close();
    }


    // 剪切
    @FXML
    public void handlePaste() {
        // 获取系统剪贴板
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            // 获取剪贴板中的文本
            String pasteString = clipboard.getString();
        }
    }

    // 报错提示窗口
    public void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);  // 移除header
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 复制选中的专利信息
    @FXML
    public void handleCopy() {
        // Get the selected patents
        ObservableList<Patent> selectedPatents = resultsTable.getSelectionModel().getSelectedItems();

        // Create a StringBuilder to hold the row data
        StringBuilder sb = new StringBuilder();

        // Iterate over each selected patent
        for (Patent selectedPatent : selectedPatents) {
            // Iterate over each column in the table
            for (TableColumn<Patent, ?> column : resultsTable.getColumns()) {
                // Get the cell data for the selected patent and append it to the StringBuilder
                // Add a tab character after each cell to separate the cells in the copied text
                sb.append(column.getCellData(selectedPatent)).append("\t");
            }
            // Add a newline character after each row to separate the rows in the copied text
            sb.append("\n");
        }

        // Copy the row data to the clipboard
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(sb.toString());
        clipboard.setContent(content);
    }

    // 全屏显示
    @FXML
    public void handleFullScreen(ActionEvent actionEvent) {
        // Get the Scene from the Node
        Scene scene = searchButton.getScene();

        // Get the Window from the Scene
        Window window = scene.getWindow();

        // Cast the Window to Stage
        Stage stage = (Stage) window;

        // Set the stage to full screen
        stage.setFullScreen(true);
    }


    // 简单检索
    @FXML
    public void handleSimpleSearch(ActionEvent actionEvent) {
        try {
            // Get the current stage and close it
            Node node = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) node.getScene().getWindow();
            currentStage.close();

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

    // 高级检索
    @FXML
    public void handleAdvancedSearch(ActionEvent actionEvent) {
        try {
            Stage currentStage;
            if (actionEvent.getSource() instanceof Node) {
                Node node = (Node) actionEvent.getSource();
                currentStage = (Stage) node.getScene().getWindow();
            } else if (actionEvent.getSource() instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) actionEvent.getSource();
                currentStage = (Stage) menuItem.getParentPopup().getOwnerNode().getScene().getWindow();
            } else {
                throw new IllegalArgumentException("Unsupported action event source");
            }
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdvancedSearch.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("高级检索系统");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // 展示专利详情信息
    @FXML
    public void handleView(ActionEvent actionEvent) {
        // 获取选中的专利
        Patent selectedPatent = resultsTable.getSelectionModel().getSelectedItem();
        if (selectedPatent != null) {
            try {
                // 加载专利详细信息的FXML文件
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PatentDetails.fxml"));
                Parent root = loader.load();

                // 获取控制器并传递专利信息
                PatentDetailsController controller = loader.getController();
                controller.setPatent(selectedPatent);

                // 创建新的窗口并显示
                Stage stage = new Stage();
                stage.setTitle("专利详细信息");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 专利自动引用
    @FXML
    public void handleCitePatents() {
        // Get the patents selected by the user
        ObservableList<Patent> selectedPatents = resultsTable.getSelectionModel().getSelectedItems();

        // Load the FXML file for the citation window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Citation.fxml"));

        try {
            Parent root = loader.load();

            // Get the controller and pass the selected patents to it
            CitationController citationController = loader.getController();
            citationController.setPatents(selectedPatents);

            Stage stage = new Stage();
            stage.setTitle("专利引用");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 专利分析
    @FXML
    public void handleAnalysis() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Analysis.fxml"));
            Parent root = loader.load();

            AnalysisController analysisController = loader.getController();
            analysisController.setSearchResults(allResults); // 修改这里，传递allResults

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("专利分析");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 撤销
    @FXML
    public void handleUndo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    // 重做
    @FXML
    public void handleRedo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }


    // 取消选中
    @FXML
    public void handleDeselect() {
        resultsTable.getSelectionModel().clearSelection();
    }
    // 全选
    @FXML
    public void handleSelectAll(ActionEvent actionEvent) {
        resultsTable.getSelectionModel().selectAll();
    }

    // 打开文件
    @FXML
    public void handleOpen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized files", "*.ser"));
        File initialDirectory = new File("D:/data_test"); // 设置初始目录
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs(); // 如果目录不存在，则创建它
        }
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Patent> patents = (List<Patent>) ois.readObject();
                resultsTable.getItems().setAll(patents);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 保存文件
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized files", "*.ser"));
        File initialDirectory = new File("D:/data_test"); // 设置初始目录
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs(); // 如果目录不存在，则创建它
        }
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(new ArrayList<>(resultsTable.getItems()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 另存为
    @FXML
    public void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized files", "*.ser"));
        File initialDirectory = new File("D:/data_test"); // 设置初始目录
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs(); // 如果目录不存在，则创建它
        }
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(new ArrayList<>(resultsTable.getItems()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 导入
    @FXML
    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized files", "*.ser"));
        File initialDirectory = new File("D:/data_test"); // 设置初始目录
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs(); // 如果目录不存在，则创建它
        }
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Patent> patents = (List<Patent>) ois.readObject();
                resultsTable.getItems().setAll(patents);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 打印
    @FXML
    public void handlePrint(ActionEvent actionEvent) {
        // 创建一个打印任务
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(resultsTable.getScene().getWindow())) {
            // 设置打印的节点
            boolean success = printerJob.printPage(resultsTable);
            if (success) {
                // 结束打印任务
                printerJob.endJob();
            }
        }
    }



    // 刷新页面
    @FXML
    public void handleRefresh(ActionEvent actionEvent) {
        try {
            // 获取当前的Stage
            Stage stage = (Stage) keywordField.getScene().getWindow();

            // 重新加载FXML文件
            Parent root = FXMLLoader.load(getClass().getResource("/Search.fxml"));

            // 创建新的Scene并设置到当前的Stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 视图选项
    @FXML
    public void handleViewOptions(ActionEvent actionEvent) {
    }


    // 检索历史
    @FXML
    public void handleHistory(ActionEvent actionEvent) {
    }

    // 帮助
    @FXML
    public void handleAssistant(ActionEvent actionEvent) {
    }

    // 设置
    @FXML
    public void handleSettings(ActionEvent actionEvent) {
    }
    @FXML

    // 用户手册
    public void handleUserManual(ActionEvent actionEvent) {
    }

    // 问答
    @FXML
    public void handleFAQ(ActionEvent actionEvent) {
    }

    // 联系支持
    @FXML
    public void handleContactSupport(ActionEvent actionEvent) {
    }

    // 关于
    @FXML
    public void handleAbout(ActionEvent actionEvent) {
    }
}
