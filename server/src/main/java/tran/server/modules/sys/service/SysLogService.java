package tran.server.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import tran.server.common.utils.PageUtils;
import tran.server.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
