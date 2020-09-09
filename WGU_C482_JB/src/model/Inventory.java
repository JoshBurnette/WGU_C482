package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// this creates an array of parts and an array of products...and a few methods, add, delete, lookup, update and getAll

public class Inventory {

   // Array of Parts and Array of Products

   private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
   private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

   public static void addPart(Part part){
      allParts.add(part);
   }

   public static ObservableList<Part> getAllParts(){
      return allParts;
   }


   public static void addProduct(Product product){
      allProducts.add(product);
   }

   public static ObservableList<Product> getAllProducts(){
      return allProducts;
   }


   public static void deletePart (Part selectedPart){

      allParts.remove(selectedPart);
   }

   public static void deleteProduct(Product selectedProduct){
      allProducts.remove(selectedProduct);
   }

   public static void updatePart(int index, Part newPart){
      Part tempPart = Inventory.lookupPart(index);
      Inventory.deletePart(tempPart);
      Inventory.addPart(newPart);
   }

   public static void updateProduct(int index, Product newProduct){
      allProducts.set(index, newProduct);
   }

   // look up part using ID
   public static Part lookupPart(int partId){
      for (Part searchedPart : Inventory.getAllParts()){
         if(searchedPart.getId() == partId)
            return searchedPart;
      }
         return null;
   }

   //look up product using ID
   public static Product lookupProduct(int productId){
      for (Product searchedProduct : allProducts){
         if(searchedProduct.getId() == productId)
            return searchedProduct;
      }
      return null;
   }

   //look up part using a string
   public static ObservableList<Part> filteredList = FXCollections.observableArrayList();

   public static void addFilteredPart(Part part){
      filteredList.add(part);
   }

   public static ObservableList<Part> getFilteredList(){
      return filteredList;
   }

   public static ObservableList<Part> lookupPart(String partName){

      for (Part part : allParts){
         if (part.getName().contains(partName)){
            Inventory.addFilteredPart(part);
         }
      }

      return filteredList;
   }

   //look up product using a string
   public static ObservableList<Product> filteredProductList = FXCollections.observableArrayList();

   public static void addFilteredProduct(Product product){
      filteredProductList.add(product);
   }

   public static ObservableList<Product> getFilteredProductList(){
      return filteredProductList;
   }

   public static ObservableList<Product> lookupProduct(String productName){
      ObservableList<Product> tempProductList = FXCollections.observableArrayList();
      for (Product product : allProducts){
         if (productName.compareTo(product.getName()) == 0){
            tempProductList.add(product);
         }
      }
      return tempProductList;
   }

}
