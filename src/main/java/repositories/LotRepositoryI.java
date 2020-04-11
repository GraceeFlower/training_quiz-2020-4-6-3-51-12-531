package repositories;

import entities.SingleLot;

public interface LotRepositoryI {

    void refreshTable();
    void initParkingLot(String lotName, Integer lotNumber);
    SingleLot hasEmptyParking();
    SingleLot findEmptyParking(String lotName);
    void updateParking(int lotId, int carId);
    int getCarId(String[] ticketInfo);
    void removeCar(String[] ticketInfo);
}
