package app.payfun.com.gofoodapos.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import app.payfun.com.gofoodapos.Define;


/**
 * Created by JuHH on 15. 11. 3..
 */
public class ConnectValue {

    // 전체 데이타를 담고 있는다.
    private ArrayList<HashMap<String, Object>> connectLists;
    /*
        아래와 같은 형식으로 들어간다.
        key:value;
        key:value;
        key {
            key:value;
            key:value;
            key:value;
            key:value;
        }

        !!! ****** 중요
        사용하는 Function은 addConnectMap 관련 Function을 사용한다.

     */
    private HashMap<String, Object> connectMaps;

    private ConnectInfo.RequestType requestType;

    public ConnectValue() {

        connectLists = new ArrayList<HashMap<String, Object>>();
        connectMaps = new HashMap<String, Object>();
        requestType = ConnectInfo.RequestType.MAP;
    }

    public ConnectInfo.RequestType getRequestValueType() {

        return requestType;
    }

    public void setRequestValueType(ConnectInfo.RequestType requestType) {

        this.requestType = requestType;
    }

    public ArrayList<HashMap<String, Object>> getConnectValue() {

        ArrayList<HashMap<String, Object>> returnData = new ArrayList<HashMap<String, Object>>();

        if (connectMaps.size() > 0) {

            boolean isArray = false;
            Iterator<String> keys = connectMaps.keySet().iterator();
            while (keys.hasNext()) {

                String key = keys.next();

                if (connectMaps.get(key) instanceof ArrayList) {

                    isArray = true;
                    if (connectLists.size() > 0) {

                        for (int i = 0; i < connectLists.size(); i++) {
                            ((ArrayList) connectMaps.get(key)).add(connectLists.get(i));
                        }
                    }
                    break;
                }
            }

            // ArrayList가 Map에 안들어가 있다면 따로 추가해준다.
            if (isArray == false) {

                if (connectLists.size() > 0) {

                    for (int i = 0; i < connectLists.size(); i++) {

                        if (connectLists.get(i) instanceof HashMap) {

                            connectMaps.putAll(connectLists.get(i));
                        } else {

                            returnData.add(connectLists.get(i));
                        }
                    }
                }
            }

            returnData.add(connectMaps);
        }

        if (returnData.size() > 0) {

            return returnData;
        } else {

            return connectLists;
        }
    }

    // Single key, value 형식의 데이타 추가
    public void addConnectMap(String key, String value) {

        key = Define.nullCheck(key);
        value = value;

        if (key.length() == 0) {

            PrintLog.print("ConnectValue", "data Error");
            return;
        }

        connectMaps.put(key, value);
    }

    // map 형식의 데이타 추가
    public void addConnectMap(String mapKey, String key, String value) {

        mapKey = Define.nullCheck(mapKey);
        key = key;

        if (mapKey.length() == 0 || key.length() == 0) {

            PrintLog.print("ConnectValue", "data Error");
            return;
        }

        if (connectMaps.containsKey(mapKey))
            ((HashMap<String, String>) connectMaps.get(mapKey)).put(key, Define.nullCheck(value));
        else {
            HashMap<String, String> mapInfos = new HashMap<String, String>();
            mapInfos.put(key, value);
            connectMaps.put(mapKey, mapInfos);
        }
    }

    /**
     * Map 형식의 데이타를 전체
     *
     * @param mapKey
     * @param values
     */
    public void addConnectMapAll(String mapKey, Object values) {

        mapKey = Define.nullCheck(mapKey);

//        if(mapKey.length() == 0 || mapInfos == null || mapInfos.size() == 0) {
//
//            PrintLog.print("ConnectValue", "data Error");
//            return;
//        }

//        HashMap<String, String> maps = new HashMap<String, String>();
//        Iterator<String> keys = mapInfos.keySet().iterator();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            maps.put(key, mapInfos.get(key));
//        }

        connectMaps.put(mapKey, values);
    }

    /**
     * JsonArray 형식으로 데이타를 추가하고 싶다면. ㅇ_ㅇ..
     *
     * @param values
     */
    public void addConnectList(ArrayList<HashMap<String, String>> values) {

        if (values == null || values.size() == 0) {

            PrintLog.print("ConnectValue", "data Error");
            return;
        }

        for (HashMap<String, String> map : values) {

            HashMap<String, Object> saveMap = new HashMap<String, Object>();

            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()) {

                String key = keys.next();
                saveMap.put(key, map.get(key));
            }

            connectLists.add(saveMap);
        }
    }

    /**
     * JsonArray 형식으로 데이타를 추가하고 싶다면. ㅇ_ㅇ..
     *
     * @param keys   추가할 키값들
     * @param values 값
     */
    public void addConnectList(String[] keys, ArrayList<HashMap<String, String>> values) {

        if (keys == null || values == null || values.size() == 0) {

            PrintLog.print("ConnectValue", "data Error");
            return;
        }

        for (HashMap<String, String> map : values) {

            HashMap<String, Object> saveMap = new HashMap<String, Object>();

            for (int i = 0; i < keys.length; i++) {

                if (map.containsKey(keys[i])) {

                    saveMap.put(keys[i], map.get(keys[i]));
                }
            }

            connectLists.add(saveMap);
        }
    }

    /**
     * JsonObject -> JsonArray 형식으로 담는 경우
     *
     * @param listKey
     * @param values
     */
    public void addConnectList(String listKey, ArrayList<HashMap<String, String>> values) {

//        if(listKey == null || listKey.length() == 0 || values == null || values.size() == 0) {
//
//            PrintLog.print("ConnectValue", "data Error");
//            return;
//        }

        HashMap<String, Object> saveMap = new HashMap<String, Object>();
        saveMap.put(listKey, values);
        connectLists.add(saveMap);
    }

    /**
     * JsonObject -> JsonArray 형식으로 담는 경우
     *
     * @param listKey
     * @param keys
     * @param values
     */
    public void addConnectList(String listKey, String[] keys, ArrayList<HashMap<String, String>> values) {

//        if(listKey == null || listKey.length() == 0 || keys == null || values == null || values.size() == 0) {
//
//            PrintLog.print("ConnectValue", "data Error");
//            return;
//        }

        ArrayList<HashMap<String, Object>> saveLists = new ArrayList<HashMap<String, Object>>();
        for (HashMap<String, String> map : values) {

            HashMap<String, Object> tempData = new HashMap<String, Object>();
            for (int i = 0; i < keys.length; i++) {

                if (map.containsKey(keys[i])) {

                    tempData.put(keys[i], map.get(keys[i]));
                }
            }
            saveLists.add(tempData);
        }

        HashMap<String, Object> saveMap = new HashMap<String, Object>();
        saveMap.put(listKey, saveLists);
        connectLists.add(saveMap);
    }
}
