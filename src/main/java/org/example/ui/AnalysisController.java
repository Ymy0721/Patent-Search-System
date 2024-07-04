package org.example.ui;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.example.model.Patent;

import javafx.scene.control.Tooltip;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalysisController {
    @FXML
    public GridPane analysisPane;
    @FXML
    private ComboBox<String> analysisComboBox;
    @FXML
    public Button analyzeButton;
    @FXML
    public Button resetButton;
    @FXML
    public ChoiceBox chartTypeChoiceBox;
    @FXML
    public Button exportImageButton;
    @FXML
    public VBox filterPane;
    @FXML
    private StackPane chartPane;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField topResultsTextField;
    @FXML
    private ChoiceBox<String> ipcLevelChoiceBox;
    @FXML
    private ChoiceBox<String> regionLevelChoiceBox;

    private ObservableList<Patent> searchResults; // 保存搜索结果

    private static final Map<String, String> PROVINCE_POSTAL_CODE_MAP = PostalCodeData.PROVINCE_POSTAL_CODE_MAP;
    private static final Map<String, String> CITY_POSTAL_CODE_MAP = PostalCodeData.CITY_POSTAL_CODE_MAP;


    public void setSearchResults(ObservableList<Patent> searchResults) {
        this.searchResults = searchResults;
    }

    @FXML
    public void initialize() {
        // 设置默认的图表类型和结果数量

        chartTypeChoiceBox.getSelectionModel().select("饼图");
        topResultsTextField.setText("10");

        // 设置默认的分析类型为"时间分析"
        analysisComboBox.getSelectionModel().select("时间分析");

        handleTimeAnalysis(); // 默认显示时间分析图表
    }



    @FXML
    public void handleExportImage() {
        // 创建一个WritableImage对象，用于存储StackPane的快照
        WritableImage image = chartPane.snapshot(new SnapshotParameters(), null);

        // 创建一个文件对话框，让用户选择保存图像的位置和文件名
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存图片");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File initialDirectory = new File("D:/data_test"); // 设置初始目录
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs(); // 如果目录不存在，则创建它
        }
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showSaveDialog(chartPane.getScene().getWindow());

        if (file != null) {
            try {
                // 使用ImageIO将图像写入PNG文件
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                // 打印错误信息
                System.err.println(e.getMessage());
            }
        }
    }

    @FXML
    public void handleReset() {
        // Reset the date pickers to null
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

        // Reset the top results text field to the default value "10"
        topResultsTextField.setText("10");

        // Reset the IPC level choice box to the default value "部"
        if (ipcLevelChoiceBox != null) {
            ipcLevelChoiceBox.getSelectionModel().select("部");
        }

        // Reset the region level choice box to the default value "国家/地区"
        if (regionLevelChoiceBox != null) {
            regionLevelChoiceBox.getSelectionModel().select("国家/地区");
        }

        // Call the handleAnalyze method to refresh the chart
        handleAnalyze();
    }



    @FXML
    public void handleAnalyze() {
        String selectedAnalysis = analysisComboBox.getSelectionModel().getSelectedItem();
        if (selectedAnalysis != null) {
            switch (selectedAnalysis) {
                case "时间分析":
                    handleTimeAnalysis();
                    break;
                case "申请人分析":
                    handleApplicantAnalysis();
                    break;
                case "发明人分析":
                    handleInventorAnalysis();
                    break;
                case "技术领域分析":
                    handleTechFieldAnalysis();
                    break;
                case "区域分析":
                    handleRegionAnalysis();
                    break;
                default:
                    break;
            }
        }
    }


    @FXML
    public void handleTimeAnalysis() {
        if (searchResults == null) {
            return;
        }

        removeExistingChoiceBox();

        // 使用filterByDateRange方法过滤专利
        Stream<Patent> filteredPatents = filterByDateRange(searchResults.stream());

        // 根据申请日期按年份分组，并计算每年的专利数量
        Map<Integer, Long> countsByYear = filteredPatents
                .filter(patent -> patent.getApplicationDate() != null)
                .collect(Collectors.groupingBy(patent -> {
                    LocalDate localDate = patent.getApplicationDate().toLocalDate();
                    return Year.from(localDate).getValue();
                }, Collectors.counting()));
        // 按照年份升序排序分组结果
        Map<Integer, Long> sortedCountsByYear = countsByYear.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
        // 获取用户输入的结果数量，如果没有输入，使用默认值10
        String topResultsText = topResultsTextField.getText();
        int topResults = topResultsText.isEmpty() ? 10 : Integer.parseInt(topResultsText);

        // 根据专利数量排序分组结果，并获取最近年份的前10个结果
        List<Map.Entry<String, Long>> topCountsByYear = countsByYear.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .skip(Math.max(0, countsByYear.size() - topResults)) // 跳过除最后10个结果之外的所有结果
                .map(entry -> new AbstractMap.SimpleEntry<>(String.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());

        // 创建图表
        createChart(topCountsByYear,"时间");

    }



    @FXML
    public void handleApplicantAnalysis() {
        if (searchResults == null) {
            return;
        }

        removeExistingChoiceBox();


        // 根据申请人分组，并计算每个申请人的专利数量
        Map<String, Long> countsByApplicant = filterByDateRange(searchResults.stream())
                .filter(patent -> patent.getApplicant() != null)
                .collect(Collectors.groupingBy(Patent::getApplicant, Collectors.counting()));

        // 获取用户输入的结果数量，如果没有输入，使用默认值10
        String topResultsText = topResultsTextField.getText();
        int topResults = topResultsText.isEmpty() ? 10 : Integer.parseInt(topResultsText);

        // 根据专利数量排序分组结果，并获取前N个结果
        List<Map.Entry<String, Long>> topCountsByApplicant = countsByApplicant.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topResults)
                .collect(Collectors.toList());

        // 创建图表
        createChart(topCountsByApplicant, "申请人");
    }

    @FXML
    public void handleInventorAnalysis() {
        if (searchResults == null) {
            return;
        }

        removeExistingChoiceBox();

        // 根据发明人分组，并计算每个发明人的专利数量
        Map<String, Long> countsByInventor = filterByDateRange(searchResults.stream())
                .filter(patent -> patent.getInventor() != null)
                .collect(Collectors.groupingBy(Patent::getInventor, Collectors.counting()));

        // 获取用户输入的结果数量，如果没有输入，使用默认值10
        String topResultsText = topResultsTextField.getText();
        int topResults = topResultsText.isEmpty() ? 10 : Integer.parseInt(topResultsText);

        // 根据专利数量排序分组结果，并获取前N个结果
        List<Map.Entry<String, Long>> topCountsByInventor = countsByInventor.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topResults)
                .collect(Collectors.toList());

        // 创建图表
        createChart(topCountsByInventor, "发明人");
    }

    @FXML
    public void handleTechFieldAnalysis() {
        if (searchResults == null) {
            return;
        }

        if (regionLevelChoiceBox != null) {
            filterPane.getChildren().remove(regionLevelChoiceBox);
            regionLevelChoiceBox = null;
        }

        // 检查ipcLevelChoiceBox是否为null
        if (ipcLevelChoiceBox == null) {
            // 创建一个新的ChoiceBox
            ipcLevelChoiceBox = new ChoiceBox<>();

            // 添加所需的选项
            ipcLevelChoiceBox.getItems().addAll("部", "大类", "小类", "大组", "小组");

            // 设置默认选中项为"部"
            ipcLevelChoiceBox.getSelectionModel().select("部");

            // 将新创建的ChoiceBox添加到界面上
            filterPane.getChildren().add(ipcLevelChoiceBox);

            // 设置ipcLevelChoiceBox为可见
            ipcLevelChoiceBox.setVisible(true);

            // 强制filterPane重新布局
            filterPane.requestLayout();
        }
        // 获取用户选择的IPC分类级别
        String ipcLevel = (String) ipcLevelChoiceBox.getSelectionModel().getSelectedItem();

        // 根据IPC分类级别分组，并计算每个分类的专利数量
        Map<String, Long> countsByIpc;
        Stream<Patent> filteredPatents = filterByDateRange(searchResults.stream()).filter(patent -> patent.getIPCClassification() != null);
        switch (ipcLevel) {
            case "部":
                countsByIpc = filteredPatents
                        .collect(Collectors.groupingBy(patent -> patent.getIPCClassification().substring(0, 1), Collectors.counting()));
                break;
            case "大类":
                countsByIpc = filteredPatents
                        .collect(Collectors.groupingBy(patent -> patent.getIPCClassification().substring(0, 3), Collectors.counting()));
                break;
            case "大组":
                countsByIpc = filteredPatents
                        .filter(patent -> patent.getIPCClassification().contains("/00")) // 过滤掉IPC分类中不包含"/00"的专利
                        .collect(Collectors.groupingBy(patent -> patent.getIPCClassification().substring(0, patent.getIPCClassification().indexOf("/00")), Collectors.counting()));
                break;
            case "小组":
            default:
                countsByIpc = filteredPatents
                        .collect(Collectors.groupingBy(Patent::getIPCClassification, Collectors.counting()));
                break;
        }

        // 获取用户输入的结果数量，如果没有输入，使用默认值10
        String topResultsText = topResultsTextField.getText();
        int topResults = topResultsText.isEmpty() ? 10 : Integer.parseInt(topResultsText);

        // 根据专利数量排序分组结果，并获取前N个结果
        List<Map.Entry<String, Long>> topCountsByIpc = countsByIpc.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topResults)
                .collect(Collectors.toList());

        // 创建图表
        createChart(topCountsByIpc, "技术领域");
    }

    @FXML
    public void handleRegionAnalysis() {
        if (searchResults == null) {
            return;
        }

        if (ipcLevelChoiceBox != null) {
            filterPane.getChildren().remove(ipcLevelChoiceBox);
            ipcLevelChoiceBox = null;
        }

        // 检查regionLevelChoiceBox是否为null
        if (regionLevelChoiceBox == null) {
            // 创建一个新的ChoiceBox
            regionLevelChoiceBox = new ChoiceBox<>();

            // 添加所需的选项
            regionLevelChoiceBox.getItems().addAll("国家/地区", "省", "市");

            // 设置默认选中项为"国家/地区"
            regionLevelChoiceBox.getSelectionModel().select("国家/地区");

            // 将新创建的ChoiceBox添加到界面上
            filterPane.getChildren().add(regionLevelChoiceBox);

            // 设置regionLevelChoiceBox为可见
            regionLevelChoiceBox.setVisible(true);

            // 强制filterPane重新布局
            filterPane.requestLayout();
        }

        // 获取用户选择的分析范围
        String regionLevel = (String) regionLevelChoiceBox.getSelectionModel().getSelectedItem();

        // 根据分析范围分组，并计算每个分组的专利数量
        Map<String, Long> countsByRegion;
        Stream<Patent> filteredPatents = filterByDateRange(searchResults.stream());
        switch (regionLevel) {
            case "国家/地区":
                countsByRegion = filteredPatents
                        .filter(patent -> Objects.nonNull(patent.getApplicantCountry()))
                        .collect(Collectors.groupingBy(Patent::getApplicantCountry, Collectors.counting()));
                break;
            case "省":
                countsByRegion = filteredPatents
                        .filter(patent -> Objects.nonNull(patent.getApplicantCountry()) && "CN".equals(patent.getApplicantCountry()) && Objects.nonNull(patent.getApplicantPostalCode()))
                        .map(patent -> {
                            String postalCode = patent.getApplicantPostalCode();
                            return postalCode.length() >= 2 ? postalCode.substring(0, 2) : "未知";
                        })
                        .collect(Collectors.groupingBy(code -> PROVINCE_POSTAL_CODE_MAP.getOrDefault(code, "未知"), Collectors.counting()));
                break;
            case "市":
                countsByRegion = filteredPatents
                        .filter(patent -> Objects.nonNull(patent.getApplicantCountry()) && "CN".equals(patent.getApplicantCountry()) && Objects.nonNull(patent.getApplicantPostalCode()))
                        .map(patent -> {
                            String postalCode = patent.getApplicantPostalCode();
                            return postalCode.length() >= 4 ? postalCode.substring(0, 4) : "未知";
                        })
                        .collect(Collectors.groupingBy(code -> CITY_POSTAL_CODE_MAP.getOrDefault(code, "未知"), Collectors.counting()));
                break;
            default:
                return;
        }

        // 获取用户输入的结果数量，如果没有输入，使用默认值10
        String topResultsText = topResultsTextField.getText();
        int topResults = topResultsText.isEmpty() ? 10 : Integer.parseInt(topResultsText);

        // 根据专利数量排序分组结果，并获取前N个结果
        List<Map.Entry<String, Long>> topCountsByRegion = countsByRegion.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topResults)
                .collect(Collectors.toList());

        // 创建图表
        createChart(topCountsByRegion, "区域");
    }


    private Stream<Patent> filterByDateRange(Stream<Patent> patents) {
        List<Patent> patentList = patents.collect(Collectors.toList());

        // 获取检索结果中的最早和最晚的申请日期
        LocalDate minApplicationDate = patentList.stream()
                .filter(patent -> patent.getApplicationDate() != null)
                .map(patent -> patent.getApplicationDate().toLocalDate())
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now().minusYears(10)); // 如果没有检索结果，使用10年前的日期

        LocalDate maxApplicationDate = patentList.stream()
                .filter(patent -> patent.getApplicationDate() != null)
                .map(patent -> patent.getApplicationDate().toLocalDate())
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now()); // 如果没��检索结果，使用当前日期

        // 获取用户输入的起止日期，如果没有输入，使用检索结果中的最早和最晚的申请日期
        LocalDate startDate = startDatePicker.getValue() != null ? startDatePicker.getValue() : minApplicationDate;
        LocalDate endDate = endDatePicker.getValue() != null ? endDatePicker.getValue() : maxApplicationDate;

        // 根据起止日期过滤搜索结果
        return patentList.stream()
                .filter(patent -> {
                    LocalDate applicationDate = patent.getApplicationDate().toLocalDate();
                    return applicationDate != null && applicationDate.isAfter(startDate) && applicationDate.isBefore(endDate);
                });
    }

    private void removeExistingChoiceBox() {
        if (ipcLevelChoiceBox != null) {
            filterPane.getChildren().remove(ipcLevelChoiceBox);
            ipcLevelChoiceBox = null;
        }
        if (regionLevelChoiceBox != null) {
            filterPane.getChildren().remove(regionLevelChoiceBox);
            regionLevelChoiceBox = null;
        }
    }

    public void createChart(List<Map.Entry<String, Long>> topCounts, String selectedDimension) {
        // 根据ChoiceBox的选中项创建不同类型的图表
        String chartType = (String) chartTypeChoiceBox.getSelectionModel().getSelectedItem();
        switch (chartType) {
            case "饼图":
                // 创建一个饼图
                PieChart pieChart = new PieChart();
                pieChart.setTitle("专利数量按" + selectedDimension + "分布");

                // 使用分组数据来创建饼图的数据
                for (Map.Entry<String, Long> entry : topCounts) {
                    PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
                    pieChart.getData().add(slice);

                    // 添加鼠标悬停事件
                    QuickTooltip tooltip = new QuickTooltip(selectedDimension + ": " + entry.getKey() + "\n专利数: " + entry.getValue()); // 鼠标悬停时显示完整信息
                    Tooltip.install(slice.getNode(), tooltip);
                }

                // 显示饼图
                chartPane.getChildren().setAll(pieChart);
                break;
            case "柱状图":
                // 创建一个柱形图
                CategoryAxis xAxisBar = new CategoryAxis();
                NumberAxis yAxisBar = new NumberAxis();
                BarChart<String, Number> barChart = new BarChart<>(xAxisBar, yAxisBar);
                barChart.setTitle("专利数量按" + selectedDimension + "分布");
                barChart.setLegendVisible(false); // 隐藏图例

                // 使用分组数据来创建柱形图的数据
                XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
                for (Map.Entry<String, Long> entry : topCounts) {
                    XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
                    seriesBar.getData().add(data);
                }
                barChart.getData().add(seriesBar);

                // 添加鼠标悬停事件
                for (XYChart.Data<String, Number> data : seriesBar.getData()) {
                    QuickTooltip tooltip = new QuickTooltip(selectedDimension + ": " + data.getXValue() + "\n专利数: " + data.getYValue());
                    Tooltip.install(data.getNode(), tooltip);
                }

                // 显示柱形图
                chartPane.getChildren().setAll(barChart);
                break;
            case "折线图":
                // 创建一个折线图
                CategoryAxis xAxisLine = new CategoryAxis();
                NumberAxis yAxisLine = new NumberAxis();
                LineChart<String, Number> lineChart = new LineChart<>(xAxisLine, yAxisLine);
                lineChart.setTitle("专利数量按" + selectedDimension + "分布");
                lineChart.setLegendVisible(false); // 隐藏图例

                // 使用分组数据来创建折线图的数据
                XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
                for (Map.Entry<String, Long> entry : topCounts) {
                    XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
                    seriesLine.getData().add(data);
                }
                lineChart.getData().add(seriesLine);

                // 添加鼠标悬停事件
                for (XYChart.Data<String, Number> data : seriesLine.getData()) {
                    QuickTooltip tooltip = new QuickTooltip(selectedDimension + ": " + data.getXValue() + "\n专利数: " + data.getYValue());
                    Tooltip.install(data.getNode(), tooltip);
                }

                // 显示折线图
                chartPane.getChildren().setAll(lineChart);
                break;
            case "表格":
                // 创建一个表格
                TableView<Map.Entry<String, Long>> tableView = new TableView<>();
                TableColumn<Map.Entry<String, Long>, String> dimensionColumn = new TableColumn<>(selectedDimension);
                dimensionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
                TableColumn<Map.Entry<String, Long>, Long> countColumn = new TableColumn<>("专利数");
                countColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getValue()).asObject());

                // 计算所有专利的总数
                long total = topCounts.stream().mapToLong(Map.Entry::getValue).sum();

                // 创建一个新的列来显示比例
                TableColumn<Map.Entry<String, Long>, String> ratioColumn = new TableColumn<>("比例");
                ratioColumn.setCellValueFactory(data -> {
                    double ratio = (double) data.getValue().getValue() / total * 100; // 计算比例
                    return new SimpleStringProperty(String.format("%.2f%%", ratio)); // 格式化为保留两位小数的百分比
                });

                tableView.getColumns().setAll(dimensionColumn, countColumn, ratioColumn); // 添加所有列到表格中
                tableView.getItems().setAll(topCounts); // 使用包含所有结果的变量

                // 设置列自动调整策略
                tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // 显示表格
                chartPane.getChildren().setAll(tableView);
                break;
        }
    }

}

class QuickTooltip extends Tooltip {
    public QuickTooltip(String text) {
        super(text);
        setShowDelay(Duration.millis(100)); // 设置显示延迟为100毫秒（0.1秒）
    }
}