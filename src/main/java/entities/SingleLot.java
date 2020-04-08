package entities;

import utils.ColumnName;

public class SingleLot {

    @ColumnName("id")
    private int lotId;
    @ColumnName("lot_name")
    private String lotName;
    @ColumnName("lot_no")
    private int lotNumber;
    @ColumnName("car_id")
    private int carId;

    public SingleLot() {
    }

    public SingleLot(int lotId, String lotName, int lotNumber, int carId) {
        this.lotId = lotId;
        this.lotName = lotName;
        this.lotNumber = lotNumber;
        this.carId = carId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
