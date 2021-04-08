package tran.modules.fts.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tran.common.utils.PageUtils;
import tran.common.utils.Query;
import tran.modules.fts.client.dao.ClientDao;
import tran.modules.fts.client.entity.ClientEntity;
import tran.modules.fts.client.service.ClientService;

import java.util.Map;


@Service("clientService")
public class ClientServiceImpl extends ServiceImpl<ClientDao, ClientEntity> implements ClientService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ClientEntity> page = this.page(
                new Query<ClientEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}