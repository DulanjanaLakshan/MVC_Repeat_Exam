package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import view.tm.StudentTm;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {
    public TextField txtID;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtNIC;
    public Button btnSaveOnAction;
    public Button btnUpdateOnAction;
    public Button btnDeleteOnAction;
    public TableView<StudentTm> tblStudent;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colNic;
    public TableColumn colContact;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("nic"));
        loadAllStudent();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtID.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtEmail.setText(newValue.getEmail());
            txtContact.setText(newValue.getContact());
            txtAddress.setText(newValue.getAddress());
            txtNIC.setText(newValue.getNic());
        });
    }

    private void loadAllStudent() {
        ArrayList<Student> students = new ArrayList<>();
        ObservableList<StudentTm> objects = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student");
            ResultSet rst = preparedStatement.executeQuery();

            while (rst.next()) {
                students.add(new Student(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6))
                );
            }
            for (Student stu : students) {
                objects.add(new StudentTm(stu.getId(), stu.getName(), stu.getEmail(), stu.getContact(), stu.getAddress(), stu.getNic()));
            }
            tblStudent.setItems(objects);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Student student = new Student(txtID.getText(), txtName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNIC.getText());

        try {
            PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement("INSERT INTO Student VALUES (?,?,?,?,?,?)");
            preparedStatement.setObject(1, student.getId());
            preparedStatement.setObject(2, student.getName());
            preparedStatement.setObject(3, student.getEmail());
            preparedStatement.setObject(4, student.getContact());
            preparedStatement.setObject(5, student.getAddress());
            preparedStatement.setObject(6, student.getNic());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                clearTextFelled();
                loadAllStudent();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
                clearTextFelled();
                loadAllStudent();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Student student = new Student(txtID.getText(), txtName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNIC.getText());

        try {
            PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement("UPDATE Student SET student_name=?,email=?,contact=?,address=?,nic=? WHERE student_id=?");
            preparedStatement.setObject(1, student.getName());
            preparedStatement.setObject(2, student.getEmail());
            preparedStatement.setObject(3, student.getContact());
            preparedStatement.setObject(4, student.getAddress());
            preparedStatement.setObject(5, student.getNic());
            preparedStatement.setObject(6, student.getId());
            int update = preparedStatement.executeUpdate();
            if (update > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement("DELETE FROM Student WHERE student_id=?");
            preparedStatement.setObject(1, txtID.getText());
            int delete = preparedStatement.executeUpdate();

            if (delete > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Delete Successfully.....!", ButtonType.OK).show();
                loadAllStudent();
                clearTextFelled();
            } else {
                new Alert(Alert.AlertType.WARNING, "Supplier Delete Unsuccessfully.....", ButtonType.OK).show();
                loadAllStudent();
                clearTextFelled();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFelled() {
        txtID.clear();
        txtName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        txtNIC.clear();
    }

}
