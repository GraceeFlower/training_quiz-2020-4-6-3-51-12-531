package repositories;

import entities.CarDetail;
import utils.JDBCUtil;
import utils.SqlUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class CarRepository implements CarRepositoryI {

    private static final String TABLE_NAME = "car_info";
    private static final String ENTER_STATE = "enter";
    private static final String EXIT_STATE = "exit";

    @Override
    public boolean isCarExist(String carNumber) {
        String sql = "SELECT car_number FROM " + TABLE_NAME + " WHERE car_number = ? AND state = ?";
        try {
            Connection conn = JDBCUtil.connectToDB();
            return (0 != SqlUtil.executeQuery(conn, sql, CarDetail.class, carNumber, ENTER_STATE).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CarDetail getCarDetail(String carNumber) {
        String sql = "INSERT INTO " + TABLE_NAME + " (car_number) VALUES (?)";
        CarDetail car = new CarDetail();
        try {
            Connection conn = JDBCUtil.connectToDB();
            SqlUtil.executeUpdate(conn, sql, carNumber);
            sql = "SELECT id, car_number FROM " + TABLE_NAME + " WHERE car_number = ? AND state = ?";
            conn = JDBCUtil.connectToDB();
            car = SqlUtil.executeQuerySingle(conn, sql, CarDetail.class, carNumber, ENTER_STATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public String getCarNumber(int carId) {
        String sql = "SELECT car_number FROM " + TABLE_NAME + " WHERE id = ? AND state = ?";
        String carNumber = "";
        try {
            Connection conn = JDBCUtil.connectToDB();
            CarDetail car = SqlUtil.executeQuerySingle(conn, sql, CarDetail.class, carId, ENTER_STATE);
            if (null != car) {
                carNumber = car.getCarNumber();
                sql = "UPDATE " + TABLE_NAME + " SET state = ? WHERE id = ?";
                conn = JDBCUtil.connectToDB();
                SqlUtil.executeUpdate(conn, sql, EXIT_STATE, carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carNumber;
    }
}
