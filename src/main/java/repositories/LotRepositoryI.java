package repositories;

import entities.SingleLot;

public interface LotRepositoryI {

    void initParkingLot(String lotName, Integer lotNumber);
    SingleLot findEmptyParking(String lotName, String carNumber);
    String checkTicket(String[] ticketInfo);
}
