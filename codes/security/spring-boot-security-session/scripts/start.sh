#!/usr/bin/env bash

# ----------------------------------------------------------------------------------
# 控制台颜色
BLACK="\033[1;30m"
RED="\033[1;31m"
GREEN="\033[1;32m"
YELLOW="\033[1;33m"
BLUE="\033[1;34m"
PURPLE="\033[1;35m"
CYAN="\033[1;36m"
RESET="$(tput sgr0)"
# ----------------------------------------------------------------------------------

printf "\n${BLUE}>>>> 编译开始\n${RESET}"
cd ../
mvn clean package -DskipTests=true -B -U
printf "\n${GREEN}<<<< 编译结束\n${RESET}"

printf "\n${BLUE}>>>> 启动应用\n${RESET}"
cd target
nohup java -Dserver.port=8080 -jar spring-boot-security-session-1.0.0.jar 2>&1 &
if [[ -n $! ]]; then
	echo $! > pid1
	printf "\n${GREEN}<<<< PID: $! 已启动，可以访问：http://127.0.0.1:8080/ \n${RESET}"
else
	printf "\n${RED}<<<< http://127.0.0.1:8080/ 启动失败\n${RESET}"
fi

nohup java -Dserver.port=8081 -jar spring-boot-security-session-1.0.0.jar 2>&1 &
if [[ -n $! ]]; then
	echo $! > pid2
	printf "\n${GREEN}<<<< PID: $! 已启动，可以访问：http://127.0.0.1:8081/ \n${RESET}"
else
	printf "\n${RED}<<<< http://127.0.0.1:8081/ 启动失败\n${RESET}"
fi

nohup java -Dserver.port=8082 -jar spring-boot-security-session-1.0.0.jar 2>&1 &
if [[ -n $! ]]; then
	echo $! > pid3
	printf "\n${GREEN}<<<< PID: $! 已启动，可以访问：http://127.0.0.1:8082/ \n${RESET}"
else
	printf "\n${RED}<<<< http://127.0.0.1:8082/ 启动失败\n${RESET}"
fi
cd -
