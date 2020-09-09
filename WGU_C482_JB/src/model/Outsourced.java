package model;

// this is a subclass of Part with only one variable...companyName.

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, int inv, double price, int max, int min, String companyName){
        super (id, name, price, inv, max, min);
        this.companyName = companyName;
    }

    public String getCompanyName() {

        return companyName;
    }

    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
