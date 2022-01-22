package cn.jin.plugin;

public class GreetingPluginExt {

    private String greeter = "Tester";
    private String message = "Message from the plugin!";

    public String getGreeter() {
        return greeter;
    }

    public void setGreeter(String greeter) {
        this.greeter = greeter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}