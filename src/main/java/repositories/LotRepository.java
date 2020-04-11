package repositories;

import entities.SingleLot;
import staticRes.StaticInfo;
import utils.JDBCUtil;
import utils.SqlUtil;

import java.sql.*;

public class LotRepository implements LotRepositoryI {

    @Override
    public void refreshTable() {
        SqlUtil.refreshTable(StaticInfo.LOT_TABLE);
    }

    @Override
    public void initParkingLot(String lotName, Integer lotNumber) {
        try {
            Connection conn;
            String sql = "INSERT INTO " + StaticInfo.LOT_TABLE + " (lot_name, lot_no) VALUES (?, ?)";
            for (int i = 0; i < lotNumber; i++) {
                conn = JDBCUtil.connectToDB();
                SqlUtil.executeUpdate(conn, sql, lotName, i + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SingleLot findEmptyParking(String lotName) {
        SingleLot lot = null;
        String sql = "SELECT id, lot_name, lot_no FROM " + StaticInfo.LOT_TABLE + " WHERE lot_name = ? AND car_id = 0 LIMIT 1";
        try {
            Connection conn = JDBCUtil.connectToDB();
            lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class, lotName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lot;
    }

    @Override
    public void updateParking(int lotId, int carId) {
        try {
            Connection conn = JDBCUtil.connectToDB();
            String sql = "UPDATE " + StaticInfo.LOT_TABLE + " SET car_id = ? WHERE id = ?";
            SqlUtil.executeUpdate(conn, sql, carId, lotId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SingleLot hasEmptyParking() {
        String sql = "SELECT id FROM " + StaticInfo.LOT_TABLE + " WHERE car_id = 0 LIMIT 1";
        SingleLot lot = null;
        try {
            Connection conn = JDBCUtil.connectToDB();
            lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class);
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
            String sql = "SELECT car_id FROM " + StaticInfo.LOT_TABLE + " WHERE lot_name = ? AND lot_no = ?";
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
        String sql = "UPDATE " + StaticInfo.LOT_TABLE + " SET car_id = 0 WHERE lot_name = ? AND lot_no = ?";
        try {
            Connection conn = JDBCUtil.connectToDB();
            SqlUtil.executeUpdate(conn, sql, ticketInfo[0], ticketInfo[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
