package craftgpt.craftgpt;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class CommandClass implements CommandExecutor {
    CraftGPT plugin;
    CommandClass(CraftGPT plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            String message = "";
            for(String arg : args){
                System.out.println(arg);
                message= message+" "+arg;
            }
            String jsonInputString = "{ \"role\": \"user\", \"content\": \""+message+"\" }";

            CompletableFuture<String> responseFuture = ApiClass.sendPostRequest(jsonInputString);

            responseFuture.thenAccept(response -> {
                if (response != null) {
                    JsonObject jsonObject = JsonParser.parseString(responseFuture.join()).getAsJsonObject();
                    String content = jsonObject.get("content").toString();
                    content = content = content.replaceAll("\\n","");
                    sender.sendMessage("<Marve> " +content);
                } else {
                    System.out.println("Error occurred while making the request.");
                    plugin.getServer().broadcastMessage("<Marve> Sorry i'm out of order X_X");
                }
            });

            return true;
        }

        return false;
    }
}
