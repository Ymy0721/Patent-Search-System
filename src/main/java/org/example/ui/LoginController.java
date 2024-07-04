package org.example.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private RadioButton user;
    @FXML
    private RadioButton admin;

    public static String currentUserId;

    private UserService userService = new UserService();
    private ToggleGroup usertypeGroup;

    @FXML
    public void initialize() {
        usertypeGroup = new ToggleGroup();
        user.setToggleGroup(usertypeGroup);
        admin.setToggleGroup(usertypeGroup);


        // Get the parent of the usernameField, which should be the GridPane
        GridPane gridPane = (GridPane) usernameField.getParent();

        // Set the maximum width and height of the GridPane
        gridPane.setMaxSize(300, 200);

        // Get the parent of the GridPane, which should be the StackPane
        StackPane stackPane = (StackPane) gridPane.getParent();

        // Set the alignment of the StackPane to CENTER
        stackPane.setAlignment(Pos.CENTER);

    }


    private void loadAndShowStage(String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        RadioButton selectedRadioButton = (RadioButton) usertypeGroup.getSelectedToggle();
        String usertype = selectedRadioButton == null ? null : selectedRadioButton.getId();

        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "请输入用户名！");
            return;
        }
        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "请输入密码！");
            return;
        }
        if (usertype == null) {
            showAlert(Alert.AlertType.ERROR, "请勾选登录方式！");
            return;
        }
        if (!username.matches("^[a-zA-Z_][a-zA-Z0-9_]{3,15}$")) {
            showAlert(Alert.AlertType.ERROR, "用户名必须为4-16位大小写字母、\n下划线、数字的组合，且只能以字母或下划线开头！");
            return;
        }
        if (!password.matches("^.{4,16}$")) {
            showAlert(Alert.AlertType.ERROR, "密码必须为4-16位！");
            return;
        }
        if (usertype.equals("admin")) {
            showAlert(Alert.AlertType.INFORMATION, "请联系开发者*袁名宇*申请注册资格！\n请发送邮件至 202211260048@mail.bnu.edu.cn");
            return;
        }
        try {
            User user = userService.getUser(username, usertype);
            if (user == null) {
                userService.createUser(username, password, usertype);
                showAlert(Alert.AlertType.INFORMATION, "注册成功！");

                // Get the current stage and close it
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();

                // Load the new FXML file and show the new stage
                if (usertype.equals("user")) {
                    loadAndShowStage("/Search.fxml", "专利检索系统");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "用户名已存在！");
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "数据库错误！请联系管理员");
        }
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        RadioButton selectedRadioButton = (RadioButton) usertypeGroup.getSelectedToggle();
        String usertype = selectedRadioButton == null ? null : selectedRadioButton.getId();

        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "请输入用户名！");
            return;
        }
        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "请输入密码！");
            return;
        }
        if (usertype == null) {
            showAlert(Alert.AlertType.ERROR, "请勾选登录方式！");
            return;
        }
        try {
            if (!userService.validateUser(username, usertype)) {
                showAlert(Alert.AlertType.ERROR, "用户不存在！请重新输入！");
            } else if (!userService.validatePassword(username, password, usertype)) {
                showAlert(Alert.AlertType.ERROR, "密码错误！请重新输入！");
            } else {
                currentUserId = username; // 保存当前登录的用户ID
                if (usertype.equals("admin")) {
                    // Get the current stage and close it
                    Stage currentStage = (Stage) usernameField.getScene().getWindow();
                    currentStage.close();
                    loadAndShowStage("/Management.fxml", "专利管理系统");
                } else if (usertype.equals("user")) {
                    // Get the current stage and close it
                    Stage currentStage = (Stage) usernameField.getScene().getWindow();
                    currentStage.close();
                    loadAndShowStage("/Search.fxml", "专利检索系统");
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "登录成功！");
                }
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "数据库错误！请联系管理员！");
        }
    }

    public void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);  // 移除header
        alert.setContentText(message);
        alert.showAndWait();
    }
}

