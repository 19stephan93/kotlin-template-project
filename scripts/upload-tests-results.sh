#!/bin/sh

MY_PATH="`dirname \"$0\"`"              # relative
MY_PATH="`( cd \"$MY_PATH\" && pwd )`"  # absolutized and normalized
if [ -z "$MY_PATH" ] ; then
  # error; for some reason, the path is not accessible
  # to the script (e.g. permissions re-evaled after suid)
  exit 1  # fail
fi

login() {
  curl --request POST "$1/login" \
  --header "Content-Type: application/json" \
  --data-raw "{
      \"username\": \"$2\",
      \"password\": \"$3\"
  }" -c /tmp/allure-server-cookies.txt
}

send_results() {
  for file in $MY_PATH/../build/reports/allure-results/{.,}*;
  do
    if [ !  -d "$file" ]; then
      curl -b /tmp/allure-server-cookies.txt -X POST "$1/send-results?project_id=$2&force_project_creation=true" -H "X-CSRF-TOKEN: $3" -H "accept: */*" -H  "Content-Type: multipart/form-data" -F "files[]=@$file"
    fi
  done
}

generate_report() {
  curl -b /tmp/allure-server-cookies.txt -X GET "$1/generate-report?project_id=$2" -H "X-CSRF-TOKEN: $3" -H "accept: */*"
}

login "$1" "$2" "$3"
csrf_access_token=$(cat /tmp/allure-server-cookies.txt | grep csrf_access_token | awk -F" " '{print $7}')
send_results "$1" "$4" "$csrf_access_token"
generate_report "$1" "$4" "$csrf_access_token"

