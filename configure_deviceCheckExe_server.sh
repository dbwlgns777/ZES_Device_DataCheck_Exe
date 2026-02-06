#!/bin/bash

SERVER_HOME=/home/zestech/
CONFIG_DIR=/src/main/resources
SERVER_DIR=influxdb_device_server
SERVER_PATH=${SERVER_HOME}${SERVER_DIR}
CONFIG_PATH=${SERVER_PATH}${CONFIG_DIR}
# Function to create directories and files

create_start_script() {
# Content for the start.sh script

echo "#!/bin/bash
DEFAULT_PORT=9600
DIR_PATH=${SERVER_PATH}
FILE_NAME=ZES_Device_DataCheck_Exe-1.0-SNAPSHOT-standalone.jar

# Check if the port argument is provided
if [ -z "\$1" ]; then
echo "Port number not provided. Using default port: \$DEFAULT_PORT"
PORT=\$DEFAULT_PORT
else
PORT=\$1
fi

# Check if the port is open
if lsof -i :\$PORT > /dev/null; then
echo "Port \$PORT is already in use. Killing the existing process..."
# Find and kill the process running on the port
lsof -t -i :\$PORT | xargs kill

    while lsof -i :\$PORT > /dev/null; do
        sleep 1
    done

    echo "Process on port \$PORT has been killed."
fi

# Start the Java process using nohup
cd \$DIR_PATH
nohup java -jar \$FILE_NAME \$PORT &
" | sudo tee /usr/local/bin/start
}

create_config_files() {
  echo "influxdb.url=http://211.191.181.163:8086
influxdb.token=q1R4DrQ-fo3651cA5cK4lXeuKuRLCwmz1J9-ehwBotukPNeTjV6vohf4Q3PXKdT0Vlvt_dupmQI85N3wqMidOA==
influxdb.org=zestech
influxdb.orgId=c0af27a1ed579441
influxdb.bucket=2023" | tee ${CONFIG_PATH}/config.properties
  echo "jdbcUrl=jdbc:mysql://211.191.181.161:8989/ZES_Authentication?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true&serverTimezone=Asia/Seoul&rewriteBatchedStatements=true&allowMultiQueries=true
username=zestech
password=zestech121212!
maximumPoolSize=20
maxLifetime=30000
leakDetectionThreshold=2000
dataSource.prepStmtCacheSqlLimit=2048
dataSource.cachePrepStmts=true
dataSource.useServerPrepStmts=true" | tee ${CONFIG_PATH}/hikari.properties
}

create_directories() {
# Create the directory influxdb_device_server
mkdir -p ${CONFIG_PATH}

    # Call the function to create the start.sh script
    create_start_script

    create_config_files

    # Make the start.sh script executable
    sudo chmod +x /usr/local/bin/start

    # Create the logrotate configuration file
    echo "${SERVER_PATH}/nohup.out {
        # Rotate log daily
        daily

        # Keep 7 rotated copies (one week worth of logs)
        rotate 7

        # Compress rotated files
        compress

        # Add date to rotated file names
        dateext

        # Copy and truncate the original log file instead of renaming
        copytruncate
    }" | sudo tee /etc/logrotate.d/nohup
}

# Call the function to create directories and files
create_directories