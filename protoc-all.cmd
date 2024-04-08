@echo off
set "PROTO_DIR=src/grpc/proto"
set "JAVA_OUT_DIR=src/main/java"
set "PYTHON_OUT_DIR=src/main/python"

:: 生成Java代码
for %%f in (%PROTO_DIR%\*.proto) do (
  protoc --java_out=%JAVA_OUT_DIR% --proto_path=%PROTO_DIR% %%f
)

:: 生成Python代码
for %%f in (%PROTO_DIR%\*.proto) do (
  protoc --python_out=%PYTHON_OUT_DIR% --proto_path=%PROTO_DIR% %%f
)
