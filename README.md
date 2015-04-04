android-sms-rpc
===============

Android SMS gate. Can send messages after GET requests to phone and transmit incoming messages to external web service.

Target API: v19(4.4 - KitKat). No deprecated functions.

API (draft)
-
Send message:
```
http://local.ip:port/messages/send?to=999999&text=wwwwwwwwwwwwwwwwwwwwwwwwwwww
```
Batch send to subscribers list:
```
http://local.ip:port/messages/batchsend?text=wwwwwwwwwwwwwwwwwwwwwwww
```
Setting subscribers list:
```
http://local.ip:port/settings/subscribers/set?subscribers=99999999999,99999999999999,999999999999,99999999
```
Getting subscribers list
```
http://local.ip:port/settings/subscribers/list
```
Add subscriber to list:
```
http://local.ip:port/settings/subscribers/add?no=999999999999
```
Remove subscriber from list:
```
http://local.ip:port/settings/subscribers/rem?no=999999999999
```
Adding external server for subscriber's answers postprocessing:
```
http://local.ip:port/settings/transmit/server?address=http%3A%2F%2Fserver.com%2Fsms
http://192.168.1.54:8080/settings/transmit/server?address=http%3A%2F%2F192.168.1.53%3A5000%2Fmessages%2Fincome%2Fnew
```
SMS Gate generate next request `http://server.com/sms?from=99999999999&text=wwwwwwwww`to external server

All data for RPC mast be encoded (URL encoded).

