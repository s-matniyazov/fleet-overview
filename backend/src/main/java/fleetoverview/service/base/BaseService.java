package fleetoverview.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import fleetoverview.util.helper.MessageSourceBundle;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 22 нояб. 2024
 **/
public abstract class BaseService {
    @Autowired
    protected MessageSourceBundle mSourceBundle;
}
