import entities.CarDetail;
import exception.CarExistingException;
import exception.InvalidTicketException;
import exception.ParkingLotFullException;
import exception.WrongFormatException;
import services.CarService;
import services.LotService;

import java.util.*;

public class Application {

    private static LotService lotService = new LotService();
    private static CarService carService = new CarService();

    public static void main(String[] args) {
        operateParking();
    }

    public static void operateParking() {
        while (true) {
            System.out.println("1. 初始化停车场数据\n2. 停车\n3. 取车\n4. 退出\n请输入你的选择(1~4)：");
            Scanner printItem = new Scanner(System.in);
            String choice = printItem.next();
            if (choice.equals("4")) {
                System.out.println("系统已退出");
                break;
            }
            handle(choice);
        }
    }

    private static void handle(String choice) {
        Scanner scanner = new Scanner(System.in);
        if (choice.equals("1")) {
            System.out.println("请输入初始化数据\n格式为\"停车场编号1：车位数,停车场编号2：车位数\" 如 \"A:8,B:9\"：");
            String initInfo = scanner.next();
            try {
                init(initInfo);
            } catch (WrongFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (choice.equals("2")) {
            System.out.println("请输入车牌号\n格式为\"车牌号\" 如: \"A12098\"：");
            String carInfo = scanner.next();
            try {
                String ticket = park(carInfo);
                String[] ticketDetails = ticket.split(",");
                System.out.format("已将您的车牌号为%s的车辆停到%s停车场%s号车位，停车券为：%s，请您妥善保存。\n", ticketDetails[2], ticketDetails[0], ticketDetails[1], ticket);
            } catch (ParkingLotFullException | WrongFormatException | CarExistingException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (choice.equals("3")) {
            System.out.println("请输入停车券信息\n格式为\"停车场编号1,车位编号,车牌号\" 如 \"A,1,8\"：");
            String ticket = scanner.next();
            try {
                String car = fetch(ticket);
                System.out.format("已为您取到车牌号为%s的车辆，很高兴为您服务，祝您生活愉快!\n", car);
            } catch (InvalidTicketException | WrongFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void init(String initInfo) throws WrongFormatException {
        String[] lots = initInfo.split(",");
        Arrays.sort(lots);
        lotService.initParkingLot(lots);
        carService.initCarDetail();
    }

    public static String park(String carNumber) throws ParkingLotFullException, WrongFormatException, CarExistingException {
        CarDetail car = carService.getCarDetail(carNumber);
        return lotService.findEmptyParking(car);
    }

    public static String fetch(String ticket) throws InvalidTicketException, WrongFormatException {
        String[] ticketInfo = ticket.split(",");
        int carId = lotService.getCarId(ticketInfo);
        String carNumber = carService.getCarNumber(carId);
        lotService.removeCar(carNumber, ticketInfo);
        return carNumber;
    }

}
