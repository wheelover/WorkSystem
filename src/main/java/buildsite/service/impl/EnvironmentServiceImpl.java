package buildsite.service.impl;

import buildsite.model.Environment;
import buildsite.service.EnvironmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {


    @Override
    public Environment add(Environment environment) {
        return null;
    }

    @Override
    public Environment get(String id) {
        return null;
    }

    @Override
    public List<Environment> getAll() {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
