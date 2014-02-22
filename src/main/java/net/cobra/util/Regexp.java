package net.cobra.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexp {

	public static List<String> match(String text, String feedpat) {
		List<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(feedpat);
		Matcher matcher = pattern.matcher(text);

		if(matcher.matches()) {
			for(int i = 1; i <= matcher.groupCount(); i++) {
				int groupcount = matcher.groupCount();
				String m = matcher.group(i);
				result.add(m);
			}
		}

		return result;
	}

}
