package services;

import entities.CarDetail;

public interface CarServiceI {

    void initCarDetail();
    CarDetail getCarDetail(String carNumber);
    String getCarNumber(int carId);
}
