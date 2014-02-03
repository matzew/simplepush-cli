simplepush-cli
==============

A Java-based SimplePush Notification sender CLI.

* Build the project ```mvn clean install```
* Execute the cli inside of the ```target``` folder

```
./simple-push send -U theUpdateURL -V version=123
```

If the ```-V``` is not provided the current timestamp (```System.currentTimeMillis```) is used.
