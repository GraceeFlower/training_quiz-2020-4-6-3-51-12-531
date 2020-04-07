package repositories;

import entities.SingleLot;
import utils.JDBCUtil;
import utils.SqlUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class LotRepository implements LotRepositoryI {

    @Override
    public void initParkingLot(String lotName, Integer lotNumber) {
        SqlUtil.refreshTable(lotName);
        try {
            Connection conn;
            String sql = "INSERT INTO " + lotName + " VALUES ()";
            for (int i = 0; i < lotNumber; i++) {
                conn = JDBCUtil.connectToDB();
                SqlUtil.executeUpdate(conn, sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SingleLot findEmptyParking(String lotName, String carNumber) {
        SingleLot lot = null;
        String sql = "SELECT id FROM " + lotName + " WHERE car_number IS NULL LIMIT 1";
        try {
            Connection conn = JDBCUtil.connectToDB();
            lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class);
            if (null != lot) {
                lot.setCarNumber(carNumber);
                conn = JDBCUtil.connectToDB();
                sql = "UPDATE " + lotName + " SET car_number = ? WHERE id = ?";
                SqlUtil.executeUpdate(conn, sql, carNumber, lot.getLotId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lot;
    }

    @Override
    public String checkTicket(String[] ticketInfo) {
        try {
            Connection conn = JDBCUtil.connectToDB();
            SingleLot lot;
            String sql = "SELECT car_number FROM " + ticketInfo[0] + " WHERE id = ? AND car_number = ?";
            lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class, ticketInfo[1], ticketInfo[2]);
            if (lot != null) {
                conn = JDBCUtil.connectToDB();
                sql = "UPDATE " + ticketInfo[0] + " SET car_number = NULL WHERE id = ?";
                SqlUtil.executeUpdate(conn, sql, ticketInfo[1]);
                return lot.getCarNumber();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
