package spit.ac.in.coronavirustracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GetRawDataService {

    public final static String DATA_URl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct //execute this function on start
    public void fetchData() throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(DATA_URl)).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());

        StringReader stringReader=new StringReader(httpResponse.body());
Iterable<CSVRecord> csvRecordIterable= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
for(CSVRecord csvRecord: csvRecordIterable){
    String state=csvRecord.get("Province/State");
    System.out.println(state);
}



    }


}
