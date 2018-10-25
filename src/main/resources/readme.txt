1、ZK作用
  1) master节点选举,主节点挂了以后,从节点会接手工作并且保证这个节点是唯一的
  2) 统一配置文件管理,只需要部署一台服务器,则可以把相同的配置文件同步更新到其他所有的服务器(如修改了redis统一配置)
  3) 发布订阅,类似消息队列,如dubbo发布者将数据存在znode上,订阅者会读取这个数据
  4) 提供分布式锁
  5) 集群管理,集群中保证数据的强一致性

2、ZK命令
  1) 启动 zkServer.sh start
  2) 客户端连接 zkCli.sh
  3) ls,如ls /显示根目录下有哪些节点
  4) ls2,如ls2 /显示节点及状态信息,ls2相当于ls和stat整合
  5) stat,显示状态信息
  6) get,获取数据及状态信息

3、状态信息
  cZxid:创建时zookeeper给节点分配的ID
  mZxid:修改后zookeeper分配的ID
  pZxid:子节点的ID
  cversion:子节点的version,子节点数据发生变化,该值会变化
  dataVersion:当前数据的版本号,当前节点的数据修改后该版本号会改变
  aclVersion:权限,当前节点权限发生变化后,该值累加1
  ephemeraOwner:
  dataLength:数据长度
  mumChildren:有几个子节点