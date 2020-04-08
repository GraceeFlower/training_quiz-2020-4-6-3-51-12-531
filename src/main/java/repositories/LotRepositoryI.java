package repositories;

import entities.SingleLot;

public interface LotRepositoryI {

    void refreshTable();
    void initParkingLot(String lotName, Integer lotNumber);
    SingleLot findEmptyParking(String lotName, int carId);
    int getCarId(String[] ticketInfo);
    void removeCar(String[] ticketInfo);
}
