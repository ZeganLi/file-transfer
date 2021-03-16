package tran.modules.fts.client.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tran.common.utils.PageUtils;
import tran.common.utils.R;
import tran.modules.fts.client.entity.ClientEntity;
import tran.modules.fts.client.service.ClientService;


/**
 * 终端设备
 *
 * @author chenshun
 * @email mr.lizegan@gmail.com
 * @date 2021-03-15 22:38:14
 */
@RestController
@RequestMapping("fts/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fts:client:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = clientService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("fts:client:info")
    public R info(@PathVariable("id") Long id){
		ClientEntity client = clientService.getById(id);

        return R.ok().put("client", client);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fts:client:save")
    public R save(@RequestBody ClientEntity client){
		clientService.save(client);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fts:client:update")
    public R update(@RequestBody ClientEntity client){
		clientService.updateById(client);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fts:client:delete")
    public R delete(@RequestBody Long[] ids){
		clientService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
