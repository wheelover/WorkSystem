package buildsite.service;

import buildsite.model.MapData;

import java.util.List;

public interface MapService {

    //增加一个工地
    MapData add(MapData mapData);

    //得到一个工地
    MapData get(String id);

    //得到所有工地
    List<MapData> getAll();

    //修改一个工地
    boolean modify(MapData mapData);

    //删除一个工地
    boolean delete(String id);


}
