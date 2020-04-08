package services;

import entities.CarDetail;
import entities.SingleLot;
import exception.InvalidTicketException;
import exception.ParkingLotFullException;
import exception.WrongFormatException;
import repositories.LotRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class LotService implements LotServiceI {

    private static LotRepository lotRepository = new LotRepository();
    private static Map<String, Integer> lotList = new LinkedHashMap<>();
    private static final String WRONG_INPUT = "格式错误！";

    @Override
    public void initParkingLot(String[] lots) {
        for (String l : lots) {
            String[] lotInfo = l.split(":");
            if (2 != lotInfo.length) {
                throw new WrongFormatException(WRONG_INPUT);
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
        String ticket = "";
        for (String key: lotList.keySet()) {
            SingleLot checkedLot = lotRepository.findEmptyParking(key, car.getId());
            if (null != checkedLot) {
                ticket = checkedLot + "," + car.getCarNumber();
                break;
            }
        }
        if (ticket.equals("")) {
            throw new ParkingLotFullException("非常抱歉，由于车位已满，暂时无法为您停车！");
        }
        return ticket;
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
                throw new InvalidTicketException("停车票无效");
            }
        } else {
            throw new WrongFormatException(WRONG_INPUT);
        }
        return carId;
    }

    @Override
    public void removeCar(String carNumber, String[] ticketInfo) {
        if (carNumber.equals(ticketInfo[2])) {
            lotRepository.removeCar(ticketInfo);
        } else {
            throw new InvalidTicketException("停车票无效");
        }
    }
}
