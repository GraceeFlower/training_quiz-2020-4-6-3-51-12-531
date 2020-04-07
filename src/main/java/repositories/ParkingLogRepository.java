package repositories;

import entities.ParkingLot;
import entities.SingleLot;
import utils.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLogRepository {

    private static SingleLotRepository singleLotRepository = new SingleLotRepository();

    public void initParkingLot(Map<String, Integer> lotList) {
//        for (ParkingLot lot : parkingLots) {
//            lotList.put(lot.getLotName(), lot.getParkingNumber());
//            singleLotRepository.initSingle(lot);
//        }
        for (Map.Entry<String, Integer> entry : lotList.entrySet()) {
            singleLotRepository.initSingle(entry.getKey(), entry.getValue());
        }
    }

    public String findParkInfo(Map<String, Integer> lotList, String carNumber) {
        String ticket = "";
//        try {
//            Connection conn = JDBCUtil.connectToDB();
//            for (Map.Entry<String, Integer> entry : lotList.entrySet()) {
//                String key = entry.getKey();
//                Integer value = entry.getValue();
//                System.out.println(key + "," + value);
//            }
//            String sql = "SELECT lname FROM " + tableName;
//            List<ParkingLot> lots = SqlUtil.executeQuery(conn, sql, ParkingLot.class);
//            for (ParkingLot lot: lots) {
//                SingleLot checkedLot = singleLotRepository.findEmptyParking(lot.getLotName(), carNumber);
//                if (null != checkedLot) {
//                    ticket = lot.getLotName() + "," + checkedLot;
//                    break;
//                }
//            }
            for (String key: lotList.keySet()) {
                SingleLot checkedLot = singleLotRepository.findEmptyParking(key, carNumber);
                if (null != checkedLot) {
                    ticket = key + "," + checkedLot;
                    break;
                }
            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return ticket;
    }

    public String checkTicket(Map<String, Integer> lotList, String[] ticketInfo) {
        String ticket = "";
        int total = Integer.parseInt(ticketInfo[1]);
        if (lotList.containsKey(ticketInfo[0]) && total > 0 && total < lotList.get(ticketInfo[0])) {
            ticket = singleLotRepository.checkTicket(ticketInfo);
        }
        return ticket;
    }
}
