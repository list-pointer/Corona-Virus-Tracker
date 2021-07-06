package spit.ac.in.coronavirustracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spit.ac.in.coronavirustracker.model.LocationStats;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetRawDataService {

    public final static String DATA_URl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> locationStatsList=new ArrayList<>();

    @PostConstruct //execute this function on start
    @Scheduled(cron = "* * 1 * * *")
//    this will make this function run every second and update the data
//    ****** stands for seconds mins hours date and year
//    this will make the function run once a day

    public void fetchData() throws IOException, InterruptedException {

//        this temp list will store data while fetching from the github
//                then populate the original list so that there is no error while fetching
List<LocationStats> tempLocationStatsList=new ArrayList<>();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(DATA_URl)).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

//        this will give us a stringReader which can be passed to the commons-csv library
//            that will store the headers in the iterable list
        StringReader stringReader=new StringReader(httpResponse.body());
Iterable<CSVRecord> csvRecordIterable= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
for(CSVRecord csvRecord: csvRecordIterable){
    LocationStats locationStats=new LocationStats();
    locationStats.setState(csvRecord.get("Province/State"));
    locationStats.setCountry(csvRecord.get("Country/Region"));
    locationStats.setTotalCases(Integer.parseInt(csvRecord.get(csvRecord.size()-1)));
    System.out.println(locationStats);
    tempLocationStatsList.add(locationStats);

}

this.locationStatsList=tempLocationStatsList;

    }


}
