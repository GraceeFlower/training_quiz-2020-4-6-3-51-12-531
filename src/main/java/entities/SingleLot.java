package entities;

import utils.ColumnName;

public class SingleLot {

    @ColumnName("id")
    private int lotId;
    @ColumnName("car_number")
    private String carNumber;

    public SingleLot() {
    }

    public SingleLot(int lotId, String carNumber) {
        this.lotId = lotId;
        this.carNumber = carNumber;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return lotId + "," + carNumber;
    }
}
