#!/bin/bash
BUCKET_NAME=$1
aws s3 mb s3://$BUCKET_NAME --region us-east-1
