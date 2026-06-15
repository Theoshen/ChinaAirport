#!/bin/sh

set -eu

BASE_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
PROPERTIES_FILE="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$PROPERTIES_FILE" ]; then
  echo "Missing $PROPERTIES_FILE" >&2
  exit 1
fi

DISTRIBUTION_URL="$(sed -n 's/^distributionUrl=//p' "$PROPERTIES_FILE" | tail -n 1)"
if [ -z "$DISTRIBUTION_URL" ]; then
  echo "distributionUrl is not configured in $PROPERTIES_FILE" >&2
  exit 1
fi

ARCHIVE_NAME="${DISTRIBUTION_URL##*/}"
MAVEN_DIR_NAME="${ARCHIVE_NAME%-bin.zip}"
MAVEN_HOME="$WRAPPER_DIR/$MAVEN_DIR_NAME"
ARCHIVE_FILE="$WRAPPER_DIR/$ARCHIVE_NAME"

download() {
  if command -v curl >/dev/null 2>&1; then
    curl -fL -C - --connect-timeout 20 --max-time 900 "$DISTRIBUTION_URL" -o "$ARCHIVE_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -c -T 900 "$DISTRIBUTION_URL" -O "$ARCHIVE_FILE"
  else
    echo "Neither curl nor wget is available to download Maven." >&2
    exit 1
  fi
}

download_fresh() {
  if command -v curl >/dev/null 2>&1; then
    curl -fL --connect-timeout 20 --max-time 900 "$DISTRIBUTION_URL" -o "$ARCHIVE_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -T 900 "$DISTRIBUTION_URL" -O "$ARCHIVE_FILE"
  else
    echo "Neither curl nor wget is available to download Maven." >&2
    exit 1
  fi
}

archive_valid() {
  if command -v unzip >/dev/null 2>&1; then
    unzip -tq "$ARCHIVE_FILE" >/dev/null 2>&1
  elif command -v jar >/dev/null 2>&1; then
    jar tf "$ARCHIVE_FILE" >/dev/null 2>&1
  else
    echo "Either unzip or jar is required to verify Maven." >&2
    exit 1
  fi
}

extract() {
  if command -v unzip >/dev/null 2>&1; then
    unzip -q "$ARCHIVE_FILE" -d "$WRAPPER_DIR"
  elif command -v jar >/dev/null 2>&1; then
    (cd "$WRAPPER_DIR" && jar xf "$ARCHIVE_FILE")
  else
    echo "Either unzip or jar is required to extract Maven." >&2
    exit 1
  fi
}

mkdir -p "$WRAPPER_DIR"

if [ ! -x "$MAVEN_HOME/bin/mvn" ]; then
  if [ -f "$ARCHIVE_FILE" ] && ! archive_valid; then
    echo "Maven archive is incomplete; resuming download for $ARCHIVE_FILE"
    download
  fi
  if [ -f "$ARCHIVE_FILE" ] && ! archive_valid; then
    echo "Maven archive is still invalid; downloading a fresh copy"
    rm -f "$ARCHIVE_FILE"
    download_fresh
  fi
  if [ ! -f "$ARCHIVE_FILE" ]; then
    echo "Downloading Maven from $DISTRIBUTION_URL"
    download_fresh
  fi
  if ! archive_valid; then
    echo "Downloaded Maven archive is not valid: $ARCHIVE_FILE" >&2
    exit 1
  fi
  echo "Extracting Maven to $WRAPPER_DIR"
  extract
  chmod +x "$MAVEN_HOME/bin/mvn"
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
