/*
Josh Burnette
Student ID: 001221270
BSCS
Mentor: Karitsa McCoy
September 2020
WGU C482

 */

package jbjavac482application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //pre-populates the table...
        Part p1 = new InHouse(1, "inhouse", 1, 1.0, 1,1, 1);
        Inventory.addPart(p1);
        Part p2 = new Outsourced(2, "outsourced", 2, 2.30, 2, 2, "Two");
        Inventory.addPart(p2);
        Product pr1 = new Product(1, "Product A", 11, 9.99, 4, 4);
        Inventory.addProduct(pr1);

        launch(args);

    }



}
