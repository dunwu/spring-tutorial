@echo off

echo 修改项目版本号
cd ../codes/parent
mvn versions:set -DnewVersion=1.0.3
cd ../../scripts
pause
