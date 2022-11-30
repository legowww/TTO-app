package com.quadint.app.web.service;

import com.quadint.app.domain.route.LocationCoordinate;
import com.quadint.app.domain.route.Route;
import com.quadint.app.domain.transportation.Bus;
import com.quadint.app.domain.transportation.TrafficType;
import com.quadint.app.domain.transportation.Walk;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RouteApiService {
    private static final String SERVICE_KEY = "Qmn6U2M5L3CCbVN8qFLeOCoE4m7xcYqwHz31rjcejo4";
    private static final String ROUTE_API_URL = "https://api.odsay.com/v1/api/searchPubTransPathT";

    public List<Route> getRoutes(LocationCoordinate lc) {
        List<Route> tempRoutes = new ArrayList<>();
        try {
            StringBuilder url = getRouteURL(lc);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(url.toString());
            JSONObject result = (JSONObject) json.get("result");
            JSONArray paths = (JSONArray) result.get("path");

            for (int i = 0; i < paths.size(); ++i) {
                JSONObject path = (JSONObject) paths.get(i);
                JSONObject info = (JSONObject) path.get("info");

                String firstStartStation = info.get("firstStartStation").toString();
                String lastEndStation = info.get("lastEndStation").toString();
                String busTransitCount = info.get("busTransitCount").toString();
                String subwayTransitCount = info.get("subwayTransitCount").toString();
                int totalTime = Integer.parseInt(info.get("totalTime").toString());

                Route tempRoute = new Route(busTransitCount, subwayTransitCount, firstStartStation, lastEndStation);
                JSONArray subPath = (JSONArray) path.get("subPath");
                for (int j = 0; j < subPath.size(); ++j) {
                    JSONObject sp = (JSONObject) subPath.get(j);
                    TrafficType trafficType = getTrafficType(Integer.parseInt(sp.get("trafficType").toString()));
                    int sectionTime = Integer.parseInt(sp.get("sectionTime").toString());

                    //WALK
                    if (trafficType == TrafficType.WALK) {
                        if (sectionTime == 1) {
                            totalTime -= 1;
                            continue;
                        }
                        Walk walk = new Walk(sectionTime);
                        tempRoute.addTransportation(walk);
                    }
                    //BUS
                    else if (trafficType == TrafficType.BUS) {
                        //운행 정보
                        String startName = sp.get("startName").toString();
                        String endName = sp.get("endName").toString();
                        String startLocalStationID = sp.get("startLocalStationID").toString();
                        String endLocalStationID = sp.get("endLocalStationID").toString();
                        JSONArray lane = (JSONArray) sp.get("lane");
                        JSONObject lane_bus = (JSONObject) lane.get(0);
                        String busNum = lane_bus.get("busNo").toString();
                        String busId = lane_bus.get("busLocalBlID").toString();
                        Bus bus = new Bus(sectionTime, busId, busNum, startLocalStationID, startName, endLocalStationID, endName);
                        tempRoute.addTransportation(bus);

                        //정류장 리스트
                        JSONObject passStopList = (JSONObject) sp.get("passStopList");
                        JSONArray stations = (JSONArray) passStopList.get("stations");
                        for (int k = 0; k < stations.size(); ++k) {
                            JSONObject station = (JSONObject) stations.get(k);
                            String stationName = station.get("stationName").toString();
                        }
                    }
                }
                tempRoute.setTotalTime(totalTime);
                tempRoutes.add(tempRoute);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(tempRoutes);
        return tempRoutes;
    }

    private StringBuilder getRouteURL(LocationCoordinate lc) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(ROUTE_API_URL);
        urlBuilder.append("?" + URLEncoder.encode("apiKey","UTF-8") + "=" + URLEncoder.encode(SERVICE_KEY, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("SX","UTF-8") + "=" + URLEncoder.encode(lc.getSx(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("SY","UTF-8") + "=" + URLEncoder.encode(lc.getSy(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("EX","UTF-8") + "=" + URLEncoder.encode(lc.getEx(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("EY","UTF-8") + "=" + URLEncoder.encode(lc.getEy(), "UTF-8"));
        return setRequest(urlBuilder);
    }

    private StringBuilder setRequest(StringBuilder urlBuilder) throws IOException {
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "text/xml");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb;
    }

    private TrafficType getTrafficType(int type) {
        if (type == 1) return TrafficType.SUBWAY;
        else if (type == 2) return TrafficType.BUS;
        else return TrafficType.WALK;
    }
}
