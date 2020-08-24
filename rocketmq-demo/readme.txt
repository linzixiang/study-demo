启动/停止NameServer
    nohup sh bin/mqnamesrv &
    sh bin/mqshutdown namesrv
启动/停止broker
    nohup sh bin/mqbroker -n 192.168.1.215:9876 &
    sh bin/mqshutdown broker
创建topic
    sh mqadmin updateTopic -n 192.168.1.215:9876 -b 192.168.1.215:10911 -t TopicTest
    sh mqadmin updateTopic -n 192.168.1.215:9876 -b 192.168.1.215:10911 -t TransactionTopic