package services;

public interface LotServiceI {

    void initParkingLot(String[] lots);
    String findEmptyParking(String carNumber);
    String checkTicket(String[] ticketInfo);
}
