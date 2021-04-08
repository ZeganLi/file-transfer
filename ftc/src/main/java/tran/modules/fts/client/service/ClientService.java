package tran.modules.fts.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import springfox.documentation.annotations.Cacheable;
import tran.common.utils.PageUtils;
import tran.modules.fts.client.entity.ClientEntity;

import java.util.Map;

/**
 * 终端设备
 *
 * @author chenshun
 * @email mr.lizegan@gmail.com
 * @date 2021-03-15 22:38:14
 */
public interface ClientService extends IService<ClientEntity> {

    @Cacheable("FTS_CLIENT_CACHE")
    PageUtils queryPage(Map<String, Object> params);
}

