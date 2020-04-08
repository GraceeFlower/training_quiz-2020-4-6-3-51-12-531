package repositories;

import entities.SingleLot;
import utils.JDBCUtil;
import utils.SqlUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class LotRepository implements LotRepositoryI {

    private static final String TABLE_NAME = "lot_list";

    @Override
    public void refreshTable() {
        SqlUtil.refreshTable(TABLE_NAME);
    }

    @Override
    public void initParkingLot(String lotName, Integer lotNumber) {
        try {
            Connection conn;
            String sql = "INSERT INTO " + TABLE_NAME + " (lot_name, lot_no) VALUES (?, ?)";
            for (int i = 0; i < lotNumber; i++) {
                conn = JDBCUtil.connectToDB();
                SqlUtil.executeUpdate(conn, sql, lotName, i + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SingleLot findEmptyParking(String lotName, int carId) {
        SingleLot lot = null;
        String sql = "SELECT id, lot_name, lot_no FROM " + TABLE_NAME + " WHERE car_id = 0 LIMIT 1";
        try {
            Connection conn = JDBCUtil.connectToDB();
            lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class);
            if (null != lot) {
                lot.setCarId(carId);
                conn = JDBCUtil.connectToDB();
                sql = "UPDATE " + TABLE_NAME + " SET car_id = ? WHERE id = ?";
                SqlUtil.executeUpdate(conn, sql, carId, lot.getLotId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lot;
    }

    @Override
    public int getCarId(String[] ticketInfo) {
        int carId = 0;
        try {
            Connection conn = JDBCUtil.connectToDB();
            SingleLot lot;
            String sql = "SELECT car_id FROM " + TABLE_NAME + " WHERE lot_name = ? AND lot_no = ?";
            lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class, ticketInfo[0], ticketInfo[1]);
            if (lot != null) {
                carId = lot.getCarId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carId;
    }

    @Override
    public void removeCar(String[] ticketInfo) {
        String sql = "UPDATE " + TABLE_NAME + " SET car_id = 0 WHERE lot_name = ? AND lot_no = ?";
        try {
            Connection conn = JDBCUtil.connectToDB();
            SqlUtil.executeUpdate(conn, sql, ticketInfo[0], ticketInfo[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
