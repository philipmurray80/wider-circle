package phil.widercircle.controller;

import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class PhilsWeatherController {

    @GetMapping("")
    public String getAction(Map<String, Object> model) {
        return "index";
    }

    @PostMapping("")
    public String postAction(@RequestBody String zip, Map<String, Object> model) throws URISyntaxException, IOException, InterruptedException {
        Double highTemp;
        Double lowTemp;
        String imgUrl;
        String weatherConditions;
        String cityState;

        zip = zip.substring(8);
        String apiKey = System.getenv("WEATHER_API_KEY");

        StringBuilder uriSb = new StringBuilder().append("http://api.weatherapi.com/v1/forecast.json")
                                              .append("?key=").append(apiKey)
                                              .append("&q=").append(zip)
                                              .append("&aqi=no");

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(uriSb.toString())).GET().build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser parser = new GsonJsonParser();

        Map<String, Object> map = parser.parseMap(response.body());
        Map<String, Object> day = (Map<String,Object>) ((Map<String,Object>) ((List<Object>) ((Map<String,Object>) map.get("forecast")).get("forecastday")).get(0)).get("day");
        Map<String, Object> location = ((Map<String,Object>) map.get("location"));
        cityState = "(" + location.get("name").toString() + ", " + location.get("region").toString() + ")";

        highTemp = (Double) day.get("maxtemp_f");
        lowTemp = (Double) day.get("mintemp_f");
        weatherConditions = ((Map<String,Object>) day.get("condition")).get("text").toString();
        imgUrl = ((Map<String,Object>) day.get("condition")).get("icon").toString();

        model.put("cityState", cityState);
        model.put("zip", zip);
        model.put("highTemp", highTemp);
        model.put("lowTemp", lowTemp);
        model.put("weatherConditions", weatherConditions);
        model.put("imgUrl", imgUrl);


        return "index";
    }

}
