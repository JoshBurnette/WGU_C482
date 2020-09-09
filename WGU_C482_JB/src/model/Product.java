package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private int id;
    private String name;
    private double price;
    private int inv;
    private int max;
    private int min;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product(int id, String name, int inv, double price, int max, int min){

        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.max = max;
        this.min = min;
    }



    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    public void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }


    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }



    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
        this.inv = inv;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
