#!/bin/sh
path=`dirname $0`
cd $path
java -cp .:lib/ibxm.jar:lib/lwjgl.jar:lib/slick.jar:lib/nifty-0.0.5.jar:lib/xpp3-1.1.4c.jar:lib/jinput.jar:xswing_v0.359.jar:lib/slick-util.jar:lib/xpp3-1.1.4c.jar:lib/nifty-default-styles.jar: -Djava.library.path=. xswing.start.XSwingWithOptions showOptionStarter=true
