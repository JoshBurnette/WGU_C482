package model;

// this is a subclass of Part with only one variable...machineId.

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, int inv, double price,  int max, int min, int machineId){
        super (id, name, price, inv, max, min);
        this.machineId = machineId;
    }


    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
