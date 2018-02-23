# Quick
By Spring Boot
## Address Module
### 安装部署
* 环境

JDK 1.8
* 打包

将address模块打包并上传到服务器
* 启动

nohup java -jar address-0.0.1-SNAPSHOT.jar &
* 查看日志

logs/address.log
* 关闭

搜索到进程后杀死，ps -ef|grep address，kill -9 进程ID
### 功能描述
* 文件扫描

按天扫描FTP服务器上的Excel地址文件（路径不包含中文），时间间隔为1分钟，扫描成功后记录在表ADDR_SEGM_FILE_RECORD中。

有详细表结构说明，包含文件记录ID（即批次号，主键唯一）、文件名、文件路径、地址数、备注（错误信息）、状态（0 未处理 1 处理中 2 处理成功 3 处理失败)、耗费时间、扫描时间、处理中时间、读取时间。

* 地址入库

按规则逐个读取文件内容，时间间隔为10秒。

如果读取失败，ADDR_SEGM_FILE_RECORD中的文件记录变更状态为2并记录读取的总地址数，成功的地址记录在ADDR_SEGM_CHECK_TMP中。

如果读取失败，ADDR_SEGM_FILE_RECORD中的文件记录变更状态为3并记录错误信息，错误原因有可能是Excel不正确导致的文件过大，使用正确的格式重新上传文件即可解决。
* 验证方法参考

按日期检查ADDR_SEGM_FILE_RECORD中的记录数是否与FTP上的Excel文件个数一致。

检查ADDR_SEGM_FILE_RECORD中是否有失败的记录，状态为3，当天状态改为0可以重新被读取。

按批次号检查ADDR_SEGM_FILE_RECORD中单个文件记录地址总数是否与Excel文件中的地址数一致，以及是否与ADDR_SEGM_CHECK_TMP中的地址记录总数一致。