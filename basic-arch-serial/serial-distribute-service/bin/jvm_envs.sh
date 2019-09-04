jar_home=.
main=com.gzs.learn.serial.SerialDistributeServer
module=serial-distribute-service-1.0.0-SNAPSHOT.jar
pidfile=logs/app.pid
logfile=logs/info.`date +%F`.log
JAVA_OPTS="-server -Xms512m -Xmx512m -XX:NewSize=128m -XX:MaxNewSize=256m -Xss512k -Ddubbo.application.logger=slf4j"
