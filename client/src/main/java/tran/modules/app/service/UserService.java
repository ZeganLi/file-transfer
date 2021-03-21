package tran.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import tran.modules.app.form.LoginForm;
import tran.modules.app.entity.UserEntity;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
