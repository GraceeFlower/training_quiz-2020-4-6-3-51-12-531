package entities;

import utils.ColumnName;

public class PartingLot {

    private String lotName;
    private int parkingNumber;

    public PartingLot() {
    }

    public PartingLot(String lotName, int parkingNumber) {
        this.lotName = lotName;
        this.parkingNumber = parkingNumber;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public int getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(int parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

}
