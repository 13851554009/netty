package com.example.netty;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

public class zorecopy {
    public static void main(String[] args) {
        ByteBuffer byteBuffer =  ByteBuffer.allocateDirect(1024);
        ((DirectBuffer) byteBuffer).cleaner().clean();
    }

//    public static void read() {
//        final ChannelConfig config = config();
//        final ChannelPipeline pipeline = pipeline();
//        final ByteBufAllocator allocator = config.getAllocator();
//        final RecvByteBufAllocator.Handle allocHandle = recvBufAllocHandle();
//        allocHandle.reset(config);
//        ByteBuf byteBuf = null;
//        boolean close = false;
//        try {
//            do {
//                byteBuf = allocHandle.allocate(allocator);
//                allocHandle.lastBytesRead(doReadBytes(byteBuf));
//                if (allocHandle.lastBytesRead() <= 0) {
//                    byteBuf.release();
//                    byteBuf = null;
//                    close = allocHandle.lastBytesRead() < 0;
//                    break;
//                }
//                allocHandle.incMessagesRead(1);
//                readPending = false;
//                pipeline.fireChannelRead(byteBuf);
//                byteBuf = null;
//            } while (allocHandle.continueReading());
//            allocHandle.readComplete();
//            pipeline.fireChannelReadComplete();
//            if (close) {
//                closeOnRead(pipeline);
//            }
//        } catch (Throwable t) {
//            handleReadException(pipeline, byteBuf, t, close, allocHandle);
//        } finally {
//            if (!readPending && !config.isAutoRead()) {
//                removeReadOp();
//            }
//        }
//
//    }


}
