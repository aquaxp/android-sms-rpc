android-sms-rpc
===============

Android SMS gate. Can send messages after GET requests to phone and transmit incoming messages to external web service.

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
