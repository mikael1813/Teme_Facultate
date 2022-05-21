package socialnetwork.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SpecialOffer extends Entity<Double>{
    double hotelId;
    LocalDate startDate;
    LocalDate endDate;
    int percents;

    public SpecialOffer(double hotelId, LocalDate startDate, LocalDate endDate, int percents) {
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;
    }

    public double getHotelId() {
        return hotelId;
    }

    public void setHotelId(double hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPercents() {
        return percents;
    }

    public void setPercents(int percents) {
        this.percents = percents;
    }
}
