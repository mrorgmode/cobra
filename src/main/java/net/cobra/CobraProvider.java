package net.cobra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cobra.valueobjects.Crime;

public class CobraProvider {

	public static List<Crime> getCrimes() {
		List<Crime> result = new ArrayList<Crime>();

		result.add(new Crime(0, new Date(), "Inbrott", "Kragstalund", "Butiken hade inbrott", "Mer info följer"));
		result.add(new Crime(1, new Date(), "Inbrott", "Kungsgatan", "Butiken hade inbrott", "Mer info följer"));
		result.add(new Crime(2, new Date(), "Inbrott", "Solna", "Butiken hade inbrott", "Mer info följer"));
		
		return result;
	}
}
