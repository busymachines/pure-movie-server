# usage:
# ./phms-server.sh start  — starts the server from a bundled jar, if the jar doesn't exist it creates it
# ./phms-server.sh clean  — starts the server from a newly created jar, deletes old jar
# ./phms-server.sh        — default, same as start

#Basically, it just run two commands:
#sbt mkJar;
#./modules/apps/server/target/universal/stage/bin/phms-app-server

CMD_CLEAN='clean'
CMD_START='start'

#the name of the executable jar that is created using `sbt mkJar`
SBT_ARGS='mkJar'
SCRIPT_NAME='modules/apps/server/target/universal/stage/bin/phms-app-server'
RUNTIME_SETUP_SCRIPT='runtime-start.sh'
RUNTIME_STOP_SCRIPT='runtime-stop.sh'

warning() {
  echo ""
  echo "*********"
  echo "[WARNING]: $1";
  echo "*********"
  echo ""
}

info() {
  echo ""
  echo "[INFO] $1"
  echo ""
}

if (( $# == 0 ));
then
  warning "-- no command line arguments specified. Defaulting to command: $CMD_START"
  user_cmd="$CMD_START"
else
  user_cmd="$1"
fi

if [ "$user_cmd" == "$CMD_START" ]
then
  if [ -f $SCRIPT_NAME ]
  then
    info "executable already exists."
  else
    info "executable does not exist. creating using: 'sbt $SBT_ARGS'"
    sbt $SBT_ARGS
  fi #SCRIPT_NAME

  info "starting up dockers ./$RUNTIME_SETUP_SCRIPT"
  ./$RUNTIME_SETUP_SCRIPT

  info "running: './$SCRIPT_NAME'"
  ./$SCRIPT_NAME

elif [ "$user_cmd" == "$CMD_CLEAN" ]
then
  info "clean + recreating jar: 'sbt mkJar'"
  sbt mkJar

  info "stopping dockers ./$RUNTIME_STOP_SCRIPT"
  ./$RUNTIME_STOP_SCRIPT

    info "starting up dockers ./$RUNTIME_SETUP_SCRIPT"
  ./$RUNTIME_SETUP_SCRIPT

  info "running: './$SCRIPT_NAME'"
  ./$SCRIPT_NAME
fi


