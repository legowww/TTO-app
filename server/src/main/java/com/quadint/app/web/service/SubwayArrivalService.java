package com.quadint.app.web.service;

import com.quadint.app.domain.time.SubwayTimeDto;
import com.quadint.app.domain.time.SubwayTimeResponse;
import com.quadint.app.web.exception.TtoAppException;
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
import java.nio.Buffer;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubwayArrivalService {
    private static final String SERVICE_KEY = "Qmn6U2M5L3CCbVN8qFLeOCoE4m7xcYqwHz31rjcejo4";

    private static final String SUBWAY_API_URL = "https://api.odsay.com/v1/api/subwayTimeTable";

    public SubwayTimeResponse getTimeResponse(String stationId, String wayCode) {
        LocalDateTime now = LocalDateTime.now();
        String[] hour = {"0", "0"};
        String[] minute = {"0", "0", "0"};

        SubwayTimeResponse subwayTimeResponse = SubwayTimeResponse.createSubwayTimeResponse(stationId, wayCode);
        SubwayTimeDto subwayTimeDto = getSubwayArrivalStationTime(stationId, wayCode);

        hour[0] = String.valueOf(now.getHour());

        String[] list = subwayTimeDto.getList();
        if (subwayTimeDto.getIdx() != "24") {
            for (int i = 0; i < list.length; i++) {
                
            }
        }

        return subwayTimeResponse;
    }

    private SubwayTimeDto getSubwayArrivalStationTime(String stationId, String wayCode) {
        try {
            LocalDateTime now = LocalDateTime.now();

            StringBuilder url = getSubwayArrivalStationUrl(stationId, wayCode);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(url.toString());
            JSONObject result = (JSONObject) json.get("result");

            if (String.valueOf(now.getDayOfWeek()) == "SATURDAY") {
                JSONObject satList = (JSONObject) result.get("SatList");
                if (wayCode == "1") {
                    JSONObject up = (JSONObject) satList.get("up");
                    JSONArray times = (JSONArray) up.get ("time");
                    if((now.getHour() >= 5) && (now.getHour() < 25)) {
                                    JSONObject time = (JSONObject) times.get(now.getHour() - 5);
                                    String idx = time.get("Idx").toString();
                                    String list = (String) time.get("list");
                                    String[] replaceList = splitList(list);
                                    if (now.getHour() != 24) {
                                        if (replaceList.length < 3) {
                                            JSONObject time2 = (JSONObject) times.get(now.getHour() - 5 + 1);
                                            String list2 = (String) time2.get("list");
                                            String[] replaceList2 = splitList(list2);
                                            String[] resultList = combineList(replaceList, replaceList2);

                                            return new SubwayTimeDto(idx, resultList);
                            }
                        }

                        return new SubwayTimeDto(idx, replaceList);
                    }
                }
                else {
                    JSONObject down = (JSONObject) satList.get("down");
                    JSONArray times = (JSONArray) down.get ("time");
                    if((now.getHour() >= 5) && (now.getHour() < 25)) {
                        JSONObject time = (JSONObject) times.get(now.getHour() - 5);
                        String idx = time.get("Idx").toString();
                        String list = (String) time.get("list");
                        String[] replaceList = splitList(list);
                        if (now.getHour() != 24) {
                            if (replaceList.length < 3) {
                                JSONObject time2 = (JSONObject) times.get(now.getHour() - 5 + 1);
                                String list2 = (String) time2.get("list");
                                String[] replaceList2 = splitList(list2);
                                String[] resultList = combineList(replaceList, replaceList2);

                                return new SubwayTimeDto(idx, resultList);
                            }
                        }

                        return new SubwayTimeDto(idx, replaceList);
                    }
                }


            }
            else if (String.valueOf(now.getDayOfWeek()) == "SUNDAY") {
                JSONObject sunList = (JSONObject) result.get("SunList");
                if (wayCode == "1") {
                    JSONObject up = (JSONObject) sunList.get("up");
                    JSONArray times = (JSONArray) up.get ("time");
                    if((now.getHour() >= 5) && (now.getHour() < 25)) {
                        JSONObject time = (JSONObject) times.get(now.getHour() - 5);
                        String idx = time.get("Idx").toString();
                        String list = (String) time.get("list");
                        String[] replaceList = splitList(list);
                        if (now.getHour() != 24) {
                            if (replaceList.length < 3) {
                                JSONObject time2 = (JSONObject) times.get(now.getHour() - 5 + 1);
                                String list2 = (String) time2.get("list");
                                String[] replaceList2 = splitList(list2);
                                String[] resultList = combineList(replaceList, replaceList2);

                                return new SubwayTimeDto(idx, resultList);
                            }
                        }

                        return new SubwayTimeDto(idx, replaceList);
                    }
                }
                else {
                    JSONObject down = (JSONObject) sunList.get("down");
                    JSONArray times = (JSONArray) down.get ("time");
                    if((now.getHour() >= 5) && (now.getHour() < 25)) {
                        JSONObject time = (JSONObject) times.get(now.getHour() - 5);
                        String idx = time.get("Idx").toString();
                        String list = (String) time.get("list");
                        String[] replaceList = splitList(list);
                        if (now.getHour() != 24) {
                            if (replaceList.length < 3) {
                                JSONObject time2 = (JSONObject) times.get(now.getHour() - 5 + 1);
                                String list2 = (String) time2.get("list");
                                String[] replaceList2 = splitList(list2);
                                String[] resultList = combineList(replaceList, replaceList2);

                                return new SubwayTimeDto(idx, resultList);
                            }
                        }

                        return new SubwayTimeDto(idx, replaceList);
                    }
                }
            }
            else {
                JSONObject ordList = (JSONObject) result.get("OrdList");
                if (wayCode == "1") {
                    JSONObject up = (JSONObject) ordList.get("up");
                    JSONArray times = (JSONArray) up.get ("time");
                    if((now.getHour() >= 5) && (now.getHour() < 25)) {
                        JSONObject time = (JSONObject) times.get(now.getHour() - 5);
                        String idx = time.get("Idx").toString();
                        String list = (String) time.get("list");
                        String[] replaceList = splitList(list);
                        if (now.getHour() != 24) {
                            if (replaceList.length < 3) {
                                JSONObject time2 = (JSONObject) times.get(now.getHour() - 5 + 1);
                                String list2 = (String) time2.get("list");
                                String[] replaceList2 = splitList(list2);
                                String[] resultList = combineList(replaceList, replaceList2);

                                return new SubwayTimeDto(idx, resultList);
                            }
                        }

                        return new SubwayTimeDto(idx, replaceList);
                    }
                }
                else {
                    JSONObject down = (JSONObject) ordList.get("down");
                    JSONArray times = (JSONArray) down.get ("time");
                    if((now.getHour() >= 5) && (now.getHour() < 25)) {
                        JSONObject time = (JSONObject) times.get(now.getHour() - 5);
                        String idx = time.get("Idx").toString();
                        String list = (String) time.get("list");
                        String[] replaceList = splitList(list);
                        if (now.getHour() != 24) {
                            if (replaceList.length < 3) {
                                JSONObject time2 = (JSONObject) times.get(now.getHour() - 5 + 1);
                                String list2 = (String) time2.get("list");
                                String[] replaceList2 = splitList(list2);
                                String[] resultList = combineList(replaceList, replaceList2);

                                return new SubwayTimeDto(idx, resultList);
                            }
                        }

                        return new SubwayTimeDto(idx, replaceList);
                    }
                }
            }

        } catch (IOException e) {
            throw new TtoAppException("getSubwayArrivalStationTime IOException error");
        } catch (ParseException e) {
            throw new TtoAppException("getSubwayArrivalStationTime ParseException error");
        }
        return null;
    }

    private StringBuilder getSubwayArrivalStationUrl(String stationId, String wayCode) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(SUBWAY_API_URL);
        urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("stationID", "UTF-8") + "=" + URLEncoder.encode(stationId, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("wayCode", "UTF-8") + "=" + URLEncoder.encode(wayCode, "UTF-8"));
        return setRequest(urlBuilder);
    }

    private StringBuilder setRequest(StringBuilder urlBuilder) throws IOException {
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb;
    }

    private String[] splitList(String list) {
        String[] replaceList = list.split("[^0-9]");

        return replaceList;
    }

    private String[] combineList(String[] list1, String[] list2) {
        List<String> l1 = new ArrayList(Arrays.asList(list1));
        List<String> l2 = new ArrayList(Arrays.asList(list2));
        l1.addAll(l2);

        String[] result = new String[l1.size()];
        result = l1.toArray(result);

        return result;
    }
}
