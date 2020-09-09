package model;

public abstract class Part {

  private int id;
  private String name;
  private double price;
  private int inv;
  private int min;
  private int max;



    public Part(int id, String name, double price, int inv, int max, int min){
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.max = max;
        this.min = min;
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
