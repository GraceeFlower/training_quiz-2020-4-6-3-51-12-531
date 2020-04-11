package entities;

import repositories.LotRepository;

import java.util.Random;

public abstract class ParkingAttendant {

    public abstract SingleLot provideTicket(CarDetail car, LotRepository lotRepository);
    public static int[] calculateParkingCharge() {
        int[] info = new int[2];
        Random r = new Random();
        info[0] = r.nextInt(24) + 1;
        int freeTime = 2;
        int limitTime = 5;
        int minCharge = 5;
        int maxCharge = 10;
        if (limitTime > info[0]) {
            info[1] = (info[0] - freeTime) * minCharge;
        } else {
            info[1] = (limitTime - freeTime) * minCharge + (info[0] - limitTime) * maxCharge;
        }
        return info;
    }
}
