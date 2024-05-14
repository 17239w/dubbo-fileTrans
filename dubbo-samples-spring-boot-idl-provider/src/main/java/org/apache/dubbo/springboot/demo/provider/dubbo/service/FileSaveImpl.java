/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.springboot.demo.provider.dubbo.service;

import com.google.protobuf.ByteString;
import org.apache.dubbo.springboot.demo.idl.DubboFileTransferServiceTriple;
import org.apache.dubbo.springboot.demo.idl.FileSlice;
import org.apache.dubbo.springboot.demo.idl.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@DubboService
public class FileSaveImpl extends DubboFileTransferServiceTriple.FileTransferServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSaveImpl.class);
    @Override
    public FileSlice transfer(FileSlice request) {
        LOGGER.info("Provider收到了保存byte array到本地的请求: {}", request);
        // Combine byte strings into a single byte array
        byte[] combinedBytes = combineByteArrays(request.getContentList());
        String filePath = "E:\\javaPros\\filetrans2\\dubbo-samples-spring-boot-idl\\save\\save.txt";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(combinedBytes);
            LOGGER.info("文件保存到了: {}", filePath);
        } catch (IOException e) {
            LOGGER.error("报错文件报错: {}", e.getMessage());
        }
        return request;
    }
    // Combine multiple byte strings into a single byte array
    private byte[] combineByteArrays(List<ByteString> byteList) {
        int totalLength = byteList.stream().mapToInt(ByteString::size).sum();
        byte[] combinedBytes = new byte[totalLength];
        int currentIndex = 0;
        for (ByteString byteString : byteList) {
            byte[] chunkBytes = byteString.toByteArray();
            System.arraycopy(chunkBytes, 0, combinedBytes, currentIndex, chunkBytes.length);
            currentIndex += chunkBytes.length;
        }
        return combinedBytes;
    }
}
