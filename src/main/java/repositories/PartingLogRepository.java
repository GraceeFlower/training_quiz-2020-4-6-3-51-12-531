package repositories;

import entities.PartingLot;
import entities.SingleLot;
import utils.JDBCUtil;
import utils.SqlUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PartingLogRepository {

    public void initPartingLot(List<PartingLot> partingLots) {
        try {
            String lotName;
            int lotNumber;
            String sql;
            for (PartingLot lot : partingLots) {
                lotName = lot.getLotName();
                refreshTable(lotName);
                lotNumber = lot.getParkingNumber();
                for (int i = 0; i < lotNumber; i++) {
                    Connection conn = JDBCUtil.connectToDB();
                    sql = "INSERT INTO " + lotName + " VALUES ()";
                    SqlUtil.executeUpdate(conn, sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable(String tableName) {
        try {
            Connection conn = JDBCUtil.connectToDB();
            SqlUtil.executeUpdate(conn, "TRUNCATE TABLE " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findPartInfo(String carNumber) {
        String[] lotNameArr = {"A", "B"};
        String sql;
        try {
            SingleLot lot;
            for (String name : lotNameArr) {
                sql = "SELECT id FROM " + name + " WHERE car_number IS NULL LIMIT 1";
                Connection conn = JDBCUtil.connectToDB();
                lot = SqlUtil.executeQuerySingle(conn, sql, SingleLot.class);
                if (null != lot) {
                    lot.setCarNumber(carNumber);
                    conn = JDBCUtil.connectToDB();
                    sql = "UPDATE " + name + " SET car_number = ? WHERE id = ?";
                    SqlUtil.executeUpdate(conn, sql, carNumber, lot.getLotId());
                    return name + "," + lot;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


}
