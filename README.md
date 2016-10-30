# MySQLAPI
This library is an easy to use MySQL API made for Spigot plugins, using connection pools for better performance.

### Maven
You can use this library as a Maven dependency, by adding the following to the repositories:
```XML
        <repository>
            <id>mrten-repo-releases</id>
            <url>http://repo.mrten.nl/repository/releases/</url>
        </repository>
```
And add the following to dependencies:
```XML
        <dependency>
            <groupId>me.mrten</groupId>
            <artifactId>MySQLAPI</artifactId>
            <version>1.0</version>
        </dependency>
```
Either shade the library into your own plugin, or add the library to your server's plugins folder. If you don't want to shade, make sure you put ```depend: [MySQLAPI]``` in your plugin.yml.

### License
The project is licensed under the Apache 2.0 License. Look at the LICENSE file for more information.

### Using MySQLAPI
You have to initialize a MySQL object, and use that to create a new Query. Have a look at the example below.
```Java
package me.mrten.example;

import me.mrten.mysqlapi.MySQL;
import me.mrten.mysqlapi.queries.Callback;
import me.mrten.mysqlapi.queries.CreateTableQuery;
import me.mrten.mysqlapi.queries.InsertQuery;
import me.mrten.mysqlapi.queries.Query;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class ExamplePlugin extends JavaPlugin implements Listener {

    private MySQL mysql;

    @Override
    public void onEnable() {
        // Create a MySQL object and connect to the database
        mysql = new MySQL();
        mysql.connect(getConfig().getString("mysql.host"), getConfig().getString("mysql.port"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"), getConfig().getString("mysql.database"));

        getServer().getPluginManager().registerEvents(this, this);

        createTable();
    }

    @Override
    public void onDisable() {
        // Disconnect from the database when the plugin disables
        mysql.disconnect();
    }

    private void createTable() {
        // Create a new SQL query
        String sql = new CreateTableQuery("players")
                .ifNotExists()
                .column("id", "INT(11) AUTO_INCREMENT")
                .column("uuid", "VARCHAR(255) NOT NULL")
                .column("username", "VARCHAR(255) NOT NULL")
                .primaryKey("id")
                .build();

        Query query = new Query(mysql, sql);
        // Execute it asynchronously
        query.executeUpdateAsync();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String sql = new InsertQuery("players")
                .value("uuid")
                .value("username")
                .build();

        Query query = new Query(mysql, sql);
        // Set the parameters of uuid and username
        query.setParameter(1, player.getUniqueId().toString());
        query.setParameter(2, player.getName());
        // Execute the query asynchronously and provide a callback
        query.executeUpdateAsync(new CreatePlayerCallback(player));
    }

    private class CreatePlayerCallback implements Callback<Integer, SQLException> {

        private Player player;

        public CreatePlayerCallback(Player player){
            this.player = player;
        }

        public void call(Integer integer, SQLException e) {
            if (e != null) {
                e.printStackTrace();
            } else {
                // If there were no errors this will be shown once the query has completed
                player.sendMessage("Your data has been inserted!");
            }
        }
    }

}
```
A more complete explanation of how to use the API will be available soon.
