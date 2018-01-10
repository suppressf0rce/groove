package Interpreter.memory;

import Interpreter.Terminal;

import java.util.Random;

public class Memory {

    public Frame global_frame;
    public Stack stack;

    public Memory() {
        global_frame = new Frame("GLOBAL_MEMORY", null);
        stack = new Stack();
    }


    public void declare(Object key, Object value, String type) {
        if (value == null) {
            Double rand = Math.pow(2, 32);
            value = new Random().nextInt(rand.intValue());
        }
        Scope ins_scope;
        if (stack.current_frame != null) {
            ins_scope = stack.current_frame.current_scope;
        } else
            ins_scope = global_frame.current_scope;

        if (type != null)
            ins_scope.set_item(key, new Terminal(type, value.toString()));
        else
            ins_scope.set_item(key, value);
    }

    public void set_item(Object key, Object value) {
        Scope ins_scope;
        if (stack.current_frame != null) {
            ins_scope = stack.current_frame.current_scope;
        } else
            ins_scope = global_frame.current_scope;

        Scope curr_scope = ins_scope;
        while (curr_scope != null && !curr_scope.contains(key))
            curr_scope = curr_scope.parent_scope;

        if (curr_scope != null)
            ins_scope = curr_scope;
        ins_scope.set_item(key, value);
    }

    public Object get_item(Object item) {
        Scope curr_scope;
        if (stack.current_frame != null) {
            curr_scope = stack.current_frame.current_scope;
        } else {
            curr_scope = global_frame.current_scope;
        }

        while (curr_scope != null && !curr_scope.contains(item)) {
            curr_scope = curr_scope.parent_scope;
        }
        if (curr_scope != null)
            return curr_scope.get_item(item);
        else
            return null;
    }

    public void new_frame(String frame_name) {
        stack.new_frame(frame_name, global_frame.current_scope);
    }

    public void del_frame() {
        stack.del_frame();
    }

    public void new_scope() {
        stack.current_frame.new_scope();
    }

    public void del_scope() {
        stack.current_frame.del_scope();
    }

}
