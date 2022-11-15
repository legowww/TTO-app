package com.demo.busapp.web.service;

import com.demo.busapp.web.dto.Bus;
import com.demo.busapp.web.dto.BusDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusApiService {
    private static final String SERVICE_KEY = "X4cMPRltQhalSiXM8QgHsuAOK1%2FasF494602CvtfRMOEOyTmY1h9UOxgzYax5T1oPy%2Bq1m9BtXlsHzznuJFxew%3D%3D";

    public List<Bus> stationInformation(String bstopId) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6280000/busArrivalService/getAllRouteBusArrivalList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("15", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("bstopId", "UTF-8") + "=" + URLEncoder.encode(bstopId, "UTF-8")); /*정류소 고유번호*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "text/xml"); //text/xml

        System.out.println("url = " + url);
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();



        JSONObject result = XML.toJSONObject(sb.toString()); //XML -> JSON 변환
        System.out.println("=================result = " + result + "===========");
        System.out.println("===========================");

        JSONObject serviceResult = (JSONObject) result.get("ServiceResult");
        JSONObject msgHeader = (JSONObject) serviceResult.get("msgHeader");
        String resultCode = msgHeader.get("resultCode").toString();

        int totalCount = Integer.parseInt(msgHeader.get("totalCount").toString());
        List<Bus> temp = new ArrayList<>();
        if (resultCode.equals("0")) {
            JSONObject msgBody = (JSONObject) serviceResult.get("msgBody");
            /**
             * 주의: totalCount 값이 1일 경우 itemList 는 배열이 아닌 단일 값을 가지므로 예외 처리
             */
            if (totalCount == 1) {
                JSONObject item = (JSONObject) msgBody.get("itemList");
                String ROUTEID = item.get("ROUTEID").toString(); //버스 노선번호ID
                String BSTOPID = item.get("BSTOPID").toString(); //버스 정류장ID
                String ARRIVALESTIMATETIME = item.get("ARRIVALESTIMATETIME").toString(); //BSTOPID 까지 도착시간(초)
                temp.add(new Bus(ROUTEID, BSTOPID, ARRIVALESTIMATETIME));
            }
            else {
                JSONArray items = (JSONArray) msgBody.get("itemList");
                for (int i = 0; i < items.length(); ++i) {
                    JSONObject item = (JSONObject) items.get(i);
                    String ROUTEID = item.get("ROUTEID").toString(); //버스 노선번호ID
                    String BSTOPID = item.get("BSTOPID").toString(); //버스 정류장ID
                    String ARRIVALESTIMATETIME = item.get("ARRIVALESTIMATETIME").toString(); //BSTOPID 까지 도착시간(초)
                    temp.add(new Bus(ROUTEID, BSTOPID, ARRIVALESTIMATETIME));
                }
            }
        }
        return temp;
    }
}
