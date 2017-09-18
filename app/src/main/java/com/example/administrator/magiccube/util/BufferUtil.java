package com.example.administrator.magiccube.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BufferUtil {

    public static final int FLOAT_BYTE_LENGTH = 4;
    public static FloatBuffer floatArray2ByteBuffer(float[] arr){
        //分配字节缓冲区空间,存放顶点坐标
        FloatBuffer ibb=ByteBuffer.allocateDirect(arr.length*FLOAT_BYTE_LENGTH).order(ByteOrder.nativeOrder()).asFloatBuffer();
        ibb.put(arr);
        //定位指针位置,从该位置开始读取顶点数据
        ibb.position(0);
        return ibb;
    }
    public static ByteBuffer byteArray2ByteBuffer(byte[] arr){
        //分配字节缓冲区空间,存放顶点坐标
        ByteBuffer  ibb=ByteBuffer.allocateDirect(arr.length).order(ByteOrder.nativeOrder());
        ibb.put(arr);
        //定位指针位置,从该位置开始读取顶点数据
        ibb.position(0);
        return ibb;
    }
}
