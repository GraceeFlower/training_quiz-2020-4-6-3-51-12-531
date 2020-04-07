import exception.InvalidTicketException;
import exception.ParkingLotFullException;
import exception.WrongFormatException;
import repositories.ParkingLogRepository;

import java.util.*;

public class Application {

    private static ParkingLogRepository lotRepository = new ParkingLogRepository();
    private static Map<String, Integer> lotList = new LinkedHashMap<>();
    private static final String WRONG_INPUT = "格式错误！";

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
            } catch (ParkingLotFullException | WrongFormatException e) {
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
        if (lots.length != 2 || !lots[0].startsWith("A:") || !lots[1].startsWith("B:")) {
            throw new WrongFormatException(WRONG_INPUT);
        } else {
            for (String l : lots) {
                String[] lotInfo = l.split(":");
                lotList.put(lotInfo[0], Integer.parseInt(lotInfo[1]));
            }
            lotRepository.initParkingLot(lotList);
        }
    }

    public static String park(String carNumber) throws ParkingLotFullException, WrongFormatException {
        String ticket;
        if (carNumber.matches("^[A-Z][A-Z0-9]{5}$")) {
            ticket = lotRepository.findParkInfo(lotList, carNumber);
            if (ticket.equals("")) {
                throw new ParkingLotFullException("非常抱歉，由于车位已满，暂时无法为您停车！");
            }
        } else {
            throw new WrongFormatException(WRONG_INPUT);
        }
        return ticket;
    }

    public static String fetch(String ticket) throws InvalidTicketException, WrongFormatException {
        String carNumber;
        String[] ticketInfo = ticket.split(",");
        if (3 == ticketInfo.length) {
            carNumber = lotRepository.checkTicket(lotList, ticketInfo);
            if (carNumber.equals("")) {
                throw new InvalidTicketException("停车券无效");
            }
        } else {
            throw new WrongFormatException(WRONG_INPUT);
        }
        return carNumber;
    }
}
