package buildsite.service;

import buildsite.model.Environment;

import java.util.List;

public interface EnvironmentService {

    Environment add(Environment environment);

    Environment get(String id);

    List<Environment> getAll();

    boolean delete(String id);

}
