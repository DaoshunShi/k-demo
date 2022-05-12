package com.example.kdemo.netty.discard;

import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class DiscardServer {

    @Setter
    private int port;

    public void run() throws Exception {
//        EventLoopGroup
//        TODO 
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}
