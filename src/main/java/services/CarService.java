package services;

import entities.CarDetail;
import exception.CarExistingException;
import exception.InvalidTicketException;
import exception.WrongFormatException;
import repositories.CarRepository;
import utils.SqlUtil;

public class CarService implements CarServiceI {

    private static final String TABLE_NAME = "car_info";
    private static final String WRONG_INPUT = "格式错误！";
    private CarRepository carRepository = new CarRepository();

    @Override
    public void initCarDetail() {
        SqlUtil.refreshTable(TABLE_NAME);
    }

    @Override
    public CarDetail getCarDetail(String carNumber) {
        if (!carNumber.matches("^[A-Z][A-Z0-9]{5}$")) {
            throw new WrongFormatException(WRONG_INPUT);
        } else if (carRepository.isCarExist(carNumber)) {
            throw new CarExistingException("车辆已经存在！");
        } else {
            return carRepository.getCarDetail(carNumber);
        }
    }

    @Override
    public String getCarNumber(int carId) {
        if (0 == carId) {
            throw new InvalidTicketException("停车票无效");
        }
        return carRepository.getCarNumber(carId);
    }

}
