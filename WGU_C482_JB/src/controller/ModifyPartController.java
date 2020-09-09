package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;
    Alert error = new Alert(Alert.AlertType.ERROR);
    Part selectedPart;
    int selectedIndex;

    public TextField modifyPartIdTxt;
    public TextField modifyPartNameTxt;
    public TextField modifyPartInvTxt;
    public TextField modifyPartPriceTxt;
    public TextField modifyPartMaxTxt;
    public TextField modifyPartMinTxt;
    public TextField modifyPartIdOrNameTxt;
    public RadioButton modifyPartInHouseRBtn;
    public RadioButton modifyPartOutsourcedRBtn;
    public Label machineIdOrCompanyName;

    private ToggleGroup radioButtonChange;

    public void onActionSave(javafx.event.ActionEvent actionEvent) throws IOException {
        //the following exception handling checks to see if min < max
        try {
            if (Integer.parseInt(modifyPartMinTxt.getText()) <= Integer.parseInt(modifyPartMaxTxt.getText())){
                if (this.radioButtonChange.getSelectedToggle().equals(this.modifyPartInHouseRBtn)){
                    Part newPart = new InHouse(Integer.parseInt(modifyPartIdTxt.getText()),
                    modifyPartNameTxt.getText(),
                    Integer.parseInt(modifyPartInvTxt.getText()),
                    Double.parseDouble(modifyPartPriceTxt.getText()),
                    Integer.parseInt(modifyPartMaxTxt.getText()),
                    Integer.parseInt(modifyPartMinTxt.getText()),
                    Integer.parseInt(modifyPartIdOrNameTxt.getText()));
                    Inventory.updatePart(Integer.parseInt(modifyPartIdTxt.getText()), newPart);
                }

                if (this.radioButtonChange.getSelectedToggle().equals(this.modifyPartOutsourcedRBtn)){
                    Part newPart = new Outsourced(Integer.parseInt(modifyPartIdTxt.getText()),
                            modifyPartNameTxt.getText(),
                            Integer.parseInt(modifyPartInvTxt.getText()),
                            Double.parseDouble(modifyPartPriceTxt.getText()),
                            Integer.parseInt(modifyPartMaxTxt.getText()),
                            Integer.parseInt(modifyPartMinTxt.getText()),
                            modifyPartIdOrNameTxt.getText());
                    Inventory.updatePart(Integer.parseInt(modifyPartIdTxt.getText()), newPart);
                }
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
            else
                throw new Exception();
        } catch (Exception e) {
            error.show();
            error.setTitle("Warning Dialog");
            error.setContentText("Min value cannot be greater than max value.");
        }
    }

    public void onActionCancel(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This part will not be modified, OK?");
        alert.setTitle("Confirmation of Cancellation");
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void onActionRBtnChange() {
        if (Objects.equals(this.radioButtonChange.getSelectedToggle(), this.modifyPartInHouseRBtn)){
            this.machineIdOrCompanyName.setText("Machine ID:");
        }
        if (this.radioButtonChange.getSelectedToggle().equals(this.modifyPartOutsourcedRBtn)){
            this.machineIdOrCompanyName.setText("Company Name:");
        }

    }


    public Part getSelectedPart() {
        return selectedPart;

    }

    public void setSelectedPart(Part part, int index) {
        selectedPart = part;
        selectedIndex = index;

        if (selectedPart instanceof InHouse){
            machineIdOrCompanyName.setText("Machine ID:");
            modifyPartInHouseRBtn.setSelected(true);
            modifyPartIdOrNameTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced){
            machineIdOrCompanyName.setText("Company Name:");
            modifyPartOutsourcedRBtn.setSelected(true);
            modifyPartIdOrNameTxt.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }

        modifyPartIdTxt.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameTxt.setText(selectedPart.getName());
        modifyPartInvTxt.setText(String.valueOf(selectedPart.getInv()));
        modifyPartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMinTxt.setText(String.valueOf(selectedPart.getMin()));

        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioButtonChange =  new ToggleGroup();
        this.modifyPartInHouseRBtn.setToggleGroup(radioButtonChange);
        this.modifyPartOutsourcedRBtn.setToggleGroup(radioButtonChange);

    }
}