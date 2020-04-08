package repositories;

import entities.CarDetail;

public interface CarRepositoryI {
    boolean isCarExist(String carNumber);
    CarDetail getCarDetail(String carNumber);
    String getCarNumber(int carId);
}
