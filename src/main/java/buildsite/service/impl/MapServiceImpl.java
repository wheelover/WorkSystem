package buildsite.service.impl;

import buildsite.model.MapData;
import buildsite.service.MapService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service
public class MapServiceImpl implements MapService {

    private static final Logger LOG = LoggerFactory.getLogger(MapServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MapData add(MapData mapData) {
        if (mapData == null){
            LOG.error("input Map data is null");
            return null;
        }

        return mongoTemplate.insert(mapData);
    }

    @Override
    public MapData get(String id) {
        if (!StringUtils.hasText(id)){
            LOG.error("input map id is blank");
            return null;
        }
        MapData mapData = mongoTemplate.findById(id, MapData.class);
        return mapData;
    }

    @Override
    public List<MapData> getAll() {
        List<MapData> mapDataList = mongoTemplate.findAll(MapData.class);
        return mapDataList;
    }

    @Override
    public boolean modify(MapData mapData) {
        if (mapData == null || !StringUtils.hasText(mapData.getId())){
            LOG.error("input map data is not correct");
            return false;
        }

        Query query = new Query(Criteria.where("id").is(mapData.getId()));

        Update updateData = new Update();
        if (mapData.getPoints() != null){
            updateData.set("points", mapData.getPoints());
        }

        if (mapData.getRadius() != null){
            updateData.set("radius", mapData.getRadius());
        }



        if (mapData.getEnvironment() != null){
            updateData.set("environment", mapData.getEnvironment());
        }

        if (mapData.getWater() != null){
            updateData.set("water", mapData.getWater());
        }

        UpdateResult result = mongoTemplate.updateFirst(query, updateData, MapData.class);
        return result != null && result.getModifiedCount() > 0;

    }

    @Override
    public boolean delete(String id) {
        if (!StringUtils.hasText(id)){
            LOG.error("input map id is blank");
            return false;
        }

        MapData mapData = new MapData();
        mapData.setId(id);

        DeleteResult result = mongoTemplate.remove(mapData);
        return result != null && result.getDeletedCount() > 0;

    }


}
