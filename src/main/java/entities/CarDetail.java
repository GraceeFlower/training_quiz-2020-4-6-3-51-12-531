package entities;

import utils.ColumnName;

public class CarDetail {

    private int id;
    @ColumnName("car_number")
    private String carNumber;
    @ColumnName("state")
    private String carState;

    public CarDetail() {
    }

    public CarDetail(int id, String carNumber, String carState) {
        this.carNumber = carNumber;
        this.carState = carState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarState() {
        return carState;
    }

    public void setCarState(String carState) {
        this.carState = carState;
    }

}
