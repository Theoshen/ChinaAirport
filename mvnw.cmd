@echo off
setlocal

set "BASE_DIR=%~dp0"
set "WRAPPER_DIR=%BASE_DIR%.mvn\wrapper"
set "PROPERTIES_FILE=%WRAPPER_DIR%\maven-wrapper.properties"

if not exist "%PROPERTIES_FILE%" (
  echo Missing %PROPERTIES_FILE% 1>&2
  exit /b 1
)

for /f "tokens=1,* delims==" %%A in ('findstr /b "distributionUrl=" "%PROPERTIES_FILE%"') do set "DISTRIBUTION_URL=%%B"
if "%DISTRIBUTION_URL%"=="" (
  echo distributionUrl is not configured in %PROPERTIES_FILE% 1>&2
  exit /b 1
)

for %%A in ("%DISTRIBUTION_URL%") do set "ARCHIVE_NAME=%%~nxA"
set "MAVEN_DIR_NAME=%ARCHIVE_NAME:-bin.zip=%"
set "MAVEN_HOME=%WRAPPER_DIR%\%MAVEN_DIR_NAME%"
set "ARCHIVE_FILE=%WRAPPER_DIR%\%ARCHIVE_NAME%"

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  if not exist "%ARCHIVE_FILE%" (
    echo Downloading Maven from %DISTRIBUTION_URL%
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%ARCHIVE_FILE%'"
    if errorlevel 1 exit /b 1
  )
  echo Extracting Maven to %WRAPPER_DIR%
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Path '%ARCHIVE_FILE%' -DestinationPath '%WRAPPER_DIR%' -Force"
  if errorlevel 1 exit /b 1
)

call "%MAVEN_HOME%\bin\mvn.cmd" %*
endlocal
