package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddProductController implements Initializable {

    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn associatedPartIdCol;
    @FXML
    private TableColumn associatedPartNameCol;
    @FXML
    private TableColumn associatedPartInvCol;
    @FXML
    private TableColumn associatedPartPriceCol;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String>  partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    Product newProduct = new Product(0, "", 0, 0.00, 0, 0);
    Stage stage;
    Parent scene;


    public TextField addProductSearchTxt;
    public TextField addProductIdTxt;
    public TextField addProductNameTxt;
    public TextField addProductInvTxt;
    public TextField addProductPriceTxt;
    public TextField addProductMaxTxt;
    public TextField addProductMinTxt;

    Alert error = new Alert(Alert.AlertType.ERROR);

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    public void addFilteredPart(Part searchedPart){
        filteredParts.add(searchedPart);
    }

    public ObservableList<Part> getFilteredParts(){
        return filteredParts;
    }


    public void onActionSearchPart(ActionEvent actionEvent) {
        if (addProductSearchTxt.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
        else {
            filteredParts.clear();
            Inventory.filteredList.clear();
            //See if user text is an int, if not, go to catch
            try{
            addFilteredPart(Inventory.lookupPart(Integer.parseInt(addProductSearchTxt.getText())));

            partTableView.setItems(getFilteredParts());
            }
            //this is if user text is a string
            catch (Exception e){
                Inventory.lookupPart(addProductSearchTxt.getText());

                partTableView.setItems(Inventory.getFilteredList());
            }
        }

    }

    public void onActionAddPartToProduct() {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(selectedPart);
        associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());

    }

    public void onActionDeletePartFromProduct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete this associated part from the product, OK?");
        alert.setTitle("Confirmation of Deletion");
        if (associatedPartsTableView.getSelectionModel().getSelectedItem() != null){
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {

            newProduct.deleteAssociatedPart(associatedPartsTableView.getSelectionModel().getSelectedItem());
        }
        }
    }

    public void onActionCancel(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This product will not be saved, OK?");
        alert.setTitle("Confirmation of Cancellation");
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void onActionSaveProduct(javafx.event.ActionEvent actionEvent) throws IOException {
            // id auto generator
            int counter = 1;
            int newId = 1;

            for (Product product : Inventory.getAllProducts()) {
                counter = product.getId();
                counter++;
            }
            newId = counter;


        newProduct.setId(newId);
        newProduct.setName(addProductNameTxt.getText());
        newProduct.setInv(Integer.parseInt(addProductInvTxt.getText()));
        newProduct.setPrice(Double.parseDouble(addProductPriceTxt.getText()));
        newProduct.setMax(Integer.parseInt(addProductMaxTxt.getText()));
        newProduct.setMin(Integer.parseInt(addProductMinTxt.getText()));

        //the following exception handling checks to see if min < max
                try {
                    if (newProduct.getMin() <= newProduct.getMax()){
                        Inventory.addProduct(newProduct);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        newProduct = new Product(0, null, 0, 0.0, 0, 0);
        associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}



