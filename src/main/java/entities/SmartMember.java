package entities;

import repositories.LotRepository;
import staticRes.StaticInfo;
import utils.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SmartMember extends ParkingAttendant {

    @Override
    public SingleLot provideTicket(CarDetail car, LotRepository lotRepository) {
        return lotRepository.findEmptyParking(findEmptierLot());
    }

    private String findEmptierLot() {
        String sql = "SELECT lot_name, COUNT(*) AS count FROM " + StaticInfo.LOT_TABLE + " WHERE car_id = 0 GROUP BY lot_name";
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        String maxLot = "";
        int maxCount = 0;
        try {
            conn = JDBCUtil.connectToDB();
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {
                if (maxCount < res.getInt("count")) {
                    maxLot = res.getString("lot_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseSource(res, stat, conn);
        }
        return maxLot;
    }
}
