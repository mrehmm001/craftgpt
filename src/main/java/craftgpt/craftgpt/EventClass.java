package craftgpt.craftgpt;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.concurrent.CompletableFuture;

public class EventClass implements Listener {
    CraftGPT plugin;
    EventClass(CraftGPT plugin){
        this.plugin=plugin;
    }

    @EventHandler
    public void onMessage(PlayerChatEvent event){
        event.setCancelled(true);
        if(event.getPlayer() instanceof Player){
            Player player = event.getPlayer();
            plugin.getServer().broadcastMessage("<"+player.getDisplayName()+"> " +event.getMessage());
            String message = player.getDisplayName()+": "+event.getMessage();
            if(message.toLowerCase().contains("@marve")){
                String jsonInputString = "{ \"role\": \"user\", \"content\": \""+message+"\" }";

                CompletableFuture<String> responseFuture = ApiClass.sendPostRequest(jsonInputString);

                responseFuture.thenAccept(response -> {
                    if (response != null) {
                        JsonObject jsonObject = JsonParser.parseString(responseFuture.join()).getAsJsonObject();
                        String content = jsonObject.get("content").toString();
                        content = content = content.replaceAll("\\n","");
                        plugin.getServer().broadcastMessage("<Marve> " +content);
                    } else {
                        System.out.println("Error occurred while making the request.");
                        plugin.getServer().broadcastMessage("<Marve> Sorry i'm out of order X_X");
                    }
                });
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        plugin.getServer().broadcastMessage(event.getDeathMessage());
        String message = event.getDeathMessage();
        event.setDeathMessage("");
        if(event.getEntity() instanceof Player){
            Player player = event.getEntity();
            String jsonInputString = "{ \"role\": \"user\", \"content\": \""+message+"\" }";

            CompletableFuture<String> responseFuture = ApiClass.getDeathMessageReponse(jsonInputString);

            responseFuture.thenAccept(response -> {
                if (response != null) {
                    JsonObject jsonObject = JsonParser.parseString(responseFuture.join()).getAsJsonObject();
                    String content = jsonObject.get("content").toString();
                    content = content.replaceAll("\\n","");
                    plugin.getServer().broadcastMessage("<Marve> " +content);
                } else {
                    System.out.println("Error occurred while making the request.");
                }
            });
        }
    }
}
