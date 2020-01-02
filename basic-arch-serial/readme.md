## serial简介
	序列号生成器,参考leaf做了一些修改,包含segment步长方式的序列号生成器以及不依赖于(弱依赖与ZK)三方组件的snowflake算法生成器
## 项目结构
- serial-distribute-inf 			dubbo接口包
- serial-distribute-respository 数据库dao层接口
- serial-distribute-service 		核心服务,提供获取序列号服务接口
- serial-distribute-manager 		核心服务,供后台应用程序生成各自业务key使用
- serial-distribute-client 		包含一个测试类,用于向序列号服务管理器注册key的示例代码