package entities;

import utils.ColumnName;

public class ParkingLot {

    @ColumnName("lname")
    private String lotName;
    @ColumnName("part_total")
    private int parkingNumber;

    public ParkingLot() {
    }

    public ParkingLot(String lotName, int parkingNumber) {
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
