package entities;

import repositories.LotRepository;
import services.LotService;

public class SillyMember extends ParkingAttendant {

    @Override
    public SingleLot provideTicket(CarDetail car, LotRepository lotRepository) {
        SingleLot checkedLot = null;
        for (String key: LotService.lotList.keySet()) {
            checkedLot = lotRepository.findEmptyParking(key);
            if (null != checkedLot) { break; }
        }
        return checkedLot;
    }
}
