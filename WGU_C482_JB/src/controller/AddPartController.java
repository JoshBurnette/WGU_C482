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
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    public TextField addPartNameTxt;
    public TextField addPartInvTxt;
    public TextField addPartPriceTxt;
    public TextField addPartMaxTxt;
    public TextField addPartMinTxt;
    public TextField addPartIdOrNameTxt;
    public RadioButton addPartInHouseRBtn;
    public RadioButton addPartOutsourcedRBtn;
    public ToggleGroup radioButtonChange;
    public Label machineIDOrCompanyName;

    Stage stage;
    Parent scene;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public void onActionCancel(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This part will not be saved, OK?");
        alert.setTitle("Confirmation of Cancellation");
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void onActionSave(javafx.event.ActionEvent actionEvent) throws IOException {
        // id auto generator
        int counter = 1;
        int newId = 1;

        for(Part part : Inventory.getAllParts()){
            counter = part.getId();
            counter++;

            }
        newId = counter;


        int id = newId;
        String name = addPartNameTxt.getText();
        int inv = Integer.parseInt((addPartInvTxt.getText()));
        double price = Double.parseDouble((addPartPriceTxt.getText()));
        int max = Integer.parseInt(addPartMaxTxt.getText());
        int min = Integer.parseInt(addPartMinTxt.getText());
        //the following exception handling checks to see if min < max
        try {
            if (min <= max){
                if (this.radioButtonChange.getSelectedToggle().equals(this.addPartInHouseRBtn)){
                    int machineId = Integer.parseInt(addPartIdOrNameTxt.getText());
                    Inventory.addPart(new InHouse(id, name, inv, price, max, min, machineId));
                }

                if (this.radioButtonChange.getSelectedToggle().equals(this.addPartOutsourcedRBtn)){
                    String companyName = addPartIdOrNameTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, inv, price, max, min, companyName));
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



    public void onActionRBtnChange() {
        if (this.radioButtonChange.getSelectedToggle().equals(this.addPartInHouseRBtn)){
            this.machineIDOrCompanyName.setText("Machine ID:");
        }
        if (this.radioButtonChange.getSelectedToggle().equals(this.addPartOutsourcedRBtn)){
            this.machineIDOrCompanyName.setText("Company Name:");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioButtonChange =  new ToggleGroup();
        this.addPartInHouseRBtn.setToggleGroup(radioButtonChange);
        this.addPartOutsourcedRBtn.setToggleGroup(radioButtonChange);
    }
}
