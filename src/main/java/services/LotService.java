package services;

import entities.*;
import exception.InvalidTicketException;
import exception.ParkingLotFullException;
import exception.WrongFormatException;
import repositories.LotRepository;
import staticRes.StaticInfo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class LotService implements LotServiceI {

    private static LotRepository lotRepository = new LotRepository();
    public static Map<String, Integer> lotList = new LinkedHashMap<>();

    @Override
    public void initParkingLot(String[] lots) {
        for (String l : lots) {
            String[] lotInfo = l.split(":");
            if (2 != lotInfo.length) {
                throw new WrongFormatException(StaticInfo.WRONG_INPUT);
            }
            lotList.put(lotInfo[0], Integer.parseInt(lotInfo[1]));
        }
        lotRepository.refreshTable();
        for (Map.Entry<String, Integer> entry : lotList.entrySet()) {
            lotRepository.initParkingLot(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String findEmptyParking(CarDetail car) {
        Random random = new Random();
        int type = random.nextInt(2);
        ParkingAttendant attendant = new SillyMember();
        if (1 == type) {
            attendant = new SmartMember();
        }
        SingleLot checkedLot = attendant.provideTicket(car, lotRepository);
        lotRepository.updateParking(checkedLot.getLotId(), car.getId());
        return checkedLot + "," + car.getCarNumber();
    }

    @Override
    public void hasEmptyParking() {
        if (null == lotRepository.hasEmptyParking()) {
            throw new ParkingLotFullException(StaticInfo.LOT_FULL);
        }
    }

    @Override
    public int getCarId(String[] ticketInfo) {
        int carId;
        if (3 == ticketInfo.length && ticketInfo[2].matches("^[A-Z][A-Z0-9]{5}$")) {
            int total = Integer.parseInt(ticketInfo[1]);
            if (lotList.containsKey(ticketInfo[0])
                && total > 0 && total <= lotList.get(ticketInfo[0])) {
                carId = lotRepository.getCarId(ticketInfo);
            } else {
                throw new InvalidTicketException(StaticInfo.INVALID_TICKET);
            }
        } else {
            throw new WrongFormatException(StaticInfo.WRONG_INPUT);
        }
        return carId;
    }

    @Override
    public void removeCar(String carNumber, String[] ticketInfo) {
        if (carNumber.equals(ticketInfo[2])) {
            lotRepository.removeCar(ticketInfo);
        } else {
            throw new InvalidTicketException(StaticInfo.INVALID_TICKET);
        }
    }
}
