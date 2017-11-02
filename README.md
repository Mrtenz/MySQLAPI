# MySQLAPI
This library is an easy to use MySQL API made for Spigot plugins, using connection pools for better performance.

### Maven
You can use this library as a Maven dependency, by adding the following to the repositories:
```XML
        <repository>
            <id>mrten-releases</id>
            <name>Mrten's Release Repo</name>
            <url>http://repo.mrten.nl/content/repositories/releases/</url>
        </repository>
```
And add the following to dependencies:
```XML
        <dependency>
            <groupId>me.mrten</groupId>
            <artifactId>MySQLAPI</artifactId>
            <version>1.2-SNAPSHOT</version>
        </dependency>
```
Either shade the library into your own plugin, or add the library to your server's plugins folder. If you don't want to shade, make sure you put ```depend: [MySQLAPI]``` in your plugin.yml.

### License
The project is licensed under the Apache 2.0 License. Look at the LICENSE file for more information.

### Using MySQLAPI
Check out the [Wiki](https://github.com/Mrtenz/MySQLAPI/wiki) for usage.
