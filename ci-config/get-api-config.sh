#!/bin/bash

TOKEN=$DROPDOX_TOKEN
CONFIG_FILE_NAME=$TMDB_CONFIG_FILE_NAME

echo "Downloading credentials..."

curl -X POST https://content.dropboxapi.com/2/files/download_zip \
    --header "Authorization: Bearer $TOKEN" \
    --header "Dropbox-API-Arg: {\"path\":\"/$CONFIG_FILE_NAME\"}" \
    -o "$CONFIG_FILE_NAME.zip" &&
    unzip -o "$CONFIG_FILE_NAME.zip" &&
    mv "$CONFIG_FILE_NAME"/api-config.properties ./ &&
    rm "$CONFIG_FILE_NAME.zip" &&
    rm -r -f "$CONFIG_FILE_NAME"
