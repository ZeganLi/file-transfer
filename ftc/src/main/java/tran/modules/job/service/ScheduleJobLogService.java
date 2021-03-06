package tran.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tran.common.utils.PageUtils;
import tran.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
