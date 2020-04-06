package repositories;

import entities.PartingLot;
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
}
