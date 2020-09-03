#!/bin/bash

logdir=/opt/module/rtlog

case $1 in
"start")
  for host in hadoop102 hadoop103 hadoop104
  do
      echo "-------------启动$host日志服务----------------"
      ssh $host "source /etc/profile; nohup java -jar $logdir/gmall-logger-0.0.1-SNAPSHOT.jar >/opt/module/rtlog/app.log 2>&1 &"
  done
  ;;
"stop")
  for host in hadoop102 hadoop103 hadoop104
  do
      echo "-------------停止$host日志服务----------------"
      ssh $host "source /etc/profile;jps -l | awk '/gmall-logger-0.0.1-SNAPSHOT.jar/{print \$1}' | xargs kill -9"
  done
  ;;
*)
  echo "输入参数错误。。。"
  echo "gmall_cluster.sh start启动脚本"
  echo "gmall_cluster.sh stop停止脚本"
  ;;
esac