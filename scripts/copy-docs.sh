#!/usr/bin/env bash

# Code generated for API Clients. DO NOT EDIT.

set -e

rm -rf docs/
mkdir docs
echo 'scala-api.docs.ngrok.com' >docs/CNAME
cp -av target/scala-2.13/api/* docs
