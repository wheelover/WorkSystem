package buildsite.service;

import buildsite.model.User;
import buildsite.param.UserQueryParam;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    /**
     * 新增
     */
    User add(User user);

    /**
     * id 查询
     */
    User get(String id);

    /**
     * 条件查询，支持分页
     */
    Page<User> list(UserQueryParam param);

    /**
     * 修改
     */
    boolean modify(User user);

    /**
     * 删除
     */
    boolean delete(String id);

    List<User> getAll();

}
