package tran.modules.fts.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tran.modules.fts.client.entity.ClientEntity;

/**
 * 终端设备
 *
 * @author chenshun
 * @email mr.lizegan@gmail.com
 * @date 2021-03-15 22:38:14
 */
@Mapper
public interface ClientDao extends BaseMapper<ClientEntity> {

}
