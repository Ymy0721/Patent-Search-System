package org.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.example.model.Patent;

import java.util.ArrayList;
import java.util.List;

public class CitationController {
    @FXML
    private TextArea citationArea;

    private List<Patent> patents;

    public void setPatents(List<Patent> patents) {
        this.patents = patents;
        initialize();
    }

    @FXML
    public void initialize() {
        if (patents == null) {
            patents = new ArrayList<>();
        }

        List<String> citationTexts = new ArrayList<>();
        for (int i = 0; i < patents.size(); i++) {
            citationTexts.add(generateCitation(patents.get(i), i + 1));
        }

        String citationText = String.join("\n", citationTexts);
        citationArea.setText(citationText);
    }

    public String generateCitation(Patent patent, int index) {
        String applicant = patent.getApplicant();
        String title = patent.getInventionName();
        String patentNumber = patent.getApplicationNumber();
        String date = patent.getPublicationDate().toString();

        return String.format("[%d] %s.%s: %s[P]. %s.", index, applicant, title, patentNumber, date);
    }

    @FXML
    public void copyToClipboard() {
        String citationText = citationArea.getText();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(citationText);
        clipboard.setContent(content);

        // 创建一个新的提示框并显示
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("复制成功");
        alert.setHeaderText(null);
        alert.setContentText("复制成功！");
        alert.showAndWait();
    }
}