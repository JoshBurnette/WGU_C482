package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable  {

    public TableView<Product> productTableView;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productPriceCol;

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

    Stage stage;
    Parent scene;

    @FXML
    private TextField searchPartsTxt;

    @FXML
    private TextField searchProductsTxt;

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    public void addFilteredPart(Part searchedPart){
        filteredParts.add(searchedPart);
    }

    public ObservableList<Part> getFilteredParts(){
        return filteredParts;
    }

    public void addFilteredroduct(Product searchedProduct){
        filteredProducts.add(searchedProduct);
    }

    public ObservableList<Product> getFilteredProducts(){
        return filteredProducts;
    }


    public void onActionSearchParts(javafx.event.ActionEvent actionEvent) {
        if (searchPartsTxt.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
        else {
            filteredParts.clear();
            Inventory.filteredList.clear();
           // see if user text is an int, if not, go to catch
           try {
               addFilteredPart(Inventory.lookupPart(Integer.parseInt(searchPartsTxt.getText())));
               partTableView.setItems(getFilteredParts());
            }
           // if user text is a string
           catch(Exception e) {
                Inventory.lookupPart(searchPartsTxt.getText());
                partTableView.setItems(Inventory.getFilteredList());
                }
        }

    }

    public void onActionDeletePart(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete this part, OK?");
        alert.setTitle("Confirmation of Deletion");
        if (partTableView.getSelectionModel().getSelectedItem() != null){
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
            partTableView.setItems(Inventory.getAllParts());
        }
        }
    }

    public void onActionModifyPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        if (partTableView.getSelectionModel().getSelectedItem() != null) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));

            root = loader.load();
            ModifyPartController controller = loader.getController();
            Part part = partTableView.getSelectionModel().getSelectedItem();
            int index = partTableView.getSelectionModel().getSelectedIndex();
            if (part != null) {
                controller.setSelectedPart(part, index);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    public void onActionAddPart(javafx.event.ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    public void onActionSearchProducts(javafx.event.ActionEvent actionEvent) {
        if (searchProductsTxt.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        }
        else {
            filteredProducts.clear();
            Inventory.filteredProductList.clear();
            // see if user text is an int, if not, go to catch
            try {
                addFilteredroduct(Inventory.lookupProduct(Integer.parseInt(searchProductsTxt.getText())));
                productTableView.setItems(getFilteredProducts());
                }
            //if user text is a string
            catch (Exception e){
                productTableView.setItems(Inventory.lookupProduct(searchProductsTxt.getText()));
            }
        }

    }

    public void onActionAddProduct(javafx.event.ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionModifyProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        if (productTableView.getSelectionModel().getSelectedItem() != null) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));

            root = loader.load();
            ModifyProductController controller = loader.getController();
            Product product = productTableView.getSelectionModel().getSelectedItem();
            int index = productTableView.getSelectionModel().getSelectedIndex();
            if (product != null) {
                controller.setSelectedProduct(product, index);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onActionDeleteProduct(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete this product, OK?");
        alert.setTitle("Confirmation of Deletion");
        if (productTableView.getSelectionModel().getSelectedItem() != null){
        Optional<ButtonType> result = alert.showAndWait();
        if(((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            productTableView.setItems(Inventory.getAllProducts());
        }
        }
    }

    public void onActionExit(javafx.event.ActionEvent actionEvent) {
                System.exit(0);
    }


    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
