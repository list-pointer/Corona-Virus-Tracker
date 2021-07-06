package spit.ac.in.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spit.ac.in.coronavirustracker.model.LocationStats;
import spit.ac.in.coronavirustracker.services.GetRawDataService;

import java.util.List;

//instead of making ot a Rest controller we are making it a controller
//meaning each function must be associated with a page
@Controller
public class HomePageController {

    @Autowired
    GetRawDataService getRawDataService;

    @GetMapping("/")
    //Model class is provided by spring boot and it can be used to set and send data on the html pagae
    public String Home(Model model) {
        List<LocationStats> locationStatsList = getRawDataService.getLocationStatsList();
        int totalReportedCase = locationStatsList.stream().mapToInt(stat -> stat.getTotalCases()).sum();
        int totalNewCases = locationStatsList.stream().mapToInt(stat -> stat.getDiff()).sum();
        model.addAttribute("locationStats", locationStatsList);
        model.addAttribute("totalReportedCase", totalReportedCase);
        model.addAttribute("totalNewCases", totalNewCases);
        //this function will be associated with home.html and will render the same
        return "home";
    }
}
