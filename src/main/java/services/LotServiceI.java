package services;

import entities.CarDetail;

public interface LotServiceI {

    void initParkingLot(String[] lots);
    String findEmptyParking(CarDetail car);
    int getCarId(String[] ticketInfo);
    void removeCar(String carNumber, String[] ticketInfo);
}
