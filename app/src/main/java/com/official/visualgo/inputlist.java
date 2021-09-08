package com.official.visualgo;

public class inputlist {
    String command,instruction;

    public inputlist(){

    }
    public inputlist(String command, String instruction) {
        this.command = command;
        this.instruction = instruction;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
