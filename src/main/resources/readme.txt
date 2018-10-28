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
  7) create,创建节点
     create /lwh lwh-data,默认创建的节点是非顺序的,持久化的
     create -e /lwh lwh-data,临时节点
     create -s /lwh/seq seq,在/lwh创建顺序节点
  8) set,修改节点
     set /lwh new-data,修改数据,dataVersion会变化,乐观锁
     set /lwh 123 1,最后的参数1表示dataVersion版本号,只有版本号一致,才会将数据修改为123
  9) delete,删除节点
     delete /lwh version

3、状态信息
  cZxid:创建时zookeeper给节点分配的ID
  mZxid:修改后zookeeper分配的ID
  pZxid:子节点的ID
  cversion:子节点的version,子节点数据发生变化,该值会变化
  dataVersion:当前数据的版本号,当前节点的数据修改后该版本号会改变
  aclVersion:权限,当前节点权限发生变化后,该值累加1
  ephemeraOwner:0x0表示持久节点,否则表示临时节点
  dataLength:数据长度
  mumChildren:有几个子节点

4、ZK特性1:session的基本原理
  1) 客户端与服务端之间的连接存在会话
  2) 每个会话都可以设置一个超时时间
  3) 心跳结束,session过期
  4) session过期,则临时节点znode会被抛弃
  5) 心跳机制:客户端向服务端的ping包请求

5、ZK特性2:watcher机制
  1) 针对每个节点的操作,都会有一个监督者watcher
  2) 当监控的某个对象znode发生了变化,则触发watcher事件
  3) zk中的watcher是一次性的,触发后立即销毁
  4) 父节点、子节点增删改都能触发其watcher
  5) 针对不同类型的操作,触发的watcher事件也不同
     (子)节点创建事件
     (子)节点删除事件
     (子)节点数据变化事件

6、watcher事件类型
   可以使用get /lwh watch给节点设置事件,这样当发生create、delete、set等时就会触发事件

   父节点的watcher事件类型
   1) NodeCreated事件,create会触发
   2) NodeDataChanged事件,set会触发
   3) NodeDeleted事件,delete触发

   子节点的watcher事件类型
   1) NodeChildrenChanged事件,ls给父节点设置watcher,创建子节点会触发该事件
      如ls /lwh watch,然后create /lwh/abc 88,则会触发NodeChildrenChanged事件

      同理,删除子节点也会触发NodeChildrenChanged事件

      修改子节点不会触发事件

   watcher使用场景:统一资源配置

7、ACL(access control lists)权限控制
   针对节点可以设置相关读写等权限,目的是为了保障数据安全性
   权限permissions可以指定不同的权限范围以及角色
   ACL命令行
   1) getAcl：获取某个节点的acl权限信息
   2) setAcl: 设置某个节点的acl权限信息
   3) addauth: 输入认证授权信息