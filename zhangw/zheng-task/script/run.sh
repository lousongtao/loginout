#!/bin/sh

case "$1" in
	start)
		echo "Start Application ..."

		if [ -s app.pid ] && kill -0 `cat app.pid` 2>/dev/null; then
			echo "Application is running."
		else
			java -Xms128M -Xmx256M -XX:-UseGCOverheadLimit -cp . -Dfile.encoding=UTF-8 -Djava.ext.dirs=./lib com.zhang.task.TaskApplication 1>out.txt 2>&1 &
			echo $! > app.pid
		fi
		;;
	stop)
		echo "Stop Application..."
		if [ -s app.pid ] && kill -0 `cat app.pid` 2>/dev/null; then
			kill `cat app.pid`
			rm  app.pid
		else
			echo "Application is not running."
		fi
		;;
	restart)
		$0 stop
		sleep 2
		$0 start
		;;
	*)
		echo "Usage : $0 [start|stop|restart]"
		;;
esac

exit 0