package ru.nstu.javafx_labs_lipatov_v2;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatModel;

public class ModalWindow {
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private TextArea mainText;
    public HabitatModel model;
    public void setText(String stat){
        mainText.setText(stat);
    }

    @FXML
    void btnOkClick(){
        model.stopGeneration();
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCancelClick(){
        model.unPauseGeneration();
        model.getView().getButtonStop().setDisable(false);
        model.getView().getButtonStart().setDisable(true);
        model.getView().getLiveObjButton().setDisable(false);
        Stage stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    void btnClose(){
        Stage stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }
}
