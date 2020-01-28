#!/bin/sh
cf login -a https://api.sys.pas.home.kwpivotal.com -u demouser -p demouser --skip-ssl-validation

#push the app
cf push

#create gateway service instance
cf create-service p.gateway standard demo-gateway -c '{ "host": "api", "domain": "apps.pas.home.kwpivotal.com"}'
cf create-service p.gateway standard demo-gateway -c '{ "host": "api", "domain": "apps.pas.home.kwpivotal.com", "sso": { "plan": "uaa-sso" } , "count": 2 }'
#cf update-service demo-gateway -c '{ "sso": { "plan": "uaa-sso" } }'

#bind app to gateway service instance by providing route config
cf bind-service car-service demo-gateway -c '{ "routes": [{"method": "GET","path": "/car-service/**", "sso-enabled": true, "filters": ["AddRequestHeader=X-request-cars, cars-header", "RateLimit=1,10s"]}]}'


#scopes currently is not working in 1.0.0, will be fixed in 1.0.1 https://pivotal.slack.com/archives/C0DRHC7DW/p1579286063010500
cf bind-service car-service demo-gateway -c '{ "routes": [{"method": "GET","path": "/car-service/**", "sso-enabled": true, "scopes": ["car-admin.read"], "filters": ["AddRequestHeader=X-request-cars, cars-header", "RateLimit=1,10s"]}]}'

cf bind-service car-service demo-gateway -c '{ "routes": [ \
{"method": "GET", "path": "/car-service/**", "sso-enabled": true, "filters": ["AddRequestHeader=X-request-cars, cars-header", "RateLimit=1,10s"]},\
{"uri": "lb://car-service.apps.internal", "method": "POST","path": "/car-service/**", "sso-enabled": true, "scopes": ["car-admin.write"], "token-relay": true},\
{"uri": "lb://car-service.apps.internal", "method": "DELETE","path": "/car-service/**", "sso-enabled": true, "scopes": ["car-admin.delete"], "token-relay": true}\
] }'

#cf bind-service greeter my-gateway -c '{"routes": [ {"path": "/greeting/**", "sso-enabled": true, "scopes": ["greeting.read"] } ] }'
#cf bind-service cook my-gateway -c '{ "routes": [ { "path": "/cook/**", "sso-enabled": true, "filters": ["Scopes=menu.read"] } ] }'

