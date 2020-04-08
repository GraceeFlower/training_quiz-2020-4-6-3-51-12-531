package repositories;

import entities.CarDetail;
import staticRes.StaticInfo;
import utils.JDBCUtil;
import utils.SqlUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class CarRepository implements CarRepositoryI {

    @Override
    public boolean isCarExist(String carNumber) {
        String sql = "SELECT car_number FROM " + StaticInfo.CAR_TABLE + " WHERE car_number = ? AND state = ?";
        try {
            Connection conn = JDBCUtil.connectToDB();
            return (0 != SqlUtil.executeQuery(conn, sql, CarDetail.class, carNumber, StaticInfo.ENTER_STATE).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CarDetail getCarDetail(String carNumber) {
        String sql = "INSERT INTO " + StaticInfo.CAR_TABLE + " (car_number) VALUES (?)";
        CarDetail car = new CarDetail();
        try {
            Connection conn = JDBCUtil.connectToDB();
            SqlUtil.executeUpdate(conn, sql, carNumber);
            sql = "SELECT id, car_number FROM " + StaticInfo.CAR_TABLE + " WHERE car_number = ? AND state = ?";
            conn = JDBCUtil.connectToDB();
            car = SqlUtil.executeQuerySingle(conn, sql, CarDetail.class, carNumber, StaticInfo.ENTER_STATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public String getCarNumber(int carId) {
        String sql = "SELECT car_number FROM " + StaticInfo.CAR_TABLE + " WHERE id = ? AND state = ?";
        String carNumber = "";
        try {
            Connection conn = JDBCUtil.connectToDB();
            CarDetail car = SqlUtil.executeQuerySingle(conn, sql, CarDetail.class, carId, StaticInfo.ENTER_STATE);
            if (null != car) {
                carNumber = car.getCarNumber();
                sql = "UPDATE " + StaticInfo.CAR_TABLE + " SET state = ? WHERE id = ?";
                conn = JDBCUtil.connectToDB();
                SqlUtil.executeUpdate(conn, sql, StaticInfo.EXIT_STATE, carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carNumber;
    }
}
