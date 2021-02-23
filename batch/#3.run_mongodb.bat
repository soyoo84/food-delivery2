@echo off
call setenv.bat
rem --dbpath 는 경로를 바꾸고 싶을 때...
mongod --dbpath D:\DEV\DATA\mongodb
pause ..