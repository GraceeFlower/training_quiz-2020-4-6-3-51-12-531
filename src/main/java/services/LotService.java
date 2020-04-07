package services;

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
        if (lots.length != 2 || !lots[0].startsWith("A:") || !lots[1].startsWith("B:")) {
            throw new WrongFormatException(WRONG_INPUT);
        } else {
            for (String l : lots) {
                String[] lotInfo = l.split(":");
                lotList.put(lotInfo[0], Integer.parseInt(lotInfo[1]));
            }
        }
        for (Map.Entry<String, Integer> entry : lotList.entrySet()) {
            lotRepository.initParkingLot(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String findEmptyParking(String carNumber) {
        String ticket = "";
        if (carNumber.matches("^[A-Z][A-Z0-9]{5}$")) {
            for (String key: lotList.keySet()) {
                SingleLot checkedLot = lotRepository.findEmptyParking(key, carNumber);
                if (null != checkedLot) {
                    ticket = key + "," + checkedLot;
                    break;
                }
            }
            if (ticket.equals("")) {
                throw new ParkingLotFullException("非常抱歉，由于车位已满，暂时无法为您停车！");
            }
        } else {
            throw new WrongFormatException(WRONG_INPUT);
        }
        return ticket;
    }

    @Override
    public String checkTicket(String[] ticketInfo) {
        String carNumber = "";
        if (3 == ticketInfo.length) {
            int total = Integer.parseInt(ticketInfo[1]);
            if (lotList.containsKey(ticketInfo[0]) && total > 0 && total < lotList.get(ticketInfo[0])) {
                carNumber = lotRepository.checkTicket(ticketInfo);
                if (carNumber.equals("")) {
                    throw new InvalidTicketException("停车券无效");
                }
            }else {
                throw new WrongFormatException(WRONG_INPUT);
            }
        }
        return carNumber;
    }
}
