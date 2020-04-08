package services;

import entities.CarDetail;
import exception.CarExistingException;
import exception.InvalidTicketException;
import exception.WrongFormatException;
import repositories.CarRepository;
import staticRes.StaticInfo;
import utils.SqlUtil;

public class CarService implements CarServiceI {

    private CarRepository carRepository = new CarRepository();

    @Override
    public void initCarDetail() {
        SqlUtil.refreshTable(StaticInfo.CAR_TABLE);
    }

    @Override
    public CarDetail getCarDetail(String carNumber) {
        if (!carNumber.matches("^[A-Z][A-Z0-9]{5}$")) {
            throw new WrongFormatException(StaticInfo.WRONG_INPUT);
        } else if (carRepository.isCarExist(carNumber)) {
            throw new CarExistingException(StaticInfo.CAR_EXIST);
        } else {
            return carRepository.getCarDetail(carNumber);
        }
    }

    @Override
    public String getCarNumber(int carId) {
        if (0 == carId) {
            throw new InvalidTicketException(StaticInfo.INVALID_TICKET);
        }
        return carRepository.getCarNumber(carId);
    }

}
