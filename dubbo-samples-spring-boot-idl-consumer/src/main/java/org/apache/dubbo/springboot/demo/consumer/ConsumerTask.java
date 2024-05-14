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
package org.apache.dubbo.springboot.demo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.springboot.demo.idl.FileSlice;
import org.apache.dubbo.springboot.demo.idl.FileTransferService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class ConsumerTask implements CommandLineRunner {
    @DubboReference
    private FileTransferService fileTransferService;
    private byte[] readLocalFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
    @Override
    public void run(String... args) throws Exception {
        // 读取本地文件内容
        String filePath = "E:\\Pictures\\Screenshots\\屏幕截图(559).png";
        byte[] fileContent = readLocalFile(filePath);
        // 将字节流进行切片，并转换成字符串数组，每个字符串存储一部分内容
        int chunkSize = 50; // 切片大小
        int numChunks = (int) Math.ceil((double) fileContent.length / chunkSize);
        String[] fileChunks = new String[numChunks];
        for (int i = 0; i < numChunks; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize, fileContent.length);
            byte[] chunkBytes = new byte[end - start];
            System.arraycopy(fileContent, start, chunkBytes, 0, end - start);
            fileChunks[i] = Base64.getEncoder().encodeToString(chunkBytes);
        }
        // 调用provider服务，将切片后的字符串数组传递给provider
        for (String chunk : fileChunks) {
            FileSlice request = FileSlice.newBuilder()
                    .setContent(chunk)
                    .build();
            fileTransferService.transfer(request);
        }
        System.out.println("Consumer：文件传输完成");
    }
}