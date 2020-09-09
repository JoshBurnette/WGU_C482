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


public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;


    public TextField modifyProductSearchTxt;
    public Label modifyProductIdTxt;
    public TextField modifyProductNameTxt;
    public TextField modifyProductInvTxt;
    public TextField modifyProductPriceTxt;
    public TextField modifyProductMaxTxt;
    public TextField modifyProductMinTxt;

    @FXML
    private TableView<Part> allPartsTableView;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn associatedPartIdCol;
    @FXML
    private TableColumn associatedPartNameCol;
    @FXML
    private TableColumn associatedPartInvCol;
    @FXML
    private TableColumn associatedPartPriceCol;

    Product newProduct = new Product(0, null, 0, 0.0, 0, 0);
    Alert error = new Alert(Alert.AlertType.ERROR);
    ObservableList<Part> currentParts = FXCollections.observableArrayList();

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    public void addFilteredPart(Part searchedPart){
        filteredParts.add(searchedPart);
    }

    public ObservableList<Part> getFilteredParts(){
        return filteredParts;
    }

    public void onActionSearchParts(ActionEvent actionEvent) {
        if (modifyProductSearchTxt.getText().isEmpty()) {
            allPartsTableView.setItems(Inventory.getAllParts());
        }
        else {
            filteredParts.clear();
            Inventory.filteredList.clear();
            //See if user text is an int, if not, go to catch
            try{
                addFilteredPart(Inventory.lookupPart(Integer.parseInt(modifyProductSearchTxt.getText())));

                allPartsTableView.setItems(getFilteredParts());
            }
            //this is if user text is a string
            catch (Exception e){
                Inventory.lookupPart(modifyProductSearchTxt.getText());

                allPartsTableView.setItems(Inventory.getFilteredList());
            }
        }

    }

    public void updateAssocParts(){
        for(int i = 0; i < currentParts.size(); i++){
            newProduct.addAssociatedPart(currentParts.get(i));
        }
    }

    public void onActionAddPartToProduct(ActionEvent actionEvent) {
        Part selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(selectedPart);
        associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
        }


    public void onActionDeletePartFromProduct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete this associated part from the product, OK?");
        alert.setTitle("Confirmation of Deletion");
        if (associatedPartsTableView.getSelectionModel().getSelectedItem() != null){
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            newProduct.deleteAssociatedPart((Part) associatedPartsTableView.getSelectionModel().getSelectedItem());
            associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
        }
        }
    }

    public void onActionCancel(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This product will not be modified, OK?");
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
        newProduct.setId(Integer.parseInt(modifyProductIdTxt.getText()));
        newProduct.setName(modifyProductNameTxt.getText());
        newProduct.setInv(Integer.parseInt(modifyProductInvTxt.getText()));
        newProduct.setPrice(Double.parseDouble(modifyProductPriceTxt.getText()));
        newProduct.setMax(Integer.parseInt(modifyProductMaxTxt.getText()));
        newProduct.setMin(Integer.parseInt(modifyProductMinTxt.getText()));

        //the following exception handling checks to see if min < max
        try {
            if (newProduct.getMin() <= newProduct.getMax()){
                //Product modifyProduct = new Product(id, name, inv, price, max, min);
                Inventory.updateProduct(selectedIndex, newProduct);

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

    Product selectedProduct;
    int selectedIndex;


    public Product getSelectedProduct() {
        return selectedProduct;

    }

    public void setSelectedProduct(Product product, int index) {
        selectedProduct = product;
        selectedIndex = index;

        if (product instanceof Product) {
            Product newProduct = product;

            this.modifyProductIdTxt.setText(Integer.toString(newProduct.getId()));
            this.modifyProductNameTxt.setText(newProduct.getName());
            this.modifyProductInvTxt.setText((Integer.toString(newProduct.getInv())));
            this.modifyProductPriceTxt.setText((Double.toString(newProduct.getPrice())));
            this.modifyProductMaxTxt.setText((Integer.toString(newProduct.getMax())));
            this.modifyProductMinTxt.setText((Integer.toString(newProduct.getMin())));

            associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
            currentParts = newProduct.getAllAssociatedParts();
            updateAssocParts();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        allPartsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
