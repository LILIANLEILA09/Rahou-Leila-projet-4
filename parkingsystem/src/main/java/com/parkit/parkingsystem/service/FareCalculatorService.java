package com.parkit.parkingsystem.service;
import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
               
               long inHour = ticket.getInTime().getTime()/60*60*1000;
               long outHour = ticket.getOutTime().getTime()/60*60*1000;

           //TODO: Some tests are failing here. Need to check if this logic is correct
           // double  duration = outHour - inHour;
            long timeDiffInMillies = Math.abs(ticket.getOutTime().getTime() - ticket.getInTime().getTime());
	        double timeDiffInMinutes = TimeUnit.MINUTES.convert(timeDiffInMillies, TimeUnit.MILLISECONDS);
	         double duration;
             
           // Duration fixed to 24 hours (1440 minutes) when the Time Difference is more
	        // than 24 hours
	    if (timeDiffInMinutes > 1440) {
	    duration = 24;
	   } else {
	    duration = timeDiffInMinutes / 60;
	  }
        // freeRate variable controls whether the user stayed for less than 30 minutes
	double freeRate = 1;

	if (timeDiffInMinutes <= 30) {
	    freeRate = 0;
	}
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR*freeRate);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR *freeRate);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}

