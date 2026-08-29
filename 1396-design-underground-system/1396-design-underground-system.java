import java.util.*;

class UndergroundSystem {

    // id -> [stationName, checkInTime]
    private Map<Integer, CheckInData> checkIns;

    // "start#end" -> [totalTime, numberOfTrips]
    private Map<String, RouteData> routes;

    public UndergroundSystem() {
        checkIns = new HashMap<>();
        routes = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        checkIns.put(id, new CheckInData(stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        CheckInData data = checkIns.get(id);

        String route = data.station + "#" + stationName;
        int travelTime = t - data.time;

        RouteData routeData = routes.getOrDefault(
            route,
            new RouteData(0, 0)
        );

        routeData.totalTime += travelTime;
        routeData.count++;

        routes.put(route, routeData);

        // Customer is no longer checked in
        checkIns.remove(id);
    }

    public double getAverageTime(String startStation, String endStation) {
        String route = startStation + "#" + endStation;

        RouteData data = routes.get(route);

        return (double) data.totalTime / data.count;
    }

    // Stores check-in information
    private static class CheckInData {
        String station;
        int time;

        CheckInData(String station, int time) {
            this.station = station;
            this.time = time;
        }
    }

    // Stores statistics for a route
    private static class RouteData {
        long totalTime;
        int count;

        RouteData(long totalTime, int count) {
            this.totalTime = totalTime;
            this.count = count;
        }
    }
}