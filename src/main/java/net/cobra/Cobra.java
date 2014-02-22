/**
 * 
 */
package net.cobra;

import net.cobra.Regexp;
import net.cobra.Crime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author perweij
 *
 */
public class Cobra {
	private static final Logger log = LoggerFactory
			.getLogger(Cobra.class);
	Twitter twitter         = null;
	String credentials_file = "c:\\temp\\testoauth.txt";



	public Cobra() {
		twitter = TwitterFactory.getSingleton();
		do_auth();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Cobra tg2 = new Cobra();

		List<Status> statuses = tg2.getStatuses();		
		List<Crime> crimes = tg2.parseStatuses(statuses);
	}



	private List<Status> getStatuses() {
		List<Status> statuses = null;
		long sinceId = 10;
		
		try {
			//statuses = twitter.getHomeTimeline(new Paging(sinceId));
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return statuses;
	}


	private List<Crime> parseStatuses(List<Status> statuses) {
		List<Crime> crimes = new ArrayList<Crime>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String feedpat = "^([0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]), ([A-ZÅÄÖ][^:]*), ([A-ZÅÄÖ][^:]*)[:] (.+?) (http.*+)$";

		for (Status status : statuses) {
			List<String> matches = Regexp.match(status.getText(), feedpat);
			if(matches.size() == 5) {				
				Date date = null;
				try {
					date = format.parse(matches.get(0));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String moredesc = download_extra_desc(matches.get(4));
				crimes.add(new Crime(status.getId(), date, matches.get(1), matches.get(2), matches.get(3), moredesc));
			}
		}
		return crimes;
	}


	private String download_extra_desc(String url) {
		String content = "";
		URLConnection connection = null;
		String line = "";

		try {
			connection =  new URL(url).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter(System.getProperty("line.separator"));

			boolean founddata = false;			
			while (scanner.hasNext()) {
				line = scanner.next();
				if(line.matches("^[[:space:]]*$")) {
					continue;
				}

				if(founddata == false) {
					if(line.compareTo("<!--googleon: all-->") == 0) {
						founddata = true;
					}
				} else {
					if(line.compareTo("<div id=\"pagefooter\">") == 0) {
						break;
					}
					//log.debug(line);
					content += line;
					log.debug("FIRST " + line);
					break;
				}
			}

		} catch ( Exception ex ) {
			ex.printStackTrace();
		}

		log.debug("MOREDESC: "+content);

		return content;
	}

	private void do_auth(){

		if(new File(credentials_file).isFile()) {
			String consumer = null;
			String consumer_key = null;
			String token = null;
			String tokenSecret = null;

			BufferedReader br = null;

			try {
				br = new BufferedReader(new FileReader(credentials_file));

				consumer = br.readLine();
				consumer_key = br.readLine();
				token = br.readLine();
				tokenSecret = br.readLine();

				twitter.setOAuthConsumer(consumer, consumer_key);

				AccessToken accessToken = new AccessToken(token, tokenSecret);
				twitter.setOAuthAccessToken(accessToken);
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			initial_auth();
		}
	}


	private void initial_auth(){
		RequestToken requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String consumer = null;
		String consumer_key = null;

		try {
			System.out.println("Enter API key (get from dev.twitter.com):");
			consumer = br.readLine();
			System.out.println("Enter API secret key (get from dev.twitter.com):");
			consumer_key = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (null == accessToken) {
			System.out.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = null;
			try {
				pin = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				if(pin.length() > 0){
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				}else{
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if(401 == te.getStatusCode()){
					System.out.println("Unable to get the access token.");
				}else{
					te.printStackTrace();
				}
			}
		}
		//persist to the accessToken for future reference.
		try {
			storeAccessToken(twitter.verifyCredentials().getId() , accessToken, consumer, consumer_key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void storeAccessToken(long useId, AccessToken accessToken, String consumer, String consumer_key){
		PrintWriter out = null;
		try {
			out = new PrintWriter(credentials_file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(consumer);
		out.println(consumer_key);
		out.println(accessToken.getToken());
		out.println(accessToken.getTokenSecret());
		out.close();
	}

}
