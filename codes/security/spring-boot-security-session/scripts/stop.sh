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

killProcessByPidFile() {
	file=$1

	if [[ -e ${file} ]]; then
		if [[ -s ${file} ]]; then
			pid=$(cat ${file})
			kill -9 ${pid}
			rm -f ${file}
			printf "\n${YELLOW}<<<< ${pid} 已停止\n${RESET}"
		else
			printf "\n${RED} PID 文件为空\n${RESET}"
		fi
	else
		printf "\n${RED} PID 文件不存在\n${RESET}"
	fi
}

killProcessByPidFile "../target/pid1"
killProcessByPidFile "../target/pid2"
killProcessByPidFile "../target/pid3"
