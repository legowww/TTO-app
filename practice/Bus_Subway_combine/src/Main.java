import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String apiKey = "Qmn6U2M5L3CCbVN8qFLeOCoE4m7xcYqwHz31rjcejo4";
        StringBuilder urlBuilder = new StringBuilder("https://api.odsay.com/v1/api/searchPubTransPathT");
        urlBuilder.append("?" + URLEncoder.encode("apiKey","UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8"));


        urlBuilder.append("&" + URLEncoder.encode("SX","UTF-8") + "=" + URLEncoder.encode("126.775751", "UTF-8")); // 신중동역(37.503078, 126.775751)
        urlBuilder.append("&" + URLEncoder.encode("SY","UTF-8") + "=" + URLEncoder.encode("37.503078", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("EX","UTF-8") + "=" + URLEncoder.encode("126.63652", "UTF-8")); //인천대자연과학대학정류장(37.37499041 126.63652)
        urlBuilder.append("&" + URLEncoder.encode("EY","UTF-8") + "=" + URLEncoder.encode("37.37499041", "UTF-8"));


        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = rd.readLine()) != null){
            sb.append(line);
        }
        System.out.println(sb.toString());
        rd.close();
        conn.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(sb.toString());
        JSONObject result = (JSONObject) json.get("result");
        JSONArray paths = (JSONArray) result.get("path");

        for (int i = 0; i < paths.size(); i++) {
            JSONObject path = (JSONObject) paths.get(i);
            JSONObject info = (JSONObject) path.get("info");

            int totalTime = Integer.parseInt(info.get("totalTime").toString());
            String firstStartStation = info.get("firstStartStation").toString();
            String lastEndStation = info.get("lastEndStation").toString();
            String busTransitCount = info.get("busTransitCount").toString();
            String subwayTransitCount = info.get("subwayTransitCount").toString();

            System.out.println(i+1 + ". 출발정류장:" + firstStartStation + " 도착정류장:" + lastEndStation + " 버스:" + busTransitCount + " 지하철:" + subwayTransitCount);
            System.out.println();

            JSONArray subPath = (JSONArray) path.get("subPath");
            for (int j = 0; j < subPath.size(); ++j) {
                JSONObject sp = (JSONObject) subPath.get(j);
                String trafficType = getTrafficType(Integer.parseInt(sp.get("trafficType").toString()));
                int sectionTime = Integer.parseInt(sp.get("sectionTime").toString());

                if (trafficType == "지하철") {
                    JSONArray lane = (JSONArray) sp.get("lane");
                    JSONObject sub = (JSONObject) lane.get(0);
                    String laneName = sub.get("name").toString();
                    JSONObject passStopList = (JSONObject) sp.get("passStopList");
                    JSONArray stations = (JSONArray) passStopList.get("stations");

                    System.out.print(laneName + " : ");
                    for (int k = 0; k < stations.size(); k++) {
                        JSONObject station = (JSONObject) stations.get(k);
                        String stationName = station.get("stationName").toString();
                        System.out.print(stationName + "->");
                    }
                    System.out.print("<" + sectionTime +"분 소요>");
                    System.out.println();
                }

                if (trafficType == "버스") {
                    JSONArray lane = (JSONArray) sp.get("lane");
                    JSONObject bus = (JSONObject) lane.get(0);
                    String busNo = bus.get("busNo").toString();
                    JSONObject passStopList = (JSONObject) sp.get("passStopList");
                    JSONArray stations = (JSONArray) passStopList.get("stations");

                    System.out.print(busNo + "번 버스 : ");
                    for (int k = 0; k < stations.size(); k++) {
                        JSONObject station = (JSONObject) stations.get(k);
                        String stationName = station.get("stationName").toString();
                        System.out.print(stationName + "->");
                    }
                    System.out.print("<" + sectionTime +"분 소요>");
                    System.out.println();
                }
            }

            System.out.println("총 합계:" + totalTime + "분");
            System.out.println("===========================================================================================");
        }
    }

    public static String getTrafficType(int type) {
        if (type == 1) return "지하철";
        else if (type == 2) return "버스";
        else return "도보";
    }
}