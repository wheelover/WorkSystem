package buildsite.model;

import java.util.List;
import java.util.Map;

public class MapData {

    private Environment environment;
    private Water water;
    private String id;
    private List<Map<String, String>> points;
    private String radius;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, String>> getPoints() {
        return points;
    }

    public void setPoints(List<Map<String, String>> points) {
        this.points = points;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }


}
