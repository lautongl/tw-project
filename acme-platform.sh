#!/bin/bash

# Acme Engineering Platform CLI
API_BASE_URL="${API_BASE_URL:-http://localhost:8080}"

show_help() {
    echo "🚀 Acme Engineering Platform"
    echo ""
    echo "USAGE:"
    echo "  ./acme-platform <bucket-name>    Create S3 bucket"
    echo "  ./acme-platform help             Show this help"
    echo ""
    echo "EXAMPLES:"
    echo "  ./acme-platform my-app-data"
    echo "  ./acme-platform analytics-bucket-$(date +%Y%m%d)"
    echo ""
    echo "PLATFORM API: ${API_BASE_URL}"
}

create_bucket() {
    local bucket_name=$1
    
    echo "🔄 Creating S3 bucket: $bucket_name"
    echo "📍 Via Platform API: ${API_BASE_URL}"
    
    # Chama SUA API Spring Boot (mesmo endpoint que funciona no Insomnia)
    response=$(curl -s -w "\n%{http_code}" -X POST \
        "${API_BASE_URL}/api/s3/bucket?bucketName=${bucket_name}")
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n -1)
    
    if [ "$http_code" -eq 200 ]; then
        echo "✅ SUCCESS: $body"
        echo "📋 Monitor: https://github.com/lautongl/tw-project/actions"
    else
        echo "❌ ERROR: HTTP $http_code"
        if [ -n "$body" ]; then
            echo "$body"
        fi
        echo "💡 Make sure your Platform API is running on ${API_BASE_URL}"
    fi
}

# Main logic
if [ -z "$1" ] || [ "$1" = "help" ]; then
    show_help
else
    create_bucket "$1"
fi
