package Interpreter.memory;

import java.util.ArrayList;

public class Stack {

    public ArrayList<Frame> frames;
    public Frame current_frame;

    public Stack() {
        frames = new ArrayList<>();
        current_frame = null;
    }

    public void new_frame(String frame_name, Scope global_scope) {
        Frame frame = new Frame(frame_name, global_scope);
        frames.add(frame);
        current_frame = frame;
    }

    public void del_frame() {
        frames.remove(frames.size() - 1);
        if (frames.size() > 0) {
            current_frame = frames.get(frames.size() - 1);
        } else {
            current_frame = null;
        }
    }

}
