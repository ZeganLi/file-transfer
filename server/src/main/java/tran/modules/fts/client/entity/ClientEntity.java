package tran.modules.fts.client.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 终端设备
 *
 * @author chenshun
 * @email mr.lizegan@gmail.com
 * @date 2021-03-15 22:38:14
 */
@Data
@TableName("client")
public class ClientEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID自增
	 */
	@TableId
	private Long id;
	/**
	 * 终端名称
	 */
	private String clientName;
	/**
	 * 连接时的密钥
	 */
	private String clientKey;
	/**
	 * 客户端的类型
	 */
	private String clientType;
	/**
	 * 终端状态
	 */
	private Integer clientStatus;
	/**
	 * 终端的版本号
	 */
	private Integer clientVersion;
	/**
	 * 该终端的文件存储目录
	 */
	private String clientStorageDir;

	/**
	 * 颁发给终端的安全密钥
	 */
	private String securityKey;

	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
