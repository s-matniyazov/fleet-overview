package fleetoverview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.UserRequest;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.service.UserService;

import static fleetoverview.util.constants.UriConstants.USERS;


@RestController
@RequestMapping(USERS)
public class UserController extends CrudController<UserEntity, UserRequest> {

    @Autowired
    public UserController(UserService service) {
        super(service);
    }
}
